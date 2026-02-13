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
            
            switch (token){ 
               
                case "OP_0":
                    stack.push("0"); // Si el token es "OP_n", se empuja el valor "n" a la pila utilizando el método push de la clase Stack.
                    break;
                case "OP_1":
                    stack.push("1"); 
                    break;
                case "OP_2":
                    stack.push("2"); 
                    break;
                case "OP_3":
                    stack.push("3"); 
                    break;
                case "OP_4":
                    stack.push("4"); 
                    break;
                case "OP_5":
                    stack.push("5"); 
                    break;
                case "OP_6":
                    stack.push("6"); 
                    break;
                case "OP_7":
                    stack.push("7"); 
                    break;
                case "OP_8":
                    stack.push("8"); 
                    break;
                case "OP_9":
                    stack.push("9"); 
                    break;
                case "OP_10":
                    stack.push("10"); 
                    break;
                case "OP_11":
                    stack.push("11"); 
                    break;
                case "OP_12":
                    stack.push("12"); 
                    break;
                case "OP_13":
                    stack.push("13"); 
                    break;
                case "OP_14":
                    stack.push("14"); 
                    break;
                case "OP_15":
                    stack.push("15"); 
                    break;
                case "OP_16":
                    stack.push("16"); 
                    break;
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
                    String Hash = stack.pop();
                    stack.push("HASH_" + Hash); //Se simula el hash, se jala el valor del tope y se le pone "hash" para simular
                    break;
                case "OP_CHECKSIG":
                    stack.pop(); //Se eliminan dos valores del tope de la pila, que representan la firma 
                    stack.pop();//y la clave pública.
                    stack.push("1"); //Se simula la verificación de la firma, se asume que siempre es válida
                    break;
                default:
                    stack.push(token); //Si no es OP osea token se asume que es un dato y se empuja a la pila
                    break;
            }
            stack.printStack(); //Imprime el contenido de la pila después de cada operación para mostrar el estado actual de la pila.
        }
        if(stack.isEmpty()){
            return false; 
        }
        return stack.pop().equals("1"); //Al final del script, se verifica si el valor en la parte superior de la pila es "1".
    }
}
