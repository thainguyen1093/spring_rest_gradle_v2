package com.edu.core.rest;

import com.edu.core.dto.PageContent;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * this interface contain all of necessary method that rest controller need
 */
public interface BaseController<CRITERIA, CREATE, UPDATE, DTO, ID> {
  ResponseEntity<PageContent<Page, DTO>> search(CRITERIA criteria, int page, int size);

  ResponseEntity<DTO> findById(ID id);

  ResponseEntity<DTO> create(CREATE create);

  ResponseEntity<DTO> update(ID id, UPDATE update);

  ResponseEntity delete(ID id);

}
