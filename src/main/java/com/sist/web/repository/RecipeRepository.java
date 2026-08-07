package com.sist.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	/*
	 *   SELECT *
	 *   FROM recipe
	 *   WHERE title LIKE '%fd%'
	 *   OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
	 */
	// 제목 검색
	public Page<Recipe> findByTitleContains(String title,Pageable pg);
	
	// 쉐프 검색
	public Page<Recipe> findByChefContains(String chef,Pageable pg);
	
	/*
	 *   SELECT COUNT(*)
	 *   FROM recipe
	 *   WHERE title LIKE '%fd%'
	 */
	public long countByTitleContains(String title);
	public long countByChefContains(String chef);
	
	@Query(value = """
			SELECT *
			FROM recipe 
			WHERE no IN(SELECT no 
			FROM recipe 
			INTERSECT
			SELECT no FROM recipedetail)
			ORDER BY no DESC
			OFFSET :start ROWS FETCH NEXT 12 ROWS ONLY 
			""",
			nativeQuery = true)
	public List<Recipe> recipeListData(@Param("start") int start);
	
	@Query(value = """
			SELECT COUNT(*)
			FROM recipe 
			WHERE no IN(SELECT no 
			FROM recipe 
			INTERSECT
			SELECT no FROM recipedetail)
			""",
			nativeQuery = true)
	public int recipeCount();
}
