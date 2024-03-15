package redlib.backend.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import redlib.backend.dao.BookInforMapper;
import redlib.backend.dao.BorrowsMapper;
import redlib.backend.dto.BookInforDTO;
import redlib.backend.dto.BorrowsDTO;
import redlib.backend.dto.DepartmentDTO;
import redlib.backend.dto.query.BookInforQueryDTO;
import redlib.backend.dto.query.DepartmentQueryDTO;
import redlib.backend.model.*;
import redlib.backend.service.AdminService;
import redlib.backend.service.BookInforService;
import redlib.backend.service.utils.BookInforUtils;
import redlib.backend.service.utils.BorrowsUtils;
import redlib.backend.service.utils.DepartmentUtils;
import redlib.backend.utils.FormatUtils;
import redlib.backend.utils.PageUtils;
import redlib.backend.utils.ThreadContextHolder;
import redlib.backend.vo.BookInforVO;
import redlib.backend.vo.DepartmentVO;
import redlib.backend.vo.WordCloudVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookInforServiceImpl implements BookInforService {

    @Autowired
    private BookInforMapper bookInforMapper;

    @Autowired
    private BorrowsMapper borrowsMapper;

    @Autowired
    private AdminService adminService;


    @Override
    public Page<BookInforVO> listByPage(BookInforQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new BookInforQueryDTO();
        }

        queryDTO.setBookName(FormatUtils.makeFuzzySearchTerm(queryDTO.getBookName()));
        queryDTO.setPublishingHouse(FormatUtils.makeFuzzySearchTerm(queryDTO.getPublishingHouse()));
        Integer size = bookInforMapper.count(queryDTO);
        //固定写法
        PageUtils pageUtils = new PageUtils(queryDTO.getCurrent(), queryDTO.getPageSize(), size);

        if (size == 0) {
            // 没有命中，则返回空数据。
            return pageUtils.getNullPage();
        }

        List<BookInfor> list = bookInforMapper.list(queryDTO, pageUtils.getOffset(), pageUtils.getLimit());
        List<BookInforVO> voList = new ArrayList<>();
        for (BookInfor bookInfor : list) {
            BookInforVO vo = BookInforUtils.convertToVO(bookInfor);
            voList.add(vo);
        }

        return new Page<>(pageUtils.getCurrent(), pageUtils.getPageSize(), pageUtils.getTotal(), voList);
    }

    @Override
    public Integer addBook(BookInforDTO bookInforDTO) {
        Token token = ThreadContextHolder.getToken();
        // 校验输入数据正确性
        BookInforUtils.validateBook(bookInforDTO);
        // 创建实体对象，用以保存到数据库
        BookInfor bookInfor = new BookInfor();
        // 将输入的字段全部复制到实体对象中
        BeanUtils.copyProperties(bookInforDTO, bookInfor);
        // 调用DAO方法保存到数据库表
        bookInforMapper.insert(bookInfor);
        return bookInfor.getId();
    }

    @Override
    public Integer updateBook(BookInforDTO bookInforDTO) {
        // 校验输入数据正确性
        Token token = ThreadContextHolder.getToken();
        BookInforUtils.validateBook(bookInforDTO);
        Assert.notNull(bookInforDTO.getId(), "图书id不能为空");
        BookInfor bookInfor = bookInforMapper.selectByPrimaryKey(bookInforDTO.getId());
        Assert.notNull(bookInfor, "没有找到该图书，Id为：" + bookInforDTO.getId());

        BeanUtils.copyProperties(bookInforDTO, bookInfor);
        bookInforMapper.updateByPrimaryKey(bookInfor);
        return bookInfor.getId();
    }

    @Override
    public void deleteByCodes(List<Integer> ids) {
        Assert.notEmpty(ids, "图书id列表不能为空");
        bookInforMapper.deleteByCodes(ids);
    }

    @Override
    public BookInfor getBookinfoById(int bookId) {
        return bookInforMapper.getBookinfoById(bookId);
    }


    @Override
    public Integer updateBookStatus(int id, String isAvailable) {
        BookInfor bookinfor = bookInforMapper.getBookinfoById(id);
        if (bookinfor == null) {
            // 没有找到对应的图书
            return 0;
        }
        if (bookinfor.getOnshelf() == isAvailable) {
            // 图书状态没有变化，不需要更新
            return 0;
        }
        // 更新图书状态
        bookinfor.setOnshelf(isAvailable);
        return bookInforMapper.updateBookinfo(bookinfor);
    }

    @Override
    public List<WordCloudVO> wordCloud() {
        return bookInforMapper.selectAll();
    }


}

