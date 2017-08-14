package org.ifly.edu.im4java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

/**
 * gm 工具
 * @author pangzhiwang
 * @date 2016-8-22
 */
public class GMUtils {
	/**
	 * 
	 * * 获得图片文件大小[小技巧来获得图片大小] 
	 * @param filePath * 文件路径 *
	 * @return 文件大小
	 */
	public int getSize(String imagePath) {
		int size = 0;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(imagePath);
			size = inputStream.available();
			inputStream.close();
			inputStream = null;

		} catch (FileNotFoundException e) {
			size = 0;
			System.out.println("文件未找到!");
		} catch (IOException e) {
			size = 0;
			System.out.println("读取文件大小错误!");
		} finally {
			// 可能异常为关闭输入流,所以需要关闭输入流
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("关闭文件读入流异常");
				}
				inputStream = null;
			}
		}
		return size;
	}


	/**
	 * 
	 * 获得图片的宽度
	 * @param filePath          文件路径
	 * @return 图片宽度
	 */

	public int getWidth(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();
			op.format("%w"); // 设置获取宽度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			//identifyCmd.setSearchPath("C:/install/GraphicsMagick-1.3.24-Q8");
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println("运行指令出错!");
		}
		return line;
	}

	/**
	 * 
	 * 获得图片的高度
	 * @param imagePath 文件路径
	 * @return 图片高度
	 */
	public int getHeight(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();
			op.format("%h"); // 设置获取高度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println("运行指令出错!" + e.toString());
		}
		return line;
	}

	/**
	 * 
	 * 图片信息
	 * @param imagePath
	 * @return
	 */
	public static String getImageInfo(String imagePath) {
		String line = null;
		try {
			IMOperation op = new IMOperation();
			op.format("width:%w,height:%h,path:%d%f,size:%b%[EXIF:DateTimeOriginal]");
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = cmdOutput.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * 
	 * 裁剪图片
	 * @param imagePath 源图片路径
	 * @param newPath 处理后图片路径
	 * @param x  起始X坐标
	 * @param y  起始Y坐标
	 * @param width  裁剪宽度
	 * @param height  裁剪高度
	 * @return 返回true说明裁剪成功,否则失败
	 */
	public boolean cutImage(String imagePath, String newPath, int x, int y,int width, int height) {
		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			/** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
			op.crop(width, height, x, y);
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {
		}
		return flag;
	}

	/**
	 * 
	 * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
	 * @param imagePath 源图片路径
	 * @param newPath  处理后图片路径
	 * @param width 缩放后的图片宽度
	 * @param height  缩放后的图片高度
	 * @return 返回true说明缩放成功,否则失败
	 */
	public boolean zoomImage(String imagePath, String newPath, Integer width, Integer height) {
		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			if (width == null) {// 根据高度缩放图片
				op.resize(null, height);
			} else if (height == null) {// 根据宽度缩放图片
				op.resize(width);
			} else {
				op.resize(width, height);
			}
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {
		}
		return flag;

	}
	
	/**
	 * 
	 * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
	 * @param imagePath 源图片路径
	 * @param newPath  处理后图片路径
	 * @param width 缩放后的图片宽度
	 * @param height  缩放后的图片高度
	 * @return 返回true说明缩放成功,否则失败
	 */
	public boolean zoomImage(String imagePath, String newPath, Integer width, Integer height,String specialChar) {
		boolean flag = false;
		try {
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			if (width == null) {// 根据高度缩放图片
				op.resize(null, height);
			} else if (height == null) {// 根据宽度缩放图片
				op.resize(width);
			} else {
				if(StringUtils.isBlank(specialChar)){
					op.resize(width, height);
				}else{
					op.resize(width, height,specialChar);
				}
			}
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);

			convert.run(op);
			flag = true;
		} catch (IOException e) {
			System.out.println("文件读取错误!");
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		} catch (IM4JavaException e) {
			flag = false;
		} finally {
		}
		return flag;
		
	}

	/**
	 * 
	 * 图片旋转
	 * @param imagePath 源图片路径
	 * @param newPath 处理后图片路径
	 * @param degree  旋转角度
	 */

	public boolean rotate(String imagePath, String newPath, double degree) {
		boolean flag = false;
		try {
			// 1.将角度转换到0-360度之间
			degree = degree % 360;
			if (degree <= 0) {
				degree = 360 + degree;
			}
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			op.rotate(degree);
			op.addImage(newPath);
			ConvertCmd cmd = new ConvertCmd(true);
			cmd.run(op);
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("图片旋转失败!");
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		GMUtils imageUtil = new GMUtils();
//		System.out.println("原图片大小:" + imageUtil.getSize("d://bbb.jpg") + "Bit");
//		System.out.println("原图片宽度:" + imageUtil.getWidth("d://bbb.jpg"));
//		System.out.println("原图片高度:" + imageUtil.getHeight("d://bbb.jpg"));
//		System.out.println("原图信息:" + imageUtil.getImageInfo("d://bbb.jpg"));
//		
//		boolean flg = imageUtil.zoomImage("d://bbb.jpg","d://bbb-no-change.jpg",200,200,">");
//		
//		System.out.println(flg);
//		if (imageUtil.zoomImage("d://bbb.jpg", "d://test1.jpg", 500, null)) {
//			if (imageUtil.rotate("d://bbb.jpg", "d://test2.jpg", 15)) {
//				if (imageUtil.cutImage("d://test2.jpg", "d://test3.jpg", 32,105, 200, 200)) {
//					System.out.println("编辑成功");
//				} else {
//					System.out.println("编辑失败03");
//				}
//			} else {
//				System.out.println("编辑失败02");
//			}
//		} else {
//			System.out.println("编辑失败01");
//		}
		
		String file = "D:\\projects\\yhd\\yIMG\\doc\\30项目管理\\30.00预研\\ossImg\\ft_example.jpg";
		String dest = "D:\\projects\\yhd\\yIMG\\doc\\30项目管理\\30.00预研\\ossImg\\ft_example_crop_North_!_pg.jpg";
		
		resizeAndCrop(file,dest);
		watermark();
	}
	
	public static void resizeAndCrop(String file, String dest) throws Exception {
		IMOperation op = new IMOperation();
		//op.interlace("None");
		
		ConvertCmd convert = new ConvertCmd(true);
		//CompositeCmd convertCmd = new CompositeCmd(true);
		op.addImage();
		op.resize(600, 300,"^")
			.gravity("North")
			.crop(300, 200, 0, 0)
			.quality(75.0);
		op.addImage();
		try {
			convert.run(op, file, dest);
		} catch (Exception e) {
             e.printStackTrace();
		}
	}
	
	public static void resizeAndCrop2(String file, String dest) throws Exception {
		IMOperation op = new IMOperation();
		op.interlace("None");
		
		ConvertCmd convert = new ConvertCmd(true);
		//CompositeCmd convertCmd = new CompositeCmd(true);
		op.addImage();
		op.strip()
		.resize(600, 300,"!")
		.gravity("North")
		.crop(300, 200, 0, 0)
		.quality(75.0);
		op.addImage();
		try {
			convert.run(op, file, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void watermark() throws Exception {
		GMOperation op = new GMOperation();
        
        // op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8").draw("text 100,100 bb"); 
        try {
         op.encoding("GB2312");
         String ss=new String("测试");
            op.font("KaiTi").pointsize(20).fill("red").draw("text 50,"+50+" '" + ss+ "'").quality(90.0);
           
            op.addImage(); 
            op.addImage();
            //op.font("Arial").fill("red").draw("text 100,100 "+new String(ss.getBytes("iso8859-1")));
        } catch (Exception e1) {
            e1.printStackTrace();
        }  
           
        ConvertCmd cmd = new ConvertCmd(true); 
        String srcPath="D:\\pangzhw\\opensouce\\imagemagick\\images\\bbb.jpg";
        String destPath="D:\\pangzhw\\opensouce\\imagemagick\\images\\bbb_m.jpg";
        try{
         cmd.run(op, srcPath, destPath);
	    }catch (Exception e) {
			e.printStackTrace();
		}
}
	
}
