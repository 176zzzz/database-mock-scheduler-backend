package com.zp.mapper;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.zp.common.constant.SysConstant;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通用crud接口
 *
 * @author CFuShn 2022/3/31 18:47
 */
@DS(SysConstant.TARGET_DS_NAME)
public interface TargetMapper {

    /**
     * 插入
     *
     * @param sql String
     * @return int
     * @author CFuShn at 2022/3/31 19:10
     */
    @Insert("${sql}")
    int insertBySql(@Param("sql") String sql);

    /**
     * 删除
     *
     * @param sql sql
     * @return int
     * @author CFuShn at 2022/3/31 19:10
     */
    @Delete("${sql}")
    int deleteBySql(@Param("sql") String sql);

    /**
     * 修改
     *
     * @param sql sql
     * @return int
     * @author CFuShn at 2022/3/31 19:10
     */
    @Update("${sql}")
    int updateBySql(@Param("sql") String sql);

    /**
     * 查询,支持接收任意结果集
     *
     * @param sql sql
     * @return List<Map < String column, Object value>>
     */
    @Select("${sql}")
    List<Map<String, Object>> selectBySql2Map(@Param("sql") String sql);

    /**
     * 调用存储过程
     *
     * @param sql sql
     */
    @Select("${sql}")
    @Options(statementType = StatementType.CALLABLE)
    void callProcedure(@Param("sql") String sql);

    /**
     * 查询,支持接收任意结果集,并放入指定实体类的List
     * <p/>
     * (**tips:查询结果放入容器时,同名列类型必须兼容)
     *
     * @param sql   sql
     * @param clazz 实体类的类型
     * @return List<Entity>
     */
    default <DTO> List<DTO> selectBySql2DTO(String sql, Class<DTO> clazz) {
        return selectBySql2Map(sql)
                .stream()
                .map(item -> JSON.parseObject(JSON.toJSONString(item), clazz))
                .collect(Collectors.toList());
    }

    /**
     * 查询单值或单个实体类
     *
     * @param sql   sql
     * @param clazz 单值或单个实体类的类型
     * @return 单值或单个实体类
     * @author CFuShn at 2022/1/19 16:11
     */
    @SuppressWarnings("all")
    default <T> T getSingleValue(String sql, Class<T> clazz) {
        List<Map<String, Object>> mapList = selectBySql2Map(sql);
        if (mapList != null && mapList.size() == 1 && mapList.get(0) != null && mapList.get(0).size() == 1) {
            Object o = mapList.get(0).values().iterator().next();
            if (clazz.equals(String.class) || o instanceof Number) {
                // 查询的结果明确是字符串,或者数字型
                return JSON.parseObject(JSON.toJSONString(o), clazz);
            } else {
                // 否则认为是个对象(比如Date类),则将首个map转为对象
                return JSON.parseObject(JSON.toJSONString(mapList.get(0)), clazz);
            }
        }
        return null;
    }

    /**
     * 查询单列,返回List
     *
     * @param sql   String
     * @param clazz Class
     * @return 该列值的List
     * @author CFuShn at 2022/2/18 18:38
     */
    @SuppressWarnings("all")
    default <T> List<T> getSingleColList(String sql, Class<T> clazz) {

        List<Map<String, Object>> mapList = selectBySql2Map(sql);
        if (mapList != null && !mapList.isEmpty() && mapList.get(0) != null && mapList.get(0).size() == 1) {
            Object sniffObj = mapList.get(0).values().iterator().next();
            if (clazz.equals(String.class) || sniffObj instanceof Number) {
                // 查询的结果明确是字符串,或者数字型
                return mapList.stream().map(recordMap -> (T) recordMap.values().iterator().next())
                        .collect(Collectors.toList());
            } else {
                // 否则认为是个对象,和selectBySql2DTO就没区别了
                return mapList.stream().map(recordMap -> JSON.parseObject(JSON.toJSONString(recordMap), clazz))
                        .collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }

    /**
     * 刷新物化视图
     *
     * @param mv             物化视图
     * @param isConcurrently 是否增量刷新 (是: 增量; 否: 全量)
     */
    default void refreshMv(String mv, boolean isConcurrently) {
        callProcedure(String.format("refresh materialized view %s %s with data", isConcurrently ? "concurrently" : "", mv));
    }
}
