package com.tamu.dmeditorbackend.controller;

import com.tamu.dmeditorbackend.entity.Template;
import com.tamu.dmeditorbackend.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/templates/")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<Template> create(@RequestBody Template template) {
        Template savedTemplate = templateService.create(template);
        return new ResponseEntity<>(savedTemplate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Template>> getAll() {
        List<Template> all = templateService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Template> getById(@PathVariable Long id) {
        Template template = templateService.getById(id);
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Template> update(@RequestBody Template template, @PathVariable Long id) {
        Template updatedTemplate = templateService.update(template, id);
        return new ResponseEntity<>(updatedTemplate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete (@PathVariable Long id){
        templateService.delete(id);
        Map<String, String> message = Map.of("message", "Template Deleted Successfully");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

