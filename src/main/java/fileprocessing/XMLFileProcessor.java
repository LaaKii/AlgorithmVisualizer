package fileprocessing;

import frontend.Button;

import java.nio.file.Path;

public class XMLFileProcessor implements FileProcessor {

    @Override
    public Button[][] processFile(Path filePath) {
        return new Button[0][];
    }

    @Override
    public void writeFile(Button[][] field, Path pathToWriteFile) {

    }
}

