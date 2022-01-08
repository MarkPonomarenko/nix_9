package ua.com.alevel.persistence.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.Student;

@Repository
public interface StudentRepository extends AbstractRepository<Student> {

}