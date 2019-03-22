package org.grabber.lg.repository;

import org.grabber.lg.entity.LjWebPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<LjWebPost, Long> {

}
