package com.service.custom.impl;

import com.dao.PostDao;
import com.dao.UserDao;
import com.dto.CategoryDTO;
import com.dto.PostDTO;
import com.dto.UserDTO;
import com.exceptions.CategoryNotFoundException;
import com.exceptions.UserNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Category;
import com.model.Post;
import com.model.User;
import com.service.custom.CategoryService;
import com.service.custom.PostService;
import com.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    protected Post convertToModel(PostDTO postDTO)throws Exception{
        Post post = objectMapper.convertValue(postDTO, Post.class);
        return post;
    }

    protected PostDTO convertToDTO(Post post)throws Exception{
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCategory_id(post.getCategory().getCategory_id());
        postDTO.setCreatedDate(post.getCreatedDate());
        postDTO.setDescription(post.getDescription());
        postDTO.setFront_image(post.getFront_image());
        postDTO.setMedia(post.getMedia());
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }
    protected List<PostDTO> convertToDTOList(List<Post> posts)throws Exception{
        List<PostDTO> dtoList =objectMapper.convertValue(posts,new TypeReference<List<PostDTO>>(){});
        return dtoList;
    }


    @Override
    public List<PostDTO> getPaginatedList(Integer offset, Integer limite) throws Exception {
        List<Post> paginatedList = postDao.getPaginatedList(offset, limite);
        List<PostDTO> dtoList = convertToDTOList(paginatedList);
        return dtoList;
    }


    protected Category convertToModel(CategoryDTO categoryDTO)throws Exception{
        Category category = objectMapper.convertValue(categoryDTO, Category.class);
        return category;
    }

    protected User convertToModel(UserDTO userDTO)throws Exception{
        User user = objectMapper.convertValue(userDTO, User.class);
        return user;
    }

    @Override
    public PostDTO savePost(PostDTO postDTO, Integer user_id) throws Exception{
        //search user from db
        UserDTO userDTO = userService.findById(user_id);
        //search category from db
        CategoryDTO categoryDTO = categoryService.findById(postDTO.getCategory_id());
        //check user exist in db
        if (userDTO == null){
            throw new UserNotFoundException("This user can not found in system");
        }else if(categoryDTO == null){//check category exist in db
            throw new CategoryNotFoundException("This Category coudn't found");
        }else {
            //convert userDto and categoryDto to model
            Category category = convertToModel(categoryDTO);
            User user = convertToModel(userDTO);
            //create a post
            Post post = new Post();
            post.setCategory(category);//add post category
            post.setCreatedDate(postDTO.getCreatedDate());
            post.setUser(user);//add user
            post.setDescription(postDTO.getDescription());
            post.setFront_image(postDTO.getFront_image());
            post.setMedia(postDTO.getMedia());
            post.setTitle(postDTO.getTitle());
            //save in db
            Post savedPost = postDao.save(post);
            return convertToDTO(savedPost);
        }
    }

    @Override
    public PostDTO add(PostDTO postDTO) throws Exception {
        Post post = convertToModel(postDTO);
        Post save = postDao.save(post);
        PostDTO savedDTO = convertToDTO(save);
        return savedDTO;
    }

    @Override
    public boolean update(PostDTO postDTO ) throws Exception {
        Post post = convertToModel(postDTO);
        Post save = postDao.save(post);
        if (save != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(PostDTO postDTO) throws Exception {
        return false;
    }

    @Override
    public PostDTO findById(Integer id) throws Exception {
        Optional<Post> byId = postDao.findById(id);
        boolean present = byId.isPresent();
        if (present){
            return convertToDTO(byId.get());
        }
        return null;
    }

    @Override
    public List<PostDTO> getAll() throws Exception {
        List<Post> list = postDao.findAll();
        List<PostDTO> all = new ArrayList<>();


        for(Post post:list){
            PostDTO dto=convertToDTO(post);
            all.add(dto);
        }

        return all;
    }
}
