package com.sist.web.service;
import java.util.*;

import org.springframework.stereotype.Service;

import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

import com.sist.web.mapper.*;

@Service
@RequiredArgsConstructor
public class DataBoardServiceImpl implements DataBoardService {
	private final DataBoardMapper mapper;

	@Override
	public List<DataBoardVO> boardListData(int start) {
		// TODO Auto-generated method stub
		return mapper.boardListData(start);
	}

	@Override
	public int boardTotalPage() {
		// TODO Auto-generated method stub
		return mapper.boardTotalPage();
	}

	@Override
	public void boardInsert(DataBoardVO vo) {
		// TODO Auto-generated method stub
		mapper.boardInsert(vo);
	}
}
