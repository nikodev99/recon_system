package who.reconsystem.app.models.tables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import who.reconsystem.app.models.Query;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.connect.DbConnect;
import who.reconsystem.app.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTable extends Table {

    @Inject
    public UserTable(@Named("UserTable") String table, Query query, DbConnect connect) {
        super(query, connect);
        this.query.setTable(table);
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

    private List<String> add(User... users) {
        List<User> users1 = new ArrayList<>(Arrays.asList(users));
        List<String> data = new ArrayList<>();
        users1.forEach(user -> data.add(user.getName()));
        return data;
    }

    private List<User> userFields() {
        return new ArrayList<>(Arrays.asList(
                User.ID, User.USER_ID, User.FIRST_NAME, User.LAST_NAME, User.USERNAME, User.PASSWORD,
                User.EMAIL, User.CREATED_AT, User.UPDATED_AT
        ));
    }
}
