package com.controller;

import com.dto.CategoryDTO;
import com.dto.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.custom.CategoryService;
import com.service.custom.UserService;
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
public class CategoryController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryService categoryService;

    //create category
    @PostMapping(value = "/categorys",consumes = "application/json")
    public ResponseEntity<JsonNode> addUser(@RequestBody String payload){
        JsonNode jsonNode;
        CategoryDTO categoryDTO;

        try{
            //convert payload string to json
            jsonNode = objectMapper.readTree(payload);
            //convert json to POJO
            categoryDTO = objectMapper.treeToValue(jsonNode, CategoryDTO.class);

            //check type if not bad request
            if (StringConstance.isBlank(categoryDTO.getName())){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_MISSING_DATA,null)
                        ));
            }

        }catch (IOException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.INVALID_DATA, null)
                    ));
        }

        //Everything's fine then save category POJO
        try {
            CategoryDTO add=categoryService.add(categoryDTO);
            if (add == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_NOT_ADDED,null)
                        ));
            }else{
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,add)
                        ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(e.getMessage(),null)
                    ));
        }

    }

    //get all category
    @GetMapping(value = "/categorys")
    public ResponseEntity<JsonNode> getPagenationList(@RequestParam String limit, String offset){
        try {
            int pageLimit = Integer.parseInt(limit);
            int pageOffSet = Integer.parseInt(offset);
            List<CategoryDTO> paginatedList = categoryService.getPaginatedList(pageOffSet, pageLimit);
            if (paginatedList!=null){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,paginatedList)));

            }else{
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_NOT_FOUND,null )
                        ));
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.ERROR_WHILE_PROCESSING,null)
                    ));
        }
    }

    //category for retrieving a single resources.
    @GetMapping(value = "/category/{id}")
    public ResponseEntity<JsonNode> getCategory(@PathVariable Integer id){
        try {
            //Find category ID
            CategoryDTO categoryDTO = categoryService.findById(id);
            //If category not found
            if (categoryDTO == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_NOT_ADDED,null)));
            }else {//If category Found
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL, categoryDTO)
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

    //category for delete
    @DeleteMapping(value = "category/{id}")
    public ResponseEntity<JsonNode> deleteCategory(@PathVariable Integer id){
        try{
            CategoryDTO search_category = categoryService.findById(id);
            if (search_category == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_NOT_FOUND,null)));
            }else {//If Found
                CategoryDTO categoryDTO = objectMapper.convertValue(search_category, CategoryDTO.class);
                categoryService.delete(categoryDTO);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL, null)));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.ERROR_WHILE_PROCESSING,null)));
        }
    }

    @PutMapping(value = "/category/{id}",consumes = "application/json")
    public ResponseEntity<JsonNode> updateCategory(@PathVariable Integer id,@RequestBody String payload){
        try {
            CategoryDTO byId = categoryService.findById(id);
            //check resource exits
            if (byId!=null){
                try {
                    JsonNode jsonNode = JsonService.toJsonNode(payload);
                    CategoryDTO categoryDTO = objectMapper.treeToValue(jsonNode, CategoryDTO.class);
                    //check properties not null
                    categoryDTO.setCategory_id(id);
                    if (categoryDTO!= null) {
                        categoryService.update(categoryDTO);
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(JsonService.toJsonNode(
                                        new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,categoryDTO)
                                ));
                    }
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(JsonService.toJsonNode(
                                    new ResponseWrapper<>(ResponseMassage.CATEGORY_MISSING_DATA,null)
                            ));

                }catch (Exception e){
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(JsonService.toJsonNode(
                                    new ResponseWrapper<>(e.getMessage(),null)
                            ));
                }

            }else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.CATEGORY_NOT_FOUND,null)
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(e.getMessage(),null)
                    ));
        }
    }
}
