package application.subSystem;

import java.util.List;

import application.model.Category;
import application.subSystem.Interface.ICategoryApi;
import application.subSystem.Interface.IQueryService;

public class CategoryApi extends BaseApi<Category> implements ICategoryApi{

	private static CategoryApi instance;
	
	private CategoryApi(Class<Category> typeParameterClass) {
		super(typeParameterClass);
	}
	
	public List<Object[]> getAllCategoryCodeAndTypes() {
 		String sql = "SELECT c.categoryCode, c.categoryName FROM Category c";
 		IQueryService queryService = new QueryService();
 		List<Object[]> result = queryService.ExecuteQuery(sql, null);
 		return result;
	}

	public static synchronized CategoryApi getInstance(Class<Category> typeParameterClass) {
		if (instance == null)
			instance = new CategoryApi(typeParameterClass);
		return instance;
	}
}
