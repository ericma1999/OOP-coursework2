import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


        ArrayList<String> rowContent = new ArrayList<String>();
        rowContent.add("S89191");
        rowContent.add("health fine");
        rowContent.add("Eric");
        rowContent.add("Ma");

        sampleDataFrame.addRow(rowContent);
        rowContent = new ArrayList<>();

        rowContent.add("S123123");
        rowContent.add("health bad");
        rowContent.add("David");
        rowContent.add("Low");


        sampleDataFrame.addRow(rowContent);
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



        ArrayList<String> rowContent = new ArrayList<String>();
        rowContent.add("Eric");
        rowContent.add("S89191");
        rowContent.add("health fine");
        rowContent.add("Ma");
        testDataFrame.addRow(rowContent);


        rowContent = new ArrayList<>();
        rowContent.add("David");
        rowContent.add("S123123");
        rowContent.add("health bad");
        rowContent.add("Low");


        testDataFrame.addRow(rowContent);

        assertEquals("David", testDataFrame.getValue("FIRST", 1));
        assertEquals("health bad", testDataFrame.getValue("STATUS", 1));
    }

    @Test
    void addRow() {
        ArrayList<String> columnContent = new ArrayList<String>();
        columnContent.add("S99919991");
        columnContent.add("health ok");
        columnContent.add("Test");
        columnContent.add("User");
        sampleDataFrame.addRow(columnContent);
        assertEquals("S99919991", this.sampleDataFrame.getColumn(0).getRowValue(2));
    }
    @Test
    void getRow(){
        List<String> rowData = sampleDataFrame.getRow(1);

        assertEquals("S123123", rowData.get(0));
        assertEquals("Low", rowData.get(3));


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
        assertEquals(2, this.sampleDataFrame.getRowCount());
    }

    @Test
    void getValue() {
        assertEquals("S89191", this.sampleDataFrame.getValue("ID", 0));
    }

    @Test
    void putValue() {
        assertTrue(this.sampleDataFrame.putValue("LAST",0, "new last name"));
        assertEquals("new last name", this.sampleDataFrame.getValue("LAST", 0));
    }

    @Test
    void addValue() {
        assertTrue(this.sampleDataFrame.addValue("ID", "S081123"));
        assertEquals("S081123",this.sampleDataFrame.getValue("ID", 2));
    }
}