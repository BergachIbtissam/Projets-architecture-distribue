package fr.univ.tp.studentapi;

import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll(); // récupère les données depuis MySQL
    }
}
