package ChainEssentials;

import com.sun.source.tree.BreakTree;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

    public String TransactionID;
    public PublicKey senderID;
    public PublicKey receiverID;
    public float amount;
    public byte[] identifier;

    public ArrayList<TransactionInput> inputs = new ArrayList<>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

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

    public void genSig(PrivateKey privateKey){
        String data = Utilities.getString(senderID) + Utilities.getString(receiverID) + Float.toString(amount);
        identifier = Utilities.applyECDSA(privateKey, data);
    }

    public boolean verifySig(){
        String data = Utilities.getString(senderID) + Utilities.getString(receiverID) + Float.toString(amount);
        return Utilities.verifySig(senderID,data,identifier);
    }

    public boolean TransactionProcessing(){
        if (verifySig() == false){
            System.out.println("Transaction has failed");
            return false;
        }

        for (TransactionInput i: inputs){
            i.UTXO = Chain.UTXOs.get(i.transactionOutID);
        }

        if (getInputsamount() < 5){
            System.out.println("Transaction value is too small");
            return false;
        }

        float remaining = getInputsamount() - amount;
        TransactionID = TransactionHash();
        outputs.add(new TransactionOutput(this.receiverID, amount,TransactionID));
        outputs.add(new TransactionOutput(this.senderID,amount,TransactionID));

        for (TransactionOutput i : outputs){
            Chain.UTXOs.put(i.ID, i);
        }

        for (TransactionInput i : inputs){
            if (i.UTXO == null) continue;
            Chain.UTXOs.remove(i.UTXO.ID);
        }

        return true;
    }

    public float getInputsamount(){
        float sum = 0;
        for (TransactionInput i: inputs){
            if (i.UTXO == null) continue;
            sum += i.UTXO.amount;
        }
        return sum;
    }

    public float getOutputsamount(){
        float sum = 0;
        for (TransactionOutput i: outputs){
            sum += i.amount;
        }
        return sum;
    }
}
