package org.dadeco.cu996.api.repository;

import org.dadeco.cu996.api.model.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository {
    Activity findByNtAccount(String ntAccount);

    Activity findByEmail(String email);

    Activity findAll();

    @Query(value = "", nativeQuery = true)
    List<Object[]> getEffortByPjoname();

    Activity save(Activity productInfo);
}
