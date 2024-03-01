package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class Fmzl2 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主表id */
    private Long id;


    /** 发明专利证书图片 */
    @Excel(name = "发明专利证书图片")
    private String photo;

    /** 证书号 */
    @Excel(name = "证书号")
    private String cardId;

    /** 发明名称 */
    @Excel(name = "发明名称")
    private String inventName;

    /** 发明人 */
    @Excel(name = "发明人")
    private String authorName;

    /** 专利号 */
    @Excel(name = "专利号")
    private String zlNum;

    /** 专利申请日 */
    @Excel(name = "专利申请日")
    private String applyDate;

    /** 专利权人 */
    @Excel(name = "专利权人")
    private String ownerName;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 授权公告日 */
    @Excel(name = "授权公告日")
    private String grantDate;

    /** 授权公告号 */
    @Excel(name = "授权公告号")
    private String grantNum;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getInventName() {
        return inventName;
    }

    public void setInventName(String inventName) {
        this.inventName = inventName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getZlNum() {
        return zlNum;
    }

    public void setZlNum(String zlNum) {
        this.zlNum = zlNum;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(String grantDate) {
        this.grantDate = grantDate;
    }

    public String getGrantNum() {
        return grantNum;
    }

    public void setGrantNum(String grantNum) {
        this.grantNum = grantNum;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String  getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String  createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("photo", getPhoto())
                .append("cardId", getCardId())
                .append("inventName", getInventName())
                .append("authorName", getAuthorName())
                .append("zlNum", getZlNum())
                .append("applyDate", getApplyDate())
                .append("ownerName", getOwnerName())
                .append("address", getAddress())
                .append("grantDate", getGrantDate())
                .append("grantNum", getGrantNum())
                .append("createUser", getCreateUser())
                .append("createDate", getCreateDate())
                .toString();
    }
}
