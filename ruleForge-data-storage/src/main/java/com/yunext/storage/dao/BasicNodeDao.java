package com.yunext.storage.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.mongodb.client.result.DeleteResult;
import com.yunext.common.base.BasicNode;
import com.yunext.common.mongodb.BaseDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/12 15:39
 */
@Repository
public class BasicNodeDao extends BaseDao<BasicNode> {


    public BasicNodeDao() {
        super(BasicNode.class);
    }

    public List<String> selectAllId() {
        Query query = new Query();
        query.fields().include("id");
        return Optional.ofNullable(this.find(query)).orElse(new ArrayList<>())
                .stream().map(BasicNode::getId).toList();
    }

    public boolean removeByIds(List<String> delete) {
        if (CollectionUtil.isEmpty(delete)) {
            return false;
        }
        long result = this.remove(new Query().addCriteria(Criteria.where("_id").in(delete)));
        return result > 0;
    }

    public List<BasicNode> save(List<BasicNode> basicNodeList) {
        List<BasicNode> result = new ArrayList<>();
        if (basicNodeList != null && !basicNodeList.isEmpty()) {
            for (BasicNode basicNode : basicNodeList) {
                result.add(this.save(basicNode));
            }
        }
        return result;
    }
}
