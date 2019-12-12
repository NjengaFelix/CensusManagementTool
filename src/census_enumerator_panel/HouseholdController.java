package census_enumerator_panel;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HouseholdController {
    @FXML
    private TextField mWallMaterialTF, mRoofMaterialTF, mFloorMaterialTF;
    @FXML
    private AnchorPane householdAP;
    @FXML
    private ComboBox<Boolean> mLivestockCB, mFarmingCB;
    private Connection DBConn;

    @FXML
    private void initialize() {
        boolean yes = true, no = false;
        ObservableList<Boolean> practise =
                FXCollections.observableArrayList(yes, no);

        mLivestockCB.setItems(practise);
        mFarmingCB.setItems(practise);
    }

    @FXML
    private void onClickNextBtn() {
        if(mWallMaterialTF.getText().isEmpty() || mRoofMaterialTF.getText().isEmpty() || mFloorMaterialTF.getText().isEmpty()
        ||mLivestockCB.getValue() == null || mFarmingCB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Enter credentials");
            alert.show();
        } else {
            String householdNo = BeginEnumerationController.getGetHousehold();


            DBConn = DatabaseConnection.getConnection();
            String updateSql = "update households set WallMaterial = ?, RoofMaterial = ?," +
                    "FloorMaterial =?, Livestock = ?, Farming = ? where HouseholdNo = ?";
            try {
                PreparedStatement ps = DBConn.prepareStatement(updateSql);
                ps.setString(1,mWallMaterialTF.getText());
                ps.setString(2,mRoofMaterialTF.getText());
                ps.setString(3,mFloorMaterialTF.getText());
                ps.setBoolean(4,mLivestockCB.getValue());
                ps.setBoolean(5,mFarmingCB.getValue());
                ps.setString(6, householdNo);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../census_enumerator_panel/census_questions_view.fxml"));
            try {
                AnchorPane questionPane = fxmlLoader.load();
                householdAP.getChildren().clear();
                householdAP.getChildren().add(questionPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void onClickPreviousBtn() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../census_enumerator_panel/census_begin_enumeration_view.fxml"));
        try {
            AnchorPane questionPane = fxmlLoader.load();
            householdAP.getChildren().clear();
            householdAP.getChildren().add(questionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}