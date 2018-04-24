package com.superma.fileDao;

import java.io.*;

public class FileDao {
    //生成文件路径
    private static String path = "C:\\Users\\Administrator\\Desktop\\毕设\\生成文件\\";//创建文件路径
    //文件路径+名称
    private static  String filenameTemp;
    /*
     * 创建文件
     * @param fileName  文件名称
     * @param filecontent   文件内容
     * @return  是否创建成功，成功则返回true
     */
    private boolean cFile(String fileNma, String filecontent, String tpye) throws IOException {
        boolean bool = false;
        filenameTemp = path + fileNma + tpye;//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        if (!file.exists()) {
            file.createNewFile();
            bool = true;
            System.out.println("success creat file" + filenameTemp);
            if (tpye.equals(".wxml")) {
            	 wFileContent(filenameTemp,"<!--This is "+fileNma+"-->\r\n"+filecontent);
			}
            if (tpye.equals(".js")) {
            	 wFileContent(filenameTemp,"///This is "+fileNma+"-->\r\n"+filecontent);
			}
            if (tpye.equals(".wxss")) {
            	 wFileContent(filenameTemp,"/*This is "+fileNma+"*/\r\n"+filecontent);
            }
            //创建成功以后写入内容到文件里
           
        }
        return bool;
    }

    /*
     * 向文件中写入内容
     * @param filePath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    private static boolean wFileContent(String filePath, String newstr) throws IOException {
        boolean bool = false;
        String filein = newstr + "\r\n";//重新写入的行，换行
        String temp ="";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;

        File file = new File(filePath);//文件路径（包括文件名称）
        //将文件读入输入流
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            while ((temp = br.readLine()) != null) {
            	buffer.append(temp);
            	//行与行之间的分隔符 相当于“\n”
            	buffer = buffer.append(System.getProperty("line.separator"));
				
			}
            
//            for (int i = 0; ; i++){
//                buffer.append(temp);
//                //行与行之间的分隔符 相当于“\n”
//                buffer = buffer.append(System.getProperty("line.separator"));
//            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            //关闭所有
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /*
     * 删除文件
     * @param fileName 文件名称
     * @return
     */
    private static boolean dFile(String fileName){
        Boolean bool = false;
        filenameTemp = path+fileName+".txt";
        File file  = new File(filenameTemp);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bool;
    }
    
    //复制文件夹和文件夹内容
    public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        String[] filePath = file.list();
        
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + File.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath  + File.separator  + filePath[i], newPath  + File.separator + filePath[i]);
            }
            
            if (new File(oldPath  + File.separator + filePath[i]).isFile()) {
                copyFile(oldPath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }
            
        }
    }
    //复制文件
    @SuppressWarnings("resource")
	public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];
        
        while((in.read(buffer)) != -1){
            out.write(buffer);
        }
    }
    //创建文件夹
    public static boolean mkDirectory(String path) {  
        File file = null;  
        try {  
            file = new File(path);  
            if (!file.exists()) {  
                return file.mkdirs();  
            }  
            else{  
                return false;  
            }  
        } catch (Exception e) {  
        } finally {  
            file = null;  
        }  
        return false;  
    }  

    //调用接口
    public boolean create(String fileNma, String filecontent, String type) throws IOException {
        return cFile(fileNma,filecontent,type);
    }

    public boolean writeFileContent(String filePath, String newstr) throws IOException {
        return wFileContent(filePath,newstr);
    }
    public boolean delFile(String fileName){
        return dFile(fileName);
    }
}
