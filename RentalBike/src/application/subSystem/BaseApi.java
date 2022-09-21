package application.subSystem;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import application.subSystem.Interface.IBaseApi;

public class BaseApi<T> implements IBaseApi<T>{
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private final Class<T> typeParameterClass;
	
	public BaseApi(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        if(entityManagerFactory == null) {
        	entityManagerFactory = Persistence.createEntityManagerFactory("RentalBike");
        }
        if(entityManager == null) {
        	entityManager = entityManagerFactory.createEntityManager();
        }
    }
	
	@Override
	public T saveOrUpdate(T entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			System.out.println("Save " + this.typeParameterClass.getName() + " success");
			return entity;
		}catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> getAll() {
		List<T> result = null;
		try {
			entityManager.getTransaction().begin();
			result = entityManager.createQuery("SELECT entity FROM " +  this.typeParameterClass.getName() + " entity", this.typeParameterClass).getResultList();
			entityManager.getTransaction().commit();
			System.out.println("Get all " + this.typeParameterClass.getName() + " success");
		}catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public T getById(int id) {
		T entity = null;
		try {
			entityManager.getTransaction().begin();
			entity = entityManager.find(typeParameterClass, id);
			entityManager.getTransaction().commit();
			System.out.println("get by id " + this.typeParameterClass.getName() + " success");
		}catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public List<T> filter(Map<String, String> param) {
		List<T> result = null;
		try {
			entityManager.getTransaction().begin();
			StringBuilder sql = new StringBuilder("SELECT entity FROM " +  this.typeParameterClass.getName() + " entity"); 
			if(param != null) {
				sql.append(" WHERE ");
				Set<String> set = param.keySet();
				int count = 0;
				for (String key : set) {
					count++;
					sql.append("entity." + key + " = " + "'"+param.get(key)+"'");
					if(count < set.size()) {
						sql.append(" AND ");
					}
				}
			}
			TypedQuery<T> typedQuery = entityManager.createQuery(sql.toString(), this.typeParameterClass);
//			if(param != null) {
//				Set<String> set = param.keySet();
//				for (String key : set) {
//					typedQuery = typedQuery.setParameter(key, param.get(key));
//				}
//			}
			result = typedQuery.getResultList();
			entityManager.getTransaction().commit();
			System.out.println("Get data " + this.typeParameterClass.getName() + " success");
		}catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void delete(T entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
			System.out.println("Delete " + this.typeParameterClass.getName() + " success");
		}catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		entityManager.close();
	    entityManagerFactory.close();
	}

	@Override
	public T getById(Object id) {
		T entity = null;
		try {
			entityManager.getTransaction().begin();
			entity = entityManager.find(typeParameterClass, id);
			entityManager.getTransaction().commit();
			System.out.println("Get by id " + this.typeParameterClass.getName() + " success");
		}catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return entity;
	}

}
