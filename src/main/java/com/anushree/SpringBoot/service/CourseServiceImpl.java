package com.anushree.SpringBoot.service;

import com.anushree.SpringBoot.exception.CourseCollectionException;
import com.anushree.SpringBoot.model.Course;
import com.anushree.SpringBoot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public void createCourse(Course course) throws ConstraintViolationException, CourseCollectionException {
        Optional<Course> courseOptional = courseRepo.findByCourse(course.getCourse());
        if(courseOptional.isPresent()) {
            throw new CourseCollectionException(CourseCollectionException.CourseAlreadyExists());
        } else {
            course.setUpdatedAt(new Date(System.currentTimeMillis()));
            courseRepo.save(course);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        if(courses.size() > 0) {
            return courses;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Course getSingleCourse(String id) throws CourseCollectionException {
        Optional<Course> optionalCourse = courseRepo.findById(id);
        if(!optionalCourse.isPresent()) {
            throw new CourseCollectionException(CourseCollectionException.NotFoundException(id));
        } else {
            return optionalCourse.get();
        }
    }

    @Override
    public void updateCourse(String id, Course course) throws CourseCollectionException {
        Optional<Course> courseOptional = courseRepo.findById(id);
        Optional<Course> courseWithSameName = courseRepo.findByCourse(course.getCourse());

        if(courseOptional.isPresent()) {

            if(courseWithSameName.isPresent() && !courseWithSameName.get().getId().equals(id)) {
                throw new CourseCollectionException(CourseCollectionException.CourseAlreadyExists());
            }
            Course courseToUpdate = courseOptional.get();
            courseToUpdate.setCourse(course.getCourse());
            courseToUpdate.setDays(course.getDays());
            courseToUpdate.setInstructor(course.getInstructor());
            courseToUpdate.setCompleted(course.getCompleted());
            courseToUpdate.setUpdatedAt(new Date (System.currentTimeMillis()));
            courseRepo.save(courseToUpdate);
        } else {
            throw new CourseCollectionException(CourseCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteCourseById(String id) throws CourseCollectionException {
        Optional<Course> courseOptional = courseRepo.findById(id);
        if(!courseOptional.isPresent()) {
            throw new CourseCollectionException(CourseCollectionException.NotFoundException(id));
        } else {
            courseRepo.deleteById(id);
        }
    }
}
