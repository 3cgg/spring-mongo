package me.libme.module.spring.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by J on 2017/8/2.
 */
@Component
class RichInsertMongoModelPipe implements IRichInsertMongoModel{


//    @Autowired
//    private DefaultKeyPut4Insert defaultKeyPut4Insert;

    private List<IRichInsertMongoModel> richInsertMongoModels;

    @Autowired
    public void setRichInsertMongoModels(List<IRichInsertMongoModel> richInsertMongoModels) {

//        List<IRichInsertMongoModel> richInsertMongoModelList=new ArrayList<>();
//        richInsertMongoModels.forEach(richInsertMongoMode->{
//            if(!(DefaultKeyPut4Insert.class.isInstance(richInsertMongoMode))
//                    ){
//                richInsertMongoModelList.add(richInsertMongoMode);
//            }
//        });

        this.richInsertMongoModels = richInsertMongoModels;

    }

    @Override
    public MongoModel rich(MongoModel mongoModel) {

//        mongoModel=defaultKeyPut4Insert.rich(mongoModel);
        for(IRichInsertMongoModel richInsertMongoModel : richInsertMongoModels){
            mongoModel=richInsertMongoModel.rich(mongoModel);
        }
        return mongoModel;
    }

}
