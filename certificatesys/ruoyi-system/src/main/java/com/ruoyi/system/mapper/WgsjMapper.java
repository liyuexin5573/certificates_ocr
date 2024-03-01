package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Wgsj;
import com.ruoyi.system.domain.Wgsj2;

import java.util.ArrayList;
import java.util.List;


public interface WgsjMapper {
    /**
     * 查询专利
     *
     * @param id 专利主键
     * @return 外观设计专利
     */
    public Wgsj selectWgsjById(Long id);

    /**
     * 查询专利列表
     *
     * @param wgsj 外观设计
     * @return 外观设计专利集合
     */
    public List<Wgsj> selectWgsjList(Wgsj2 wgsj);

    /**
     * 新增专利
     *
     * @param wgsj 外观设计
     * @return 结果
     */
    public int insertWgsj(Wgsj wgsj);

    /**
     * 修改专利
     *
     * @param wgsj 外观设计
     * @return 结果
     */
    public int updateWgsj(Wgsj wgsj);

    /**
     * 删除外观设计
     *
     * @param id 外观设计主键
     * @return 结果
     */
    public int deleteWgsjById(Long id);

    /**
     * 批量删除外观设计
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWgsjByIds(String[] ids);

    ArrayList data();
}
