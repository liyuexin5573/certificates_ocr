package com.ruoyi.system.EasyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BjytExcelTemplate {
    @ExcelProperty("序号")
    private Integer id;
    @ExcelProperty("报警编号")
    private String bjNo;
    @ExcelProperty("报警位置")
    private String bjAddress;
    @ExcelProperty("是否处理")
    private Integer handle;
    @ExcelProperty("报警时间")
    private String sjc;
    @ExcelProperty("处理人员")
    private String rms;
    @ExcelProperty("抓拍照片")
    private String upImg;
    @ExcelProperty("检查情况")
    private String upVideo;
}
