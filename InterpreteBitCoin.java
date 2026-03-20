import java.security.PublicKey;

public class InterpreteBitCoin {
     
    private Stack stack;
     
    public InterpreteBitCoin(){
        stack = new Stack();
    }

    public boolean execute(String script){ //Recibe un String en este caso "Script" e indica que se va a ejecutar
        
        String[] tokens = script.split(" ");// Divide el script en partes usando espacios como separadores y 
        // las guarda en un arreglo de Strings llamado "tokens"

        for(String token : tokens){ // Itera sobre cada token en el arreglo "tokens" utilizando un bucle for-each. 
        // En cada iteración, el token actual se asigna a la variable "token".
        //Manejo dinámico de OP_0 a OP_16
            if(token.startsWith("OP_")){
                try {
                    int value = Integer.parseInt(token.substring(3));
                    if(value >= 0 && value <= 16){
                        stack.push(String.valueOf(value));
                    continue; // saltar el switch
                    }
                    } catch(Exception e){
                    // no es número → sigue al switch normal
                    }
            }
            System.out.println("\n>> Ejecutando: " + token);
            
            switch (token){ 
                case "OP_DUP":
                    if(stack.isEmpty()){
                        return false;
                    }
                    String top = stack.peek();
                    stack.push(top); //Se obtiene el valor en la parte superior de la pila utilizando el método peek 
                    // y luego se empuja ese mismo valor a la pila utilizando el método push.
                    break;
                case "OP_DROP":
                    if(stack.isEmpty()){
                        return false;
                    }
                    stack.pop(); //Elimina el valor en la parte superior de la pila 
                    break;
                case "OP_EQUAL":
                    if(stack.isEmpty()){
                        return false;
                    }
                    String v1 = stack.pop(); //elimina 1 valor del tope de pila para comparar
                    String v2 = stack.pop();//elimina otro valor del tope de pila para comparar
                    if(v1.equals (v2)){
                        stack.push("1"); // Si los valores son iguales, se empuja "1" a la pila
                    } else {
                        stack.push("0"); // Si no son iguales, se empuja "0" a la pila
                    }
                    break;
                case "OP_EQUALVERIFY":
                    if(stack.isEmpty()){
                        return false;
                    }
                    String ev1 = stack.pop();
                    String ev2 = stack.pop();
                    if(!ev1.equals(ev2)){
                        throw new RuntimeException("OP_EQUALVERIFY falló: los valores no son iguales"); 
                        //lo mismo que Equal pero en vez de 0 da error si no son iguales
                    }
                    break;
                case "OP_HASH160":
                    if(stack.isEmpty()){
                        return false;
                    }
                    String value = stack.pop();
                    try{
                        String hash = HashUtil.sha256(value); 
                        stack.push(hash); //Se calcula el hash del valor utilizando la función sha256 y se empuja el resultado a la pila.
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    break;
                case "OP_CHECKSIG":
                    if(stack.size() < 2){ // Verifica si hay al menos dos elementos en la pila antes de intentar realizar la operación OP_CHECKSIG.
                        throw new RuntimeException("OP_CHECKSIG requiere firma y clave pública");
                    }
                    try {
                        String pubKeyStr = stack.pop();
                        String signatureStr = stack.pop();

                        byte[] signature = HashUtil.hexToBytes(signatureStr);
                        PublicKey pubKey = SignatureUtil.stringToPublicKey(pubKeyStr);

                        String mensaje = "tx-demo";

                        System.out.println("\n--- OP_CHECKSIG ---");
                        System.out.println("Firma: " + signatureStr);
                        System.out.println("PubKey: " + pubKeyStr);
                        System.out.println("Mensaje: " + mensaje);

                        boolean valid = SignatureUtil.verify(mensaje, signature, pubKey);

                        System.out.println("Resultado verificación: " + valid);
                        System.out.println("-------------------");

                        stack.push(valid ? "1" : "0");

                    } catch (Exception e) {
                        throw new RuntimeException("Error en OP_CHECKSIG: " + e.getMessage());
                    }
                    break;
                    default:
                        stack.push(token);
                        break;
            }
            System.out.println("Stack: " + stack);
            System.out.println("---------------------------------"); //Imprime el contenido de la pila después de cada operación para mostrar el estado actual de la pila.
        }
        if(stack.isEmpty()){
            return false; 
        }
        return stack.pop().equals("1"); //Al final del script, se verifica si el valor en la parte superior de la pila es "1".
    }
}
