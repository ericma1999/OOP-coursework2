import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Model {

    private final DataFrame dataFrame;
    private static final String dateFormat = "yyyy-MM-dd";

    public Model (String filePath) throws IOException {
        this.dataFrame =  new DataLoader(filePath).getDataFrame();
    }

    public int getTotalRows(){
        return this.dataFrame.getRowCount();
    }

    public String[] getColumnNames(){
        return this.dataFrame.getColumnNames();
    }

    public String getValueAt(int index, int row){
        return this.dataFrame.getColumn(index).getRowValue(row);
    }

    public List<List<String>> getAllData(){
        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            output.add(this.dataFrame.getRow(i));
        }
        return output;

    }
    public List<List<String>> getDataWithFilters(Map<String, String> filters){
        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            boolean shouldAdd = true;
//            loop through the hashmap keys and check the value, if the column matches all the hashmap's key value
//            add it to the output
            for (String columnName: filters.keySet()) {

                String currentColumnRowValue = this.dataFrame.getColumn(columnName).getRowValue(i).toLowerCase();

                if (!currentColumnRowValue.contains(filters.get(columnName).toLowerCase())) {
                    shouldAdd = false;
                }
            }
            if (shouldAdd){
                output.add(this.dataFrame.getRow(i));
            }
        }
        return output;
    }


    public void writeToJSON(String path){
        new JSONWriter(this.dataFrame, path);
    }

    public List<List<String>> findOldest(){
        Column birthColumn = this.dataFrame.getColumn("BIRTHDATE");
        Column deathColumn = this.dataFrame.getColumn("DEATHDATE");
        List<List<String>> oldestPersons = new ArrayList<>();
        long oldestAge = -1;
        for (int i = 0; i < dataFrame.getRowCount(); i++) {

            if (!deathColumn.getRowValue(i).equals("")){
                continue;
            }

            if (oldestAge == -1){
                oldestAge = differenceDate(birthColumn.getRowValue(i));
                oldestPersons.add(this.dataFrame.getRow(i));
                continue;
            }

            long difference = differenceDate(birthColumn.getRowValue(i));

            if (difference > oldestAge){
                oldestPersons = new ArrayList<>();
                oldestPersons.add(this.dataFrame.getRow(i));
                oldestAge = difference;
            } else if(difference == oldestAge){
                oldestPersons.add(this.dataFrame.getRow(i));
            }
        }

        return oldestPersons;

    }

    private long differenceDate(String date){
        String today = new SimpleDateFormat(dateFormat).format(new Date());

        return differenceDate(today, date);

    }

    private long differenceDate(String firstDate, String lastDate){
        long diff = -1;
        try{
            Date laterDate = new SimpleDateFormat(dateFormat).parse(firstDate);
            Date earlierDate = new SimpleDateFormat(dateFormat).parse(lastDate);

            long milliSecondsAlive = laterDate.getTime() - earlierDate.getTime();
            diff = TimeUnit.MINUTES.convert(milliSecondsAlive, TimeUnit.MILLISECONDS);

        }catch (ParseException e){}
        return diff;
    }
}
