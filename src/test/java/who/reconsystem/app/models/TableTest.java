package who.reconsystem.app.models;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import who.reconsystem.app.guice.QueryModule;
import who.reconsystem.app.models.tables.UserTable;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.root.config.PBKDF2WithHmacSHA1;
import who.reconsystem.app.root.config.StrongIdGenerator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableTest {

    private final Table table;

    public TableTest() {
        Injector injector = Guice.createInjector(new QueryModule());
        this.table = injector.getInstance(UserTable.class);
    }

    @Test
    public void testInsert() {
        List<Object> values = Arrays.asList(StrongIdGenerator.generateSecureRandomId(15), "Nikhe", "Niama", "nikhe", PBKDF2WithHmacSHA1.hashPassword("toto"), "nikhe@test.com",
                Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"), Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"));
        long insertId = table.insert(values);
        boolean isInserted = insertId != 0;

        assertTrue(isInserted);
    }

    @Test
    public void testFindAll() {
        List<String[]> data = table.findAll();

        assertEquals(data.size(), 2);
        assertEquals(data.get(1)[0], "2");
        assertEquals(data.get(1)[4], "nikhe");
    }

    @Test
    public void testFind() {
        List<String> data = table.find(2);

        assertEquals(data.size(), 8);
        assertEquals(data.get(0), "2");
        assertEquals(data.get(4), "test1");
    }

    @Test
    public void testUpdate() {
        List<Object> values = Arrays.asList("sB6qS1rmTJkucT2", "Test", "Test", "test", PBKDF2WithHmacSHA1.hashPassword("toto"), "test@test.com",
                "2023-05-24 14:57:01", Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"), "1");
        long isUpdated = table.update(values);

        assertTrue(isUpdated != 0);
    }

}
