package org.dadeco.cu996.api.repository;

import org.dadeco.cu996.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByNtAccount(String ntAccount);

	User findByEmail(String email);

}
