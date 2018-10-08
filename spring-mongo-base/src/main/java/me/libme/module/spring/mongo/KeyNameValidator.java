package me.libme.module.spring.mongo;

/**
 * Created by J on 2017/8/2.
 */
public interface KeyNameValidator {

    void validate(String key) throws IllegalArgumentException;

}
