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

I used 

## The formula used:<br>
New Q(s,a) = Q(s,a) + α[R(s,a) + γ max Q'(s',a') - Q(s,a)]<br>where:<br>s = State<br>
                                                                 a = Action<br>
                                                                 α(Alpha) = Learning Rate<br>
                                                                 γ(Gamma) = Discount Rate<br>
                                                                 Q(s,a) = Current Q-Value<br>
                                                                 max Q'(s',a') = Maximum expected future reward<br>
                                                                 R(s,a) = Reward for taking an action at that state<br>

The values used were:<br>Step Size = 0.25<br>
                      Epsilon = 0.1<br>
                      γ(Gamma) = 0.95
                                                                  
## Installation

Make a clone of all the files in the repository.

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install following:

```bash
pip install matplotlib
pip install numpy
pip install pygame
pip install tqdm
```
Make sure you are using the correct version of Python (ideally Python 3).<br>
Keep all the documents in the same folder; if not, redirect them accordingly.<br>
Run the main.py file

## Output

After successfully running the code in the terminal, there should be a progress bar.<br>
After it reaches 100%, a maze map will automatically open in another window and visually show you the best path taken by the agent.

## Not functioning?
If you run into difficulties or errors in the code please feel free to reach out.<br>
Email: contact@shahmeer.xyz