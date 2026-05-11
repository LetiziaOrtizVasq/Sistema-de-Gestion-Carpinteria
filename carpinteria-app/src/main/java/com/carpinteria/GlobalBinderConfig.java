package com.carpinteria;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@ControllerAdvice
public class GlobalBinderConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                } else {
                    setValue(LocalDate.parse(text));
                }
            }

            @Override
            public String getAsText() {
                LocalDate value = (LocalDate) getValue();
                return value != null ? value.toString() : "";
            }
        });
    }
}
