package adapters.fileprocessing;

import adapters.fileprocessing.interfaces.FileParser;
import adapters.fileprocessing.interfaces.FileProcessor;
import frontend.Button;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class JSONFileProcessor implements FileProcessor {

    public Button[][] processFile(Path filePath, FileParser parser) {
        Button[][] result = new Button[0][];
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath.toString()))
        {
            result = parser.parseFile(jsonParser, reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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