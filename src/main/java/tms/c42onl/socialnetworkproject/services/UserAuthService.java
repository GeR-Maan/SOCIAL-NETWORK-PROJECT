package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.exceptions.AuthException;
import tms.c42onl.socialnetworkproject.models.User;
import tms.c42onl.socialnetworkproject.storages.UserAuthStorage;

import java.util.Optional;

public class UserAuthService {


    private final UserAuthStorage storage;


    public UserAuthService(UserAuthStorage storage) {
        this.storage = storage;
    }
    // проверка на пустоту
    public User authenticate(String login, String password) throws AuthException {
        if (login == null || login.isBlank() || password == null || password.isEmpty()) {
            throw new AuthException("Логин и пароль не могут быть пустыми");
        }
        // поиск в БД
        Optional<User> userOptional = storage.findByLogin(login);
        // если не нашли — ошибка
        if (userOptional.isEmpty()) {
            throw new AuthException("Неверный логин или пароль");
        }
        // достаем пользователя
        User user = userOptional.get();
        // сверяем пароли
        if (!user.getPassword().equals(password)) {
            throw new AuthException("Неверный логин или пароль");
        }

        return user;
    }
}