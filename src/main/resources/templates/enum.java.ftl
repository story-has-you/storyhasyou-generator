
import com.storyhasyou.kratos.enums.BaseEnum;
import lombok.AllArgsConstructor;


/**
* @author ${enum.author} created by ${enum.date}
*/
@AllArgsConstructor
public class ${enum.enumName} implements BaseEnum<${enum.baseEnumType}> {

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