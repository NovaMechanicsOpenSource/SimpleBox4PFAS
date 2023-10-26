package eu.nanosolveit.simplebox4pfas.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import eu.nanosolveit.simplebox4pfas.Scenario;
import lombok.Getter;
import lombok.Setter;

public class InputEngine {
	//SUBSTANCE PROPERTIES dissolved/conventional form - this hold also the nanomaterial properties in the proper units according to the excel 
	Map<String, Double> substancesData = new HashMap<String, Double>();

	//EMISSION RATES
	Map<String, Double> emissionRates = new HashMap<String, Double>();

	//LANDSCAPE SETTINGS
	Map<String, Map<String, Double> > landSettings = new HashMap<String, Map<String, Double> >();

	//ENVIRONMENTAL PROPERTIES 
	Map<String, Double> envProperties = new HashMap<String, Double>();

	//CACLULATED NANO PROPERTIES 
	Map<String, Double> nanoProperties = new HashMap<String, Double>();

	//This is the QSAR Table used 
	Map<String, ArrayList<Double> > QSARTable = new HashMap<String, ArrayList<Double> >(); 


	public double getSubstancesData(String str) {
		return substancesData.get(str);
	}

	public double getEmissionRates(String str) {
		return emissionRates.get(str);
	}

	public double getLandscapeSettings(String str1, String str2) {
		return landSettings.get(str1).get(str2);
	}

	public double getEnvProperties(String str) {
		return envProperties.get(str);
	}

	public double getNanoProperties(String str) {
		return nanoProperties.get(str);
	}

	public InputEngine(Scenario scene, Map<String, String> nanomaterial ) 
	{		
		//Coming from the nanomaterial that is read	and transformed accordingly		

		if ( Double.valueOf( nanomaterial.get("RhoS") ) > 0 )
			substancesData.put("RhoS", Double.valueOf( nanomaterial.get("RhoS") ) );
		else
			substancesData.put("RhoS", 4.23e+3 );

		if ( Double.valueOf( nanomaterial.get("RadS") ) > 0 )
			substancesData.put("RadS", Double.valueOf( nanomaterial.get("RadS") )*1e-9 );
		else
			substancesData.put("RadS", 10.*1e-9 );

		if ( Double.valueOf( nanomaterial.get("Kow") ) > 0.0  )
			substancesData.put("Kaw", Double.valueOf( nanomaterial.get("Kaw") ) );
		else
			substancesData.put("Kaw", 1e-20 );

		if ( Double.valueOf( nanomaterial.get("Kow") ) > 0.0 ) 
			substancesData.put("Kow", Double.valueOf( nanomaterial.get("Kow") ) );
		else
			substancesData.put("Kow", 2750. );

		if ( Double.valueOf( nanomaterial.get("pKa") ) > 0.0)
			substancesData.put("pKa", Double.valueOf( nanomaterial.get("pKa") ) );
		else
			substancesData.put("pKa", 7.);

		substancesData.put("Pvap25", Double.valueOf( nanomaterial.get("Pvap25") ) );

		substancesData.put("Molweight", Double.valueOf( nanomaterial.get("Molweight") )/1000. );

		if ( Double.valueOf( nanomaterial.get("Sol25") ) > 0.0 )
			substancesData.put("Sol25", Double.valueOf( nanomaterial.get("Sol25") ) );
		else {
			double v0 = Math.log10( substancesData.get("Kow") )*(-1.214) + 0.85;
			double mol = substancesData.get("Molweight");
			substancesData.put("Sol25",  Math.pow(10, v0)*1000. );
		}

		if ( Double.valueOf( nanomaterial.get("Tm") ) > 0.0 ) 
			substancesData.put("Tm", Double.valueOf( nanomaterial.get("Tm") ) + 273. );
		else  
			substancesData.put("Tm", 325.1);

		substancesData.put("Kow.alt", Math.pow(10, Math.log10( substancesData.get("Kow") ) - 3.5 ) );		
		substancesData.put( "D", substancesData.get("Kow") + 1 - substancesData.get("Kow.alt") );
		substancesData.put( "FRorig.cldw", 1. );
		substancesData.put( "FRorig.aerw", 1. );
		substancesData.put( "FRorig.w1", 1. );
		substancesData.put( "FRorig.w2", 1. );
		substancesData.put( "FRorig.sd1", 1. );
		substancesData.put( "FRorig.sd2", 1. );
		substancesData.put( "FRorig.s1", 1. );
		substancesData.put( "FRorig.s1w", 1. );
		substancesData.put( "FRorig.s2", 1. );
		substancesData.put( "FRorig.s2w", 1. );
		substancesData.put( "FRorig.s3", 1. );
		substancesData.put( "FRorig.s3w", 1. );		
		substancesData.put( "FRorig.s3w", 1. );		
		substancesData.put( "BACT.test", 40000. );		
		substancesData.put( "Q.10", 2. );		

		if ( substancesData.get( "Tm" ) > 298  )
			substancesData.put( "H0vap", ( -3.82*Math.log( substancesData.get("Pvap25")*Math.exp(-6.79*(1-substancesData.get("Tm")/ 298.) ) ) + 70. )*1000. );
		else
			substancesData.put( "H0vap", ( -3.82*Math.log( substancesData.get("Pvap25") ) + 70. )*1000.);

		substancesData.put( "H0sol", 1e+4 );

		if ( Double.valueOf( nanomaterial.get("AtefSP.w0") ) > 0.0 )
			substancesData.put("AtefSP.w0", Double.valueOf( nanomaterial.get("AtefSP.w0") ) );
		else
			substancesData.put("AtefSP.w0", 0.1 );

		if ( Double.valueOf( nanomaterial.get("AtefSA.w0") ) > 0.0 )
			substancesData.put("AtefSA.w0", Double.valueOf( nanomaterial.get("AtefSA.w0") ) );
		else
			substancesData.put("AtefSA.w0", 0.1 );

		if ( Double.valueOf( nanomaterial.get("AtefSP.w1") ) > 0.0 )
			substancesData.put("AtefSP.w1", Double.valueOf( nanomaterial.get("AtefSP.w1") ) );
		else
			substancesData.put("AtefSP.w1", 0.1 );

		if ( Double.valueOf( nanomaterial.get("AtefSA.w1") ) > 0.0 ) 
			substancesData.put("AtefSA.w1", Double.valueOf( nanomaterial.get("AtefSA.w1") ) );
		else
			substancesData.put("AtefSA.w1", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSP.w2") ) > 0.0 )
			substancesData.put("AtefSP.w2", Double.valueOf( nanomaterial.get("AtefSP.w2") ) );
		else
			substancesData.put("AtefSP.w2", 1. );

		if ( Double.valueOf( nanomaterial.get("AtefSA.w2") ) > 0.0 )
			substancesData.put("AtefSA.w2", Double.valueOf( nanomaterial.get("AtefSA.w2") ) );
		else
			substancesData.put("AtefSA.w2", 1. );

		if ( Double.valueOf( nanomaterial.get("AtefSP.w3") ) > 0.0 )
			substancesData.put("AtefSP.w3", Double.valueOf( nanomaterial.get("AtefSP.w3") ) );
		else
			substancesData.put("AtefSP.w3", 1. );

		if ( Double.valueOf( nanomaterial.get("AtefSA.w3") ) > 0.0 )
			substancesData.put("AtefSA.w3", Double.valueOf( nanomaterial.get("AtefSA.w3") ) );
		else
			substancesData.put("AtefSA.w3", 1. );

		if ( Double.valueOf( nanomaterial.get("AtefSA.s1") ) > 0.0 )
			substancesData.put("AtefSA.s1", Double.valueOf( nanomaterial.get("AtefSA.s1") ) );
		else
			substancesData.put("AtefSA.s1", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSP.s1") ) > 0.0 )
			substancesData.put("AtefSP.s1", Double.valueOf( nanomaterial.get("AtefSP.s1") ) );
		else
			substancesData.put("AtefSP.s1", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSA.s2") ) > 0.0 )
			substancesData.put("AtefSA.s2", Double.valueOf( nanomaterial.get("AtefSA.s2") ) );
		else
			substancesData.put("AtefSA.s2", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSP.s2") ) > 0.0 )
			substancesData.put("AtefSP.s2", Double.valueOf( nanomaterial.get("AtefSP.s2") ) );
		else
			substancesData.put("AtefSP.s2", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSA.s3") ) > 0.0 )
			substancesData.put("AtefSA.s3", Double.valueOf( nanomaterial.get("AtefSA.s3") ) );
		else
			substancesData.put("AtefSA.s3", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSP.s3") ) > 0.0 )
			substancesData.put("AtefSP.s3", Double.valueOf( nanomaterial.get("AtefSP.s3") ) );
		else
			substancesData.put("AtefSP.s3", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSA.sd0") ) > 0.0 )
			substancesData.put("AtefSA.sd0", Double.valueOf( nanomaterial.get("AtefSA.sd0") ) );
		else
			substancesData.put("AtefSA.sd0", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSP.sd0") ) > 0.0 )
			substancesData.put("AtefSP.sd0", Double.valueOf( nanomaterial.get("AtefSP.sd0") ) );
		else
			substancesData.put("AtefSP.sd0", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSA.sd1") ) > 0.0 )
			substancesData.put("AtefSA.sd1", Double.valueOf( nanomaterial.get("AtefSA.sd1") ) );
		else
			substancesData.put("AtefSA.sd1", 0.1 );		

		if ( Double.valueOf( nanomaterial.get("AtefSP.sd1") ) > 0.0 )
			substancesData.put("AtefSP.sd1", Double.valueOf( nanomaterial.get("AtefSP.sd1") ) );
		else
			substancesData.put("AtefSP.sd1", 0.1 );

		if ( Double.valueOf( nanomaterial.get("AtefSA.sd2") ) > 0.0 )
			substancesData.put("AtefSA.sd2", Double.valueOf( nanomaterial.get("AtefSA.sd2") ) );
		else
			substancesData.put("AtefSA.sd2", 1. );

		if ( Double.valueOf( nanomaterial.get("AtefSP.sd2") ) > 0.0 )
			substancesData.put("AtefSP.sd2", Double.valueOf( nanomaterial.get("AtefSP.sd2") ) );
		else
			substancesData.put("AtefSP.sd2", 1. );

		if ( Double.valueOf( nanomaterial.get("kdegS.a") ) > 0.0 )
			substancesData.put("kdegS.a", Double.valueOf( nanomaterial.get("kdegS.a") ) );
		else
			substancesData.put("kdegS.a", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.a") ) > 0.0 )
			substancesData.put("kdegA.a", Double.valueOf( nanomaterial.get("kdegA.a") ) );
		else
			substancesData.put("kdegA.a", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.a") ) > 0.0 )
			substancesData.put("kdegP.a", Double.valueOf( nanomaterial.get("kdegP.a") ) );
		else
			substancesData.put("kdegP.a", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.w0") ) > 0.0 )
			substancesData.put("kdegS.w0", Double.valueOf( nanomaterial.get("kdegS.w0") ) );
		else
			substancesData.put("kdegS.w0", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.w1") ) > 0.0 )
			substancesData.put("kdegS.w1", Double.valueOf( nanomaterial.get("kdegS.w1") ) );
		else
			substancesData.put("kdegS.w1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.w2") ) > 0.0 )
			substancesData.put("kdegS.w2", Double.valueOf( nanomaterial.get("kdegS.w2") ) );
		else
			substancesData.put("kdegS.w2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.s1") ) > 0.0 )
			substancesData.put("kdegS.s1", Double.valueOf( nanomaterial.get("kdegS.s1") ) );
		else
			substancesData.put("kdegS.s1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.s2") ) > 0.0 )
			substancesData.put("kdegS.s2", Double.valueOf( nanomaterial.get("kdegS.s2") ) );
		else
			substancesData.put("kdegS.s2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.s3") ) > 0.0 )
			substancesData.put("kdegS.s3", Double.valueOf( nanomaterial.get("kdegS.s3") ) );
		else
			substancesData.put("kdegS.s3", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.sd0") ) > 0.0 )
			substancesData.put("kdegS.sd0", Double.valueOf( nanomaterial.get("kdegS.sd0") ) );
		else
			substancesData.put("kdegS.sd0", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.sd1") ) > 0.0 )
			substancesData.put("kdegS.sd1", Double.valueOf( nanomaterial.get("kdegS.sd1") ) );
		else
			substancesData.put("kdegS.sd1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.sd2") ) > 0.0 )
			substancesData.put("kdegS.sd2", Double.valueOf( nanomaterial.get("kdegS.sd2") ) );
		else
			substancesData.put("kdegS.sd2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegS.w3") ) > 0.0 )
			substancesData.put("kdegS.w3", Double.valueOf( nanomaterial.get("kdegS.w3") ) );
		else
			substancesData.put("kdegS.w3", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.w0") ) > 0.0 )
			substancesData.put("kdegA.w0", Double.valueOf( nanomaterial.get("kdegA.w0") ) );
		else
			substancesData.put("kdegA.w0", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.w1") ) > 0.0 )
			substancesData.put("kdegA.w1", Double.valueOf( nanomaterial.get("kdegA.w1") ) );
		else
			substancesData.put("kdegA.w1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.w2") ) > 0.0 )
			substancesData.put("kdegA.w2", Double.valueOf( nanomaterial.get("kdegA.w2") ) );
		else
			substancesData.put("kdegA.w2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.w0") ) > 0.0 )
			substancesData.put("kdegP.w0", Double.valueOf( nanomaterial.get("kdegP.w0") ) );
		else
			substancesData.put("kdegP.w0", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.w1") ) > 0.0 )
			substancesData.put("kdegP.w1", Double.valueOf( nanomaterial.get("kdegP.w1") ) );
		else
			substancesData.put("kdegP.w1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.w2") ) > 0.0 )
			substancesData.put("kdegP.w2", Double.valueOf( nanomaterial.get("kdegP.w2") ) );
		else
			substancesData.put("kdegP.w2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.s1") ) > 0.0 )
			substancesData.put("kdegA.s1", Double.valueOf( nanomaterial.get("kdegA.s1") ) );
		else
			substancesData.put("kdegA.s1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.s2") ) > 0.0 )
			substancesData.put("kdegA.s2", Double.valueOf( nanomaterial.get("kdegA.s2") ) );
		else
			substancesData.put("kdegA.s2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.s3") ) > 0.0 )
			substancesData.put("kdegA.s3", Double.valueOf( nanomaterial.get("kdegA.s3") ) );
		else
			substancesData.put("kdegA.s3", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.s1") ) > 0.0 )
			substancesData.put("kdegP.s1", Double.valueOf( nanomaterial.get("kdegP.s1") ) );
		else
			substancesData.put("kdegP.s1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.s2") ) > 0.0 )
			substancesData.put("kdegP.s2", Double.valueOf( nanomaterial.get("kdegP.s2") ) );
		else
			substancesData.put("kdegP.s2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.s3") ) > 0.0 )
			substancesData.put("kdegP.s3", Double.valueOf( nanomaterial.get("kdegP.s3") ) );
		else
			substancesData.put("kdegP.s3", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.sd0") ) > 0.0 ) 
			substancesData.put("kdegA.sd0", Double.valueOf( nanomaterial.get("kdegA.sd0") ) );
		else
			substancesData.put("kdegA.sd0", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.sd1") ) > 0.0 )
			substancesData.put("kdegA.sd1", Double.valueOf( nanomaterial.get("kdegA.sd1") ) );
		else
			substancesData.put("kdegA.sd1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.sd2") ) > 0.0 )
			substancesData.put("kdegA.sd2", Double.valueOf( nanomaterial.get("kdegA.sd2") ) );
		else
			substancesData.put("kdegA.sd2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.sd0") ) > 0.0 )
			substancesData.put("kdegP.sd0", Double.valueOf( nanomaterial.get("kdegP.sd0") ) );
		else
			substancesData.put("kdegP.sd0", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.sd1") ) > 0.0 )
			substancesData.put("kdegP.sd1", Double.valueOf( nanomaterial.get("kdegP.sd1") ) );
		else
			substancesData.put("kdegP.sd1", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.sd2") ) > 0.0 )
			substancesData.put("kdegP.sd2", Double.valueOf( nanomaterial.get("kdegP.sd2") ) );
		else
			substancesData.put("kdegP.sd2", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegA.w3") ) > 0.0 )
			substancesData.put("kdegA.w3", Double.valueOf( nanomaterial.get("kdegA.w3") ) );
		else
			substancesData.put("kdegA.w3", 0. );

		if ( Double.valueOf( nanomaterial.get("kdegP.w3") ) > 0.0 )
			substancesData.put("kdegP.w3", Double.valueOf( nanomaterial.get("kdegP.w3") ) );
		else
			substancesData.put("kdegP.w3", 0. );

		// We first must have made the substances (a part of it) and then create the QSARTable
		makeQSARTable();			

		landSettings.put("REGIONAL", new HashMap<String, Double>() );
		//Transform variables to compatible units and add new values according to the excel's input
		for ( String var:scene.getLandscapeInfo("REGIONAL").keySet() ) {
			if ( var.compareTo( "AREAland.R") == 0 || var.compareTo( "AREAsea.R") == 0)
				landSettings.get("REGIONAL").put(var,Double.valueOf( scene.getLandscapeInfo("REGIONAL").get(var) )*1000000. ); //To become m2 from km2 
			else if ( var.compareTo( "TEMP.R") == 0 )
				landSettings.get("REGIONAL").put(var,Double.valueOf( scene.getLandscapeInfo("REGIONAL").get(var) ) + 273. ); //To become K 				
			else if ( var.compareTo( "RAINrate.R") == 0 )
				landSettings.get("REGIONAL").put(var, Double.valueOf( scene.getLandscapeInfo("REGIONAL").get(var) )
						/1000./(3600.*24.*365.) ); //To become from mm.yr-1 to m s-1 				
			else if ( var.compareTo( "EROSION.R") == 0 )
				landSettings.get("REGIONAL").put(var,Double.valueOf( scene.getLandscapeInfo("REGIONAL").get(var) )
						/1000./(3600.*24.*365.) ); //To become from mm.yr-1 to m s-1 								
			else if ( var.compareTo( "COL.w0R") == 0 || var.compareTo( "COL.w1R") == 0 || var.compareTo( "COL.w2R") == 0) 
				landSettings.get("REGIONAL").put(var,  0.001 );  								
			else if	( var.compareTo( "SUSP.w0R") == 0 ) //from [mg.L-1] to kg.m-3
				landSettings.get("REGIONAL").put(var, 5e-4 );  								
			else if	( var.compareTo( "SUSP.w1R") == 0)
				landSettings.get("REGIONAL").put(var, 1.5e-2 );  								
			else if	( var.compareTo( "SUSP.w2R") == 0 ) //from [mg.L-1] to kg.m-3
				landSettings.get("REGIONAL").put(var, 5e-3 );  								
			else if	( var.compareTo( "mConcNC.sR") == 0 )
				landSettings.get("REGIONAL").put(var,  0.1 );  		
			else if ( var.compareTo( "NumConcNuc.aR") == 0. )
				landSettings.get("REGIONAL").put(var,  3.2e+9 );
			else if ( var.compareTo( "NumConcAcc.aR") == 0. )
				landSettings.get("REGIONAL").put(var,   2.9e+9 );
			else if ( var.compareTo( "NumConcCP.aR") == 0. )
				landSettings.get("REGIONAL").put(var,  3e+5 );
			else 
				landSettings.get("REGIONAL").put(var,Double.valueOf( scene.getLandscapeInfo("REGIONAL").get(var) ) );
		}

		//Transform variables to compatible units and add new values according to the excel's input
		landSettings.put("CONTINENTAL", new HashMap<String, Double>() );
		for ( String var:scene.getLandscapeInfo("CONTINENTAL").keySet() ) {
			//We change this because now Areal Land is computed by the total area - the regional area
			if ( var.compareTo( "AREAland.C") == 0 ) {							
				//		landSettings.get("CONTINENTAL").put(var, Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) )*1000000. ); //To become m2 from km2
				Double totalAreaC = Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get("TOTAREAland.C") )*1000000.; //To become m2 from km2
				Double areaR = Double.valueOf( scene.getLandscapeInfo("REGIONAL").get("AREAland.R") )*1000000.; //To become m2 from km2
				landSettings.get("CONTINENTAL").put(var,  totalAreaC - areaR); 
			}
			else if ( var.compareTo( "AREAsea.C") == 0 ) {
				//We change this because now Areal sea is computed by the total area - the regional area (as previous comment) 
				//				landSettings.get("CONTINENTAL").put(var, Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) )*1000000. ); //To become m2 from km2
				//		landSettings.get("CONTINENTAL").put(var, Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) )*1000000. ); //To become m2 from km2
				Double totalAreaC = Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get("TOTAREAsea.C") )*1000000.; //To become m2 from km2
				Double areaR = Double.valueOf( scene.getLandscapeInfo("REGIONAL").get("AREAsea.R") )*1000000.; //To become m2 from km2
				landSettings.get("CONTINENTAL").put(var,  (totalAreaC - areaR) ); 
			}
			else if ( var.compareTo( "TEMP.C") == 0 )
				landSettings.get("CONTINENTAL").put(var,Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) ) + 273. ); //To become K 				
			else if ( var.compareTo( "RAINrate.C") == 0 )
				landSettings.get("CONTINENTAL").put(var, Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) )
						/1000./(3600.*24.*365.) ); //To become from mm.yr-1 to m s-1 				
			else if ( var.compareTo( "EROSION.C") == 0 )
				landSettings.get("CONTINENTAL").put(var,Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) )
						/1000./(3600.*24.*365.) ); //To become from mm.yr-1 to m s-1 								
			else if ( var.compareTo( "COL.w0C") == 0 || var.compareTo( "COL.w1C") == 0 || var.compareTo( "COL.w2C") == 0) 
				landSettings.get("CONTINENTAL").put(var,  0.001 );  								
			else if	( var.compareTo( "SUSP.w0C") == 0 ) //from [mg.L-1] to kg.m-3
				landSettings.get("CONTINENTAL").put(var, 5e-4 );  								
			else if (var.compareTo( "SUSP.w1C") == 0 )
				landSettings.get("CONTINENTAL").put(var, 1.5e-2 );  												
			else if	( var.compareTo( "SUSP.w2C") == 0 ) //from [mg.L-1] to kg.m-3
				landSettings.get("CONTINENTAL").put(var, 5e-3 );  											
			else if	( var.compareTo( "mConcNC.sC") == 0 )
				landSettings.get("CONTINENTAL").put(var,  0.1 );  
			else if ( var.compareTo( "NumConcNuc.aC") == 0. )
				landSettings.get("CONTINENTAL").put(var,  3.2e+9 );
			else if ( var.compareTo( "NumConcAcc.aC") == 0. )
				landSettings.get("CONTINENTAL").put(var, 2.9e+9 );
			else if ( var.compareTo( "NumConcCP.aC") == 0. )
				landSettings.get("CONTINENTAL").put(var,  3e+5 );
			else
				landSettings.get("CONTINENTAL").put(var,Double.valueOf( scene.getLandscapeInfo("CONTINENTAL").get(var) ) );				
		}

		landSettings.put("ALL-SCALE",  new HashMap<String, Double>() );
		landSettings.get("ALL-SCALE").put("pH.cldw", 5.6);
		landSettings.get("ALL-SCALE").put("pH.aerw", 3.);
		landSettings.get("ALL-SCALE").put("pH.w1", 7.);
		landSettings.get("ALL-SCALE").put("pH.w2", 8.);
		landSettings.get("ALL-SCALE").put("pH.s1", 5.);
		landSettings.get("ALL-SCALE").put("pH.s2", 7.);
		landSettings.get("ALL-SCALE").put("pH.s3", 7.);		
		landSettings.get("ALL-SCALE").put("DIFFgas", 0.0000257*Math.sqrt(18.)/Math.sqrt( Double.valueOf( nanomaterial.get("Molweight") ) ) );
		landSettings.get("ALL-SCALE").put("DIFFwater", 0.000000002*Math.sqrt(32.)/Math.sqrt( Double.valueOf( nanomaterial.get("Molweight") ) ) );
		landSettings.get("ALL-SCALE").put("CORGaers", 0.1 );		
		landSettings.get("ALL-SCALE").put("CORG", 0.02 );		
		landSettings.get("ALL-SCALE").put("RHOaers", 2000. );		
		landSettings.get("ALL-SCALE").put("RHOsolid", 2500. );		
		landSettings.get("ALL-SCALE").put("DYNVISC.a", 1.81E-05 );		
		landSettings.get("ALL-SCALE").put("Rho.a", 1.225 );		
		landSettings.get("ALL-SCALE").put("DYNVISC.w", 0.001002 );		
		landSettings.get("ALL-SCALE").put("RHO.w", 998. );		
		landSettings.get("ALL-SCALE").put("kboltz", 1.3806488E-23 );		
		landSettings.get("ALL-SCALE").put("g", 9.81 );		

		// ... and continue with the substances 
		if ( Double.valueOf( nanomaterial.get("Ksw") ) > 0.0 )
			substancesData.put("Ksw", Double.valueOf( nanomaterial.get("Ksw") ) );
		else
			substancesData.put("Ksw", getQSARVal("Nano", "Koc(orig)")*landSettings.get("ALL-SCALE").get("CORG")*landSettings.get("ALL-SCALE").get("RHOsolid")/1000. );
		substancesData.put("Ksw.alt", getQSARVal("Nano", "Koc(alt)")*landSettings.get("ALL-SCALE").get("CORG")*landSettings.get("ALL-SCALE").get("RHOsolid")/1000. );

		substancesData.put("C.OHrad", 500000. ); 	
		substancesData.put("k0.OHrad", 0.000000000079 ); 	
		substancesData.put("Ea.OHrad", 6000. ); 	

		if ( Double.valueOf( nanomaterial.get("kdegG.air") ) > 0.0 )
			substancesData.put("kdegG.air", Double.valueOf( nanomaterial.get("kdegG.air") ));
		else {
			double v0 = Math.exp( -substancesData.get("Ea.OHrad")/(8.314*298.) );	
			substancesData.put("kdegG.air", substancesData.get("C.OHrad")*substancesData.get("k0.OHrad")*v0 );
		}

		if ( Double.valueOf( nanomaterial.get("kdegD.water") ) > 0.0 )
			substancesData.put("kdegD.water", Double.valueOf( nanomaterial.get("kdegD.water") ));
		else {
			double v0 = Math.exp( -substancesData.get("Ea.OHrad")/(8.314*298.) );	
			substancesData.put("kdegD.water", substancesData.get("C.OHrad")*substancesData.get("k0.OHrad")*v0 );
		}

		if ( Double.valueOf( nanomaterial.get("AHamakerSP.w") ) > 0.0  )
			substancesData.put("AHamakerSP.w", Double.valueOf( nanomaterial.get("AHamakerSP.w") ));
		else
			substancesData.put("AHamakerSP.w", 6.9e-21 );

		//Coming only from the nanomaterial
		substancesData.put("kdegD.water", Double.valueOf( nanomaterial.get("kdegD.water") )); 
		//Coming only from the nanomaterial
		substancesData.put("kdegD.sed", Double.valueOf( nanomaterial.get("kdegD.sed") ));
		//Coming only from the nanomaterial
		substancesData.put("kdegD.soil", Double.valueOf( nanomaterial.get("kdegD.soil") ));

		// ENVIROMENTAL PROPERTIES (ALL-SCALES)
		if ( Double.valueOf( scene.getEnviInfo("RadNuc") ) > 0.0 )
			envProperties.put( "RadNuc", Double.valueOf( scene.getEnviInfo("RadNuc") )/1e+9 );
		else
			envProperties.put( "RadNuc", 1.00E-08 );

		if ( Double.valueOf( scene.getEnviInfo("RhoNuc") ) > 0.0 )
			envProperties.put( "RhoNuc", Double.valueOf( scene.getEnviInfo("RhoNuc") ) );
		else
			envProperties.put( "RhoNuc", 1300. );

		if ( Double.valueOf( scene.getEnviInfo("RadAcc") ) > 0.0 )
			envProperties.put( "RadAcc", Double.valueOf( scene.getEnviInfo("RadAcc") )/1e+9 );
		else
			envProperties.put( "RadAcc", 5.8E-08 );

		if ( Double.valueOf( scene.getEnviInfo("RhoAcc") ) > 0.0 )
			envProperties.put( "RhoAcc", Double.valueOf( scene.getEnviInfo("RhoAcc") ) );
		else
			envProperties.put( "RhoAcc",  landSettings.get("ALL-SCALE").get("RHOaers") );

		if ( Double.valueOf( scene.getEnviInfo("RadCP.a") ) > 0.0 )
			envProperties.put( "RadCP.a", Double.valueOf( scene.getEnviInfo("RadCP.a") )/1e6 );
		else
			envProperties.put( "RadCP.a", 9e-7);

		if ( Double.valueOf( scene.getEnviInfo("RhoCP.a") ) > 0.0 )
			envProperties.put( "RhoCP.a", Double.valueOf( scene.getEnviInfo("RhoCP.a") ) );
		else
			envProperties.put( "RhoCP.a",  landSettings.get("ALL-SCALE").get("RHOaers") );

		if ( Double.valueOf( scene.getEnviInfo("RadNC.w") ) > 0.0 )
			envProperties.put( "RadNC.w", Double.valueOf( scene.getEnviInfo("RadNC.w") )/1e+9 );
		else
			envProperties.put( "RadNC.w", 1.5e-7);

		if ( Double.valueOf( scene.getEnviInfo("RhoNC.w") ) > 0.0 )
			envProperties.put( "RhoNC.w", Double.valueOf( scene.getEnviInfo("RhoNC.w") ) );
		else
			envProperties.put( "RhoNC.w",  2000. );

		if ( Double.valueOf( scene.getEnviInfo("RadSPM.w") ) > 0.0 )
			envProperties.put( "RadSPM.w", Double.valueOf( scene.getEnviInfo("RadSPM.w") )/1e+6 );
		else
			envProperties.put( "RadSPM.w", 3.0e-6);

		if ( Double.valueOf( scene.getEnviInfo("RhoSPM.w") ) > 0.0 )
			envProperties.put( "RhoSPM.w", Double.valueOf( scene.getEnviInfo("RhoSPM.w") ) );
		else
			envProperties.put( "RhoSPM.w", 2.5e+3);

		if ( Double.valueOf( scene.getEnviInfo("RadNC.sd") ) > 0.0 )
			envProperties.put( "RadNC.sd", Double.valueOf( scene.getEnviInfo("RadNC.sd") )/1e+9 );
		else
			envProperties.put( "RadNC.sd", 1.5e-7);

		if ( Double.valueOf( scene.getEnviInfo("RhoNC.sd") ) > 0.0 )
			envProperties.put( "RhoNC.sd", Double.valueOf( scene.getEnviInfo("RhoNC.sd") ) );
		else
			envProperties.put( "RhoNC.sd", 2.0e+3);

		if ( Double.valueOf( scene.getEnviInfo("RadFP.sd") ) > 0.0 )
			envProperties.put( "RadFP.sd", Double.valueOf( scene.getEnviInfo("RadFP.sd") )/1e+6 );
		else
			envProperties.put( "RadFP.sd", 1.28e-4);

		if ( Double.valueOf( scene.getEnviInfo("RadNC.s") ) > 0.0 )
			envProperties.put( "RadNC.s", Double.valueOf( scene.getEnviInfo("RadNC.s") )/1e+9 );
		else
			envProperties.put( "RadNC.s", 1.5e-7);

		if ( Double.valueOf( scene.getEnviInfo("RhoNC.s") ) > 0.0 )
			envProperties.put( "RhoNC.s", Double.valueOf( scene.getEnviInfo("RhoNC.s") ) );
		else
			envProperties.put( "RhoNC.s", 2.0e+3);

		if ( Double.valueOf( scene.getEnviInfo("RadFP.s") ) > 0.0 )
			envProperties.put( "RadFP.s", Double.valueOf( scene.getEnviInfo("RadFP.s") )/1e+6 );
		else
			envProperties.put( "RadFP.s", 1.28e-4);

		if ( Double.valueOf( scene.getEnviInfo("RhoFP.s") ) > 0.0 )
			envProperties.put( "RhoFP.s", Double.valueOf( scene.getEnviInfo("RhoFP.s") ) );
		else
			envProperties.put( "RhoFP.s", 2.5e+3);

		envProperties.put( "SingleVolNuc",  (4./3.)*Math.PI*Math.pow(envProperties.get("RadNuc"), 3) );		
		envProperties.put( "SingleVolAcc",  (4./3.)*Math.PI*Math.pow(envProperties.get("RadAcc"), 3) );
		envProperties.put( "SingleVolCP.a", (4./3.)*Math.PI*Math.pow(envProperties.get("RadCP.a"), 3) );
		envProperties.put( "SingleVolNC.w", (4./3.)*Math.PI*Math.pow(envProperties.get("RadNC.w"), 3) );

		envProperties.put( "SetVelNC.w",  2.*(envProperties.get("RhoNC.w") - landSettings.get("ALL-SCALE").get("RHO.w") )*landSettings.get("ALL-SCALE").get("g")*
				Math.pow(envProperties.get("RadNC.w"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );

		envProperties.put( "SingleVolSPM.w", (4./3.)*Math.PI*Math.pow(envProperties.get("RadSPM.w"), 3) );

		envProperties.put( "SetVelSPM.w",  2.*(envProperties.get("RhoSPM.w") - landSettings.get("ALL-SCALE").get("RHO.w") )*landSettings.get("ALL-SCALE").get("g")*
				Math.pow(envProperties.get("RadSPM.w"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );

		envProperties.put( "SetVelSPM.w",  2.*(envProperties.get("RhoSPM.w") - landSettings.get("ALL-SCALE").get("RHO.w") )*landSettings.get("ALL-SCALE").get("g")*
				Math.pow(envProperties.get("RadSPM.w"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );

		envProperties.put( "SingleVolNC.sd", (4./3.)*Math.PI*Math.pow(envProperties.get("RadNC.sd"), 3) );

		envProperties.put( "SetVelNC.sd",  2.*(envProperties.get("RhoNC.sd") - landSettings.get("ALL-SCALE").get("RHO.w") )*landSettings.get("ALL-SCALE").get("g")*
				Math.pow(envProperties.get("RadNC.sd"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );

		envProperties.put( "SingleVolNC.s", (4./3.)*Math.PI*Math.pow(envProperties.get("RadNC.s"), 3) );

		envProperties.put( "SetVelNC.s",  2.*(envProperties.get("RhoNC.s") - landSettings.get("ALL-SCALE").get("RHO.w") )*landSettings.get("ALL-SCALE").get("g")*
				Math.pow(envProperties.get("RadNC.s"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );


		nanoProperties.put("SetVelS.w", 2.*( substancesData.get("RhoS") - landSettings.get("ALL-SCALE").get("RHO.w"))*landSettings.get("ALL-SCALE").get("g")*
				Math.pow(substancesData.get("RadS"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );

		nanoProperties.put( "SingleVolS", (4./3.)*Math.PI*Math.pow(substancesData.get("RadS"), 3) );
		nanoProperties.put( "SingleMassS", (4./3.)*Math.PI*Math.pow(substancesData.get("RadS"), 3)*substancesData.get("RhoS") );

		nanoProperties.put( "ThermvelS", Math.pow( 8.*landSettings.get("ALL-SCALE").get("kboltz")*landSettings.get("REGIONAL").get("TEMP.R")/
				( Math.PI*nanoProperties.get("SingleMassS") ) , 1./2. )  );

		nanoProperties.put("KnudS", 66e-9/substancesData.get("RadS") );	

		nanoProperties.put("CunninghamS", 1 + nanoProperties.get("KnudS")*(  1.142+0.558*Math.exp( -0.999/nanoProperties.get("KnudS") )  ) );	

		nanoProperties.put("DiffS.aR",  (landSettings.get("ALL-SCALE").get("kboltz")*landSettings.get("REGIONAL").get("TEMP.R")*nanoProperties.get("CunninghamS") )/
				(6.*Math.PI*landSettings.get("ALL-SCALE").get("DYNVISC.a")*substancesData.get("RadS") ) );

		nanoProperties.put("DiffS.wR",  (landSettings.get("ALL-SCALE").get("kboltz")*landSettings.get("REGIONAL").get("TEMP.R") )/
				(6.*Math.PI*landSettings.get("ALL-SCALE").get("DYNVISC.w")*substancesData.get("RadS") ) );

		nanoProperties.put("DiffS.aC",  (landSettings.get("ALL-SCALE").get("kboltz")*landSettings.get("CONTINENTAL").get("TEMP.C")*nanoProperties.get("CunninghamS") )/
				(6.*Math.PI*landSettings.get("ALL-SCALE").get("DYNVISC.a")* substancesData.get("RadS") ) );

		nanoProperties.put("DiffS.wC",  (landSettings.get("ALL-SCALE").get("kboltz")*landSettings.get("CONTINENTAL").get("TEMP.C") )/
				(6.*Math.PI*landSettings.get("ALL-SCALE").get("DYNVISC.w")* substancesData.get("RadS") ) );

		double temp1 = substancesData.get("RhoS")*(4./3.)*Math.PI*Math.pow( substancesData.get("RadS"), 3);
		double temp2 = envProperties.get("RhoNC.w")*(4./3.)*Math.PI*Math.pow( envProperties.get("RadNC.w"), 3);
		double temp3 = (4./3.)*Math.PI*Math.pow( substancesData.get("RadS"), 3);
		double temp4 = (4./3.)*Math.PI*Math.pow( envProperties.get("RadNC.w"), 3);
		nanoProperties.put("RhoA.w", (temp1 + temp2)/(temp3 + temp4) );
		nanoProperties.put("RadA.w", Math.pow( (Math.pow(envProperties.get("RadNC.w"),3) + Math.pow(substancesData.get("RadS"),3) ), 1./3.) );
		nanoProperties.put("SetVelA.w", 2.*( nanoProperties.get("RhoA.w") - landSettings.get("ALL-SCALE").get("RHO.w"))*landSettings.get("ALL-SCALE").get("g")*
				Math.pow( nanoProperties.get("RadA.w"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );

		double temp11 = substancesData.get("RhoS")*(4./3.)*Math.PI*Math.pow( substancesData.get("RadS"), 3);
		double temp22 = envProperties.get("RhoSPM.w")*(4./3.)*Math.PI*Math.pow( envProperties.get("RadSPM.w"), 3);
		double temp33 = (4./3.)*Math.PI*Math.pow( substancesData.get("RadS"), 3);
		double temp44 = (4./3.)*Math.PI*Math.pow( envProperties.get("RadSPM.w"), 3);
		nanoProperties.put("RhoP.w", (temp11 + temp22)/(temp33 + temp44) );
		nanoProperties.put("RadP.w", Math.pow( (Math.pow(envProperties.get("RadSPM.w"),3) + Math.pow(substancesData.get("RadS"),3) ), 1./3.) );
		nanoProperties.put("SetVelP.w", 2.*( nanoProperties.get("RhoP.w") - landSettings.get("ALL-SCALE").get("RHO.w"))*landSettings.get("ALL-SCALE").get("g")*
				Math.pow( nanoProperties.get("RadP.w"), 2)/(9.*landSettings.get("ALL-SCALE").get("DYNVISC.w") ) );


		double temp111 = substancesData.get("RhoS")*(4./3.)*Math.PI*Math.pow( substancesData.get("RadS"), 3);
		double temp222 = envProperties.get("RhoCP.a")*(4./3.)*Math.PI*Math.pow( envProperties.get("RadCP.a"), 3);
		double temp333 = (4./3.)*Math.PI*Math.pow( substancesData.get("RadS"), 3);
		double temp444 = (4./3.)*Math.PI*Math.pow( envProperties.get("RadCP.a"), 3);
		nanoProperties.put("RhoP.a", (temp111 + temp222)/(temp333 + temp444) );
		nanoProperties.put("RadP.a", Math.pow( (Math.pow(envProperties.get("RadCP.a"),3) + Math.pow(substancesData.get("RadS"),3) ), 1./3.) );		
		nanoProperties.put("CunninghamP.a", 1.+ (66e-9/nanoProperties.get("RadP.a") )*1.142+0.558*Math.exp( -0.999/( 66e-9/nanoProperties.get("RadP.a") ) ) );
		nanoProperties.put("SetVelP.a", Math.pow( (2.*nanoProperties.get("RadP.a") ), 2 )*(nanoProperties.get("RhoP.a") - landSettings.get("ALL-SCALE").get("Rho.a") )
				*landSettings.get("ALL-SCALE").get("g")*nanoProperties.get("CunninghamP.a") /(18.*landSettings.get("ALL-SCALE").get("DYNVISC.a") ) );

		emissionRates.put("E.aRG", 0.);
		emissionRates.put("E.w0RD", 0.);
		emissionRates.put("E.w1RD", 0.);
		emissionRates.put("E.w2RD", 0.);
		emissionRates.put("E.s1RD", 0.);
		emissionRates.put("E.s2RD", 0.);
		emissionRates.put("E.s3RD", 0.);
		emissionRates.put("E.aCG", 0.);
		emissionRates.put("E.w0CD", 0.);
		emissionRates.put("E.w1CD", 0.);
		emissionRates.put("E.w1CD", 0.);
		emissionRates.put("E.w2CD", 0.);
		emissionRates.put("E.s1CD", 0.);
		emissionRates.put("E.s2CD", 0.);
		emissionRates.put("E.s3CD", 0.);
		emissionRates.put("E.aMG", 0.);
		emissionRates.put("E.w2MD", 0.);
		emissionRates.put("E.sMD", 0.);
		emissionRates.put("E.aAG", 0.);
		emissionRates.put("E.w2AD", 0.);
		emissionRates.put("E.sAD", 0.);
		emissionRates.put("E.aAS", 0.);
		emissionRates.put("E.w2AS", 0.);
		emissionRates.put("E.sAS", 0.);
		emissionRates.put("E.aTG", 0.);
		emissionRates.put("E.w2TD", 0.);
		emissionRates.put("E.sTD", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.aCS") ) > 0.0  )
			emissionRates.put("E.aCS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.aCS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.aCS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.w0CS") ) > 0.0  )
			emissionRates.put("E.w0CS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.w0CS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.w0CS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.w1CS") ) > 0.0  )
			emissionRates.put("E.w1CS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.w1CS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.w1CS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.w2CS") ) > 0.0  )
			emissionRates.put("E.w2CS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.w2CS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.w2CS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.s1CS") ) > 0.0  )
			emissionRates.put("E.s1CS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.s1CS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.s1CS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.s2CS") ) > 0.0  )
			emissionRates.put("E.s2CS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.s2CS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.s2CS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.s3CS") ) > 0.0  )
			emissionRates.put("E.s3CS", (Double.valueOf( scene.getSolidInfo("CONTINENTAL").get("E.s3CS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.s3CS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("MODERATE").get("E.aMS") ) > 0.0  )
			emissionRates.put("E.aMS", (Double.valueOf( scene.getSolidInfo("MODERATE").get("E.aMS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.aMS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("MODERATE").get("E.w2MS") ) > 0.0  )
			emissionRates.put("E.w2MS", (Double.valueOf( scene.getSolidInfo("MODERATE").get("E.w2MS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.w2MS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("MODERATE").get("E.sMS") ) > 0.0  )
			emissionRates.put("E.sMS", (Double.valueOf( scene.getSolidInfo("MODERATE").get("E.sMS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.sMS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("ARCTIC").get("E.aAS") ) > 0.0  )
			emissionRates.put("E.aAS", (Double.valueOf( scene.getSolidInfo("ARCTIC").get("E.aAS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.aAS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("ARCTIC").get("E.w2AS") ) > 0.0  )
			emissionRates.put("E.w2AS", (Double.valueOf( scene.getSolidInfo("ARCTIC").get("E.w2AS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.w2AS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("ARCTIC").get("E.sAS") ) > 0.0  )
			emissionRates.put("E.sAS", (Double.valueOf( scene.getSolidInfo("ARCTIC").get("E.sAS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.sAS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("TROPICAL").get("E.aTS") ) > 0.0  )
			emissionRates.put("E.aTS", (Double.valueOf( scene.getSolidInfo("TROPICAL").get("E.aTS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.aTS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("TROPICAL").get("E.w2TS") ) > 0.0  )
			emissionRates.put("E.w2TS", (Double.valueOf( scene.getSolidInfo("TROPICAL").get("E.w2TS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.w2TS", 0.);

		if ( Double.valueOf( scene.getSolidInfo("TROPICAL").get("E.sTS") ) > 0.0  )
			emissionRates.put("E.sTS", (Double.valueOf( scene.getSolidInfo("TROPICAL").get("E.sTS") )*1000.)/(substancesData.get("Molweight")*3600.*24.*365.) );
		else 
			emissionRates.put("E.sTS", 0.);

		//Coming from the GUI (it may be altered by the user ...) 
		emissionRates.put("E.aRS",  (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.aRS") ) )  );
		emissionRates.put("E.w0RS", (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.w0RS") ) ) );
		emissionRates.put("E.w1RS", (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.w1RS") ) ) );
		emissionRates.put("E.w2RS", (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.w2RS") ) ) );
		emissionRates.put("E.s1RS", (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.s1RS") ) ) );
		emissionRates.put("E.s2RS", (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.s2RS") ) ) );
		emissionRates.put("E.s3RS", (Double.valueOf( scene.getSolidInfo("REGIONAL").get("E.s3RS") ) ) );
	}

	public void makeQSARTable() { 	
		double val = 0.0;
		if ( substancesData.get("Pvap25") > 100000 )
			val = (100000./substancesData.get("Sol25")/(8.314*298.) );			
		else 
			val = ( substancesData.get("Pvap25")/substancesData.get("Sol25"))/(8.314*298.);

		QSARTable.put("Neutral", new ArrayList<Double>() );
		QSARTable.get("Neutral").add( 1.26*Math.pow(substancesData.get("Kow"), 0.81 ) );
		QSARTable.get("Neutral").add( 1.26*Math.pow(substancesData.get("Kow"), 0.81 ) );
		QSARTable.get("Neutral").add(  val > 1.e-20 ? val : 1.e-20  );
		QSARTable.get("Neutral").add(  val > 1.e-20 ? val : 1.e-20  );

		QSARTable.put("Acid", new ArrayList<Double>() );		
		QSARTable.get("Acid").add( Math.pow(10, (0.54*Math.log10(substancesData.get("Kow")) + 1.11 ) ) );
		QSARTable.get("Acid").add( Math.pow(10, (0.11*Math.log10(substancesData.get("Kow")) + 1.54 ) ) );
		QSARTable.get("Acid").add(  val > 1.e-20 ? val : 1.e-20  );
		QSARTable.get("Acid").add(  val > 1.e-20 ? val : 1.e-20  );

		QSARTable.put("Base",  new ArrayList<Double>() );
		QSARTable.get("Base").add( Math.pow(10, (0.37*Math.log10(substancesData.get("Kow")) + 1.7 ) ) );

		if (substancesData.get("pKa") >0 ) {
			double v0 = substancesData.get("Kow")/(1. + substancesData.get("Kow") ); 
			double v1 = Math.pow(substancesData.get("pKa"), 0.65);					
			QSARTable.get("Base").add( Math.pow(10, v0*v1 ) );		
		}
		else
			QSARTable.get("Base").add( 1. );			
		QSARTable.get("Base").add(  val > 1.e-20 ? val : 1.e-20  );
		QSARTable.get("Base").add(  1.e-20 );

		QSARTable.put("Metal",  new ArrayList<Double>());
		QSARTable.get("Metal").add( 1000. );
		QSARTable.get("Metal").add( 1000. );
		QSARTable.get("Metal").add(  1.e-20 );
		QSARTable.get("Metal").add(  1.e-20 );

		QSARTable.put("Nano",  new ArrayList<Double>());
		QSARTable.get("Nano").add( 1000. );
		QSARTable.get("Nano").add( 1000. );
		QSARTable.get("Nano").add(  val > 1.e-20 ? val : 1.e-20  );
		QSARTable.get("Nano").add(  val > 1.e-20 ? val : 1.e-20  );

		QSARTable.put("Hydrophobics",  new ArrayList<Double>());
		QSARTable.get("Hydrophobics").add( 10.47*Math.pow(substancesData.get("Kow"), 0.52) );
		QSARTable.get("Hydrophobics").add( 10.47*Math.pow(substancesData.get("Kow"), 0.52) );
	}

	double getQSARVal(String chem, String var) {
		if ( var.compareTo( "Koc(orig)"  ) == 0 )
			return QSARTable.get(chem).get(0);

		if ( var.compareTo( "Koc(alt)"  ) == 0 )
			return QSARTable.get(chem).get(1);

		if ( var.compareTo( "Kh(orig)"  ) == 0 )
			return QSARTable.get(chem).get(2);

		if ( var.compareTo( "Kh(alt)"  ) == 0 )
			return QSARTable.get(chem).get(3);

		if ( var.compareTo( "Koc(orig)"  ) != 0 || 
				var.compareTo( "Koc(alt)"  )  != 0 || 
				var.compareTo( "Kh(orig)"  )  != 0 ||
				var.compareTo( "Kh(orig)"  )  != 0  )
			System.out.println("Warning: Not supported val in QSAR table.");

		return 0.0;
	}
}
