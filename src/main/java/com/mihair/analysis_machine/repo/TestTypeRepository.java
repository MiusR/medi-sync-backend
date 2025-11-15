package com.mihair.analysis_machine.repo;

import com.mihair.analysis_machine.model.util.TestType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTypeRepository extends JpaRepository<TestType, Long> {
    public TestType findByName(String name);

    public boolean existsTestTypeByName(String name);
}
