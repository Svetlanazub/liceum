package pl.zubkova.webuniversity.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zubkova.webuniversity.dao.StudentDao;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Svetlana_Zubkova
 */
public class StudentServiceImplTests {
    private StudentDao studentDao;
    private StudentService studentService;
    private final Student student;

    public StudentServiceImplTests() {
        student = new Student();
        student.setId(1);
        student.setName("Antoni");
        student.setSurname("Kowlowsky");
        student.setAge(20);
        student.setEmail("antoni_kozlowski@gmail.com");
        student.setSpecialisation("Geometry");
    }


    @BeforeEach
    public void setUp() {
        studentDao = mock(StudentDao.class);
        studentService = new StudentServiceImpl(studentDao);
    }
    @Test
    void should_invoke_studentDao_save_when_createStudent() {
        studentService.createStudent(student);
        verify(studentDao).save(any());
    }
    @Test
    void should_invoke_studentDao_update_when_updateStudent() {
        when(studentDao.findById(student.getId())).thenReturn(Optional.of(student));
        studentService.updateStudent(student);
        verify(studentDao).update(any());
    }
    @Test
    void should_invoke_studentDao_delete_when_deleteStudent() {
        when(studentDao.findById(1)).thenReturn(Optional.of(student));
        studentService.deleteStudent(1);
        verify(studentDao).delete(1);
    }
    @Test
    void should_invoke_studentDao_addTeacherToTheStudent() {
        studentService.addTeacherToTheStudent(1, 2);
        verify(studentDao).addTeacherToStudent(1, 2);
    }
    @Test
    void should_throw_RuntimeException_when_student_not_found_when_delete() {
        assertThrows(RuntimeException.class, () -> studentService.deleteStudent(5));
    }

    @Test
    void should_throw_RuntimeException_when_student_not_found() {
        Assertions.assertThrows(RuntimeException.class, () -> studentService.getStudentByNameAndSurname("Ana", "Wock"));
    }

}