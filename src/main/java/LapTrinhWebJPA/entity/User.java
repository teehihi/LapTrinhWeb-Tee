package LapTrinhWebJPA.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId")
	private int userId;

	@Column(name = "Username", columnDefinition = "varchar(50) NOT NULL", unique = true)
	private String username;

	@Column(name = "Password", columnDefinition = "varchar(100) NOT NULL")
	private String password;

	@Column(name = "Fullname", columnDefinition = "nvarchar(100)")
	private String fullname;

	@Column(name = "Email", columnDefinition = "varchar(100)")
	private String email;

	@Column(name = "Phone", columnDefinition = "varchar(15)")
	private String phone;

	@Column(name = "Role")
	private int role;

	@Column(name = "CreateDate")
	private Date createDate;
}