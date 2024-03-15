package redlib.backend.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WordCloudVO {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String book_name;

    /**
     *
     */
    private String publishing_house;

    /**
     *
     */
    private String classification_num;

    /**
     *
     */
    private String isbn;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publish_date;

    /**
     *
     */
    private BigDecimal price;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buy_date;

    private String onshelf;
}
