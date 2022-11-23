package pl.zubkova.webuniversity.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "Name should not be empty")
    @Size(min=2, message = "Name should be more that 2 characters")
    private String name;
    @NotNull(message = "Surname should not be empty")
    @Size(min=2, message = "Surname should be more that 2 characters")
    private String surname;
    @NotNull(message = "Age should not be empty")
    @Min(value = 18, message = "Age should be > 18")
    private int age;
    @NotNull(message = "Email should not be empty")
    @Email
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
