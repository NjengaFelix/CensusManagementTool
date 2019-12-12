package census_enumerator_panel;

import accounts_panel.LoginController;
import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BeginEnumerationController {
    @FXML
    private AnchorPane beginEnuAP;
    @FXML
    private TextField houseHoldNoTF, locationTF, houseHoldMembersTF;
    private static String getHousehold;
    Connection DBConn;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public static String getGetHousehold() {
        return getHousehold;
    }

    @FXML
    private void onClickNextCatBtn() {

        if(houseHoldNoTF.getText().isEmpty() || locationTF.getText().isEmpty() || houseHoldMembersTF.getText().isEmpty()) {
            alert.setTitle("Error");
            alert.setContentText("Enter credentials");
            alert.show();
        }else {
            if(executeInsert() > 0) {
                getHousehold = houseHoldNoTF.getText();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../census_enumerator_panel/census_household_view.fxml"));
                try {
                    AnchorPane questionPane = fxmlLoader.load();
                    beginEnuAP.getChildren().clear();
                    beginEnuAP.getChildren().add(questionPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private int executeInsert() {
        int checkUpdate = 0;
        try {
            DBConn = DatabaseConnection.getConnection();
            String insertSql = "insert into households(HouseholdNo, Location, Capacity, username) values (?,?,?,?)";
            PreparedStatement ps = DBConn.prepareStatement(insertSql);
            ps.setString(1,houseHoldNoTF.getText());
            ps.setString(2, locationTF.getText());
            ps.setString(3,houseHoldMembersTF.getText());
            ps.setString(4, LoginController.getUserName());
            checkUpdate = ps.executeUpdate();
        } catch (SQLException e) {
            alert.setTitle("Database error");
            alert.setHeaderText("Correct the wrong inputs");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        return checkUpdate;
    }




}


