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

@Component
public class FileValidator extends ResponseEntityExceptionHandler implements Validator {

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg");
    
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                             .body("Unable to upload. File is too large!");
//    }
    
	@Override
	public boolean supports(Class<?> aClass) {
		return MultipartFile.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		MultipartFile file = (MultipartFile) o;

		// file validation
		if (!file.isEmpty() && !contentTypes.contains(file.getContentType())) {
			errors.rejectValue("image", "Type.game.image");
		}
        if (file.getSize() > 10 * 1024 * 1024) {
            errors.rejectValue("image", "Size.game.image");
        }
	}
}