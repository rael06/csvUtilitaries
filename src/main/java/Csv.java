import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Csv {
    private Path path;
    private File file;
    private List<String> lines;

    public Csv(String p) {
        path = Paths.get(p);
        file = path.toFile();
        try {
            lines = makeLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> makeLines() throws IOException {
        return Files.readAllLines(path);
    }

    public void formatLines() {
        lines = lines.stream().map(this::formatLine).collect(Collectors.toList());
    }

    private String formatLine(String line) {
        return line.replace(", ", " - ");
    }

    public static void main(String[] args) {
        Csv csv = new Csv(args[0]);
        csv.formatLines();
        try {
            csv.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("\n");
        lines.forEach(sj::add);
        return sj.toString();
    }

    public void write() throws IOException {
        Path test = path.getParent().resolve(FilenameUtils.removeExtension(path.getFileName().toString()) + "-test.csv");
        Files.write(test, toString().getBytes(), StandardOpenOption.CREATE);
    }

    public Path getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public List<String> getLines() {
        return lines;
    }
}
