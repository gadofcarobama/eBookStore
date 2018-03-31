package service;

import bean.Book;
import bean.Category;
import dao.CategoryDao;
import dao.CategoryDaoImpl;

import java.util.List;

public class CategoryService {
    public List<Category> findCate(){
        CategoryDao categoryDao=new CategoryDaoImpl();
        List<Category> list=categoryDao.findCate();
        return list;
    }

    public Category findCateByCid(String cid) {
        CategoryDao categoryDao=new CategoryDaoImpl();
        Category category = categoryDao.findCateByCid(cid);
        return category;
    }

    public void updateCategory(Category category) {
         CategoryDao categoryDao=new CategoryDaoImpl();
         categoryDao.updateCategory(category);

    }

    public void updateBookByCid(List<Book> bookList) {
        //遍历出book，并进行修改
        for (Book book:bookList){
            CategoryDao categoryDao=new CategoryDaoImpl();
            categoryDao.updateBookCid(book);
        }
    }

    public void deleteCategory(String cid) {
        CategoryDao categoryDao=new CategoryDaoImpl();
        categoryDao.deleteCate(cid);
    }

    public void addCategory(Category category) {
        CategoryDao categoryDao=new CategoryDaoImpl();
        categoryDao.addCategory(category);
    }
}
