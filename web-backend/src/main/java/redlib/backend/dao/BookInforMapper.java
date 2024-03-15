package redlib.backend.dao;

import org.apache.ibatis.annotations.Param;
import redlib.backend.dto.query.BookInforQueryDTO;
import redlib.backend.model.BookInfor;
import redlib.backend.vo.BookInforVO;
import redlib.backend.vo.WordCloudVO;


import java.util.List;

public interface BookInforMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BookInfor record);

    int insertSelective(BookInfor record);

    BookInfor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BookInfor record);

    int updateByPrimaryKey(BookInfor record);

    /**
     * 根据查询条件获取命中个数
     *
     * @param queryDTO 查询条件
     * @return 命中数量
     */
    Integer count(BookInforQueryDTO queryDTO);

    /**
     * 根据查询条件获取部门列表
     *
     * @param queryDTO 查询条件
     * @param offset   开始位置
     * @param limit    记录数量
     * @return 部门列表
     */
    List<BookInfor> list(
            @Param("queryDTO") BookInforQueryDTO queryDTO,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

    void deleteByCodes(@Param("codeList") List<Integer> codeList);



    BookInfor getBookinfoById(Integer id);

    Integer updateBookinfo(BookInfor bookinfo);

    List<WordCloudVO> selectAll();


}