package redlib.backend.service;

import redlib.backend.dto.BookInforDTO;
import redlib.backend.dto.query.BookInforQueryDTO;
import redlib.backend.model.BookInfor;
import redlib.backend.model.Page;
import redlib.backend.vo.BookInforVO;
import redlib.backend.vo.WordCloudVO;


import java.util.List;

public interface BookInforService {

    Page<BookInforVO> listByPage(BookInforQueryDTO queryDTO);


    Integer addBook(BookInforDTO bookInforDTO);

    Integer updateBook(BookInforDTO bookInforDTO);

    void deleteByCodes(List<Integer> ids);

    BookInfor getBookinfoById(int bookId);

    Integer updateBookStatus(int bookId, String isAvailable);

    List<WordCloudVO> wordCloud();
}
