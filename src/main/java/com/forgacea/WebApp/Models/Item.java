package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Items")
public class Item {
	@Id
	@GeneratedValue
	int id;

	String name;
}
