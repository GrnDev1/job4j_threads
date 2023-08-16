package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> function = (key, val) -> {
            if (val.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            val = new Base(val.getId(), val.getVersion() + 1);
            val.setName(model.getName());
            return val;
        };
        return memory.computeIfPresent(model.getId(), function) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}