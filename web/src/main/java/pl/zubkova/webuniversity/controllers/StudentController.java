package pl.zubkova.webuniversity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zubkova.webuniversity.dto.SortingField;
import pl.zubkova.webuniversity.dto.SortingOrder;
import pl.zubkova.webuniversity.dto.SortingDto;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Provides operations having to do with {@link pl.zubkova.webuniversity.entity.Student}
 *
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

    /**
     * Allows creating {@link pl.zubkova.webuniversity.entity.Student}.
     *
     * @return ResponseEntity which contains created student with generated id. Response code 200.
     */
    @PostMapping("/addStudent")
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.createStudent(student));
    }

    /**
     * Allows to get all existing students.
     *
     * @return Response entity containing the list of students. Response code 200.
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    /**
     * Allows a client to get list of students sorted and with appropriate amount of students on the page.
     * All params for sorting are optional and can be used in conjunction:
     * <ul>
     *  <li>sort by name/surname/specialisation/li>
     *  <li> in ASC/DESC order</li>
     * </ul>
     *
     * @param sortingField   can be either name, surname or specialisation.
     * @param sortingOrder   either ascending or descending.
     * @param pageSize       number of students on the page
     * @param pageId         page number
     * @return Response entity containing the list of students. Response code 200.
     */
    @GetMapping("/page")
    public ResponseEntity<List<Student>> getAllSortedStudentsByPage(@RequestParam(value = "pageId") int pageId,
                                                                    @RequestParam(value = "pageSize") int pageSize,
                                                                    @RequestParam(required = false) SortingField sortingField,
                                                                    @RequestParam(required = false) SortingOrder sortingOrder) {
        SortingDto sortingDto = SortingDto.builder()
                .sortingField(sortingField)
                .sortingOrder(sortingOrder)
                .build();
        return ResponseEntity.ok().body(studentService.getSortedStudentsByPage(pageId, pageSize, sortingDto));
    }

    /**
     * Provides a student having passed his name and surname. If it's absent error will be returned(404).
     *
     * @param name    name of searching student.
     * @param surname surname of searching student.
     * @return {@link Student}. Response code 200.
     */
    @GetMapping("/student")
    public ResponseEntity<Student> getStudentByNameAndSurname(@RequestParam("name") String name,
                                                              @RequestParam("surname") String surname) {
        return ResponseEntity.ok().body(studentService.getStudentByNameAndSurname(name, surname));
    }

    /**
     * Allows to get all existing teachers of the student. Student id should be passed.
     *
     * @param studentId should be positive integer number.
     * @return Response entity containing the list of teachers. Response code 200.
     */
    @GetMapping("/teachers/{studentId}")
    public ResponseEntity<List<Teacher>> getAllTeachersForTheStudent(
            @PathVariable("studentId") @Min(1) int studentId) {
        return ResponseEntity.ok().body(studentService.getAllTeachersOfTheStudent(studentId));
    }

    /**
     * Provides ability to update student entirely.
     *
     * @param student should have all fields filled and valid. Otherwise, error will be returned(400).
     * @return updated student. Response code 200.
     */
    @PutMapping
    public ResponseEntity<Student> updateStudent(
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudent(student));
    }

    /**
     * Allows student deleting. Student id should be passed.
     *
     * @param studentId should be positive integer number.
     * @return responseEntity having empty body. Response code 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(
            @PathVariable("id") @Min(1) int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Allows teacher adding to the student. Teacher and student ids should be passed.
     *
     * @param studentId should be positive integer number.
     * @param teacherId should be positive integer number.
     * @return responseEntity having empty body. Response code 200.
     */
    @PostMapping("/addTeacher")
    public ResponseEntity addTeacherToTheStudent(
            @RequestParam("teacherId") int teacherId,
            @RequestParam("studentId") int studentId) {
        studentService.addTeacherToTheStudent(teacherId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Allows teacher deleting from the student. Teacher and student ids should be passed.
     *
     * @param studentId should be positive integer number.
     * @param teacherId should be positive integer number.
     * @return responseEntity having empty body. Response code 204.
     */
    @DeleteMapping("/deleteTeacher")
    public ResponseEntity deleteTeacherFromStudent(@RequestParam("teacherId") int teacherId,
                                                   @RequestParam("studentId") int studentId) {
        studentService.deleteTeacherFromTheStudent(teacherId, studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }
}
