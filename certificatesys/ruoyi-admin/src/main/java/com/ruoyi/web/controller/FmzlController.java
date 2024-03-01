package com.ruoyi.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Fmzl;
import com.ruoyi.system.service.IFmzlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/system/fmzl")
public class FmzlController extends BaseController {
    private String prefix = "system/fmzl";

    @Autowired
    private IFmzlService fmzlService;

    @RequiresPermissions("system:fmzl:view")
    @GetMapping()
    public String fmzl()
    {
        return prefix + "/fmzl";
    }

    /**
     * 查询发明专利列表
     */
    @RequiresPermissions("system:fmzl:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Fmzl fmzl)
    {
        startPage();
        List<Fmzl> list = fmzlService.selectFmzlList(fmzl);
        return getDataTable(list);
    }

    /**
     * 导出发明专利列表
     */
    @RequiresPermissions("system:fmzl:export")
    @Log(title = "发明专利", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Fmzl fmzl)
    {
        List<Fmzl> list = fmzlService.selectFmzlList(fmzl);
        ExcelUtil<Fmzl> util = new ExcelUtil<Fmzl>(Fmzl.class);
        return util.exportExcel(list, "发明专利数据");
    }

    /**
     * 新增发明专利
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存发明专利
     */
    @RequiresPermissions("system:fmzl:add")
    @Log(title = "发明专利", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Fmzl fmzl)
    {
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = sysUser.getLoginName();
        fmzl.setCreateUser(loginName);

        return toAjax(fmzlService.insertFmzl(fmzl));
    }

    /**
     * 修改发明专利
     */
    @RequiresPermissions("system:fmzl:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Fmzl fmzl = fmzlService.selectFmzlById(id);
        mmap.put("fmzl", fmzl);
        return prefix + "/edit";
    }

    /**
     * 修改保存发明专利
     */
    @RequiresPermissions("system:fmzl:edit")
    @Log(title = "发明专利", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Fmzl fmzl)
    {
        return toAjax(fmzlService.updateFmzl(fmzl));
    }

    /**
     * 删除发明专利
     */
    @RequiresPermissions("system:fmzl:remove")
    @Log(title = "发明专利", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(fmzlService.deleteFmzlByIds(ids));
    }
    /**
     * 查询发明专利列表
     */
    @GetMapping("/data")
    @ResponseBody
    public Object data()
    {
        return  fmzlService.data();
    }
}
