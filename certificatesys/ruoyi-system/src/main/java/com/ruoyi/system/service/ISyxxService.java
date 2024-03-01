package com.ruoyi.system.service;

import com.ruoyi.system.domain.Syxx;
import java.util.List;

/**
 * 实用新型service接口
 */

public interface ISyxxService {
    /**
     * 查询专利
     *
     * @param id 实用新型主键
     * @return 实用新型
     */
    public Syxx selectSyxxById(Long id);

    /**
     * 查询专利列表
     *
     * @param syxx 实用新型
     * @return 实用新型集合
     */
    public List<Syxx> selectSyxxList(Syxx syxx);

    /**
     * 新增专利
     *
     * @param syxx 实用新型
     * @return 结果
     */
    public int insertSyxx(Syxx syxx);

    /**
     * 修改专利
     *
     * @param syxx 实用新型
     * @return 结果
     */
    public int updateSyxx(Syxx syxx);

    /**
     * 删除专利
     *
     * @param id 实用新型主键
     * @return 结果
     */
    public int deleteSyxxById(Long id);

    /**
     * 批量删除专利
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSyxxByIds(String ids);

    Object data();
}
