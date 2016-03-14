package unc.group16.bo.interfaces;

import unc.group16.interfaces.TableRecord;


public interface Manager <T extends TableRecord> {
    Long create(T t);
    T read(Long id);
    boolean update(T t);
    boolean delete(Long id);
}
