package com.revature.projectzero.repositories;

// Interface for repositories.
// I left this as-is even though I did not use all of the methods
// I felt like we learned these specific ones for a reason...

public interface CrudRepository<T> {

    T findByID(int id);
    T save(T newResource);
    boolean update(T updatedResource);
    boolean deleteByID(int id);

}
