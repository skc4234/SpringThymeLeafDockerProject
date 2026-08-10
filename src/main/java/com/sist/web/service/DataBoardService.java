package com.sist.web.service;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sist.web.vo.DataBoardVO;

public interface DataBoardService {
	public List<DataBoardVO> boardListData(int start);
	public int boardTotalPage();
	public void boardInsert(DataBoardVO vo);
}
