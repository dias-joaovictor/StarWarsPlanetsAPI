package br.com.thorntail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Planet implements Serializable {

	private static final long serialVersionUID = 8576223570920637982L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Planet")
	@TableGenerator(name = "Planet", table = "sequences", pkColumnName = "id")
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "CLIMATE", nullable = false)
	private String climate;

	@JsonIgnore
	@Column(name = "TERRAIN", nullable = false)
	private String terrain;
	
	

	public Planet() {
		// TODO Auto-generated constructor stub
	}

	public Planet(Long id, String name, String climate, String terrain) {
		super();
		this.id = id;
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain + "]";
	}

}
