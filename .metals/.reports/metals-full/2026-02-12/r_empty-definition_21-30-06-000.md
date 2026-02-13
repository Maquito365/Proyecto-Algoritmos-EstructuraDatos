error id: file:///C:/Users/joses/OneDrive/Desktop/estructura%20de%20datos/Proyecto%201%20est,%20data/Proyecto-Algoritmos-EstructuraDatos/Main.java:java/util/Scanner#
file:///C:/Users/joses/OneDrive/Desktop/estructura%20de%20datos/Proyecto%201%20est,%20data/Proyecto-Algoritmos-EstructuraDatos/Main.java
empty definition using pc, found symbol in pc: java/util/Scanner#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 17
uri: file:///C:/Users/joses/OneDrive/Desktop/estructura%20de%20datos/Proyecto%201%20est,%20data/Proyecto-Algoritmos-EstructuraDatos/Main.java
text:
```scala
import java.util.@@Scanner;
public class Main {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int opcion=0;

        while (opcion !=3){
            System.out.println("Simulador de Script de Bitcoin");
            System.out.println("1. Ejecutar Script de forma Manual");
            System.out.println("2. Ejemplo de P2PKH");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch(opcion){
                case 1:
                    InterpreteBitCoin interprete1 = new InterpreteBitCoin();
                    System.out.print("Ingrese el Script a ejecutar (separe con espacios): ");
                    String script = scanner.nextLine();

                    try{
                        boolean resultado = interprete1.execute(script);
                        System.out.println("Resultado de la ejecución: " + resultado);
                    }catch(Exception e){
                        System.out.println("Error al ejecutar el script: " + e.getMessage());
                    }
                    break;
                case 2:
                    InterpreteBitCoin interpreter2 = new InterpreteBitCoin();

                    String p2pkh = "firma123 pubKey123 OP_DUP OP_HASH160 HASH_pubKey123 OP_EQUALVERIFY OP_CHECKSIG";

                    System.out.println("\nEjecutando P2PKH...");
                    try {
                        boolean result = interpreter2.execute(p2pkh);
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
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/Scanner#