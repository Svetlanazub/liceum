package pl.zubkova.webuniversity.dao;

import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;
import java.util.Optional;

/**
 * @author Svetlana_Zubkova
 */
public interface TeacherDao {
    void save(Teacher teacher);

    void update(Teacher teacher);

    void delete(int id);

    List<Teacher> findAllTeachers();
    public Optional<Teacher> findById(int teacherId);
    Optional<Teacher> findByNameAndSurname(String name, String surname);

    List<Student> findAllStudentsForTheTeacher(int teacherId);

    void addStudentToTeacher(int studentId, int teacherId);

    void deleteStudentFromTeacher(int studentId, int teacherId);
}
