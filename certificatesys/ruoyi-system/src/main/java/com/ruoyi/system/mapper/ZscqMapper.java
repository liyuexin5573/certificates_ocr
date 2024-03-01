package com.ruoyi.system.mapper;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.system.domain.Zscq;
import com.ruoyi.system.domain.Zscq2;

/**
 * 知识产权Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-11
 */
public interface ZscqMapper 
{
    /**
     * 查询知识产权
     * 
     * @param id 知识产权主键
     * @return 知识产权
     */
    public Zscq selectZscqById(Long id);

    /**
     * 查询知识产权列表
     * 
     * @param zscq 知识产权
     * @return 知识产权集合
     */
    public List<Zscq> selectZscqList(Zscq2 zscq);

    /**
     * 新增知识产权
     * 
     * @param zscq 知识产权
     * @return 结果
     */
    public int insertZscq(Zscq zscq);

    /**
     * 修改知识产权
     * 
     * @param zscq 知识产权
     * @return 结果
     */
    public int updateZscq(Zscq zscq);

    /**
     * 删除知识产权
     * 
     * @param id 知识产权主键
     * @return 结果
     */
    public int deleteZscqById(Long id);

    /**
     * 批量删除知识产权
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZscqByIds(String[] ids);

    ArrayList data();
}
