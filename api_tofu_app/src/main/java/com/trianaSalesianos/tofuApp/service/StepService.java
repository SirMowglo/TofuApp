package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.CategoryNotFoundException;
import com.trianaSalesianos.tofuApp.exception.StepNotFoundException;
import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Step;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.step.StepRequest;
import com.trianaSalesianos.tofuApp.model.dto.step.StepResponse;
import com.trianaSalesianos.tofuApp.repository.CategoryRepository;
import com.trianaSalesianos.tofuApp.repository.StepRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StepService {

    private final StepRepository stepRepository;

    public PageDto<StepResponse> search(List<SearchCriteria> params, Pageable pageable){
        GenericSpecificationBuilder<Step> stepSpecificationBuilder = new GenericSpecificationBuilder<>(params, Step.class);

        Specification<Step> spec = stepSpecificationBuilder.build();
        Page<StepResponse> stepResponsePage = stepRepository.findAll(spec,pageable).map(StepResponse::fromStep);

        return new PageDto<>(stepResponsePage);
    }
    public PageDto<StepResponse> getAllBySearch(String search, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<StepResponse> res = search(params,pageable);

        if (res.getContent().isEmpty()) throw new StepNotFoundException();

        return res;
    }

    public Step findById(UUID id) {
        return stepRepository.findById(id)
                .orElseThrow(() -> new StepNotFoundException());
    }

    public Step save(Step step) {
        return stepRepository.save(step);
    }

    public StepResponse update(StepRequest stepRequest, UUID id) {
        Step s = stepRepository.findById(id)
                .orElseThrow(() -> new StepNotFoundException());

        if(stepRequest.getStepNumber() <= 0)
            s.setStepNumber(stepRequest.getStepNumber());

        if(!stepRequest.getDescription().isEmpty())
            s.setDescription(stepRequest.getDescription());

        return StepResponse.fromStep(stepRepository.save(s));
    }


    public void delete(UUID id) {
        Step s = findById(id);

        stepRepository.delete(s);
    }
}
