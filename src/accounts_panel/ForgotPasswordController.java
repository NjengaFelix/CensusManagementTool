package accounts_panel;

import com.mysql.jdbc.PreparedStatement;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ForgotPasswordController implements ControlledScreen {

    ScreensController myController;
    Connection DBConn;
    @FXML
    Label lblStatus, lblSecurityQn;
    @FXML
    TextField txtEmail, txtPassword, txtPassword1, txtSecurityAns;
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void onClickSignUp(ActionEvent actionEvent) {
        if (txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtSecurityAns.getText().isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else if(!validatePassword()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid password");
            alert.setContentText("Be between 8 and 40 characters long\n" +
                    "Contain at least one digit.\n" +
                    "Contain at least one lower case character.\n" +
                    "Contain at least one upper case character.\n" +
                    "Contain at least on special character from [ @ # $ % ! . ].");
            alert.show();
        }else if(!validateBothPasswords()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Passwords do not match");
        } else {
            updateCredentials();
        }
    }

    private void updateCredentials() {
        DBConn = DatabaseConnection.getConnection();
        String updateSql = "update users set password =? where email = ?";
        try {
            PreparedStatement ps = (PreparedStatement) DBConn.prepareStatement(updateSql);
            ps.setString(1, txtPassword.getText());
            ps.setString(2, txtEmail.getText());
            ps.executeQuery();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private boolean validateEmail() {
        boolean email = false;
        String getEmail = null;
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
        if(!getEmail.matches(txtEmail.getText())) {
            email = true;
        }
        return email;
    }

    private boolean validatePassword() {
        boolean password = false;
        if(txtPassword.getText().matches(PASSWORD_PATTERN)) {
            password = true;
        }
        return password;
    }

    private boolean validateBothPasswords() {
        boolean password = false;
        if(txtPassword.getText().matches(txtPassword1.getText())) {
            password = true;
        }
        return password;
    }

    @FXML
    private void onClickConfirmEmail() {
        if(!validateEmail()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Email doesn't exist");
        } else {
            String getSecurityQn = null;
            String selectSql = "select securityquestion from users where email = ?";
            DBConn = DatabaseConnection.getConnection();
            try {
                PreparedStatement ps = (PreparedStatement) DBConn.prepareStatement(selectSql);
                ps.setString(1, txtEmail.getText());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getSecurityQn = rs.getString("securityquestion");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            lblSecurityQn.setText(getSecurityQn);
        }


    }


    public void onClickSignIn(ActionEvent actionEvent) {
    myController.setScreen(Main.screen5ID);
    }
}
