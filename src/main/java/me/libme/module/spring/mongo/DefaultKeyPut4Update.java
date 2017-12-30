package me.libme.module.spring.mongo;

//import me.libme.spring.kernel.web.ServerSessionHolder;
//import me.libme.spring.kernel.web.SessionUser;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Created by J on 2017/8/2.
// */
//@Component
//public class DefaultKeyPut4Update implements IRichUpdateMongoModel {
//
//    @Override
//    public MongoModel rich(MongoModel mongoModel) {
//
//        SessionUser sessionUser= ServerSessionHolder.getSessionUser();
//        mongoModel.operations().setString("updateId",sessionUser.getId());
//        mongoModel.operations().setDate("updateTime",new Date());
//
//        return mongoModel;
//
//    }
//
//}
