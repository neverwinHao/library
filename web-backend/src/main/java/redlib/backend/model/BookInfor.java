package redlib.backend.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 描述:book_infor表的实体类
 * @version
 * @author:  liu'hao
 * @创建时间: 2023-03-12
 */
@Data
public class BookInfor {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String bookName;

    /**
     * 
     */
    private String publishingHouse;

    /**
     * 
     */
    private String classificationNum;

    /**
     * 
     */
    private String isbn;

    /**
     * 
     */

    private Date publishDate;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 
     */
    private Date buyDate;

    private String onshelf;
}