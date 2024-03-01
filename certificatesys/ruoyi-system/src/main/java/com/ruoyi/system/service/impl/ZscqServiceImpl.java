package com.ruoyi.system.service.impl;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.ruoyi.common.utils.AuthService;
import com.ruoyi.system.domain.Zscq2;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZscqMapper;
import com.ruoyi.system.domain.Zscq;
import com.ruoyi.system.service.IZscqService;
import com.ruoyi.common.core.text.Convert;

/**
 * 知识产权Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-11
 */
@Service
public class ZscqServiceImpl implements IZscqService {
    private static final Logger log = LoggerFactory.getLogger(ZscqServiceImpl.class);

    @Autowired
    private ZscqMapper zscqMapper;

    /**
     * 查询知识产权
     *
     * @param id 知识产权主键
     * @return 知识产权
     */
    @Override
    public Zscq selectZscqById(Long id) {
        return zscqMapper.selectZscqById(id);
    }

    /**
     * 查询知识产权列表
     *
     * @param zscq 知识产权
     * @return 知识产权
     */
    @Override
    public List<Zscq> selectZscqList(Zscq zscq) throws ParseException {
        Zscq2 zscq2 = new Zscq2();
        zscq2.setId(zscq.getId());
        zscq2.setPhoto(zscq.getPhoto());
        zscq2.setCardId(zscq.getCardId());
        zscq2.setSoftwareName(zscq.getSoftwareName());
        zscq2.setCompanyName(zscq.getCompanyName());
        zscq2.setStartDate(zscq.getStartDate());
        zscq2.setFirstDate(zscq.getFirstDate());
        zscq2.setGetWay(zscq.getGetWay());
        zscq2.setWayScope(zscq.getWayScope());
        zscq2.setRegistNum(zscq.getRegistNum());
        zscq2.setNum(zscq.getNum());
        zscq2.setCardDate(zscq.getCardDate());
        zscq2.setCreateUser(zscq.getCreateUser());
        if (zscq.getCreateDate()!=null){
            String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(zscq.getCreateDate().getTime()));
            zscq2.setCreateDate(createDate);
        }

        return zscqMapper.selectZscqList(zscq2);
    }

    /**
     * 新增知识产权
     *
     * @param zscq 知识产权
     * @return 结果
     */
    @Override
    public int insertZscq(Zscq zscq) {
        String token = AuthService.getAuth();
        //获取图片
        String photo = zscq.getPhoto().replace("/profile", "");
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
                    if (i < 2 || i == resultWords.size() - 1) {
                        continue;
                    }
                    sb.append(((JSONObject) resultWords.get(i)).get("words"));
                }
                String str = sb.toString().replaceAll(" ", "");
                log.info(str);
                try {

                        String cardId = str.substring(str.indexOf("证书号") + 3, str.indexOf("号",str.indexOf("证书号")+3)+1).replaceAll("，", "").replaceAll("：", "");
                        zscq.setCardId(cardId);

                } catch (Exception e) {
                }
                try {
                    String softwareName = str.substring(str.indexOf("软件名称") + 4, str.indexOf("著作权人")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setSoftwareName(softwareName);
                } catch (Exception e) {
                }
                try {
                    String companyName = str.substring(str.indexOf("著作权人") + 4, str.indexOf("开发完成日期：")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setCompanyName(companyName);
                } catch (Exception e) {
                }
                try {
                    String startDate = str.substring(str.indexOf("开发完成日期") + 6, str.indexOf("首次发表日期")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setStartDate(startDate);

                } catch (Exception e) {
                }
                try {
                    String firstDate = str.substring(str.indexOf("首次发表日期") + 6, str.indexOf("权利取得方式")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setFirstDate(firstDate);
                } catch (Exception e) {
                }
                try {
                    String getWay = str.substring(str.indexOf("权利取得方式") + 6, str.indexOf("权利范围")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setGetWay(getWay);
                } catch (Exception e) {
                }
                try {
                    String wayScope = str.substring(str.indexOf("权利范围") + 4, str.indexOf("登记号")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setWayScope(wayScope);
                } catch (Exception e) {
                }
                try {
                    String registNum = str.substring(str.indexOf("登记号") + 3, str.indexOf("根据")).replaceAll("，", "").replaceAll("：", "");
                    zscq.setRegistNum(registNum);
                } catch (Exception e) {
                }
                try {
                    String num = str.substring(str.indexOf("No"), str.indexOf("No") + 10).replaceAll("，", "").replaceAll("：", "");
                    zscq.setNum(num);
                } catch (Exception e) {
                }
                try {
                    String cardDate = ((JSONObject) resultWords.get(resultWords.size() - 2)).get("words") + "".replaceAll("，", "").replaceAll("：", "");
                    zscq.setCardDate(cardDate);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zscqMapper.insertZscq(zscq);
    }

    /**
     * 修改知识产权
     *
     * @param zscq 知识产权
     * @return 结果
     */
    @Override
    public int updateZscq(Zscq zscq) {
        return zscqMapper.updateZscq(zscq);
    }

    /**
     * 批量删除知识产权
     *
     * @param ids 需要删除的知识产权主键
     * @return 结果
     */
    @Override
    public int deleteZscqByIds(String ids) {
        return zscqMapper.deleteZscqByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除知识产权信息
     *
     * @param id 知识产权主键
     * @return 结果
     */
    @Override
    public int deleteZscqById(Long id) {
        return zscqMapper.deleteZscqById(id);
    }

    @Override
    public Map data() {
        ArrayList<HashMap<String, Integer>> data = zscqMapper.data();
        HashMap<String, Object> resultData = new HashMap<>();
        ArrayList<Object> key = new ArrayList<>();
        ArrayList<Object> value = new ArrayList<>();
        for (HashMap<String, Integer> datum : data) {
            key.add(datum.get("cardDate"));
            value.add(datum.get("num"));
        }
        resultData.put("card", key);
        resultData.put("num", value);
        return resultData;
    }
}
