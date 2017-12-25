package com.pavelchak.Repository;

import com.pavelchak.domain.Gadget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GagdetRepository extends JpaRepository<Gadget, Long> {
}
