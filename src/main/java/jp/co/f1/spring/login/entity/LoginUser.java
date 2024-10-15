package jp.co.f1.spring.login.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jp.co.f1.spring.login.annotation.Unused;

@Entity
@Table(name = "login_user")
public class LoginUser {

    @Id
    @GeneratedValue
    private UUID userId; // UUID型のID

	@Column(name = "user_name")
	@NotEmpty(message = "ユーザー名は必須です")
	private String username;

	@Column(name = "password")
	@NotEmpty(message = "パスワードは必須です")
	private String password;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Column(name = "email")
	@NotEmpty(message = "メールアドレスは必須です")
	@Email(message = "有効なメールアドレスを入力してください。")
	@Unused
	private String email;
	

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}




}
