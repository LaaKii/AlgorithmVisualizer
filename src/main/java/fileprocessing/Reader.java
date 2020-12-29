package fileprocessing;

import frontend.Button;

import java.nio.file.Path;

public interface Reader {
    Button[][] readFile(Path filePath);
}