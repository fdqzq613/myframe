package com.some.web.page;

import com.some.common.exception.RespException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 简单包一层 分页属性设置
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月24日
 * @since  JDK1.6
 */
public class CommonPageRequest<T> extends PageRequest{
	private static int defaultPage = 0;
	private static int defaultSize = 10;
	//by:qzq 2017年4月24日 
		private static final long serialVersionUID = 1L;
		
	private Specification<T> specification;
	
	public CommonPageRequest(int page, int size, Sort sort) {
		super(page, size, sort);
	}
	public CommonPageRequest() {
		super(defaultPage, defaultSize);
	}
	public CommonPageRequest(int page, int size) {
		super(page, size);
	}
	
	
	public Specification<T> getSpecification(){
		return specification;
	}
	/**
	 * 提供自定义直接自定义实现Specification
	 * @param specification
	 * @author qzq
	 * @date 2018年4月11日 上午9:49:22
	 */
	public void setSpecification(Specification<T> specification) {
		this.specification = specification;
	}
	public Pageable getPageable(){
		return this;
	}
	/**
	 * 添加查询条件
	 * @param expression
	 * @author qzq
	 */
	public void add(IExpression expression){
		if(specification==null){
			specification = new CommonSpecification<T>();
		}
		if(!(specification instanceof CommonSpecification)){
			throw new RespException("该方法只支持CommonExpression类");
		}
		
		((CommonSpecification<T>)specification).add(expression);
	}
}
