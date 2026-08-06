package com.sist.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.*;
import java.util.*;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	// findByXxxContains ==> WHERE Xxx LIKE '%fd%'
	// findByXxx => WHERE Xxx=?
	// findByXxxStartsWith => WHERE Xxx LIKE 'fd%'
	// findByXxxEndsWith => WHERE Xxx LIKE '%fd'
	// findAll(Pageable, Sort)
	// count(), save(), delete()
	// 제목 검색
	public List<Recipe> findByTitleContains(String title);
	
	// 쉐프 검색
	public List<Recipe> findByChefContains(String chef);
}
