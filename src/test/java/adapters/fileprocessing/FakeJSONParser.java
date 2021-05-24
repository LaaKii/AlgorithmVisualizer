package adapters.fileprocessing;

import adapters.fileprocessing.interfaces.FileParser;
import frontend.Button;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class FakeJSONParser implements FileParser {
    @Override
    public Button[][] parseFile(JSONParser jsonParser, FileReader reader) {
        Button[][] buttons = new Button[2][2];
        Button emptyButton = new Button("0");
        buttons[0][0] = emptyButton;
        buttons[0][1] = emptyButton;
        buttons[1][0] = emptyButton;
        buttons[1][1] = emptyButton;

        return buttons;
    }
}
