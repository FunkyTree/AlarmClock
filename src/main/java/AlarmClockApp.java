import ch.zhaw.alarmclock.main.simulation.AlarmClockMainSimulationBase;
import ch.zhaw.alarmclock.runtime.Executable;

public class AlarmClockApp extends AlarmClockMainSimulationBase {

	public AlarmClockApp(String[] args) throws Exception {
		super(args);
	}

	@Override
	protected Executable getDefaultExecutable() {
		return new AlarmClock();
	}

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(final String[] args) throws Exception {
		AlarmClockMainSimulationBase.main(new AlarmClockApp(args));
	}
}
