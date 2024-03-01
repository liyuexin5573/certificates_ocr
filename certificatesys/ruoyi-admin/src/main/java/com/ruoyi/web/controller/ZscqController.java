package com.ruoyi.web.controller;

import java.text.ParseException;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Zscq;
import com.ruoyi.system.service.IZscqService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 软件著作权Controller
 * 
 * @author ruoyi
 * @date 2022-10-11
 */
@Controller
@RequestMapping("/system/zscq")
public class ZscqController extends BaseController
{
    private String prefix = "system/zscq";

    @Autowired
    private IZscqService zscqService;

    @RequiresPermissions("system:zscq:view")
    @GetMapping()
    public String zscq()
    {
        return prefix + "/zscq";
    }

    /**
     * 查询软件著作权列表
     */
    @RequiresPermissions("system:zscq:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Zscq zscq) throws ParseException {
        startPage();
        List<Zscq> list = zscqService.selectZscqList(zscq);
        return getDataTable(list);
    }

    /**
     * 导出软件著作权列表
     */
    @RequiresPermissions("system:zscq:export")
    @Log(title = "软件著作权", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Zscq zscq) throws ParseException {
        List<Zscq> list = zscqService.selectZscqList(zscq);
        ExcelUtil<Zscq> util = new ExcelUtil<Zscq>(Zscq.class);
        return util.exportExcel(list, "软件著作权数据");
    }

    /**
     * 新增软件著作权
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存软件著作权
     */
    @RequiresPermissions("system:zscq:add")
    @Log(title = "软件著作权", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Zscq zscq)
    {
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = sysUser.getLoginName();
        zscq.setCreateUser(loginName);

        return toAjax(zscqService.insertZscq(zscq));
    }

    /**
     * 修改软件著作权
     */
    @RequiresPermissions("system:zscq:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Zscq zscq = zscqService.selectZscqById(id);
        mmap.put("zscq", zscq);
        return prefix + "/edit";
    }

    /**
     * 修改保存软件著作权
     */
    @RequiresPermissions("system:zscq:edit")
    @Log(title = "软件著作权", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Zscq zscq)
    {
        return toAjax(zscqService.updateZscq(zscq));
    }

    /**
     * 删除软件著作权
     */
    @RequiresPermissions("system:zscq:remove")
    @Log(title = "软件著作权", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zscqService.deleteZscqByIds(ids));
    }
    /**
     * 查询软件著作权列表
     */
    @GetMapping("/data")
    @ResponseBody
    public Object data()
    {
        return  zscqService.data();
    }
}
