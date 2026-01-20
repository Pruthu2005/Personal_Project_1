import java.util.ArrayList;
import com.google.gson.*;

public class Chain {
    public static ArrayList<Block> chain = new ArrayList<>();

    public static void main(String[] args){
        Block block1 = new Block("First Block","0");
        chain.add(block1);

        Block block2 = new Block("Second Block", block1.blockId);
        chain.add(block2);

        Block block3 = new Block("Third Block", block2.blockId);
        chain.add(block3);

        String chainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
        System.out.println(chainJson);
    }

    public static Boolean isVal(){
        Block current;
        Block previous;

        for (int i=1; i< chain.size(); i++){
            current = chain.get(i);
            previous = chain.get(i-1);
            if (!current.blockId.equals(current.calcBlockID())){
                System.out.println("Block ID's are not the same. Invalid");
                return false;
            }
            if (!previous.blockId.equals(previous.calcBlockID())){
                System.out.println("Block ID's are not the same. Invalid");
                return false;
            }
        }
        return true;
    }


}
