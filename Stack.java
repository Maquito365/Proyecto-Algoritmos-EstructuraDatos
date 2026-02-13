import java.util.ArrayDeque;
import java.util.Deque;


public class Stack {

    private Deque<String> stack;

    public Stack(){
        stack = new ArrayDeque<>();
    }

    public void push(String value){
        stack.push(value); //Empuja el valor a la pila
    }

    public String pop(){
        if(stack.isEmpty()){
            throw new RuntimeException("Pila Vacia");
        }
        return stack.pop(); //Elimina y devuelve el valor en la parte superior de la pila
    }

    public String peek(){
        if(stack.isEmpty()){
            throw new RuntimeException("Pila Vacia");
        }
        return stack.peek(); //Devuelve el valor en la parte superior de la pila sin eliminarlo
    }

    public boolean isEmpty(){
        return stack.isEmpty(); //Verifica si la pila está vacía
    }

    public void printStack(){
        System.out.println(stack); //Imprime el contenido de la pila
    }

}
