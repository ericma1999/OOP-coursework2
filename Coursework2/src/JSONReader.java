import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.regex.Pattern;

public class JSONReader {

    private final DataFrame dataFrame = new DataFrame();

//    to get the columnNames and its value in correct order
    private final LinkedHashMap<String, String> tempValues = new LinkedHashMap<>();

    private final Stack<Character> stack = new Stack<>();
    private StringBuilder currentContent = new StringBuilder();
    private StringBuilder currentProperty = new StringBuilder();
    private static final Pattern pattern = Pattern.compile("[\\[\\]\", ]");
    private boolean firstItem = true;


    public JSONReader(String path) throws IOException{
        FileReader file = new FileReader(path);

        BufferedReader contents = new BufferedReader(file);
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
    }

    public DataFrame getDataFrame(){
        return this.dataFrame;
    }

    private void readProperties(BufferedReader contents) throws IOException {
        int charIntRepresentation;
        while ((charIntRepresentation = contents.read()) != -1){
            Character character = Character.toChars(charIntRepresentation)[0];

//            current character is not in a quotation mark so skip it
            if ((character.equals(' ') || character.equals('\n')) && !stack.peek().equals('"')){
                continue;
            }

            if (character.equals('"')){
                if (!stack.peek().equals('"')){

                    stack.add('"');

                }else {

                    stack.pop();
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

    private void resetTempContent(){
        currentContent = new StringBuilder();
        currentProperty = new StringBuilder();
    }

    private boolean handleComma(){
        if (!this.tempValues.get(currentProperty.toString()).equals("")){
            return false;
        }else {
            this.tempValues.put(currentProperty.toString(), currentContent.toString());
            resetTempContent();
        }
        return true;
    }

    private void addContentToDataFrame(){
        if (firstItem){
            this.dataFrame.setColumnNames(new ArrayList<>(this.tempValues.keySet()));
            firstItem = false;
        }

        this.dataFrame.addRow(new ArrayList<>(this.tempValues.values()));
    }

    private boolean handleCloseBracket(){
        this.tempValues.put(currentProperty.toString(), currentContent.toString());
        resetTempContent();
        addContentToDataFrame();
        stack.pop();
        return !stack.isEmpty();
    }

    private boolean handlePropertyEndCases(Character nextCharacter) throws IOException{
        switch (nextCharacter){
            case ':':
                handleColon();
                break;
            case ',':
                if (!handleComma()){
                    throw new IOException("Format was incorrect");
                }
                break;
            case '}':
                return handleCloseBracket();
            default:
                throw new IOException("JSON could not be parse");
        }
        return true;
    }

    private Character readTillNoWhiteSpace(BufferedReader contents) throws IOException{
        Character character;
        while (true){
            character = Character.toChars(contents.read())[0];

            if (!(character.equals('\n') || character.equals(' '))) {
                return character;
            }
        }
    }
}
