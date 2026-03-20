package src;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\nSimulador de Script de Bitcoin");
            System.out.println("1. Ejecutar Script de forma Manual");
            System.out.println("2. Demo P2PKH (correcto / incorrecto)");
            System.out.println("3. Ver Opcodes disponibles");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");

            // Validación de entrada
            if(scanner.hasNextInt()){
                opcion = scanner.nextInt();
            } else {
                System.out.println("Entrada inválida, ingrese un número.");
                scanner.next();
                continue;
            }
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
                    System.out.println("\n=== DEMO P2PKH ===");

                    try {
                        Wallet wallet = new Wallet();
                        wallet.generateKeys();

                        String mensaje = "tx-demo";

                        byte[] signature = SignatureUtil.sign(mensaje, wallet.privateKey);

                        String signatureHex = HashUtil.bytesToHex(signature);
                        String pubKeyHex = HashUtil.bytesToHex(wallet.publicKey.getEncoded());
                        String pubKeyHash = HashUtil.sha256(pubKeyHex);

                        //CORRECTO
                        String p2pkh = signatureHex + " " + pubKeyHex +
                                " OP_DUP OP_HASH160 " + pubKeyHash +
                                " OP_EQUALVERIFY OP_CHECKSIG";

                        System.out.println("\n--- P2PKH CORRECTO ---");
                        System.out.println("Script:");
                        System.out.println(p2pkh);

                        InterpreteBitCoin interprete2 = new InterpreteBitCoin();
                        boolean result = interprete2.execute(p2pkh);

                        System.out.println("Resultado: " + result);

                        //INCORRECTO (firma alterada)
                        String signatureHexBad = signatureHex.substring(1);

                        String p2pkhBad = signatureHexBad + " " + pubKeyHex +
                                " OP_DUP OP_HASH160 " + pubKeyHash +
                                " OP_EQUALVERIFY OP_CHECKSIG";

                        System.out.println("\n--- P2PKH INCORRECTO ---");
                        System.out.println("Script:");
                        System.out.println(p2pkhBad);

                        InterpreteBitCoin interprete3 = new InterpreteBitCoin();

                        try {
                            boolean resultBad = interprete3.execute(p2pkhBad);
                            System.out.println("Resultado: " + resultBad);
                        } catch (Exception e) {
                            System.out.println("Resultado: false");
                            System.out.println("Motivo: Firma inválida o datos corruptos");
                        }

                    } catch (Exception e) {
                        System.out.println("Error en P2PKH: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- OPCODES DISPONIBLES ---");

                    System.out.println("\nStack:");
                    System.out.println("OP_DUP - Duplica el elemento superior");
                    System.out.println("OP_DROP - Elimina el elemento superior");
                    System.out.println("OP_SWAP - Intercambia los dos elementos superiores");
                    System.out.println("OP_OVER - Duplica el segundo elemento");

                    System.out.println("\nLógica:");
                    System.out.println("OP_EQUAL - Compara dos valores");
                    System.out.println("OP_EQUALVERIFY - Compara y falla si son distintos");
                    System.out.println("OP_NOT - Invierte 0/1");
                    System.out.println("OP_BOOLAND - AND lógico");
                    System.out.println("OP_BOOLOR - OR lógico");

                    System.out.println("\nAritmética:");
                    System.out.println("OP_ADD - Suma");
                    System.out.println("OP_SUB - Resta");
                    System.out.println("OP_NUMEQUALVERIFY - Compara números");

                    System.out.println("\nComparación:");
                    System.out.println("OP_LESSTHAN - Menor que");
                    System.out.println("OP_GREATERTHAN - Mayor que");
                    System.out.println("OP_LESSTHANOREQUAL - Menor o igual");
                    System.out.println("OP_GREATERTHANOREQUAL - Mayor o igual");

                    System.out.println("\nControl:");
                    System.out.println("OP_IF / OP_NOTIF - Condicional");
                    System.out.println("OP_ELSE / OP_ENDIF - Flujo alterno");
                    System.out.println("OP_VERIFY - Verifica y falla si es 0");
                    System.out.println("OP_RETURN - Termina con error");

                    System.out.println("\nCriptografía:");
                    System.out.println("OP_SHA256 - Hash SHA256");
                    System.out.println("OP_HASH160 - Hash simplificado");
                    System.out.println("OP_HASH256 - Doble hash");

                    System.out.println("\nFirmas:");
                    System.out.println("OP_CHECKSIG - Verifica firma");
                    System.out.println("OP_CHECKSIGVERIFY - Verifica y falla si es inválida");

                    break;

                case 4:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }

        scanner.close();
    }
}