package com.shgonzal.mongodbgettingstarted.repository;

import com.shgonzal.mongodbgettingstarted.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {

	Optional<Student> findStudentByEmail(String email);
}
