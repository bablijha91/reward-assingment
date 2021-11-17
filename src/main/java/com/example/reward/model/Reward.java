package com.example.reward.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reward {
	
	private int id;
	
	private String name;
	
	private double trans;
	
	private String date;
	
	private double point;
	

}
