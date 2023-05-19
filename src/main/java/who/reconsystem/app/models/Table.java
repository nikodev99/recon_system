package who.reconsystem.app.models;

import com.google.inject.Inject;

public class Table {

    protected Query query;

    @Inject
    public Table(Query query) {
        this.query = query;
    }

    public String insert() {
        return "";
    }
}
