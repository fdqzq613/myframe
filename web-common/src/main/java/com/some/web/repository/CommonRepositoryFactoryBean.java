package com.some.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 通用Repository的构建工厂
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date   2017年4月25日
 * @since  JDK1.6
 */
public class CommonRepositoryFactoryBean <R extends JpaRepository<T, I>, T, I extends Serializable> 
extends JpaRepositoryFactoryBean<R, T, I> {
	 /**
	 * @param repositoryInterface
	 */
	public CommonRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}
	@Override
	    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager ) {
	        return new MyRepositoryFactory(entityManager);
	    }
	 private static class MyRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

	        private final EntityManager entityManager;

	        public MyRepositoryFactory(EntityManager entityManager) {
	            super(entityManager);
	            this.entityManager = entityManager;
	        }


	  
	        @Override
	        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
	            return CommonRepositoryImpl.class;
	        }
	    }
}
