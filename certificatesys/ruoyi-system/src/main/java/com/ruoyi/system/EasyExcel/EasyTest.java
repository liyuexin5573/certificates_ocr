package com.ruoyi.system.EasyExcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EasyTest {

    public static void main(String[] args) throws Exception {
        easyExcelTest();
    }

    public static String easyExcelTest() throws IOException {
        //1.先对数据库中数据年份月份进行分组
        //2.根据年份遍历查询数据

        //模拟数据库数据，以时间为组,可以在数据库中根据年份和月份 group by后根据年份月份查询数据
        ArrayList<Bjyt> sourceDatas = new ArrayList<>();
        Bjyt bjyt = new Bjyt();
        bjyt.setId(0);
        bjyt.setBjNo("123123");
        bjyt.setBjSj2("3123123");
        bjyt.setBjSj("123123");
        bjyt.setBjAddress("12312213");
        bjyt.setBjNr("123213132");
        bjyt.setHandle(1);
        bjyt.setState("123213132");
        bjyt.setSjc("123123");
        bjyt.setFqBh("123123213");
        bjyt.setIsS(1);
        bjyt.setIsDeleted(1);
        bjyt.setCreateTime(new Timestamp(new java.util.Date().getTime()));
        bjyt.setRms("123123");
        bjyt.setYz("123123");
        bjyt.setUpRemarks("243234");
        //模拟图片路径
        bjyt.setUpImg("D:\\Projects\\RuoYi\\ruoyi-system\\src\\main\\java\\com\\ruoyi\\system\\EasyExcel\\img.jpg");
        bjyt.setUpVideo("D:\\Projects\\RuoYi\\ruoyi-system\\src\\main\\java\\com\\ruoyi\\system\\EasyExcel\\img.jpg");
        sourceDatas.add(bjyt);

        ArrayList<BjytExcelTemplate> bjytExcelTemplates = new ArrayList<>();
        Integer count = 1;
        int year = 0;
        int month = 0;

        if (sourceDatas.size() != 0) {
            Timestamp createTime = sourceDatas.get(0).getCreateTime();
            year = createTime.getYear();
            month = createTime.getMonth();
            for (Bjyt s : sourceDatas) {
                //将数据库数据填入Excel模板
                BjytExcelTemplate bjytExcelTemplate = new BjytExcelTemplate();
                bjytExcelTemplate.setId(count);
                bjytExcelTemplate.setBjNo(s.getBjNo());//报警编号
                bjytExcelTemplate.setBjAddress(s.getBjAddress());//报警位置
                bjytExcelTemplate.setHandle(s.getHandle());//是否处理
                bjytExcelTemplate.setSjc(s.getSjc());//报警时间
                bjytExcelTemplate.setRms(s.getRms());//处理人员
                bjytExcelTemplate.setUpImg(s.getUpImg());//抓拍照片
                bjytExcelTemplate.setUpVideo(s.getUpVideo());//检查情况
                bjytExcelTemplates.add(bjytExcelTemplate);
                count++;
            }
        }
        //3.生成EXCEL文档

        //excel保存路径前缀
        String suffix = "./file/";

        //创建Excel文件
        Workbook wb = new HSSFWorkbook();
        //生成sheet
        Sheet sheet = wb.createSheet(year+"_"+month);
        //创建行
        Row row = null;
        //创建列
        Cell cell = null;
        //创建表头单元格样式
        CellStyle cs_header = wb.createCellStyle();
        //设置字体样式
        Font boldFont = wb.createFont();
        //设置文字类型
        boldFont.setFontName("宋体");
        //设置加粗
        boldFont.setBold(true);
        //设置文字大小
        boldFont.setFontHeightInPoints((short) 18);
        //应用设置的字体
        cs_header.setFont(boldFont);
        //设置边框下、左、右、上
        cs_header.setBorderBottom(BorderStyle.THIN);
        cs_header.setBorderLeft(BorderStyle.THIN);
        cs_header.setBorderRight(BorderStyle.THIN);
        cs_header.setBorderTop(BorderStyle.THIN);
        //水平居中
        cs_header.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cs_header.setVerticalAlignment(VerticalAlignment.CENTER);
        //前景填充色
        cs_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //设置前景填充样式
        cs_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置标题
        String head1 = "xxx路维修工区铁路" + year + "年" + month + "月运行报告";
        String head2 = year + "年" + month + "月，发现xxxxxxx报警" + bjytExcelTemplates.size() + "条，报警详情如下：";
        String head3 = "报警详情";
        Row row1 = sheet.createRow(0);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue(head1);
        cell1.setCellStyle(cs_header);

        Row row2 = sheet.createRow(1);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue(head2);
        cell2.setCellStyle(cs_header);

        Row row3 = sheet.createRow(2);
        Cell cell3 = row3.createCell(0);
        cell3.setCellValue(head3);
        cell3.setCellStyle(cs_header);

        row = sheet.createRow(3);
        //设置单元格行高
        row.setHeightInPoints(24);

        //设置标题
        String[] headers = new String[]{
                "序号", "报警编号", "报警位置", "是否处理", "报警时间", "处理人员", "抓拍照片", "检查情况"
        };
        //逐个设置标题样式
        sheet.setColumnWidth(0,100 * 256);
        sheet.setColumnWidth(0,100 * 256);
        sheet.setColumnWidth(0,100 * 256);
        sheet.setColumnWidth(0,100 * 256);

        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i,15 * 256);
            //创建单元格
            cell = row.createCell(i);
            //设置单元格内容
            cell.setCellValue(headers[i]);
            //设置单元格样式
            cell.setCellStyle(cs_header);
        }
        //创建文本单元格样式
        CellStyle cs_text = wb.createCellStyle();
        //创建文字设置
        Font textFont = wb.createFont();
        //设置文字类型
        textFont.setFontName("Consolas");
        //设置文字大小
        textFont.setFontHeightInPoints((short) 10);
        //应用设置
        cs_text.setFont(textFont);
        //设置边框
        cs_text.setBorderBottom(BorderStyle.THIN);
        cs_text.setBorderLeft(BorderStyle.THIN);
        cs_text.setBorderRight(BorderStyle.THIN);
        cs_text.setBorderTop(BorderStyle.THIN);
        //水平居中
        cs_text.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cs_text.setVerticalAlignment(VerticalAlignment.CENTER);
        //调取数据
        //记录总共多少列（由于接口查询出来的实体类集合，所以不好循环，使用）
        Integer cellSum = 0;
        //将数据写入表格
        for (int i = 0; i < bjytExcelTemplates.size(); i++) {
            //创建行，由于0行是标题，所以+1
            row = sheet.createRow(i + 4);
            //实体类集合不太好循环，所以逐一设置，如果是其他则可使用for循环
            BjytExcelTemplate bjytExcelTemplate = bjytExcelTemplates.get(i);
            row.createCell(0).setCellValue(bjytExcelTemplate.getId());
            row.createCell(1).setCellValue(bjytExcelTemplate.getBjNo());
            row.createCell(2).setCellValue(bjytExcelTemplate.getBjAddress());
            row.createCell(3).setCellValue(bjytExcelTemplate.getHandle());
            row.createCell(4).setCellValue(bjytExcelTemplate.getSjc());
            row.createCell(5).setCellValue(bjytExcelTemplate.getRms());

            byte[] byteArray1 = null;
            String upImg = bjytExcelTemplate.getUpImg();
            InputStream input1 = new FileInputStream(new File(upImg));
            int lenth1 = input1.available();
            byteArray1 = new byte[lenth1];
            input1.read(byteArray1);
            input1.close();
            // 利用HSSFPatriarch将图片写入EXCEL
            Drawing<?> patriarch1 = sheet.createDrawingPatriarch();
            //图片一导出到单元格B2中
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 0, 0,
                    (short) 6, i+4, (short)7,i+5);
            // 插入图片
            patriarch1.createPicture(anchor1, wb.addPicture(byteArray1, HSSFWorkbook.PICTURE_TYPE_JPEG));


            byte[] byteArray2 = null;
            String upVideo = bjytExcelTemplate.getUpVideo();
            InputStream input2 = new FileInputStream(new File(upVideo));
            int lenth2 = input2.available();
            byteArray2 = new byte[lenth2];
            input2.read(byteArray2);
            input2.close();
            // 利用HSSFPatriarch将图片写入EXCEL
            Drawing<?> patriarch2 = sheet.createDrawingPatriarch();
            //图片一导出到单元格B2中
            HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 0, 0,
                    (short) 7, i+4, (short)8,i+5);
            // 插入图片
            patriarch1.createPicture(anchor2, wb.addPicture(byteArray2, HSSFWorkbook.PICTURE_TYPE_JPEG));

            //为每一个单元格设置样式
            for (int j = 0; j < cellSum; j++) {
                row.getCell(j).setCellStyle(cs_text);
            }
        }
        //合并单元格，横向
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, 7));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 0, 7));
        sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 0, 7));
        //设置单元格宽度自适应
        for (int i = 0; i <= cellSum; i++) {
            sheet.autoSizeColumn((short) i, true); //自动调整列宽
        }
        //设置中文文件名称
        String fileName = suffix+year+"_"+month+".xlsx"; //需要提前新建目录
        FileOutputStream out = new FileOutputStream(new File(fileName));
        wb.write(out);
        //强制刷新
        out.flush();
        out.close();
        wb.close();
        return "ok";
    }
}
