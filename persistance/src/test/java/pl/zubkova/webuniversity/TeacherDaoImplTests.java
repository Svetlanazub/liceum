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
import pl.zubkova.webuniversity.config.ApplicationConfig;
import pl.zubkova.webuniversity.dao.impl.StudentDaoImpl;
import pl.zubkova.webuniversity.dao.impl.TeacherDaoImpl;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Svetlana_Zubkova
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@ComponentScan("pl.zubkova.webuniversity")
class TeacherDaoImplTests {
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
    public void row_set_should_not_be_empty_after_adding_student() {
        //given
        StudentDaoImpl studentDao = new StudentDaoImpl();
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        Student student = getStudent("Jana", "hill", 18, "jana_hill@gmail.com", "Biologyy");

        //when
        studentDao.setSimpleJdbcInsert(template2);
        teacherDao.setSimpleJdbcInsert(simpleJdbcInsert);
        studentDao.save(student);
        int studentId = student.getId();

        teacherDao.setJdbcTemplate(jdbcTemplate);
        int listStudentsSizeBeforeAdding = teacherDao.findAllStudentsForTheTeacher(3).size();
        teacherDao.addStudentToTeacher(studentId, 3);

        //then
        Assertions.assertEquals("Jana", teacherDao.findAllStudentsForTheTeacher(3).get(listStudentsSizeBeforeAdding).getName());
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
        teacherDao.save(teacher);
        int teacherId = teacher.getId();
        studentDao.save(student);
        int studentId = student.getId();

        teacherDao.setJdbcTemplate(jdbcTemplate);
        teacherDao.addStudentToTeacher(studentId, teacherId);
        teacherDao.deleteStudentFromTeacher(studentId, teacherId);

        //then
        Assertions.assertTrue(teacherDao.findAllStudentsForTheTeacher(teacherId).isEmpty());
    }

    @Test
    public void should_id_not_be_null_when_save() {
        //given
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        Teacher teacher = getTeacher("Marek", "Iwanow", 51,
                "marek_iwanow@gmail.com", "Physics");

        //when
        teacherDao.setSimpleJdbcInsert(template2);
        teacherDao.save(teacher);

        //then
        Assertions.assertNotNull(teacher.getId());
    }


    @Test
    public void should_return_corresponding_list_all_teachers_size() {
        //given
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();

        //when
        teacherDao.setJdbcTemplate(jdbcTemplate);
        List<Teacher> allTeachers = teacherDao.findAllTeachers();
        int actualAmount = allTeachers.size();
        long checkedAmount = allTeachers.stream()
                .count();

//        //then
        Assertions.assertEquals(checkedAmount, actualAmount);
        Assertions.assertTrue(allTeachers.size() > 0);
    }

    @Test
    public void should_find_teacher_by_name_and_surname() {
        //given
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();

        //when
        teacherDao.setNamedParameterJdbcTemplate(template1);
        Optional<Teacher> teacherFound = teacherDao.findByNameAndSurname("Pawel", "Dudek");

        //then
        Assertions.assertNotNull(teacherFound);
        Assertions.assertEquals("Pawel", teacherFound.get().getName());
        Assertions.assertEquals("Dudek", teacherFound.get().getSurname());
    }

    @Test
    public void should_be_updated_email_after_update() {
        //given
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        Teacher teacher = getTeacher("Mateusz", "Brown", 35, "mateusz_brown@gmail.com", "Medicine");
        Teacher updatedTeacher = getTeacher("Mateusz", "Brown", 35, "Mateusz_Brown22@gmail.com", "Medicine");

        //when
        teacherDao.setSimpleJdbcInsert(simpleJdbcInsert);
        teacherDao.save(teacher);
        int id = teacher.getId();
        updatedTeacher.setId(id);
        teacherDao.setNamedParameterJdbcTemplate(template1);
        teacherDao.update(updatedTeacher);

        //then
        Assertions.assertEquals(updatedTeacher.getEmail(), teacherDao.findById(id).get().getEmail());
    }

    @Test
    public void should_find_all_students_for_teacher() {
        //given
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();

        //when
        teacherDao.setJdbcTemplate(jdbcTemplate);
        List<Student> allStudentsForTheTeacher = teacherDao.findAllStudentsForTheTeacher(3);

        //then
        Assertions.assertNotNull(allStudentsForTheTeacher);
        Assertions.assertTrue(allStudentsForTheTeacher.size() > 0);

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