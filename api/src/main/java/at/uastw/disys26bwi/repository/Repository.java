package at.uastw.disys26bwi.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<Entity, Id> {
  List<Entity> queryAll();

  Optional<Entity> findById(Id id);

  Entity save(Entity entity);

  boolean delete(Id id);

  Entity update(Id id, Entity entity);

  void seed();

}
