package com.ruoyi.system.service;

import com.ruoyi.system.domain.Wgsj;
import java.util.List;

/**
 * 外观设计service接口
 */

public interface IWgsjService {
    /**
     * 查询专利
     *
     * @param id 外观设计主键
     * @return 外观设计
     */
    public Wgsj selectWgsjById(Long id);

    /**
     * 查询专利列表
     *
     * @param wgsj 外观设计
     * @return 外观设计集合
     */
    public List<Wgsj> selectWgsjList(Wgsj wgsj);

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
     * 删除专利
     *
     * @param id 外观设计主键
     * @return 结果
     */
    public int deleteWgsjById(Long id);

    /**
     * 批量删除专利
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWgsjByIds(String ids);

    Object data();
}
