package com.example.Gamesite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.Gamesite.model.User;
import com.example.Gamesite.service.UserService;

// UserValidator is used in registering a new user and changing user details
@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	// Validation for registering a new user
	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		
		// Username validation 
		// Check if it is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		// Check username length is between 6 and 32
		if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		// Check that username is not taken
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}
		
		// Password validation
		// Check if it is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		// Check password length is between 8 and 32
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}
		// Check that the password confirm field is same as password field
		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
		
		// Email validation
		// Check if it is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		// Check that email is not taken
		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Duplicate.userForm.email");
		}
	}
	
	// Validation for changing username 
	public void validateUsername(Object o, Errors errors) {		
		User user = (User) o;

		// Check if it is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		// Check username length is between 6 and 32
		if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		// Check that username is not taken
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}
	}
	
	// Validation for changing password
	public void validatePassword(Object o, Errors errors) {
		User user = (User) o;
		
		// Check if it is empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		// Check password length is between 8 and 32
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}
		// Check that the password confirm field is same as password field
		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
	}
}
