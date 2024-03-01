package com.ruoyi.system.service;

import java.text.ParseException;
import java.util.List;
import com.ruoyi.system.domain.Zscq;

/**
 * 知识产权Service接口
 * 
 *
 */
public interface IZscqService 
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
    public List<Zscq> selectZscqList(Zscq zscq) throws ParseException;

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
     * 批量删除知识产权
     * 
     * @param ids 需要删除的知识产权主键集合
     * @return 结果
     */
    public int deleteZscqByIds(String ids);

    /**
     * 删除知识产权信息
     * 
     * @param id 知识产权主键
     * @return 结果
     */
    public int deleteZscqById(Long id);

    Object data();
}
