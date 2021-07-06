package adapters.fileprocessing.interfaces;

import frontend.Button;

import java.nio.file.Path;

public interface FileProcessor {
    Button[][] processFile(Path filePath, FileParser fileParser);
    void writeFile(Button[][] field, Path pathToWritefile);
}