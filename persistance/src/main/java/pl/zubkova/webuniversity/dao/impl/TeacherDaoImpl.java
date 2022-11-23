package pl.zubkova.webuniversity.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import pl.zubkova.webuniversity.dao.TeacherDao;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;

import java.util.*;

/**
 * @author Svetlana_Zubkova
 */
@Repository
public class TeacherDaoImpl implements TeacherDao {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Autowired
    @Qualifier("Teachers")
    public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    private static final String READ_TEACHER_BY_ID = "SELECT name, surname, age, email, " +
            "subject FROM public.teachers WHERE id = :id";
    private static final String READ_ALL_TEACHERS = "select * from teachers";
    private static final String UPDATE_TEACHER = "UPDATE public.teachers SET name = :name, surname = :surname, age = :age, " +
            "email = :email, subject = :subject WHERE id = :id";

    private static final String DELETE_TEACHER = "DELETE FROM public.teachers WHERE id = :id";
    private static final String READ_TEACHER_BY_NAME_AND_SURNAME = "SELECT name, surname, age, email, " +
            "subject FROM public.teachers WHERE name = :name and surname = :surname";

    private static final String ADD_STUDENT_TO_TEACHER =
            "insert into students_teachers (student_id, teacher_id) values (?, ?)";
    private static final String DELETE_STUDENT_FROM_TEACHER = "delete from students_teachers where student_id = ? and teacher_id = ?";
    private static final String READ_STUDENTS_BY_TEACHER_ID =
            "SELECT* FROM students JOIN students_teachers ON students.id = student_id WHERE teacher_id = ?";
    @Override
    public Teacher save(Teacher teacher) {
        Map<String, Object> params = getParams(teacher);
        int teacherId = 0;
        try {
            teacherId  = simpleJdbcInsert.executeAndReturnKey(params).intValue();
        } catch (DataAccessException e) {
            e.getMessage();
        }
        teacher.setId(teacherId);
        return teacher;
    }

    @Override
    public Teacher update(Teacher teacher) {
        if (teacher.getId() != 0) {
            Map<String, Object> params = getParams(teacher);
            params.put("id", teacher.getId());
            namedParameterJdbcTemplate.update(UPDATE_TEACHER, params);
        }
        return teacher;
    }

    @Override
    public void delete(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            namedParameterJdbcTemplate.update(DELETE_TEACHER, params);
        } catch (DataAccessException e) {
            e.getMessage();
        }
    }
    @Override
    public Optional<Teacher> findById(int teacherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", teacherId);
        Teacher teacher;
        try {
            teacher = namedParameterJdbcTemplate.queryForObject(READ_TEACHER_BY_ID, params, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
        return Optional.of(teacher);
    }
    @Override
    public List<Teacher> findAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = jdbcTemplate.query(READ_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            e.getMessage();
        }
        return teachers;
    }

    @Override
    public Optional<Teacher> findByNameAndSurname(String name, String surname) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("surname", surname);
        Teacher teacher;
        try {
            teacher = namedParameterJdbcTemplate.queryForObject(READ_TEACHER_BY_NAME_AND_SURNAME, params, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
        return Optional.of(teacher);
    }

    @Override
    public List<Student> findAllStudentsForTheTeacher(int teacherId) {

        List<Student> students = new ArrayList<>();
        try {
            students =  jdbcTemplate.query(READ_STUDENTS_BY_TEACHER_ID, new BeanPropertyRowMapper<>(Student.class), teacherId);
        } catch (DataAccessException e) {
            e.getMessage();
        }
        return students;
    }

    @Override
    public void addStudentToTeacher(int studentId, int teacherId) {
        try {
            jdbcTemplate.update(ADD_STUDENT_TO_TEACHER, studentId, teacherId);
        } catch (DataAccessException e) {
            e.getMessage();
        }
    }

    @Override
    public void deleteStudentFromTeacher(int studentId, int teacherId) {
        try {
            jdbcTemplate.update(DELETE_STUDENT_FROM_TEACHER, studentId, teacherId);

        } catch (DataAccessException e) {
            e.getMessage();
        }
    }

    @Override
    public List<Teacher> getTeachersByPage(int pageId, int total) {
        String sql = "Select * from public.teachers limit" + (pageId - 1) + "," + total;
        List<Teacher> teacherList = new ArrayList<>();
        try {

            teacherList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Teacher.class));

        } catch (DataAccessException e) {
            e.getMessage();
        }
        return teacherList;
    }

    private static Map<String, Object> getParams(Teacher teacher) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", teacher.getName());
        params.put("surname", teacher.getSurname());
        params.put("age", teacher.getAge());
        params.put("email", teacher.getEmail());
        params.put("subject", teacher.getSubject());
        return params;
    }
}
