package jp.co.f1.spring.login.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.f1.spring.login.entity.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, UUID> {

	public LoginUser findByUsernameAndPassword(String username,String password);
	public LoginUser findByBirthdayAndEmail(LocalDate birthday,String email);
	public LoginUser findByUserId(UUID userId);
	public LoginUser findByEmail(String email);
}