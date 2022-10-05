
package library;
 
public class InvalidBookPriceException extends IllegalArgumentException {

    public InvalidBookPriceException() {
    }

    public InvalidBookPriceException(String string) {
        super(string);
    }

    public InvalidBookPriceException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public InvalidBookPriceException(Throwable thrwbl) {
        super(thrwbl);
    }
   
    
}
