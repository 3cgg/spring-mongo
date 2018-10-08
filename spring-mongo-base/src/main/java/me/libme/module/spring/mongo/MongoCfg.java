package me.libme.module.spring.mongo;

import java.util.List;

/**
 * Created by J on 2017/8/1.
 */
public interface MongoCfg {

    String primaryKey();

    String deletedKey();

    List<String> invalidKeys();

}
