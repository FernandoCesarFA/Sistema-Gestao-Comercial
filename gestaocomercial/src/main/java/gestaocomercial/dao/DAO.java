package gestaocomercial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements AutoCloseable {
	private Class<T> classe;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	public DAO(Class<T> classe) throws PersistenceException {
		this.classe = classe;
		entityManagerFactory = Persistence.createEntityManagerFactory("GestaoComercial");
		entityManager = entityManagerFactory.createEntityManager();
	}//Construtor
	
	public void adiciona(T t) {
	    try {
	        entityManager.getTransaction().begin();
	        entityManager.merge(t);
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        throw e;
	    }
	}

	public void altera(T t) {
	    try {
	        entityManager.getTransaction().begin();
	        entityManager.merge(t);
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        throw e;
	    }
	}

	public void remove(T t) {
	    try {
	        entityManager.getTransaction().begin();
	        entityManager.remove(entityManager.merge(t));
	        entityManager.getTransaction().commit();
	    } catch (Exception e) {
	        if (entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        throw e;
	    }
	}

	
	public T buscaPorId(Long id) {
		return entityManager.find(classe, id);
	}//buscaPorId()
	
	public List<T> listaTodos(){
		CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		List<T> list = entityManager.createQuery(query).getResultList();
		return list;
	}//listaTodos()
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void close() throws Exception {
		entityManager.close();
		entityManagerFactory.close();
	}//close()
}//DAO