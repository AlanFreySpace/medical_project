package medical_project.entity.common;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Patient {
	private Long id;//患者id
	private Long patientCategoryId;//分类id
	private String name;//患者姓名
	private String tags;//分类标签，用来按照患者分类搜索
	private String imageUrl;//患者照片
	//private Double price;//图书价格
	//private int stock;//图书库存
	//private int sellNum;//销量
	//private int viewNum;//浏览量
	//private int commentNum;//评论数
	private String content;//患者详情
	private Date createTime;//首次就诊时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatientCategoryId() {
		return patientCategoryId;
	}
	public void setPatientCategoryId(Long patientCategoryId) {
		this.patientCategoryId = patientCategoryId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
