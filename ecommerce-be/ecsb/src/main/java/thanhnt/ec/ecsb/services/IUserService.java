package thanhnt.ec.ecsb.services;

import thanhnt.ec.ecsb.dto.UserDTO;
import thanhnt.ec.ecsb.exceptions.DataNotFoundException;

public interface IUserService {
    void createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
