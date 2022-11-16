package robatortas.code.files.core.utils;

/**<NEWLINE>
 * <b>MathUtils class</b>
 * <br><br>
 * To get any mathematical functions you need!
 * <br><br>
 * This library is built without any external classes.
 * <br><br>
 * Brings a bit of functions the standard Java Math Library doesn't support!
 */
public class MathUtils {
	
	/**<NEWLINE>
	 * <b>euclDistance function in the MathUtils class</b>
	 * <br><br>
	 * Get the Euclidean Distance from two points in 2D space.
	 * 
	 * @param x The X coordinate of the initial point.
	 * @param y The Y coordinate of the initial point.
	 * @param x1 The X coordinate of the ending point.
	 * @param y1 The Y coordinate of the ending point.
	 * 
	 * @see https://en.wikipedia.org/wiki/Euclidean_distance
	 */
	public static double euclDistance(double x, double y, double x1, double y1, double radius) {
		// Distance in X & Distance in Y
		double dx = x-x1;
		double dy = y-y1;
		
		double distance = Math.sqrt((dx*dx)+(dy*dy));
		if(distance <= radius) return distance;
		else return 0;
	}
	
	/**<NEWLINE>
	 * <b>power function in the MathUtils class</b>
	 * <br><br>
	 * Get the power of a number.
	 * <br><br>
	 * 
	 * @param val The value that is wanted to be powered.
	 * @param powerOf The power of the val parameter.
	 */
	public static double power(double val, double powerOf) {
		double result = val;
		// If it is a floating point value (decimal)
		if(Double.isFinite(powerOf)) {
			for(int i = 0; i <= powerOf-2; i++) {
				result *= val;
			}
		} else {
			for(int i = 0; i < powerOf-1; i++) {
				result *= val;
			}
		}
		return result;
	}
	
	/**<NEWLINE>
	 * <b>round function in the MathUtils class</b>
	 * <br><br>
	 * Round a double! (or a float if you want)
	 * 
	 * @param val The value that is wanted to be rounded.
	 */
	public static double round(double val) {
		String doubleString = String.valueOf(val);
		int indexDecimal = doubleString.indexOf('.');
		int decimal = Integer.parseInt(doubleString.substring(indexDecimal+1));
		if(decimal >= 5) {
			val+=1;
			decimal = 0;
		}
		else if(decimal < 5) decimal-=decimal;
		String finl = (int) val + "." + decimal;
		return Double.parseDouble(finl);
	}

	/**<NEWLINE>
	 * <b>squareRoot function in the MathUtils class</b>
	 * <br><br>
	 * Get the square root of a value.
	 * 
	 * @param val The value that will be rooted.
	 */
	static int subsTime = 0;
	public static double squareRoot(double val) {
		for(int i = 0; i < val; i++) {
			if(i % 2 != 0) {
				val--;
//				subsTime++;
//				System.out.println(val);
				return subsTime;
			}
		}
		return subsTime;
	}
}