import ChainEssentials.Utilities;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

    public String TransactionID;
    public PublicKey senderID;
    public PublicKey receiverID;
    public float amount;
    public byte[] identifier;

    public ArrayList inputs = new ArrayList<>();
    public ArrayList outputs = new ArrayList<>();

    public static int generatedSeq = 0;

    public Transaction(PublicKey sender, PublicKey receiver, float amount, ArrayList inputs){
        this.senderID = sender;
        this.receiverID = receiver;
        this.amount = amount;
        this.inputs = inputs;
    }

    private String TransactionHash(){
        generatedSeq += 1;
        return Utilities.cryptohelp(senderID.toString()+ receiverID.toString()+ Float.toString(amount));
    }
}
