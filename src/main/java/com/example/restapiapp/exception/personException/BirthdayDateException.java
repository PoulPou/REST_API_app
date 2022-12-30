package com.example.restapiapp.exception.personException;

public class BirthdayDateException extends Exception{
    public BirthdayDateException() {
        super("The birthday cannot be earlier than the date of employment or earlier than 01-01-1960.");
    }
}
