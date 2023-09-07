package who.reconsystem.app.models.tables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import who.reconsystem.app.models.Query;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.connect.DbConnect;
import who.reconsystem.app.user.UserEntity;
import who.reconsystem.app.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserTable extends Table {

    @Inject
    public UserTable(@Named("UserTable") String table, Query query, DbConnect connect) {
        super(query, connect);
        this.query.setTable(table);
    }

    public User loggedUser(String username, String email) {
        List<String> fields = only(1, 5);
        String statement = query.select(fields)
                .from()
                .where(UserEntity.USERNAME.getName())
                .orWhere(UserEntity.EMAIL.getName())
                .query();
        List<String> data = find(statement, fields, Arrays.asList(username, email));
        return data.isEmpty() ? User.builder().build() : User.builder()
                .userId(data.get(0))
                .password(data.get(1))
                .build();
    }

    @Override
    protected List<String> allFields() {
        System.out.println("value is: " + userFields());
        return add(userFields().toArray(new UserEntity[0]));
    }

    @Override
    protected List<String> insertFields() {
        List<UserEntity> userEntityList = userFields();
        userEntityList.remove(0);
        return add(userEntityList.toArray(new UserEntity[0]));
    }

    @Override
    protected List<String> updateFields() {
        List<UserEntity> userEntityList = userFields();
        userEntityList.remove(0);
        userEntityList.add(UserEntity.ID);
        return add(userEntityList.toArray(new UserEntity[0]));
    }

    @Override
    protected String findRequest() {
        return query.selectAll()
                .where(UserEntity.USER_ID.getName())
                .query();
    }

    private List<String> add(UserEntity... userEntities) {
        List<UserEntity> users1 = new ArrayList<>(Arrays.asList(userEntities));
        List<String> data = new ArrayList<>();
        users1.forEach(userEntity -> data.add(userEntity.getName()));
        return data;
    }

    private List<String> only(int...indexes) {
        List<String> matter = new ArrayList<>();
        for (int index: indexes) {
            matter.add(userFields().get(index).getName());
        }
        return matter;
    }

    private List<UserEntity> userFields() {
        return Arrays.stream(UserEntity.values()).collect(Collectors.toList());
    }
}
