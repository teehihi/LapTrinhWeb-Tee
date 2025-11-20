package LapTrinhWebJPA.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductId")
	private int productId;

	@Column(name = "ProductName", columnDefinition = "nvarchar(200) NOT NULL")
	private String productName;

	@Column(name = "Description", columnDefinition = "nvarchar(MAX)")
	private String description;

	@Column(name = "Price")
	private double price;

	@Column(name = "Image", columnDefinition = "nvarchar(500)")
	private String image;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;
}