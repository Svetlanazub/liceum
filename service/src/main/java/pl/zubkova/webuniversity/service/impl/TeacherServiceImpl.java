package pl.zubkova.webuniversity.service.impl;

import org.springframework.stereotype.Service;
import pl.zubkova.webuniversity.dao.TeacherDao;
import pl.zubkova.webuniversity.dto.SortingDto;
import pl.zubkova.webuniversity.entity.Student;
import pl.zubkova.webuniversity.entity.Teacher;
import pl.zubkova.webuniversity.service.TeacherService;

import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherDao teacherDao;

    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherDao.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return teacherDao.update(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) {
        if (teacherDao.findById(teacherId).isPresent()) {
            teacherDao.delete(teacherId);
        } else throw new RuntimeException("No teacher with this ID");
    }

    @Override
    public Teacher getTeacherByNameAndSurname(String name, String surname) {
        Teacher teacher = teacherDao.findByNameAndSurname(name, surname).orElseThrow(() ->
                new RuntimeException("No such teacher"));
        return teacher;
    }

    @Override
    public List<Student> getAllStudentsOfTheTeacher(int teacherId) {
        return teacherDao.findAllStudentsForTheTeacher(teacherId);
    }

    @Override
    public List<Teacher> getTeachers() {
        return teacherDao.findAllTeachers();
    }

    @Override
    public void addStudentToTheTeacher(int studentId, int teacherId) {
        teacherDao.addStudentToTeacher(studentId, teacherId);
    }

    @Override
    public void deleteStudentFromTheTeacher(int studentId, int teacherId) {
        teacherDao.deleteStudentFromTeacher(studentId, teacherId);
    }

    @Override
    public List<Teacher> getSortedTeachersByPage(int pageID, int pageSize, SortingDto sortingDto) {
        return teacherDao.getSortedTeachersByPage(pageID, pageSize, sortingDto);
    }
}
