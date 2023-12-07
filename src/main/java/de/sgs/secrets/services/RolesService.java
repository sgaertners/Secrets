package de.sgs.secrets.services;

import de.sgs.secrets.entities.Role;
import de.sgs.secrets.entities.User;
import de.sgs.secrets.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RolesService {

    final RoleRepository roleRepository;

    public RolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    public Role getRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    public Role getRoleByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElse(null);
    }
}
