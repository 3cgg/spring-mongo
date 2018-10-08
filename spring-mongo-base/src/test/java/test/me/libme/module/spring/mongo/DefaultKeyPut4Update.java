package test.me.libme.module.spring.mongo;

import me.libme.module.spring.mongo.IRichUpdateMongoModel;
import me.libme.module.spring.mongo.MongoModel;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by J on 2017/8/2.
 */
@Component
public class DefaultKeyPut4Update implements IRichUpdateMongoModel {

    @Override
    public MongoModel rich(MongoModel mongoModel) {

        SessionUser sessionUser= SessionUser.DEFUALT;
        mongoModel.operations().setString("updateId",sessionUser.getId());
        mongoModel.operations().setDate("updateTime",new Date());

        return mongoModel;

    }

}
