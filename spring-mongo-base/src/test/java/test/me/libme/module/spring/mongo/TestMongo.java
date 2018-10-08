package test.me.libme.module.spring.mongo;

import me.libme.kernel._c._m.JModel;
import me.libme.kernel._c.json.JJSON;
import me.libme.kernel._c.util.JDateUtils;
import me.libme.kernel._c.util.JUniqueUtils;
import me.libme.module.spring.mongo.DocumentOperations;
import me.libme.module.spring.mongo.MongoCfg;
import me.libme.module.spring.mongo.MongoFlatQuery;
import me.libme.module.spring.mongo.MongoModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by J on 2017/8/1.
 */
public class TestMongo extends _Test {


    private String collectionName="test";

    @Autowired
    private MongoCfg mongoCfg;


    @Autowired
    private DocumentOperations documentOperations;

    public  static class Person implements JModel{

        private String uniqueId;

        private  String name ;

        private int age;

        private String deleted;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }
    }


    @Before
    public void dropCollection(){
        documentOperations.dropCollection(collectionName);
    }


    @Before
    public void prepared(){

        List<Person> persons=new ArrayList<>();
        Person person1=new Person();
        person1.setName("J");
        person1.setAge(20);
        person1.setUniqueId(JUniqueUtils.unique());
        person1.setDeleted("N");
        persons.add(person1);

        for(int i=0;i<10;i++){
            Person person=new Person();
            person.setUniqueId(JUniqueUtils.unique());
            person.setName("J-"+new Random(i).nextInt(999));
            person.setAge(20+new Random(i).nextInt(999));
            person.setDeleted("N");
            persons.add(person);
        }

        List<MongoModel> mongoModels= new ArrayList<>();
        for(int i=0;i<10;i++){
            MongoModel mongoModel=new MongoModel();
            mongoModel.operations()
                    .setString("mongomodel","Y")
                    .setString("name","J-"+new Random(i).nextInt(999))
                    .setLong("age",30l+new Random(i).nextLong())
                    .setNumber("height",1.75+new Random(i).nextDouble())
                    .setString(mongoCfg.primaryKey(),JUniqueUtils.unique())
                    .setString(mongoCfg.deletedKey(),"N");
            mongoModels.add(mongoModel);
        }

        List<JModel> data=new ArrayList<>();
        data.addAll(persons);
        data.addAll(mongoModels);
        documentOperations.insert(collectionName,data);

    }

    @Test
    public void insert(){

        System.out.println("------------------INSERT TEST END-----------------");

    }




    @Test
    public void find(){

        MongoFlatQuery mongoQuery= MongoFlatQuery.get()
                .regex("name","^J.*");

        List<? extends Map> list= documentOperations.list(collectionName,mongoQuery);
        System.out.println("-----------------------LIST OUT BEGIN-------------------");
        list.forEach(one->{
            System.out.println(JJSON.get().format(one));
        });
        System.out.println("-----------------------LIST OUT END-------------------");

        System.out.println("------------------FIND TEST END-----------------");
    }


    @Test
    public void update(){

        MongoFlatQuery mongoQuery= MongoFlatQuery.get()
                .is("age",20);
        Map entity= documentOperations.one(collectionName,mongoQuery);
        Object id=entity.get(mongoCfg.primaryKey());
        if(id==null){
            id=entity.get("_id");
        }
        MongoModel mongoModel=new MongoModel();
        mongoModel.operations().setString("name","updated--"+ JDateUtils.format(new Date()));
        documentOperations.update(collectionName,String.valueOf(id),mongoModel);
        System.out.println("------------------UPDATE TEST END-----------------");
    }


    @Test
    public void delete(){

        MongoFlatQuery mongoQuery= MongoFlatQuery.get()
                .is("age",20);
        Map entity= documentOperations.one(collectionName,mongoQuery);
        Object id=entity.get(mongoCfg.primaryKey());
        if(id==null){
            id=entity.get("_id");
        }

        documentOperations.delete(collectionName,String.valueOf(id));

        System.out.println("------------------DELETE TEST END-----------------");


    }


    @Test
    public void deletePermanently(){

        MongoFlatQuery mongoQuery= MongoFlatQuery.get()
                .is("age",20);
        Map entity= documentOperations.one(collectionName,mongoQuery);
        Object id=entity.get(mongoCfg.primaryKey());
        if(id==null){
            id=entity.get("_id");
        }

        documentOperations.deletePermanently(collectionName,String.valueOf(id));

        System.out.println("------------------DELETE PERMANENTLY TEST END-----------------");

    }

























}
