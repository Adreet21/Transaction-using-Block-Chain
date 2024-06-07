# Transaction using Block Chain

This is a simple Transaction programme between wallets.<br>
It uses block chain algorithm to make it secure.<br>
This was made to intreduce myself to the world of blockchain technologies.


## What is a BlockChain?<br>
A blockchain is a type of digital ledger or record book that keeps track of transactions in a secure and transparent way.<br>
This system is decentralized, meaning no single person or organization controls it. It ensures that the information recorded is transparent, secure, and can't be easily tampered with.


## How have I implemented it?<br>
I used each block in the block chain to keep the transaction data.
Each block in the block chain contains one transaction.

I used SHA-256 algorithm to a string to crate hash values for the blocks,<br> 
apply the ECDSA Signature and verify a string signature to ensure the data was not tempered with.<br>

                                                                  
## Installation

Make a clone of all the files in the repository.<br>

You need to have Java abd JDK installed.

You have to add the JAR files in the "libs" file in your java library.<br>
OR<br>
if youre using vscode you can drag and drop the libs file in the reference folder under the java project section.

## Output

After successfully running the code, in the terminal there should be a prompt asking how much money you want to transfer to wallet B.<br>
You can send upto 1000 coins. with Multiple transactions. After you finished all your money the program will end and it will say if the block that you used was valid(Not tampered with).<br>

Demo video: https://www.youtube.com/watch?v=6p6xZo2PXBo

## Not functioning?
If you run into difficulties or errors in the code please feel free to reach out.<br>
Email: contact@shahmeer.xyz