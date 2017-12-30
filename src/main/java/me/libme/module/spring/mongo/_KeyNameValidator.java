package me.libme.module.spring.mongo;

import me.libme.kernel._c.json.JJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by J on 2017/8/2.
 */
@Component
public class _KeyNameValidator implements KeyNameValidator{

    @Autowired
    private MongoCfg mongoCfg;

    @Override
    public void validate(String key) throws IllegalArgumentException {

        List<String> invalidKeys=mongoCfg.invalidKeys();
        if(invalidKeys.contains(key)){
            throw new IllegalArgumentException( key +" : fall in invalid keys "+ JJSON.get().format(invalidKeys));
        }


    }



}
