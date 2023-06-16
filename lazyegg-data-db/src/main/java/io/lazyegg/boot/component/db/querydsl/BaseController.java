package io.lazyegg.boot.component.db.querydsl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * AppController
 *
 * @author DifferentW  nsmeng3@163.com
 */
public abstract class BaseController<T, ID extends Serializable> {

    @Resource
    private BaseService<T, ID> baseService;

    public BaseService<T, ID> getBaseService() {
        return baseService;
    }

    @GetMapping
    public ResponseEntity<Object> list() {
        IBaseRepository iBaseRepository = baseService.getiBaseRepository();
        List all = iBaseRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
