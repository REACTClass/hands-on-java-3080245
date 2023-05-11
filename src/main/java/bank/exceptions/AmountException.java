package bank.exceptions;

public class AmountException extends Exception { //Inherit from Java's built-in exceptions
  
  public AmountException(String message) {
    super(message);
  }
}
