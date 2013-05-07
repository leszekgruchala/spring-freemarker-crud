package eu.gruchala.crud.utils;

public interface HashProvider<T> {

    String get(T from);
}
