package io.lazyegg.boot.component.db;

import io.lazyegg.boot.component.db.properties.DataSourceProperties;

public interface DataSourceService<T> {
    T create(DataSourceProperties properties);
}
