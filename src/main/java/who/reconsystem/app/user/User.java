package who.reconsystem.app.user;

public enum User {
    ID, USER_ID, FIRST_NAME, LAST_NAME, USERNAME, EMAIL, CREATED_AT, UPDATED_AT, AGE;

    private String name;

    public String getName() {
        return name;
    }

    static {
        ID.name = ID.name().toLowerCase();
        USER_ID.name = USER_ID.name().toLowerCase();
        FIRST_NAME.name = FIRST_NAME.name().toLowerCase();
        LAST_NAME.name = LAST_NAME.name().toLowerCase();
        USERNAME.name = USERNAME.name().toLowerCase();
        EMAIL.name = EMAIL.name().toLowerCase();
        CREATED_AT.name = CREATED_AT.name().toLowerCase();
        UPDATED_AT.name = UPDATED_AT.name().toLowerCase();
        AGE.name = AGE.name().toLowerCase();
    }
}
