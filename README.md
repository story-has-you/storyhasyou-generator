# storyhasyou-generator

基于mybatis-plus的代码生成器，里面用到基类BaseController，BaseService，BaseServiceImpl等类在https://github.com/story-has-you/blades-of-chaos   这个项目里。



新增了枚举生成，如果想要在自动生成枚举，对数据库注释的格式有些规范

例如

```sql
`vip_level` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'vip等级: 1.普通会员, 2.青铜会员, 3.黄金会员, 4.钻石会员',
```

会自动翻译中文生成枚举类

```java
import com.storyhasyou.kratos.enums.BaseEnum;
import lombok.AllArgsConstructor;


/**
 * @author fangxi created by 2020/12/14
 */
@AllArgsConstructor
public enum VipLevelEnum implements BaseEnum<Integer> {


    /**
     * 普通会员
     */
    REGULAR_MEMBER(1, "普通会员"),

    /**
     * 青铜会员
     */
    BRONZE_MEMBER(2, "青铜会员"),

    /**
     * 黄金会员
     */
    GOLD_MEMBER(3, "黄金会员"),

    /**
     * 钻石会员
     */
    DIAMOND_MEMBER(4, "钻石会员"),
    ;

    private final Integer code;
    private final String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
```



需要在代码里面配置需要生成的枚举类

```java
    /**
     * 枚举列
     */
    private static final Map<String, Class<?>> COLUMNS = ImmutableMap.<String, Class<?>>builder()
            .put("表名.字段名", Integer.class)
            .put("t_user.vip_level", Integer.class)
            .build();
```

