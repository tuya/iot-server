package com.tuya.iot.suite.ability.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月03日 14:54
 */
@Getter
@Setter
@ToString
@SuperBuilder
public class PageRequestInfo implements Serializable {

    /**
     * Integer	每页容量	是
     */
    @SerializedName("pageSize")
    private Integer pageSize ;
    /**
     * Integer	当前页码	是
     */
    @SerializedName("pageNum")
    private Integer pageNum ;

}
