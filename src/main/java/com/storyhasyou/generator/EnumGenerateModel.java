package com.storyhasyou.generator;

import lombok.Data;

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

    private String enumPackage;

}
