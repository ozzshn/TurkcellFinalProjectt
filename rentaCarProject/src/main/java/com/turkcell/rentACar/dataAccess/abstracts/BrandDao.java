package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.Brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {
	
	Brand getByBrandId(int id);

	List<Brand> getAllByBrandId(int id);

	boolean existsByBrandName(String brandName);
}
