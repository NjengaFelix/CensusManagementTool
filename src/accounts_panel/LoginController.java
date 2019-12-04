package accounts_panel;

import database.DatabaseConnection;
import home_panel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements ControlledScreen {
    ScreensController myController;
    @FXML
    private TextField txtUsername, txtPassword;
    @FXML
    private Label lblStatus;
    Connection DBConn;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void onClickSignIn() {
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else {
            confirmData();
            clearFields();
        }
    }

    private void confirmData() {
        String uname = txtUsername.getText();
        String pass = txtPassword.getText();
        if (checkLoginUser(uname, pass)) {
            //Check type of user
            checkTypeOfUser(uname);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Incorrect username or password");
            alert.show();
            //Clear fields
            clearFields();
        }
    }

    public void checkTypeOfUser(String uname) {
        DBConn = DatabaseConnection.getConnection();
        String selectSql = "select userstatus, PendingStatus from users where username = ?";
        try {
            PreparedStatement ps = DBConn.prepareStatement(selectSql);
            ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            String status = null;
            Boolean pendingStatus = false;
            while(rs.next()) {
                status = rs.getString("userstatus");
                pendingStatus = rs.getBoolean("pendingstatus");
            }
            if(status.contains("Administrator") && pendingStatus) {
                myController.setScreen(Main.screen2ID);
            } else if(status.contains("Supervisor") && pendingStatus) {
                myController.setScreen(Main.screen3ID);
            } else if(status.contains("Census enumerator") && pendingStatus) {
                //Census enumerator
                myController.setScreen(Main.screen4ID);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("User must be approved");
                alert.show();
                //Clear fields
                clearFields();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean checkLoginUser(String uname, String pass) { //get input from login system module
        String checkQuery = "select * from users where username = ? and password = ? ";
        boolean status = false; //initially false

        try {
            DBConn = DatabaseConnection.getConnection();
            PreparedStatement ps =  DBConn.prepareStatement(checkQuery);
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet resultSet = ps.executeQuery();

            status = resultSet.next();
            ps.close();
            return status;

        } catch (SQLException e) {
//            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return status;
    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
    }

    public void onClickSignup(ActionEvent actionEvent) {
        myController.setScreen(Main.screen6ID);
    }

    public void onClickForgotPass(MouseEvent mouseEvent) {
        myController.setScreen(Main.screen7ID);
    }
}



