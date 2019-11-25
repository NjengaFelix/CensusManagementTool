package census_text_editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class CensusTextEditorController {
    @FXML
    private TextArea mTextArea;
    private TextFile currentTextFile;
    private CensusTextEditorModel censusTextEditorModel;
    private static Window stage;
    private FileChooser fileChooser = new FileChooser();
    private File file;

    public CensusTextEditorController(CensusTextEditorModel censusTextEditorModel) {
        this.censusTextEditorModel = censusTextEditorModel;
    }

    @FXML
    private void onClickNewMenu() {
       /* mTextArea.clear();
        configureFileChooser(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
        TextFile newTextFile = new TextFile(file.toPath(),Arrays.asList(mTextArea.getText().split("\n")));
        censusTextEditorModel.save(newTextFile);*/
    }

    @FXML
    private void onClickOpenMenu() {
        configureFileChooser(fileChooser);
        file = fileChooser.showOpenDialog(stage);
        if(file !=null) {
            openFile(file);
        }

    }

    @FXML
    private void onClickCloseMenu() {
        censusTextEditorModel.close();
    }

    @FXML
    private void onClickSaveAsMenu() {

    }

    @FXML
    private void onClickSaveCategory() {
        TextFile newTextFile = new TextFile(currentTextFile.getFile(), Arrays.asList(mTextArea.getText().split("\n")));
        censusTextEditorModel.save(newTextFile);
    }

    @FXML
    private void onClickAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Census text editor");
        alert.setTitle("About");
        alert.setContentText("Census text editor(Version 1.0)");
        alert.show();
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Open File");
       fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
       //Set extension filter
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);


    }

    private void openFile(File file) {
        IOResult<TextFile> io = censusTextEditorModel.load(file.toPath());

        if(io.isOk() && io.hasData()) {
            currentTextFile = io.getData();
            mTextArea.clear();
            currentTextFile.getContent().forEach(line -> mTextArea.appendText(line + "\n"));
        } else {
            System.out.println("Failed");
        }
    }




}
