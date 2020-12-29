package fileprocessing;

import frontend.Button;

import java.nio.file.Path;

public interface FileProcessor {
    Button[][] readFile(Path filePath);
    void writeFile(Button[][] field, Path pathToWritefile);
}