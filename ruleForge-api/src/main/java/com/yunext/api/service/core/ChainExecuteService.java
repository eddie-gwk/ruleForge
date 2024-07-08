package com.yunext.api.service.core;

import com.yunext.common.base.ResultDto;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:25
 */
public interface ChainExecuteService {

    public ResultDto<?> executeOnce(String chain);
}
