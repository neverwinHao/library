package redlib.backend.dto.query;


import lombok.Data;

@Data
public class BookInforQueryDTO extends PageQueryDTO{
    /**
     * 图书名称，模糊匹配
     */
    private String bookName;
    /**
     * 出版社名称，模糊匹配
     */
    private String publishingHouse;
}
