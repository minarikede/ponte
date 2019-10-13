package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.controller.ImageMeta.ImageMetaBuilder;
import hu.ponte.hr.repository.ImageMetaRepository;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStore {

  @Autowired
  private ImageMetaRepository imageMetaRepository;

  @Autowired
  private SignService signService;

  public void storeFile(MultipartFile file)
      throws Exception {

    ImageMetaBuilder imageMetaBuilder = ImageMeta.builder()
        .name(file.getOriginalFilename())
        .mimeType(file.getContentType())
        .size(file.getSize())
        .digitalSign(signService.sign(file.getBytes()))
        .base64File(encode(file));

    imageMetaRepository.save(imageMetaBuilder.build());

  }

  public List<ImageMeta> getFiles() {
    return StreamSupport.stream(imageMetaRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  public ImageMeta getFile(String id) {
    return imageMetaRepository.findById(id).orElse(null);
  }


  private String encode(MultipartFile file) throws IOException {
    return new String(Base64.getEncoder().encode(file.getBytes()));
  }

}
