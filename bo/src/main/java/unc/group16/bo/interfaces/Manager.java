package unc.group16.bo.interfaces;

public interface Manager <T> {
    Long create(T t);
    T read(Long id);
    boolean update(T t);
    boolean delete(Long id);
}
