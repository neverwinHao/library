package redlib.backend.service.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import redlib.backend.dto.BookInforDTO;
import redlib.backend.dto.BorrowsDTO;
import redlib.backend.model.BookInfor;
import redlib.backend.model.Borrows;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.BookInforVO;
import redlib.backend.vo.BorrowsVO;

public class BorrowsUtils {

    public static void validateBorrows(BorrowsDTO borrowsDTO) {
        FormatUtils.trimFieldToNull(borrowsDTO);
        Assert.notNull(borrowsDTO, "借阅记录信息输入数据不能为空");
//        Assert.hasText(borrowsDTO.getBookName(), "图书名称不能为空");
    }


    public static BorrowsVO convertToVO(Borrows borrows) {
        BorrowsVO borrowsVO = new BorrowsVO();
        BeanUtils.copyProperties(borrows, borrowsVO);

        //departmentVO.setCreatedByDesc(nameMap.get(department.getCreatedBy()));
        return borrowsVO;
    }
}
