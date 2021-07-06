package frontend;

import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NumberTextFieldTest {

    private NumberTextField textField = new NumberTextField("123");

    @Test
    public void testReplaceTextWithLetter(){
        textField.replaceSelection("a");
        Assert.assertEquals("123", textField.getText());
        Assert.assertEquals("-fx-control-inner-background: #a81830", textField.getStyle());
    }

    @Test
    public void testReplaceText(){
        textField.replaceSelection("1");
        Assert.assertEquals("1123", textField.getText());
        Assert.assertEquals("", textField.getStyle());
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
