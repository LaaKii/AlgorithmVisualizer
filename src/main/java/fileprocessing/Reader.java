package fileprocessing;

import javafx.scene.control.Button;

import java.nio.file.Path;

public interface Reader {
    Button[][] readFile(Path filePath);
}