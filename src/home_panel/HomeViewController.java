package home_panel;

import census_text_editor.CensusTextEditorController;
import census_text_editor.CensusTextEditorModel;
import database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HomeViewController implements ControlledScreen {
    private FXMLLoader loader;
    private Stage stage = new Stage();
    ScreensController myController;
    Connection DBConn;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private void onClickLoginBtn() {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void onClickExitBtn() {
        System.exit(0);
    }



    /*@FXML
    private void onClickAdminPanel() {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void onClickSupervisorPanel() {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void onClickCensusEnumeratorPanel() {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void onClickTextEditorPanel() {
        loader = new FXMLLoader(getClass().getResource("../census_text_editor/census_text_editor_view.fxml"));
        loader.setControllerFactory(t -> new CensusTextEditorController(new CensusTextEditorModel()));
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
