package com.anushree.SpringBoot.controller;

import com.anushree.SpringBoot.model.Course;
import com.anushree.SpringBoot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        List<Course> courseList = courseRepo.findAll();
        if(courseList.size() > 0) {
            return  new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No courses available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            course.setUpdatedAt(new Date(System.currentTimeMillis()));
            courseRepo.save(course);
            return new ResponseEntity<Course>(course, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getSingleCourse(@PathVariable("id") String id) {
        Optional<Course> courseOptional = courseRepo.findById(id);
        if(courseOptional.isPresent()) {
            return new ResponseEntity<>(courseOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourseByID(@PathVariable("id") String id, @RequestBody Course course) {
        Optional<Course> courseOptional = courseRepo.findById(id);
        if(courseOptional.isPresent()) {
            Course courseToSave = courseOptional.get();
            courseToSave.setCourse(course.getCourse()!=null ? course.getCourse() : courseToSave.getCourse());
            courseToSave.setDays(course.getDays()!=null ? course.getDays() : courseToSave.getDays());
            courseToSave.setInstructor(course.getInstructor()!=null ? course.getInstructor() : courseToSave.getInstructor());
            courseToSave.setCompleted(course.getCompleted()!=null ? course.getCompleted() : courseToSave.getCompleted());
            courseToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            courseRepo.save(courseToSave);
            return new ResponseEntity<>(courseToSave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            courseRepo.deleteById(id);
            return new ResponseEntity<>("Successfully Deleted course with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}

