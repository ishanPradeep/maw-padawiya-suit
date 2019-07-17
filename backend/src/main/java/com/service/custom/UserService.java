package com.service.custom;

import com.dto.UserDTO;
import com.service.SuperService;
import java.util.List;

public interface UserService extends SuperService<UserDTO> {
    public List<UserDTO> getPaginatedList(Integer offset, Integer limite)throws Exception;
}
