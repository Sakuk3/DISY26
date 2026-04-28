package at.uastw.disys26bwi.repository;

import java.util.*;

public abstract class InMemoryRepository<Entity, Id> implements Repository<Entity, Id> {

  protected final Map<Id, Entity> store = new HashMap<>();

  protected abstract Id getId(Entity entity);

  @Override
  public List<Entity> queryAll() {
    return new ArrayList<>(store.values());
  }

  @Override
  public Optional<Entity> findById(Id id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Entity save(Entity entity) {
    store.put(getId(entity), entity);
    return entity;
  }

  @Override
  public boolean delete(Id id) {
    return this.store.remove(id) != null;
  }

  @Override
  public Entity update(Id id, Entity entity) {
    Id entityId = getId(entity);
    if (!Objects.equals(id, entityId)) {
      store.remove(id);
    }
    store.put(entityId, entity);
    return entity;
  }
}
