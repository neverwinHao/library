package redlib.backend.service.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import redlib.backend.dto.BookInforDTO;
import redlib.backend.model.BookInfor;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.BookInforVO;


import java.util.Map;

public class BookInforUtils {
    public static void validateBook(BookInforDTO bookInforDTO) {
        FormatUtils.trimFieldToNull(bookInforDTO);
        Assert.notNull(bookInforDTO, "图书信息输入数据不能为空");
//        Assert.hasText(bookInforDTO.getBookName(), "图书名称不能为空");
    }

    public static BookInforVO convertToVO(BookInfor bookInfor) {
        BookInforVO bookInforVO = new BookInforVO();
        BeanUtils.copyProperties(bookInfor, bookInforVO);
        return bookInforVO;
    }
}
