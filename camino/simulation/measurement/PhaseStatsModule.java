package simulation.measurement;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import data.OutputManager;

import misc.LoggedException;

import simulation.DiffusionSimulation;
import simulation.SimulationParams;
import simulation.dynamics.Walker;
import tools.CL_Initializer;

/**
 * generates mean-squared displacements
 *
 * @author matt (m.hall@cs.ucl.ac.uk)
 *
 */
public class PhaseStatsModule extends StatisticsModule {

	/** logging object */
	private final Logger logger= Logger.getLogger(this.getClass().getName());

	/** dimensionality of space */
	private static final int D= DiffusionSimulation.D;

	public PhaseStatsModule(Walker[] walker) {
		
		super(walker);
		
		
		Ns = numMeas * walker.length;
	
		stats = new double[Ns];
	}
	
	public PhaseStatsModule(Walker[] walker, int numMeas_in) {
		
		super(walker, numMeas_in);
		
		
		Ns = numMeas * walker.length;
	
		stats = new double[Ns];
	}

	/**
	 * Override runtime stats method. Just do nothing since all we care about is final phase
	 */
	public double[] getRuntimeStats(double t) {
		for(int i = 0; i < walker.length; i++) {
			double[] dphi = walker[i].dPhi;
			for(int j = 0; j < numMeas; j++) {
				if (dphi == null) {
					stats[numMeas*i + j] += 0.0;
				}
				else
				{
					stats[numMeas*i + j] = walker[i].dPhi[j];
				}
			}
		}
		
		return stats;
	}

	/**
	 * Override post simulation stats, get the total phase accrued for each spin
	 */
	public double[] getPostSimulationStats() {
		//loop over all spins
//		for(int i = 0; i < walker.length; i++) {
//			for(int j = 0; j < D; j++) {
//				stats[D*i + j] = walker[i].dPhi[j];
//			}
//		}
//		
		return null;
	}

}
