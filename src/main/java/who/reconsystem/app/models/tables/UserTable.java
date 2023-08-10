package who.reconsystem.app.models.tables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import who.reconsystem.app.models.Query;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.connect.DbConnect;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.user.User;
import who.reconsystem.app.user.UserBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTable extends Table {

    @Inject
    public UserTable(@Named("UserTable") String table, Query query, DbConnect connect) {
        super(query, connect);
        this.query.setTable(table);
    }

    public UserBean loggedUser(String username, String email) {
        List<String> fields = only(1, 5);
        String statement = query.select(fields)
                .from()
                .where(User.USERNAME.getName())
                .orWhere(User.EMAIL.getName())
                .query();
        List<String> data = find(statement, fields, Arrays.asList(username, email));
        return UserBean.builder()
                .userId(data.get(0))
                .password(data.get(1))
                .build();
    }

    @Override
    protected List<String> allFields() {
        return add(userFields().toArray(new User[0]));
    }

    @Override
    protected List<String> insertFields() {
        List<User> userList = userFields();
        userList.remove(0);
        return add(userList.toArray(new User[0]));
    }

    @Override
    protected List<String> updateFields() {
        List<User> userList = userFields();
        userList.remove(0);
        userList.add(User.ID);
        return add(userList.toArray(new User[0]));
    }

    @Override
    protected String findRequest() {
        return query.selectAll()
                .where(User.USER_ID.getName())
                .query();
    }

    private List<String> add(User... users) {
        List<User> users1 = new ArrayList<>(Arrays.asList(users));
        List<String> data = new ArrayList<>();
        users1.forEach(user -> data.add(user.getName()));
        return data;
    }

    private List<String> only(int...indexes) {
        List<String> matter = new ArrayList<>();
        for (int index: indexes) {
            matter.add(userFields().get(index).getName());
        }
        return matter;
    }

    private List<User> userFields() {
        return new ArrayList<>(Arrays.asList(
                User.ID, User.USER_ID, User.FIRST_NAME, User.LAST_NAME, User.USERNAME, User.PASSWORD,
                User.EMAIL, User.CREATED_AT, User.UPDATED_AT
        ));
    }
}
