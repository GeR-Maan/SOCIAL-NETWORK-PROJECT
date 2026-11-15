package tms.c42onl.socialnetworkproject.exception;

import lombok.Getter;
import tms.c42onl.socialnetworkproject.model.User;

@Getter
public class RegistrationException extends RuntimeException {
    private final User invalidUser;

    public RegistrationException(String message, User invalidUser) {
        super(message);
        this.invalidUser = invalidUser;
    }

}
