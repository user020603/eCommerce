package thanhnt.ec.ecsb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thanhnt.ec.ecsb.model.Role;
import thanhnt.ec.ecsb.response.ResponseObject;
import thanhnt.ec.ecsb.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get list of roles successfully")
                        .status(HttpStatus.OK)
                        .data(roles)
                        .build()
        );
    }
}
