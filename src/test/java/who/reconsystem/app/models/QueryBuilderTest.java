package who.reconsystem.app.models;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.user.User;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryBuilderTest {
    private final Query query;

    public QueryBuilderTest() {
        this.query = new QueryBuilder("users");
    }

    @Test
    public void testInsert() {
        String insertStatement = query.insert().query();
        String insertStatement1 = query.insert().into()
                .insertedFields(Arrays.asList(User.USERNAME.getName(), User.AGE.getName()))
                .insertedValues(Arrays.asList("'Nikhe'", 30)).query();
        String insertStatement2 = query.insert().into()
                        .insertedFieldAndValue(new LinkedHashMap<String, Object>(){
                            {
                                put(User.USERNAME.getName(), "'Nikhe'");
                                put(User.AGE.getName(), 30);
                            }
                        }).query();
        String insertStatement3 = query.insert(Arrays.asList(
                User.USERNAME.getName(), User.AGE.getName()
        )).query();
        String insertStatement4 = query.insert(Arrays.asList("username", "age")).query();

        assertEquals(insertStatement, "INSERT ");
        assertEquals(insertStatement1, "INSERT INTO users (username, age) VALUES ('Nikhe', 30)");
        assertEquals(insertStatement2, "INSERT INTO users (username, age) VALUES ('Nikhe', 30)");
        assertEquals(insertStatement3, "INSERT INTO users (username, age) VALUES (?, ?)");
        assertEquals(insertStatement4, "INSERT INTO users (username, age) VALUES (?, ?)");
    }

    @Test
    public void testUpdate() {
        Map<String, Object> mapData = new LinkedHashMap<>();
        mapData.put(User.USERNAME.getName(), "'Nikhe'");
        mapData.put(User.AGE.getName(), 30);

        String update = query.update().query();

        String update1 = query.updateId(Arrays.asList(
                User.USERNAME.getName(), User.AGE.getName()
        )).query();

        String update2 = query.updateId(mapData).query();

        String update3 = query.update().set().updateValue(Arrays.asList(
                User.USERNAME.getName(), User.AGE.getName()
        )).whereId().query();

        String update4 = query.update().set(Arrays.asList(
                User.USERNAME.getName(), User.AGE.getName()
        )).where(
                Arrays.asList(User.ID.getName(),
                        User.USER_ID.getName()
                ), Operator.AND).query();

        String update5 = query.update().set(mapData).whereId().query();

        assertEquals(update, "UPDATE users");
        assertEquals(update1, "UPDATE users SET username = ?, age = ? WHERE id = ?");
        assertEquals(update2, "UPDATE users SET username = 'Nikhe', age = 30 WHERE id = ?");
        assertEquals(update3, "UPDATE users SET username = ?, age = ? WHERE id = ?");
        assertEquals(update4, "UPDATE users SET username = ?, age = ? WHERE id = ? AND user_id = ?");
        assertEquals(update5, "UPDATE users SET username = 'Nikhe', age = 30 WHERE id = ?");
    }

    @Test
    public void testDelete() {
        String delete = query.delete().query();
        String delete1 = query.deleteId().query();
        String delete2 = query.delete(User.USER_ID.getName()).query();
        String delete3 = query.delete(User.USER_ID.getName(), 12).query();
        String delete4 = query.delete(new LinkedHashMap<String, Object>(){{
            put(User.ID.getName(), 10);
            put(User.USER_ID.getName(), 12);
            put(User.USERNAME.getName(), "'Nikhe'");
        }}, Operator.AND).query();

        assertEquals(delete, "DELETE FROM users");
        assertEquals(delete1, "DELETE FROM users WHERE id = ?");
        assertEquals(delete2, "DELETE FROM users WHERE user_id = ?");
        assertEquals(delete3, "DELETE FROM users WHERE user_id = 12");
        assertEquals(delete4, "DELETE FROM users WHERE id = 10 AND user_id = 12 AND username = 'Nikhe'");
    }

    @Test
    public void testSelect() {
        String select = query.selectAll().query();
        String select1 = query.select().from().query();
        String select2 = query.select(User.USER_ID.getName()).from().query();
        String select3 = query.select(Arrays.asList(
                User.USER_ID.getName(), User.USERNAME.getName(), User.EMAIL.getName()
        )).from().whereId().query();
        String select4 = query.select().from().where(Arrays.asList(
                User.USERNAME.getName(), User.EMAIL.getName()
        ), Operator.OR).query();
        String select5 = query.select().from().where(Arrays.asList(
                User.ID.getName(), User.USER_ID.getName()
        ), Arrays.asList(Operator.GREATER_THAN, Operator.EQUALS), Operator.AND).query();
        String select6 = query.select().from().where(Arrays.asList(
                User.ID.getName(), User.USER_ID.getName()
        ), Arrays.asList(Operator.GREATER_THAN, Operator.EQUALS), Operator.OR).query();
        String select7 = query.select(User.USERNAME.getName()).from().where().like(User.USERNAME.getName()).query();
        String select8 = query.select(User.USERNAME.getName()).from().where().like(User.USERNAME.getName(), "AKZ").query();
        String select9 = query.select().from().order(User.USERNAME.getName(), Ordering.DESC).limit(100).query();

        assertEquals(select, "SELECT * FROM users");
        assertEquals(select1, "SELECT * FROM users");
        assertEquals(select2, "SELECT user_id FROM users");
        assertEquals(select3, "SELECT user_id, username, email FROM users WHERE id = ?");
        assertEquals(select4, "SELECT * FROM users WHERE username = ? OR email = ?");
        assertEquals(select5, "SELECT * FROM users WHERE id > ? AND user_id = ?");
        assertEquals(select6, "SELECT * FROM users WHERE (id > ? OR id > ?) OR (user_id = ? OR user_id = ?)");
        assertEquals(select7, "SELECT username FROM users WHERE username LIKE ?");
        assertEquals(select8, "SELECT username FROM users WHERE username LIKE '%AKZ%'");
        assertEquals(select9, "SELECT * FROM users ORDER BY username DESC LIMIT 100");
    }

}
