import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    Column test;
    @BeforeEach
    void setup(){
        test = new Column("Eric Ma", "S89191,health fine");
    }

    @Test
    void getName() {
        assertEquals("Eric Ma", test.getName());
    }

    @Test
    void getSize() {
        assertEquals(2, test.getSize());
    }

    @Test
    void getRowValue() {
        assertEquals("S89191", test.getRowValue(0));
    }

    @Test
    void setRowValue() {
        test.setRowValue(0, "S123123");
        assertEquals("S123123", test.getRowValue(0));
    }

    @Test
    void addRowValue() {
        test.addRowValue("insurance covered");
        assertEquals("insurance covered", test.getRowValue(2));
        assertEquals(3, test.getSize());
    }
}