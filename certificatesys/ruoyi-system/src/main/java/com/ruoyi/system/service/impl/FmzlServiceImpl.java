package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.AuthService;
import com.ruoyi.system.domain.Fmzl;
import com.ruoyi.system.domain.Fmzl2;
import com.ruoyi.system.mapper.FmzlMapper;

import com.ruoyi.system.service.IFmzlService;
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
 * 发明专利Service业务层处理
 *
 *
 */
@Service
public class FmzlServiceImpl implements IFmzlService {
    private static final Logger log = LoggerFactory.getLogger(FmzlServiceImpl.class);
    @Autowired
    private FmzlMapper fmzlMapper;
    /**
     * 查询发明专利
     *
     * @param id 发明专利主键
     * @return 发明专利
     */
    @Override
    public Fmzl selectFmzlById(Long id) {
        return fmzlMapper.selectFmzlById(id);
    }

    /**
     * 查询发明专利列表
     *
     * @param fmzl 发明专利
     * @return 发明专利
     */
    @Override
    public List<Fmzl> selectFmzlList(Fmzl fmzl) {
        Fmzl2 fmzl2 = new Fmzl2();
        fmzl2.setId(fmzl.getId());
        fmzl2.setPhoto(fmzl.getPhoto());
        fmzl2.setCardId(fmzl.getCardId());
        fmzl2.setInventName(fmzl.getInventName());
        fmzl2.setAuthorName(fmzl.getAuthorName());
        fmzl2.setZlNum(fmzl.getZlNum());
        fmzl2.setApplyDate(fmzl.getApplyDate());
        fmzl2.setOwnerName(fmzl.getOwnerName());
        fmzl2.setAddress(fmzl.getAddress());
        fmzl2.setGrantDate(fmzl.getGrantDate());
        fmzl2.setGrantNum(fmzl.getGrantNum());
        fmzl2.setCreateUser(fmzl.getCreateUser());
        if (fmzl.getCreateDate()!=null){
            String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(fmzl.getCreateDate().getTime()));
            fmzl2.setCreateDate(createDate);
        }
        return fmzlMapper.selectFmzlList(fmzl2);
    }

    /**
     * 新增知识产权发明专利
     *
     * @param fmzl 发明专利
     * @return 结果
     */
    @Override
    public int insertFmzl(Fmzl fmzl) {
        String token = AuthService.getAuth();
        //获取图片
        String photo = fmzl.getPhoto().replace("/profile", "");
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
                    /*if (i == 1 || i == resultWords.size() - 1) {
                        continue;
                    }*/
                    sb.append(((JSONObject) resultWords.get(i)).get("words"));
                }
                String str = sb.toString().replaceAll(" ", "");
                log.info(str);
                try {
                    String cardId = str.substring(str.indexOf("证书号") + 3, str.indexOf("号",str.indexOf("证书号")+3)+1).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setCardId(cardId);
                } catch (Exception e) {
                }
                try {
                    String inventName = str.substring(str.indexOf("发明名称") + 4, str.indexOf("发明人")).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setInventName(inventName);
                } catch (Exception e) {
                }
                try {
                    String authorName = str.substring(str.indexOf("发明人") + 3, str.indexOf("专利号")).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setAuthorName(authorName);
                } catch (Exception e) {
                }
                try {
                    String zlNum = str.substring(str.indexOf("专利号：") + 3, str.indexOf("日")-4).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setZlNum(zlNum);
                } catch (Exception e) {
                }
                try {
                    String applyDate =str.substring(str.indexOf("日") + 1, str.indexOf("专利权人")).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setApplyDate(applyDate);
                } catch (Exception e) {
                }
                try {
                    String address = str.substring(str.indexOf("地址") + 2, str.indexOf("授权公告日")).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setAddress(address);
                } catch (Exception e) {
                }
                try {
                    if(fmzl.getAddress()!=null){
                        String ownerName = str.substring(str.indexOf("专利权人") + 4, str.indexOf("地址")).replaceAll("，", "").replaceAll("：", "");
                        fmzl.setOwnerName(ownerName);
                    }else{
                        String ownerName = str.substring(str.indexOf("专利权人") + 4, str.indexOf("授权公告日")).replaceAll("，", "").replaceAll("：", "");
                        fmzl.setOwnerName(ownerName);
                    }
                } catch (Exception e) {
                }
                try {
                    String grantDate = str.substring(str.indexOf("授权公告日") + 5, str.indexOf("授权公告日")+17).replaceAll("，", "").replaceAll("：", "");
                    fmzl.setGrantDate(grantDate);
                } catch (Exception e) {
                }
                try {
                    if(str.indexOf("授权公告号")>0){
                        String grantNum = str.substring(str.indexOf("授权公告号")+5, str.indexOf("本发明")).replaceAll("，", "").replaceAll("：", "");
                        fmzl.setGrantNum(grantNum);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fmzlMapper.insertFmzl(fmzl);
    }

    /**
     * 修改发明专利
     *
     * @param fmzl 发明专利
     * @return 结果
     */
    @Override
    public int updateFmzl(Fmzl fmzl) {
        return fmzlMapper.updateFmzl(fmzl);
    }
    /**
     * 删除知发明专利
     *
     * @param id 发明专利主键
     * @return 结果
     */
    @Override
    public int deleteFmzlById(Long id) {
        return fmzlMapper.deleteFmzlById(id);
    }
    /**
     * 批量删除发明专利
     *
     * @param ids 需要删除的知识产权主键
     * @return 结果
     */
    @Override
    public int deleteFmzlByIds(String ids) {
        return fmzlMapper.deleteFmzlByIds(Convert.toStrArray(ids));
    }

    @Override
    public Object data() {
        ArrayList<HashMap<String, Integer>> data = fmzlMapper.data();
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
