package at.uastw.disys26bwi.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<S, T> implements Mapper<S, T> {

  @Override
  public List<T> mapList(List<S> source) {
    if (source == null) {
      return List.of();
    }
    return source.stream()
      .map(this::map)
      .collect(Collectors.toList());
  }

  @Override
  public List<S> mapReverseList(List<T> target) {
    if (target == null) {
      return List.of();
    }
    return target.stream()
      .map(this::mapReverse)
      .collect(Collectors.toList());
  }
}

