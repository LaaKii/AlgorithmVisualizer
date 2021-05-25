package backend.searchAlgorithms;

import frontend.Button;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {


    @Test
    public void testSetAndGetTwoDimensionalField(){
        Button[][] buttons = new Button[4][4];
        Field field = new Field(buttons);

        Assert.assertEquals(4, field.getTwoDimensionalField().length);
        Assert.assertEquals(4, field.getTwoDimensionalField()[1].length);
    }

    @Test
    public void testSetAndGetThreeDimensionalField(){
        Button[][][] buttons = new Button[4][4][4];
        Field field = new Field(buttons);
        Assert.assertEquals(4, field.getThreeDimensionalField().length);
        Assert.assertEquals(4, field.getThreeDimensionalField()[1].length);
        Assert.assertEquals(4, field.getThreeDimensionalField()[1][1].length);
    }

}
