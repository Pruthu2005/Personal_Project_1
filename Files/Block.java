import java.util.Date;

public class Block {
    public String hash;
    public String preHash;
    private String data;
    private long time;

    public Block(String data, String preHash){
        this.data = data;
        this.preHash = preHash;
        this.time = new Date().getTime();
    }
}
