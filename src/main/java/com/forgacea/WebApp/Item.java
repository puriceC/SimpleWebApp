package com.forgacea.WebApp;

import javax.persistence.*;

@Entity
@Table(name = "Items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
