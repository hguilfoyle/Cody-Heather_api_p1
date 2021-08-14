package com.revature.ncu.datasources.repositories;

// Interface for repositories.
// I left this as-is even though I did not use all of the methods
// I felt like we learned these specific ones for a reason...

import java.util.List;

public interface CrudRepository<T> {

    List<T> findAll();
    T findById(String id);
    T save(T newResource);
    boolean update(T updatedResource);
    boolean deleteById(String id);

}
