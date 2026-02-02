package ChainEssentials;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String blockId;
    public String preBlockID;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private String data;
    private long time;
    private int calc = 0;

    public Block(String preHash){
        this.preBlockID = preHash;
        this.time = new Date().getTime();
        this.blockId = calcBlockID();
    }

    public String calcBlockID(){
        String calcBlockID = Utilities.cryptohelp(preBlockID + Long.toString(time) + data + calc);
        return calcBlockID;
    }

    public void mining(int zeros){
        String goal = new String (new char[zeros]).replace('\0','0');
        while (!blockId.substring( 0, zeros ).equals(goal)){
            calc += 1;
            blockId = calcBlockID();
        }
        System.out.println("ChainEssentials.Block has been mined. ChainEssentials.Block ID: " + blockId);
    }

    public boolean addTrans(Transaction transaction){
        if (transaction == null){
            return false;
        }
        if (preBlockID != "0"){
            if (transaction.TransactionProcessing() == false){
                System.out.println("Transaction Failed.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction successfully added to the block");
        return true;
    }
}
