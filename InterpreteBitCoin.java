import java.security.PublicKey;

public class InterpreteBitCoin {
     
    private Stack stack;
     
    public InterpreteBitCoin(){
        stack = new Stack();
    }

    public boolean execute(String script){ //Recibe un String en este caso "Script" e indica que se va a ejecutar
        if(script == null || script.trim().isEmpty()){
            throw new RuntimeException("El script está vacío");
        }
        
        boolean enCondicional = false;
        boolean ejecutar = true;
        String[] tokens = script.trim().split("\\s+");// Divide el script en partes usando espacios como separadores y 
        // las guarda en un arreglo de Strings llamado "tokens"

        for(String token : tokens){ // Itera sobre cada token en el arreglo "tokens" utilizando un bucle for-each. 
        // En cada iteración, el token actual se asigna a la variable "token".
        String tokenUpper = token.toUpperCase();
        if(!ejecutar && !tokenUpper.equals("OP_ELSE") && !tokenUpper.equals("OP_ENDIF")){
            continue;
        }
        //Manejo dinámico de OP_0 a OP_16
            if(tokenUpper.startsWith("OP_")){
                try {
                    int value = Integer.parseInt(tokenUpper.substring(3));
                    if(value >= 0 && value <= 16){
                        System.out.println("\n>> Ejecutando: " + tokenUpper);

                        stack.push(String.valueOf(value));
                        System.out.println("Stack: "+stack);
                        System.out.println("---------------------------------");
                    continue; // saltar el switch

                    }
                    } catch(Exception e){
                    // no es número → sigue al switch normal
                    }
            }
            System.out.println("\n>> Ejecutando: " + tokenUpper);
            switch (tokenUpper){ 
                case "OP_DUP":
                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_DUP requiere al menos 1 elemento");
                    }
                    String x = stack.pop();
                    stack.push(x);
                    stack.push(x); //Se obtiene el valor en la parte superior de la pila
                    // y luego se empuja ese mismo valor a la pila utilizando el método push.
                    break;
                case "OP_DROP":
                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_DROP requiere al menos 1 elemento");
                    }
                    stack.pop(); //Elimina el valor en la parte superior de la pila 
                    break;
                case "OP_EQUAL":
                    if(stack.size()<2){
                        throw new RuntimeException("OP_EQUAL requiere 2 elementos");
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
                    if(stack.size()<2){
                        throw new RuntimeException("OP_EQUALVERIFY requiere 2 elementos");
                    }
                    String ev1 = stack.pop();
                    String ev2 = stack.pop();
                    if(!ev1.equals(ev2)){
                        throw new RuntimeException("OP_EQUALVERIFY falló: los valores no son iguales"); 
                        //lo mismo que Equal pero en vez de 0 da error si no son iguales
                    }
                    break;
                case "OP_NUMEQUALVERIFY":
                    if(stack.size() < 2){
                        throw new RuntimeException("OP_NUMEQUALVERIFY requiere 2 elementos");
                    }
                    try {
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        if(a != b){
                            throw new RuntimeException("OP_NUMEQUALVERIFY falló: números diferentes");
                        }
                    } catch(NumberFormatException e){
                        throw new RuntimeException("OP_NUMEQUALVERIFY requiere números");
                    }
                    break;
                case "OP_LESSTHAN":
                    if(stack.size() < 2){
                        throw new RuntimeException("OP_LESSTHAN requiere 2 elementos");
                    }
                    try {
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        stack.push(b < a ? "1" : "0");

                    } catch(NumberFormatException e){
                        throw new RuntimeException("OP_LESSTHAN requiere números");
                    }
                    break;
                case "OP_GREATERTHAN":
                    if(stack.size() < 2){
                        throw new RuntimeException("OP_GREATERTHAN requiere 2 elementos");
                    }
                    try {
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        stack.push(b > a ? "1" : "0");

                    } catch(NumberFormatException e){
                        throw new RuntimeException("OP_GREATERTHAN requiere números");
                    }
                    break;
                case "OP_LESSTHANOREQUAL":
                    if(stack.size() < 2){
                        throw new RuntimeException("OP_LESSTHANOREQUAL requiere 2 elementos");
                    }
                    try {
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        stack.push(b <= a ? "1" : "0");

                    } catch(NumberFormatException e){
                        throw new RuntimeException("OP_LESSTHANOREQUAL requiere números");
                    }
                    break;
                case "OP_GREATERTHANOREQUAL":
                    if(stack.size() < 2){
                        throw new RuntimeException("OP_GREATERTHANOREQUAL requiere 2 elementos");
                    }
                    try {
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        stack.push(b >= a ? "1" : "0");

                    } catch(NumberFormatException e){
                        throw new RuntimeException("OP_GREATERTHANOREQUAL requiere números");
                    }
                    break;
                case "OP_HASH160":
                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_HASH160 requiere 1 elemento");
                    }
                    String value = stack.pop();
                    try{
                        String hash = HashUtil.sha256(value); 
                        stack.push(hash); //Se calcula el hash del valor utilizando la función sha256 y se empuja el resultado a la pila.
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    break;
                case "OP_SHA256":
                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_SHA256 requiere 1 elemento");
                    }
                    try {
                        String data = stack.pop();
                        stack.push(HashUtil.sha256(data));
                    } catch(Exception e){
                        throw new RuntimeException("Error en OP_SHA256");
                    }
                    break;
                case "OP_HASH256":
                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_HASH256 requiere 1 elemento");
                    }
                    try {
                        String data = stack.pop();
                        String hash1 = HashUtil.sha256(data);
                        String hash2 = HashUtil.sha256(hash1);

                        stack.push(hash2);
                    } catch(Exception e){
                        throw new RuntimeException("Error en OP_HASH256");
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
                case "OP_CHECKSIGVERIFY":
                    if(stack.size() < 2){
                        throw new RuntimeException("OP_CHECKSIGVERIFY requiere firma y clave pública");
                    }
                    try {
                        String pubKeyStr = stack.pop();
                        String signatureStr = stack.pop();

                        byte[] signature = HashUtil.hexToBytes(signatureStr);
                        PublicKey pubKey = SignatureUtil.stringToPublicKey(pubKeyStr);

                        String mensaje = "tx-demo";

                        boolean valid = SignatureUtil.verify(mensaje, signature, pubKey);

                        if(!valid){
                            throw new RuntimeException("OP_CHECKSIGVERIFY falló");
                        }

                    } catch (Exception e){
                        throw new RuntimeException("Error en OP_CHECKSIGVERIFY: " + e.getMessage());
                    }
                    break;
                case "OP_IF":
                    enCondicional = true;
                    if (stack.isEmpty()){
                        throw new RuntimeException("OP_IF requiere un valor");
                    }
                    String condicion = stack.pop();

                    ejecutar = !condicion.equals ("0");
                    break;
                case "OP_ELSE":
                    if(!enCondicional){
                        throw new RuntimeException("OP_ELSE no puede ejecutarse sin OP_IF");
                    }
                    ejecutar = !ejecutar;
                    break;
                case "OP_ENDIF":
                    if(!enCondicional){
                        throw new RuntimeException("OP_ENDIF sin OP_IF");
                    }
                    ejecutar = true;
                    enCondicional = false;
                    break;
                case "OP_NOTIF":
                    enCondicional = true;

                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_NOTIF requiere un valor");
                    }

                    String cond = stack.pop();

                    ejecutar = cond.equals("0"); // invertido
                    break;
                case "OP_ADD":
                    if(stack.size() < 2){
                        throw new RuntimeException("se necesitan al menos 2 elementos en el stack");
                    }

                    try{
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        int suma = a+b;

                        stack.push(String.valueOf(suma));
                    }catch(NumberFormatException e){
                        throw new RuntimeException("OP_ADD deben de ser números no mensajes");
                    }
                    break;
                case "OP_SUB":
                    if(stack.size() <2){
                        throw new RuntimeException("se necesitan al menos 2 elementos en el stack");
                    }
                    try{
                        int a = Integer.parseInt(stack.pop());
                        int b = Integer.parseInt(stack.pop());

                        int resta = b - a;

                        stack.push(String.valueOf(resta));

                    }catch(NumberFormatException e){
                        throw new RuntimeException("OP_SUB solo funciona con números");
                    }
                    break;
                case "OP_NOT":
                    if(stack.isEmpty()){
                        throw new RuntimeException("El stack esta vacio, añade elementos");
                    }
                    try{
                        
                        int val = Integer.parseInt(stack.pop());

                        if(val == 0){
                            val = 1;
                        }
                        else{
                            val = 0;
                        }
                        stack.push(String.valueOf(val));
                    }catch(NumberFormatException e){
                        throw new RuntimeException("deben de ser números");
                    }
                    break;
                case "OP_BOOLAND":
                    if(stack.size() < 2){
                        throw new RuntimeException("Se necesitan al menos 2 elementos en el stack");
                    }
                    try{
                        int last1 = Integer.parseInt(stack.pop());
                        int last2= Integer.parseInt(stack.pop());

                        int resultado;

                        if(last1==0 || last2==0 ){
                            resultado = 0;
                        }else{
                            resultado = 1;
                        }
                        stack.push(String.valueOf(resultado));
                    }catch(NumberFormatException e){
                        throw new RuntimeException("Deben ser números");
                    }
                    break;
                case "OP_BOOLOR":
                    if(stack.size() < 2){
                        throw new RuntimeException("Se necesitan al menos 2 elementos en el stack");
                    }
                    try{
                        int last1 = Integer.parseInt(stack.pop());
                        int last2 = Integer.parseInt(stack.pop());

                        int resultado;

                        if(last1==0 && last2==0 ){
                            resultado = 0;
                        }else{
                            resultado = 1;
                        }
                        stack.push(String.valueOf(resultado));
                    }catch(NumberFormatException e){
                        throw new RuntimeException("Deben ser números");
                    }
                    break;
                case "OP_SWAP":{
                    if(stack.size()<2){
                        throw new RuntimeException("Deben haber 2 elementos minimo");
                    }
                    
                    String e1 = stack.pop();
                    String e2 = stack.pop();

                    stack.push(e1);
                    stack.push(e2);
                    break;
                }
                case "OP_OVER":{
                    if(stack.size()<2){
                        throw new RuntimeException("Deben haber 2 elementos minimo");
                    }
                    String e1 = stack.pop();
                    String e2 = stack.pop();

                    stack.push(e2);
                    stack.push(e1);
                    stack.push(e2);
                    break;
                }
                case "OP_VERIFY":
                    if(stack.isEmpty()){
                        throw new RuntimeException("OP_VERIFY requiere 1 elemento");
                    }
                    String val = stack.pop();
                    if(val.equals("0")){
                        throw new RuntimeException("OP_VERIFY falló: valor es 0");
                    }
                    // si no es 0, continúa
                    break;
                case "OP_RETURN":
                    throw new RuntimeException("OP_RETURN ejecutado: script inválido");
                        default:
                        try {
                            Integer.parseInt(token);
                            stack.push(token);
                        } catch(Exception e){
                            stack.push(token);
                        }
                        break;
            }
            System.out.println("Stack: " + stack);
            System.out.println("---------------------------------"); //Imprime el contenido de la pila después de cada operación para mostrar el estado actual de la pila.
        }
        if(stack.isEmpty()){
            return true; 
        }
        return !stack.pop().equals("0"); 
    }
}

