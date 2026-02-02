package ChainEssentials;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public Wallet(){
        this.generateKeys();
    }

    public void generateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime192v1");
            keyPairGenerator.initialize(ecGenParameterSpec,secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public float getBal(){
        float total_amount = 0;
        for (Map.Entry<String,TransactionOutput> i :Chain.UTXOs.entrySet()){
            TransactionOutput UTXO = i.getValue();
            if (UTXO.Mine(publicKey)){
                UTXOs.put(UTXO.ID, UTXO);
                total_amount += UTXO.amount;
            }
        }
        return total_amount;
    }

    public Transaction send(PublicKey _reciever, float value){
        if (getBal() < value){
            System.out.println("Insufficient funds. Transaction has been canceled");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total_amount = 0;
        for (Map.Entry<String, TransactionOutput> i: UTXOs.entrySet()){
            TransactionOutput UTXO = i.getValue();
            total_amount += UTXO.amount;
            inputs.add(new TransactionInput(UTXO.ID));
            if (total_amount > value){
                break;
            }
        }

        Transaction newTran = new Transaction(publicKey, _reciever, value,inputs);
        newTran.genSig(privateKey);

        for (TransactionInput i:inputs){
            UTXOs.remove(i.transactionOutID);
        }
        return newTran;
    }
}
