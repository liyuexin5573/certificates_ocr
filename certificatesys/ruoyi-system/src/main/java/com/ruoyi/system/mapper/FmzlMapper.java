package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Fmzl;
import com.ruoyi.system.domain.Fmzl2;

import java.util.ArrayList;
import java.util.List;


public interface FmzlMapper {
    /**
     * 查询专利
     *
     * @param id 专利主键
     * @return 发明专利
     */
    public Fmzl selectFmzlById(Long id);

    /**
     * 查询专利列表
     *
     * @param fmzl 发明专利
     * @return 发明专利集合
     */
    public List<Fmzl> selectFmzlList(Fmzl2 fmzl);

    /**
     * 新增专利
     *
     * @param fmzl 发明专利
     * @return 结果
     */
    public int insertFmzl(Fmzl fmzl);

    /**
     * 修改专利
     *
     * @param fmzl 发明专利
     * @return 结果
     */
    public int updateFmzl(Fmzl fmzl);

    /**
     * 删除专利
     *
     * @param id 发明专利主键
     * @return 结果
     */
    public int deleteFmzlById(Long id);

    /**
     * 批量删除专利
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFmzlByIds(String[] ids);

    ArrayList data();
}
