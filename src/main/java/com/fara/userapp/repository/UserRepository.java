package com.fara.userapp.repository;

import com.fara.userapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByUserName(String userName);

	User findUserByUserName(String userName);

	@Query("SELECT u FROM User u WHERE (u.userName LIKE :userName OR :userName IS NULL) " +
			"AND (u.name LIKE :name OR :name IS NULL) " +
			"AND (u.email LIKE :email OR :email IS NULL)")
	Page<User> findUsersByUserNameOrNameOrEmail(@Param("userName") String userName,
												@Param("name") String name,
												@Param("email") String email,
												Pageable pageable);

}
