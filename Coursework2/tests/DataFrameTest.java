import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {

    DataFrame sampleDataFrame;
    @BeforeEach
    void setup(){
        sampleDataFrame = new DataFrame();
        sampleDataFrame.setColumnNames("ID,STATUS,FIRST,LAST");
        sampleDataFrame.addColumn( "S89191,health fine,Eric,Ma");
        sampleDataFrame.addColumn("S123123,health bad,David,Low");
    }

    @Test
    void testNameSwapped(){
        DataFrame testDataFrame = new DataFrame();
        testDataFrame.setColumnNames("FIRST,ID,STATUS,LAST");
        testDataFrame.addColumn( "Eric,S89191,health fine,Ma");
        testDataFrame.addColumn("David,S123123,health bad,Low");

        assertEquals("S89191", testDataFrame.getValue("Eric Ma", 1));
        assertEquals("health bad", testDataFrame.getValue("David Low", 2));
    }

    @Test
    void addColumn() {
        sampleDataFrame.addColumn("S99919991,health ok,Test,User");
    }

    @Test
    void getColumnNames() {
        String[] columns = this.sampleDataFrame.getColumnNames();
        assertEquals("ID", columns[0]);
        assertEquals("STATUS", columns[1]);
    }

    @Test
    void getRowCount() {
        assertEquals(4, this.sampleDataFrame.getRowCount());
    }

    @Test
    void getValue() {
        assertEquals("S89191", this.sampleDataFrame.getValue("Eric Ma", 0));
    }

    @Test
    void putValue() {
        assertTrue(this.sampleDataFrame.putValue("Eric Ma",0, "S555555"));
        assertEquals("S555555", this.sampleDataFrame.getValue("Eric Ma", 0));
    }

    @Test
    void addValue() {
        assertTrue(this.sampleDataFrame.addValue("David Low", "Test hello"));
        assertEquals("Test hello",this.sampleDataFrame.getValue("David Low", 4));
    }
}