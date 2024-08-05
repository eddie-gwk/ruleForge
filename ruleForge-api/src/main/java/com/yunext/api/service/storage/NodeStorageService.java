package com.yunext.api.service.storage;


import com.yunext.api.dto.BasicNodeQueryDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.common.base.BasicNode;
import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：节点保存服务
 * @date ：Created in 2024/7/12 15:44
 */
public interface NodeStorageService {

    ResultDto<?> save(List<BasicNode<?, ?>> nodeList);

    BasicNode<?, ?> findOne(String id);

    List<BasicNode<?, ?>> list(BasicNodeQueryDto queryDto);
}
