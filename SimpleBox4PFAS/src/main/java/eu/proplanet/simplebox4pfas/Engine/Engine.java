package eu.proplanet.simplebox4pfas.Engine;

import org.apache.commons.math3.linear.MatrixUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import eu.proplanet.simplebox4pfas.Scenario;
import lombok.Getter;
import lombok.Setter;

public class Engine {
	InputEngine input = null;
	RegionalEngine regional = null;
	Map<String, String> nano = null;
	Scenario scene = null;
	RegionalEngine environment = null;

	// Matrix that holds the linear coefficients
	double[][] coeffs;

	// Apache real matrix of the coeffs
	// These are needed because of the decision to use apache to manipulate
	// matrices.
	// We should emit double[][] but the entries are too many - for now.
	// If a double[][] does not exist is because by the time we added apache we had
	// not given values to this matrix (or array);
	@Getter
	@Setter
	RealMatrix rCoeffs = null;

	// Mass flows matrix in the paper that holds the linear coefficients
	@Getter
	@Setter
	RealMatrix rMassFlows;

	// Volume array
	double[] volumes;

	@Getter
	@Setter
	RealMatrix rVolumes = null;

	// Emissions array
	double[] emissions;

	@Getter
	@Setter
	RealMatrix rEmissions = null;

	// Concentration of the nanomaterial
	@Getter
	@Setter
	RealMatrix rConc = null;

	// Fugacity
	@Getter
	@Setter
	RealMatrix rFugacity = null;

	// Mass of the nanomaterial in mol
	@Getter
	@Setter
	RealMatrix rMassMol = null;

	// Mass of the nanomaterial in Kg
	@Getter
	@Setter
	RealMatrix rMassKg = null;

	// Mass distribution
	@Getter
	@Setter
	RealMatrix rMassDistrubution = null;

	// Mass removal
	@Getter
	@Setter
	RealMatrix rMassMolRemoval = null;

	double dTotalMassMol = 0.0;

	public Engine(InputEngine in, RegionalEngine envi, Map<String, String> nanomaterial, Scenario scenario) {
		environment = envi;
		input = in;
		nano = nanomaterial;
		scene = scenario;

		int N = 33;

		this.coeffs = new double[N][N];
		this.volumes = new double[N];
		this.emissions = new double[N];

		this.rMassMol = new Array2DRowRealMatrix(new double[N]);
		this.rMassKg = new Array2DRowRealMatrix(new double[N]);
		this.rConc = new Array2DRowRealMatrix(new double[N]);
		this.rFugacity = new Array2DRowRealMatrix(new double[N]);

		this.rMassFlows = new Array2DRowRealMatrix(N, N);
		this.rMassDistrubution = new Array2DRowRealMatrix(new double[N]);
		this.rMassMolRemoval = new Array2DRowRealMatrix(new double[N]);
	}

	public void build() {
		for (int i = 0; i < coeffs.length; i++) {

			this.volumes[i] = 0.0;
			this.emissions[i] = 0.0;

			for (int j = 0; j < coeffs[i].length; j++) {
				this.coeffs[i][j] = 0.0;
			}
		}

		this.createVolumes();
		this.createEmissions();
		this.createCoeffMatrix();
		this.createMassMatrices();
//		this.createMassMolRemoval();
		this.createMassFlows();
	}

	void createMassFlows() {
		for (int i = 0; i < this.rMassFlows.getRowDimension(); i++) {
			for (int j = 0; j < this.rMassFlows.getColumnDimension(); j++) {
				if (i != j)
					this.rMassFlows.setEntry(i, j, this.rCoeffs.getEntry(i, j) * this.rMassMol.getEntry(j, 0));
			}
		}

		for (int i = 0; i < this.rMassFlows.getRowDimension(); i++) {
			double dSumCols = 0.0;
			for (int j = 0; j < this.rMassFlows.getColumnDimension(); j++) {
				if (i != j)
					dSumCols += this.rMassFlows.getEntry(i, j);
			}

			double dSumRows = 0.0;
			for (int j = 0; j < this.rMassFlows.getColumnDimension(); j++) {
				if (i != j)
					dSumRows += this.rMassFlows.getEntry(j, i);
			}

			this.rMassFlows.setEntry(i, i, dSumCols - dSumRows);
		}

		/*
		 * try { FileWriter myWriter = new FileWriter("MassFlowMatrix"); for ( int i =
		 * 0; i< this.rMassFlows.getRowDimension(); i++) { for ( int j = 0; j<
		 * this.rMassFlows.getColumnDimension(); j++ ) myWriter.write( Double.toString(
		 * this.rMassFlows.getEntry( i , j ) ) + "\t" );
		 * 
		 * myWriter.write( "\n" ); } myWriter.close();
		 * 
		 * } catch (IOException e) { System.out.println("An error occurred.");
		 * e.printStackTrace(); }
		 */
	}

	void createMassMolRemoval() {
		this.rMassMolRemoval.setEntry(0, 0,
				environment.getEnvProps("REGIONAL", "k.aRG") * this.rMassMol.getEntry(0, 0));
//		this.rMassMolRemoval.setEntry(1, 0, environment.getEnvProps("REGIONAL","k.aRS")*this.rMassMol.getEntry(1, 0) );
//		this.rMassMolRemoval.setEntry(2, 0, environment.getEnvProps("REGIONAL","k.aRA")*this.rMassMol.getEntry(2, 0) );
//		this.rMassMolRemoval.setEntry(3, 0, environment.getEnvProps("REGIONAL","k.aRP")*this.rMassMol.getEntry(3, 0) );
		this.rMassMolRemoval.setEntry(1, 0,
				environment.getEnvProps("REGIONAL", "k.w0RD") * this.rMassMol.getEntry(1, 0));
		this.rMassMolRemoval.setEntry(2, 0,
				environment.getEnvProps("REGIONAL", "k.w1RD") * this.rMassMol.getEntry(2, 0));
		this.rMassMolRemoval.setEntry(3, 0,
				environment.getEnvProps("REGIONAL", "k.w2RD") * this.rMassMol.getEntry(3, 0));
		/*
		 * this.rMassMolRemoval.setEntry(19, 0,
		 * environment.getEnvProps("REGIONAL","k.sd0RD")*this.rMassMol.getEntry(19, 0)
		 * ); this.rMassMolRemoval.setEntry(20, 0,
		 * environment.getEnvProps("REGIONAL","k.sd0RS")*this.rMassMol.getEntry(20, 0)
		 * ); this.rMassMolRemoval.setEntry(21, 0,
		 * environment.getEnvProps("REGIONAL","k.sd0RA")*this.rMassMol.getEntry(21, 0)
		 * ); this.rMassMolRemoval.setEntry(22, 0,
		 * environment.getEnvProps("REGIONAL","k.sd0RP")*this.rMassMol.getEntry(22, 0)
		 * ); this.rMassMolRemoval.setEntry(23, 0,
		 * environment.getEnvProps("REGIONAL","k.sd1RD")*this.rMassMol.getEntry(23, 0)
		 * ); this.rMassMolRemoval.setEntry(24, 0,
		 * environment.getEnvProps("REGIONAL","k.sd1RS")*this.rMassMol.getEntry(24, 0)
		 * ); this.rMassMolRemoval.setEntry(25, 0,
		 * environment.getEnvProps("REGIONAL","k.sd1RA")*this.rMassMol.getEntry(25, 0)
		 * ); this.rMassMolRemoval.setEntry(26, 0,
		 * environment.getEnvProps("REGIONAL","k.sd1RP")*this.rMassMol.getEntry(26, 0)
		 * ); this.rMassMolRemoval.setEntry(27, 0,
		 * environment.getEnvProps("REGIONAL","k.sd2RD")*this.rMassMol.getEntry(27, 0)
		 * ); this.rMassMolRemoval.setEntry(28, 0,
		 * environment.getEnvProps("REGIONAL","k.sd2RS")*this.rMassMol.getEntry(28, 0)
		 * ); this.rMassMolRemoval.setEntry(29, 0,
		 * environment.getEnvProps("REGIONAL","k.sd2RA")*this.rMassMol.getEntry(29, 0)
		 * ); this.rMassMolRemoval.setEntry(30, 0,
		 * environment.getEnvProps("REGIONAL","k.sd2RP")*this.rMassMol.getEntry(30, 0)
		 * );
		 */
		this.rMassMolRemoval.setEntry(6, 0,
				environment.getEnvProps("REGIONAL", "k.s1RD") * this.rMassMol.getEntry(4, 0));
//		this.rMassMolRemoval.setEntry(32, 0, environment.getEnvProps("REGIONAL","k.s1RS")*this.rMassMol.getEntry(32, 0) );
//		this.rMassMolRemoval.setEntry(33, 0, environment.getEnvProps("REGIONAL","k.s1RA")*this.rMassMol.getEntry(33, 0) );
//		this.rMassMolRemoval.setEntry(34, 0, environment.getEnvProps("REGIONAL","k.s1RP")*this.rMassMol.getEntry(34, 0) );
		this.rMassMolRemoval.setEntry(7, 0,
				environment.getEnvProps("REGIONAL", "k.s2RD") * this.rMassMol.getEntry(5, 0));
		/*
		 * this.rMassMolRemoval.setEntry(36, 0,
		 * environment.getEnvProps("REGIONAL","k.s2RS")*this.rMassMol.getEntry(36, 0) );
		 * this.rMassMolRemoval.setEntry(37, 0,
		 * environment.getEnvProps("REGIONAL","k.s2RA")*this.rMassMol.getEntry(37, 0) );
		 * this.rMassMolRemoval.setEntry(38, 0,
		 * environment.getEnvProps("REGIONAL","k.s2RP")*this.rMassMol.getEntry(38, 0) );
		 */
		this.rMassMolRemoval.setEntry(8, 0,
				environment.getEnvProps("REGIONAL", "k.s3RD") * this.rMassMol.getEntry(6, 0));
		/*
		 * this.rMassMolRemoval.setEntry(40, 0,
		 * environment.getEnvProps("REGIONAL","k.s3RS")*this.rMassMol.getEntry(40, 0) );
		 * this.rMassMolRemoval.setEntry(41, 0,
		 * environment.getEnvProps("REGIONAL","k.s3RA")*this.rMassMol.getEntry(41, 0) );
		 * this.rMassMolRemoval.setEntry(42, 0,
		 * environment.getEnvProps("REGIONAL","k.s3RP")*this.rMassMol.getEntry(42, 0) );
		 */

		this.rMassMolRemoval.setEntry(9, 0,
				environment.getEnvProps("CONTINENTAL", "k.aC") * this.rMassMol.getEntry(7, 0));
		/*
		 * this.rMassMolRemoval.setEntry(44, 0,
		 * environment.getEnvProps("CONTINENTAL","k.aCS")*this.rMassMol.getEntry(44, 0)
		 * ); this.rMassMolRemoval.setEntry(45, 0,
		 * environment.getEnvProps("CONTINENTAL","k.aCA")*this.rMassMol.getEntry(45, 0)
		 * ); this.rMassMolRemoval.setEntry(46, 0,
		 * environment.getEnvProps("CONTINENTAL","k.aCP")*this.rMassMol.getEntry(46, 0)
		 * ); this.rMassMolRemoval.setEntry(50, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w0C")*this.rMassMol.getEntry(50, 0)
		 * ); this.rMassMolRemoval.setEntry(51, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w0CS")*this.rMassMol.getEntry(51, 0)
		 * ); this.rMassMolRemoval.setEntry(52, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w0CA")*this.rMassMol.getEntry(52, 0)
		 * ); this.rMassMolRemoval.setEntry(53, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w0CP")*this.rMassMol.getEntry(53, 0)
		 * ); this.rMassMolRemoval.setEntry(54, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w1C")*this.rMassMol.getEntry(54, 0)
		 * ); this.rMassMolRemoval.setEntry(55, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w1CS")*this.rMassMol.getEntry(55, 0)
		 * ); this.rMassMolRemoval.setEntry(56, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w1CA")*this.rMassMol.getEntry(56, 0)
		 * ); this.rMassMolRemoval.setEntry(57, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w1CP")*this.rMassMol.getEntry(57, 0)
		 * ); this.rMassMolRemoval.setEntry(58, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w2C")*this.rMassMol.getEntry(58, 0)
		 * ); this.rMassMolRemoval.setEntry(59, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w2CS")*this.rMassMol.getEntry(59, 0)
		 * ); this.rMassMolRemoval.setEntry(60, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w2CA")*this.rMassMol.getEntry(60, 0)
		 * ); this.rMassMolRemoval.setEntry(61, 0,
		 * environment.getEnvProps("CONTINENTAL","k.w2CP")*this.rMassMol.getEntry(61, 0)
		 * ); this.rMassMolRemoval.setEntry(62, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd0CD")*this.rMassMol.getEntry(62,
		 * 0) ); this.rMassMolRemoval.setEntry(63, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd0CS")*this.rMassMol.getEntry(63,
		 * 0) ); this.rMassMolRemoval.setEntry(64, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd0CA")*this.rMassMol.getEntry(64,
		 * 0) ); this.rMassMolRemoval.setEntry(65, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd0CP")*this.rMassMol.getEntry(65,
		 * 0) ); this.rMassMolRemoval.setEntry(66, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd1C")*this.rMassMol.getEntry(66, 0)
		 * ); this.rMassMolRemoval.setEntry(67, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd1CS")*this.rMassMol.getEntry(67,
		 * 0) ); this.rMassMolRemoval.setEntry(68, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd1CA")*this.rMassMol.getEntry(68,
		 * 0) ); this.rMassMolRemoval.setEntry(69, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd1CP")*this.rMassMol.getEntry(69,
		 * 0) ); this.rMassMolRemoval.setEntry(70, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd2C")*this.rMassMol.getEntry(70, 0)
		 * ); this.rMassMolRemoval.setEntry(71, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd2CS")*this.rMassMol.getEntry(71,
		 * 0) ); this.rMassMolRemoval.setEntry(72, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd2CA")*this.rMassMol.getEntry(72,
		 * 0) ); this.rMassMolRemoval.setEntry(73, 0,
		 * environment.getEnvProps("CONTINENTAL","k.sd2CP")*this.rMassMol.getEntry(73,
		 * 0) ); this.rMassMolRemoval.setEntry(74, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s1C")*this.rMassMol.getEntry(74, 0)
		 * ); this.rMassMolRemoval.setEntry(75, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s1CS")*this.rMassMol.getEntry(75, 0)
		 * ); this.rMassMolRemoval.setEntry(76, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s1CA")*this.rMassMol.getEntry(76, 0)
		 * ); this.rMassMolRemoval.setEntry(77, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s1CP")*this.rMassMol.getEntry(77, 0)
		 * ); this.rMassMolRemoval.setEntry(78, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s2C") *this.rMassMol.getEntry(78, 0)
		 * ); this.rMassMolRemoval.setEntry(79, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s2CS")*this.rMassMol.getEntry(79, 0)
		 * ); this.rMassMolRemoval.setEntry(80, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s2CA")*this.rMassMol.getEntry(80, 0)
		 * ); this.rMassMolRemoval.setEntry(81, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s2CP")*this.rMassMol.getEntry(81, 0)
		 * ); this.rMassMolRemoval.setEntry(82, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3C")*this.rMassMol.getEntry(82, 0)
		 * ); this.rMassMolRemoval.setEntry(83, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3CS")*this.rMassMol.getEntry(83, 0)
		 * ); this.rMassMolRemoval.setEntry(84, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3CA")*this.rMassMol.getEntry(84, 0)
		 * ); this.rMassMolRemoval.setEntry(85, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3CP")*this.rMassMol.getEntry(85, 0)
		 * ); this.rMassMolRemoval.setEntry(82, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3C")*this.rMassMol.getEntry(82, 0)
		 * ); this.rMassMolRemoval.setEntry(83, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3CS")*this.rMassMol.getEntry(83, 0)
		 * ); this.rMassMolRemoval.setEntry(84, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3CA")*this.rMassMol.getEntry(84, 0)
		 * ); this.rMassMolRemoval.setEntry(85, 0,
		 * environment.getEnvProps("CONTINENTAL","k.s3CP")*this.rMassMol.getEntry(85, 0)
		 * );
		 * 
		 * this.rMassMolRemoval.setEntry(86, 0,
		 * environment.getEnvProps("MODERATE","k.aM")*this.rMassMol.getEntry(86, 0) );
		 * this.rMassMolRemoval.setEntry(87, 0,
		 * environment.getEnvProps("MODERATE","k.aMS")*this.rMassMol.getEntry(87, 0) );
		 * this.rMassMolRemoval.setEntry(88, 0,
		 * environment.getEnvProps("MODERATE","k.aMA")*this.rMassMol.getEntry(88, 0) );
		 * this.rMassMolRemoval.setEntry(89, 0,
		 * environment.getEnvProps("MODERATE","k.aMP")*this.rMassMol.getEntry(89, 0) );
		 * this.rMassMolRemoval.setEntry(93, 0,
		 * environment.getEnvProps("MODERATE","k.w2MD")*this.rMassMol.getEntry(93, 0) );
		 * this.rMassMolRemoval.setEntry(94, 0,
		 * environment.getEnvProps("MODERATE","k.w2MS")*this.rMassMol.getEntry(94, 0) );
		 * this.rMassMolRemoval.setEntry(95, 0,
		 * environment.getEnvProps("MODERATE","k.w2MA")*this.rMassMol.getEntry(95, 0) );
		 * this.rMassMolRemoval.setEntry(96, 0,
		 * environment.getEnvProps("MODERATE","k.w2MP")*this.rMassMol.getEntry(96, 0) );
		 * this.rMassMolRemoval.setEntry(97, 0,
		 * environment.getEnvProps("MODERATE","k.w3MD")*this.rMassMol.getEntry(97, 0) );
		 * this.rMassMolRemoval.setEntry(98, 0,
		 * environment.getEnvProps("MODERATE","k.w3MS")*this.rMassMol.getEntry(98, 0) );
		 * this.rMassMolRemoval.setEntry(99, 0,
		 * environment.getEnvProps("MODERATE","k.w3MA")*this.rMassMol.getEntry(99, 0) );
		 * this.rMassMolRemoval.setEntry(100, 0,
		 * environment.getEnvProps("MODERATE","k.w3MP")*this.rMassMol.getEntry(100, 0)
		 * ); this.rMassMolRemoval.setEntry(101, 0,
		 * environment.getEnvProps("MODERATE","k.sdMD")*this.rMassMol.getEntry(101, 0)
		 * ); this.rMassMolRemoval.setEntry(102, 0,
		 * environment.getEnvProps("MODERATE","k.sdMS")*this.rMassMol.getEntry(102, 0)
		 * ); this.rMassMolRemoval.setEntry(103, 0,
		 * environment.getEnvProps("MODERATE","k.sdMA")*this.rMassMol.getEntry(103, 0)
		 * ); this.rMassMolRemoval.setEntry(104, 0,
		 * environment.getEnvProps("MODERATE","k.sdMP")*this.rMassMol.getEntry(104, 0)
		 * ); this.rMassMolRemoval.setEntry(105, 0,
		 * environment.getEnvProps("MODERATE","k.sMD")*this.rMassMol.getEntry(105, 0) );
		 * this.rMassMolRemoval.setEntry(106, 0,
		 * environment.getEnvProps("MODERATE","k.sMS")*this.rMassMol.getEntry(106, 0) );
		 * this.rMassMolRemoval.setEntry(107, 0,
		 * environment.getEnvProps("MODERATE","k.sMA")*this.rMassMol.getEntry(107, 0) );
		 * this.rMassMolRemoval.setEntry(108, 0,
		 * environment.getEnvProps("MODERATE","k.sMP")*this.rMassMol.getEntry(108, 0) );
		 * 
		 * this.rMassMolRemoval.setEntry(109, 0,
		 * environment.getEnvProps("ARCTIC","k.aA")*this.rMassMol.getEntry(109, 0) );
		 * this.rMassMolRemoval.setEntry(110, 0,
		 * environment.getEnvProps("ARCTIC","k.aAS")*this.rMassMol.getEntry(110, 0) );
		 * this.rMassMolRemoval.setEntry(111, 0,
		 * environment.getEnvProps("ARCTIC","k.aAA")*this.rMassMol.getEntry(111, 0) );
		 * this.rMassMolRemoval.setEntry(112, 0,
		 * environment.getEnvProps("ARCTIC","k.aAP")*this.rMassMol.getEntry(112, 0) );
		 * this.rMassMolRemoval.setEntry(116, 0,
		 * environment.getEnvProps("ARCTIC","k.w2AD")*this.rMassMol.getEntry(116, 0) );
		 * this.rMassMolRemoval.setEntry(117, 0,
		 * environment.getEnvProps("ARCTIC","k.w2AS")*this.rMassMol.getEntry(117, 0) );
		 * this.rMassMolRemoval.setEntry(118, 0,
		 * environment.getEnvProps("ARCTIC","k.w2AA")*this.rMassMol.getEntry(118, 0) );
		 * this.rMassMolRemoval.setEntry(119, 0,
		 * environment.getEnvProps("ARCTIC","k.w2AP")*this.rMassMol.getEntry(119, 0) );
		 * this.rMassMolRemoval.setEntry(120, 0,
		 * environment.getEnvProps("ARCTIC","k.w3AD")*this.rMassMol.getEntry(120, 0) );
		 * this.rMassMolRemoval.setEntry(121, 0,
		 * environment.getEnvProps("ARCTIC","k.w3AS")*this.rMassMol.getEntry(121, 0) );
		 * this.rMassMolRemoval.setEntry(122, 0,
		 * environment.getEnvProps("ARCTIC","k.w3AA")*this.rMassMol.getEntry(122, 0) );
		 * this.rMassMolRemoval.setEntry(123, 0,
		 * environment.getEnvProps("ARCTIC","k.w3AP")*this.rMassMol.getEntry(123, 0) );
		 * this.rMassMolRemoval.setEntry(124, 0,
		 * environment.getEnvProps("ARCTIC","k.sdAD")*this.rMassMol.getEntry(124, 0) );
		 * this.rMassMolRemoval.setEntry(125, 0,
		 * environment.getEnvProps("ARCTIC","k.sdAS")*this.rMassMol.getEntry(125, 0) );
		 * this.rMassMolRemoval.setEntry(126, 0,
		 * environment.getEnvProps("ARCTIC","k.sdAA")*this.rMassMol.getEntry(126, 0) );
		 * this.rMassMolRemoval.setEntry(127, 0,
		 * environment.getEnvProps("ARCTIC","k.sdAP")*this.rMassMol.getEntry(127, 0) );
		 * this.rMassMolRemoval.setEntry(128, 0,
		 * environment.getEnvProps("ARCTIC","k.sAD")*this.rMassMol.getEntry(128, 0) );
		 * this.rMassMolRemoval.setEntry(129, 0,
		 * environment.getEnvProps("ARCTIC","k.sAS")*this.rMassMol.getEntry(129, 0) );
		 * this.rMassMolRemoval.setEntry(130, 0,
		 * environment.getEnvProps("ARCTIC","k.sAA")*this.rMassMol.getEntry(130, 0) );
		 * this.rMassMolRemoval.setEntry(131, 0,
		 * environment.getEnvProps("ARCTIC","k.sAP")*this.rMassMol.getEntry(131, 0) );
		 * 
		 * this.rMassMolRemoval.setEntry(132, 0,
		 * environment.getEnvProps("TROPICAL","k.aT")*this.rMassMol.getEntry(132, 0) );
		 * this.rMassMolRemoval.setEntry(133, 0,
		 * environment.getEnvProps("TROPICAL","k.aTS")*this.rMassMol.getEntry(133, 0) );
		 * this.rMassMolRemoval.setEntry(134, 0,
		 * environment.getEnvProps("TROPICAL","k.aTA")*this.rMassMol.getEntry(134, 0) );
		 * this.rMassMolRemoval.setEntry(135, 0,
		 * environment.getEnvProps("TROPICAL","k.aTP")*this.rMassMol.getEntry(135, 0) );
		 * this.rMassMolRemoval.setEntry(139, 0,
		 * environment.getEnvProps("TROPICAL","k.w2TD")*this.rMassMol.getEntry(139, 0)
		 * ); this.rMassMolRemoval.setEntry(140, 0,
		 * environment.getEnvProps("TROPICAL","k.w2TS")*this.rMassMol.getEntry(140, 0)
		 * ); this.rMassMolRemoval.setEntry(141, 0,
		 * environment.getEnvProps("TROPICAL","k.w2TA")*this.rMassMol.getEntry(141, 0)
		 * ); this.rMassMolRemoval.setEntry(142, 0,
		 * environment.getEnvProps("TROPICAL","k.w2TP")*this.rMassMol.getEntry(142, 0)
		 * ); this.rMassMolRemoval.setEntry(143, 0,
		 * environment.getEnvProps("TROPICAL","k.w3TD")*this.rMassMol.getEntry(143, 0)
		 * ); this.rMassMolRemoval.setEntry(144, 0,
		 * environment.getEnvProps("TROPICAL","k.w3TS")*this.rMassMol.getEntry(144, 0)
		 * ); this.rMassMolRemoval.setEntry(145, 0,
		 * environment.getEnvProps("TROPICAL","k.w3TA")*this.rMassMol.getEntry(145, 0)
		 * ); this.rMassMolRemoval.setEntry(146, 0,
		 * environment.getEnvProps("TROPICAL","k.w3TP")*this.rMassMol.getEntry(146, 0)
		 * ); this.rMassMolRemoval.setEntry(147, 0,
		 * environment.getEnvProps("TROPICAL","k.sdTD")*this.rMassMol.getEntry(147, 0)
		 * ); this.rMassMolRemoval.setEntry(148, 0,
		 * environment.getEnvProps("TROPICAL","k.sdTS")*this.rMassMol.getEntry(148, 0)
		 * ); this.rMassMolRemoval.setEntry(149, 0,
		 * environment.getEnvProps("TROPICAL","k.sdTA")*this.rMassMol.getEntry(149, 0)
		 * ); this.rMassMolRemoval.setEntry(150, 0,
		 * environment.getEnvProps("TROPICAL","k.sdTP")*this.rMassMol.getEntry(150, 0)
		 * ); this.rMassMolRemoval.setEntry(151, 0,
		 * environment.getEnvProps("TROPICAL","k.sTD")*this.rMassMol.getEntry(151, 0) );
		 * this.rMassMolRemoval.setEntry(152, 0,
		 * environment.getEnvProps("TROPICAL","k.sTS")*this.rMassMol.getEntry(152, 0) );
		 * this.rMassMolRemoval.setEntry(153, 0,
		 * environment.getEnvProps("TROPICAL","k.sTA")*this.rMassMol.getEntry(153, 0) );
		 * this.rMassMolRemoval.setEntry(154, 0,
		 * environment.getEnvProps("TROPICAL","k.sTP")*this.rMassMol.getEntry(154, 0) );
		 * 
		 * /* int iCount = 0; for (double val:this.rMassMol.getColumn(0) )
		 * System.out.println( iCount++ + " " + val );
		 */
	}

	public double getMassMol(int i) {
		return this.rMassMol.getEntry(i, 0);
	}

	public double getMassKg(int i) {
		return this.rMassKg.getEntry(i, 0);
	}

	public double getConcentration(int i) {
		return this.rConc.getEntry(i, 0);
	}

	public double getFugacity(int i) {
		return this.rFugacity.getEntry(i, 0);
	}

	void createMassMatrices() {
		for (int i = 0; i < this.rEmissions.getRowDimension(); i++)
			this.rEmissions.setEntry(i, 0, -this.rEmissions.getEntry(i, 0));

		/*
		 * RealMatrix rInv = MatrixUtils.inverse(this.rCoeffs);
		 * 
		 * for ( int i = 0; i< rInv.getRowDimension(); i++) { for ( int j = 0; j<
		 * rInv.getColumnDimension(); j++ ) if ( Math.abs( rInv.getEntry( i , j ) ) <
		 * 1.e-5) rInv.setEntry(i, j, 0.0); }
		 * 
		 * try { FileWriter myWriter = new FileWriter("InverseCoeffMatrix2"); for ( int
		 * i = 0; i< rInv.getRowDimension(); i++) { for ( int j = 0; j<
		 * rInv.getColumnDimension(); j++ ) myWriter.write( Double.toString(
		 * rInv.getEntry( i , j ) ) + "\t" );
		 * 
		 * myWriter.write( "\n" ); } myWriter.close();
		 * 
		 * } catch (IOException e) { System.out.println("An error occurred.");
		 * e.printStackTrace(); }
		 * 
		 * 
		 * this.rMassMol = rInv.multiply( this.rEmissions ) ;
		 */

		// It seems that LU has also a problem. It through matrix is singular while the
		// direct approach returns the matrix.
		// We check if the matrix is reversible. If not we use direct approach.
		// Update: Entering the threshold seems to fix the issue
		LUDecomposition ludecompose = new LUDecomposition(this.rCoeffs, 1e-60);
		DecompositionSolver lusolver = ludecompose.getSolver();
//		if ( lusolver.isNonSingular() )
		this.rMassMol = lusolver.solve(this.rEmissions);
//		else {			
//			System.out.println( "Warning: The mass matrix is singular. Trying direct method ...");
//			this.rMassMol = ( MatrixUtils.inverse(this.rCoeffs) ).multiply( this.rEmissions ) ;	
//		}

		for (int i = 0; i < this.rEmissions.getRowDimension(); i++)
			this.rEmissions.setEntry(i, 0, -this.rEmissions.getEntry(i, 0));

		int iCount = 0;
		for (double val : this.rMassMol.getColumn(0)) {
			this.rMassKg.setEntry(iCount, 0, val * input.getSubstancesData("Molweight") / 1000);
			this.rConc.setEntry(iCount, 0, val / this.rVolumes.getEntry(iCount++, 0));
			this.dTotalMassMol = this.dTotalMassMol + val;
		}

		iCount = 0;
		for (double val : this.rMassMol.getColumn(0))
			this.rMassDistrubution.setEntry(iCount, 0, val / this.dTotalMassMol);
		
		this.rFugacity.setEntry(0, 0,
				this.rConc.getEntry(0, 0) * 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(1, 0, this.rConc.getEntry(1, 0) * environment.getEnvProps("REGIONAL", "KawD.R") * 8.314
				* input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(2, 0, this.rConc.getEntry(2, 0) * environment.getEnvProps("REGIONAL", "KawD.R")
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(3, 0, this.rConc.getEntry(3, 0) * environment.getEnvProps("REGIONAL", "KawD.R")
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(4, 0, this.rConc.getEntry(4, 0)
				* (environment.getEnvProps("REGIONAL", "KawD.R") / environment.getEnvProps("REGIONAL", "Ksdw1.R"))
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(5, 0, this.rConc.getEntry(5, 0)
				* (environment.getEnvProps("REGIONAL", "KawD.R") / environment.getEnvProps("REGIONAL", "Ksdw2.R"))
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(6, 0, this.rConc.getEntry(6, 0)
				* (environment.getEnvProps("REGIONAL", "KawD.R") / environment.getEnvProps("REGIONAL", "Ks1w.R"))
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(7, 0, this.rConc.getEntry(7, 0)
				* (environment.getEnvProps("REGIONAL", "KawD.R") / environment.getEnvProps("REGIONAL", "Ks2w.R"))
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry(8, 0, this.rConc.getEntry(8, 0)
				* (environment.getEnvProps("REGIONAL", "KawD.R") / environment.getEnvProps("REGIONAL", "Ks3w.R"))
				* 8.314 * input.getLandscapeSettings("REGIONAL", "TEMP.R"));

		this.rFugacity.setEntry(9, 0,
				this.rConc.getEntry(9, 0) * 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(10, 0, this.rConc.getEntry(10, 0) * environment.getEnvProps("CONTINENTAL", "Kaw.C")
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(11, 0, this.rConc.getEntry(11, 0) * environment.getEnvProps("CONTINENTAL", "Kaw.C")
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(12, 0, this.rConc.getEntry(12, 0) * environment.getEnvProps("CONTINENTAL", "Kaw.C")
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(13, 0, this.rConc.getEntry(13, 0)
				* (environment.getEnvProps("CONTINENTAL", "Kaw.C") / environment.getEnvProps("CONTINENTAL", "Ksdw1.C"))
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(14, 0, this.rConc.getEntry(14, 0)
				* (environment.getEnvProps("CONTINENTAL", "Kaw.C") / environment.getEnvProps("CONTINENTAL", "Ksdw2.C"))
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(15, 0, this.rConc.getEntry(15, 0)
				* (environment.getEnvProps("CONTINENTAL", "Kaw.C") / environment.getEnvProps("CONTINENTAL", "Ks1w.C"))
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(16, 0, this.rConc.getEntry(16, 0)
				* (environment.getEnvProps("CONTINENTAL", "Kaw.C") / environment.getEnvProps("CONTINENTAL", "Ks2w.C"))
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));
		this.rFugacity.setEntry(17, 0, this.rConc.getEntry(17, 0)
				* (environment.getEnvProps("CONTINENTAL", "Kaw.C") / environment.getEnvProps("CONTINENTAL", "Ks3w.C"))
				* 8.314 * input.getLandscapeSettings("CONTINENTAL", "TEMP.C"));

		this.rFugacity.setEntry(18, 0,
				this.rConc.getEntry(18, 0) * 8.314 * environment.getEnvProps("MODERATE", "TEMP.M"));
		this.rFugacity.setEntry(19, 0, this.rConc.getEntry(19, 0) * environment.getEnvProps("MODERATE", "Kaw.M") * 8.314
				* environment.getEnvProps("MODERATE", "TEMP.M"));
		this.rFugacity.setEntry(20, 0, this.rConc.getEntry(20, 0) * environment.getEnvProps("MODERATE", "Kaw.M") * 8.314
				* environment.getEnvProps("MODERATE", "TEMP.M"));
		this.rFugacity.setEntry(21, 0,
				this.rConc.getEntry(21, 0)
						* (environment.getEnvProps("MODERATE", "Kaw.M") / environment.getEnvProps("MODERATE", "Ksdw.M"))
						* 8.314 * environment.getEnvProps("MODERATE", "TEMP.M"));
		this.rFugacity.setEntry(22, 0,
				this.rConc.getEntry(22, 0)
						* (environment.getEnvProps("MODERATE", "Kaw.M") / environment.getEnvProps("MODERATE", "Ksw.M"))
						* 8.314 * environment.getEnvProps("MODERATE", "TEMP.M"));

		this.rFugacity.setEntry(23, 0,
				this.rConc.getEntry(23, 0) * 8.314 * environment.getEnvProps("ARCTIC", "TEMP.A"));
		this.rFugacity.setEntry(24, 0, this.rConc.getEntry(24, 0) * environment.getEnvProps("ARCTIC", "Kaw.A") * 8.314
				* environment.getEnvProps("ARCTIC", "TEMP.A"));
		this.rFugacity.setEntry(25, 0, this.rConc.getEntry(25, 0) * environment.getEnvProps("ARCTIC", "Kaw.A") * 8.314
				* environment.getEnvProps("ARCTIC", "TEMP.A"));
		this.rFugacity.setEntry(26, 0,
				this.rConc.getEntry(26, 0)
						* (environment.getEnvProps("ARCTIC", "Kaw.A") / environment.getEnvProps("ARCTIC", "Ksdw.A"))
						* 8.314 * environment.getEnvProps("ARCTIC", "TEMP.A"));
		this.rFugacity.setEntry(27, 0,
				this.rConc.getEntry(27, 0)
						* (environment.getEnvProps("ARCTIC", "Kaw.A") / environment.getEnvProps("ARCTIC", "Ksw.A"))
						* 8.314 * environment.getEnvProps("ARCTIC", "TEMP.A"));

		this.rFugacity.setEntry(28, 0,
				this.rConc.getEntry(28, 0) * 8.314 * environment.getEnvProps("TROPICAL", "TEMP.T"));
		this.rFugacity.setEntry(29, 0, this.rConc.getEntry(29, 0) * environment.getEnvProps("TROPICAL", "Kaw.T")
				* 8.314 * environment.getEnvProps("TROPICAL", "TEMP.T"));
		this.rFugacity.setEntry(30, 0, this.rConc.getEntry(30, 0) * environment.getEnvProps("TROPICAL", "Kaw.T")
				* 8.314 * environment.getEnvProps("TROPICAL", "TEMP.T"));
		this.rFugacity.setEntry(31, 0,
				this.rConc.getEntry(31, 0)
						* (environment.getEnvProps("TROPICAL", "Kaw.T") / environment.getEnvProps("TROPICAL", "Ksdw.T"))
						* 8.314 * environment.getEnvProps("TROPICAL", "TEMP.T"));
		this.rFugacity.setEntry(32, 0,
				this.rConc.getEntry(32, 0)
						* (environment.getEnvProps("TROPICAL", "Kaw.T") / environment.getEnvProps("TROPICAL", "Ksw.T"))
						* 8.314 * environment.getEnvProps("TROPICAL", "TEMP.T"));
	}

	void createCoeffMatrix() {
		this.coeffs[0][1] = environment.getEnvProps("REGIONAL", "k.w0RD.aRG"); // this.regional.getK_w0RD_aRG();
		this.coeffs[0][2] = environment.getEnvProps("REGIONAL", "k.w1RD.aRG"); // this.regional.getK_w1RD_aRG();
		this.coeffs[0][3] = environment.getEnvProps("REGIONAL", "k.w2RD.aRG"); // this.regional.getK_w2RD_aRG();
		this.coeffs[0][6] = environment.getEnvProps("REGIONAL", "k.s1RD.aRG");// this.regional.getK_s1RD_aRG();
		this.coeffs[0][7] = environment.getEnvProps("REGIONAL", "k.s2RD.aRG"); // this.regional.getK_s2RD_aRG();
		this.coeffs[0][8] = environment.getEnvProps("REGIONAL", "k.s3RD.aRG"); // this.regional.getK_s3RD_aRG();
		this.coeffs[0][9] = environment.getEnvProps("CONTINENTAL", "k.aC.aR"); // this.continental.getK_aC_aR();

		this.coeffs[1][0] = environment.getEnvProps("REGIONAL", "k.aRG.w0RD"); // this.regional.getK_aRG_w0RD();

		this.coeffs[2][0] = environment.getEnvProps("REGIONAL", "k.aRG.w1RD"); // this.regional.getK_aRG_w1RD();
		this.coeffs[2][1] = environment.getEnvProps("REGIONAL", "k.w0R.w1R"); // this.regional.getK_w0R_w1R();
		this.coeffs[2][4] = environment.getEnvProps("REGIONAL", "k.sd1RD.w1RD"); // this.regional.getK_sd1RD_w1RD();
		this.coeffs[2][6] = environment.getEnvProps("REGIONAL", "k.s1RD.w1RD"); // this.regional.getK_s1RD_w1RD();
		this.coeffs[2][7] = environment.getEnvProps("REGIONAL", "k.s2RD.w1RD"); // this.regional.getK_s2RD_w1RD();
		this.coeffs[2][8] = environment.getEnvProps("REGIONAL", "k.s3RD.w1RD"); // this.regional.getK_s3RD_w1RD();
		this.coeffs[2][11] = environment.getEnvProps("CONTINENTAL", "k.w1C.w1R"); // this.continental.getK_w1C_w1R();

		this.coeffs[3][0] = environment.getEnvProps("REGIONAL", "k.aRG.w2RD"); // this.regional.getK_aRG_w2RD();
		this.coeffs[3][1] = environment.getEnvProps("REGIONAL", "k.w0R.w2R"); // this.regional.getK_w0R_w2R();
		this.coeffs[3][2] = environment.getEnvProps("REGIONAL", "k.w1R.w2R"); // this.regional.getK_w1R_w2R();
		this.coeffs[3][5] = environment.getEnvProps("REGIONAL", "k.sd2RD.w2RD"); // this.regional.getK_sd2RD_w2RD();
		this.coeffs[3][12] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2R"); // this.continental.getK_w2C_w2R();

		this.coeffs[4][2] = environment.getEnvProps("REGIONAL", "k.w1RD.sd1RD"); // this.regional.getK_w1RD_sd1RD();
		this.coeffs[5][3] = environment.getEnvProps("REGIONAL", "k.w2RD.sd2RD"); // this.regional.getK_w2RD_sd2RD();

		this.coeffs[6][0] = environment.getEnvProps("REGIONAL", "k.aRG.s1RD"); // this.regional.getK_aRG_s1RD();
		this.coeffs[7][0] = environment.getEnvProps("REGIONAL", "k.aRG.s2RD"); // this.regional.getK_aRG_s2RD();
		this.coeffs[8][0] = environment.getEnvProps("REGIONAL", "k.aRG.s3RD"); // this.regional.getK_aRG_s3RD();

		this.coeffs[9][0] = environment.getEnvProps("REGIONAL", "k.aR.aC"); // this.regional.getK_aR_aC();
		this.coeffs[9][10] = environment.getEnvProps("CONTINENTAL", "k.w0CD.aCG"); // this.formulas.getK_w0C_aC();
		this.coeffs[9][11] = environment.getEnvProps("CONTINENTAL", "k.w1CD.aCG"); // this.formulas.getK_w1C_aC();
		this.coeffs[9][12] = environment.getEnvProps("CONTINENTAL", "k.w2CD.aCG"); // this.formulas.getK_w2C_aC();
		this.coeffs[9][15] = environment.getEnvProps("CONTINENTAL", "k.s1CD.aCG"); // this.formulas.getK_s1C_aC();
		this.coeffs[9][16] = environment.getEnvProps("CONTINENTAL", "k.s2CD.aCG"); // this.formulas.getK_s2C_aC();
		this.coeffs[9][17] = environment.getEnvProps("CONTINENTAL", "k.s3CD.aCG"); // this.formulas.getK_s3C_aC();
		this.coeffs[9][18] = environment.getEnvProps("MODERATE", "k.aM.aC");// this.formulas.getK_aM_aC();

		this.coeffs[10][9] = environment.getEnvProps("CONTINENTAL", "k.aCG.w0CD"); // this.formulas.getK_aC_w0C();

		this.coeffs[11][9] = environment.getEnvProps("CONTINENTAL", "k.aCG.w1CD"); // this.formulas.getK_aC_w0C();
		this.coeffs[11][10] = environment.getEnvProps("CONTINENTAL", "k.w0C.w1C"); // this.formulas.getK_w0C_w1C();
		this.coeffs[11][13] = environment.getEnvProps("CONTINENTAL", "k.sd1CD.w1CD"); // this.formulas.getK_sd1C_w1C();
		this.coeffs[11][15] = environment.getEnvProps("CONTINENTAL", "k.s1C.w1C"); // this.formulas.getK_s1C_w1C();
		this.coeffs[11][16] = environment.getEnvProps("CONTINENTAL", "k.s2C.w1C"); // this.formulas.getK_s2C_w1C();
		this.coeffs[11][17] = environment.getEnvProps("CONTINENTAL", "k.s3C.w1C"); // this.formulas.getK_s3C_w1C();

		this.coeffs[12][3] = environment.getEnvProps("REGIONAL", "k.w2R.w2C"); // this.regional.getK_w2R_w2C();
		this.coeffs[12][9] = environment.getEnvProps("CONTINENTAL", "k.aCG.w2CD"); // this.formulas.getK_aC_w2C();
		this.coeffs[12][10] = environment.getEnvProps("CONTINENTAL", "k.w0C.w2C"); // this.continental.getK_w0C_w2C();
		this.coeffs[12][11] = environment.getEnvProps("CONTINENTAL", "k.w1C.w2C"); // this.continental.getK_w1C_w2C();
		this.coeffs[12][14] = environment.getEnvProps("CONTINENTAL", "k.sd2CD.w2CD"); // this.formulas.getK_sd2C_w2C();
		this.coeffs[12][19] = environment.getEnvProps("MODERATE", "k.w2M.w2C"); // this.formulas.getK_w2M_w2C();

		this.coeffs[13][11] = environment.getEnvProps("CONTINENTAL", "k.w1CD.sd1CD"); // this.formulas.getK_w1C_sd1C();

		this.coeffs[14][12] = environment.getEnvProps("CONTINENTAL", "k.w2CD.sd2CD"); // this.formulas.getK_w2C_sd2C();

		this.coeffs[15][9] = environment.getEnvProps("CONTINENTAL", "k.aCG.s1CD"); // this.formulas.getK_aC_s1C();

		this.coeffs[16][9] = environment.getEnvProps("CONTINENTAL", "k.aCG.s2CD"); // this.formulas.getK_aC_s1C();

		this.coeffs[17][9] = environment.getEnvProps("CONTINENTAL", "k.aCG.s3CD"); // this.formulas.getK_aC_s1C();

		this.coeffs[18][9] = environment.getEnvProps("CONTINENTAL", "k.aC.aM"); // this.continental.getK_aC_aM();
		this.coeffs[18][9] = environment.getEnvProps("CONTINENTAL", "k.aC.aM"); // this.continental.getK_aC_aM();
		this.coeffs[18][19] = environment.getEnvProps("MODERATE", "k.w2MD.aMG"); // this.formulas.getK_w2M_aM();
		this.coeffs[18][22] = environment.getEnvProps("MODERATE", "k.sMD.aMG"); // this.formulas.getK_sM_aM();
		this.coeffs[18][23] = environment.getEnvProps("ARCTIC", "k.aA.aM"); // this.global.getK_aA_aM();
		this.coeffs[18][28] = environment.getEnvProps("TROPICAL", "k.aT.aM"); // this.global.getK_aT_aM();

		this.coeffs[19][12] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2M"); // this.continental.getK_w2C_w2M();
		this.coeffs[19][18] = environment.getEnvProps("MODERATE", "k.aMG.w2MD"); // this.global.getK_aM_w2M();
		this.coeffs[19][20] = environment.getEnvProps("MODERATE", "k.w3M.w2M"); // this.formulas.getK_w3M_w2M();
		this.coeffs[19][22] = environment.getEnvProps("MODERATE", "k.sMD.w2MD"); // this.formulas.getK_sM_w2M();
		this.coeffs[19][24] = environment.getEnvProps("ARCTIC", "k.w2A.w2M"); // this.formulas.getK_w2A_w2M();
		this.coeffs[19][29] = environment.getEnvProps("TROPICAL", "k.w2T.w2M"); // this.formulas.getK_w2T_w2M();

		this.coeffs[20][19] = environment.getEnvProps("MODERATE", "k.w2M.w3M"); // this.global.getK_w2M_w3M();
		this.coeffs[20][21] = environment.getEnvProps("MODERATE", "k.sdMD.w3MD"); // this.global.getK_sdM_w3M();
		this.coeffs[20][25] = environment.getEnvProps("ARCTIC", "k.w3A.w3M"); // this.global.getK_w3A_w3M();
		this.coeffs[20][30] = environment.getEnvProps("TROPICAL", "k.w3T.w3M"); // this.global.getK_w3T_w3M();

		this.coeffs[21][20] = environment.getEnvProps("MODERATE", "k.w3MD.sdMD"); // this.global.getK_w3M_sdM();

		this.coeffs[22][18] = environment.getEnvProps("MODERATE", "k.aMG.sMD"); // this.global.getK_aM_sM();

		this.coeffs[23][18] = environment.getEnvProps("MODERATE", "k.aM.aA"); // this.global.getK_aM_aA();
		this.coeffs[23][24] = environment.getEnvProps("ARCTIC", "k.w2AD.aAG"); // this.global.getK_w2A_aA();
		this.coeffs[23][27] = environment.getEnvProps("ARCTIC", "k.sAD.aAG"); // this.global.getK_sA_aA();

		this.coeffs[24][19] = environment.getEnvProps("MODERATE", "k.w2M.w2A"); // this.global.getK_w2M_w2A();
		this.coeffs[24][23] = environment.getEnvProps("ARCTIC", "k.aAG.w2AD"); // this.global.getK_aA_w2A();
		this.coeffs[24][25] = environment.getEnvProps("ARCTIC", "k.w3A.w2A"); // this.global.getK_w3A_w2A();
		this.coeffs[24][27] = environment.getEnvProps("ARCTIC", "k.sAD.w2AD"); // this.global.getK_sA_w2A();

		this.coeffs[25][20] = environment.getEnvProps("MODERATE", "k.w3M.w3A"); // this.global.getK_w3M_w3A();
		this.coeffs[25][24] = environment.getEnvProps("ARCTIC", "k.w2A.w3A"); // this.global.getK_w2A_w3A();
		this.coeffs[25][26] = environment.getEnvProps("ARCTIC", "k.sdAD.w3AD"); // this.global.getK_sdA_w3A();

		this.coeffs[26][25] = environment.getEnvProps("ARCTIC", "k.w3AD.sdAD"); // this.global.getK_w3A_sdA();

		this.coeffs[27][23] = environment.getEnvProps("ARCTIC", "k.aAG.sAD"); // this.global.getK_aA_sA();

		this.coeffs[28][18] = environment.getEnvProps("MODERATE", "k.aM.aT"); // this.global.getK_aM_aT();
		this.coeffs[28][29] = environment.getEnvProps("TROPICAL", "k.w2TD.aTG"); // this.global.getK_w2T_aT();
		this.coeffs[28][32] = environment.getEnvProps("TROPICAL", "k.sTD.aTG"); // this.global.getK_sT_aT();

		this.coeffs[29][19] = environment.getEnvProps("MODERATE", "k.w2M.w2T"); // this.global.getK_aT_w2T();
		this.coeffs[29][28] = environment.getEnvProps("TROPICAL", "k.aTG.w2TD"); // this.global.getK_aT_w2T();
		this.coeffs[29][30] = environment.getEnvProps("TROPICAL", "k.w3T.w2T"); // this.global.getK_w3T_w2T();
		this.coeffs[29][32] = environment.getEnvProps("TROPICAL", "k.sTD.w2TD"); // this.global.getK_sT_w2T();

		this.coeffs[30][20] = environment.getEnvProps("MODERATE", "k.w3M.w3T"); // this.global.getK_w3M_w3T();
		this.coeffs[30][29] = environment.getEnvProps("TROPICAL", "k.w2T.w3T"); // this.global.getK_w2T_w3T();
		this.coeffs[30][31] = environment.getEnvProps("TROPICAL", "k.sdTD.w3TD"); // this.global.getK_sdT_w3T();

		this.coeffs[31][30] = environment.getEnvProps("TROPICAL", "k.w3TD.sdTD"); // this.global.getK_w3T_sdT();

		this.coeffs[32][28] = environment.getEnvProps("TROPICAL", "k.aTG.sTD"); // this.global.getK_aT_sT();

		/// Upper now!
		// Compute the diagonal
		for (int i = 0; i < coeffs.length; i++) {
			double dSum = 0.0;
			for (int j = 0; j < coeffs.length; j++) {
				if (i != j)
					dSum += coeffs[j][i];
			}

			if (i == 0)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.aRG") - dSum; // -this.regional.getK_aRG()-dSum;
			else if (i == 1)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.w0RD") - dSum; // -this.regional.getK_sd1RD()-dSum;
			else if (i == 2)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "KDEG.w1R") - dSum; // -this.regional.getK_w1RD()-dSum;
																								// //it is the same as
																								// k.w1RD
			else if (i == 3)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.w2RD") - dSum; // -this.regional.getK_w2RD()-dSum;
			else if (i == 4)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.sd1RD") - dSum; // -this.regional.getK_sd1RD()-dSum;
			else if (i == 5)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.sd2RD") - dSum; // -this.regional.getK_sd2RD()-dSum;
			else if (i == 6)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.s1RD") - dSum; // -this.regional.getK_sd1RD()-dSum;
			else if (i == 7)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.s2RD") - dSum; // -this.regional.getK_s2RD()-dSum;
			else if (i == 8)
				this.coeffs[i][i] = -environment.getEnvProps("REGIONAL", "k.s3RD") - dSum; // -this.regional.getK_s2RD()-dSum;
			else if (i == 9)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.aC") - dSum; // -this.continental.getK_aC()-dSum;
			else if (i == 10)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.w0C") - dSum; // -this.continental.getK_w0C()-dSum;
			else if (i == 11)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.w1C") - dSum; // -this.continental.getK_w1C()-dSum;
			else if (i == 12)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.w2C") - dSum; // -this.continental.getK_w2C()-dSum;
			else if (i == 13)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.sd1C") - dSum; // -this.continental.getK_sd1C()-dSum;
			else if (i == 14)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.sd2C") - dSum; // -this.continental.getK_sd2C()-dSum;
			else if (i == 15)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.s1C") - dSum; // -this.continental.getK_s1C()-dSum;
			else if (i == 16)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.s2C") - dSum; // -this.continental.getK_s2C()-dSum;
			else if (i == 17)
				this.coeffs[i][i] = -environment.getEnvProps("CONTINENTAL", "k.s3C") - dSum; // -this.continental.getK_s3C()-dSum;
			else if (i == 18)
				this.coeffs[i][i] = -environment.getEnvProps("MODERATE", "k.aM") - dSum; // -this.global.getK_aM()-dSum;
			else if (i == 19)
				this.coeffs[i][i] = -environment.getEnvProps("MODERATE", "k.w2MD") - dSum; // -this.global.getK_w2M()-dSum;
			else if (i == 20)
				this.coeffs[i][i] = -environment.getEnvProps("MODERATE", "k.w3MD") - dSum; // -this.global.getK_w3M()-dSum;
			else if (i == 21)
				this.coeffs[i][i] = -environment.getEnvProps("MODERATE", "k.sdMD") - dSum; // -this.global.getK_sdM()-dSum;
			else if (i == 22)
				this.coeffs[i][i] = -environment.getEnvProps("MODERATE", "k.sMD") - dSum; // -this.global.getK_sM()-dSum;
			else if (i == 23)
				this.coeffs[i][i] = -environment.getEnvProps("ARCTIC", "k.aA") - dSum; // -this.global.getK_aA()-dSum;
			else if (i == 24)
				this.coeffs[i][i] = -environment.getEnvProps("ARCTIC", "k.w2AD") - dSum; // -this.global.getK_w2A()-dSum;
			else if (i == 25)
				this.coeffs[i][i] = -environment.getEnvProps("ARCTIC", "k.w3AD") - dSum; // -this.global.getK_w3A()-dSum;
			else if (i == 26)
				this.coeffs[i][i] = -environment.getEnvProps("ARCTIC", "k.sdAD") - dSum; // -this.global.getK_sdA()-dSum;
			else if (i == 27)
				this.coeffs[i][i] = -environment.getEnvProps("ARCTIC", "k.sAD") - dSum; // -this.global.getK_sA()-dSum;
			else if (i == 28)
				this.coeffs[i][i] = -environment.getEnvProps("TROPICAL", "k.aT") - dSum; // -this.global.getK_aT()-dSum;
			else if (i == 29)
				this.coeffs[i][i] = -environment.getEnvProps("TROPICAL", "k.w2TD") - dSum; // -this.global.getK_w2T()-dSum;
			else if (i == 30)
				this.coeffs[i][i] = -environment.getEnvProps("TROPICAL", "k.w3TD") - dSum; // -this.global.getK_w3T()-dSum;
			else if (i == 31)
				this.coeffs[i][i] = -environment.getEnvProps("TROPICAL", "k.sdTD") - dSum; // -this.global.getK_sdT()-dSum;
			else if (i == 32)
				this.coeffs[i][i] = -environment.getEnvProps("TROPICAL", "k.sTD") - dSum; // -this.global.getK_sT()-dSum;
		}

		// Create the real matrix
		this.rCoeffs = new Array2DRowRealMatrix(this.coeffs);

		/*
		 * try { FileWriter myWriter = new FileWriter("CoeffMatrix"); for ( int i = 0;
		 * i< this.rCoeffs.getRowDimension(); i++) { for ( int j = 0; j<
		 * this.rCoeffs.getColumnDimension(); j++ ) myWriter.write( Double.toString(
		 * this.rCoeffs.getEntry( i , j ) ) + "\t" ); myWriter.write( "\n" ); }
		 * myWriter.close();
		 * 
		 * } catch (IOException e) { System.out.println("An error occurred.");
		 * e.printStackTrace(); }
		 */

		/*
		 * int iCount = 0; for ( double val:this.coeffs[2]) { if ( val != 0 )
		 * System.out.println( iCount + " " + val );
		 * 
		 * iCount++; }
		 */
	}

	void createEmissions() {
		this.emissions[0] = input.getEmissionRates("E.aRS"); // this.input.getE_aRG();
		this.emissions[2] = input.getEmissionRates("E.w1RS"); // this.input.getE_w1RD();
		this.emissions[3] = input.getEmissionRates("E.w2RS"); // this.input.getE_w2RD();
		this.emissions[6] = input.getEmissionRates("E.s1RS"); // this.input.getE_s1RD();
		this.emissions[7] = input.getEmissionRates("E.s2RS"); // this.input.getE_s2RD();
		this.emissions[8] = input.getEmissionRates("E.s3RS"); // this.input.getE_s3RD();
		this.emissions[9] = input.getEmissionRates("E.aCS"); // this.input.getE_aCG();
		this.emissions[11] = input.getEmissionRates("E.w1CS"); // this.input.getE_w1CD();
		this.emissions[12] = input.getEmissionRates("E.w2CS"); // this.input.getE_w2CD();
		this.emissions[15] = input.getEmissionRates("E.s1CS"); // this.input.getE_s1CD();
		this.emissions[16] = input.getEmissionRates("E.s2CS"); // this.input.getE_s2CD();
		this.emissions[17] = input.getEmissionRates("E.s3CS"); // this.input.getE_s3CD();
		this.emissions[18] = input.getEmissionRates("E.aMS"); // this.input.getE_aMG();
		this.emissions[19] = input.getEmissionRates("E.w2MS"); // this.input.getE_w2MD();
		this.emissions[22] = input.getEmissionRates("E.sMS"); // this.input.getE_sMD();
		this.emissions[23] = input.getEmissionRates("E.aAS"); // this.input.getE_aAG();
		this.emissions[24] = input.getEmissionRates("E.w2AS"); // this.input.getE_w2AD();
		this.emissions[27] = input.getEmissionRates("E.sAS"); // this.input.getE_sAD();
		this.emissions[28] = input.getEmissionRates("E.aTS"); // this.input.getE_aTG();
		this.emissions[29] = input.getEmissionRates("E.w2TS"); // this.input.getE_w2TD();
		this.emissions[32] = input.getEmissionRates("E.sTS"); // this.input.getE_sTD();

		this.rEmissions = new Array2DRowRealMatrix(emissions);
	}

	void createVolumes() {
		// Create the volumes
		volumes[0] = environment.getEnvProps("REGIONAL", "VOLUME.aR"); // this.regional.getVOLUME_aR();
		volumes[1] = environment.getEnvProps("REGIONAL", "VOLUME.w0R"); // this.regional.getVOLUME_w0R();
		volumes[2] = environment.getEnvProps("REGIONAL", "VOLUME.w1R"); // this.regional.getVOLUME_w1R();
		volumes[3] = environment.getEnvProps("REGIONAL", "VOLUME.w2R"); // this.regional.getVOLUME_w2R();
		volumes[4] = environment.getEnvProps("REGIONAL", "VOLUME.sd1R"); // this.regional.getVOLUME_sd1R();
		volumes[5] = environment.getEnvProps("REGIONAL", "VOLUME.sd2R"); // this.regional.getVOLUME_sd2R();
		volumes[6] = environment.getEnvProps("REGIONAL", "VOLUME.s1R"); // this.regional.getVOLUME_s1R();
		volumes[7] = environment.getEnvProps("REGIONAL", "VOLUME.s2R"); // this.regional.getVOLUME_s2R();
		volumes[8] = environment.getEnvProps("REGIONAL", "VOLUME.s3R"); // this.regional.getVOLUME_s3R();
		volumes[9] = environment.getEnvProps("CONTINENTAL", "VOLUME.aC"); // this.continental.getVOLUME_aC();
		volumes[10] = environment.getEnvProps("CONTINENTAL", "VOLUME.w0C"); // this.continental.getVOLUME_w1C();
		volumes[11] = environment.getEnvProps("CONTINENTAL", "VOLUME.w1C"); // this.continental.getVOLUME_w1C();
		volumes[12] = environment.getEnvProps("CONTINENTAL", "VOLUME.w2C"); // this.continental.getVOLUME_w2C();
		volumes[13] = environment.getEnvProps("CONTINENTAL", "VOLUME.sd1C"); // this.continental.getVOLUME_w2C();
		volumes[14] = environment.getEnvProps("CONTINENTAL", "VOLUME.sd2C"); // this.continental.getVOLUME_w2C();
		volumes[15] = environment.getEnvProps("CONTINENTAL", "VOLUME.s1C"); // this.continental.getVOLUME_s1C();
		volumes[16] = environment.getEnvProps("CONTINENTAL", "VOLUME.s2C"); // this.continental.getVOLUME_s2C();
		volumes[17] = environment.getEnvProps("CONTINENTAL", "VOLUME.s3C"); // this.continental.getVOLUME_s2C();
		volumes[18] = environment.getEnvProps("MODERATE", "VOLUME.aM"); // this.global.getVOLUME_aM();
		volumes[19] = environment.getEnvProps("MODERATE", "VOLUME.w2M"); // this.global.getVOLUME_w2M();
		volumes[20] = environment.getEnvProps("MODERATE", "VOLUME.w3M"); // this.global.getVOLUME_w2M();
		volumes[21] = environment.getEnvProps("MODERATE", "VOLUME.sdM"); // this.global.getVOLUME_sdM();
		volumes[22] = environment.getEnvProps("MODERATE", "VOLUME.sM"); // this.global.getVOLUME_sM();
		volumes[23] = environment.getEnvProps("ARCTIC", "VOLUME.aA"); // this.global.getVOLUME_aA();
		volumes[24] = environment.getEnvProps("ARCTIC", "VOLUME.w2A"); // this.global.getVOLUME_w2A();
		volumes[25] = environment.getEnvProps("ARCTIC", "VOLUME.w3A"); // this.global.getVOLUME_w2A();
		volumes[26] = environment.getEnvProps("ARCTIC", "VOLUME.sdA"); // this.global.getVOLUME_sdA();
		volumes[27] = environment.getEnvProps("ARCTIC", "VOLUME.sA"); // this.global.getVOLUME_sA();
		volumes[28] = environment.getEnvProps("TROPICAL", "VOLUME.aT"); // this.global.getVOLUME_sA();
		volumes[29] = environment.getEnvProps("TROPICAL", "VOLUME.w2T"); // this.global.getVOLUME_w2T();
		volumes[30] = environment.getEnvProps("TROPICAL", "VOLUME.w3T"); // this.global.getVOLUME_w3T();
		volumes[31] = environment.getEnvProps("TROPICAL", "VOLUME.sdT"); // this.global.getVOLUME_w3T();
		volumes[32] = environment.getEnvProps("TROPICAL", "VOLUME.sT"); // this.global.getVOLUME_sT();

		this.rVolumes = new Array2DRowRealMatrix(volumes);
	}

}
