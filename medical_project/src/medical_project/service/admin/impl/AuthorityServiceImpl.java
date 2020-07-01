package medical_project.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medical_project.dao.admin.AuthorityDao;
import medical_project.entity.admin.Authority;
import medical_project.service.admin.AuthorityService;


@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;
	
	@Override
	public int add(Authority authority) {
		// TODO Auto-generated method stub
		return authorityDao.add(authority);
	}

	@Override
	public int deleteByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return authorityDao.deleteByRoleId(roleId);
	}

	@Override
	public List<Authority> findListByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return authorityDao.findListByRoleId(roleId);
	}

}
