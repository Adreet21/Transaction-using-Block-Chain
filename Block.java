/*
This class is used to make the blocks in the blockchain.
 */

// Import the necessary libraries
import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash; //The hash of the block
	public String previousHash; //The hash of the previous block
	public String merkleRoot; //The Merkle Root of the block
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //The transactions in the block
	public long timeStamp; //The time the block was created
	public int nonce; //The number of times the block has been mined
	
	//Block Constructor.  
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleRoot
				);
		return calculatedhash;
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((!"0".equals(previousHash))) {
			if((transaction.processTransaction() != true)) {
				System.out.println("\nTransaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("\nTransaction Successfully added to Block");
		return true;
	}
}