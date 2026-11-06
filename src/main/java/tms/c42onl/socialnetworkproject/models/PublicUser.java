package tms.c42onl.socialnetworkproject.models;

import java.util.Objects;


public class PublicUser {

    private final Long id;
    private final String username;
    private final String displayName;

    public PublicUser(Long id, String username, String displayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicUser)) return false;
        PublicUser that = (PublicUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}