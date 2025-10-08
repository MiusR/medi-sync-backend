package com.mihair.analysis_machine.service;

import com.mihair.analysis_machine.model.util.TestType;
import com.mihair.analysis_machine.repo.TestTypeRepository;
import com.mihair.analysis_machine.service.exception.ValueNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestTypeService {

    private final TestTypeRepository repository;

    public TestTypeService(TestTypeRepository repository) {
        this.repository = repository;
    }

    public List<TestType> getTestTypes() {
        return repository.findAll();
    }

    public TestType getTestByName(String name) throws ValueNotFound {
        if (repository.existsTestTypeByName(name))
            return repository.findByName(name);
        throw new ValueNotFound("Not found value with name: " + name);
    }
}
