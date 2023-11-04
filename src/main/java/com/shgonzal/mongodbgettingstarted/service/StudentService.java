package com.shgonzal.mongodbgettingstarted.service;

import com.shgonzal.mongodbgettingstarted.model.Student;
import com.shgonzal.mongodbgettingstarted.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
}
