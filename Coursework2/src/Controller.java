import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    Model model;

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

    public ArrayList<ArrayList<String>> getAllData(){
        if (this.model == null){
            return null;
        }
        return model.getAllData();
    }

    public String[] getColumnNames(){
        return model.getColumnNames();
    }

    public ArrayList<ArrayList<String>> getDataWithFilters(HashMap<String, String> filters){
        return model.getDataWithFilters(filters);
    }

    public ArrayList<ArrayList<String>> findOldest(){
        return model.findOldest();
    }

    public void writeToJSON(String pathName){
        model.writeToJSON(pathName);
    }
}
