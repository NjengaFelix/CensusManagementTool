package accounts_panel;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import home_panel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

public class SignUpController implements ControlledScreen {
    @FXML
    private TextField txtFirstName, txtLastName, txtEmail, txtUsername, txtPassword, txtSecurityAns;
    @FXML
    private ComboBox<String> mUserStatusCB, mSecurityQnCB;
    @FXML
    private Label lblStatus;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection DBConn;
    ScreensController myController;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";


    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private void initialize() {
        ObservableList<String> user_status =
                FXCollections.observableArrayList(
                        "Administrator",
                        "Supervisor", "Census enumerator"
                );

        mUserStatusCB.setItems(user_status);

        ObservableList<String> security_questions =
                FXCollections.observableArrayList(
                        "What is your father's name?",
                        "What is your dog's name?", "What is your favourite color?"
                );

        mSecurityQnCB.setItems(security_questions);
    }

    @FXML
    private void onClickSignUp() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtUsername.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || mUserStatusCB.getValue() == null || mSecurityQnCB.getValue() == null) {

            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else if(!validateUserName()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Username already taken");
        } else if(!validateEmail()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter a valid email with @ or .com");
        }else if(!validatePassword()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid password");
            alert.setContentText("Be between 8 and 40 characters long\n" +
                    "Contain at least one digit.\n" +
                    "Contain at least one lower case character.\n" +
                    "Contain at least one upper case character.\n" +
                    "Contain at least on special character from [ @ # $ % ! . ].");
            alert.show();
        } else {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(saveData());
            myController.setScreen(Main.screen5ID);
        }


    }

    private boolean validateEmail() {
        boolean email = false;
        if(txtEmail.getText().matches(EMAIL_PATTERN)) {
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

    private boolean validateUserName() {
        boolean username = false;
        String getUsername = null;
        String selectSql = "select username from users";
        DBConn = DatabaseConnection.getConnection();
        try {
            PreparedStatement ps = (PreparedStatement) DBConn.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                getUsername = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        assert getUsername != null;
        if(!getUsername.matches(txtUsername.getText())) {
            username = true;
        }
        return username;
    }


    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
    }

    private String saveData() {
        DBConn = DatabaseConnection.getConnection();
        try {
            String st = "INSERT INTO users (firstname, lastname, email, username, password, securityquestion, securityanswer, userstatus, pendingstatus)" +
                    " VALUES (?,?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) DBConn.prepareStatement(st);
            preparedStatement.setString(1, txtFirstName.getText());
            preparedStatement.setString(2, txtLastName.getText());
            preparedStatement.setString(3, txtEmail.getText());
            preparedStatement.setString(4, txtUsername.getText());
            preparedStatement.setString(5, txtPassword.getText());
            preparedStatement.setString(6, mSecurityQnCB.getValue());
            preparedStatement.setString(7, txtSecurityAns.getText());
            preparedStatement.setString(8, mUserStatusCB.getValue());
            preparedStatement.setBoolean(9, pendingStatus());
            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    private boolean pendingStatus() {
        boolean pendingStatus = false;
        if(mUserStatusCB.getValue().contains("Administrator")) {
            pendingStatus = true;
        } else if(mUserStatusCB.getValue().contains("Supervisor")) {
            pendingStatus = false;
        } else {
            pendingStatus = false;
        }
        return pendingStatus;
    }


    @FXML
    private void onClickSignIn() {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void onMouseClickedForgotPass() {
        myController.setScreen(Main.screen7ID);
    }
}