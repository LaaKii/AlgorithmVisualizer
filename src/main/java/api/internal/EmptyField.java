package api.internal;

public class EmptyField implements FieldType{
    @Override
    public String getText() {
        return "0";
    }
}
