package com.example.reward.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.reward.model.Reward;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RewardService {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Map<Integer, Double> process(int id)
	{
		log.info("Inside process method - reading file and reward calculation starts");
		
		List<Reward> rewardList = new ArrayList<>();
		
		
		double point = 0.0;
		
		Map<Integer , Double> monthMap = new HashMap<>();
		
		try {
			rewardList = readFile();
			for(Reward reward : rewardList) {
				if(id == reward.getId())
				{
					int mnth = getMonthFromDate(reward.getDate());
					point = calculateReward(reward.getTrans());
					
					if(!monthMap.containsKey(mnth))
					{
						monthMap.put(mnth,point);
					}
					else
					{
						monthMap.put(mnth , (monthMap.get(mnth) + point));
					}
				}
			}
			
			
		}catch(Exception e)
		{
			
		}
		
		return monthMap;
	}
	
	public static double calculateReward(double transaction)
	{
		log.info("Inside calculate Reward");
		double transactionOver100 = 0.0;
		double transactionOver50 = 0.0;
		
		if(transaction <50)
			return 0.0;
		if(transaction > 100)
		{
			transactionOver100 = (transaction - 100) * 2;
		}
		if(transaction > 50 && transaction < 100) {
			transactionOver50 = (transaction % 50 ) * 1;
		}else {
			transactionOver50 = 50;
		}
		
		return (transactionOver100 + transactionOver50);
		
	}
	
	private List<Reward> readFile() throws IOException{
		log.info("Inside ReadFile -  T   ransaction file reading starts");
		
		List<Reward> rewardList = new ArrayList<>();
		Resource resource = resourceLoader.getResource("classpath:transaction.csv");
		InputStream inputStream = resource.getInputStream();
		
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		CSVParser csvParser = new  CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
		Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		
		for(CSVRecord csvRecord : csvRecords) {
			Reward reward = new Reward(
					Integer.parseInt(csvRecord.get("id")) ,
					csvRecord.get("name") , 
					Double.parseDouble(csvRecord.get("amount")),
					csvRecord.get("date"),
					0.0);
			rewardList.add(reward);
		}
		
		csvParser.close();
		log.info("Transaction file reading Ends");
		return rewardList;
		
	}
	
	private int getMonthFromDate(String date)
	{
	
		   SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		   try {
			   Date dt = formatter.parse(date);
			   LocalDate localDate = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			   int month = localDate.getMonthValue();
			   return month;
		   }catch(ParseException e)
		   {
			   log.error("Error in parsing date");
		   }
		   return 0;
	}

	
	public static void main(String[] args) {
		System.out.println(calculateReward(40));
	}
}
