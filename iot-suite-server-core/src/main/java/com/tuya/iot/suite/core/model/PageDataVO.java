package com.tuya.iot.suite.core.model;

import com.tuya.iot.suite.core.model.PageVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Description 这个类用的下划线风格，以后用驼峰风格。
 * @see PageVO
 *
 * @author Chyern
 * @since 2021/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class PageDataVO<T> implements Serializable {
    private static final long serialVersionUID = -2347968397270730090L;

    private Integer page_no;

    private Integer page_size;

    private Integer total;

    private List<T> data;
}
