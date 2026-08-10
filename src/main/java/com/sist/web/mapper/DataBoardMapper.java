package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.*;
import com.sist.web.vo.*;

@Mapper
@Repository
public interface DataBoardMapper {
	/*
	 *   JPA
	 *   public Page<DataBoard> findAll(Pageable pg)
	 *   
	 *   @Query("SELECT no,name,subject,TO_CHAR(regdate,'yyyy-mm-dd') as dbday "
			+ "FROM springdataboard "
			+ "ORDER BY no DESC "
			+ "OFFSET :start ROWS FETCH NEXT 10 ROWS ONLY")
		 
		 
	 */
	/*
	 * 	 @Getter
	 *   public class DataBoardVO {
	 *   	private int no;
	 *   	private String name,subject,dbday
	 *   	private int hit,filecount;
	 *   }
	 */
	@Select("SELECT no,name,subject,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit,filecount "
			+ "FROM springdataboard "
			+ "ORDER BY no DESC "
			+ "OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY")
	public List<DataBoardVO> boardListData(int start);
	
	@Select("SELECT CEIL(COUNT(*)/10.0) FROM springdataboard")
	public int boardTotalPage();
	
	// Sequence 설정
	@SelectKey(keyProperty = "no", 
			resultType = int.class,
			before = true,
			statement = "SELECT NVL(MAX(no)+1,1) as no FROM springdataboard")
	
	@Insert("INSERT INTO springdataboard VALUES("
			+ "#{no},#{name},#{subject},#{content},#{pwd},"
			+ "SYSDATE,0,#{filename},#{filesize},#{filecount}"
			+ ")")
	public void boardInsert(DataBoardVO vo);
	
	// 상세보기, 수정, 삭제
}
