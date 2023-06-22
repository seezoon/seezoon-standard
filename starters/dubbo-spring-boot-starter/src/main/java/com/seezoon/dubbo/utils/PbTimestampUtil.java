package com.seezoon.dubbo.utils;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * pb 时间转化
 *
 * @author dfenghuang
 * @date 2023/6/21 23:09
 */
public class PbTimestampUtil {

    public static Timestamp to(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).build();
    }

    public static Timestamp to(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).build();
    }

    public static LocalDate toLocalDate(Timestamp timestamp) {
        if (null == timestamp) {
            return null;
        }
        // 将 Timestamp 转换为 Instant
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (null == timestamp) {
            return null;
        }
        // 将 Timestamp 转换为 Instant
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
