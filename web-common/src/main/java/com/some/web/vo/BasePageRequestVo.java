package com.some.web.vo;

import com.some.common.vo.BaseRequestVo;
import io.swagger.annotations.ApiParam;


public class BasePageRequestVo extends BaseRequestVo {


	//页码
	@ApiParam(value="页码",required = true,defaultValue="1",example="1")
	private int page=1;
	//每页条数
	@ApiParam(value="每页条数",required = true,defaultValue="10",example="10")
	private int pageCount=10;
	/**
	 * @return Returns the page.
	 * @author qzq
	 * @date   2018年11月23日 下午2:32:09
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param  page The page to set.
	 * @author qzq
	 * @date   2018年11月23日 下午2:32:09
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return Returns the pageCount.
	 * @author qzq
	 * @date   2018年11月23日 下午2:32:09
	 */
	public int getPageCount() {
		return pageCount;
	}
	/**
	 * @param  pageCount The pageCount to set.
	 * @author qzq
	 * @date   2018年11月23日 下午2:32:09
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
