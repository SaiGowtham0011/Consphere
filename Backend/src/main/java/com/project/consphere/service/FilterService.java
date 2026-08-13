package com.project.consphere.service;

import com.project.consphere.dto.CreateFilterRequest;
import com.project.consphere.dto.FilterResponse;
import com.project.consphere.dto.PostResponse;
import com.project.consphere.dto.UpdateFilterRequest;

import java.util.List;

public interface FilterService {

    FilterResponse createFilter(CreateFilterRequest request);

    List<FilterResponse> getAllFilters();

    List<FilterResponse> getMyFilters();

    List<FilterResponse> getBuiltInFilters();

    FilterResponse getFilterById(Long id);

    FilterResponse updateFilter(Long id, UpdateFilterRequest request);

    void deleteFilter(Long id);

    List<PostResponse> getFilterFeed(Long id);
}
