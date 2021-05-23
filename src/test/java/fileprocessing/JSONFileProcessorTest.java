package fileprocessing;

import frontend.Button;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JSONFileProcessorTest {

    private JSONFileProcessor jsonFileProcessor = new JSONFileProcessor();

    @Test
    public void testProcessFile(){
        Button[][] buttons = jsonFileProcessor.processFile(Path.of("test.json"), new FakeJSONParser());
        Assert.assertEquals(2, buttons.length);
    }

    @Test
    public void testWriteFile(){
        Button[][] buttons = jsonFileProcessor.processFile(Path.of("test.json"), new JSONFileParser());
        buttons[0][1] = new Button("Z");
        jsonFileProcessor.writeFile(buttons, Path.of("test.json"));
        Button[][] buttons1 = jsonFileProcessor.processFile(Path.of("test.json"), new JSONFileParser());
        Assert.assertEquals("Z", buttons1[0][1].getText());
    }


    //Need to do this, because toolkit of javafx has to be initialized to use Button
    @BeforeClass
    public static void initToolkit() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        // That's a pretty reasonable delay... Right?
        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
    }
}
