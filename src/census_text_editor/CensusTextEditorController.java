package census_text_editor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.File;
import java.util.Arrays;

public class CensusTextEditorController {
    @FXML
    private TextArea mTextArea;
    @FXML
    private TextField mCategoryNameLbl;
    @FXML
    private Label mLeftStatusLbl;
    private TextFile currentTextFile;
    private CensusTextEditorModel censusTextEditorModel;
    private static Window stage = new Stage();
    private FileChooser fileChooser = new FileChooser();
    private static final String ERROR_FILE_OPERATION = "File operation failed";

    public CensusTextEditorController(CensusTextEditorModel censusTextEditorModel) {
        this.censusTextEditorModel = censusTextEditorModel;
    }

    @FXML
    private void onClickNewMenu() {
       mTextArea.clear();
        configureFileChooser(fileChooser);
        fileChooser.setInitialFileName(mCategoryNameLbl.getText().trim());
        File file = fileChooser.showSaveDialog(stage);
        if(censusTextEditorModel.newFile(file)) {

            mLeftStatusLbl.setText(file.getName()+ " created");
            //Update the currentFile to save effectively
            openFile(file);
        } else {
            mLeftStatusLbl.setText(ERROR_FILE_OPERATION);
        }

    }

    @FXML
    private void onClickOpenMenu() {
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if(file !=null) {
            openFile(file);
        }

    }

    @FXML
    private void onClickSaveCategory() {
        try{
            TextFile newTextFile = new TextFile(currentTextFile.getFile(), Arrays.asList(mTextArea.getText().split("\n")));
            censusTextEditorModel.save(newTextFile);
            mLeftStatusLbl.setText(newTextFile.getFile().getFileName().toString()+" changes made");
        } catch (NullPointerException e) {
            onClickNewMenu();
        }

    }

    @FXML
    private void onClickSaveAsMenu() {
        try {
            TextFile newTextFile = new TextFile(currentTextFile.getFile(), Arrays.asList(mTextArea.getText().split("\n")));
            configureFileChooser(fileChooser);
            fileChooser.setInitialFileName(newTextFile.getFile().getFileName().toString());
            fileChooser.showSaveDialog(stage);
        } catch (NullPointerException e) {
            onClickNewMenu();
        }

    }

    @FXML
    private void onClickCloseMenu() {
        censusTextEditorModel.close();
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
            mLeftStatusLbl.setText(file.getName()+" opened");
        } else {
            mLeftStatusLbl.setText(ERROR_FILE_OPERATION);
        }
    }


}
