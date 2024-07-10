package com.yunext.client.web.api.common;

import com.yunext.api.dto.SelectDto;
import com.yunext.api.vo.ApiResultVo;
import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：开放枚举类
 * @date ：Created in 2024/7/9 17:31
 */
@RestController
@RequestMapping("/api/web/common/enum")
public class EnumController {

    /**
     * 节点类型
     * @return
     */
    @GetMapping(value = "/node/type")
    public ApiResultVo<List<SelectDto>> nodeType() {
        List<SelectDto> selectDtoList = new ArrayList<>();
        for (NodeTypeEnum value : NodeTypeEnum.values()) {
            selectDtoList.add(new SelectDto(value.name(), value.name()));
        }
        return ApiResultVo.success(selectDtoList);
    }

    /**
     * 组件信息
     * @return
     */
    @GetMapping(value = "/component/info")
    public ApiResultVo<List<SelectDto>> componentInfo() {
        List<SelectDto> selectDtoList = new ArrayList<>();
        for (ComponentEnum value : ComponentEnum.values()) {
            selectDtoList.add(new SelectDto(value.name(), value.name()));
        }
        return ApiResultVo.success(selectDtoList);
    }
}
