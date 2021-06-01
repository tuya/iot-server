package com.tuya.iot.suite.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Description 分页对象
 *
 * @author benguan
 * @since 2021/6/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageVO<T> implements Serializable {
    private static final long serialVersionUID = 1;

    private Integer pageNo;

    private Integer pageSize;

    private Integer total;

    private List<T> data;
}
