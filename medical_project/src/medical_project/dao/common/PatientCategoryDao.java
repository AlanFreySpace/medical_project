package medical_project.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import medical_project.entity.common.PatientCategory;

@Repository
public interface PatientCategoryDao {
	public int add(PatientCategory patientCategory);
	public int edit(PatientCategory patientCategory);
	public int delete(Long id);
	public List<PatientCategory> findList(Map<String, Object> queryMap);
	public Integer getTotal(Map<String, Object> queryMap);
	public PatientCategory findById(Long id);
}
