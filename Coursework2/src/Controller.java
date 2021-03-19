import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    public List<List<String>> getAllData(){
        if (this.model == null){
            return null;
        }
        return model.getAllData();
    }

    public String[] getColumnNames(){
        return model.getColumnNames();
    }

    public List<List<String>> getDataWithFilters(HashMap<String, String> filters){
        return model.getDataWithFilters(filters);
    }

    public List<List<String>> findOldest(){
        return model.findOldest();
    }

    public void writeToJSON(String pathName){
        model.writeToJSON(pathName);
    }
}
