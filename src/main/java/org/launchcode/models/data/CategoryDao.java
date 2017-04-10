package org.launchcode.models.data;

import org.launchcode.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.transaction.Transactional;

/**
 * Created by Engineer on 4/10/2017.
 */
@Repository
@Transactional
public interface CategoryDao extends CrudRepository<Category, Integer>{
}
