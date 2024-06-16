package gestaocomercial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

import gestaocomercial.dominio.Cliente;
import gestaocomercial.dominio.Item;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;

public class DAO<T> implements AutoCloseable {
    private Class<T> classe;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public DAO(Class<T> classe) throws PersistenceException {
        this.classe = classe;
        entityManagerFactory = Persistence.createEntityManagerFactory("GestaoComercial");
        entityManager = entityManagerFactory.createEntityManager();
    }

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
    
    public Long adicionaRetornaId(T t) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
            // Após a persistência, retornar o ID da entidade
            return getEntityId(t);
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
    }

    public List<T> listaTodos() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));
        List<T> list = entityManager.createQuery(query).getResultList();
        return list;
    }

    private Long getEntityId(T t) {
        if (t instanceof Produto) {
            return ((Produto) t).getId();
        } else if (t instanceof Cliente) {
            return ((Cliente) t).getId();
        } else if (t instanceof Item) {
            return ((Item) t).getId();
        } else if (t instanceof Venda) {
            return ((Venda) t).getId();
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + t.getClass());
        }
    }

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
    }
}
