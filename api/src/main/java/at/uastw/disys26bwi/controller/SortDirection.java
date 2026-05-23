package at.uastw.disys26bwi.controller;

import org.springframework.data.domain.Sort;

public enum SortDirection {
  asc,
  desc;

  Sort.Direction toSpringDirection() {
    return this == asc ? Sort.Direction.ASC : Sort.Direction.DESC;
  }
}

