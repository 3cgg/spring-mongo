package me.libme.module.spring.mongo;

import org.springframework.stereotype.Component;

import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by J on 2017/7/31.
 */
@Component
public class _CollectionNameValidator implements CollectionNameValidator {

    @Override
    public void validate(String collectionName) throws IllegalFormatException {
        String patternString="[0-9a-zA-Z-]+";
        Pattern pattern=Pattern.compile(patternString);
        Matcher matcher= pattern.matcher(collectionName);
        if(!matcher.matches()){
            throw new IllegalArgumentException("parameter [collection name] must be "+patternString+".");
        }
    }


}
