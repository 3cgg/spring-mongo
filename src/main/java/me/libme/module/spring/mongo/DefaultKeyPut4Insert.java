package me.libme.module.spring.mongo;
//
//import me.libme.spring.kernel.web.ServerSessionHolder;
//import me.libme.spring.kernel.web.SessionUser;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Created by J on 2017/8/2.
// */
//@Component
//@Order
//public class DefaultKeyPut4Insert implements IRichInsertMongoModel {
//
//    @Override
//    public MongoModel rich(MongoModel mongoModel) {
//
//        SessionUser sessionUser= ServerSessionHolder.getSessionUser();
//
//        mongoModel.operations().setString("createId",sessionUser.getId());
//        mongoModel.operations().setDate("createTime",new Date());
//
//        mongoModel.operations().setString("updateId",sessionUser.getId());
//        mongoModel.operations().setDate("updateTime",new Date());
//
//        mongoModel.operations().setLong("version",0l);
//        return mongoModel;
//
//    }
//
//}
