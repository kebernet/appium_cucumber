package cucumber.hooks;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.logging.LogEntry;

public class Recorder {

    private static final Logger LOGGER = Logger.getLogger(Recorder.class.getCanonicalName());
    private static List<File> IMAGES;
    private static List<LogEntry> LOGS;

    public static void record(File file) {
        IMAGES.add(file);
    }

    public static void log(List<LogEntry> logs){
        LOGS = logs;
    }

    @Before
    public void initialize() {
        IMAGES = new ArrayList<>();
        LOGS = new ArrayList<>();
    }

    @After
    public void finalize(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            File outDir = new File("build/cucumber-images");
            outDir.mkdirs();
            outDir.mkdir();
            if(IMAGES.isEmpty()){
                return;
            }
            BufferedImage first = ImageIO.read(IMAGES.iterator().next());
            File destination = new File(outDir, scenario.getName().replaceAll("[^\\w]", "_") + ".gif");
            try (
                    ImageOutputStream outputStream = new FileImageOutputStream(destination);
                    AnimatedGIFEncoder encoder = new AnimatedGIFEncoder(outputStream, first.getType(), 750, true)) {

                IMAGES.stream()
                        .map(f -> {
                            try {
                                return ImageIO.read(f);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .forEach(i -> {
                            try {
                                encoder.writeToSequence(i);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
            LOGGER.info("Wrote scenario animation to "+ destination.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteStreams.copy(new FileInputStream(destination), baos);
            scenario.embed(baos.toByteArray(), "image/gif");
            scenario.embed(logFile(), "text/plain");
        }
    }

    private byte[] logFile(){
        StringBuilder sb = new StringBuilder();
        LOGS.stream()
                .map(e-> new Date(e.getTimestamp())+","+e.getLevel().getName()+", "+e.getMessage())
                .forEach(line-> sb.append(line).append("\n"));
        return sb.toString().getBytes(Charsets.UTF_8);

    }

}
