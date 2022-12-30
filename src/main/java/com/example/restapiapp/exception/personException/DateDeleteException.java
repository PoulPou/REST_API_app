package com.example.restapiapp.exception.personException;

public class DateDeleteException extends Exception{
    public DateDeleteException() {
        super("The date of dismissal cannot be earlier than the date of employment");
    }
}
