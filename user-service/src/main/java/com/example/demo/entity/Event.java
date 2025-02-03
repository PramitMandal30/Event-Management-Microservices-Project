package com.example.demo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Event {

	private int id;

	@NotEmpty(message = "event name cannot be empty")
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date date;

	@NotEmpty(message = "location shouldn't be empty")
	private String location;

	@NotEmpty(message = "venue shouldn't be empty")
	private String venue;

	@NotEmpty(message = "provide a brief description")
	private String description;

}