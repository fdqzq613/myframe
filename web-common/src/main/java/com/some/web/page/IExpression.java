package com.some.web.page;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 操作表达式
 * 
 * 功能说明:
 *
 * @Copyright: 优芽网络科技-版权所有
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
public interface IExpression {
	//操作符号
	 public enum Operator {  
	        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR ,IN,RIGHT_LIKE,BETWEEN
	    }  ;
	    //生成操作语句
	    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                                     CriteriaBuilder builder);
}
