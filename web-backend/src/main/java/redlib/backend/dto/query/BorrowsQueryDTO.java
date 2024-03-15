package redlib.backend.dto.query;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowsQueryDTO extends PageQueryDTO{
    private String borrower;
    private String bookName;

}
