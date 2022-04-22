package ftn.uns.ac.rs.masterrad.exceptions;

public class InvalidCredentialsException extends Throwable {

    @Override
    public String getMessage() {
        return "Invalid credentials. Try again.";
    }
}
