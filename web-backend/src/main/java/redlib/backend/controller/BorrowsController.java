package redlib.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redlib.backend.annotation.BackendModule;
import redlib.backend.annotation.Privilege;
import redlib.backend.dto.BorrowsDTO;
import redlib.backend.dto.query.BorrowsQueryDTO;
import redlib.backend.model.BookInfor;
import redlib.backend.model.Borrows;
import redlib.backend.model.Page;
import redlib.backend.service.BookInforService;
import redlib.backend.service.BorrowsService;
import redlib.backend.vo.BorrowsVO;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/borrows")
@BackendModule({"page:页面", "update:修改", "add:创建", "delete:删除"})
public class BorrowsController {

    @Autowired
    private BorrowsService borrowsService;

    @PostMapping("listBorrows")
    @Privilege("page")
    public Page<BorrowsVO> listBorrows(@RequestBody BorrowsQueryDTO queryDTO) {
        return borrowsService.listByPage(queryDTO);
    }

    @PostMapping("addBorrows")
    @Privilege("add")
    public Integer addBorrows(@RequestBody BorrowsDTO borrowsDTO) {
        return borrowsService.addBorrows(borrowsDTO);
    }

    @PostMapping("updateBorrows")
    @Privilege("update")
    public Integer updateBorrows(@RequestBody BorrowsDTO borrowsDTO) {
        return borrowsService.updateBorrows(borrowsDTO);
    }

    @PostMapping("deleteBorrows")
    @Privilege("delete")
    public void deleteBorrows(@RequestBody List<Integer> ids) {

        borrowsService.deleteByCodes(ids);
    }

    @Autowired
    private BookInforService bookInforService;

    @PostMapping("returnbook")
    public String returnBook(@RequestBody int borrowId) {
        Borrows borrow = borrowsService.getBorrowById(borrowId);
        if (borrow == null) {
            return "没有该借阅ID的借阅信息";
        }
        Date returnTime = new Date();
        int result = borrowsService.updateBorrowReturnTime(borrowId, returnTime);
        if (result <= 0) {
            return "设置归还时间失败";
        }
        // 2. 将图书信息表中对应的图书的“是否在架”字段由“不在架”改为在架
        BookInfor bookInfor = bookInforService.getBookinfoById(borrow.getBookId());
        if (bookInfor == null) {
            return "没有找到这本书";
        }
        result = bookInforService.updateBookStatus(borrow.getBookId(), "在架");
        if (result <= 0) {
            return "归还失败";
        }
        return "归还成功！！";
    }


    @PostMapping("getBorrowById")
    public Borrows getBorrowById(@RequestBody int borrowId) {
        return borrowsService.getBorrowById(borrowId);
    }

    }

