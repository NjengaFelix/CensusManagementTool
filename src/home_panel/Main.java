package home_panel;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import screens_navigation.ScreensController;

public class Main extends Application {
    public static String screen1ID = "main_screen";
    public static String screen1File = "../home_panel/home_view.fxml";
    public static String screen2ID = "admin_screen";
    public static String screen2File = "../admin_panel/admin_panel_view.fxml";
    public static String screen3ID = "supervisor_screen";
    public static String screen3File = "../supervisor_panel/supervisor_panel_view.fxml";
    public static String screen4ID = "enumerator_screen";
    public static String screen4File = "../census_enumerator_panel/census_enumerator_panel_view.fxml";
    public static String screen5ID = "login_screen";
    public static String screen5File = "../accounts_panel/login_view.fxml";
    public static String screen6ID = "sign_up_screen";
    public static String screen6File = "../accounts_panel/sign_up_view.fxml";
    public static String screen7ID = "forgot_password_screen";
    public static String screen7File = "../accounts_panel/forgot_password_view.fxml";
    public static String screen8ID = "admin_supervisor_screen";
    public static String screen8File = "../admin_panel/manage_supervisors_view.fxml";
    public static String screen9ID = "supervisor_enumerator_screen";
    public static String screen9File = "../supervisor_panel/manage_enumerators_view.fxml";




    @Override
    public void start(Stage primaryStage) {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.screen1ID, Main.screen1File);
        mainContainer.loadScreen(Main.screen2ID, Main.screen2File);
        mainContainer.loadScreen(Main.screen3ID, Main.screen3File);
        mainContainer.loadScreen(Main.screen4ID, Main.screen4File);
        mainContainer.loadScreen(Main.screen5ID, Main.screen5File);
        mainContainer.loadScreen(Main.screen6ID, Main.screen6File);
        mainContainer.loadScreen(Main.screen7ID, Main.screen7File);
        mainContainer.loadScreen(Main.screen8ID, Main.screen8File);
        mainContainer.loadScreen(Main.screen9ID, Main.screen9File);


        mainContainer.setScreen(Main.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Census Management Tool");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}
