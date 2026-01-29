package ChainEssentials;
import java.security.*;
import java.util.Base64;

public class Utilities {

    public static String cryptohelp(String input){

        try{
            MessageDigest apply = MessageDigest.getInstance("SHA-256");
            byte[] id = apply.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < id.length; i++){
                String hex = Integer.toHexString(0xff & id[i]);
                if (hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] applyECDSA(PrivateKey privateKey, String input){
        Signature signature;
        byte[] output = new byte[0];
        try {
            signature = Signature.getInstance("ECDSA","BC");
            signature.initSign(privateKey);
            byte[] stringByte = input.getBytes();
            signature.update(stringByte);
            byte[] OGSig = signature.sign();
            output = OGSig;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return output;
    }

    public static boolean verifySig(PublicKey publicKey, String input, byte[] sign){
        try {
            Signature signVerify = Signature.getInstance("ECDSA", "BC");
            signVerify.initVerify(publicKey);
            signVerify.update(input.getBytes());
            return signVerify.verify(sign);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getString(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
