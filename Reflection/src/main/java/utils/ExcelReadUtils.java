package utils;

import entity.ArrayDemo;
import entity.CostItem;
import entity.SkillSoulSetting;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class ExcelReadUtils<T> {

    public static final String STRING = "String";
    public static final String INTEGER = "int";
    public static final String ARRAY = ";";

    public static final String EXCEL_XLSX = "xlsx";
    public static final String EXCEL_LSX = "lsx";
    public static final String START_READ = "SERVER";
    public static final String END_READ = "END";

    private List<String> filedName = new LinkedList<String>();
    private ArrayList<T> list = new ArrayList<T>();
    private Class<T> kind;

    public ExcelReadUtils(Class<T> kind) {
        this.kind = kind;
    }

    /**
     * 读取Excel文件内容
     */
    public void readExcel(String fileName) throws IOException, InvalidFormatException, IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        Workbook wb = getWorkBook(fileName);
        if (wb==null){
            throw new IllegalArgumentException("");
        }
        Sheet sheet = null;
        int numOfSheets = wb.getNumberOfSheets();
        for (int i = 0 ;i < numOfSheets ; i++){
            sheet = wb.getSheetAt(i);
            if (sheet == null){
                continue;
            }
            readSheet(sheet,i);
        }
    }

    public String readSheet(Sheet sheet,int flag) throws IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        Row row = null;
        Cell cell = null;
        StringBuilder sb = new StringBuilder();
        Map<String,Integer> map = getReadRange(sheet,flag);
        int firstReadNum = map.get("start");
        int endReadNum = map.get("end");
        for (int i = firstReadNum ;i<=endReadNum ;i++){
            row = sheet.getRow(i);
            if (row!=null){
                list.add(changeTo(row));
            }
        }
        return sb.toString();
    }

    public Map<String,Integer> getReadRange(Sheet sheet,int flag){
        Map<String,Integer> map = new HashMap<String, Integer>();
        Row row = null;
        Cell cell = null;
        String str = "";
        int rowNum = sheet.getLastRowNum();
        for (int i = 0 ; i<rowNum ; i++){
            row = sheet.getRow(i);
            cell = row.getCell(0);
            if (cell!=null){
                if (START_READ.equals(cell.getStringCellValue())){
                    if (flag==0){
                        int colNum = row.getLastCellNum();
                        for (int j=1;j<colNum;j++){  ///修改
                            cell = row.getCell(j);
                            str = cell.getStringCellValue();
                            if (str!=null && !"".equals(str)){
                                filedName.add(str);
                            }
                        }
                    }
                    map.put("start",i+1);
                }else if (END_READ.equals(cell.getStringCellValue())){
                    map.put("end",i);
                    break;
                }
            }

        }
        return map;
    }

    /**
     * 获取对应Excel文件的Workbook
     */
    public Workbook getWorkBook(String fileName) throws IOException, InvalidFormatException {
        InputStream in = null;
        Workbook wb = null;
        String suffiex = getSuffiex(fileName);
        if ("".equals(suffiex)) {
            throw new IllegalArgumentException("文件后缀名不能为空");
        }
        if (EXCEL_LSX.equals(suffiex) || EXCEL_XLSX.equals(suffiex)){
            try{
                 in = new FileInputStream(fileName);
                 wb = WorkbookFactory.create(in);
                 return wb;
            }finally {
                if (in != null){
                    in.close();
                }
                if (wb!=null){
                    wb.close();
                }
            }
        }
        throw new IllegalArgumentException("文件后缀名必须为.xlsx或.xls");
    }

    /**
     * 获得文件路径的后缀名
     */
    public String getSuffiex(String fileName){
        if (fileName==null || fileName==""){
            throw new IllegalArgumentException("文件名为空");
        }
        int index = fileName.lastIndexOf(".");
        if (index==-1){
            return "";
        }
        return fileName.substring(index+1,fileName.length());
    }

    public T changeTo(Row row) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        T t = kind.newInstance();
        Cell cell = null;
        int colNum = filedName.size();
        for (int i = 1 ;i<=colNum;i++){
            cell = row.getCell(i);
            if (cell!=null){
                typeConversion(t,cell,i-1);
            }
        }
        return t;
    }

    public T typeConversion(T t,Cell cell,int index) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        Field field = null;
        cell.setCellType(CellType.STRING);
        String str = cell.getStringCellValue();
        field = t.getClass().getDeclaredField(filedName.get(index));
        field.setAccessible(true);
        Type type = field.getGenericType();
        String temp = type.toString();
        System.out.println(temp);
        if (temp.endsWith(STRING)){
            field.set(t,str);
        }else if (temp.endsWith(INTEGER)){
            if ("".equals(str)){
                field.set(t,0);
            }else {
                field.set(t,Integer.valueOf(str));
            }

        }else if (temp.endsWith(ARRAY)){
            String strArray[];
            CostItem[] costItems;
            if (str.length()>0){
                String s[] = temp.split(" ");
                int length = s.length;
                String name = s[length-1].substring(2,s[length-1].length()-1);
                Class c = Class.forName(name);
                JSONArray array = JSONArray.fromObject(str);
                int size = array.size();
                costItems = new CostItem[size];
                for (int i = 0 ;i<size;i++){
                    JSONObject obj = array.getJSONObject(i);
                    costItems[i] = (CostItem) JSONObject.toBean(obj,c);
                }
                field.set(t,costItems);
            }

        }else if (temp.startsWith("java.util.Map")){
            if (temp.indexOf(">")==-1){
                Map map = JSONObject.fromObject(str);
                System.out.println(map);
            }else {
                if (str!=null && !"".equals(str)){
                    System.out.println(str);
                    Map map = JSONObject.fromObject(str);
                    System.out.println(map);
                    String[] s = temp.substring(14,temp.length()-1).split(",");
                    for (String ss : s){
                        Class<?> name = Class.forName(ss);
                        Object[] objects = name.getEnumConstants();
                        
                    }
                }

            }
        }
        return t;
    }


    public static void main(String[] args) throws IOException, InvalidFormatException, IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        ExcelReadUtils utils = new ExcelReadUtils(SkillSoulSetting.class);
        utils.readExcel("D:/JetBrains/IDEAWorkSpace/LearningDemo/Reflection/src/main/resources/SkillSoulSetting.xlsx");
        for(int i=0;i<utils.list.size();i++){
            System.out.println(utils.list.get(i));
        }
//        ArrayDemo demo = new ArrayDemo();
//        Field field = demo.getClass().getDeclaredField("unitValue");
//        System.out.println(field.getGenericType());
    }
}

