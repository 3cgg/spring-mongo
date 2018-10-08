package me.libme.module.spring.mongo;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * Created by J on 2017/8/2.
 */
interface IMongoCriteria {

    Criteria append(Criteria criteria) throws RuntimeException;

}
