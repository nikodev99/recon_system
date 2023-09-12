package who.reconsystem.app.models;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.guice.bidings.QueryBiding;
import who.reconsystem.app.root.config.DateFormat;
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
        this.table = QueryBiding.useUserTable();
    }

    @Test
    public void testInsert() {
        List<Object> values = Arrays.asList(StrongIdGenerator.generateSecureRandomId(15), "Kane", "Kojo", "kojo", PBKDF2WithHmacSHA1.hashPassword("toto"), "kojo@test.com",
                Functions.instantDatetime(DateFormat.DATETIME_BY_BAR), Functions.instantDatetime(DateFormat.DATETIME_BY_BAR));
        long insertId = table.insert(values);
        boolean isInserted = insertId != 0;

        assertTrue(isInserted);
    }

    @Test
    public void testFindAll() {
        List<String[]> data = table.findAll();

        assertEquals(data.size(), 4);
    }

    @Test
    public void testFind() {
        List<String> data = table.find("ffQRWjn5NfIR7BmdBRrp");

        assertEquals(data.size(), 9);
        assertEquals(data.get(0), "2");
        assertEquals(data.get(4), "davy");
    }

    @Test
    public void testUpdate() {
        List<Object> values = Arrays.asList("v2wj4P8XC9KCOHE", "Kane", "Kojo Mbere", "kojo", PBKDF2WithHmacSHA1.hashPassword("toto"), "kojo@test.com",
                "2023-09-06 17:02:03", Functions.instantDatetime(DateFormat.DATETIME_BY_BAR), "4");
        long isUpdated = table.update(values);

        assertTrue(isUpdated != 0);
    }

}
