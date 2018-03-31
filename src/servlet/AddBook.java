package servlet;

import bean.Book;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "AddBook")
public class AddBook extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取磁盘文件工厂类
        DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
        //创建核心上传类
        FileUpload fileUpload=new FileUpload(diskFileItemFactory);
        //处理上传的中文乱码
        fileUpload.setHeaderEncoding("utf-8");
        //创建map集合
        Map<String,Object> map=new HashMap<String,Object>();
        String filename=null;
        //解析request
        try {
            List<FileItem> list=fileUpload.parseRequest(request);
            for (FileItem fileItem:list){
                //判断是否是普通输入项
                if (fileItem.isFormField()){
                   String name = fileItem.getFieldName();
                   //获取普通输入项的值，处理中文乱码
                   String value=fileItem.getString("utf-8");
                   map.put(name,value);
//                   System.out.println(map);只有bname放入map中了
                }
                else {
                    //获取文件上穿项
                    //获取文件名
                    filename=fileItem.getName();
                    //获取文件名最后的/的索引值
                    int len=filename.lastIndexOf("//");
                    if (len!=-1){
                        //截取字符串
                    filename =filename.substring(len+1);
                    }
                    //获取上传到的地址的真实路径
                    String path = getServletContext().getRealPath("book_img");
                    //获取输入流
                    InputStream in=fileItem.getInputStream();
                    //输出流
                    OutputStream out=new FileOutputStream(path+"/"+filename);
                    //流对接
                    byte[] bytes=new byte[1024];
                    int lens=0;
                    while (( lens=in.read(bytes))!=-1){
                        out.write(bytes,0,lens);
                    }
                    //关闭流
                    in.close();
                    out.close();
                }

            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //用BeanUtils进行封装
        Book book=new Book();
        try {
            BeanUtils.populate(book,map);
            //手动设置图片和bid
            book.setBid(UUID.randomUUID().toString());
            book.setImage("book_img"+"/"+filename);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用方法进行数据库的封装
        BookService bookService=new BookService();
        bookService.addBook(book);
        request.getRequestDispatcher("/book?method=findAllBook").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   doPost(request,response);
    }
}
