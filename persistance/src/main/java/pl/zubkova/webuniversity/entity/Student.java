package pl.zubkova.webuniversity.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Svetlana_Zubkova
 */
@Data
@NoArgsConstructor
@Getter
@Setter

public class Student {

    private int id;
    @NotNull(message = "Name should not be empty")
    @Size(min=2, message = "Name should be more that 2 characters")
    private String name;
    @NotNull(message = "Surname should not be empty")
    @Size(min=2, message = "Surname should be more that 2 letters")
    private String surname;
    @NotNull(message = "Age should not be empty")
    @Min(value = 18, message = "Age should be > 18")
    private int age;
    @NotNull(message = "Email should not be empty")
    @Email
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
