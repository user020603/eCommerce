package thanhnt.ec.ecsb.services;

import thanhnt.ec.ecsb.dto.CategoryDTO;
import thanhnt.ec.ecsb.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void updateCategory(Long categoryId, CategoryDTO category);
    void deleteCategory(Long id);
}
