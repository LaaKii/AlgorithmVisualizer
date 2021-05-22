package backend.common;

import backend.searchAlgorithms.Direction;
import backend.searchAlgorithms.Field;
import backend.searchAlgorithms.Index;
import backend.searchAlgorithms.interfaces.FieldChecker;
import backend.searchAlgorithms.interfaces.FieldinfoProvider;
import frontend.Button;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TwoDimensionalIndiceProviderTest {

    private FieldinfoProvider fieldinfoProvider;
    private Index indexToCheck;
    private Field field;
    private Button[][] buttons = new Button[3][3];

    @Before
    public void init(){
        fieldinfoProvider = new TwoDimensionalIndiceProvider();
        indexToCheck = new Index(1,1);
        field = new Field();
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
        field.setTwoDimensionalField(buttons);
    }

    @Test
    public void testGetNextIndices(){
        //"Normal" Directions
        List<Index> nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.NORTH, field);
        Index expectedIndex = new Index(0,1);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.SOUTH, field);
        expectedIndex = new Index(2,1);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.WEST, field);
        expectedIndex = new Index(1,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.EAST, field);
        expectedIndex = new Index(1,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));

        //Special Directions
        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.NORTHEAST, field);
        expectedIndex = new Index(0,1);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(0,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));

        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.NORTHWEST, field);
        expectedIndex = new Index(0,1);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(0,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));

        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.SOUTHWEST, field);
        expectedIndex = new Index(2,1);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(2,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));

        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.SOUTHEAST, field);
        expectedIndex = new Index(2,1);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(2,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));
    }

    @Test
    public void testGetNextIndicesWhereDirectionsAreNotDirectlyReachable(){
        Button wallButton = new Button("X");
        wallButton.setVisited(false);
        buttons[0][1] = wallButton;
        field.setTwoDimensionalField(buttons);
        List<Index> nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.NORTHEAST, field);
        Index expectedIndex = new Index(1,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(0,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));


        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.NORTHWEST, field);
        expectedIndex = new Index(1,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(0,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));

        init();

        buttons[2][1] = wallButton;
        field.setTwoDimensionalField(buttons);
        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.SOUTHWEST, field);
        expectedIndex = new Index(1,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(2,0);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));

        nextIndices = fieldinfoProvider.getNextIndices(indexToCheck, Direction.SOUTHEAST, field);
        expectedIndex = new Index(1,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(0)));
        expectedIndex = new Index(2,2);
        Assert.assertEquals(true, checkIfIndexIsExpected(expectedIndex, nextIndices.get(1)));
    }

    private boolean checkIfIndexIsExpected(Index expectedIndex, Index indexToCheck){
        return expectedIndex.getRow()==indexToCheck.getRow() && expectedIndex.getColumn()==indexToCheck.getColumn();
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
