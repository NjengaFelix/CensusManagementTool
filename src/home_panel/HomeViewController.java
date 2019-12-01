package home_panel;

import census_text_editor.CensusTextEditorController;
import census_text_editor.CensusTextEditorModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeViewController {
    private FXMLLoader loader;
    private Stage stage = new Stage();

    @FXML
    private void onClickAdminPanel() {
        loader = new FXMLLoader(getClass().getResource("../admin_panel/admin_panel_view.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSupervisorPanel() {
        loader = new FXMLLoader(getClass().getResource("../supervisor_panel/supervisor_panel_view.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickCensusEnumeratorPanel() {
        loader = new FXMLLoader(getClass().getResource("../census_enumerator_panel/census_enumerator_panel_view.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}
