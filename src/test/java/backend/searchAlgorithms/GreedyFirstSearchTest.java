package backend.searchAlgorithms;

import frontend.Button;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreedyFirstSearchTest {

    private Button[][] buttons;
    private Button emptyField;
    private Button goal;
    private GreedyFirstSearch gfs = new GreedyFirstSearch();
    private SearchField field = new SearchField();

    @Before
    public void init(){
        buttons = new Button[4][4];
        emptyField = new Button("0");
        emptyField.setVisited(false);
        goal = new Button("Z");
        goal.setVisited(false);
        buttons[0][0] = emptyField;
        buttons[0][1] = emptyField;
        buttons[0][2] = emptyField;
        buttons[0][3] = emptyField;
        buttons[1][0] = emptyField;
        buttons[1][1] = emptyField;
        buttons[1][2] = emptyField;
        buttons[1][3] = emptyField;
        buttons[2][0] = emptyField;
        buttons[2][1] = goal;
        buttons[2][2] = emptyField;
        buttons[2][3] = emptyField;
        buttons[3][0] = emptyField;
        buttons[3][1] = emptyField;
        buttons[3][2] = emptyField;
        buttons[3][3] = emptyField;
    }

    @Test
    public void testHeuristic(){
        Index index1 = new Index(1,2);
        Index index2 = new Index(3,1);

        Assert.assertEquals(3, gfs.heuristic(index1, index2));
    }

    @Test
    public void testDoSearchTargetAboveWhereTargetIsNotReachable(){
        Index startField = new Index(3,1);
        field.setStartField(startField);

        //Goal above of start
        setAllButtonsUnvisited();
        buttons[2][1] = goal;
        Index endField = new Index(1,1);
        field.setEndField(endField);
        field.setButtons(buttons);
        Assert.assertTrue(!gfs.doSearch(field));
    }

    private void setAllButtonsUnvisited(){
        for(int i = 0; i<buttons.length-1; i++) {
            for (int j = 0; j < buttons[i].length-1; j++) {
                buttons[i][j].setVisited(false);
            }
        }
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
