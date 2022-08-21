package ${(appObjectPackage)!};

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ${(entityPackage)!};

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${(tableName)!}")
@ApiModel(value="${(entityName)!}AO对象", description="${(tableComment)!}")
public class ${(entityName)!}AO extends ${(entityName)!} {

}
