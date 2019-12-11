//package apache_poi_excel;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import  model.form.Tuple2;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.DVConstraint;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataFormat;
//import org.apache.poi.ss.usermodel.DataValidation;
//import org.apache.poi.ss.usermodel.DataValidationHelper;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.ss.util.CellRangeAddressList;
//import org.apache.poi.xssf.usermodel.XSSFDataValidation;
//import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
//import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * excel 导入导出功能
// * 终止读取行可以是表格没有边框 也可以是中文标题 后|{'required':true,'emptyEnd':true}
// */
//public class ExcelUtil
//{
//    public static final Logger LOG= LoggerFactory.getLogger(ExcelUtil.class);
//    /**
//     * 导出功能
//     * @param list 要导出成excel的数据
//     * @param titles 标题
//     * @param fields  对象的属性
//     * @param workbookOutputStream 输出的excel流，外部负责打开关闭,为ServletOutputStream
//     * @return
//     * @throws Exception
//     */
//    public static void exportObject2Excel(List<? extends Object> list, String [] titles, String [] fields, OutputStream workbookOutputStream)throws Exception
//    {
//        if(titles==null || titles.length==0 || fields==null|| fields.length==0 || titles.length!=fields.length)
//            throw new Exception("titles 或field参数不正确");
//
//
//
//        Workbook workbook=new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet();
//
////        //保护工作表，密码为空串
////        sheet.protectSheet("");
//
//        workbook.setSheetName(0, "第一页");
//        //基本行样式
//        CellStyle baseCellStyle = workbook.createCellStyle();
//        baseCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        baseCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        baseCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        baseCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        baseCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        baseCellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
//        baseCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
//        baseCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
//        baseCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());//默认 背景色
//        baseCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        baseCellStyle.setWrapText(true);
//        //默认是true
//        baseCellStyle.setLocked(false);
//
//        //标题行样式
//        CellStyle titleStyle=workbook.createCellStyle();
//        titleStyle.cloneStyleFrom(baseCellStyle);
//
//        Font titleFont = workbook.createFont();
//        titleFont.setColor(HSSFColor.VIOLET.index);
//        titleFont.setFontHeightInPoints((short) 12);
//        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//        Font redFont = workbook.createFont();
//        redFont.setColor(HSSFColor.RED.index);
//        redFont.setFontHeightInPoints((short) 12);
//        redFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//        titleStyle.setFont(titleFont);
//        titleStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//
//        //数据行样式
//        CellStyle dataStyle=workbook.createCellStyle();
//        dataStyle.cloneStyleFrom(baseCellStyle);
//        dataStyle.setWrapText(false);
//
//        //单元格式为文本
//        CellStyle numStrStyle = workbook.createCellStyle();
//        numStrStyle.cloneStyleFrom(dataStyle);
//        DataFormat format = workbook.createDataFormat();
//        numStrStyle.setDataFormat(format.getFormat("@"));
//
//        //日期行样式
//        CellStyle dateCellStyle=workbook.createCellStyle();
//        dateCellStyle.cloneStyleFrom(dataStyle);
//        short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd");
//        dateCellStyle.setDataFormat(shortDateFormat);
//
//        //锁定
//        CellStyle lockCellStyle=workbook.createCellStyle();
//        lockCellStyle.cloneStyleFrom(dataStyle);
//        lockCellStyle.setLocked(true);
//
//        //颜色
//        CellStyle yellowCellStyle=workbook.createCellStyle();
//        yellowCellStyle.cloneStyleFrom(dataStyle);
//        yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//
//        CellStyle redCellStyle=workbook.createCellStyle();
//        redCellStyle.cloneStyleFrom(dataStyle);
//        redCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
//        Row titleRow = sheet.createRow(0);
//        //冻结标题行
//        sheet.createFreezePane(0, 1);
//
//        for(int i=0;i<titles.length;i++)
//        {
//            Cell cell0 = titleRow.createCell(i);
//            String title=titles[i];
//
//            int default_width=sheet.getColumnWidth(i);
//            //default_width=2048
//            sheet.setColumnWidth(i, (int)(default_width*2.8));
//
//            Tuple2 tuple2= titleExtFunc(title,i,sheet,lockCellStyle); //扩展功能
//            XSSFRichTextString richText=new XSSFRichTextString();
//            if(tuple2.getTwo()!=null && tuple2.getTwo().equals("required"))
//            {
//                String content="*"+tuple2.getOne();
//                richText.setString(content);
//                richText.applyFont(0,1,redFont);
//                richText.applyFont(1,content.length(),titleFont);
//                cell0.setCellValue(richText);
//            }else
//                cell0.setCellValue(tuple2.getOne());
//            cell0.setCellStyle(titleStyle);
//        }
//        int colNum=1;
//        for(Object stu:list)
//        {
//            Row row = sheet.createRow(colNum++);
//            for(int f=0;f<fields.length;f++)
//            {
//                Cell cell= row.createCell(f);
//                cell.setCellStyle(dataStyle);
//                Object obj=getObjectField(stu,fields[f] );
//                if(obj==null)
//                    continue;
//                if(obj instanceof Integer || obj instanceof Long)
//                {
//                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                    cell.setCellValue(Long.parseLong(obj.toString()));
//                }else if(obj instanceof Float ||obj instanceof Double )
//                {
//                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                    cell.setCellValue(Double.parseDouble(obj.toString()));
//                }
//                else if(obj instanceof Date)
//                {
//                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                    cell.setCellStyle(dateCellStyle);
//                    cell.setCellValue((Date)obj);
//                }else if(obj instanceof String)
//                {
//                    String str=(String)obj;
//                    String usedStr=dataExtFunc(str,cell,redCellStyle,yellowCellStyle); //扩展功能
//                    cell.setCellStyle(numStrStyle);
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    cell.setCellValue(usedStr);
//                }
//                else
//                { //char
//                    cell.setCellStyle(numStrStyle);
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    cell.setCellValue(obj.toString());
//                }
//            }
//        }
//        workbook.write(workbookOutputStream);
//    }
//
//    /**
//     * 导出 的 标题扩展功能 ，目前支持
//     * hideCol:true隐藏列,
//     * required:true 必须填加红色*
//     * listValues:[] 下拉列表项
//     * @param title
//     * @param columnNum
//     * @param sheet
//     * @return
//     */
//    protected static Tuple2 titleExtFunc(String title, int columnNum, Sheet sheet,CellStyle lockCellStyle)
//    {
//        Tuple2 tuple2=new Tuple2();
//        String resStr=title;
//        if(title.contains("|"))
//        {
//            String[] strArray=title.split("\\|");
//            resStr=strArray[0];
//            JSONObject jsonObject=  JSONObject.parseObject(strArray[1]);
//            if(jsonObject.get("hideCol")!=null && jsonObject.get("hideCol").equals(Boolean.TRUE))
//            {
//                sheet.setColumnWidth(columnNum, 0);
//            }
//
//            if(jsonObject.get("required")!=null && jsonObject.get("required").equals(Boolean.TRUE))
//            {
//                //外部处理
//                tuple2.setTwo("required");
//            }
////            if(jsonObject.get("readonly")!=null && jsonObject.get("readonly").equals(Boolean.TRUE))
////            {
////                //空密码
////                sheet.protectSheet("");
////                //如何设置可增加、删除行？？
////                sheet.setDefaultColumnStyle(columnNum,lockCellStyle);//可能会标题数据样式不一样
////            }
//
//            DataValidationHelper help = new XSSFDataValidationHelper((XSSFSheet)sheet);
//            int maxRows=1048576 -1 ;//excel 2010 最长 1048576 -1 = 0xFFFFF
//            //int maxRows=0xFFFFF;
//
//            if(jsonObject.get("listValues")!=null && jsonObject.get("listValues") instanceof JSONArray)
//            {
//                JSONArray jsonArray= (JSONArray)jsonObject.get("listValues");
//                String [] values=new String[jsonArray.size()];
//                jsonArray.toArray(values);
//
//                //下拉列表
//                XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(values);
//                //(int firstRow, int lastRow, int firstCol, int lastCol)
//                CellRangeAddressList regions=new CellRangeAddressList(1,maxRows,columnNum,columnNum);
//                DataValidation validation = help.createValidation(constraint, regions);
//                //可为空
//                validation.setEmptyCellAllowed(true);
//                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
//                validation.setShowPromptBox(true);
//                validation.createErrorBox("Error", "请下拉选择值");
//                validation.setShowErrorBox(true);
//                sheet.addValidationData(validation);
//            }
//            if(jsonObject.get("numRange")!=null && jsonObject.get("numRange") instanceof JSONArray)
//            {
//
//                JSONArray jsonArray= (JSONArray)jsonObject.get("numRange");
//                if(jsonArray.size()!=2)
//                {
//                    throw new RuntimeException("numRange值必须为两个元素的数组");
//                }
//                Integer [] values=new Integer[jsonArray.size()];
//                jsonArray.toArray(values);
//
//                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) help.createNumericConstraint( DVConstraint.ValidationType.INTEGER, DVConstraint.OperatorType.BETWEEN, "10", "100" ) ;
////                XSSFDataValidationConstraint dvConstraint= new XSSFDataValidationConstraint(DVConstraint.ValidationType.DECIMAL, DVConstraint.OperatorType.BETWEEN, "0.00", "100.00");
//                CellRangeAddressList addressList = new CellRangeAddressList(1, maxRows, columnNum, columnNum);
//                XSSFDataValidation validation = (XSSFDataValidation) help.createValidation(dvConstraint, addressList);
//                //可为空
//                validation.setEmptyCellAllowed(true);
//                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
//                validation.setShowPromptBox(true);
//                validation.createErrorBox("Error", "仅整数范围从"+values[0]+"到"+values[1]);
//                validation.setShowErrorBox(true);
////			validation.setSuppressDropDownArrow(true);
//                sheet.addValidationData(validation);
//            }
//            if(jsonObject.get("decimalRange")!=null && jsonObject.get("decimalRange") instanceof JSONArray)
//            {
//                JSONArray jsonArray= (JSONArray)jsonObject.get("decimalRange");
//                if(jsonArray.size()!=2)
//                {
//                    throw new RuntimeException("decimalRange值必须为两个元素的数组");
//                }
//                BigDecimal [] values=new BigDecimal[jsonArray.size()];
//                jsonArray.toArray(values);
//
////                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) help.createNumericConstraint( DVConstraint.ValidationType.INTEGER, DVConstraint.OperatorType.BETWEEN, "10", "100" ) ;
//                XSSFDataValidationConstraint dvConstraint= new XSSFDataValidationConstraint(DVConstraint.ValidationType.DECIMAL, DVConstraint.OperatorType.BETWEEN, "0.00", "100.00");
//                CellRangeAddressList addressList = new CellRangeAddressList(1, maxRows, columnNum, columnNum);
//                XSSFDataValidation validation = (XSSFDataValidation) help.createValidation(dvConstraint, addressList);
//                //可为空
//                validation.setEmptyCellAllowed(true);
//                validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
//                validation.setShowPromptBox(true);
//                validation.createErrorBox("Error", "仅小数范围从"+values[0]+"到"+values[1]);
//                validation.setShowErrorBox(true);
////			validation.setSuppressDropDownArrow(true);
//                sheet.addValidationData(validation);
//            }
//        }
//        tuple2.setOne(resStr);
//        return tuple2;
//    }
//    /**
//     * 导出 的 数据扩展功能 ，目前支持设置背景色,目前子类没办法扩展，临时先复制整个类吧
//     * @param data
//     * @param cell
//     * @param redCellStyle
//     * @param yellowCellStyle
//     * @return
//     */
//    private static String dataExtFunc(String data,Cell cell, CellStyle redCellStyle ,CellStyle yellowCellStyle)
//    {
//        String resStr=data;
//        if(data.contains("|"))
//        {
//            String[] strArray=data.split("\\|");
//            resStr=strArray[0];
//            JSONObject jsonObject=  JSONObject.parseObject(strArray[1]);
//            if(jsonObject.get("bgColor")!=null && jsonObject.get("bgColor").equals("yellow"))
//            {
//                cell.setCellStyle(yellowCellStyle);
//            }
//            else if(jsonObject.get("bgColor")!=null && jsonObject.get("bgColor").equals("red"))
//            {
//                cell.setCellStyle(redCellStyle);
//            }
////            if(jsonObject.get("readonly")!=null && jsonObject.get("readonly").equals("true"))
////            {
////                cell.setCellStyle(lockCellStyle);
////            }
//        }
//        return resStr;
//    }
//
//
//    public  static int parseEmptyEndIndex(String[] titles)
//    {
//        for(int i=0;i<titles.length;i++)
//        {
//            String title=titles[i];
//            if(title.contains("|"))
//            {
//                String[] strArray = title.split("\\|");
//                String resStr = strArray[0];
//                JSONObject jsonObject = JSONObject.parseObject(strArray[1]);
//                if (jsonObject.get("emptyEnd") != null && jsonObject.get("emptyEnd").equals(Boolean.TRUE))
//                {
//                    return i;
//                }
//            }
//        }
//        return -1;
//
//    }
//
//    /**
//     * 导入excel
//     * @param workbookInputStream excel 的输入流，外部负责打开和关闭
//     * @param fields 类的属性
//     * @param clazz  哪种类
//     * @return 集合数据
//     * @throws Exception
//     */
//    public static <T> List<T>  importExcel2Object(InputStream workbookInputStream, String [] fields, String [] titles,Class<T> clazz)throws Exception
//    {
//        List<T> res=new ArrayList<>();
//        Workbook workbook = WorkbookFactory.create(workbookInputStream);
//
//        Sheet sheet = workbook.getSheetAt(0);
//
//        int endIndex  = parseEmptyEndIndex(titles);
//
//        int  total=sheet.getLastRowNum();
//        O:   for(int i=1;i<=total;i++)
//        {
//            Row row = sheet.getRow(i);
//            T obj=clazz.getDeclaredConstructor().newInstance();
//            for(int f=0;f<fields.length;f++)
//            {
//                Cell cell = row.getCell(f);
//                //如终止列不写内容cell为null
//                if(cell==null)
//                {
//                    if(endIndex==f)
//                    {
//                        break O;
//                    }
//                }else
//                {
//                    if(endIndex!=-1)
//                    {
//                        if(endIndex==f && StringUtils.isBlank(cell.toString()))
//                        {
//                            break O;
//                        }
//                    }else
//                    {
//                        if(cell.getCellStyle().getBorderBottom()==HSSFCellStyle.BORDER_NONE)//防止用户多输入了一行空白
//                        {
//                            break O;
//                        }
//                    }
//                    Object value = readCellValue(cell);
//                    setObjectField(obj, fields[f],titles[f], value);
//                }
//            }
//            res.add(obj);
//        }
//        return res;
//    }
//    private  static Object readCellValue(Cell cell)
//    {
//        int type=cell.getCellType();
//        Object res=null;
//
//        switch (type)
//        {
//            case Cell.CELL_TYPE_NUMERIC:
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    res=cell.getDateCellValue();
//                    break;
//                }
//                double dbl=cell.getNumericCellValue();
//                int num=(int)dbl;
//                if(dbl-num>0)
//                    res=dbl;
//                else
//                    res=num;
//                break;
//            case  Cell.CELL_TYPE_STRING:
//                res=cell.getStringCellValue();
//                break;
//            case Cell.CELL_TYPE_FORMULA:
//                res=cell.getStringCellValue();
//                break;
//            default:
//                res=cell.getStringCellValue();
//        }
//        return res;
//    }
//    private static Object getObjectField(Object obj,String strField)throws Exception
//    {
//        if(obj instanceof  Map)
//        {
//            Map map=(Map)obj;
//            return map.get(strField);
//        }
//        Field field=obj.getClass().getDeclaredField(strField);
//        if(!field.isAccessible())
//            field.setAccessible(true);
//        Object res=field.get(obj);
//        return  res;
//    }
//    private  static void setObjectField(Object obj,String strField,String strTitle,Object value)throws Exception
//    {
//        if(value==null)
//            return ;
//        if(obj instanceof  Map)
//        {
//            Map map=(Map)obj;
//            map.put(strField,value);
//            return;
//        }
//        Field  field=obj.getClass().getDeclaredField(strField);
//        if(!field.isAccessible())
//            field.setAccessible(true);
//
//        if(value instanceof String  )
//        {
//            if(field.getType() == Timestamp.class || field.getType() ==Date.class)
//            {
//                if("".equals(value))
//                    value = null;
//
//            }
//            else if(field.getType() == Boolean.class || field.getType() ==boolean.class)
//            {
//                if("".equals(value))
//                    value = false;
//            }
//            else  if(field.getType() == long.class || field.getType() == Long.class)
//            {
//                if("".equals(value))
//                {
//                    value=0L;
//                }else
//                {
//                    try{
//                        value=Long.parseLong(value.toString());
//                    }catch(Exception e)
//                    {
//                        LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                        throw new NumberFormatException(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长");
//                    }
//                }
//            }
//            else if(field.getType() == Integer.class || field.getType() ==int.class)
//            {
//                if("".equals(value))
//                {
//                    value=0;
//                }else
//                {
//                    try{
//                        value=Integer.parseInt(value.toString());
//                    }catch(Exception e)
//                    {
//                        LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                        throw new NumberFormatException(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长");
//                    }
//                }
//            }
//            else  if(field.getType() == double.class || field.getType() ==Double.class)
//            {
//                if("".equals(value))
//                {
//                    value=0d;
//                }else
//                {
//                    try{
//                        value=Double.parseDouble(value.toString());
//                    }catch(Exception e)
//                    {
//                        LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                        throw new NumberFormatException(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长");
//                    }
//                }
//            }
//            else if(field.getType() == float.class || field.getType() ==Float.class)
//            {
//                if("".equals(value))
//                {
//                    value=0f;
//                }else
//                {
//                    try{
//                        value=Float.parseFloat(value.toString());
//                    }catch(Exception e)
//                    {
//                        LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                        throw new NumberFormatException(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长");
//                    }
//                }
//            }
//        } else
//        {//value非String
//
//            if(field.getType() == Timestamp.class &&   value instanceof  Date)
//            {
//                value=new Timestamp( ((Date) value).getTime());
//            }
//            else if  (field.getType()  == Long.class || field.getType()  == long.class )
//            {
//                if( value instanceof Integer)
//                {
//                    try{
//                        value=Long.parseLong(value.toString());
//                    }catch(Exception e)
//                    {
//                        LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                        throw new NumberFormatException(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长");
//                    }
//                }else if(  value instanceof Double)
//                {   //长整数在excel中显示为科学计数法变Double
//                    String longStr=new DecimalFormat("#").format((Double)value);
//                    try{
//                        value=Long.parseLong(longStr);
//                    }catch(Exception e)
//                    {
//                        LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                        throw new NumberFormatException(strTitle+"设置值"+value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
//                    }
//                }
//            } else if
//            (
//                    (field.getType()  == Double.class
//                            || field.getType()  == double.class
//                    )
//                            &&  value.getClass()!=  Double.class
//                            &&  value.getClass()!=  double.class
//            )
//            {
//                try{
//                    value=Double.parseDouble(value.toString());
//                }catch(Exception e)
//                {
//                    LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                    throw new NumberFormatException(strTitle+"设置值"+value+"值不正确，请输入正确的数值，不要超长");
//
//                }
//            }else if( field.getType()  == String.class  && value.getClass()  != String.class )
//            {
//                value=value.toString();
//            }
//
//        }//value非String
//
//
//        //为数值类型超长报错,类型不匹配错
//        if(field.getType() == Long.class || field.getType() == long.class
//                || field.getType() == Integer.class || field.getType() == int.class
//                || field.getType() == Double.class || field.getType() == double.class
//                || field.getType() == Float.class || field.getType() == float.class
//        )
//        {
//            try{
//                field.set(obj,value);
//            }catch(Exception e)
//            {
//                LOG.warn(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长,原因为:",e);
//                throw new NumberFormatException(strTitle+"设置值"+value+"不正确,请输入正确的数值，不要超长");
//            }
//        }else
//        {
//            try {
//                field.set(obj, value);
//            }catch (Exception e){
//                LOG.error("导入excel时字段"+field.getName()+"设置值 "+value+"时出错误,原因",e);
//                LOG.error(e.getMessage());
//                throw e;
//            }
//        }
//
//    }
//}