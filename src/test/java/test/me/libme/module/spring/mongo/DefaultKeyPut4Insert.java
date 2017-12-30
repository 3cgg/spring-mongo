package test.me.libme.module.spring.mongo;

import me.libme.module.spring.mongo.IRichInsertMongoModel;
import me.libme.module.spring.mongo.MongoModel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by J on 2017/8/2.
 */
@Component
@Order
public class DefaultKeyPut4Insert implements IRichInsertMongoModel {

    @Override
    public MongoModel rich(MongoModel mongoModel) {

        SessionUser sessionUser= SessionUser.DEFUALT;

        mongoModel.operations().setString("createId",sessionUser.getId());
        mongoModel.operations().setDate("createTime",new Date());

        mongoModel.operations().setString("updateId",sessionUser.getId());
        mongoModel.operations().setDate("updateTime",new Date());

        mongoModel.operations().setLong("version",0l);
        return mongoModel;

    }

}
