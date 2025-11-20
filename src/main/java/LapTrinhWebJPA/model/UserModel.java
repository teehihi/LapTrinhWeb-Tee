package LapTrinhWebJPA.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;
	private String passWord;
	private String email;
	private String fullName;
	private String phone;
	private int role;
	private Date createDate;
}