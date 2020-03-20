package acmevolar.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Clouds {
	 private float all;


	 // Getter Methods 

	 public float getAll() {
	  return all;
	 }

	 // Setter Methods 

	 public void setAll(float all) {
	  this.all = all;
	 }
	}
