package eu.nanosolveit.simplebox4pfas;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

public class SimpleBox4PFASModelController extends SelectorComposer<Window>{

	Window win, attach;

	@Wire
	Button calculate, adjust, save, load;

	@Wire
	Combobox scenarios, nanomaterials;

	@Wire 
	Doublebox molweight, density;

	@Wire 
	Doublebox toAir, toLakeWater, toFreshWater, toSea, toNatSoil, toAgriSoil, toOtherSoil;

	@Wire 
	Doublebox toAirCont, toLakeWaterCont, toFreshWaterCont, toSeaCont, toNatSoilCont, toAgriSoilCont, toOtherSoilCont;

	@Wire 
	Doublebox toAirArc, toWaterArc, toSoilArc;

	@Wire 
	Doublebox toAirTrop, toWaterTrop, toSoilTrop;

	@Wire 
	Doublebox toAirMod, toWaterMod, toSoilMod;

	@Wire
	Checkbox globalParams;

	@Wire
	Label globalScaleLabel, arcticLabelEm, tropicalLabelEm, modLabelEm;

	@Wire
	Grid globalGridEm; //arcticGridEm, tropicalGridEm, modGridEm;

	// Holds the nanomaterials data as coming from the excel file or the scenario csv
	Map<String, Map<String, String> > nanoData = new HashMap<String, Map<String, String> >(); 
	Map<String, Scenario> scenariosData = new HashMap<String, Scenario >();

	ListModelList<String> availScenarios = null;
	ListModelList<String> availNMs = null;


	@Listen("onCheck=#globalParams")
	public void showGlobalEm() {
		if ( globalParams.isChecked() ) {

			globalScaleLabel.setVisible(true);
			globalGridEm.setVisible(true);
		}
		else {

			globalScaleLabel.setVisible(false);
			globalGridEm.setVisible(false);
		}
	}

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		this.win = comp;	

		globalParams.setChecked(false);
		globalScaleLabel.setVisible(false);
		globalGridEm.setVisible(false);

		// Load scenarios
		loadScenarios();
		availScenarios = new ListModelList<String>();
		for ( String name:scenariosData.keySet() ) 
			availScenarios.add( name );		
		availScenarios.add( "User defined ..." );

		int iCount = 0;
		for ( String entry:availScenarios ) {
			if ( entry.compareTo("default scenario") == 0 )				
				availScenarios.addToSelection( availScenarios.get(iCount) );

			iCount++;
		}		
		scenarios.setModel( availScenarios );

		// Load nanomaterials properties ...
		availNMs = new ListModelList<String>();
		loadNanomaterials();
		for ( String name:nanoData.keySet() )
			if ( name != null && !name.trim().contentEquals("") && name.compareTo("Units") != 0 )
				availNMs.add( name );

		availNMs.addToSelection( availNMs.get( 0 ) );
		nanomaterials.setModel( availNMs );

		molweight.setValue(Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("Molweight") ) );
		density.setValue(  Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("RhoS") ) );

		toAir.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("REGIONAL").get("E.aRS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toLakeWater.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("REGIONAL").get("E.w0RS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toFreshWater.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("REGIONAL").get("E.w1RS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toSea.setValue( Double.valueOf( scenariosData.get( "default scenario").getSolidInfo("REGIONAL").get("E.w2RS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toNatSoil.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("REGIONAL").get("E.s1RS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toAgriSoil.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("REGIONAL").get("E.s2RS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toOtherSoil.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("REGIONAL").get("E.s3RS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

		toAirCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.aCS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toLakeWaterCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.w0CS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toFreshWaterCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.w1CS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toSeaCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.w2CS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toNatSoilCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.s1CS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toAgriSoilCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.s2CS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toOtherSoilCont.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("CONTINENTAL").get("E.s3CS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

		toAirArc.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("ARCTIC").get("E.aAS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toWaterArc.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("ARCTIC").get("E.w2AS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toSoilArc.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("ARCTIC").get("E.sAS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

		toAirTrop.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("TROPICAL").get("E.aTS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toWaterTrop.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("TROPICAL").get("E.w2TS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toSoilTrop.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("TROPICAL").get("E.sTS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

		toAirMod.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("MODERATE").get("E.aMS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toWaterMod.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("MODERATE").get("E.w2MS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		toSoilMod.setValue( Double.valueOf( scenariosData.get( "default scenario" ).getSolidInfo("MODERATE").get("E.sMS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

		load.setVisible(false);
	}

	@Listen("onChange=#nanomaterials,#scenarios")
	public void nanoSceneChanged() {

		//Reload the scenarios
		loadScenarios();

		molweight.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("Molweight") ) );
		density.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("RhoS") ) );

		if ( scenarios.getSelectedItem().getValue().toString() != "User defined ..." ) {
			toAir.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.aRS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toLakeWater.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.w0RS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toFreshWater.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.w1RS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toSea.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.w2RS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toNatSoil.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.s1RS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toAgriSoil.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.s2RS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toOtherSoil.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("REGIONAL").get("E.s3RS") )*1000./(molweight.getValue()*3600.*24.*365.) );

			toAirCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.aCS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toLakeWaterCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.w0CS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toFreshWaterCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.w1CS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toSeaCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.w2CS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toNatSoilCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.s1CS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toAgriSoilCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.s2CS") )*1000./((molweight.getValue()/1000.)*3600.*24.*365.) );
			toOtherSoilCont.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("CONTINENTAL").get("E.s3CS") )*1000./(molweight.getValue()*3600.*24.*365.) );

			toAirArc.setValue( Double.valueOf( scenariosData.get(scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("ARCTIC").get("E.aAS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
			toWaterArc.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("ARCTIC").get("E.w2AS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
			toSoilArc.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("ARCTIC").get("E.sAS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

			toAirTrop.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("TROPICAL").get("E.aTS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
			toWaterTrop.setValue( Double.valueOf( scenariosData.get(scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("TROPICAL").get("E.w2TS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
			toSoilTrop.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("TROPICAL").get("E.sTS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );

			toAirMod.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("MODERATE").get("E.aMS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
			toWaterMod.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("MODERATE").get("E.w2MS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
			toSoilMod.setValue( Double.valueOf( scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getSolidInfo("MODERATE").get("E.sMS") )*1000./( (molweight.getValue()/1000.)*3600.*24.*365.) );
		}				

		if ( scenarios.getSelectedItem().getValue().toString() == "User defined ..." ) 
		{
			calculate.setDisabled(true); 
			adjust.setDisabled(true); 
			save.setDisabled(true); 
			nanomaterials.setDisabled(true);
		}
		else {
			calculate.setDisabled(false); 
			adjust.setDisabled(false); 
			save.setDisabled(false); 
			nanomaterials.setDisabled(false);
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
						if ( !labelsMade ) 
							labels.add( cell.getStringCellValue() );
						else
							inputNanoData.put( labels.get( i ), cell.getStringCellValue() );
						i++;
						break;
					}
					case NUMERIC:
					{
						double val = cell.getNumericCellValue();
						inputNanoData.put( labels.get( i ), String.valueOf( val ) );
						i++;
						break;
					}
					default:  
					{
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
				for ( int i = 37; i < 63; i++) 
					regLandInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertLandscapeInfo("REGIONAL", regLandInfo);

				Map< String, String > contLandInfo = new HashMap<String, String>();
				for ( int i = 65; i < 93; i++) 
					contLandInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertLandscapeInfo("CONTINENTAL", contLandInfo);
				
				iScenario++;
			}

			wb.close();
		} catch(Exception ioe) {
			ioe.printStackTrace();
		}
	}

	void adjustNanoData()
	{		
		nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("Molweight", String.valueOf(molweight.getValue() ) ); 
		nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("RhoS", String.valueOf(density.getValue() ) ); 
	}

	void adjustScenarioData() {
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.aRS", String.valueOf( toAir.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.w0RS", String.valueOf( toLakeWater.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.w1RS", String.valueOf( toFreshWater.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.w2RS", String.valueOf( toSea.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.s1RS", String.valueOf( toNatSoil.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.s2RS", String.valueOf( toAgriSoil.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("REGIONAL", "E.s3RS", String.valueOf( toOtherSoil.getValue() ) );

		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.aCS", String.valueOf( toAirCont.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.w0CS", String.valueOf( toLakeWaterCont.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.w1CS", String.valueOf( toFreshWaterCont.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.w2CS", String.valueOf( toSeaCont.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.s1CS", String.valueOf( toNatSoilCont.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.s2CS", String.valueOf( toAgriSoilCont.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("CONTINENTAL", "E.s3CS", String.valueOf( toOtherSoilCont.getValue() ) );

		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("ARCTIC", "E.aAS", String.valueOf( toAirArc.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("ARCTIC", "E.w2AS", String.valueOf( toWaterArc.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("ARCTIC", "E.sAS", String.valueOf( toSoilArc.getValue() ) );

		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("TROPICAL", "E.aTS", String.valueOf( toAirTrop.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("TROPICAL", "E.w2TS", String.valueOf( toWaterTrop.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("TROPICAL", "E.sTS", String.valueOf( toSoilTrop.getValue() ) );

		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("MODERATE", "E.aMS", String.valueOf( toAirTrop.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("MODERATE", "E.w2MS", String.valueOf( toWaterTrop.getValue() ) );
		scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).putSolidInfo("MODERATE", "E.sMS", String.valueOf( toSoilTrop.getValue() ) );
	}

	@Listen("onClick = #calculate")
	public void openOutput() {		

		//Adjust nano data coming from the user (if (s)he has changed it)
		adjustNanoData();

		//Adjust scenario data coming from the user (if (s)he has changed it)
		adjustScenarioData();

		SimpleBox4PFASModel model = new SimpleBox4PFASModel( 
				nanoData, 
				scenariosData, 
				scenarios.getSelectedItem().getValue().toString(),
				nanomaterials.getSelectedItem().getValue().toString()
				);

		Session ses = Sessions.getCurrent();	
		ses.setAttribute("Input", model.getInput());
		ses.setAttribute("Environment", model.getEnvironment() );
		ses.setAttribute("Engine", model.getEngine() );

		ses.setAttribute("Nanomaterial", nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ));
		ses.setAttribute("Scenario name", scenarios.getSelectedItem().getValue().toString() );
		ses.setAttribute("Nanomaterial name", nanomaterials.getSelectedItem().getValue().toString() );

		this.attach = (Window) Executions.getCurrent().createComponents("output.zul", null, null); 
		this.attach.setClosable(true);
		this.attach.doModal();			
	}

	@Listen("onClick = #adjust")
	public void openAdvanced() {
		Session ses = Sessions.getCurrent();	
		String name = scenarios.getSelectedItem().getValue().toString(); 

		ses.setAttribute("Scenario", scenariosData.get( scenarios.getSelectedItem().getValue().toString() ) );

		this.attach = (Window) Executions.getCurrent().createComponents("advanced.zul", null, null); 
		this.attach.setClosable(true);
		this.attach.doModal();			
	}

	@Listen("onClick = #save")
	public void saveScenario() throws IOException, InterruptedException
	{
		String 	out = "Landscape scenarios\n";

		out += "\n"; 
		out = "Scenario;" + scenarios.getSelectedItem().getValue().toString() + "\n"; 

		out += "\n"; 
		out += "Substance properties\n";
		out += "Substance;" + nanomaterials.getSelectedItem().getValue().toString() + "\n"; 
		out += "Molecular weight [gr/mol];" +  String.valueOf(molweight.getValue()) + "\n" ; 
//		out += "Primary radius [nm];" +  String.valueOf(radius.getValue() ) + "\n"; 
		out += "Primary density [kg/m3];" +  String.valueOf(density.getValue() ) + "\n"; 
//		out += "Hamaker constant heteroagglomerate (ENP, water, SiO2);" +  String.valueOf(hamaker.getValue() ) + "\n"; 

		out += "\n"; 
/*		out += "Nanomaterial-Environment interaction parameters;"  + "\n"; 		
		out += "Attachement efficiency;"  + "\n"; 

		if ( freshWaterRadio.getSelectedIndex() == 0 ) {
			out +="attach fresh water;false"+ "\n";
			out += "In fresh water [-];" +  String.valueOf(freshWater.getValue() ) + "\n";
		}
		else {
			out +="attach fresh water;true"+ "\n";
			out += "Lake water NCs (less than 450 nm) [-];" +  String.valueOf(lakeWaterNC.getValue()) + "\n"; 
			out += "Lake water SPM (greater than 450 nm) [-];" +  String.valueOf(lakeWaterSPM.getValue()) + "\n"; 
			out += "Fresh water NCs (less than 450 nm) [-];" +  String.valueOf(freshWaterNC.getValue()) + "\n"; 
			out += "Fresh water SPM (greater than 450 nm) [-];" +  String.valueOf(freshWaterSPM.getValue()) + "\n"; 
		}

		if ( attachmentSoilDbRadio.getSelectedIndex() == 0 ) { 
			out +="attach soil;false"+ "\n";
			out += "In soil [-];" + String.valueOf(attachmentSoilDb.getValue() ) + "\n";
		}
		else {
			out +="attach soil;true"+ "\n";
			out += "Natural soil NCs (less than 450 nm) [-];" +  String.valueOf(naturalSoilNCs.getValue()) + "\n"; 
			out += "Natural soil grains [-];" +  String.valueOf(naturalSoilGrains.getValue() ) + "\n"; 
			out += "Agricultural soil NCs (less than 450 nm) [-];" +  String.valueOf(agriSoilNCs.getValue() ) + "\n"; 
			out += "Agricultural soil grains [-];" +  String.valueOf(agriSoilGrains.getValue() ) + "\n"; 
			out += "Other soil NCs (less than 450 nm) [-];" +  String.valueOf(otherSoilNCs.getValue() ) + "\n"; 
			out += "Other soil grains [-];" +  String.valueOf(otherSoilGrains.getValue() ) + "\n"; 
		}

		if ( attachmentFreshSediDbRadio.getSelectedIndex() == 0 ) {
			out +="attach fresh sedi;false"+ "\n";
			out += "In freshwater sediments [-];" + String.valueOf(attachmentSoilDb.getValue() ) + "\n";
		}
		else {
			out +="attach fresh sedi;true"+ "\n";
			out += "Lake sediment NCs (less than 450 nm) [-];" +  String.valueOf(lakeSedimentNCs.getValue() ) + "\n"; 
			out += "Lake sediment grains;" +  String.valueOf(lakeSedimentGrains.getValue() ) + "\n"; 
			out += "Fresh sediment NCs (less than 450 nm) [-];" +  String.valueOf(freshSedimentNCs.getValue() ) + "\n"; 
			out += "Fresh sediment grains;" +  String.valueOf( frashSedimentGrains.getValue() ) + "\n"; 
		}

		if ( attachmentMarineSediDbRadio.getSelectedIndex() == 0 ) { 
			out +="attach marine sedi;false" + "\n";
			out += "In marine sediments [-];" + String.valueOf( attachmentMarineSediDb.getValue()) + "\n";
		}
		else {
			out +="attach marine sedi;true" + "\n";
			out += "Marine sediment NCs (less than 450 nm);" +  String.valueOf(marineSedimentNCs.getValue() ) + "\n"; 
			out += "Marine sediment grains;" +  String.valueOf(marineSedimentGrains.getValue() ) + "\n"; 
		}		

		out += "\n"; 
		out += "Dissolution rates;" + "\n"; 

		if ( dissolutionFreshDbRadio.getSelectedIndex() == 0 ) {
			out += "dis fresh water;false" + "\n" ;
			out += "In fresh water [1/s];" + String.valueOf( dissolutionFreshDb.getValue() ) + "\n";
		}
		else {
			out += "dis fresh water;true" + "\n" ;
			out += "ENPs (S) in lake water [1/s];" +  String.valueOf(dissolutionlakeWaterENPs.getValue() ) + "\n"; 
			out += "ENPs (S) in fresh water [1/s];" +  String.valueOf(dissolutionfreshWaterSPM.getValue() ) + "\n"; 
			out += "ENPs and NCs (A) in lake water [1/s];" +  String.valueOf(dissolutionlakeWaterENPsNCsA.getValue() ) + "\n"; 
			out += "ENPs and NCs (P) in lake water [1/s];" +  String.valueOf(dissolutionlakeWaterENPsNCsP.getValue() ) + "\n"; 
			out += "ENPs and NCs (A) in fresh water [1/s];" +  String.valueOf(dissolutionfreshWaterENPsNCsA.getValue() ) + "\n"; 
			out += "ENPs and NCs (P) in fresh water [1/s];" +  String.valueOf(dissolutionfreshWaterENPsNCsP.getValue() ) + "\n"; 
		}		

		if ( dissolutionSeaWaterDbRadio.getSelectedIndex() == 0 ) {
			out += "dis sea water;false" + "\n" ;
			out += "In sea water [1/s];" + String.valueOf( dissolutionFreshDb.getValue() ) + "\n";
		}
		else {
			out += "dis sea water;true" + "\n" ;
			out += "ENPs (S) in sea water [1/s];" +  String.valueOf(dissolutionSeaWaterENPs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in sea water [1/s];" +  String.valueOf(dissolutionHeteroENPsNCsSeaWater.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and SPM (P) in sea water [1/s];" +  String.valueOf(dissolutionHeteroENPsSPMsSeaWater.getValue() ) + "\n"; 
		}		

		if ( dissolutionSoilDbRadio.getSelectedIndex() == 0 ) {
			out += "dis soil;false" + "\n" ;
			out += "In soil [1/s];" + String.valueOf( dissolutionSoilDb.getValue() ) + "\n";
		}
		else {
			out += "dis soil;true" + "\n" ;
			out += "ENPs (S) in natural soil pore water [1/s];" +  String.valueOf(dissolutionNatSoilPoreWaterENPs.getValue()) + "\n"; 
			out += "ENPs (S) in agricultural soil pore water [1/s];" +  String.valueOf(dissolutionAgriSoilPoreWaterENPs.getValue() ) + "\n"; 
			out += "ENPs (S) in other soil pore water [1/s];" +  String.valueOf(dissolutionOtherSoilPoreWaterENPs.getValue() ) + "\n"; 

			out += "ENPs and NCs (A) in natural soil pore water [1/s];" +  String.valueOf(dissolutionNatSoilENPsNCs.getValue() ) + "\n"; 
			out += "ENPs and FP (P) in natural soil [1/s];" +  String.valueOf(dissolutionNatSoilENPsFP.getValue() ) + "\n"; 
			out += "ENPs and NCs (A) in agricultural soil pore water [1/s];" +  String.valueOf(dissolutionAgriSoilPoreENPsNCs.getValue() ) + "\n"; 

			out += "ENPs and FP (P) in agricultural soil [1/s];" +  String.valueOf(dissolutionAgriSoilENPsFP.getValue() ) + "\n"; 
			out += "ENPs and NCs (A) in other soil pore water [1/s];" +  String.valueOf(dissolutionOtherSoilPoreENPsNCs.getValue() ) + "\n"; 
			out += "ENPs and FP (P) in other soil [1/s];" +  String.valueOf(dissolutionOtherSoileENPsFP.getValue() ) + "\n"; 
		}		

		if ( dissolutionFreshSediDbRadio.getSelectedIndex() == 0 ) {
			out += "dis fresh sedi;false" + "\n" ;
			out += "In freshwater sediments [1/s];" + String.valueOf( dissolutionFreshSediDb.getValue() ) + "\n";
		}
		else {
			out += "dis fresh sedi;true" + "\n" ;
			out += "ENPs (S) in lake sediment [1/s];" +  String.valueOf(disSedilakeSediENPs.getValue()) + "\n"; 
			out += "ENPs (S) in fresh sediment [1/s];" +  String.valueOf(disFreshSediENPs.getValue() ) + "\n"; 
			out += "ENPs and NCs (A) in lake sediment [1/s];" +  String.valueOf(disLakeSediENPsNCs.getValue() ) + "\n"; 

			out += "ENPs and FP (P) in lake sediment [1/s];" +  String.valueOf(disLakeSediENPsFP.getValue() ) + "\n"; 
			out += "ENPs and NCs (A) in fresh sediment [1/s];" +  String.valueOf(disFreshSediENPsNCs.getValue() ) + "\n"; 
			out += "ENPs and FP (P) in fresh sediment [1/s];" +  String.valueOf(disFreshSediENPsFP.getValue() ) + "\n"; 
		}		

		if ( dissolutionMarineSediDbRadio.getSelectedIndex() == 0 ) {
			out += "dis marine sedi;false" + "\n" ;
			out += "In marine sediments [1/s];" + String.valueOf( dissolutionMarineSediDb.getValue() ) + "\n";
		}
		else {
			out += "dis marine sedi;true" + "\n" ;
			out += "ENPs (S) in marine sediment [1/s];" +  String.valueOf(disMarineSedimentENPs.getValue()) + "\n"; 
			out += "ENPs (S) in deep sea water [1/s];" +  String.valueOf(disDeepSeaENPs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in marine sediment [1/s];" +  String.valueOf(disMarineSedimentENPsNCs.getValue() ) + "\n"; 		
			out += "Heteroagglomerates of ENPs and FP (P) in marine sediment [1/s];" +  String.valueOf(disMarineSedimentENPsFP.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in deep sea water [1/s];" +  String.valueOf(disDeepSeaWaterENPsNCs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and SPM (P) in deep sea water [1/s];" +  String.valueOf(disDeepSeaWaterENPsSPM.getValue() ) + "\n"; 
		}			

		out += "\n"; 
		out += "Degradation rates;" + "\n"; 

		if ( degradationAirDbRadio.getSelectedIndex() == 0 ) {
			out += "deg air;false" + "\n" ;
			out += "In air [1/s];" + String.valueOf( degradationAirDb.getValue() ) + "\n";
		}
		else {
			out += "deg air;true" + "\n" ;
			out += "ENPs (S) in air [1/s];" +  String.valueOf(degAirENPs.getValue()) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in lake water [1/s];" +  String.valueOf(degHeteroLakeWaterENPsNCs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and SPM (P) in lake water [1/s];" +  String.valueOf(degHeteroLakeWaterENPsSPM.getValue() ) + "\n"; 		
		}			

		if ( degradationFreshWaterDbRadio.getSelectedIndex() == 0 ) {
			out += "deg fresh water;false" + "\n" ;
			out += "In fresh water [1/s];" + String.valueOf( degradationFreshWaterDb.getValue() ) + "\n";
		}
		else {
			out += "deg fresh water;true" + "\n" ;
			out += "ENPs (S) in lake water [1/s];" +  String.valueOf(degLakeWaterENPs.getValue()) + "\n"; 
			out += "ENPs (S) in fresh water [1/s];" +  String.valueOf(degFreshWaterENPs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in lake water [1/s];" +  String.valueOf(degHetLakeWaterENPsNCs.getValue() ) + "\n"; 		
			out += "Heteroagglomerates of ENPs and SPM (P) in lake water [1/s];" +  String.valueOf(degHetLakeWaterENPsSPM.getValue()) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in fresh water [1/s];" +  String.valueOf(degHetFreshWaterENPsNCs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and SPM (P) in fresh water [1/s];" +  String.valueOf(degHetFreshWaterENPsSPM.getValue() ) + "\n"; 		
		}			

		if ( degradationSeaWaterDbRadio.getSelectedIndex() == 0 ) {
			out += "deg sea water;false" + "\n" ;
			out += "In sea water [1/s];" + String.valueOf( degradationSeaWaterDb.getValue() ) + "\n";
		}
		else {
			out += "deg sea water;true" + "\n" ;
			out += "ENPs (S) in sea water [1/s];" +  String.valueOf(degSeaWaterENPs.getValue()) + "\n"; 
			out += "ENPs and NCs (A) in sea water [1/s];" +  String.valueOf(degSeaWaterENPsNCs.getValue() ) + "\n"; 
			out += "ENPs and SPM (P) in sea water [1/s];" +  String.valueOf(degSeaWaterENPsSPM.getValue() ) + "\n"; 		
		}	

		if ( degradationSoilDbRadio.getSelectedIndex() == 0 ) { 
			out += "deg soil;false" + "\n" ;
			out += "In soil [1/s];" + String.valueOf( degradationSoilDb.getValue() ) + "\n";
		}
		else {
			out += "deg soil;true" + "\n" ;
			out += "ENPs (S) in natural soil pore water [1/s];" +  String.valueOf(degNatSoilPoreWaterENPs.getValue()) + "\n"; 
			out += "ENPs (S) in agricultural soil pore water [1/s];" +  String.valueOf(degAgriSoilPoreWaterENPs.getValue() ) + "\n"; 
			out += "ENPs (S) in other soil pore water [1/s];" +  String.valueOf(degOtherSoilPoreWaterENPs.getValue() ) + "\n"; 		

			out += "Heteroagglomerates of ENPs and NCs (A) in natural soil pore water [1/s];" +  String.valueOf(degHeteroNatSoilPoreWaterENPsNCs.getValue()) + "\n"; 
			out += "Heteroagglomerates of ENPs and FP (P) in natural soil [1/s];" +  String.valueOf(degHeteroNatSoilENPsFP.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in agricultural soil pore water [1/s];" +  String.valueOf(degHeteroAgriSoilENPsNC.getValue() ) + "\n"; 		

			out += "Heteroagglomerates of ENPs and FP (P) in agricultural soil [1/s];" +  String.valueOf(degHeteroAgriSoilENPsFP.getValue()) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in other soil pore water [1/s];" +  String.valueOf(degHeteroOtherSoilENPsNCs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and FP (P) in other soil [1/s];" +  String.valueOf(degHeteroOtherSoilENPsFP.getValue() ) + "\n"; 		
		}		

		if ( degradationFreshSediDbRadio.getSelectedIndex() == 0 ) {
			out += "deg fresh sedi;false" + "\n" ;
			out += "In freshwater sediments [1/s];" + String.valueOf( degradationFreshSedilDb.getValue() ) + "\n";
		}
		else {
			out += "deg fresh sedi;true" + "\n" ;
			out += "ENPs (S) in lake sediment [1/s];" +  String.valueOf(lakeSedimentENPs.getValue()) + "\n"; 
			out += "ENPs (S) in fresh sediment [1/s];" +  String.valueOf(freshSedimentENPs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in lake sediment [1/s];" +  String.valueOf(heteroLakeENPsNCs.getValue() ) + "\n"; 		

			out += "Heteroagglomerates of ENPs and FP (P) in lake sediment [1/s];" +  String.valueOf(heteroLakeENPsFP.getValue()) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in fresh sediment [1/s];" +  String.valueOf(heteroFreshENPsNCs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and FP (P) in fresh sediment [1/s];" +  String.valueOf(heteroFreshENPsFP.getValue() ) + "\n"; 		 		
		}		

		if ( degradationMarineSediDbRadio.getSelectedIndex() == 0 ) {
			out += "deg marine sedi;false" + "\n" ;
			out += "In marine sediments [1/s];" + String.valueOf( degradationMarineSediDb.getValue() ) + "\n";
		} 
		else {
			out += "deg marine sedi;true" + "\n" ;
			out += "ENPs (S) in marine sediment [1/s];" +  String.valueOf(marineSedimentENPs.getValue()) + "\n"; 
			out += "ENPs (S) in deep sea water [1/s];" +  String.valueOf(deepSeaWaterENPs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in marine sediment [1/s];" +  String.valueOf(heteroMarineENPsNCs.getValue() ) + "\n"; 		

			out += "Heteroagglomerates of ENPs and FP (P) in marine sediment [1/s];" +  String.valueOf(heteroMarineENPsFP.getValue()) + "\n"; 
			out += "Heteroagglomerates of ENPs and NCs (A) in deep sea water [1/s];" +  String.valueOf(heteroDeepSeaENPsNCs.getValue() ) + "\n"; 
			out += "Heteroagglomerates of ENPs and SPM (P) in deep sea water [1/s];" +  String.valueOf(heteroDeepSeaENPsSPM.getValue() ) + "\n"; 		 		
		}							

*/

		out += "\n"; 
		out += "Emission rates;" + "\n"; 
		out += "Regional;" + "\n"; 
		out += "to air [tn/y];" +  String.valueOf(toAir.getValue() ) + "\n"; 
		out += "to lake water [tn/y];" +  String.valueOf(toLakeWater.getValue() ) + "\n"; 
		out += "to fresh water [tn/y];" +  String.valueOf(toFreshWater.getValue() ) + "\n"; 
		out += "to sea water [tn/y];" +  String.valueOf(toSea.getValue() ) + "\n"; 
		out += "to natural soil [tn/y];" +  String.valueOf(toNatSoil.getValue() ) + "\n"; 
		out += "to agricultural soil [tn/y];" +  String.valueOf(toAgriSoil.getValue() ) + "\n"; 
		out += "to other soil [tn/y];" +  String.valueOf(toOtherSoil.getValue() ) + "\n"; 

		out += "Continental;" + "\n"; 
		out += "to air [tn/y];" +  String.valueOf(toAirCont.getValue() ) + "\n"; 
		out += "to lake water [tn/y];" +  String.valueOf(toLakeWaterCont.getValue() ) + "\n"; 
		out += "to fresh water [tn/y];" +  String.valueOf(toFreshWaterCont.getValue() ) + "\n"; 
		out += "to sea water [tn/y];" +  String.valueOf(toSeaCont.getValue() ) + "\n"; 
		out += "to natural soil [tn/y];" +  String.valueOf(toNatSoilCont.getValue() ) + "\n"; 
		out += "to agricultural soil [tn/y];" +  String.valueOf(toAgriSoilCont.getValue() ) + "\n"; 
		out += "to other soil [tn/y];" +  String.valueOf(toOtherSoilCont.getValue() ) + "\n"; 

		out += "Arctic;" + "\n"; 
		out += "to air [tn/y];" +  String.valueOf(toAirArc.getValue() ) + "\n"; 
		out += "to water [tn/y];" +  String.valueOf(toWaterArc.getValue() ) + "\n"; 
		out += "to soil [tn/y];" +  String.valueOf(toSoilArc.getValue() ) + "\n"; 

		out += "Tropical;" + "\n"; 
		out += "to air [tn/y];" +  String.valueOf(toAirTrop.getValue() ) + "\n"; 
		out += "to water [tn/y];" +  String.valueOf(toWaterTrop.getValue() ) + "\n"; 
		out += "to soil [tn/y];" +  String.valueOf(toSoilTrop.getValue() ) + "\n"; 

		out += "Moderate;" + "\n"; 
		out += "to air [tn/y];" +  String.valueOf(toAirMod.getValue() ) + "\n"; 
		out += "to water [tn/y];" +  String.valueOf(toWaterMod.getValue() ) + "\n"; 
		out += "to soil [tn/y];" +  String.valueOf(toSoilMod.getValue() ) + "\n"; 

		out += "\n"; 
		out += "ADVANCED;" + "\n"; 

		out += "\n"; 
		out += "Regional;" + "\n"; 

		out += "Land area [km2];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("AREAland.R") + "\n";
		out += "Sea area [km2];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("AREAsea.R") + "\n";
		out += "Lake water fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRAClake.R") + "\n";
		out += "Fresh water fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRACfresh.R") + "\n";
		out += "Natural soil fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRACnatsoil.R") + "\n";
		out += "Agricultural soil fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRACagsoil.R")+ "\n";
		out += "Urban/industrial fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRACothersoil.R")+ "\n";
		out += "Average precipitation [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("RAINrate.R")+ "\n";
		out += "Temperature [K];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("TEMP.R")+ "\n";
		out += "Wind speed [m/s];" +scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("WINDspeed.R")+ "\n";
		out += "Lake water depth [m];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("DEPTHlake.R")+ "\n";
		out += "Fresh water depth [m];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("DEPTHfreshwater.R")+ "\n";
		out += "Run off fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRACrun.R")+ "\n";
		out += "Infiltration fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("FRACinf.R")+ "\n";
		out += "Soil erosion [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("EROSION.R")+ "\n";

		if ( !scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getAdvRegChecked() ) {
			out += "reg suspended matter;false" + "\n" ;
			out += "Concentration of suspended matter in waters [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("SUSP.w0R")+ "\n";
		}
		else {
			out += "reg suspended matter;true" + "\n" ;
			out += "Concentration of suspended matter in lake water [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("SUSP.w0R")+ "\n";
			out += "Concentration of suspended matter in fresh water [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("SUSP.w1R")+ "\n";
			out += "Concentration of suspended matter in sea water [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("REGIONAL").get("SUSP.w2R")+ "\n";
		}

		out += "\n"; 
		out += "Continental;" + "\n"; 

		out += "Total land area [km2];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("TOTAREAland.C") + "\n";
		out += "Total sea area [km2];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("TOTAREAsea.C") + "\n";
		out += "Lake water fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRAClake.C") + "\n";
		out += "Fresh water fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRACfresh.C") + "\n";
		out += "Natural soil fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRACnatsoil.C")+ "\n";
		out += "Agricultural soil fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRACagsoil.C")+ "\n";
		out += "Urban/industrial fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRACothersoil.C")+ "\n";
		out += "Average precipitation [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("RAINrate.C")+ "\n";
		out += "Temperature [K];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("TEMP.C")+ "\n";
		out += "Wind speed [m/s];" +scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("WINDspeed.C")+ "\n";
		out += "Lake water depth [m];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("DEPTHlake.C")+ "\n";
		out += "Fresh water depth [m];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("DEPTHfreshwater.C")+ "\n";
		out += "Run off fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRACrun.C")+ "\n";
		out += "Infiltration fraction [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("FRACinf.C")+ "\n";
		out += "Soil erosion [-];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("EROSION.C")+ "\n";

		if ( !scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getAdvContChecked() ) {
			out += "cont suspended matter;false" + "\n" ;
			out += "Concentration of suspended matter in waters [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("SUSP.w0C")+ "\n";		
		}
		else {
			out += "cont suspended matter;true" + "\n" ;
			out += "Concentration of suspended matter in lake water [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("SUSP.w0C")+ "\n";
			out += "Concentration of suspended matter in fresh water [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("SUSP.w1C")+ "\n";
			out += "Concentration of suspended matter in sea water [mg/L];" + scenariosData.get( scenarios.getSelectedItem().getValue().toString() ).getLandscapeInfo("CONTINENTAL").get("SUSP.w2C")+ "\n";
		}

		InputStream targetStream = new ByteArrayInputStream(out.getBytes(StandardCharsets.UTF_8));
		Filedownload.save(targetStream, null, "scenario.csv");
	}

	@Listen("onUpload = #load")
	public void loadScenario( UploadEvent event ) throws Exception
	{ 
		if ( event != null ) {
			Media media = event.getMedia();

			if ( !media.isBinary() ) {
				//				InputStream is = media.getStreamData();    
				Reader is = media.getReaderData();   

				BufferedReader bufferedReader = new BufferedReader( is, 1);
				String line;

				String scenarioName = null;
				boolean readingAttachement = false;
				boolean readingDissolution = false;
				boolean readingDegradation = false;
				boolean readingAdvReg = false;
				boolean readingAdvCont = false;
				boolean readAdv = false;

				boolean readingEmReg = false;
				boolean readingEmCont = false;
				boolean readingEmArc = false;
				boolean readingEmTrop = false;
				boolean readingEmMod = false;
				boolean readEm = false;

				while ((line = bufferedReader.readLine()) != null) {

					String[] data = line.split(";");

					if ( data.length == 0 )
						continue;

					if ( data[ 0 ].compareTo("ADVANCED") == 0 ) {
						readingAttachement = false;
						readingDissolution = false;
						readingDegradation = false;						
						readAdv = true;
						readEm = false;
						continue;
					}

					if ( readAdv && data[ 0 ].compareTo("Regional") == 0 ){
						readingAdvReg = true;
						readingAdvCont = false;
						continue;						
					}

					if ( readAdv && data[ 0 ].compareTo("Continental") == 0 ) {
						readingAdvReg = false;
						readingAdvCont = true;
						continue;
					}

					try 
					{
						if ( data[ 0 ].compareTo("Scenario" ) == 0 ) {
							scenarioName = data[1];

							int iCount = 0; 
							for ( String entry:availScenarios) {
								if ( entry.compareTo(data[1] ) == 0 )
									availScenarios.addToSelection( availScenarios.get(iCount) );

								iCount++;
							}															
							scenarios.setModel( availScenarios );						

						}
					}
					catch(Exception e)
					{
						e.addSuppressed(e);
					}

					if ( data[ 0 ].compareTo("Substance" ) == 0 ) {
						int iCount = 0; 
						for ( String entry:availNMs) {
							if ( entry.compareTo(data[1] ) == 0 )
								availNMs.addToSelection( availNMs.get(iCount) );

							iCount++;
						}															
						nanomaterials.setModel( availNMs );	
					}

					if ( data[ 0 ].compareTo( "Molecular weight [gr/mol]" ) == 0 )
						molweight.setValue( Double.valueOf(  data[ 1 ] ) );

//					if ( data[ 0 ].compareTo( "Primary radius [nm]" ) == 0 )
//						radius.setValue( Double.valueOf(  data[ 1 ] ) );

					if ( data[ 0 ].compareTo( "Primary density [kg/m3]" ) == 0 )
						density.setValue( Double.valueOf(  data[ 1 ] ) );

//					if ( data[ 0 ].compareTo( "Hamaker constant heteroagglomerate (ENP, water, SiO2)" ) == 0 )
//						hamaker.setValue( Double.valueOf(  data[ 1 ] ) );

//					if ( data[ 0 ].compareTo( "Hamaker constant heteroagglomerate (ENP, water, SiO2)" ) == 0 )
//						hamaker.setValue( Double.valueOf(  data[ 1 ] ) );

					//Attachement efficiency
/* if ( data[ 0 ].compareTo( "attach fresh water" ) == 0 ) {

						readingAttachement = true;					
						readingDissolution = false;
						readingDegradation = false;

						if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
							freshWaterRadio.setSelectedIndex( 1 ); 
							freshWaterCoarse.setVisible(false);
							freshWaterDetailed.setVisible(true);
							freshWaterDetailedReporting.setVisible(false);
						}

						continue;
					}

					if ( readingAttachement ) { 

						if ( data[ 0 ].compareTo( "In fresh water [-]" ) == 0 )
							freshWater.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Lake water NCs (less than 450 nm) [-]" ) == 0 )
							lakeWaterNC.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Lake water SPM (greater than 450 nm) [-]" ) == 0 )
							lakeWaterSPM.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Fresh water NCs (less than 450 nm) [-]" ) == 0 )
							freshWaterNC.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Fresh water SPM (greater than 450 nm) [-]" ) == 0 )
							freshWaterSPM.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "attach soil" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								attachmentSoilDbRadio.setSelectedIndex( 1 ); 
								attachmentSoilDbCoarse.setVisible(false);
								attachmentSoilDbDetailed.setVisible(true);
								attachmentSoilDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In soil [-]" ) == 0 )
							attachmentSoilDb.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Natural soil NCs (less than 450 nm) [-]" ) == 0 )
							naturalSoilNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Natural soil grains [-]" ) == 0 )
							naturalSoilGrains.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Agricultural soil NCs (less than 450 nm) [-]" ) == 0 )
							agriSoilNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Agricultural soil grains [-]" ) == 0 )
							agriSoilGrains.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Other soil NCs (less than 450 nm) [-]" ) == 0 )
							otherSoilNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Other soil grains [-]" ) == 0 )
							otherSoilGrains.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "attach fresh sedi" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								attachmentFreshSediDbRadio.setSelectedIndex( 1 ); 
								attachmentFreshSediDbCoarse.setVisible(false);
								attachmentFreshSediDbDetailed.setVisible(true);
								attachmentFreshSediDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In freshwater sediments [-]" ) == 0 )
							attachmentSoilDb.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Lake sediment NCs (less than 450 nm) [-]" ) == 0 )
							lakeSedimentNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Lake sediment grains;" ) == 0 )
							lakeSedimentGrains.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Fresh sediment NCs (less than 450 nm) [-]" ) == 0 )
							lakeSedimentGrains.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Fresh sediment grains" ) == 0 )
							lakeSedimentGrains.setValue( Double.valueOf(  data[ 1 ] ) );


						if ( data[ 0 ].compareTo( "attach marine sedi" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								attachmentMarineSediDbRadio.setSelectedIndex( 1 ); 
								attachmentMarineSediDbCoarse.setVisible(false);
								attachmentMarineSediDbDetailed.setVisible(true);
								attachmentMarineSediDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In marine sediments [-]" ) == 0 )
							attachmentMarineSediDb.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Marine sediment NCs (less than 450 nm)" ) == 0 )
							marineSedimentNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Marine sediment grains;" ) == 0 )
							marineSedimentGrains.setValue( Double.valueOf(  data[ 1 ] ) );
					}

					//Dissolution rates

					if ( data[ 0 ].compareTo( "dis fresh water" ) == 0 ) {

						readingAttachement = false;					
						readingDissolution = true;
						readingDegradation = false;

						if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
							dissolutionFreshDbRadio.setSelectedIndex( 1 ); 
							dissolutionFreshDbCoarse.setVisible(false);
							dissolutionFreshDbDetailed.setVisible(true);
							dissolutionFreshDbDetailedReporting.setVisible(false);
						}

						continue;
					}

					if ( readingDissolution ) {

						if ( data[ 0 ].compareTo( "In fresh water [1/s]" ) == 0 )
							marineSedimentGrains.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs (S) in lake water [1/s]" ) == 0 )
							dissolutionlakeWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs (S) in fresh water [1/s]" ) == 0 )
							dissolutionfreshWaterSPM.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in lake water [1/s]" ) == 0 )
							dissolutionlakeWaterENPsNCsA.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in lake water [1/s]" ) == 0 )
							dissolutionlakeWaterENPsNCsA.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs and NCs (P) in lake water [1/s]" ) == 0 )
							dissolutionlakeWaterENPsNCsP.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in fresh water [1/s]" ) == 0 )
							dissolutionfreshWaterENPsNCsA.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs and NCs (P) in fresh water [1/s]" ) == 0 )
							dissolutionfreshWaterENPsNCsP.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "dis sea water" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								dissolutionSeaWaterDbRadio.setSelectedIndex( 1 ); 
								dissolutionSeaWaterDbCoarse.setVisible(false);
								dissolutionSeaWaterDbDetailed.setVisible(true);
								dissolutionSeaWaterDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In sea water [1/s]" ) == 0 )
							dissolutionFreshDb.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs (S) in sea water [1/s]" ) == 0 )
							dissolutionSeaWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in sea water [1/s]" ) == 0 )
							dissolutionHeteroENPsNCsSeaWater.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and SPM (P) in sea water [1/s]" ) == 0 )
							dissolutionHeteroENPsSPMsSeaWater.setValue( Double.valueOf(  data[ 1 ] ) );				

						if ( data[ 0 ].compareTo( "dis soil" ) == 0 ) {
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								dissolutionSoilDbRadio.setSelectedIndex( 1 ); 
								dissolutionSoilDbCoarse.setVisible(false);
								dissolutionSoilDbDetailed.setVisible(true);
								dissolutionSoilDbDetailedReporting.setVisible(false);
							}
						}

						if ( data[ 0 ].compareTo( "In soil [1/s]" ) == 0 )
							dissolutionSoilDb.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs (S) in natural soil pore water [1/s]" ) == 0 )
							dissolutionNatSoilPoreWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs (S) in agricultural soil pore water [1/s]" ) == 0 )
							dissolutionAgriSoilPoreWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs (S) in other soil pore water [1/s]" ) == 0 )
							dissolutionOtherSoilPoreWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in natural soil pore water [1/s];" ) == 0 )
							dissolutionNatSoilENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and FP (P) in natural soil [1/s]" ) == 0 )
							dissolutionNatSoilENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in agricultural soil pore water [1/s]" ) == 0 )
							dissolutionAgriSoilPoreENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and FP (P) in agricultural soil [1/s]" ) == 0 )
							dissolutionAgriSoilENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in other soil pore water [1/s]" ) == 0 )
							dissolutionOtherSoilPoreENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and FP (P) in other soil [1/s]" ) == 0 )
							dissolutionOtherSoileENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "dis fresh sedi" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								dissolutionFreshSediDbRadio.setSelectedIndex( 1 ); 
								dissolutionFreshSediDbCoarse.setVisible(false);
								dissolutionFreshSediDbDetailed.setVisible(true);
								dissolutionFreshSediDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In freshwater sediments [1/s]" ) == 0 )
							dissolutionFreshSediDb.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs (S) in lake sediment [1/s]" ) == 0 )
							disSedilakeSediENPs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs (S) in fresh sediment [1/s]" ) == 0 )
							disFreshSediENPs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in lake sediment [1/s]" ) == 0 )
							disLakeSediENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and FP (P) in lake sediment [1/s]" ) == 0 )
							disLakeSediENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in fresh sediment [1/s]" ) == 0 )
							disFreshSediENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "ENPs and FP (P) in fresh sediment [1/s]" ) == 0 )
							disFreshSediENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );						

						if ( data[ 0 ].compareTo( "dis marine sedi" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								dissolutionMarineSediDbRadio.setSelectedIndex( 1 ); 
								dissolutionMarineSediDbCoarse.setVisible(false);
								dissolutionMarineSediDbDetailed.setVisible(true);
								dissolutionMarineSediDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In marine sediments [1/s]" ) == 0 )
							dissolutionMarineSediDb.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "ENPs (S) in marine sediment [1/s]" ) == 0 )
							disMarineSedimentENPs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "ENPs (S) in deep sea water [1/s]" ) == 0 )
							disDeepSeaENPs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in marine sediment [1/s]" ) == 0 )
							disMarineSedimentENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in marine sediment [1/s]" ) == 0 )
							disMarineSedimentENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in deep sea water [1/s]" ) == 0 )
							disDeepSeaWaterENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and SPM (P) in deep sea water [1/s]" ) == 0 )
							disDeepSeaWaterENPsSPM.setValue( Double.valueOf(  data[ 1 ] ) );	

					}

					if ( data[ 0 ].compareTo( "deg air" ) == 0 ) {
						readingAttachement = false;					
						readingDissolution = false;
						readingDegradation = true;

						if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
							degradationAirDbRadio.setSelectedIndex( 1 ); 
							degradationAirDbCoarse.setVisible(false);
							degradationAirDbDetailed.setVisible(true);
							degradationAirDbDetailedReporting.setVisible(false);
						}

						continue;
					}

					if ( readingDegradation ) {
						if ( data[ 0 ].compareTo( "In air [1/s]" ) == 0 )
							degradationAirDb.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "ENPs (S) in air [1/s]" ) == 0 )
							degAirENPs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in lake water [1/s]" ) == 0 )
							degHeteroLakeWaterENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and SPM (P) in lake water [1/s];" ) == 0 )
							degHeteroLakeWaterENPsSPM.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "deg fresh water" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								degradationFreshWaterDbRadio.setSelectedIndex( 1 ); 
								degradationFreshWaterDbCoarse.setVisible(false);
								degradationFreshWaterDbDetailed.setVisible(true);
								degradationFreshWaterDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In fresh water [1/s]" ) == 0 )
							degradationFreshWaterDb.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "ENPs (S) in lake water [1/s]" ) == 0 )
							degLakeWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "ENPs (S) in fresh water [1/s]" ) == 0 )
							degFreshWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in lake water [1/s]" ) == 0 )
							degHetLakeWaterENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and SPM (P) in lake water [1/s]" ) == 0 )
							degHetLakeWaterENPsSPM.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in fresh water [1/s]" ) == 0 )
							degHetFreshWaterENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );					

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and SPM (P) in fresh water [1/s]" ) == 0 )
							degHetFreshWaterENPsSPM.setValue( Double.valueOf(  data[ 1 ] ) );		

						if ( data[ 0 ].compareTo( "deg sea water" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								degradationSeaWaterDbRadio.setSelectedIndex( 1 ); 
								degradationSeaWaterDbCoarse.setVisible(false);
								degradationSeaWaterDbDetailed.setVisible(true);
								degradationSeaWaterDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In sea water [1/s]" ) == 0 )
							degradationSeaWaterDb.setValue( Double.valueOf(  data[ 1 ] ) );		

						if ( data[ 0 ].compareTo( "ENPs (S) in sea water [1/s];" ) == 0 )
							degSeaWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );		

						if ( data[ 0 ].compareTo( "ENPs and NCs (A) in sea water [1/s]" ) == 0 )
							degSeaWaterENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );		

						if ( data[ 0 ].compareTo( "ENPs and SPM (P) in sea water [1/s]" ) == 0 )
							degSeaWaterENPsSPM.setValue( Double.valueOf(  data[ 1 ] ) );							

						if ( data[ 0 ].compareTo( "deg soil" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								degradationSoilDbRadio.setSelectedIndex( 1 ); 
								degradationSoilDbCoarse.setVisible(false);
								degradationSoilDbDetailed.setVisible(true);
								degradationSoilDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In soil [1/s]" ) == 0 )
							degradationSoilDb.setValue( Double.valueOf(  data[ 1 ] ) );							

						if ( data[ 0 ].compareTo( "ENPs (S) in natural soil pore water [1/s]" ) == 0 )
							degNatSoilPoreWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );							

						if ( data[ 0 ].compareTo( "ENPs (S) in agricultural soil pore water [1/s]" ) == 0 )
							degAgriSoilPoreWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "ENPs (S) in other soil pore water [1/s]" ) == 0 )
							degOtherSoilPoreWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in natural soil pore water [1/s]" ) == 0 )
							degHeteroNatSoilPoreWaterENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in natural soil [1/s]" ) == 0 )
							degHeteroNatSoilENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in agricultural soil pore water [1/s]" ) == 0 )
							degHeteroAgriSoilENPsNC.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in agricultural soil [1/s]" ) == 0 )
							degHeteroAgriSoilENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in other soil pore water [1/s]" ) == 0 )
							degHeteroOtherSoilENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in other soil [1/s]" ) == 0 )
							degHeteroOtherSoilENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "deg fresh sedi" ) == 0 )
							if ( data[ 1 ].compareTo( "true" ) ==  0 ) {
								degradationFreshSediDbRadio.setSelectedIndex( 1 ); 
								degradationFreshSediDbCoarse.setVisible(false);
								degradationFreshSediDbDetailed.setVisible(true);
								degradationFreshSediDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In freshwater sediments [1/s" ) == 0 )
							degradationFreshSedilDb.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "ENPs (S) in lake sediment [1/s]" ) == 0 )
							lakeSedimentENPs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "ENPs (S) in fresh sediment [1/s]" ) == 0 )
							freshSedimentENPs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in lake sediment [1/s]" ) == 0 )
							heteroLakeENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in lake sediment [1/s]" ) == 0 )
							heteroLakeENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in fresh sediment [1/s]" ) == 0 )
							heteroFreshENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in fresh sediment [1/s]" ) == 0 )
							heteroFreshENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );	

						if ( data[ 0 ].compareTo( "deg marine sedi" ) == 0  )
							if ( data[1].compareTo( "true" ) == 0) {							
								degradationMarineSediDbRadio.setSelectedIndex( 1 ); 
								degradationMarineSediDbCoarse.setVisible(false);
								degradationMarineSediDbDetailed.setVisible(true);
								degradationMarineSediDbDetailedReporting.setVisible(false);
							}

						if ( data[ 0 ].compareTo( "In marine sediments [1/s]" ) == 0 )
							degradationMarineSediDb.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs (S) in marine sediment [1/s];" ) == 0 )
							marineSedimentENPs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "ENPs (S) in deep sea water [1/s]" ) == 0 )
							deepSeaWaterENPs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in marine sediment [1/s]" ) == 0 )
							heteroMarineENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and FP (P) in marine sediment [1/s]" ) == 0 )
							heteroMarineENPsFP.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and NCs (A) in deep sea water [1/s]" ) == 0 )
							heteroDeepSeaENPsNCs.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "Heteroagglomerates of ENPs and SPM (P) in deep sea water [1/s]" ) == 0 )
							heteroDeepSeaENPsSPM.setValue( Double.valueOf(  data[ 1 ] ) );

					}

					setCompactOfNotSelected();					
*/
					if ( data[0].compareTo( "Emission rates" ) == 0 ) {
						readingAttachement = false;					
						readingDissolution = false;
						readingDegradation = false;

						readEm = true;
						continue;
					}

					//Read emissions
					if ( readEm && data[0].compareTo( "Regional" ) == 0 ) { 						
						readingEmReg = true;
						readingEmCont = false;
						readingEmArc = false;
						readingEmTrop = false;
						readingEmMod = false;
						continue;
					}

					if ( readEm && data[0].compareTo( "Continental" ) == 0 ) {
						readingEmReg = false;
						readingEmCont = true;
						readingEmArc = false;
						readingEmTrop = false;
						readingEmMod = false;
						continue;
					}

					if ( readEm && data[0].compareTo( "Arctic" ) == 0 ) {
						readingEmReg = false;
						readingEmCont = false;
						readingEmArc = true;
						readingEmTrop = false;
						readingEmMod = false;
						continue;
					}

					if ( readEm && data[0].compareTo( "Tropical" ) == 0 ) {
						readingEmReg = false;
						readingEmCont = false;
						readingEmArc = false;
						readingEmTrop = true;
						readingEmMod = false;
						continue;
					}

					if ( readEm && data[0].compareTo( "Moderate" ) == 0 ) {
						readingEmReg = false;
						readingEmCont = false;
						readingEmArc = false;
						readingEmTrop = false;
						readingEmMod = true;
						continue;
					}

					if ( readingEmReg ) {
						if ( data[ 0 ].compareTo( "to air [tn/y]" ) == 0 )
							toAir.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to lake water [tn/y]" ) == 0 )
							toLakeWater.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to fresh water [tn/y]" ) == 0 )
							toFreshWater.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to sea water [tn/y]" ) == 0 )
							toSea.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to natural soil [tn/y]" ) == 0 )
							toNatSoil.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to agricultural soil [tn/y]" ) == 0 )
							toAgriSoil.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to other soil [tn/y]" ) == 0 )
							toOtherSoil.setValue( Double.valueOf(  data[ 1 ] ) );
					}
					else if ( readingEmCont ) {
						if ( data[ 0 ].compareTo( "to air [tn/y]" ) == 0 )
							toAirCont.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to lake water [tn/y]" ) == 0 )
							toLakeWaterCont.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to fresh water [tn/y]" ) == 0 )
							toFreshWaterCont.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to sea water [tn/y]" ) == 0 )
							toSeaCont.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to natural soil [tn/y]" ) == 0 )
							toNatSoilCont.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to agricultural soil [tn/y]" ) == 0 )
							toAgriSoilCont.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to other soil [tn/y]" ) == 0 )
							toOtherSoilCont.setValue( Double.valueOf(  data[ 1 ] ) );
					}
					else if ( readingEmArc ) {
						if ( data[ 0 ].compareTo( "to air [tn/y]" ) == 0 )
							toAirArc.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to water [tn/y]" ) == 0 )
							toWaterArc.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to soil [tn/y]" ) == 0 )
							toSoilArc.setValue( Double.valueOf(  data[ 1 ] ) );
					}
					else if ( readingEmTrop ) {
						if ( data[ 0 ].compareTo( "to air [tn/y]" ) == 0 )
							toAirTrop.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to water [tn/y]" ) == 0 )
							toWaterTrop.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to soil [tn/y]" ) == 0 )
							toSoilTrop.setValue( Double.valueOf(  data[ 1 ] ) );
					}
					else if ( readingEmMod ) {
						if ( data[ 0 ].compareTo( "to air [tn/y]" ) == 0 )
							toAirMod.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to water [tn/y]" ) == 0 )
							toWaterMod.setValue( Double.valueOf(  data[ 1 ] ) );

						if ( data[ 0 ].compareTo( "to soil [tn/y]" ) == 0 )
							toSoilMod.setValue( Double.valueOf(  data[ 1 ] ) );
					}

					//ADVANCED
					if ( readingAdvReg ) {
						if ( data[ 0 ].compareTo( "Land area [km2]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "AREAland.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Sea area [km2]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "AREAsea.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Lake water fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "FRAClake.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Natural soil fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "FRACnatsoil.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Agricultural soil fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "FRACagsoil.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Urban/industrial fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "FRACothersoil.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Average precipitation [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "RAINrate.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Temperature [K]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "TEMP.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Wind speed [m/s]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "WINDspeed.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Lake water depth [m]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "DEPTHlake.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Fresh water depth [m]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "DEPTHfreshwater.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Run off fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "FRACrun.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Infiltration fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "FRACinf.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Soil erosion [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "EROSION.R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in waters [mg/L]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "SUSP.w0R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in lake water [mg/L]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "SUSP.w0R", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in fresh water [mg/L];" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "SUSP.w1R", data[ 1 ] );

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in sea water [mg/L]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("REGIONAL", "SUSP.w2R", data[ 1 ] );
					}

					if ( readingAdvCont ) {
						if ( data[ 0 ].compareTo( "Total land area [km2]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "TOTAREAland.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Total sea area [km2]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "TOTAREAsea.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Lake water fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "FRAClake.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Natural soil fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "FRACnatsoil.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Agricultural soil fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "FRACagsoil.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Urban/industrial fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "FRACothersoil.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Average precipitation [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "RAINrate.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Temperature [K]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "TEMP.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Wind speed [m/s]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "WINDspeed.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Lake water depth [m]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "DEPTHlake.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Fresh water depth [m]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "DEPTHfreshwater.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Run off fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "FRACrun.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Infiltration fraction [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "FRACinf.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Soil erosion [-]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "EROSION.C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in waters [mg/L]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "SUSP.w0C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in lake water [mg/L]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "SUSP.w0C", data[ 1 ] );  

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in fresh water [mg/L];" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "SUSP.w1C", data[ 1 ] );

						if ( data[ 0 ].compareTo( "Concentration of suspended matter in sea water [mg/L]" ) == 0 )			
							scenariosData.get( scenarioName ).putLandscapeInfo("CONTINENTAL", "SUSP.w2C", data[ 1 ] );
					}

				}
				is.close();

			} else {

				String s = media.getStringData();
				//Issue an error! 

			}
		}

		calculate.setDisabled(false); 
		adjust.setDisabled(false); 
		save.setDisabled(false); 
		load.setVisible(false);
		nanomaterials.setDisabled(false);
	}

	@Listen("onChange=#scenarios")
	public void userDefSelected()
	{
		//Put here the variables according to the scenarios choosen by the user... 
		if ( scenarios.getSelectedItem().getValue().toString().compareTo("User defined ...") == 0 )
			load.setVisible(true);
		else 
			load.setVisible(false);
	}
}
