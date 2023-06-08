import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Polynomial{
	double[] coefficients;
	int[] exponents;
	
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
		exponents = new int[1];
		exponents[0] = 0;
	}
	
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = new double[coefficients.length];
		this.exponents = new int[exponents.length];
		for(int i = 0; i < coefficients.length; i++) {
			this.coefficients[i] = coefficients[i];
		}
		for(int i = 0; i < exponents.length; i++) {
			this.exponents[i] = exponents[i];
		}
	}
	
	public Polynomial(File file) {
		try {
		Scanner scanner = new Scanner(file);
		String line = scanner.nextLine();
		scanner.close();
		ArrayList<Double> coefficients = new ArrayList<Double>();
		ArrayList<Integer> exponents = new ArrayList<Integer>();
		String[] split_line = line.split("(?=-)|(?=\\+)");
		for (String split: split_line) {
			String[] second_split = split.split("[x]");
			boolean variable_power_one = false;
			for(int i = 0; i < split.length(); i++){
				char ch = split.charAt(i);
				if (ch == 'x') {
					variable_power_one = true;
					break;
				}
				variable_power_one = false;
			}
			coefficients.add(Double.parseDouble(second_split[0]));
			try {
				exponents.add(Integer.parseInt(second_split[1]));
			}catch (Exception e) {
				if(variable_power_one){
					exponents.add(1);
					continue;
				}
				exponents.add(0);
			}
		}
		
		this.coefficients = new double[coefficients.size()];
		this.exponents = new int[exponents.size()];
		
		convert_to_array(coefficients, this.coefficients);
		convert_to_array(exponents, this.exponents);
		
		
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	public Polynomial add(Polynomial p) {

		int length = coefficients.length+p.coefficients.length;
		int explength = exponents.length+p.exponents.length;
		int idx = 0;
		
		
		double[] coeffresult = new double[length];
		int[] expresult = new int[explength];
		
		for(int i = 0; i < length; i++) {
			
			double x,y = 0;
			
			try {
				x = coefficients[i];
			} catch (Exception e) {
				x = -1;
			}
			
			try {
				y = p.coefficients[i];
			} catch (Exception e) {
				y = -1;
			}
			
			if(x != -1 && !search_array(coeffresult, x)) {
				coeffresult[idx] = x;
				idx++;
			}
			if(y != -1 && !search_array(coeffresult, y)) {
				coeffresult[idx] = y;
				idx++;
			}	
			
		}
		
		idx = 0;
		
		for(int i = 0; i < explength; i++) {
			
			int x,y = 0;
			
			try {
				x = exponents[i];
			} catch (Exception e) {
				x = -1;
			}
			
			try {
				y = p.exponents[i];
			} catch (Exception e) {
				y = -1;
			}
			
			if(x != -1 && !search_array(expresult, x)) {
				expresult[idx] = x;
				idx++;
			}
			if(y != -1 && !search_array(expresult, y)) {
				expresult[idx] = y;
				idx++;
			}	
			
		}
		
		Arrays.sort(expresult);
		
		Polynomial q = new Polynomial(coeffresult, expresult);
		return q;
	}
	
	private boolean search_array(int[] exponents, int exponent) {
		for(int i = 0; i < exponents.length; i++) {
			if(exponents[i] == exponent) {
				return true;
			}
		}
		return false;
	}
	
	private boolean search_array(double[] exponents, double exponent) {
		for(int i = 0; i < exponents.length; i++) {
			if(exponents[i] == exponent) {
				return true;
			}
		}
		return false;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for(int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * (Math.pow(x, exponents[i]));
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result = 0;
		result = evaluate(x);
		if(result == 0) {
			return true;
		}
		return false;
	}
	
	public Polynomial multiply(Polynomial p) {
		
		HashMap<Integer, Double> hashmap = new HashMap<Integer, Double>();
		int length = coefficients.length+p.coefficients.length;
		int explength = exponents.length+p.exponents.length;
		
		double[] coeffresult = new double[length];
		int[] expresult = new int[explength];
		int idx = 0;
		
		for (int i = 0; i < exponents.length; i++) {
			for(int j = 0; j < p.exponents.length; j++) {
				int new_exponent = exponents[i] + p.exponents[j];
				boolean added = false;
				if(!search_array(expresult, new_exponent)) {
					expresult[idx] = new_exponent;
					added = true;
				}
				double new_coefficient = coefficients[i] * p.coefficients[j];
				if(!added) {
					coeffresult[idx-1] = coeffresult[idx-1] + new_coefficient;
				}else {
					coeffresult[idx] = new_coefficient;
				}
				idx++;
			}
		}
		
		for(int i = 0; i < expresult.length; i++) {
			hashmap.put(expresult[i], coeffresult[i]);
		}
		
		ArrayList<Double> cresult = new ArrayList<Double>();
		ArrayList<Integer> eresult = new ArrayList<Integer>();
		
		for(int key: hashmap.keySet()) {
			if(key == 0 && hashmap.get(key) == 0) {
				continue;
			}
			cresult.add(hashmap.get(key));
			eresult.add(key);
		}
		
		coeffresult = new double[cresult.size()];	
		expresult = new int[eresult.size()];
		
		convert_to_array(cresult, coeffresult);
		convert_to_array(eresult, expresult);
		
		Polynomial q = new Polynomial(coeffresult, expresult);
		return q;
	}
	
	private void convert_to_array(ArrayList<Integer> arraylist, int[] result) {
		int idx = 0;
		for (Integer value : arraylist.toArray(new Integer[0])) {
			result[idx] = value.intValue();
			idx++;
		}
	}
	
	private void convert_to_array(ArrayList<Double> arraylist, double[] result) {
		int idx = 0;
		for (Double value : arraylist.toArray(new Double[0])) {
			result[idx] = value.doubleValue();
			idx++;
		}
	}
	
	public void saveToFile(String filename) {
		File file = new File("./" + filename);
		try {
			if (file.createNewFile()) {
			    System.out.println("File created: " + file.getName());
			  } else {
			    System.out.println("File already exists.");
			  }
			FileWriter writer = new FileWriter("./" + filename);
			String result = "" + coefficients[0];
			if( exponents[0] == 1) {
				result += "x";
			}
			else if(exponents[0] != 0){
				result += "x" + exponents[0];
			}
			for(int i = 1; i < coefficients.length; i++) {
				if(coefficients[i] > 0) {
					if(exponents[i] == 1){
						result += "+" + coefficients[i] + "x";
						continue;
					}
					result += "+" + coefficients[i] + "x" + exponents[i];
				}else {
					if(exponents[i] == 1){
						result += coefficients[i] + "x";
						continue;
					}
					result += coefficients[i] + "x" + exponents[i];
				}
			}
			writer.write(result);
			writer.close();
			System.out.println("Written to file!");
		} catch (IOException e) {
			System.out.println("Error");
		}
		
		
	}
}