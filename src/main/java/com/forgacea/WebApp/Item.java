package com.forgacea.WebApp;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;
}
