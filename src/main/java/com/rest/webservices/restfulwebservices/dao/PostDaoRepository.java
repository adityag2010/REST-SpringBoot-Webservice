package com.rest.webservices.restfulwebservices.dao;

import com.rest.webservices.restfulwebservices.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDaoRepository extends JpaRepository<Post, Integer> {
}
