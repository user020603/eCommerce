package thanhnt.ec.ecsb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thanhnt.ec.ecsb.model.Role;
import thanhnt.ec.ecsb.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
