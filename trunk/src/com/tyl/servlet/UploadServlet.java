package com.tyl.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.tyl.image.SearchImages;
import com.tyl.image.SimpleImageSearchHits;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletConfig config = this.getServletConfig();
		SmartUpload mySmartUpload = new SmartUpload();//上传图片的工具类
        mySmartUpload.initialize(config, request, response);// 初始化
        try {
            mySmartUpload.upload();// 上传
            com.jspsmart.upload.File f1 = mySmartUpload.getFiles().getFile(0);//因为只一次只上传一个图片，所以就getFile(0)，如果多次还要迭代遍历

            String imageName = f1.getFileName();//得到图片的名字
            int idx = imageName.lastIndexOf(".");
            String imageType = imageName.substring(idx, imageName.length());// 得到图片的类型，比如是 .jpg
            Calendar cal=Calendar.getInstance(); //处理时间的一个类

            String newImageName = String.valueOf(System.currentTimeMillis());//图片的新名字，最好不要用随机数，因为随机数也可能有一样的
           
            String path = "D:\\pache-tomcat-8.0.14\\webapps\\lucenceWeb\\tmp";
            File file = new File(path);
            if(!file.exists()){//如果不存在这个路径
                file.mkdirs();//就创建
            }
            String imagePath = path+File.separator+newImageName+imageType;//已经保存的图片的绝对路径，下面要对图片重新命名
            //生成保存图片的路径，File.separator是个跨平台的分隔符
            System.out.println(imagePath);
            f1.saveAs(imagePath);// 保存图片到这个目录下
           
            BufferedImage bimg;
			try {
				file = new File(imagePath);
				bimg = ImageIO.read(file);
				String index = "D:\\apache-tomcat-8.0.14\\webapps\\lucenceWeb\\imageIndex";
				System.out.println(index + "---------");
				IndexReader reader = IndexReader.open(FSDirectory.open(new File(index)));
				SimpleImageSearchHits sish = new SearchImages().search(bimg, reader);
				System.out.println(sish.length() + " sish.length() ");
				for(int i = 0; i < sish.length(); i++){
					Document d = sish.doc(i);
					System.out.println(sish.score(i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
        } catch (SmartUploadException e) {
            e.printStackTrace();
        }
	}

}
