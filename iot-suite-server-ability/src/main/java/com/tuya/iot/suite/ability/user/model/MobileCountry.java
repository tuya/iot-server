package com.tuya.iot.suite.ability.user.model;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年04月20日 14:11
 */
public class MobileCountry implements Serializable {
    /**
     * 简称缩写
     */
    private String abbr;
    /**
     * 中文
     */
    private String chinese;
    /**
     * 繁体中文
     */
    private String chinese_tw;
    /**
     * 编码
     */
    private String code;
    /**
     * 英文
     */
    private String english;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese_tw() {
        return chinese_tw;
    }

    public void setChinese_tw(String chinese_tw) {
        this.chinese_tw = chinese_tw;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return "MobileCountry{" +
                "abbr='" + abbr + '\'' +
                ", chinese='" + chinese + '\'' +
                ", chinese_tw='" + chinese_tw + '\'' +
                ", code='" + code + '\'' +
                ", english='" + english + '\'' +
                '}';
    }
}
