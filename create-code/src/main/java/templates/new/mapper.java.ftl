package ${package.Mapper};

import ${package.Entity}.${entity};
import com.some.web.repository.BaseDefaultMapper;

/**
 * <p>
 * ${table.comment} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends BaseDefaultMapper<${entity}> {

}
</#if>
