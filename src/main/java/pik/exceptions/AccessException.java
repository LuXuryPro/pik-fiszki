package pik.exceptions;


public class AccessException extends Exception {
    private String message;

    AccessException() {}

    AccessException(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

