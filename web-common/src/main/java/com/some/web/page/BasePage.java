package com.some.web.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 
 * 功能说明:
 * mybatis 基础分页
 * @version V1.0
 * @author qzq
 * @date   2018年11月26日
 * @since  JDK1.6
 */
@JsonIgnoreProperties({ "records", "size","current"})
@SuppressWarnings("serial")
public class BasePage<T> extends Page<T>{
    public BasePage(long current, long size) {
       super(current,size);
    }

    public BasePage(long current, long size, long total) {
    	 super(current,size,total);
    }

	/**
	 * 每页条数
	 * @return Returns the pageCount.
	 * @author qzq
	 * @date   2018年11月26日 下午3:04:23
	 */
	public long getPageCount() {
		return this.getSize();
	}


	/**
	 * 查询当前页码
	 * @return Returns the page.
	 * @author qzq
	 * @date   2018年11月26日 下午3:04:23
	 */
	public long getPage() {
		return this.getCurrent();
	}
	@Override
	@JSONField(serialize = false)
	public List<T> getRecords() {
		return super.getRecords();
	}


	/**
	 * 直接返回records 结果集
	 * @return Returns the list.
	 * @author qzq
	 * @date   2018年11月26日 下午3:04:23
	 */
	public List<T> getList() {
		return this.getRecords();
	}

    
    
}
