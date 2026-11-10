package tms.c42onl.socialnetworkproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public User(String login, String password, String firstName, String lastName, LocalDate birthDate){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
