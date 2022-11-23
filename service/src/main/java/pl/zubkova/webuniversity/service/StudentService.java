package pl.zubkova.webuniversity.service;

import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
public interface StudentService {
    Student createStudent (Student student);

    Student updateStudent (Student student);

    void deleteStudent (int studentId);

    Student getStudentByNameAndSurname (String name, String surname);

    List<Teacher> getAllTeachersOfTheStudent (int studentId);

    List<Student> getStudents();

    void addTeacherToTheStudent (int teacherId, int studentId);

    void deleteTeacherFromTheStudent (int teacherId, int studentId);
    List<Student> getStudentsByPage(int pageid, int total);
}
