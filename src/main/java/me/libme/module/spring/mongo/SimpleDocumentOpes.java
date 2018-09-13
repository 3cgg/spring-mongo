package me.libme.module.spring.mongo;

import me.libme.kernel._c._m.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 2017/7/31.
 */
public class SimpleDocumentOpes implements DocumentOperations {

    @Autowired(required = false)
    private MongoOperations mongoOperations;

    @Autowired
    private MongoCfg mongoCfg;

    @Autowired
    private CollectionNameValidator collectionNameValidator;

    @Autowired
    private RichInsertMongoModelPipe richInsertMongoModel;

    @Autowired
    private RichUpdateMongoModelPipe richUpdateMongoModel;

    @Override
    public void insert(String collectionName, JModel data) {
        collectionNameValidator.validate(collectionName);
        Object object=data;
        if(MongoModel.class.isInstance(data)){
            MongoModel mongoModel=(MongoModel)data;
            richInsertMongoModel.rich(mongoModel);
            object=mongoModel.directive().insert();
        }
        mongoOperations.insert(object,collectionName);
    }

    @Override
    public void insert(String collectionName, List<? extends JModel> data) {
        collectionNameValidator.validate(collectionName);
        List inserted=new ArrayList<>(data.size());
        data.forEach(model->{
            Object object=model;
            if(MongoModel.class.isInstance(model)){
                MongoModel mongoModel=(MongoModel)model;
                richInsertMongoModel.rich(mongoModel);
                object=mongoModel.directive().insert();
            }
            inserted.add(object);
        });
        mongoOperations.insert(inserted,collectionName);

    }

    @Override
    public void update(String collectionName, String id, JModel data) {
        Query query=null;
        if(ObjectId.isValid(id)){
            query=Query.query(Criteria.where("_id").is(
                    new ObjectId(id)));
        }else{
            query=Query.query(Criteria.where(mongoCfg.primaryKey()).is(id));
        }
        update(collectionName,query,data);
    }


    @Override
    public void update(String collectionName, Query query, JModel data) {
        collectionNameValidator.validate(collectionName);
        Update update=null;
        if(MongoModel.class.isInstance(data)){
            MongoModel mongoModel=(MongoModel)data;
            richUpdateMongoModel.rich(mongoModel);
            update=mongoModel.directive().update();
        }
        mongoOperations.updateMulti(query,update,collectionName);
    }

    @Override
    public void update(String collectionName, IMongoQuery query, JModel data) {
        update(collectionName,query.query(),data);
    }

    @Override
    public void delete(String collectionName, String id) {
        MongoModel mongoModel=new MongoModel();
        mongoModel.operations().setString(mongoCfg.deletedKey(),"Y");
        update(collectionName,id,mongoModel);
    }

    @Override
    public void delete(String collectionName, Query query) {
        MongoModel mongoModel=new MongoModel();
        mongoModel.operations().setString(mongoCfg.deletedKey(),"Y");
        update(collectionName,query,mongoModel);
    }

    @Override
    public void delete(String collectionName, IMongoQuery query) {
        delete(collectionName,query.query());
    }

    @Override
    public void deletePermanently(String collectionName, String id) {
        Query query=null;
        if(ObjectId.isValid(id)){
            query=Query.query(Criteria.where("_id").is(
                    new ObjectId(id)));
        }else{
            query=Query.query(Criteria.where(mongoCfg.primaryKey()).is(id));
        }
        deletePermanently(collectionName,query);
    }

    @Override
    public void deletePermanently(String collectionName, Query query) {
        collectionNameValidator.validate(collectionName);
        mongoOperations.remove(query,collectionName);
    }

    @Override
    public void deletePermanently(String collectionName, IMongoQuery query) {
        deletePermanently(collectionName,query.query());
    }

    @Override
    public JPage<? extends Map> page(String collectionName, Query query, SimplePageRequest page) {
        return page(collectionName,query,page,LinkedHashMap.class);
    }

    @Override
    public <T> JPage<T> page(String collectionName, Query query, SimplePageRequest page, Class<T> clazz) {
        collectionNameValidator.validate(collectionName);
        long count=mongoOperations.count(query,collectionName);

        int pageNumber=page.getPageNumber();
        int pageSize=page.getPageSize();
        int tempTotalPageNumber= JImpl.caculateTotalPageNumber(count, pageSize);
        pageNumber=pageNumber>tempTotalPageNumber?tempTotalPageNumber:pageNumber;
        PageRequest pageRequest=new PageRequest(pageNumber,page.getPageSize());
        query.with(pageRequest);
        List<T> data=mongoOperations.find(query,clazz,collectionName);

        JImpl pageImpl=new JImpl();
        JPageUtil.replaceConent(pageImpl, data);
        pageImpl.setTotalRecordNumber(count);
        pageImpl.setTotalPageNumber(tempTotalPageNumber);
        SimplePageRequest simplePageRequest=new SimplePageRequest(pageNumber, page.getPageSize());
        pageImpl.setPageable(simplePageRequest);

        return pageImpl;
    }


    @Override
    public JPage<? extends Map> page(String collectionName, IMongoQuery query, SimplePageRequest page) {
        return page(collectionName,query.query(),page);
    }

    @Override
    public <T> JPage<T> page(String collectionName, IMongoQuery query, SimplePageRequest page, Class<T> clazz) {
        return page(collectionName,query.query(),page,clazz);
    }



    @Override
    public List<? extends Map> list(String collectionName, Query query) {
        return list(collectionName,query,LinkedHashMap.class);
    }

    @Override
    public <T> List<T> list(String collectionName, Query query, Class<T> clazz) {
        collectionNameValidator.validate(collectionName);
        List<T> data=mongoOperations.find(query,clazz,collectionName);
        return data;
    }

    @Override
    public List<? extends Map> list(String collectionName, IMongoQuery query) {
        return list(collectionName,query.query());
    }

    @Override
    public <T> List<T> list(String collectionName, IMongoQuery query, Class<T> clazz) {
        return list(collectionName,query.query(),clazz);
    }


    @Override
    public Map one(String collectionName, Query query) {
        return one(collectionName,query,LinkedHashMap.class);
    }

    @Override
    public <T> T one(String collectionName, Query query, Class<T> clazz) {
        collectionNameValidator.validate(collectionName);
        return mongoOperations.findOne(query,clazz,collectionName);
    }

    @Override
    public Map one(String collectionName, IMongoQuery query) {
        return one(collectionName,query.query());
    }

    @Override
    public <T> T one(String collectionName, IMongoQuery query, Class<T> clazz) {
        return one(collectionName,query.query(),clazz);
    }

    @Override
    public Map one(String collectionName, String id) {
        return one(collectionName,id,LinkedHashMap.class);
    }

    @Override
    public <T> T one(String collectionName, String id, Class<T> clazz) {
        Query query=null;
        if(ObjectId.isValid(id)){
            query=Query.query(Criteria.where("_id").is(
                    new ObjectId(id)));
        }else{
            query=Query.query(Criteria.where(mongoCfg.primaryKey()).is(id));
        }
        return one(collectionName,query,clazz);
    }


    @Override
    public void dropCollection(String collectionName) {
        collectionNameValidator.validate(collectionName);
        mongoOperations.dropCollection(collectionName);
    }

}
