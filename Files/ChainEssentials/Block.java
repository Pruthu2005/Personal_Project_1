package ChainEssentials;

import java.util.Date;

public class Block {
    public String blockId;
    public String preBlockID;
    private String data;
    private long time;
    private int calc = 0;

    public Block(String data, String preHash){
        this.data = data;
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
}
