package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Syxx;
import com.ruoyi.system.domain.Syxx2;

import java.util.ArrayList;
import java.util.List;


public interface SyxxMapper {
    /**
     * 查询专利
     *
     * @param id 专利主键
     * @return 实用新型专利
     */
    public Syxx selectSyxxById(Long id);

    /**
     * 查询专利列表
     *
     * @param syxx 发明专利
     * @return 发明专利集合
     */
    public List<Syxx> selectSyxxList(Syxx2 syxx);

    /**
     * 新增发明专利
     *
     * @param syxx 发明专利
     * @return 结果
     */
    public int insertSyxx(Syxx syxx);

    /**
     * 修改发明专利
     *
     * @param syxx 发明专利
     * @return 结果
     */
    public int updateSyxx(Syxx syxx);

    /**
     * 删除发明专利
     *
     * @param id 发明专利主键
     * @return 结果
     */
    public int deleteSyxxById(Long id);

    /**
     * 批量删除发明专利
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSyxxByIds(String[] ids);

    ArrayList data();
}
