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
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class AdminPanelController {
    @FXML
    private Button mUploadCatBtn, mManageSupBtn, mOpenTextEditorBtn;
    @FXML
    private Label mLeftStatusLbl, mRightStatusLbl;
    @FXML
    private ScrollPane uploadCatSP;
    @FXML
    private AnchorPane mUploadAP;
    private Stage stage = new Stage();
    private FileChooser fileChooser = new FileChooser();
    //Creating an array to save already uploaded file names. Maximum number of categories is 5
    private String[] fileNames = new String[5];
    private int i, y = 0;

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
            //Can only update 5 categories
            if(i<5) {
                listUploadedFiles(fileChooser.showOpenDialog(stage));
            } else {
                mLeftStatusLbl.setText("Can only update five categories");
            }
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

    private void listUploadedFiles(@NotNull File file) {
        String fileName = file.getName();

        try{
            for(String name : fileNames) {
                if (name.contains(fileName)) {
                    mLeftStatusLbl.setText("File already uploaded");
                    break;
                } else {
                    configureFileView(fileName);
                    fileNames[i] = fileName;
                    //Change the next position for the name to be saved
                    i++;
                    break;
                }
            }
        } catch (NullPointerException e) {
            configureFileView(fileName);
            fileNames[i] = fileName;
            //Change the next position for the name to be saved
            i++;
        }
    }

    private void configureFileView(String labelName) {
        AnchorPane filePane = new AnchorPane();
        filePane.setMaxSize(425, 50);
        mUploadAP.getChildren().add(filePane);
        filePane.setLayoutY(y);
        Label label1 = new Label(labelName);
        filePane.getChildren().add(label1);
        //Change the y layout position
        y+=50;
    }
}
