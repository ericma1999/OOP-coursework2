import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller {
    Model model;
    private static final String dateFormat = "yyyy-MM-dd";

    public Controller(){

    }

    public boolean loadFile(String filePath) {
        try{
            this.model = new Model(filePath);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    public List<List<String>> getAllData(){
        return model.getAllData();
    }

    public List<String> getColumnNames(){
        return model.getColumnNames();
    }

    public List<List<String>> getDataWithFilters(Map<String, String> filters){
        return model.getDataWithFilters(filters);
    }

    public void writeToJSON(String pathName){
        model.writeToJSON(pathName);
    }

    public Map<String, Double> getOccurrences(String columnName){
        HashMap<String, Double> tempValues = new HashMap<>();

        for (int i = 0; i < model.getTotalRows(); i++) {
            String currentValue = model.getValueAt(columnName, i);

            tempValues.merge(currentValue, 1.0, Double::sum);
        }
        return tempValues;
    }

    public List<List<String>> findOldest(){

        double max = -1;
        DateFormat dateFormat = new SimpleDateFormat(this.dateFormat);
        Date currentDate =  new Date();

        List<String> oldestPerson = null;

        for (int i = 0; i < model.getTotalRows(); i++) {
            String birthDate = model.getValueAt("BIRTHDATE",i);
            String deathDate = model.getValueAt("DEATHDATE",i);

            if (birthDate != null && deathDate.equals("")){
                double year = Math.round(differenceDate(birthDate, dateFormat.format(currentDate)) / 365.0) * -1;

                if (year > max){
                    max = year;
                    oldestPerson = model.getRow(i);
                }
            }
        }
        ArrayList<List<String>> result = new ArrayList<>();
        result.add(oldestPerson);
        return result;
    }

    private long differenceDate(String firstDate, String lastDate){
        long diff = -1;
        try{
            Date laterDate = new SimpleDateFormat(dateFormat).parse(firstDate);
            Date earlierDate = new SimpleDateFormat(dateFormat).parse(lastDate);
            long milliSecondsAlive = laterDate.getTime() - earlierDate.getTime();
            diff = TimeUnit.DAYS.convert(milliSecondsAlive, TimeUnit.MILLISECONDS);

        }catch (ParseException e){
            System.out.println(e.getMessage());
        }
        return diff;
    }
}
