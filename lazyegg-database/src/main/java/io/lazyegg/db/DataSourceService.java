package io.lazyegg.db;

import io.lazyegg.db.properties.DataSourceProperties;

public interface DataSourceService<T> {
    T create(DataSourceProperties properties);
}
