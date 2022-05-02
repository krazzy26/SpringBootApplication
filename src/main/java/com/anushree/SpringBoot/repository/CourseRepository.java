package com.anushree.SpringBoot.repository;

import com.anushree.SpringBoot.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    @Query("{course:?0}")
    Optional<Course> findByCourse(String course);
}
