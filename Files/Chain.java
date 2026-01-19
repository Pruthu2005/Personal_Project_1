import java.util.ArrayList;
import com.google.gson.*;

public class Chain {


    public static void main(String[] args){
        Block block1 = new Block("First Block","0");


        Block block2 = new Block("Second Block", block1.blockId);

        Block block3 = new Block("Third Block", block2.blockId);
    }
}
