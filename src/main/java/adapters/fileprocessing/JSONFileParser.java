package adapters.fileprocessing;

import adapters.fileprocessing.interfaces.FileParser;
import frontend.Button;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONFileParser implements FileParser {

    @Override
    public Button[][] parseFile(JSONParser jsonParser, FileReader reader) {
        Object obj = null;
        try {
            obj = jsonParser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Button[][] result = new Button[1][1];
        if (!isObjectNull(obj)){
            result = getButtonsField((JSONArray) obj);
        }
        return result;
    }

    private Button[][] getButtonsField(JSONArray obj) {
        JSONArray matrix = obj;
        Button[][] result = new Button[matrix.size()][((JSONArray) matrix.get(0)).size()];
        for(int i = 0; i<matrix.size(); i++){
            JSONArray row = ((JSONArray)matrix.get(i));
            for(int j = 0; j<row.size(); j++){
                Button tempButton = new Button(String.valueOf(row.get(j)));
                result[i][j] = tempButton;
            }
        }
        return result;
    }

    private boolean isObjectNull(Object obj) {
        if (obj == null){
            System.err.println("Object is null. Check File passed to JSONFileParser");
            return true;
        }
        return false;
    }
}
