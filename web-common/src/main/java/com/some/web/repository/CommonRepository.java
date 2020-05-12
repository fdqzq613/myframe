package com.some.web.repository;

import com.some.web.page.CommonPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 通用Repository
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	
	T findOne(ID id);
    /**
     * 获取分页数据
     * @param pageable
     * @return
     * @author qzq
     * @date 2017年4月25日 下午2:29:12
     */
	Page<T> findAll(Pageable pageable);
	
	Page<T> findAll(Specification<T> specification, Pageable pageable);
	/**
	 * 获取默认分页
	 * @param pageRequest
	 * @return
	 * @author qzq
	 * @date 2017年4月25日 下午3:44:45
	 */
	Page<T> findAll(CommonPageRequest<?> pageRequest);
}
