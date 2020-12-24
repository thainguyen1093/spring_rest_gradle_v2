package com.edu.core.service.impl;

import com.edu.core.dto.PageContent;
import com.edu.core.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * this class provide default all implementation for all necessary method that service need
 */
public abstract class BaseServiceImpl<REPOSITORY extends JpaRepository<ENTITY, ID> & JpaSpecificationExecutor<ENTITY>, CRITERIA, CREATE, UPDATE, DTO, ENTITY, ID> implements BaseService<CRITERIA, CREATE, UPDATE, DTO, ENTITY, ID> {

  @Autowired
  private REPOSITORY repository;

  @Override
  public PageContent<Page, DTO> search(CRITERIA criteria, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    return Optional.ofNullable(criteria)
        .map(this::newSpecification) // create Specification object
        .map(specification -> repository.findAll(specification, pageable)) // fill all Resource that match with criteria
        .map(this::fromPageEntityToPageContent) // convert to our object PageContent
        .orElseGet(null);
  }

  @Override
  public Optional<DTO> findById(ID id) {
    return repository.findById(id)
        .map(this::fromEntityToDto);
  }

  @Override
  public Optional<DTO> create(CREATE create) {
    return Optional.ofNullable(create)
        .map(this::fromCreateToEntity)
        .map(repository::save)
        .map(this::fromEntityToDto);
  }

  @Override
  public Optional<DTO> update(ID id, UPDATE update) {
    return Optional.ofNullable(id)
        .flatMap(repository::findById)
        .map(entity -> fromUpdateToEntity(update, entity))
        .map(repository::save)
        .map(this::fromEntityToDto);
  }

  @Override
  public void delete(ID id) {
    repository.deleteById(id);
  }

  public ENTITY fromCreateToEntity(CREATE create) {
    // ENTITY at position number 5 in list generic class
    ENTITY entity = newGenericAtPosition(5);
    BeanUtils.copyProperties(create, entity);
    return entity;
  }

  public ENTITY fromUpdateToEntity(UPDATE update, ENTITY entity) {
    BeanUtils.copyProperties(update, entity);
    return entity;
  }

  public DTO fromEntityToDto(ENTITY entity) {
    // DTO at position number 4 in list generic class
    DTO dto = newGenericAtPosition(4);
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

  public List<DTO> fromEntityToDto(List<ENTITY> entityList) {
    return entityList.stream()
        .map(this::fromEntityToDto)
        .collect(Collectors.toList());
  }

  public PageContent<Page, DTO> fromPageEntityToPageContent(Page<ENTITY> entityPage) {
    PageContent<Page, DTO> pageContent = new PageContent(entityPage);

    List<DTO> dtoList = fromEntityToDto(entityPage.getContent());
    pageContent.setElement(dtoList);
    return pageContent;
  }

  private <T> T newGenericAtPosition(int number) {
    Type type = getClass().getGenericSuperclass();
    ParameterizedType paramType = (ParameterizedType) type;
    Class<T> clazz = (Class<T>) paramType.getActualTypeArguments()[number];

    try {
      return clazz.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Can not create the instance of: " + clazz.getName());
  }

  public Specification<ENTITY> newSpecification(CRITERIA criteria) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}
