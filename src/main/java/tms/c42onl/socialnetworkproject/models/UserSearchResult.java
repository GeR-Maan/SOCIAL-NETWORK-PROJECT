package tms.c42onl.socialnetworkproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSearchResult {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;

}