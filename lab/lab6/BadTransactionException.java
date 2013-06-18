/**
 *  For bad transaction input, such as negative money to deposit
 */

public class BadTransactionException extends Exception{
    public BadTransactionException(String problem) {
        super(problem);
    }
}
