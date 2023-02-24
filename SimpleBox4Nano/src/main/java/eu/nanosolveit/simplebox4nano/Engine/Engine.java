package eu.nanosolveit.simplebox4nano.Engine;

import org.apache.commons.math3.linear.MatrixUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import eu.nanosolveit.simplebox4nano.Scenario;
import lombok.Getter;
import lombok.Setter;

public class Engine {
	InputEngine input = null;
	RegionalEngine regional = null;
	Map<String, String > nano = null;
	Scenario scene = null;	
	RegionalEngine environment = null;
	
	
	//Matrix that holds the linear coefficients 
	double[][] coeffs;
	
	//Apache real matrix of the coeffs 
	//These are needed because of the decision to use apache to manipulate matrices. 
	//We should emit double[][] but the entries are too many - for now.	
	//If a double[][] does not exist is because by the time we added apache we had not given values to this matrix (or array);
	@Getter @Setter
	RealMatrix rCoeffs = null;

	//Mass flows matrix in the paper that holds the linear coefficients 
	@Getter @Setter
	RealMatrix rMassFlows;

	//Volume array
	double[] volumes;

	@Getter @Setter
	RealMatrix rVolumes = null;  

	//Emissions array
	double[] emissions;

	@Getter @Setter
	RealMatrix rEmissions = null; 

	//Concentration of the nanomaterial
	@Getter @Setter
	RealMatrix rConc = null;

	//Fugacity 
	@Getter @Setter
	RealMatrix rFugacity = null; 

	//Mass of the nanomaterial in mol
	@Getter @Setter
	RealMatrix rMassMol = null;		
	
	//Mass of the nanomaterial in Kg
	@Getter @Setter
	RealMatrix rMassKg =  null;

	//Mass distribution 
	@Getter @Setter
	RealMatrix rMassDistrubution =  null;

	//Mass removal 
	@Getter @Setter
	RealMatrix rMassMolRemoval =  null;

	double dTotalMassMol = 0.0;
	
	public Engine( InputEngine in, RegionalEngine envi, Map<String, String> nanomaterial, Scenario scenario ) 
	{
		environment = envi;
		input = in;
		nano = nanomaterial;
		scene = scenario;
		
		this.coeffs = new double[155][155];
		this.volumes = new double[155];
		this.emissions = new double[155];

		this.rMassMol = new Array2DRowRealMatrix( new double[155] );		
		this.rMassKg = new Array2DRowRealMatrix( new double[155] );		
		this.rConc = new Array2DRowRealMatrix( new double[155] );
		this.rFugacity = new Array2DRowRealMatrix( new double[155] );

		this.rMassFlows = new Array2DRowRealMatrix( 155, 155);
		this.rMassDistrubution = new Array2DRowRealMatrix(new double[155]);
		this.rMassMolRemoval = new Array2DRowRealMatrix(new double[155]);
	}

	public void  build()
	{
		for ( int i = 0; i< coeffs.length; i++ ) {
			
			this.volumes[ i ] = 0.0;
			this.emissions[ i ] = 0.0;
			
			for ( int j = 0;  j < coeffs[ i ].length; j++ ) {
				this.coeffs[ i ][ j ]  = 0.0;		
			}
		}

		this.createVolumes();
		this.createEmissions();
		this.createCoeffMatrix();
		this.createMassMatrices();
		this.createMassMolRemoval();
		this.createMassFlows();		
		
	}	
	
	void createMassFlows()
	{
		for ( int i = 0; i < this.rMassFlows.getRowDimension(); i++) {
			for ( int j = 0; j < this.rMassFlows.getColumnDimension(); j++) { 
				if ( i != j)
					this.rMassFlows.setEntry(i, j,  this.rCoeffs.getEntry(i, j)*this.rMassMol.getEntry(j, 0) );								
			}
		}
				
		for ( int i = 0; i < this.rMassFlows.getRowDimension(); i++) {
			double dSumCols = 0.0;
			for ( int j = 0; j < this.rMassFlows.getColumnDimension(); j++) {  
				if ( i != j)
					dSumCols += this.rMassFlows.getEntry(i, j);				
			}
			
			double dSumRows = 0.0;
			for ( int j = 0; j < this.rMassFlows.getColumnDimension(); j++) {  
				if ( i != j)
					dSumRows += this.rMassFlows.getEntry(j, i);
			}

			this.rMassFlows.setEntry(i, i, this.rEmissions.getEntry(i, 0) + dSumCols - dSumRows - this.rMassMolRemoval.getEntry(i, 0));
		}
		
/*		try {
		FileWriter myWriter = new FileWriter("MassFlowMatrix");
		for ( int i = 0; i< this.rMassFlows.getRowDimension(); i++) {
			for ( int j = 0; j< this.rMassFlows.getColumnDimension(); j++ ) 
				myWriter.write(  Double.toString( this.rMassFlows.getEntry( i , j ) ) + "\t" );

			myWriter.write( "\n" );			    		  
		}
		myWriter.close();

	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }*/
		
	}
	
	void createMassMolRemoval()
	{		
		this.rMassMolRemoval.setEntry(0, 0, environment.getEnvProps("REGIONAL","k.aRG")*this.rMassMol.getEntry(0, 0) );
		this.rMassMolRemoval.setEntry(1, 0, environment.getEnvProps("REGIONAL","k.aRS")*this.rMassMol.getEntry(1, 0) );
		this.rMassMolRemoval.setEntry(2, 0, environment.getEnvProps("REGIONAL","k.aRA")*this.rMassMol.getEntry(2, 0) );
		this.rMassMolRemoval.setEntry(3, 0, environment.getEnvProps("REGIONAL","k.aRP")*this.rMassMol.getEntry(3, 0) );
		this.rMassMolRemoval.setEntry(7, 0, environment.getEnvProps("REGIONAL","k.w0RD")*this.rMassMol.getEntry(7, 0) );
		this.rMassMolRemoval.setEntry(11, 0, environment.getEnvProps("REGIONAL","k.w1RD")*this.rMassMol.getEntry(11, 0) );
		this.rMassMolRemoval.setEntry(15, 0, environment.getEnvProps("REGIONAL","k.w2RD")*this.rMassMol.getEntry(15, 0) );
		this.rMassMolRemoval.setEntry(19, 0, environment.getEnvProps("REGIONAL","k.sd0RD")*this.rMassMol.getEntry(19, 0) );
		this.rMassMolRemoval.setEntry(20, 0, environment.getEnvProps("REGIONAL","k.sd0RS")*this.rMassMol.getEntry(20, 0) );
		this.rMassMolRemoval.setEntry(21, 0, environment.getEnvProps("REGIONAL","k.sd0RA")*this.rMassMol.getEntry(21, 0) );
		this.rMassMolRemoval.setEntry(22, 0, environment.getEnvProps("REGIONAL","k.sd0RP")*this.rMassMol.getEntry(22, 0) );
		this.rMassMolRemoval.setEntry(23, 0, environment.getEnvProps("REGIONAL","k.sd1RD")*this.rMassMol.getEntry(23, 0) );
		this.rMassMolRemoval.setEntry(24, 0, environment.getEnvProps("REGIONAL","k.sd1RS")*this.rMassMol.getEntry(24, 0) );
		this.rMassMolRemoval.setEntry(25, 0, environment.getEnvProps("REGIONAL","k.sd1RA")*this.rMassMol.getEntry(25, 0) );
		this.rMassMolRemoval.setEntry(26, 0, environment.getEnvProps("REGIONAL","k.sd1RP")*this.rMassMol.getEntry(26, 0) );
		this.rMassMolRemoval.setEntry(27, 0, environment.getEnvProps("REGIONAL","k.sd2RD")*this.rMassMol.getEntry(27, 0) );
		this.rMassMolRemoval.setEntry(28, 0, environment.getEnvProps("REGIONAL","k.sd2RS")*this.rMassMol.getEntry(28, 0) );
		this.rMassMolRemoval.setEntry(29, 0, environment.getEnvProps("REGIONAL","k.sd2RA")*this.rMassMol.getEntry(29, 0) );
		this.rMassMolRemoval.setEntry(30, 0, environment.getEnvProps("REGIONAL","k.sd2RP")*this.rMassMol.getEntry(30, 0) );
		this.rMassMolRemoval.setEntry(31, 0, environment.getEnvProps("REGIONAL","k.s1RD")*this.rMassMol.getEntry(31, 0) );
		this.rMassMolRemoval.setEntry(32, 0, environment.getEnvProps("REGIONAL","k.s1RS")*this.rMassMol.getEntry(32, 0) );
		this.rMassMolRemoval.setEntry(33, 0, environment.getEnvProps("REGIONAL","k.s1RA")*this.rMassMol.getEntry(33, 0) );
		this.rMassMolRemoval.setEntry(34, 0, environment.getEnvProps("REGIONAL","k.s1RP")*this.rMassMol.getEntry(34, 0) );
		this.rMassMolRemoval.setEntry(35, 0, environment.getEnvProps("REGIONAL","k.s2RD")*this.rMassMol.getEntry(35, 0) );
		this.rMassMolRemoval.setEntry(36, 0, environment.getEnvProps("REGIONAL","k.s2RS")*this.rMassMol.getEntry(36, 0) );
		this.rMassMolRemoval.setEntry(37, 0, environment.getEnvProps("REGIONAL","k.s2RA")*this.rMassMol.getEntry(37, 0) );
		this.rMassMolRemoval.setEntry(38, 0, environment.getEnvProps("REGIONAL","k.s2RP")*this.rMassMol.getEntry(38, 0) );
		this.rMassMolRemoval.setEntry(39, 0, environment.getEnvProps("REGIONAL","k.s3RD")*this.rMassMol.getEntry(39, 0) );
		this.rMassMolRemoval.setEntry(40, 0, environment.getEnvProps("REGIONAL","k.s3RS")*this.rMassMol.getEntry(40, 0) );
		this.rMassMolRemoval.setEntry(41, 0, environment.getEnvProps("REGIONAL","k.s3RA")*this.rMassMol.getEntry(41, 0) );
		this.rMassMolRemoval.setEntry(42, 0, environment.getEnvProps("REGIONAL","k.s3RP")*this.rMassMol.getEntry(42, 0) );
		
		this.rMassMolRemoval.setEntry(43, 0, environment.getEnvProps("CONTINENTAL","k.aC")*this.rMassMol.getEntry(43, 0) );
		this.rMassMolRemoval.setEntry(44, 0, environment.getEnvProps("CONTINENTAL","k.aCS")*this.rMassMol.getEntry(44, 0) );
		this.rMassMolRemoval.setEntry(45, 0, environment.getEnvProps("CONTINENTAL","k.aCA")*this.rMassMol.getEntry(45, 0) );
		this.rMassMolRemoval.setEntry(46, 0, environment.getEnvProps("CONTINENTAL","k.aCP")*this.rMassMol.getEntry(46, 0) );
		this.rMassMolRemoval.setEntry(50, 0, environment.getEnvProps("CONTINENTAL","k.w0C")*this.rMassMol.getEntry(50, 0) );
		this.rMassMolRemoval.setEntry(51, 0, environment.getEnvProps("CONTINENTAL","k.w0CS")*this.rMassMol.getEntry(51, 0) );
		this.rMassMolRemoval.setEntry(52, 0, environment.getEnvProps("CONTINENTAL","k.w0CA")*this.rMassMol.getEntry(52, 0) );
		this.rMassMolRemoval.setEntry(53, 0, environment.getEnvProps("CONTINENTAL","k.w0CP")*this.rMassMol.getEntry(53, 0) );
		this.rMassMolRemoval.setEntry(54, 0, environment.getEnvProps("CONTINENTAL","k.w1C")*this.rMassMol.getEntry(54, 0) );
		this.rMassMolRemoval.setEntry(55, 0, environment.getEnvProps("CONTINENTAL","k.w1CS")*this.rMassMol.getEntry(55, 0) );
		this.rMassMolRemoval.setEntry(56, 0, environment.getEnvProps("CONTINENTAL","k.w1CA")*this.rMassMol.getEntry(56, 0) );
		this.rMassMolRemoval.setEntry(57, 0, environment.getEnvProps("CONTINENTAL","k.w1CP")*this.rMassMol.getEntry(57, 0) );
		this.rMassMolRemoval.setEntry(58, 0, environment.getEnvProps("CONTINENTAL","k.w2C")*this.rMassMol.getEntry(58, 0) );
		this.rMassMolRemoval.setEntry(59, 0, environment.getEnvProps("CONTINENTAL","k.w2CS")*this.rMassMol.getEntry(59, 0) );
		this.rMassMolRemoval.setEntry(60, 0, environment.getEnvProps("CONTINENTAL","k.w2CA")*this.rMassMol.getEntry(60, 0) );
		this.rMassMolRemoval.setEntry(61, 0, environment.getEnvProps("CONTINENTAL","k.w2CP")*this.rMassMol.getEntry(61, 0) );
		this.rMassMolRemoval.setEntry(62, 0, environment.getEnvProps("CONTINENTAL","k.sd0CD")*this.rMassMol.getEntry(62, 0) );
		this.rMassMolRemoval.setEntry(63, 0, environment.getEnvProps("CONTINENTAL","k.sd0CS")*this.rMassMol.getEntry(63, 0) );
		this.rMassMolRemoval.setEntry(64, 0, environment.getEnvProps("CONTINENTAL","k.sd0CA")*this.rMassMol.getEntry(64, 0) );
		this.rMassMolRemoval.setEntry(65, 0, environment.getEnvProps("CONTINENTAL","k.sd0CP")*this.rMassMol.getEntry(65, 0) );
		this.rMassMolRemoval.setEntry(66, 0, environment.getEnvProps("CONTINENTAL","k.sd1C")*this.rMassMol.getEntry(66, 0) );
		this.rMassMolRemoval.setEntry(67, 0, environment.getEnvProps("CONTINENTAL","k.sd1CS")*this.rMassMol.getEntry(67, 0) );
		this.rMassMolRemoval.setEntry(68, 0, environment.getEnvProps("CONTINENTAL","k.sd1CA")*this.rMassMol.getEntry(68, 0) );
		this.rMassMolRemoval.setEntry(69, 0, environment.getEnvProps("CONTINENTAL","k.sd1CP")*this.rMassMol.getEntry(69, 0) );
		this.rMassMolRemoval.setEntry(70, 0, environment.getEnvProps("CONTINENTAL","k.sd2C")*this.rMassMol.getEntry(70, 0) );
		this.rMassMolRemoval.setEntry(71, 0, environment.getEnvProps("CONTINENTAL","k.sd2CS")*this.rMassMol.getEntry(71, 0) );
		this.rMassMolRemoval.setEntry(72, 0, environment.getEnvProps("CONTINENTAL","k.sd2CA")*this.rMassMol.getEntry(72, 0) );
		this.rMassMolRemoval.setEntry(73, 0, environment.getEnvProps("CONTINENTAL","k.sd2CP")*this.rMassMol.getEntry(73, 0) );
		this.rMassMolRemoval.setEntry(74, 0, environment.getEnvProps("CONTINENTAL","k.s1C")*this.rMassMol.getEntry(74, 0) );
		this.rMassMolRemoval.setEntry(75, 0, environment.getEnvProps("CONTINENTAL","k.s1CS")*this.rMassMol.getEntry(75, 0) );
		this.rMassMolRemoval.setEntry(76, 0, environment.getEnvProps("CONTINENTAL","k.s1CA")*this.rMassMol.getEntry(76, 0) );
		this.rMassMolRemoval.setEntry(77, 0, environment.getEnvProps("CONTINENTAL","k.s1CP")*this.rMassMol.getEntry(77, 0) );
		this.rMassMolRemoval.setEntry(78, 0, environment.getEnvProps("CONTINENTAL","k.s2C") *this.rMassMol.getEntry(78, 0) );
		this.rMassMolRemoval.setEntry(79, 0, environment.getEnvProps("CONTINENTAL","k.s2CS")*this.rMassMol.getEntry(79, 0) );
		this.rMassMolRemoval.setEntry(80, 0, environment.getEnvProps("CONTINENTAL","k.s2CA")*this.rMassMol.getEntry(80, 0) );
		this.rMassMolRemoval.setEntry(81, 0, environment.getEnvProps("CONTINENTAL","k.s2CP")*this.rMassMol.getEntry(81, 0) );
		this.rMassMolRemoval.setEntry(82, 0, environment.getEnvProps("CONTINENTAL","k.s3C")*this.rMassMol.getEntry(82, 0) );
		this.rMassMolRemoval.setEntry(83, 0, environment.getEnvProps("CONTINENTAL","k.s3CS")*this.rMassMol.getEntry(83, 0) );
		this.rMassMolRemoval.setEntry(84, 0, environment.getEnvProps("CONTINENTAL","k.s3CA")*this.rMassMol.getEntry(84, 0) );
		this.rMassMolRemoval.setEntry(85, 0, environment.getEnvProps("CONTINENTAL","k.s3CP")*this.rMassMol.getEntry(85, 0) );
		this.rMassMolRemoval.setEntry(82, 0, environment.getEnvProps("CONTINENTAL","k.s3C")*this.rMassMol.getEntry(82, 0) );
		this.rMassMolRemoval.setEntry(83, 0, environment.getEnvProps("CONTINENTAL","k.s3CS")*this.rMassMol.getEntry(83, 0) );
		this.rMassMolRemoval.setEntry(84, 0, environment.getEnvProps("CONTINENTAL","k.s3CA")*this.rMassMol.getEntry(84, 0) );
		this.rMassMolRemoval.setEntry(85, 0, environment.getEnvProps("CONTINENTAL","k.s3CP")*this.rMassMol.getEntry(85, 0) );
		
		this.rMassMolRemoval.setEntry(86, 0, environment.getEnvProps("MODERATE","k.aM")*this.rMassMol.getEntry(86, 0) );
		this.rMassMolRemoval.setEntry(87, 0, environment.getEnvProps("MODERATE","k.aMS")*this.rMassMol.getEntry(87, 0) );
		this.rMassMolRemoval.setEntry(88, 0, environment.getEnvProps("MODERATE","k.aMA")*this.rMassMol.getEntry(88, 0) );
		this.rMassMolRemoval.setEntry(89, 0, environment.getEnvProps("MODERATE","k.aMP")*this.rMassMol.getEntry(89, 0) );
		this.rMassMolRemoval.setEntry(93, 0, environment.getEnvProps("MODERATE","k.w2MD")*this.rMassMol.getEntry(93, 0) );
		this.rMassMolRemoval.setEntry(94, 0, environment.getEnvProps("MODERATE","k.w2MS")*this.rMassMol.getEntry(94, 0) );
		this.rMassMolRemoval.setEntry(95, 0, environment.getEnvProps("MODERATE","k.w2MA")*this.rMassMol.getEntry(95, 0) );
		this.rMassMolRemoval.setEntry(96, 0, environment.getEnvProps("MODERATE","k.w2MP")*this.rMassMol.getEntry(96, 0) );
		this.rMassMolRemoval.setEntry(97, 0, environment.getEnvProps("MODERATE","k.w3MD")*this.rMassMol.getEntry(97, 0) );
		this.rMassMolRemoval.setEntry(98, 0, environment.getEnvProps("MODERATE","k.w3MS")*this.rMassMol.getEntry(98, 0) );
		this.rMassMolRemoval.setEntry(99, 0, environment.getEnvProps("MODERATE","k.w3MA")*this.rMassMol.getEntry(99, 0) );
		this.rMassMolRemoval.setEntry(100, 0, environment.getEnvProps("MODERATE","k.w3MP")*this.rMassMol.getEntry(100, 0) );
		this.rMassMolRemoval.setEntry(101, 0, environment.getEnvProps("MODERATE","k.sdMD")*this.rMassMol.getEntry(101, 0) );
		this.rMassMolRemoval.setEntry(102, 0, environment.getEnvProps("MODERATE","k.sdMS")*this.rMassMol.getEntry(102, 0) );
		this.rMassMolRemoval.setEntry(103, 0, environment.getEnvProps("MODERATE","k.sdMA")*this.rMassMol.getEntry(103, 0) );
		this.rMassMolRemoval.setEntry(104, 0, environment.getEnvProps("MODERATE","k.sdMP")*this.rMassMol.getEntry(104, 0) );
		this.rMassMolRemoval.setEntry(105, 0, environment.getEnvProps("MODERATE","k.sMD")*this.rMassMol.getEntry(105, 0) );
		this.rMassMolRemoval.setEntry(106, 0, environment.getEnvProps("MODERATE","k.sMS")*this.rMassMol.getEntry(106, 0) );
		this.rMassMolRemoval.setEntry(107, 0, environment.getEnvProps("MODERATE","k.sMA")*this.rMassMol.getEntry(107, 0) );
		this.rMassMolRemoval.setEntry(108, 0, environment.getEnvProps("MODERATE","k.sMP")*this.rMassMol.getEntry(108, 0) );
		
		this.rMassMolRemoval.setEntry(109, 0, environment.getEnvProps("ARCTIC","k.aA")*this.rMassMol.getEntry(109, 0) );
		this.rMassMolRemoval.setEntry(110, 0, environment.getEnvProps("ARCTIC","k.aAS")*this.rMassMol.getEntry(110, 0) );
		this.rMassMolRemoval.setEntry(111, 0, environment.getEnvProps("ARCTIC","k.aAA")*this.rMassMol.getEntry(111, 0) );
		this.rMassMolRemoval.setEntry(112, 0, environment.getEnvProps("ARCTIC","k.aAP")*this.rMassMol.getEntry(112, 0) );
		this.rMassMolRemoval.setEntry(116, 0, environment.getEnvProps("ARCTIC","k.w2AD")*this.rMassMol.getEntry(116, 0) );
		this.rMassMolRemoval.setEntry(117, 0, environment.getEnvProps("ARCTIC","k.w2AS")*this.rMassMol.getEntry(117, 0) );
		this.rMassMolRemoval.setEntry(118, 0, environment.getEnvProps("ARCTIC","k.w2AA")*this.rMassMol.getEntry(118, 0) );
		this.rMassMolRemoval.setEntry(119, 0, environment.getEnvProps("ARCTIC","k.w2AP")*this.rMassMol.getEntry(119, 0) );
		this.rMassMolRemoval.setEntry(120, 0, environment.getEnvProps("ARCTIC","k.w3AD")*this.rMassMol.getEntry(120, 0) );
		this.rMassMolRemoval.setEntry(121, 0, environment.getEnvProps("ARCTIC","k.w3AS")*this.rMassMol.getEntry(121, 0) );
		this.rMassMolRemoval.setEntry(122, 0, environment.getEnvProps("ARCTIC","k.w3AA")*this.rMassMol.getEntry(122, 0) );
		this.rMassMolRemoval.setEntry(123, 0, environment.getEnvProps("ARCTIC","k.w3AP")*this.rMassMol.getEntry(123, 0) );
		this.rMassMolRemoval.setEntry(124, 0, environment.getEnvProps("ARCTIC","k.sdAD")*this.rMassMol.getEntry(124, 0) );
		this.rMassMolRemoval.setEntry(125, 0, environment.getEnvProps("ARCTIC","k.sdAS")*this.rMassMol.getEntry(125, 0) );
		this.rMassMolRemoval.setEntry(126, 0, environment.getEnvProps("ARCTIC","k.sdAA")*this.rMassMol.getEntry(126, 0) );
		this.rMassMolRemoval.setEntry(127, 0, environment.getEnvProps("ARCTIC","k.sdAP")*this.rMassMol.getEntry(127, 0) );		
		this.rMassMolRemoval.setEntry(128, 0, environment.getEnvProps("ARCTIC","k.sAD")*this.rMassMol.getEntry(128, 0) );
		this.rMassMolRemoval.setEntry(129, 0, environment.getEnvProps("ARCTIC","k.sAS")*this.rMassMol.getEntry(129, 0) );
		this.rMassMolRemoval.setEntry(130, 0, environment.getEnvProps("ARCTIC","k.sAA")*this.rMassMol.getEntry(130, 0) );
		this.rMassMolRemoval.setEntry(131, 0, environment.getEnvProps("ARCTIC","k.sAP")*this.rMassMol.getEntry(131, 0) );
		
		this.rMassMolRemoval.setEntry(132, 0, environment.getEnvProps("TROPICAL","k.aT")*this.rMassMol.getEntry(132, 0) );
		this.rMassMolRemoval.setEntry(133, 0, environment.getEnvProps("TROPICAL","k.aTS")*this.rMassMol.getEntry(133, 0) );
		this.rMassMolRemoval.setEntry(134, 0, environment.getEnvProps("TROPICAL","k.aTA")*this.rMassMol.getEntry(134, 0) );
		this.rMassMolRemoval.setEntry(135, 0, environment.getEnvProps("TROPICAL","k.aTP")*this.rMassMol.getEntry(135, 0) );
		this.rMassMolRemoval.setEntry(139, 0, environment.getEnvProps("TROPICAL","k.w2TD")*this.rMassMol.getEntry(139, 0) );
		this.rMassMolRemoval.setEntry(140, 0, environment.getEnvProps("TROPICAL","k.w2TS")*this.rMassMol.getEntry(140, 0) );
		this.rMassMolRemoval.setEntry(141, 0, environment.getEnvProps("TROPICAL","k.w2TA")*this.rMassMol.getEntry(141, 0) );
		this.rMassMolRemoval.setEntry(142, 0, environment.getEnvProps("TROPICAL","k.w2TP")*this.rMassMol.getEntry(142, 0) );
		this.rMassMolRemoval.setEntry(143, 0, environment.getEnvProps("TROPICAL","k.w3TD")*this.rMassMol.getEntry(143, 0) );		
		this.rMassMolRemoval.setEntry(144, 0, environment.getEnvProps("TROPICAL","k.w3TS")*this.rMassMol.getEntry(144, 0) );
		this.rMassMolRemoval.setEntry(145, 0, environment.getEnvProps("TROPICAL","k.w3TA")*this.rMassMol.getEntry(145, 0) );
		this.rMassMolRemoval.setEntry(146, 0, environment.getEnvProps("TROPICAL","k.w3TP")*this.rMassMol.getEntry(146, 0) );
		this.rMassMolRemoval.setEntry(147, 0, environment.getEnvProps("TROPICAL","k.sdTD")*this.rMassMol.getEntry(147, 0) );
		this.rMassMolRemoval.setEntry(148, 0, environment.getEnvProps("TROPICAL","k.sdTS")*this.rMassMol.getEntry(148, 0) );
		this.rMassMolRemoval.setEntry(149, 0, environment.getEnvProps("TROPICAL","k.sdTA")*this.rMassMol.getEntry(149, 0) );
		this.rMassMolRemoval.setEntry(150, 0, environment.getEnvProps("TROPICAL","k.sdTP")*this.rMassMol.getEntry(150, 0) );
		this.rMassMolRemoval.setEntry(151, 0, environment.getEnvProps("TROPICAL","k.sTD")*this.rMassMol.getEntry(151, 0) );
		this.rMassMolRemoval.setEntry(152, 0, environment.getEnvProps("TROPICAL","k.sTS")*this.rMassMol.getEntry(152, 0) );
		this.rMassMolRemoval.setEntry(153, 0, environment.getEnvProps("TROPICAL","k.sTA")*this.rMassMol.getEntry(153, 0) );
		this.rMassMolRemoval.setEntry(154, 0, environment.getEnvProps("TROPICAL","k.sTP")*this.rMassMol.getEntry(154, 0) );
		
/*		int iCount = 0;
		for (double val:this.rMassMol.getColumn(0) ) 
			System.out.println( iCount++ + " " + val );*/	
	}
	
	
	public double getMassMol( int i) {
		return this.rMassMol.getEntry(i, 0);
	}

	public double getMassKg( int i) {
		return this.rMassKg.getEntry(i, 0);
	}
	
	public double getConcentration(int i) {
		return this.rConc.getEntry(i, 0);
	}

	public double getFugacity( int i) {
		return this.rFugacity.getEntry(i, 0);
	}
	
	void createMassMatrices() 
	{		
		for (int i = 0; i < this.rEmissions.getRowDimension(); i++)
			this.rEmissions.setEntry(i, 0, -this.rEmissions.getEntry(i, 0) ); 
	
/*		RealMatrix rInv = MatrixUtils.inverse(this.rCoeffs);

		for ( int i = 0; i< rInv.getRowDimension(); i++) {
			for ( int j = 0; j< rInv.getColumnDimension(); j++ ) 
				if ( Math.abs( rInv.getEntry( i , j ) ) < 1.e-5)
					rInv.setEntry(i, j, 0.0);
		}
		
		try {
			FileWriter myWriter = new FileWriter("InverseCoeffMatrix2");
			for ( int i = 0; i< rInv.getRowDimension(); i++) {
				for ( int j = 0; j< rInv.getColumnDimension(); j++ ) 
					myWriter.write(  Double.toString( rInv.getEntry( i , j ) ) + "\t" );

				myWriter.write( "\n" );			    		  
			}
			myWriter.close();
 
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }


		this.rMassMol = rInv.multiply( this.rEmissions ) ;*/
		
		//It seems that LU has also a problem. It through matrix is singular while the direct approach returns the matrix. 
		//We check if the matrix is reversible. If not we use direct approach.
		//Update: Entering the threshold seems to fix the issue
		LUDecomposition ludecompose = new LUDecomposition(this.rCoeffs, 1e-60);
		DecompositionSolver lusolver = ludecompose.getSolver();	
//		if ( lusolver.isNonSingular() )
		this.rMassMol = lusolver.solve( this.rEmissions );
//		else {			
//			System.out.println( "Warning: The mass matrix is singular. Trying direct method ...");
//			this.rMassMol = ( MatrixUtils.inverse(this.rCoeffs) ).multiply( this.rEmissions ) ;	
//		}

		for (int i = 0; i < this.rEmissions.getRowDimension(); i++)
			this.rEmissions.setEntry(i, 0, -this.rEmissions.getEntry(i, 0) ); 
		
		int iCount = 0; 
		for (double val:this.rMassMol.getColumn( 0 ) ) {
			this.rMassKg.setEntry(iCount,  0, val*input.getSubstancesData("Molweight")/1000 );
			this.rConc.setEntry(iCount,  0, val/this.rVolumes.getEntry(iCount++, 0) );
			this.dTotalMassMol = this.dTotalMassMol + val;
		}

		iCount = 0;
		for (double val:this.rMassMol.getColumn( 0 ) ) 
			this.rMassDistrubution.setEntry(iCount, 0, val/this.dTotalMassMol );
		
		this.rFugacity.setEntry( 0, 0, this.rConc.getEntry(0, 0)*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R") );
		this.rFugacity.setEntry( 7, 0, this.rConc.getEntry(7, 0)*environment.getEnvProps("REGIONAL","KawD.R")*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 11, 0, this.rConc.getEntry(11, 0)*environment.getEnvProps("REGIONAL","KawD.R")*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 15, 0, this.rConc.getEntry(15, 0)*environment.getEnvProps("REGIONAL","KawD.R")*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 23, 0, this.rConc.getEntry(23, 0)*(environment.getEnvProps("REGIONAL","KawD.R")/environment.getEnvProps("REGIONAL","Ksdw1.R") )*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 27, 0, this.rConc.getEntry(27, 0)*(environment.getEnvProps("REGIONAL","KawD.R")/environment.getEnvProps("REGIONAL","Ksdw2.R") )*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 31, 0, this.rConc.getEntry(31, 0)*(environment.getEnvProps("REGIONAL","KawD.R")/environment.getEnvProps("REGIONAL","Ks1w.R") )*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 35, 0, this.rConc.getEntry(35, 0)*(environment.getEnvProps("REGIONAL","KawD.R")/environment.getEnvProps("REGIONAL","Ks2w.R") )*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));
		this.rFugacity.setEntry( 39, 0, this.rConc.getEntry(39, 0)*(environment.getEnvProps("REGIONAL","KawD.R")/environment.getEnvProps("REGIONAL","Ks3w.R") )*8.314*input.getLandscapeSettings("REGIONAL", "TEMP.R"));

		this.rFugacity.setEntry( 43, 0, this.rConc.getEntry(43, 0)*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 50, 0, this.rConc.getEntry(50, 0)*environment.getEnvProps("CONTINENTAL","Kaw.C")*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 54, 0, this.rConc.getEntry(54, 0)*environment.getEnvProps("CONTINENTAL","Kaw.C")*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 58, 0, this.rConc.getEntry(58, 0)*environment.getEnvProps("CONTINENTAL","Kaw.C")*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 66, 0, this.rConc.getEntry(66, 0)*(environment.getEnvProps("CONTINENTAL","Kaw.C")/environment.getEnvProps("CONTINENTAL","Ksdw1.C") )*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 70, 0, this.rConc.getEntry(70, 0)*(environment.getEnvProps("CONTINENTAL","Kaw.C")/environment.getEnvProps("CONTINENTAL","Ksdw2.C") )*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 74, 0, this.rConc.getEntry(74, 0)*(environment.getEnvProps("CONTINENTAL","Kaw.C")/environment.getEnvProps("CONTINENTAL","Ks1w.C") )*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 78, 0, this.rConc.getEntry(78, 0)*(environment.getEnvProps("CONTINENTAL","Kaw.C")/environment.getEnvProps("CONTINENTAL","Ks2w.C") )*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );
		this.rFugacity.setEntry( 82, 0, this.rConc.getEntry(82, 0)*(environment.getEnvProps("CONTINENTAL","Kaw.C")/environment.getEnvProps("CONTINENTAL","Ks3w.C") )*8.314*input.getLandscapeSettings("CONTINENTAL", "TEMP.C") );

		this.rFugacity.setEntry( 86, 0, this.rConc.getEntry(86, 0)*8.314*environment.getEnvProps("MODERATE","TEMP.M") );
		this.rFugacity.setEntry( 93, 0, this.rConc.getEntry(93, 0)*environment.getEnvProps("MODERATE","Kaw.M")*8.314*environment.getEnvProps("MODERATE","TEMP.M") );
		this.rFugacity.setEntry( 97, 0, this.rConc.getEntry(97, 0)*environment.getEnvProps("MODERATE","Kaw.M")*8.314*environment.getEnvProps("MODERATE","TEMP.M") );
		this.rFugacity.setEntry( 101, 0, this.rConc.getEntry(101, 0)*(environment.getEnvProps("MODERATE","Kaw.M")/environment.getEnvProps("MODERATE","Ksdw.M") )*8.314*environment.getEnvProps("MODERATE","TEMP.M") );
		this.rFugacity.setEntry( 105, 0, this.rConc.getEntry(105, 0)*(environment.getEnvProps("MODERATE","Kaw.M")/environment.getEnvProps("MODERATE","Ksw.M") )*8.314*environment.getEnvProps("MODERATE","TEMP.M") );

		this.rFugacity.setEntry( 109, 0, this.rConc.getEntry(109, 0)*8.314*environment.getEnvProps("ARCTIC","TEMP.A") );
		this.rFugacity.setEntry( 116, 0, this.rConc.getEntry(116, 0)*environment.getEnvProps("ARCTIC","Kaw.A")*8.314*environment.getEnvProps("ARCTIC","TEMP.A") );
		this.rFugacity.setEntry( 120, 0, this.rConc.getEntry(120, 0)*environment.getEnvProps("ARCTIC","Kaw.A")*8.314*environment.getEnvProps("ARCTIC","TEMP.A") );		
		this.rFugacity.setEntry( 124, 0, this.rConc.getEntry(124, 0)*(environment.getEnvProps("ARCTIC","Kaw.A")/environment.getEnvProps("ARCTIC","Ksdw.A") )*8.314*environment.getEnvProps("ARCTIC","TEMP.A") );
		this.rFugacity.setEntry( 128, 0, this.rConc.getEntry(128, 0)*(environment.getEnvProps("ARCTIC","Kaw.A")/environment.getEnvProps("ARCTIC","Ksw.A") )*8.314*environment.getEnvProps("ARCTIC","TEMP.A") );

		this.rFugacity.setEntry( 132, 0, this.rConc.getEntry(132, 0)*8.314*environment.getEnvProps("TROPICAL","TEMP.T"));
		this.rFugacity.setEntry( 139, 0, this.rConc.getEntry(139, 0)*environment.getEnvProps("TROPICAL","Kaw.T")*8.314*environment.getEnvProps("TROPICAL","TEMP.T"));
		this.rFugacity.setEntry( 143, 0, this.rConc.getEntry(143, 0)*environment.getEnvProps("TROPICAL","Kaw.T")*8.314*environment.getEnvProps("TROPICAL","TEMP.T"));
		this.rFugacity.setEntry( 147, 0, this.rConc.getEntry(147, 0)*(environment.getEnvProps("TROPICAL","Kaw.T")/environment.getEnvProps("TROPICAL","Ksdw.T") )*8.314*environment.getEnvProps("TROPICAL","TEMP.T"));
		this.rFugacity.setEntry( 151, 0, this.rConc.getEntry(151, 0)*(environment.getEnvProps("TROPICAL","Kaw.T")/environment.getEnvProps("TROPICAL","Ksw.T") )*8.314*environment.getEnvProps("TROPICAL","TEMP.T"));			
	}
	
	void createCoeffMatrix() 
	{
		this.coeffs[ 0 ][ 7 ] = environment.getEnvProps("REGIONAL", "k.w0RD.aRG"); // this.regional.getK_w0RD_aRG();			
		this.coeffs[ 0 ][ 11 ] = environment.getEnvProps("REGIONAL", "k.w1RD.aRG"); //this.regional.getK_w1RD_aRG();  		
		this.coeffs[ 0 ][ 15 ] = environment.getEnvProps("REGIONAL", "k.w2RD.aRG"); // this.regional.getK_w2RD_aRG();  		
		this.coeffs[ 0 ][ 31 ] = environment.getEnvProps("REGIONAL", "k.s1RD.aRG");// this.regional.getK_s1RD_aRG();  
		this.coeffs[ 0 ][ 35 ] = environment.getEnvProps("REGIONAL", "k.s2RD.aRG"); //this.regional.getK_s2RD_aRG();  
		this.coeffs[ 0 ][ 39 ] = environment.getEnvProps("REGIONAL", "k.s3RD.aRG"); //this.regional.getK_s3RD_aRG();  		
		
		this.coeffs[ 0 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aR"); //this.continental.getK_aC_aR();  		
		
		this.coeffs[ 1 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aR"); //this.continental.getK_aC_aR();  		 

		
		this.coeffs[ 2 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.aRA"); //this.regional.getK_aRS_aRA();  
		this.coeffs[ 2 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aR"); //this.continental.getK_aC_aR();  

		this.coeffs[ 3 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.aRP"); //this.regional.getK_aRS_aRP();  
		this.coeffs[ 3 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aR"); //this.continental.getK_aC_aR();  

		this.coeffs[ 4 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.cwRS"); //this.regional.getK_aRS_cwRS();  

		this.coeffs[ 5 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aRA.cwRA"); //this.regional.getK_aRA_cwRA();

		this.coeffs[ 6 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.cwRP"); //this.regional.getK_aRP_cwRP();  

		this.coeffs[ 7 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aRG.w0RD"); //this.regional.getK_aRG_w0RD();  
		
		this.coeffs[ 7 ][ 8 ] = Double.valueOf( nano.get("kdis.w0S.w0D") ); //this.input.getKdis_w0S_w0D();  
		this.coeffs[ 7 ][ 9 ] = Double.valueOf( nano.get("kdis.w0A.w0D") ); //this.input.getKdis_w0A_w0D();  		
		this.coeffs[ 7 ][ 10 ] = Double.valueOf( nano.get("kdis.w0P.w0D") ); //this.input.getKdis_w0P_w0D();  
		this.coeffs[ 7 ][ 11 ] = environment.getEnvProps("REGIONAL", "k.w1R.w0R"); //this.regional.getK_w1R_w0R();  
		this.coeffs[ 7 ][ 19 ] = environment.getEnvProps("REGIONAL", "k.sd0RD.w0RD"); //this.regional.getK_sd0RD_w0RD();  

		this.coeffs[ 8 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.w0RS"); //this.regional.getK_aRS_w0RS();  
		this.coeffs[ 8 ][ 4 ] = environment.getEnvProps("REGIONAL", "k.cwRS.w0RS"); //this.regional.getK_cwRS_w0RS();  
		this.coeffs[ 8 ][ 20 ] = environment.getEnvProps("REGIONAL", "k.sd0RS.w0RS"); // this.regional.getK_sd0RS_w0RS();  

		this.coeffs[ 9 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aRA.w0RA"); //this.regional.getK_aRA_w0RA();  
		this.coeffs[ 9 ][ 5 ] = environment.getEnvProps("REGIONAL", "k.cwRA.w0RA"); //this.regional.getK_cwRA_w0RA();  
		this.coeffs[ 9 ][ 8 ] = environment.getEnvProps("REGIONAL", "k.w0RS.w0RA"); //this.regional.getK_w0RS_w0RA();  

		this.coeffs[ 9 ][ 21 ] = environment.getEnvProps("REGIONAL", "k.sd0RA.w0RA"); //this.regional.getK_sd0RA_w0RA();  

		this.coeffs[ 10 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.w0RP"); //this.regional.getK_aRP_w0RP();  
		this.coeffs[ 10 ][ 6 ] = environment.getEnvProps("REGIONAL", "k.cwRP.w0RP"); //this.regional.getK_cwRP_w0RP();  
		this.coeffs[ 10 ][ 8 ] = environment.getEnvProps("REGIONAL", "k.w0RS.w0RP"); //this.regional.getK_w0RS_w0RP();  
		this.coeffs[ 10 ][ 22 ] = environment.getEnvProps("REGIONAL", "k.sd0RP.w0RP"); //this.regional.getK_sd0RP_w0RP();  

		this.coeffs[ 11 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aRG.w1RD"); //this.regional.getK_aRG_w1RD();  
		this.coeffs[ 11 ][ 7 ] = environment.getEnvProps("REGIONAL", "k.w0R.w1R"); //this.regional.getK_w0R_w1R();  
		this.coeffs[ 11 ][ 12 ] = Double.valueOf( nano.get("kdis.w1S.w1D") ); //this.input.getKdis_w1S_w1D();  
		this.coeffs[ 11 ][ 13 ] = Double.valueOf( nano.get("kdis.w1A.w1D") ); //this.input.getKdis_w1A_w1D();  
		this.coeffs[ 11 ][ 14 ] = Double.valueOf( nano.get("kdis.w1P.w1D") ); //this.input.getKdis_w1P_w1D();  
		this.coeffs[ 11 ][ 23 ] = environment.getEnvProps("REGIONAL", "k.sd1RD.w1RD"); //this.regional.getK_sd1RD_w1RD();  
		this.coeffs[ 11 ][ 31 ] = environment.getEnvProps("REGIONAL", "k.s1RD.w1RD"); //this.regional.getK_s1RD_w1RD();  
		this.coeffs[ 11 ][ 35 ] = environment.getEnvProps("REGIONAL", "k.s2RD.w1RD"); //this.regional.getK_s2RD_w1RD();  
		this.coeffs[ 11 ][ 39 ] = environment.getEnvProps("REGIONAL", "k.s3RD.w1RD"); //this.regional.getK_s3RD_w1RD();  
		this.coeffs[ 11 ][ 54 ] = environment.getEnvProps("CONTINENTAL", "k.w1C.w1R"); // this.continental.getK_w1C_w1R();  

		this.coeffs[ 12 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.w1RS"); //this.regional.getK_aRS_w1RS();  
		this.coeffs[ 12 ][ 4 ] = environment.getEnvProps("REGIONAL", "k.cwRS.w1RS"); //this.regional.getK_cwRS_w1RS();  
		this.coeffs[ 12 ][ 8 ] = environment.getEnvProps("REGIONAL", "k.w0R.w1R"); //this.regional.getK_w0R_w1R();  
		this.coeffs[ 12 ][ 24 ] = environment.getEnvProps("REGIONAL", "k.sd1RS.w1RS"); //this.regional.getK_sd1RS_w1RS();  
		this.coeffs[ 12 ][ 32 ] = environment.getEnvProps("REGIONAL", "k.s1RS.w1RS"); //this.regional.getK_s1RS_w1RS();  
		this.coeffs[ 12 ][ 36 ] = environment.getEnvProps("REGIONAL", "k.s2RS.w1RS"); //this.regional.getK_s2RS_w1RS();  
		this.coeffs[ 12 ][ 40 ] = environment.getEnvProps("REGIONAL", "k.s3RS.w1RS"); //this.regional.getK_s3RS_w1RS();  

		this.coeffs[ 13 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aRA.w1RA"); //this.regional.getK_aRA_w1RA();  
		this.coeffs[ 13 ][ 5 ] = environment.getEnvProps("REGIONAL", "k.cwRA.w1RA"); //this.regional.getK_cwRA_w1RA();  
		this.coeffs[ 13 ][ 9 ] = environment.getEnvProps("REGIONAL", "k.w0R.w1R"); //this.regional.getK_w0R_w1R();  
		this.coeffs[ 13 ][ 12 ] = environment.getEnvProps("REGIONAL", "k.w1RS.w1RA"); //this.regional.getK_w1RS_w1RA();  
		this.coeffs[ 13 ][ 25 ] = environment.getEnvProps("REGIONAL", "k.sd1RA.w1RA"); //this.regional.getK_sd1RA_w1RA();  
		this.coeffs[ 13 ][ 33 ] = environment.getEnvProps("REGIONAL", "k.s1RA.w1RA"); //this.regional.getK_s1RA_w1RA();  
		this.coeffs[ 13 ][ 37 ] = environment.getEnvProps("REGIONAL", "k.s2RA.w1RA"); //this.regional.getK_s2RA_w1RA();  
		this.coeffs[ 13 ][ 41 ] = environment.getEnvProps("REGIONAL", "k.s3RA.w1RA"); //this.regional.getK_s3RA_w1RA();  

		this.coeffs[ 14 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.w1RP"); //this.regional.getK_aRP_w1RP();  
		this.coeffs[ 14 ][ 6 ] = environment.getEnvProps("REGIONAL", "k.cwRP.w1RP"); //this.regional.getK_cwRP_w1RP();  
		this.coeffs[ 14 ][ 10 ] = environment.getEnvProps("REGIONAL", "k.w0R.w1R"); //this.regional.getK_w0R_w1R();  
		this.coeffs[ 14 ][ 12 ] = environment.getEnvProps("REGIONAL", "k.w1RS.w1RP"); //this.regional.getK_w1RS_w1RP();  
		this.coeffs[ 14 ][ 26 ] = environment.getEnvProps("REGIONAL", "k.sd1RP.w1RP"); //this.regional.getK_sd1RP_w1RP();  
		this.coeffs[ 14 ][ 34 ] = environment.getEnvProps("REGIONAL", "k.s1RP.w1RP"); //this.regional.getK_s1RP_w1RP();  
		this.coeffs[ 14 ][ 38 ] = environment.getEnvProps("REGIONAL", "k.s2RP.w1RP"); //this.regional.getK_s2RP_w1RP();  
		this.coeffs[ 14 ][ 42 ] = environment.getEnvProps("REGIONAL", "k.s3RP.w1RP"); //this.regional.getK_s3RP_w1RP();  

		this.coeffs[ 15 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aRG.w2RD"); //this.regional.getK_aRG_w2RD();  
		this.coeffs[ 15 ][ 7 ] = environment.getEnvProps("REGIONAL", "k.w0R.w2R"); //this.regional.getK_w0R_w2R();  
		this.coeffs[ 15 ][ 11 ] = environment.getEnvProps("REGIONAL", "k.w1R.w2R"); //this.regional.getK_w1R_w2R();  
		this.coeffs[ 15 ][ 16 ] = Double.valueOf( nano.get("kdis.w2S.w2D") ); //this.input.getKdis_w2S_w2D();  
		this.coeffs[ 15 ][ 17 ] = Double.valueOf( nano.get("kdis.w2A.w2D") ); //this.input.getKdis_w2A_w2D();  
		this.coeffs[ 15 ][ 18 ] = Double.valueOf( nano.get("kdis.w2P.w2D") ); //this.input.getKdis_w2P_w2D();  
		this.coeffs[ 15 ][ 27 ] = environment.getEnvProps("REGIONAL", "k.sd2RD.w2RD"); //this.regional.getK_sd2RD_w2RD();  
		this.coeffs[ 15 ][ 58 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2R"); //this.continental.getK_w2C_w2R();  

		this.coeffs[ 16 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.w2RS"); //this.regional.getK_aRS_w2RS();  		
		this.coeffs[ 16 ][ 4 ] = environment.getEnvProps("REGIONAL", "k.cwRS.w2RS"); //this.regional.getK_cwRS_w2RS();  		
		this.coeffs[ 16 ][ 8 ] = environment.getEnvProps("REGIONAL", "k.w0R.w2R"); //this.regional.getK_w0R_w2R();  		
		this.coeffs[ 16 ][ 12 ] = environment.getEnvProps("REGIONAL", "k.w1R.w2R"); //this.regional.getK_w1R_w2R();  		
		this.coeffs[ 16 ][ 28 ] = environment.getEnvProps("REGIONAL", "k.sd2RS.w2RS"); //this.regional.getK_sd2RS_w2RS();  		
		this.coeffs[ 16 ][ 59 ] =  environment.getEnvProps("CONTINENTAL", "k.w2C.w2R"); //this.continental.getK_w2C_w2R();  		

		this.coeffs[ 17 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aRA.w2RA"); //this.regional.getK_aRA_w2RA();  		
		this.coeffs[ 17 ][ 5 ] = environment.getEnvProps("REGIONAL", "k.cwRA.w2RA"); //this.regional.getK_cwRA_w2RA();  		
		this.coeffs[ 17 ][ 9 ] = environment.getEnvProps("REGIONAL", "k.w0R.w2R"); //this.regional.getK_w0R_w2R();  		
		this.coeffs[ 17 ][ 13 ] = environment.getEnvProps("REGIONAL", "k.w1R.w2R"); //this.regional.getK_w1R_w2R();  		
		this.coeffs[ 17 ][ 16 ] = environment.getEnvProps("REGIONAL", "k.w2RS.w2RA"); //this.regional.getK_w2RS_w2RA();  		
		this.coeffs[ 17 ][ 29 ] = environment.getEnvProps("REGIONAL", "k.sd2RA.w2RA"); //this.regional.getK_sd2RA_w2RA();  		
		this.coeffs[ 17 ][ 60 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2R"); //this.continental.getK_w2C_w2R();  		

		this.coeffs[ 18 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.w2RP"); //this.regional.getK_aRP_w2RP();  		
		this.coeffs[ 18 ][ 6 ] = environment.getEnvProps("REGIONAL", "k.cwRP.w2RP"); //this.regional.getK_cwRP_w2RP();  		
		this.coeffs[ 18 ][ 10 ] = environment.getEnvProps("REGIONAL", "k.w0R.w2R"); //this.regional.getK_w0R_w2R();  		
		this.coeffs[ 18 ][ 14 ] = environment.getEnvProps("REGIONAL", "k.w1R.w2R"); //this.regional.getK_w1R_w2R();  		
		this.coeffs[ 18 ][ 16 ] = environment.getEnvProps("REGIONAL", "k.w2RS.w2RP"); //this.regional.getK_w2RS_w2RP();  		
		this.coeffs[ 18 ][ 30 ] = environment.getEnvProps("REGIONAL", "k.sd2RP.w2RP"); //this.regional.getK_sd2RP_w2RP();  		
		this.coeffs[ 18 ][ 61 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2R"); //this.continental.getK_w2C_w2R();  		

		this.coeffs[ 19 ][ 7 ] = environment.getEnvProps("REGIONAL", "k.w0RD.sd0RD"); //this.regional.getK_w0RD_sd0RD();  		
		this.coeffs[ 19 ][ 20 ] = Double.valueOf( nano.get("kdis.sd0S.sd0D") ); //this.input.getKdis_sd0S_sd0D();  		
		this.coeffs[ 19 ][ 21 ] = Double.valueOf( nano.get("kdis.sd0A.sd0D") ); //this.input.getKdis_sd0A_sd0D();  		
		this.coeffs[ 19 ][ 22 ] = Double.valueOf( nano.get("kdis.sd0P.sd0D") ); //this.input.getKdis_sd0P_sd0D();  		

		this.coeffs[ 20 ][ 8 ] = environment.getEnvProps("REGIONAL", "k.w0RS.sd0RS"); //this.regional.getK_w0RS_sd0RS();  		
		this.coeffs[ 21 ][ 9 ] = environment.getEnvProps("REGIONAL", "k.w0RA.sd0RA"); //this.regional.getK_w0RA_sd0RA();  		
		this.coeffs[ 21 ][ 20 ] = environment.getEnvProps("REGIONAL", "k.sd0RS.sd0RA"); //this.regional.getK_sd0RS_sd0RA();  		

		this.coeffs[ 22 ][ 10 ] = environment.getEnvProps("REGIONAL", "k.w0RP.sd0RP"); //this.regional.getK_w0RP_sd0RP();  		
		this.coeffs[ 22 ][ 20 ] = environment.getEnvProps("REGIONAL", "k.sd0RS.sd0RP"); //this.regional.getK_sd0RS_sd0RP();  		

		this.coeffs[ 23 ][ 11 ] = environment.getEnvProps("REGIONAL", "k.w1RD.sd1RD"); //this.regional.getK_w1RD_sd1RD();  		
		this.coeffs[ 23 ][ 24 ] = Double.valueOf( nano.get("kdis.sd1S.sd1D") ); //this.input.getKdis_sd1S_sd1D();  		
		this.coeffs[ 23 ][ 25 ] = Double.valueOf( nano.get("kdis.sd1A.sd1D") ); //this.input.getKdis_sd1A_sd1D();  		
		this.coeffs[ 23 ][ 26 ] = Double.valueOf( nano.get("kdis.sd1P.sd1D") ); //this.input.getKdis_sd1P_sd1D();  		

		this.coeffs[ 24 ][ 12 ] = environment.getEnvProps("REGIONAL", "k.w1RS.sd1RS"); //this.regional.getK_w1RS_sd1RS();  		
		this.coeffs[ 25 ][ 13 ] = environment.getEnvProps("REGIONAL", "k.w1RA.sd1RA"); //this.regional.getK_w1RA_sd1RA();  		
		this.coeffs[ 25 ][ 24 ] = environment.getEnvProps("REGIONAL", "k.sd1RS.sd1RA"); //this.regional.getK_sd1RS_sd1RA();  		

		this.coeffs[ 26 ][ 14 ] = environment.getEnvProps("REGIONAL", "k.w1RP.sd1RP"); //this.regional.getK_w1RP_sd1RP();  		
		this.coeffs[ 26 ][ 24 ] = environment.getEnvProps("REGIONAL", "k.sd1RS.sd1RP"); //this.regional.getK_sd1RS_sd1RP();  		

		this.coeffs[ 27 ][ 15 ] = environment.getEnvProps("REGIONAL", "k.w2RD.sd2RD"); //this.regional.getK_w2RD_sd2RD();  		
		this.coeffs[ 27 ][ 28 ] = Double.valueOf( nano.get("kdis.sd2S.sd2D") ); //this.input.getKdis_sd2S_sd2D();  		
		this.coeffs[ 27 ][ 29 ] = Double.valueOf( nano.get("kdis.sd2A.sd2D") ); //this.input.getKdis_sd2A_sd2D();  		
		this.coeffs[ 27 ][ 30 ] = Double.valueOf( nano.get("kdis.sd2P.sd2D") ); //this.input.getKdis_sd2P_sd2D();  		

		this.coeffs[ 28 ][ 16 ] = environment.getEnvProps("REGIONAL", "k.w2RS.sd2RS"); //this.regional.getK_w2RS_sd2RS();  		

		this.coeffs[ 29 ][ 17 ] = environment.getEnvProps("REGIONAL", "k.w2RA.sd2RA"); //this.regional.getK_w2RA_sd2RA();  		
		this.coeffs[ 29 ][ 28 ] = environment.getEnvProps("REGIONAL", "k.sd2RS.sd2RA"); //this.regional.getK_sd2RS_sd2RA();  		

		this.coeffs[ 30 ][ 18 ] = environment.getEnvProps("REGIONAL", "k.w2RP.sd2RP"); //this.regional.getK_w2RP_sd2RP();  		
		this.coeffs[ 30 ][ 28 ] = environment.getEnvProps("REGIONAL", "k.sd2RS.sd2RP"); //this.regional.getK_sd2RS_sd2RP();  		

		this.coeffs[ 31 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aRG.s1RD"); //this.regional.getK_aRG_s1RD();  		
		this.coeffs[ 31 ][ 32 ] = Double.valueOf( nano.get("kdis.s1S.s1D") ); //this.input.getKdis_s1S_s1D();  		
		this.coeffs[ 31 ][ 33 ] = Double.valueOf( nano.get("kdis.s1A.s1D") ); //this.input.getKdis_s1A_s1D();  		
		this.coeffs[ 31 ][ 34 ] = Double.valueOf( nano.get("kdis.s1P.s1D") ); //this.input.getKdis_s1P_s1D();  		

		this.coeffs[ 32 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.s1RS"); //this.regional.getK_aRS_s1RS();  		
		this.coeffs[ 32 ][ 4 ] = environment.getEnvProps("REGIONAL", "k.cwRS.s1RS"); //this.regional.getK_cwRS_s1RS();  		

		this.coeffs[ 33 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aRA.s1RA"); //this.regional.getK_aRA_s1RA();  		
		this.coeffs[ 33 ][ 5 ] = environment.getEnvProps("REGIONAL", "k.cwRA.s1RA"); //this.regional.getK_cwRA_s1RA();  		
		this.coeffs[ 33 ][ 32 ] = environment.getEnvProps("REGIONAL", "k.s1RS.s1RA"); //this.regional.getK_s1RS_s1RA();  		

		this.coeffs[ 34 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.s1RP"); //this.regional.getK_aRP_s1RP();  		
		this.coeffs[ 34 ][ 6 ] = environment.getEnvProps("REGIONAL", "k.cwRP.s1RP"); //this.regional.getK_cwRP_s1RP();  		
		this.coeffs[ 34 ][ 32 ] = environment.getEnvProps("REGIONAL", "k.s1RS.s1RP"); //this.regional.getK_s1RS_s1RP();  		

		this.coeffs[ 35 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aRG.s2RD"); //this.regional.getK_aRG_s2RD();  		
		this.coeffs[ 35 ][ 36 ] = Double.valueOf( nano.get("kdis.s2S.s2D") ); //this.input.getKdis_s2S_s2D();  		
		this.coeffs[ 35 ][ 37 ] = Double.valueOf( nano.get("kdis.s2A.s2D") ); //this.input.getKdis_s2A_s2D();  		
		this.coeffs[ 35 ][ 38 ] = Double.valueOf( nano.get("kdis.s2P.s2D") ); //this.input.getKdis_s2P_s2D();  		

		this.coeffs[ 36 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aRS.s2RS"); //this.regional.getK_aRS_s2RS();  		
		this.coeffs[ 36 ][ 4 ] = environment.getEnvProps("REGIONAL", "k.cwRS.s2RS"); //this.regional.getK_cwRS_s2RS();  		

		this.coeffs[ 37 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aRA.s2RA"); //this.regional.getK_aRA_s2RA();  		
		this.coeffs[ 37 ][ 5 ] = environment.getEnvProps("REGIONAL", "k.cwRA.s2RA"); //this.regional.getK_cwRA_s2RA();  		
		this.coeffs[ 37 ][ 36 ] = environment.getEnvProps("REGIONAL", "k.s2RS.s2RA"); //this.regional.getK_s2RS_s2RA();  		

		this.coeffs[ 38 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.s2RP"); //this.regional.getK_aRP_s2RP();  		
		this.coeffs[ 38 ][ 6 ] = environment.getEnvProps("REGIONAL", "k.cwRP.s2RP"); //this.regional.getK_cwRP_s2RP();  		
		this.coeffs[ 38 ][ 36 ] = environment.getEnvProps("REGIONAL", "k.s2RS.s2RP"); //this.regional.getK_s2RS_s2RP();  		

		this.coeffs[ 39 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aRG.s3RD"); //this.regional.getK_aRG_s3RD();  		
		this.coeffs[ 39 ][ 40 ] = Double.valueOf( nano.get("kdis.s3S.s3D") ); //this.input.getKdis_s3S_s3D();  		
		this.coeffs[ 39 ][ 41 ] = Double.valueOf( nano.get("kdis.s3A.s3D") ); //this.input.getKdis_s3A_s3D();  		
		this.coeffs[ 39 ][ 42 ] = Double.valueOf( nano.get("kdis.s3P.s3D") ); //this.input.getKdis_s3P_s3D();  		

		this.coeffs[ 40 ][ 1 ] =  environment.getEnvProps("REGIONAL", "k.aRS.s3RS"); //this.regional.getK_aRS_s3RS();  		
		this.coeffs[ 40 ][ 4 ] =  environment.getEnvProps("REGIONAL", "k.cwRS.s3RS"); //this.regional.getK_cwRS_s3RS();  		

		this.coeffs[ 41 ][ 2 ] =  environment.getEnvProps("REGIONAL", "k.aRA.s3RA"); //this.regional.getK_aRA_s3RA();  		
		this.coeffs[ 41 ][ 5 ] = environment.getEnvProps("REGIONAL", "k.cwRA.s3RA"); //this.regional.getK_cwRA_s3RA();
		this.coeffs[ 41 ][ 40 ] = environment.getEnvProps("REGIONAL", "k.s3RS.s3RA"); //this.regional.getK_s3RS_s3RA();

		this.coeffs[ 42 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aRP.s3RP"); //this.regional.getK_aRP_s3RP();  		
		this.coeffs[ 42 ][ 6 ] = environment.getEnvProps("REGIONAL", "k.cwRP.s3RP"); //this.regional.getK_cwRP_s3RP();  		
		this.coeffs[ 42 ][ 40 ] = environment.getEnvProps("REGIONAL", "k.s3RS.s3RP"); //this.regional.getK_s3RS_s3RP();  		
		
		this.coeffs[ 43 ][ 0 ] = environment.getEnvProps("REGIONAL", "k.aR.aC"); //this.regional.getK_aR_aC();  		
		this.coeffs[ 43 ][ 50 ] = environment.getEnvProps("CONTINENTAL", "k.w0CD.aCG"); // this.formulas.getK_w0C_aC();  		
		this.coeffs[ 43 ][ 54 ] = environment.getEnvProps("CONTINENTAL", "k.w1CD.aCG"); // this.formulas.getK_w1C_aC();  		
		this.coeffs[ 43 ][ 58 ] = environment.getEnvProps("CONTINENTAL", "k.w2CD.aCG"); // this.formulas.getK_w2C_aC();  		
		this.coeffs[ 43 ][ 74 ] = environment.getEnvProps("CONTINENTAL", "k.s1CD.aCG"); //this.formulas.getK_s1C_aC(); 
		this.coeffs[ 43 ][ 78 ] = environment.getEnvProps("CONTINENTAL", "k.s2CD.aCG"); //this.formulas.getK_s2C_aC();  		
		this.coeffs[ 43 ][ 82 ] = environment.getEnvProps("CONTINENTAL", "k.s3CD.aCG"); //this.formulas.getK_s3C_aC();  		
		this.coeffs[ 43 ][ 86 ] = environment.getEnvProps("MODERATE", "k.aM.aC");// this.formulas.getK_aM_aC();  		

		this.coeffs[ 44 ][ 1 ] = environment.getEnvProps("REGIONAL", "k.aR.aC"); //this.regional.getK_aR_aC();  		
		this.coeffs[ 44 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aM.aC"); //this.formulas.getK_aM_aC();  		

		this.coeffs[ 45 ][ 2 ] = environment.getEnvProps("REGIONAL", "k.aR.aC"); //this.regional.getK_aR_aC();  		
		this.coeffs[ 45 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.aCA"); // this.continental.getK_aCS_aCA();  		
		this.coeffs[ 45 ][ 88 ] = environment.getEnvProps("MODERATE", "k.aM.aC"); //this.formulas.getK_aM_aC();  		

		this.coeffs[ 46 ][ 3 ] = environment.getEnvProps("REGIONAL", "k.aR.aC"); //this.regional.getK_aR_aC();  		
		this.coeffs[ 46 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.aCP"); // this.continental.getK_aCS_aCP();  		
		this.coeffs[ 46 ][ 89 ] = environment.getEnvProps("MODERATE", "k.aM.aC"); // this.formulas.getK_aM_aC();  		

		this.coeffs[ 47 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.cwCS"); //this.continental.getK_aCS_cwCS(); 		

		this.coeffs[ 48 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.cwCA"); //this.continental.getK_aCA_cwCA(); 		

		this.coeffs[ 49 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.cwCP"); //this.continental.getK_aCP_cwCP(); 		

		this.coeffs[ 50 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aCG.w0CD"); //this.formulas.getK_aC_w0C(); 		
		this.coeffs[ 50 ][ 51 ] = Double.valueOf( nano.get("kdis.w0S.w0D") ); //this.input.getKdis_w0S_w0D(); 		
		this.coeffs[ 50 ][ 52 ] = Double.valueOf( nano.get("kdis.w0A.w0D") ); //this.input.getKdis_w0A_w0D(); 		
		this.coeffs[ 50 ][ 53 ] = Double.valueOf( nano.get("kdis.w0P.w0D") ); //this.input.getKdis_w0P_w0D(); 		
		this.coeffs[ 50 ][ 54 ] = environment.getEnvProps("CONTINENTAL", "k.w1C.w0C"); //this.continental.getK_w1C_w0C(); 		
		this.coeffs[ 50 ][ 62 ] = environment.getEnvProps("CONTINENTAL", "k.sd0CD.w0CD"); //this.continental.getK_sd0CD_w0CD(); 		

		this.coeffs[ 51 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.w0CS"); //this.continental.getK_aCS_w0CS(); 		
		this.coeffs[ 51 ][ 47 ] = environment.getEnvProps("CONTINENTAL", "k.cwCS.w0CS"); //this.continental.getK_cwCS_w0CS(); 		
		this.coeffs[ 51 ][ 63 ] = environment.getEnvProps("CONTINENTAL", "k.sd0CS.w0CS"); //this.continental.getK_sd0CS_w0CS(); 		

		this.coeffs[ 52 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.w0CA"); //this.continental.getK_aCA_w0CA(); 		
		this.coeffs[ 52 ][ 48 ] = environment.getEnvProps("CONTINENTAL", "k.cwCA.w0CA"); //this.continental.getK_cwCA_w0CA(); 		
		this.coeffs[ 52 ][ 51 ] = environment.getEnvProps("CONTINENTAL", "k.w0CS.w0CA"); //this.continental.getK_w0CS_w0CA(); 		
		this.coeffs[ 52 ][ 64 ] = environment.getEnvProps("CONTINENTAL", "k.sd0CA.w0CA"); //this.continental.getK_sd0CA_w0CA(); 		

		this.coeffs[ 53 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.w0CP"); //this.continental.getK_aCP_w0CP(); 			
		this.coeffs[ 53 ][ 49 ] = environment.getEnvProps("CONTINENTAL", "k.cwCP.w0CP"); //this.continental.getK_cwCP_w0CP(); 		
		this.coeffs[ 53 ][ 51 ] = environment.getEnvProps("CONTINENTAL", "k.w0CS.w0CP"); //this.continental.getK_w0CS_w0CP(); 		
		this.coeffs[ 53 ][ 65 ] = environment.getEnvProps("CONTINENTAL", "k.sd0CP.w0CP"); //this.continental.getK_sd0CP_w0CP(); 		

		this.coeffs[ 54 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aCG.w1CD"); //this.formulas.getK_aC_w1C(); 		
		this.coeffs[ 54 ][ 50 ] = environment.getEnvProps("CONTINENTAL", "k.w0C.w1C"); //this.formulas.getK_w0C_w1C(); 		
		this.coeffs[ 54 ][ 55 ] = Double.valueOf( nano.get("kdis.w1S.w1D") ); //this.input.getKdis_w1S_w1D(); 		
		this.coeffs[ 54 ][ 56 ] = Double.valueOf( nano.get("kdis.w1A.w1D") ); //this.input.getKdis_w1A_w1D(); 		
		this.coeffs[ 54 ][ 57 ] = Double.valueOf( nano.get("kdis.w1P.w1D") ); //this.input.getKdis_w1P_w1D(); 		
		this.coeffs[ 54 ][ 66 ] = environment.getEnvProps("CONTINENTAL", "k.sd1CD.w1CD"); //this.formulas.getK_sd1C_w1C(); 		
		
		this.coeffs[ 54 ][ 74 ] = environment.getEnvProps("CONTINENTAL", "k.s1C.w1C"); //this.formulas.getK_s1C_w1C(); 		
		this.coeffs[ 54 ][ 78 ] = environment.getEnvProps("CONTINENTAL", "k.s2C.w1C"); //this.formulas.getK_s2C_w1C(); 		
		this.coeffs[ 54 ][ 82 ] = environment.getEnvProps("CONTINENTAL", "k.s3C.w1C"); //this.formulas.getK_s3C_w1C(); 		

		this.coeffs[ 55 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.w1CS"); //this.continental.getK_aCS_w1CS(); 				
		this.coeffs[ 55 ][ 47 ] = environment.getEnvProps("CONTINENTAL", "k.cwCS.w1CS"); //this.continental.getK_cwCS_w1CS(); 				
		this.coeffs[ 55 ][ 51 ] = environment.getEnvProps("CONTINENTAL", "k.w0C.w1C"); //this.continental.getK_w0C_w1C(); 		
		this.coeffs[ 55 ][ 67 ] = environment.getEnvProps("CONTINENTAL", "k.sd1CS.w1CS"); //this.continental.getK_sd1CS_w1CS(); 		
		this.coeffs[ 55 ][ 75 ] = environment.getEnvProps("CONTINENTAL", "k.s1CS.w1CS"); //this.continental.getK_s1CS_w1CS(); 		
		this.coeffs[ 55 ][ 79 ] = environment.getEnvProps("CONTINENTAL", "k.s2CA.w1CA"); //this.continental.getK_s2CA_w1CA(); 		
		this.coeffs[ 55 ][ 83 ] = environment.getEnvProps("CONTINENTAL", "k.s3CS.w1CS"); //this.continental.getK_s3CS_w1CS(); 		

		this.coeffs[ 56 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.w1CA"); //this.continental.getK_aCA_w1CA(); 				
		this.coeffs[ 56 ][ 48 ] = environment.getEnvProps("CONTINENTAL", "k.cwCA.w1CA"); //this.continental.getK_cwCA_w1CA(); 				
		this.coeffs[ 56 ][ 52 ] = environment.getEnvProps("CONTINENTAL", "k.w0C.w1C"); //this.continental.getK_w0C_w1C(); 				
		this.coeffs[ 56 ][ 55 ] = environment.getEnvProps("CONTINENTAL", "k.w1CS.w1CA"); //this.continental.getK_w1CS_w1CA(); 				
		this.coeffs[ 56 ][ 68 ] = environment.getEnvProps("CONTINENTAL", "k.sd1CA.w1CA"); //this.continental.getK_sd1CA_w1CA(); 				
		this.coeffs[ 56 ][ 76 ] = environment.getEnvProps("CONTINENTAL", "k.s1CA.w1CA"); //this.continental.getK_s1CA_w1CA(); 				
		this.coeffs[ 56 ][ 80 ] = environment.getEnvProps("CONTINENTAL", "k.s2CA.w1CA"); //this.continental.getK_s2CA_w1CA(); 				
		this.coeffs[ 56 ][ 84 ] = environment.getEnvProps("CONTINENTAL", "k.s3CA.w1CA"); //this.continental.getK_s3CA_w1CA(); 				

		this.coeffs[ 57 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.w1CP"); //this.continental.getK_aCP_w1CP(); 				
		this.coeffs[ 57 ][ 49 ] = environment.getEnvProps("CONTINENTAL", "k.cwCP.w1CP"); //this.continental.getK_cwCP_w1CP(); 				
		this.coeffs[ 57 ][ 53 ] = environment.getEnvProps("CONTINENTAL", "k.w0C.w1C"); //this.continental.getK_w0C_w1C(); 				
		this.coeffs[ 57 ][ 55 ] = environment.getEnvProps("CONTINENTAL", "k.w1CS.w1CP"); //this.continental.getK_w1CS_w1CP(); 				
		this.coeffs[ 57 ][ 69 ] = environment.getEnvProps("CONTINENTAL", "k.sd1CP.w1CP"); //this.continental.getK_sd1CP_w1CP(); 				
		this.coeffs[ 57 ][ 77 ] = environment.getEnvProps("CONTINENTAL", "k.s1CP.w1CP"); //this.continental.getK_s1CP_w1CP(); 				
		this.coeffs[ 57 ][ 81 ] = environment.getEnvProps("CONTINENTAL", "k.s2CP.w1CP"); //this.continental.getK_s2CP_w1CP(); 				
		this.coeffs[ 57 ][ 85 ] = environment.getEnvProps("CONTINENTAL", "k.s3CP.w1CP"); //this.continental.getK_s3CP_w1CP(); 				

		this.coeffs[ 58 ][ 15 ] = environment.getEnvProps("REGIONAL", "k.w2R.w2C"); //this.regional.getK_w2R_w2C(); 		
		this.coeffs[ 58 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aCG.w2CD"); //this.formulas.getK_aC_w2C(); 		
		this.coeffs[ 58 ][ 50 ] = environment.getEnvProps("CONTINENTAL", "k.w0C.w2C"); // this.continental.getK_w0C_w2C(); 		
		this.coeffs[ 58 ][ 54 ] = environment.getEnvProps("CONTINENTAL", "k.w1C.w2C"); //this.continental.getK_w1C_w2C(); 		
		this.coeffs[ 58 ][ 59 ] = Double.valueOf( nano.get("kdis.w2S.w2D") ); //this.input.getKdis_w2S_w2D(); 		
		this.coeffs[ 58 ][ 60 ] = Double.valueOf( nano.get("kdis.w2A.w2D") ); //this.input.getKdis_w2A_w2D(); 		
		this.coeffs[ 58 ][ 61 ] = Double.valueOf( nano.get("kdis.w2P.w2D") ); //this.input.getKdis_w2P_w2D(); 		
		this.coeffs[ 58 ][ 70 ] = environment.getEnvProps("CONTINENTAL", "k.sd2CD.w2CD"); //this.formulas.getK_sd2C_w2C(); 				
		this.coeffs[ 58 ][ 93 ] = environment.getEnvProps("MODERATE", "k.w2M.w2C"); //this.formulas.getK_w2M_w2C(); 	

		this.coeffs[ 59 ][ 16 ] = environment.getEnvProps("REGIONAL", "k.w2R.w2C"); //this.regional.getK_w2R_w2C(); 		
		this.coeffs[ 59 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.w2CS"); //this.continental.getK_aCS_w2CS(); 		
		this.coeffs[ 59 ][ 47 ] = environment.getEnvProps("CONTINENTAL", "k.cwCS.w2CS"); //this.continental.getK_cwCS_w2CS(); 		
		this.coeffs[ 59 ][ 55 ] = environment.getEnvProps("CONTINENTAL", "k.w1C.w2C"); //this.continental.getK_w1C_w2C(); 		
		this.coeffs[ 59 ][ 71 ] = environment.getEnvProps("CONTINENTAL", "k.sd2CS.w2CS"); //this.continental.getK_sd2CS_w2CS(); 		
		this.coeffs[ 59 ][ 94 ] = environment.getEnvProps("MODERATE", "k.w2M.w2C"); //this.formulas.getK_w2M_w2C(); 	

		this.coeffs[ 60 ][ 17 ] = environment.getEnvProps("REGIONAL", "k.w2R.w2C"); //this.regional.getK_w2R_w2C(); 	
		this.coeffs[ 60 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.w2CA"); //this.continental.getK_aCA_w2CA(); 		
		this.coeffs[ 60 ][ 48 ] = environment.getEnvProps("CONTINENTAL", "k.cwCA.w2CA"); //this.continental.getK_cwCA_w2CA(); 		
		this.coeffs[ 60 ][ 56 ] = environment.getEnvProps("CONTINENTAL", "k.w1C.w2C"); //this.continental.getK_w1C_w2C(); 		
		this.coeffs[ 60 ][ 59 ] = environment.getEnvProps("CONTINENTAL", "k.w2CS.w2CA"); //this.continental.getK_w2CS_w2CA(); 		
		this.coeffs[ 60 ][ 72 ] = environment.getEnvProps("CONTINENTAL", "k.sd2CA.w2CA"); //this.continental.getK_sd2CA_w2CA(); 		
		this.coeffs[ 60 ][ 95 ] = environment.getEnvProps("MODERATE", "k.w2M.w2C"); //this.formulas.getK_w2M_w2C(); 

		this.coeffs[ 61 ][ 18 ] = environment.getEnvProps("REGIONAL", "k.w2R.w2C"); //this.formulas.getK_w2R_w2C(); 
		this.coeffs[ 61 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.w2CP"); //this.continental.getK_aCP_w2CP(); 
		this.coeffs[ 61 ][ 49 ] = environment.getEnvProps("CONTINENTAL", "k.cwCP.w2CP"); //this.continental.getK_cwCP_w2CP(); 
		this.coeffs[ 61 ][ 57 ] = environment.getEnvProps("CONTINENTAL", "k.w1C.w2C"); //this.continental.getK_w1C_w2C(); 
		this.coeffs[ 61 ][ 59 ] = environment.getEnvProps("CONTINENTAL", "k.w2CS.w2CP"); //this.continental.getK_w2CS_w2CP(); 
		this.coeffs[ 61 ][ 73 ] = environment.getEnvProps("CONTINENTAL", "k.sd2CP.w2CP"); //this.continental.getK_sd2CP_w2CP(); 
		this.coeffs[ 61 ][ 96 ] = environment.getEnvProps("MODERATE", "k.w2M.w2C"); //this.formulas.getK_w2M_w2C(); 

		this.coeffs[ 62 ][ 50 ] = environment.getEnvProps("CONTINENTAL", "k.w0CD.sd0CD"); //this.continental.getK_w0CD_sd0CD(); 
		this.coeffs[ 62 ][ 63 ] = Double.valueOf( nano.get("kdis.sd0S.sd0D") ); //this.input.getKdis_sd0S_sd0D(); 		
		this.coeffs[ 62 ][ 64 ] = Double.valueOf( nano.get("kdis.sd0A.sd0D") ); //this.input.getKdis_sd0A_sd0D(); 		
		this.coeffs[ 62 ][ 65 ] = Double.valueOf( nano.get("kdis.sd0P.sd0D") ); //this.input.getKdis_sd0P_sd0D(); 		

		this.coeffs[ 63 ][ 51 ] = environment.getEnvProps("CONTINENTAL", "k.w0CS.sd0CS"); //this.continental.getK_w0CS_sd0CS(); 		
		this.coeffs[ 64 ][ 52 ] = environment.getEnvProps("CONTINENTAL", "k.w0CA.sd0CA"); //this.continental.getK_w0CA_sd0CA(); 		
		this.coeffs[ 64 ][ 63 ] = environment.getEnvProps("CONTINENTAL", "k.sd0CS.sd0CA"); //this.continental.getK_sd0CS_sd0CA(); 		

		this.coeffs[ 65 ][ 53 ] = environment.getEnvProps("CONTINENTAL", "k.w0CP.sd0CP"); //this.continental.getK_w0CP_sd0CP(); 		
		this.coeffs[ 65 ][ 63 ] = environment.getEnvProps("CONTINENTAL", "k.sd0CS.sd0CP"); //this.continental.getK_sd0CS_sd0CP(); 		

		this.coeffs[ 66 ][ 54 ] = environment.getEnvProps("CONTINENTAL", "k.w1CD.sd1CD"); //this.formulas.getK_w1C_sd1C(); 		
		this.coeffs[ 66 ][ 67 ] = Double.valueOf( nano.get("kdis.sd1S.sd1D") ); //this.input.getKdis_sd1S_sd1D(); 		
		this.coeffs[ 66 ][ 68 ] = Double.valueOf( nano.get("kdis.sd1A.sd1D") ); //this.input.getKdis_sd1A_sd1D(); 		
		this.coeffs[ 66 ][ 69 ] = Double.valueOf( nano.get("kdis.sd1P.sd1D") ); //this.input.getKdis_sd1P_sd1D(); 		

		this.coeffs[ 67 ][ 55 ] = environment.getEnvProps("CONTINENTAL", "k.w1CS.sd1CS"); //this.continental.getK_w1CS_sd1CS(); 		

		this.coeffs[ 68 ][ 56 ] = environment.getEnvProps("CONTINENTAL", "k.w1CA.sd1CA"); //this.continental.getK_w1CA_sd1CA(); 		
		this.coeffs[ 68 ][ 67 ] = environment.getEnvProps("CONTINENTAL", "k.sd1CS.sd1CA"); //this.continental.getK_sd1CS_sd1CA(); 		

		this.coeffs[ 69 ][ 57 ] = environment.getEnvProps("CONTINENTAL", "k.w1CP.sd1CP"); //this.continental.getK_w1CP_sd1CP(); 		
		this.coeffs[ 69 ][ 67 ] = environment.getEnvProps("CONTINENTAL", "k.sd1CS.sd1CP"); //this.continental.getK_sd1CS_sd1CP(); 		

		this.coeffs[ 70 ][ 58 ] = environment.getEnvProps("CONTINENTAL", "k.w2CD.sd2CD"); //this.formulas.getK_w2C_sd2C(); 		
		this.coeffs[ 70 ][ 71 ] = Double.valueOf( nano.get("kdis.sd2S.sd2D") ); //this.input.getKdis_sd2S_sd2D(); 		
		this.coeffs[ 70 ][ 72 ] = Double.valueOf( nano.get("kdis.sd2A.sd2D") ); //this.input.getKdis_sd2A_sd2D(); 		
		this.coeffs[ 70 ][ 73 ] = Double.valueOf( nano.get("kdis.sd2P.sd2D") ); //this.input.getKdis_sd2P_sd2D(); 		

		this.coeffs[ 71 ][ 59 ] = environment.getEnvProps("CONTINENTAL", "k.w2CS.sd2CS"); //this.continental.getK_w2CS_sd2CS(); 		

		this.coeffs[ 72 ][ 60 ] = environment.getEnvProps("CONTINENTAL", "k.w2CA.sd2CA"); //this.continental.getK_w2CA_sd2CA(); 		
		this.coeffs[ 72 ][ 71 ] = environment.getEnvProps("CONTINENTAL", "k.sd2CS.sd2CA"); //this.continental.getK_sd2CS_sd2CA(); 		

		this.coeffs[ 73 ][ 61 ] = environment.getEnvProps("CONTINENTAL", "k.w2CP.sd2CP"); //this.continental.getK_w2CP_sd2CP(); 		
		this.coeffs[ 73 ][ 71 ] = environment.getEnvProps("CONTINENTAL", "k.sd2CS.sd2CP"); //this.continental.getK_sd2CS_sd2CP(); 		
		
		this.coeffs[ 74 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aCG.s1CD"); //this.formulas.getK_aC_s1C(); 		
		this.coeffs[ 74 ][ 75 ] = Double.valueOf( nano.get("kdis.s1S.s1D") ); //this.input.getKdis_s1S_s1D(); 		
		this.coeffs[ 74 ][ 76 ] = Double.valueOf( nano.get("kdis.s1A.s1D") ); //this.input.getKdis_s1A_s1D();	
		this.coeffs[ 74 ][ 77 ] = Double.valueOf( nano.get("kdis.s1P.s1D") ); //this.input.getKdis_s1P_s1D();		

		this.coeffs[ 75 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.s1CS"); //this.continental.getK_aCS_s1CS(); 		
		this.coeffs[ 75 ][ 47 ] = environment.getEnvProps("CONTINENTAL", "k.cwCS.s1CS"); //this.continental.getK_cwCS_s1CS(); 		

		this.coeffs[ 76 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.s1CA"); //this.continental.getK_aCA_s1CA(); 		
		this.coeffs[ 76 ][ 48 ] = environment.getEnvProps("CONTINENTAL", "k.cwCA.s1CA"); //this.continental.getK_cwCA_s1CA(); 		
		this.coeffs[ 76 ][ 75 ] = environment.getEnvProps("CONTINENTAL", "k.s1CS.s1CA"); //this.continental.getK_s1CS_s1CA(); 		

		this.coeffs[ 77 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.s1CP"); //this.continental.getK_aCP_s1CP(); 		
		this.coeffs[ 77 ][ 49 ] = environment.getEnvProps("CONTINENTAL", "k.cwCP.s1CP"); //this.continental.getK_cwCP_s1CP(); 		
		this.coeffs[ 77 ][ 75 ] = environment.getEnvProps("CONTINENTAL", "k.s1CS.s1CP"); //this.continental.getK_s1CS_s1CP(); 		

		this.coeffs[ 78 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aCG.s2CD"); //his.formulas.getK_aC_s2C(); 		
		this.coeffs[ 78 ][ 79 ] = Double.valueOf( nano.get("kdis.s2S.s2D") ); //this.input.getKdis_s2S_s2D(); 		
		this.coeffs[ 78 ][ 80 ] = Double.valueOf( nano.get("kdis.s2A.s2D") ); //this.input.getKdis_s2A_s2D();	
		this.coeffs[ 78 ][ 81 ] = Double.valueOf( nano.get("kdis.s2P.s2D") ); //this.input.getKdis_s2P_s2D();		

		this.coeffs[ 79 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.s2CS"); //this.continental.getK_aCS_s2CS(); 		
		this.coeffs[ 79 ][ 47 ] = environment.getEnvProps("CONTINENTAL", "k.cwCS.s2CS"); //this.continental.getK_cwCS_s2CS(); 		

		this.coeffs[ 80 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.s2CA"); //this.continental.getK_aCA_s2CA(); 		
		this.coeffs[ 80 ][ 48 ] = environment.getEnvProps("CONTINENTAL", "k.cwCA.s2CA"); //this.continental.getK_cwCA_s2CA(); 		
		this.coeffs[ 80 ][ 79 ] = environment.getEnvProps("CONTINENTAL", "k.s2CS.s2CA"); //this.continental.getK_s2CS_s2CA(); 		

		this.coeffs[ 81 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.s2CP"); //this.continental.getK_aCP_s2CP(); 		
		this.coeffs[ 81 ][ 49 ] = environment.getEnvProps("CONTINENTAL", "k.cwCP.s2CP"); //this.continental.getK_cwCP_s2CP(); 		
		this.coeffs[ 81 ][ 79 ] = environment.getEnvProps("CONTINENTAL", "k.s2CS.s2CP"); //this.continental.getK_s2CS_s2CP(); 		

		this.coeffs[ 82 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aCG.s3CD"); //this.formulas.getK_aC_s3C(); 		
		this.coeffs[ 82 ][ 83 ] = Double.valueOf( nano.get("kdis.s3S.s3D") ); //this.input.getKdis_s3S_s3D(); 		
		this.coeffs[ 82 ][ 84 ] = Double.valueOf( nano.get("kdis.s3A.s3D") ); //this.input.getKdis_s3A_s3D();	
		this.coeffs[ 82 ][ 85 ] = Double.valueOf( nano.get("kdis.s3P.s3D") ); //this.input.getKdis_s3P_s3D();		

		this.coeffs[ 83 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aCS.s3CS"); //this.continental.getK_aCS_s3CS(); 		
		this.coeffs[ 83 ][ 47 ] = environment.getEnvProps("CONTINENTAL", "k.cwCS.s3CS"); //this.continental.getK_cwCS_s3CS(); 		

		this.coeffs[ 84 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aCA.s3CA"); //this.continental.getK_aCA_s3CA(); 		
		this.coeffs[ 84 ][ 48 ] = environment.getEnvProps("CONTINENTAL", "k.cwCA.s3CA"); //this.continental.getK_cwCA_s3CA(); 		
		this.coeffs[ 84 ][ 83 ] = environment.getEnvProps("CONTINENTAL", "k.s3CS.s3CA"); //this.continental.getK_s3CS_s3CA(); 		

		this.coeffs[ 85 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aCP.s3CP"); //this.continental.getK_aCP_s3CP(); 		
		this.coeffs[ 85 ][ 49 ] = environment.getEnvProps("CONTINENTAL", "k.cwCP.s3CP"); //this.continental.getK_cwCP_s3CP(); 		
		this.coeffs[ 85 ][ 83 ] = environment.getEnvProps("CONTINENTAL", "k.s3CS.s3CP"); //this.continental.getK_s3CS_s3CP(); 		

		this.coeffs[ 86 ][ 43 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aM"); //this.continental.getK_aC_aM(); 		
		
		this.coeffs[ 86 ][ 93 ] = environment.getEnvProps("MODERATE", "k.w2MD.aMG"); //this.formulas.getK_w2M_aM(); 		
		this.coeffs[ 86 ][ 105 ] = environment.getEnvProps("MODERATE", "k.sMD.aMG"); //this.formulas.getK_sM_aM(); 		
		
		this.coeffs[ 86 ][ 109 ] = environment.getEnvProps("ARCTIC", "k.aA.aM"); //this.global.getK_aA_aM(); 		
		this.coeffs[ 86 ][ 132 ] = environment.getEnvProps("TROPICAL", "k.aT.aM"); //this.global.getK_aT_aM(); 		

		this.coeffs[ 87 ][ 44 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aM"); //this.continental.getK_aC_aM(); 		
		this.coeffs[ 87 ][ 110 ] = environment.getEnvProps("ARCTIC", "k.aA.aM"); //this.global.getK_aA_aM(); 		
		this.coeffs[ 87 ][ 133 ] = environment.getEnvProps("TROPICAL", "k.aT.aM"); //this.global.getK_aT_aM(); 		

		this.coeffs[ 88 ][ 45 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aM"); //this.continental.getK_aC_aM(); 		
		this.coeffs[ 88 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aMS.aMA"); //this.global.getK_aMS_aMA(); 		
		this.coeffs[ 88 ][ 111 ] = environment.getEnvProps("ARCTIC", "k.aA.aM"); //this.global.getK_aA_aM(); 		
		this.coeffs[ 88 ][ 134 ] = environment.getEnvProps("TROPICAL", "k.aT.aM"); //this.global.getK_aT_aM(); 		

		this.coeffs[ 89 ][ 46 ] = environment.getEnvProps("CONTINENTAL", "k.aC.aM"); //this.continental.getK_aC_aM(); 		
		this.coeffs[ 89 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aMS.aMP"); // this.global.getK_aMS_aMP(); 		
		this.coeffs[ 89 ][ 112 ] = environment.getEnvProps("ARCTIC", "k.aA.aM"); //this.global.getK_aA_aM(); 		
		this.coeffs[ 89 ][ 135 ] = environment.getEnvProps("TROPICAL", "k.aT.aM"); //this.global.getK_aT_aM(); 		

		this.coeffs[ 90 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aMS.cwMS"); //this.global.getK_aMS_cwMS(); 		

		this.coeffs[ 91 ][ 88 ] = environment.getEnvProps("MODERATE", "k.aMA.cwMA"); //this.global.getK_aMA_cwMA(); 		

		this.coeffs[ 92 ][ 89 ] = environment.getEnvProps("MODERATE", "k.aMP.cwMP"); //this.global.getK_aMP_cwMP(); 		

		this.coeffs[ 93 ][ 58 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2M"); //this.continental.getK_w2C_w2M(); 		
		this.coeffs[ 93 ][ 86 ] = environment.getEnvProps("MODERATE", "k.aMG.w2MD"); //this.global.getK_aM_w2M(); 		
		this.coeffs[ 93 ][ 94 ] = Double.valueOf( nano.get("kdis.w2S.w2D") ); //this.input.getKdis_w2S_w2D(); 		
		this.coeffs[ 93 ][ 95 ] = Double.valueOf( nano.get("kdis.w2A.w2D") ); //this.input.getKdis_w2A_w2D();	
		this.coeffs[ 93 ][ 96 ] = Double.valueOf( nano.get("kdis.w2P.w2D") ); //this.input.getKdis_w2P_w2D();		
		this.coeffs[ 93 ][ 97 ] = environment.getEnvProps("MODERATE", "k.w3M.w2M"); //this.formulas.getK_w3M_w2M(); 	
		this.coeffs[ 93 ][ 105 ] = environment.getEnvProps("MODERATE", "k.sMD.w2MD"); //this.formulas.getK_sM_w2M(); 
		this.coeffs[ 93 ][ 116 ] = environment.getEnvProps("ARCTIC", "k.w2A.w2M"); // this.formulas.getK_w2A_w2M(); 
		this.coeffs[ 93 ][ 139 ] = environment.getEnvProps("TROPICAL", "k.w2T.w2M"); //this.formulas.getK_w2T_w2M(); 

		this.coeffs[ 94 ][ 59 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2M"); //this.continental.getK_w2C_w2M(); 		
		this.coeffs[ 94 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aMS.w2MS"); //this.global.getK_aMS_w2MS(); 		
		this.coeffs[ 94 ][ 90 ] = environment.getEnvProps("MODERATE", "k.cwMS.w2MS"); //this.global.getK_cwMS_w2MS(); 		
		this.coeffs[ 94 ][ 98 ] = environment.getEnvProps("MODERATE", "k.w3M.w2M"); //this.global.getK_w3M_w2M(); 		
		this.coeffs[ 94 ][ 106 ] = environment.getEnvProps("MODERATE", "k.sMS.w2MS"); //this.global.getK_sMS_w2MS(); 		
		this.coeffs[ 94 ][ 117 ] = environment.getEnvProps("ARCTIC", "k.w2A.w2M"); //this.global.getK_w2A_w2M(); 		
		this.coeffs[ 94 ][ 140 ] = environment.getEnvProps("TROPICAL", "k.w2T.w2M"); //this.global.getK_w2T_w2M(); 		

		this.coeffs[ 95 ][ 60 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2M"); //this.continental.getK_w2C_w2M(); 						
		this.coeffs[ 95 ][ 88 ] = environment.getEnvProps("MODERATE", "k.aMA.w2MA"); //this.global.getK_aMA_w2MA(); 	
		this.coeffs[ 95 ][ 91 ] = environment.getEnvProps("MODERATE", "k.cwMA.w2MA"); //this.global.getK_cwMA_w2MA(); 	
		this.coeffs[ 95 ][ 94 ] = environment.getEnvProps("MODERATE", "k.w2MS.w2MA"); //this.global.getK_w2MS_w2MA(); 	
		this.coeffs[ 95 ][ 99 ] = environment.getEnvProps("MODERATE", "k.w3M.w2M"); //this.global.getK_w3M_w2M(); 	
		this.coeffs[ 95 ][ 107 ] = environment.getEnvProps("MODERATE", "k.sMA.w2MA"); //this.global.getK_sMA_w2MA(); 	
		this.coeffs[ 95 ][ 118 ] = environment.getEnvProps("ARCTIC", "k.w2A.w2M"); //this.global.getK_w2A_w2M(); 	
		this.coeffs[ 95 ][ 141 ] = environment.getEnvProps("TROPICAL", "k.w2T.w2M"); //this.global.getK_w2T_w2M(); 		

		this.coeffs[ 96 ][ 61 ] = environment.getEnvProps("CONTINENTAL", "k.w2C.w2M"); //this.continental.getK_w2C_w2M(); 						
		this.coeffs[ 96 ][ 89 ] = environment.getEnvProps("MODERATE", "k.aMP.w2MP"); //this.global.getK_aMP_w2MP(); 	
		this.coeffs[ 96 ][ 92 ] = environment.getEnvProps("MODERATE", "k.cwMP.w2MP"); //this.global.getK_cwMP_w2MP(); 	
		this.coeffs[ 96 ][ 94 ] = environment.getEnvProps("MODERATE", "k.w2MS.w2MP"); //this.global.getK_w2MS_w2MP(); 	
		this.coeffs[ 96 ][ 100 ] = environment.getEnvProps("MODERATE", "k.w3M.w2M"); //this.global.getK_w3M_w2M(); 	
		this.coeffs[ 96 ][ 108 ] = environment.getEnvProps("MODERATE", "k.sMP.w2MP"); //this.global.getK_sMP_w2MP(); 	
		this.coeffs[ 96 ][ 119 ] = environment.getEnvProps("ARCTIC", "k.w2A.w2M"); //this.global.getK_w2A_w2M(); 	
		this.coeffs[ 96 ][ 142 ] = environment.getEnvProps("TROPICAL", "k.w2T.w2M"); //this.global.getK_w2T_w2M(); 		

		this.coeffs[ 97 ][ 93 ] = environment.getEnvProps("MODERATE", "k.w2M.w3M"); //this.global.getK_w2M_w3M(); 	
		this.coeffs[ 97 ][ 98 ] = Double.valueOf( nano.get("kdis.w3S.w3D") ); //this.input.getKdis_w3S_w3D(); 		
		this.coeffs[ 97 ][ 99 ] = Double.valueOf( nano.get("kdis.w3A.w3D") ); //this.input.getKdis_w3A_w3D();	
		this.coeffs[ 97 ][ 100 ] = Double.valueOf( nano.get("kdis.w3P.w3D") ); //this.input.getKdis_w3P_w3D();		
		this.coeffs[ 97 ][ 101 ] = environment.getEnvProps("MODERATE", "k.sdMD.w3MD"); //this.global.getK_sdM_w3M();		
		this.coeffs[ 97 ][ 120 ] = environment.getEnvProps("ARCTIC", "k.w3A.w3M"); //this.global.getK_w3A_w3M();		
		this.coeffs[ 97 ][ 142 ] = environment.getEnvProps("TROPICAL", "k.w3T.w3M"); //this.global.getK_w3T_w3M(); 		

		this.coeffs[ 98 ][ 94 ] = environment.getEnvProps("MODERATE", "k.w2MS.w3MS"); //this.global.getK_w2MS_w3MS(); 	
		this.coeffs[ 98 ][ 102 ] = environment.getEnvProps("MODERATE", "k.sdMS.w3MS"); //this.global.getK_sdMS_w3MS(); 	
		this.coeffs[ 98 ][ 121 ] = environment.getEnvProps("ARCTIC", "k.w3A.w3M"); //this.global.getK_w3A_w3M(); 	
		this.coeffs[ 98 ][ 144 ] = environment.getEnvProps("TROPICAL", "k.w3T.w3M"); //this.global.getK_w3T_w3M(); 		

		this.coeffs[ 99 ][ 95 ] = environment.getEnvProps("MODERATE", "k.w2MA.w3MA"); //this.global.getK_w2MA_w3MA(); 	
		this.coeffs[ 99 ][ 98 ] = environment.getEnvProps("MODERATE", "k.w3MS.w3MA"); //this.global.getK_w3MS_w3MA(); 	
		this.coeffs[ 99 ][ 103 ] = environment.getEnvProps("MODERATE", "k.sdMA.w3MA"); //this.global.getK_sdMA_w3MA(); 	
		this.coeffs[ 99 ][ 122 ] = environment.getEnvProps("ARCTIC", "k.w3A.w3M"); //this.global.getK_w3A_w3M(); 	
		this.coeffs[ 99 ][ 145 ] = environment.getEnvProps("TROPICAL", "k.w3T.w3M"); //this.global.getK_w3T_w3M(); 		

		this.coeffs[ 100 ][ 96 ] = environment.getEnvProps("MODERATE", "k.w2MP.w3MP"); //this.global.getK_w2MP_w3MP(); 	
		this.coeffs[ 100 ][ 98 ] = environment.getEnvProps("MODERATE", "k.w3MS.w3MP"); //this.global.getK_w3MS_w3MP(); 	
		this.coeffs[ 100 ][ 104 ] = environment.getEnvProps("MODERATE", "k.sdMP.w3MP"); //this.global.getK_sdMP_w3MP(); 	
		this.coeffs[ 100 ][ 123 ] = environment.getEnvProps("ARCTIC", "k.w3A.w3M"); //this.global.getK_w3A_w3M(); 	
		this.coeffs[ 100 ][ 146 ] = environment.getEnvProps("TROPICAL", "k.w3T.w3M"); //this.global.getK_w3T_w3M(); 		

		this.coeffs[ 101 ][ 97 ] = environment.getEnvProps("MODERATE", "k.w3MD.sdMD"); //this.global.getK_w3M_sdM(); 		
		this.coeffs[ 101 ][ 102 ] = Double.valueOf( nano.get("kdis.sd2S.sd2D") ); //this.input.getKdis_sd2S_sd2D(); 		
		this.coeffs[ 101 ][ 103 ] = Double.valueOf( nano.get("kdis.sd2A.sd2D") ); //this.input.getKdis_sd2A_sd2D();	
		this.coeffs[ 101 ][ 104 ] = Double.valueOf( nano.get("kdis.sd2P.sd2D") ); //this.input.getKdis_sd2P_sd2D();		

		this.coeffs[ 102 ][ 98 ] = environment.getEnvProps("MODERATE", "k.w3MS.sdMS"); //this.global.getK_w3MS_sdMS(); 		

		this.coeffs[ 103 ][ 99 ] = environment.getEnvProps("MODERATE", "k.w3MA.sdMA"); //this.global.getK_w3MA_sdMA(); 		
		this.coeffs[ 103 ][ 102 ] = environment.getEnvProps("MODERATE", "k.sdMS.sdMA"); //this.global.getK_sdMS_sdMA(); 		


		this.coeffs[ 104 ][ 100 ] = environment.getEnvProps("MODERATE", "k.w3MP.sdMP"); //this.global.getK_w3MP_sdMP(); 		
		this.coeffs[ 104 ][ 102 ] = environment.getEnvProps("MODERATE", "k.sdMS.sdMP"); //this.global.getK_sdMS_sdMP(); 		

		this.coeffs[ 105 ][ 86 ] = environment.getEnvProps("MODERATE", "k.aMG.sMD"); //this.global.getK_aM_sM(); 		
		this.coeffs[ 105 ][ 106 ] = Double.valueOf( nano.get("kdis.s1S.s1D") ); //this.input.getKdis_s1S_s1D(); 		
		this.coeffs[ 105 ][ 107 ] = Double.valueOf( nano.get("kdis.s1A.s1D") ); //this.input.getKdis_s1A_s1D();	
		this.coeffs[ 105 ][ 108 ] = Double.valueOf( nano.get("kdis.s1P.s1D") ); //this.input.getKdis_s1P_s1D();		

		this.coeffs[ 106 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aMS.sMS"); //this.global.getK_aMS_sMS(); 		
		this.coeffs[ 106 ][ 90 ] = environment.getEnvProps("MODERATE", "k.cwMS.sMS"); //this.global.getK_cwMS_sMS(); 		

		this.coeffs[ 107 ][ 88 ] = environment.getEnvProps("MODERATE", "k.aMA.sMA"); //this.global.getK_aMA_sMA(); 		
		this.coeffs[ 107 ][ 91 ] = environment.getEnvProps("MODERATE", "k.cwMA.sMA"); //this.global.getK_cwMA_sMA(); 		
		this.coeffs[ 107 ][ 106 ] = environment.getEnvProps("MODERATE", "k.sMS.sMA"); //this.global.getK_sMS_sMA(); 		

		this.coeffs[ 108 ][ 89 ] = environment.getEnvProps("MODERATE", "k.aMP.sMP"); //this.global.getK_aMP_sMP(); 		
		this.coeffs[ 108 ][ 92 ] = environment.getEnvProps("MODERATE", "k.cwMP.sMP"); //this.global.getK_cwMP_sMP(); 		
		this.coeffs[ 108 ][ 106 ] = environment.getEnvProps("MODERATE", "k.sMS.sMP"); //this.global.getK_sMS_sMP(); 		

		this.coeffs[ 109 ][ 86 ] = environment.getEnvProps("MODERATE", "k.aM.aA"); //this.global.getK_aM_aA(); 		
		this.coeffs[ 109 ][ 116 ] = environment.getEnvProps("ARCTIC", "k.w2AD.aAG"); //this.global.getK_w2A_aA(); 		
		this.coeffs[ 109 ][ 128 ] = environment.getEnvProps("ARCTIC", "k.sAD.aAG"); //this.global.getK_sA_aA(); 		

		this.coeffs[ 110 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aM.aA"); //this.global.getK_aM_aA(); 		

		this.coeffs[ 111 ][ 88 ] = environment.getEnvProps("MODERATE", "k.aM.aA"); //this.global.getK_aM_aA(); 		
		this.coeffs[ 111 ][ 110 ] = environment.getEnvProps("ARCTIC", "k.aAS.aAA"); //this.global.getK_aAS_aAA(); 		

		this.coeffs[ 112 ][ 89 ] = environment.getEnvProps("MODERATE", "k.aM.aA"); //this.global.getK_aM_aA(); 		
		this.coeffs[ 112 ][ 110 ] = environment.getEnvProps("ARCTIC", "k.aAS.aAP"); //this.global.getK_aAS_aAP(); 		

		this.coeffs[ 113 ][ 110 ] = environment.getEnvProps("ARCTIC", "k.aAS.cwAS"); //this.global.getK_aAS_cwAS(); 		

		this.coeffs[ 114 ][ 111 ] = environment.getEnvProps("ARCTIC", "k.aAA.cwAA"); // this.global.getK_aAA_cwAA(); 		

		this.coeffs[ 115 ][ 112 ] = environment.getEnvProps("ARCTIC", "k.aAP.cwAP"); //this.global.getK_aAP_cwAP(); 		

		this.coeffs[ 116 ][ 93 ] = environment.getEnvProps("MODERATE", "k.w2M.w2A"); //this.global.getK_w2M_w2A(); 		
		this.coeffs[ 116 ][ 109 ] = environment.getEnvProps("ARCTIC", "k.aAG.w2AD"); //this.global.getK_aA_w2A(); 		
		this.coeffs[ 116 ][ 117 ] = Double.valueOf( nano.get("kdis.w2S.w2D") ); //this.input.getKdis_w2S_w2D(); 		
		this.coeffs[ 116 ][ 118 ] = Double.valueOf( nano.get("kdis.w2A.w2D") ); //this.input.getKdis_w2A_w2D();	
		this.coeffs[ 116 ][ 119 ] = Double.valueOf( nano.get("kdis.w2P.w2D") ); //this.input.getKdis_w2P_w2D();		
		this.coeffs[ 116 ][ 120 ] = environment.getEnvProps("ARCTIC", "k.w3A.w2A"); //this.global.getK_w3A_w2A();		
		this.coeffs[ 116 ][ 128 ] = environment.getEnvProps("ARCTIC", "k.sAD.w2AD"); //this.global.getK_sA_w2A();		

		this.coeffs[ 117 ][ 94 ] = environment.getEnvProps("MODERATE", "k.w2M.w2A"); //this.global.getK_w2M_w2A(); 		
		this.coeffs[ 117 ][ 110 ] = environment.getEnvProps("ARCTIC", "k.aAS.w2AS"); //this.global.getK_aAS_w2AS(); 		
		this.coeffs[ 117 ][ 113 ] = environment.getEnvProps("ARCTIC", "k.cwAS.w2AS"); //this.global.getK_cwAS_w2AS(); 		
		this.coeffs[ 117 ][ 121 ] = environment.getEnvProps("ARCTIC", "k.w3A.w2A"); //this.global.getK_w3A_w2A(); 		
		this.coeffs[ 117 ][ 129 ] = environment.getEnvProps("ARCTIC", "k.sAS.w2AS"); //this.global.getK_sAS_w2AS(); 		

		this.coeffs[ 118 ][ 95 ] =  environment.getEnvProps("MODERATE", "k.w2M.w2A"); //this.global.getK_w2M_w2A(); 		
		this.coeffs[ 118 ][ 111 ] = environment.getEnvProps("ARCTIC", "k.aAA.w2AA"); //this.global.getK_aAA_w2AA(); 		
		this.coeffs[ 118 ][ 114 ] = environment.getEnvProps("ARCTIC", "k.cwAA.w2AA"); //this.global.getK_cwAA_w2AA(); 		
		this.coeffs[ 118 ][ 117 ] = environment.getEnvProps("ARCTIC", "k.w2AS.w2AA"); //this.global.getK_w2AS_w2AA(); 		
		this.coeffs[ 118 ][ 122 ] = environment.getEnvProps("ARCTIC", "k.w3A.w2A"); //this.global.getK_w3A_w2A(); 		
		this.coeffs[ 118 ][ 130 ] = environment.getEnvProps("ARCTIC", "k.sAA.w2AA"); //this.global.getK_sAA_w2AA(); 		

		this.coeffs[ 119 ][ 96 ] = environment.getEnvProps("MODERATE", "k.w2M.w2A"); //this.global.getK_w2M_w2A(); 				
		this.coeffs[ 119 ][ 112 ] = environment.getEnvProps("ARCTIC", "k.aAP.w2AP"); //this.global.getK_aAP_w2AP(); 		
		this.coeffs[ 119 ][ 115 ] = environment.getEnvProps("ARCTIC", "k.cwAP.w2AP"); //this.global.getK_cwAP_w2AP(); 		
		this.coeffs[ 119 ][ 117 ] = environment.getEnvProps("ARCTIC", "k.w2AS.w2AP"); //this.global.getK_w2AS_w2AP(); 		
		this.coeffs[ 119 ][ 123 ] = environment.getEnvProps("ARCTIC", "k.w3A.w2A"); //this.global.getK_w3A_w2A(); 		
		this.coeffs[ 119 ][ 131 ] = environment.getEnvProps("ARCTIC", "k.sAP.w2AP"); //this.global.getK_sAP_w2AP(); 		

		this.coeffs[ 120 ][ 97 ] = environment.getEnvProps("MODERATE", "k.w3M.w3A"); //this.global.getK_w3M_w3A(); 				
		this.coeffs[ 120 ][ 116 ] = environment.getEnvProps("ARCTIC", "k.w2A.w3A"); //this.global.getK_w2A_w3A(); 				
		this.coeffs[ 120 ][ 121 ] = Double.valueOf( nano.get("kdis.w3S.w3D") ); //this.input.getKdis_w3S_w3D(); 		
		this.coeffs[ 120 ][ 122 ] = Double.valueOf( nano.get("kdis.w3A.w3D") ); //this.input.getKdis_w3A_w3D();	
		this.coeffs[ 120 ][ 123 ] = Double.valueOf( nano.get("kdis.w3P.w3D") ); //this.input.getKdis_w3P_w3D();		
		this.coeffs[ 120 ][ 124 ] = environment.getEnvProps("ARCTIC", "k.sdAD.w3AD"); //this.global.getK_sdA_w3A();	

		this.coeffs[ 121 ][ 117 ] = environment.getEnvProps("ARCTIC", "k.w2AS.w3AS"); //this.global.getK_w2AS_w3AS(); 				
		this.coeffs[ 121 ][ 125 ] = environment.getEnvProps("ARCTIC", "k.sdAS.w3AS"); //this.global.getK_sdAS_w3AS(); 				

		this.coeffs[ 122 ][ 118 ] = environment.getEnvProps("ARCTIC", "k.w2AA.w3AA"); //this.global.getK_w2AA_w3AA(); 					
		this.coeffs[ 122 ][ 121 ] = environment.getEnvProps("ARCTIC", "k.w3AS.w3AA"); //this.global.getK_w3AS_w3AA(); 				
		this.coeffs[ 122 ][ 126 ] = environment.getEnvProps("ARCTIC", "k.sdAA.w3AA"); //tthis.global.getK_sdAA_w3AA(); 				

		this.coeffs[ 123 ][ 119 ] = environment.getEnvProps("ARCTIC", "k.w2AP.w3AP"); //this.global.getK_w2AP_w3AP(); 					
		this.coeffs[ 123 ][ 121 ] = environment.getEnvProps("ARCTIC", "k.w3AS.w3AP"); //this.global.getK_w3AS_w3AP(); 					
		this.coeffs[ 123 ][ 127 ] = environment.getEnvProps("ARCTIC", "k.sdAP.w3AP"); //this.global.getK_sdAP_w3AP(); 					

		this.coeffs[ 124 ][ 120 ] = environment.getEnvProps("ARCTIC", "k.w3AD.sdAD"); //this.global.getK_w3A_sdA(); 					
		this.coeffs[ 124 ][ 125 ] = Double.valueOf( nano.get("kdis.sd2S.sd2D") ); //this.input.getKdis_sd2S_sd2D(); 		
		this.coeffs[ 124 ][ 126 ] = Double.valueOf( nano.get("kdis.sd2A.sd2D") ); //this.input.getKdis_sd2A_sd2D();	
		this.coeffs[ 124 ][ 127 ] = Double.valueOf( nano.get("kdis.sd2P.sd2D") ); //this.input.getKdis_sd2P_sd2D();		

		this.coeffs[ 125 ][ 121 ] = environment.getEnvProps("ARCTIC", "k.w3AS.sdAS"); //this.global.getK_w3AS_sdAS(); 					

		this.coeffs[ 126 ][ 122 ] = environment.getEnvProps("ARCTIC", "k.w3AA.sdAA"); //this.global.getK_w3AA_sdAA(); 					
		this.coeffs[ 126 ][ 125 ] = environment.getEnvProps("ARCTIC", "k.sdAS.sdAA"); //this.global.getK_sdAS_sdAA(); 				

		this.coeffs[ 127 ][ 123 ] = environment.getEnvProps("ARCTIC", "k.w3AP.sdAP"); //this.global.getK_w3AP_sdAP(); 					
		this.coeffs[ 127 ][ 125 ] = environment.getEnvProps("ARCTIC", "k.sdAS.sdAP"); //this.global.getK_sdAS_sdAP(); 					

		this.coeffs[ 128 ][ 109 ] = environment.getEnvProps("ARCTIC", "k.aAG.sAD"); //this.global.getK_aA_sA(); 					
		this.coeffs[ 128 ][ 129 ] = Double.valueOf( nano.get("kdis.s1S.s1D") ); //this.input.getKdis_s1S_s1D(); 		
		this.coeffs[ 128 ][ 130 ] = Double.valueOf( nano.get("kdis.s1A.s1D") ); //this.input.getKdis_s1A_s1D();	
		this.coeffs[ 128 ][ 131 ] = Double.valueOf( nano.get("kdis.s1P.s1D") ); //this.input.getKdis_s1P_s1D();		

		this.coeffs[ 129 ][ 110 ] = environment.getEnvProps("ARCTIC", "k.aAS.sAS"); //this.global.getK_aAS_sAS(); 					
		this.coeffs[ 129 ][ 113 ] = environment.getEnvProps("ARCTIC", "k.cwAS.sAS"); //this.global.getK_cwAS_sAS(); 					

		this.coeffs[ 130 ][ 111 ] = environment.getEnvProps("ARCTIC", "k.aAA.sAA"); //this.global.getK_aAA_sAA(); 					
		this.coeffs[ 130 ][ 114 ] = environment.getEnvProps("ARCTIC", "k.cwAA.sAA"); //this.global.getK_cwAA_sAA(); 					
		this.coeffs[ 130 ][ 129 ] = environment.getEnvProps("ARCTIC", "k.sAS.sAA"); //this.global.getK_sAS_sAA(); 					

		this.coeffs[ 131 ][ 112 ] = environment.getEnvProps("ARCTIC", "k.aAP.sAP"); //this.global.getK_aAP_sAP(); 					
		this.coeffs[ 131 ][ 115 ] = environment.getEnvProps("ARCTIC", "k.cwAP.sAP"); //this.global.getK_cwAP_sAP(); 					
		this.coeffs[ 131 ][ 129 ] =  environment.getEnvProps("ARCTIC", "k.sAS.sAP"); //this.global.getK_sAS_sAP(); 					

		this.coeffs[ 132 ][ 86 ] =  environment.getEnvProps("MODERATE", "k.aM.aT"); //this.global.getK_aM_aT(); 					
		this.coeffs[ 132 ][ 139 ] = environment.getEnvProps("TROPICAL", "k.w2TD.aTG"); //this.global.getK_w2T_aT(); 					
		this.coeffs[ 132 ][ 151 ] = environment.getEnvProps("TROPICAL", "k.sTD.aTG"); //this.global.getK_sT_aT(); 					

		this.coeffs[ 133 ][ 87 ] = environment.getEnvProps("MODERATE", "k.aM.aT"); //this.global.getK_aM_aT(); 					

		this.coeffs[ 134 ][ 88 ] = environment.getEnvProps("MODERATE", "k.aM.aT"); //this.global.getK_aM_aT(); 		
		this.coeffs[ 134 ][ 133 ] = environment.getEnvProps("TROPICAL", "k.aTS.aTA"); //this.global.getK_ATS_ATA(); 		

		this.coeffs[ 135 ][ 89 ] = environment.getEnvProps("MODERATE", "k.aM.aT"); //this.global.getK_aM_aT(); 		
		this.coeffs[ 135 ][ 133 ] = environment.getEnvProps("TROPICAL", "k.aTS.aTP"); //this.global.getK_ATS_ATP(); 		

		this.coeffs[ 136 ][ 133 ] = environment.getEnvProps("TROPICAL", "k.aTS.cwTS"); //this.global.getK_ATS_cwTS(); 		

		this.coeffs[ 137 ][ 134 ] = environment.getEnvProps("TROPICAL", "k.aTA.cwTA"); //this.global.getK_ATA_cwTA(); 		

		this.coeffs[ 138 ][ 135 ] =  environment.getEnvProps("TROPICAL", "k.aTP.cwTP"); //this.global.getK_ATP_cwTP(); 		

		this.coeffs[ 139 ][ 132 ] = environment.getEnvProps("TROPICAL", "k.aTG.w2TD"); //this.global.getK_aT_w2T(); 		
		this.coeffs[ 139 ][ 140 ] = Double.valueOf( nano.get("kdis.w2S.w2D") ); //this.input.getKdis_w2S_w2D(); 		
		this.coeffs[ 139 ][ 141 ] = Double.valueOf( nano.get("kdis.w2A.w2D") ); //this.input.getKdis_w2A_w2D();	
		this.coeffs[ 139 ][ 142 ] = Double.valueOf( nano.get("kdis.w2P.w2D") ); //this.input.getKdis_w2P_w2D();		
		this.coeffs[ 139 ][ 143 ] = environment.getEnvProps("TROPICAL", "k.w3T.w2T"); //this.global.getK_w3T_w2T();	
		this.coeffs[ 139 ][ 151 ] = environment.getEnvProps("TROPICAL", "k.sTD.w2TD"); //this.global.getK_sT_w2T();	

		this.coeffs[ 140 ][ 133 ] = environment.getEnvProps("TROPICAL", "k.aTS.w2TS"); //this.global.getK_ATS_w2TS(); 		
		this.coeffs[ 140 ][ 136 ] = environment.getEnvProps("TROPICAL", "k.cwTS.w2TS"); //this.global.getK_cwTS_w2TS(); 			
		this.coeffs[ 140 ][ 144 ] = environment.getEnvProps("TROPICAL", "k.w3T.w2T"); //this.global.getK_w3T_w2T(); 		
		this.coeffs[ 140 ][ 152 ] = environment.getEnvProps("TROPICAL", "k.sTS.w2TS"); //this.global.getK_sTS_w2TS();	

		this.coeffs[ 141 ][ 134 ] = environment.getEnvProps("TROPICAL", "k.aTA.w2TA"); //this.global.getK_ATA_w2TA(); 		
		this.coeffs[ 141 ][ 137 ] = environment.getEnvProps("TROPICAL", "k.cwTA.w2TA"); //this.global.getK_cwTA_w2TA(); 		
		this.coeffs[ 141 ][ 140 ] = environment.getEnvProps("TROPICAL", "k.w2TS.w2TA"); //this.global.getK_w2TS_w2TA(); 		
		this.coeffs[ 141 ][ 145 ] = environment.getEnvProps("TROPICAL", "k.w3T.w2T"); //this.global.getK_w3T_w2T(); 		
		this.coeffs[ 141 ][ 153 ] = environment.getEnvProps("TROPICAL", "k.sTA.w2TA"); //this.global.getK_sTA_w2TA(); 		

		this.coeffs[ 142 ][ 135 ] = environment.getEnvProps("TROPICAL", "k.aTP.w2TP"); //this.global.getK_ATP_w2TP(); 		
		this.coeffs[ 142 ][ 138 ] = environment.getEnvProps("TROPICAL", "k.cwTP.w2TP"); //this.global.getK_cwTP_w2TP(); 		
		this.coeffs[ 142 ][ 140 ] = environment.getEnvProps("TROPICAL", "k.w2TS.w2TP"); //this.global.getK_w2TS_w2TP(); 		
		this.coeffs[ 142 ][ 146 ] = environment.getEnvProps("TROPICAL", "k.w3T.w2T"); //this.global.getK_w3T_w2T(); 		
		this.coeffs[ 142 ][ 154 ] = environment.getEnvProps("TROPICAL", "k.sTP.w2TP"); //this.global.getK_sTP_w2TP(); 		

		this.coeffs[ 143 ][ 97 ] = environment.getEnvProps("MODERATE", "k.w3M.w3T"); //this.global.getK_w3M_w3T(); 		
		this.coeffs[ 143 ][ 139 ] = environment.getEnvProps("TROPICAL", "k.w2T.w3T"); //this.global.getK_w2T_w3T(); 	
		this.coeffs[ 143 ][ 144 ] = Double.valueOf( nano.get("kdis.w3S.w3D") ); //this.input.getKdis_w3S_w3D(); 		
		this.coeffs[ 143 ][ 145 ] = Double.valueOf( nano.get("kdis.w3A.w3D") ); //this.input.getKdis_w3A_w3D();	
		this.coeffs[ 143 ][ 146 ] = Double.valueOf( nano.get("kdis.w3P.w3D") ); //this.input.getKdis_w3P_w3D();		
		this.coeffs[ 143 ][ 147 ] = environment.getEnvProps("TROPICAL", "k.sdTD.w3TD"); //this.global.getK_sdT_w3T();		

		this.coeffs[ 144 ][ 98 ] = environment.getEnvProps("MODERATE", "k.w3M.w3T"); //this.global.getK_w3M_w3T(); 		
		this.coeffs[ 144 ][ 140 ] = environment.getEnvProps("TROPICAL", "k.w2TS.w3TS"); //this.global.getK_w2TS_w3TS(); 		
		this.coeffs[ 144 ][ 148 ] = environment.getEnvProps("TROPICAL", "k.sdTS.w3TS"); //this.global.getK_sdTS_w3TS(); 		

		this.coeffs[ 145 ][ 99 ] = environment.getEnvProps("MODERATE", "k.w3M.w3T"); //this.global.getK_w3M_w3T(); 				
		this.coeffs[ 145 ][ 141 ] = environment.getEnvProps("TROPICAL", "k.w2TA.w3TA"); //this.global.getK_w2TA_w3TA(); 		
		this.coeffs[ 145 ][ 144 ] = environment.getEnvProps("TROPICAL", "k.w3TS.w3TA"); //this.global.getK_w3TS_w3TA(); 		
		this.coeffs[ 145 ][ 149 ] = environment.getEnvProps("TROPICAL", "k.sdTA.w3TA"); //this.global.getK_sdTA_w3TA(); 		

		this.coeffs[ 146 ][ 100 ] = environment.getEnvProps("MODERATE", "k.w3M.w3T"); //this.global.getK_w3M_w3T(); 				
		this.coeffs[ 146 ][ 142 ] = environment.getEnvProps("TROPICAL", "k.w2TP.w3TP"); //this.global.getK_w2TA_w3TA(); 		
		this.coeffs[ 146 ][ 144 ] = environment.getEnvProps("TROPICAL", "k.w3TS.w3TP"); //this.global.getK_w3TS_w3TP(); 		
		this.coeffs[ 146 ][ 150 ] = environment.getEnvProps("TROPICAL", "k.sdTP.w3TP"); //this.global.getK_sdTP_w3TP(); 		

		this.coeffs[ 147 ][ 143 ] = environment.getEnvProps("TROPICAL", "k.w3TD.sdTD"); //this.global.getK_w3T_sdT(); 				
		this.coeffs[ 147 ][ 148 ] = Double.valueOf( nano.get("kdis.sd2S.sd2D") ); //this.input.getKdis_sd2S_sd2D(); 		
		this.coeffs[ 147 ][ 149 ] = Double.valueOf( nano.get("kdis.sd2A.sd2D") ); //this.input.getKdis_sd2A_sd2D();	
		this.coeffs[ 147 ][ 150 ] = Double.valueOf( nano.get("kdis.sd2P.sd2D") ); //this.input.getKdis_sd2P_sd2D();		

		this.coeffs[ 148 ][ 144 ] = environment.getEnvProps("TROPICAL", "k.w3TS.sdTS"); //this.global.getK_w3TS_sdTS(); 				

		this.coeffs[ 149 ][ 145 ] = environment.getEnvProps("TROPICAL", "k.w3TA.sdTA"); //this.global.getK_w3TA_sdTA(); 				
		this.coeffs[ 149 ][ 148 ] = environment.getEnvProps("TROPICAL", "k.sdTS.sdTA"); //this.global.getK_sdTS_sdTA(); 				

		this.coeffs[ 150 ][ 146 ] = environment.getEnvProps("TROPICAL", "k.w3TP.sdTP"); //this.global.getK_w3TP_sdTP(); 				
		this.coeffs[ 150 ][ 148 ] = environment.getEnvProps("TROPICAL", "k.sdTS.sdTP"); //this.global.getK_sdTS_sdTP(); 				

		this.coeffs[ 150 ][ 146 ] = environment.getEnvProps("TROPICAL", "k.w3TP.sdTP"); //this.global.getK_w3TP_sdTP(); 	
		
		this.coeffs[ 151 ][ 132 ] = environment.getEnvProps("TROPICAL", "k.aTG.sTD"); //this.global.getK_aT_sT(); 				
		this.coeffs[ 151 ][ 152 ] = Double.valueOf( nano.get("kdis.s1S.s1D") ); //this.input.getKdis_s1S_s1D(); 		
		this.coeffs[ 151 ][ 153 ] = Double.valueOf( nano.get("kdis.s1A.s1D") ); //this.input.getKdis_s1A_s1D();	
		this.coeffs[ 151 ][ 154 ] = Double.valueOf( nano.get("kdis.s1P.s1D") ); //this.input.getKdis_s1P_s1D();		

		this.coeffs[ 152 ][ 133 ] = environment.getEnvProps("TROPICAL", "k.aTS.sTS"); //this.global.getK_ATS_sTS(); 				
		this.coeffs[ 152 ][ 136 ] = environment.getEnvProps("TROPICAL", "k.cwTS.sTS"); //this.global.getK_cwTS_sTS(); 				

		this.coeffs[ 153 ][ 134 ] = environment.getEnvProps("TROPICAL", "k.aTA.sTA"); //his.global.getK_ATA_sTA(); 				
		this.coeffs[ 153 ][ 137 ] = environment.getEnvProps("TROPICAL", "k.cwTA.sTA"); //this.global.getK_cwTA_sTA(); 				
		this.coeffs[ 153 ][ 152 ] = environment.getEnvProps("TROPICAL", "k.sTS.sTA"); //this.global.getK_sTS_sTA(); 				

		this.coeffs[ 154 ][ 135 ] = environment.getEnvProps("TROPICAL", "k.aTP.sTP"); //this.global.getK_ATP_sTP(); 				
		this.coeffs[ 154 ][ 138 ] = environment.getEnvProps("TROPICAL", "k.cwTP.sTP"); //this.global.getK_cwTP_sTP(); 				
		this.coeffs[ 154 ][ 152 ] = environment.getEnvProps("TROPICAL", "k.sTS.sTP"); //this.global.getK_sTS_sTP(); 				
		
		//Compute the diagonal
		for ( int i = 0; i < coeffs.length; i++ ) {
			double dSum = 0.0;
			for ( int j = 0; j < coeffs.length; j++ ) {				
				if ( i != j)	
					dSum += coeffs[ j ][ i ];
			}
			
			
			if (i == 0) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.aRG") - dSum; //-this.regional.getK_aRG()-dSum; 
			else if (i == 1) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.aRS") - dSum; //-this.regional.getK_aRS()-dSum;
			else if (i== 2) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.aRA") - dSum; //-this.regional.getK_aRA()-dSum;  
			else if (i== 3) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.aRP") - dSum; //-this.regional.getK_aRP()-dSum;  
			else if (i== 4) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 5) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 6) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 7) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w0RD") - dSum; //-this.regional.getK_w0RD()-dSum;  
			else if (i== 8) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w0RS") - dSum; //-this.regional.getK_w0RS()-dSum;  
			else if (i== 9) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w0RA") - dSum; //-this.regional.getK_w0RA()-dSum;  
			else if (i== 10) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w0RP") - dSum; //-this.regional.getK_w0RP()-dSum;  
			else if (i== 11) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "KDEG.w1R") - dSum; //-this.regional.getK_w1RD()-dSum;   //it is the same as k.w1RD
			else if (i== 12) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w1RS") - dSum; //-this.regional.getK_w1RS()-dSum;  
			else if (i== 13) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w1RA") - dSum; //-this.regional.getK_w1RA()-dSum;  
			else if (i== 14) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w1RP") - dSum; //-this.regional.getK_w1RP()-dSum;  
			else if (i== 15) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w2RD") - dSum; //-this.regional.getK_w2RD()-dSum;  
			else if (i== 16) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w2RS") - dSum; //-this.regional.getK_w2RS()-dSum;  
			else if (i== 17) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w2RA") - dSum; //-this.regional.getK_w2RA()-dSum;  
			else if (i== 18) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.w2RP") - dSum; //-this.regional.getK_w2RP()-dSum;  
			else if (i== 19) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd0RD") - dSum; //-this.regional.getK_sd0RD()-dSum;  
			else if (i== 20) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd0RS") - dSum; //-this.regional.getK_sd0RS()-dSum;  
			else if (i== 21) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd0RA") - dSum; //-this.regional.getK_sd0RA()-dSum;  
			else if (i== 22) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd0RP") - dSum; //-this.regional.getK_sd0RP()-dSum;  
			else if (i== 23) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd1RD") - dSum; //-this.regional.getK_sd1RD()-dSum;  
			else if (i== 24) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd1RS") - dSum; //-this.regional.getK_sd1RS()-dSum;  
			else if (i== 25) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd1RA") - dSum; //-this.regional.getK_sd1RA()-dSum;  
			else if (i== 26) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd1RP") - dSum; //-this.regional.getK_sd1RP()-dSum;  
			else if (i== 27) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd2RD") - dSum; //-this.regional.getK_sd2RD()-dSum;  
			else if (i== 28) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd2RS") - dSum; //-this.regional.getK_sd2RS()-dSum;  
			else if (i== 29) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd2RA") - dSum; //-this.regional.getK_sd2RA()-dSum;  
			else if (i== 30) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.sd2RP") - dSum; //-this.regional.getK_sd2RP()-dSum;  
			else if (i== 31) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s1RD") - dSum; //-this.regional.getK_s1RD()-dSum;  
			else if (i== 32) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s1RS") - dSum; //-this.regional.getK_s1RS()-dSum;  
			else if (i== 33) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s1RA") - dSum; //-this.regional.getK_s1RA()-dSum;  
			else if (i== 34) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s1RP") - dSum; //-this.regional.getK_s1RP()-dSum;  
			else if (i== 35) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s2RD") - dSum; //-this.regional.getK_s2RD()-dSum;  
			else if (i== 36) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s2RS") - dSum; //-this.regional.getK_s2RS()-dSum;  
			else if (i== 37) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s2RA") - dSum; //-this.regional.getK_s2RA()-dSum;  
			else if (i== 38) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s2RP") - dSum; //-this.regional.getK_s2RP()-dSum; 
			else if (i== 39) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s3RD") - dSum; //-this.regional.getK_s3RD()-dSum;  
			else if (i== 40) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s3RS") - dSum; //-this.regional.getK_s3RS()-dSum;  
			else if (i== 41) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s3RA") - dSum; //-this.regional.getK_s3RA()-dSum;  
			else if (i== 42) this.coeffs[ i ][ i ] = -environment.getEnvProps("REGIONAL", "k.s3RP") - dSum; //-this.regional.getK_s3RP()-dSum;  
			else if (i== 43) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.aC") - dSum; //-this.continental.getK_aC()-dSum;  
			else if (i== 44) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.aCS") - dSum; //-this.continental.getK_aCS()-dSum;  
			else if (i== 45) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.aCA") - dSum; //-this.continental.getK_aCA()-dSum;  
			else if (i== 46) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.aCP") - dSum; //-this.continental.getK_aCP()-dSum;  
			else if (i== 47) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 48) this.coeffs[ i ][ i ] = -dSum; 
			else if (i== 49) this.coeffs[ i ][ i ] = -dSum; 
			else if (i== 50) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w0C") - dSum; //-this.continental.getK_w0C()-dSum;  
			else if (i== 51) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w0CS") - dSum; //-this.continental.getK_w0CS()-dSum;  
			else if (i== 52) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w0CA") - dSum; //-this.continental.getK_w0CA()-dSum;  
			else if (i== 53) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w0CP") - dSum; //-this.continental.getK_w0CP()-dSum;  
			else if (i== 54) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w1C") - dSum; //-this.continental.getK_w1C()-dSum;  
			else if (i== 55) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w1CS") - dSum; //-this.continental.getK_w1CS()-dSum;  
			else if (i== 56) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w1CA") - dSum; //-this.continental.getK_w1CA()-dSum;  
			else if (i== 57) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w1CP") - dSum; //-this.continental.getK_w1CP()-dSum;  
			else if (i== 58) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w2C") - dSum; //-this.continental.getK_w2C()-dSum;  
			else if (i== 59) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w2CS") - dSum; //-this.continental.getK_w2CS()-dSum;  
			else if (i== 60) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w2CA") - dSum; //-this.continental.getK_w2CA()-dSum;  
			else if (i== 61) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.w2CP") - dSum; //-this.continental.getK_w2CP()-dSum;
			else if (i== 62) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd0CD") - dSum; //-this.continental.getK_sd0CD()-dSum;  
			else if (i== 63) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd0CS") - dSum; //-this.continental.getK_sd0CS()-dSum;  
			else if (i== 64) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd0CA") - dSum; //-this.continental.getK_sd0CA()-dSum;  
			else if (i== 65) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd0CP") - dSum; //-this.continental.getK_sd0CP()-dSum;  
			else if (i== 66) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd1C") - dSum; //-this.continental.getK_sd1C()-dSum;  
			else if (i== 67) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd1CS") - dSum; //-this.continental.getK_sd1CS()-dSum;  
			else if (i== 68) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd1CA") - dSum; //-this.continental.getK_sd1CA()-dSum;  
			else if (i== 69) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd1CP") - dSum; //-this.continental.getK_sd1CP()-dSum;  
			else if (i== 70) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd2C") - dSum; //-this.continental.getK_sd2C()-dSum;  
			else if (i== 71) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd2CS") - dSum; //-this.continental.getK_sd2CS()-dSum;  
			else if (i== 72) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd2CA") - dSum; //-this.continental.getK_sd2CA()-dSum;  
			else if (i== 73) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.sd2CP") - dSum; //-this.continental.getK_sd2CP()-dSum;  
			else if (i== 74) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s1C") - dSum; //-this.continental.getK_s1C()-dSum;  
			else if (i== 75) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s1CS") - dSum; //-this.continental.getK_s1CS()-dSum;  
			else if (i== 76) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s1CA") - dSum; //-this.continental.getK_s1CA()-dSum;  
			else if (i== 77) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s1CP") - dSum; //-this.continental.getK_s1CP()-dSum;  
			else if (i== 78) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s2C") - dSum; //-this.continental.getK_s2C()-dSum;  
			else if (i== 79) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s2CS") - dSum; //-this.continental.getK_s2CS()-dSum;  
			else if (i== 80) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s2CA") - dSum; //-this.continental.getK_s2CA()-dSum;  
			else if (i== 81) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s2CP") - dSum; //-this.continental.getK_s2CP()-dSum;  
			else if (i== 82) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s3C") - dSum; //-this.continental.getK_s3C()-dSum;  
			else if (i== 83) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s3CS") - dSum; //-this.continental.getK_s3CS()-dSum;  
			else if (i== 84) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s3CA") - dSum; //-this.continental.getK_s3CA()-dSum;  
			else if (i== 85) this.coeffs[ i ][ i ] = -environment.getEnvProps("CONTINENTAL", "k.s3CP") - dSum; //-this.continental.getK_s3CP()-dSum;  
			else if (i== 86) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.aM") - dSum; //-this.global.getK_aM()-dSum;  
			else if (i== 87) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.aMS") - dSum; //-this.global.getK_aMS()-dSum;  
			else if (i== 88) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.aMA") - dSum; //-this.global.getK_aMA()-dSum;  
			else if (i== 89) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.aMP") - dSum; //-this.global.getK_aMP()-dSum;  
			else if (i== 90) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 91) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 92) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 93) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MD") - dSum; //-this.global.getK_w2M()-dSum;  
			else if (i== 94) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MS") - dSum; //-this.global.getK_w2MS()-dSum;  
			else if (i== 95) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MA") - dSum; //-this.global.getK_w2MA()-dSum;  
			else if (i== 96) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MP") - dSum; //-this.global.getK_w2MP()-dSum;  
			else if (i== 97) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w3MD") - dSum; //-this.global.getK_w3M()-dSum;  
			else if (i== 98) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MS") - dSum; //-this.global.getK_w3MS()-dSum;  
			else if (i== 99) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MA") - dSum; //-this.global.getK_w3MA()-dSum;  
			else if (i== 100) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.w2MP") - dSum; //-this.global.getK_w3MP()-dSum;  
			else if (i== 101) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sdMD") - dSum; //-this.global.getK_sdM()-dSum;  
			else if (i== 102) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sdMS") - dSum; //-this.global.getK_sdMS()-dSum;  
			else if (i== 103) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sdMA") - dSum; //-this.global.getK_sdMA()-dSum;  
			else if (i== 104) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sdMP") - dSum; //-this.global.getK_sdMP()-dSum;  
			else if (i== 105) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sMD") - dSum; //-this.global.getK_sM()-dSum;  
			else if (i== 106) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sMS") - dSum; //-this.global.getK_sMS()-dSum;  
			else if (i== 107) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sMA") - dSum; //-this.global.getK_sMA()-dSum;  
			else if (i== 108) this.coeffs[ i ][ i ] = -environment.getEnvProps("MODERATE", "k.sMP") - dSum; //-this.global.getK_sMP()-dSum;  
			else if (i== 109) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.aA") - dSum; //-this.global.getK_aA()-dSum;  
			else if (i== 110) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.aAS") - dSum; //-this.global.getK_aAS()-dSum;  
			else if (i== 111) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.aAA") - dSum; //-this.global.getK_aAA()-dSum;  
			else if (i== 112) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.aAP") - dSum; //-this.global.getK_aAP()-dSum;  
			else if (i== 113) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 114) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 115) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 116) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w2AD") - dSum; //-this.global.getK_w2A()-dSum;  
			else if (i== 117) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w2AS") - dSum; //-this.global.getK_w2AS()-dSum;  
			else if (i== 118) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w2AA") - dSum; //-this.global.getK_w2AA()-dSum;  
			else if (i== 119) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w2AP") - dSum; //-this.global.getK_w2AP()-dSum;  
			else if (i== 120) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w3AD") - dSum; //-this.global.getK_w3A()-dSum;  
			else if (i== 121) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w3AS") - dSum; //-this.global.getK_w3AS()-dSum;  
			else if (i== 122) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w3AA") - dSum; //-this.global.getK_w3AA()-dSum;  
			else if (i== 123) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.w3AP") - dSum; //-this.global.getK_w3AP()-dSum;  
			else if (i== 124) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sdAD") - dSum; //-this.global.getK_sdA()-dSum;  
			else if (i== 125) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sdAS") - dSum; //-this.global.getK_sdAS()-dSum;  
			else if (i== 126) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sdAA") - dSum; //-this.global.getK_sdAA()-dSum;  
			else if (i== 127) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sdAP") - dSum; //-this.global.getK_sdAP()-dSum; 
			else if (i== 128) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sAD") - dSum; //-this.global.getK_sA()-dSum;  
			else if (i== 129) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sAS") - dSum; //-this.global.getK_sAS()-dSum;  
			else if (i== 130) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sAA") - dSum; //-this.global.getK_sAA()-dSum;  
			else if (i== 131) this.coeffs[ i ][ i ] = -environment.getEnvProps("ARCTIC", "k.sAP") - dSum; //-this.global.getK_sAP()-dSum;  
			else if (i== 132) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.aT") - dSum; //-this.global.getK_aT()-dSum;  
			else if (i== 133) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.aTS") - dSum; //-this.global.getK_ATS()-dSum;  
			else if (i== 134) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.aTA") - dSum; //-this.global.getK_ATA()-dSum;  
			else if (i== 135) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.aTP") - dSum; //-this.global.getK_ATP()-dSum;  
			else if (i== 136) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 137) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 138) this.coeffs[ i ][ i ] = -dSum;  
			else if (i== 139) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w2TD") - dSum; //-this.global.getK_w2T()-dSum;  
			else if (i== 140) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w2TS") - dSum; //-this.global.getK_w2TS()-dSum;  
			else if (i== 141) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w2TA") - dSum; //-this.global.getK_w2TA()-dSum;  
			else if (i== 142) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w2TP") - dSum; //-this.global.getK_w2TP()-dSum;
			else if (i== 143) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w3TD") - dSum; //-this.global.getK_w3T()-dSum;  
			else if (i== 144) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w3TS") - dSum; //-this.global.getK_w3TS()-dSum;  
			else if (i== 145) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w3TA") - dSum; //-this.global.getK_w3TA()-dSum;  
			else if (i== 146) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.w3TP") - dSum; //-this.global.getK_w3TP()-dSum;  
			else if (i== 147) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sdTD") - dSum; //-this.global.getK_sdT()-dSum;  
			else if (i== 148) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sdTS") - dSum; //-this.global.getK_sdTS()-dSum;  
			else if (i== 149) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sdTA") - dSum; //-this.global.getK_sdTA()-dSum;  
			else if (i== 150) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sdTP") - dSum; //-this.global.getK_sdTP()-dSum;  
			else if (i== 151) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sTD") - dSum; //-this.global.getK_sT()-dSum;  
			else if (i== 152) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sTS") - dSum;  //-this.global.getK_sTS()-dSum;  
			else if (i== 153) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sTA") - dSum; //-this.global.getK_sTA()-dSum;  
			else if (i== 154) this.coeffs[ i ][ i ] = -environment.getEnvProps("TROPICAL", "k.sTP") - dSum; //-this.global.getK_sTP()-dSum;  
		}
		
		//Create the real matrix
		this.rCoeffs = new Array2DRowRealMatrix( this.coeffs );	
		
/*		try {
			FileWriter myWriter = new FileWriter("CoeffMatrix");
			for ( int i = 0; i< this.rCoeffs.getRowDimension(); i++) {
				for ( int j = 0; j< this.rCoeffs.getColumnDimension(); j++ ) 
					myWriter.write(  Double.toString( this.rCoeffs.getEntry( i , j ) ) + "\t" );
				myWriter.write( "\n" );			    		  
			}
			myWriter.close();
 
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		     } */
		
/*		int iCount = 0;
		for ( double val:this.coeffs[2]) {
			if ( val != 0 )
				System.out.println( iCount + " " + val );
		
			iCount++;
		}*/
	}

	void createEmissions()
	{
		this.emissions[ 0 ] = input.getEmissionRates("E.aRG"); //this.input.getE_aRG();
		this.emissions[ 1 ] = input.getEmissionRates("E.aRS"); //this.input.getE_aRS();

		this.emissions[ 7 ] = input.getEmissionRates("E.w0RD");  // this.input.getE_w0RD();
		this.emissions[ 8 ] = input.getEmissionRates("E.w0RS"); //this.input.getE_w0RS();

		this.emissions[ 11 ] = input.getEmissionRates("E.w1RD"); //this.input.getE_w1RD();
		this.emissions[ 12 ] = input.getEmissionRates("E.w1RS"); //this.input.getE_w1RS();		

		this.emissions[ 15 ] = input.getEmissionRates("E.w2RD"); //this.input.getE_w2RD();
		this.emissions[ 16 ] = input.getEmissionRates("E.w2RS"); //this.input.getE_w2RS();

		this.emissions[ 31 ] = input.getEmissionRates("E.s1RD"); //this.input.getE_s1RD();
		this.emissions[ 32 ] = input.getEmissionRates("E.s1RS"); //this.input.getE_s1RS();

		this.emissions[ 35 ] = input.getEmissionRates("E.s2RD"); //this.input.getE_s2RD();
		this.emissions[ 36 ] = input.getEmissionRates("E.s2RS"); //this.input.getE_s2RS();

		this.emissions[ 39 ] = input.getEmissionRates("E.s3RD"); //this.input.getE_s3RD();
		this.emissions[ 40 ] = input.getEmissionRates("E.s3RS"); //this.input.getE_s3RS();

		this.emissions[ 43 ] = input.getEmissionRates("E.aCG"); //this.input.getE_aCG();
		this.emissions[ 44 ] = input.getEmissionRates("E.aCS"); //this.input.getE_aCS();

		this.emissions[ 50 ] = input.getEmissionRates("E.w0CD"); //this.input.getE_w0CD();
		this.emissions[ 51 ] = input.getEmissionRates("E.w0CS"); //this.input.getE_w0CS();

		this.emissions[ 54 ] = input.getEmissionRates("E.w1CD"); //this.input.getE_w1CD();
		this.emissions[ 55 ] = input.getEmissionRates("E.w1CS"); //this.input.getE_w1CS();

		this.emissions[ 58 ] = input.getEmissionRates("E.w2CD"); //this.input.getE_w2CD();
		this.emissions[ 59 ] = input.getEmissionRates("E.w2CS"); //this.input.getE_w2CS();

		this.emissions[ 74 ] = input.getEmissionRates("E.s1CD"); //this.input.getE_s1CD();
		this.emissions[ 75 ] = input.getEmissionRates("E.s1CS"); //this.input.getE_s1CS();

		this.emissions[ 78 ] = input.getEmissionRates("E.s2CD"); //this.input.getE_s2CD();
		this.emissions[ 79 ] = input.getEmissionRates("E.s2CS"); //this.input.getE_s2CS();

		this.emissions[ 82 ] = input.getEmissionRates("E.s3CD"); //this.input.getE_s3CD();
		this.emissions[ 83 ] = input.getEmissionRates("E.s3CS"); //this.input.getE_s3CS();

		this.emissions[ 86 ] = input.getEmissionRates("E.aMG"); //this.input.getE_aMG();
		this.emissions[ 87 ] = input.getEmissionRates("E.aMS"); //this.input.getE_aMS();

		this.emissions[ 93 ] = input.getEmissionRates("E.w2MD"); //this.input.getE_w2MD();
		this.emissions[ 94 ] = input.getEmissionRates("E.w2MS"); //this.input.getE_w2MS();

		this.emissions[ 105 ] = input.getEmissionRates("E.sMD"); //this.input.getE_sMD();
		this.emissions[ 106 ] = input.getEmissionRates("E.sMS"); //this.input.getE_sMS();

		this.emissions[ 109 ] = input.getEmissionRates("E.aAG"); //this.input.getE_aAG();
		this.emissions[ 110 ] = input.getEmissionRates("E.aAS"); //this.input.getE_aAS();

		this.emissions[ 109 ] = input.getEmissionRates("E.aAG"); //this.input.getE_aAG();
		this.emissions[ 110 ] = input.getEmissionRates("E.aAS"); //this.input.getE_aAS();

		this.emissions[ 116 ] = input.getEmissionRates("E.w2AD"); //this.input.getE_w2AD();
		this.emissions[ 117 ] = input.getEmissionRates("E.w2AS"); //this.input.getE_w2AS();

		this.emissions[ 128 ] = input.getEmissionRates("E.sAD"); //this.input.getE_sAD();
		this.emissions[ 129 ] = input.getEmissionRates("E.sAS"); //this.input.getE_sAS();

		this.emissions[ 132 ] = input.getEmissionRates("E.aTG"); //this.input.getE_aTG();
		this.emissions[ 133 ] = input.getEmissionRates("E.aTS"); //this.input.getE_aTS();

		this.emissions[ 139 ] = input.getEmissionRates("E.w2TD"); //this.input.getE_w2TD();
		this.emissions[ 140 ] = input.getEmissionRates("E.w2TS"); //this.input.getE_w2TS();

		this.emissions[ 151 ] = input.getEmissionRates("E.sTD"); //this.input.getE_sTD();
		this.emissions[ 152 ] = input.getEmissionRates("E.sTS"); //this.input.getE_sTS();
		
		this.rEmissions = new Array2DRowRealMatrix( emissions );
	}

	void createVolumes() 
	{
		//Create the volumes 
		for ( int i = 0; i <= 3; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.aR"); //this.regional.getVOLUME_aR();

		for ( int i = 4; i <= 6; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.cwR"); //this.regional.getVOLUME_cwR();

		for ( int i = 7; i <= 10; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.w0R"); //this.regional.getVOLUME_w0R();

		for ( int i = 11; i <= 14; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.w1R"); //this.regional.getVOLUME_w1R();

		for ( int i = 15; i <= 18; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.w2R"); //this.regional.getVOLUME_w2R();

		for ( int i = 19; i <= 22; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.sd0R"); //this.regional.getVOLUME_sd0R();

		for ( int i = 23; i <= 26; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.sd1R"); //this.regional.getVOLUME_sd1R();

		for ( int i = 27; i <= 30; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.sd2R"); //this.regional.getVOLUME_sd2R();

		for ( int i = 27; i <= 30; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.sd2R"); //this.regional.getVOLUME_sd2R();

		for ( int i = 31; i <= 34; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.s1R"); //this.regional.getVOLUME_s1R();

		for ( int i = 35; i <= 38; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.s2R"); //this.regional.getVOLUME_s2R();

		for ( int i = 39; i <= 42; i++)
			volumes[ i ] = environment.getEnvProps("REGIONAL", "VOLUME.s3R"); //this.regional.getVOLUME_s3R();

		for ( int i = 43; i <= 46; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.aC"); //this.continental.getVOLUME_aC();

		for ( int i = 47; i <= 49; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.cwC"); //this.continental.getVOLUME_cwC();

		for ( int i = 50; i <= 53; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.w0C"); //this.continental.getVOLUME_w0C();

		for ( int i = 54; i <= 57; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.w1C"); //this.continental.getVOLUME_w1C();

		for ( int i = 58; i <= 61; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.w2C"); //this.continental.getVOLUME_w2C();

		for ( int i = 62; i <= 65; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.sd0C"); //this.continental.getVOLUME_sd0C();

		for ( int i = 66; i <= 69; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.sd1C"); //this.continental.getVOLUME_sd1C();

		for ( int i = 70; i <= 73; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.sd2C"); //this.continental.getVOLUME_sd2C();

		for ( int i = 74; i <= 77; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.s1C"); //this.continental.getVOLUME_s1C();

		for ( int i = 78; i <= 81; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.s2C"); //this.continental.getVOLUME_s2C();

		for ( int i = 82; i <= 85; i++)
			volumes[ i ] = environment.getEnvProps("CONTINENTAL", "VOLUME.s3C"); //this.continental.getVOLUME_s3C();

		for ( int i = 86; i <= 89; i++)
			volumes[ i ] = environment.getEnvProps("MODERATE", "VOLUME.aM"); //this.global.getVOLUME_aM();

		for ( int i = 90; i <= 92; i++)
			volumes[ i ] = environment.getEnvProps("MODERATE", "VOLUME.cwM"); //this.global.getVOLUME_cwM();

		for ( int i = 93; i <= 96; i++)
			volumes[ i ] = environment.getEnvProps("MODERATE", "VOLUME.w2M"); //this.global.getVOLUME_w2M();

		for ( int i = 97; i <= 100; i++)
			volumes[ i ] = environment.getEnvProps("MODERATE", "VOLUME.w3M"); //this.global.getVOLUME_w3M();

		for ( int i = 101; i <= 104; i++)
			volumes[ i ] = environment.getEnvProps("MODERATE", "VOLUME.sdM"); //this.global.getVOLUME_sdM();

		for ( int i = 105; i <= 108; i++)
			volumes[ i ] = environment.getEnvProps("MODERATE", "VOLUME.sM"); //this.global.getVOLUME_sM();

		for ( int i = 109; i <= 112; i++)
			volumes[ i ] = environment.getEnvProps("ARCTIC", "VOLUME.aA"); //this.global.getVOLUME_aA();

		for ( int i = 113; i <= 115; i++)
			volumes[ i ] = environment.getEnvProps("ARCTIC", "VOLUME.cwA"); //this.global.getVOLUME_cwA();

		for ( int i = 116; i <= 119; i++)
			volumes[ i ] = environment.getEnvProps("ARCTIC", "VOLUME.w2A"); //this.global.getVOLUME_w2A();

		for ( int i = 120; i <= 123; i++)
			volumes[ i ] = environment.getEnvProps("ARCTIC", "VOLUME.w3A"); //this.global.getVOLUME_w3A();

		for ( int i = 124; i <= 127; i++)
			volumes[ i ] = environment.getEnvProps("ARCTIC", "VOLUME.sdA"); //this.global.getVOLUME_sdA();

		for ( int i = 128; i <= 131; i++)
			volumes[ i ] = environment.getEnvProps("ARCTIC", "VOLUME.sA"); //this.global.getVOLUME_sA();

		for ( int i = 132; i <= 135; i++)
			volumes[ i ] = environment.getEnvProps("TROPICAL", "VOLUME.aT"); //this.global.getVOLUME_aT();

		for ( int i = 136; i <= 138; i++)
			volumes[ i ] = environment.getEnvProps("TROPICAL", "VOLUME.cwT"); //this.global.getVOLUME_cwT();

		for ( int i = 139; i <= 142; i++)
			volumes[ i ] = environment.getEnvProps("TROPICAL", "VOLUME.w2T"); //this.global.getVOLUME_w2T();

		for ( int i = 143; i <= 146; i++)
			volumes[ i ] = environment.getEnvProps("TROPICAL", "VOLUME.w3T"); //this.global.getVOLUME_w3T();

		for ( int i = 147; i <= 150; i++)
			volumes[ i ] = environment.getEnvProps("TROPICAL", "VOLUME.sdT"); //this.global.getVOLUME_sdT();

		for ( int i = 151; i <= 154; i++)
			volumes[ i ] = environment.getEnvProps("TROPICAL", "VOLUME.sT"); //this.global.getVOLUME_sT();
		
		this.rVolumes = new Array2DRowRealMatrix( volumes );
	}
	
	
}
