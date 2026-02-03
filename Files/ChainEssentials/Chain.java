package ChainEssentials;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;

public class Chain {
    public static ArrayList<Block> chain = new ArrayList<>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();
    public static int zeros = 3;
    public static float minTransaction = 0.1f;
    public static Wallet wallet1;
    public static Wallet wallet2;
    public static Transaction OGTransaction;

    public static void main(String[] args){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        wallet1 = new Wallet();
        wallet2 = new Wallet();
        Wallet coins = new Wallet();

        OGTransaction = new Transaction(coins.publicKey, wallet1.publicKey, 100f,null);
        OGTransaction.genSig(coins.privateKey);
        OGTransaction.TransactionID = "0";
        OGTransaction.outputs.add(new TransactionOutput(OGTransaction.receiverID, OGTransaction.amount, OGTransaction.TransactionID));
        UTXOs.put(OGTransaction.outputs.get(0).ID,OGTransaction.outputs.get(0));

        System.out.println("Creating and Mining Original block");
        Block OGblock = new Block("0");
        OGblock.addTrans(OGTransaction);
        chain.add(OGblock);

        System.out.println("Private and Public keys: ");
        System.out.println(Utilities.getString(wallet1.privateKey));
        System.out.println(Utilities.getString(wallet1.publicKey));

        System.out.println("Creating Transaction: ");
        Transaction transaction = new Transaction(wallet1.publicKey, wallet2.publicKey, 5,null);
        transaction.genSig(wallet1.privateKey);

        System.out.println("Checking if transaction is valid: ");
        System.out.println(transaction.verifySig());

        Block block1 = new Block(OGblock.blockId);
        System.out.println("Balance of Wallet 1: " + wallet1.getBal());
        System.out.println("Trying to send funds to Wallet 2. Amount: 40");
        block1.addTrans(wallet1.send(wallet2.publicKey, 40f));
        chain.add(block1);
        System.out.println("After Transaction: ");
        System.out.println("Balance of Wallet 1: " + wallet1.getBal());
        System.out.println("Balance of Wallet 2: " + wallet2.getBal());

        Block block2 = new Block(block1.blockId);
        System.out.println("Attempting to send more funds from wallet 1 to wallet 2. Amount: 1000");
        block2.addTrans(wallet1.send(wallet2.publicKey, 1000f));
        chain.add(block2);
        System.out.println("After Transaction: ");
        System.out.println("Balance of Wallet 1: " + wallet1.getBal());
        System.out.println("Balance of Wallet 2: " + wallet2.getBal());

        Block block3 = new Block(block2.blockId);
        System.out.println("Wallet 2 is trying to send some funds back to wallet 1. Amount: 30");
        block3.addTrans(wallet2.send(wallet1.publicKey, 30f));
        chain.add(block3);
        System.out.println("After Transaction: ");
        System.out.println("Balance of Wallet 1: " + wallet1.getBal());
        System.out.println("Balance of Wallet 2: " + wallet2.getBal());

        isVal();
    }

    public static Boolean isVal(){
        Block current;
        Block previous;
        String blockIdGoal = new String(new char[zeros]).replace('\0','0');
        HashMap<String, TransactionOutput> tempCur = new HashMap<String,TransactionOutput>();
        tempCur.put(OGTransaction.outputs.get(0).ID,OGTransaction.outputs.get(0));

        for (int i=1; i< chain.size(); i++){
            current = chain.get(i);
            previous = chain.get(i-1);
            if (!current.blockId.equals(current.calcBlockID())){
                System.out.println("ChainEssentials.Block ID's are not the same. Invalid");
                return false;
            }
            if (!previous.blockId.equals(current.preBlockID)){
                System.out.println("ChainEssentials.Block ID's are not the same. Invalid");
                return false;
            }
            if (!current.blockId.substring(0, zeros).equals(blockIdGoal)){
                System.out.println("The block cannot be mined");
                return false;
            }

            TransactionOutput transOutput;
            for (int m=0; i<current.transactions.size(); i++){
                Transaction currentTrans = current.transactions.get(m);

                if (!currentTrans.verifySig()){
                    System.out.println("Invalid Signature");
                    return false;
                }

                if (currentTrans.getInputsamount() != currentTrans.getOutputsamount()){
                    System.out.println("Inputs are not equal to outputs");
                    return false;
                }

                for (TransactionInput t: currentTrans.inputs){
                    transOutput = tempCur.get(t.transactionOutID);
                    if (transOutput == null){
                        System.out.println("Missing inputs of Transaction");
                        return false;
                    }
                    if (t.UTXO.amount != transOutput.amount){
                        System.out.println("Input transaction value is invalid");
                        return false;
                    }
                    tempCur.remove(t.transactionOutID);
                }

                for (TransactionOutput o: currentTrans.outputs){
                    tempCur.put(o.ID,o);
                }

                if (currentTrans.outputs.get(0).receiver != currentTrans.receiverID){
                    System.out.println("Incorrect receiver");
                    return false;
                }

                if (currentTrans.outputs.get(0).receiver != currentTrans.senderID){
                    System.out.println("Incorrect sender");
                    return false;
                }

            }

        }
        System.out.println("Valid Chain");
        return true;
    }

    public static void addBlock(Block newBlock){
        newBlock.mining(zeros);
        chain.add(newBlock);
    }

}
