package interfaces;

import com.entity.Categories;

public interface ICategories {
	
	public void create(Categories categories);
	public Categories retrieve(int entry_id);
	public void update(Categories categories);
	public void delete(Categories categories);

}
