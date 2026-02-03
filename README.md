**Java Blockchain Project**

This project is a simplified blockchain implementation written in Java, developed as a personal learning project by following and independently coding along with a tutorial by CryptoKshatriya. The goal of the project was to gain a practical understanding of fundamental blockchain concepts such as block creation, cryptographic hashing, chain validation, and mining.
Although a tutorial was used as guidance, all code was written manually to reinforce understanding of how blockchain systems work at a low level.

There are a 3 different classes:

**Block Class:**
  
The ChainEssentials.Block class represents an individual block within the blockchain. Each block stores transaction-related data, including a transaction message (e.g., “User sent $X to OtherUser”), a timestamp, the hash of the previous block, a nonce, and the block’s own hash.
This class is responsible for generating the block’s hash using SHA-256 and supports mining by adjusting the nonce until a valid hash meeting the difficulty requirement is produced.

**Utility Class**
 
The Utility class contains helper methods used throughout the project. It includes a cryptographic helper function that takes a string as input and generates a SHA-256 hash. This ensures data integrity and plays a key role in maintaining the immutability of the blockchain.

**Wallet Class:**

The Wallet class stores user-related information, including the public and private keys and the user’s current balance. It is also responsible for initiating transactions by verifying whether the user has sufficient funds before allowing a transfer to proceed.

**Transaction class:**

This class represents and manages transactions between two parties. It stores details such as the sender ID, receiver ID, transfer amount, transaction inputs and outputs, and a generated digital signature.The digital signature verifies that the transaction was authorised by the sender and helps prevent unauthorised access. Transaction inputs reference previous transactions to confirm that the sender has sufficient funds, while transaction outputs record the transferred amount and the receiver’s information.

**TransactionInput and TransactionOutput classess:**

These classes are extensions of the transaction class and these hold the information of the inputs and outputs as previously stated.

**Chain Class:**

Chain class manages the blockchain as an ordered sequence of blocks stored in an ArrayList. It handles:
  Adding new blocks to the chain
  Mining blocks
  Validating the blockchain by checking hash consistency and block linkage
  The class also provides functionality to output the entire blockchain and verify whether the chain is valid, returning true if all integrity checks pass and false otherwise.

**How it works:**

This project simulates blockchain-style transactions similar to those used in cryptocurrencies. While the process resembles a traditional bank-to-bank transfer, it improves security by using blockchain verification mechanisms. Each transaction is stored inside a block, which contains information such as the transfer amount, sender ID, and receiver ID. Before a transaction is processed, the system first checks whether the sender has sufficient funds. If the sender does not have enough balance, the transaction is cancelled. If the balance check is successful, the system performs a proof-of-work process. This involves a background computation that takes time to complete and uses the previous block’s ID to validate the transaction. Any attempt to alter the transaction data would change the previous block ID, causing the validation to fail. Once the proof-of-work is completed, the transaction is considered valid only if the computed block ID matches the stored previous block ID. Although this process is intentionally time-consuming, it improves security by making unauthorised changes difficult, as every transaction must pass the proof-of-work verification.
