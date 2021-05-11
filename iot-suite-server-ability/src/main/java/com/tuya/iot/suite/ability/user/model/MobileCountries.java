package com.tuya.iot.suite.ability.user.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author mickey
 * @date 2021年04月20日 14:11
 */
public class MobileCountries implements Serializable {
    /**
     * 中国区号码
     */
    private List<MobileCountry> cn;
    /**
     * 国外区号码
     */
    private List<MobileCountry> eu;

    /**
     * 国外区号码
     */
    private List<MobileCountry> in;

    /**
     * 国外区号码
     */
    private List<MobileCountry> us;


    public List<MobileCountry> getCn() {
        return cn;
    }

    public void setCn(List<MobileCountry> cn) {
        this.cn = cn;
    }

    public List<MobileCountry> getEu() {
        return eu;
    }

    public void setEu(List<MobileCountry> eu) {
        this.eu = eu;
    }

    public List<MobileCountry> getIn() {
        return in;
    }

    public void setIn(List<MobileCountry> in) {
        this.in = in;
    }

    public List<MobileCountry> getUs() {
        return us;
    }

    public void setUs(List<MobileCountry> us) {
        this.us = us;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MobileCountries{");
        sb.append("cn=").append(cn);
        sb.append(", eu=").append(eu);
        sb.append(", in=").append(in);
        sb.append(", us=").append(us);
        sb.append('}');
        return sb.toString();
    }
}
