package com.example.demo.user.repository;

import com.example.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**+
 * The repository for all of the Users in the database
 * @author jroot,ascase
 */
public interface UserRepository extends JpaRepository<User,Long>
{
}
