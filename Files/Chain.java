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
}
