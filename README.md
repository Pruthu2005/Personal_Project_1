**Java Blockchain Project**
  This project is a simplified blockchain implementation written in Java, developed as a personal learning project by following and independently coding along with a tutorial by CryptoKshatriya. The goal of the project was to gain a practical understanding of fundamental blockchain concepts such as block creation, cryptographic hashing, chain validation, and mining.
  Although a tutorial was used as guidance, all code was written manually to reinforce understanding of how blockchain systems work at a low level.

There are a 3 different classes:
  **ChainEssentials.Block Class**
    The ChainEssentials.Block class represents an individual block within the blockchain. Each block stores transaction-related data, including a transaction message (e.g., “User sent $X to OtherUser”), a timestamp, the hash of the previous block, a nonce, and the block’s own hash.
    This class is responsible for generating the block’s hash using SHA-256 and supports mining by adjusting the nonce until a valid hash meeting the difficulty requirement is produced.

 **Utility Class**
    The Utility class contains helper methods used throughout the project. It includes a cryptographic helper function that takes a string as input and generates a SHA-256 hash. This ensures data integrity and plays a key role in maintaining the immutability of the blockchain.

  **ChainEssentials.Chain Class**
    The ChainEssentials.Chain class manages the blockchain as an ordered sequence of blocks stored in an ArrayList. It handles:
    Adding new blocks to the chain
    Mining blocks
    Validating the blockchain by checking hash consistency and block linkage
    The class also provides functionality to output the entire blockchain and verify whether the chain is valid, returning true if all integrity checks pass and false otherwise.
