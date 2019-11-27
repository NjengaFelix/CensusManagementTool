package admin_panel;

import census_text_editor.CensusTextEditorController;
import census_text_editor.CensusTextEditorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AdminPanelController {
    @FXML
    private Button mUploadCatBtn, mManageSupBtn, mOpenTextEditorBtn;
    @FXML
    private Label mCatFileLbl, mLeftStatusLbl, mRightStatusLbl;
    @FXML
    private ScrollPane uploadCatSP;
    @FXML
    private AnchorPane mUploadAP;
    private Stage stage = new Stage();
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private void onClickNavigateBtn(ActionEvent actionEvent) {
        if(actionEvent.getSource() == mOpenTextEditorBtn) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../census_text_editor/census_text_editor_view.fxml"));
            loader.setControllerFactory(t -> new CensusTextEditorController(new CensusTextEditorModel()));
            try {
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(actionEvent.getSource() == mUploadCatBtn) {
            mLeftStatusLbl.setText("Upload category view");
            uploadCatSP.setVisible(true);
            configureFileChooser(fileChooser);
            listUploadedFiles(fileChooser.showOpenDialog(stage));
        } else if(actionEvent.getSource() == mManageSupBtn) {
            mLeftStatusLbl.setText("Manage supervisor view");
        } else {
            mLeftStatusLbl.setText("Select option");
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {

        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //Set extension filter
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

    private void listUploadedFiles(File file) {
        //TODO: listing of the uploaded files well
        String fileName = file.getName();
        mCatFileLbl.setText(fileName);
        Label label = new Label(fileName);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMaxHeight(150);
        anchorPane.setMaxWidth(350);
        mUploadAP.getChildren().add(anchorPane);
        anchorPane.getChildren().add(label);
    }
}
