package com.example.restapiapp.exception.personException;

public class NameException extends Exception{
    public NameException() {
        super("Name, surname or patronymic contain incorrect characters");
    }
}
