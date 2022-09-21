package application.subSystem.Interface;

import java.util.List;
import java.util.Map;

public interface IBaseApi<T> {
	T saveOrUpdate(T entity);
	List<T> getAll();
	T getById(int id);
	T getById(Object id);
	List<T> filter(Map<String, String> param);
	void delete(T entity);
	void close();
}
