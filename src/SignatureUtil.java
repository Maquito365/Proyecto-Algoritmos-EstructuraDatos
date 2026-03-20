package src;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;

public class SignatureUtil {
    // Método para firmar un mensaje usando una clave privada
    public static byte[] sign(String data, PrivateKey privateKey) throws Exception {
        Signature signer = Signature.getInstance("SHA256withECDSA");
        signer.initSign(privateKey);
        signer.update(data.getBytes());
        return signer.sign();
    }
    // Método para verificar una firma usando una clave pública
    public static boolean verify(String data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withECDSA");
        verifier.initVerify(publicKey);
        verifier.update(data.getBytes());
        return verifier.verify(signature);
    }
    // Método para convertir una cadena hexadecimal a una clave pública
    public static PublicKey stringToPublicKey(String hex) throws Exception {
    byte[] keyBytes = HashUtil.hexToBytes(hex);
    // Crea un objeto X509EncodedKeySpec a partir de los bytes de la clave y luego 
    // utiliza un KeyFactory para generar la clave pública a partir de ese especificación.
    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("EC");
    
    return keyFactory.generatePublic(spec);
}
}
