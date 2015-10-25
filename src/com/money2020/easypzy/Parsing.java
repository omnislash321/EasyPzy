package com.money2020.easypzy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;

public class Parsing {
	//QBYA1F7005359
	
		public static List<String> getProductEntries(String barcodeId){
		File sdcard = Environment.getExternalStorageDirectory();
		
		File file = new File(sdcard, "stores.csv");
		
//		String file = "C:/Users/batbo_000/Downloads/stores.csv";

		String delimiter = ",";
		List<String> resultSet = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String result = "";
			while((result = br.readLine()) != null){
				if(result.substring(0, result.indexOf(delimiter)).equals(barcodeId)){
					resultSet.add(result);				
					Log.d("Testing", "size: " + result);
				}
			}
		}	
		catch(IOException e){
			System.out.println("Error reading file");
		}
		
		return resultSet;
	}
		
	public static List<Product> parseEntries(String barcodeId){
		List<String> results = getProductEntries(barcodeId);
		List<Product> parsedSet = new ArrayList<Product>();
		for(int i = 0; i < results.size(); i++){
			int index = 0;
			Product temp = new Product();
			
			temp.setBarcodeId(results.get(i).substring(index, results.get(i).indexOf(",", index)));
			
			index = results.get(i).indexOf(",", index) +1;
			
			temp.setCompany(results.get(i).substring(index, results.get(i).indexOf(",", index)));
			
			index = results.get(i).indexOf(",", index) +1;
			
			temp.setProductName(results.get(i).substring(index,  results.get(i).indexOf(",", index)));
			
			index = results.get(i).indexOf(",", index) +1;
			
			temp.setPrice(Float.valueOf(results.get(i).substring(index, results.get(i).indexOf(",", index))));
			
			index = results.get(i).indexOf(",", index)+1;
			
			temp.setStoreName(results.get(i).substring(index,  results.get(i).length()));
			parsedSet.add(temp);
		}
		return parsedSet;
	}
	
	public static List<Float> getPrices(String barcodeId){
		List<Product> set = parseEntries(barcodeId);
		
		List<Float> prices = new ArrayList<Float>();
		for(Product e : set){
			prices.add(e.getPrice());
		}
		
		return prices;
	}
}
