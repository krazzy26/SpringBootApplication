package com.anushree.SpringBoot.exception;

public class CourseCollectionException extends Exception {

    private static final long serialVersionUID = 1L;

    public CourseCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Course with " + id + " not found";
    }

    public static String CourseAlreadyExists() {
        return "Course with given name already exists";
    }

}
