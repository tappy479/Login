package jp.co.f1.spring.login.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.f1.spring.login.entity.LoginUser;
import jp.co.f1.spring.login.repository.LoginUserRepository;

@Component
public class UnusedValidator implements ConstraintValidator<Unused, String> {

    @Autowired
    private LoginUserRepository repo;

    public boolean isValid(String value, ConstraintValidatorContext context) {
    	if(repo==null) {
    		return true;
    	}
        LoginUser email = repo.findByEmail(value); // ここのvalueは入力値
        return email==null;
    }
}
