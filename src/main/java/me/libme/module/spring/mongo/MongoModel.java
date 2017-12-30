package me.libme.module.spring.mongo;

import me.libme.kernel._c._fn.Try;
import me.libme.kernel._c._m.JModel;
import me.libme.kernel._c.bean.SimpleFieldOnClassFinder;
import me.libme.kernel._c.util.DateConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by J on 2017/7/31.
 */
public class MongoModel implements JModel {

    private String collection;

    private Map<String,Object> data=new LinkedHashMap<>();

    private Operations operations=new Operations();

    private Filter filter=new Filter();

    private Directive directive=new Directive();

    Directive directive(){
        return directive;
    }

    public Operations operations(){
        return operations;
    }

    public Filter filter(){
        return filter;
    }

    public static interface Matcher{
        boolean matcher(String key,Object value);
    }

    public class Filter{

        /**
         * FOR {@link JBaseMongoModel}
         */
        private Matcher INSERT=
                (key,value)->{
                return "id".equalsIgnoreCase(key)
                        ||"createId".equalsIgnoreCase(key)
                        ||"createTime".equalsIgnoreCase(key)
                        ||"updateId".equalsIgnoreCase(key)
                        ||"updateTime".equalsIgnoreCase(key)
                        ||"version".equalsIgnoreCase(key);
                };

        /**
         * FOR {@link JBaseMongoModel}
         */
        private Matcher UPDATE=
                (key,value)->{
                    return "id".equalsIgnoreCase(key);
                };


        public Filter filter(Matcher matcher){
            Map<String,Object> retain=new LinkedHashMap<>();
            data.forEach((key,value)->{
                if(!matcher.matcher(key,value)){
                    retain.put(key,value);
                }
            });
            data=retain;
            return this;
        }

        /**
         *
         * @see JBaseMongoModel
         * @return
         */
        public Filter filterInsert(){
            return filter(INSERT);
        }

        /**
         *
         * @see JBaseMongoModel
         * @return
         */
        public Filter filterUpdate(){
            return filter(INSERT);
        }


    }


    public class Operations{

        public Operations setObject(String key,Object value){
            if(Long.class.isInstance(value)){
                return setLong(key,(Long)value);
            }
            if(Date.class.isInstance(value)){
                return setDate(key,(Date)value);
            }
            if(String.class.isInstance(value)){
                return setString(key,(String)value);
            }
            if(Number.class.isInstance(value)){
                return setNumber(key,(Number)value);
            }
            data.put(key,value);
            return this;
        }

        public Operations setLong(String key,Long value){
            data.put(key,value);
            return this;
        }

        public Operations setDate(String key,Date value){
            data.put(key,value);
            return this;
        }

        public Operations setDate(String key,String value){
            return setDate(key,value,null);
        }

        public Operations setDate(String key,String value,DateConverter converter){
            Date date=
                    converter==null?DateConverter.option().convert(value)
                     : converter.convert(value);
            return setDate(key,date);
        }

        public Operations setString(String key,String value){
            data.put(key,value);
            return this;
        }

        public Operations setNumber(String key,Number value){
            data.put(key,value);
            return this;
        }

        public Object get(String key){
            return data.get(key);
        }

        public Object remove(String key){
            return data.remove(key);
        }

        public void collection(String collection){
            MongoModel.this.collection=collection;
        }

        public String collection(){
            return collection;
        }

    }



    class Directive{

        public Update update(){
            Update update=new Update();
            data.forEach((key,value)->{
                update.set(key,value)
                ;
            });
            return update;
        }

        public Map<?,?> insert(){
            return data;
        }

    }

    @Override
    public MongoModel clone() throws CloneNotSupportedException {
        MongoModel mongoModel=new MongoModel();
        mongoModel.data=this.data;
        mongoModel.collection=this.collection;
        return mongoModel;
    }

    public static MongoModel parse(final JModel baseModel) throws Exception{

        if(MongoModel.class.isInstance(baseModel)){
            return ((MongoModel)baseModel).clone();
        }

        MongoModel mongoModel=new MongoModel();
        SimpleFieldOnClassFinder simpleFieldOnClassFinder
                =new SimpleFieldOnClassFinder(baseModel.getClass());
        simpleFieldOnClassFinder.find()
                .forEach(Try.apply(fieldMeta->{
                    Field field=fieldMeta.getField();
                    field.setAccessible(true);
                    Object value=field.get(baseModel);
                    mongoModel.operations.setObject(fieldMeta.getFieldName(),value);
                }));
        if(JBaseMongoModel.class.isInstance(baseModel)){

            mongoModel.operations().collection(collection(baseModel.getClass()));
        }

        return mongoModel;

    }


    public static String collection(Class<?> clazz){
        Document document=clazz.getAnnotation(Document.class);
        return document.collection();
    }



}
