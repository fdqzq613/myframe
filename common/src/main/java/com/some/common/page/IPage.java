package com.some.common.page;

import java.util.List;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月27日
 */
public interface IPage<T> {
	public int getPageCount();

	public void setPageCount(int pageCount);

	public int getPage();

	public void setPage(int page);

	public List<T> getList();

	public void setList(List<T> list) ;

	public long getTotal() ;

	public void setTotal(long total) ;

	public default int getSize(){
		return getPageCount();
		
	}

	public default int getTotalPages(){
		return getPageCount() == 0 ? 1 : (int) Math.ceil((double) getTotal() / (double) getPageCount());
	}
}
