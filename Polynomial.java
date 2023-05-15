public class Polynomial{
	double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = new double[coefficients.length];
		for(int i = 0; i < coefficients.length; i++) {
			this.coefficients[i] = coefficients[i];
		}
	}
	
	public Polynomial add(Polynomial p) {
		int length = 0;
		
		
		if(coefficients.length > p.coefficients.length) {
			length = coefficients.length;
		}else {
			length = p.coefficients.length;
		}
		
		double[] result = new double[length];
		
		for(int i = 0; i < length; i++) {
			double x, y = 0;
			
			try {
				x = coefficients[i];
			} catch (Exception e) {
				x = 0;
			}
			
			try {
				y = p.coefficients[i];
			} catch (Exception e) {
				y = 0;
			}
			
			result[i] = x + y;
		}
		
		
		Polynomial q = new Polynomial(result);
		return q;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for(int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * (Math.pow(x, i));
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
}