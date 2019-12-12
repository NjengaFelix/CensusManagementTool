package supervisor_panel;

import database.DatabaseConnection;
import home_panel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupervisorPanelController implements ControlledScreen {

    ScreensController myController;
    @FXML
    private Button mLocationsBtn, mManageEnuBtn, mHomePanelBtn;
    Connection DBConn;
    @FXML
    TableView<HouseholdView> locationsViewTBV;
    @FXML
    private TableColumn<HouseholdView,String> column1, column2, column3, column4, column5;
    ObservableList<HouseholdView> locationsView = FXCollections.observableArrayList();
    @FXML
    private TextArea mDetailsTA;

    @FXML
    private void initialize() {

        mDetailsTA.setText("The enumerated locations report\n" +
                "is generated in this page for\n" +
                "the supervisor");

      column1.setCellValueFactory(new PropertyValueFactory<>("householdNo"));
        column2.setCellValueFactory(new PropertyValueFactory<>("location"));
        column3.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        column4.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column5.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        locationsViewTBV.setItems(getlocationsView());

    }



    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }


    public void onClickNavigate(ActionEvent actionEvent) {
        if(actionEvent.getSource() == mLocationsBtn) {
            mDetailsTA.setText("The enumerated locations report\n" +
                    "is generated in this page for\n" +
                    "the supervisor");
        } else if(actionEvent.getSource() == mManageEnuBtn) {
           myController.setScreen(Main.screen9ID);
        } else if(actionEvent.getSource() == mHomePanelBtn) {
            myController.setScreen(Main.screen1ID);
        }
    }

    private ObservableList<HouseholdView> getlocationsView() {

        DBConn = DatabaseConnection.getConnection();
        String selectSql = "select * from locationsview";
        try {
            PreparedStatement ps = DBConn.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                locationsView.add(new HouseholdView(Integer.toString(rs.getInt("householdNo")),rs.getString("location"),
                        Integer.toString(rs.getInt("capacity")), rs.getString("firstName"),
                        rs.getString("lastName")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return locationsView;
    }

    /*private void setLocationsView(String householdno, String location, String capacity, String firstName, String lastName) {
        ObservableList<HouseholdView> locationsView = FXCollections.observableArrayList(
                new HouseholdView(householdno, location, capacity, firstName, lastName)
        );
    }*/
}
