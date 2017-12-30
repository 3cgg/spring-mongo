package me.libme.module.spring.mongo;

import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by J on 2017/8/1.
 */
public interface IMongoQuery {

    Query query() throws RuntimeException;

}
