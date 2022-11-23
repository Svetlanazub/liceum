package pl.zubkova.webuniversity.service.impl;

import org.springframework.stereotype.Service;
import pl.zubkova.webuniversity.dao.StudentDao;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.StudentService;

import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student createStudent(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentDao.update(student);
    }

    @Override
    public void deleteStudent(int studentId) {
       if (studentDao.findById(studentId).isPresent()) {
            studentDao.delete(studentId);
        } else throw new RuntimeException("No student with this ID");
    }

    @Override
    public Student getStudentByNameAndSurname(String name, String surname) {
        Student student = studentDao.findByNameAndSurname(name, surname).orElseThrow(() ->
                new RuntimeException("No such student"));
        return student;
    }

    @Override
    public List<Teacher> getAllTeachersOfTheStudent(int studentId) {
        return studentDao.findAllTeachersForTheStudent(studentId);
    }

    @Override
    public List<Student> getStudents() {
        return studentDao.findAllStudents();
    }

    @Override
    public void addTeacherToTheStudent(int teacherId, int studentId) {
        studentDao.addTeacherToStudent(teacherId, studentId);
    }

    @Override
    public void deleteTeacherFromTheStudent(int teacherId, int studentId) {
        studentDao.deleteTeacherFromStudent(teacherId, studentId);
    }

    @Override
    public List<Student> getStudentsByPage(int pageID, int total) {
        return studentDao.getStudentsByPage(pageID, total);
    }
}
