package io.lazyegg.boot.component.db.querydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IBaseRepository<T, ID extends Serializable> extends
        JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {
}
