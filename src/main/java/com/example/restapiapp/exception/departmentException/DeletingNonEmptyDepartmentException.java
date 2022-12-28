package com.example.restapiapp.exception.departmentException;

public class DeletingNonEmptyDepartmentException extends Exception{
    public DeletingNonEmptyDepartmentException() {
        super("it is not possible to delete a non-empty department");
    }

}
