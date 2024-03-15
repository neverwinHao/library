package redlib.backend.service;


import redlib.backend.dto.BookInforDTO;
import redlib.backend.dto.BorrowsDTO;
import redlib.backend.dto.query.BorrowsQueryDTO;
import redlib.backend.model.Borrows;
import redlib.backend.model.Page;
import redlib.backend.vo.BorrowsVO;

import java.util.Date;
import java.util.List;

public interface BorrowsService {
    Page<BorrowsVO> listByPage(BorrowsQueryDTO queryDTO);

    Borrows getBorrowById(int borrowId);

    Integer updateBorrowReturnTime(int borrowId, Date returnTime);

    Integer addBorrows(BorrowsDTO borrowsDTO);

    Integer updateBorrows(BorrowsDTO borrowsDTO);

    void deleteByCodes(List<Integer> ids);

}
