package supervisor_panel;


import database.DatabaseConnection;
import home_panel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageEnumeratorsController implements ControlledScreen {
    ScreensController myController;
    @FXML
    private Label lblStatus;
    @FXML
    private TableView<ManageEnumeratorView> manageEnumeratorTBV;
    @FXML
    private TableColumn<ManageEnumeratorView,String> column1, column2, column3, column4;
    Connection DBConn;
    private ObservableList<ManageEnumeratorView> getEnumeratorsView = FXCollections.observableArrayList();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @Override
    public void setScreenParent(ScreensController screenParent) { myController = screenParent; }

    @FXML
    private void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("email"));
        column4.setCellValueFactory(new PropertyValueFactory<>("status"));

        manageEnumeratorTBV.setEditable(true);
        column4.setCellFactory(TextFieldTableCell.forTableColumn());


        manageEnumeratorTBV.setItems(getEnumeratorsView());

    }

    private ObservableList<ManageEnumeratorView> getEnumeratorsView() {

        DBConn = DatabaseConnection.getConnection();
        String selectSql = "select * from enumeratorsView";
        try {
            PreparedStatement ps = DBConn.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                getEnumeratorsView.add(new ManageEnumeratorView(rs.getString("firstName"),rs.getString("lastName"),
                        rs.getString("email"),rs.getString("pendingstatus")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getEnumeratorsView;
    }

    @FXML
    private void onEditCommitColumn4(TableColumn.CellEditEvent<ManageEnumeratorView, String> manageEnumeratorViewStringCellEditEvent) {
        ManageEnumeratorView manageEnumeratorView = manageEnumeratorTBV.getSelectionModel().getSelectedItem();
        manageEnumeratorView.setStatus(manageEnumeratorViewStringCellEditEvent.getNewValue());
        String getPermission = manageEnumeratorView.getStatus();
        String getEmail = manageEnumeratorView.getEmail();
        if(setPermission(getPermission,getEmail) > 0) {
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Status for "+ manageEnumeratorView.getEmail()+" changed");
        }
    }

    private int setPermission(String permission, String email) {
        int executeUpdate = 0;
        DBConn = DatabaseConnection.getConnection();
        String selectSql = "update users set pendingstatus = ? where email = ?";
        try {
            PreparedStatement ps = DBConn.prepareStatement(selectSql);
            ps.setString(1, permission);
            ps.setString(2, email);
            executeUpdate = ps.executeUpdate();
        } catch (SQLException e) {
            alert.setTitle("Database error");
            alert.setHeaderText("Correct the wrong inputs");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Error");
        }
        return executeUpdate;
    }

    @FXML
    private void onClickSupervisorPanel() {
        myController.setScreen(Main.screen3ID);
    }
}
