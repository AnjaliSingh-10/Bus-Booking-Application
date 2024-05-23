package com.RedBus.User.Repository;

import com.RedBus.User.Entity.Role;
import com.RedBus.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Dictionary;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role ,Integer> {


}
