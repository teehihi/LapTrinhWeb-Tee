package LapTrinhWebJPA.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private double price;
	private String image;
	private int categoryId;
}