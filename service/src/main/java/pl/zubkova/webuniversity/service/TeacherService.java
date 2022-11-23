package pl.zubkova.webuniversity.service;

import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
public interface TeacherService {
   Teacher createTeacher (Teacher teacher);

    Teacher updateTeacher (Teacher teacher);

    void deleteTeacher (int teacherId);

    Teacher getTeacherByNameAndSurname (String name, String surname);

    List<Student> getAllStudentsOfTheTeacher (int teacherId);

    List<Teacher> getTeachers();

    void addStudentToTheTeacher (int studentId, int teacherId);

    void deleteStudentFromTheTeacher (int studentId, int teacherId);

    List<Teacher> getTeachersByPage(int pageId, int total);
}
