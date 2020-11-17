
import com.storyhasyou.kratos.enums.BaseEnum;
import lombok.AllArgsConstructor;


/**
* @author ${enum.author} created by ${enum.date}
*/
@AllArgsConstructor
public enum ${enum.enumName} implements BaseEnum<${enum.baseEnumType}> {

    <#list enum.elements as element>
        <#if enum.baseEnumType == "String">
        /**
         * ${element.chinese}
         */
        ${element.message}("${element.code}", "${element.chinese}"),
        </#if>

        <#if enum.baseEnumType == "Integer">
        /**
         * ${element.chinese}
         */
        ${element.message}(${element.code}, "${element.chinese}"),
        </#if>
    </#list>
    ;

    private final ${enum.baseEnumType} code;
    private final String message;

    @Override
    public ${enum.baseEnumType} getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}