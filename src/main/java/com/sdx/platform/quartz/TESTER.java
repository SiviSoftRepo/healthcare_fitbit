package com.sdx.platform.quartz;

public class TESTER {
	
	public static void main(String[] args) {
		System.out.println("getRGB  "+getGreenToRedGradientByValue(0, 100));
		System.out.println("getRGB  "+getGreenToRedGradientByValue(100, 100));
		System.out.println("getRGB  "+getGreenToRedGradientByValue(350, 100));
		System.out.println("getRGB  "+getGreenToRedGradientByValue(655, 100));
		System.out.println("getRGB  "+getGreenToRedGradientByValue(2200, 100));
	}
	
	static int getTrafficlightColor(double value){
	    return java.awt.Color.HSBtoRGB((float)value/3f, 1f, 1f);
	}
	
	private static int getGreenToRedGradientByValue(int currentValue, int maxValue)
	{
		currentValue = ((currentValue*100)/4500);
	    int r = ( (255 * currentValue) / maxValue );
	    int g = ( 255 * (maxValue-currentValue) ) / maxValue;
	    int b = 0;
	    return ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
	}
}
