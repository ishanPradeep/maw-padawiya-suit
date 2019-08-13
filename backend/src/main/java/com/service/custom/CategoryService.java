package com.service.custom;

import com.dto.CategoryDTO;
import com.dto.PostDTO;
import com.service.SuperService;

import java.util.List;

public interface CategoryService extends SuperService<CategoryDTO> {
    public List<CategoryDTO> getPaginatedList(Integer offset, Integer limite)throws Exception;
    public CategoryDTO findByName(String category_name)throws Exception;
    public CategoryDTO findById(Integer category_id)throws Exception;

}
