package medical_project.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import medical_project.entity.common.Patient;

@Repository
public interface PatientDao {
	public int add(Patient patient);
	public int edit(Patient patient);
	public int delete(Long id);
	public List<Patient> findList(Map<String, Object> queryMap);
	public Integer getTotal(Map<String, Object> queryMap);
	public Patient findById(Long id);
}
