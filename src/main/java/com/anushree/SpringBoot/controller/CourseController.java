package com.anushree.SpringBoot.controller;

import com.anushree.SpringBoot.exception.CourseCollectionException;
import com.anushree.SpringBoot.model.Course;
import com.anushree.SpringBoot.repository.CourseRepository;
import com.anushree.SpringBoot.service.CourseService;
import com.anushree.SpringBoot.service.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return  new ResponseEntity<List<Course>>(courses, courses.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            courseService.createCourse(course);
            return new ResponseEntity<Course>(course, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (CourseCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getSingleCourse(@PathVariable("id") String id) {
       try {
           return new ResponseEntity<>(courseService.getSingleCourse(id), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourseByID(@PathVariable("id") String id, @RequestBody Course course) {
      try {
          courseService.updateCourse(id, course);
          return new ResponseEntity<>("Updated Course with id "+id, HttpStatus.OK);
      } catch (ConstraintViolationException e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
      } catch (CourseCollectionException e) {
          return new ResponseEntity<> (e.getMessage(), HttpStatus.NOT_FOUND);
      }
    }


    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            courseService.deleteCourseById(id);
            return new ResponseEntity<>("Successfully Deleted course with id " + id, HttpStatus.OK);
        } catch (CourseCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}

