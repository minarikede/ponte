package hu.ponte.hr;

import hu.ponte.hr.services.SignService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

/**
 * @author zoltan
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class SignTest {

  private final String IMAGE_BASE_PATH = "src/test/resources/images/";

  @Autowired
  SignService signService;

  Map<String, String> files = new LinkedHashMap<String, String>() {
    {
      put("cat.jpg",
          "XYZ+wXKNd3Hpnjxy4vIbBQVD7q7i0t0r9tzpmf1KmyZAEUvpfV8AKQlL7us66rvd6eBzFlSaq5HGVZX2DYTxX1C5fJlh3T3QkVn2zKOfPHDWWItdXkrccCHVR5HFrpGuLGk7j7XKORIIM+DwZKqymHYzehRvDpqCGgZ2L1Q6C6wjuV4drdOTHps63XW6RHNsU18wHydqetJT6ovh0a8Zul9yvAyZeE4HW7cPOkFCgll5EZYZz2iH5Sw1NBNhDNwN2KOxrM4BXNUkz9TMeekjqdOyyWvCqVmr5EgssJe7FAwcYEzznZV96LDkiYQdnBTO8jjN25wlnINvPrgx9dN/Xg==");
      put("enhanced-buzz.jpg",
          "tsLUqkUtzqgeDMuXJMt1iRCgbiVw13FlsBm2LdX2PftvnlWorqxuVcmT0QRKenFMh90kelxXnTuTVOStU8eHRLS3P1qOLH6VYpzCGEJFQ3S2683gCmxq3qc0zr5kZV2VcgKWm+wKeMENyprr8HNZhLPygtmzXeN9u6BpwUO9sKj7ImBvvv/qZ/Tht3hPbm5SrDK4XG7G0LVK9B8zpweXT/lT8pqqpYx4/h7DyE+L5bNHbtkvcu2DojgJ/pNg9OG+vTt/DfK7LFgCjody4SvZhSbLqp98IAaxS9BT6n0Ozjk4rR1l75QP5lbJbpQ9ThAebXQo+Be4QEYV/YXf07WXTQ==");
      put("rnd.jpg",
          "lM6498PalvcrnZkw4RI+dWceIoDXuczi/3nckACYa8k+KGjYlwQCi1bqA8h7wgtlP3HFY37cA81ST9I0X7ik86jyAqhhc7twnMUzwE/+y8RC9Xsz/caktmdA/8h+MlPNTjejomiqGDjTGvLxN9gu4qnYniZ5t270ZbLD2XZbuTvUAgna8Cz4MvdGTmE3MNIA5iavI1p+1cAN+O10hKwxoVcdZ2M3f7/m9LYlqEJgMnaKyI/X3m9mW0En/ac9fqfGWrxAhbhQDUB0GVEl7WBF/5ODvpYKujHmBAA0ProIlqA3FjLTLJ0LGHXyDgrgDfIG/EDHVUQSdLWsM107Cg6hQg==");
    }
  };

  @Test
  public void testProvidedData() {
    files.forEach((key, value) -> {
      try {

        Assert.assertEquals(signService.sign(getFileContent(key)), value);

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  public void test_verify() {
    files.forEach((key, value) -> {
      try {
        String signature = signService.sign(getFileContent(key));

        Assert.assertTrue(signService.verify(getFileContent(key), signature));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  public void test_isBase64() {

    files.forEach((key, value) -> {
      try {
        Assert.assertTrue(Base64.isBase64(signService.sign(getFileContent(key))));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  private byte[] getFileContent(String key) throws IOException {
    File file = ResourceUtils.getFile(IMAGE_BASE_PATH + key);
    return Files.readAllBytes(file.toPath());
  }


}
