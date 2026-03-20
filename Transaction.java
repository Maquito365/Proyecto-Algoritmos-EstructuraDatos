import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction {
    // Información de la transacción: remitente, destinatario y cantidad
    public final String sender;
    public final String recipient;
    public final double amount;
    // Firma digital de la transacción y clave pública del remitente para verificación
    public byte[] signature;
    public PublicKey senderKey;
    // Constructor para inicializar una transacción con el remitente, destinatario y cantidad
    public Transaction(String sender, String recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    // mensaje que se firma
    public String getData() {
        return sender + "->" + recipient + ":" + amount;
    }

    //firmar usando SignatureUtil
    public void sign(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        this.signature = SignatureUtil.sign(getData(), privateKey);
        this.senderKey = publicKey;
    }

    //verificar firma
    public boolean isValid() throws Exception {
        if(signature == null || senderKey == null) return false;
        return SignatureUtil.verify(getData(), signature, senderKey);
    }
}