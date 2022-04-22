package ftn.uns.ac.rs.masterrad.exceptions;

public class NoSuchApplicationException extends Throwable {

    @Override
    public String getMessage() {
        return "Application with supplied Client ID and Client secret doesn't exist. Try again.";
    }
}
