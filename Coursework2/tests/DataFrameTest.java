import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {

    DataFrame sampleDataFrame;
    @BeforeEach
    void setup(){
        sampleDataFrame = new DataFrame();
        ArrayList<String> testColumns = new ArrayList<>();
        testColumns.add("ID");
        testColumns.add("STATUS");
        testColumns.add("FIRST");
        testColumns.add("LAST");
        sampleDataFrame.setColumnNames(testColumns);


        ArrayList<String> columnContent = new ArrayList<String>();
        columnContent.add("S89191");
        columnContent.add("health fine");
        columnContent.add("Eric");
        columnContent.add("Ma");

        sampleDataFrame.addColumn(columnContent);
        columnContent = new ArrayList<>();

        columnContent.add("S123123");
        columnContent.add("health bad");
        columnContent.add("David");
        columnContent.add("Low");


        sampleDataFrame.addColumn(columnContent);
    }

    @Test
    void testNameSwapped(){
        DataFrame testDataFrame = new DataFrame();
        ArrayList<String> testColumns = new ArrayList<>();

        testColumns.add("FIRST");
        testColumns.add("ID");
        testColumns.add("STATUS");
        testColumns.add("LAST");
        testDataFrame.setColumnNames(testColumns);



        ArrayList<String> columnContent = new ArrayList<String>();
        columnContent.add("Eric");
        columnContent.add("S89191");
        columnContent.add("health fine");
        columnContent.add("Ma");
        testDataFrame.addColumn(columnContent);


        columnContent = new ArrayList<>();
        columnContent.add("David");
        columnContent.add("S123123");
        columnContent.add("health bad");
        columnContent.add("Low");


        testDataFrame.addColumn(columnContent);

        assertEquals("S89191", testDataFrame.getValue("Eric Ma", 1));
        assertEquals("health bad", testDataFrame.getValue("David Low", 2));
    }

    @Test
    void addColumn() {
        ArrayList<String> columnContent = new ArrayList<String>();
        columnContent.add("S99919991");
        columnContent.add("health ok");
        columnContent.add("Test");
        columnContent.add("User");
        sampleDataFrame.addColumn(columnContent);
    }

    @Test
    void getColumnNames() {
        String[] columns = this.sampleDataFrame.getColumnNames();
        System.out.println("this is a test");
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