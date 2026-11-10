package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.exceptions.RegistrationException;
import tms.c42onl.socialnetworkproject.models.User;
import tms.c42onl.socialnetworkproject.storages.UserStorage;

import java.time.LocalDate;

public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void registerUser(String login, String password, String firstName, String lastName, LocalDate birthDate) {
        User tempUser = new User(null, login, password, firstName, lastName, birthDate);

        if (login == null || login.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty()) {
            throw new RegistrationException("Пожалуйста заполните все поля", tempUser);
        }

        if (userStorage.findByLogin(login).isPresent()){
            throw new RegistrationException("Этот логин уже занят. Пожалуйста выберите другой.", tempUser);
        }

        tempUser.setPassword(password);

        userStorage.save(tempUser);
    }
}
