package com.anushree.SpringBoot.service;

import com.anushree.SpringBoot.exception.CourseCollectionException;
import com.anushree.SpringBoot.model.Course;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface CourseService {
    public void createCourse(Course course) throws ConstraintViolationException, CourseCollectionException;
    public List<Course> getAllCourses();
    public Course getSingleCourse(String id) throws CourseCollectionException;
    public void updateCourse(String id, Course course) throws CourseCollectionException;
    public void deleteCourseById(String id) throws CourseCollectionException;
}
