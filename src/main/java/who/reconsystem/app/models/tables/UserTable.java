package who.reconsystem.app.models.tables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import who.reconsystem.app.models.Query;
import who.reconsystem.app.models.Table;

public class UserTable extends Table {

    @Inject
    public UserTable(@Named("UserTable") String table, Query query) {
        super(query);
        this.query.setTable(table);
    }

    @Override
    public String insert() {
        return query.insert().query();
    }
}
