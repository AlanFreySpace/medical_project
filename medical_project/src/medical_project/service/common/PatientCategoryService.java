package medical_project.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import medical_project.entity.common.PatientCategory;

@Service
public interface PatientCategoryService {
	public int add(PatientCategory patientCategory);
	public int edit(PatientCategory patientCategory);
	public int delete(Long id);
	public List<PatientCategory> findList(Map<String, Object> queryMap);
	public Integer getTotal(Map<String, Object> queryMap);
	public PatientCategory findById(Long id);
}
