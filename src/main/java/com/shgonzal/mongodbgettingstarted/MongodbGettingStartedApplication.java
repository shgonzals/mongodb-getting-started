package com.shgonzal.mongodbgettingstarted;

import com.shgonzal.mongodbgettingstarted.model.Address;
import com.shgonzal.mongodbgettingstarted.model.Gender;
import com.shgonzal.mongodbgettingstarted.model.Student;
import com.shgonzal.mongodbgettingstarted.repository.StudentRepository;
import org.apache.tomcat.util.buf.StringCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class MongodbGettingStartedApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbGettingStartedApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (StudentRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address(
					"Spain",
					"46001",
					"Valencia"
			);
			String email = "sheila3@test.es";
			Student student = new Student(
					"Sheila",
					"Gonzalez",
					email,
					Gender.FEMALE,
					address,
					LocalDateTime.now(),
					List.of("INF","SPA"),
					BigDecimal.TEN
			);

			repository.findStudentByEmail(email).ifPresentOrElse(
					s -> {
						System.out.println(s + " already exists");
					}, () -> {
						System.out.println("Inserting student " + student);
						repository.insert(student);
					}
			);

			//usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students =  mongoTemplate.find(query, Student.class);

		if(students.size() > 1){
			throw new IllegalStateException("found many students with email: " + email);
		}

		if(students.isEmpty()){
			System.out.println("Inserting student " + student);
			repository.insert(student);
		}else{
			System.out.println(student + " already exists");
		}
	}
}
