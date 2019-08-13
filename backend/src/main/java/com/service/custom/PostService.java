package com.service.custom;

import com.dto.PostDTO;
import com.service.SuperService;

import java.util.List;

public interface PostService extends SuperService<PostDTO> {
    public List<PostDTO> getPaginatedList(Integer offset, Integer limite)throws Exception;
    public PostDTO savePost(PostDTO  postDTO,Integer user_id)throws Exception;
}