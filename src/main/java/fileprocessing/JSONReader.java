package fileprocessing;

import frontend.Button;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class JSONReader implements Reader{

    public Button[][] readFile(Path filePath) {
        Button[][] result = new Button[0][];
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath.toString()))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray matrix = (JSONArray) obj;
            result = new Button[matrix.size()][((JSONArray)matrix.get(0)).size()];
            for(int i = 0; i<matrix.size(); i++){
                JSONArray row = ((JSONArray)matrix.get(i));
                for(int j = 0; j<row.size(); j++){
                    Button tempButton = new Button(String.valueOf(row.get(j)));
                    result[i][j] = tempButton;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}