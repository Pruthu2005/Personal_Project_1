import java.util.Date;

public class Block {
    public String blockId;
    public String preBlockID;
    private String data;
    private long time;

    public Block(String data, String preHash){
        this.data = data;
        this.preBlockID = preHash;
        this.time = new Date().getTime();
        this.blockId = calcBlockID();
    }

    public String calcBlockID(){
        String calcBlockID = Utilities.cryptohelp(preBlockID + Long.toString(time) + data);
        return calcBlockID;
    }
}
