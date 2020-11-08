/**
 * 项目名称：Leshi_Android_Client_1.0 
 * 所属包名: com.leshi.utils
 * 文件名		：FileUtils.java
 * 创建日期	：2014年6月13日
 * Copyright (c)  西安西安乐食
 * All rights reserved.
 */
package com.lianjiao.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

/**
 * 类名称：FileUtils   
 * 类描述：   文件操作工具类
 * @创建者：zhangyao     
 * @创建时间：2014年6月13日
 * @变更记录：2014年6月13日下午4:08:43 by 记录变更人
 */
public class FileUtils {

public static String SDPATH = Environment.getExternalStorageDirectory() + File.separator;
	
	/**
	 * 
	 * 方法名：checkFilePath
	 * 描述： 转化为SD卡的绝对路径
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2014年6月13日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月13日下午5:01:23 by
	 */
	public static String checkFilePath(String fileName) {
		
		String fileRealPath = "";
		if(fileName.startsWith(SDPATH)){
			fileRealPath = fileName;
		}else{
			fileRealPath = SDPATH + fileName;
		}
		return fileRealPath;
	}	
	
	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public static File creatSDFile(String fileName) throws IOException{
		String fileRealPath  = checkFilePath(fileName);
		File file = new File(fileRealPath);
		file.createNewFile();
		return file;
	}
	

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public static File creatSDDir(String fileName){
		
		String fileRealPath  = checkFilePath(fileName);
		String state = android.os.Environment.getExternalStorageState();
		if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) { 
			try {
				throw new IOException("SD Card is not mounted,It is  " + state + ".");
			} catch (IOException e) {
				e.printStackTrace();				
			} 
		}
		
		File directory = new File(fileRealPath);//.getParentFile();
		if (!directory.exists() && !directory.mkdirs()) { 
			try {
				throw new IOException("Path to file could not be created");
				
			} catch (IOException e) {
				e.printStackTrace();			
			}
		}
		return directory;
	}
	
	/**
	 * 判断设备上的文件是否存在	
	 * @param 
	 */
	public static boolean isFileExist(String fileName){
		File file = new File(fileName);
		return file.exists();
	}
	
	/**
	 * 删除SD卡上的文件,或者文件夹
	 * @param boolean
	 */
	public static boolean  deleteFile(File file) {
		boolean isSuccess = true;
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
				} else if (file.isDirectory()) { // 否则如果它是一个目录
			File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
			for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
			} else {
				isSuccess = false;
			}
			return isSuccess;
		}
	
	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public static File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try {
			String ls[] = new String[10];//文件夹最多嵌套十层
			String filePath = "";
			ls = path.split("/");
			for(int i= 0;i<ls.length;i++){
				filePath = filePath + ls[i];
				filePath = filePath+"/";
				
				creatSDDir(filePath);
			}
			
			File f = creatSDFile(path + fileName);
			output = new FileOutputStream(f);
			byte buffer [] = new byte[4 * 1024];
			while(input.read(buffer) != -1){
				output.write(buffer);
			}
			output.flush();
			file = f;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				output.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return file;
	}	

	
	/**
	* 读取设备中文本文件
	* @param fileName
	* @return
	*/
	public static String readFile(String fileName) {
		
		if(isFileExist(fileName)) {
			StringBuffer sb = new StringBuffer();
			File file = new File(fileName);
				try {
					FileInputStream fis = new FileInputStream(file);
					int c;
					while ((c = fis.read()) != -1) {
						sb.append((char) c);
					}
					fis.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			return sb.toString();
		}
		
		return "";
	}
	

	/**
	 * 向手机写图片
	 * @param buffer   
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile( byte[] buffer, String folder, String fileName )
	{
		boolean writeSucc = false;
		
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		
		String folderPath = "";
		if( sdCardExist )
		{
			folderPath = Environment.getExternalStorageDirectory() + File.separator +  folder + File.separator;
		}
		else
		{
			writeSucc =false;
		}
		
		File fileDir = new File(folderPath);
		if(!fileDir.exists()) 
		{
			fileDir.mkdirs();
		}
		  
		File file = new File( folderPath + fileName );
		FileOutputStream out = null;
		try 
		{
			out = new FileOutputStream( file );
			out.write(buffer);
			writeSucc = true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try {out.close();} catch (IOException e) {e.printStackTrace();}
		}
		
		return writeSucc;
	}
	
	/**
	 * 根据文件绝对路径获取文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileName( String filePath )
	{
		if( TextUtils.isEmpty(filePath) )
			return "";
		return filePath.substring( filePath.lastIndexOf( File.separator )+1 );
	}
	
	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat( String filePath){
		if(TextUtils.isEmpty(filePath)){
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator)+1,point);
	}
	
	/**
	 * 获取文件扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat( String fileName )
	{
		if( TextUtils.isEmpty(fileName) )	return "";
		
		int point = fileName.lastIndexOf( '.' );
		return fileName.substring( point+1 );
	}
	
	/**
	 * 获取文件大小
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath )
	{
		long size = 0;
		
		File file = new File( filePath );
		if(file!=null && file.exists())
		{
			size = file.length();
		} 
		return size;
	}
	
	/**
	 * 获取文件大小
	 * @param size 字节
	 * @return
	 */
	public static String getFileSize(long size) 
	{
		if (size <= 0)	return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float)size / 1024;
		if (temp >= 1024) 
		{
			return df.format(temp / 1024) + "M";
		}
		else 
		{
			return df.format(temp) + "K";
		}
	}

	/**
	 * 转换文件大小
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

	/**
	 * 获取目录文件大小
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
	    if (!dir.isDirectory()) {
	    	return 0;
	    }
	    long dirSize = 0;
	    File[] files = dir.listFiles();
	    for (File file : files) {
	    	if (file.isFile()) {
	    		dirSize += file.length();
	    	} else if (file.isDirectory()) {
	    		dirSize += file.length();
	    		dirSize += getDirSize(file); //递归调用继续统计
	    	}
	    }
	    return dirSize;
	}
	
	/**
	 * 获取目录文件个数
	 * @param f
	 * @return
	 */
	public long getFileList(File dir){
        long count = 0;
        File[] files = dir.listFiles();
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
            	count = count + getFileList(file);//递归
            	count--;
            }
        }
        return count;  
    }
	
	public static byte[] toBytes(InputStream in) throws IOException 
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    int ch;
	    while ((ch = in.read()) != -1)
	    {
	    	out.write(ch);
	    }
	    byte buffer[]=out.toByteArray();
	    out.close();
	    return buffer;
	}	

	
	/**
	 * 计算SD卡的剩余空间
	 * @return 返回-1，说明没有安装sd卡
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}


	/**
	 * 检查是否安装SD卡
	 * @return
	 */
	public static boolean isSDCardExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = false;
		} else
			status = true;
		return status;
	}

	/**
	 * 删除目录(包括：目录里的所有文件)
	 * @param fileName
	 * @return
	 */
	public static boolean deleteDirectory(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) { 

			File path = Environment.getExternalStorageDirectory();
			String fileRealPath = "";
			if(fileName.startsWith(path.toString())){
				fileRealPath = fileName;
			}else{
				fileRealPath = path.toString() + fileName;
			}
			File newPath = new File(fileRealPath);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then
				// delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					//Log.i("DirectoryManager deleteDirectory", fileName);
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * 删除文件
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			String fileRealPath = "";
			if(fileName.startsWith(path.toString())){
				fileRealPath = fileName;
			}else{
				fileRealPath = path.toString() + fileName;
			}
			File newPath = new File(fileRealPath);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}
	
	
	public static String saveImageFile(Bitmap photo,File imagefile){	
		
		String headimage_path = imagefile.getAbsolutePath();
			
		if (imagefile.exists()) {
			imagefile.delete();
		}
		
		try {
			FileOutputStream out = new FileOutputStream(imagefile);
			photo.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return headimage_path;
	}
}
