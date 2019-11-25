package census_text_editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CensusTextEditorModel {

    public void saveNew(TextFile textFile) {
        try {
            Files.write(textFile.getFile(), textFile.getContent(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(TextFile textFile) {
        try {
            Files.write(textFile.getFile(), textFile.getContent(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IOResult<TextFile> load(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);
            return new IOResult<>(new TextFile(file, lines), true);
        } catch (IOException e) {
            return new IOResult<>(null, false);
        }
    }

    public void close() {
        System.exit(0);
    }
}
