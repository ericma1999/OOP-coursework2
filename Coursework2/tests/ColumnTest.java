import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    Column test;
    @BeforeEach
    void setup(){
        ArrayList<String> rowContent = new ArrayList<>();
        rowContent.add("S89191");
        rowContent.add("health fine");
        test = new Column("Eric Ma", rowContent);
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