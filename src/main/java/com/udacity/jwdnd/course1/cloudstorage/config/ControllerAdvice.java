package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotNull;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    //StandardServletMultipartResolver
    @ExceptionHandler(MultipartException.class)
    public String multipartException(@NonNull MultipartException e, @NonNull RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getCause().getMessage());
        return "redirect:/result";

    }

    //CommonsMultipartResolver
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String maxUploadSizeExceededException(@NonNull MaxUploadSizeExceededException e,
                                                 @NonNull RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "The file is too large and can't be uploaded.");
        return "redirect:/result";
    }
}
