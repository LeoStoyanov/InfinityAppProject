package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**+
 * The repository for all of the Users in the database
 * @author jroot
 */
public interface UserRepository extends JpaRepository<User,Long>
{
}
