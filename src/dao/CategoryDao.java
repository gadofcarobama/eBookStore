package dao;

import bean.Book;
import bean.Category;

import java.util.List;

public interface CategoryDao {
    public List<Category> findCate();

    Category findCateByCid(String cid);

    void updateCategory(Category category);

    void updateBookCid(Book book);

    void deleteCate(String cid);

    void addCategory(Category category);
}
