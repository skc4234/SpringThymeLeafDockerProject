package com.sist.web.restcontroller;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.entity.*;
import com.sist.web.service.*;

import lombok.RequiredArgsConstructor;

// Router X(화면 변경 없음)
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeRestController {
	private final RecipeService rService;
	
	// VueJS로 보낼 HOME 화면
	@GetMapping("/recipe/list_vue")
	public ResponseEntity<Map> recipe_list_vue(
			@RequestParam("page") int page) {
		Map map = new HashMap();
		try {
			List<Recipe> list=rService.recipeListData(page);
			int[] pages=rService.getPageData(page, 12);
			int count=rService.recipeCount();
			map.put("list", list);
			map.put("pages", pages);
			map.put("count", count);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			//return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(map);
	}
	
	// Get+Post
	// 반드시 비동기
	@RequestMapping("/recipe/find_vue")
	public ResponseEntity<Map> recipe_find_vue(
			@RequestParam("page") int page,
			@RequestParam("fd") String fd) {
		Map map = new HashMap();
		try {
			List<Recipe> list=rService.findByTitleContains(fd, page);
			int[] pages=rService.getPageDataLike(1, fd, page, 12);
			map.put("list", list);
			map.put("pages", pages);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			//return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(map);
	}
	
	@RequestMapping("/recipe/chef_recipe_vue")
	public ResponseEntity<Map> chef_recipe_vue(
			@RequestParam("page") int page,
			@RequestParam("chef") String chef) {
		Map map = new HashMap();
		try {
			List<Recipe> list=rService.findByChefContains(chef, page);
			int[] pages=rService.getPageDataLike(2, chef, page, 12);
			map.put("list", list);
			map.put("pages", pages);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			//return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/recipe/detail_vue")
	public ResponseEntity<Map> recipe_detail_vue(
			@RequestParam("no") int no){
		Map map=new HashMap();
		try {
			RecipeDetail vo=new RecipeDetail();
			vo=rService.recipeDetailListData(no);
			List<String> mList=new ArrayList<String>();
			List<String> iList=new ArrayList<String>();
			String[] makes=vo.getFoodmake().split("\n");
			for(String s:makes) {
				StringTokenizer st=new StringTokenizer(s,"^");
				mList.add(st.nextToken());
				iList.add(st.nextToken());
			}
			map.put("vo", vo);
			map.put("mList", mList);
			map.put("iList", iList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(map);
	}
}
