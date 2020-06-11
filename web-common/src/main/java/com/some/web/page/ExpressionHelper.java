package com.some.web.page;

import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 表达式帮助类
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
public class ExpressionHelper {
	 /** 
     * 等于  传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression eq(String fieldName, Object value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.EQ);
    }  
      
    /** 
     * 不等于  传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression ne(String fieldName, Object value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.NE);
    }  
  
    /** 
     * 模糊匹配  传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression like(String fieldName, String value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.LIKE);
    }  
  
 
  
    /** 
     * 大于 传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression gt(String fieldName, Object value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.GT);
    }  
  
    /** 
     * 小于 传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression lt(String fieldName, Object value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.LT);
    }  
  
    /** 
     * 小于等于 传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression lte(String fieldName, Object value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.LTE);
    }  
  
    /** 
     * 大于等于 传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression gte(String fieldName, Object value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.GTE);
    } 
    
	 /** 
     * in查询  传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression in(String fieldName, List value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.IN);
    } 
    
	 /** 
     * 右边like查询('id%')  传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression rightLike(String fieldName, List value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.RIGHT_LIKE);
    } 
    
    /** 
     * between查询 数组大小为2 传空值跳过
     * @param fieldName 
     * @param value 
     * @return 
     */  
    public static CommonExpression between(String fieldName, Comparable[] value) {  
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new CommonExpression (fieldName, value, IExpression.Operator.BETWEEN);
    } 
    /**
     * 自定义表达式 ，允许值为空
     * @param fieldName
     * @param value
     * @param operator
     * @return
     * @author qzq
     * @date 2017年5月25日 下午1:40:43
     */
    public static CommonExpression customExpression(String fieldName, Object value,IExpression.Operator operator) {
        return new CommonExpression (fieldName, value, operator);  
    }  
    
    /**
     * 自定义表达式 完全接口自由定义
     * @param expression
     * @return
     * @author qzq
     * @date 2017年5月25日 下午1:40:43
     */
    public static IExpression customExpression(IExpression expression) {  
        return expression;  
    }  
   
}
