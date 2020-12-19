package com.edu.core.rest.impl;

import com.edu.core.constant.Constant;
import com.edu.core.dto.PageContent;
import com.edu.core.rest.BaseController;
import com.edu.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * this class provide default all implementation for all necessary method that rest controller need
 * */
public abstract class BaseControllerImpl<SERVICE extends BaseService<CRITERIA, CREATE, UPDATE, DTO, ENTITY, ID>, CRITERIA, CREATE, UPDATE, DTO, ENTITY, ID> implements BaseController<CRITERIA, CREATE, UPDATE, DTO, ID> {

  @Autowired
  private SERVICE service;

  @Override
  @GetMapping
  public ResponseEntity<PageContent<Page, DTO>> search(@Valid CRITERIA criteria,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "100") int size) {
    PageContent<Page, DTO> dtoResult = service.search(criteria, page, size);
    return ResponseEntity.ok(dtoResult);
  }

  @Override
  @GetMapping(Constant.ID_PATH)
  public ResponseEntity<DTO> findById(@PathVariable ID id) {
    return defaultWrapperResponse(service.findById(id));
  }

  @Override
  @PostMapping
  public ResponseEntity<DTO> create(@Valid @RequestBody CREATE create) {
    return defaultWrapperResponse(service.create(create));
  }

  @Override
  @PutMapping(Constant.ID_PATH)
  public ResponseEntity<DTO> update(@NotBlank @PathVariable ID id, @Valid @RequestBody UPDATE update) {
    return defaultWrapperResponse(service.update(id, update));
  }

  @Override
  @DeleteMapping(Constant.ID_PATH)
  public ResponseEntity delete(@NotBlank @PathVariable ID id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }

  private ResponseEntity<DTO> defaultWrapperResponse(Optional<DTO> dtoO){
    return dtoO.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }
}
