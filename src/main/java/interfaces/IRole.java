package interfaces;

import com.entity.Role;

public interface IRole {
	public void create(Role role);
	public Role retrieve(int id);
	public void update(Role role);
	public void delete(Role role);
}
