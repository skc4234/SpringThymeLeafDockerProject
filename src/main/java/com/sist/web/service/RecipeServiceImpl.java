package com.sist.web.service;

import org.hibernate.engine.spi.ExecutableList.Sorter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;
import com.sist.web.entity.*;
import com.sist.web.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
	private final RecipeRepository rDao;

	@Override
	public List<Recipe> findByTitleContains(String title) {
		// TODO Auto-generated method stub
		return rDao.findByTitleContains(title);
	}

	@Override
	public List<Recipe> findByChefContains(String chef) {
		// TODO Auto-generated method stub
		return rDao.findByChefContains(chef);
	}

	// JPA => 객체 단위로 사용 => @Entity
	// 객체 <===> Column(메소드) = ORM
	// ORM: JPA, MyBatis, LinQ(C#)
	@Override
	public List<Recipe> recipeListData(int page) {
		// TODO Auto-generated method stub
		// Pageable : 페이지 요청 정보
		// 페이지 번호 / 페이지 크기 / 정렬 조건
		List<Recipe> list=new ArrayList<Recipe>();
		Pageable pg=PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC,"no"));
		/* 
			SELECT * 
			FROM recipe 
			ORDER BY no ASC 
			OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY
		*/
		Page<Recipe> pList=rDao.findAll(pg);
		if(pList!=null&&pList.hasContent()) {
			list=pList.getContent();
		}
		return list;
	}

	@Override
	public int[] getPageData(int page) {
		// TODO Auto-generated method stub
		int totalpage=(int)(Math.ceil(rDao.count()/12.0));
		final int BLOCK=10;
		int startpage=((page-1)/BLOCK*BLOCK)+1;
		int endpage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endpage>totalpage) endpage=totalpage;
		int[] pages= {page,totalpage,startpage,endpage};
		return pages;
	}
}
