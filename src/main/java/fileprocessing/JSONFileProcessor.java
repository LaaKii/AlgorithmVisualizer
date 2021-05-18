package fileprocessing;

import frontend.Button;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class JSONFileProcessor implements FileProcessor {

    public Button[][] processFile(Path filePath) {
        Button[][] result = new Button[0][];
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath.toString()))
        {
            result = parseFile(jsonParser, reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Button[][] parseFile(JSONParser jsonParser, FileReader reader) {
        Object obj = null;
        try {
            obj = jsonParser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray matrix = (JSONArray) obj;
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

    @Override
    public void writeFile(Button[][] field, Path pathToWriteFile) {
        JSONArray outerArray = new JSONArray();
        for(Button[] btnArr: field){
            JSONArray innerArray = new JSONArray();
            for(Button btn : btnArr){
                innerArray.add(btn.getText());
            }
            outerArray.add(innerArray);
        }

        try(FileWriter fileWriter = new FileWriter(pathToWriteFile.toFile())){
            fileWriter.write(outerArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}