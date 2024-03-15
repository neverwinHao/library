package redlib.backend.model;

import java.util.Date;
import lombok.Data;

/**
 * 描述:borrows表的实体类
 * @version
 * @author:  liu'hao
 * @创建时间: 2023-03-27
 */
@Data
public class Borrows {
    /**
     * 
     */
    private Integer borrowId;

    /**
     * 
     */
    private Integer bookId;

    /**
     * 
     */
    private Date borrowTime;

    /**
     * 
     */
    private Date returnTime;

    /**
     * 
     */
    private String borrower;

    /**
     * 
     */
    private String bookName;
}