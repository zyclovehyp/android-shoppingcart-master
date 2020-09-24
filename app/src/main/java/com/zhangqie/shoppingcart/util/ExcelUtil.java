package com.zhangqie.shoppingcart.util;

import android.content.Context;
import android.widget.Toast;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
    public static WritableFont arial14font = null;

    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";


    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);//对齐格式
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); //设置边框

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     */
    public static void initExcel(String fileName, String title) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(title, 0);

            sheet.setRowView(0, 340); //设置行高

            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化Excel
     */
    public static void initExcel(String fileName, Map<String, ArrayList<ArrayList<String>>> allData) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            Set<String> keys = allData.keySet();

            Iterator<String> ki = keys.iterator();

            int i = 0;
            while (ki.hasNext()) {

                WritableSheet sheet = workbook.createSheet(ki.next(), i);

                i++;
                sheet.setRowView(0, 340); //设置行高
            }

            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);


                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 5) {
                            sheet.setColumnView(i, list.get(i).length() + 8); //设置列宽
                        } else {
                            sheet.setColumnView(i, list.get(i).length() + 5); //设置列宽
                        }
                    }
                    sheet.setRowView(j + 1, 350); //设置行高
                }

                writebook.write();
                Toast.makeText(c, "导出到手机存储中文件夹Record成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }



    public static void writeObjListToExcel(String filePath, Map<String, ArrayList<ArrayList<String>>> allData, Context c) {
        ArrayList<ArrayList<String>> objList = null;
        WritableWorkbook writebook = null;
        InputStream in = null;
        try {
            WorkbookSettings setEncode = new WorkbookSettings();
            setEncode.setEncoding(UTF8_ENCODING);
            in = new FileInputStream(new File(filePath));
            Workbook workbook = Workbook.getWorkbook(in);

            writebook = Workbook.createWorkbook(new File(filePath), workbook);


            Iterator<String> key = allData.keySet().iterator();

            int i = 0;
            while (key.hasNext()) {

                objList = allData.get(key.next());
                WritableSheet sheet = writebook.getSheet(i);


                writeSheet(objList, sheet);
                i++;
            }


            writebook.write();
            Toast.makeText(c, filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writebook != null) {
                try {
                    writebook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void writeSheet(ArrayList<ArrayList<String>> objList, WritableSheet sheet) throws WriteException {


        boolean isDan = false;
        int line = 0;
        for (int j = 0; j < objList.size(); j++) {
            ArrayList<String> list = (ArrayList<String>) objList.get(j);
            for (int i = 0; i < list.size(); i++) {
                if (j == 0 || j == line) {
                    sheet.addCell(new Label(i, j + 1, list.get(i), arial10format));
                } else {
                    sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                }
                if (j == 5) {
                    isDan = "5".equals(list.get(0));
                }
                if (isDan) {
                    line = 31;
                } else {
                    line = 28;
                }
                sheet.setColumnView(i, 7); //设置列宽
            }
            sheet.setRowView(j + 1, 350); //设置行高
        }
        sheet.mergeCells(0, 1, 7, 1);

        //单双径啊。。。
        //双径。。。。。
        if (isDan) {
            sheet.mergeCells(1, 28, 7, 28);//其他记录
            sheet.mergeCells(1, 29, 7, 29);//

            sheet.mergeCells(1, 30, 2, 30);
            sheet.mergeCells(4, 30, 5, 30);
            sheet.mergeCells(0, 32, 8, 32);

           /* sheet.mergeCells(1, 29, 7, 29);
            sheet.mergeCells(1, 30, 2, 30);
            sheet.mergeCells(4, 30, 5, 30);
            sheet.mergeCells(0, 32, 8, 32);*/
        } else {
            sheet.mergeCells(1, 25, 7, 25);//其他记录
            sheet.mergeCells(1, 26, 7, 26);//
            sheet.mergeCells(1, 27, 2, 27);
            sheet.mergeCells(4, 27, 5, 27);
            sheet.mergeCells(0, 29, 8, 29);
          /*  sheet.mergeCells(1, 26, 7, 26);
            sheet.mergeCells(1, 27, 2, 27);
            sheet.mergeCells(4, 27, 5, 27);
            sheet.mergeCells(0, 29, 8, 29);*/
        }
    }

    public static void exportCSV(String filePath, ArrayList<ArrayList<String>> objList) {

        File file = new File(filePath);

        FileOutputStream ot = null;

        OutputStreamWriter osw = null;

        BufferedWriter bw = null;


        try {

            ot = new FileOutputStream(file);
            osw = new OutputStreamWriter(ot, "UTF-8");
            bw = new BufferedWriter(osw);

            if (null != objList && objList.size() > 0) {
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);

                    StringBuffer row = new StringBuffer();

                    for (int i = 0; i < list.size(); i++) {
                        row.append(list.get(i)).append(",");
                    }
                    bw.append(row.toString()).append("\r\n");
                }

            }


        } catch (Exception e) {

        } finally {
            try {
                bw.close();
                osw.close();
                ot.close();
            } catch (Exception e) {

            }
        }


    }
}