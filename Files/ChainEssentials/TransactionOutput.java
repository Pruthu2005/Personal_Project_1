package ChainEssentials;

import java.security.PublicKey;

public class TransactionOutput {
    public String ID;
    public PublicKey receiver;
    public float amount;
    public String previousTransactionID;

    public TransactionOutput(PublicKey receiver, float value, String previousTransactionID){
        this.receiver = receiver;
        this.amount = value;
        this.previousTransactionID = previousTransactionID;
        this.ID = Utilities.cryptohelp(Utilities.getString(receiver)+Float.toString(amount)+previousTransactionID);
    }

    public boolean Mine(PublicKey publicKey){
        return (publicKey == receiver);
    }
}
