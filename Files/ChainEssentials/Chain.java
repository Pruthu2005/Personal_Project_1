package ChainEssentials;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
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
        chain.add(block1);
        System.out.println("Mining block 1...");
        chain.get(0).mining(zeros);

        Block block2 = new Block(block1.blockId);
        chain.add(block2);
        System.out.println("Mining block 2...");
        chain.get(1).mining(zeros);

        Block block3 = new Block(block2.blockId);
        chain.add(block3);
        System.out.println("Mining block 3...");
        chain.get(2).mining(zeros);

        System.out.println("Is ChainEssentials.Block ChainEssentials.Chain valid: " + isVal());

        String chainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
        System.out.println("\nChainEssentials.Block ChainEssentials.Chain: ");
        System.out.println(chainJson);
    }

    public static Boolean isVal(){
        Block current;
        Block previous;
        String blockIdGoal = new String(new char[zeros]).replace('\0','0');

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


        }
        return true;
    }


}
