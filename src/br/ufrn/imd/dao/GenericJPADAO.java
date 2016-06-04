package br.ufrn.imd.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class GenericJPADAO<T, F> implements IDataAcess<T, F> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T salvar(T object){
		entityManager.persist(object);
		return object;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T atualizar(T object){
		object = entityManager.merge(object);
		return object;
	}
			
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
