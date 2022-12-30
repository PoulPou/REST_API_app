package com.example.restapiapp.exception.personException;

public class IncorrectSalaryException extends Exception{
    public IncorrectSalaryException() {
        super ("The salary may not exceed the salary of the boss or be more than 500,000 rubles.");
    }
}
