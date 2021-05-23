package fileprocessing.interfaces;

import frontend.Button;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public interface FileParser {
    Button[][] parseFile(JSONParser jsonParser, FileReader reader);
}
