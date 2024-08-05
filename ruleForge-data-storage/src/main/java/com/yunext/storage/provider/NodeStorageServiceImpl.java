package com.yunext.storage.provider;

import cn.hutool.core.collection.CollectionUtil;
import com.yunext.api.dto.BasicNodeQueryDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.service.storage.NodeStorageService;
import com.yunext.common.base.BasicNode;
import com.yunext.common.utils.ModelMapperUtil;
import com.yunext.common.utils.StringUtil;
import com.yunext.storage.dao.BasicNodeDao;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/12 15:45
 */
@DubboService
public class NodeStorageServiceImpl implements NodeStorageService {

    @Resource
    private BasicNodeDao basicNodeDao;

    @Override
    public ResultDto<?> save(List<BasicNode<?, ?>> nodeList) {
        if (CollectionUtil.isEmpty(nodeList)) {
            return ResultDto.success();
        }
        List<String> existing = basicNodeDao.selectAllId();
        List<String> newInput = nodeList.stream().map(BasicNode::getId).toList();
        //差集-待删除的节点
        List<String> delete = CollectionUtil.subtractToList(existing, newInput);
        //类型擦除
        List<BasicNode> list = nodeList.stream().map(item -> (BasicNode) item).toList();
        List<BasicNode> insert = basicNodeDao.save(list);
        basicNodeDao.removeByIds(delete);
        return ResultDto.excute(!insert.isEmpty());
    }

    @Override
    public BasicNode<?, ?> findOne(String id) {
        return basicNodeDao.findById(id);
    }

    @Override
    public List<BasicNode<?, ?>> list(BasicNodeQueryDto queryDto) {
        Query query = this.createQuery(queryDto);
        List<BasicNode> basicNodeList = Optional.ofNullable(basicNodeDao.find(query)).orElse(new ArrayList<>());
        return basicNodeList.stream().map(d -> {
            BasicNode<?, ?> basicNode = ModelMapperUtil.map(d, BasicNode.class);
            return basicNode;
        }).collect(Collectors.toList());
    }

    private Query createQuery(BasicNodeQueryDto queryDto) {
        Query query = new Query();
        if (StringUtil.isNotEmpty(queryDto.getNodeType())) {
            query.addCriteria(Criteria.where("type").is(queryDto.getNodeType()));
        }
        if (StringUtil.isNotEmpty(queryDto.getComponentName())) {
            query.addCriteria(Criteria.where("component").is(queryDto.getComponentName()));
        }
        return query;
    }
}
