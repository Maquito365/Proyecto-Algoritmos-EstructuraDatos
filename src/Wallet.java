package src;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet{
    // Cada wallet tiene un par de claves: una privada para firmar transacciones y una pública para recibir fondos y verificar firmas.
    public PrivateKey privateKey;
        public PublicKey  publicKey; // Clave pública para recibir fondos y verificar firmas
        // Método para generar un par de claves (privada y pública) usando criptografía de curva elíptica (EC)
        public void generateKeys() throws Exception {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("EC");
            gen.initialize(256);
            KeyPair pair = gen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
        }

        // Método para obtener una dirección de wallet a partir de la clave pública (simulada como un hash de la clave pública)s
        public String getAddress() {
            return HashUtil.bytesToHex(publicKey.getEncoded()).substring(0, 16) + "...";
        }
    

}