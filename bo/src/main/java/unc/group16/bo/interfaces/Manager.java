package unc.group16.bo.interfaces;

public interface Manager <T> {
    public T create(T t);
    public T read(Long id);
    public T update(T t);
    public void delete(Long id);
}
