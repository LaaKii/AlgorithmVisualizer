package adapters.fileprocessing;

import adapters.fileprocessing.interfaces.FileParser;
import adapters.fileprocessing.interfaces.FileProcessor;
import adapters.fileprocessing.interfaces.RemoteConnectionEtablisher;
import frontend.Button;

import java.nio.file.Path;

public class RestRequestProcessor implements FileProcessor, RemoteConnectionEtablisher {
    @Override
    public Button[][] processFile(Path filePath, FileParser parser) {
        return new Button[0][];
    }

    @Override
    public void writeFile(Button[][] field, Path pathToWritefile) {

    }

    @Override
    public void readFilesFromServer() {

    }
}
