package com.some.web.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.some.common.page.IPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 功能说明: 默认前端返回page jpa方式
 * 
 * @version V1.0
 * @author qzq
 * @date 2018年6月25日
 * @since JDK1.6
 */
@JsonIgnoreProperties({ "content", "sort", "number", "totalPages", "totalElements", "numberOfElements" })
public class DefaultPage<T> extends PageImpl<T> implements IPage<T> {
	// by:qzq 2018年6月25日
	private static final long serialVersionUID = 6530591002231278758L;
	// 每页条数
	private int pageCount = 10;
	// 查询当前页码
	private int page = 1;
	// 分页结果集
	private List<T> list = new ArrayList<T>();
	// 总条数
	private long total;

	public DefaultPage() {
		super(new ArrayList<>());
	}

	public DefaultPage(List<T> content, Pageable pageable, long total) {

		super(content, pageable, total);

		this.page = this.getNumber() + 1;
		this.pageCount = this.getSize();
		this.total = this.getTotalElements();
		this.list = this.getContent();

	}

	/**
	 * Creates a new {@link PageImpl} with the given content. This will result
	 * in the created {@link Page} being identical to the entire {@link List}.
	 * 
	 * @param content
	 *            must not be {@literal null}.
	 */
	public DefaultPage(List<T> content) {
		super(content);
		this.page = this.getNumber() + 1;
		this.pageCount = this.getSize();
		this.total = this.getTotalElements();
		this.list = this.getContent();
	}

	@JSONField(serialize = false)
	@Override
	public List<T> getContent() {
		return super.getContent();
	}

	@JSONField(serialize = false)
	@Override
	public Pageable getPageable() {
		return super.getPageable();
	}

	@JSONField(serialize = false)
	@Override
	public int getNumberOfElements() {
		return super.getNumberOfElements();
	}

	@JSONField(serialize = false)
	@Override
	public long getTotalElements() {
		return super.getTotalElements();
	}

	@JSONField(serialize = false)
	@Override
	public int getNumber() {
		return super.getNumber();
	}

	@JSONField(serialize = false)
	@Override
	public Sort getSort() {
		return super.getSort();
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return
	 * @author qzq
	 * @date 2020年4月27日 上午11:23:56
	 */
	@Override
	public int getSize() {

		return this.pageCount;
	}

	/**
	 * @return
	 * @author qzq
	 * @date 2020年4月27日 上午11:24:53
	 */
	@Override
	public int getTotalPages() {

		return getPageCount() == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageCount());
	}
}
