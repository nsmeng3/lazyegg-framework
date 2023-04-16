package io.lazyegg.boot.component.db.querydsl;

import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * AppService
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Transactional
public abstract class BaseService<T, ID extends Serializable> {

    @Autowired
    private IBaseRepository<T, ID> iBaseRepository;

    public IBaseRepository getiBaseRepository() {
        return iBaseRepository;
    }
}
