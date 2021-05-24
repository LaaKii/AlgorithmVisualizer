package api;

import api.internal.EmptyField;
import api.internal.StartField;
import api.internal.Wall;
import backend.searchAlgorithms.Index;
import org.junit.Test;

public class FieldHandlerTest {

    @Test
    public void test(){
        FieldHandler fieldHandler = new FieldHandler(null);
        FieldHandler.FieldChanger fieldChanger = fieldHandler.new FieldChanger();



        fieldChanger.changeFieldTo(new Index(1,2), new Wall()).changeFieldTo(new Index(2,1), new EmptyField()).commitChanges();
    }
}
