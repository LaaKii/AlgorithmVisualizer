package adapters.fileprocessing;

import adapters.fileprocessing.interfaces.FileParser;
import adapters.fileprocessing.interfaces.FileProcessor;
import frontend.Button;

import java.nio.file.Path;

public class XMLFileProcessor implements FileProcessor {

    @Override
    public Button[][] processFile(Path filePath, FileParser fileParser) {
        return new Button[0][];
    }

    @Override
    public void writeFile(Button[][] field, Path pathToWriteFile) {

    }
}

