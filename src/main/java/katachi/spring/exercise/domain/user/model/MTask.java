package katachi.spring.exercise.domain.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class MTask {

	private String id;
	private String userId;
	private String taskName;
	private Date registrationDate;
	private Date expireDate;
	private Date finshedDate;
	private Integer isDeleted;
	private Date createDateTime;
	private Date updateDateTime;
	private MUser user;

	public boolean isAlert() {
		Date date = new Date();
		if (date.after(expireDate) && finshedDate == null) {
			return true;
		}
		return false;
	}
}
