import ch.zhaw.alarmclock.runtime.RgbIntColor;
import ch.zhaw.alarmclock.runtime.RgbPixelScreen;
import org.w3c.dom.css.RGBColor;

import java.lang.reflect.Array;

public class RgbPixelScreenHelper {

	private RgbPixelScreenHelper() {
	}

	public static void fillScreenBlack(RgbPixelScreen screen){
		fillRectangle(screen, 0, 0, screen.getWidth(), screen.getHeight(), 0, 0, 0); 
	}
	
	public static void fillScreen(RgbPixelScreen screen, RgbIntColor color){
		fillRectangle(screen, 0, 0, screen.getWidth(), screen.getHeight(), 
				color.getRed(), color.getGreen(), color.getBlue()); 
	}
	
	public static void fillScreen(RgbPixelScreen screen, int red, int green, int blue){
		fillRectangle(screen, 0, 0, screen.getWidth(), screen.getHeight(), red, green, blue); 
	}
	
	public static void fillRectangleBlack(RgbPixelScreen screen, 
			int startX, int startY, int width, int height){
		fillRectangle(screen, startX, startY, width, height, 0, 0, 0); 
	}
	
	public static void fillRectangle(RgbPixelScreen screen, 
			int startX, int startY, int width, int height, 
			RgbIntColor color){
		fillRectangle(screen, startX, startY, width, height, 
				color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public static void fillRectangle(RgbPixelScreen screen, 
			int startX, int startY, int width, int height, 
			int red, int green, int blue){
		for(int y = startY; y < startY + height; y++){
			for (int x = startX; x < startX + width; x++){
				screen.setRgbPixel(x, y, red, green, blue);  
			}
		}
	}
	
	public static void drawVerticalLine(RgbPixelScreen screen, 
			int startX, int startY, int height, 
			RgbIntColor color){
		drawVerticalLine(screen, startX, startY, height, 
				color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public static void drawVerticalLine(RgbPixelScreen screen, 
			int startX, int startY, int height, 
			int red, int green, int blue){
		for(int y = startY; y < startY + height; y++){
			screen.setRgbPixel(startX, y, red, green, blue);  
		}
	}

	public static void drawHorizontalLine(RgbPixelScreen screen, 
			int startX, int startY, int width, 
			RgbIntColor color){
		drawHorizontalLine(screen, startX, startY, width, 
				color.getRed(), color.getGreen(), color.getBlue()); 
	}
	
	public static void drawHorizontalLine(RgbPixelScreen screen, 
			int startX, int startY, int width, 
			int red, int green, int blue){
		for (int x = startX; x < startX + width; x++){
			screen.setRgbPixel(x, startY, red, green, blue);  
		}
	}
	
	public static void drawCross(RgbPixelScreen screen, 
			int startX, int startY, int width, 
			RgbIntColor color){
		drawCross(screen, startX, startY, width, 
				color.getRed(), color.getGreen(), color.getBlue()); 
	}
	
	public static void drawCross(RgbPixelScreen screen, 
			int startX, int startY, int width, 
			int red, int green, int blue){
		int x1 = startX;
		int x2 = startX + width-1;
		int y1 = startY;
		for (int i = 0; i < width; i++){
			screen.setRgbPixel(x1, y1, red, green, blue);  
			screen.setRgbPixel(x2, y1, red, green, blue); 
			x1++;
			x2--;
			y1++;
		}
	}
	
	public static void drawOk(RgbPixelScreen screen, 
			int startX, int startY, RgbIntColor color){
		screen.setRgbPixel(startX, startY+1, color);
		screen.setRgbPixel(startX + 1, startY+2, color);
		screen.setRgbPixel(startX + 2, startY+1, color);
		screen.setRgbPixel(startX + 3, startY, color);
	}

	public static void drawNumber(RgbPixelScreen screen, int number, int startX, int startY, RgbIntColor color){
	    int digitWidth = 3;
	    int digitHeight = 5;
	    int halfWidth = 1;
	    int halfHeight = 3;

	    switch (number){
            case 0:
                fillRectangle(screen, startX, startY, digitWidth, digitHeight, color);
                fillRectangle(screen, startX+1, startY+1, halfWidth, halfHeight, 0,0,0);
                break;
            case 1:
                drawVerticalLine(screen, startX + 1, startY, digitHeight, color);
                break;
            case 2:
                // draw 8
                fillRectangle(screen, startX, startY, digitWidth, digitHeight, color);
                fillRectangle(screen, startX+1, startY+1, halfWidth, halfHeight, 0,0,0);
                drawHorizontalLine(screen, startX, startY + halfHeight - 1, digitWidth, color);

                // remove what's too much
                screen.setRgbPixel(startX, startY + 1, 0,0,0);
                screen.setRgbPixel(startX + digitWidth - 1, startY + digitHeight - 2, 0,0,0);
                break;
            case 3:
                // draw 8
                fillRectangle(screen, startX, startY, digitWidth, digitHeight, color);
                fillRectangle(screen, startX+1, startY+1, halfWidth, halfHeight, 0,0,0);
                drawHorizontalLine(screen, startX, startY + halfHeight - 1, digitWidth, color);

                // remove what's too much
                screen.setRgbPixel(startX, startY + digitHeight - 2, 0,0,0);
                screen.setRgbPixel(startX , startY + 1, 0,0,0);
                break;
            case 4:
                fillRectangle(screen, startX, startY, digitWidth, halfHeight, color);
                screen.setRgbPixel(startX + 1, startY + 1, 0,0,0);
                drawVerticalLine(screen, startX + digitWidth - 1, startY + halfHeight - 1, halfHeight, color);
                screen.setRgbPixel(startX + 1, startY, 0,0,0);
                break;
            case 5:
                // draw 8
                fillRectangle(screen, startX, startY, digitWidth, digitHeight, color);
                fillRectangle(screen, startX+1, startY+1, halfWidth, halfHeight, 0,0,0);
                drawHorizontalLine(screen, startX, startY + halfHeight - 1, digitWidth, color);

                // remove what's too much
                screen.setRgbPixel(startX, startY + digitHeight - 2, 0,0,0);
                screen.setRgbPixel(startX + digitWidth - 1, startY + 1, 0,0,0);
                break;
            case 6:
                // draw 8
                fillRectangle(screen, startX, startY, digitWidth, digitHeight, color);
                fillRectangle(screen, startX+1, startY+1, halfWidth, halfHeight, 0,0,0);
                drawHorizontalLine(screen, startX, startY + halfHeight - 1, digitWidth, color);

                // remove what's too much
                screen.setRgbPixel(startX + digitWidth - 1, startY + 1, 0,0,0);
                break;
            case 7:
                drawHorizontalLine(screen, startX, startY, digitWidth, color);
                drawVerticalLine(screen, startX + digitWidth - 1, startY, digitHeight, color);
                break;
            case 8:
                fillRectangle(screen, startX, startY, digitWidth, digitHeight, color);
                fillRectangle(screen, startX+1, startY+1, halfWidth, halfHeight, 0,0,0);
                drawHorizontalLine(screen, startX, startY + halfHeight - 1, digitWidth, color);
                break;
            case 9:
                fillRectangle(screen, startX, startY, digitWidth, halfHeight, color);
                screen.setRgbPixel(startX + 1, startY + 1, 0,0,0);
                drawVerticalLine(screen, startX + digitWidth - 1, startY + halfHeight - 1, halfHeight - 1, color);
                drawHorizontalLine(screen, startX, startY + digitHeight - 1, digitWidth, color);
                break;
        }



    }
}






















