package katachi.spring.exercise.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class updateForm {

	private String id;

	@Length(min = 0, max = 100)

	private String taskName;
	private String userName;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date expireDate;
	private Integer check;
}
