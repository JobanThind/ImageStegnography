package Stegno;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Secure {
   private static String secret="138hgndkirysiogl!/";
   private static SecretKeySpec secretKey;
   private static byte[] key;

   public static void setKey(String myKey)                  //generate key from our given key
   {
      MessageDigest sha = null;                       //Hash value is called message digest
      try {

         key = myKey.getBytes("UTF-8");            //convert mykey to bytes array
         sha = MessageDigest.getInstance("SHA-1");             //get sha1 hash
         key = sha.digest(key);
         key = Arrays.copyOf(key, 16);                         //intialize key with padding
         secretKey = new SecretKeySpec(key, "AES");      //secret key is an interface that groups secret keys
      }
      catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
   }

   public static String encrypt(String strToEncrypt)
   {
      try
      {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);
         return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      }
      catch (Exception e)
      {
         System.out.println("Error while encrypting: " + e.toString());
      }
      return null;
   }

   public static String decrypt(String strToDecrypt)
   {
      try
      {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
         cipher.init(Cipher.DECRYPT_MODE, secretKey);
         return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      }
      catch (Exception e)
      {
         System.out.println("Error while decrypting: " + e.toString());
      }
      return null;
   }
}






