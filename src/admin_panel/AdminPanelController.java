package admin_panel;

import census_text_editor.CensusTextEditorController;
import census_text_editor.CensusTextEditorModel;
import database.DatabaseConnection;
import home_panel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminPanelController implements ControlledScreen {
    @FXML
    private Button mUploadCatBtn, mManageSupBtn, mOpenTextEditorBtn, mHomePanelBtn;
    @FXML
    private Label mLeftStatusLbl, mRightStatusLbl;
    @FXML
    private ScrollPane uploadCatSP;
    @FXML
    private AnchorPane mUploadAP;
    @FXML
    private TextArea mDetailsTA;
    private Stage stage = new Stage();
    private FileChooser fileChooser = new FileChooser();
    //Creating an array to save already uploaded file names. Maximum number of categories is 5
    private String[] fileNames = new String[5];
    private int i, y = 0;
    private Connection DBConn = null;
    ScreensController myController;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);


    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private void initialize() {
        mDetailsTA.setText("Can only update a maximum of 4\n" +
                " categories" +
                "The uploaded categories are sent to\n" +
                " the Census enumerator panel\n" +
                "Only a maximum of 5-8 are\n" +
                " allowed per category");
    }

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
            //Can only update 5 categories
            mDetailsTA.setText("Can only update a maximum of 4\n" +
                    " categories. " +
                    "The uploaded categories\n" +
                    " are sent to\n" +
                    " the Census enumerator panel\n" +
                    "Only a maximum of 5-8 are\n" +
                    " allowed per category");
            if(i<5) {
                try {
                    mLeftStatusLbl.setText("Upload category view");
                    uploadCatSP.setVisible(true);
                    configureFileChooser(fileChooser);
                    listUploadedFiles(fileChooser.showOpenDialog(stage));
                } catch (SQLException | NullPointerException | IllegalArgumentException e) {
                    alert.setTitle("NullPointer error");
                    alert.setHeaderText("Select a file");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            } else {
                mLeftStatusLbl.setText("Can only update five categories");
            }
        } else if(actionEvent.getSource() == mManageSupBtn) {
           myController.setScreen(Main.screen8ID);
        }else if(actionEvent.getSource() == mHomePanelBtn) {
            myController.setScreen(Main.screen1ID);
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

    private void listUploadedFiles(@NotNull File file) throws SQLException {
        String fileName = removeLastCharacter(file.getName());
        System.out.println(fileName);

        try{
            for(String name : fileNames) {
                if (name.contains(fileName)) {
                    mLeftStatusLbl.setText("File already uploaded");
                    break;
                } else {
                    DBConn = DatabaseConnection.getConnection();
                    String sqlInsert = "insert into category (categoryName, filePath) VALUES (?,?)";
                    PreparedStatement ps = DBConn.prepareStatement(sqlInsert);
                    ps.setString(1, fileName);
                    ps.setString(2, file.getPath());
                    ps.executeUpdate();
                    configureFileView(fileName);
                    fileNames[i] = fileName;
                    //Change the next position for the name to be saved
                    i++;
                    break;
                }
            }
        } catch (NullPointerException e) {
            configureFileView(fileName);
            DBConn = DatabaseConnection.getConnection();
            String sqlInsert = "insert into category (categoryName, filePath) VALUES (?,?)";
            PreparedStatement ps = DBConn.prepareStatement(sqlInsert);
            ps.setString(1, fileName);
            ps.setString(2, file.getPath());
            ps.executeUpdate();
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

    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 4);
        }
        return result;
    }
}
