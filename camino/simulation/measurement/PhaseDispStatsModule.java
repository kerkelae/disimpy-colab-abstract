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
public class PhaseDispStatsModule extends StatisticsModule {

	/** logging object */
	private final Logger logger= Logger.getLogger(this.getClass().getName());

	/** dimensionality of space */
	private static final int D= DiffusionSimulation.D;

	
	public PhaseDispStatsModule(Walker[] walker) {
		
		super(walker);
		
		
		Ns = (numMeas + D) * walker.length;
	
		stats = new double[Ns];
	}
	
	public PhaseDispStatsModule(Walker[] walker, int numMeas_in) {
		
		super(walker, numMeas_in);
		
		
		Ns = (numMeas + D) * walker.length;
	
		stats = new double[Ns];
	}

	/**
	 * Override runtime stats method. Just do nothing since all we care about is final phase
	 */
	public double[] getRuntimeStats(double t) {
		int index;
		for(int i = 0; i < walker.length; i++) {
			for(int k = 0; k < D; k++) {
				index = (numMeas + D)*i + k;
				stats[index] = (walker[i].r[k]- walker[i].r0[k]);
			}
			double[] dphi = walker[i].dPhi;
			for(int j = 0; j < numMeas; j++) {
				
				index = (numMeas+D)*i + D + j;
				if (dphi == null) {
					stats[index] += 0.0;
				}
				else
				{
					stats[index] = walker[i].dPhi[j];
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
