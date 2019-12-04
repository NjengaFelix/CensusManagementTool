package accounts_panel;

import com.mysql.jdbc.PreparedStatement;
import database.DatabaseConnection;
import home_panel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageSupervisor implements ControlledScreen {
    ScreensController myController;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblStatus;
    @FXML
    private ComboBox<Boolean> mPermissionCB;
    Connection DBConn;
    private String getEmail = null;
    private static final Boolean approve_permission = true;
    private static final Boolean deny_permission = false;
    @Override
    public void setScreenParent(ScreensController screenParent) { myController = screenParent; }

    @FXML
    private void initialize() {
        ObservableList<Boolean> permissions =
                FXCollections.observableArrayList(
                        approve_permission, deny_permission);

        mPermissionCB.setItems(permissions);
    }

    @FXML
    private void onClickConfirmEmail() {
        if(txtEmail.getText().isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter email");
        }else if(validateEmail()) {
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Email found");
        } else {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Invalid email");
        }
    }

    @FXML
    private void onClickApproveBtn(ActionEvent actionEvent) {
        if(mPermissionCB.getValue() ==null) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter choice");
        } else {
            setPermission();
            myController.setScreen(Main.screen2ID);
        }
    }

    private void setPermission() {
        DBConn = DatabaseConnection.getConnection();
        String selectSql = "update users set pendingstatus = ? where email = ?";
        try {
            PreparedStatement ps = (PreparedStatement) DBConn.prepareStatement(selectSql);
            ps.setBoolean(1, mPermissionCB.getValue());
            ps.setString(2, getEmail);
            ps.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    private boolean validateEmail() {
        boolean email = false;
        String selectSql = "select email from users";
        DBConn = DatabaseConnection.getConnection();
        try {
            PreparedStatement ps = (PreparedStatement) DBConn.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                getEmail = rs.getString("email");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        assert getEmail != null;
        if( getEmail.matches(txtEmail.getText())) {
            email = true;
        }
        return email;
    }
}
