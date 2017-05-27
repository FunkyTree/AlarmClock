import ch.zhaw.alarmclock.runtime.Executable;
import ch.zhaw.alarmclock.runtime.Platform;
import ch.zhaw.alarmclock.runtime.RgbIntColor;
import ch.zhaw.alarmclock.runtime.RgbPixelScreen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmClock implements Executable {
	private int delayCounter;
	private int currentDelayCounter;
	protected Platform platform;
    private int currentRectangleSize;
    private int digitWidth = 3;
    private int digitHeight = 5;
    private int halfWidth = 1;
    private int halfHeight = 3;

    private Date wakeUpDate;
    private boolean wakeUpState = false;
    private boolean hoursBlink = false;
    private boolean minutesBlink = false;
    private int changeCur = Calendar.HOUR;
    private boolean alarmActivated = false;
    private boolean showHours = true;
    private boolean showMinutes = true;

	private RgbIntColor[] colors = new RgbIntColor[]{
				new RgbIntColor(31, 0, 0), // 31 is maximum value for one color component 
				new RgbIntColor(0, 31, 0), // 31 is maximum value for one color component 
				new RgbIntColor(0, 31, 31) // 31 is maximum value for one color component 
			};
	private int currentColorIndex = 0;

	public AlarmClock(int delayCounter) {
		this.delayCounter = delayCounter;
	}

	public AlarmClock() {
		this.delayCounter = 10;
	}

	@Override
	public String toString() {
		return "Praktikum AlarmClock";
	}

	@Override
	public void start(Platform platform) {
		this.platform = platform;

		wakeUpDate = Calendar.getInstance().getTime();

		platform.setTimer10HzEventListener(this::handleTimer10HzEvent);
		platform.setButtonEventListener("Main", this::toggleWakeupState);
        platform.setButtonEventListener("Yellow", this::yellowClick);
        platform.setButtonEventListener("Blue", this::blueClick);
        platform.setButtonEventListener("Red", this::redClick);
        platform.setButtonEventListener("Green", this::greenClick);
	}

	protected void changeColor(Object sender){
		currentColorIndex = (currentColorIndex + 1) % colors.length;
	}

	protected void toggleWakeupState(Object sender){
	    wakeUpState = !wakeUpState;
    }

    protected void yellowClick(Object sender){
	    if (wakeUpState){
	        if (hoursBlink){
	            hoursBlink = false;
	            minutesBlink = true;
	            changeCur = Calendar.MINUTE;
            }

            else {
                resetWakeupMode();
                alarmActivated = true;
	        }
        }
    }

    protected void blueClick(Object sender){
        if (wakeUpState){
            resetWakeupMode();
        }
    }

    private void resetWakeupMode(){
        minutesBlink = false;
        hoursBlink = false;
        showHours = true;
        showMinutes = true;
    }

    protected void redClick(Object sender){
        if (wakeUpState){
            wakeUpDate = AddToDate(wakeUpDate, 1, changeCur);
            if (changeCur == Calendar.HOUR) hoursBlink = true;
            if (changeCur == Calendar.MINUTE) minutesBlink = true;
        }
    }

    protected void greenClick(Object sender){
        if (wakeUpState){
            wakeUpDate = AddToDate(wakeUpDate, -1, changeCur);
            if (changeCur == Calendar.HOUR) hoursBlink = true;
            if (changeCur == Calendar.MINUTE) minutesBlink = true;
        }
    }

    private Date AddToDate(Date date, int amount, int cur){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(cur, amount);
        return cal.getTime();
    }

	private Date GetDisplayedDate (){
	    if (wakeUpState) return wakeUpDate;
	    else{
	        return Calendar.getInstance().getTime();
        }
    }

	protected void handleTimer10HzEvent(Object sender) throws Exception {
		if (currentDelayCounter > 0){
			currentDelayCounter--;
			return;
		}
		currentDelayCounter = delayCounter;
		if (wakeUpState){
		    DisplayWakeupTime();
        }

        else {
		    DisplayTimeAndDate();
        }
	}

	private void DisplayWakeupTime() throws Exception{
        try (RgbPixelScreen screen = getScreen()) {
            RgbIntColor color = colors[currentColorIndex];
            RgbPixelScreenHelper.fillScreenBlack(screen);

            // format date to date and time
            Date currentDate = GetDisplayedDate();
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String timeString = timeFormat.format(currentDate);

            Calendar time = Calendar.getInstance();
            time.setTime(currentDate);
            int hours = time.get(Calendar.HOUR_OF_DAY);
            int minutes = time.get(Calendar.MINUTE);

            int xPos = 2;
            int yPos = 5;
            if (showHours){
                RgbPixelScreenHelper.drawNumber(screen, hours / 10, xPos, yPos, color);
                xPos += 4;
                RgbPixelScreenHelper.drawNumber(screen, hours % 10, xPos, yPos, color);
                xPos += 4;
            }

            else{
                xPos += 8;
            }

            screen.setRgbPixel(xPos, yPos + digitHeight - 1, color);
            screen.setRgbPixel(xPos, yPos + halfHeight - 1, color);
            xPos += 2;

            if (showMinutes){
                RgbPixelScreenHelper.drawNumber(screen, minutes / 10, xPos, yPos, color);
                xPos += 4;
                RgbPixelScreenHelper.drawNumber(screen, minutes % 10, xPos, yPos, color);
            }

            if (hoursBlink) showHours = !showHours;
            else showHours = true;

            if (minutesBlink) showMinutes = !showMinutes;
            else showMinutes = true;
        }
    }

	private void DisplayTimeAndDate() throws Exception{
        try (RgbPixelScreen screen = getScreen()) {
            RgbIntColor color = colors[currentColorIndex];
            RgbPixelScreenHelper.fillScreenBlack(screen);

            // format date to date and time
            Date currentDate = GetDisplayedDate();
            DateFormat dateFormat = new SimpleDateFormat("MM.dd.yy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String dateString = dateFormat.format(currentDate);
            String timeString = timeFormat.format(currentDate);

            int xPos = 2;
            int yPos = 5;
            for (int i = 0; i < dateString.length(); i++) {
                if (!Character.isDigit(dateString.charAt(i))) {
                    screen.setRgbPixel(xPos, yPos + digitHeight - 1, color);
                }
                if (Character.isDigit(dateString.charAt(i))) {
                    RgbPixelScreenHelper.drawNumber(screen, Character.getNumericValue(dateString.charAt(i)), xPos, yPos, color);
                    xPos += 2;
                }
                xPos += 2;
            }

            xPos = 2;
            yPos = 5 + digitHeight + 2;
            for (int i = 0; i < timeString.length(); i++) {
                if (!Character.isDigit(timeString.charAt(i))) {
                    screen.setRgbPixel(xPos, yPos + digitHeight - 1, color);
                    screen.setRgbPixel(xPos, yPos + halfHeight - 1, color);
                }
                if (Character.isDigit(timeString.charAt(i))) {
                    RgbPixelScreenHelper.drawNumber(screen, Character.getNumericValue(timeString.charAt(i)), xPos, yPos, color);
                    xPos += 2;
                }
                xPos += 2;
            }
        }
    }

	private RgbPixelScreen getScreen() {
		return platform.getDisplay().getScreen();
	}
}
