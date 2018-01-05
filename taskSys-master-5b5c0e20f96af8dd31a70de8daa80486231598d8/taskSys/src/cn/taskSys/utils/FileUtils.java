/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.taskSys.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 文件操作工具类 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 * 
 * @author ThinkGem
 * @version 2013-06-21
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	private static Logger logger = Logger.getLogger(FileUtils.class);
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	/**
	 * 创建目录
	 * 
	 * @param descDirName
	 *            目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 压缩文件(.zip)的函数
	 * 
	 * @param zipDirectory
	 *            :(需要)压缩的文件夹路径
	 * @param zipPath
	 *            :文件压缩后放置的路径,该路径可以为null,null表示压缩到原文件的同级目录
	 * @return :返回一个压缩好的文件(File),否则返回null
	 */
	public File doZip(String zipDirectory, String zipoutPath, String cjcode,
			String containdir) {
		String[] cjCode = cjcode.split(",");
		String[] condir = containdir.split(",");
		File zipDir = new File(zipDirectory);
		File zipoutDir = new File(zipoutPath);
		ZipOutputStream zipOut = null;
		if (zipoutPath == null) {
			zipoutPath = zipDir.getParent();
		}
		if (!zipoutDir.exists()) {
			zipoutDir.mkdirs();
		}
		// 压缩后生成的zip文件名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String zipFileName = zipoutPath + "/" + "委托扣款授权书_"
				+ sdf.format(new Date()) + ".zip";

		try {
			zipOut = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFileName)));
			// 压缩文件
			for (String inputdir : cjCode) {
				if (cjCode.length <= 0) {
					if (new File(zipDirectory + inputdir + "/").exists()) {
						handleDir(new File(zipDirectory + inputdir + "/"),
								new File(zipDirectory + inputdir + "/")
										.getParent().length() + 1, zipOut);
					}
				} else {
					for (String dir : condir) {
						if (new File(zipDirectory + inputdir + "/" + dir + "/")
								.exists()) {
							handleDir(new File(zipDirectory + inputdir + "/"
									+ dir + "/"), new File(zipDirectory
									+ inputdir + "/").getParent().length() + 1,
									zipOut);
						}
					}
				}
			}
			zipOut.close();
			return new File(zipFileName);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 由doZip调用,递归完成目录文件读取
	 * 
	 * @param dir
	 *            :(需要)压缩的文件夹(File 类型)
	 * @param len
	 *            :一个参数(记录压缩文件夹的parent路径的长度)
	 * @param zipOut
	 *            :需要压缩进的压缩文件
	 * @throws IOException
	 *             :如果出错,会抛出IOE异常
	 */
	private void handleDir(File dir, int len, ZipOutputStream zipOut)
			throws IOException {
		FileInputStream fileIn = null;
		File[] files = dir.listFiles();

		if (files != null) {
			if (files.length > 0) { // 如果目录不为空,则分别处理目录和文件.
				for (File fileName : files) {

					if (fileName.isDirectory()) {
						handleDir(fileName, len, zipOut);
					} else {
						fileIn = new FileInputStream(fileName);
						zipOut.putNextEntry(new ZipEntry(fileName.getPath()
								.substring(len).replaceAll("\\\\", "/")));
						int b;
						while ((b = fileIn.read()) != -1)
							zipOut.write(b);
						zipOut.closeEntry();
					}
				}
			} else { // 如果目录为空,则单独创建之.
				zipOut.putNextEntry(new ZipEntry(dir.getPath().substring(len)
						+ "/"));
				zipOut.closeEntry();
			}
		} else {// 如果是一个单独的文件
			fileIn = new FileInputStream(dir);
			zipOut.putNextEntry(new ZipEntry(dir.getPath().substring(len)));

			int b;
			while ((b = fileIn.read()) != -1) {
				zipOut.write(b);
				zipOut.closeEntry();
			}
		}
		if (null != fileIn) {
			fileIn.close();
		}
	}

	public void downFile(File filedown, HttpServletResponse response)
			throws IOException {

		response.setContentType(CONTENT_TYPE);
		response.addHeader("Content-Disposition",
				"attachment;filename="
						+ new String(filedown.getName().getBytes("gb2312"),
								"ISO8859-1"));
		response.setContentLength((int) filedown.length());
		FileInputStream fis = new FileInputStream(filedown);
		BufferedInputStream buff = new BufferedInputStream(fis);
		byte[] b = new byte[1024];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < filedown.length()) {
			int j = buff.read(b, 0, 1024);
			k += j;
			myout.write(b, 0, j);
		}
		fis.close();
		myout.flush();
		myout.close();
	}

	public boolean deleteFile(File file) {
		boolean flag = false;
		if (file.isFile() && file.exists()) {
			file.getAbsoluteFile().delete();
			flag = true;
		}
		return flag;
	}

	public static void main(String[] temp) {
		/*
		 * String inputFileName = "D:/opt/chpimage/caifu/"; String zipFileName =
		 * "D:/opt/chpimage/caifu/zip"; String
		 * ss="81A200000000001524-00005,82A200000000001524-00002"; String
		 * dd="hkxy"; FileUtils book = new FileUtils(); try {
		 * book.doZip(inputFileName, zipFileName,ss,dd); } catch (Exception ex)
		 * { ex.printStackTrace(); }
		 */

		ScriptEngine jse = new ScriptEngineManager()
				.getEngineByName("JavaScript");
		String s1 = "kkk*123/4";
		s1 = s1.replace("kkk", "11");

		try {
			System.out.println("a+" + jse.eval(s1));
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static InputStream getTemplateInputStream(Class lass, String fileName) {
		try {
			String filepath = getRootPath1(lass);
			if (filepath.toLowerCase().endsWith(".jar")) {
				return getJarInputStream(lass, fileName);
			} else {
				String filePathAndName = filepath + File.separator + fileName;
				FileInputStream fs = new FileInputStream(filePathAndName);
				return fs;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static String getRootPath(Class lass) throws Exception {
		URL url = null;
		// url = lass.getResource("/");
		// url = lass.getClassLoader().getResource("/");
		url = lass.getProtectionDomain().getCodeSource().getLocation();
		return url.getPath();
	}

	public static InputStream getJarInputStream(Class lass, String fileName) throws Exception {
		JarFile jarFile = null;
		String jarName = getRootPath1(lass);// Jar包所在的位置
		// String fileName = "META-INF/MANIFEST.MF";// 文件在jar包里的路径
		String _fileName = fileName;
		_fileName = _fileName.replaceAll("\\\\", "/");
		jarFile = new JarFile(jarName);
		// 读入jar文件
		JarEntry entry = jarFile.getJarEntry(_fileName);
		InputStream input = null;
		input = jarFile.getInputStream(entry);// 读入需要的文件
		return input;
	}
	
	
	public static String getRootPath1(Class lass) throws Exception  {
		String classPath = lass.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		//windows下
		if("\\".equals(File.separator)){
			rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		//linux下
		if("/".equals(File.separator)){
			rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
}



