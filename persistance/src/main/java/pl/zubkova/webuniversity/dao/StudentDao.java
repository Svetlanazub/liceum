package pl.zubkova.webuniversity.dao;

import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;
import java.util.Optional;
//Wymagania:
//1. Obie klasy można tworzyć, usuwać i edytować. Dane powinny być walidowane: poprawny email, imię dłuższe od dwóch liter, wiek > 18.
//2. Powinna być możliwość przypisania nauczyciela do studenta oraz usunięcie nauczyciela studentowi jak i na odwrót.
//3. Powinna być możliwość wyświetlenia wszystkich studentów oraz wszystkich nauczycieli (dwa endpointy, możliwość stronicowania i sortowania).
//4. Dane można filtrować: wyszukać wszystkich studentów danego nauczyciela i odwrotnie.
//5. Studentów oraz nauczycieli można wyszukiwać po imieniu i nazwisku.

/**
 * @author Svetlana_Zubkova
 */
public interface StudentDao {
    void save(Student student);
    void update(Student student);
    void delete(int id);
    List<Student> findAllStudents();
    Optional<Student> findByNameAndSurname(String name, String surname);
    List<Teacher> findAllTeachersForTheStudent(int studentId);
    void addTeacherToStudent(int teacherId, int studentId);

    void deleteTeacherFromStudent(int teacherId, int studentId);
    Optional<Student> findById(int studentId);
}
