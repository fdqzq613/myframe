package com.some.web.page;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用jpa表达式封装
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
public class CommonSpecification<T> implements Specification<T>{
	 //by:qzq 2019年1月21日 
	private static final long serialVersionUID = 9058255948547262717L;
	private List<IExpression> expressions = new ArrayList<IExpression>();
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		 if (!expressions.isEmpty()) {  
	            List<Predicate> predicates = new ArrayList<Predicate>();  
	            for(IExpression c : expressions){  
	                predicates.add(c.toPredicate(root, query,builder));  
	            }  
	           
	            if (predicates.size() > 0) {  
	                return builder.and(predicates.toArray(new Predicate[predicates.size()]));  
	            }  
	        }
		 
	        return builder.conjunction();  
	}  
	
	/**
	 * 额外自定义添加表达式
	 * @param expression
	 * @author qzq
	 * @date 2017年4月25日 下午2:15:18
	 */
	 public void add(IExpression expression){  
	        if(expression!=null){  
	        	expressions.add(expression);  
	        }  
	    }  

}
