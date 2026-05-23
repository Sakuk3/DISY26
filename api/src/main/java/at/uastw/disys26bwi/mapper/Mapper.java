package at.uastw.disys26bwi.mapper;

import java.util.List;

public interface Mapper<S, T> {
  T map(S source);

  S mapReverse(T target);

  List<T> mapList(List<S> source);

  List<S> mapReverseList(List<T> target);
}

