package hu.ponte.hr.controller;


import hu.ponte.hr.services.ImageStore;
import hu.ponte.hr.services.SignService;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

  @Autowired
  private ImageStore imageStore;

  @Autowired
  private SignService signService;

  @GetMapping("meta")
  public List<ImageMeta> listImages() {
    return imageStore.getFiles();
  }

  @GetMapping("preview/{id}")
  public ResponseEntity getImage(@PathVariable("id") String id)
      throws Exception {
    ImageMeta file = imageStore.getFile(id);
    byte[] decodedImage = Base64.getDecoder().decode(file.getBase64File());

    if (signService.verify(decodedImage, file.getDigitalSign())) {
      return ResponseEntity.ok(decodedImage);
    }
    return ResponseEntity.ok(null);
  }
}
