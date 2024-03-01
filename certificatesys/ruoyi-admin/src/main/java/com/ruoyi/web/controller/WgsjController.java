package com.ruoyi.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Wgsj;
import com.ruoyi.system.service.IWgsjService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/wgsj")
public class WgsjController extends BaseController {
    private String prefix = "system/wgsj";

    @Autowired
    private IWgsjService wgsjService;

    @RequiresPermissions("system:wgsj:view")
    @GetMapping()
    public String wgsj()
    {
        return prefix + "/wgsj";
    }

    /**
     * 查询外观设计列表
     */
    @RequiresPermissions("system:wgsj:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Wgsj wgsj)
    {
        startPage();
        List<Wgsj> list = wgsjService.selectWgsjList(wgsj);
        return getDataTable(list);
    }

    /**
     * 导出外观设计列表
     */
    @RequiresPermissions("system:wgsj:export")
    @Log(title = "外观设计专利", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Wgsj wgsj)
    {
        List<Wgsj> list = wgsjService.selectWgsjList(wgsj);
        ExcelUtil<Wgsj> util = new ExcelUtil<Wgsj>(Wgsj.class);
        return util.exportExcel(list, "外观设计专利数据");
    }

    /**
     * 新增外观设计
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存外观设计
     */
    @RequiresPermissions("system:wgsj:add")
    @Log(title = "外观设计专利", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Wgsj wgsj)
    {
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = sysUser.getLoginName();
        wgsj.setCreateUser(loginName);

        return toAjax(wgsjService.insertWgsj(wgsj));
    }

    /**
     * 修改外观设计
     */
    @RequiresPermissions("system:wgsj:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Wgsj wgsj = wgsjService.selectWgsjById(id);
        mmap.put("wgsj", wgsj);
        return prefix + "/edit";
    }

    /**
     * 修改保存外观设计
     */
    @RequiresPermissions("system:wgsj:edit")
    @Log(title = "外观设计专利", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave( Wgsj wgsj)
    {
        return toAjax(wgsjService.updateWgsj(wgsj));
    }

    /**
     * 删除外观设计
     */
    @RequiresPermissions("system:wgsj:remove")
    @Log(title = "外观设计专利", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(wgsjService.deleteWgsjByIds(ids));
    }
    /**
     * 查询外观设计列表
     */
    @GetMapping("/data")
    @ResponseBody
    public Object data()
    {
        return  wgsjService.data();
    }
}
