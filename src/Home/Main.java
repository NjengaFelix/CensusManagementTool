package Home;

import census_text_editor.CensusTextEditorController;
import census_text_editor.CensusTextEditorModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       Parent root = FXMLLoader.load(getClass().getResource("home_view.fxml"));
        primaryStage.setTitle("Census Enumeration Tool");
        primaryStage.setScene(new Scene(root, 1300, 800));
        primaryStage.show();

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("../census_text_editor/census_text_editor_view.fxml"));
        loader.setControllerFactory(t -> new CensusTextEditorController(new CensusTextEditorModel()));

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }


}
