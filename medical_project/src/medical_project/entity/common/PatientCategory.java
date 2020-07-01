package medical_project.entity.common;

import org.springframework.stereotype.Component;

@Component
public class PatientCategory {
	private Long id;//病人分类id
	private Long parentId;//分类父id
	private String name;//病人分类名称
	private String tags;//分类标签，用来标记识别分类的层级关系
	private String remark;//病人分类备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long get_parentId() {
		return parentId;
	}
}
