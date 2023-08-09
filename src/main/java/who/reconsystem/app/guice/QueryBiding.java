package who.reconsystem.app.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.tables.UserTable;

public class QueryBiding {
    public static Table useUserTable() {
        Injector injector = Guice.createInjector(new QueryModule());
        return injector.getInstance(UserTable.class);
    }
}
