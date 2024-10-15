package jp.co.f1.spring.login.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.f1.spring.login.entity.LoginUser;
import jp.co.f1.spring.login.repository.LoginUserRepository;

@Controller
public class LoginController {

	@Autowired
	private LoginUserRepository userinfo;

	@GetMapping("/")
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login");
		return mav;
	}

	@PostMapping("/login")
	public ModelAndView loginUser(LoginUser loginUser, BindingResult result, ModelAndView mav) {
		LoginUser user = userinfo.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
		if (ObjectUtils.isEmpty(user)) {
			mav.setViewName("login");
			mav.addObject("error", "ユーザー名またはパスワードが正しくありません");
			return mav;
		}
		mav.addObject("username", user.getUsername());
		mav.setViewName("main");
		return mav;
	}

	@GetMapping("/insert")
	public ModelAndView regist() {
		ModelAndView mav = new ModelAndView("insert");
		mav.addObject("loginUser", new LoginUser());
		mav.addObject("years", generateYears());
		mav.addObject("months", generateMonths());
		mav.addObject("days", generateDays());
		return mav;

	}

	@PostMapping("/insert")
	public ModelAndView userRegist(@ModelAttribute @Valid LoginUser loginUser, BindingResult result,
			ModelAndView mav, RedirectAttributes redirectAttributes,
			int birthdayYear, int birthdayMonth, int birthdayDay) {
		if (result.hasErrors()) {
			mav.addObject("message", "入力内容に誤りがあります。");
			mav.addObject("years", generateYears());
			mav.addObject("months", generateMonths());
			mav.addObject("days", generateDays());
			mav.setViewName("insert");
			return mav;
		}

	    // 生年月日設定
	    LocalDate birthday = LocalDate.of(birthdayYear, birthdayMonth, birthdayDay);
	    loginUser.setBirthday(birthday);
	    
	    // ユーザー情報を保存
	    userinfo.saveAndFlush(loginUser);
	    redirectAttributes.addFlashAttribute("completed", "登録が完了しました。");
	    mav = new ModelAndView("redirect:/");
	    return mav;
	
	}

	@GetMapping("/update")
	public ModelAndView update(ModelAndView mav) {
		mav.setViewName("update");
		mav.addObject("years", generateYears());
		mav.addObject("months", generateMonths());
		mav.addObject("days", generateDays());
		return mav;
	}

	@PostMapping("/update")
    public ModelAndView userUpdate(@ModelAttribute LoginUser loginUser, ModelAndView mav,
    								int birthdayYear, int birthdayMonth, int birthdayDay) {
        LocalDate birthday = LocalDate.of(birthdayYear, birthdayMonth, birthdayDay);
        loginUser.setBirthday(birthday);
        LoginUser user = userinfo.findByBirthdayAndEmail(loginUser.getBirthday(), loginUser.getEmail());
        if(ObjectUtils.isEmpty(user)) {
        	mav.addObject("error", "情報が一致しません。再度お試しください。");
            mav.addObject("years", generateYears());
            mav.addObject("months", generateMonths());
            mav.addObject("days", generateDays());
        	mav.setViewName("update");
        	return mav;
        }
        mav.setViewName("success");
        mav.addObject("message", "ユーザー名:"+user.getUsername()+", パスワード:"+user.getPassword());
    	return mav;
    	
    }


	//年月日のリストを生成する
	private List<Integer> generateYears() {
		List<Integer> years = new ArrayList<>();
		for (int i = 1900; i <= LocalDate.now().getYear(); i++) {
			years.add(i);
		}
		return years;
	}

	private List<Integer> generateMonths() {
		List<Integer> months = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			months.add(i);
		}
		return months;
	}

	private List<Integer> generateDays() {
		List<Integer> days = new ArrayList<>();
		for (int i = 1; i <= 31; i++) {
			days.add(i);
		}
		return days;
	}

}
