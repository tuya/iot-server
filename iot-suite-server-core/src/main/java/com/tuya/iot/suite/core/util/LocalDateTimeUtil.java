package com.tuya.iot.suite.core.util;

import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/18
 */
public class LocalDateTimeUtil {


    /**
     * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String convertTimeToString(Long time){
        if (StringUtils.isEmpty(time)){
            return null;
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd
     */
    public static String convertTimeToStringYMD(Long time){
        if (StringUtils.isEmpty(time)){
            return null;
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }


    /**
     * 将字符串转日期成Long类型的时间戳，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static Long convertTimeToLong(String time) {
        if (StringUtils.isEmpty(time)){
            throw new IllegalArgumentException("时间参数异常！");
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(time, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将字符串转日期成Long类型的时间戳，格式为：yyyy-MM-dd
     */
    public static Long convertTimeToLongYMD(String time) {
        if (StringUtils.isEmpty(time)){
            throw new IllegalArgumentException("时间参数异常！");
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime parse = LocalDateTime.parse(time, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将日期转换为字符串，格式为：yyyy-MM-dd HH:mm:ss
     * @param localDateTime
     * @return
     */
    public static  String convertDateToString(LocalDateTime localDateTime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = dtf.format(localDateTime);
        return dateTime;
    }

    /**
     * 将日期转换为字符串，格式为：yyyy-MM-dd
     * @param localDateTime
     * @return
     */
    public static  String convertDateToStringYMD(LocalDateTime localDateTime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTime = dtf.format(localDateTime);
        return dateTime;
    }

    /**
     * 将字符串转换为日期，格式为：yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public static LocalDateTime convertStringToDate(String time){
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, dft);
        return dateTime;
    }

    /**
     * 将字符串转换为日期，格式为：yyyy-MM-dd
     * @param time
     * @return
     */
    public static LocalDateTime convertStringToDateYMD(String time){
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDateTime.parse(time, dft);
        return dateTime;
    }

    /**
     * 取本月第一天
     */
    public static LocalDate firstDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 取本月第N天
     */
    public static LocalDate dayOfThisMonth(int n) {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(n);
    }

    /**
     * 取本月最后一天
     */
    public static LocalDate lastDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 取本月第一天的开始时间
     */
    public static LocalDateTime startOfThisMonth() {
        return LocalDateTime.of(firstDayOfThisMonth(), LocalTime.MIN);
    }

    /**
     * 取本月最后一天的结束时间
     */
    public static LocalDateTime endOfThisMonth() {
        return LocalDateTime.of(lastDayOfThisMonth(), LocalTime.MAX);
    }

    /**
     * 计算两个时间的时间差（秒）
     *
     * @param from
     * @param to
     * @return
     */
    public static Long calculateSecondBetween(LocalDateTime from, LocalDateTime to) {
        return durationBetween(from, to).getSeconds();
    }

    public static Duration durationBetween(LocalDateTime from, LocalDateTime to) {
        return Duration.between(from, to);
    }
}
