package redlib.backend.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowsVO {
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


    private String bookName;
}
