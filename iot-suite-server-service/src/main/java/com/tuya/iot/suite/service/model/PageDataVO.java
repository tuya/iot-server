package com.tuya.iot.suite.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDataVO<T> implements Serializable {
    private static final long serialVersionUID = -2347968397270730090L;

    private Integer page_no;

    private Integer page_size;

    private Integer total;

    private List<T> data;
}
