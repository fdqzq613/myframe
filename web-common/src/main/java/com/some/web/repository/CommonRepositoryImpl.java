package com.some.web.repository;

import com.some.web.page.CommonPageRequest;
import com.some.web.page.DefaultPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * s
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
public class CommonRepositoryImpl <T, ID extends Serializable> extends SimpleJpaRepository<T, ID> 
implements CommonRepository<T, ID> {
	/**
	 * 兼容旧版用 
	 * @param id
	 * @return
	 * @author qzq
	 * @date 2019年1月21日 上午10:47:19
	 */
	@Override
	public T findOne(ID id){
		Optional<T> o =  findById(id);
		return o.isPresent()?o.get():null;
	}
	

	public CommonRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	@Override
	public Page<T> findAll(CommonPageRequest pageRequest){
		return findAll(pageRequest.getSpecification(), pageRequest);
	}
	@Override
	public Page<T> findAll(Pageable pageable) {

		if (null == pageable) {
			return new DefaultPage<T>(findAll());
		}

		return findAll((Specification<T>) null, pageable);
	}
	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {

		TypedQuery<T> query = getQuery(spec, pageable);
		return pageable == null ? new DefaultPage<T>(query.getResultList()) : readPage(query, pageable, spec);
	}
	@Override
	protected Page<T> readPage(TypedQuery<T> query, Pageable pageable, @Nullable Specification<T> spec) {
		return readPage(query, getDomainClass(), pageable, spec);
	}
	@Override
	protected <S extends T> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable,
			Specification<S> spec) {

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Long total = executeCountQuery(getCountQuery(spec, domainClass));
		List<S> content = total > pageable.getOffset() ? query.getResultList() : Collections.<S> emptyList();

		return new DefaultPage<S>(content, pageable, total);
	}
	
	private static Long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query);

		List<Long> totals = query.getResultList();
		Long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}

}
