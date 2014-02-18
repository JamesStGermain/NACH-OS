package nachos.threads;
import nachos.machine.*;

/**
 * The Autograder creates a ReactWater object and a bunch of threads. hThreads call the hReady method
 * while the oThreads call the oReady method. We need to join two hReady threads and one
 * oReady thread in order to makeWater.
 */

public class ReactWater{
    /**
     * Linkedlists required to hold the atoms that are ready
     */
    LinkedList<KThread> hQueue = new LinkedList<KThread>();
    LinkedList<KThread> oQueue = new LinkedList<kThread>();
    
    /**
     * Constructor for ReactWater which eventually calls upon MakeWater when the conditions
     * are met.
     */
    public ReactWater() {
    	//We only need to implement oReady and hReady according to the Nacho Phase I document
        }

    /** 
     *   When H element comes, if there already exist another H element 
     *   and an O element, then call the method of Makewater(). Or let 
     *   H element wait in line. 
     **/ 
    public void hReady() {
    	if(hQueue.getLast() && oQueue.getLast())
    		hQueue.removeLast();
    		oQueue.removeLast();
    		MakeWater();
    	else
    		hQueue.add(this.(Thread));
        }
 
    /** 
     *   When O element comes, if there already exist another two H
     *   elements, then call the method of Makewater(). Or let O element
     *   wait in line. 
     **/ 
    public void oReady() {
    	if(hQueue.getLast() && hQueue.get(hQueue.size()-2))
    		hQueue.removeLast();
    		hQueue.remove(hQueue.size()-2);
    		MakeWater();
    	else
    		oQueue.add(this.(Thread));
        }
    
    /** 
     *   Print out the message of "water was made!".
     **/
    public void Makewater() {
        System.out.println("Water has been made!");
        }
    }