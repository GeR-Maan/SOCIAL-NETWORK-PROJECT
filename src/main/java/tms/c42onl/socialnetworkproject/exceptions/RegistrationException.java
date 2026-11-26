package tms.c42onl.socialnetworkproject.exceptions;

import lombok.Getter;
import tms.c42onl.socialnetworkproject.models.User;

@Getter
public class RegistrationException extends RuntimeException {
    private final User invalidUser;

    public RegistrationException(String message, User invalidUser) {
        super(message);
        this.invalidUser = invalidUser;
    }

}
