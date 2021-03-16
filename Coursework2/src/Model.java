import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Model {

    private DataFrame dataFrame;

    public Model (String fileName){
        try{
            this.dataFrame =  new DataLoader(fileName).getDataFrame();
        }catch(Exception e){
            System.out.println("error");
        }
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

    public ArrayList<ArrayList<String>> getAllData(){
        ArrayList<ArrayList<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            output.add(this.dataFrame.getRow(i));
        }
        return output;

    }
    public ArrayList<ArrayList<String>> getDataWithFilters(Map<String, String> filters){
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            boolean shouldAdd = true;
            for (String columnName: filters.keySet()) {
                this.dataFrame.getColumn(columnName).getRowValue(i);

                String currentColumnRowValue = this.dataFrame.getColumn(columnName).getRowValue(i).toLowerCase();

                if (currentColumnRowValue.contains(filters.get(columnName).toLowerCase())) {
                    shouldAdd = shouldAdd && true;
                }else{
                    shouldAdd = false;
                }
            }
            if (shouldAdd){
                output.add(this.dataFrame.getRow(i));
            }
        }
        return output;
    }

    public ArrayList<ArrayList<String>> findOldest(){
        Column birthColumn = this.dataFrame.getColumn("BIRTHDATE");
        Column deathColumn = this.dataFrame.getColumn("DEATHDATE");
        ArrayList<ArrayList<String>> oldestPersons = new ArrayList<>();
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
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return differenceDate(today, date);

    }

    private long differenceDate(String firstDate, String lastDate){
        long diff = -1;
        try{
            Date laterDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
            Date earlierDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastDate);

            long milliSecondsAlive = laterDate.getTime() - earlierDate.getTime();
            diff = TimeUnit.MINUTES.convert(milliSecondsAlive, TimeUnit.MILLISECONDS);

        }catch (Exception e){
            System.out.println("something went wrong");
        }
        return diff;
    }

//    public void peopleSamePlace(){
//        HashMap<String, Integer> tallyMap = new HashMap<>();
//
//
//        for (String state: this.dataFrame.getColumn("CITY").getRowValues()) {
//            Integer currentValue = tallyMap.get(state);
//            if (currentValue == null){
//                tallyMap.put(state, 1);
//            }else{
//                tallyMap.put(state, currentValue + 1);
//            }
//
//        }
//    }

}
