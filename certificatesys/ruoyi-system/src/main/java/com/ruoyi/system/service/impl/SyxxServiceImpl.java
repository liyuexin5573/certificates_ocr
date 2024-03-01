package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.AuthConfig;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.AuthService;
import com.ruoyi.system.domain.Syxx;
import com.ruoyi.system.domain.Syxx2;

import com.ruoyi.system.mapper.SyxxMapper;
import com.ruoyi.system.service.ISyxxService;
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
 * 实用新型发明专利Service业务层处理
 *
 *
 */
@Service
public class SyxxServiceImpl implements ISyxxService {
    private static final Logger log = LoggerFactory.getLogger(SyxxServiceImpl.class);
    @Autowired
    private SyxxMapper syxxMapper;

    /**
     * 查询发明专利
     *
     * @param id 发明专利主键
     * @return 发明专利
     */
    @Override
    public Syxx selectSyxxById(Long id) {
        return syxxMapper.selectSyxxById(id);
    }

    /**
     * 查询发明专利列表
     *
     * @param syxx 发明专利
     * @return 发明专利
     */
    @Override
    public List<Syxx> selectSyxxList(Syxx syxx) {
        Syxx2 syxx2 = new Syxx2();
        syxx2.setId(syxx.getId());
        syxx2.setPhoto(syxx.getPhoto());
        syxx2.setCardId(syxx.getCardId());
        syxx2.setInventName(syxx.getInventName());
        syxx2.setAuthorName(syxx.getAuthorName());
        syxx2.setZlNum(syxx.getZlNum());
        syxx2.setApplyDate(syxx.getApplyDate());
        syxx2.setOwnerName(syxx.getOwnerName());
        syxx2.setAddress(syxx.getAddress());
        syxx2.setGrantDate(syxx.getGrantDate());

        syxx2.setGrantNum(syxx.getGrantNum());
        syxx2.setCreateUser(syxx.getCreateUser());
        if (syxx.getCreateDate()!=null){
            String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(syxx.getCreateDate().getTime()));
            syxx2.setCreateDate(createDate);
        }
        return syxxMapper.selectSyxxList(syxx2);
    }

    /**
     * 新增发明专利
     *
     * @param syxx 发明专利
     * @return 结果
     */
    @Override
    public int insertSyxx(Syxx syxx) {
        String token = AuthService.getAuth();
        //获取图片
        String photo = syxx.getPhoto().replace("/profile", "");
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
                        syxx.setCardId(cardId);

                } catch (Exception e) {
                }
                try {
                    String inventName = str.substring(str.indexOf("实用新型名称") + 6, str.indexOf("发明人")).replaceAll("，", "").replaceAll("：", "");
                    syxx.setInventName(inventName);
                } catch (Exception e) {
                }
                try {
                    String authorName = str.substring(str.indexOf("发明人") + 3, str.indexOf("专利号")).replaceAll("，", "").replaceAll("：", "");
                    syxx.setAuthorName(authorName);
                } catch (Exception e) {
                }
                try {
                    String zlNum = str.substring(str.indexOf("专利号：") + 3, str.indexOf("申请日")-2).replaceAll("，", "").replaceAll("：", "");
                    syxx.setZlNum(zlNum);
                } catch (Exception e) {
                }
                try {
                    String applyDate = str.substring(str.indexOf("申请日") + 3, str.indexOf("专利权人")).replaceAll("，", "").replaceAll("：", "");
                    syxx.setApplyDate(applyDate);
                } catch (Exception e) {
                }
                try {
                    String address = str.substring(str.indexOf("地址") + 2, str.indexOf("授权公告日")).replaceAll("，", "").replaceAll("：", "");
                    syxx.setAddress(address);
                } catch (Exception e) {
                }
                try {
                    if(syxx.getAddress()!=null){
                        String ownerName = str.substring(str.indexOf("专利权人") + 4, str.indexOf("地址")).replaceAll("，", "").replaceAll("：", "");
                        syxx.setOwnerName(ownerName);
                    }else{
                        String ownerName = str.substring(str.indexOf("专利权人") + 4, str.indexOf("授权公告日")).replaceAll("，", "").replaceAll("：", "");
                        syxx.setOwnerName(ownerName);
                    }
                } catch (Exception e) {
                }
                try {
                    String grantDate =str.substring(str.indexOf("授权公告日") + 5, str.indexOf("授权公告日")+17).replaceAll("，", "").replaceAll("：", "");
                    syxx.setGrantDate(grantDate);
                } catch (Exception e) {
                }
                try {
                    if(str.indexOf("授权公告号")>0){
                        if(str.indexOf("本实用新型")>0){
                            String grantNum = str.substring(str.indexOf("授权公告号")+5, str.indexOf("本实用新型")).replaceAll("，", "").replaceAll("：", "");
                            syxx.setGrantNum(grantNum);
                        }else {
                            String grantNum = str.substring(str.indexOf("授权公告号")+5, str.indexOf("国家知识产权局")).replaceAll("，", "").replaceAll("：", "");
                            syxx.setGrantNum(grantNum);
                        }

                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return syxxMapper.insertSyxx(syxx);
    }

    /**
     * 修改发明专利
     *
     * @param syxx 发明专利
     * @return 结果
     */
    @Override
    public int updateSyxx(Syxx syxx) {
        return syxxMapper.updateSyxx(syxx);
    }
    /**
     * 删除发明专利
     *
     * @param id 发明专利主键
     * @return 结果
     */
    @Override
    public int deleteSyxxById(Long id) {
        return syxxMapper.deleteSyxxById(id);
    }
    /**
     * 批量删除发明专利
     *
     * @param ids 需要删除的知识产权主键
     * @return 结果
     */
    @Override
    public int deleteSyxxByIds(String ids) {
        return syxxMapper.deleteSyxxByIds(Convert.toStrArray(ids));
    }

    @Override
    public Object data() {
        ArrayList<HashMap<String, Integer>> data = syxxMapper.data();
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
