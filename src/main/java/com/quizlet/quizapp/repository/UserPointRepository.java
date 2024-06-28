package com.quizlet.quizapp.repository;

import com.quizlet.quizapp.model.UserPoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointRepository extends CrudRepository<UserPoint, Integer> {
    @Query(value = "select * from user_point where user_id = :id", nativeQuery = true)
    public List<UserPoint> findByUserId(@Param("id") Long id);
}
