package com.edu.core.service;

import com.edu.core.dto.PageContent;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * this interface contain all of necessary method that service need
 */
public interface BaseService<CRITERIA, CREATE, UPDATE, DTO, ENTITY, ID> {

  PageContent<Page, DTO> search(CRITERIA criteria, Integer page, Integer size);

  Optional<DTO> findById(ID id);

  Optional<DTO> create(CREATE create);

  Optional<DTO> update(ID id, UPDATE update);

  void delete(ID id);

}
