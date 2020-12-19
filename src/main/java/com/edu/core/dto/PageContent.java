package com.edu.core.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageContent<P extends Page, DTO> {

  private int pageNumber;

  private int pageSize;

  private long totalElements;

  private int totalPages;

  private List<DTO> element;

  public PageContent(P pageEntity) {
    pageNumber = pageEntity.getNumber();
    pageSize = pageEntity.getSize();
    totalElements = pageEntity.getTotalElements();
    totalPages = pageEntity.getTotalPages();
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public List<DTO> getElement() {
    return element;
  }

  public void setElement(List<DTO> element) {
    this.element = element;
  }

  @Override
  public String toString() {
    return "PageContent{" +
        "pageNumber=" + pageNumber +
        ", pageSize=" + pageSize +
        ", totalElements=" + totalElements +
        ", totalPages=" + totalPages +
        ", element=" + element +
        '}';
  }
}
