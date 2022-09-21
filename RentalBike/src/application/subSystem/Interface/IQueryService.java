package application.subSystem.Interface;

import java.util.List;
import java.util.Map;

public interface IQueryService {
	public  List<Object[]> ExecuteQuery(String sql, Map<String, Object> param);
}
