package me.libme.module.spring.mongo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * flatting query
 * Created by J on 2017/8/1.
 */
public class MongoFlatQuery implements IMongoQuery ,IMongoCriteria{

    protected Query query;

    protected Criteria criteria;

    protected List<_Criteria> criterias=new ArrayList<>();

    @Override
    public Query query() throws RuntimeException {
        return this.query;
    }

    @Override
    public Criteria append(Criteria criteria) throws RuntimeException {
        return this.criteria;
    }

    public MongoFlatQuery is(String property, Object value){
        IS criteria=new IS();
        criteria.set(property,value);
        criterias.add(criteria);
        return this;
    }

    public MongoFlatQuery lt(String property, Object value){
        LT criteria=new LT();
        criteria.set(property,value);
        criterias.add(criteria);
        return this;
    }

    public MongoFlatQuery lte(String property, Object value){
        LTE criteria=new LTE();
        criteria.set(property,value);
        criterias.add(criteria);
        return this;
    }

    public MongoFlatQuery gt(String property, Object value){
        GT criteria=new GT();
        criteria.set(property,value);
        criterias.add(criteria);
        return this;
    }


    public MongoFlatQuery gte(String property, Object value){
        GTE criteria=new GTE();
        criteria.set(property,value);
        criterias.add(criteria);
        return this;
    }


    public MongoFlatQuery ne(String property, Object value) {
        NE criteria = new NE();
        criteria.set(property, value);
        criterias.add(criteria);
        return this;
    }

    public MongoFlatQuery regex(String property, String value) {
        REGEX criteria = new REGEX();
        criteria.set(property, value);
        criterias.add(criteria);
        return this;
    }

    private abstract class _Criteria implements IMongoCriteria{

        protected String property;

        protected Object value;

        public abstract Criteria criteria();

        protected _Criteria set(String property,Object value){
            this.property=property;
            this.value=value;
            return this;
        }
    }

    private class IS extends _Criteria{
        @Override
        public Criteria criteria() {
            Criteria criteria=Criteria.where(property).is(value);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).is(this.value);
        }
    }

    private class LT extends _Criteria{

        @Override
        public Criteria criteria() {
            Criteria criteria=Criteria.where(property).lt(value);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).lt(this.value);
        }
    }

    private class LTE extends _Criteria{

        @Override
        public Criteria criteria() {
            Criteria criteria=Criteria.where(property).lte(value);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).lte(this.value);
        }
    }

    private class GT extends _Criteria{

        @Override
        public Criteria criteria() {
            Criteria criteria=Criteria.where(property).gt(value);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).gt(this.value);
        }
    }

    private class GTE extends _Criteria{

        @Override
        public Criteria criteria() {
            Criteria criteria=Criteria.where(property).gte(value);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).gte(this.value);
        }
    }


    private class NE extends _Criteria{

        @Override
        public Criteria criteria() {
            Criteria criteria=Criteria.where(property).ne(value);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).ne(this.value);
        }
    }

    private class REGEX extends _Criteria{

        @Override
        public Criteria criteria() {
            Objects.requireNonNull(this.value);
            Pattern pattern=Pattern.compile(String.valueOf(this.value));
            Criteria criteria=Criteria.where(property).regex(pattern);
            return criteria;
        }

        @Override
        public Criteria append(Criteria criteria) {
            return criteria.and(this.property).regex(String.valueOf(this.value));
        }
    }


    /**
     * end up current state , normal -> and -> or -> ... loop back
     * @return
     */
    public Operations ready(){
        Criteria criteria=this.append(this.criteria);
        Operations operations=new Operations();
        operations.query=this.query;
        operations.criteria=criteria;
        return operations;
    }

    /**
     * return normal query {"name":"J" [,"age":10 [,"height":1.75]...]}
     * @return
     */
    public static MongoFlatQuery get(){
        Query query=new Query();
        Criteria criteria=new Criteria();
        MongoFlatQuery mongoQuery=new MongoFlatQuery();
        mongoQuery.query=query;
        mongoQuery.criteria=criteria;
        return mongoQuery.ready().normalOperator();
    }

    public class Operations implements IMongoQuery{
        private Query query;

        private Criteria criteria;

        public MongoFlatQuery normalOperator(){
            NormalOperator normalOperator= new NormalOperator();
            normalOperator.query=this.query;
            normalOperator.criteria=this.criteria;
            return normalOperator;
        }

        public MongoFlatQuery andOperator(){
            AndOperator andOperator=  new AndOperator();
            andOperator.query=this.query;
            andOperator.criteria=this.criteria;
            return andOperator;
        }

        public MongoFlatQuery orOperator(){
            OrOperator orOperator= new OrOperator();
            orOperator.query=this.query;
            orOperator.criteria=this.criteria;
            return orOperator;
        }

        @Override
        public Query query() throws RuntimeException {
            return this.query;
        }
    }

    public class NormalOperator extends MongoFlatQuery implements IMongoQuery,IMongoCriteria{
        @Override
        public Query query() throws RuntimeException {
            /*if(!criterias.isEmpty()){
                Criteria criteria=null;
                for(int i=0;i<criterias.size();i++){
                    _Criteria _criteria=criterias.get(i);
                    if(i==0){
                        criteria=_criteria.criteria();
                    }else{
                        criteria=_criteria.append(criteria);
                    }
                }
                Criteria criteria=new Criteria();
                for(_Criteria _criteria : criterias){
                    criteria=_criteria.append(criteria);
                }

                query.addCriteria(criteria);
            }*/
            Criteria criteria=this.append(this.criteria);
            return query.addCriteria(criteria);
        }

        @Override
        public Criteria append(Criteria criteria) throws RuntimeException {
            criterias.forEach(_criteria->{
                _criteria.append(criteria);
            });
            return criteria;
        }


    }


    public class AndOperator extends MongoFlatQuery implements IMongoQuery,IMongoCriteria{

        @Override
        public Query query() throws RuntimeException {
            /*if(!this.criterias.isEmpty()){
                Criteria criteria=Criteria.where("and-ope-"+ JUniqueUtils.sequence());
                List<Criteria> criterias=new ArrayList<>();
                this.criterias.forEach(_criteria->{
                    criterias.add(_criteria.criteria());
                });

                criteria.andOperator(criterias.toArray(new Criteria[]{}));
                this.query.addCriteria(criteria);
            }*/
            Criteria criteria=this.append(this.criteria);
            return this.query.addCriteria(criteria);
        }

        @Override
        public Criteria append(Criteria criteria) throws RuntimeException {
            if(!this.criterias.isEmpty()){
                List<Criteria> criterias=new ArrayList<>();
                this.criterias.forEach(_criteria->{
                    criterias.add(_criteria.criteria());
                });

                criteria.andOperator(criterias.toArray(new Criteria[]{}));
            }
            return criteria;
        }
    }

    public class OrOperator extends MongoFlatQuery implements IMongoQuery,IMongoCriteria{

        @Override
        public Query query() throws RuntimeException {
            /*if(!this.criterias.isEmpty()){
                Criteria criteria=new Criteria();
                List<Criteria> criterias=new ArrayList<>();

                this.criterias.forEach(_criteria->{
                    criterias.add(_criteria.criteria());
                });

                criteria.orOperator(criterias.toArray(new Criteria[]{}));
                this.query.addCriteria(criteria);
            }*/
            Criteria criteria=this.append(this.criteria);
            return this.query.addCriteria(criteria);
        }


        @Override
        public Criteria append(Criteria criteria) throws RuntimeException {
            if(!this.criterias.isEmpty()){
                List<Criteria> criterias=new ArrayList<>();
                this.criterias.forEach(_criteria->{
                    criterias.add(_criteria.criteria());
                });

                criteria.orOperator(criterias.toArray(new Criteria[]{}));
            }
            return criteria;
        }
    }


}
