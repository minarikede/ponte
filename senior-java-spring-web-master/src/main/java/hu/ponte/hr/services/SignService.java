package hu.ponte.hr.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class SignService {

  @Value("${keysPath.privateKey}")
  private String privateKeyPath;

  @Value("${keysPath.publicKey}")
  private String publicKeyPath;

  @Value("${signature.algorithm}")
  private String signatureAlgorithm;

  public String sign(byte[] bytes) throws Exception {
    PrivateKey privateKey = getPrivateKey();

    Signature privateSignature = Signature.getInstance(signatureAlgorithm);
    privateSignature.initSign(privateKey);
    privateSignature.update(bytes);

    byte[] signature = privateSignature.sign();

    return Base64.getEncoder().encodeToString(signature);
  }

  public boolean verify(byte[] bytes, String signature) throws Exception {
    PublicKey publicKey = getPublicKey();

    Signature publicSignature = Signature.getInstance(signatureAlgorithm);
    publicSignature.initVerify(publicKey);
    publicSignature.update(bytes);

    byte[] signatureBytes = Base64.getDecoder().decode(signature);

    return publicSignature.verify(signatureBytes);
  }

  private PrivateKey getPrivateKey()
      throws Exception {
    File file = ResourceUtils.getFile(privateKeyPath);

    byte[] keyBytes = Files.readAllBytes(Paths.get(file.getPath()));

    PKCS8EncodedKeySpec spec =
        new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(spec);
  }

  private PublicKey getPublicKey()
      throws Exception {
    File file = ResourceUtils.getFile(publicKeyPath);

    byte[] keyBytes = Files.readAllBytes(Paths.get(file.getPath()));

    X509EncodedKeySpec spec =
        new X509EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(spec);
  }
}
