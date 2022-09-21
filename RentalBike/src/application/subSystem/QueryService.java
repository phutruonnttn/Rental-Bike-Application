package application.subSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import application.subSystem.Interface.IQueryService;

public class QueryService implements IQueryService{
	protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentalBike");
	protected EntityManager entityManager = entityManagerFactory.createEntityManager();

	@Override
	public List<Object[]> ExecuteQuery(String sql, Map<String, Object> param) {
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createQuery(sql);
			if(param != null) {
				Set<String> set = param.keySet();
				for (String key : set) {
					query = query.setParameter(key, param.get(key));
				}
			}
			result = query.getResultList();
			entityManager.getTransaction().commit();
			System.out.println("Excecute query success");
		} catch(RuntimeException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}

}
