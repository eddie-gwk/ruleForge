package com.yunext.api.service.core;

import com.yunext.api.dto.ResultDto;
import com.yunext.common.base.BasicNode;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 9:47
 */
public interface ComponentService {

    ResultDto<?> inject(BasicNode<?, ?> root);

}
