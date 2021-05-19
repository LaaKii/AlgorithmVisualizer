package fileprocessing.interfaces;

import frontend.Button;

import java.nio.file.Path;

public interface FileProcessor {
    Button[][] processFile(Path filePath);
    void writeFile(Button[][] field, Path pathToWritefile);
}