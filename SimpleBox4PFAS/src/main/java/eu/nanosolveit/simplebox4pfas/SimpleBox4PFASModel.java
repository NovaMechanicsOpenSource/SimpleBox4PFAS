package eu.nanosolveit.simplebox4pfas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zk.ui.Sessions;

import eu.nanosolveit.simplebox4pfas.Engine.Engine;
import eu.nanosolveit.simplebox4pfas.Engine.InputEngine;
import eu.nanosolveit.simplebox4pfas.Engine.RegionalEngine;

public class SimpleBox4PFASModel {

	InputEngine input = null;
	RegionalEngine environment = null;
	Engine engine = null;	

	Map<String, Map<String, String> > nanoData = new HashMap<String, Map<String, String> >(); 
	Map<String, Scenario> scenariosData = new HashMap<String, Scenario >();
	
	String nanoName = null;
	String sceneName = null;
	
	public SimpleBox4PFASModel( Map<String, Map<String, String> > nano, Map<String, Scenario> scenarios, 
			String scenario, String nanomaterial )
	{	
		sceneName = scenario;
		nanoName = nanomaterial;
		
		//Load all scenarios
		//The scenarios must be re-loaded for the values that are not set by the user (in case of API call). 
		loadScenarios();

		//Replace with user values or from the API
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.aRS",  scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.aRS") );
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.w0RS", scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.w0RS"));
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.w1RS", scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.w1RS"));
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.w2RS", scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.w2RS"));
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.s1RS", scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.s1RS"));
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.s2RS", scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.s2RS"));
		scenariosData.get( sceneName ).putSolidInfo("REGIONAL", "E.s3RS", scenarios.get( sceneName ).getSolidInfo( "REGIONAL").get("E.s3RS"));

		//Replace with user values or from the API
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.aRS",  scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.aCS") );
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.w0RS", scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.w0CS"));
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.w1RS", scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.w1CS"));
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.w2RS", scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.w2CS"));
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.s1RS", scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.s1CS"));
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.s2RS", scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.s2CS"));
		scenariosData.get( sceneName ).putSolidInfo("CONTINENTAL", "E.s3RS", scenarios.get( sceneName ).getSolidInfo( "CONTINENTAL").get("E.s3CS"));

		scenariosData.get( sceneName ).putSolidInfo("ARCTIC", "E.aAS",  scenarios.get( sceneName ).getSolidInfo( "ARCTIC").get("E.aAS") );
		scenariosData.get( sceneName ).putSolidInfo("ARCTIC", "E.w2AS", scenarios.get( sceneName ).getSolidInfo( "ARCTIC").get("E.w2AS"));
		scenariosData.get( sceneName ).putSolidInfo("ARCTIC", "E.sAS", scenarios.get( sceneName ).getSolidInfo( "ARCTIC").get("E.sAS"));

		scenariosData.get( sceneName ).putSolidInfo("TROPICAL", "E.aTS",  scenarios.get( sceneName ).getSolidInfo( "TROPICAL").get("E.aTS") );
		scenariosData.get( sceneName ).putSolidInfo("TROPICAL", "E.w2TS", scenarios.get( sceneName ).getSolidInfo( "TROPICAL").get("E.w2TS"));
		scenariosData.get( sceneName ).putSolidInfo("TROPICAL", "E.sTS", scenarios.get( sceneName ).getSolidInfo( "TROPICAL").get("E.sTS"));

		scenariosData.get( sceneName ).putSolidInfo("MODERATE", "E.aMS",  scenarios.get( sceneName ).getSolidInfo( "MODERATE").get("E.aMS") );
		scenariosData.get( sceneName ).putSolidInfo("MODERATE", "E.w2MS", scenarios.get( sceneName ).getSolidInfo( "MODERATE").get("E.w2MS"));
		scenariosData.get( sceneName ).putSolidInfo("MODERATE", "E.sMS", scenarios.get( sceneName ).getSolidInfo( "MODERATE").get("E.sMS"));
		
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "AREAland.R",  scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("AREAland.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "AREAsea.R",  scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("AREAsea.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRAClake.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRAClake.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRACfresh.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRACfresh.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRACnatsoil.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRACnatsoil.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRACagsoil.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRACagsoil.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRACothersoil.R",  scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRACothersoil.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "RAINrate.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("RAINrate.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "TEMP.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("TEMP.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "WINDspeed.R",  scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("WINDspeed.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "DEPTHlake.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("DEPTHlake.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "DEPTHfreshwater.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("DEPTHfreshwater.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRACrun.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRACrun.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "FRACinf.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("FRACinf.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "EROSION.R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("EROSION.R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "SUSP.w0R",  scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("SUSP.w0R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "SUSP.w1R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("SUSP.w1R")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("REGIONAL", "SUSP.w2R",   scenarios.get( sceneName ).getLandscapeInfo( "REGIONAL").get("SUSP.w2R")  ) ;

		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "TOTAREAland.C",  scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("TOTAREAland.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "TOTAREAsea.C",  scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("TOTAREAsea.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRAClake.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRAClake.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRACfresh.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRACfresh.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRACnatsoil.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRACnatsoil.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRACagsoil.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRACagsoil.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRACothersoil.C",  scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRACothersoil.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "RAINrate.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("RAINrate.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "TEMP.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("TEMP.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "WINDspeed.C",  scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("WINDspeed.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "DEPTHlake.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("DEPTHlake.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "DEPTHfreshwater.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("DEPTHfreshwater.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRACrun.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRACrun.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "FRACinf.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("FRACinf.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "EROSION.C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("EROSION.C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "SUSP.w0C",  scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("SUSP.w0C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "SUSP.w1C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("SUSP.w1C")  ) ;
		scenariosData.get( sceneName ).putLandscapeInfo("CONTINENTAL", "SUSP.w2C",   scenarios.get( sceneName ).getLandscapeInfo( "CONTINENTAL").get("SUSP.w2C")  ) ;

		//load all available nanomaterials
		//The nanomaterials (as the scenarios) must be re-loaded for the values that are not set by the user (in case of API call). 
		loadNanomaterials();
		
		//Replace with user values or from the API
		for( String entry:nano.get(nanoName).keySet() ) 
			nanoData.get(nanoName).put(entry, nano.get(nanoName).get(entry) );

		input = new InputEngine( scenariosData.get( scenario ), nanoData.get( nanoName ) );
		environment = new RegionalEngine( input );
		engine = new Engine( input, environment, nanoData.get( nanoName ), scenariosData.get( sceneName )	);
		engine.build();		
	}	
	
	public InputEngine getInput() { return input; } 
	public RegionalEngine getEnvironment() { return environment; } 
	public Engine getEngine() { return engine; } 
	public Map<String, String> getNanoData() { return nanoData.get(nanoName); } 
	public String getNanoName() { return nanoName; } 
	public String getSceneName() { return sceneName; } 

	public void loadScenarios(){
		try {
			ServletContext c = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();
			
			XSSFWorkbook wb = new XSSFWorkbook( c.getResourceAsStream("/resources/templates/scenarios.xlsx") );
			XSSFSheet sheet = wb.getSheetAt(0);

			//Get all scenarios names from 3rd row, 8th col to the end
			ArrayList<String> names = new ArrayList<String >();
			for ( int i = 8; i < sheet.getRow( 2 ).getLastCellNum(); i++) {
				Cell cell = sheet.getRow( 2 ).getCell( i );
				String str = cell.getStringCellValue();
				names.add( str );
			}

			//Create all structures in every scenario before storing the actual values
			int iScenario = 8; // This must points to the first entry of the first scenario 
			for ( String str:names ) {		

				// Create the scenario
				scenariosData.put(str, new Scenario( str ) );

				Map< String, String > regSolidInfo = new HashMap<String, String>();
				for ( int i = 7; i < 14; i++) 
					regSolidInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertSolidInfo("REGIONAL", regSolidInfo );

				Map< String, String > contSolidInfo = new HashMap<String, String>();
				for ( int i = 15; i < 22; i++) 
					contSolidInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertSolidInfo("CONTINENTAL", contSolidInfo);

				Map< String, String > modSolidInfo = new HashMap<String, String>();
				for ( int i = 23; i < 26; i++) 
					modSolidInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertSolidInfo("MODERATE", modSolidInfo);

				Map< String, String > artSolidInfo = new HashMap<String, String>();
				for ( int i = 27; i < 30; i++) 
					artSolidInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertSolidInfo("ARCTIC", artSolidInfo);

				Map< String, String > tropSolidInfo = new HashMap<String, String>();
				for ( int i = 31; i < 34; i++) 
					tropSolidInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );			
				scenariosData.get( str ).insertSolidInfo("TROPICAL", tropSolidInfo);

				Map< String, String > regLandInfo = new HashMap<String, String>();
				for ( int i = 37; i < 64; i++) 
					regLandInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertLandscapeInfo("REGIONAL", regLandInfo);

				Map< String, String > contLandInfo = new HashMap<String, String>();
				for ( int i = 65; i < 94; i++) 
					contLandInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertLandscapeInfo("CONTINENTAL", contLandInfo);
				
				iScenario++;
			}

			wb.close();
		} catch(Exception ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void loadNanomaterials(){
		try {	
			
			ServletContext c = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();

			XSSFWorkbook wb = new XSSFWorkbook( c.getResourceAsStream("/resources/templates/nanomaterials.xlsx") );
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();   

			ArrayList<String> labels = new ArrayList<String>();
			boolean labelsMade = false;

			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				Iterator< Cell > cellIterator = row.cellIterator();   //iterating over each column  

				int i = 0;
				Map<String, String>	inputNanoData = new HashMap<String, String>();

				while (cellIterator.hasNext())   
				{  
					Cell cell = cellIterator.next();  
					switch ( cell.getCellType() )               
					{  
					case STRING:
					{
						//						System.out.print( cell.getStringCellValue() + "\t\t\t");
						if ( !labelsMade ) 
							labels.add( cell.getStringCellValue() );
						else
							inputNanoData.put( labels.get( i ), cell.getStringCellValue() );
						i++;
						break;
					}
					case NUMERIC:
					{
						//						System.out.print( cell.getNumericCellValue() + "\t\t\t");  
						double val = cell.getNumericCellValue();
						inputNanoData.put( labels.get( i ), String.valueOf( val ) );
						i++;
						break;
					}
					default:  
					{
						//						System.out.print( cell.getNumericCellValue() + "\t\t\t");  
						double val = cell.getNumericCellValue();
						inputNanoData.put( labels.get( i ), String.valueOf( val ) );
						i++;
						break;
					}
					}  					
				}  

				labelsMade = true;				
				nanoData.put( inputNanoData.get("Name"), (Map<String, String>) inputNanoData );
			}  

			wb.close();
		} catch(Exception ioe) {
			ioe.printStackTrace();
		}
	}
}


