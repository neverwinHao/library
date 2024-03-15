package redlib.backend.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redlib.backend.dao.BorrowsMapper;
import redlib.backend.dto.BorrowsDTO;
import redlib.backend.dto.query.BorrowsQueryDTO;
import redlib.backend.model.Borrows;
import redlib.backend.model.Page;
import redlib.backend.model.Token;
import redlib.backend.service.BorrowsService;
import redlib.backend.service.utils.BookInforUtils;
import redlib.backend.service.utils.BorrowsUtils;
import redlib.backend.utils.FormatUtils;
import redlib.backend.utils.PageUtils;
import redlib.backend.utils.ThreadContextHolder;
import redlib.backend.vo.BookInforVO;
import redlib.backend.vo.BorrowsVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BorrowsServiceImpl implements BorrowsService {

    @Autowired
    private BorrowsMapper borrowsMapper;

    @Override
    public Page<BorrowsVO> listByPage(BorrowsQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new BorrowsQueryDTO();
        }

        queryDTO.setBorrower(FormatUtils.makeFuzzySearchTerm(queryDTO.getBorrower()));
        queryDTO.setBookName(FormatUtils.makeFuzzySearchTerm(queryDTO.getBookName()));
        Integer size = borrowsMapper.count(queryDTO);
        PageUtils pageUtils = new PageUtils(queryDTO.getCurrent(), queryDTO.getPageSize(), size);
        if (size == 0) {
            // 没有命中，则返回空数据。
            return pageUtils.getNullPage();
        }

        List<Borrows> list = borrowsMapper.list(queryDTO, pageUtils.getOffset(), pageUtils.getLimit());
        List<BorrowsVO> voList = new ArrayList<>();
        for (Borrows borrows : list) {
            BorrowsVO vo = BorrowsUtils.convertToVO(borrows);
            voList.add(vo);
        }

        return new Page<>(pageUtils.getCurrent(), pageUtils.getPageSize(), pageUtils.getTotal(), voList);
    }

    @Override
    public Borrows getBorrowById(int borrowId) {
        return borrowsMapper.getBorrowById(borrowId);
    }

    @Override
    public Integer updateBorrowReturnTime(int borrowId, Date returnTime) {
        Borrows borrows = borrowsMapper.getBorrowById(borrowId);
        if (borrows == null) {
            // 没有找到对应的借阅历史
            return 0;
        }
        if (borrows.getReturnTime() != null) {
            // 归还时间已经有值，不需要更新
            return 0;
        }
        // 更新借阅历史的归还时间
        borrows.setReturnTime(returnTime);
        return borrowsMapper.updateBorrow(returnTime,borrowId);
    }

    @Override
    public Integer addBorrows(BorrowsDTO borrowsDTO) {
        Token token = ThreadContextHolder.getToken();
        BorrowsUtils.validateBorrows(borrowsDTO);
        Borrows borrows = new Borrows();
        BeanUtils.copyProperties(borrowsDTO, borrows);
        borrowsMapper.insert(borrows);
        return borrows.getBorrowId();
    }

    @Override
    public Integer updateBorrows(BorrowsDTO borrowsDTO) {
        Token token = ThreadContextHolder.getToken();
        BorrowsUtils.validateBorrows(borrowsDTO);
        Assert.notNull(borrowsDTO.getBorrowId(),"借阅记录id不能为空");
        Borrows borrows = borrowsMapper.selectByPrimaryKey(borrowsDTO.getBorrowId());
        Assert.notNull(borrows, "没有找到该条记录，Id为：" + borrowsDTO.getBorrowId());
        BeanUtils.copyProperties(borrowsDTO, borrows);
        borrowsMapper.updateByPrimaryKey(borrows);
        return borrows.getBorrowId();
    }

    @Override
    public void deleteByCodes(List<Integer> ids) {
        Assert.notEmpty(ids, "图书id列表不能为空");
        borrowsMapper.deleteByCodes(ids);
    }
}

