package com.online.education.Repository;

import com.online.education.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Long>  {
    UserType findById(long id);
    UserType findByName(String name);
}
