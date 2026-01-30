package ChainEssentials;

public class TransactionInput {
    public String transactionOutID;
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutID){
        this.transactionOutID = transactionOutID;
    }
}
