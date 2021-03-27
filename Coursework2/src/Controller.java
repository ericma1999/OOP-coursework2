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

    public boolean writeToJSON(String pathName){
        try{
            model.writeToJSON(pathName);
        }catch (IOException e){
            return false;
        }
        return true;
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
        DateFormat dateFormat = new SimpleDateFormat(Controller.dateFormat);
        Date currentDate =  new Date();

        ArrayList<List<String>> results = new ArrayList<>();

        for (int i = 0; i < model.getTotalRows(); i++) {
            String birthDate = model.getValueAt("BIRTHDATE",i);
            String deathDate = model.getValueAt("DEATHDATE",i);

            if (birthDate != null && deathDate.equals("")){
                Long difference = differenceDate(dateFormat.format(currentDate), birthDate);

                if (difference == null){
                    return null;
                }

                if (difference > max){
                    max = difference;
                    results = new ArrayList<>();
                    results.add(model.getRow(i));
                } else if (difference == max){
                    results.add(model.getRow(i));
                }
            }
        }
        return results;
    }

    private Long differenceDate(String firstDate, String lastDate){
        long diff = -1;
        try{
            Date laterDate = new SimpleDateFormat(dateFormat).parse(firstDate);
            Date earlierDate = new SimpleDateFormat(dateFormat).parse(lastDate);
            long milliSecondsAlive = laterDate.getTime() - earlierDate.getTime();
            diff = TimeUnit.DAYS.convert(milliSecondsAlive, TimeUnit.MILLISECONDS);

        }catch (ParseException e){
            return null;
        }
        return diff;
    }
}
