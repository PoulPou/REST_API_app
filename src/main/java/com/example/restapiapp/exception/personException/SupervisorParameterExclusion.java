package com.example.restapiapp.exception.personException;

public class SupervisorParameterExclusion extends Exception{
    public SupervisorParameterExclusion() {
        super("There can't be two bosses in a department.");
    }
}
