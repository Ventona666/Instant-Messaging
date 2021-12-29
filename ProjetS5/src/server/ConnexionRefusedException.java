package server;

public class ConnexionRefusedException extends Exception{
    public ConnexionRefusedException(String errorMessage){
        super(errorMessage);
    }

    public ConnexionRefusedException(Throwable cause){
        super(cause);
    }

    public ConnexionRefusedException(String errorMessage, Throwable cause){
        super(errorMessage,cause);
    }
}
