package com.unw.crypto.db;

import com.unw.crypto.model.Tick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TickRepository extends JpaRepository<Tick, Long> {

}
