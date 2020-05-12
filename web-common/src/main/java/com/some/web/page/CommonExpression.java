package com.some.web.page;

import com.some.common.exception.RespException;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.util.List;


/**
 * 通用表达式
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
public class CommonExpression implements IExpression{
	 private String fieldName;       //属性名  
	    private Object value;           //对应值  
	    private Operator operator;      //操作符  
	    
	    public CommonExpression(String fieldName,Object value,Operator operator){
	    	this.fieldName=fieldName;
	    	this.value=value;
	    	this.operator=operator;
	    }
	    

	  /**
	   * 获取表达式Predicate
	   * @param root
	   * @param query
	   * @param builder
	   * @return
	   * @author qzq
	   * @date 2017年4月25日 下午2:07:35
	   */
	@Override
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
	     Path expression = root.get(fieldName);
	        if(fieldName.contains(".")){  
	            String[] names = StringUtils.split(fieldName, ".");  
	            expression = root.get(names[0]);  
	            for (int i = 1; i < names.length; i++) {  
	                expression = expression.get(names[i]);  
	            }  
	        }
		switch (operator) {  
	        case EQ:  
	            return builder.equal(expression, value);  
	        case NE:  
	            return builder.notEqual(expression, value);  
	        case IN:
	        	List list = (List) value;
	        	In<Object> in = builder.in(expression);
                for (int i=0;i<list.size();i++) {
                    in.value(list.get(i));
                }
	        	return in;
	        case LIKE:  
	            return builder.like((Expression<String>) expression, "%" + value + "%"); 
	        case RIGHT_LIKE:  
	            return builder.like((Expression<String>) expression, value + "%"); 
	        case BETWEEN:  
	        	Comparable[] bValue = (Comparable[]) value;
	            return builder.between(expression, bValue[0], bValue[1]) ;
	        case LT:  
	            return builder.lessThan((Expression<Comparable>)expression, (Comparable) value);  
	        case GT:  
	            return builder.greaterThan((Expression<Comparable>)expression, (Comparable) value);  
	        case LTE:  
	            return builder.lessThanOrEqualTo((Expression<Comparable>)expression, (Comparable) value);  
	        case GTE:  
	            return builder.greaterThanOrEqualTo((Expression<Comparable>)expression, (Comparable) value);  
	        default:  
	        	throw new RespException("不支持的表达式！");
	           
	}
	}

}
