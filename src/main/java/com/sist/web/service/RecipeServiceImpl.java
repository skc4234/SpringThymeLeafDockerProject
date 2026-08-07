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
	private final ChefRepository cDao;
	private final RecipeDetailRepository dDao;

	@Override
	public List<Recipe> findByTitleContains(String title,int page) {
		// TODO Auto-generated method stub
		List<Recipe> list=new ArrayList<Recipe>();
		final int ROWSIZE=12;
		Pageable pg=PageRequest.of(page-1, ROWSIZE, Sort.by(Sort.Direction.ASC,"no"));
		Page<Recipe> pList=rDao.findByTitleContains(title,pg);
		if(pList!=null&&pList.hasContent()) {
			list=pList.getContent();
		}
		return list;
	}

	@Override
	public List<Recipe> findByChefContains(String chef,int page) {
		// TODO Auto-generated method stub
		List<Recipe> list=new ArrayList<Recipe>();
		final int ROWSIZE=12;
		Pageable pg=PageRequest.of(page-1, ROWSIZE, Sort.by(Sort.Direction.ASC,"no"));
		Page<Recipe> pList=rDao.findByChefContains(chef, pg);
		if(pList!=null&&pList.hasContent()) {
			list=pList.getContent();
		}
		return list;
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
		int start=(page*12)-12;
		list=rDao.recipeListData(start);
		return list;
	}

	@Override
	public int[] getPageData(int page,int rowsize) {
		// TODO Auto-generated method stub
		int totalpage=(int)(Math.ceil(rDao.recipeCount()/(double)rowsize));
		final int BLOCK=10;
		int startpage=((page-1)/BLOCK*BLOCK)+1;
		int endpage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endpage>totalpage) endpage=totalpage;
		int[] pages= {page,totalpage,startpage,endpage};
		return pages;
	}

	@Override
	public List<Chef> chefListData(int page) {
		// TODO Auto-generated method stub
		List<Chef> list=new ArrayList<Chef>();
		Pageable pg=PageRequest.of(page-1, 20);
		Page<Chef> pList=cDao.findAll(pg);
		if(pList!=null&&pList.hasContent()) {
			list=pList.getContent();
		}
		return list;
	}

	@Override
	public int[] chefGetPageData(int page, int rowsize) {
		// TODO Auto-generated method stub
		int totalpage=(int)(Math.ceil(cDao.count()/(double)rowsize));
		final int BLOCK=10;
		int startpage=((page-1)/BLOCK*BLOCK)+1;
		int endpage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endpage>totalpage) endpage=totalpage;
		int[] pages= {page,totalpage,startpage,endpage};
		return pages;
	}

	@Override
	public int[] getPageDataLike(int mode, String fd, int page, int rowsize) {
		// TODO Auto-generated method stub
		int count=0;
		if(mode==1) {
			count=(int)rDao.countByTitleContains(fd);
		}
		else if(mode==2) {
			count=(int)rDao.countByChefContains(fd);
		}
		int totalpage=(int)(Math.ceil(count/(double)rowsize));
		final int BLOCK=10;
		int startpage=((page-1)/BLOCK*BLOCK)+1;
		int endpage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endpage>totalpage) endpage=totalpage;
		int[] pages= {page,totalpage,startpage,endpage};
		return pages;
	}

	@Override
	public int recipeCount() {
		// TODO Auto-generated method stub
		return rDao.recipeCount();
	}

	@Override
	public RecipeDetail recipeDetailListData(int no) {
		// TODO Auto-generated method stub
		return dDao.findByNo(no);
	}
}
