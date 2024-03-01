package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 知识产权对象 zscq
 * 
 * @author ruoyi
 * @date 2022-10-11
 */
public class Zscq2 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主表id */
    private Long id;

    /** 知识产权证书图片 */
    @Excel(name = "知识产权证书图片")
    private String photo;

    /** 证书号 */
    @Excel(name = "证书号")
    private String cardId;

    /** 软件名称 */
    @Excel(name = "软件名称")
    private String softwareName;

    /** 著作权人 */
    @Excel(name = "著作权人")
    private String companyName;

    /** 开发完成日期 */
    @Excel(name = "开发完成日期")
    private String startDate;

    /** 首次发表日期 */
    @Excel(name = "首次发表日期")
    private String firstDate;

    /** 权利取得方式 */
    @Excel(name = "权利取得方式")
    private String getWay;

    /** 权利范围 */
    @Excel(name = "权利范围")
    private String wayScope;

    /** 登记号 */
    @Excel(name = "登记号")
    private String registNum;

    /** 序列号 */
    @Excel(name = "序列号")
    private String num;

    /** 证书时间 */
    @Excel(name = "证书时间")
    private String cardDate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy年MM月dd日")
    private String createDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setPhoto(String photo) 
    {
        this.photo = photo;
    }

    public String getPhoto() 
    {
        return photo;
    }
    public void setCardId(String cardId) 
    {
        this.cardId = cardId;
    }

    public String getCardId() 
    {
        return cardId;
    }
    public void setSoftwareName(String softwareName) 
    {
        this.softwareName = softwareName;
    }

    public String getSoftwareName() 
    {
        return softwareName;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setStartDate(String startDate) 
    {
        this.startDate = startDate;
    }

    public String getStartDate() 
    {
        return startDate;
    }
    public void setFirstDate(String firstDate) 
    {
        this.firstDate = firstDate;
    }

    public String getFirstDate() 
    {
        return firstDate;
    }
    public void setGetWay(String getWay) 
    {
        this.getWay = getWay;
    }

    public String getGetWay() 
    {
        return getWay;
    }
    public void setWayScope(String wayScope) 
    {
        this.wayScope = wayScope;
    }

    public String getWayScope() 
    {
        return wayScope;
    }
    public void setRegistNum(String registNum) 
    {
        this.registNum = registNum;
    }

    public String getRegistNum() 
    {
        return registNum;
    }
    public void setNum(String num) 
    {
        this.num = num;
    }

    public String getNum() 
    {
        return num;
    }
    public void setCardDate(String cardDate) 
    {
        this.cardDate = cardDate;
    }

    public String getCardDate() 
    {
        return cardDate;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("photo", getPhoto())
            .append("cardId", getCardId())
            .append("softwareName", getSoftwareName())
            .append("companyName", getCompanyName())
            .append("startDate", getStartDate())
            .append("firstDate", getFirstDate())
            .append("getWay", getGetWay())
            .append("wayScope", getWayScope())
            .append("registNum", getRegistNum())
            .append("num", getNum())
            .append("cardDate", getCardDate())
            .append("createUser", getCreateUser())
            .append("createDate", getCreateDate())
            .toString();
    }
}
