package ChainEssentials;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import com.google.gson.*;

public class Chain {
    public static ArrayList<Block> chain = new ArrayList<>();
    public static int zeros = 5;
    public static Wallet wallet1;
    public static Wallet wallet2;

    public static void main(String[] args){
        Block block1 = new Block("First ChainEssentials.Block","0");
        chain.add(block1);
        System.out.println("Mining block 1...");
        chain.get(0).mining(zeros);

        Block block2 = new Block("Second ChainEssentials.Block", block1.blockId);
        chain.add(block2);
        System.out.println("Mining block 2...");
        chain.get(1).mining(zeros);

        Block block3 = new Block("Third ChainEssentials.Block", block2.blockId);
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
