package test.me.libme.module.spring.mongo;


import me.libme.module.spring.mongo.MongoFlatQuery;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by J on 2017/8/1.
 */
public class _TT {

    public static void main(String[] args) {
        MongoFlatQuery mongoQuery= MongoFlatQuery.get()
                .regex("name","^J.*")
                .is("age",30)
                .lt("height",1.75);
        Query query=mongoQuery.query();


        mongoQuery= MongoFlatQuery.get()
                .regex("name","^J.*")
                .is("age",30)
                .lt("height",1.75)
                .ready()
                .andOperator()
                .is("name","JJ")
                .gt("age",6)
                //.ready().orOperator()
                //.is("name","JJ")
                //.gt("age",6)

        ;

        query=mongoQuery.query();

        mongoQuery= MongoFlatQuery.get()
                .regex("name","^J.*")
                .is("age",30)
                .lt("height",1.75)
                .ready().orOperator()
                .is("name","JJ")
                .gt("age",6)

        ;

        query=mongoQuery.query();

        mongoQuery= MongoFlatQuery.get()
                .ready()
                .andOperator()
                .is("name","JJ")
                .gt("age",6)
                .ready()
                .orOperator()
                .is("name","JJ")
                .gt("age",6)

        ;

        query=mongoQuery.query();

        System.out.println(query);
    }


}
