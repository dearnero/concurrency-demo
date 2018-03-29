package com.nero.demo.lock;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * <p>
 * date : 2018/2/9
 * time : 17:37
 * </p>
 *
 * @author Nero
 */
@Mapper
public interface ItemCountMapper {

    @Select("SELECT count FROM item_count where id = #{id}")
    Integer findCountById(@Param("id") Long id);

    @Update("UPDATE item_count SET count = #{count} where id = #{id}")
    Integer updateCountById(@Param("id") Long id, @Param("count") int count);
}
