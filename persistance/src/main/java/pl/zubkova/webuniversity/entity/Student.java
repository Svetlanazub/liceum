package pl.zubkova.webuniversity.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Svetlana_Zubkova
 */
@Data
@NoArgsConstructor
@Getter
@Setter

public class Student {

    private int id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String specialisation;
    @Getter
    @Setter
    private List<Teacher> teachersList = new ArrayList<>();

    public Student(String name, String surname, int age, String email, String specialisation) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.specialisation = specialisation;
    }

    private void addTeacherToStudent(Teacher teacher){
        if(teachersList == null){
            teachersList = new ArrayList<>();
        }
        teachersList.add(teacher);
    }
}
