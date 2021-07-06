package api.internal;


public class IndexIsNotInFieldException extends RuntimeException{
    public IndexIsNotInFieldException(String message){
        super(message);
    }
}
