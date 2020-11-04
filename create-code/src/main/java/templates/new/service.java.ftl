package ${package.Service};
import com.some.common.exception.RespException;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import com.some.web.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * <p>
 * ${table.comment} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
@Slf4j
public class ${entity}Service extends BaseService<${table.mapperName}, ${entity}> {
    /**
    * 逻辑删除
    * @param ids
    */
 @Transactional
 public void delete(String ids){
    String[] idss = ids.split(",");
    for(String id:idss){
       ${entity}  ${lowEntity} = this.getById(id);
       if( ${lowEntity}==null){
         log.warn("尝试删除项目:{},但是该项目已经被删除",id);
         throw new RespException("存在项目已经被删除");
       }
       ${lowEntity}.setStatus(0);
       ${lowEntity}.setModifyTime(LocalDateTime.now());
       ${lowEntity}.setModifyUserid(getUserId());
       boolean result = this.updateById( ${lowEntity});
       if(result==false){
         log.error("存在项目删除失败,id:{}",id);
         throw new RespException("存在项目删除失败");
      }
    }
 }
}
</#if>
