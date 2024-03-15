package redlib.backend.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookInforVO {
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    /**
     *
     */
    private BigDecimal price;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyDate;

    private String onshelf;
}
