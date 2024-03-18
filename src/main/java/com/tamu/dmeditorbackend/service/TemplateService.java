package com.tamu.dmeditorbackend.service;

import com.tamu.dmeditorbackend.entity.Template;
import com.tamu.dmeditorbackend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tamu.dmeditorbackend.service.Util.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public Template create(Template template) {
        return templateRepository.save(template);
    }

    public List<Template> getAll() {
       return templateRepository.findAll();
    }

    public Template getById(Long id) {
        return templateRepository.findById(id).orElseThrow(()->new RuntimeException("Template not found"));
    }

    public Template update(Template template, Long id) {
        Template updatedTemplate = templateRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Template not found" +id));
        copyNonNullProperties(template,updatedTemplate);
        return templateRepository.save(updatedTemplate);
    }

    public void delete(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Template not found"));
        templateRepository.delete(template);
    }
}
