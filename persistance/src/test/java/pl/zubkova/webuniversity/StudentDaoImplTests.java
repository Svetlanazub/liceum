package pl.zubkova.webuniversity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import pl.zubkova.webuniversity.config.ApplicationConfig;
import pl.zubkova.webuniversity.dao.impl.StudentDaoImpl;
import pl.zubkova.webuniversity.dao.impl.TeacherDaoImpl;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;
import java.util.Optional;

/**
 * @author Svetlana_Zubkova
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@ComponentScan("pl.zubkova.webuniversity")
public class StudentDaoImplTests {
    @Autowired
    private EmbeddedDatabase dataSource;
    @Autowired
    private NamedParameterJdbcTemplate template1;
    @Autowired
    @Qualifier("Students")
    private SimpleJdbcInsert template2;
    @Autowired
    @Qualifier("Teachers")
    private SimpleJdbcInsert simpleJdbcInsert;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    public void row_set_should_not_be_empty_after_adding_teacher() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        Teacher teacher = getTeacher("Jadwiga", "Marek", 48, "jad_marek@gmail.com", "Math");

        //when
        studentDao.setSimpleJdbcInsert(template2);
        teacherDao.setSimpleJdbcInsert(simpleJdbcInsert);
        teacherDao.save(teacher);
        int teacherId = teacher.getId();

        studentDao.setJdbcTemplate(jdbcTemplate);
        int listTeachersSizeBeforeAdding = studentDao.findAllTeachersForTheStudent(3).size();
        studentDao.addTeacherToStudent(teacherId, 3);

        //then
        Assertions.assertEquals("Jadwiga",studentDao.findAllTeachersForTheStudent(3).get(listTeachersSizeBeforeAdding).getName());
    }

    @Test
    public void row_set_should_be_empty_after_deleting_teacher() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        Teacher teacher = getTeacher("Jadwiga", "Marek", 48, "jad_marek@gmail.com", "Math");
        Student student = getStudent("Ola", "Small", 18, "ola_small@gmail.com", "Chemistry");
        //when
        studentDao.setSimpleJdbcInsert(template2);
        teacherDao.setSimpleJdbcInsert(simpleJdbcInsert);
        studentDao.save(student);
        int studentId = student.getId();
        teacherDao.save(teacher);
        int teacherId = teacher.getId();

        studentDao.setJdbcTemplate(jdbcTemplate);
        studentDao.addTeacherToStudent(teacherId, studentId);
        studentDao.deleteTeacherFromStudent(teacherId, studentId);

        //then
        Assertions.assertTrue(studentDao.findAllTeachersForTheStudent(studentId).isEmpty());
    }
    @Test
    public void should_id_not_be_null_when_save() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();
        Student student = getStudent("Ivan", "Kowalsky", 21,
                "ivan_kowalsky@gmail.com", "Physics");

        //when
        studentDao.setSimpleJdbcInsert(template2);
        studentDao.save(student);

        //then
        Assertions.assertNotNull(student.getId());
    }


    @Test
    public void should_return_corresponding_list_all_students_size() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();

        //when
        studentDao.setJdbcTemplate(jdbcTemplate);
        List<Student> allStudents = studentDao.findAllStudents();
        int actualAmount = allStudents.size();
        long checkedAmount = allStudents.stream()
                .count();

//        //then
        Assertions.assertEquals(checkedAmount, actualAmount);
        Assertions.assertTrue(allStudents.size() > 0);
    }

    @Test
    public void should_find_student_by_name_and_surname() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();

        //when
        studentDao.setNamedParameterJdbcTemplate(template1);
        Optional<Student> studentFound = studentDao.findByNameAndSurname("Anna", "Lis");


        //then
        Assertions.assertNotNull(studentFound);
        Assertions.assertEquals("Anna", studentFound.get().getName());
        Assertions.assertEquals("Lis", studentFound.get().getSurname());
    }

    @Test
    public void should_be_updated_email_after_update() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();
        Student student = getStudent("Mary", "Brown", 19, "mary_brown@gmail.com", "Medicine");
        Student updatedStudent = getStudent("Mary", "Brown", 19, "Mary_Brown22@gmail.com", "Medicine");

        //when
        studentDao.setSimpleJdbcInsert(template2);
        studentDao.save(student);
        int id = student.getId();
        updatedStudent.setId(id);
        studentDao.setNamedParameterJdbcTemplate(template1);
        studentDao.update(updatedStudent);

        //then
        Assertions.assertEquals(updatedStudent.getEmail(), studentDao.findById(id).get().getEmail());
    }

    @Test
    public void should_find_all_teachers_for_student() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();

        //when
        studentDao.setJdbcTemplate(jdbcTemplate);
        List<Teacher> allTeachersForTheStudent = studentDao.findAllTeachersForTheStudent(3);

        //then
        Assertions.assertNotNull(allTeachersForTheStudent);
        Assertions.assertTrue(allTeachersForTheStudent.size() > 0);

    }



    private static Student getStudent(String name, String surname, int age, String email, String specialization) {
        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        student.setAge(age);
        student.setEmail(email);
        student.setSpecialisation(specialization);
        return student;
    }

    private static Teacher getTeacher(String name, String surname, int age, String email, String subject) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setAge(age);
        teacher.setEmail(email);
        teacher.setSubject(subject);
        return teacher;
    }
}
