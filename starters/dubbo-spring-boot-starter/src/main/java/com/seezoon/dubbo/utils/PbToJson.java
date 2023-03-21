package com.seezoon.dubbo.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

/**
 * pb 转换json 工具
 *
 * @author dfenghuang
 * @date 2023/3/21 23:56
 */
public class PbToJson {

    public static String toJson(Message message) {
        if (null == message) {
            throw new IllegalArgumentException("message must be not null");
        }
        try {
            return JsonFormat.printer().print(message);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
