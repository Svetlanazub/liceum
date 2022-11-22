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

public class Teacher {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String subject;
    private List<Student> studentsList;

    public Teacher(int id, String name, String surname, int age, String email, String subject) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.subject = subject;
    }

    public void addStudentToTeacher(Student student){
        if (studentsList == null){
            studentsList = new ArrayList<>();
        }
        studentsList.add(student);
    }
}
