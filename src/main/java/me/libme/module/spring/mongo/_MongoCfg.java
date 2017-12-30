package me.libme.module.spring.mongo;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by J on 2017/8/1.
 */
@Component
public class _MongoCfg implements MongoCfg {

    @Override
    public String primaryKey() {
        return "uniqueId";
    }


    @Override
    public String deletedKey() {
        return "deleted";
    }

    @Override
    public List<String> invalidKeys() {
        return Arrays.asList("_id"
        ,"id"
        ,"_class"
        ,"class");
    }

}
