import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {

    DataFrame sampleDataFrame;
    @BeforeEach
    void setup(){
        sampleDataFrame = new DataFrame();
        sampleDataFrame.setColumnNames("ID,STATUS");
        sampleDataFrame.addColumn("Eric Ma", "S89191,health fine");
        sampleDataFrame.addColumn("David Low", "S123123,health bad");
    }

    @Test
    void addColumn() {
        sampleDataFrame.addColumn("Test User", "S99919991,health ok");
    }

    @Test
    void getColumnNames() {
        String[] columns = this.sampleDataFrame.getColumnNames();
        assertEquals("ID", columns[0]);
        assertEquals("STATUS", columns[1]);
    }

    @Test
    void getRowCount() {
        assertEquals(2, this.sampleDataFrame.getRowCount());
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
        assertEquals("Test hello",this.sampleDataFrame.getValue("David Low", 2));
    }
}