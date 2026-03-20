package Test;
import org.junit.jupiter.api.Test;
import src.InterpreteBitCoin;
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

        assertThrows(RuntimeException.class, () -> {
            i.execute("OP_DUP");
        });
    }

    @Test
    public void testAdd() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertTrue(i.execute("OP_2 OP_3 OP_ADD OP_5 OP_EQUAL"));
    }

    @Test
    public void testIfElse() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertTrue(i.execute("OP_1 OP_IF OP_2 OP_ELSE OP_3 OP_ENDIF OP_2 OP_EQUAL"));
    }

    @Test
    public void testVerifyFail() {
        InterpreteBitCoin i = new InterpreteBitCoin();

        assertThrows(RuntimeException.class, () -> {
            i.execute("OP_0 OP_VERIFY");
        });
    }

    @Test
    public void testHash() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertTrue(i.execute("hola OP_SHA256"));
    }

    @Test
    public void testBoolAnd() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertTrue(i.execute("OP_1 OP_1 OP_BOOLAND"));
    }

    @Test
    public void testSwap() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertFalse(i.execute("OP_5 OP_3 OP_SWAP OP_3 OP_EQUAL"));
    }

    @Test
    public void testOver() {
        InterpreteBitCoin i = new InterpreteBitCoin();
        assertTrue(i.execute("OP_5 OP_3 OP_OVER OP_5 OP_EQUAL"));
    }


}