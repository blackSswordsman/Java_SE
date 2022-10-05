
package library;

public class InvalidBookCountException extends IllegalArgumentException{

    public InvalidBookCountException() {
    }

    public InvalidBookCountException(String string) {
        super(string);
    }

    public InvalidBookCountException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public InvalidBookCountException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
