package com.simplify.repo;
import com.simplify.model.Insurance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InsuranceRepo extends JpaRepository<Insurance, Long> {

	List<Insurance> findByType(String type);

	List<Insurance> findByPurchasedFalse();

	List<Insurance> findTop5ByOrderByPopularityScoreDesc();

}
