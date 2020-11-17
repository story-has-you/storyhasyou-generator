package com.storyhasyou.generator;

import lombok.Data;

import java.util.List;

/**
 * The type Enum generate model.
 *
 * @author 方曦 created by 2020/11/17
 */
@Data
public class EnumGenerateModel {

    /**
     * 枚举字段
     */
    private String column;
    /**
     * The Base enum type.
     */
    private String baseEnumType;
    /**
     * The Enum name.
     */
    private String enumName;
    /**
     * 作者
     */
    private String author;
    /**
     * 日期
     */
    private String date;

    /**
     * 表名
     */
    private String tableName;

    private List<Element> elements;

    @Data
    public static final class Element {
        private Object code;
        private String message;
        private String chinese;
    }

}
