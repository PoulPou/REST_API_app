package com.example.restapiapp.exception.personException;

public class IncorrectPhoneNumberException extends Exception{
    public IncorrectPhoneNumberException() {
        super("The phone number can only contain the characters \"+-0123456789()\"." +
                "\nThe phone number must be filled in using one of four forms:" +
                "\n1234567890" +
                "\n123-456-7890" +
                "\n(123)456-7890" +
                "\n(123)4567890)");
    }
}
