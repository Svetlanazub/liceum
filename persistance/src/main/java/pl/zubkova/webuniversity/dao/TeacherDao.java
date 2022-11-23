package pl.zubkova.webuniversity.dao;

import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;
import java.util.Optional;

/**
 * @author Svetlana_Zubkova
 */
public interface TeacherDao {
    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(int id);

    List<Teacher> findAllTeachers();
    public Optional<Teacher> findById(int teacherId);
    Optional<Teacher> findByNameAndSurname(String name, String surname);

    List<Student> findAllStudentsForTheTeacher(int teacherId);

    void addStudentToTeacher(int studentId, int teacherId);

    void deleteStudentFromTeacher(int studentId, int teacherId);
    List<Teacher>getTeachersByPage(int pageId,int total);
}
