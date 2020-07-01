package medical_project.service.common.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medical_project.dao.common.PatientCategoryDao;
import medical_project.entity.common.PatientCategory;
import medical_project.service.common.PatientCategoryService;

@Service
public class PatientCategoryServiceImpl implements PatientCategoryService {

	@Autowired
	private PatientCategoryDao patientCategoryDao;
	
	@Override
	public int add(PatientCategory patientCategory) {
		// TODO Auto-generated method stub
		return patientCategoryDao.add(patientCategory);
	}

	@Override
	public int edit(PatientCategory patientCategory) {
		// TODO Auto-generated method stub
		return patientCategoryDao.edit(patientCategory);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return patientCategoryDao.delete(id);
	}

	@Override
	public List<PatientCategory> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return patientCategoryDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return patientCategoryDao.getTotal(queryMap);
	}

	@Override
	public PatientCategory findById(Long id) {
		// TODO Auto-generated method stub
		return patientCategoryDao.findById(id);
	}

}
