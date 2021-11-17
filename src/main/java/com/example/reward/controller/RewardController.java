package com.example.reward.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reward.service.RewardService;

@RestController
@RequestMapping("v1/bjha")
public class RewardController {
	
	
	@Autowired
	RewardService rewardService;
	
	
	@GetMapping("/reward/{id}")
	public Map<Integer , Double> getFile(@PathVariable int id)
	{
		Map<Integer , Double> monthMap = rewardService.process(id);
		return monthMap;
	}

}
