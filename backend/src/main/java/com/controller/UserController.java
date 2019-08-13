package com.controller;

import com.dto.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    //user creating
    @PostMapping(value = "/users",consumes = "application/json")
    public ResponseEntity<JsonNode> addUser(@RequestBody String payload){
        JsonNode jsonNode;
        UserDTO userDTO;

        try{
            //convert payload string to json
            jsonNode = objectMapper.readTree(payload);
            //convert json to POJO
            userDTO = objectMapper.treeToValue(jsonNode, UserDTO.class);

            //check email if not bad request
            if (StringConstance.isBlank(userDTO.getEmail())){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.USER_MISSING_DATA,null)
                        ));
            }

        }catch (IOException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JsonService.toJsonNode(
                            new ResponseWrapper<>(ResponseMassage.INVALID_DATA, null)
                    ));
        }

        //Everything's fine then save user POJO
        try {
            UserDTO add=userService.add(userDTO);
            if (add == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.USER_NOT_ADDED,null)
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

    //get all users
    @GetMapping(value = "/users")
    public ResponseEntity<JsonNode> getPagenationList(@RequestParam String limit, String offset){
        try {
            int pageLimit = Integer.parseInt(limit);
            int pageOffSet = Integer.parseInt(offset);
            List<UserDTO> paginatedList = userService.getPaginatedList(pageOffSet, pageLimit);
            if (paginatedList!=null){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,paginatedList)));

            }else{
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.USER_NOT_FOUND,null )
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

    //Use for retrieving a single resources.
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<JsonNode> getUser(@PathVariable Integer id){
        try {
            //Find Note via ID
            UserDTO userDTO = userService.findById(id);
            //If Note not found
            if (userDTO == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.USER_NOT_ADDED,null)));
            }else {//If note Found
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.SUCCESSFUL, userDTO)
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

    //Use for delete
    @DeleteMapping(value = "users/{id}")
    public ResponseEntity<JsonNode> deleteUser(@PathVariable Integer id){
        try{
            UserDTO searchedUser = userService.findById(id);
            if (searchedUser == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(JsonService.toJsonNode(
                                new ResponseWrapper<>(ResponseMassage.USER_NOT_FOUND,null)));
            }else {//If Found
                UserDTO userDTO = objectMapper.convertValue(searchedUser, UserDTO.class);
                userService.delete(userDTO);
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


    @PutMapping(value = "/users/{id}",consumes = "application/json")
    public ResponseEntity<JsonNode> updateUser(@PathVariable Integer id,@RequestBody String payload){
        try {
            UserDTO byId = userService.findById(id);
            //check resource exits
            if (byId!=null){
                try {
                    JsonNode jsonNode = JsonService.toJsonNode(payload);
                    UserDTO userDTO = objectMapper.treeToValue(jsonNode, UserDTO.class);
                    //check properties not null
                    userDTO.setUser_id(id);
                    if (userDTO!= null) {
                        userService.update(userDTO);
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(JsonService.toJsonNode(
                                        new ResponseWrapper<>(ResponseMassage.SUCCESSFUL,userDTO)
                                ));
                    }
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(JsonService.toJsonNode(
                                    new ResponseWrapper<>(ResponseMassage.USER_MISSING_DATA,null)
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
                                new ResponseWrapper<>(ResponseMassage.USER_NOT_FOUND,null)
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
