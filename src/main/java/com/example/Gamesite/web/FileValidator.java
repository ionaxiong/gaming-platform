package com.example.Gamesite.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// FileValidator is used to validate files uploaded for game
@Component
public class FileValidator extends ResponseEntityExceptionHandler implements Validator {

	// Allowed file types
    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg");
    
	@Override
	public boolean supports(Class<?> aClass) {
		return MultipartFile.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		MultipartFile file = (MultipartFile) o;

		// Check that the file is not empty and is allowed type
		if (!file.isEmpty() && !contentTypes.contains(file.getContentType())) {
			errors.rejectValue("image", "Type.game.image");
		}
		
		// Maximum size of file, 10 megabytes
        if (file.getSize() > 10 * 1024 * 1024) {
            errors.rejectValue("image", "Size.game.image");
        }
	}
}