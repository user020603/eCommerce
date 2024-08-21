package thanhnt.ec.ecsb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thanhnt.ec.ecsb.dto.CategoryDTO;
import thanhnt.ec.ecsb.model.Category;
import thanhnt.ec.ecsb.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements iCategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category.builder().name(categoryDTO.getName()).build();
        categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        // solid remove
        categoryRepository.deleteById(id);
    }
}
