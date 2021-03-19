import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class JSONReader {

    private final HashMap<String, String> tempValues = new HashMap<>();
    private final ArrayList<Character> stack = new ArrayList<>();
    private StringBuilder currentContent = new StringBuilder();
    private StringBuilder currentProperty = new StringBuilder();
    private static final Pattern pattern = Pattern.compile("[\\[\\]\", ]");


    public JSONReader(String path) throws IOException{
        FileReader file = new FileReader(path);

        try (BufferedReader contents = new BufferedReader(file)) {
            int charIntRepresentation;
            while ((charIntRepresentation = contents.read()) != -1){
                Character character = Character.toChars(charIntRepresentation)[0];

                if (pattern.matcher(character.toString()).find()){
                    continue;
                }

//                read the properties of each JSON block {}
                if (character.equals('{')){
                    stack.add('{');
                    this.readProperties(contents);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readProperties(BufferedReader contents) throws Exception {
        int charIntRepresentation;
        while ((charIntRepresentation = contents.read()) != -1){
            Character character = Character.toChars(charIntRepresentation)[0];


            if ((character.equals(' ') || character.equals('\n')) && stack.get(stack.size() - 1) != '"'){
                continue;
            }

            if (character.equals('"')){

                if (stack.get(stack.size() -1) != '"'){
                    stack.add('"');
                }else {
                    stack.remove(stack.size() - 1);
                    Character nextCharacter = readTillNoWhiteSpace(contents);
                    if(!handlePropertyEndCases(nextCharacter)){
                        break;
                    }
                }
                continue;
            }
            currentContent.append(character);

        }
    }

    private void handleColon(){
        this.tempValues.put(currentContent.toString(), "");
        currentProperty = new StringBuilder(currentContent);
        currentContent = new StringBuilder();
    }

    private boolean handleComma(){
        if (!this.tempValues.get(currentProperty.toString()).equals("")){
            return false;
        }else {
            this.tempValues.put(currentProperty.toString(), currentContent.toString());
            currentContent = new StringBuilder();
            currentProperty = new StringBuilder();
        }
        return true;
    }

    private boolean handleCloseBracket(){
        this.tempValues.put(currentProperty.toString(), currentContent.toString());
        stack.remove(stack.size() - 1);
        return !stack.isEmpty();
    }

    private boolean handlePropertyEndCases(Character nextCharacter) throws Exception{
        switch (nextCharacter){
            case ':':
                handleColon();
                break;
            case ',':
                if (!handleComma()){
                    throw new Exception("Format was incorrect");
                }
                break;
            case '}':
                return handleCloseBracket();
            default:
                throw new Exception("Something is wrong");
        }
        return true;
    }

    private Character readTillNoWhiteSpace(BufferedReader contents) throws Exception{
        Character character;
        while (true){
            character = Character.toChars(contents.read())[0];

            if (!(character.equals('\n') || character.equals(' '))) {
                return character;
            }
        }
    }
}
