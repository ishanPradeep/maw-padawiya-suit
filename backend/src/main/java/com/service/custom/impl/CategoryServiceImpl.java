package com.service.custom.impl;

import com.dao.CategoryDao;
import com.dao.PostDao;
import com.dto.CategoryDTO;
import com.dto.PostDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Category;
import com.model.Post;
import com.service.custom.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ObjectMapper objectMapper;

    protected Category convertToModel(CategoryDTO categoryDTO)throws Exception{
        Category category = objectMapper.convertValue(categoryDTO, Category.class);
        return category;
    }
    protected CategoryDTO convertToDTO(Category category)throws Exception{
        CategoryDTO categoryDTO = objectMapper.convertValue(category, CategoryDTO.class);
        return categoryDTO;
    }
    protected List<CategoryDTO> convertToDTOList(List<Category> categories)throws Exception{
        List<CategoryDTO> dtoList =objectMapper.convertValue(categories,new TypeReference<List<CategoryDTO>>(){});
        return dtoList;
    }

    @Override
    public List<CategoryDTO> getPaginatedList(Integer offset, Integer limite) throws Exception {
        List<Category> paginatedList = categoryDao.getPaginatedList(offset, limite);
        List<CategoryDTO> dtoList = convertToDTOList(paginatedList);
        return dtoList;
    }

    @Override
    public CategoryDTO findByName(String category_name) throws Exception {
        return convertToDTO(categoryDao.findByName(category_name));
    }

    @Override
    public CategoryDTO findById(Integer category_id) throws Exception {
        Optional<Category> byId = categoryDao.findById(category_id);
        return convertToDTO(byId.get());
    }

    @Override
    public CategoryDTO add(CategoryDTO categoryDTO) throws Exception {
        Category category = convertToModel(categoryDTO);
        Category save = categoryDao.save(category);
        CategoryDTO savedDTO = convertToDTO(save);
        return savedDTO;
    }

    @Override
    public boolean update(CategoryDTO categoryDTO) throws Exception {
        Category category = convertToModel(categoryDTO);
        Category save = categoryDao.save(category);

        if (save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(CategoryDTO categoryDTO) throws Exception {
        Category category = convertToModel(categoryDTO);
        categoryDao.delete(category);
        return true;
    }

    @Override
    public List<CategoryDTO> getAll() throws Exception {
        return null;
    }
}
