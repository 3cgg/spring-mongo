package me.libme.module.spring.mongo;

import me.libme.kernel._c._m.JModel;
import me.libme.kernel._c._m.JPage;
import me.libme.kernel._c._m.SimplePageRequest;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by J on 2017/7/18.
 */
public interface DocumentOperations {


    void insert(String collectionName,JModel data);

    void insert(String collectionName,List<? extends JModel> data);

    void update(String collectionName,String id,JModel data);

    void update(String collectionName,Query query,JModel data);

    void update(String collectionName,IMongoQuery query,JModel data);

    /**
     * logical delete
     * @param collectionName
     * @param id
     */
    void delete(String collectionName,String id);

    /**
     * logical delete
     * @param collectionName
     * @param query
     */
    void delete(String collectionName,Query query);

    /**
     * logical delete
     * @param collectionName
     * @param query
     */
    void delete(String collectionName,IMongoQuery query);

    void deletePermanently(String collectionName,String id);

    void deletePermanently(String collectionName,Query query);

    void deletePermanently(String collectionName,IMongoQuery query);

    JPage<? extends Map> page(String collectionName, Query query, SimplePageRequest page);

    <T> JPage<T> page(String collectionName, Query query, SimplePageRequest page,Class<T> clazz);

    JPage<? extends Map> page(String collectionName, IMongoQuery query, SimplePageRequest page);

    <T> JPage<T> page(String collectionName, IMongoQuery query, SimplePageRequest page,Class<T> clazz);

    List<? extends Map> list(String collectionName, Query query);

    <T> List<T> list(String collectionName, Query query,Class<T> clazz);

    List<? extends Map> list(String collectionName, IMongoQuery query);

    <T> List<T> list(String collectionName, IMongoQuery query,Class<T> clazz);

    Map one(String collectionName, Query query);

    <T> T one(String collectionName, Query query,Class<T> clazz);

    Map one(String collectionName, IMongoQuery query);

    <T> T one(String collectionName, IMongoQuery query,Class<T> clazz);

    Map one(String collectionName, String id);

    <T> T one(String collectionName, String id,Class<T> clazz);

    /**
     * like truncate instruction
     * @param collectionName
     */
    void dropCollection(String collectionName);

}
