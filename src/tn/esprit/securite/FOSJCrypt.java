package tn.esprit.securite;



//import com.google.common.primitives.Bytes;


import javabc.SecureRandom;
import com.codename1.util.Base64;
import com.google.common.io.BaseEncoding;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.crypto.Digest;

import org.bouncycastle.crypto.digests.SHA512Digest;
        


/**
 * FOSJCrypt fait le hachage et le cryptage comme le FOSUser(default encoder:Sha512) via application desktop Java.
 * La génération de salt et comme de FOSUser
 * La génération de hash et celle que FOSUser 5000 itération sur digester (une avant le boucle pour fusionner le salt avec le password) and ajout les accolades
 * Conversion
 * Google Guava utiliser pour convertir le hash en base64 (password column FOSUser contient hash convertit en base64 par cette API
 * Cette API utilise le même Algorithm par défaut utiliser par FOSUser(voir security.yml encoders{algorithm})
 * @author Zain ELabidine membre de " The Optimists"
 * @Email: zainelabidine.bensaleh@esprit.tn
 *Api à télécharger:
 * 1) bc de cd1
 * 
 *
 */
public class FOSJCrypt {
    private static final String ALGORITHM = "SHA-512";
    private static final int ITERATIONS = 5000;
    private static final int SALT_SIZE = 32;


    /**
     * Private constructor.
     */
    private FOSJCrypt() {
    }
    public static Sha512 crypt(String clairPass) {
        String gs=generateSalt();
        //System.out.println(gs);
        try {
            
            byte[] hash = encodePassword(clairPass, gs);
            
            return passwordToPersist(hash, clairPass, gs);
        } catch (Exception  ex) {
            System.out.println("++++++++"+ex.getMessage());
        }
        return null;
    }

    /**
     * tgeneri salt kif al yasn3ou fosuser(bel algorrithm par défaut :sh512)
     * igeneri 32 byte et y7awelhom en base64
     * ibadel el + bel .
     * ine7i el char el le5er
     * @return
     */
    private static String generateSalt() {       
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        String saltBase64_encoded=BaseEncoding.base64().encode(salt);
        String saltPlusDot= saltBase64_encoded.replace('+','.');
        return saltPlusDot.substring(0,saltPlusDot.length()-1);
    }

    /**
     * 4999 itération dans la boucles une seul avant la boucle
     * 1ére : fait merge passwrd et sal par méthode
     * dans la boucle il réutilise le hash précédant pour générer le nouveau message en clair
     * @param password
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    /*private static byte[] encodePassword(String password,String salt) throws UnsupportedEncodingException 
             {      
                 
        String mergedPasswordAndSalt =mergePasswordAndSalt(password, salt);
        SHA512Digest digester =new  SHA512Digest();   
        
        byte[] hash = new byte[digester.getDigestSize()];   
        digester.update(hash, 0, mergedPasswordAndSalt.length());
        digester.doFinal(hash, 0);
                 System.out.println("init hash= "+Base64.encode(hash));  
              byte[] c=new byte[88];
              c = concat(hash,mergedPasswordAndSalt.getBytes("UTF-8")); 
        for (int  i = 1; i < ITERATIONS; ++i) {  
                                                        
            digester.doFinal(c,0);            
                          
        }     
        System.out.println("FINAL hash= "+Base64.encode(hash));
        return hash;
    }*/
    private static byte[] encodePassword(String password,String salt) throws UnsupportedEncodingException 
             {      
                 
        String mergedPasswordAndSalt =mergePasswordAndSalt(password, salt);
        
        
        byte[] hash = new byte[88];   
            
       hash=digestt(mergedPasswordAndSalt.getBytes("UTF-8"));  
                 System.out.println("init hash= "+Base64.encode(hash));  
              
              
        for (int  i = 1; i < ITERATIONS; ++i) {                                                          
            hash=digestt(concat(hash,mergedPasswordAndSalt.getBytes("UTF-8")));
                          
        }     
        System.out.println("FINAL hash= "+Base64.encode(hash));
        return hash;
    }
    
    public static byte[] digestt(byte[] bytes) {
        Digest digest = new SHA512Digest();
        byte[] resBuf = new byte[digest.getDigestSize()];

        digest.update(bytes, 0, bytes.length);
        digest.doFinal(resBuf, 0);
        return resBuf;
    }
    
    
  
    /**
     * i7ot des accolade mnloul welle5er mta3 salt
     * @param pass
     * @param salt
     * @return
     */
    private static String mergePasswordAndSalt(String pass, String salt) {
        if (salt == null) {
            return salt;
        }
        String cg="{";String cd="}";
        return pass+cg+salt+cd;
    }
    /*
     *  paramétre: originalHash el hash ala mel base de donnés mais en byte donc lazem el hash ala en String tetbadel bytes
     */
    public static   boolean verifyPassword(byte[] originalHash, String passwordClair, String salt) throws
            Exception {
        byte[] comparisonHash = encodePassword(passwordClair, salt);
        return comparePasswords(originalHash, comparisonHash);
    }
    private static Sha512 passwordToPersist(byte[] originalHash, String password, String salt) throws
            Exception  {
        Sha512 sh=new Sha512();
        sh.setSalt(salt);sh.setHash(BaseEncoding.base64().encode(originalHash));
        
        return sh;
    }
    private static boolean comparePasswords(byte[] originalHash, byte[] comparisonHash) {
        int diff = originalHash.length ^ comparisonHash.length;
        for (int i = 0; i < originalHash.length && i < comparisonHash.length; i++) {
            diff |= originalHash[i] ^ comparisonHash[i];
        }

        return diff == 0;
    }

    public static boolean checkPassword(String originalHashStr, String passwordClair,String salt) throws Exception  {
        byte[] comparisonHash = encodePassword(passwordClair, salt);    
        byte[] originalHash = Base64.decode(originalHashStr.getBytes("UTF-8"));
        return comparePasswords(originalHash, comparisonHash);
    }
    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        byte[][] arr$ = arrays;
        int pos = arrays.length;

        for(int i$ = 0; i$ < pos; ++i$) {
            byte[] array = arr$[i$];
            length += array.length;
        }

        byte[] result = new byte[length];
        pos = 0;
        byte[][] arr$$=arrays; 
        arr$=arr$$;
        int len$ = arrays.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte[] array = arr$[i$];
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }

        return result;
    }
}
