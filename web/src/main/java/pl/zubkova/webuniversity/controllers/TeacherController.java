package pl.zubkova.webuniversity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zubkova.webuniversity.dto.SortingDto;
import pl.zubkova.webuniversity.dto.SortingField;
import pl.zubkova.webuniversity.dto.SortingOrder;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.TeacherService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Provides operations having to do with {@link pl.zubkova.webuniversity.entity.Teacher}
 *
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

    /**
     * Allows creating {@link pl.zubkova.webuniversity.entity.Teacher}.
     *
     * @return ResponseEntity which contains created teacher with generated id. Response code 200.
     */
    @PostMapping("/addTeacher")
    public ResponseEntity<Teacher> createTeacher(
            @Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok().body(teacherService.createTeacher(teacher));
    }

    /**
     * Allows to get all existing teachers.
     *
     * @return Response entity containing the list of teachers. Response code 200.
     */
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok().body(teacherService.getTeachers());
    }

    /**
     * Provides a teacher having passed his name and surname. If it's absent error will be returned(404).
     *
     * @param name    name of searching teacher.
     * @param surname surname of searching teacher.
     * @return {@link Teacher}. Response code 200.
     */
    @GetMapping("/teacher")
    public ResponseEntity<Teacher> getTeacherByNameAndSurname(@RequestParam("name") String name,
                                                              @RequestParam("surname") String surname) {
        return ResponseEntity.ok().body(teacherService.getTeacherByNameAndSurname(name, surname));
    }

    /**
     * Allows to get all existing students of the teacher. Teacher id should be passed.
     *
     * @param teacherId should be positive integer number.
     * @return Response entity containing the list of students. Response code 200.
     */
    @GetMapping("/students/{teacherId}")
    public ResponseEntity<List<Student>> getAllStudentsForTheTeacher(
            @PathVariable("teacherId") @Min(1) int teacherId) {
        return ResponseEntity.ok().body(teacherService.getAllStudentsOfTheTeacher(teacherId));
    }

    /**
     * Provides ability to update teacher entirely.
     *
     * @param teacher should have all fields filled and valid. Otherwise error will be returned(400).
     * @return updated teacher. Response code 200.
     */
    @PutMapping
    public ResponseEntity<Teacher> updateStudent(
            @Valid @RequestBody Teacher teacher) {
        return ResponseEntity.ok().body(teacherService.updateTeacher(teacher));
    }

    /**
     * Allows a client to get list of teachers sorted and with appropriate amount of teachers on the page.
     * All params for sorting are optional and can be used in conjunction:
     * <ul>
     *  <li>sort by name/surname/subject/li>
     *  <li> in ASC/DESC order</li>
     * </ul>
     *
     * @param sortingField can be either name, surname or subject.
     * @param sortingOrder either ascending or descending.
     * @param pageSize     number of teachers on the page
     * @param pageId       page number
     * @return Response entity containing the list of teachers. Response code 200.
     */
    @GetMapping("/page")
    public ResponseEntity<List<Teacher>> getAllSortedStudentsByPage(@RequestParam(value = "pageId") int pageId,
                                                                    @RequestParam(value = "pageSize") int pageSize,
                                                                    @RequestParam(required = false) SortingField sortingField,
                                                                    @RequestParam(required = false) SortingOrder sortingOrder) {
        SortingDto sortingDto = SortingDto.builder()
                .sortingField(sortingField)
                .sortingOrder(sortingOrder)
                .build();
        return ResponseEntity.ok().body(teacherService.getSortedTeachersByPage(pageId, pageSize, sortingDto));
    }

    /**
     * Allows teacher deleting. Teacher id should be passed.
     *
     * @param id should be positive integer number.
     * @return responseEntity having empty body. Response code 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(
            @PathVariable("id") @Min(1) int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Allows student adding to the teacher. Teacher and student ids should be passed.
     *
     * @param studentId should be positive integer number.
     * @param teacherId should be positive integer number.
     * @return responseEntity having empty body. Response code 201.
     */
    @PostMapping("/addStudent")
    public ResponseEntity addTeacherToTheStudent(
            @RequestParam("studentId") @Min(1) int studentId,
            @RequestParam("teacherId") @Min(1) int teacherId) {
        teacherService.addStudentToTheTeacher(studentId, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Allows student deleting from the teacher. Teacher and student ids should be passed.
     *
     * @param studentId should be positive integer number.
     * @param teacherId should be positive integer number.
     * @return responseEntity having empty body. Response code 204.
     */
    @DeleteMapping("/deleteStudent")
    public ResponseEntity deleteTeacherFromStudent(@RequestParam("studentId") @Min(1) int studentId,
                                                   @RequestParam("teacherId") @Min(1) int teacherId) {
        teacherService.deleteStudentFromTheTeacher(studentId, teacherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }
}
