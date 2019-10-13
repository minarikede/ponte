package hu.ponte.hr.repository;

import hu.ponte.hr.controller.ImageMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetaRepository extends CrudRepository<ImageMeta, String> {

}
