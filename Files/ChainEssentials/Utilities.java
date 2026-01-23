package ChainEssentials;

import java.security.MessageDigest;

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
}
