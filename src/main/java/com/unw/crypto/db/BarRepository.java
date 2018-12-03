package com.unw.crypto.db;

import com.unw.crypto.model.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {

}
