package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import java.util.*;
import com.sist.web.entity.*;
import com.sist.web.service.*;
/*
 *   요청 ====== DispatcherServlet ====== @Controller
 *                      |                      |
 *                      ------------------------
 *                       ㄴ연동(필요한 데이터나 내장객체 => 매개변수)
 */

/*
 *   매개변수 Annotation
 *   - @RequestParam : 단일 값
 *   - @ModelAttribute : 커맨드 객체(vo)
 *   - @RequestBody : JSON => 객체, @RestController에서 사용
 *   
 *   Model : 전송 객체 => request
 *   RedirectAttributes : sendRedirect시 값 전송
 *   HttpSession
 *   HttpServletRequest  : 쿠키 가져오기
 *   HttpServletResponse : 쿠키 생성
 *   
 *   @RequestParam(value="page", required=false) String page
 *                                 ㄴ null값 허용
 *                                 
 *                                 
 *   1. Repository / Mapper => 데이터베이스만 연동
 *   2. Service             => 실제 비즈니스 로직, DAO+DAO 조립
 *   3. Controller          => 조립된 데이터만 받아서 HTML로 전송
 */

/*
 * 	 // git init
 *   git init => git 연결폴더 생성
 *   git branch -M main => 브랜치 변경
 *   git remote add origin git주소 => github 연결
 *   git add . => 수정/추가된 파일 추가
 *   git commit -m "" => 커밋
 *   git push -u origin main --force => github로 push(강제)
 *   
 *   // commit
 *   git add .
 *   git commit -m ""
 *   git push -u origin main
 */

@Controller
@RequiredArgsConstructor // 생성자+@Autowired, 반드시 Lombok 설치
public class RecipeController {
	private final RecipeService rService;
	
	@GetMapping("/main/main")
	public String main_main(@RequestParam(value = "page",required = false) String page, Model model) {
		if(page==null) page="1";
		List<Recipe> list=rService.recipeListData(Integer.parseInt(page));
		int[] pages=rService.getPageData(Integer.parseInt(page),12);
		int count=rService.recipeCount();
		model.addAttribute("list",list);
		//model.addAttribute("curpage",pages[0]);
		//model.addAttribute("totalpage",pages[1]);
		//model.addAttribute("startpage",pages[2]);
		//model.addAttribute("endpage",pages[3]);
		model.addAttribute("count",count);
		model.addAttribute("pages",pages);
		model.addAttribute("main_html","main/home");
		return "main/main";
	}
	
	@GetMapping("/recipe/chef_list")
	public String recipe_chef(@RequestParam(value = "page",required = false) String page, Model model) {
		if(page==null) page="1";
		List<Chef> list=rService.chefListData(Integer.parseInt(page));
		int[] pages=rService.chefGetPageData(Integer.parseInt(page), 20);
		model.addAttribute("list",list);
		//model.addAttribute("curpage",pages[0]);
		//model.addAttribute("totalpage",pages[1]);
		//model.addAttribute("startpage",pages[2]);
		//model.addAttribute("endpage",pages[3]);
		model.addAttribute("pages",pages);
		model.addAttribute("main_html","recipe/chef");
		return "main/main";
	}
	
	@GetMapping("/recipe/find")
	public String recipe_find(Model model) {
		model.addAttribute("main_html","recipe/find");
		return "main/main";
	}
	
	@GetMapping("/recipe/chef_recipe")
	public String recipe_chef_recipe(@RequestParam("chef") String chef, Model model) {
		model.addAttribute("chef",chef);
		model.addAttribute("main_html","recipe/chef_recipe");
		return "main/main";
	}
	
	@GetMapping("/recipe/detail")
	public String recipe_detail(@RequestParam("no") int no, Model model) {
		RecipeDetail vo=rService.recipeDetailListData(no);
		model.addAttribute("vo",vo);
		List<String> mList=new ArrayList<String>();
		List<String> iList=new ArrayList<String>();
		String[] makes=vo.getFoodmake().split("\n");
		for(String s:makes) {
			StringTokenizer st=new StringTokenizer(s,"^");
			mList.add(st.nextToken());
			iList.add(st.nextToken());
		}
		model.addAttribute("mList",mList);
		model.addAttribute("iList",iList);
		//model.addAttribute("no",no);
		model.addAttribute("main_html","recipe/detail");
		return "main/main";
	}
}
