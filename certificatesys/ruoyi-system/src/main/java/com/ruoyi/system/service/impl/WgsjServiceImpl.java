package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.AuthService;
import com.ruoyi.system.domain.Wgsj;
import com.ruoyi.system.domain.Wgsj2;

import com.ruoyi.system.mapper.WgsjMapper;
import com.ruoyi.system.service.IWgsjService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 外观设计Service业务层处理
 *
 *
 */
@Service
public class WgsjServiceImpl implements IWgsjService {
    private static final Logger log = LoggerFactory.getLogger(WgsjServiceImpl.class);
    @Autowired
    private WgsjMapper wgsjMapper;

    /**
     * 查询外观设计
     *
     * @param id 外观设计主键
     * @return 外观设计
     */
    @Override
    public Wgsj selectWgsjById(Long id) {
        return wgsjMapper.selectWgsjById(id);
    }

    /**
     * 查询外观设计列表
     *
     * @param wgsj 外观设计
     * @return 外观设计
     */
    @Override
    public List<Wgsj> selectWgsjList(Wgsj wgsj) {
        Wgsj2 wgsj2 = new Wgsj2();
        wgsj2.setId(wgsj.getId());
        wgsj2.setPhoto(wgsj.getPhoto());
        wgsj2.setCardId(wgsj.getCardId());
        wgsj2.setInventName(wgsj.getInventName());
        wgsj2.setAuthorName(wgsj.getAuthorName());
        wgsj2.setZlNum(wgsj.getZlNum());
        wgsj2.setApplyDate(wgsj.getApplyDate());
        wgsj2.setOwnerName(wgsj.getOwnerName());
        wgsj2.setAddress(wgsj.getAddress());
        wgsj2.setGrantDate(wgsj.getGrantDate());
        wgsj2.setGrantNum(wgsj.getGrantNum());
        wgsj2.setCreateUser(wgsj.getCreateUser());
        if (wgsj.getCreateDate()!=null){
            String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(wgsj.getCreateDate().getTime()));
            wgsj2.setCreateDate(createDate);
        }
        return wgsjMapper.selectWgsjList(wgsj2);
    }

    /**
     * 新增外观设计
     *
     * @param wgsj 外观设计
     * @return 结果
     */
    @Override
    public int insertWgsj(Wgsj wgsj) {
        String token = AuthService.getAuth();
        //获取图片
        String photo = wgsj.getPhoto().replace("/profile", "");
        String profile = "D:/ruoyi/uploadPath";
        File file = new File(profile + photo);
        // 将图片文件转化为二进制流
        InputStream input = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            input = new FileInputStream(file);
            data = new byte[input.available()];
            input.read(data);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 图片头
        String paramStr = "image=" + URLEncoder.encode(Base64.encodeBase64String(data));
        System.out.println(paramStr);

        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + token;

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            log.info("sendPost - {}", url);
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(paramStr);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
            HashMap resultMap = JSON.parseObject(result.toString(), HashMap.class);
            Object arrayResult = resultMap.get("words_result");
            if (arrayResult != null) {
                JSONArray resultWords = (JSONArray) arrayResult;
                //保存证书信息
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < resultWords.size(); i++) {
                    sb.append(((JSONObject) resultWords.get(i)).get("words"));
                }
                String str = sb.toString().replaceAll(" ", "");
                log.info(str);
                try {
                    String cardId = str.substring(str.indexOf("证书号") + 3, str.indexOf("号",str.indexOf("证书号")+3)+1).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setCardId(cardId);
                } catch (Exception e) {
                }
                try {
                    String inventName = str.substring(str.indexOf("外观设计名称") + 6, str.indexOf("设计人")).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setInventName(inventName);
                } catch (Exception e) {
                }
                try {
                    String authorName = str.substring(str.indexOf("设计人") + 3, str.indexOf("专利号")).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setAuthorName(authorName);
                } catch (Exception e) {
                }
                try {
                    String zlNum = str.substring(str.indexOf("专利号：") + 3, str.indexOf("申请日")-2).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setZlNum(zlNum);
                } catch (Exception e) {
                }
                try {
                    String applyDate = str.substring(str.indexOf("申请日") + 3, str.indexOf("专利权人")).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setApplyDate(applyDate);
                } catch (Exception e) {
                }
                try {
                    String address = str.substring(str.indexOf("地址") + 2, str.indexOf("授权公告日")).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setAddress(address);
                } catch (Exception e) {
                }
                try {
                    if(wgsj.getAddress()!=null){
                        String ownerName = str.substring(str.indexOf("专利权人") + 4, str.indexOf("地址")).replaceAll("，", "").replaceAll("：", "");
                        wgsj.setOwnerName(ownerName);
                    }else{
                        String ownerName = str.substring(str.indexOf("专利权人") + 4, str.indexOf("授权公告日")).replaceAll("，", "").replaceAll("：", "");
                        wgsj.setOwnerName(ownerName);
                    }
                } catch (Exception e) {
                }
                try {
                    String grantDate =str.substring(str.indexOf("授权公告日") + 5, str.indexOf("授权公告日")+17).replaceAll("，", "").replaceAll("：", "");
                    wgsj.setGrantDate(grantDate);
                } catch (Exception e) {
                }
                try {
                    if(str.indexOf("授权公告号")>0){
                        String grantNum = str.substring(str.indexOf("授权公告号")+5, str.indexOf("本外观设计")).replaceAll("，", "").replaceAll("：", "");
                        wgsj.setGrantNum(grantNum);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wgsjMapper.insertWgsj(wgsj);
    }

    /**
     * 修改外观设计
     *
     * @param wgsj 外观设计
     * @return 结果
     */
    @Override
    public int updateWgsj(Wgsj wgsj) {
        return wgsjMapper.updateWgsj(wgsj);
    }
    /**
     * 删除外观设计
     *
     * @param id 外观设计主键
     * @return 结果
     */
    @Override
    public int deleteWgsjById(Long id) {
        return wgsjMapper.deleteWgsjById(id);
    }
    /**
     * 批量删除外观设计
     *
     * @param ids 需要删除的知识产权主键
     * @return 结果
     */
    @Override
    public int deleteWgsjByIds(String ids) {
        return wgsjMapper.deleteWgsjByIds(Convert.toStrArray(ids));
    }

    @Override
    public Object data() {
        ArrayList<HashMap<String, Integer>> data = wgsjMapper.data();
        HashMap<String, Object> resultData = new HashMap<>();
        ArrayList<Object> key = new ArrayList<>();
        ArrayList<Object> value = new ArrayList<>();
        for (HashMap<String, Integer> datum : data) {
            key.add(datum.get("grantDate"));
            value.add(datum.get("num"));
        }
        resultData.put("card", key);
        resultData.put("num", value);
        return resultData;
    }
}
