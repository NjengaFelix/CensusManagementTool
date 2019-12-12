package census_enumerator_panel;

import screens_navigation.ControlledScreen;
import screens_navigation.ScreensController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class QuestionsController implements ControlledScreen {
    ScreensController myController;
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    /*private List<String> categoryQuestions(Path file) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(file);
            System.out.println(lines.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }*/
}
