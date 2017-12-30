package test.me.libme.module.spring.mongo;


import me.libme.module.spring.mongo.IRichInsertMongoModel;
import me.libme.module.spring.mongo.MongoModel;
import org.springframework.stereotype.Component;

/**
 * Created by J on 2017/8/2.
 */
@Component
public class _CusDefKey4Insert implements IRichInsertMongoModel {


    @Override
    public MongoModel rich(MongoModel mongoModel) {

        mongoModel.operations().setString("cpp","TEST-ONLY");

        return mongoModel;
    }
}
