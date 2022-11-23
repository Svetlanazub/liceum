package pl.zubkova.webuniversity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.TeacherService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
@RestController
@Validated
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @PostMapping("/addTeacher")
    public ResponseEntity<Teacher> createTeacher(
            @Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok().body(teacherService.createTeacher(teacher));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok().body(teacherService.getTeachers());
    }

    @GetMapping("/teacher")
    public ResponseEntity<Teacher> getTeacherByNameAndSurname(@RequestParam("name") String name,
                                                              @RequestParam("surname") String surname) {
        return ResponseEntity.ok().body(teacherService.getTeacherByNameAndSurname(name, surname));
    }

    @GetMapping("/students/{teacherId}")
    public ResponseEntity<List<Student>> getAllStudentsForTheTeacher(
            @PathVariable("teacherId") @Min(1) int teacherId) {
        return ResponseEntity.ok().body(teacherService.getAllStudentsOfTheTeacher(teacherId));
    }

    @PutMapping
    public ResponseEntity<Teacher> updateStudent(
            @Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok().body(teacherService.updateTeacher(teacher));
    }
    @GetMapping("/page/{pageId}")
    public ResponseEntity<List<Teacher>> getAllTeachersByPage(@PathVariable ("pageId")  @Min(1) int pageId) {
        int total = 5;
        if (pageId != 1) {
            pageId = (pageId - 1) * total + 1;
        }
        System.out.println(pageId);
        return ResponseEntity.ok().body(teacherService.getTeachersByPage(pageId, total));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(
            @PathVariable("id") @Min(1) int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addStudent")
    public ResponseEntity addTeacherToTheStudent(
            @RequestParam("studentId") int studentId,
            @RequestParam("teacherId") int teacherId) {
        teacherService.addStudentToTheTeacher(studentId, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity deleteTeacherFromStudent(@RequestParam("studentId") int studentId,
                                                   @RequestParam("teacherId") int teacherId) {
        teacherService.deleteStudentFromTheTeacher(studentId, teacherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }
}
