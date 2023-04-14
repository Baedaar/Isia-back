package fr.eql.ai113.isia_back.service.impl.exception;

public class EmployeNotFoundException extends Exception {
    public EmployeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeNotFoundException(String message) {
        super(message);
    }
}
