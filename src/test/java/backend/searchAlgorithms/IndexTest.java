package backend.searchAlgorithms;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IndexTest {

    private Index index;

    @Before
    public void setup(){
        index = new Index(4,5);
    }

    @Test
    public void testIsSameField(){
        Index indexToCheck = new Index();
        indexToCheck.setRow(4);
        indexToCheck.setColumn(5);
        boolean isSameField = index.isSameField(indexToCheck);
        assertTrue(isSameField);
    }

    @Test
    public void negativTestIsSameField(){
        Index indexToCheck = new Index();
        indexToCheck.setRow(5);
        indexToCheck.setColumn(5);
        assertFalse(index.isSameField(indexToCheck));
    }

}
