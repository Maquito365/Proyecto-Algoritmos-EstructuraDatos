package Test;
import org.junit.jupiter.api.Test;
import src.InterpreteBitCoin;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class InterpreteTest{

    @Test
    public void testEqualTrue(){
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertTrue(i.execute("OP_1 OP_1 OP_EQUAL"));
    }

    @Test
    public void testEqualFalse(){
       InterpreteBitCoin i = new InterpreteBitCoin();
       assertFalse(i.execute("OP_1 OP_2 OP_EQUAL"));  
    }

    @Test
    public void testStackEmpty() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertThrows(RuntimeException.class, () -> i.execute("OP_DUP"));
    }

}