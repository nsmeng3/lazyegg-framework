package io.lazyegg.boot.component.db.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 */
@Slf4j
@Component
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 可以通过 getFieldValByName()方法判断
       // Object modifyTime = getFieldValByName("updateTime", metaObject);
        log.info("进入创建");
        // 获取当前用户信息
//        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.setFieldValByName("createTime",new Date(),metaObject);
//        this.setFieldValByName("creator",loginUser.getUser().getUserName(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
//        this.setFieldValByName("updater",loginUser.getUser().getUserName(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object modifier = getFieldValByName("updater", metaObject);
//        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userId = loginUser.getUser().getUserName();
//        if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
//            setFieldValByName("updater", userId.toString(), metaObject);
//        }
    }
}
