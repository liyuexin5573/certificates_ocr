package com.ruoyi.system.service;

import com.ruoyi.system.domain.Fmzl;

import java.util.List;

/**
 * 发明专利service接口
 */

public interface IFmzlService {
    /**
     * 查询发明专利
     *
     * @param id 发明专利主键
     * @return 发明专利
     */
    public Fmzl selectFmzlById(Long id);

    /**
     * 查询发明专利列表
     *
     * @param fmzl 发明专利
     * @return 发明专利集合
     */
    public List<Fmzl> selectFmzlList(Fmzl fmzl);

    /**
     * 新增发明专利
     *
     * @param fmzl 发明专利
     * @return 结果
     */
    public int insertFmzl(Fmzl fmzl);

    /**
     * 修改发明专利
     *
     * @param fmzl 发明专利
     * @return 结果
     */
    public int updateFmzl(Fmzl fmzl);

    /**
     * 删除发明专利
     *
     * @param id 发明专利主键
     * @return 结果
     */
    public int deleteFmzlById(Long id);

    /**
     * 批量删除发明专利
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFmzlByIds(String ids);

    Object data();
}