package who.reconsystem.app.user;

public enum UserEntity {
    ID, USER_ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, EMAIL, CREATED_AT, UPDATED_AT;

    private String name;

    public String getName() {
        return name.toLowerCase();
    }

    static {
        ID.name = ID.name();
        USER_ID.name = USER_ID.name();
        FIRST_NAME.name = FIRST_NAME.name();
        LAST_NAME.name = LAST_NAME.name();
        USERNAME.name = USERNAME.name();
        PASSWORD.name = PASSWORD.name();
        EMAIL.name = EMAIL.name();
        CREATED_AT.name = CREATED_AT.name();
        UPDATED_AT.name = UPDATED_AT.name();
        //AGE.name = AGE.name();
    }
}
