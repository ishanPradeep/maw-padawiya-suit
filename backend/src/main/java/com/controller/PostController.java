package com.controller;

import com.dto.CategoryDTO;
import com.dto.PostDTO;
import com.dto.UserDTO;
import com.exceptions.CategoryNotFoundException;
import com.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Category;
import com.service.custom.CategoryService;
import com.service.custom.PostService;
import com.util.JsonService;
import com.util.ResponseMassage;
import com.util.ResponseWrapper;
import com.util.StringConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    //create post
    @PostMapping(value = "/user/{user_id}/posts",consumes = "application/json")
    public ResponseEntity<JsonNode> addPost(@PathVariable("user_id") Integer user_id, @RequestBody String payload){
        JsonNode jsonNode;
        PostDTO postDTO;

        try{
            //convert payload string to json
            jsonNode = objectMapper.readTree(payload);
            //convert json to POJO
            postDTO = objectMapper.treeToValue(jsonNode, PostDTO.class);

            //check title if not bad request
            if (StringConstance.isBlank(postDTO.getTitle())){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.POST_MISSING_DATA,null)
                        ));
            }

        }catch (IOException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.INVALID_DATA, null)
                    ));
        }

        //Everything's fine then save post POJO
        try {
            PostDTO add = postService.savePost(postDTO,user_id);
            if (add == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.POST_NOT_ADDED,null)
                        ));
            }else{
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,add)
                        ));
            }

        }catch (UserNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(e.getMessage(),null)
                    ));
        }catch (CategoryNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(e.getMessage(),null)
                    ));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(e.getMessage(),null)
                    ));
        }

    }


    @PutMapping(value = "/post/{id}",consumes = "application/json")
    public ResponseEntity<JsonNode> updatePost(@PathVariable Integer id,@RequestBody String payload){
//        try {
//            PostDTO byId = postService.findById(id);
//            //check resource exits
//            if (byId!=null){
//                try {
//                    JsonNode jsonNode = JsonService.toJsonNode(payload);
//                    System.out.println(objectMapper.treeToValue(jsonNode, PostDTO.class));
//                    PostDTO postDTO = objectMapper.treeToValue(jsonNode, PostDTO.class);
//                    //check properties not null
//                    postDTO.setId(id);
//                    if (postDTO!= null) {
//                        postService.update(postDTO);
//                        return ResponseEntity
//                                .status(HttpStatus.OK)
//                                .body(JsonService.toJsonNode(
//                                        new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,postDTO)
//                                ));
//                    }
//                    return ResponseEntity
//                            .status(HttpStatus.BAD_REQUEST)
//                            .body(JsonService.toJsonNode(
//                                    new ResponseWrapper<>(ResponseMassage.POST_MISSING_DATA,null)
//                            ));
//
//                }catch (Exception e){
//                    return ResponseEntity
//                            .status(HttpStatus.BAD_REQUEST)
//                            .body(JsonService.toJsonNode(
//                                    new ResponseWrapper<>(e.getMessage(),null)
//                            ));
//                }
//
//            }else {
//                return ResponseEntity
//                        .status(HttpStatus.NOT_FOUND)
//                        .body(JsonService.toJsonNode(
//                                new ResponseWrapper<>(ResponseMassage.POST_NOT_FOUND,null)
//                        ));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(JsonService.toJsonNode(
//                            new ResponseWrapper<>(e.getMessage(),null)
//                    ));
//        }

        return null;
    }

    //get all posts
    @GetMapping(value = "/posts")
    public ResponseEntity<JsonNode> getPagenationList(@RequestParam String limit, String offset){
        try {
            int pageLimit = Integer.parseInt(limit);
            int pageOffSet = Integer.parseInt(offset);
//            List<PostDTO> paginatedList = postService.getPaginatedList(pageOffSet, pageLimit);
            List<PostDTO> paginatedList = postService.getAll();
            if (paginatedList!=null){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,paginatedList)));

            }else{
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.POST_NOT_FOUND,null )
                        ));
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.ERROR_WHILE_PROCESSING,e)
                    ));
        }
    }

    //Post for retrieving a single resources.
    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<JsonNode> getUser(@PathVariable Integer id){
        try {
            //Find Note via ID
            PostDTO postDTO = postService.findById(id);
            CategoryDTO categoryDTO = categoryService.findById(postDTO.getCategory_id());

            //If Note not found
            if (postDTO == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.POST_NOT_ADDED,null)));
            }else if (categoryDTO == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_NOT_ADDED,null)));
            }
            else {//If note Found
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL, postDTO)
                        ));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.ERROR_WHILE_PROCESSING,null)
                    ));
        }
    }




}
