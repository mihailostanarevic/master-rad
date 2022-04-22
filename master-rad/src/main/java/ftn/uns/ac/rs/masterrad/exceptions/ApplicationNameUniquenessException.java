package ftn.uns.ac.rs.masterrad.exceptions;

public class ApplicationNameUniquenessException extends Throwable {

    private final String appName;

    public ApplicationNameUniquenessException(final String appName) {
        this.appName = appName;
    }

    @Override
    public String getMessage() {
        return String.format("A application with the name %s already exists. Try again with another name.", appName);
    }

}
