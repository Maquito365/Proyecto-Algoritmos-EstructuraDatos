import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("\nSimulador de Script de Bitcoin");
            System.out.println("1. Ejecutar Script de forma Manual");
            System.out.println("2. Ejemplo de P2PKH");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    InterpreteBitCoin interprete1 = new InterpreteBitCoin();
                    System.out.print("Ingrese el Script a ejecutar (separe con espacios): ");
                    String script = scanner.nextLine();

                    try {
                        boolean resultado = interprete1.execute(script);
                        System.out.println("Resultado de la ejecución: " + resultado);
                    } catch (Exception e) {
                        System.out.println("Error al ejecutar el script: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\nEjecutando P2PKH real...");

                    try {
                        Wallet wallet = new Wallet();
                        wallet.generateKeys();

                        String mensaje = "tx-demo";

                        byte[] signature = SignatureUtil.sign(mensaje, wallet.privateKey);

                        String signatureHex = HashUtil.bytesToHex(signature);
                        String pubKeyHex = HashUtil.bytesToHex(wallet.publicKey.getEncoded());
                        String pubKeyHash = HashUtil.sha256(pubKeyHex);

                        String p2pkh = signatureHex + " " + pubKeyHex +
                                " OP_DUP OP_HASH160 " + pubKeyHash +
                                " OP_EQUALVERIFY OP_CHECKSIG";

                        System.out.println("Script generado:");
                        System.out.println(p2pkh);

                        InterpreteBitCoin interprete2 = new InterpreteBitCoin();
                        boolean result = interprete2.execute(p2pkh);

                        System.out.println("Resultado P2PKH: " + result);

                    } catch (Exception e) {
                        System.out.println("Error en P2PKH: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }

        scanner.close();
    }
}