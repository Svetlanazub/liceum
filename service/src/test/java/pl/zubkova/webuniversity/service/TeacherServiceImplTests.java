package pl.zubkova.webuniversity.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zubkova.webuniversity.dao.TeacherDao;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.impl.TeacherServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Svetlana_Zubkova
 */
public class TeacherServiceImplTests {
    private TeacherDao teacherDao;
    private TeacherService teacherService;
    private final Teacher teacher;

    public TeacherServiceImplTests() {
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Wojtek");
        teacher.setSurname("Pawlowsky");
        teacher.setAge(43);
        teacher.setEmail("wojtek_pawlowski@gmail.com");
        teacher.setSubject("History");
    }


    @BeforeEach
    public void setUp() {
        teacherDao = mock(TeacherDao.class);
        teacherService = new TeacherServiceImpl(teacherDao);
    }
    @Test
    void should_invoke_teacherDao_save_when_createTeacher() {
        teacherService.createTeacher(teacher);
        verify(teacherDao).save(any());
    }
    @Test
    void should_invoke_teacherDao_update_when_updateTeacher() {
        when(teacherDao.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        teacherService.updateTeacher(teacher);
        verify(teacherDao).update(any());
    }
    @Test
    void should_invoke_teacherDao_delete_when_deleteTeacher() {
        when(teacherDao.findById(1)).thenReturn(Optional.of(teacher));
        teacherService.deleteTeacher(1);
        verify(teacherDao).delete(1);
    }

    @Test
    void should_throw_RuntimeException_when_teacher_not_found_when_delete() {
        assertThrows(RuntimeException.class, () -> teacherService.deleteTeacher(5));
    }

    @Test
    void should_throw_RuntimeException_when_teacher_not_found() {
        Assertions.assertThrows(RuntimeException.class, () -> teacherService.getTeacherByNameAndSurname("Adam", "Wilk"));
    }

    @Test
    void should_invoke_teacherDao_addStudentToTheTeacher() {
        teacherService.addStudentToTheTeacher(1, 2);
        verify(teacherDao).addStudentToTeacher(1, 2);
    }

}
