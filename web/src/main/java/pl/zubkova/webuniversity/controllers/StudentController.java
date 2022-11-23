package pl.zubkova.webuniversity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
@RestController
@Validated
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.createStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    @GetMapping("/page/{pageId}")
    public ResponseEntity<List<Student>> getAllStudentsByPage(@PathVariable ("pageId")  @Min(1) int pageId) {
        int total = 5;
        if (pageId != 1) {
            pageId = (pageId - 1) * total + 1;
        }
        System.out.println(pageId);
        return ResponseEntity.ok().body(studentService.getStudentsByPage(pageId, total));
    }

    @GetMapping("/student")
    public ResponseEntity<Student> getStudentByNameAndSurname(@RequestParam("name") String name,
                                                              @RequestParam("surname") String surname) {
        return ResponseEntity.ok().body(studentService.getStudentByNameAndSurname(name, surname));
    }

    @GetMapping("/teachers/{studentId}")
    public ResponseEntity<List<Teacher>> getAllTeachersForTheStudent(
            @PathVariable("studentId") @Min(1) int studentId) {
        return ResponseEntity.ok().body(studentService.getAllTeachersOfTheStudent(studentId));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudent(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(
            @PathVariable("id") @Min(1) int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addTeacher")
    public ResponseEntity addTeacherToTheStudent(
            @RequestParam("teacherId") int teacherId,
            @RequestParam("studentId") int studentId) {
        studentService.addTeacherToTheStudent(teacherId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/deleteTeacher")
    public ResponseEntity deleteTeacherFromStudent(@RequestParam("teacherId") int teacherId,
                                                   @RequestParam("studentId") int studentId) {
        studentService.deleteTeacherFromTheStudent(teacherId, studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }
}
