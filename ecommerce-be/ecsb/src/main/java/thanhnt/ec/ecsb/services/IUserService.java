package thanhnt.ec.ecsb.services;

import thanhnt.ec.ecsb.dto.UserDTO;
import thanhnt.ec.ecsb.exceptions.DataNotFoundException;
import thanhnt.ec.ecsb.model.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
}
