package com.sist.web.service;

import java.util.*;
import com.sist.web.entity.*;
import com.sist.web.repository.RecipeDetailRepository;

public interface RecipeService {
	public List<Recipe> findByTitleContains(String title,int page);
	public List<Recipe> findByChefContains(String chef,int page);
	public List<Recipe> recipeListData(int page);
	public int[] getPageData(int page, int rowsize);
	public int[] chefGetPageData(int page, int rowsize);
	public List<Chef> chefListData(int page);
	public int[] getPageDataLike(int mode, String fd, int page, int rowsize);
	public int recipeCount();
	public RecipeDetail recipeDetailListData(int no);
}
