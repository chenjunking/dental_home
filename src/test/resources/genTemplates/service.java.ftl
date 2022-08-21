package ${package.Service};

import ${package.Other}.${entity}AO;
import ${superServiceClassPackage};

/**
* <p>
    * ${table.comment!} 服务类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}AO>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}AO> {

}
</#if>
