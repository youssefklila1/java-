package middlefeastdes.service_interface;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void add(T t) throws SQLException;

    void delete(int id) throws SQLException;

    void update(T t) throws SQLException;

    List<T> findAll() throws SQLException;

    T findById(int id) throws SQLException;

    List<T> searchBy(String column, String query) throws  SQLException;

    List<T> sortBy(String column, boolean descending) throws SQLException;
}
