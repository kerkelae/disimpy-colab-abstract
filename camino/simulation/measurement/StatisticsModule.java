package simulation.measurement;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import simulation.DiffusionSimulation;
import simulation.dynamics.StickyWalker;
import simulation.dynamics.Walker;

/**
 * derives statistical measures from the walkers directly,
 * without recourse to a synthetic scan. Obviously this is
 * theoretic data rather than scan measurements.
 * 
 * The class has a reference to the walkers in a simulation
 * and obtains various statistics at a time point (or otherwise)
 * that are returned in an array via the getStats() method
 *
 *
 * @author matt (m.hall@cs.ucl.ac.uk)
 *
 */
public abstract class StatisticsModule {

    /** logging object */
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    /** dimensionality of space */
    private final int D= DiffusionSimulation.D;
    
    /** number of stats returned */
    protected int Ns;
    
    /** array of walkers to do the stats on */
    protected final Walker[] walker;
    
    /** space to store returned values */
    protected double[] stats;
    
    /** number of measurements */
    protected int numMeas;
    
    /** constructor. needs array of walkers. Assume that there are only 3 measurements
     * 
     * @param walker array of walkers from simulation
     * 
     */
    public StatisticsModule(Walker[] walker){
        this.walker= walker;
        this.numMeas = 3;
        
        Ns=5;
        
        stats= new double[Ns];
    }
    public StatisticsModule(Walker[] walker, int numMeas_in){
        this.walker= walker;
        this.numMeas = numMeas_in;
        
        Ns=5;
        
        stats= new double[Ns];
    }
    public abstract double[] getRuntimeStats(double t);
        
    
    
    /**
     * abstract method that is called after the end of the simulation
     * so that stats can be calculated that accumulate over the entire
     * duration of the simulation
     * 
     * @return additional array of stats containing post-simulation measures
     * 
     */
    public abstract double[] getPostSimulationStats();
    
    
    
    
}
