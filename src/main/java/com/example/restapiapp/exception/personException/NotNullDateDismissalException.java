package com.example.restapiapp.exception.personException;

public class NotNullDateDismissalException extends Exception{
    public NotNullDateDismissalException() {
        super("Date of dismissal must be null.");
    }
}
