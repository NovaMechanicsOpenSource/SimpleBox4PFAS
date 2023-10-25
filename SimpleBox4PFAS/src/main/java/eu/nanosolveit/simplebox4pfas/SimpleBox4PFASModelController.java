package eu.nanosolveit.simplebox4nano;

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

public class SimpleBox4NanoModelController extends SelectorComposer<Window>{

	Window win, attach;

	@Wire
	Button calculate, adjust, save, load;

	@Wire
	Combobox scenarios, nanomaterials;

	@Wire 
	Doublebox molweight, radius, density, hamaker;

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

	// Start attachment efficiency
	@Wire
	Listbox attachmentEfficiency;

	@Wire
	Radiogroup freshWaterRadio, attachmentSoilDbRadio, attachmentFreshSediDbRadio, attachmentMarineSediDbRadio;

	@Wire
	Hbox freshWaterCoarse, freshWaterDetailed, freshWaterDetailedReporting, attachmentSoilDbCoarse, attachmentSoilDbDetailed, attachmentSoilDbDetailedReporting;

	@Wire
	Hbox attachmentFreshSediDbDetailed, attachmentFreshSediDbCoarse, attachmentFreshSediDbDetailedReporting;

	@Wire
	Hbox attachmentMarineSediDbDetailed, attachmentMarineSediDbCoarse, attachmentMarineDbDetailedReporting, attachmentMarineSediDbDetailedReporting;

	@Wire
	Label freshWaterCompact, attachmentSoilDbCompact, attachmentFreshSediDbCompact, attachmentMarineSediDbCompact;

	@Wire
	Doublebox freshWater, lakeWaterNC, lakeWaterSPM, freshWaterNC, freshWaterSPM, attachmentSoilDb, naturalSoilNCs, naturalSoilGrains,
	agriSoilNCs, agriSoilGrains, otherSoilNCs, otherSoilGrains;

	@Wire
	Doublebox attachmentFreshSediDb, lakeSedimentNCs, lakeSedimentGrains, freshSedimentNCs, frashSedimentGrains, 
	attachmentMarineSediDb, marineSedimentNCs, marineSedimentGrains;
	// End attachment efficiency

	// Start dissolution rates	
	@Wire
	Listbox dissolutionRates;

	@Wire
	Radiogroup dissolutionFreshDbRadio, dissolutionSeaWaterDbRadio, dissolutionSoilDbRadio, dissolutionFreshSediDbRadio, dissolutionMarineSediDbRadio;

	@Wire
	Hbox dissolutionFreshDbCoarse, dissolutionFreshDbDetailed, dissolutionFreshDbDetailedReporting;

	@Wire
	Hbox dissolutionSeaWaterDbCoarse, dissolutionSeaWaterDbDetailed, dissolutionSeaWaterDbDetailedReporting;

	@Wire
	Hbox dissolutionSoilDbCoarse, dissolutionSoilDbDetailed, dissolutionSoilDbDetailedReporting;

	@Wire
	Hbox dissolutionFreshSediDbCoarse, dissolutionFreshSediDbDetailed, dissolutionFreshSediDbDetailedReporting;

	@Wire
	Hbox dissolutionMarineSediDbCoarse, dissolutionMarineSediDbDetailed, dissolutionMarineSediDbDetailedReporting;

	@Wire
	Label dissolutionFreshDbCompact, dissolutionSeaWaterDbCompact, dissolutionSoilDbCompact, dissolutionFreshSediDbCompact, dissolutionMarineSediDbCompact ;

	@Wire
	Doublebox dissolutionFreshDb, dissolutionlakeWaterENPs, dissolutionfreshWaterSPM, dissolutionlakeWaterENPsNCsA, dissolutionlakeWaterENPsNCsP, dissolutionfreshWaterENPsNCsA, dissolutionfreshWaterENPsNCsP; 

	@Wire
	Doublebox dissolutionSeaWaterDb, dissolutionSeaWaterENPs, dissolutionHeteroENPsNCsSeaWater, dissolutionHeteroENPsSPMsSeaWater; 

	@Wire
	Doublebox dissolutionSoilDb, dissolutionNatSoilPoreWaterENPs, dissolutionAgriSoilPoreWaterENPs, dissolutionOtherSoilPoreWaterENPs, dissolutionNatSoilENPsNCs,
	dissolutionNatSoilENPsFP, dissolutionAgriSoilPoreENPsNCs, dissolutionAgriSoilENPsFP, dissolutionOtherSoilPoreENPsNCs, dissolutionOtherSoileENPsFP; 

	@Wire
	Doublebox dissolutionFreshSediDb, disSedilakeSediENPs, disFreshSediENPs, disLakeSediENPsNCs, disLakeSediENPsFP, disFreshSediENPsNCs, disFreshSediENPsFP; 

	@Wire
	Doublebox dissolutionMarineSediDb, disMarineSedimentENPs, disDeepSeaENPs, disMarineSedimentENPsNCs, disMarineSedimentENPsFP, disDeepSeaWaterENPsNCs, disDeepSeaWaterENPsSPM; 
	// End dissolution rates	

	// Start degradation rates	
	@Wire
	Listbox degradationRates;

	@Wire
	Radiogroup degradationAirDbRadio, degradationFreshWaterDbRadio, degradationSeaWaterDbRadio, degradationSoilDbRadio;

	@Wire
	Radiogroup degradationFreshSediDbRadio, degradationMarineSediDbRadio;

	@Wire
	Hbox degradationAirDbCoarse, degradationAirDbDetailed, degradationAirDbDetailedReporting;

	@Wire
	Hbox degradationFreshWaterDbCoarse, degradationFreshWaterDbDetailed, degradationFreshWaterDbDetailedReporting;

	@Wire
	Hbox degradationSeaWaterDbCoarse, degradationSeaWaterDbDetailed, degradationSeaWaterDbDetailedReporting;

	@Wire
	Hbox degradationSoilDbCoarse, degradationSoilDbDetailed, degradationSoilDbDetailedReporting;

	@Wire
	Hbox degradationFreshSediDbCoarse, degradationFreshSediDbDetailed, degradationFreshSediDbDetailedReporting;

	@Wire
	Hbox degradationMarineSediDbCoarse, degradationMarineSediDbDetailed, degradationMarineSediDbDetailedReporting;

	@Wire
	Label degradationAirDbCompact, degradationFreshWaterDbCompact, degradationSeaWaterDbCompact, degradationSoilDbCompact, degradationFreshSediDbCompact, degradationMarineSediDbCompact;

	@Wire
	Doublebox degradationAirDb, degAirENPs, degHeteroLakeWaterENPsNCs, degHeteroLakeWaterENPsSPM; 

	@Wire
	Doublebox degradationFreshWaterDb, degLakeWaterENPs, degFreshWaterENPs, degHetLakeWaterENPsNCs, degHetLakeWaterENPsSPM, degHetFreshWaterENPsNCs, degHetFreshWaterENPsSPM; 

	@Wire
	Doublebox degradationSeaWaterDb, degSeaWaterENPs, degSeaWaterENPsNCs, degSeaWaterENPsSPM; 

	@Wire
	Doublebox degradationSoilDb, degNatSoilPoreWaterENPs, degAgriSoilPoreWaterENPs, degOtherSoilPoreWaterENPs, degHeteroNatSoilPoreWaterENPsNCs, degHeteroNatSoilENPsFP, degHeteroAgriSoilENPsNC, degHeteroAgriSoilENPsFP, degHeteroOtherSoilENPsNCs, degHeteroOtherSoilENPsFP; 

	@Wire
	Doublebox degradationFreshSedilDb, lakeSedimentENPs, freshSedimentENPs, heteroLakeENPsNCs, heteroLakeENPsFP, heteroFreshENPsNCs, heteroFreshENPsFP; 

	@Wire
	Doublebox degradationMarineSediDb, marineSedimentENPs, deepSeaWaterENPs, heteroMarineENPsNCs, heteroMarineENPsFP, heteroDeepSeaENPsNCs, heteroDeepSeaENPsSPM; 
	// End degradation rates	

	@Wire
	Checkbox globalParams;

//	@Wire 
//	Separator sep1; //, sep2, sep3;

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
		radius.setValue(  Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("RadS") ) );
		density.setValue(  Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("RhoS") ) );
		hamaker.setValue(  Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AHamakerSP.w") ) );

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

		freshWater.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.w0") ) );
		lakeWaterNC.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.w0") ) );
		lakeWaterSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.w0") ) );
		freshWaterNC.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.w1") ) );
		freshWaterSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.w1") ) );

		attachmentSoilDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get( "AtefSA.s1") ) );
		naturalSoilNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.s1") ) );
		naturalSoilGrains.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.s1") ) );
		agriSoilNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.s2") ) );
		agriSoilGrains.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.s2") ) );
		otherSoilNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.s3") ) );
		otherSoilGrains.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.s3") ) );

		attachmentFreshSediDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.sd0") ) );
		lakeSedimentNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.sd0") ) );
		lakeSedimentGrains.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.sd0") ) );
		freshSedimentNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.sd1") ) );
		frashSedimentGrains.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.sd1") ) );

		attachmentMarineSediDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.sd2") ) );
		marineSedimentNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSA.sd2") ) );
		marineSedimentGrains.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("AtefSP.sd2") ) );

		dissolutionFreshDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w0S.w0D") ) );
		dissolutionlakeWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w0S.w0D") ) );
		dissolutionfreshWaterSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w0S.w0D") ) );
		dissolutionlakeWaterENPsNCsA.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w0A.w0D") ) );
		dissolutionlakeWaterENPsNCsP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w0P.w0D") ) );
		dissolutionfreshWaterENPsNCsA.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w1A.w1D") ) );
		dissolutionfreshWaterENPsNCsP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w1P.w1D") ) );	

		dissolutionSeaWaterDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w2S.w2D") ) );
		dissolutionSeaWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w2S.w2D") ) );
		dissolutionHeteroENPsNCsSeaWater.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w2A.w2D") ) );
		dissolutionHeteroENPsSPMsSeaWater.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w2P.w2D") ) );

		dissolutionSoilDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s1S.s1D") ) );
		dissolutionNatSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s1S.s1D") ) );
		dissolutionAgriSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s2S.s2D") ) );
		dissolutionOtherSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s3S.s3D") ) );
		dissolutionNatSoilENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s1A.s1D") ) );
		dissolutionNatSoilENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s1P.s1D") ) );
		dissolutionAgriSoilPoreENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s2A.s2D") ) );
		dissolutionAgriSoilENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s2P.s2D") ) );
		dissolutionOtherSoilPoreENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s3A.s3D") ) );
		dissolutionOtherSoileENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.s3P.s3D") ) );

		dissolutionFreshSediDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd0S.sd0D") ) );
		disSedilakeSediENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd0S.sd0D") ) );
		disFreshSediENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd1S.sd1D") ) );
		disLakeSediENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd0A.sd0D") ) );
		disLakeSediENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd0P.sd0D") ) );
		disFreshSediENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd1A.sd1D") ) );
		disFreshSediENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd1P.sd1D") ) );

		dissolutionMarineSediDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd2S.sd2D") ) );
		disMarineSedimentENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd2S.sd2D") ) );
		disDeepSeaENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w3S.w3D") ) );
		disMarineSedimentENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd2A.sd2D") ) );
		disMarineSedimentENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.sd2P.sd2D") ) );
		disDeepSeaWaterENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w3A.w3D") ) );
		disDeepSeaWaterENPsSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdis.w3P.w3D") ) );

		degradationAirDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.a") ) );
		degAirENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.a") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroLakeWaterENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.a") ) ); 
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroLakeWaterENPsSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.a") ) ); 

		degradationFreshWaterDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.w0") ) );
		degLakeWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.w0") ) );
		degFreshWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.w1") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHetLakeWaterENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.w0") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHetLakeWaterENPsSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.w0") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHetFreshWaterENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.w1") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHetFreshWaterENPsSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.w1") ) );

		degradationSeaWaterDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.w2") ) );
		degSeaWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.w2") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degSeaWaterENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.w2") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degSeaWaterENPsSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.w2") ) );

		degradationSoilDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.s1") ) );
		degNatSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.s1") ) );		
		degAgriSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.s2") ) );
		degOtherSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.s3") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroNatSoilPoreWaterENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.s1") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroNatSoilENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.s1") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroAgriSoilENPsNC.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.s2") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroAgriSoilENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.s2") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroOtherSoilENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.s3") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		degHeteroOtherSoilENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.s3") ) );

		degradationFreshSedilDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.sd0") ) );
		lakeSedimentENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.sd0") ) );
		freshSedimentENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.sd1") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroLakeENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.sd0") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroLakeENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.sd0") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroFreshENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.sd1") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroFreshENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.sd1") ) );

		degradationMarineSediDb.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.sd2") ) );
		marineSedimentENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.sd2") ) );
		deepSeaWaterENPs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegS.w3") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroMarineENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.sd2") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroMarineENPsFP.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.sd2") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroDeepSeaENPsNCs.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegA.w3") ) );
		//Check this value because we added it in nanomaterials excel. Sam has also a comment on that in the online excel
		heteroDeepSeaENPsSPM.setValue( Double.valueOf( nanoData.get( availNMs.get( 0 ) ).get("kdegP.w3") ) );

		load.setVisible(false);
	}

	@Listen("onChange=#nanomaterials,#scenarios")
	public void nanoSceneChanged() {

		//Reload the scenarios
		loadScenarios();

		molweight.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("Molweight") ) );
		radius.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("RadS") ) );
		density.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("RhoS") ) );
		hamaker.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AHamakerSP.w") ) );

		//Fresh water
		freshWater.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.w0") ) );
		lakeWaterNC.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.w0") ) );
		lakeWaterSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.w0") ) );
		freshWaterNC.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.w1") ) );
		freshWaterSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.w1") ) );

		attachmentSoilDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get( "AtefSA.s1") ) );
		naturalSoilNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.s1") ) );
		naturalSoilGrains.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.s1") ) );
		agriSoilNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.s2") ) );
		agriSoilGrains.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.s2") ) );
		otherSoilNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.s3") ) );
		otherSoilGrains.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.s3") ) );

		attachmentFreshSediDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.sd0") ) );
		lakeSedimentNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.sd0") ) );
		lakeSedimentGrains.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.sd0") ) );
		freshSedimentNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.sd1") ) );
		frashSedimentGrains.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.sd1") ) );

		attachmentMarineSediDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.sd2") ) );
		marineSedimentNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSA.sd2") ) );
		marineSedimentGrains.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("AtefSP.sd2") ) );

		dissolutionFreshDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w0S.w0D") ) );
		dissolutionlakeWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w0S.w0D") ) );
		dissolutionfreshWaterSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w1S.w1D") ) );
		dissolutionlakeWaterENPsNCsA.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w0A.w0D") ) );
		dissolutionlakeWaterENPsNCsP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w0P.w0D") ) );
		dissolutionfreshWaterENPsNCsA.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w1A.w1D") ) );
		dissolutionfreshWaterENPsNCsP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w1P.w1D") ) );	

		dissolutionSeaWaterDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w2S.w2D") ) );
		dissolutionSeaWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w2S.w2D") ) );
		dissolutionHeteroENPsNCsSeaWater.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w2A.w2D") ) );
		dissolutionHeteroENPsSPMsSeaWater.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w2P.w2D") ) );

		dissolutionSoilDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s1S.s1D") ) );
		dissolutionNatSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s1S.s1D") ) );
		dissolutionAgriSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s2S.s2D") ) );
		dissolutionOtherSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s3S.s3D") ) );
		dissolutionNatSoilENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s1A.s1D") ) );
		dissolutionNatSoilENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s1P.s1D") ) );
		dissolutionAgriSoilPoreENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s2A.s2D") ) );
		dissolutionAgriSoilENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s2P.s2D") ) );
		dissolutionOtherSoilPoreENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s3A.s3D") ) );
		dissolutionOtherSoileENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.s3P.s3D") ) );

		dissolutionFreshSediDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd0S.sd0D") ) );
		disSedilakeSediENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd0S.sd0D") ) );
		disFreshSediENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd1S.sd1D") ) );
		disLakeSediENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd0A.sd0D") ) );
		disLakeSediENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd0P.sd0D") ) );
		disFreshSediENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd1A.sd1D") ) );
		disFreshSediENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd1P.sd1D") ) );

		dissolutionMarineSediDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd2S.sd2D") ) );
		disMarineSedimentENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd2S.sd2D") ) );
		disDeepSeaENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w3S.w3D") ) );
		disMarineSedimentENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd2A.sd2D") ) );
		disMarineSedimentENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.sd2P.sd2D") ) );
		disDeepSeaWaterENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w3A.w3D") ) );
		disDeepSeaWaterENPsSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdis.w3P.w3D") ) );

		degradationAirDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.a") ) );
		degAirENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.a") ) );
		degHeteroLakeWaterENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.a") ) );
		degHeteroLakeWaterENPsSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.a") ) );

		degradationFreshWaterDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.w0") ) );
		degLakeWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.w0") ) );
		degFreshWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.w1") ) );
		degHetLakeWaterENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.w0") ) );
		degHetLakeWaterENPsSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.w0") ) );
		degHetFreshWaterENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.w1") ) );
		degHetFreshWaterENPsSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.w1") ) );

		degradationSeaWaterDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.w2") ) );
		degSeaWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.w2") ) );
		degSeaWaterENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.w2") ) );
		degSeaWaterENPsSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.w2") ) );

		degradationSoilDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.s1") ) );
		degNatSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.s1") ) );		
		degAgriSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.s2") ) );
		degOtherSoilPoreWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.s3") ) );
		degHeteroNatSoilPoreWaterENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.s1") ) );
		degHeteroNatSoilENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.s1") ) );
		degHeteroAgriSoilENPsNC.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.s2") ) );
		degHeteroAgriSoilENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.s2") ) );
		degHeteroOtherSoilENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.s3") ) );
		degHeteroOtherSoilENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.s3") ) );

		degradationFreshSedilDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.sd0") ) );
		lakeSedimentENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.sd0") ) );
		freshSedimentENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.sd1") ) );
		heteroLakeENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.sd0") ) );
		heteroLakeENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.sd0") ) );
		heteroFreshENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.sd1") ) );
		heteroFreshENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.sd1") ) );

		degradationMarineSediDb.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.sd2") ) );
		marineSedimentENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.sd2") ) );
		deepSeaWaterENPs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegS.w3") ) );
		heteroMarineENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.sd2") ) );
		heteroMarineENPsFP.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.sd2") ) );
		heteroDeepSeaENPsNCs.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegA.w3") ) );
		heteroDeepSeaENPsSPM.setValue( Double.valueOf( nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).get("kdegP.w3") ) );

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

		setCompactOfNotSelected();
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

				Map< String, String > regDissInfo = new HashMap<String, String>();
				for ( int i = 37; i < 44; i++) 
					regDissInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );			
				scenariosData.get( str ).insertDissGasInfo("REGIONAL", regDissInfo);

				Map< String, String > contDissInfo = new HashMap<String, String>();
				for ( int i = 45; i < 52; i++) 
					contDissInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertDissGasInfo("CONTINENTAL", contDissInfo);

				Map< String, String > modDissInfo = new HashMap<String, String>();
				for ( int i = 53; i < 56; i++)
					modDissInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertDissGasInfo("MODERATE", modDissInfo);

				Map< String, String > artDissInfo = new HashMap<String, String>();
				for ( int i = 57; i < 60; i++) 
					artDissInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertDissGasInfo("ARCTIC", artDissInfo);

				Map< String, String > tropDissInfo = new HashMap<String, String>();
				for ( int i = 61; i < 64; i++) 
					tropDissInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertDissGasInfo("TROPICAL", tropDissInfo);

				Map< String, String > regLandInfo = new HashMap<String, String>();
				for ( int i = 68; i < 94; i++) 
					regLandInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertLandscapeInfo("REGIONAL", regLandInfo);

				Map< String, String > contLandInfo = new HashMap<String, String>();
				for ( int i = 96; i < 124; i++) 
					contLandInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue(), String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertLandscapeInfo("CONTINENTAL", contLandInfo);

				Map< String, String > enviInfo = new HashMap<String, String>();
				for ( int i = 126; i < 143; i++) 
					enviInfo.put( sheet.getRow( i ).getCell( 6 ).getStringCellValue() , String.valueOf( sheet.getRow( i ).getCell( iScenario ).getNumericCellValue() ) );
				scenariosData.get( str ).insertEnviInfo( enviInfo );

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
		nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("RadS", String.valueOf(radius.getValue() ) ); 
		nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("RhoS", String.valueOf(density.getValue() ) ); 
		nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AHamakerSP.w", String.valueOf(hamaker.getValue() ) ); 

		if ( freshWaterRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.w0", String.valueOf(freshWater.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.w0", String.valueOf(freshWater.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.w1", String.valueOf(freshWater.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.w1", String.valueOf(freshWater.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.w0", String.valueOf(lakeWaterNC.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.w0", String.valueOf(lakeWaterSPM.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.w1", String.valueOf(freshWaterNC.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.w1", String.valueOf(freshWaterSPM.getValue() ) );
		}

		if ( attachmentSoilDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.s1", String.valueOf(attachmentSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.s1", String.valueOf(attachmentSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.s2", String.valueOf(attachmentSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.s2", String.valueOf(attachmentSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.s3", String.valueOf(attachmentSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.s3", String.valueOf(attachmentSoilDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.s1", String.valueOf(naturalSoilNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.s1", String.valueOf(naturalSoilGrains.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.s2", String.valueOf(agriSoilNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.s2", String.valueOf(agriSoilGrains.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.s3", String.valueOf(otherSoilNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.s3", String.valueOf(otherSoilGrains.getValue() ) );
		}

		if ( attachmentFreshSediDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.sd0", String.valueOf(attachmentFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.sd0", String.valueOf(attachmentFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.sd1", String.valueOf(attachmentFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.sd1", String.valueOf(attachmentFreshSediDb.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.sd0", String.valueOf(lakeSedimentNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.sd0", String.valueOf(lakeSedimentGrains.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.sd1", String.valueOf(freshSedimentNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.sd1", String.valueOf(frashSedimentGrains.getValue() ) );		
		}

		if ( attachmentMarineSediDbRadio.getSelectedIndex() == 0 ) {	
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.sd2", String.valueOf(attachmentMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.sd2", String.valueOf(attachmentMarineSediDb.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSA.sd2", String.valueOf(marineSedimentNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("AtefSP.sd2", String.valueOf(marineSedimentGrains.getValue() ) );
		}

		if ( dissolutionFreshDbRadio.getSelectedIndex() == 0 ) {	
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w0S.w0D", String.valueOf(dissolutionFreshDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w1S.w1D", String.valueOf(dissolutionFreshDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w0A.w0D", String.valueOf(dissolutionFreshDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w0P.w0D", String.valueOf(dissolutionFreshDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w1A.w1D", String.valueOf(dissolutionFreshDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w1P.w1D", String.valueOf(dissolutionFreshDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w0S.w0D", String.valueOf(dissolutionlakeWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w1S.w1D", String.valueOf(dissolutionfreshWaterSPM.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w0A.w0D", String.valueOf(dissolutionlakeWaterENPsNCsA.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w0P.w0D", String.valueOf(dissolutionlakeWaterENPsNCsP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w1A.w1D", String.valueOf(dissolutionfreshWaterENPsNCsA.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w1P.w1D", String.valueOf(dissolutionfreshWaterENPsNCsP.getValue() ) ); 
		}

		if ( dissolutionSeaWaterDbRadio.getSelectedIndex() == 0 ) {	
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w2S.w2D", String.valueOf(dissolutionSeaWaterDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w2A.w2D", String.valueOf(dissolutionSeaWaterDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w2P.w2D", String.valueOf(dissolutionSeaWaterDb.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w2S.w2D", String.valueOf(dissolutionSeaWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w2A.w2D", String.valueOf(dissolutionHeteroENPsNCsSeaWater.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w2P.w2D", String.valueOf(dissolutionHeteroENPsSPMsSeaWater.getValue() ) );
		}

		if ( dissolutionSoilDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s1S.s1D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s2S.s2D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s3S.s3D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s1A.s1D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s1P.s1D", String.valueOf(dissolutionSoilDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s2A.s2D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s2P.s2D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s3A.s3D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s3P.s3D", String.valueOf(dissolutionSoilDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s1S.s1D", String.valueOf(dissolutionNatSoilPoreWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s2S.s2D", String.valueOf(dissolutionAgriSoilPoreWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s3S.s3D", String.valueOf(dissolutionOtherSoilPoreWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s1A.s1D", String.valueOf(dissolutionNatSoilENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s1P.s1D", String.valueOf(dissolutionNatSoilENPsFP.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s2A.s2D", String.valueOf(dissolutionAgriSoilPoreENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s2P.s2D", String.valueOf(dissolutionAgriSoilENPsFP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s3A.s3D", String.valueOf(dissolutionOtherSoilPoreENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.s3P.s3D", String.valueOf(dissolutionOtherSoileENPsFP.getValue() ) ); 			
		}

		if ( dissolutionFreshSediDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd0S.sd0D", String.valueOf(dissolutionFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd1S.sd1D", String.valueOf(dissolutionFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd0A.sd0D", String.valueOf(dissolutionFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd0P.sd0D", String.valueOf(dissolutionFreshSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd1A.sd1D", String.valueOf(dissolutionFreshSediDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd1P.sd1D", String.valueOf(dissolutionFreshSediDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd0S.sd0D", String.valueOf(disSedilakeSediENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd1S.sd1D", String.valueOf(disFreshSediENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd0A.sd0D", String.valueOf(disLakeSediENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd0P.sd0D", String.valueOf(disLakeSediENPsFP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd1A.sd1D", String.valueOf(disFreshSediENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd1P.sd1D", String.valueOf(disFreshSediENPsFP.getValue() ) ); 
		}

		if ( dissolutionMarineSediDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd2S.sd2D", String.valueOf(dissolutionMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w3S.w3D", String.valueOf(dissolutionMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd2A.sd2D", String.valueOf(dissolutionMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd2P.sd2D", String.valueOf(dissolutionMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w3A.w3D", String.valueOf(dissolutionMarineSediDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w3P.w3D", String.valueOf(dissolutionMarineSediDb.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd2S.sd2D", String.valueOf(disMarineSedimentENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w3S.w3D", String.valueOf(disDeepSeaENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd2A.sd2D", String.valueOf(disMarineSedimentENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.sd2P.sd2D", String.valueOf(disMarineSedimentENPsFP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w3A.w3D", String.valueOf(disDeepSeaWaterENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdis.w3P.w3D", String.valueOf(disDeepSeaWaterENPsSPM.getValue() ) );
		}

		if ( degradationAirDbRadio.getSelectedIndex() == 0 ) {		
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.a", String.valueOf(degradationAirDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.a", String.valueOf(degradationAirDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.a", String.valueOf(degradationAirDb.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.a", String.valueOf(degAirENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.a", String.valueOf(degHeteroLakeWaterENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.a", String.valueOf(degHeteroLakeWaterENPsSPM.getValue() ) );			
		}

		if ( degradationFreshWaterDbRadio.getSelectedIndex() == 0  ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w0", String.valueOf(degradationFreshWaterDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w1", String.valueOf(degradationFreshWaterDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w0", String.valueOf(degradationFreshWaterDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w0", String.valueOf(degradationFreshWaterDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w1", String.valueOf(degradationFreshWaterDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w1", String.valueOf(degradationFreshWaterDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w0", String.valueOf(degLakeWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w1", String.valueOf(degFreshWaterENPs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w0", String.valueOf(degHetLakeWaterENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w0", String.valueOf(degHetLakeWaterENPsSPM.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w1", String.valueOf(degHetFreshWaterENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w1", String.valueOf(degHetFreshWaterENPsSPM.getValue() ) ); 
		}

		if ( degradationSeaWaterDbRadio.getSelectedIndex() == 0  ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w2", String.valueOf(degradationSeaWaterDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w2", String.valueOf(degradationSeaWaterDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w2", String.valueOf(degradationSeaWaterDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w2", String.valueOf(degSeaWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w2", String.valueOf(degSeaWaterENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w2", String.valueOf(degSeaWaterENPsSPM.getValue() ) ); 			
		}

		if ( degradationSoilDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.s1", String.valueOf(degradationSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.s2", String.valueOf(degradationSoilDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.s3", String.valueOf(degradationSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.s1", String.valueOf(degradationSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.s1", String.valueOf(degradationSoilDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.s2", String.valueOf(degradationSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.s2", String.valueOf(degradationSoilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.s3", String.valueOf(degradationSoilDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.s3", String.valueOf(degradationSoilDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.s1", String.valueOf(degNatSoilPoreWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.s2", String.valueOf(degAgriSoilPoreWaterENPs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.s3", String.valueOf(degOtherSoilPoreWaterENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.s1", String.valueOf(degHeteroNatSoilPoreWaterENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.s1", String.valueOf(degHeteroNatSoilENPsFP.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.s2", String.valueOf(degHeteroAgriSoilENPsNC.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.s2", String.valueOf(degHeteroAgriSoilENPsFP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.s3", String.valueOf(degHeteroOtherSoilENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.s3", String.valueOf(degHeteroOtherSoilENPsFP.getValue() ) ); 
		}

		if ( degradationFreshSediDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.sd0", String.valueOf(degradationFreshSedilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.sd1", String.valueOf(degradationFreshSedilDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.sd0", String.valueOf(degradationFreshSedilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.sd0", String.valueOf(degradationFreshSedilDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.sd1", String.valueOf(degradationFreshSedilDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.sd1", String.valueOf(degradationFreshSedilDb.getValue() ) ); 
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.sd0", String.valueOf(lakeSedimentENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.sd1", String.valueOf(freshSedimentENPs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.sd0", String.valueOf(heteroLakeENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.sd0", String.valueOf(heteroLakeENPsFP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.sd1", String.valueOf(heteroFreshENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.sd1", String.valueOf(heteroFreshENPsFP.getValue() ) ); 
		}

		if ( degradationMarineSediDbRadio.getSelectedIndex() == 0 ) {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.sd2", String.valueOf(degradationMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w3", String.valueOf(degradationMarineSediDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.sd2", String.valueOf(degradationMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.sd2", String.valueOf(degradationMarineSediDb.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w3", String.valueOf(degradationMarineSediDb.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w3", String.valueOf(degradationMarineSediDb.getValue() ) );
		}
		else {
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.sd2", String.valueOf(marineSedimentENPs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegS.w3", String.valueOf(deepSeaWaterENPs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.sd2", String.valueOf(heteroMarineENPsNCs.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.sd2", String.valueOf(heteroMarineENPsFP.getValue() ) ); 
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegA.w3", String.valueOf(heteroDeepSeaENPsNCs.getValue() ) );
			nanoData.get( nanomaterials.getSelectedItem().getValue().toString() ).put("kdegP.w3", String.valueOf(heteroDeepSeaENPsSPM.getValue() ) );
		}
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

		SimpleBox4NanoModel model = new SimpleBox4NanoModel( 
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

	@Listen("onSelect=#attachmentEfficiency")
	public void setLine(Event ev)
	{
		Listitem item = attachmentEfficiency.getSelectedItem();

		if(freshWaterRadio.getSelectedIndex() == 1)
		{
			freshWaterCoarse.setVisible(false);

			if( item.getId().equals("freshWaterItem"))
			{
				freshWaterDetailed.setVisible(true);
				freshWaterDetailedReporting.setVisible(false);
			}
			else
			{
				freshWaterDetailed.setVisible(false);
				freshWaterDetailedReporting.setVisible(true);
			}
		}
		else
		{
			freshWaterCoarse.setVisible(true);
			freshWaterDetailed.setVisible(false);
			freshWaterDetailedReporting.setVisible(false);
		}

		if(attachmentSoilDbRadio.getSelectedIndex() == 1)
		{
			attachmentSoilDbCoarse.setVisible(false);

			if(item.getId().equals("attachmentSoilDbItem"))
			{
				attachmentSoilDbDetailed.setVisible(true);
				attachmentSoilDbDetailedReporting.setVisible(false);
			}
			else
			{
				attachmentSoilDbDetailed.setVisible(false);
				attachmentSoilDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			attachmentSoilDbCoarse.setVisible(true);
			attachmentSoilDbDetailed.setVisible(false);
			attachmentSoilDbDetailedReporting.setVisible(false);
		}

		//Attachment Fresh sediment
		if(attachmentFreshSediDbRadio.getSelectedIndex() == 1)
		{
			attachmentFreshSediDbCoarse.setVisible(false);

			if(item.getId().equals("attachmentFreshSediDbItem"))
			{
				attachmentFreshSediDbDetailed.setVisible(true);
				attachmentFreshSediDbDetailedReporting.setVisible(false);
			}
			else
			{
				attachmentFreshSediDbDetailed.setVisible(false);
				attachmentFreshSediDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			attachmentFreshSediDbCoarse.setVisible(true);
			attachmentFreshSediDbDetailed.setVisible(false);
			attachmentFreshSediDbDetailedReporting.setVisible(false);
		}

		//Attachment Marine sediment
		if(attachmentMarineSediDbRadio.getSelectedIndex() == 1)
		{
			attachmentMarineSediDbCoarse.setVisible(false);

			if(item.getId().equals("attachmentMarineSediDbItem"))
			{
				attachmentMarineSediDbDetailed.setVisible(true);
				attachmentMarineSediDbDetailedReporting.setVisible(false);
			}
			else
			{
				attachmentMarineSediDbDetailed.setVisible(false);
				attachmentMarineSediDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			attachmentMarineSediDbCoarse.setVisible(true);
			attachmentMarineSediDbDetailed.setVisible(false);
			attachmentMarineSediDbDetailedReporting.setVisible(false);
		}

		setCompactOfNotSelected();
		attachmentEfficiency.clearSelection();
	}

	@Listen("onSelect=#dissolutionRates")
	public void setLineDissolutionRates(Event ev)
	{
		Listitem item = dissolutionRates.getSelectedItem();

		if(dissolutionFreshDbRadio.getSelectedIndex() == 1)
		{
			dissolutionFreshDbCoarse.setVisible(false);

			if(item.getId().equals("dissolutionFreshDbItem"))
			{
				dissolutionFreshDbDetailed.setVisible(true);
				dissolutionFreshDbDetailedReporting.setVisible(false);
			}
			else
			{
				dissolutionFreshDbDetailed.setVisible(false);
				dissolutionFreshDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			dissolutionFreshDbCoarse.setVisible(true);
			dissolutionFreshDbDetailed.setVisible(false);
			dissolutionFreshDbDetailedReporting.setVisible(false);
		}

		if(dissolutionSeaWaterDbRadio.getSelectedIndex() == 1)
		{
			dissolutionSeaWaterDbCoarse.setVisible(false);

			if(item.getId().equals("dissolutionSeaWaterDbItem"))
			{
				dissolutionSeaWaterDbDetailed.setVisible(true);
				dissolutionSeaWaterDbDetailedReporting.setVisible(false);
			}
			else
			{
				dissolutionSeaWaterDbDetailed.setVisible(false);
				dissolutionSeaWaterDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			dissolutionSeaWaterDbCoarse.setVisible(true);
			dissolutionSeaWaterDbDetailed.setVisible(false);
			dissolutionSeaWaterDbDetailedReporting.setVisible(false);
		}

		if(dissolutionSoilDbRadio.getSelectedIndex() == 1)
		{
			dissolutionSoilDbCoarse.setVisible(false);

			if(item.getId().equals("dissolutionSoilDbItem"))
			{
				dissolutionSoilDbDetailed.setVisible(true);
				dissolutionSoilDbDetailedReporting.setVisible(false);
			}
			else
			{
				dissolutionSoilDbDetailed.setVisible(false);
				dissolutionSoilDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			dissolutionSoilDbCoarse.setVisible(true);
			dissolutionSoilDbDetailed.setVisible(false);
			dissolutionSoilDbDetailedReporting.setVisible(false);
		}

		if( dissolutionFreshSediDbRadio.getSelectedIndex() == 1)
		{
			dissolutionFreshSediDbCoarse.setVisible(false);

			if(item.getId().equals("dissolutionFreshSediDbItem"))
			{
				dissolutionFreshSediDbDetailed.setVisible(true);
				dissolutionFreshSediDbDetailedReporting.setVisible(false);
			}
			else
			{
				dissolutionFreshSediDbDetailed.setVisible(false);
				dissolutionFreshSediDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			dissolutionFreshSediDbCoarse.setVisible(true);
			dissolutionFreshSediDbDetailed.setVisible(false);
			dissolutionFreshSediDbDetailedReporting.setVisible(false);
		}

		if( dissolutionMarineSediDbRadio.getSelectedIndex() == 1)
		{
			dissolutionMarineSediDbCoarse.setVisible(false);

			if(item.getId().equals("dissolutionMarineSediDbItem"))
			{
				dissolutionMarineSediDbDetailed.setVisible(true);
				dissolutionMarineSediDbDetailedReporting.setVisible(false);
			}
			else
			{
				dissolutionMarineSediDbDetailed.setVisible(false);
				dissolutionMarineSediDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			dissolutionMarineSediDbCoarse.setVisible(true);
			dissolutionMarineSediDbDetailed.setVisible(false);
			dissolutionMarineSediDbDetailedReporting.setVisible(false);
		}

		setCompactOfNotSelected();
		dissolutionRates.clearSelection();
	}

	@Listen("onSelect=#degradationRates")
	public void setLineDegradationRates(Event ev)
	{
		Listitem item = degradationRates.getSelectedItem();

		if(degradationAirDbRadio.getSelectedIndex() == 1)
		{
			degradationAirDbCoarse.setVisible(false);

			if(item.getId().equals("degradationAirDbItem"))
			{
				degradationAirDbDetailed.setVisible(true);
				degradationAirDbDetailedReporting.setVisible(false);
			}
			else
			{
				degradationAirDbDetailed.setVisible(false);
				degradationAirDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			degradationAirDbCoarse.setVisible(true);
			degradationAirDbDetailed.setVisible(false);
			degradationAirDbDetailedReporting.setVisible(false);
		}

		if(degradationFreshWaterDbRadio.getSelectedIndex() == 1)
		{
			degradationFreshWaterDbCoarse.setVisible(false);

			if(item.getId().equals("degradationFreshWaterDbItem"))
			{
				degradationFreshWaterDbDetailed.setVisible(true);
				degradationFreshWaterDbDetailedReporting.setVisible(false);
			}
			else
			{
				degradationFreshWaterDbDetailed.setVisible(false);
				degradationFreshWaterDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			degradationFreshWaterDbCoarse.setVisible(true);
			degradationFreshWaterDbDetailed.setVisible(false);
			degradationFreshWaterDbDetailedReporting.setVisible(false);
		}

		if(degradationSeaWaterDbRadio.getSelectedIndex() == 1)
		{
			degradationSeaWaterDbCoarse.setVisible(false);

			if(item.getId().equals("degradationSeaWaterDbItem"))
			{
				degradationSeaWaterDbDetailed.setVisible(true);
				degradationSeaWaterDbDetailedReporting.setVisible(false);
			}
			else
			{
				degradationSeaWaterDbDetailed.setVisible(false);
				degradationSeaWaterDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			degradationSeaWaterDbCoarse.setVisible(true);
			degradationSeaWaterDbDetailed.setVisible(false);
			degradationSeaWaterDbDetailedReporting.setVisible(false);
		}

		if(degradationSoilDbRadio.getSelectedIndex() == 1)
		{
			degradationSoilDbCoarse.setVisible(false);

			if(item.getId().equals("degradationSoilDbItem"))
			{
				degradationSoilDbDetailed.setVisible(true);
				degradationSoilDbDetailedReporting.setVisible(false);
			}
			else
			{
				degradationSoilDbDetailed.setVisible(false);
				degradationSoilDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			degradationSoilDbCoarse.setVisible(true);
			degradationSoilDbDetailed.setVisible(false);
			degradationSoilDbDetailedReporting.setVisible(false);
		}

		if(degradationFreshSediDbRadio.getSelectedIndex() == 1)
		{
			degradationFreshSediDbCoarse.setVisible(false);

			if(item.getId().equals("degradationFreshSediDbItem"))
			{
				degradationFreshSediDbDetailed.setVisible(true);
				degradationFreshSediDbDetailedReporting.setVisible(false);
			}
			else
			{
				degradationFreshSediDbDetailed.setVisible(false);
				degradationFreshSediDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			degradationFreshSediDbCoarse.setVisible(true);
			degradationFreshSediDbDetailed.setVisible(false);
			degradationFreshSediDbDetailedReporting.setVisible(false);
		}

		if(degradationMarineSediDbRadio.getSelectedIndex() == 1)
		{
			degradationMarineSediDbCoarse.setVisible(false);

			if(item.getId().equals("degradationMarineSediDbItem"))
			{
				degradationMarineSediDbDetailed.setVisible(true);
				degradationMarineSediDbDetailedReporting.setVisible(false);
			}
			else
			{
				degradationMarineSediDbDetailed.setVisible(false);
				degradationMarineSediDbDetailedReporting.setVisible(true);
			}
		}
		else
		{
			degradationMarineSediDbCoarse.setVisible(true);
			degradationMarineSediDbDetailed.setVisible(false);
			degradationMarineSediDbDetailedReporting.setVisible(false);
		}

		setCompactOfNotSelected();
		degradationRates.clearSelection();
	}

	@Listen("onCheck=#freshWaterRadio")
	public void setSelectedFreshWaterRadio(CheckEvent ev)
	{
		attachmentEfficiency.setSelectedIndex(0);
		setLine(ev);
	}

	@Listen("onCheck=#attachmentSoilDbRadio")
	public void setSelectedSoilRadio(CheckEvent ev)
	{
		attachmentEfficiency.setSelectedIndex(1);
		setLine(ev);
	}

	@Listen("onCheck=#attachmentFreshSediDbRadio")
	public void setSelectedFreshSediRadio(CheckEvent ev)
	{
		attachmentEfficiency.setSelectedIndex(2);
		setLine(ev);
	}

	@Listen("onCheck=#attachmentMarineSediDbRadio")
	public void setSelectedMarineSediRadio(CheckEvent ev)
	{
		attachmentEfficiency.setSelectedIndex(3);
		setLine(ev);
	}

	@Listen("onCheck=#dissolutionFreshDbRadio")
	public void setSelectedDisFreshRadio(CheckEvent ev)
	{
		dissolutionRates.setSelectedIndex(0);
		setLineDissolutionRates(ev);
	}

	@Listen("onCheck=#dissolutionSeaWaterDbRadio")
	public void setSelectedDisSeaWaterRadio(CheckEvent ev)
	{
		dissolutionRates.setSelectedIndex(1);
		setLineDissolutionRates(ev);
	}

	@Listen("onCheck=#dissolutionSoilDbRadio")
	public void setSelectedDisSoilRadio(CheckEvent ev)
	{
		dissolutionRates.setSelectedIndex(2);
		setLineDissolutionRates(ev);
	}

	@Listen("onCheck=#dissolutionFreshSediDbRadio")
	public void setSelectedFreshSediRadioDis(CheckEvent ev)
	{
		dissolutionRates.setSelectedIndex(3);
		setLineDissolutionRates(ev);
	}

	@Listen("onCheck=#dissolutionMarineSediDbRadio")
	public void setSelectedMarineSediRadioDis(CheckEvent ev)
	{
		dissolutionRates.setSelectedIndex(4);
		setLineDissolutionRates(ev);
	}

	@Listen("onCheck=#degradationAirDbRadio")
	public void setSelectedAirRadioDeg(CheckEvent ev)
	{
		degradationRates.setSelectedIndex(0);
		setLineDegradationRates(ev);
	}

	@Listen("onCheck=#degradationFreshWaterDbRadio")
	public void setSelectedFreshWaterRadioDeg(CheckEvent ev)
	{
		degradationRates.setSelectedIndex(1);
		setLineDegradationRates(ev);
	}

	@Listen("onCheck=#degradationSeaWaterDbRadio")
	public void setSelectedSeaWaterRadioDeg(CheckEvent ev)
	{
		degradationRates.setSelectedIndex(2);
		setLineDegradationRates(ev);
	}

	@Listen("onCheck=#degradationSoilDbRadio")
	public void setSelectedSoilRadioDeg(CheckEvent ev)
	{
		degradationRates.setSelectedIndex(3);
		setLineDegradationRates(ev);
	}

	@Listen("onCheck=#degradationFreshSediDbRadio")
	public void setSelectedFreshSediRadioDeg(CheckEvent ev)
	{
		degradationRates.setSelectedIndex(4);
		setLineDegradationRates(ev);
	}

	@Listen("onCheck=#degradationMarineSediDbRadio")
	public void setSelectedMarineSediRadioDeg(CheckEvent ev)
	{
		degradationRates.setSelectedIndex(5);
		setLineDegradationRates(ev);
	}

	void setCompactOfNotSelected()
	{
		try
		{
			freshWaterCompact.setValue("(Lake water NCs=" + lakeWaterNC.getText() 
			+ " [-], Lake water SPM = " + lakeWaterSPM.getText() 
			+ " [-], Fresh water NCs = " + freshWaterNC.getText() 
			+ " [-], Fresh water SPM = " + freshWaterSPM.getText() 
			+ " [-] )");
		}
		catch(Exception e)
		{
			freshWaterCompact.setValue("Missing values");
		}

		try
		{
			attachmentSoilDbCompact.setValue("(Natural soil NCs = " + naturalSoilNCs.getText() 
			+ " [-], Natural soil grains = " + naturalSoilGrains.getText() + " [-], Agricultural soil NCs = " + agriSoilNCs.getText() 
			+ " [-], Agricultural soil grains = " + agriSoilGrains.getText() + " [-], Other soil NCs = " + otherSoilNCs.getText() 
			+ " [-], Other soil grains = " + otherSoilGrains.getText() + " [-] )");
		}
		catch(Exception e)
		{
			attachmentSoilDbCompact.setValue("Missing values");
		}

		try
		{
			attachmentFreshSediDbCompact.setValue("(Lake sediment NCs (less than 450 nm) = " + lakeSedimentNCs.getText() 
			+ " [-], Lake sediment grains = " + lakeSedimentGrains.getText() + " [-], Fresh sediment NCs (less than 450 nm) = " + freshSedimentNCs.getText() 
			+ " [-], Fresh sediment grains = " + frashSedimentGrains.getText() 
			+ " [-])");
		}
		catch(Exception e)
		{
			attachmentFreshSediDbCompact.setValue("Missing values");
		}

		try
		{
			attachmentMarineSediDbCompact.setValue("(Marine sediment NCs (less than 450 nm) = " + marineSedimentNCs.getText() 
			+ " [-], Marine sediment grains = " + marineSedimentGrains.getText() 
			+ " [-])");
		}
		catch(Exception e)
		{
			attachmentMarineSediDbCompact.setValue("Missing values");
		}

		try
		{
			dissolutionFreshDbCompact.setValue("(ENPs (S) in lake water = " + dissolutionlakeWaterENPs.getText() 
			+ " [1/s], ENPs (S) in fresh water = " + dissolutionfreshWaterSPM.getText() 
			+ " [1/s], ENPs and NCs (A) in lake water = " +  dissolutionlakeWaterENPsNCsA.getText() 
			+ " [1/s], ENPs and NCs (P) in lake water = " + dissolutionlakeWaterENPsNCsP.getText() 
			+ " [1/s], ENPs and NCs (A) in fresh water = " + dissolutionfreshWaterENPsNCsA.getText() 
			+ " [1/s], ENPs and NCs (P) in fresh water = " + dissolutionfreshWaterENPsNCsP.getText() 
			+ " [1/s])" );
		}
		catch(Exception e)
		{
			dissolutionFreshDbCompact.setValue("Missing values");
		}

		try
		{
			dissolutionSeaWaterDbCompact.setValue("(ENPs (S) in sea water = " + dissolutionSeaWaterENPs.getText() 
			+ " [1/s], Heteroagglomerates of ENPs and NCs (A) in sea water = " + dissolutionHeteroENPsNCsSeaWater.getText() 
			+ " [1/s], Heteroagglomerates of ENPs and SPM (P) in sea water = " +  dissolutionHeteroENPsSPMsSeaWater.getText() 
			+  ")" );
		}
		catch(Exception e)
		{
			dissolutionSeaWaterDbCompact.setValue("Missing values");
		}

		try
		{
			dissolutionSoilDbCompact.setValue("(ENPs (S) in natural soil pore water [1/s] = " + dissolutionNatSoilPoreWaterENPs.getText()
			+ " [1/s], ENPs (S) in agricultural soil pore water [1/s] = " + dissolutionAgriSoilPoreWaterENPs.getText() 
			+ " [1/s], ENPs (S) in other soil pore water [1/s] = " + dissolutionOtherSoilPoreWaterENPs.getText()
			+ " [1/s], ENPs and NCs (A) in natural soil pore water [1/s] = " + dissolutionNatSoilENPsNCs.getText()
			+ " [1/s], ENPs and FP (P) in natural soil = " +  dissolutionNatSoilENPsFP.getText() 
			+ " [1/s], ENPs and NCs (A) in agricultural soil pore water = " +  dissolutionAgriSoilPoreENPsNCs.getText() 
			+ " [1/s], ENPs and FP (P) in agricultural soil = " +  dissolutionAgriSoilENPsFP.getText() 
			+ " [1/s], ENPs and NCs (A) in other soil pore water [1/s] = " + dissolutionOtherSoilPoreENPsNCs.getText()
			+ " [1/s], ENPs and FP (P) in other soil [1/s] = " + dissolutionOtherSoileENPsFP.getText()
			+ " [1/s])" );
		}
		catch(Exception e)
		{
			dissolutionSoilDbCompact.setValue("Missing values");
		}

		try
		{
			dissolutionFreshSediDbCompact.setValue("(ENPs (S) in lake sediment = " + disSedilakeSediENPs.getText() 
			+ " [1/s], ENPs (S) in fresh sediment = " + disFreshSediENPs.getText() 
			+ " [1/s], ENPs and NCs (A) in lake sediment = " +  disLakeSediENPsNCs.getText() 
			+ " [1/s], ENPs and FP (P) in lake sediment = " +  disLakeSediENPsFP.getText() 
			+ " [1/s], ENPs and NCs (A) in fresh sediment = " +  disFreshSediENPsNCs.getText() 
			+ " [1/s], ENPs and FP (P) in fresh sediment = " +  disFreshSediENPsFP.getText() 
			+  " [1/s])" );
		}
		catch(Exception e)
		{
			dissolutionFreshSediDbCompact.setValue("Missing values");
		}

		try
		{
			dissolutionMarineSediDbCompact.setValue("(ENPs (S) in marine sediment = " + disMarineSedimentENPs.getText() +
					" [1/s], ENPs (S) in deep sea water = " + disDeepSeaENPs.getText() + 
					" [1/s], ENPs and NCs (A) in marine sediment = " +  disMarineSedimentENPsNCs.getText() +
					" [1/s], ENPs and FP (P) in marine sediment = " +  disMarineSedimentENPsFP.getText() +
					" [1/s], ENPs and NCs (A) in deep sea water = " +  disDeepSeaWaterENPsNCs.getText() + 
					" [1/s], ENPs and SPM (P) in deep sea water = " +  disDeepSeaWaterENPsSPM.getText() +  
					" [1/s])" );
		}
		catch(Exception e)
		{
			dissolutionMarineSediDbCompact.setValue("Missing values");
		}

		try
		{
			degradationAirDbCompact.setValue("(ENPs (S) in air = " + degAirENPs.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in lake water = " + degHeteroLakeWaterENPsNCs.getText() + 
					" [1/s], Heteroagglomerates of ENPs and SPM (P) in lake water = " +  degHeteroLakeWaterENPsSPM.getText() +
					" [1/s])" );
		}
		catch(Exception e)
		{
			degradationAirDbCompact.setValue("Missing values");
		}

		try
		{
			degradationFreshWaterDbCompact.setValue("(ENPs (S) in lake water = " + degLakeWaterENPs.getText() +
					" [1/s], ENPs (S) in fresh water = " + degFreshWaterENPs.getText() + 
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in lake water = " +  degHetLakeWaterENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and SPM (P) in lake water = " +  degHetLakeWaterENPsSPM.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in fresh water = " +  degHetFreshWaterENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and SPM (P) in fresh water = " +  degHetFreshWaterENPsSPM.getText() +
					" [1/s])" );
		}
		catch(Exception e)
		{
			degradationSeaWaterDbCompact.setValue("Missing values");
		}

		try
		{
			degradationSeaWaterDbCompact.setValue("(ENPs (S) in sea water = " + degSeaWaterENPs.getText() +
					" [1/s], ENPs and NCs (A) in sea water = " + degSeaWaterENPsNCs.getText() + 
					" [1/s], ENPs and SPM (P) in sea water = " + degSeaWaterENPsSPM.getText() +
					" [1/s])" );
		}
		catch(Exception e)
		{
			degradationSeaWaterDbCompact.setValue("Missing values");
		}

		try
		{
			degradationSoilDbCompact.setValue("(ENPs (S) in natural soil pore water = " + degNatSoilPoreWaterENPs.getText() +
					" [1/s], ENPs (S) in agricultural soil pore water = " + degAgriSoilPoreWaterENPs.getText() + 
					" [1/s], ENPs (S) in other soil pore water = " + degOtherSoilPoreWaterENPs.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in natural soil pore water = " + degHeteroNatSoilPoreWaterENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and FP (P) in natural soil = " + degHeteroNatSoilENPsFP.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in agricultural soil pore water = " + degHeteroAgriSoilENPsNC.getText() +
					" [1/s], Heteroagglomerates of ENPs and FP (P) in agricultural soil = " + degHeteroAgriSoilENPsFP.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in other soil pore water = " + degHeteroOtherSoilENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and FP (P) in other soil = " + degHeteroOtherSoilENPsFP.getText() +
					" [1/s])" );
		}
		catch(Exception e)
		{
			degradationSoilDbCompact.setValue("Missing values");
		}

		try
		{
			degradationFreshSediDbCompact.setValue("(ENPs (S) in lake sediment = " + lakeSedimentENPs.getText() +
					" [1/s], ENPs (S) in fresh sediment = " + freshSedimentENPs.getText() + 
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in lake sediment = " + heteroLakeENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and FP (P) in lake sediment = " + heteroLakeENPsFP.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in fresh sediment = " + heteroFreshENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and FP (P) in fresh sediment = " + heteroFreshENPsFP.getText() +
					" [1/s])" );
		}
		catch(Exception e)
		{
			degradationFreshSediDbCompact.setValue("Missing values");
		}

		try
		{
			degradationMarineSediDbCompact.setValue("(ENPs (S) in marine  sediment = " + marineSedimentENPs.getText() +
					" [1/s], ENPs (S) in deep sea water = " + deepSeaWaterENPs.getText() + 
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in marine sediment = " + heteroMarineENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and FP (P) in marine sediment = " + heteroMarineENPsFP.getText() +
					" [1/s], Heteroagglomerates of ENPs and NCs (A) in deep sea water = " + heteroDeepSeaENPsNCs.getText() +
					" [1/s], Heteroagglomerates of ENPs and SPM (P) in deep sea water = " + heteroDeepSeaENPsSPM.getText() +
					" [1/s])" );
		}
		catch(Exception e)
		{
			degradationMarineSediDbCompact.setValue("Missing values");
		}
	}


}
