package adapters.fileprocessing;

import adapters.fileprocessing.interfaces.FileParser;
import adapters.fileprocessing.interfaces.FileProcessor;

import java.nio.file.Path;

public class FileToButtonArrayMap {

    private FileProcessor processor;
    private FileParser parser;

    public FileToButtonArrayMap(FileProcessor processor, FileParser parser){
        this.processor=processor;
        this.parser = parser;
    }

    public frontend.Button[][] getButtonArrayByFile(Path filePath){
        return processor.processFile(filePath, parser);
    }

}
