package LapTrinhWebJPA.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String cateName;
	private String image;
	private Integer ownerId;
	private String ownerName;

	public CategoryModel(int id, String cateName, String image) {
		this.id = id;
		this.cateName = cateName;
		this.image = image;
	}

	public CategoryModel(int id, String cateName, String image, Integer ownerId, String ownerName) {
		this(id, cateName, image);
		this.ownerId = ownerId;
		this.ownerName = ownerName;
	}
}