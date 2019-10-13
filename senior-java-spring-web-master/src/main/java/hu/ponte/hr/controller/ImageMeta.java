package hu.ponte.hr.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author zoltan
 */
@Getter
@Builder
@Entity
@Table(name = "image_meta")
@AllArgsConstructor
public class ImageMeta
{
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "mime_type")
	private String mimeType;

	@Column(name = "size")
	private long size;

	private String digitalSign;

	@Column(name = "base64_file", columnDefinition="BLOB")
	@Lob
	private String base64File;

	public ImageMeta() {
	}

}
