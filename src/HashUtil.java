package src;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashUtil {

    // Método para calcular el hash SHA-256 de un texto dado
    public static String sha256(String text) throws NoSuchAlgorithmException {
        byte [] bytes = MessageDigest.getInstance("SHA-256").digest(text.getBytes());
        return HexFormat.of().formatHex(bytes);
    }
    // Método para convertir un arreglo de bytes a una cadena hexadecimal
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    // Método para convertir una cadena hexadecimal a un arreglo de bytes
    public static byte[] hexToBytes(String hex) {
    int length = hex.length();
    byte[] bytes = new byte[length / 2];
    // Itera sobre la cadena hexadecimal en pasos de 2 caracteres, convirtiendo cada par de caracteres a un byte y almacenándolo en el arreglo de bytes
    for (int i = 0; i < length; i += 2) {
        bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
    }

    return bytes;
}
}