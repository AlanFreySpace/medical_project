package medical_project.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import medical_project.entity.admin.Authority;


@Repository
public interface AuthorityDao {
	public int add(Authority authority);
	public int deleteByRoleId(Long roleId);
	public List<Authority> findListByRoleId(Long roleId);
}
