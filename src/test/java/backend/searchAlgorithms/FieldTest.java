package backend.searchAlgorithms;

import frontend.Button;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {


    @Test
    public void testSetAndGetTwoDimensionalField(){
        Field field = new Field();
        Button[][] buttons = new Button[4][4];

        field.setTwoDimensionalField(buttons);
        Assert.assertEquals(4, field.getTwoDimensionalField().length);
        Assert.assertEquals(4, field.getTwoDimensionalField()[1].length);
    }

    @Test
    public void testSetAndGetThreeDimensionalField(){
        Field field = new Field();
        Button[][][] buttons = new Button[4][4][4];

        field.setThreeDimensionalField(buttons);
        Assert.assertEquals(4, field.getThreeDimensionalField().length);
        Assert.assertEquals(4, field.getThreeDimensionalField()[1].length);
        Assert.assertEquals(4, field.getThreeDimensionalField()[1][1].length);
    }

}
