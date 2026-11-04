package tms.c42onl.socialnetworkproject.services;

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

        if (login == null || login.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new RegistrationException("The login and password are required fields.", tempUser);
        }

        if (userStorage.existsByLogin(login)){
            throw new RegistrationException("A user with this login already exists. Please enter a different login.", tempUser);
        }

        tempUser.setPassword(password);

        userStorage.save(tempUser);
    }
}
