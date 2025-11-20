package LapTrinhWebJPA.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String cateName;
	private String image;
}