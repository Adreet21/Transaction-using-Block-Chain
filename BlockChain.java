/*
This is the main class of the blockchain. 
It creates the genesis block and the wallets for the users.
 */

 // Import the necessary libraries
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BlockChain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	public static int difficulty = 5;
	public static float minimumTransaction = 0.1f;
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;

	public static void main(String[] args) {

		//Add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//Create wallets:
		walletA = new Wallet();
		walletB = new Wallet();		
		Wallet coinbase = new Wallet();
		
		//Create genesis transaction, which sends 100 Coin to wallet A: 
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 1000f, null);
		genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		System.out.println("Creating and Mining Genesis block... ");
		Block block0 = new Block("0");
		block0.addTransaction(genesisTransaction);
		addBlock(block0);
		
		//Interface
		System.out.println("\nWallet A's balance is: " + walletA.getBalance());
		System.out.println("Wallet B's balance is: " + walletB.getBalance() + "\n");
		int blocknumber = 0;
		Scanner scanner = new Scanner(System.in);
		while (walletA.getBalance() > 0){
			System.out.println("How much Coin do you want to send to Wallet B?");
			float amount = scanner.nextFloat();
			blocknumber++;
			Block currentblocknumber = new Block(blockchain.get(blocknumber - 1).hash);
			currentblocknumber.addTransaction(walletA.sendFunds(walletB.publicKey, amount));
			addBlock(currentblocknumber);
			System.out.println("\nWallet A's balance is: " + walletA.getBalance());
			System.out.println("Wallet B's balance is: " + walletB.getBalance());
		}
		scanner.close();
		isChainValid();
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			
			//compare registered hash and calculated hash:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("\nCurrent Hashes not equal");
				return false;
			}
			
			//compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("\nPrevious Hashes not equal");
				return false;
			}
			
			//check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("\nThis block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for (int t = 0; t < currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);
				if(!currentTransaction.verifySignature()) {
					System.out.println("\nSignature on Transaction(" + t + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("\nInputs are note equal to outputs on Transaction(" + t + ")");
					return false; 
				}
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					if(tempOutput == null) {
						System.out.println("\nReferenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					if(input.UTXO.value != tempOutput.value) {
						System.out.println("\nReferenced input Transaction(" + t + ") value is Invalid");
						return false;
					}
					tempUTXOs.remove(input.transactionOutputId);
				}
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("\nTransaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("\nTransaction(" + t + ") output 'change' is not sender.");
					return false;
				}
			}
		}
		System.out.println("\nBlockchain is VALID!!!");
		return true;
	}

	public static void addBlock(Block newBlock) {
    newBlock.mineBlock(difficulty);
    newBlock.hash = newBlock.calculateHash();
    blockchain.add(newBlock);
	}
}