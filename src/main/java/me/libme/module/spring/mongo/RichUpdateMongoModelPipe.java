package me.libme.module.spring.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by J on 2017/8/2.
 */
@Component
class RichUpdateMongoModelPipe implements IRichUpdateMongoModel{

//    @Autowired
//    private DefaultKeyPut4Update defaultKeyPut4Update;

    private List<IRichUpdateMongoModel> richUpdateMongoModels;

    @Autowired
    public void setRichUpdateMongoModels(List<IRichUpdateMongoModel> richUpdateMongoModels) {
//        List<IRichUpdateMongoModel> richUpdateMongoModelList=new ArrayList<>();
//        richUpdateMongoModels.forEach(richUpdateMongoMode->{
//            if(!(DefaultKeyPut4Update.class.isInstance(richUpdateMongoMode))
//                    ){
//                richUpdateMongoModelList.add(richUpdateMongoMode);
//            }
//        });

        this.richUpdateMongoModels = richUpdateMongoModels;
    }

    @Override
    public MongoModel rich(MongoModel mongoModel) {

//        mongoModel=defaultKeyPut4Update.rich(mongoModel);
        for(IRichUpdateMongoModel richUpdateMongoModel : richUpdateMongoModels){
            mongoModel=richUpdateMongoModel.rich(mongoModel);
        }
        return mongoModel;
    }
}
