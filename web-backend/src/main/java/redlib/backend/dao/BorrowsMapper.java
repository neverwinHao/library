package redlib.backend.dao;

import org.apache.ibatis.annotations.Param;

import redlib.backend.dto.query.BorrowsQueryDTO;
import redlib.backend.model.Borrows;

import java.util.Date;
import java.util.List;

public interface BorrowsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Borrows record);

    int insertSelective(Borrows record);

    Borrows selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Borrows record);

    int updateByPrimaryKey(Borrows record);

    Integer count(BorrowsQueryDTO queryDTO);

    List<Borrows> list(
            @Param("queryDTO") BorrowsQueryDTO queryDTO,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );



    Borrows getBorrowById(Integer borrowId);

    Integer updateBorrow(Date returnTime,Integer borrowId);

    void deleteByCodes(@Param("codeList") List<Integer> codeList);
}


