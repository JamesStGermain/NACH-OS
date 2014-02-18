package nachos.threads;
import nachos.machine.*;

/**
 * The TAlarm class keeps track of which thread has been put to sleep and how much
 * time they are to be kept that way. Giving us access to that information in one
 * single object.
 */

public class TAlarm{
	KThread thread;
	long time;
	
	public TAlarm(KThread _thread, long _time){
		thread = _thread;
		time = _time;
		}
	
	long getTime(){return time;}
	Kthread getThread(){return thread;}
	}

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
	/**
	 * LinkedList holding TAlarms that can easily be cycled though and checked.
	 */
	LinkedList<TAlarm> alarmQueue = new LinkedList<TAlarm>();
	
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public Alarm() {
    	Machine.timer().setInterruptHandler(new Runnable() {
    		public void run() {timerInterrupt();}
	    	});
    	}

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {
    	KThread.currentThread().yield();
    	/**
    	 * Cycle through the alarmQueue and check for matching times of if
    	 * a time has been accidentally passed.
    	 */
    	for(i = alarmQueue.size(); i > 0; --i){
    		if(System.currentTimeMillis() >= (alarmQueue.get(i)).getTime()){
    			((alarmQueue.get(i)).getThread()).ready();
    			alarmQueue.remove(i);
    			}
    		}
    		currentThread().yield();
    	}

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {
    	if(x>0){
    		Interrupt.disable();
    		/**
    		 * Using the system time + x, add the current thread with required time
    		 * to the linked list
    		 */
    		TAlarm ta = new TAlarm(currentThread(), System.currentTimeMillis()+x);
    		alarmQueue.add(ta);
    		sleep();
    		Interrupt.enable();
    		}
    	}
	}
