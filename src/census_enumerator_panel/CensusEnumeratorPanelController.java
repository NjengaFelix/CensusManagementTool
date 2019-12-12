package census_enumerator_panel;
import database.DatabaseConnection;
import home_panel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CensusEnumeratorPanelController implements ControlledScreen  {
    @FXML
    private Button mBeginEnuBtn, mAccountBtn, mHomePanel;
    @FXML
    private ComboBox<String> mContinueEnuCB;
    @FXML
    private AnchorPane mEnumeratorViewAP;
    /*@FXML
    private Label mLeftStatus;*/
    @FXML
    private TextArea mDetailsTA;
    private String category2 = "Pending category", category3 = "Pending category", category4 = "Pending category", category5 = "Pending category";
    private Connection DBConn;
    String [] categoryName = new String[4];
    int i=0;


    ScreensController myController;

    @FXML
    private void initialize() {

        DBConn = DatabaseConnection.getConnection();

        try {
            updateCategories();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> categoryItems =
                FXCollections.observableArrayList(
                        "Household",
                        category2, category3, category4, category5
                );

        mContinueEnuCB.setItems(categoryItems);

        verticalNavigation("census_begin_enumeration_view.fxml");

        mDetailsTA.setText("Begin census enumeration\n" +
                "by entering the household\n" +
                "details");


    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }


    @FXML
    private void onClickNavigateBtn(ActionEvent actionEvent) {
        if(actionEvent.getSource() == mBeginEnuBtn) {
                verticalNavigation("census_begin_enumeration_view.fxml");
            mDetailsTA.setText("Begin census enumeration\n" +
                    "by entering the household\n" +
                    "details");
        } else if(actionEvent.getSource() == mAccountBtn) {
           // viewMapView();
        }else if (actionEvent.getSource() == mHomePanel){
            myController.setScreen(Main.screen1ID);
        }
    }


   public void verticalNavigation(String resources) {
       //This method helps to change screens vertically
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resources));
       AnchorPane questionPane = null;
       try {
           questionPane = fxmlLoader.load();
       } catch (IOException e) {
           e.printStackTrace();
       }
       try {
           mEnumeratorViewAP.getChildren().clear();
           mEnumeratorViewAP.getChildren().add(questionPane);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


    @FXML
    private void onClickContinueEnuCB() {

        category2 = categoryName[0];
        category3 = categoryName[1];
        category4 = categoryName[2];
        category5 = categoryName[3];

        String option = mContinueEnuCB.getValue();

        if(option.contains("Household")) {
                verticalNavigation("census_household_view.fxml");
            mDetailsTA.setText("Continue census enumeration\n" +
                    "by finishing the household\n" +
                    "details");
        } else if(option.contains(category2)) {
            verticalNavigation("census_questions_view.fxml");
        } else if(option.contains(category3)) {

        } else if(option.contains(category4)) {

        } else if(option.contains(category5)) {

        }
    }


    private void updateCategories() throws SQLException {
        String selectSql = "select CategoryName from category";
        PreparedStatement ps = DBConn.prepareStatement(selectSql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            setCategoryNames(rs.getString("CategoryName"));
        }
    }

    public void setCategoryNames(String name) {

        categoryName[i] = name;
        i++;
    }

    /*private void updateCategories() throws SQLException {
        String selectSql = "select * from category where CategoryID = ?";
            PreparedStatement ps = DBConn.prepareStatement(selectSql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                category2 = rs.getString("categoryName");
            }

        ps = DBConn.prepareStatement("select * from category where CategoryID = ?");
        ps.setInt(1, 2);
        rs = ps.executeQuery();
        while(rs.next()) {
            category3 = rs.getString("categoryName");
        }

        ps = DBConn.prepareStatement("select * from category where CategoryID = ?");
        ps.setInt(1, 3);
        rs = ps.executeQuery();
        while(rs.next()) {
            category4 = rs.getString("categoryName");
        }

        ps = DBConn.prepareStatement("select * from category where CategoryID = ?");
        ps.setInt(1, 4);
        rs = ps.executeQuery();
        while(rs.next()) {
            category5 = rs.getString("categoryName");
        }


    }*/



}
