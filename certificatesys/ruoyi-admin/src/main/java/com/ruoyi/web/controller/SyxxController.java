package com.ruoyi.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Syxx;
import com.ruoyi.system.service.ISyxxService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/syxx")
public class SyxxController extends BaseController {
    private String prefix = "system/syxx";

    @Autowired
    private ISyxxService syxxService;

    @RequiresPermissions("system:syxx:view")
    @GetMapping()
    public String syxx()
    {
        return prefix + "/syxx";
    }

    /**
     * 查询发明专利列表
     */
    @RequiresPermissions("system:syxx:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Syxx syxx)
    {
        startPage();
        List<Syxx> list = syxxService.selectSyxxList(syxx);
        return getDataTable(list);
    }

    /**
     * 导出发明专利列表
     */
    @RequiresPermissions("system:syxx:export")
    @Log(title = "实用新型专利", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Syxx syxx)
    {
        List<Syxx> list = syxxService.selectSyxxList(syxx);
        ExcelUtil<Syxx> util = new ExcelUtil<Syxx>(Syxx.class);
        return util.exportExcel(list, "实用新型专利数据");
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
    @RequiresPermissions("system:syxx:add")
    @Log(title = "实用新型专利", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Syxx syxx)
    {
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        // 获取当前的用户名称
        String loginName = sysUser.getLoginName();
       syxx.setCreateUser(loginName);

        return toAjax(syxxService.insertSyxx(syxx));
    }

    /**
     * 修改发明专利
     */
    @RequiresPermissions("system:syxx:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Syxx syxx = syxxService.selectSyxxById(id);
        mmap.put("syxx", syxx);
        return prefix + "/edit";
    }

    /**
     * 修改保存发明专利
     */
    @RequiresPermissions("system:syxx:edit")
    @Log(title = "实用新型专利", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave( Syxx syxx)
    {
        return toAjax(syxxService.updateSyxx(syxx));
    }

    /**
     * 删除发明专利
     */
    @RequiresPermissions("system:syxx:remove")
    @Log(title = "实用新型专利", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(syxxService.deleteSyxxByIds(ids));
    }
    /**
     * 查询发明专利列表
     */
    @GetMapping("/data")
    @ResponseBody
    public Object data()
    {
        return  syxxService.data();
    }
}
