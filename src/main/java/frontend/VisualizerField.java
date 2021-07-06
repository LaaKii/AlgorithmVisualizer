package frontend;

import adapters.fileprocessing.FileToButtonArrayMap;
import backend.common.AlertManager;
import backend.searchAlgorithms.SearchField;
import backend.searchAlgorithms.interfaces.SearchAlgorithm;
import adapters.fileprocessing.JSONFileParser;
import adapters.fileprocessing.interfaces.FileProcessor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

public class VisualizerField {

    private backend.searchAlgorithms.SearchField searchField;
    private SearchAlgorithm searchAlgorithm;
    private Path pathToConfig;

    //needed for heuristic search algorithms
    private FileProcessor fileProcessor;

    public VisualizerField(Path pathToConfig, FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
        this.pathToConfig=pathToConfig;
    }

    public Node refreshField(){
        return createFieldByConfig(pathToConfig);
    }

    public Node createFieldByConfig(Path pathToConfig) {
        searchField = new backend.searchAlgorithms.SearchField();
        FileToButtonArrayMap fileToButtonArrayMap = new FileToButtonArrayMap(fileProcessor, new JSONFileParser());
        searchField.initField(fileToButtonArrayMap.getButtonArrayByFile(pathToConfig));
        return searchField.getGrid();
    }

    public boolean nextSearchStep(){
        if (searchAlgorithm !=null){
          return searchAlgorithm.doSearch(searchField);
        } else{
            showAlgorithmError();
            throw new NullPointerException("Search Algorithm isn't set");
        }
    }

    private void showAlgorithmError(){
        AlertManager alertManager = new AlertManager.Builder()
                .setTitle("Algorithm Error")
                .setHeaderText("Algorithm not set")
                .setContextText("Either the algorithm couldn't be recognized or it isn't set")
                .setAlertType(Alert.AlertType.ERROR)
                .build();
        alertManager.showAlert();
    }

    public void setSearchAlgorithm(SearchAlgorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    public void resetField(VBox parent){
        parent.getChildren().remove(searchField.getGrid());
        searchField.setButtons(null);
        parent.getChildren().add(refreshField());
    }

    public backend.searchAlgorithms.SearchField getSearchField(){
        return searchField;
    }

    public void safeField(Path pathToWriteFile) {
        fileProcessor.writeFile(searchField.getButtons(),pathToWriteFile);
    }
}

