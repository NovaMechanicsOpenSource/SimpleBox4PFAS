package eu.proplanet.simplebox4pfas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import eu.proplanet.simplebox4pfas.Engine.Engine;
import eu.proplanet.simplebox4pfas.Engine.InputEngine;
import eu.proplanet.simplebox4pfas.Engine.RegionalEngine;

public class output extends SelectorComposer<Window> {

	Window win;
	InputEngine input = null;
	RegionalEngine environment = null;
	Engine engine = null;
	Map<String, String> nanoData = null;

	//Names of regions
	//	private String regPart = "Regional - particulate";
	//	private String contPart = "Continental - particulate";
	//	private String globPart = "Global - particulate";
	private String regDis = "Regional";
	private String contDis = "Continental";
	private String globDis = "Global";
	//	private String nanoSpec = "Nanomaterial PEC summary";

	@Wire
	Image imgResA, imgResB;

	@Wire
	Combobox resultsCombo;

	@Wire
	Button download;

	@Wire
	Grid concGrid, zonesGrid, concGridT, concGrid1, concGrid2, concGrid3;

	@Wire
	Textbox airReg1, airCon1, airMod1, airArc1, airTro1;

	@Wire
	Label concLabT, concLab1, concLab2, concLab3;

	@Wire
	Textbox freshWatLReg1, freshWatLCon1, freshWatLMod1, freshWatLArc1, freshWatLTro1; 

	@Wire
	Textbox freshWatLSReg1, freshWatLSCon1, freshWatLSMod1, freshWatLSArc1, freshWatLSTro1; 

	@Wire
	Textbox freshWatReg1, freshWatCon1, freshWatMod1, freshWatArc1, freshWatTro1; 

	@Wire
	Textbox freshWatSedReg1, freshWatSedCon1, freshWatSedMod1, freshWatSedArc1, freshWatSedTro1; 

	@Wire
	Textbox surfSeaOcReg1, surfSeaOcCon1, surfSeaOcMod1, surfSeaOcArc1, surfSeaOcTro1; 

	@Wire
	Textbox deepSeaOcReg1, deepSeaOcCon1, deepSeaOcMod1, deepSeaOcArc1, deepSeaOcTro1; 

	@Wire
	Textbox marSedReg1, marSedCon1, marSedMod1, marSedArc1, marSedTro1; 

	@Wire
	Textbox natSoilReg1, natSoilCon1, natSoilMod1, natSoilArc1, natSoilTro1; 

	@Wire
	Textbox agriSoilReg1, agriSoilCon1, agriSoilMod1, agriSoilArc1, agriSoilTro1; 

	@Wire
	Textbox otherSoilReg1, otherSoilCom1, otherSoilMod1, otherSoilArc1, otherSoilTro1; 

	@Wire
	Textbox airReg2, airCon2, airMod2, airArc2, airTro2;

	@Wire
	Textbox freshWatLReg2, freshWatLCon2, freshWatLMod2, freshWatLArc2, freshWatLTro2; 

	@Wire
	Textbox freshWatLSReg2, freshWatLSCon2, freshWatLSMod2, freshWatLSArc2, freshWatLSTro2; 

	@Wire
	Textbox freshWatReg2, freshWatCon2, freshWatMod2, freshWatArc2, freshWatTro2; 

	@Wire
	Textbox freshWatSedReg2, freshWatSedCon2, freshWatSedMod2, freshWatSedArc2, freshWatSedTro2; 

	@Wire
	Textbox surfSeaOcReg2, surfSeaOcCon2, surfSeaOcMod2, surfSeaOcArc2, surfSeaOcTro2; 

	@Wire
	Textbox deepSeaOcReg2, deepSeaOcCon2, deepSeaOcMod2, deepSeaOcArc2, deepSeaOcTro2; 

	@Wire
	Textbox marSedReg2, marSedCon2, marSedMod2, marSedArc2, marSedTro2; 

	@Wire
	Textbox natSoilReg2, natSoilCon2, natSoilMod2, natSoilArc2, natSoilTro2; 

	@Wire
	Textbox agriSoilReg2, agriSoilCon2, agriSoilMod2, agriSoilArc2, agriSoilTro2; 

	@Wire
	Textbox otherSoilReg2, otherSoilCom2, otherSoilMod2, otherSoilArc2, otherSoilTro2; 

	@Wire
	Textbox airReg3, airCon3, airMod3, airArc3, airTro3;

	@Wire
	Textbox freshWatLReg3, freshWatLCon3, freshWatLMod3, freshWatLArc3, freshWatLTro3; 

	@Wire
	Textbox freshWatLSReg3, freshWatLSCon3, freshWatLSMod3, freshWatLSArc3, freshWatLSTro3; 

	@Wire
	Textbox freshWatReg3, freshWatCon3, freshWatMod3, freshWatArc3, freshWatTro3; 

	@Wire
	Textbox freshWatSedReg3, freshWatSedCon3, freshWatSedMod3, freshWatSedArc3, freshWatSedTro3; 

	@Wire
	Textbox surfSeaOcReg3, surfSeaOcCon3, surfSeaOcMod3, surfSeaOcArc3, surfSeaOcTro3; 

	@Wire
	Textbox deepSeaOcReg3, deepSeaOcCon3, deepSeaOcMod3, deepSeaOcArc3, deepSeaOcTro3; 

	@Wire
	Textbox marSedReg3, marSedCon3, marSedMod3, marSedArc3, marSedTro3; 

	@Wire
	Textbox natSoilReg3, natSoilCon3, natSoilMod3, natSoilArc3, natSoilTro3; 

	@Wire
	Textbox agriSoilReg3, agriSoilCon3, agriSoilMod3, agriSoilArc3, agriSoilTro3; 

	@Wire
	Textbox otherSoilReg3, otherSoilCom3, otherSoilMod3, otherSoilArc3, otherSoilTro3; 

	@Wire
	Textbox airRegT, airConT, airModT, airArcT, airTroT;

	@Wire
	Textbox freshWatLRegT, freshWatLConT, freshWatLModT, freshWatLArcT, freshWatLTroT; 

	@Wire
	Textbox freshWatLSRegT, freshWatLSConT, freshWatLSModT, freshWatLSArcT, freshWatLSTroT; 

	@Wire
	Textbox freshWatRegT, freshWatConT, freshWatModT, freshWatArcT, freshWatTroT; 

	@Wire
	Textbox freshWatSedRegT, freshWatSedConT, freshWatSedModT, freshWatSedArcT, freshWatSedTroT; 

	@Wire
	Textbox surfSeaOcRegT, surfSeaOcConT, surfSeaOcModT, surfSeaOcArcT, surfSeaOcTroT; 

	@Wire
	Textbox deepSeaOcRegT, deepSeaOcConT, deepSeaOcModT, deepSeaOcArcT, deepSeaOcTroT; 

	@Wire
	Textbox marSedRegT, marSedConT, marSedModT, marSedArcT, marSedTroT; 

	@Wire
	Textbox natSoilRegT, natSoilConT, natSoilModT, natSoilArcT, natSoilTroT; 

	@Wire
	Textbox agriSoilRegT, agriSoilConT, agriSoilModT, agriSoilArcT, agriSoilTroT; 

	@Wire
	Textbox otherSoilRegT, otherSoilComT, otherSoilModT, otherSoilArcT, otherSoilTroT; 

	@Wire
	Textbox modAirRes, arctAirRes, tropAirRes, modSurfSeaWRes, arctSurfSeaWRes, tropSurfSeaWRes, modDeepSeaWRes,
	arctDeepSeaWRes, tropDeepSeaWRes, modSeaWSedRes, arctSeaWSedRes, tropSeaWSedRes, modSoilRes, arctSoilRes, tropSoilRes;


	@Wire 
	Textbox airRes, freshWaterlakeRes, freshWaterRes, seaWaterRes, freshWaterSedRes, seaWaterSedRes, naturalSoilRes, agriSoilRes, otherSoilRes;

	@Wire
	Doublebox cutOffDiam;

	Map<String, Map<String, Map<String, Double> > > masses = new HashMap<String, Map<String, Map<String, Double> > >(); //kg
	Map<String, Map<String, Map<String, Double> > > concentrations = new HashMap<String, Map<String, Map<String, Double> > >(); //
	Map<String, Map<String, Map<String, Double> > > fugacities = new HashMap<String, Map<String, Map<String, Double> > >(); //

	Map<String, Map<String, Double> > inflow = new HashMap<String, Map<String,  Double > >(); //
	Map<String, Map<String, Double> > outflow = new HashMap<String, Map<String, Double > > (); //
	Map<String, Map<String, Double> > removal = new HashMap<String, Map<String, Double > >(); //
	Map<String, Map<String, Double> > formation = new HashMap<String, Map<String, Double > >(); //
	Map<String, Map<String, Double> > degradation = new HashMap<String, Map<String, Double > >(); //	
	Map<String, Map<String, Double> > emission = new HashMap<String, Map<String, Double > >(); //

	Map<String, Map<String, Map<String, Double> > > transport = new HashMap<String, Map<String, Map<String, Double> > >(); //

	Map<String, Double> totalD = new HashMap<String, Double>();
	Map<String, Double> totalS = new HashMap<String, Double>();
	Map<String, Double> totalA = new HashMap<String, Double>();
	Map<String, Double> totalP = new HashMap<String, Double>();

	double totalReg, totalCont, totalMod, totalArct, totalTrop;
	String scenarioName = null;
	String nanomaterialName = null;

	//This is to be called by the API 
	public void setAPIInfo( 
			InputEngine in,  
			RegionalEngine env, 
			Engine eng, 
			Map<String, String> nano,
			String scenario, 
			String nanomaterial) 
	{

		input = in;		 
		environment = env;		 
		engine = eng;		 
		nanoData = nano;		 
		scenarioName = scenario;
		nanomaterialName = nanomaterial;

		//build masses table for output
		buildMasses();
		buildConcentrations();
		buildFugacities();

		//build output for steady state
		buildInflow();
		buildOutflow();
		buildRemoval();
		//		buildFormation();
		buildDegradation();
		buildEmission();
		buildTransport();
		//		buildTotals();

		//build the tables for the NMs only.
		/*		buildTable1();
		buildTable2();
		buildTable3();
		buildTableTotal();*/
	}

	@Override
	public void doAfterCompose(Window comp) throws Exception 
	{
		super.doAfterCompose(comp);
		this.win = comp;	

		//Get the scenario as defined in the main window
		Session current = Sessions.getCurrent();	
		input = (InputEngine) current.getAttribute("Input");		 
		environment = (RegionalEngine) current.getAttribute("Environment");		 
		engine = (Engine) current.getAttribute("Engine");		 
		nanoData = (Map<String, String>) current.getAttribute("Nanomaterial");		 
		scenarioName = (String) current.getAttribute("Scenario name");
		nanomaterialName = (String) current.getAttribute("Nanomaterial name");

		//build info and tables for output
		/*		buildMasses();
		buildConcentrations();
		buildFugacities();
		buildEmission();*/

		ListModelList<String> availRegions = new ListModelList<String>();
		//		availRegions.add( regPart );
		//		availRegions.add( contPart );
		//		availRegions.add( globPart );
		availRegions.add( regDis );
		availRegions.add( contDis );
		availRegions.add( globDis );
		//		availRegions.add( nanoSpec );
		availRegions.addToSelection( availRegions.get(0) );

		//First show the results for regional since the combobox is not auto 
		//Compute the particulate matter to show first
		disBuilder();

		showImageRegional();
		imgResA.setVisible(true);
		imgResB.setVisible(false);			

		concGrid.setVisible(true);
		zonesGrid.setVisible(false);

		concGridT.setVisible(false);
		concGrid1.setVisible(false);
		concGrid2.setVisible(false);
		concGrid3.setVisible(false);

		concLabT.setVisible(false);
		concLab1.setVisible(false);
		concLab2.setVisible(false);
		concLab3.setVisible(false);

		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Reg").get("Air") ) ) );	
		freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Reg").get("Fresh water lake") ) ) );
		freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Reg").get("Fresh water") ) ) );
		seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water") ) ) );
		freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Reg").get("Fresh water sediment") ) ) );
		seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Reg").get("Marine sediment") ) ) );
		naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Reg").get("Natural soil") ) ) );
		agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Reg").get("Agricultural soil") ) ) );
		otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Reg").get("Other soil") ) ) );			

		resultsCombo.setModel( availRegions );
	}

	private void disBuilder() {
		buildMasses();
		buildConcentrations();

		buildFugacities();
		buildEmission();

		buildInflow();
		buildOutflow();
		buildRemoval();
		buildDegradation();
		buildTransport();
		buildTotals();
	}

	void showImageRegional() throws IOException 
	{
		ServletContext c = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();

		BufferedImage bimg = ImageIO.read( c.getResourceAsStream("/resources/ResultsA_v2.png") );

		Font font = new Font("Calibri", Font.BOLD, 30); Graphics2D graphics = bimg.createGraphics();
		graphics.setFont(font);

		graphics.setColor(Color.BLUE);
		graphics.drawString(
				String.format("%.2e", inflow.get("Regional Scale").get("air") ),
				200, 230  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("air") ),
				0, 340  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("fresh water lakes") ),
				0, 860  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("fresh water") ),
				0, 970  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Regional Scale").get("fresh water") ),
				1030, 920  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("coastal sea water") ),
				0, 1050  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Regional Scale").get("coastal sea water") ),
				280, 990  				
				);

		//green
		graphics.setColor(new Color(0, 128, 0) );
		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("air") ),
				200, 420  				
				);

		graphics.drawString(
				String.format("%.2e", 0.00 ), 1760, 1070 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("fresh water") ),760, 1100 	 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("coastal sea water") ), 280, 1180 	 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("other soil") ), 3910, 1660 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("agricultural soil") ),  3910, 1720	
				);


		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("natural soil") ), 3910, 1780 		
				);

		//red
		graphics.setColor(new Color(227, 30, 36) );
		if ( transport.get("water-air").get("Regional Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Regional Scale").get("coastal sea water") ),
					470, 570  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0),
					470, 570  				
					);

		if ( transport.get("water-air").get("Regional Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Regional Scale").get("fresh water") ),
					1050, 570  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 570  				
					);

		if ( transport.get("water-air").get("Regional Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Regional Scale").get("fresh water lakes") ),
					1860, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1860, 490  				
					);

		if ( transport.get("soil-air").get("Regional Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Regional Scale").get("natural soil") ),
					2380, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2380, 490  				
					);

		if ( transport.get("soil-air").get("Regional Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Regional Scale").get("other soil") ),
					3010, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3010, 490  				
					);

		if ( transport.get("soil-air").get("Regional Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Regional Scale").get("agricultural soil") ),
					3420, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3420, 490  				
					);

		if ( transport.get("air-water").get("Regional Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Regional Scale").get("coastal sea water") ),
					470, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					470, 820  				
					);

		if ( transport.get("air-water").get("Regional Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Regional Scale").get("fresh water") ),
					1050, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 820  				
					);

		if ( transport.get("air-water").get("Regional Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Regional Scale").get("fresh water lakes") ),
					1860, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1860, 820  				
					);

		if ( transport.get("air-soil").get("Regional Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Regional Scale").get("natural soil") ),
					2380, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2380, 820  				
					);

		if ( transport.get("air-soil").get("Regional Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Regional Scale").get("other soil") ),
					3010, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3010, 820  				
					);

		if ( transport.get("air-soil").get("Regional Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Regional Scale").get("agricultural soil") ),
					3420, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3420, 820  				
					);

		if ( transport.get("sed-water").get("Regional Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Regional Scale").get("fresh water sediment") ),
					1050, 1110  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 1110  				
					);

		if ( transport.get("water-sed").get("Regional Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Regional Scale").get("fresh water sediment") ),
					1050, 1340  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 1340  				
					);

		if ( transport.get("sed-water").get("Regional Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Regional Scale").get("coastal marine sediment") ),
					300, 1580  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					300, 1580 				
					);

		if ( transport.get("water-sed").get("Regional Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Regional Scale").get("coastal marine sediment") ),
					300, 1810  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					300, 1810  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Regional Scale").get("fresh water") ),
				1430, 780  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("air") ),
				860, 410
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("fresh water lakes") ),
				1390, 1020  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("fresh water") ),
				1850, 1020  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("natural soil") ),
				2370, 1350  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("other soil") ),
				2900, 1350  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("agricultural soil") ),
				3600, 1350   	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("fresh water sediment") ),
				1350, 1300  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("coastal sea water") ),
				610, 1510  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("coastal marine sediment") ),
				630, 1790  	  				
				);


		//black
		graphics.setColor( Color.BLACK );
		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Reg").get("Air")/totalReg*100 ) + " %",
				1190, 120	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water").get("Reg").get("Fresh water")/totalReg*100 ) + " %",
				1490, 1130	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water sediment").get("Reg").get("Fresh water sediment")/totalReg*100 ) + " %",
				1210, 1460	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water")/totalReg*100 ) + " %",
				730, 1620  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Natural soil").get("Reg").get("Natural soil")/totalReg*100 ) + " %",
				2390, 1170   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Reg").get("Other soil")/totalReg*100 ) + " %",
				3000, 1170	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Agricultural soil").get("Reg").get("Agricultural soil")/totalReg*100 ) + " %",
				3620, 1170	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Reg").get("Marine sediment")/totalReg*100 ) + " %",
				690, 1930  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water lake").get("Reg").get("Fresh water lake")/totalReg*100 ) + " %",
				1750, 910	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("coastal marine sediment" ) ),
				310, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("fresh water sediment" ) ),
				1050, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("natural soil" ) ),
				2200, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("other soil" ) ),
				2980, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("agricultural soil" ) ),
				3600, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("air" ) ),
				3650, 130	   	  	  				
				);

		graphics.dispose();

		imgResA.setContent( bimg );		
	}

	void showImageContinental() throws IOException 
	{
		//Note the image is the same. 
		ServletContext c = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();

		//		File img = new File( Thread.currentThread().getContextClassLoader().getResource("../../resources/ResultsA_v2.png").toString().replace("file:/", "") );		
		//		File img = new File("C:/Users/nikol/eclipse-workspace/nanosolveit/NanoSolveIT/src/main/webapp/resources/ResultsA_v2.png");
		BufferedImage bimg = ImageIO.read( c.getResourceAsStream("/resources/ResultsA_v2.png") );

		Font font = new Font("Calibri", Font.BOLD, 30); Graphics2D graphics = bimg.createGraphics();
		graphics.setFont(font);

		graphics.setColor(Color.BLUE);
		graphics.drawString(
				String.format("%.2e", inflow.get("Continental Scale").get("air") ),
				200, 230  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("air") ),
				0, 340  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("fresh water lakes") ),
				0, 860  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("fresh water") ),
				0, 970  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Continental Scale").get("fresh water") ),
				1030, 920  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("coastal sea water") ),
				0, 1050  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Continental Scale").get("coastal sea water") ),
				280, 990  				
				);


		//green
		graphics.setColor(new Color(0, 128, 0) );
		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("air") ),
				200, 420  				
				);

		graphics.drawString(
				String.format("%.2e", 0.00 ), 1760, 1070 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("fresh water") ),760, 1100 	 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("coastal sea water") ), 280, 1180 	 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("other soil") ), 3910, 1660 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("agricultural soil") ),  3910, 1720	
				);


		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("natural soil") ), 3910, 1780 		
				);

		//red
		graphics.setColor(new Color(227, 30, 36) );
		if ( transport.get("water-air").get("Continental Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Continental Scale").get("coastal sea water") ),
					470, 570  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0),
					470, 570  				
					);

		if ( transport.get("soil-water").get("Continental Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-water").get("Continental Scale").get("fresh water") ),
					1050, 570  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 570  				
					);

		if ( transport.get("water-air").get("Continental Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Continental Scale").get("fresh water lakes") ),
					1860, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1860, 490  				
					);

		if ( transport.get("soil-air").get("Continental Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Continental Scale").get("natural soil") ),
					2380, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2380, 490  				
					);

		if ( transport.get("soil-air").get("Continental Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Continental Scale").get("other soil") ),
					3010, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3010, 490  				
					);

		if ( transport.get("soil-air").get("Continental Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Continental Scale").get("agricultural soil") ),
					3420, 490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3420, 490  				
					);

		if ( transport.get("air-water").get("Continental Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Continental Scale").get("coastal sea water") ),
					470, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					470, 820  				
					);

		if ( transport.get("air-water").get("Continental Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Continental Scale").get("fresh water") ),
					1050, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 820  				
					);

		if ( transport.get("air-water").get("Continental Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Continental Scale").get("fresh water lakes") ),
					1860, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1860, 820  				
					);

		if ( transport.get("air-soil").get("Continental Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Continental Scale").get("natural soil") ),
					2380, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2380, 820  				
					);

		if ( transport.get("air-soil").get("Continental Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Continental Scale").get("other soil") ),
					3010, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3010, 820  				
					);

		if ( transport.get("air-soil").get("Continental Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Continental Scale").get("agricultural soil") ),
					3420, 820  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					3420, 820  				
					);

		if ( transport.get("sed-water").get("Continental Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Continental Scale").get("fresh water sediment") ),
					1050, 1110  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 1110  				
					);

		if ( transport.get("water-sed").get("Continental Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Continental Scale").get("fresh water sediment") ),
					1050, 1340  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1050, 1340  				
					);

		if ( transport.get("sed-water").get("Continental Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Continental Scale").get("coastal marine sediment") ),
					300, 1580  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					300, 1580 				
					);

		if ( transport.get("water-sed").get("Continental Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Continental Scale").get("coastal marine sediment") ),
					300, 1810  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					300, 1810  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Continental Scale").get("fresh water") ),
				1430, 780  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("air") ),
				860, 410
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("fresh water lakes") ),
				1390, 1020  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("fresh water") ),
				1850, 1020  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("natural soil") ),
				2370, 1350  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("other soil") ),
				2900, 1350  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("agricultural soil") ),
				3600, 1350   	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("fresh water sediment") ),
				1350, 1300  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("coastal sea water") ),
				610, 1510  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("coastal marine sediment") ),
				630, 1790  	  				
				);


		graphics.setColor( Color.BLACK );
		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Cont").get("Air")/totalCont*100 ) + " %",
				1190, 120	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water").get("Cont").get("Fresh water")/totalCont*100 ) + " %",
				1490, 1130	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water sediment").get("Cont").get("Fresh water sediment")/totalCont*100 ) + " %",
				1210, 1460	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water")/totalCont*100 ) + " %",
				730, 1620  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Natural soil").get("Cont").get("Natural soil")/totalCont*100 ) + " %",
				2390, 1170   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Cont").get("Other soil")/totalCont*100 ) + " %",
				3000, 1170	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Agricultural soil").get("Cont").get("Agricultural soil")/totalCont*100 ) + " %",
				3620, 1170	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Cont").get("Marine sediment")/totalCont*100 ) + " %",
				690, 1930  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water lake").get("Cont").get("Fresh water lake")/totalCont*100 ) + " %",
				1750, 910	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("coastal marine sediment" ) ),
				310, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("fresh water sediment" ) ),
				1050, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("natural soil" ) ),
				2200, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("other soil" ) ),
				2980, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("agricultural soil" ) ),
				3600, 2120	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("air" ) ),
				3650, 130	   	  	  				
				);

		graphics.dispose();

		imgResA.setContent( bimg );		
	}

	void showImageGlobal() throws IOException
	{
		ServletContext c = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();
		//		File img = new File( Thread.currentThread().getContextClassLoader().getResource("../../resources/ResultsB_v2.png").toString().replace("file:/", "") );		
		BufferedImage bimg = ImageIO.read( c.getResourceAsStream("/resources/ResultsB_v2.png") );

		Font font = new Font("Calibri", Font.BOLD, 20);
		Graphics2D graphics = bimg.createGraphics();
		graphics.setFont(font);

		// --- For MODERATE --------------------------------------------------------------------------------//
		graphics.setColor(Color.BLUE);
		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Moderate climate zone").get("air") ),
				118, 293  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Moderate climate zone").get("air") ),
				0, 390  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Moderate climate zone").get("upper ocean water") ),
				110, 560  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Moderate climate zone").get("upper ocean water") ),
				0, 620  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Moderate climate zone").get("deep sea") ),
				110, 790  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Moderate climate zone").get("deep sea") ),
				0, 860  				
				);

		//green
		graphics.setColor(new Color(0, 128, 0) );

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Moderate climate zone").get("air") ), 
				123, 190  				
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Moderate climate zone").get("upper ocean water") ), 
				115, 490  	
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Moderate climate zone").get("soil") ), 
				550, 955  	
				);

		//red
		graphics.setColor(new Color(227, 30, 36) );
		if ( transport.get("water-air").get("Global Scale - Moderate climate zone").get("upper ocean water") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Global Scale - Moderate climate zone").get("upper ocean water") ),
					190, 390  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190, 390  	  				
					);

		if ( transport.get("air-water").get("Global Scale - Moderate climate zone").get("upper ocean water") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Global Scale - Moderate climate zone").get("upper ocean water") ),
					//String.format("%.2e", environment.getEnvProps("MODERATE","k.w2M.aM")*engine.getMassMol(93)*input.getSubstancesData("Molweight") ),
					190, 530  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					//String.format("%.2e", environment.getEnvProps("MODERATE","k.w2M.aM")*engine.getMassMol(93)*input.getSubstancesData("Molweight") ),
					190, 530  	  				
					);

		if ( environment.getEnvProps("MODERATE","k.w3M.w2M")*engine.getMassMol(20)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("MODERATE","k.w3M.w2M")*engine.getMassMol(20)*input.getSubstancesData("Molweight") ),
					190, 592  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190, 592  	  				
					);

		if (environment.getEnvProps("MODERATE","k.w2M.w3M")*engine.getMassMol(19)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("MODERATE","k.w2M.w3M")*engine.getMassMol(19)*input.getSubstancesData("Molweight") ),
					190, 725  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190, 725  	  				
					);

		if (  environment.getEnvProps("MODERATE","k.sdMD.w3MD")*engine.getMassMol(21)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("MODERATE","k.sdMD.w3MD")*engine.getMassMol(21)*input.getSubstancesData("Molweight") ),
					380, 850  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					380, 900  	  				
					);

		if (  transport.get("water-sed").get("Global Scale - Moderate climate zone").get("ocean sediment") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Global Scale - Moderate climate zone").get("ocean sediment") ),
					380, 970  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					380, 970  	  				
					);

		if (  transport.get("soil-air").get("Global Scale - Moderate climate zone").get("soil") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Global Scale - Moderate climate zone").get("soil") ),
					670, 350  	  		 	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					670, 350  	  		  	  				
					);

		if (  transport.get("air-soil").get("Global Scale - Moderate climate zone").get("soil") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Global Scale - Moderate climate zone").get("soil") ),
					670, 480  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					670, 480  	  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Global Scale - Moderate climate zone").get("upper ocean water") ),
				440, 510  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Moderate climate zone").get("air") ),
				255, 320  
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Moderate climate zone").get("upper ocean water") ),
				395, 620   
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Moderate climate zone").get("deep sea") ),
				250, 820   
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Moderate climate zone").get("ocean sediment") ),
				100, 1050  
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Moderate climate zone").get("soil") ),
				575, 850  
				);

		//black
		graphics.setColor( Color.BLACK );
		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Mod").get("Air")/totalMod*100 ) + " %",
				195, 115 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water")/totalMod*100 ) + " %",
				425, 555 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water")/totalMod*100 ) + " %",
				430, 720 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Mod").get("Marine sediment")/totalMod*100 ) + " %",
				250, 970 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Mod").get("Other soil")/totalMod*100 ) + " %",
				620, 740  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Moderate climate zone").get("ocean sediment") ),
				370, 1150  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Moderate climate zone").get("soil") ),
				600, 1150  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Moderate climate zone").get("air") ),
				835, 60  	  				
				);


		// --- For ARCTIC --------------------------------------------------------------------------------//
		graphics.setColor(Color.BLUE);

		int x = 1000;

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Arctic climate zone").get("air") ),
				118 + x, 293  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Arctic climate zone").get("air") ),
				0 + x, 390  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Arctic climate zone").get("upper ocean water") ),
				110 + x, 560  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Arctic climate zone").get("upper ocean water") ),
				0 + x, 620  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Arctic climate zone").get("deep sea") ),
				110 + x, 790  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Arctic climate zone").get("deep sea") ),
				0 + x, 860  				
				);

		//green
		graphics.setColor(new Color(0, 128, 0) );

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Arctic climate zone").get("air") ), 
				123 + x, 190  				
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Arctic climate zone").get("upper ocean water") ), 
				115 + x, 490  	
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Arctic climate zone").get("soil") ), 
				550 + x, 955  	
				);

		//red			
		graphics.setColor(new Color(227, 30, 36) );
		if ( environment.getEnvProps("ARCTIC","k.w2AD.aAG")*engine.getMassMol(24)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.w2AD.aAG")*engine.getMassMol(24)*input.getSubstancesData("Molweight") ),
					190 + x, 390  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 390  	  				
					);

		if ( transport.get("air-water").get("Global Scale - Arctic climate zone").get("upper ocean water") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Global Scale - Arctic climate zone").get("upper ocean water") ),
					190 + x, 530  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 530  	  				
					);

		if ( environment.getEnvProps("ARCTIC","k.w3A.w2A")*engine.getMassMol(25)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.w3A.w2A")*engine.getMassMol(25)*input.getSubstancesData("Molweight") ),
					190 + x, 592  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 592  	  				
					);

		if (environment.getEnvProps("ARCTIC","k.w2A.w3A")*engine.getMassMol(24)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.w2A.w3A")*engine.getMassMol(24)*input.getSubstancesData("Molweight") ),
					190 + x, 725  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 725  	  				
					);

		if (  environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*engine.getMassMol(26)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*engine.getMassMol(26)*input.getSubstancesData("Molweight") ),
					380 + x, 850  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					380 + x, 900  	  				
					);

		if (  transport.get("water-sed").get("Global Scale - Arctic climate zone").get("ocean sediment") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Global Scale - Arctic climate zone").get("ocean sediment") ),
					380 + x, 970  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					380 + x, 970  	  				
					);

		if (  transport.get("soil-air").get("Global Scale - Arctic climate zone").get("soil") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Global Scale - Arctic climate zone").get("soil") ),
					670 + x, 350  	  		 	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					670 + x, 350  	  		  	  				
					);

		if (  transport.get("air-soil").get("Global Scale - Arctic climate zone").get("soil") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Global Scale - Arctic climate zone").get("soil") ),
					670 + x, 480  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					670 + x, 480  	  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Global Scale - Arctic climate zone").get("upper ocean water") ),
				440 + x, 510  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Arctic climate zone").get("air") ),
				255 + x, 320  
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Arctic climate zone").get("upper ocean water") ),
				395 + x, 620   
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Arctic climate zone").get("deep sea") ),
				250 + x, 820   
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Arctic climate zone").get("ocean sediment") ),
				100 + x, 1050  
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Arctic climate zone").get("soil") ),
				575 + x, 850  
				);

		//black
		graphics.setColor( Color.BLACK );
		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Arct").get("Air")/totalArct*100 ) + " %",
				195 + x, 115 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water")/totalArct*100 ) + " %",
				425 + x, 555 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water")/totalArct*100 ) + " %",
				430 + x, 720 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Arct").get("Marine sediment")/totalArct*100 ) + " %",
				250 + x, 970 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Arct").get("Other soil")/totalArct*100 ) + " %",
				620 + x, 740  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Arctic climate zone").get("ocean sediment") ),
				370 + x, 1150  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Arctic climate zone").get("soil") ),
				600 + x, 1150  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Arctic climate zone").get("air") ),
				835 + x, 60  	  				
				);

		// --- For TROPICAL --------------------------------------------------------------------------------//
		graphics.setColor(Color.BLUE);

		x = x + 995;

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Tropical climate zone").get("air") ),
				118 + x, 293  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Tropical climate zone").get("air") ),
				0 + x, 390  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Tropical climate zone").get("upper ocean water") ),
				110 + x, 560  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Tropical climate zone").get("upper ocean water") ),
				0 + x, 620  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Global Scale - Tropical climate zone").get("deep sea") ),
				110 + x, 790  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Global Scale - Tropical climate zone").get("deep sea") ),
				0 + x, 860  				
				);

		//green
		graphics.setColor(new Color(0, 128, 0) );

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Tropical climate zone").get("air") ), 
				123 + x, 190  				
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Tropical climate zone").get("upper ocean water") ), 
				115 + x, 490  	
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Global Scale - Tropical climate zone").get("soil") ), 
				550 + x, 955  	
				);

		graphics.setColor(new Color(227, 30, 36) );

		//red
		if ( environment.getEnvProps("TROPICAL","k.w2TD.aTG")*engine.getMassMol(29)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(					
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.w2TD.aTG")*engine.getMassMol(29)*input.getSubstancesData("Molweight") ),					
					190 + x, 390  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 390  	  				
					);

		if ( transport.get("air-water").get("Global Scale - Tropical climate zone").get("upper ocean water") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Global Scale - Tropical climate zone").get("upper ocean water") ),
					190 + x, 530  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 530  	  				
					);

		if ( environment.getEnvProps("TROPICAL","k.w3T.w2T")*engine.getMassMol(30)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.w3T.w2T")*engine.getMassMol(30)*input.getSubstancesData("Molweight") ),
					190 + x, 592  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 592  	  				
					);

		if (environment.getEnvProps("TROPICAL","k.w2T.w3T")*engine.getMassMol(29)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.w2T.w3T")*engine.getMassMol(29)*input.getSubstancesData("Molweight") ),
					190 + x, 725  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 725  	  				
					);

		if (  environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*engine.getMassMol(31)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*engine.getMassMol(31)*input.getSubstancesData("Molweight") ),
					380 + x, 850  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					380 + x, 900  	  				
					);

		if (  transport.get("water-sed").get("Global Scale - Tropical climate zone").get("ocean sediment") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Global Scale - Tropical climate zone").get("ocean sediment") ),
					380 + x, 970  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					380 + x, 970  	  				
					);

		if (  transport.get("soil-air").get("Global Scale - Tropical climate zone").get("soil") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Global Scale - Tropical climate zone").get("soil") ),
					670 + x, 350  	  		 	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					670 + x, 350  	  		  	  				
					);

		if (  transport.get("air-soil").get("Global Scale - Tropical climate zone").get("soil") > 0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Global Scale - Tropical climate zone").get("soil") ),
					670 + x, 480  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					670 + x, 480  	  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Global Scale - Tropical climate zone").get("upper ocean water") ),
				440 + x, 510  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Tropical climate zone").get("air") ),
				255 + x, 320  
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Tropical climate zone").get("upper ocean water") ),
				395 + x, 620   
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Tropical climate zone").get("deep sea") ),
				250 + x, 820   
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Tropical climate zone").get("ocean sediment") ),
				100 + x, 1050  
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Global Scale - Tropical climate zone").get("soil") ),
				575 + x, 850  
				);

		//black
		graphics.setColor( Color.BLACK );
		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Trop").get("Air")/totalTrop*100 ) + " %",
				195 + x, 115 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water")/totalTrop*100 ) + " %",
				425 + x, 555 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water")/totalTrop*100 ) + " %",
				430 + x, 720 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Trop").get("Marine sediment")/totalTrop*100 ) + " %",
				250 + x, 970 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Trop").get("Other soil")/totalTrop*100 ) + " %",
				620 + x, 740  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Tropical climate zone").get("ocean sediment") ),
				370 + x, 1150  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Tropical climate zone").get("soil") ),
				600 + x, 1150  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Global Scale - Tropical climate zone").get("air") ),
				835 + x, 60  	  				
				);

		graphics.dispose();

		//Change to B
		imgResB.setContent( bimg );		
	}

	void buildMasses() 
	{
		masses.put("Air", new HashMap< String, Map< String, Double > >() );		
		masses.get("Air").put("Reg", new HashMap<String, Double>() );
		masses.get("Air").put("Cont", new HashMap<String, Double>() );
		masses.get("Air").put("Mod", new HashMap<String, Double>() );
		masses.get("Air").put("Arct", new HashMap<String, Double>() );
		masses.get("Air").put("Trop", new HashMap<String, Double>() );

		masses.get("Air").get("Reg").put("Air", engine.getMassMol(0)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Reg").put("* GAS PHASE", environment.getEnvProps("REGIONAL", "FRgas.aR")*
				masses.get("Air").get("Reg").get("Air") );
		masses.get("Air").get("Reg").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("REGIONAL", "FRgas.aR") )*
				masses.get("Air").get("Reg").get("Air"));

		masses.get("Air").get("Cont").put("Air", engine.getMassMol(9)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Cont").put("* GAS PHASE", environment.getEnvProps("CONTINENTAL", "FRgas.aC")*
				masses.get("Air").get("Cont").get("Air") );
		masses.get("Air").get("Cont").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("CONTINENTAL", "FRgas.aC") )*
				masses.get("Air").get("Cont").get("Air"));

		masses.get("Air").get("Mod").put("Air", engine.getMassMol(18)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Mod").put("* GAS PHASE", environment.getEnvProps("CONTINENTAL", "FRgas.aC")*
				masses.get("Air").get("Mod").get("Air") );
		masses.get("Air").get("Mod").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("MODERATE", "FRgas.aM") )*
				masses.get("Air").get("Mod").get("Air"));

		masses.get("Air").get("Arct").put("Air", engine.getMassMol(23)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Arct").put("* GAS PHASE", environment.getEnvProps("ARCTIC", "FRgas.aA")*
				masses.get("Air").get("Arct").get("Air") );
		masses.get("Air").get("Arct").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("ARCTIC", "FRgas.aA") )*
				masses.get("Air").get("Arct").get("Air"));

		masses.get("Air").get("Trop").put("Air", engine.getMassMol(28)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Trop").put("* GAS PHASE", environment.getEnvProps("TROPICAL", "FRgas.aT")*
				masses.get("Air").get("Trop").get("Air") );
		masses.get("Air").get("Trop").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("TROPICAL", "FRgas.aT") )*
				masses.get("Air").get("Trop").get("Air"));

		masses.put("Fresh water lake", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water lake").get("Reg").put("Fresh water lake", engine.getMassMol(1)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Reg").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w0R")*
				masses.get("Fresh water lake").get("Reg").get("Fresh water lake") );
		masses.get("Fresh water lake").get("Reg").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("REGIONAL", "FRwD.w0R") )*
				masses.get("Fresh water lake").get("Reg").get("Fresh water lake"));

		masses.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water lake").get("Cont").put("Fresh water lake", engine.getMassMol(10)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Cont").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w0R")*
				masses.get("Fresh water lake").get("Cont").get("Fresh water lake") );
		masses.get("Fresh water lake").get("Cont").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("REGIONAL", "FRwD.w0R") )*
				masses.get("Fresh water lake").get("Cont").get("Fresh water lake"));

		masses.put("Fresh water", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water").get("Reg").put("Fresh water", engine.getMassMol(2)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Reg").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w1R")*
				masses.get("Fresh water").get("Reg").get("Fresh water") );
		masses.get("Fresh water").get("Reg").put("* SUSPENDED SOLIDS", (1. -  environment.getEnvProps("REGIONAL", "FRwD.w1R") )*
				masses.get("Fresh water").get("Reg").get("Fresh water"));

		masses.get("Fresh water").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water").get("Cont").put("Fresh water", engine.getMassMol(11)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Cont").put("* DISSOLVED", environment.getEnvProps("CONTINENTAL", "FRw.w1C")*
				masses.get("Fresh water").get("Cont").get("Fresh water") );
		masses.get("Fresh water").get("Cont").put("* SUSPENDED SOLIDS", (1. - environment.getEnvProps("CONTINENTAL", "FRw.w1C"))*
				masses.get("Fresh water").get("Cont").get("Fresh water"));

		masses.put("Fresh water sediment", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water sediment").get("Reg").put("Fresh water sediment", engine.getMassMol(4)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Reg").put("* PORE WATER", environment.getEnvProps("REGIONAL", "FRACw.sdR")*
				masses.get("Fresh water sediment").get("Reg").get("Fresh water sediment") );
		masses.get("Fresh water sediment").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRACs.sdR")*
				masses.get("Fresh water sediment").get("Reg").get("Fresh water sediment"));

		masses.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water sediment").get("Cont").put("Fresh water sediment", engine.getMassMol(13)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Cont").put("* PORE WATER", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Fresh water sediment").get("Cont").get("Fresh water sediment") );
		masses.get("Fresh water sediment").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
				masses.get("Fresh water sediment").get("Cont").get("Fresh water sediment"));

		masses.put("Surface sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		masses.get("Surface sea/ocean water").put("Reg", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Cont", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Mod", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Arct", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Trop", new HashMap<String, Double>() );

		masses.get("Surface sea/ocean water").get("Reg").put("Surface sea/ocean water", engine.getMassMol(3)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Reg").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w2R")*
				masses.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water") );
		masses.get("Surface sea/ocean water").get("Reg").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("REGIONAL", "FRwD.w2R") )*
				masses.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water"));

		masses.get("Surface sea/ocean water").get("Cont").put("Surface sea/ocean water", engine.getMassMol(12)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Cont").put("* DISSOLVED", environment.getEnvProps("CONTINENTAL", "FRw.w2C")*
				masses.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water") );
		masses.get("Surface sea/ocean water").get("Cont").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("CONTINENTAL", "FRw.w2C") )*
				masses.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water"));

		masses.get("Surface sea/ocean water").get("Mod").put("Surface sea/ocean water", engine.getMassMol(19)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Mod").put("* DISSOLVED", environment.getEnvProps("MODERATE", "FRw.w2M")*
				masses.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water") );
		masses.get("Surface sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("MODERATE", "FRw.w2M") )*
				masses.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water"));

		masses.get("Surface sea/ocean water").get("Arct").put("Surface sea/ocean water", engine.getMassMol(24)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Arct").put("* DISSOLVED", environment.getEnvProps("ARCTIC", "FRw.w2A")*
				masses.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water") );
		masses.get("Surface sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("ARCTIC", "FRw.w2A") )*
				masses.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water"));

		masses.get("Surface sea/ocean water").get("Trop").put("Surface sea/ocean water", engine.getMassMol(29)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Trop").put("* DISSOLVED", environment.getEnvProps("TROPICAL", "FRw.w2T")*
				masses.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water") );
		masses.get("Surface sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("TROPICAL", "FRw.w2T") )*
				masses.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water"));

		masses.put("Deep sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		masses.get("Deep sea/ocean water").put("Mod", new HashMap<String, Double>() );
		masses.get("Deep sea/ocean water").put("Arct", new HashMap<String, Double>() );
		masses.get("Deep sea/ocean water").put("Trop", new HashMap<String, Double>() );

		masses.get("Deep sea/ocean water").get("Mod").put("Deep sea/ocean water", engine.getMassMol(20)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Mod").put("* DISSOLVED", environment.getEnvProps("MODERATE", "FRw.w3M")*
				masses.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water") );
		masses.get("Deep sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("MODERATE", "FRw.w3M") )*
				masses.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water"));

		masses.get("Deep sea/ocean water").get("Arct").put("Deep sea/ocean water", engine.getMassMol(25)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Arct").put("* DISSOLVED", environment.getEnvProps("ARCTIC", "FRw.w3A")*
				masses.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water") );
		masses.get("Deep sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("ARCTIC", "FRw.w3A") )*
				masses.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water"));

		masses.get("Deep sea/ocean water").get("Trop").put("Deep sea/ocean water", engine.getMassMol(30)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Trop").put("* DISSOLVED", environment.getEnvProps("TROPICAL", "FRw.w3T")*
				masses.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water") );
		masses.get("Deep sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("TROPICAL", "FRw.w3T") )*
				masses.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water"));

		masses.put("Marine sediment", new HashMap< String, Map< String, Double > >() );		
		masses.get("Marine sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Cont", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Mod", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Arct", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Trop", new HashMap<String, Double>() );

		masses.get("Marine sediment").get("Reg").put("Marine sediment", engine.getMassMol(5)*input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Reg").put("* PORE WATER", environment.getEnvProps("REGIONAL", "FRACw.sdR")*
				masses.get("Marine sediment").get("Reg").get("Marine sediment") );
		masses.get("Marine sediment").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRACs.sdR")*
				masses.get("Marine sediment").get("Reg").get("Marine sediment"));

		masses.get("Marine sediment").get("Cont").put("Marine sediment", engine.getMassMol(14)*input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Cont").put("* PORE WATER", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Marine sediment").get("Cont").get("Marine sediment") );
		masses.get("Marine sediment").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Marine sediment").get("Cont").get("Marine sediment"));

		masses.get("Marine sediment").get("Mod").put("Marine sediment", engine.getMassMol(21)*input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Mod").put("* PORE WATER", environment.getEnvProps("MODERATE", "FRACw.sdM")*
				masses.get("Marine sediment").get("Mod").get("Marine sediment") );
		masses.get("Marine sediment").get("Mod").put("* SOLID PHASE", (1.-environment.getEnvProps("MODERATE", "FRACw.sdM") )*
				masses.get("Marine sediment").get("Mod").get("Marine sediment"));

		masses.get("Marine sediment").get("Arct").put("Marine sediment", engine.getMassMol(26)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Arct").put("* PORE WATER", environment.getEnvProps("ARCTIC", "FRACw.sdA")*
				masses.get("Marine sediment").get("Arct").get("Marine sediment") );
		masses.get("Marine sediment").get("Arct").put("* SOLID PHASE", (1.-environment.getEnvProps("ARCTIC", "FRACw.sdA") )*
				masses.get("Marine sediment").get("Arct").get("Marine sediment"));

		masses.get("Marine sediment").get("Trop").put("Marine sediment", engine.getMassMol(31)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Trop").put("* PORE WATER", environment.getEnvProps("TROPICAL", "FRACw.sdT")*
				masses.get("Marine sediment").get("Trop").get("Marine sediment") );
		masses.get("Marine sediment").get("Trop").put("* SOLID PHASE", (1.-environment.getEnvProps("TROPICAL", "FRACw.sdT") )*
				masses.get("Marine sediment").get("Trop").get("Marine sediment"));

		masses.put("Natural soil", new HashMap< String, Map< String, Double > >() );		
		masses.get("Natural soil").put("Reg", new HashMap<String, Double>() );
		masses.get("Natural soil").put("Cont", new HashMap<String, Double>() );

		masses.get("Natural soil").get("Reg").put("Natural soil", engine.getMassMol(6)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Reg").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("REGIONAL", "FRwD.s1R")*
				masses.get("Natural soil").get("Reg").get("Natural soil") );
		masses.get("Natural soil").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRs.s1R")*
				masses.get("Natural soil").get("Reg").get("Natural soil"));

		masses.get("Natural soil").get("Cont").put("Natural soil", engine.getMassMol(15)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Cont").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("CONTINENTAL", "FRw.s1C")*
				masses.get("Natural soil").get("Cont").get("Natural soil") );
		masses.get("Natural soil").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				masses.get("Natural soil").get("Cont").get("Natural soil"));

		masses.put("Agricultural soil", new HashMap< String, Map< String, Double > >() );		
		masses.get("Agricultural soil").put("Reg", new HashMap<String, Double>() );
		masses.get("Agricultural soil").put("Cont", new HashMap<String, Double>() );

		masses.get("Agricultural soil").get("Reg").put("Agricultural soil", engine.getMassMol(7)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Reg").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("REGIONAL", "FRwD.s1R")*
				masses.get("Agricultural soil").get("Reg").get("Agricultural soil") );
		masses.get("Agricultural soil").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRs.s1R")*
				masses.get("Agricultural soil").get("Reg").get("Agricultural soil"));

		masses.get("Agricultural soil").get("Cont").put("Agricultural soil", engine.getMassMol(16)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Cont").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("CONTINENTAL", "FRw.s1C")*
				masses.get("Agricultural soil").get("Cont").get("Agricultural soil") );
		masses.get("Agricultural soil").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				masses.get("Agricultural soil").get("Cont").get("Agricultural soil"));

		masses.put("Other soil", new HashMap< String, Map< String, Double > >() );		
		masses.get("Other soil").put("Reg", new HashMap<String, Double>() );
		masses.get("Other soil").put("Cont", new HashMap<String, Double>() );
		masses.get("Other soil").put("Mod", new HashMap<String, Double>() );
		masses.get("Other soil").put("Arct", new HashMap<String, Double>() );
		masses.get("Other soil").put("Trop", new HashMap<String, Double>() );

		masses.get("Other soil").get("Reg").put("Other soil", engine.getMassMol(8)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Reg").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("REGIONAL", "FRwD.s3R")*
				masses.get("Other soil").get("Reg").get("Other soil") );
		masses.get("Other soil").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRs.s3R")*
				masses.get("Other soil").get("Reg").get("Other soil"));

		masses.get("Other soil").get("Cont").put("Other soil", engine.getMassMol(17)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Cont").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("CONTINENTAL", "FRw.s3C")*
				masses.get("Other soil").get("Cont").get("Other soil") );
		masses.get("Other soil").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRs.s3C")*
				masses.get("Other soil").get("Cont").get("Other soil"));

		masses.get("Other soil").get("Mod").put("Other soil", engine.getMassMol(22)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Mod").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("MODERATE", "FRw.sM")*
				masses.get("Other soil").get("Mod").get("Other soil") );
		masses.get("Other soil").get("Mod").put("* SOLID PHASE", (1.-environment.getEnvProps("MODERATE", "FRw.sM") )*
				masses.get("Other soil").get("Mod").get("Other soil"));

		masses.get("Other soil").get("Arct").put("Other soil", engine.getMassMol(27)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Arct").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("ARCTIC", "FRw.sA")*
				masses.get("Other soil").get("Arct").get("Other soil") );
		masses.get("Other soil").get("Arct").put("* SOLID PHASE", (1.-environment.getEnvProps("ARCTIC", "FRw.sA") )*
				masses.get("Other soil").get("Arct").get("Other soil"));

		masses.get("Other soil").get("Trop").put("Other soil", engine.getMassMol(32)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Trop").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("TROPICAL", "FRw.sT")*
				masses.get("Other soil").get("Trop").get("Other soil") );
		masses.get("Other soil").get("Trop").put("* SOLID PHASE", (1.-environment.getEnvProps("TROPICAL", "FRw.sT") )*
				masses.get("Other soil").get("Trop").get("Other soil"));
	}

	void buildConcentrations()
	{
		concentrations.put("Air", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Air").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Air").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Air").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Air").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Air").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Air").get("Reg").put("Air",
				engine.getConcentration(0)*(input.getSubstancesData("Molweight")*1000.)  );
		concentrations.get("Air").get("Reg").put("* GAS PHASE",
				environment.getEnvProps("REGIONAL","FRgas.aR")* concentrations.get("Air").get("Reg").get("Air")	);
		concentrations.get("Air").get("Reg").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("REGIONAL","FRgas.aR"))* concentrations.get("Air").get("Reg").get("Air") );

		concentrations.get("Air").get("Cont").put("Air", engine.getConcentration(9)*(input.getSubstancesData("Molweight")*1000.)  );
		concentrations.get("Air").get("Cont").put("* GAS PHASE", 
				environment.getEnvProps("CONTINENTAL","FRgas.aC")* concentrations.get("Air").get("Cont").get("Air"));
		concentrations.get("Air").get("Cont").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("CONTINENTAL","FRgas.aC"))* concentrations.get("Air").get("Cont").get("Air") );

		concentrations.get("Air").get("Mod").put("Air",engine.getConcentration(18)*(input.getSubstancesData("Molweight")*1000.)  );
		concentrations.get("Air").get("Mod").put("* GAS PHASE",
				environment.getEnvProps("MODERATE","FRgas.aM")* concentrations.get("Air").get("Mod").get("Air") );
		concentrations.get("Air").get("Mod").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("MODERATE","FRgas.aM"))* concentrations.get("Air").get("Mod").get("Air")
				);

		concentrations.get("Air").get("Arct").put("Air", engine.getConcentration(23)*(input.getSubstancesData("Molweight")*1000.)  );	
		concentrations.get("Air").get("Arct").put("* GAS PHASE",
				environment.getEnvProps("ARCTIC","FRgas.aA")* concentrations.get("Air").get("Arct").get("Air")
				);
		concentrations.get("Air").get("Arct").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("ARCTIC","FRgas.aA"))* concentrations.get("Air").get("Arct").get("Air")
				);

		concentrations.get("Air").get("Trop").put("Air",engine.getConcentration(28)*(input.getSubstancesData("Molweight")*1000.)  );			
		concentrations.get("Air").get("Trop").put("* GAS PHASE",
				environment.getEnvProps("TROPICAL","FRgas.aT")*concentrations.get("Air").get("Trop").get("Air")
				);
		concentrations.get("Air").get("Trop").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("TROPICAL","FRgas.aT"))* concentrations.get("Air").get("Trop").get("Air")
				);

		concentrations.put("Fresh water lake", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water lake").get("Reg").put("Fresh water lake", engine.getConcentration(1)*(input.getSubstancesData("Molweight"))
				);
		concentrations.get("Fresh water lake").get("Reg").put("* DISSOLVED",
				environment.getEnvProps("REGIONAL","FRwD.w0R")* concentrations.get("Fresh water lake").get("Reg").get("Fresh water lake")
				);
		concentrations.get("Fresh water lake").get("Reg").put("* SUSPENDED SOLIDS",
				environment.getEnvProps("REGIONAL","Kp.susp1R")* concentrations.get("Fresh water lake").get("Reg").get("* DISSOLVED")
				);

		concentrations.get("Fresh water lake").get("Cont").put("Fresh water lake",
				engine.getConcentration(10)*(input.getSubstancesData("Molweight"))
				);
		concentrations.get("Fresh water lake").get("Cont").put("* DISSOLVED",
				environment.getEnvProps("CONTINENTAL","FRw.w0C")* concentrations.get("Fresh water lake").get("Cont").get("Fresh water lake")
				);
		concentrations.get("Fresh water lake").get("Cont").put("* SUSPENDED SOLIDS",
				environment.getEnvProps("CONTINENTAL","Kp.susp1C")* concentrations.get("Fresh water lake").get("Cont").get("* DISSOLVED")
				);

		concentrations.put("Fresh water", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water").get("Reg").put("Fresh water",
				engine.getConcentration(2)*(input.getSubstancesData("Molweight"))
				);
		concentrations.get("Fresh water").get("Reg").put("* DISSOLVED",
				concentrations.get("Fresh water").get("Reg").get("Fresh water")*
				environment.getEnvProps("REGIONAL", "FRwD.w1R")
				);
		concentrations.get("Fresh water").get("Reg").put("* SUSPENDED SOLIDS",
				concentrations.get("Fresh water").get("Reg").get("* DISSOLVED")*
				environment.getEnvProps("REGIONAL", "Kp.susp1R")
				);

		concentrations.get("Fresh water").get("Cont").put("Fresh water",
				engine.getConcentration(11)*input.getSubstancesData("Molweight") 
				);
		concentrations.get("Fresh water").get("Cont").put("* DISSOLVED",
				concentrations.get("Fresh water").get("Cont").get("Fresh water")*
				environment.getEnvProps("CONTINENTAL", "FRw.w1C")
				);
		concentrations.get("Fresh water").get("Cont").put("* SUSPENDED SOLIDS",
				concentrations.get("Fresh water").get("Cont").get("* DISSOLVED")*
				environment.getEnvProps("CONTINENTAL", "Kp.susp1C")
				);

		concentrations.put("Fresh water sediment", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water sediment").get("Reg").put("Fresh water sediment",
				(engine.getConcentration(4)*(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Fresh water sediment").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(4)*environment.getEnvProps("REGIONAL", "FRACs.sdR")/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")/
						(environment.getEnvProps("REGIONAL", "Kp.sd1R")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("REGIONAL", "FRACs.sdR"))*(input.getSubstancesData("Molweight")*1000*1000)/
				(environment.getEnvProps("REGIONAL", "FRACs.sdR")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water sediment").get("Reg").put("* PORE WATER",
				concentrations.get("Fresh water sediment").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.sd1R")
				);

		concentrations.get("Fresh water sediment").get("Cont").put("Fresh water sediment",
				engine.getConcentration(13)*
				(input.getSubstancesData("Molweight")*1000.) /
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Fresh water sediment").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(13)*environment.getEnvProps("CONTINENTAL", "FRACs.sdC")/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")/
						(environment.getEnvProps("CONTINENTAL", "Kp.sd1C")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC"))*(input.getSubstancesData("Molweight")*1000*1000)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water sediment").get("Cont").put("* PORE WATER",
				concentrations.get("Fresh water sediment").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.sd1C")
				);

		concentrations.put("Surface sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Surface sea/ocean water").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Surface sea/ocean water").get("Reg").put("Surface sea/ocean water",
				engine.getConcentration(3)*(input.getSubstancesData("Molweight"))
				);
		concentrations.get("Surface sea/ocean water").get("Reg").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water")*
				environment.getEnvProps("REGIONAL", "FRwD.w2R")
				);
		concentrations.get("Surface sea/ocean water").get("Reg").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Reg").get("* DISSOLVED")*
				environment.getEnvProps("REGIONAL", "Kp.susp2R")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("Surface sea/ocean water",
				engine.getConcentration(12)*(input.getSubstancesData("Molweight"))
				);
		concentrations.get("Surface sea/ocean water").get("Cont").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water")*
				environment.getEnvProps("CONTINENTAL", "FRw.w2C")
				);
		concentrations.get("Surface sea/ocean water").get("Cont").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Cont").get("* DISSOLVED")*
				environment.getEnvProps("CONTINENTAL", "Kp.susp2C")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("Surface sea/ocean water",
				engine.getConcentration(19)*(input.getSubstancesData("Molweight")*1000.)
				);
		concentrations.get("Surface sea/ocean water").get("Mod").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water")*
				environment.getEnvProps("MODERATE", "FRw.w2M")
				);
		concentrations.get("Surface sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Mod").get("* DISSOLVED")*
				environment.getEnvProps("MODERATE", "Kp.susp2M")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("Surface sea/ocean water",
				engine.getConcentration(24)*(input.getSubstancesData("Molweight")*1000.)
				);
		concentrations.get("Surface sea/ocean water").get("Arct").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water")*
				environment.getEnvProps("ARCTIC", "FRw.w2A")
				);
		concentrations.get("Surface sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Arct").get("* DISSOLVED")*
				environment.getEnvProps("ARCTIC", "Kp.susp2A")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("Surface sea/ocean water",
				engine.getConcentration(29)*(input.getSubstancesData("Molweight")*1000.)
				);
		concentrations.get("Surface sea/ocean water").get("Trop").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water")*
				environment.getEnvProps("TROPICAL", "FRw.w2T")
				);
		concentrations.get("Surface sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Trop").get("* DISSOLVED")*
				environment.getEnvProps("TROPICAL", "Kp.susp2T")
				);

		concentrations.put("Deep sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Deep sea/ocean water").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Deep sea/ocean water").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Deep sea/ocean water").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Deep sea/ocean water").get("Mod").put("Deep sea/ocean water",
				engine.getConcentration(20)*(input.getSubstancesData("Molweight")*1000.)
				);
		concentrations.get("Deep sea/ocean water").get("Mod").put("* DISSOLVED",
				concentrations.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water")*
				environment.getEnvProps("MODERATE", "FRw.w3M")
				);
		concentrations.get("Deep sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				concentrations.get("Deep sea/ocean water").get("Mod").get("* DISSOLVED")*
				environment.getEnvProps("MODERATE", "Kp.susp3M")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("Deep sea/ocean water",
				engine.getConcentration(25)*(input.getSubstancesData("Molweight")*1000.)
				);
		concentrations.get("Deep sea/ocean water").get("Arct").put("* DISSOLVED",
				concentrations.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water")*
				environment.getEnvProps("ARCTIC", "FRw.w3A")
				);
		concentrations.get("Deep sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				concentrations.get("Deep sea/ocean water").get("Arct").get("* DISSOLVED")*
				environment.getEnvProps("ARCTIC", "Kp.susp3A")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("Deep sea/ocean water",
				engine.getConcentration(30)*(input.getSubstancesData("Molweight")*1000.)
				);
		concentrations.get("Deep sea/ocean water").get("Trop").put("* DISSOLVED",
				concentrations.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water")*
				environment.getEnvProps("TROPICAL", "FRw.w3T")
				);
		concentrations.get("Deep sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				concentrations.get("Deep sea/ocean water").get("Trop").get("* DISSOLVED")*
				environment.getEnvProps("TROPICAL", "Kp.susp3T")
				);

		concentrations.put("Marine sediment", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Marine sediment").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Marine sediment").get("Reg").put("Marine sediment",
				engine.getConcentration(5)*input.getSubstancesData("Molweight")*1000./
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR") *
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(5)*environment.getEnvProps("REGIONAL", "FRACs.sdR")/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")/
						(environment.getEnvProps("REGIONAL", "Kp.sd2R")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("REGIONAL", "FRACs.sdR"))*(input.getSubstancesData("Molweight")*1000)/
				(environment.getEnvProps("REGIONAL", "FRACs.sdR")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Reg").put("* PORE WATER",
				concentrations.get("Marine sediment").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.sd2R")
				);

		concentrations.get("Marine sediment").get("Cont").put("Marine sediment",
				( ( engine.getConcentration(14) )*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(14)*environment.getEnvProps("CONTINENTAL", "FRACs.sdC")/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")/
						(environment.getEnvProps("CONTINENTAL", "Kp.sd2C")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC"))*(input.getSubstancesData("Molweight")*1000)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Cont").put("* PORE WATER",
				concentrations.get("Marine sediment").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.sd2C")
				);

		concentrations.get("Marine sediment").get("Mod").put("Marine sediment",
				( ( engine.getConcentration(21) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sdM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sdM")*1000 + 
						environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Mod").put("* SOLID PHASE",
				engine.getConcentration(21)*environment.getEnvProps("MODERATE", "FRACs.sdM")/
				(environment.getEnvProps("MODERATE", "FRACw.sdM")/
						(environment.getEnvProps("MODERATE", "Kp.sdM")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("MODERATE", "FRACs.sdM"))*(input.getSubstancesData("Molweight")*1000)/
				(environment.getEnvProps("MODERATE", "FRACs.sdM")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Mod").put("* PORE WATER",
				concentrations.get("Marine sediment").get("Mod").get("* SOLID PHASE")/
				environment.getEnvProps("MODERATE", "Kp.sdM")
				);

		concentrations.get("Marine sediment").get("Arct").put("Marine sediment",
				( ( engine.getConcentration(26) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sdA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")*1000 + 
						environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Arct").put("* SOLID PHASE",
				engine.getConcentration(26)*environment.getEnvProps("ARCTIC", "FRACs.sdA")/
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")/
						(environment.getEnvProps("ARCTIC", "Kp.sdA")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("ARCTIC", "FRACs.sdA"))*(input.getSubstancesData("Molweight")*1000)/
				(environment.getEnvProps("ARCTIC", "FRACs.sdA")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Arct").put("* PORE WATER",
				concentrations.get("Marine sediment").get("Arct").get("* SOLID PHASE")/
				environment.getEnvProps("ARCTIC", "Kp.sdA")
				);

		concentrations.get("Marine sediment").get("Trop").put("Marine sediment",
				( ( engine.getConcentration(31) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sdT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")*1000 + 
						environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Trop").put("* SOLID PHASE",
				engine.getConcentration(31)*environment.getEnvProps("TROPICAL", "FRACs.sdT")/
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")/
						(environment.getEnvProps("TROPICAL", "Kp.sdT")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("TROPICAL", "FRACs.sdT"))*(input.getSubstancesData("Molweight")*1000)/
				(environment.getEnvProps("TROPICAL", "FRACs.sdT")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Marine sediment").get("Trop").put("* PORE WATER",
				concentrations.get("Marine sediment").get("Trop").get("* SOLID PHASE")/
				environment.getEnvProps("TROPICAL", "Kp.sdT")
				);


		concentrations.put("Natural soil", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Natural soil").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Natural soil").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Natural soil").get("Reg").put("Natural soil",
				( engine.getConcentration(6)*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.s1R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s1R") - environment.getEnvProps("REGIONAL", "FRACw.s1R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Natural soil").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(6)*environment.getEnvProps("REGIONAL", "FRs.s1R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACs.s1R")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Natural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Natural soil").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.s1R")
				);

		concentrations.get("Natural soil").get("Cont").put("Natural soil",
				( ( engine.getConcentration(15))*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s1C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s1C") - environment.getEnvProps("CONTINENTAL", "FRACw.s1C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Natural soil").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(15)*environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.s1C")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Natural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Natural soil").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.s1C")
				);

		concentrations.put("Agricultural soil", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Agricultural soil").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Agricultural soil").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Agricultural soil").get("Reg").put("Agricultural soil",
				(  engine.getConcentration(7)*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.s2R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s2R") - environment.getEnvProps("REGIONAL", "FRACw.s2R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Agricultural soil").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(7)*environment.getEnvProps("REGIONAL", "FRs.s2R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACs.s2R")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Agricultural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Agricultural soil").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.s2R")
				);

		concentrations.get("Agricultural soil").get("Cont").put("Agricultural soil",
				( engine.getConcentration(16)*input.getSubstancesData("Molweight")*1000./
				(environment.getEnvProps("CONTINENTAL", "FRACw.s2C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s2C") - environment.getEnvProps("CONTINENTAL", "FRACw.s2C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) )
				);
		concentrations.get("Agricultural soil").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(16)*environment.getEnvProps("CONTINENTAL", "FRs.s2C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.s2C")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Agricultural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Agricultural soil").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.s2C")
				);

		concentrations.put("Other soil", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Other soil").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Other soil").get("Reg").put("Other soil",
				(  engine.getConcentration(8)*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.s3R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s3R") - environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Other soil").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(8)*environment.getEnvProps("REGIONAL", "FRs.s3R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACs.s3R")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Other soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.s3R")
				);

		concentrations.get("Other soil").get("Cont").put("Other soil",
				(  engine.getConcentration(17)*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s3C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s3C") - environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Other soil").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(17)*environment.getEnvProps("CONTINENTAL", "FRs.s3C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.s3C")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Other soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.s3C")
				);

		concentrations.get("Other soil").get("Mod").put("Other soil",
				( engine.getConcentration(22)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sM")*1000 + 
						( 1. - environment.getEnvProps("MODERATE", "FRACa.sM") - environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Other soil").get("Mod").put("* SOLID PHASE",
				engine.getConcentration(22)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("MODERATE", "FRACs.sM")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Other soil").get("Mod").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Mod").get("* SOLID PHASE")/
				environment.getEnvProps("MODERATE", "Kp.sM")
				);

		concentrations.get("Other soil").get("Arct").put("Other soil",
				( engine.getConcentration(27)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sA")*1000 + 
						( 1. - environment.getEnvProps("ARCTIC", "FRACa.sA") - environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Other soil").get("Arct").put("* SOLID PHASE",
				engine.getConcentration(27)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("ARCTIC", "FRACs.sA")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Other soil").get("Arct").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Arct").get("* SOLID PHASE")/
				environment.getEnvProps("ARCTIC", "Kp.sA")
				);

		concentrations.get("Other soil").get("Trop").put("Other soil",
				( engine.getConcentration(32)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sT")*1000 + 
						( 1. - environment.getEnvProps("TROPICAL", "FRACa.sT") - environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);
		concentrations.get("Other soil").get("Trop").put("* SOLID PHASE",
				engine.getConcentration(32)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("TROPICAL", "FRACs.sT")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);
		concentrations.get("Other soil").get("Trop").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Trop").get("* SOLID PHASE")/
				environment.getEnvProps("TROPICAL", "Kp.sT")
				);
	}

	void buildFugacities() 
	{				
		fugacities.put("Air", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Air").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Air").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Air").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Air").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Air").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Air").get("Reg").put("Air",
				engine.getFugacity(0)
				);
		fugacities.get("Air").get("Reg").put("* GAS PHASE",
				fugacities.get("Air").get("Reg").get("Air")*environment.getEnvProps("REGIONAL","FRgas.aR")
				);
		fugacities.get("Air").get("Reg").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Reg").get("Air")*(1. - environment.getEnvProps("REGIONAL","FRgas.aR") )
				);

		fugacities.get("Air").get("Cont").put("Air",
				engine.getFugacity(9)
				);
		fugacities.get("Air").get("Cont").put("* GAS PHASE",
				fugacities.get("Air").get("Cont").get("Air")*environment.getEnvProps("CONTINENTAL","FRgas.aC")
				);
		fugacities.get("Air").get("Cont").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Cont").get("Air")*(1. - environment.getEnvProps("CONTINENTAL","FRgas.aC") )
				);

		fugacities.get("Air").get("Mod").put("Air",
				engine.getFugacity(18)
				);
		fugacities.get("Air").get("Mod").put("* GAS PHASE",
				fugacities.get("Air").get("Mod").get("Air")*environment.getEnvProps("MODERATE","FRgas.aM")
				);
		fugacities.get("Air").get("Mod").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Mod").get("Air")*(1. - environment.getEnvProps("MODERATE","FRgas.aM") )
				);

		fugacities.get("Air").get("Arct").put("Air",
				engine.getFugacity(23)
				);
		fugacities.get("Air").get("Arct").put("* GAS PHASE",
				fugacities.get("Air").get("Arct").get("Air")*environment.getEnvProps("ARCTIC","FRgas.aA")
				);
		fugacities.get("Air").get("Arct").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Arct").get("Air")*(1. - environment.getEnvProps("ARCTIC","FRgas.aA") )
				);

		fugacities.get("Air").get("Trop").put("Air",
				engine.getFugacity(28)
				);
		fugacities.get("Air").get("Trop").put("* GAS PHASE",
				fugacities.get("Air").get("Trop").get("Air")*environment.getEnvProps("TROPICAL","FRgas.aT")
				);

		fugacities.get("Air").get("Trop").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Trop").get("Air")*(1. - environment.getEnvProps("TROPICAL","FRgas.aT") )
				);


		fugacities.put("Fresh water lake", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Fresh water lake").get("Reg").put("Fresh water lake",
				engine.getFugacity(1)
				);
		fugacities.get("Fresh water lake").get("Reg").put("* DISSOLVED",
				fugacities.get("Fresh water lake").get("Reg").get("Fresh water lake")*environment.getEnvProps("REGIONAL","FRwD.w0R")
				);
		fugacities.get("Fresh water lake").get("Reg").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water lake").get("Reg").get("Fresh water lake")*(1. - environment.getEnvProps("REGIONAL","FRwD.w0R") )
				);

		fugacities.get("Fresh water lake").get("Cont").put("Fresh water lake",
				engine.getFugacity(10)
				);
		fugacities.get("Fresh water lake").get("Cont").put("* DISSOLVED",
				fugacities.get("Fresh water lake").get("Cont").get("Fresh water lake")*environment.getEnvProps("CONTINENTAL","FRw.w0C")
				);

		fugacities.get("Fresh water lake").get("Cont").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water lake").get("Cont").get("Fresh water lake")*(1. - environment.getEnvProps("CONTINENTAL","FRw.w0C") )
				);

		fugacities.put("Fresh water", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Fresh water").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Fresh water").get("Reg").put("Fresh water",
				engine.getFugacity(2)
				);
		fugacities.get("Fresh water").get("Reg").put("* DISSOLVED",
				fugacities.get("Fresh water").get("Reg").get("Fresh water")*environment.getEnvProps("REGIONAL","FRwD.w1R")
				);
		fugacities.get("Fresh water").get("Reg").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water").get("Reg").get("Fresh water")*(1. - environment.getEnvProps("REGIONAL","FRwD.w1R") )
				);

		fugacities.get("Fresh water").get("Cont").put("Fresh water",
				engine.getFugacity(11)
				);
		fugacities.get("Fresh water").get("Cont").put("* DISSOLVED",
				fugacities.get("Fresh water").get("Cont").get("Fresh water")*environment.getEnvProps("CONTINENTAL","FRw.w1C")
				);
		fugacities.get("Fresh water").get("Cont").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water").get("Cont").get("Fresh water")*(1. - environment.getEnvProps("CONTINENTAL","FRw.w1C") )
				);

		fugacities.put("Fresh water sediment", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Fresh water sediment").get("Reg").put("Fresh water sediment",
				engine.getFugacity(4)
				);
		fugacities.get("Fresh water sediment").get("Reg").put("* PORE WATER",
				fugacities.get("Fresh water sediment").get("Reg").get("Fresh water sediment")*environment.getEnvProps("REGIONAL","FRACw.sdR")
				);
		fugacities.get("Fresh water sediment").get("Reg").put("* SOLID PHASE",
				fugacities.get("Fresh water sediment").get("Reg").get("Fresh water sediment")*(1. - environment.getEnvProps("REGIONAL","FRACw.sdR") )
				);


		fugacities.get("Fresh water sediment").get("Cont").put("Fresh water sediment",
				engine.getFugacity(13)
				);
		fugacities.get("Fresh water sediment").get("Cont").put("* PORE WATER",
				fugacities.get("Fresh water sediment").get("Cont").get("Fresh water sediment")*environment.getEnvProps("CONTINENTAL","FRACw.sdC")
				);

		fugacities.get("Fresh water sediment").get("Cont").put("* SOLID PHASE",
				fugacities.get("Fresh water sediment").get("Cont").get("Fresh water sediment")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.sdC") )
				);

		fugacities.put("Surface sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Surface sea/ocean water").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Surface sea/ocean water").get("Reg").put("Surface sea/ocean water",
				engine.getFugacity(3)
				);
		fugacities.get("Surface sea/ocean water").get("Reg").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water")*environment.getEnvProps("REGIONAL","FRwD.w2R")
				);
		fugacities.get("Surface sea/ocean water").get("Reg").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water")*(1. - environment.getEnvProps("REGIONAL","FRwD.w2R") )
				);

		fugacities.get("Surface sea/ocean water").get("Cont").put("Surface sea/ocean water",
				engine.getFugacity(12)
				);
		fugacities.get("Surface sea/ocean water").get("Cont").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water")*environment.getEnvProps("CONTINENTAL","FRw.w2C")
				);
		fugacities.get("Surface sea/ocean water").get("Cont").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water")*(1. - environment.getEnvProps("CONTINENTAL","FRw.w2C") )
				);

		fugacities.get("Surface sea/ocean water").get("Mod").put("Surface sea/ocean water",
				engine.getFugacity(19)
				);
		fugacities.get("Surface sea/ocean water").get("Mod").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water")*environment.getEnvProps("MODERATE","FRw.w2M")
				);
		fugacities.get("Surface sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water")*(1. - environment.getEnvProps("MODERATE","FRw.w2M") )
				);

		fugacities.get("Surface sea/ocean water").get("Arct").put("Surface sea/ocean water",
				engine.getFugacity(24)
				);
		fugacities.get("Surface sea/ocean water").get("Arct").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water")*environment.getEnvProps("ARCTIC","FRw.w2A")
				);
		fugacities.get("Surface sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water")*(1. - environment.getEnvProps("ARCTIC","FRw.w2A") )
				);

		fugacities.get("Surface sea/ocean water").get("Trop").put("Surface sea/ocean water",
				engine.getFugacity(29)
				);
		fugacities.get("Surface sea/ocean water").get("Trop").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water")*environment.getEnvProps("TROPICAL","FRw.w2T")
				);
		fugacities.get("Surface sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water")*(1. - environment.getEnvProps("TROPICAL","FRw.w2T") )
				);

		fugacities.put("Deep sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Deep sea/ocean water").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Deep sea/ocean water").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Deep sea/ocean water").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Deep sea/ocean water").get("Mod").put("Deep sea/ocean water",
				engine.getFugacity(20)
				);

		fugacities.get("Deep sea/ocean water").get("Mod").put("* DISSOLVED",
				fugacities.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water")*environment.getEnvProps("MODERATE","FRw.w3M")
				);

		fugacities.get("Deep sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				fugacities.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water")*(1. - environment.getEnvProps("MODERATE","FRw.w3M") )
				);

		fugacities.get("Deep sea/ocean water").get("Arct").put("Deep sea/ocean water",
				engine.getFugacity(25)
				);

		fugacities.get("Deep sea/ocean water").get("Arct").put("* DISSOLVED",
				fugacities.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water")*environment.getEnvProps("ARCTIC","FRw.w3A")
				);

		fugacities.get("Deep sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				fugacities.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water")*(1. - environment.getEnvProps("ARCTIC","FRw.w3A") )
				);

		fugacities.get("Deep sea/ocean water").get("Trop").put("Deep sea/ocean water",
				engine.getFugacity(30)
				);

		fugacities.get("Deep sea/ocean water").get("Trop").put("* DISSOLVED",
				fugacities.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water")*environment.getEnvProps("TROPICAL","FRw.w3T")
				);

		fugacities.get("Deep sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				fugacities.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water")*(1. - environment.getEnvProps("TROPICAL","FRw.w3T") )
				);

		fugacities.put("Marine sediment", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Marine sediment").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Marine sediment").get("Reg").put("Marine sediment",
				engine.getFugacity(5)
				);

		fugacities.get("Marine sediment").get("Reg").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Reg").get("Marine sediment")*environment.getEnvProps("REGIONAL","FRACw.sdR")
				);

		fugacities.get("Marine sediment").get("Reg").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Reg").get("Marine sediment")*(1. - environment.getEnvProps("REGIONAL","FRACw.sdR") )
				);

		fugacities.get("Marine sediment").get("Cont").put("Marine sediment",
				engine.getFugacity(14)
				);

		fugacities.get("Marine sediment").get("Cont").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Cont").get("Marine sediment")*environment.getEnvProps("CONTINENTAL","FRACw.sdC")
				);

		fugacities.get("Marine sediment").get("Cont").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Cont").get("Marine sediment")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.sdC") )
				);

		fugacities.get("Marine sediment").get("Mod").put("Marine sediment",
				engine.getFugacity(21)
				);

		fugacities.get("Marine sediment").get("Mod").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Mod").get("Marine sediment")*environment.getEnvProps("MODERATE","FRACw.sdM")
				);

		fugacities.get("Marine sediment").get("Mod").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Mod").get("Marine sediment")*(1. - environment.getEnvProps("MODERATE","FRACw.sdM") )
				);

		fugacities.get("Marine sediment").get("Arct").put("Marine sediment",
				engine.getFugacity(26)
				);

		fugacities.get("Marine sediment").get("Arct").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Arct").get("Marine sediment")*environment.getEnvProps("ARCTIC","FRACw.sdA")
				);

		fugacities.get("Marine sediment").get("Arct").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Arct").get("Marine sediment")*(1. - environment.getEnvProps("ARCTIC","FRACw.sdA") )
				);

		fugacities.get("Marine sediment").get("Trop").put("Marine sediment",
				engine.getFugacity(31)
				);

		fugacities.get("Marine sediment").get("Trop").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Trop").get("Marine sediment")*environment.getEnvProps("TROPICAL","FRACw.sdT")
				);

		fugacities.get("Marine sediment").get("Trop").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Trop").get("Marine sediment")*(1. - environment.getEnvProps("TROPICAL","FRACw.sdT") )
				);

		fugacities.put("Natural soil", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Natural soil").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Natural soil").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Natural soil").get("Reg").put("Natural soil",
				engine.getFugacity(6)
				);

		fugacities.get("Natural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Natural soil").get("Reg").get("Natural soil")*environment.getEnvProps("REGIONAL","FRACw.s1R")
				);

		fugacities.get("Natural soil").get("Reg").put("* SOLID PHASE",
				fugacities.get("Natural soil").get("Reg").get("Natural soil")*(1. - environment.getEnvProps("REGIONAL","FRACw.s1R") )
				);


		fugacities.get("Natural soil").get("Cont").put("Natural soil",
				engine.getFugacity(15)
				);

		fugacities.get("Natural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Natural soil").get("Cont").get("Natural soil")*environment.getEnvProps("CONTINENTAL","FRACw.s1C")
				);

		fugacities.get("Natural soil").get("Cont").put("* SOLID PHASE",
				fugacities.get("Natural soil").get("Cont").get("Natural soil")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.s1C") )
				);

		fugacities.put("Agricultural soil", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Agricultural soil").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Agricultural soil").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Agricultural soil").get("Reg").put("Agricultural soil",
				engine.getFugacity(7)
				);

		fugacities.get("Agricultural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Agricultural soil").get("Reg").get("Agricultural soil")*environment.getEnvProps("REGIONAL","FRACw.s2R")
				);

		fugacities.get("Agricultural soil").get("Reg").put("* SOLID PHASE",
				fugacities.get("Agricultural soil").get("Reg").get("Agricultural soil")*(1. - environment.getEnvProps("REGIONAL","FRACw.s2R") )
				);

		fugacities.get("Agricultural soil").get("Cont").put("Agricultural soil",
				engine.getFugacity(12)
				);

		fugacities.get("Agricultural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Agricultural soil").get("Cont").get("Agricultural soil")*environment.getEnvProps("CONTINENTAL","FRACw.s2C")
				);

		fugacities.get("Agricultural soil").get("Cont").put("* SOLID PHASE",
				fugacities.get("Agricultural soil").get("Cont").get("Agricultural soil")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.s2C") )
				);

		fugacities.put("Other soil", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Other soil").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Other soil").get("Reg").put("Other soil",
				engine.getFugacity(8)
				);

		fugacities.get("Other soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Reg").get("Other soil")*environment.getEnvProps("REGIONAL","FRACw.s3R")
				);

		fugacities.get("Other soil").get("Reg").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Reg").get("Other soil")*(1. - environment.getEnvProps("REGIONAL","FRACw.s3R") )
				);

		fugacities.get("Other soil").get("Cont").put("Other soil",
				engine.getFugacity(17)
				);

		fugacities.get("Other soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Cont").get("Other soil")*environment.getEnvProps("CONTINENTAL","FRACw.s3C")
				);

		fugacities.get("Other soil").get("Cont").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Cont").get("Other soil")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.s3C") )
				);

		fugacities.get("Other soil").get("Mod").put("Other soil",
				engine.getFugacity(22)
				);

		fugacities.get("Other soil").get("Mod").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Mod").get("Other soil")*environment.getEnvProps("MODERATE","FRACw.sM")
				);

		fugacities.get("Other soil").get("Mod").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Mod").get("Other soil")*(1. - environment.getEnvProps("MODERATE","FRACw.sM") )
				);

		fugacities.get("Other soil").get("Arct").put("Other soil",
				engine.getFugacity(27)
				);

		fugacities.get("Other soil").get("Arct").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Arct").get("Other soil")*environment.getEnvProps("ARCTIC","FRACw.sA")
				);

		fugacities.get("Other soil").get("Arct").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Arct").get("Other soil")*(1. - environment.getEnvProps("ARCTIC","FRACw.sA") )
				);

		fugacities.get("Other soil").get("Trop").put("Other soil",
				engine.getFugacity(32)
				);

		fugacities.get("Other soil").get("Trop").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Trop").get("Other soil")*environment.getEnvProps("TROPICAL","FRACw.sT")
				);

		fugacities.get("Other soil").get("Trop").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Trop").get("Other soil")*(1. - environment.getEnvProps("TROPICAL","FRACw.sT") )
				);		
	}

	void buildInflow()
	{			
		inflow.put("Regional Scale", new HashMap< String, Double >() );		
		inflow.get("Regional Scale").put("air", environment.getEnvProps("CONTINENTAL","k.aC.aR")
				*engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("fresh water lakes", environment.getEnvProps("REGIONAL","k.w1R.w0R")
				*engine.getMassMol(2)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("fresh water",(
				environment.getEnvProps("REGIONAL","WATERflow.w0R.w1R")*engine.getConcentration(1) + 
				environment.getEnvProps("CONTINENTAL","WATERflow.w1C.w1R")*engine.getConcentration(11) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("coastal sea water",(
				environment.getEnvProps("REGIONAL","k.w1R.w2R")*engine.getMassMol(2) + 
				environment.getEnvProps("CONTINENTAL","k.w2C.w2R")*engine.getMassMol(12) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Continental Scale", new HashMap< String, Double >() );		
		inflow.get("Continental Scale").put("air",(
				environment.getEnvProps("REGIONAL","k.aR.aC")*engine.getMassMol(0) + 
				environment.getEnvProps("MODERATE","k.aM.aC")*engine.getMassMol(18) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("fresh water lakes", environment.getEnvProps("CONTINENTAL","k.w1C.w0C")
				*engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("fresh water", environment.getEnvProps("CONTINENTAL","k.w0C.w1C")
				*engine.getMassMol(10)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("MODERATE","k.w2M.w2C")*engine.getMassMol(19) + 
				environment.getEnvProps("REGIONAL","k.w2R.w2C")*engine.getMassMol(3) +
				environment.getEnvProps("CONTINENTAL","k.w1C.w2C")*engine.getMassMol(11) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Moderate climate zone").put("air",(
				environment.getEnvProps("CONTINENTAL","k.aC.aM")*engine.getMassMol(9) +
				environment.getEnvProps("ARCTIC","k.aA.aM")*engine.getMassMol(23) +
				environment.getEnvProps("TROPICAL","k.aT.aM")*engine.getMassMol(28) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Moderate climate zone").put("upper ocean water",(
				environment.getEnvProps("CONTINENTAL","k.w2C.w2M")*engine.getMassMol(12)  +
				environment.getEnvProps("ARCTIC","k.w2A.w2M")*engine.getMassMol(19) +
				environment.getEnvProps("TROPICAL","k.w2T.w2M")*engine.getMassMol(29) +
				environment.getEnvProps("MODERATE","k.w3M.w2M")*engine.getMassMol(20) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Moderate climate zone").put("deep sea",(
				environment.getEnvProps("MODERATE","k.w2M.w3M")*engine.getMassMol(19)  +
				environment.getEnvProps("ARCTIC","k.w3A.w3M")*engine.getMassMol(25) +
				environment.getEnvProps("TROPICAL","k.w3T.w3M")*engine.getMassMol(30) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("MODERATE","k.aM.aA")*engine.getMassMol(18)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Arctic climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2A")*engine.getMassMol(19)  +
				environment.getEnvProps("ARCTIC","k.w3A.w2A")*engine.getMassMol(25) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Arctic climate zone").put("deep sea",(
				environment.getEnvProps("ARCTIC","k.w2A.w3A")*engine.getMassMol(24)  +
				environment.getEnvProps("MODERATE","k.w3M.w3A")*engine.getMassMol(20) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("MODERATE","k.aM.aT")*engine.getMassMol(18)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Tropical climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2T")*engine.getMassMol(19)  +
				environment.getEnvProps("TROPICAL","k.w3T.w2T")*engine.getMassMol(30) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Tropical climate zone").put("deep sea",(
				environment.getEnvProps("TROPICAL","k.w2T.w3T")*engine.getMassMol(29)  +
				environment.getEnvProps("MODERATE","k.w3M.w3T")*engine.getMassMol(20) )*
				input.getSubstancesData("Molweight")
				);
	}

	void buildOutflow()
	{	

		outflow.clear();

		outflow.put("Regional Scale", new HashMap< String, Double >() );		

		outflow.get("Regional Scale").put("air", 
				environment.getEnvProps("REGIONAL","k.aR.aC")
				*engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Regional Scale").put("fresh water lakes", 
				(environment.getEnvProps("REGIONAL","k.w0R.w1R") + 
						environment.getEnvProps("REGIONAL","k.w0R.w2R") )*
				engine.getMassMol(1)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Regional Scale").put("fresh water",(
				environment.getEnvProps("REGIONAL","WATERflow.w1R.w0R") + 
				environment.getEnvProps("REGIONAL","WATERflow.w1R.w2R") )*
				engine.getConcentration(2)*
				input.getSubstancesData("Molweight")
				);		

		outflow.get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2R.w2C")
				*engine.getMassMol(3)*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Continental Scale", new HashMap< String, Double >() );		

		outflow.get("Continental Scale").put("air",(
				environment.getEnvProps("CONTINENTAL","k.aC.aM") + 
				environment.getEnvProps("CONTINENTAL","k.aC.aR") )*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("fresh water lakes", (
				environment.getEnvProps("CONTINENTAL","k.w0C.w1C") + 
				environment.getEnvProps("CONTINENTAL","k.w0C.w2C") )*
				engine.getMassMol(10)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.w1C.w0C") + 
				environment.getEnvProps("CONTINENTAL","k.w1C.w1R") +
				environment.getEnvProps("CONTINENTAL","k.w1C.w2C") )*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("CONTINENTAL","k.w2C.w2M") + 
				environment.getEnvProps("CONTINENTAL","k.w2C.w2R") )*
				engine.getMassMol(12)*
				input.getSubstancesData("Molweight")
				);


		outflow.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Moderate climate zone").put("air",(
				environment.getEnvProps("MODERATE","k.aM.aA") +
				environment.getEnvProps("MODERATE","k.aM.aC") +
				environment.getEnvProps("MODERATE","k.aM.aT") )*
				engine.getMassMol(18)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Moderate climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2C") +
				environment.getEnvProps("MODERATE","k.w2M.w2A") +
				environment.getEnvProps("MODERATE","k.w2M.w2T") +
				environment.getEnvProps("MODERATE","k.w2M.w3M") )*
				engine.getMassMol(19)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Moderate climate zone").put("deep sea",(
				environment.getEnvProps("MODERATE","k.w3M.w2M") +
				environment.getEnvProps("MODERATE","k.w3M.w3A") +
				environment.getEnvProps("MODERATE","k.w3M.w3T") )*
				engine.getMassMol(20)*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("ARCTIC","k.aA.aM")*
				engine.getMassMol(23)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Arctic climate zone").put("upper ocean water",(
				environment.getEnvProps("ARCTIC","k.w2A.w2M") +
				environment.getEnvProps("ARCTIC","k.w2A.w3A") )*
				engine.getMassMol(24)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Arctic climate zone").put("deep sea",(
				environment.getEnvProps("ARCTIC","k.w3A.w2A") +
				environment.getEnvProps("ARCTIC","k.w3A.w3M") )*
				engine.getMassMol(25)*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("TROPICAL","k.aT.aM")*
				engine.getMassMol(28)*
				input.getSubstancesData("Molweight")				
				);

		outflow.get("Global Scale - Tropical climate zone").put("upper ocean water",(
				environment.getEnvProps("TROPICAL","k.w2T.w2M") +
				environment.getEnvProps("TROPICAL","k.w2T.w3T") )*
				engine.getMassMol(29)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Tropical climate zone").put("deep sea",(
				environment.getEnvProps("TROPICAL","k.w3T.w2T") +
				environment.getEnvProps("TROPICAL","k.w3T.w3M") )*
				engine.getMassMol(30)*
				input.getSubstancesData("Molweight")
				);
	}

	void buildRemoval()
	{
		removal.clear();

		removal.put("Regional Scale", new HashMap< String, Double >() );		
		removal.get("Regional Scale").put("air", 
				environment.getEnvProps("REGIONAL","kesc.aR")*
				environment.getEnvProps("REGIONAL","VOLUME.aR")
				*engine.getConcentration(0)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("fresh water sediment", 
				environment.getEnvProps("REGIONAL","NETsedrate.w1R")*
				environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
				environment.getEnvProps("REGIONAL","AREAFRAC.w1R")*
				engine.getConcentration(4)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","NETsedrate.w2R")*
				environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
				environment.getEnvProps("REGIONAL","AREAFRAC.w2R")*
				engine.getConcentration(5)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("natural soil", 
				(environment.getEnvProps("REGIONAL","FRACinf.s1R")*
						input.getLandscapeSettings("REGIONAL", "RAINrate.R")/
						environment.getEnvProps("REGIONAL","Ks1w.R")*
						environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
						environment.getEnvProps("REGIONAL","AREAFRAC.s1R")*
						environment.getEnvProps("REGIONAL","CORRleach.s1R"))*
				engine.getConcentration(6)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("agricultural soil", 
				(environment.getEnvProps("REGIONAL","FRACinf.s2R")*
						input.getLandscapeSettings("REGIONAL", "RAINrate.R")/
						environment.getEnvProps("REGIONAL","Ks2w.R")*
						environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
						environment.getEnvProps("REGIONAL","AREAFRAC.s2R")*
						environment.getEnvProps("REGIONAL","CORRleach.s2R"))*
				engine.getConcentration(7)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("other soil", 
				(environment.getEnvProps("REGIONAL","FRACinf.s3R")*
						input.getLandscapeSettings("REGIONAL", "RAINrate.R")/
						environment.getEnvProps("REGIONAL","Ks1w.R")*
						environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
						environment.getEnvProps("REGIONAL","AREAFRAC.s3R")*
						environment.getEnvProps("REGIONAL","CORRleach.s3R"))*
				engine.getConcentration(8)*
				input.getSubstancesData("Molweight")
				);

		removal.put("Continental Scale", new HashMap< String, Double >() );		
		removal.get("Continental Scale").put("air", 
				environment.getEnvProps("CONTINENTAL","kesc.aC")*
				environment.getEnvProps("CONTINENTAL","VOLUME.aC")
				*engine.getConcentration(9)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","NETsedrate.w1C")*
				environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
				environment.getEnvProps("CONTINENTAL","AREAFRAC.w1C")*
				engine.getConcentration(13)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","NETsedrate.w2C")*
				environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
				environment.getEnvProps("CONTINENTAL","AREAFRAC.w2C")*
				engine.getConcentration(14)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("natural soil", 
				(environment.getEnvProps("CONTINENTAL","FRACinf.s1C")*
						input.getLandscapeSettings("CONTINENTAL", "RAINrate.C")/
						environment.getEnvProps("CONTINENTAL","Ks1w.C")*
						environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
						environment.getEnvProps("CONTINENTAL","AREAFRAC.s1C")*
						environment.getEnvProps("CONTINENTAL","CORRleach.s1C"))*
				engine.getConcentration(15)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("agricultural soil", 
				(environment.getEnvProps("CONTINENTAL","FRACinf.s2C")*
						input.getLandscapeSettings("CONTINENTAL", "RAINrate.C")/
						environment.getEnvProps("CONTINENTAL","Ks2w.C")*
						environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
						environment.getEnvProps("CONTINENTAL","AREAFRAC.s2C")*
						environment.getEnvProps("CONTINENTAL","CORRleach.s2C"))*
				engine.getConcentration(16)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("other soil", 
				(environment.getEnvProps("CONTINENTAL","FRACinf.s3C")*
						input.getLandscapeSettings("CONTINENTAL", "RAINrate.C")/
						environment.getEnvProps("CONTINENTAL","Ks3w.C")*
						environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
						environment.getEnvProps("CONTINENTAL","AREAFRAC.s3C")*
						environment.getEnvProps("CONTINENTAL","CORRleach.s3C"))*
				engine.getConcentration(17)*
				input.getSubstancesData("Molweight")
				);


		removal.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Moderate climate zone").put("air",
				environment.getEnvProps("MODERATE","kesc.aM")*
				environment.getEnvProps("MODERATE","VOLUME.aM")*
				engine.getConcentration(18)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Moderate climate zone").put("ocean sediment",
				environment.getEnvProps("MODERATE","NETsedrate.wM")*
				environment.getEnvProps("MODERATE","SYSTEMAREA.M")*
				environment.getEnvProps("MODERATE","AREAFRAC.wM")*
				engine.getConcentration(21)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Moderate climate zone").put("soil",
				(environment.getEnvProps("MODERATE","FRACinf.sM")*
						environment.getEnvProps("MODERATE", "RAINrate.M")/
						environment.getEnvProps("MODERATE","Ksw.M")*
						environment.getEnvProps("MODERATE","SYSTEMAREA.M")*
						environment.getEnvProps("MODERATE","AREAFRAC.sM")*
						environment.getEnvProps("MODERATE","CORRleach.sM"))*
				engine.getConcentration(22)*
				input.getSubstancesData("Molweight")
				);


		removal.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("ARCTIC","kesc.aA")*
				environment.getEnvProps("ARCTIC","VOLUME.aA")*
				engine.getConcentration(23)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Arctic climate zone").put("ocean sediment",
				environment.getEnvProps("ARCTIC","NETsedrate.wA")*
				environment.getEnvProps("ARCTIC","SYSTEMAREA.A")*
				environment.getEnvProps("ARCTIC","AREAFRAC.wA")*
				engine.getConcentration(26)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Arctic climate zone").put("soil",
				(environment.getEnvProps("ARCTIC","FRACinf.sA")*
						environment.getEnvProps("ARCTIC", "RAINrate.A")/
						environment.getEnvProps("ARCTIC","Ksw.A")*
						environment.getEnvProps("ARCTIC","SYSTEMAREA.A")*
						environment.getEnvProps("ARCTIC","AREAFRAC.sA")*
						environment.getEnvProps("ARCTIC","CORRleach.sA"))*
				engine.getConcentration(27)*
				input.getSubstancesData("Molweight")
				);

		removal.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("TROPICAL","kesc.aT")*
				environment.getEnvProps("TROPICAL","VOLUME.aT")*
				engine.getConcentration(28)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Tropical climate zone").put("ocean sediment",
				environment.getEnvProps("TROPICAL","NETsedrate.wT")*
				environment.getEnvProps("TROPICAL","SYSTEMAREA.T")*
				environment.getEnvProps("TROPICAL","AREAFRAC.wT")*
				engine.getConcentration(31)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Tropical climate zone").put("soil",
				(environment.getEnvProps("TROPICAL","FRACinf.sT")*						
						environment.getEnvProps("TROPICAL","RAINrate.T")/
						environment.getEnvProps("TROPICAL","Ksw.T")*
						environment.getEnvProps("TROPICAL","SYSTEMAREA.T")*
						environment.getEnvProps("TROPICAL","AREAFRAC.sT")*
						environment.getEnvProps("TROPICAL","CORRleach.sT"))*
				engine.getConcentration(32)*
				input.getSubstancesData("Molweight")
				);
	}

	void buildDegradation() 
	{
		degradation.put("Regional Scale", new HashMap< String, Double >() );		

		degradation.get("Regional Scale").put("air", 
				environment.getEnvProps("REGIONAL","KDEG.aR")*
				environment.getEnvProps("REGIONAL","VOLUME.aR")*
				engine.getConcentration(0)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("fresh water lakes",  
				environment.getEnvProps("REGIONAL","KDEG.w0R")*
				engine.getMassMol(1)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("fresh water", 				
				environment.getEnvProps("REGIONAL","KDEG.w1R")*
				environment.getEnvProps("REGIONAL","VOLUME.w1R")*
				engine.getConcentration(2)* 
				input.getSubstancesData("Molweight")

				);

		degradation.get("Regional Scale").put("coastal sea water",  
				environment.getEnvProps("REGIONAL","KDEG.w2R")*
				engine.getMassMol(3)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("fresh water sediment",  
				environment.getEnvProps("REGIONAL","KDEG.sd1R")*
				environment.getEnvProps("REGIONAL","VOLUME.sd1R")*
				engine.getConcentration(4)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("coastal marine sediment",  
				environment.getEnvProps("REGIONAL","KDEG.sd2R")*
				environment.getEnvProps("REGIONAL","VOLUME.sd2R")*
				engine.getConcentration(5)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("natural soil",  
				environment.getEnvProps("REGIONAL","KDEG.s1R")*
				environment.getEnvProps("REGIONAL","VOLUME.s1R")*
				engine.getConcentration(6)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("agricultural soil",  
				environment.getEnvProps("REGIONAL","KDEG.s2R")*
				environment.getEnvProps("REGIONAL","VOLUME.s2R")*
				engine.getConcentration(7)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","KDEG.s3R")*
				environment.getEnvProps("REGIONAL","VOLUME.s3R")*
				engine.getConcentration(8)* 
				input.getSubstancesData("Molweight")
				);


		degradation.put("Continental Scale", new HashMap< String, Double >() );		

		degradation.get("Continental Scale").put("air", 
				environment.getEnvProps("CONTINENTAL","KDEG.aC")*
				environment.getEnvProps("CONTINENTAL","VOLUME.aC")*
				engine.getConcentration(9)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("fresh water lakes",  
				environment.getEnvProps("CONTINENTAL","KDEG.w0C")*
				engine.getMassMol(10)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("fresh water", 				
				environment.getEnvProps("CONTINENTAL","KDEG.w1C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.w1C")*
				engine.getConcentration(11)* 
				input.getSubstancesData("Molweight")

				);

		degradation.get("Continental Scale").put("coastal sea water",  
				environment.getEnvProps("CONTINENTAL","KDEG.w2C")*
				engine.getMassMol(12)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("fresh water sediment",  
				environment.getEnvProps("CONTINENTAL","KDEG.sd1C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.sd1C")*
				engine.getConcentration(13)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("coastal marine sediment",  
				environment.getEnvProps("CONTINENTAL","KDEG.sd2C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.sd2C")*
				engine.getConcentration(14)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("natural soil",  
				environment.getEnvProps("CONTINENTAL","KDEG.s1C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.s1C")*
				engine.getConcentration(15)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("agricultural soil",  
				environment.getEnvProps("CONTINENTAL","KDEG.s2C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.s2C")*
				engine.getConcentration(16)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","KDEG.s3C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.s3C")*
				engine.getConcentration(17)* 
				input.getSubstancesData("Molweight")
				);

		degradation.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Moderate climate zone").put("air", 
				environment.getEnvProps("MODERATE","KDEG.aM")*
				environment.getEnvProps("MODERATE","VOLUME.aM")*
				engine.getConcentration(18)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("upper ocean water",  
				environment.getEnvProps("MODERATE","KDEG.w2M")*
				environment.getEnvProps("MODERATE","VOLUME.w2M")*
				engine.getConcentration(19)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("deep sea",  
				environment.getEnvProps("MODERATE","KDEG.w3M")*
				environment.getEnvProps("MODERATE","VOLUME.w3M")*
				engine.getConcentration(20)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("ocean sediment",
				environment.getEnvProps("MODERATE","KDEG.sdM")*
				environment.getEnvProps("MODERATE","VOLUME.sdM")*
				engine.getConcentration(21)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Moderate climate zone").put("soil",  
				environment.getEnvProps("MODERATE","KDEG.sM")*
				environment.getEnvProps("MODERATE","VOLUME.sM")*
				engine.getConcentration(22)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Arctic climate zone").put("air", 
				environment.getEnvProps("ARCTIC","KDEG.aA")*
				environment.getEnvProps("ARCTIC","VOLUME.aA")*
				engine.getConcentration(23)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("upper ocean water",  
				environment.getEnvProps("ARCTIC","KDEG.w2A")*
				environment.getEnvProps("ARCTIC","VOLUME.w2A")*
				engine.getConcentration(24)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("deep sea",  
				environment.getEnvProps("ARCTIC","KDEG.w3A")*
				environment.getEnvProps("ARCTIC","VOLUME.w3A")*
				engine.getConcentration(25)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("ocean sediment",
				environment.getEnvProps("ARCTIC","KDEG.sdA")*
				environment.getEnvProps("ARCTIC","VOLUME.sdA")*
				engine.getConcentration(26)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Arctic climate zone").put("soil",  
				environment.getEnvProps("ARCTIC","KDEG.sA")*
				environment.getEnvProps("ARCTIC","VOLUME.sA")*
				engine.getConcentration(27)* 
				input.getSubstancesData("Molweight")
				);	

		degradation.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Tropical climate zone").put("air", 
				environment.getEnvProps("TROPICAL","KDEG.aT")*
				environment.getEnvProps("TROPICAL","VOLUME.aT")*
				engine.getConcentration(28)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("upper ocean water",  
				environment.getEnvProps("TROPICAL","KDEG.w2T")*
				environment.getEnvProps("TROPICAL","VOLUME.w2T")*
				engine.getConcentration(29)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("deep sea",  
				environment.getEnvProps("TROPICAL","KDEG.w3T")*
				environment.getEnvProps("TROPICAL","VOLUME.w3T")*
				engine.getConcentration(30)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("ocean sediment",
				environment.getEnvProps("TROPICAL","KDEG.sdT")*
				environment.getEnvProps("TROPICAL","VOLUME.sdT")*
				engine.getConcentration(31)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Tropical climate zone").put("soil",  
				environment.getEnvProps("TROPICAL","KDEG.sT")*
				environment.getEnvProps("TROPICAL","VOLUME.sT")*
				engine.getConcentration(32)* 
				input.getSubstancesData("Molweight")
				);	
	}

	void buildTransport() 
	{
		transport.clear();

		transport.put("air-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("air-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("air-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("air-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("air-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("air-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("air-water").get("Regional Scale").put("fresh water lakes", 
				environment.getEnvProps("REGIONAL","k.aRG.w0RD")*
				engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.aRG.w1RD")*
				engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.aRG.w2RD")*
				engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("fresh water lakes", 
				environment.getEnvProps("CONTINENTAL","k.aCG.w0CD")*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.aCG.w1CD")*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.aCG.w2CD")*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.aMG.w2MD")*
				engine.getMassMol(18)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.aAG.w2AD")*
				engine.getMassMol(23)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.aTG.w2TD")*
				engine.getMassMol(28)*
				input.getSubstancesData("Molweight")
				);


		transport.put("water-air", new HashMap< String, Map< String, Double > >() );		
		transport.get("water-air").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("water-air").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("water-air").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("water-air").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("water-air").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );


		transport.get("water-air").get("Regional Scale").put("fresh water lakes", 
				environment.getEnvProps("REGIONAL","k.w0RD.aRG")*
				engine.getMassMol(1)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.w1RD.aRG")*
				engine.getMassMol(2)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2RD.aRG")*
				engine.getMassMol(3)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Continental Scale").put("fresh water lakes", 
				environment.getEnvProps("CONTINENTAL","k.w0CD.aCG")*
				engine.getMassMol(10)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.w1CD.aCG")*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.w2CD.aCG")*
				engine.getMassMol(12)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.w2MD.aMG")*
				engine.getMassMol(19)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.w2AD.aAG")*
				engine.getMassMol(24)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.w2TD.aTG")*
				engine.getMassMol(29)*
				input.getSubstancesData("Molweight")
				);

		transport.put("air-soil", new HashMap< String, Map< String, Double > >() );		
		transport.get("air-soil").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("air-soil").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("air-soil").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("air-soil").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("air-soil").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("air-soil").get("Regional Scale").put("natural soil", 
				environment.getEnvProps("REGIONAL","k.aRG.s1RD")*
				engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Regional Scale").put("agricultural soil", 
				environment.getEnvProps("REGIONAL","k.aRG.s2RD")*
				engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","k.aRG.s3RD")*
				engine.getMassMol(0)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Continental Scale").put("natural soil", 
				environment.getEnvProps("CONTINENTAL","k.aCG.s1CD")*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","k.aCG.s2CD")*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","k.aCG.s3CD")*
				engine.getMassMol(9)*
				input.getSubstancesData("Molweight")
				);		

		transport.get("air-soil").get("Global Scale - Moderate climate zone").put("soil", 
				environment.getEnvProps("MODERATE","k.aMG.sMD")*
				engine.getMassMol(18)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Global Scale - Arctic climate zone").put("soil", 
				environment.getEnvProps("ARCTIC","k.aAG.sAD")*
				engine.getMassMol(23)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Global Scale - Tropical climate zone").put("soil", 
				environment.getEnvProps("TROPICAL","k.aTG.sTD")*
				engine.getMassMol(28)*
				input.getSubstancesData("Molweight")
				);

		transport.put("soil-air", new HashMap< String, Map< String, Double > >() );		
		transport.get("soil-air").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("soil-air").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("soil-air").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("soil-air").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("soil-air").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("soil-air").get("Regional Scale").put("natural soil", 
				environment.getEnvProps("REGIONAL","k.s1RD.aRG")*
				engine.getMassMol(6)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Regional Scale").put("agricultural soil", 
				environment.getEnvProps("REGIONAL","k.s2RD.aRG")*
				engine.getMassMol(7)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","k.s3RD.aRG")*
				engine.getMassMol(8)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Continental Scale").put("natural soil", 
				environment.getEnvProps("CONTINENTAL","k.s1CD.aCG")*
				engine.getMassMol(15)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","k.s2CD.aCG")*
				engine.getMassMol(16)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","k.s3CD.aCG")*
				engine.getMassMol(17)*
				input.getSubstancesData("Molweight")
				);		

		transport.get("soil-air").get("Global Scale - Moderate climate zone").put("soil", 
				environment.getEnvProps("MODERATE","k.sMD.aMG")*
				engine.getMassMol(22)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Global Scale - Arctic climate zone").put("soil", 
				environment.getEnvProps("ARCTIC","k.sAD.aAG")*
				engine.getMassMol(27)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Global Scale - Tropical climate zone").put("soil", 
				environment.getEnvProps("TROPICAL","k.sTD.aTG")*
				engine.getMassMol(32)*
				input.getSubstancesData("Molweight")
				);


		transport.put("soil-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("soil-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("soil-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("soil-water").get("Regional Scale").put("fresh water", (
				environment.getEnvProps("REGIONAL","k.s1RD.w1RD")*engine.getMassMol(1) + 
				environment.getEnvProps("REGIONAL","k.s2RD.w1RD")*engine.getMassMol(7) +
				environment.getEnvProps("REGIONAL","k.s3RD.w1RD")*engine.getMassMol(8) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("natural soil", 
				environment.getEnvProps("REGIONAL","k.s1RD.w1RD")*
				engine.getMassMol(6)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("agricultural soil", 
				environment.getEnvProps("REGIONAL","k.s2RD.w1RD")*
				engine.getMassMol(7)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","k.s3RD.w1RD")*
				engine.getMassMol(8)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.s1C.w1C")*engine.getMassMol(15) + 
				environment.getEnvProps("CONTINENTAL","k.s2C.w1C")*engine.getMassMol(16) +
				environment.getEnvProps("CONTINENTAL","k.s3C.w1C")*engine.getMassMol(17) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("natural soil", 
				environment.getEnvProps("CONTINENTAL","k.s1C.w1C")*
				engine.getMassMol(15)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","k.s2C.w1C")*
				engine.getMassMol(16)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","k.s3C.w1C")*
				engine.getMassMol(17)*
				input.getSubstancesData("Molweight")
				);		

		transport.get("soil-water").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.sMD.w2MD")*
				engine.getMassMol(22)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Moderate climate zone").put("soil", 
				environment.getEnvProps("MODERATE","k.sMD.w2MD")*
				engine.getMassMol(22)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.sAD.w2AD")*
				engine.getMassMol(27)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Arctic climate zone").put("soil", 
				environment.getEnvProps("ARCTIC","k.sAD.w2AD")*
				engine.getMassMol(27)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.sTD.w2TD")*
				engine.getMassMol(32)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Tropical climate zone").put("soil", 
				environment.getEnvProps("TROPICAL","k.sTD.w2TD")*
				engine.getMassMol(32)*
				input.getSubstancesData("Molweight")
				);

		transport.put("water-sed", new HashMap< String, Map< String, Double > >() );		
		transport.get("water-sed").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("water-sed").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("water-sed").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.w1RD.sd1RD")*
				engine.getMassMol(2)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2RD.sd2RD")*
				engine.getMassMol(3)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("water-sed").get("Regional Scale").put("fresh water sediment", 
				environment.getEnvProps("REGIONAL","k.w1RD.sd1RD")*
				engine.getMassMol(2)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","k.w2RD.sd2RD")*
				engine.getMassMol(3)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.w1CD.sd1CD")*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.w2CD.sd2CD")*
				engine.getMassMol(12)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","k.w1CD.sd1CD")*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","k.w2CD.sd2CD")*
				engine.getMassMol(12)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Moderate climate zone").put("deep sea", 
				environment.getEnvProps("MODERATE","k.w3MD.sdMD")*
				engine.getMassMol(20)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Moderate climate zone").put("ocean sediment", 
				environment.getEnvProps("MODERATE","k.w3MD.sdMD")*
				engine.getMassMol(20)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Arctic climate zone").put("deep sea", 
				environment.getEnvProps("ARCTIC","k.w3AD.sdAD")*
				engine.getMassMol(25)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Arctic climate zone").put("ocean sediment", 
				environment.getEnvProps("ARCTIC","k.w3AD.sdAD")*
				engine.getMassMol(25)*
				input.getSubstancesData("Molweight")
				);


		transport.get("water-sed").get("Global Scale - Tropical climate zone").put("deep sea", 
				environment.getEnvProps("TROPICAL","k.w3TD.sdTD")*
				engine.getMassMol(30)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Tropical climate zone").put("ocean sediment", 
				environment.getEnvProps("TROPICAL","k.w3TD.sdTD")*
				engine.getMassMol(30)*
				input.getSubstancesData("Molweight")
				);

		transport.put("sed-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("sed-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("sed-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("sed-water").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.sd1RD.w1RD")*
				engine.getMassMol(4)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.sd2RD.w2RD")*
				engine.getMassMol(5)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("sed-water").get("Regional Scale").put("fresh water sediment", 
				environment.getEnvProps("REGIONAL","k.sd1RD.w1RD")*
				engine.getMassMol(4)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","k.sd2RD.w2RD")*
				engine.getMassMol(5)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.sd1CD.w1CD")*
				engine.getMassMol(13)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.sd2CD.w2CD")*
				engine.getMassMol(14)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("sed-water").get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","k.sd1CD.w1CD")*
				engine.getMassMol(13)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","k.sd2CD.w2CD")*
				engine.getMassMol(14)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.sdMD.w3MD")*
				engine.getMassMol(21)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Moderate climate zone").put("deep sea", 
				environment.getEnvProps("MODERATE","k.sdMD.w3MD")*
				engine.getMassMol(21)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*
				engine.getMassMol(26)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Arctic climate zone").put("deep sea", 
				environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*
				engine.getMassMol(26)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*
				engine.getMassMol(31)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Tropical climate zone").put("deep sea", 
				environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*
				engine.getMassMol(31)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Regional Scale").put("air", 
				transport.get("air-water").get("Regional Scale").get("fresh water lakes") + 
				transport.get("air-water").get("Regional Scale").get("fresh water") + 
				transport.get("air-water").get("Regional Scale").get("coastal sea water")  +
				transport.get("air-soil").get("Regional Scale").get("natural soil") +
				transport.get("air-soil").get("Regional Scale").get("agricultural soil") +
				transport.get("air-soil").get("Regional Scale").get("other soil") 
				);

		transport.get("air-water").get("Continental Scale").put("air", 
				transport.get("air-water").get("Continental Scale").get("fresh water lakes") + 
				transport.get("air-water").get("Continental Scale").get("fresh water") + 
				transport.get("air-water").get("Continental Scale").get("coastal sea water")+  
				transport.get("air-soil").get("Continental Scale").get("natural soil") +
				transport.get("air-soil").get("Continental Scale").get("agricultural soil") +
				transport.get("air-soil").get("Continental Scale").get("other soil") 
				);

		transport.get("air-water").get("Global Scale - Moderate climate zone").put("air", 
				transport.get("air-water").get("Global Scale - Moderate climate zone").get("upper ocean water") + 
				transport.get("air-soil").get("Global Scale - Moderate climate zone").get("soil") 
				);

		transport.get("air-water").get("Global Scale - Arctic climate zone").put("air", 
				transport.get("air-water").get("Global Scale - Arctic climate zone").get("upper ocean water") + 
				transport.get("air-soil").get("Global Scale - Arctic climate zone").get("soil") 
				);

		transport.get("air-water").get("Global Scale - Tropical climate zone").put("air", 
				transport.get("air-water").get("Global Scale - Tropical climate zone").get("upper ocean water") + 
				transport.get("air-soil").get("Global Scale - Tropical climate zone").get("soil") 
				);

		transport.get("water-air").get("Regional Scale").put("air", 
				transport.get("water-air").get("Regional Scale").get("fresh water lakes") + 
				transport.get("water-air").get("Regional Scale").get("fresh water") + 
				transport.get("water-air").get("Regional Scale").get("coastal sea water")  +
				transport.get("soil-air").get("Regional Scale").get("natural soil") +
				transport.get("soil-air").get("Regional Scale").get("agricultural soil") +
				transport.get("soil-air").get("Regional Scale").get("other soil") 
				);

		transport.get("water-air").get("Continental Scale").put("air", 
				transport.get("water-air").get("Continental Scale").get("fresh water lakes") + 
				transport.get("water-air").get("Continental Scale").get("fresh water") + 
				transport.get("water-air").get("Continental Scale").get("coastal sea water")+  
				transport.get("soil-air").get("Continental Scale").get("natural soil") +
				transport.get("soil-air").get("Continental Scale").get("agricultural soil") +
				transport.get("soil-air").get("Continental Scale").get("other soil") 
				);

		transport.get("water-air").get("Global Scale - Moderate climate zone").put("air", 
				transport.get("water-air").get("Global Scale - Moderate climate zone").get("upper ocean water") + 
				transport.get("soil-air").get("Global Scale - Moderate climate zone").get("soil") 
				);

		transport.get("water-air").get("Global Scale - Arctic climate zone").put("air", 
				transport.get("water-air").get("Global Scale - Arctic climate zone").get("upper ocean water") + 
				transport.get("soil-air").get("Global Scale - Arctic climate zone").get("soil") 
				);

		transport.get("water-air").get("Global Scale - Tropical climate zone").put("air", 
				transport.get("water-air").get("Global Scale - Tropical climate zone").get("upper ocean water") + 
				transport.get("soil-air").get("Global Scale - Tropical climate zone").get("soil") 
				);
	}

	void buildEmission() 
	{
		//Clear because it can come from not nano environment
		emission.clear();

		emission.put("Regional Scale", new HashMap< String, Double >() );

		emission.get("Regional Scale").put("air", 
				input.getEmissionRates("E.aRS")*
				input.getSubstancesData("Molweight")
				);
		
		System.out.print( input.getSubstancesData("Molweight") );

		emission.get("Regional Scale").put("fresh water lakes", 
				input.getEmissionRates("E.w0RD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("fresh water", 
				input.getEmissionRates("E.w1RS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("coastal sea water", 
				input.getEmissionRates("E.w2RS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("natural soil", 
				input.getEmissionRates("E.s1RS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("agricultural soil", 
				input.getEmissionRates("E.s2RS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("other soil", 
				input.getEmissionRates("E.s3RS")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Continental Scale", new HashMap< String, Double >() );

		emission.get("Continental Scale").put("air", 
				input.getEmissionRates("E.aCS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("fresh water lakes", 
				input.getEmissionRates("E.w0CD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("fresh water", 
				input.getEmissionRates("E.w1CS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("coastal sea water", 
				input.getEmissionRates("E.w2CS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("natural soil", 
				input.getEmissionRates("E.s1CS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("agricultural soil", 
				input.getEmissionRates("E.s2CS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("other soil", 
				input.getEmissionRates("E.s3CS")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );
		emission.get("Global Scale - Moderate climate zone").put("air", 
				input.getEmissionRates("E.aMS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Moderate climate zone").put("upper ocean water", 
				input.getEmissionRates("E.w2MS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Moderate climate zone").put("soil", 
				input.getEmissionRates("E.sMS")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );
		emission.get("Global Scale - Arctic climate zone").put("air", 
				input.getEmissionRates("E.aAS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Arctic climate zone").put("upper ocean water", 
				input.getEmissionRates("E.w2AS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Arctic climate zone").put("soil", 
				input.getEmissionRates("E.sAS")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );
		emission.get("Global Scale - Tropical climate zone").put("air", 
				input.getEmissionRates("E.aTS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Tropical climate zone").put("upper ocean water", 
				input.getEmissionRates("E.w2TS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Tropical climate zone").put("soil", 
				input.getEmissionRates("E.sTD")*
				input.getSubstancesData("Molweight")
				);
	}

	void buildTotals()
	{
		totalD.put("Regional Scale", 		
				masses.get("Air").get("Reg").get("Air") + 
				masses.get("Fresh water lake").get("Reg").get("Fresh water lake") + 
				masses.get("Fresh water").get("Reg").get("Fresh water") +
				masses.get("Fresh water sediment").get("Reg").get("Fresh water sediment") +
				masses.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water") + 
				masses.get("Marine sediment").get("Reg").get("Marine sediment") +
				masses.get("Natural soil").get("Reg").get("Natural soil") + 
				masses.get("Agricultural soil").get("Reg").get("Agricultural soil") + 
				masses.get("Other soil").get("Reg").get("Other soil") 
				);

		totalD.put("Continental Scale", 		
				masses.get("Air").get("Cont").get("Air") + 
				masses.get("Fresh water lake").get("Cont").get("Fresh water lake") + 
				masses.get("Fresh water").get("Cont").get("Fresh water") +
				masses.get("Fresh water sediment").get("Cont").get("Fresh water sediment") +
				masses.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water") + 
				masses.get("Marine sediment").get("Cont").get("Marine sediment") +
				masses.get("Natural soil").get("Cont").get("Natural soil") + 
				masses.get("Agricultural soil").get("Cont").get("Agricultural soil") + 
				masses.get("Other soil").get("Cont").get("Other soil") 
				);

		totalD.put("Moderate Scale", 		
				masses.get("Air").get("Mod").get("Air") + 
				masses.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water") + 
				masses.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water") + 
				masses.get("Marine sediment").get("Mod").get("Marine sediment") +
				masses.get("Other soil").get("Mod").get("Other soil") 
				);

		totalD.put("Arctic Scale", 		
				masses.get("Air").get("Arct").get("Air") + 
				masses.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water") + 
				masses.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water") + 
				masses.get("Marine sediment").get("Arct").get("Marine sediment") +
				masses.get("Other soil").get("Arct").get("Other soil") 
				);

		totalD.put("Tropical Scale", 		
				masses.get("Air").get("Trop").get("Air") + 
				masses.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water") + 
				masses.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water") + 
				masses.get("Marine sediment").get("Trop").get("Marine sediment") +
				masses.get("Other soil").get("Trop").get("Other soil") 
				);

		totalReg = totalD.get("Regional Scale");
		totalCont = totalD.get("Continental Scale");
		totalMod = totalD.get("Moderate Scale");
		totalArct = totalD.get("Arctic Scale");
		totalTrop = totalD.get("Tropical Scale");
	}

	@Listen("onChange=#resultsCombo")
	public void showResultsFor() throws IOException
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		/*		if ( resultsCombo.getSelectedItem().getValue().equals( regPart) ) {
			disBuilder();

			showImageRegional();
			imgResA.setVisible(true);
			imgResB.setVisible(false);			

			concGrid.setVisible(true);
			zonesGrid.setVisible(false);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();

		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(contPart) ) {
			disBuilder();			

			showImageContinental();
			imgResA.setVisible(true);
			imgResB.setVisible(false);			

			concGrid.setVisible(true);
			zonesGrid.setVisible(false);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(globPart) ) {
			disBuilder();

			showImageGlobal();
			imgResA.setVisible(false);
			imgResB.setVisible(true);

			concGrid.setVisible(false);
			zonesGrid.setVisible(true);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();
		}		
		else if ( resultsCombo.getSelectedItem().getValue().equals(contPart) ) {
			disBuilder();

			showImageContinental();
			imgResA.setVisible(true);
			imgResB.setVisible(false);			

			concGrid.setVisible(true);
			zonesGrid.setVisible(false);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();
		}
		else
		 */
		if ( resultsCombo.getSelectedItem().getValue().equals(regDis) ) {
			disBuilder();

			showImageRegional();
			imgResA.setVisible(true);
			imgResB.setVisible(false);			

			concGrid.setVisible(true);
			zonesGrid.setVisible(false);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(contDis) ) {			
			disBuilder();

			showImageContinental();
			imgResA.setVisible(true);
			imgResB.setVisible(false);			

			concGrid.setVisible(true);
			zonesGrid.setVisible(false);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(globDis) ) {
			disBuilder();

			showImageGlobal();
			imgResA.setVisible(false);
			imgResB.setVisible(true);

			concGrid.setVisible(false);
			zonesGrid.setVisible(true);

			concGridT.setVisible(false);
			concGrid1.setVisible(false);
			concGrid2.setVisible(false);
			concGrid3.setVisible(false);

			concLabT.setVisible(false);
			concLab1.setVisible(false);
			concLab2.setVisible(false);
			concLab3.setVisible(false);
			updateResTables();
		}
		/*		else if ( resultsCombo.getSelectedItem().getValue().equals(nanoSpec) ) {
			disBuilder();

			imgResA.setVisible(false);
			imgResB.setVisible(false);

			concGrid.setVisible(false);
			zonesGrid.setVisible(false);

			concGridT.setVisible(true);
			concGrid1.setVisible(true);
			concGrid2.setVisible(true);
			concGrid3.setVisible(true);

			concLabT.setVisible(true);
			concLab1.setVisible(true);
			concLab2.setVisible(true);
			concLab3.setVisible(true);
			updateResTables();
		}*/
	}

	void updateResTables()
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		if ( resultsCombo.getSelectedItem().getValue().equals( regDis ) ) {		
			airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Reg").get("Air") ) ) );	
			freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Reg").get("Fresh water lake") ) ) );
			freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Reg").get("Fresh water") ) ) );
			seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Reg").get("Surface sea/ocean water") ) ) );
			freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Reg").get("Fresh water sediment") ) ) );
			seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Reg").get("Marine sediment") ) ) );
			naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Reg").get("Natural soil") ) ) );
			agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Reg").get("Agricultural soil") ) ) );
			otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Reg").get("Other soil") ) ) );			
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals( contDis ) ) {		
			airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Cont").get("Air") ) ) );	
			freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Cont").get("Fresh water lake") ) ) );
			freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Cont").get("Fresh water") ) ) );
			seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Cont").get("Surface sea/ocean water") ) ) );
			freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Cont").get("Fresh water sediment") ) ) );
			seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Cont").get("Marine sediment") ) ) );
			naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Cont").get("Natural soil") ) ) );
			agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Cont").get("Agricultural soil") ) ) );
			otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Cont").get("Other soil") ) ) );						
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals( globDis ) ) {
			modAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Mod").get("Air") ) ) );	
			modSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Mod").get("Surface sea/ocean water") ) ) );
			modDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Mod").get("Deep sea/ocean water") ) ) );
			modSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Mod").get("Marine sediment") ) ) );
			modSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Mod").get("Other soil") ) ) );

			arctAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Arct").get("Air") ) ) );	
			arctSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Arct").get("Surface sea/ocean water") ) ) );
			arctDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Arct").get("Deep sea/ocean water") ) ) );
			arctSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Arct").get("Marine sediment") ) ) );
			arctSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Arct").get("Other soil") ) ) );

			tropAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Trop").get("Air") ) ) );	
			tropSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Trop").get("Surface sea/ocean water") ) ) );
			tropDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Trop").get("Deep sea/ocean water") ) ) );
			tropSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Trop").get("Marine sediment") ) ) );
			tropSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Trop").get("Other soil") ) ) );			
		}
	}

	@Listen("onClick = #download")
	public void downloadData() throws IOException, InterruptedException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		//Create a workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet spreadsheet = workbook.createSheet("all species output");

		// Create the rows used
		ArrayList< XSSFRow > rows = new ArrayList< XSSFRow >();

		//For styles
		XSSFCellStyle boldStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		boldStyle.setFont(font);  

		XSSFCellStyle noBoldStyle = workbook.createCellStyle();
		XSSFFont noBoldFont = workbook.createFont();
		noBoldFont.setBold(false);
		noBoldStyle.setFont(noBoldFont);  

		//According to excel
		int iRowsNum = 54;
		for ( int i = 0; i < iRowsNum; i++ )
			rows.add( spreadsheet.createRow( i ) );

		rows.get(0).createCell( 1 ).setCellValue( "Output table 1:  Steady-state Concentrations, Fugacities, Emissions and Mass" );
		rows.get(0).getCell(1).setCellStyle(boldStyle);

		//leave a blank row
		rows.get(1).createCell( 0 ).setCellValue(" ");			

		rows.get(2).createCell( 1 ).setCellValue("Model");			
		rows.get(2).getCell( 1 ).setCellStyle( boldStyle );
		rows.get(2).createCell( 2 ).setCellValue("SimpleBox vs 4.02");			

		rows.get(3).createCell( 1 ).setCellValue( "Substance" );			
		rows.get(3).getCell( 1 ).setCellStyle( boldStyle );
		rows.get(3).createCell( 2 ).setCellValue( nanomaterialName );			

		rows.get(4).createCell( 1 ).setCellValue("Scenario");
		rows.get(4).getCell( 1 ).setCellStyle( boldStyle );
		rows.get(4).createCell( 2 ).setCellValue( scenarioName );			

		rows.get(8).createCell( 2 ).setCellValue("Masses (kg)");
		rows.get(8).getCell( 2 ).setCellStyle(boldStyle);

		rows.get(9).createCell( 2 ).setCellValue( "Reg" );			
		rows.get(9).getCell( 2 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 4 ).setCellValue( "Cont" );			
		rows.get(9).getCell( 4 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 6 ).setCellValue( "Mod" );			
		rows.get(9).getCell( 6 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 8 ).setCellValue( "Arct" );			
		rows.get(9).getCell( 8 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 10 ).setCellValue( "Trop" );			
		rows.get(9).getCell( 10 ).setCellStyle(boldStyle);

		rows.get(8).createCell( 12 ).setCellValue("Concentrations");
		rows.get(8).getCell( 12 ).setCellStyle(boldStyle);

		rows.get(9).createCell( 12 ).setCellValue( "Reg" );			
		rows.get(9).getCell( 12 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 13 ).setCellValue( "Cont" );			
		rows.get(9).getCell( 13 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 14 ).setCellValue( "Mod" );			
		rows.get(9).getCell( 14 ).setCellStyle(boldStyle);
		rows.get(9).createCell( 15 ).setCellValue( "Arct" );			
		rows.get(9).getCell(15).setCellStyle(boldStyle);
		rows.get(9).createCell( 16 ).setCellValue( "Trop" );			
		rows.get(9).getCell(16).setCellStyle(boldStyle);

		rows.get(8).createCell( 18).setCellValue("Fugacities (Pa)");
		rows.get(8).getCell(18).setCellStyle(boldStyle);

		rows.get(9).createCell( 18 ).setCellValue( "Reg" );			
		rows.get(9).getCell(18).setCellStyle(boldStyle);
		rows.get(9).createCell( 19 ).setCellValue( "Cont" );			
		rows.get(9).getCell(19).setCellStyle(boldStyle);
		rows.get(9).createCell( 20 ).setCellValue( "Mod" );			
		rows.get(9).getCell(20).setCellStyle(boldStyle);
		rows.get(9).createCell( 21 ).setCellValue( "Arct" );			
		rows.get(9).getCell(21).setCellStyle(boldStyle);
		rows.get(9).createCell( 22 ).setCellValue( "Trop" );			
		rows.get(9).getCell(22).setCellStyle(boldStyle);

		ArrayList<String> regions = new ArrayList<String>();
		regions.add("Reg");
		regions.add("Cont");
		regions.add("Mod");
		regions.add("Arct");
		regions.add("Trop");

		ArrayList<String> teras = new ArrayList<String>();
		teras.add("Air");
		teras.add("Fresh water lake");
		teras.add("Fresh water lake sediment");
		teras.add("Fresh water");
		teras.add("Fresh water sediment");
		teras.add("Surface sea/ocean water");
		teras.add("Deep sea/ocean water");
		teras.add("Marine sediment");
		teras.add("Natural soil");
		teras.add("Agricultural soil");
		teras.add("Other soil");

		ArrayList<String> prop1 = new ArrayList<String>();
		prop1.add("* GAS PHASE");
		prop1.add("* AEROSOL- and CLOUD PHASES");

		ArrayList<String> prop2 = new ArrayList<String>();
		prop2.add("* PORE WATER");
		prop2.add("* SOLID PHASE");

		ArrayList<String> prop3 = new ArrayList<String>();
		prop3.add("* DISSOLVED");
		prop3.add("* SUSPENDED SOLIDS");

		ArrayList<String> prop4 = new ArrayList<String>();
		prop4.add("* PORE WATER / GROUNDWATER");
		prop4.add("* SOLID PHASE");

		int iRow = 10;
		rows.get( iRow ).createCell(1).setCellValue("Air");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop1) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Fresh water lake");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop3) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Fresh water");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop3) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Fresh water sediment");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop2) 		
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Surface sea/ocean water");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop3) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Deep sea/ocean water");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop3) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Marine sediment");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop3) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Natural soil");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop4) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Agricultural soil");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop4) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		iRow++;
		rows.get( iRow ).createCell(1).setCellValue("Other soil");
		rows.get( iRow ).getCell(1).setCellStyle(boldStyle);
		iRow++;
		for ( String entry:prop4) 
			rows.get(iRow++).createCell(1).setCellValue( entry );

		//Writing masses for Reg and Cont
		int k =0;
		for ( int j = 2; j <= 4; j+=2, k++) {
			iRow = 10;
			rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Air").get( regions.get( k ) ).get("Air") );
			for ( String entry:prop1) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Air").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Fresh water lake").get(regions.get(k) ).get("Fresh water lake") );
			for ( String entry:prop3) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Fresh water lake").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Fresh water").get(regions.get(k)).get("Fresh water"));
			for ( String entry:prop3) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Fresh water").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Fresh water sediment").get(regions.get(k)).get("Fresh water sediment"));
			for ( String entry:prop2) 		
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Fresh water sediment").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Surface sea/ocean water").get(regions.get(k)).get("Surface sea/ocean water"));
			for ( String entry:prop3) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			//		rows.get( iRow++ ).createCell(2).setCellValue("Deep sea/ocean water");/
			for ( String entry:prop3) 
				iRow++;
			//			rows.get( iRow ).createCell(2).setCellValue( masses.get("Deep sea/ocean water").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Marine sediment").get(regions.get(k)).get( "Marine sediment" ) );
			for ( String entry:prop2) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Marine sediment").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Natural soil").get(regions.get(k)).get( "Natural soil" ));
			for ( String entry:prop4) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Natural soil").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Agricultural soil").get(regions.get(k)).get( "Agricultural soil" ));
			for ( String entry:prop4) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Agricultural soil").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Other soil").get(regions.get(k)).get( "Other soil" ));
			for ( String entry:prop4) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Other soil").get(regions.get(k)).get( entry ) );
		}

		//Writing masses for Mod, Arct and Trop
		k = 2;
		for ( int j = 6; j <= 10; j+=2, k++) {
			iRow = 10;
			rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Air").get( regions.get( k ) ).get("Air") );
			for ( String entry:prop1) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Air").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop2) 		
				iRow++;

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Surface sea/ocean water").get(regions.get(k)).get("Surface sea/ocean water"));
			for ( String entry:prop3) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Deep sea/ocean water").get(regions.get(k)).get( "Deep sea/ocean water" ));
			for ( String entry:prop3)
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Deep sea/ocean water").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Marine sediment").get(regions.get(k)).get( "Marine sediment" ) );
			for ( String entry:prop2) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Marine sediment").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			//			rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Natural soil").get(regions.get(k)).get( "Natural soil" ));
			for ( String entry:prop4) 
				iRow++;
			//			rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Natural soil").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			//		rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Agricultural soil").get(regions.get(k)).get( "Agricultural soil" ));
			for ( String entry:prop4) 
				iRow++;
			//			rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Agricultural soil").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Other soil").get(regions.get(k)).get( "Other soil" ));
			for ( String entry:prop4) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Other soil").get(regions.get(k)).get( entry ) );
		}

		//Writing concentrations for Reg and Cont
		k = 0;
		for ( int j = 12; j <= 13; j++, k++) {
			iRow = 10;
			rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );			
			rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Air").get( regions.get( k ) ).get("Air") );
			for ( String entry:prop1) {
				rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );			
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Air").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Fresh water lake").get(regions.get(k) ).get("Fresh water lake") );
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );		
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Fresh water lake").get(regions.get(k)).get( entry ) );
			}


			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Fresh water").get(regions.get(k)).get("Fresh water"));
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Fresh water").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Fresh water sediment").get(regions.get(k)).get("Fresh water sediment"));
			for ( String entry:prop2) {
				if ( entry ==  "* PORE WATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Fresh water sediment").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Surface sea/ocean water").get(regions.get(k)).get("Surface sea/ocean water"));
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Marine sediment").get(regions.get(k)).get( "Marine sediment" ) );
			for ( String entry:prop2) {
				if ( entry ==  "* PORE WATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Marine sediment").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Natural soil").get(regions.get(k)).get( "Natural soil" ));
			for ( String entry:prop4) {
				if ( entry ==  "* PORE WATER / GROUNDWATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Natural soil").get(regions.get(k)).get( entry ) );				
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Agricultural soil").get(regions.get(k)).get( "Agricultural soil" ));
			for ( String entry:prop4) { 
				if ( entry ==  "* PORE WATER / GROUNDWATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Agricultural soil").get(regions.get(k)).get( entry ) );
			}


			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Other soil").get(regions.get(k)).get( "Other soil" ));
			for ( String entry:prop4) {
				if ( entry ==  "* PORE WATER / GROUNDWATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Other soil").get(regions.get(k)).get( entry ) );
			}
		}

		//Writing concentrations for Mod, Arct and Trop
		k = 2;
		for ( int j = 14; j <= 16; j++, k++) {
			iRow = 10;
			rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Air").get( regions.get( k ) ).get("Air") );
			for ( String entry:prop1) 
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Air").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop2) 		
				iRow++;

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Surface sea/ocean water").get(regions.get(k)).get("Surface sea/ocean water"));
			for ( String entry:prop3) 
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Deep sea/ocean water").get(regions.get(k)).get("Deep sea/ocean water"));
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );	
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Deep sea/ocean water").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Marine sediment").get(regions.get(k)).get( "Marine sediment" ) );
			for ( String entry:prop2) 
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Marine sediment").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			for ( String entry:prop4) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop4) 
				iRow++;

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Other soil").get(regions.get(k)).get( "Other soil" ));
			for ( String entry:prop4) 
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Other soil").get(regions.get(k)).get( entry ) );
		}

		
		//Writing fugacities for Reg and Cont
		k = 0;
		for ( int j = 18; j <= 19; j++, k++) {
			iRow = 10;
			rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );			
			rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Air").get( regions.get( k ) ).get("Air") );
			for ( String entry:prop1) {
				rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );			
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Air").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Fresh water lake").get(regions.get(k) ).get("Fresh water lake") );
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.m-3" );		
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Fresh water lake").get(regions.get(k)).get( entry ) );
			}


			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Fresh water").get(regions.get(k)).get("Fresh water"));
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Fresh water").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Fresh water sediment").get(regions.get(k)).get("Fresh water sediment"));
			for ( String entry:prop2) {
				if ( entry ==  "* PORE WATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Fresh water sediment").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Surface sea/ocean water").get(regions.get(k)).get("Surface sea/ocean water"));
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Marine sediment").get(regions.get(k)).get( "Marine sediment" ) );
			for ( String entry:prop2) {
				if ( entry ==  "* PORE WATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Marine sediment").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Natural soil").get(regions.get(k)).get( "Natural soil" ));
			for ( String entry:prop4) {
				if ( entry ==  "* PORE WATER / GROUNDWATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Natural soil").get(regions.get(k)).get( entry ) );				
			}

			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Agricultural soil").get(regions.get(k)).get( "Agricultural soil" ));
			for ( String entry:prop4) { 
				if ( entry ==  "* PORE WATER / GROUNDWATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Agricultural soil").get(regions.get(k)).get( entry ) );
			}


			iRow++;
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Other soil").get(regions.get(k)).get( "Other soil" ));
			for ( String entry:prop4) {
				if ( entry ==  "* PORE WATER / GROUNDWATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		

				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Other soil").get(regions.get(k)).get( entry ) );
			}
		}

		//Writing fugacities for Mod, Arct and Trop
		k = 2;
		for ( int j = 20; j <= 22; j++, k++) {
			iRow = 10;
			rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Air").get( regions.get( k ) ).get("Air") );
			for ( String entry:prop1) 
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Air").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop3) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop2) 		
				iRow++;

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Surface sea/ocean water").get(regions.get(k)).get("Surface sea/ocean water"));
			for ( String entry:prop3) 
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Deep sea/ocean water").get(regions.get(k)).get("Deep sea/ocean water"));
			for ( String entry:prop3) {
				if ( entry ==  "* SUSPENDED SOLIDS" )
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(d)-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );	
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Deep sea/ocean water").get(regions.get(k)).get( entry ) );
			}

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Marine sediment").get(regions.get(k)).get( "Marine sediment" ) );
			for ( String entry:prop2) 
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Marine sediment").get(regions.get(k)).get( entry ) );

			iRow++;
			iRow++;
			for ( String entry:prop4) 
				iRow++;

			iRow++;
			iRow++;
			for ( String entry:prop4) 
				iRow++;

			iRow++;
			rows.get( iRow++ ).createCell(j).setCellValue(fugacities.get("Other soil").get(regions.get(k)).get( "Other soil" ));
			for ( String entry:prop4) 
				rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Other soil").get(regions.get(k)).get( entry ) );
		}

		//Re-build in order to write the info
		disBuilder();
		int iCol = addNewSheet( "Steady state mass flows", "Output table 2: Steady-state mass flows (kg.s-1)",
				workbook, regions, iRow);

		//Write to the excel 
		workbook.write( os );
		Filedownload.save( os.toByteArray(), null, "output.xlsx");
	}

	int addNewSheet( String sheetName, String tableName, XSSFWorkbook workbook,  ArrayList<String> regions, int iRow ) {
		// STEADY-STATE output
		XSSFSheet spreadsheet = workbook.createSheet( sheetName );

		// Create the rows used
		ArrayList< XSSFRow > rowsSS = new ArrayList< XSSFRow >();

		//For styles
		XSSFCellStyle boldStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		boldStyle.setFont(font);  

		XSSFCellStyle noBoldStyle = workbook.createCellStyle();
		XSSFFont noBoldFont = workbook.createFont();
		noBoldFont.setBold(false);
		noBoldStyle.setFont(noBoldFont);  

		regions.clear();
		regions.add("Regional Scale");
		regions.add("Continental Scale");
		regions.add("Global Scale - Moderate climate zone");
		regions.add("Global Scale - Arctic climate zone");
		regions.add("Global Scale - Tropical climate zone");

		ArrayList<String> compartLabels = new ArrayList<String>();
		compartLabels.add("air");
		compartLabels.add("fresh water lakes");
		compartLabels.add("fresh water");
		compartLabels.add("coastal sea water");
		compartLabels.add("fresh water sediment");
		compartLabels.add("coastal marine sediment");
		compartLabels.add("natural soil");
		compartLabels.add("agricultural soil");
		compartLabels.add("other soil");

		ArrayList<String> globalLabels = new ArrayList<String>();
		globalLabels.add("air");
		globalLabels.add("upper ocean water");
		globalLabels.add("deep sea");
		globalLabels.add("ocean sediment");
		globalLabels.add("soil");

		int iRowsNumSS = 49;
		for ( int i = 0; i < iRowsNumSS; i++ )
			rowsSS.add( spreadsheet.createRow( i ) );

		rowsSS.get(0).createCell( 1 ).setCellValue( tableName );
		rowsSS.get(0).getCell(1).setCellStyle( boldStyle );

		//leave a blank row
		rowsSS.get(1).createCell( 0 ).setCellValue(" ");			

		rowsSS.get(2).createCell( 1 ).setCellValue("Model");	
		rowsSS.get(2).getCell( 1 ).setCellStyle( boldStyle );	

		rowsSS.get(2).createCell( 2 ).setCellValue("SimpleBox4nano vs 4.01-nano 44139");			

		rowsSS.get(3).createCell( 1 ).setCellValue( "Substance" );			
		rowsSS.get(3).getCell( 1 ).setCellStyle( boldStyle );

		rowsSS.get(3).createCell( 2 ).setCellValue( nanomaterialName );			

		rowsSS.get(4).createCell( 1 ).setCellValue("Scenario");			
		rowsSS.get(4).getCell( 1 ).setCellStyle( boldStyle );

		rowsSS.get(4).createCell( 2 ).setCellValue( scenarioName );			

		ArrayList<String> transLabels = new ArrayList<String>();
		transLabels.add("Emission");
		transLabels.add("Inflow");
		transLabels.add("Outflow");
		transLabels.add("Removal");
		transLabels.add("Degradation");
		transLabels.add("air-water");
		transLabels.add("water-air");
		transLabels.add("air-soil");
		transLabels.add("soil-air");
		transLabels.add("soil-water");
		transLabels.add("water-sed");
		transLabels.add("sed-water");

		rowsSS.get(8).createCell( 1 ).setCellValue("Species");	
		rowsSS.get(8).getCell( 1 ).setCellStyle( boldStyle );	

		iRow = 10;
		for (String region:regions) {
			rowsSS.get( iRow ).createCell( 1 ).setCellValue( region );
			rowsSS.get( iRow ).getCell( 1).setCellStyle( boldStyle );
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) 
					rowsSS.get( iRow + i ).createCell( 1 ).setCellValue( compartLabels.get( i - 1 ) );

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					rowsSS.get( iRow + i ).createCell( 1 ).setCellValue( globalLabels.get( i - 1 ) );

				iRow += globalLabels.size()+1;
			}
		}

		int iCol = 2;
		for ( String entry:transLabels)
			rowsSS.get( 8 ).createCell( iCol++ ).setCellValue( entry);	


		//Write emissions
		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( emission.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 2 ).setCellValue( emission.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 2 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( emission.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 2 ).setCellValue( emission.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 2 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}

		//Write inflow
		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( inflow.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 3 ).setCellValue( inflow.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 3 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( inflow.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 3 ).setCellValue( inflow.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 3 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}

		//Write outflow
		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( outflow.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 4 ).setCellValue( outflow.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 4 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( outflow.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 4 ).setCellValue( outflow.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 4 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}

		//Write removal
		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( removal.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 5 ).setCellValue( removal.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 5 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( removal.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 5 ).setCellValue( removal.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 5 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}

		//Write formation
		/*		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( formation.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue( formation.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( formation.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue( formation.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}
		 */

		//Write degradation
		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( degradation.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue( degradation.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( degradation.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue( degradation.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 6 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}

		//Write air-water transport
		iCol = 7;
		for ( String trans:transLabels ) {
			if ( transport.get( trans ) == null ) continue;

			iRow = 10;
			for (String region:regions) {
				if ( region == "Regional Scale" || region == "Continental Scale" ) {
					for ( int i= 1; i <= compartLabels.size(); i++ ) {
						if ( transport.get( trans ).get(region).get( compartLabels.get(i - 1) ) != null )
							rowsSS.get( iRow + i ).createCell( iCol ).setCellValue( transport.get( trans ).get(region).get( compartLabels.get(i - 1) ) );
						else
							rowsSS.get( iRow + i ).createCell( iCol ).setCellValue("");
					}
					iRow += compartLabels.size()+1;
				}
				else {
					for ( int i= 1; i <= globalLabels.size(); i++ ) 
						if ( transport.get( trans ).get(region).get( globalLabels.get(i - 1) )  != null )
							rowsSS.get( iRow + i ).createCell( iCol ).setCellValue( transport.get( trans ).get(region).get( globalLabels.get(i - 1) )  );
						else
							rowsSS.get( iRow + i ).createCell( iCol ).setCellValue("");

					iRow += globalLabels.size()+1;
				}
			}

			iCol++;
		}


		return iCol;
	}

	public Map<String, Map<String, Map<String, Double> > > getMasses() { return masses; }
	public Map<String, Map<String, Map<String, Double> > > getConcentrations() { return concentrations; }
	public Map<String, Map<String, Map<String, Double> > > getFugacities() { return fugacities; }
	public Map<String, Map<String, Map<String, Double> > > getTransport() { return transport; }

	public Map<String, Map<String, Double> > getInflow() { return inflow; }
	public Map<String, Map<String, Double> > getOutflow() { return outflow; }
	public Map<String, Map<String, Double> > getRemoval() { return removal; }
	public Map<String, Map<String, Double> > getFormation() { return formation; }
	public Map<String, Map<String, Double> > getDegradation() { return degradation; }
	public Map<String, Map<String, Double> > getEmission() { return emission; }

	public Map<String, Double> getTotalD() { return totalD; }
	public Map<String, Double> getTotalS() { return totalS; }
	public Map<String, Double> getTotalA() { return totalA; }
	public Map<String, Double> getTotalP() { return totalP; }
}



