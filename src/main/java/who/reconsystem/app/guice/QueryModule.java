package who.reconsystem.app.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import who.reconsystem.app.models.Query;
import who.reconsystem.app.models.QueryBuilder;

public class QueryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Query.class).to(QueryBuilder.class);
    }

    @Provides
    @Named("Table")
    public String provideTable() {
        return "";
    }

    @Provides
    @Named("UserTable")
    public String provideUserTable() {
        return "users";
    }
}
