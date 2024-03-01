package com.ruoyi.system.EasyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created on 2022/10/13.
 *
 * @author wanglei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bjyt {
    private Integer id;
    private String bjNo;
    private String bjSj2;
    private String bjSj;
    private String bjAddress;
    private String bjNr;
    private Integer handle;
    private String state;
    private String sjc;
    private String fqBh;
    private Integer isS;
    private Integer isDeleted;
    private Timestamp createTime;
    private String rms;
    private String yz;
    private String upRemarks;
    private String upImg;
    private String upVideo;
}
