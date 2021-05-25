package backend.common;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.interfaces.FieldChecker;
import frontend.Button;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TwoDimenstionalFieldCheckerTest {

    @Test
    public void testCanNextFieldBeReached(){
        FieldChecker fieldChecker = new TwoDimensionalFieldChecker();
        Index indexToCheck = new Index(1,1);
        Button[][] buttons = new Button[3][3];

        Button button00 = new Button("0");
        button00.setVisited(false);
        buttons[0][0]=button00;
        buttons[0][1]=button00;
        buttons[0][2]=button00;
        buttons[1][0]=button00;
        buttons[1][1]=button00;
        buttons[1][2]=button00;
        buttons[2][0]=button00;
        buttons[2][1]=button00;
        buttons[2][2]=button00;
        Field field = new Field(buttons);
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.NORTH, field));
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.SOUTH, field));
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.EAST, field));
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.WEST, field));
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.NORTHEAST, field));
        //Needs to be done, since indexToCheck gets manipulated in method
        indexToCheck = new Index(1,1);
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.NORTHWEST, field));
        //Needs to be done, since indexToCheck gets manipulated in method
        indexToCheck = new Index(1,1);
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.SOUTHEAST, field));
        //Needs to be done, since indexToCheck gets manipulated in method
        indexToCheck = new Index(1,1);
        Assert.assertEquals(true, fieldChecker.canNextFieldByDirectionBeReached(indexToCheck, Direction.SOUTHWEST, field));

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

