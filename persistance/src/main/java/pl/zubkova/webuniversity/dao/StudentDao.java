package pl.zubkova.webuniversity.dao;

import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;
import java.util.Optional;

/**
 * @author Svetlana_Zubkova
 */
public interface StudentDao {
    Student save(Student student);
    Student update(Student student);
    void delete(int id);
    List<Student> findAllStudents();
    Optional<Student> findByNameAndSurname(String name, String surname);
    List<Teacher> findAllTeachersForTheStudent(int studentId);
    void addTeacherToStudent(int teacherId, int studentId);

    void deleteTeacherFromStudent(int teacherId, int studentId);
    Optional<Student> findById(int studentId);
    List<Student>getStudentsByPage(int pageId,int total);
}
