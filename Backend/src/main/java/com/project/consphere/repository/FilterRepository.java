package com.project.consphere.repository;

import com.project.consphere.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {

    List<Filter> findByBuiltInTrue();

    List<Filter> findByOwnerId(Long ownerId);

    List<Filter> findByBuiltInTrueOrOwnerId(Long ownerId);

    boolean existsByNameAndBuiltInTrue(String name);

    boolean existsByNameAndOwnerId(String name, Long ownerId);
}
