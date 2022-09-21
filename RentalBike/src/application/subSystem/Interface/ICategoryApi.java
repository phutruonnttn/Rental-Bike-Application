package application.subSystem.Interface;

import java.util.List;

import application.model.Category;

public interface ICategoryApi extends IBaseApi<Category>{
	public List<Object[]> getAllCategoryCodeAndTypes();
}
