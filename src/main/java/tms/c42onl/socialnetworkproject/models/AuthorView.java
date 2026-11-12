package tms.c42onl.socialnetworkproject.models;

/**
 * avatarUrl — null, пока TMS-17/30 не согласуют поле
 */
public class AuthorView {

    private final long userId;
    private final String displayName;
    private final String avatarUrl;

    public AuthorView(long userId, String displayName, String avatarUrl) {
        this.userId = userId;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
    }

    public long getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public String getAvatarUrl() { return avatarUrl; }
}