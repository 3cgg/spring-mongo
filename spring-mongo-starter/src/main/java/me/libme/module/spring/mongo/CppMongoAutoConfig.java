package me.libme.module.spring.mongo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Created by J on 2018/9/12.
 */
@ConditionalOnClass({MongoOperations.class})
public class CppMongoAutoConfig {


    @Bean
    public SimpleDocumentOpes simpleDocumentOpes(){
        return new SimpleDocumentOpes();
    }

    @Bean
    public RichInsertMongoModelPipe richInsertMongoModelPipe(){
        return new RichInsertMongoModelPipe();
    }


    @Bean
    public RichUpdateMongoModelPipe richUpdateMongoModelPipe(){
        return new RichUpdateMongoModelPipe();
    }


    @Bean
    public _MongoCfg internalMongoCfg(){
        return new _MongoCfg();
    }


    @Bean
    public _KeyNameValidator keyNameValidator(){
        return new _KeyNameValidator();
    }

    @Bean
    public _CollectionNameValidator collectionNameValidator(){
        return new _CollectionNameValidator();
    }


















}
