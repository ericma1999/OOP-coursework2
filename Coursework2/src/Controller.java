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

    public List<List<String>> findOldest(){
        return model.findOldest();
    }

    public void writeToJSON(String pathName){
        model.writeToJSON(pathName);
    }

    public Map<String, Double> getOccurences(){
        HashMap<String, Double> tempValues = new HashMap<>();

        for (int i = 0; i < model.getTotalRows(); i++) {
            String currentValue = model.getValueAt("ETHNICITY", i);

            if (tempValues.get(currentValue) == null){
                tempValues.put(currentValue, 1.0);
            }else {
                tempValues.put(currentValue, tempValues.get(currentValue) + 1.0);
            }
        }
        return tempValues;
    }





//    public void generateAgeGroup(){
//        DateFormat dateFormat = new SimpleDateFormat(this.dateFormat);
//        Date currentDate =  new Date();
//
//        LinkedHashMap<Integer, Integer> tempTree = new LinkedHashMap<>();
//
//        for (int i = 0; i < model.getTotalRows(); i++) {
//            String birthDate = model.getValueAt("BIRTHDATE", i);
//            String deathDate = model.getValueAt("DEATHDATE", i);
//
//            int minValue = -1;
//            int maxValue = -1;
//
//            if (birthDate != null && deathDate == ""){
//                int year = Math.round(differenceDate(birthDate, dateFormat.format(currentDate)) / 365) * -1;
//
//                int key = ((year / 10 ) * 10) + 1;
//
//
//                if (minValue == -1 || key < minValue){
//                    minValue = key;
//                }
//
//                if (maxValue == -1 || key < maxValue){
//                    maxValue = key;
//                }
//
//
//                if (tempTree.get(key) == null){
//                    tempTree.put(key,1);
//                }else {
//                    tempTree.put(key, tempTree.get(key) + 1);
//                }
//            }
//        }
//
//    }
//
//    private long differenceDate(String firstDate, String lastDate){
//        long diff = -1;
//        try{
//            Date laterDate = new SimpleDateFormat(dateFormat).parse(firstDate);
//            Date earlierDate = new SimpleDateFormat(dateFormat).parse(lastDate);
//            long milliSecondsAlive = laterDate.getTime() - earlierDate.getTime();
//            diff = TimeUnit.DAYS.convert(milliSecondsAlive, TimeUnit.MILLISECONDS);
//
//        }catch (ParseException e){
//            System.out.println(e.getMessage());
//        }
//        return diff;
//    }
}
