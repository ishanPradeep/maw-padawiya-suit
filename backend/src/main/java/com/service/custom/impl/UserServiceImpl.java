package com.service.custom.impl;

import com.dao.UserDao;
import com.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    ObjectMapper objectMapper;

    protected User convertToModel(UserDTO userDTO)throws Exception{
        User user = objectMapper.convertValue(userDTO, User.class);
        return user;
    }
    protected UserDTO convertToDTO(User user)throws Exception{
        UserDTO userDTO = objectMapper.convertValue(user, UserDTO.class);
        return userDTO;
    }
    protected List<UserDTO> convertToDTOList(List<User> users)throws Exception{
        List<UserDTO> dtoList =objectMapper.convertValue(users,new TypeReference<List<UserDTO>>(){});
        return dtoList;
    }

    @Override
    public List<UserDTO> getPaginatedList(Integer offset, Integer limite) throws Exception {
        List<User> paginatedList = userDao.getPaginatedList(offset, limite);
        List<UserDTO> dtoList = convertToDTOList(paginatedList);
        return dtoList;
    }

    @Override
    public UserDTO add(UserDTO userDTO) throws Exception {
        User user = convertToModel(userDTO);
        User save = userDao.save(user);
        UserDTO savedDTO = convertToDTO(save);
        return savedDTO;
    }

    @Override
    public boolean update(UserDTO userDTO ) throws Exception {
        User user = convertToModel(userDTO);
        User save = userDao.save(user);
        if (save!= null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(UserDTO userDTO) throws Exception {
        User user = convertToModel(userDTO);
        userDao.delete(user);
        return true;
    }

    @Override
    public UserDTO findById(Integer id) throws Exception {
        Optional<User> byId = userDao.findById(id);
        boolean present = byId.isPresent();
        if (present){
            UserDTO user = convertToDTO(byId.get());
            return user;
        }else {
            return null;
        }
    }

    @Override
    public List<UserDTO> getAll() throws Exception {
        return null;
    }
}
