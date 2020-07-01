package medical_project.service.common.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medical_project.dao.common.PatientDao;
import medical_project.entity.common.Patient;
import medical_project.service.common.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientDao patientDao;
	
	
	@Override
	public int add(Patient patient) {
		// TODO Auto-generated method stub
		return patientDao.add(patient);
	}

	@Override
	public int edit(Patient patient) {
		// TODO Auto-generated method stub
		return patientDao.edit(patient);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return patientDao.delete(id);
	}

	@Override
	public List<Patient> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return patientDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return patientDao.getTotal(queryMap);
	}

	@Override
	public Patient findById(Long id) {
		// TODO Auto-generated method stub
		return patientDao.findById(id);
	}
}
