package screens_navigation;

import screens_navigation.ScreensController;

public interface ControlledScreen {
    //This method will allow injection of the Parent ScreenPane
    public void setScreenParent(ScreensController screenParent);
}
