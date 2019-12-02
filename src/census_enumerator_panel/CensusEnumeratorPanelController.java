package census_enumerator_panel;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CensusEnumeratorPanelController {
    @FXML
    private Button mBeginEnuBtn, mContinueEnuBtn, mViewMapBtn, mAccountBtn, mSettingsBtn;
    @FXML
    private AnchorPane mEnumeratorViewAP;

    @FXML
    private void onClickNavigateBtn(ActionEvent actionEvent) {
        if(actionEvent.getSource() == mBeginEnuBtn) {
            beginEnuView();
        } else if(actionEvent.getSource() == mContinueEnuBtn) {
            continueEnuView();
        } else if(actionEvent.getSource() == mViewMapBtn) {
           // viewMapView();
        } else if(actionEvent.getSource() == mAccountBtn) {
           // mAccountView();
        } else if(actionEvent.getSource() == mSettingsBtn) {
           // mSettingsView();
        } else {
            //Select a view
        }
    }

    private void beginEnuView() {
        //This view has the default category household

    }

    private void continueEnuView() {
        mEnumeratorViewAP.getChildren().addListener((ListChangeListener<Node>) c -> {

        });
    }

    private void configureQuestions() {
        AnchorPane mQuestionAP = new AnchorPane();
        mQuestionAP.setMaxSize(425, 120);
        mQuestionAP.setLayoutX(0);
        mQuestionAP.setLayoutY(0);
        mEnumeratorViewAP.getChildren().add(mQuestionAP);
        Label mQuestionLBL = new Label("Enter household number");
        mQuestionAP.setLayoutX(25);
        mQuestionAP.setLayoutY(25);
        mQuestionAP.getChildren().add(mQuestionLBL);
         TextField mQuestionTF = new TextField();
        mQuestionTF.setLayoutX(20);
        mQuestionTF.setLayoutY(60);
        mQuestionAP.getChildren().add(mQuestionTF);
    }




}
