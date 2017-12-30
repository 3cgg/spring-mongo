package me.libme.module.spring.mongo;

/**
 * Created by J on 2017/7/31.
 */
public interface CollectionNameValidator {

    void validate(String collectionName) throws IllegalArgumentException;
}
