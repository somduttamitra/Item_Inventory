package com.ecommerce.demo.Repository;

import com.ecommerce.demo.Model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply,Long> {
}
