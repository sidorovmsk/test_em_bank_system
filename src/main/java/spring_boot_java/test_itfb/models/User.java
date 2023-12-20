package spring_boot_java.test_itfb.models;

import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = "Имя не должно быть пустым")
//    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(unique = true, nullable = false, name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Task> authoredTasks;

    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", email='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}