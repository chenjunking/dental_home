package ${package.Mapper};

import ${package.Other}.${entity}AO;
import ${superMapperClassPackage};
<#if mapperAnnotation>
import org.apache.ibatis.annotations.Mapper;
</#if>

/**
* <p>
    * ${table.comment!} Mapper 接口
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotation>
@Mapper
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}AO>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}AO> {

}
</#if>
