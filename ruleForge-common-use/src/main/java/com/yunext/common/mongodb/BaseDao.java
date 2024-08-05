package com.yunext.common.mongodb;

import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/8/5 11:10
 */
public class BaseDao<T> {

    @Resource
    private MongoTemplate mongoTemplate;

    private final Class<T> clazz;

    public BaseDao(Class<T> clazz) {
        this.clazz = clazz;
    }


    public T insert(T t) {
        return this.mongoTemplate.insert(t);
    }

    public T save(T t) {
        return this.mongoTemplate.save(t);
    }

    public long removeById(String id) {
        return this.mongoTemplate.remove(new Query().addCriteria(Criteria.where("_id").is(id)), clazz).getDeletedCount();
    }

    public long remove(Query query) {
        return this.mongoTemplate.remove(query, clazz).getDeletedCount();
    }

    public List<T> find(Query query) {
        return this.mongoTemplate.find(query, clazz);
    }

    public T findById(String id) {
        return this.mongoTemplate.findById(id, clazz);
    }
}
