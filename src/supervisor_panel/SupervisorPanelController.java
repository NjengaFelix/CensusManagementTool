package supervisor_panel;

import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

public class SupervisorPanelController implements ControlledScreen {

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
