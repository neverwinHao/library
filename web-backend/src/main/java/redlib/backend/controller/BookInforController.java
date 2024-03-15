package redlib.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redlib.backend.annotation.BackendModule;
import redlib.backend.annotation.Privilege;
import redlib.backend.dto.BookInforDTO;
import redlib.backend.dto.BorrowsDTO;
import redlib.backend.dto.query.BookInforQueryDTO;
import redlib.backend.model.BookInfor;
import redlib.backend.model.Page;
import redlib.backend.service.BookInforService;
import redlib.backend.service.BorrowsService;
import redlib.backend.vo.BookInforVO;
import redlib.backend.vo.WordCloudVO;


import java.util.List;

@RestController
@RequestMapping("/api/bookinfor")
@BackendModule({"page:页面", "update:修改", "add:创建", "delete:删除"})
public class BookInforController {

    @Autowired
    private BookInforService bookInforService;

    @Autowired
    private BorrowsService borrowsService;

    @PostMapping("listBookInfor")
    @Privilege("page")
    public Page<BookInforVO> listBookInfor(@RequestBody BookInforQueryDTO queryDTO) {
        return bookInforService.listByPage(queryDTO);
    }

    @PostMapping("addBook")
    @Privilege("add")
    public Integer addBook(@RequestBody BookInforDTO bookInforDTO) {
        return bookInforService.addBook(bookInforDTO);
    }

    @PostMapping("updateBook")
    @Privilege("update")
    public Integer updateBook(@RequestBody BookInforDTO bookInforDTO) {
        return bookInforService.updateBook(bookInforDTO);
    }

    @PostMapping("deleteBook")
    @Privilege("delete")
    public void deleteBook(@RequestBody List<Integer> ids) {
        bookInforService.deleteByCodes(ids);
    }

    @PostMapping("getBookinforById")
    public BookInfor getBookinforById(@RequestBody int bookId) {
        return bookInforService.getBookinfoById(bookId);
    }


    @PostMapping("addBorrows")
    public Integer addBorrows(@RequestBody BorrowsDTO borrowsDTO) {
        return borrowsService.addBorrows(borrowsDTO);
    }

    @PostMapping("updateBorrows")
    public Integer updateBorrows(@RequestBody BorrowsDTO borrowsDTO) {
        return borrowsService.updateBorrows(borrowsDTO);
    }

    @PostMapping("wordCloud")
    public List<WordCloudVO> wordCloud() {
        return bookInforService.wordCloud();
    }
}

