package eu.nanosolveit.simplebox4nano;

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

import eu.nanosolveit.simplebox4nano.Engine.Engine;
import eu.nanosolveit.simplebox4nano.Engine.InputEngine;
import eu.nanosolveit.simplebox4nano.Engine.RegionalEngine;

public class output extends SelectorComposer<Window> {

	Window win;
	InputEngine input = null;
	RegionalEngine environment = null;
	Engine engine = null;
	Map<String, String> nanoData = null;

	//Names of regions
	private String regPart = "Regional - particulate";
	private String contPart = "Continental - particulate";
	private String globPart = "Global - particulate";
	private String regDis = "Regional - dissolved";
	private String contDis = "Continental - dissolved";
	private String globDis = "Global - dissolved";
	private String nanoSpec = "Nanomaterial PEC summary";

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
		buildFormation();
		buildDegradation();
		buildEmission();
		buildTransport();
		buildTotals();

		//build the tables for the NMs only.
		buildTable1();
		buildTable2();
		buildTable3();
		buildTableTotal();
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

		//build masses table for output
		buildMasses();
		buildConcentrations();
		buildFugacities();

		ListModelList<String> availRegions = new ListModelList<String>();
		availRegions.add( regPart );
		availRegions.add( contPart );
		availRegions.add( globPart );
		availRegions.add( regDis );
		availRegions.add( contDis );
		availRegions.add( globDis );
		availRegions.add( nanoSpec );
		availRegions.addToSelection( availRegions.get(0) );

		//First show the results for regional since the combobox is not auto 
		//Compute the particulate matter to show first
		partBuilder();

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

		airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );	
		freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
		otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );			

		resultsCombo.setModel( availRegions );

		/*		cutOffDiam.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0) throws Exception{ 
				changeRes(); 
			}
		}); */
	}

	private void disBuilder() {
		buildInflow();
		buildOutflow();
		buildRemoval();
		buildFormation();
		buildDegradation();
		buildEmission();
		buildTransport();
		buildTotals();
	}

	private void partBuilder() {
		buildInflowNano();
		buildOutflowNano();
		buildRemovalNano();
		buildFormationNano();
		buildDegradationNano();
		buildEmissionNano();
		buildTransportNano();
		buildTotals();

		//build the tables for the NMs only.
		buildTable1();
		buildTable2();
		buildTable3();
		buildTableTotal();
	}

	void showImageRegional() throws IOException 
	{
		ServletContext c = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();

		BufferedImage bimg = ImageIO.read( c.getResourceAsStream("/resources/ResultsA_v2.png") );

		Font font = new Font("Calibri", Font.BOLD, 20);Graphics2D graphics = bimg.createGraphics();
		graphics.setFont(font);

		graphics.setColor(Color.BLUE);

		graphics.drawString(
				String.format("%.2e", inflow.get("Regional Scale").get("air") ),
				200, 200  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("air") ),
				0, 280  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("fresh water lakes") ),
				0, 700  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("fresh water") ),
				0, 800  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Regional Scale").get("fresh water") ),
				840, 760  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Regional Scale").get("coastal sea water") ),
				0, 860  				
				);

		//green
		graphics.setColor(new Color(0, 128, 0) );
		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("air") ),
				200, 420  				
				);

		graphics.drawString(
				String.format("%.2e", 0.00 ), 1440, 890 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("fresh water") ), 750, 970 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("coastal sea water") ), 220, 970 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("agricultural soil") ), 2740, 1390 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("other soil") ), 2300, 1430 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Regional Scale").get("natural soil") ), 1490, 1480 		
				);

		//red
		graphics.setColor(new Color(227, 30, 36) );
		if ( transport.get("water-air").get("Regional Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Regional Scale").get("coastal sea water") ),
					360, 480  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0),
					360, 480  				
					);

		if ( transport.get("water-air").get("Regional Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Regional Scale").get("fresh water") ),
					850, 480  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 480  				
					);

		if ( transport.get("water-air").get("Regional Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Regional Scale").get("fresh water lakes") ),
					1510, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1510, 410  				
					);

		if ( transport.get("soil-air").get("Regional Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Regional Scale").get("natural soil") ),
					1940, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1940, 410  				
					);


		if ( transport.get("soil-air").get("Regional Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Regional Scale").get("other soil") ),
					2460, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2460, 410  				
					);

		if ( transport.get("soil-air").get("Regional Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Regional Scale").get("agricultural soil") ),
					2800, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2800, 410  				
					);

		if ( transport.get("air-water").get("Regional Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Regional Scale").get("coastal sea water") ),
					360, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					360, 670  				
					);

		if ( transport.get("air-water").get("Regional Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Regional Scale").get("fresh water") ),
					850, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 670  				
					);

		if ( transport.get("air-water").get("Regional Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Regional Scale").get("fresh water lakes") ),
					1510, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1510, 670  				
					);

		if ( transport.get("air-soil").get("Regional Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Regional Scale").get("natural soil") ),
					1940, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1940, 670  				
					);

		if ( transport.get("air-soil").get("Regional Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Regional Scale").get("other soil") ),
					2460, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2460, 670  				
					);

		if ( transport.get("air-soil").get("Regional Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Regional Scale").get("agricultural soil") ),
					2800, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2800, 670  				
					);


		if ( transport.get("sed-water").get("Regional Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Regional Scale").get("fresh water sediment") ),
					850, 920  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 920 				
					);

		if ( transport.get("water-sed").get("Regional Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Regional Scale").get("fresh water sediment") ),
					850, 1100  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 1100  				
					);

		if ( transport.get("sed-water").get("Regional Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Regional Scale").get("coastal marine sediment") ),
					230, 1310  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					230, 1310 				
					);

		if ( transport.get("water-sed").get("Regional Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Regional Scale").get("coastal marine sediment") ),
					230, 1490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					230, 1490  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Regional Scale").get("fresh water") ),
				1150, 660  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("air") ),
				680, 340
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("fresh water") ),
				1120, 830  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("fresh water lakes") ),
				1500, 830  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("fresh water sediment") ),
				1080, 1070  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("natural soil") ),
				1925, 1110  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("other soil") ),
				2370, 1110  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("agricultural soil") ),
				2940, 1110  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("coastal sea water") ),
				475, 1240  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Regional Scale").get("coastal marine sediment") ),
				490, 1470  	  				
				);


		//black
		graphics.setColor( Color.BLACK );

		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				980, 100	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				1100, 970	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				995, 1200	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				475, 1380  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				1925, 1000	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				2370, 1000	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				2940, 1000	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				570, 1584  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)")/totalReg*100 ) + " %",
				1444, 748	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("coastal marine sediment" ) ),
				250, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("fresh water sediment" ) ),
				860, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("natural soil" ) ),
				1810, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("other soil" ) ),
				2450, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("agricultural soil" ) ),
				2970, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Regional Scale").get("air" ) ),
				3165, 30	   	  	  				
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

		Font font = new Font("Calibri", Font.BOLD, 20); 
		Graphics2D graphics = bimg.createGraphics();
		graphics.setFont(font);

		graphics.setColor(Color.BLUE);

		graphics.drawString(
				String.format("%.2e", inflow.get("Continental Scale").get("air") ),
				200, 200  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("air") ),
				0, 280  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("fresh water lakes") ),
				0, 700  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("fresh water") ),
				0, 800  				
				);

		graphics.drawString(
				String.format("%.2e", inflow.get("Continental Scale").get("fresh water") ),
				840, 760  				
				);

		graphics.drawString(
				String.format("%.2e", outflow.get("Continental Scale").get("coastal sea water") ),
				0, 860  				
				);

		//green
		graphics.setColor(new Color(0, 128, 0) );
		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("air") ),
				200, 420  				
				);

		graphics.drawString(
				String.format("%.2e", 0.00 ), 1440, 890 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("fresh water") ), 750, 970 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("coastal sea water") ), 220, 970 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("agricultural soil") ), 2740, 1390 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("other soil") ), 2300, 1430 		
				);

		graphics.drawString(
				String.format("%.2e", emission.get("Continental Scale").get("natural soil") ), 1490, 1480 		
				);

		//red
		graphics.setColor(new Color(227, 30, 36) );
		if ( transport.get("water-air").get("Continental Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Continental Scale").get("coastal sea water") ),
					360, 480  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0),
					360, 480  				
					);

		if ( transport.get("water-air").get("Continental Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Continental Scale").get("fresh water") ),
					850, 480  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 480  				
					);

		if ( transport.get("water-air").get("Continental Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-air").get("Continental Scale").get("fresh water lakes") ),
					1510, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1510, 410  				
					);

		if ( transport.get("soil-air").get("Continental Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Continental Scale").get("natural soil") ),
					1940, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1940, 410  				
					);


		if ( transport.get("soil-air").get("Continental Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Continental Scale").get("other soil") ),
					2460, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2460, 410  				
					);

		if ( transport.get("soil-air").get("Continental Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("soil-air").get("Continental Scale").get("agricultural soil") ),
					2800, 410  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2800, 410  				
					);

		if ( transport.get("air-water").get("Continental Scale").get("coastal sea water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Continental Scale").get("coastal sea water") ),
					360, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					360, 670  				
					);

		if ( transport.get("air-water").get("Continental Scale").get("fresh water") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Continental Scale").get("fresh water") ),
					850, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 670  				
					);

		if ( transport.get("air-water").get("Continental Scale").get("fresh water lakes") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-water").get("Continental Scale").get("fresh water lakes") ),
					1510, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1510, 670  				
					);

		if ( transport.get("air-soil").get("Continental Scale").get("natural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Continental Scale").get("natural soil") ),
					1940, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					1940, 670  				
					);

		if ( transport.get("air-soil").get("Continental Scale").get("other soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Continental Scale").get("other soil") ),
					2460, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2460, 670  				
					);

		if ( transport.get("air-soil").get("Continental Scale").get("agricultural soil") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("air-soil").get("Continental Scale").get("agricultural soil") ),
					2800, 670  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					2800, 670  				
					);


		if ( transport.get("sed-water").get("Continental Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Continental Scale").get("fresh water sediment") ),
					850, 920  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 920 				
					);

		if ( transport.get("water-sed").get("Continental Scale").get("fresh water sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Continental Scale").get("fresh water sediment") ),
					850, 1100  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					850, 1100  				
					);

		if ( transport.get("sed-water").get("Continental Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("sed-water").get("Continental Scale").get("coastal marine sediment") ),
					230, 1310  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					230, 1310 				
					);

		if ( transport.get("water-sed").get("Continental Scale").get("coastal marine sediment") > 0.0 )
			graphics.drawString(
					String.format("%.2e", transport.get("water-sed").get("Continental Scale").get("coastal marine sediment") ),
					230, 1490  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					230, 1490  				
					);

		//orange
		graphics.setColor(new Color(255, 102, 36) );
		graphics.drawString(
				String.format("%.2e", transport.get("soil-water").get("Continental Scale").get("fresh water") ),
				1150, 660  	  				
				);

		//gray
		graphics.setColor( Color.GRAY );
		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("air") ),
				680, 340
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("fresh water") ),
				1120, 830  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("fresh water lakes") ),
				1500, 830  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("fresh water sediment") ),
				1080, 1070  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("natural soil") ),
				1925, 1110  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("other soil") ),
				2370, 1110  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("agricultural soil") ),
				2940, 1110  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("coastal sea water") ),
				475, 1240  	  				
				);

		graphics.drawString(
				String.format("%.2e", degradation.get("Continental Scale").get("coastal marine sediment") ),
				490, 1470  	  				
				);


		//black
		graphics.setColor( Color.BLACK );

		graphics.drawString(
				String.format("%.2f", masses.get("Air").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				980, 100	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				1100, 970	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				995, 1200	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				475, 1380  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				1925, 1000	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				2370, 1000	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				2940, 1000	   	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				570, 1584  	  	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)")/totalCont*100 ) + " %",
				1444, 748	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("coastal marine sediment" ) ),
				250, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("fresh water sediment" ) ),
				860, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("natural soil" ) ),
				1810, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("other soil" ) ),
				2450, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("agricultural soil" ) ),
				2970, 1750	   	  	  				
				);

		graphics.drawString(
				String.format("%.2e", removal.get("Continental Scale").get("air" ) ),
				3165, 30	   	  	  				
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

		if ( environment.getEnvProps("MODERATE","k.w3M.w2M")*engine.getMassMol(97)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("MODERATE","k.w3M.w2M")*engine.getMassMol(97)*input.getSubstancesData("Molweight") ),
					190, 592  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190, 592  	  				
					);

		if (environment.getEnvProps("MODERATE","k.w2M.w3M")*engine.getMassMol(93)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("MODERATE","k.w2M.w3M")*engine.getMassMol(93)*input.getSubstancesData("Molweight") ),
					190, 725  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190, 725  	  				
					);

		if (  environment.getEnvProps("MODERATE","k.sdMD.w3MD")*engine.getMassMol(101)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("MODERATE","k.sdMD.w3MD")*engine.getMassMol(101)*input.getSubstancesData("Molweight") ),
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
				String.format("%.2f", masses.get("Air").get("Mod").get("Dissolved/Gas species (G/D)")/totalMod*100 ) + " %",
				195, 115 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")/totalMod*100 ) + " %",
				425, 555 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")/totalMod*100 ) + " %",
				430, 720 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)")/totalMod*100 ) + " %",
				250, 970 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)")/totalMod*100 ) + " %",
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
		if ( environment.getEnvProps("ARCTIC","k.w2AD.aAG")*engine.getMassMol(116)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.w2AD.aAG")*engine.getMassMol(116)*input.getSubstancesData("Molweight") ),
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

		if ( environment.getEnvProps("ARCTIC","k.w3A.w2A")*engine.getMassMol(120)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.w3A.w2A")*engine.getMassMol(120)*input.getSubstancesData("Molweight") ),
					190 + x, 592  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 592  	  				
					);

		if (environment.getEnvProps("ARCTIC","k.w2A.w3A")*engine.getMassMol(116)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.w2A.w3A")*engine.getMassMol(116)*input.getSubstancesData("Molweight") ),
					190 + x, 725  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 725  	  				
					);

		if (  environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*engine.getMassMol(124)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*engine.getMassMol(124)*input.getSubstancesData("Molweight") ),
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
				String.format("%.2f", masses.get("Air").get("Arct").get("Dissolved/Gas species (G/D)")/totalArct*100 ) + " %",
				195 + x, 115 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")/totalArct*100 ) + " %",
				425 + x, 555 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")/totalArct*100 ) + " %",
				430 + x, 720 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)")/totalArct*100 ) + " %",
				250 + x, 970 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)")/totalArct*100 ) + " %",
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
		if ( environment.getEnvProps("TROPICAL","k.w2TD.aTG")*engine.getMassMol(139)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(					
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.w2TD.aTG")*engine.getMassMol(139)*input.getSubstancesData("Molweight") ),					
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

		if ( environment.getEnvProps("TROPICAL","k.w3T.w2T")*engine.getMassMol(143)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.w3T.w2T")*engine.getMassMol(143)*input.getSubstancesData("Molweight") ),
					190 + x, 592  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 592  	  				
					);

		if (environment.getEnvProps("TROPICAL","k.w2T.w3T")*engine.getMassMol(139)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.w2T.w3T")*engine.getMassMol(139)*input.getSubstancesData("Molweight") ),
					190 + x, 725  	  				
					);
		else
			graphics.drawString(
					String.format("%.2e", 0.0 ),
					190 + x, 725  	  				
					);

		if (  environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*engine.getMassMol(147)*input.getSubstancesData("Molweight") > 0 )
			graphics.drawString(
					String.format("%.2e", environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*engine.getMassMol(147)*input.getSubstancesData("Molweight") ),
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
				String.format("%.2f", masses.get("Air").get("Trop").get("Dissolved/Gas species (G/D)")/totalTrop*100 ) + " %",
				195 + x, 115 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")/totalTrop*100 ) + " %",
				425 + x, 555 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")/totalTrop*100 ) + " %",
				430 + x, 720 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)")/totalTrop*100 ) + " %",
				250 + x, 970 	  				
				);

		graphics.drawString(
				String.format("%.2f", masses.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)")/totalTrop*100 ) + " %",
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


	void buildTable1() 
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		airReg1.setValue( formatter.format(				
				(engine.getMassMol( 1 ) + engine.getMassMol( 4 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )
				) );

		airCon1.setValue(  formatter.format(
				(engine.getMassMol( 44 ) + engine.getMassMol( 47 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )  
				) );

		airMod1.setValue( formatter.format( 
				(engine.getMassMol( 87 ) + engine.getMassMol( 90 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )  
				) );

		airArc1.setValue( formatter.format(
				(engine.getMassMol( 110 ) + engine.getMassMol( 113 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )  
				) );

		airTro1.setValue( formatter.format( 
				(engine.getMassMol( 133 ) + engine.getMassMol( 136 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )  
				) );

		freshWatLReg1.setValue( formatter.format( engine.getConcentration( 8 )*input.getSubstancesData("Molweight")  )
				);

		freshWatLCon1.setValue( formatter.format( engine.getConcentration( 51 )*input.getSubstancesData("Molweight")  )
				);

		freshWatLMod1.setValue(" ");
		freshWatLArc1.setValue(" ");
		freshWatLTro1.setValue(" ");

		freshWatLSReg1.setValue( formatter.format(
				engine.getConcentration( 20 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.sdR") + environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatLSCon1.setValue( formatter.format(
				engine.getConcentration( 63 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatLSMod1.setValue(" ");
		freshWatLSArc1.setValue(" ");
		freshWatLSTro1.setValue(" "); 

		freshWatReg1.setValue( formatter.format( engine.getConcentration( 12 )*input.getSubstancesData("Molweight") ) 
				);

		freshWatCon1.setValue( formatter.format( engine.getConcentration( 55 )*input.getSubstancesData("Molweight") ) 
				);

		freshWatMod1.setValue(" ");
		freshWatArc1.setValue(" ");
		freshWatTro1.setValue(" "); 

		freshWatSedReg1.setValue( formatter.format(
				engine.getConcentration( 24 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.sdR") + environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatSedCon1.setValue( formatter.format(
				engine.getConcentration( 67 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatSedMod1.setValue(" ");
		freshWatSedArc1.setValue(" ");
		freshWatSedTro1.setValue(" "); 

		surfSeaOcReg1.setValue( formatter.format( engine.getConcentration( 16  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcCon1.setValue( formatter.format( engine.getConcentration( 59  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcMod1.setValue( formatter.format( engine.getConcentration( 94 )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcArc1.setValue( formatter.format( engine.getConcentration( 117  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcTro1.setValue( formatter.format( engine.getConcentration( 140  )*input.getSubstancesData("Molweight")  )
				);

		deepSeaOcReg1.setValue(" ");
		deepSeaOcCon1.setValue(" ");
		deepSeaOcMod1.setValue( formatter.format( engine.getConcentration( 98  )*input.getSubstancesData("Molweight")  ) );
		deepSeaOcArc1.setValue( formatter.format( engine.getConcentration( 121  )*input.getSubstancesData("Molweight")  ) );
		deepSeaOcTro1.setValue( formatter.format( engine.getConcentration( 144  )*input.getSubstancesData("Molweight")  ) );

		marSedReg1.setValue( formatter.format(
				engine.getConcentration( 28 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedCon1.setValue( formatter.format(
				engine.getConcentration( 71 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedMod1.setValue( formatter.format(
				engine.getConcentration( 102 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("MODERATE", "FRACw.sdM") + environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedArc1.setValue( formatter.format(
				engine.getConcentration( 125 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("ARCTIC", "FRACw.sdA") + environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedTro1.setValue( formatter.format(
				engine.getConcentration( 148 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("TROPICAL", "FRACw.sdT") + environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		natSoilReg1.setValue( formatter.format(
				engine.getConcentration( 32 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s1R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s1R")-
						environment.getEnvProps("REGIONAL", "FRACw.s1R"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		natSoilCon1.setValue( formatter.format(
				engine.getConcentration( 75 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s1C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s1C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s1C"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		natSoilMod1.setValue(" ");
		natSoilArc1.setValue(" ");
		natSoilTro1.setValue(" ");

		agriSoilReg1.setValue( formatter.format(
				engine.getConcentration( 36 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s2R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s2R")-
						environment.getEnvProps("REGIONAL", "FRACw.s2R"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		agriSoilCon1.setValue( formatter.format(
				engine.getConcentration( 79 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s2C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s2C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s2C"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		agriSoilMod1.setValue(" ");
		agriSoilArc1.setValue(" ");
		agriSoilTro1.setValue(" ");		


		otherSoilReg1.setValue( formatter.format(
				engine.getConcentration( 40 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s3R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s3R")-
						environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		otherSoilCom1.setValue( formatter.format(
				engine.getConcentration( 83 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s3C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s3C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilMod1.setValue( formatter.format(
				engine.getConcentration( 106 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("MODERATE", "FRACw.sM") + (1. -environment.getEnvProps("MODERATE", "FRACa.sM")-
						environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilArc1.setValue( formatter.format(
				engine.getConcentration( 129 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("ARCTIC", "FRACw.sA") + (1. -environment.getEnvProps("ARCTIC", "FRACa.sA")-
						environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilTro1.setValue( formatter.format(
				engine.getConcentration( 152 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("TROPICAL", "FRACw.sT") + (1. -environment.getEnvProps("TROPICAL", "FRACa.sT")-
						environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );		

	}

	void buildTable2() 
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		airReg2.setValue( formatter.format(				
				(engine.getMassMol( 2 ) + engine.getMassMol( 5 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )
				) );

		airCon2.setValue(  formatter.format(
				(engine.getMassMol( 45 ) + engine.getMassMol( 48 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )  
				) );

		airMod2.setValue( formatter.format( 
				(engine.getMassMol( 88 ) + engine.getMassMol( 91 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )  
				) );

		airArc2.setValue( formatter.format(
				(engine.getMassMol( 111 ) + engine.getMassMol( 114 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )  
				) );

		airTro2.setValue( formatter.format( 
				(engine.getMassMol( 134 ) + engine.getMassMol( 137 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )  
				) );

		freshWatLReg2.setValue( formatter.format( engine.getConcentration( 9 )*input.getSubstancesData("Molweight")  )
				);

		freshWatLCon2.setValue( formatter.format( engine.getConcentration( 51 )*input.getSubstancesData("Molweight")  )
				);

		freshWatLMod2.setValue(" ");
		freshWatLArc2.setValue(" ");
		freshWatLTro2.setValue(" ");

		freshWatLSReg2.setValue( formatter.format(
				engine.getConcentration( 21 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.sdR") + environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  

				) );

		freshWatLSCon2.setValue( formatter.format(
				engine.getConcentration( 64 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatLSMod2.setValue(" ");
		freshWatLSArc2.setValue(" ");
		freshWatLSTro2.setValue(" "); 

		freshWatReg2.setValue( formatter.format( engine.getConcentration( 13 )*input.getSubstancesData("Molweight") ) 
				);

		freshWatCon2.setValue( formatter.format( engine.getConcentration( 56 )*input.getSubstancesData("Molweight") ) 
				);

		freshWatMod2.setValue(" ");
		freshWatArc2.setValue(" ");
		freshWatTro2.setValue(" "); 

		freshWatSedReg2.setValue( formatter.format(
				engine.getConcentration( 25 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.sdR") + environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatSedCon2.setValue( formatter.format(
				engine.getConcentration( 68 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatSedMod2.setValue(" ");
		freshWatSedArc2.setValue(" ");
		freshWatSedTro2.setValue(" "); 

		surfSeaOcReg2.setValue( formatter.format( engine.getConcentration( 17  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcCon2.setValue( formatter.format( engine.getConcentration( 60  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcMod2.setValue( formatter.format( engine.getConcentration( 95 )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcArc2.setValue( formatter.format( engine.getConcentration( 118  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcTro2.setValue( formatter.format( engine.getConcentration( 141  )*input.getSubstancesData("Molweight")  )
				);

		deepSeaOcReg2.setValue(" ");
		deepSeaOcCon2.setValue(" ");
		deepSeaOcMod2.setValue( formatter.format( engine.getConcentration( 99  )*input.getSubstancesData("Molweight")  ) );
		deepSeaOcArc2.setValue( formatter.format( engine.getConcentration( 122  )*input.getSubstancesData("Molweight")  ) );
		deepSeaOcTro2.setValue( formatter.format( engine.getConcentration( 145  )*input.getSubstancesData("Molweight")  ) );

		marSedReg2.setValue( formatter.format(
				engine.getConcentration( 29 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedCon2.setValue( formatter.format(
				engine.getConcentration( 72 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedMod2.setValue( formatter.format(
				engine.getConcentration( 103 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("MODERATE", "FRACw.sdM") + environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedArc2.setValue( formatter.format(
				engine.getConcentration( 126 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("ARCTIC", "FRACw.sdA") + environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedTro2.setValue( formatter.format(
				engine.getConcentration( 149 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("TROPICAL", "FRACw.sdT") + environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		natSoilReg2.setValue( formatter.format(
				engine.getConcentration( 33 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s1R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s1R")-
						environment.getEnvProps("REGIONAL", "FRACw.s1R"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		natSoilCon2.setValue( formatter.format(
				engine.getConcentration( 76 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s1C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s1C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s1C"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		natSoilMod2.setValue(" ");
		natSoilArc2.setValue(" ");
		natSoilTro2.setValue(" ");

		agriSoilReg2.setValue( formatter.format(
				engine.getConcentration( 37 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s2R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s2R")-
						environment.getEnvProps("REGIONAL", "FRACw.s2R"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		agriSoilCon2.setValue( formatter.format(
				engine.getConcentration( 80 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s2C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s2C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s2C"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		agriSoilMod2.setValue(" ");
		agriSoilArc2.setValue(" ");
		agriSoilTro2.setValue(" ");		


		otherSoilReg2.setValue( formatter.format(
				engine.getConcentration( 41 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s3R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s3R")-
						environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		otherSoilCom2.setValue( formatter.format(
				engine.getConcentration( 84 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s3C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s3C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilMod2.setValue( formatter.format(
				engine.getConcentration( 107 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("MODERATE", "FRACw.sM") + (1. -environment.getEnvProps("MODERATE", "FRACa.sM")-
						environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilArc2.setValue( formatter.format(
				engine.getConcentration( 130 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("ARCTIC", "FRACw.sA") + (1. -environment.getEnvProps("ARCTIC", "FRACa.sA")-
						environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilTro2.setValue( formatter.format(
				engine.getConcentration( 153 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("TROPICAL", "FRACw.sT") + (1. -environment.getEnvProps("TROPICAL", "FRACa.sT")-
						environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );
	}

	void buildTable3() 
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		airReg3.setValue( formatter.format(				
				(engine.getMassMol( 3 ) + engine.getMassMol( 6 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )
				) );

		airCon3.setValue(  formatter.format(
				(engine.getMassMol( 46 ) + engine.getMassMol( 49 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )  
				) );

		airMod3.setValue( formatter.format( 
				(engine.getMassMol( 89 ) + engine.getMassMol( 92 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )  
				) );

		airArc3.setValue( formatter.format(
				(engine.getMassMol( 112 ) + engine.getMassMol( 115 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )  
				) );

		airTro3.setValue( formatter.format( 
				(engine.getMassMol( 135 ) + engine.getMassMol( 138 ))*(input.getSubstancesData("Molweight")*1000. )/
				(environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )  
				) );

		freshWatLReg3.setValue( formatter.format( engine.getConcentration( 10 )*input.getSubstancesData("Molweight")  )
				);

		freshWatLCon3.setValue( formatter.format( engine.getConcentration( 52 )*input.getSubstancesData("Molweight")  )
				);

		freshWatLMod3.setValue(" ");
		freshWatLArc3.setValue(" ");
		freshWatLTro3.setValue(" ");

		freshWatLSReg3.setValue( formatter.format(
				engine.getConcentration( 22 )*(input.getSubstancesData("Molweight")*1000. )/ 
				( environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000. + environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		freshWatLSCon3.setValue( formatter.format(
				engine.getConcentration( 65 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatLSMod3.setValue(" ");
		freshWatLSArc3.setValue(" ");
		freshWatLSTro3.setValue(" "); 

		freshWatReg3.setValue( formatter.format( engine.getConcentration( 14 )*input.getSubstancesData("Molweight") ) 
				);

		freshWatCon3.setValue( formatter.format( engine.getConcentration( 57 )*input.getSubstancesData("Molweight") ) 
				);

		freshWatMod3.setValue(" ");
		freshWatArc3.setValue(" ");
		freshWatTro3.setValue(" "); 

		freshWatSedReg3.setValue( formatter.format(
				engine.getConcentration( 26 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.sdR") + environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatSedCon3.setValue( formatter.format(
				engine.getConcentration( 69 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		freshWatSedMod3.setValue(" ");
		freshWatSedArc3.setValue(" ");
		freshWatSedTro3.setValue(" "); 

		surfSeaOcReg3.setValue( formatter.format( engine.getConcentration( 18  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcCon3.setValue( formatter.format( engine.getConcentration( 61  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcMod3.setValue( formatter.format( engine.getConcentration( 96 )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcArc3.setValue( formatter.format( engine.getConcentration( 119  )*input.getSubstancesData("Molweight")  )
				);

		surfSeaOcTro3.setValue( formatter.format( engine.getConcentration( 142  )*input.getSubstancesData("Molweight")  )
				);

		deepSeaOcReg3.setValue(" ");
		deepSeaOcCon3.setValue(" ");
		deepSeaOcMod3.setValue( formatter.format( engine.getConcentration( 100 )*input.getSubstancesData("Molweight")  ) );
		deepSeaOcArc3.setValue( formatter.format( engine.getConcentration( 123  )*input.getSubstancesData("Molweight")  ) );
		deepSeaOcTro3.setValue( formatter.format( engine.getConcentration( 146  )*input.getSubstancesData("Molweight")  ) );

		marSedReg3.setValue( formatter.format(
				engine.getConcentration( 30 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedCon3.setValue( formatter.format(
				engine.getConcentration( 73 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.sdC") + environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedMod3.setValue( formatter.format(
				engine.getConcentration( 104 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("MODERATE", "FRACw.sdM") + environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedArc3.setValue( formatter.format(
				engine.getConcentration( 127 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("ARCTIC", "FRACw.sdA") + environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		marSedTro3.setValue( formatter.format(
				engine.getConcentration( 150 )*(input.getSubstancesData("Molweight")*1000. )/
				(1000.*environment.getEnvProps("TROPICAL", "FRACw.sdT") + environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )  
				) );

		natSoilReg3.setValue( formatter.format(
				engine.getConcentration( 34 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s1R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s1R")-
						environment.getEnvProps("REGIONAL", "FRACw.s1R"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		natSoilCon3.setValue( formatter.format(
				engine.getConcentration( 77 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s1C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s1C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s1C"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		natSoilMod3.setValue(" ");
		natSoilArc3.setValue(" ");
		natSoilTro3.setValue(" ");

		agriSoilReg3.setValue( formatter.format(
				engine.getConcentration( 38 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s2R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s2R")-
						environment.getEnvProps("REGIONAL", "FRACw.s2R"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		agriSoilCon3.setValue( formatter.format(
				engine.getConcentration( 81 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s2C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s2C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s2C"))*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		agriSoilMod3.setValue(" ");
		agriSoilArc3.setValue(" ");
		agriSoilTro3.setValue(" ");		


		otherSoilReg3.setValue( formatter.format(
				engine.getConcentration( 42 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("REGIONAL", "FRACw.s3R") + (1. -environment.getEnvProps("REGIONAL", "FRACa.s3R")-
						environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") ) 
				) );

		otherSoilCom3.setValue( formatter.format(
				engine.getConcentration( 85 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("CONTINENTAL", "FRACw.s3C") + (1. -environment.getEnvProps("CONTINENTAL", "FRACa.s3C")-
						environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilMod3.setValue( formatter.format(
				engine.getConcentration( 108 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("MODERATE", "FRACw.sM") + (1. -environment.getEnvProps("MODERATE", "FRACa.sM")-
						environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilArc3.setValue( formatter.format(
				engine.getConcentration( 131 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("ARCTIC", "FRACw.sA") + (1. -environment.getEnvProps("ARCTIC", "FRACa.sA")-
						environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );

		otherSoilTro3.setValue( formatter.format(
				engine.getConcentration( 154 )*(input.getSubstancesData("Molweight")*1000. )/ 
				(1000.*environment.getEnvProps("TROPICAL", "FRACw.sT") + (1. -environment.getEnvProps("TROPICAL", "FRACa.sT")-
						environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				) );
	}

	void buildTableTotal() 
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		airRegT.setValue( formatter.format(
				Double.valueOf( airReg1.getValue() ) + Double.valueOf( airReg2.getValue() ) + Double.valueOf( airReg3.getValue() ) 
				) );

		airConT.setValue(  formatter.format(
				Double.valueOf( airCon1.getValue() ) + Double.valueOf( airCon2.getValue() ) + Double.valueOf( airCon3.getValue() ) 
				) );

		airModT.setValue( formatter.format( 
				Double.valueOf( airMod1.getValue() ) + Double.valueOf( airMod2.getValue() ) + Double.valueOf( airMod3.getValue() ) 
				) );

		airArcT.setValue( formatter.format(
				Double.valueOf( airArc1.getValue() ) + Double.valueOf( airArc2.getValue() ) + Double.valueOf( airArc3.getValue() ) 
				) );

		airTroT.setValue( formatter.format( 
				Double.valueOf( airTro1.getValue() ) + Double.valueOf( airTro2.getValue() ) + Double.valueOf( airTro3.getValue() ) 
				) );

		freshWatLRegT.setValue( formatter.format( 
				Double.valueOf( freshWatLReg1.getValue() ) + Double.valueOf( freshWatLReg2.getValue() ) + Double.valueOf( freshWatLReg3.getValue() ) 
				) );

		freshWatLConT.setValue( formatter.format( 
				Double.valueOf( freshWatLCon1.getValue() ) + Double.valueOf( freshWatLCon2.getValue() ) + Double.valueOf( freshWatLCon3.getValue() ) 
				) );

		freshWatLModT.setValue(" ");
		freshWatLArcT.setValue(" ");
		freshWatLTroT.setValue(" ");

		freshWatLSRegT.setValue( formatter.format(
				Double.valueOf( freshWatLSReg1.getValue() ) + Double.valueOf( freshWatLSReg2.getValue() ) + Double.valueOf( freshWatLSReg3.getValue() ) 
				) );

		freshWatLSConT.setValue( formatter.format(
				Double.valueOf( freshWatLSCon1.getValue() ) + Double.valueOf( freshWatLSCon2.getValue() ) + Double.valueOf( freshWatLSCon3.getValue() ) 
				) );

		freshWatLSModT.setValue(" ");
		freshWatLSArcT.setValue(" ");
		freshWatLSTroT.setValue(" "); 

		freshWatRegT.setValue( formatter.format( 
				Double.valueOf( freshWatReg1.getValue() ) + Double.valueOf( freshWatReg2.getValue() ) + Double.valueOf( freshWatReg3.getValue() ) 
				) );

		freshWatConT.setValue( formatter.format( 
				Double.valueOf( freshWatCon1.getValue() ) + Double.valueOf( freshWatCon2.getValue() ) + Double.valueOf( freshWatCon3.getValue() ) 
				) );

		freshWatModT.setValue(" ");
		freshWatArcT.setValue(" ");
		freshWatTroT.setValue(" "); 

		freshWatSedRegT.setValue( formatter.format(
				Double.valueOf( freshWatSedReg1.getValue() ) + Double.valueOf( freshWatSedReg2.getValue() ) + Double.valueOf( freshWatSedReg3.getValue() ) 
				) );

		freshWatSedConT.setValue( formatter.format(
				Double.valueOf( freshWatSedCon1.getValue() ) + Double.valueOf( freshWatSedCon2.getValue() ) + Double.valueOf( freshWatSedCon3.getValue() ) 
				) );

		freshWatSedModT.setValue(" ");
		freshWatSedArcT.setValue(" ");
		freshWatSedTroT.setValue(" "); 

		surfSeaOcRegT.setValue( formatter.format( 
				Double.valueOf( surfSeaOcReg1.getValue() ) + Double.valueOf( surfSeaOcReg2.getValue() ) + Double.valueOf( surfSeaOcReg3.getValue() ) 
				) );

		surfSeaOcConT.setValue( formatter.format( 
				Double.valueOf( surfSeaOcCon1.getValue() ) + Double.valueOf( surfSeaOcCon2.getValue() ) + Double.valueOf( surfSeaOcCon3.getValue() ) 
				) );

		surfSeaOcModT.setValue( formatter.format( 
				Double.valueOf( surfSeaOcMod1.getValue() ) + Double.valueOf( surfSeaOcMod2.getValue() ) + Double.valueOf( surfSeaOcMod3.getValue() ) 
				) );

		surfSeaOcArcT.setValue( formatter.format( 
				Double.valueOf( surfSeaOcArc1.getValue() ) + Double.valueOf( surfSeaOcArc2.getValue() ) + Double.valueOf( surfSeaOcArc3.getValue() ) 
				) );

		surfSeaOcTroT.setValue( formatter.format( 
				Double.valueOf( surfSeaOcTro1.getValue() ) + Double.valueOf( surfSeaOcTro2.getValue() ) + Double.valueOf( surfSeaOcTro3.getValue() ) 				
				) );

		deepSeaOcRegT.setValue(" ");
		deepSeaOcConT.setValue(" ");

		deepSeaOcModT.setValue( formatter.format( 
				Double.valueOf( deepSeaOcMod1.getValue() ) + Double.valueOf( deepSeaOcMod2.getValue() ) + Double.valueOf( deepSeaOcMod3.getValue() ) 				
				) );

		deepSeaOcArcT.setValue( formatter.format( 
				Double.valueOf( deepSeaOcArc1.getValue() ) + Double.valueOf( deepSeaOcArc2.getValue() ) + Double.valueOf( deepSeaOcArc3.getValue() ) 				
				) );

		deepSeaOcTroT.setValue( formatter.format( 
				Double.valueOf( deepSeaOcTro1.getValue() ) + Double.valueOf( deepSeaOcTro2.getValue() ) + Double.valueOf( deepSeaOcTro3.getValue() ) 				
				) );

		marSedRegT.setValue( formatter.format(
				Double.valueOf( marSedReg1.getValue() ) + Double.valueOf( marSedReg2.getValue() ) + Double.valueOf( marSedReg3.getValue() ) 				
				) );

		marSedConT.setValue( formatter.format(
				Double.valueOf( marSedCon1.getValue() ) + Double.valueOf( marSedCon2.getValue() ) + Double.valueOf( marSedCon3.getValue() ) 				
				) );

		marSedModT.setValue( formatter.format(
				Double.valueOf( marSedMod1.getValue() ) + Double.valueOf( marSedMod2.getValue() ) + Double.valueOf( marSedMod3.getValue() ) 				
				) );

		marSedArcT.setValue( formatter.format(
				Double.valueOf( marSedArc1.getValue() ) + Double.valueOf( marSedArc2.getValue() ) + Double.valueOf( marSedArc3.getValue() ) 				
				) );

		marSedTroT.setValue( formatter.format(
				Double.valueOf( marSedTro1.getValue() ) + Double.valueOf( marSedTro2.getValue() ) + Double.valueOf( marSedTro3.getValue() ) 				
				) );

		natSoilRegT.setValue( formatter.format(
				Double.valueOf( natSoilReg1.getValue() ) + Double.valueOf( natSoilReg2.getValue() ) + Double.valueOf( natSoilReg3.getValue() ) 				
				) );

		natSoilConT.setValue( formatter.format(
				Double.valueOf( natSoilCon1.getValue() ) + Double.valueOf( natSoilCon2.getValue() ) + Double.valueOf( natSoilCon3.getValue() ) 				
				) );

		natSoilModT.setValue(" ");
		natSoilArcT.setValue(" ");
		natSoilTroT.setValue(" ");

		agriSoilRegT.setValue( formatter.format(
				Double.valueOf( agriSoilReg1.getValue() ) + Double.valueOf( agriSoilReg2.getValue() ) + Double.valueOf( agriSoilReg3.getValue() ) 				
				) );

		agriSoilConT.setValue( formatter.format(
				Double.valueOf( agriSoilCon1.getValue() ) + Double.valueOf( agriSoilCon2.getValue() ) + Double.valueOf( agriSoilCon3.getValue() ) 				
				) );

		agriSoilModT.setValue(" ");
		agriSoilArcT.setValue(" ");
		agriSoilTroT.setValue(" ");		

		otherSoilRegT.setValue( formatter.format(
				Double.valueOf( otherSoilReg1.getValue() ) + Double.valueOf( otherSoilReg2.getValue() ) + Double.valueOf( otherSoilReg3.getValue() ) 				
				) );

		otherSoilComT.setValue( formatter.format(
				Double.valueOf( otherSoilCom1.getValue() ) + Double.valueOf( otherSoilCom2.getValue() ) + Double.valueOf( otherSoilCom3.getValue() ) 				
				) );

		otherSoilModT.setValue( formatter.format(
				Double.valueOf( otherSoilMod1.getValue() ) + Double.valueOf( otherSoilMod2.getValue() ) + Double.valueOf( otherSoilMod3.getValue() ) 				
				) );

		otherSoilArcT.setValue( formatter.format(
				Double.valueOf( otherSoilArc1.getValue() ) + Double.valueOf( otherSoilArc2.getValue() ) + Double.valueOf( otherSoilArc3.getValue() ) 				
				) );

		otherSoilTroT.setValue( formatter.format(
				Double.valueOf( otherSoilTro1.getValue() ) + Double.valueOf( otherSoilTro2.getValue() ) + Double.valueOf( otherSoilTro3.getValue() ) 				
				) );
	}

	//	@Listen("onChange=#cutOffDiam")
	void changeRes()
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		if ( 2*input.getNanoProperties("RadP.a") < cutOffDiam.getValue()*0.000000001 ) {	
			airRegT.setValue( formatter.format( Double.valueOf( airReg1.getValue() + airReg2.getValue() + airReg3.getValue() ) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				airRegT.setValue( String.valueOf( formatter.format(
						Double.valueOf( airReg1.getValue() ) + Double.valueOf( airReg2.getValue() ) 
						) ) );		

			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				airRegT.setValue( formatter.format( Double.valueOf( airReg1.getValue() ) ) );		
			}		
			else
				airRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.a") < cutOffDiam.getValue()*0.000000001 ) {	
			airConT.setValue(  formatter.format( airCon1.getValue() + airCon2.getValue() + airCon3.getValue() ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				airConT.setValue(  String.valueOf( formatter.format(
						Double.valueOf(  airCon1.getValue() ) + Double.valueOf( airCon2.getValue() ) 
						) ) );		

			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				airConT.setValue(  formatter.format( Double.valueOf( airCon1.getValue() ) ) );		
			}		
			else
				airConT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.a") < cutOffDiam.getValue()*0.000000001 ) {	
			airModT.setValue( formatter.format( Double.valueOf( airMod1.getValue() + airMod2.getValue() + airMod3.getValue() ) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				airModT.setValue( String.valueOf( formatter.format(
						Double.valueOf( airMod1.getValue() ) + Double.valueOf( airMod2.getValue() ) 
						) ) );		

			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				airModT.setValue( formatter.format( Double.valueOf( airMod1.getValue() ) ) );		
			}		
			else
				airModT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.a") < cutOffDiam.getValue()*0.000000001 ) {	
			airArcT.setValue( formatter.format( Double.valueOf( airArc1.getValue() + airArc2.getValue() + airArc3.getValue() ) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				airArcT.setValue( String.valueOf( formatter.format( 
						Double.valueOf( airArc1.getValue() ) + Double.valueOf( airArc2.getValue() ) 
						) ) );		

			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				airArcT.setValue( formatter.format( Double.valueOf( airArc1.getValue() ) ) );		
			}		
			else
				airArcT.setValue("0.0");		
		}

		if ( 2*input.getNanoProperties("RadP.a") < cutOffDiam.getValue()*0.000000001 ) {	
			airTroT.setValue( formatter.format( Double.valueOf( airTro1.getValue() + airTro2.getValue() + airTro3.getValue() ) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				airTroT.setValue( String.valueOf( formatter.format(
						Double.valueOf( airTro1.getValue() ) + Double.valueOf( airTro2.getValue() ) 
						) ) );		

			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				airTroT.setValue( formatter.format( Double.valueOf( airTro1.getValue() ) ) );		
			}		
			else
				airTroT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatLRegT.setValue( formatter.format( Double.valueOf( freshWatLReg1.getValue() +  freshWatLReg2.getValue() +
					freshWatLReg3.getValue()) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatLRegT.setValue( String.valueOf( formatter.format( 
						Double.valueOf( freshWatLReg1.getValue() ) + Double.valueOf( freshWatLReg2.getValue() ) 
						) ) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatLRegT.setValue( formatter.format( Double.valueOf( freshWatLReg1.getValue() ) ) );		
			}		
			else
				freshWatLRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatLConT.setValue(  formatter.format( Double.valueOf( freshWatLCon1.getValue() + freshWatLCon2.getValue() +
					freshWatLCon3.getValue()) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatLConT.setValue(  String.valueOf(
						Double.valueOf( freshWatLCon1.getValue() ) + Double.valueOf( freshWatLCon2.getValue() ) 
						) );		

			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatLConT.setValue( formatter.format( Double.valueOf( freshWatLCon1.getValue() ) ) );		
			}		
			else
				freshWatLConT.setValue( "0.0" );		
		}

		freshWatLModT.setValue(" ");
		freshWatLArcT.setValue(" ");
		freshWatLTroT.setValue(" ");

		if ( 2.*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatLSRegT.setValue( formatter.format( Double.valueOf(  freshWatLSReg1.getValue() + freshWatLSReg2.getValue() +
					freshWatLSReg3.getValue()) ) );	
		}
		else {
			if ( 2.*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatLSRegT.setValue(  String.valueOf(
						Double.valueOf( freshWatLSReg1.getValue() ) + Double.valueOf( freshWatLSReg2.getValue() ) 
						) );		

			}
			else if  ( 2.*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatLSRegT.setValue( formatter.format( Double.valueOf( freshWatLSReg1.getValue() ) ) );		
			}		
			else
				freshWatLSRegT.setValue("0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatLSConT.setValue( formatter.format( Double.valueOf( freshWatLSConT.getValue() ) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatLSConT.setValue( String.valueOf(
						Double.valueOf(  freshWatLSCon1.getValue() ) + Double.valueOf( freshWatLSCon2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatLSConT.setValue( formatter.format( Double.valueOf( freshWatLSCon1.getValue() ) ) );		
			}		
			else
				freshWatLSConT.setValue("0.0");		
		}

		freshWatLSModT.setValue(" ");
		freshWatLSArcT.setValue(" ");
		freshWatLSTroT.setValue(" "); 

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatRegT.setValue( formatter.format( Double.valueOf( freshWatReg1.getValue() + freshWatReg2.getValue() + 
					freshWatReg3.getValue()) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatRegT.setValue( String.valueOf(
						Double.valueOf(  freshWatReg1.getValue() ) + Double.valueOf( freshWatReg2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatRegT.setValue( formatter.format( Double.valueOf( freshWatReg1.getValue() ) ) );		
			}		
			else
				freshWatRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatConT.setValue( formatter.format( Double.valueOf( freshWatCon1.getValue() + freshWatCon2.getValue() + 
					freshWatCon3.getValue()) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatConT.setValue( String.valueOf(
						Double.valueOf( freshWatCon1.getValue() ) + Double.valueOf( freshWatCon2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatConT.setValue( formatter.format( Double.valueOf( freshWatCon1.getValue() )) );		
			}		
			else
				freshWatConT.setValue("0.0");		
		}

		freshWatModT.setValue(" ");
		freshWatArcT.setValue(" ");
		freshWatTroT.setValue(" "); 

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatSedRegT.setValue(formatter.format( Double.valueOf( freshWatSedReg1.getValue() + freshWatCon2.getValue() + 
					freshWatCon3.getValue()) ));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatSedRegT.setValue( String.valueOf(
						Double.valueOf( freshWatSedReg1.getValue() ) + Double.valueOf( freshWatSedReg2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatSedRegT.setValue(  formatter.format( Double.valueOf(freshWatSedReg1.getValue() )) );		
			}		
			else
				freshWatSedRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			freshWatSedConT.setValue( formatter.format( Double.valueOf( freshWatSedCon1.getValue() + freshWatSedCon2.getValue() +
					freshWatSedCon3.getValue())));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				freshWatSedConT.setValue(  String.valueOf(
						Double.valueOf( freshWatSedCon1.getValue() ) + Double.valueOf( freshWatSedCon2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				freshWatSedConT.setValue( formatter.format( Double.valueOf( freshWatSedCon1.getValue() ) ));		
			}		
			else
				freshWatSedConT.setValue( "0.0" );		
		}

		freshWatSedModT.setValue(" ");
		freshWatSedArcT.setValue(" ");
		freshWatSedTroT.setValue(" "); 

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			surfSeaOcRegT.setValue( formatter.format( Double.valueOf( surfSeaOcReg1.getValue() + surfSeaOcReg2.getValue() +
					surfSeaOcReg3.getValue())));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				surfSeaOcRegT.setValue( String.valueOf(
						Double.valueOf( surfSeaOcReg1.getValue() ) + Double.valueOf( surfSeaOcReg2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				surfSeaOcRegT.setValue( formatter.format( Double.valueOf( surfSeaOcReg1.getValue() )) );		
			}		
			else
				surfSeaOcRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			surfSeaOcConT.setValue( formatter.format( Double.valueOf( surfSeaOcCon1.getValue() + surfSeaOcCon2.getValue() +
					surfSeaOcCon3.getValue() )) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				surfSeaOcConT.setValue(  String.valueOf(
						Double.valueOf( surfSeaOcCon1.getValue() ) + Double.valueOf( surfSeaOcCon2.getValue() ) 
						)  );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				surfSeaOcConT.setValue( formatter.format( Double.valueOf( surfSeaOcCon1.getValue() )) );		
			}		
			else
				surfSeaOcConT.setValue( "0.0");		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			surfSeaOcModT.setValue( formatter.format( Double.valueOf(surfSeaOcMod1.getValue() + surfSeaOcMod2.getValue() +
					surfSeaOcMod3.getValue() )) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				surfSeaOcModT.setValue( String.valueOf(
						Double.valueOf( surfSeaOcMod1.getValue() ) + Double.valueOf( surfSeaOcMod2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				surfSeaOcModT.setValue( formatter.format( Double.valueOf( surfSeaOcMod1.getValue() )) );		
			}		
			else
				surfSeaOcModT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			surfSeaOcArcT.setValue( formatter.format( Double.valueOf(surfSeaOcArc1.getValue() +surfSeaOcArc2.getValue() +
					surfSeaOcArc3.getValue() )) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				surfSeaOcArcT.setValue( String.valueOf(
						Double.valueOf( surfSeaOcArc1.getValue() ) + Double.valueOf( surfSeaOcArc2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				surfSeaOcArcT.setValue( formatter.format( Double.valueOf(surfSeaOcArc1.getValue() )) );		
			}		
			else
				surfSeaOcArcT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			surfSeaOcTroT.setValue( formatter.format( Double.valueOf( surfSeaOcTro1.getValue() + surfSeaOcTro2.getValue() + 
					surfSeaOcTro3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				surfSeaOcTroT.setValue( String.valueOf(
						Double.valueOf( surfSeaOcTro1.getValue() ) + Double.valueOf( surfSeaOcTro2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				surfSeaOcTroT.setValue(formatter.format( Double.valueOf( surfSeaOcTro1.getValue() )) );		
			}		
			else
				surfSeaOcTroT.setValue( "0.0" );		
		}

		deepSeaOcRegT.setValue(" ");
		deepSeaOcConT.setValue(" ");

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			deepSeaOcModT.setValue( formatter.format( Double.valueOf( deepSeaOcMod1.getValue() + deepSeaOcMod2.getValue() +
					deepSeaOcMod3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				deepSeaOcModT.setValue( String.valueOf(
						Double.valueOf( deepSeaOcMod1.getValue() ) + Double.valueOf( deepSeaOcMod2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				deepSeaOcModT.setValue( formatter.format( Double.valueOf(deepSeaOcMod1.getValue())) );		
			}		
			else
				deepSeaOcModT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			deepSeaOcArcT.setValue(formatter.format( Double.valueOf( deepSeaOcArc1.getValue() + deepSeaOcArc2.getValue() +
					deepSeaOcArc3.getValue() )) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				deepSeaOcArcT.setValue( String.valueOf(
						Double.valueOf( deepSeaOcArc1.getValue() ) + Double.valueOf( deepSeaOcArc2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				deepSeaOcArcT.setValue( formatter.format( Double.valueOf(deepSeaOcArc1.getValue() )) );		
			}		
			else
				deepSeaOcArcT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			deepSeaOcTroT.setValue( formatter.format( Double.valueOf( deepSeaOcTro1.getValue() + deepSeaOcTro2.getValue() + 
					deepSeaOcTro3.getValue() )));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				deepSeaOcTroT.setValue( String.valueOf(
						Double.valueOf( deepSeaOcTro1.getValue() ) + Double.valueOf( deepSeaOcTro2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				deepSeaOcTroT.setValue( formatter.format( Double.valueOf(deepSeaOcTro1.getValue() )) );		
			}		
			else
				deepSeaOcTroT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			marSedRegT.setValue( formatter.format( Double.valueOf( marSedReg1.getValue() + marSedReg2.getValue() +
					marSedReg3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				marSedRegT.setValue( String.valueOf(
						Double.valueOf( marSedReg1.getValue() ) + Double.valueOf( marSedReg2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				marSedRegT.setValue( formatter.format( Double.valueOf(marSedReg1.getValue() )) );		
			}		
			else
				marSedRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			marSedConT.setValue( formatter.format( Double.valueOf( marSedCon1.getValue() + marSedCon2.getValue() + 
					marSedCon3.getValue() )) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				marSedConT.setValue( String.valueOf(
						Double.valueOf( marSedCon1.getValue() ) + Double.valueOf( marSedCon2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				marSedConT.setValue( formatter.format( Double.valueOf(marSedCon1.getValue() ) ) );		
			}		
			else
				marSedConT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			marSedModT.setValue( formatter.format( Double.valueOf( marSedMod1.getValue() + marSedMod2.getValue() +
					marSedMod3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				marSedModT.setValue( String.valueOf(
						Double.valueOf( marSedMod1.getValue() ) + Double.valueOf( marSedMod2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				marSedModT.setValue( formatter.format( Double.valueOf(marSedMod1.getValue() )) );		
			}		
			else
				marSedModT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			marSedArcT.setValue( formatter.format( Double.valueOf( marSedArc1.getValue() + marSedArc2.getValue() +
					marSedArc3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				marSedArcT.setValue( String.valueOf(
						Double.valueOf( marSedArc1.getValue() ) + Double.valueOf( marSedArc2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				marSedArcT.setValue( formatter.format( Double.valueOf(marSedArc1.getValue() )) );		
			}		
			else
				marSedArcT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			marSedTroT.setValue( formatter.format( Double.valueOf( marSedTro1.getValue() + marSedTro2.getValue() + 
					marSedTro3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				marSedTroT.setValue( String.valueOf(
						Double.valueOf( marSedTro1.getValue() ) + Double.valueOf( marSedTro2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				marSedTroT.setValue( formatter.format( Double.valueOf(marSedTro1.getValue() )) );		
			}		
			else
				marSedTroT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			natSoilRegT.setValue( formatter.format( Double.valueOf( natSoilReg1.getValue() + natSoilReg2.getValue() + 
					natSoilReg3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				natSoilRegT.setValue( String.valueOf(
						Double.valueOf( natSoilReg1.getValue() ) + Double.valueOf( natSoilReg2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				natSoilRegT.setValue( formatter.format( Double.valueOf(natSoilReg1.getValue() ) ) );		
			}		
			else
				natSoilRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			natSoilConT.setValue( formatter.format( Double.valueOf( natSoilCon1.getValue() + natSoilCon2.getValue() + 
					natSoilCon3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				natSoilConT.setValue( String.valueOf(
						Double.valueOf( natSoilCon1.getValue() ) + Double.valueOf( natSoilCon2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				natSoilConT.setValue( formatter.format( Double.valueOf(natSoilCon1.getValue() )) );		
			}		
			else
				natSoilConT.setValue( "0.0" );		
		}

		natSoilModT.setValue(" ");
		natSoilArcT.setValue(" ");
		natSoilTroT.setValue(" ");

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			agriSoilRegT.setValue( formatter.format( Double.valueOf( agriSoilReg1.getValue() + agriSoilReg2.getValue() +
					agriSoilReg3.getValue())));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				agriSoilRegT.setValue( String.valueOf(
						Double.valueOf( agriSoilReg1.getValue() ) + Double.valueOf( agriSoilReg2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				agriSoilRegT.setValue( formatter.format( Double.valueOf(agriSoilReg1.getValue() )) );		
			}		
			else
				agriSoilRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			agriSoilConT.setValue( formatter.format( Double.valueOf( agriSoilCon1.getValue() + agriSoilCon2.getValue() + 
					agriSoilCon3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				agriSoilConT.setValue( String.valueOf(
						Double.valueOf( agriSoilCon1.getValue() ) + Double.valueOf( agriSoilCon2.getValue() ) 
						) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				agriSoilConT.setValue( formatter.format( Double.valueOf(agriSoilCon1.getValue() )) );		
			}		
			else
				agriSoilConT.setValue( "0.0" );		
		}

		agriSoilModT.setValue(" ");
		agriSoilArcT.setValue(" ");
		agriSoilTroT.setValue(" ");		

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			otherSoilRegT.setValue( formatter.format( Double.valueOf( otherSoilReg1.getValue() + 
					otherSoilReg2.getValue() + otherSoilReg3.getValue()  )) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				otherSoilRegT.setValue( String.valueOf( formatter.format( 
						Double.valueOf( otherSoilReg1.getValue() ) + Double.valueOf( otherSoilReg2.getValue() ) 
						) ) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				otherSoilRegT.setValue( formatter.format( Double.valueOf( otherSoilReg1.getValue() )) );		
			}		
			else
				otherSoilRegT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			otherSoilComT.setValue( formatter.format( Double.valueOf( otherSoilCom1.getValue() + otherSoilCom2.getValue() + 
					otherSoilCom3.getValue())) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				otherSoilComT.setValue( String.valueOf( formatter.format(
						Double.valueOf( otherSoilCom1.getValue() ) + Double.valueOf( otherSoilCom2.getValue() ) 
						) ) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				otherSoilComT.setValue( formatter.format( Double.valueOf( otherSoilCom1.getValue() )) );		
			}		
			else
				otherSoilComT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			otherSoilModT.setValue( formatter.format( Double.valueOf( otherSoilMod1.getValue() + otherSoilMod2.getValue() + 
					otherSoilMod3.getValue() ) ) );	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				otherSoilModT.setValue( String.valueOf( formatter.format(
						Double.valueOf( otherSoilMod1.getValue() ) + Double.valueOf( otherSoilMod2.getValue() ) 
						) ) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				otherSoilModT.setValue( formatter.format( Double.valueOf( otherSoilMod1.getValue() ) ) );		
			}		
			else
				otherSoilModT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			otherSoilArcT.setValue( formatter.format( Double.valueOf( otherSoilArc1.getValue()+
					otherSoilArc2.getValue() + otherSoilArc3.getValue() ) ));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				otherSoilArcT.setValue( String.valueOf( formatter.format(
						Double.valueOf( otherSoilArc1.getValue() ) + Double.valueOf( otherSoilArc2.getValue() ) 
						) ) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				otherSoilArcT.setValue( formatter.format( Double.valueOf(  otherSoilArc1.getValue() ) ) );		
			}		
			else
				otherSoilArcT.setValue( "0.0" );		
		}

		if ( 2*input.getNanoProperties("RadP.w") < cutOffDiam.getValue()*0.000000001 ) {	
			otherSoilTroT.setValue( formatter.format( Double.valueOf(  otherSoilTro1.getValue()  + otherSoilTro2.getValue() +
					otherSoilTro3.getValue() )));	
		}
		else {
			if ( 2*input.getNanoProperties("RadA.w") < cutOffDiam.getValue()*0.000000001 ) 	{
				otherSoilTroT.setValue( String.valueOf( formatter.format(
						Double.valueOf( otherSoilTro1.getValue() ) + Double.valueOf( otherSoilTro2.getValue() ) 
						) ) );		
			}
			else if  ( 2*input.getSubstancesData("RadS") < cutOffDiam.getValue()*0.000000001 ) {				
				otherSoilTroT.setValue( formatter.format( Double.valueOf( otherSoilTro1.getValue() ))) ;		
			}		
			else
				otherSoilTroT.setValue( "0.0" );		
		}
	}



	void buildMasses() 
	{
		masses.put("Air", new HashMap< String, Map< String, Double > >() );		
		masses.get("Air").put("Reg", new HashMap<String, Double>() );
		masses.get("Air").put("Cont", new HashMap<String, Double>() );
		masses.get("Air").put("Mod", new HashMap<String, Double>() );
		masses.get("Air").put("Arct", new HashMap<String, Double>() );
		masses.get("Air").put("Trop", new HashMap<String, Double>() );

		masses.get("Air").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(0)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Reg").put("* GAS PHASE", environment.getEnvProps("REGIONAL", "FRgas.aR")*masses.get("Air").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Air").get("Reg").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("REGIONAL", "FRgas.aR") )*masses.get("Air").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Air").get("Reg").put("Solid species (S)", (engine.getMassMol(1) + engine.getMassMol(4) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Reg").put("Species attached to NCs (<450 nm) (A)", (engine.getMassMol(2) + engine.getMassMol(5) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", (engine.getMassMol(3) + engine.getMassMol(6) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Reg").put("Air", masses.get("Air").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Air").get("Reg").get("Solid species (S)") +
				masses.get("Air").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Air").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Air").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(43)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Cont").put("* GAS PHASE", environment.getEnvProps("CONTINENTAL", "FRgas.aC")*masses.get("Air").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Air").get("Cont").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("CONTINENTAL", "FRgas.aC") )*
				masses.get("Air").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Air").get("Cont").put("Solid species (S)", (engine.getMassMol(44) + engine.getMassMol(47) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Cont").put("Species attached to NCs (<450 nm) (A)", (engine.getMassMol(45) + engine.getMassMol(48) )*
				input.getSubstancesData("Molweight") );
		masses.get("Air").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", (engine.getMassMol(46) + 
				engine.getMassMol(49) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Cont").put("Air", masses.get("Air").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Air").get("Cont").get("Solid species (S)") +
				masses.get("Air").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Air").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Air").get("Mod").put("Dissolved/Gas species (G/D)", engine.getMassMol(86)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Mod").put("* GAS PHASE", environment.getEnvProps("CONTINENTAL", "FRgas.aC")*
				masses.get("Air").get("Mod").get("Dissolved/Gas species (G/D)") );
		masses.get("Air").get("Mod").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("CONTINENTAL", "FRgas.aC") )*
				masses.get("Air").get("Mod").get("Dissolved/Gas species (G/D)"));
		masses.get("Air").get("Mod").put("Solid species (S)", (engine.getMassMol(87) + engine.getMassMol(90) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Mod").put("Species attached to NCs (<450 nm) (A)", (engine.getMassMol(88) + engine.getMassMol(91) )*
				input.getSubstancesData("Molweight") );
		masses.get("Air").get("Mod").put("Species attached to suspended particles (>450 nm) (P)", (engine.getMassMol(89) + 
				engine.getMassMol(92) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Mod").put("Air",  masses.get("Air").get("Mod").get("Dissolved/Gas species (G/D)") +
				masses.get("Air").get("Mod").get("Solid species (S)") +
				masses.get("Air").get("Mod").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Air").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Air").get("Arct").put("Dissolved/Gas species (G/D)", engine.getMassMol(109)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Arct").put("* GAS PHASE", environment.getEnvProps("CONTINENTAL", "FRgas.aC")*
				masses.get("Air").get("Arct").get("Dissolved/Gas species (G/D)") );
		masses.get("Air").get("Arct").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("CONTINENTAL", "FRgas.aC") )*
				masses.get("Air").get("Arct").get("Dissolved/Gas species (G/D)"));
		masses.get("Air").get("Arct").put("Solid species (S)", (engine.getMassMol(110) + engine.getMassMol(113) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Arct").put("Species attached to NCs (<450 nm) (A)", (engine.getMassMol(111) + engine.getMassMol(114) )*
				input.getSubstancesData("Molweight") );
		masses.get("Air").get("Arct").put("Species attached to suspended particles (>450 nm) (P)", (engine.getMassMol(112) + 
				engine.getMassMol(115) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Arct").put("Air",  
				masses.get("Air").get("Arct").get("Dissolved/Gas species (G/D)") +
				masses.get("Air").get("Arct").get("Solid species (S)") +
				masses.get("Air").get("Arct").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Air").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Air").get("Trop").put("Dissolved/Gas species (G/D)", engine.getMassMol(132)*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Trop").put("* GAS PHASE", environment.getEnvProps("CONTINENTAL", "FRgas.aC")*
				masses.get("Air").get("Trop").get("Dissolved/Gas species (G/D)") );
		masses.get("Air").get("Trop").put("* AEROSOL- and CLOUD PHASES", (1.-environment.getEnvProps("CONTINENTAL", "FRgas.aC") )*
				masses.get("Air").get("Trop").get("Dissolved/Gas species (G/D)"));
		masses.get("Air").get("Trop").put("Solid species (S)", (engine.getMassMol(133) + engine.getMassMol(136) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Trop").put("Species attached to NCs (<450 nm) (A)", (engine.getMassMol(134) + engine.getMassMol(137) )*
				input.getSubstancesData("Molweight") );
		masses.get("Air").get("Trop").put("Species attached to suspended particles (>450 nm) (P)", (engine.getMassMol(135) + 
				engine.getMassMol(138) )*input.getSubstancesData("Molweight") );
		masses.get("Air").get("Trop").put("Air",   
				masses.get("Air").get("Trop").get("Dissolved/Gas species (G/D)") +
				masses.get("Air").get("Trop").get("Solid species (S)") +
				masses.get("Air").get("Trop").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Air").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Fresh water lake", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water lake").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(7)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Reg").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w0R")*masses.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water lake").get("Reg").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("REGIONAL", "FRwD.w0R") )*masses.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water lake").get("Reg").put("Solid species (S)", engine.getMassMol(8)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(9)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(10)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Reg").put("Fresh water lake", 
				masses.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)") +  
				masses.get("Fresh water lake").get("Reg").get("Solid species (S)") +
				masses.get("Fresh water lake").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water lake").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);


		masses.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water lake").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(50)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Cont").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w0R")*
				masses.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water lake").get("Cont").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("REGIONAL", "FRwD.w0R") )*
				masses.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water lake").get("Cont").put("Solid species (S)", engine.getMassMol(51)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(52)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(53)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake").get("Cont").put("Fresh water lake",  
				masses.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water lake").get("Cont").get("Solid species (S)") +
				masses.get("Fresh water lake").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water lake").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Fresh water lake sediment", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water lake sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water lake sediment").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water lake sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water lake sediment").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(19)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Reg").put("* PORE WATER", environment.getEnvProps("REGIONAL", "FRACw.sdR")*
				masses.get("Fresh water lake sediment").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water lake sediment").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRACs.sdR")*
				masses.get("Fresh water lake sediment").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water lake sediment").get("Reg").put("Solid species (S)", engine.getMassMol(20)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(21)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(22)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Reg").put("Fresh water lake sediment",  
				masses.get("Fresh water lake sediment").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water lake sediment").get("Reg").get("Solid species (S)") +
				masses.get("Fresh water lake sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water lake sediment").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Fresh water lake sediment").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water lake sediment").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(62)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Cont").put("* PORE WATER", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Fresh water lake sediment").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water lake sediment").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
				masses.get("Fresh water lake sediment").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water lake sediment").get("Cont").put("Solid species (S)", engine.getMassMol(63)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(64)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(65)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water lake sediment").get("Cont").put("Fresh water lake sediment",  
				masses.get("Fresh water lake sediment").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water lake sediment").get("Cont").get("Solid species (S)") +
				masses.get("Fresh water lake sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water lake sediment").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Fresh water", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(11)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Reg").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w1R")*
				masses.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water").get("Reg").put("* SUSPENDED SOLIDS", (1. -  environment.getEnvProps("REGIONAL", "FRwD.w1R") )*
				masses.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water").get("Reg").put("Solid species (S)", engine.getMassMol(12)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(13)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(14)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Reg").put("Fresh water",  
				masses.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water").get("Reg").get("Solid species (S)") +
				masses.get("Fresh water").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Fresh water").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(54)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Cont").put("* DISSOLVED", environment.getEnvProps("CONTINENTAL", "FRw.w1C")*
				masses.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water").get("Cont").put("* SUSPENDED SOLIDS", (1. - environment.getEnvProps("CONTINENTAL", "FRw.w1C"))*
				masses.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water").get("Cont").put("Solid species (S)", engine.getMassMol(55)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(56)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(57)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water").get("Cont").put("Fresh water",  
				masses.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water").get("Cont").get("Solid species (S)") +
				masses.get("Fresh water").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Fresh water sediment", new HashMap< String, Map< String, Double > >() );		
		masses.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );

		masses.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Fresh water sediment").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(23)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Reg").put("* PORE WATER", environment.getEnvProps("REGIONAL", "FRACw.sdR")*
				masses.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water sediment").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRACs.sdR")*
				masses.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water sediment").get("Reg").put("Solid species (S)", engine.getMassMol(24)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(25)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(26)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Reg").put("Fresh water sediment",  
				masses.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water sediment").get("Reg").get("Solid species (S)") +
				masses.get("Fresh water sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water sediment").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );
		masses.get("Fresh water sediment").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(66)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Cont").put("* PORE WATER", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Fresh water sediment").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
				masses.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Fresh water sediment").get("Cont").put("Solid species (S)", engine.getMassMol(67)*input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(68)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(69)*
				input.getSubstancesData("Molweight") );
		masses.get("Fresh water sediment").get("Cont").put("Fresh water sediment",  
				masses.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water sediment").get("Cont").get("Solid species (S)") +
				masses.get("Fresh water sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water sediment").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Surface sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		masses.get("Surface sea/ocean water").put("Reg", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Cont", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Mod", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Arct", new HashMap<String, Double>() );
		masses.get("Surface sea/ocean water").put("Trop", new HashMap<String, Double>() );

		masses.get("Surface sea/ocean water").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(15)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Reg").put("* DISSOLVED", environment.getEnvProps("REGIONAL", "FRwD.w2R")*
				masses.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Surface sea/ocean water").get("Reg").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("REGIONAL", "FRwD.w2R") )*
				masses.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Surface sea/ocean water").get("Reg").put("Solid species (S)", engine.getMassMol(16)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(17)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(18)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Reg").put("Surface sea/ocean water",  
				masses.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Surface sea/ocean water").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(58)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Cont").put("* DISSOLVED", environment.getEnvProps("CONTINENTAL", "FRw.w2C")*
				masses.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Surface sea/ocean water").get("Cont").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("CONTINENTAL", "FRw.w2C") )*
				masses.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Surface sea/ocean water").get("Cont").put("Solid species (S)", engine.getMassMol(59)
				*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(60)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(61)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Cont").put("Surface sea/ocean water",  
				masses.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Surface sea/ocean water").get("Mod").put("Dissolved/Gas species (G/D)", engine.getMassMol(93)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Mod").put("* DISSOLVED", environment.getEnvProps("MODERATE", "FRw.w2M")*
				masses.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") );
		masses.get("Surface sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("MODERATE", "FRw.w2M") )*
				masses.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)"));
		masses.get("Surface sea/ocean water").get("Mod").put("Solid species (S)", engine.getMassMol(94)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Mod").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(95)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Mod").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(96)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Mod").put("Surface sea/ocean water", 
				masses.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Mod").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Mod").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Surface sea/ocean water").get("Arct").put("Dissolved/Gas species (G/D)", engine.getMassMol(116)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Arct").put("* DISSOLVED", environment.getEnvProps("ARCTIC", "FRw.w2A")*
				masses.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") );
		masses.get("Surface sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("ARCTIC", "FRw.w2A") )*
				masses.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)"));
		masses.get("Surface sea/ocean water").get("Arct").put("Solid species (S)", engine.getMassMol(117)*input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Arct").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(118)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Arct").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(119)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Arct").put("Surface sea/ocean water",  
				masses.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Arct").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Arct").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Surface sea/ocean water").get("Trop").put("Dissolved/Gas species (G/D)", engine.getMassMol(139)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Trop").put("* DISSOLVED", environment.getEnvProps("TROPICAL", "FRw.w2T")*
				masses.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") );
		masses.get("Surface sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("TROPICAL", "FRw.w2T") )*
				masses.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)"));
		masses.get("Surface sea/ocean water").get("Trop").put("Solid species (S)", engine.getMassMol(140)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Trop").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(141)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Trop").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(142)*
				input.getSubstancesData("Molweight") );
		masses.get("Surface sea/ocean water").get("Trop").put("Surface sea/ocean water",  
				masses.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Trop").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Trop").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Deep sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		masses.get("Deep sea/ocean water").put("Mod", new HashMap<String, Double>() );
		masses.get("Deep sea/ocean water").put("Arct", new HashMap<String, Double>() );
		masses.get("Deep sea/ocean water").put("Trop", new HashMap<String, Double>() );


		masses.get("Deep sea/ocean water").get("Mod").put("Dissolved/Gas species (G/D)", engine.getMassMol(97)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Mod").put("* DISSOLVED", environment.getEnvProps("MODERATE", "FRw.w3M")*
				masses.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") );
		masses.get("Deep sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("MODERATE", "FRw.w3M") )*
				masses.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)"));
		masses.get("Deep sea/ocean water").get("Mod").put("Solid species (S)", engine.getMassMol(98)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Mod").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(99)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Mod").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(100)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Mod").put("Deep sea/ocean water", 
				masses.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") +
				masses.get("Deep sea/ocean water").get("Mod").get("Solid species (S)") +
				masses.get("Deep sea/ocean water").get("Mod").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Deep sea/ocean water").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Deep sea/ocean water").get("Arct").put("Dissolved/Gas species (G/D)", engine.getMassMol(120)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Arct").put("* DISSOLVED", environment.getEnvProps("ARCTIC", "FRw.w3A")*
				masses.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") );
		masses.get("Deep sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("ARCTIC", "FRw.w3A") )*
				masses.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)"));
		masses.get("Deep sea/ocean water").get("Arct").put("Solid species (S)", engine.getMassMol(121)*input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Arct").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(122)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Arct").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(123)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Arct").put("Deep sea/ocean water",  
				masses.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") +
				masses.get("Deep sea/ocean water").get("Arct").get("Solid species (S)") +
				masses.get("Deep sea/ocean water").get("Arct").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Deep sea/ocean water").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Deep sea/ocean water").get("Trop").put("Dissolved/Gas species (G/D)", engine.getMassMol(143)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Trop").put("* DISSOLVED", environment.getEnvProps("TROPICAL", "FRw.w3T")*
				masses.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") );
		masses.get("Deep sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS", (1.-environment.getEnvProps("TROPICAL", "FRw.w3T") )*
				masses.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)"));
		masses.get("Deep sea/ocean water").get("Trop").put("Solid species (S)", engine.getMassMol(144)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Trop").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(145)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Trop").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(146)*
				input.getSubstancesData("Molweight") );
		masses.get("Deep sea/ocean water").get("Trop").put("Deep sea/ocean water",  
				masses.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") +
				masses.get("Deep sea/ocean water").get("Trop").get("Solid species (S)") +
				masses.get("Deep sea/ocean water").get("Trop").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Deep sea/ocean water").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Marine sediment", new HashMap< String, Map< String, Double > >() );		
		masses.get("Marine sediment").put("Reg", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Cont", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Mod", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Arct", new HashMap<String, Double>() );
		masses.get("Marine sediment").put("Trop", new HashMap<String, Double>() );

		masses.get("Marine sediment").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(27)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Reg").put("* PORE WATER", environment.getEnvProps("REGIONAL", "FRACw.sdR")*
				masses.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Marine sediment").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRACs.sdR")*
				masses.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Marine sediment").get("Reg").put("Solid species (S)", engine.getMassMol(28)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(29)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(30)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Reg").put("Marine sediment",  
				masses.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Marine sediment").get("Reg").get("Solid species (S)") +
				masses.get("Marine sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Marine sediment").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Marine sediment").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(70)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Cont").put("* PORE WATER", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Marine sediment").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*
				masses.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Marine sediment").get("Cont").put("Solid species (S)", engine.getMassMol(71)
				*input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(72)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(73)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Cont").put("Marine sediment",  
				masses.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Marine sediment").get("Cont").get("Solid species (S)") +
				masses.get("Marine sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Marine sediment").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Marine sediment").get("Mod").put("Dissolved/Gas species (G/D)", engine.getMassMol(101)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Mod").put("* PORE WATER", environment.getEnvProps("MODERATE", "FRACw.sdM")*
				masses.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)") );
		masses.get("Marine sediment").get("Mod").put("* SOLID PHASE", (1.-environment.getEnvProps("MODERATE", "FRACw.sdM") )*
				masses.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)"));
		masses.get("Marine sediment").get("Mod").put("Solid species (S)", engine.getMassMol(102)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Mod").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(103)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Mod").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(104)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Mod").put("Marine sediment", 
				masses.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)") +
				masses.get("Marine sediment").get("Mod").get("Solid species (S)") +
				masses.get("Marine sediment").get("Mod").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Marine sediment").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Marine sediment").get("Arct").put("Dissolved/Gas species (G/D)", engine.getMassMol(124)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Arct").put("* PORE WATER", environment.getEnvProps("ARCTIC", "FRACw.sdA")*
				masses.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)") );
		masses.get("Marine sediment").get("Arct").put("* SOLID PHASE", (1.-environment.getEnvProps("ARCTIC", "FRACw.sdA") )*
				masses.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)"));
		masses.get("Marine sediment").get("Arct").put("Solid species (S)", engine.getMassMol(125)*input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Arct").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(126)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Arct").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(127)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Arct").put("Marine sediment",  
				masses.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)") +
				masses.get("Marine sediment").get("Arct").get("Solid species (S)") +
				masses.get("Marine sediment").get("Arct").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Marine sediment").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Marine sediment").get("Trop").put("Dissolved/Gas species (G/D)", engine.getMassMol(147)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Trop").put("* PORE WATER", environment.getEnvProps("TROPICAL", "FRACw.sdT")*
				masses.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)") );
		masses.get("Marine sediment").get("Trop").put("* SOLID PHASE", (1.-environment.getEnvProps("TROPICAL", "FRACw.sdT") )*
				masses.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)"));
		masses.get("Marine sediment").get("Trop").put("Solid species (S)", engine.getMassMol(148)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Trop").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(149)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Trop").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(150)*
				input.getSubstancesData("Molweight") );
		masses.get("Marine sediment").get("Trop").put("Marine sediment",  
				masses.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)") +
				masses.get("Marine sediment").get("Trop").get("Solid species (S)") +
				masses.get("Marine sediment").get("Trop").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Marine sediment").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Natural soil", new HashMap< String, Map< String, Double > >() );		
		masses.get("Natural soil").put("Reg", new HashMap<String, Double>() );
		masses.get("Natural soil").put("Cont", new HashMap<String, Double>() );

		masses.get("Natural soil").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(31)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Reg").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("REGIONAL", "FRwD.s1R")*
				masses.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Natural soil").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRs.s1R")*
				masses.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Natural soil").get("Reg").put("Solid species (S)", engine.getMassMol(32)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(33)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(34)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Reg").put("Natural soil",
				masses.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Natural soil").get("Reg").get("Solid species (S)") +
				masses.get("Natural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Natural soil").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Natural soil").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(74)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Cont").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("CONTINENTAL", "FRw.s1C")*
				masses.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Natural soil").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				masses.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Natural soil").get("Cont").put("Solid species (S)", engine.getMassMol(75)
				*input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(76)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(77)*
				input.getSubstancesData("Molweight") );
		masses.get("Natural soil").get("Cont").put("Natural soil",  				
				masses.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Natural soil").get("Cont").get("Solid species (S)") +
				masses.get("Natural soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Natural soil").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Agricultural soil", new HashMap< String, Map< String, Double > >() );		
		masses.get("Agricultural soil").put("Reg", new HashMap<String, Double>() );
		masses.get("Agricultural soil").put("Cont", new HashMap<String, Double>() );

		masses.get("Agricultural soil").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(35)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Reg").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("REGIONAL", "FRwD.s1R")*
				masses.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Agricultural soil").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRs.s1R")*
				masses.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Agricultural soil").get("Reg").put("Solid species (S)", engine.getMassMol(36)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(37)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(38)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Reg").put("Agricultural soil",  
				masses.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Agricultural soil").get("Reg").get("Solid species (S)") +
				masses.get("Agricultural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Agricultural soil").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Agricultural soil").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(78)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Cont").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("CONTINENTAL", "FRw.s1C")*
				masses.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Agricultural soil").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				masses.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Agricultural soil").get("Cont").put("Solid species (S)", engine.getMassMol(79)
				*input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(80)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(81)*
				input.getSubstancesData("Molweight") );
		masses.get("Agricultural soil").get("Cont").put("Agricultural soil",  
				masses.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)") +				
				masses.get("Agricultural soil").get("Cont").get("Solid species (S)") +
				masses.get("Agricultural soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Agricultural soil").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.put("Other soil", new HashMap< String, Map< String, Double > >() );		
		masses.get("Other soil").put("Reg", new HashMap<String, Double>() );
		masses.get("Other soil").put("Cont", new HashMap<String, Double>() );
		masses.get("Other soil").put("Mod", new HashMap<String, Double>() );
		masses.get("Other soil").put("Arct", new HashMap<String, Double>() );
		masses.get("Other soil").put("Trop", new HashMap<String, Double>() );

		masses.get("Other soil").get("Reg").put("Dissolved/Gas species (G/D)", engine.getMassMol(39)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Reg").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("REGIONAL", "FRwD.s3R")*
				masses.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)") );
		masses.get("Other soil").get("Reg").put("* SOLID PHASE", environment.getEnvProps("REGIONAL", "FRs.s3R")*
				masses.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)"));
		masses.get("Other soil").get("Reg").put("Solid species (S)", engine.getMassMol(40)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Reg").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(41)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Reg").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(42)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Reg").put("Other soil",  
				masses.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Reg").get("Solid species (S)") +
				masses.get("Other soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Other soil").get("Cont").put("Dissolved/Gas species (G/D)", engine.getMassMol(82)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Cont").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("CONTINENTAL", "FRw.s3C")*
				masses.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)") );
		masses.get("Other soil").get("Cont").put("* SOLID PHASE", environment.getEnvProps("CONTINENTAL", "FRs.s3C")*
				masses.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)"));
		masses.get("Other soil").get("Cont").put("Solid species (S)", engine.getMassMol(83)
				*input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Cont").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(84)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Cont").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(85)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Cont").put("Other soil",  
				masses.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Cont").get("Solid species (S)") +
				masses.get("Other soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Other soil").get("Mod").put("Dissolved/Gas species (G/D)", engine.getMassMol(105)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Mod").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("MODERATE", "FRw.sM")*
				masses.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)") );
		masses.get("Other soil").get("Mod").put("* SOLID PHASE", (1.-environment.getEnvProps("MODERATE", "FRw.sM") )*
				masses.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)"));
		masses.get("Other soil").get("Mod").put("Solid species (S)", engine.getMassMol(106)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Mod").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(107)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Mod").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(108)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Mod").put("Other soil", 
				masses.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Mod").get("Solid species (S)") +
				masses.get("Other soil").get("Mod").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Other soil").get("Arct").put("Dissolved/Gas species (G/D)", engine.getMassMol(128)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Arct").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("ARCTIC", "FRw.sA")*
				masses.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)") );
		masses.get("Other soil").get("Arct").put("* SOLID PHASE", (1.-environment.getEnvProps("ARCTIC", "FRw.sA") )*
				masses.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)"));
		masses.get("Other soil").get("Arct").put("Solid species (S)", engine.getMassMol(129)*input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Arct").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(130)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Arct").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(131)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Arct").put("Other soil",  
				masses.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Arct").get("Solid species (S)") +
				masses.get("Other soil").get("Arct").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		masses.get("Other soil").get("Trop").put("Dissolved/Gas species (G/D)", engine.getMassMol(151)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Trop").put("* PORE WATER / GROUNDWATER", environment.getEnvProps("TROPICAL", "FRw.sT")*
				masses.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)") );
		masses.get("Other soil").get("Trop").put("* SOLID PHASE", (1.-environment.getEnvProps("TROPICAL", "FRw.sT") )*
				masses.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)"));
		masses.get("Other soil").get("Trop").put("Solid species (S)", engine.getMassMol(152)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Trop").put("Species attached to NCs (<450 nm) (A)", engine.getMassMol(153)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Trop").put("Species attached to suspended particles (>450 nm) (P)", engine.getMassMol(154)*
				input.getSubstancesData("Molweight") );
		masses.get("Other soil").get("Trop").put("Other soil",  
				masses.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Trop").get("Solid species (S)") +
				masses.get("Other soil").get("Trop").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") 
				);
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
				( engine.getMassMol(0) +
						engine.getMassMol(1) +
						engine.getMassMol(2) +
						engine.getMassMol(3) +				
						engine.getMassMol(4) +
						engine.getMassMol(5) +
						engine.getMassMol(6) +
						engine.getMassMol(7) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )			
				);

		concentrations.get("Air").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getMassMol(0)*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )			
				);

		concentrations.get("Air").get("Reg").put("* GAS PHASE",
				environment.getEnvProps("REGIONAL","FRgas.aR")* concentrations.get("Air").get("Reg").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Reg").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("REGIONAL","FRgas.aR"))* concentrations.get("Air").get("Reg").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Reg").put("Solid species (S)",
				( engine.getMassMol(1) +
						engine.getMassMol(4) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )			
				);

		concentrations.get("Air").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(2) +
						engine.getMassMol(5) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )			
				);

		concentrations.get("Air").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(3) +
						engine.getMassMol(6) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("REGIONAL", "VOLUME.aR") + environment.getEnvProps("REGIONAL", "VOLUME.cwR") )			
				);

		concentrations.get("Air").get("Cont").put("Air",
				(engine.getMassMol(43) +
						engine.getMassMol(44) +
						engine.getMassMol(45) +
						engine.getMassMol(46) +				
						engine.getMassMol(47) +
						engine.getMassMol(48) +
						engine.getMassMol(49) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )			
				);

		concentrations.get("Air").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getMassMol(43)*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )			
				);

		concentrations.get("Air").get("Cont").put("* GAS PHASE",
				environment.getEnvProps("CONTINENTAL","FRgas.aC")* concentrations.get("Air").get("Cont").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Cont").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("CONTINENTAL","FRgas.aC"))* concentrations.get("Air").get("Cont").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Cont").put("Solid species (S)",
				( engine.getMassMol(44) +
						engine.getMassMol(47) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )		
				);

		concentrations.get("Air").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(45) +
						engine.getMassMol(48) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )		
				);

		concentrations.get("Air").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(46) +
						engine.getMassMol(49) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("CONTINENTAL", "VOLUME.aC") + environment.getEnvProps("CONTINENTAL", "VOLUME.cwC") )		
				);

		concentrations.get("Air").get("Mod").put("Air",
				(engine.getMassMol(86) +
						engine.getMassMol(87) +
						engine.getMassMol(88) +
						engine.getMassMol(89) +				
						engine.getMassMol(90) +
						engine.getMassMol(91) +
						engine.getMassMol(92) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )			
				);

		concentrations.get("Air").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getMassMol(86)*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )			
				);

		concentrations.get("Air").get("Mod").put("* GAS PHASE",
				environment.getEnvProps("MODERATE","FRgas.aM")* concentrations.get("Air").get("Mod").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Mod").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("MODERATE","FRgas.aM"))* concentrations.get("Air").get("Mod").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Mod").put("Solid species (S)",
				( engine.getMassMol(87) +
						engine.getMassMol(90) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )			
				);

		concentrations.get("Air").get("Mod").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(88) +
						engine.getMassMol(91) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )			
				);

		concentrations.get("Air").get("Mod").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(89) +
						engine.getMassMol(92) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("MODERATE", "VOLUME.aM") + environment.getEnvProps("MODERATE", "VOLUME.cwM") )			
				);

		concentrations.get("Air").get("Arct").put("Air",
				(engine.getMassMol(109) +
						engine.getMassMol(110) +
						engine.getMassMol(111) +
						engine.getMassMol(112) +				
						engine.getMassMol(113) +
						engine.getMassMol(114) +
						engine.getMassMol(115) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )			
				);

		concentrations.get("Air").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getMassMol(109)*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )			
				);

		concentrations.get("Air").get("Arct").put("* GAS PHASE",
				environment.getEnvProps("ARCTIC","FRgas.aA")* concentrations.get("Air").get("Arct").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Arct").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("ARCTIC","FRgas.aA"))* concentrations.get("Air").get("Arct").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Arct").put("Solid species (S)",
				( engine.getMassMol(110) +
						engine.getMassMol(113) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )			
				);

		concentrations.get("Air").get("Arct").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(111) +
						engine.getMassMol(114) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )			
				);

		concentrations.get("Air").get("Arct").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(112) +
						engine.getMassMol(115) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("ARCTIC", "VOLUME.aA") + environment.getEnvProps("ARCTIC", "VOLUME.cwA") )			
				);

		concentrations.get("Air").get("Trop").put("Air",
				(engine.getMassMol(132) +
						engine.getMassMol(133) +
						engine.getMassMol(134) +
						engine.getMassMol(135) +				
						engine.getMassMol(136) +
						engine.getMassMol(137) +
						engine.getMassMol(138) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )			
				);

		concentrations.get("Air").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getMassMol(132)*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )			
				);

		concentrations.get("Air").get("Trop").put("* GAS PHASE",
				environment.getEnvProps("TROPICAL","FRgas.aT")* concentrations.get("Air").get("Trop").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Trop").put("* AEROSOL- and CLOUD PHASES",
				(1.- environment.getEnvProps("TROPICAL","FRgas.aT"))* concentrations.get("Air").get("Trop").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Air").get("Trop").put("Solid species (S)",
				( engine.getMassMol(133) +
						engine.getMassMol(136) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )			
				);

		concentrations.get("Air").get("Trop").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(134) +
						engine.getMassMol(137) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )			
				);

		concentrations.get("Air").get("Trop").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(135) +
						engine.getMassMol(138) )*
				(input.getSubstancesData("Molweight")*1000.)/
				( environment.getEnvProps("TROPICAL", "VOLUME.aT") + environment.getEnvProps("TROPICAL", "VOLUME.cwT") )			
				);

		concentrations.put("Fresh water lake", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water lake").get("Reg").put("Fresh water lake",
				( engine.getMassMol(7) +
						engine.getMassMol(8) +
						engine.getMassMol(9) +
						engine.getMassMol(10) )*
				(input.getSubstancesData("Molweight")*1000.)/
				environment.getEnvProps("REGIONAL", "VOLUME.w0R")  			
				);

		concentrations.get("Fresh water lake").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(7)*input.getSubstancesData("Molweight") 			
				);

		concentrations.get("Fresh water lake").get("Reg").put("* DISSOLVED",
				environment.getEnvProps("REGIONAL","FRwD.w0R")* concentrations.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Fresh water lake").get("Reg").put("* SUSPENDED SOLIDS",
				environment.getEnvProps("REGIONAL","Kp.susp1R")* concentrations.get("Fresh water lake").get("Reg").get("* DISSOLVED")
				);

		concentrations.get("Fresh water lake").get("Reg").put("Solid species (S)",
				engine.getConcentration(8)*input.getSubstancesData("Molweight")   			
				);

		concentrations.get("Fresh water lake").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(9)*input.getSubstancesData("Molweight")   			
				);

		concentrations.get("Fresh water lake").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(10)*input.getSubstancesData("Molweight")   			
				);

		concentrations.get("Fresh water lake").get("Cont").put("Fresh water lake",
				(engine.getConcentration(50) +
						engine.getConcentration(51) +
						engine.getConcentration(52) +
						engine.getConcentration(53) )*
				(input.getSubstancesData("Molweight")*1000.)/
				environment.getEnvProps("CONTINENTAL", "VOLUME.w0C")  		
				);

		concentrations.get("Fresh water lake").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(50)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water lake").get("Cont").put("* DISSOLVED",
				environment.getEnvProps("CONTINENTAL","FRw.w0C")* concentrations.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)")
				);

		concentrations.get("Fresh water lake").get("Cont").put("* SUSPENDED SOLIDS",
				environment.getEnvProps("CONTINENTAL","Kp.susp1C")* concentrations.get("Fresh water lake").get("Cont").get("* DISSOLVED")
				);

		concentrations.get("Fresh water lake").get("Cont").put("Solid species (S)",
				engine.getConcentration(51)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water lake").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(52)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water lake").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(53)*input.getSubstancesData("Molweight")
				);

		concentrations.put("Fresh water lake sediment", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water lake sediment").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water lake sediment").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water lake sediment").get("Reg").put("Fresh water lake sediment",
				( ( engine.getMassMol(19) +
						engine.getMassMol(20) +
						engine.getMassMol(21) +
						engine.getMassMol(22) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd0R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water lake sediment").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(19)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water lake sediment").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(19)*environment.getEnvProps("REGIONAL", "FRACs.sdR")/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")/
						(environment.getEnvProps("REGIONAL", "Kp.sd0R")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("REGIONAL", "FRACs.sdR"))*(input.getSubstancesData("Molweight")*1000*1000)/
				(environment.getEnvProps("REGIONAL", "FRACs.sdR")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water lake sediment").get("Reg").put("* PORE WATER",
				concentrations.get("Fresh water lake sediment").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.sd0R")
				);


		concentrations.get("Fresh water lake sediment").get("Reg").put("Solid species (S)",
				( engine.getMassMol(20)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd0R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water lake sediment").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(21)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd0R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water lake sediment").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(22)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd0R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("Fresh water lake sediment",
				( ( engine.getMassMol(62) +
						engine.getMassMol(63) +
						engine.getMassMol(64) +
						engine.getMassMol(65) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd0C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(62)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(62)*environment.getEnvProps("CONTINENTAL", "FRACs.sdC")/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")/
						(environment.getEnvProps("CONTINENTAL", "Kp.sd0C")*
								input.getLandscapeSettings("ALL-SCALE", "RHOsolid")/1000)+
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC"))*(input.getSubstancesData("Molweight")*1000*1000)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("* PORE WATER",
				concentrations.get("Fresh water lake sediment").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.sd0C")
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("Solid species (S)",
				( engine.getMassMol(63)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd0C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(64)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd0C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water lake sediment").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(65)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd0C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.put("Fresh water", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water").get("Reg").put("Fresh water",
				( ( engine.getMassMol(11) +
						engine.getMassMol(12) +
						engine.getMassMol(13) +
						engine.getMassMol(14) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("REGIONAL", "VOLUME.w1R") ) 
				);

		concentrations.get("Fresh water").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(11)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Reg").put("* DISSOLVED",
				concentrations.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("REGIONAL", "FRwD.w1R")
				);

		concentrations.get("Fresh water").get("Reg").put("* SUSPENDED SOLIDS",
				concentrations.get("Fresh water").get("Reg").get("* DISSOLVED")*
				environment.getEnvProps("REGIONAL", "Kp.susp1R")
				);


		concentrations.get("Fresh water").get("Reg").put("Solid species (S)",
				engine.getConcentration(12)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(13)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(14)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Cont").put("Fresh water",
				( ( engine.getMassMol(54) +
						engine.getMassMol(55) +
						engine.getMassMol(56) +
						engine.getMassMol(57) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("CONTINENTAL", "VOLUME.w1C") ) 
				);

		concentrations.get("Fresh water").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(54)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Cont").put("* DISSOLVED",
				concentrations.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("CONTINENTAL", "FRw.w1C")
				);

		concentrations.get("Fresh water").get("Cont").put("* SUSPENDED SOLIDS",
				concentrations.get("Fresh water").get("Cont").get("* DISSOLVED")*
				environment.getEnvProps("CONTINENTAL", "Kp.susp1C")
				);

		concentrations.get("Fresh water").get("Cont").put("Solid species (S)",
				engine.getConcentration(55)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(56)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Fresh water").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(57)*input.getSubstancesData("Molweight")
				);

		concentrations.put("Fresh water sediment", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Fresh water sediment").get("Reg").put("Fresh water sediment",
				( ( engine.getMassMol(23) +
						engine.getMassMol(24) +
						engine.getMassMol(25) +
						engine.getMassMol(26) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd1R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water sediment").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(23)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water sediment").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(23)*environment.getEnvProps("REGIONAL", "FRACs.sdR")/
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

		concentrations.get("Fresh water sediment").get("Reg").put("Solid species (S)",
				( engine.getMassMol(24)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd1R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water sediment").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(25)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd1R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water sediment").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(26)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd1R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water sediment").get("Cont").put("Fresh water sediment",
				( ( engine.getMassMol(66) +
						engine.getMassMol(67) +
						engine.getMassMol(68) +
						engine.getMassMol(69) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd1C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water sediment").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(66)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Fresh water sediment").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(66)*environment.getEnvProps("CONTINENTAL", "FRACs.sdC")/
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

		concentrations.get("Fresh water sediment").get("Cont").put("Solid species (S)",
				( engine.getMassMol(67)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd1C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water sediment").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(68)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd1C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Fresh water sediment").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(69)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd1C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.put("Surface sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Surface sea/ocean water").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Surface sea/ocean water").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Surface sea/ocean water").get("Reg").put("Surface sea/ocean water",
				( ( engine.getMassMol(15) +
						engine.getMassMol(16) +
						engine.getMassMol(17) +
						engine.getMassMol(18) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("REGIONAL", "VOLUME.w2R") ) 
				);

		concentrations.get("Surface sea/ocean water").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(15)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Reg").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("REGIONAL", "FRwD.w2R")
				);

		concentrations.get("Surface sea/ocean water").get("Reg").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Reg").get("* DISSOLVED")*
				environment.getEnvProps("REGIONAL", "Kp.susp2R")
				);


		concentrations.get("Surface sea/ocean water").get("Reg").put("Solid species (S)",
				engine.getConcentration(16)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(17)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(18)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("Surface sea/ocean water",
				( ( engine.getMassMol(58) +
						engine.getMassMol(59) +
						engine.getMassMol(60) +
						engine.getMassMol(61) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("CONTINENTAL", "VOLUME.w2C") ) 
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(58)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("CONTINENTAL", "FRw.w2C")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Cont").get("* DISSOLVED")*
				environment.getEnvProps("CONTINENTAL", "Kp.susp2C")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("Solid species (S)",
				engine.getConcentration(59)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(60)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(61)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("Surface sea/ocean water",
				( ( engine.getMassMol(93) +
						engine.getMassMol(94) +
						engine.getMassMol(95) +
						engine.getMassMol(96) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("MODERATE", "VOLUME.w2M") ) 
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(93)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("MODERATE", "FRw.w2M")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Mod").get("* DISSOLVED")*
				environment.getEnvProps("MODERATE", "Kp.susp2M")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("Solid species (S)",
				engine.getConcentration(94)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(95)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Mod").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(96)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("Surface sea/ocean water",
				( ( engine.getMassMol(116) +
						engine.getMassMol(117) +
						engine.getMassMol(118) +
						engine.getMassMol(119) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("ARCTIC", "VOLUME.w2A") ) 
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(116)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("ARCTIC", "FRw.w2A")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Arct").get("* DISSOLVED")*
				environment.getEnvProps("ARCTIC", "Kp.susp2A")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("Solid species (S)",
				engine.getConcentration(117)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(118)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Arct").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(119)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("Surface sea/ocean water",
				( ( engine.getMassMol(139) +
						engine.getMassMol(140) +
						engine.getMassMol(141) +
						engine.getMassMol(142) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("TROPICAL", "VOLUME.w2T") ) 
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(139)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("* DISSOLVED",
				concentrations.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("TROPICAL", "FRw.w2T")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				concentrations.get("Surface sea/ocean water").get("Trop").get("* DISSOLVED")*
				environment.getEnvProps("TROPICAL", "Kp.susp2T")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("Solid species (S)",
				engine.getConcentration(140)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(141)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Surface sea/ocean water").get("Trop").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(142)*input.getSubstancesData("Molweight")
				);

		concentrations.put("Deep sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Deep sea/ocean water").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Deep sea/ocean water").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Deep sea/ocean water").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Deep sea/ocean water").get("Mod").put("Deep sea/ocean water",
				( ( engine.getMassMol(97) +
						engine.getMassMol(98) +
						engine.getMassMol(99) +
						engine.getMassMol(100) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("MODERATE", "VOLUME.w3M") ) 
				);

		concentrations.get("Deep sea/ocean water").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(97)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Mod").put("* DISSOLVED",
				concentrations.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("MODERATE", "FRw.w3M")
				);

		concentrations.get("Deep sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				concentrations.get("Deep sea/ocean water").get("Mod").get("* DISSOLVED")*
				environment.getEnvProps("MODERATE", "Kp.susp3M")
				);

		concentrations.get("Deep sea/ocean water").get("Mod").put("Solid species (S)",
				engine.getConcentration(98)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Mod").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(99)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Mod").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(100)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("Deep sea/ocean water",
				( ( engine.getMassMol(120) +
						engine.getMassMol(121) +
						engine.getMassMol(122) +
						engine.getMassMol(123) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("ARCTIC", "VOLUME.w3A") ) 
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(120)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("* DISSOLVED",
				concentrations.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("ARCTIC", "FRw.w3A")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				concentrations.get("Deep sea/ocean water").get("Arct").get("* DISSOLVED")*
				environment.getEnvProps("ARCTIC", "Kp.susp3A")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("Solid species (S)",
				engine.getConcentration(121)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(122)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Arct").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(123)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("Deep sea/ocean water",
				( ( engine.getMassMol(143) +
						engine.getMassMol(144) +
						engine.getMassMol(145) +
						engine.getMassMol(146) )*
						input.getSubstancesData("Molweight")/
						environment.getEnvProps("TROPICAL", "VOLUME.w3T") ) 
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(143)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("* DISSOLVED",
				concentrations.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")*
				environment.getEnvProps("TROPICAL", "FRw.w3T")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				concentrations.get("Deep sea/ocean water").get("Trop").get("* DISSOLVED")*
				environment.getEnvProps("TROPICAL", "Kp.susp3T")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("Solid species (S)",
				engine.getConcentration(144)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(145)*input.getSubstancesData("Molweight")
				);

		concentrations.get("Deep sea/ocean water").get("Trop").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(146)*input.getSubstancesData("Molweight")
				);

		concentrations.put("Marine sediment", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Marine sediment").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Marine sediment").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Marine sediment").get("Reg").put("Marine sediment",
				( ( engine.getMassMol(27) +
						engine.getMassMol(28) +
						engine.getMassMol(29) +
						engine.getMassMol(30) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd2R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(27)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(27)*environment.getEnvProps("REGIONAL", "FRACs.sdR")/
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

		concentrations.get("Marine sediment").get("Reg").put("Solid species (S)",
				( engine.getMassMol(28)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd2R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(29)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd2R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(30)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("REGIONAL", "VOLUME.sd2R") )/
				(environment.getEnvProps("REGIONAL", "FRACw.sdR")*1000 + 
						environment.getEnvProps("REGIONAL", "FRACs.sdR")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Cont").put("Marine sediment",
				( ( engine.getMassMol(70) +
						engine.getMassMol(71) +
						engine.getMassMol(72) +
						engine.getMassMol(73) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd2C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(70)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(70)*environment.getEnvProps("CONTINENTAL", "FRACs.sdC")/
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

		concentrations.get("Marine sediment").get("Cont").put("Solid species (S)",
				( engine.getMassMol(71)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd2C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(72)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd2C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(73)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.sd2C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.sdC")*1000 + 
						environment.getEnvProps("CONTINENTAL", "FRACs.sdC")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Mod").put("Marine sediment",
				( ( engine.getMassMol(101) +
						engine.getMassMol(102) +
						engine.getMassMol(103) +
						engine.getMassMol(104) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sdM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sdM")*1000 + 
						environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(101)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("MODERATE", "FRACw.sdM")*1000 + 
						environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Mod").put("* SOLID PHASE",
				engine.getConcentration(101)*environment.getEnvProps("MODERATE", "FRACs.sdM")/
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

		concentrations.get("Marine sediment").get("Mod").put("Solid species (S)",
				( engine.getMassMol(102)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sdM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sdM")*1000 + 
						environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Mod").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(103)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sdM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sdM")*1000 + 
						environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Mod").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(104)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sdM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sdM")*1000 + 
						environment.getEnvProps("MODERATE", "FRACs.sdM")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Arct").put("Marine sediment",
				( ( engine.getMassMol(124) +
						engine.getMassMol(125) +
						engine.getMassMol(126) +
						engine.getMassMol(127) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sdA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")*1000 + 
						environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(124)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")*1000 + 
						environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Arct").put("* SOLID PHASE",
				engine.getConcentration(124)*environment.getEnvProps("ARCTIC", "FRACs.sdA")/
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

		concentrations.get("Marine sediment").get("Arct").put("Solid species (S)",
				( engine.getMassMol(125)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sdA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")*1000 + 
						environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Arct").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(126)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sdA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")*1000 + 
						environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Arct").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(127)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sdA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sdA")*1000 + 
						environment.getEnvProps("ARCTIC", "FRACs.sdA")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Trop").put("Marine sediment",
				( ( engine.getMassMol(147) +
						engine.getMassMol(148) +
						engine.getMassMol(149) +
						engine.getMassMol(150) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sdT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")*1000 + 
						environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(147)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")*1000 + 
						environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Marine sediment").get("Trop").put("* SOLID PHASE",
				engine.getConcentration(147)*environment.getEnvProps("TROPICAL", "FRACs.sdT")/
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

		concentrations.get("Marine sediment").get("Trop").put("Solid species (S)",
				( engine.getMassMol(148)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sdT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")*1000 + 
						environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Trop").put("Species attached to NCs (<450 nm) (A)",
				( engine.getMassMol(149)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sdT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")*1000 + 
						environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.get("Marine sediment").get("Trop").put("Species attached to suspended particles (>450 nm) (P)",
				( engine.getMassMol(150)*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sdT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sdT")*1000 + 
						environment.getEnvProps("TROPICAL", "FRACs.sdT")*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )			
				);

		concentrations.put("Natural soil", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Natural soil").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Natural soil").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Natural soil").get("Reg").put("Natural soil",
				( ( engine.getMassMol(31) +
						engine.getMassMol(32) +
						engine.getMassMol(33) +
						engine.getMassMol(34) )*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.s1R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s1R") - environment.getEnvProps("REGIONAL", "FRACw.s1R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Natural soil").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(31)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("REGIONAL", "FRACw.s1R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s1R") - environment.getEnvProps("REGIONAL", "FRACw.s1R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				);

		concentrations.get("Natural soil").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(31)*environment.getEnvProps("REGIONAL", "FRs.s1R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACs.s1R")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Natural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Natural soil").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.s1R")
				);

		concentrations.get("Natural soil").get("Reg").put("Solid species (S)",
				engine.getConcentration(32)*environment.getEnvProps("REGIONAL", "FRs.s1R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s1R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s1R") - environment.getEnvProps("REGIONAL", "FRACw.s1R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Natural soil").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(33)*environment.getEnvProps("REGIONAL", "FRs.s1R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s1R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s1R") - environment.getEnvProps("REGIONAL", "FRACw.s1R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Natural soil").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(34)*environment.getEnvProps("REGIONAL", "FRs.s1R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s1R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s1R") - environment.getEnvProps("REGIONAL", "FRACw.s1R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Natural soil").get("Cont").put("Natural soil",
				( ( engine.getMassMol(74) +
						engine.getMassMol(75) +
						engine.getMassMol(76) +
						engine.getMassMol(77) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.s1C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s1C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s1C") - environment.getEnvProps("CONTINENTAL", "FRACw.s1C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Natural soil").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(74)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("CONTINENTAL", "FRACw.s1C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s1C") - environment.getEnvProps("CONTINENTAL", "FRACw.s1C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Natural soil").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(74)*environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.s1C")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Natural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Natural soil").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.s1C")
				);

		concentrations.get("Natural soil").get("Cont").put("Solid species (S)",
				engine.getConcentration(75)*environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s1C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s1C") - environment.getEnvProps("CONTINENTAL", "FRACw.s1C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )	
				);

		concentrations.get("Natural soil").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(76)*environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s1C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s1C") - environment.getEnvProps("CONTINENTAL", "FRACw.s1C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Natural soil").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(77)*environment.getEnvProps("CONTINENTAL", "FRs.s1C")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s1C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s1C") - environment.getEnvProps("CONTINENTAL", "FRACw.s1C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.put("Agricultural soil", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Agricultural soil").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Agricultural soil").put("Cont", new HashMap<String, Double>() );

		concentrations.get("Agricultural soil").get("Reg").put("Agricultural soil",
				( ( engine.getMassMol(35) +
						engine.getMassMol(36) +
						engine.getMassMol(37) +
						engine.getMassMol(38) )*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.s2R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s2R") - environment.getEnvProps("REGIONAL", "FRACw.s2R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Agricultural soil").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(35)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("REGIONAL", "FRACw.s2R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s2R") - environment.getEnvProps("REGIONAL", "FRACw.s2R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				);

		concentrations.get("Agricultural soil").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(35)*environment.getEnvProps("REGIONAL", "FRs.s2R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACs.s2R")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Agricultural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Agricultural soil").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.s2R")
				);

		concentrations.get("Agricultural soil").get("Reg").put("Solid species (S)",
				engine.getConcentration(36)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s2R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s2R") - environment.getEnvProps("REGIONAL", "FRACw.s2R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Agricultural soil").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(37)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s2R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s2R") - environment.getEnvProps("REGIONAL", "FRACw.s2R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Agricultural soil").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(38)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s2R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s2R") - environment.getEnvProps("REGIONAL", "FRACw.s2R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Agricultural soil").get("Cont").put("Agricultural soil",
				( ( engine.getMassMol(78) +
						engine.getMassMol(79) +
						engine.getMassMol(80) +
						engine.getMassMol(81) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.s2C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s2C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s2C") - environment.getEnvProps("CONTINENTAL", "FRACw.s2C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Agricultural soil").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(78)*
				(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("CONTINENTAL", "FRACw.s2C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s2C") - environment.getEnvProps("CONTINENTAL", "FRACw.s2C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Agricultural soil").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(78)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.s2C")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Agricultural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Agricultural soil").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.s2C")
				);

		concentrations.get("Agricultural soil").get("Cont").put("Solid species (S)",
				engine.getConcentration(79)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s2C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s2C") - environment.getEnvProps("CONTINENTAL", "FRACw.s2C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )	
				);

		concentrations.get("Agricultural soil").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(80)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s2C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s2C") - environment.getEnvProps("CONTINENTAL", "FRACw.s2C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Agricultural soil").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(81)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s2C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s2C") - environment.getEnvProps("CONTINENTAL", "FRACw.s2C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.put("Other soil", new HashMap< String, Map< String, Double > >() );		
		concentrations.get("Other soil").put("Reg", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Cont", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Mod", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Arct", new HashMap<String, Double>() );
		concentrations.get("Other soil").put("Trop", new HashMap<String, Double>() );

		concentrations.get("Other soil").get("Reg").put("Other soil",
				( ( engine.getMassMol(39) +
						engine.getMassMol(40) +
						engine.getMassMol(41) +
						engine.getMassMol(42) )*
						(input.getSubstancesData("Molweight")*1000.) )/
				(environment.getEnvProps("REGIONAL", "FRACw.s3R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s3R") - environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(39)*(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("REGIONAL", "FRACw.s3R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s3R") - environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Other soil").get("Reg").put("* SOLID PHASE",
				engine.getConcentration(39)*environment.getEnvProps("REGIONAL", "FRs.s3R")*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACs.s3R")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Other soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Reg").get("* SOLID PHASE")/
				environment.getEnvProps("REGIONAL", "Kp.s3R")
				);

		concentrations.get("Other soil").get("Reg").put("Solid species (S)",
				engine.getConcentration(40)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s3R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s3R") - environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Other soil").get("Reg").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(41)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s3R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s3R") - environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )				
				);

		concentrations.get("Other soil").get("Reg").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(42)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("REGIONAL", "FRACw.s3R")*1000 + 
						( 1. - environment.getEnvProps("REGIONAL", "FRACa.s3R") - environment.getEnvProps("REGIONAL", "FRACw.s3R") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Cont").put("Other soil",
				( ( engine.getMassMol(82) +
						engine.getMassMol(83) +
						engine.getMassMol(84) +
						engine.getMassMol(85) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("CONTINENTAL", "VOLUME.s3C") )/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s3C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s3C") - environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(82)*
				(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("CONTINENTAL", "FRACw.s3C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s3C") - environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Cont").put("* SOLID PHASE",
				engine.getConcentration(82)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACs.s3C")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Other soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Cont").get("* SOLID PHASE")/
				environment.getEnvProps("CONTINENTAL", "Kp.s3C")
				);

		concentrations.get("Other soil").get("Cont").put("Solid species (S)",
				engine.getConcentration(83)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s3C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s3C") - environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )	
				);

		concentrations.get("Other soil").get("Cont").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(84)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s3C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s3C") - environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Cont").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(85)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("CONTINENTAL", "FRACw.s3C")*1000 + 
						( 1. - environment.getEnvProps("CONTINENTAL", "FRACa.s3C") - environment.getEnvProps("CONTINENTAL", "FRACw.s3C") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Mod").put("Other soil",
				( ( engine.getMassMol(105) +
						engine.getMassMol(106) +
						engine.getMassMol(107) +
						engine.getMassMol(108) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("MODERATE", "VOLUME.sM") )/
				(environment.getEnvProps("MODERATE", "FRACw.sM")*1000 + 
						( 1. - environment.getEnvProps("MODERATE", "FRACa.sM") - environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(105)*
				(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("MODERATE", "FRACw.sM")*1000 + 
						( 1. - environment.getEnvProps("MODERATE", "FRACa.sM") - environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Mod").put("* SOLID PHASE",
				engine.getConcentration(105)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("MODERATE", "FRACs.sM")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Other soil").get("Mod").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Mod").get("* SOLID PHASE")/
				environment.getEnvProps("MODERATE", "Kp.sM")
				);

		concentrations.get("Other soil").get("Mod").put("Solid species (S)",
				engine.getConcentration(106)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("MODERATE", "FRACw.sM")*1000 + 
						( 1. - environment.getEnvProps("MODERATE", "FRACa.sM") - environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )	
				);

		concentrations.get("Other soil").get("Mod").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(107)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("MODERATE", "FRACw.sM")*1000 + 
						( 1. - environment.getEnvProps("MODERATE", "FRACa.sM") - environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Mod").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(108)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("MODERATE", "FRACw.sM")*1000 + 
						( 1. - environment.getEnvProps("MODERATE", "FRACa.sM") - environment.getEnvProps("MODERATE", "FRACw.sM") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Arct").put("Other soil",
				( ( engine.getMassMol(128) +
						engine.getMassMol(129) +
						engine.getMassMol(130) +
						engine.getMassMol(131) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("ARCTIC", "VOLUME.sA") )/
				(environment.getEnvProps("ARCTIC", "FRACw.sA")*1000 + 
						( 1. - environment.getEnvProps("ARCTIC", "FRACa.sA") - environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(128)*
				(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("ARCTIC", "FRACw.sA")*1000 + 
						( 1. - environment.getEnvProps("ARCTIC", "FRACa.sA") - environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Arct").put("* SOLID PHASE",
				engine.getConcentration(128)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("ARCTIC", "FRACs.sA")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Other soil").get("Arct").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Arct").get("* SOLID PHASE")/
				environment.getEnvProps("ARCTIC", "Kp.sA")
				);

		concentrations.get("Other soil").get("Arct").put("Solid species (S)",
				engine.getConcentration(129)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("ARCTIC", "FRACw.sA")*1000 + 
						( 1. - environment.getEnvProps("ARCTIC", "FRACa.sA") - environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )	
				);

		concentrations.get("Other soil").get("Arct").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(130)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("ARCTIC", "FRACw.sA")*1000 + 
						( 1. - environment.getEnvProps("ARCTIC", "FRACa.sA") - environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Arct").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(131)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("ARCTIC", "FRACw.sA")*1000 + 
						( 1. - environment.getEnvProps("ARCTIC", "FRACa.sA") - environment.getEnvProps("ARCTIC", "FRACw.sA") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Trop").put("Other soil",
				( ( engine.getMassMol(151) +
						engine.getMassMol(152) +
						engine.getMassMol(153) +
						engine.getMassMol(154) )*
						(input.getSubstancesData("Molweight")*1000.)/
						environment.getEnvProps("TROPICAL", "VOLUME.sT") )/
				(environment.getEnvProps("TROPICAL", "FRACw.sT")*1000 + 
						( 1. - environment.getEnvProps("TROPICAL", "FRACa.sT") - environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getConcentration(151)*
				(input.getSubstancesData("Molweight")*1000.)/ 			
				(environment.getEnvProps("TROPICAL", "FRACw.sT")*1000 + 
						( 1. - environment.getEnvProps("TROPICAL", "FRACa.sT") - environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Trop").put("* SOLID PHASE",
				engine.getConcentration(151)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("TROPICAL", "FRACs.sT")*input.getLandscapeSettings("ALL-SCALE", "RHOsolid"))
				);

		concentrations.get("Other soil").get("Trop").put("* PORE WATER / GROUNDWATER",
				concentrations.get("Other soil").get("Trop").get("* SOLID PHASE")/
				environment.getEnvProps("TROPICAL", "Kp.sT")
				);

		concentrations.get("Other soil").get("Trop").put("Solid species (S)",
				engine.getConcentration(152)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("TROPICAL", "FRACw.sT")*1000 + 
						( 1. - environment.getEnvProps("TROPICAL", "FRACa.sT") - environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )	
				);

		concentrations.get("Other soil").get("Trop").put("Species attached to NCs (<450 nm) (A)",
				engine.getConcentration(153)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("TROPICAL", "FRACw.sT")*1000 + 
						( 1. - environment.getEnvProps("TROPICAL", "FRACa.sT") - environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
				);

		concentrations.get("Other soil").get("Trop").put("Species attached to suspended particles (>450 nm) (P)",
				engine.getConcentration(154)*
				(input.getSubstancesData("Molweight")*1000.)/
				(environment.getEnvProps("TROPICAL", "FRACw.sT")*1000 + 
						( 1. - environment.getEnvProps("TROPICAL", "FRACa.sT") - environment.getEnvProps("TROPICAL", "FRACw.sT") )*
						input.getLandscapeSettings("ALL-SCALE", "RHOsolid") )
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

		fugacities.get("Air").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(0)
				);

		fugacities.get("Air").get("Reg").put("* GAS PHASE",
				fugacities.get("Air").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRgas.aR")
				);

		fugacities.get("Air").get("Reg").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRgas.aR") )
				);

		fugacities.get("Air").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(43)
				);

		fugacities.get("Air").get("Cont").put("* GAS PHASE",
				fugacities.get("Air").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRgas.aC")
				);

		fugacities.get("Air").get("Cont").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRgas.aC") )
				);

		fugacities.get("Air").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(86)
				);

		fugacities.get("Air").get("Mod").put("* GAS PHASE",
				fugacities.get("Air").get("Mod").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("MODERATE","FRgas.aM")
				);

		fugacities.get("Air").get("Mod").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Mod").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("MODERATE","FRgas.aM") )
				);

		fugacities.get("Air").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(109)
				);

		fugacities.get("Air").get("Arct").put("* GAS PHASE",
				fugacities.get("Air").get("Arct").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("ARCTIC","FRgas.aA")
				);

		fugacities.get("Air").get("Arct").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Arct").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("ARCTIC","FRgas.aA") )
				);

		fugacities.get("Air").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(132)
				);

		fugacities.get("Air").get("Trop").put("* GAS PHASE",
				fugacities.get("Air").get("Trop").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("TROPICAL","FRgas.aT")
				);

		fugacities.get("Air").get("Trop").put("* AEROSOL- and CLOUD PHASES",
				fugacities.get("Air").get("Trop").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("TROPICAL","FRgas.aT") )
				);


		fugacities.put("Fresh water lake", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Fresh water lake").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Fresh water lake").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Fresh water lake").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(7)
				);

		fugacities.get("Fresh water lake").get("Reg").put("* DISSOLVED",
				fugacities.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRwD.w0R")
				);

		fugacities.get("Fresh water lake").get("Reg").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRwD.w0R") )
				);

		fugacities.get("Fresh water lake").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(50)
				);

		fugacities.get("Fresh water lake").get("Cont").put("* DISSOLVED",
				fugacities.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRw.w0C")
				);

		fugacities.get("Fresh water lake").get("Cont").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRw.w0C") )
				);

		fugacities.put("Fresh water", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Fresh water").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Fresh water").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Fresh water").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(11)
				);

		fugacities.get("Fresh water").get("Reg").put("* DISSOLVED",
				fugacities.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRwD.w1R")
				);

		fugacities.get("Fresh water").get("Reg").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRwD.w1R") )
				);


		fugacities.get("Fresh water").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(54)
				);

		fugacities.get("Fresh water").get("Cont").put("* DISSOLVED",
				fugacities.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRw.w1C")
				);

		fugacities.get("Fresh water").get("Cont").put("* SUSPENDED SOLIDS",
				fugacities.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRw.w1C") )
				);

		fugacities.put("Fresh water sediment", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Fresh water sediment").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Fresh water sediment").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Fresh water sediment").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(23)
				);

		fugacities.get("Fresh water sediment").get("Reg").put("* PORE WATER",
				fugacities.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRACw.sdR")
				);

		fugacities.get("Fresh water sediment").get("Reg").put("* SOLID PHASE",
				fugacities.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRACw.sdR") )
				);


		fugacities.get("Fresh water sediment").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(66)
				);

		fugacities.get("Fresh water sediment").get("Cont").put("* PORE WATER",
				fugacities.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRACw.sdC")
				);

		fugacities.get("Fresh water sediment").get("Cont").put("* SOLID PHASE",
				fugacities.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.sdC") )
				);

		fugacities.put("Surface sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Surface sea/ocean water").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Surface sea/ocean water").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Surface sea/ocean water").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(15)
				);

		fugacities.get("Surface sea/ocean water").get("Reg").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRwD.w2R")
				);

		fugacities.get("Surface sea/ocean water").get("Reg").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRwD.w2R") )
				);

		fugacities.get("Surface sea/ocean water").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(58)
				);

		fugacities.get("Surface sea/ocean water").get("Cont").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRw.w2C")
				);

		fugacities.get("Surface sea/ocean water").get("Cont").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRw.w2C") )
				);

		fugacities.get("Surface sea/ocean water").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(93)
				);

		fugacities.get("Surface sea/ocean water").get("Mod").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("MODERATE","FRw.w2M")
				);

		fugacities.get("Surface sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("MODERATE","FRw.w2M") )
				);

		fugacities.get("Surface sea/ocean water").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(116)
				);

		fugacities.get("Surface sea/ocean water").get("Arct").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("ARCTIC","FRw.w2A")
				);

		fugacities.get("Surface sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("ARCTIC","FRw.w2A") )
				);

		fugacities.get("Surface sea/ocean water").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(139)
				);

		fugacities.get("Surface sea/ocean water").get("Trop").put("* DISSOLVED",
				fugacities.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("TROPICAL","FRw.w2T")
				);

		fugacities.get("Surface sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				fugacities.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("TROPICAL","FRw.w2T") )
				);

		fugacities.put("Deep sea/ocean water", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Deep sea/ocean water").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Deep sea/ocean water").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Deep sea/ocean water").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Deep sea/ocean water").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(97)
				);

		fugacities.get("Deep sea/ocean water").get("Mod").put("* DISSOLVED",
				fugacities.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("MODERATE","FRw.w3M")
				);

		fugacities.get("Deep sea/ocean water").get("Mod").put("* SUSPENDED SOLIDS",
				fugacities.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("MODERATE","FRw.w3M") )
				);

		fugacities.get("Deep sea/ocean water").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(120)
				);

		fugacities.get("Deep sea/ocean water").get("Arct").put("* DISSOLVED",
				fugacities.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("ARCTIC","FRw.w3A")
				);

		fugacities.get("Deep sea/ocean water").get("Arct").put("* SUSPENDED SOLIDS",
				fugacities.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("ARCTIC","FRw.w3A") )
				);

		fugacities.get("Deep sea/ocean water").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(143)
				);

		fugacities.get("Deep sea/ocean water").get("Trop").put("* DISSOLVED",
				fugacities.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("TROPICAL","FRw.w3T")
				);

		fugacities.get("Deep sea/ocean water").get("Trop").put("* SUSPENDED SOLIDS",
				fugacities.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("TROPICAL","FRw.w3T") )
				);

		fugacities.put("Marine sediment", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Marine sediment").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Marine sediment").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Marine sediment").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(27)
				);

		fugacities.get("Marine sediment").get("Reg").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRACw.sdR")
				);

		fugacities.get("Marine sediment").get("Reg").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRACw.sdR") )
				);

		fugacities.get("Marine sediment").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(70)
				);

		fugacities.get("Marine sediment").get("Cont").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRACw.sdC")
				);

		fugacities.get("Marine sediment").get("Cont").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.sdC") )
				);

		fugacities.get("Marine sediment").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(101)
				);

		fugacities.get("Marine sediment").get("Mod").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("MODERATE","FRACw.sdM")
				);

		fugacities.get("Marine sediment").get("Mod").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("MODERATE","FRACw.sdM") )
				);

		fugacities.get("Marine sediment").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(124)
				);

		fugacities.get("Marine sediment").get("Arct").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("ARCTIC","FRACw.sdA")
				);

		fugacities.get("Marine sediment").get("Arct").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("ARCTIC","FRACw.sdA") )
				);

		fugacities.get("Marine sediment").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(147)
				);

		fugacities.get("Marine sediment").get("Trop").put("* PORE WATER",
				fugacities.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("TROPICAL","FRACw.sdT")
				);

		fugacities.get("Marine sediment").get("Trop").put("* SOLID PHASE",
				fugacities.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("TROPICAL","FRACw.sdT") )
				);

		fugacities.put("Natural soil", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Natural soil").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Natural soil").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Natural soil").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(31)
				);

		fugacities.get("Natural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRACw.s1R")
				);

		fugacities.get("Natural soil").get("Reg").put("* SOLID PHASE",
				fugacities.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRACw.s1R") )
				);


		fugacities.get("Natural soil").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(74)
				);

		fugacities.get("Natural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRACw.s1C")
				);

		fugacities.get("Natural soil").get("Cont").put("* SOLID PHASE",
				fugacities.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.s1C") )
				);

		fugacities.put("Agricultural soil", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Agricultural soil").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Agricultural soil").put("Cont", new HashMap<String, Double>() );

		fugacities.get("Agricultural soil").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(35)
				);

		fugacities.get("Agricultural soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRACw.s2R")
				);

		fugacities.get("Agricultural soil").get("Reg").put("* SOLID PHASE",
				fugacities.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRACw.s2R") )
				);

		fugacities.get("Agricultural soil").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(78)
				);

		fugacities.get("Agricultural soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRACw.s2C")
				);

		fugacities.get("Agricultural soil").get("Cont").put("* SOLID PHASE",
				fugacities.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.s2C") )
				);

		fugacities.put("Other soil", new HashMap< String, Map< String, Double > >() );		
		fugacities.get("Other soil").put("Reg", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Cont", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Mod", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Arct", new HashMap<String, Double>() );
		fugacities.get("Other soil").put("Trop", new HashMap<String, Double>() );

		fugacities.get("Other soil").get("Reg").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(39)
				);

		fugacities.get("Other soil").get("Reg").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("REGIONAL","FRACw.s3R")
				);

		fugacities.get("Other soil").get("Reg").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("REGIONAL","FRACw.s3R") )
				);

		fugacities.get("Other soil").get("Cont").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(82)
				);

		fugacities.get("Other soil").get("Cont").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("CONTINENTAL","FRACw.s3C")
				);

		fugacities.get("Other soil").get("Cont").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("CONTINENTAL","FRACw.s3C") )
				);

		fugacities.get("Other soil").get("Mod").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(105)
				);

		fugacities.get("Other soil").get("Mod").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("MODERATE","FRACw.sM")
				);

		fugacities.get("Other soil").get("Mod").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("MODERATE","FRACw.sM") )
				);

		fugacities.get("Other soil").get("Arct").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(128)
				);

		fugacities.get("Other soil").get("Arct").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("ARCTIC","FRACw.sA")
				);

		fugacities.get("Other soil").get("Arct").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("ARCTIC","FRACw.sA") )
				);

		fugacities.get("Other soil").get("Trop").put("Dissolved/Gas species (G/D)",
				engine.getFugacity(151)
				);

		fugacities.get("Other soil").get("Trop").put("* PORE WATER / GROUNDWATER",
				fugacities.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)")*environment.getEnvProps("TROPICAL","FRACw.sT")
				);

		fugacities.get("Other soil").get("Trop").put("* SOLID PHASE",
				fugacities.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)")*(1. - environment.getEnvProps("TROPICAL","FRACw.sT") )
				);		
	}

	void buildInflow()
	{			
		inflow.put("Regional Scale", new HashMap< String, Double >() );		
		inflow.get("Regional Scale").put("air", environment.getEnvProps("CONTINENTAL","k.aC.aR")
				*engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("fresh water lakes", environment.getEnvProps("REGIONAL","k.w1R.w0R")
				*engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("fresh water",(
				environment.getEnvProps("REGIONAL","WATERflow.w0R.w1R")*engine.getConcentration(7) + 
				environment.getEnvProps("CONTINENTAL","WATERflow.w1C.w1R")*engine.getConcentration(54) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("coastal sea water",(
				environment.getEnvProps("REGIONAL","k.w1R.w2R")*engine.getMassMol(11) + 
				environment.getEnvProps("CONTINENTAL","k.w2C.w2R")*engine.getMassMol(58) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Continental Scale", new HashMap< String, Double >() );		
		inflow.get("Continental Scale").put("air",(
				environment.getEnvProps("REGIONAL","k.aR.aC")*engine.getMassMol(0) + 
				environment.getEnvProps("MODERATE","k.aM.aC")*engine.getMassMol(86) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("fresh water lakes", environment.getEnvProps("CONTINENTAL","k.w1C.w0C")
				*engine.getMassMol(54)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("fresh water", environment.getEnvProps("CONTINENTAL","k.w0C.w1C")
				*engine.getMassMol(50)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("MODERATE","k.w2M.w2C")*engine.getMassMol(93) + 
				environment.getEnvProps("REGIONAL","k.w2R.w2C")*engine.getMassMol(15) +
				environment.getEnvProps("CONTINENTAL","k.w1C.w2C")*engine.getMassMol(54) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Moderate climate zone").put("air",(
				environment.getEnvProps("CONTINENTAL","k.aC.aM")*engine.getMassMol(43) +
				environment.getEnvProps("ARCTIC","k.aA.aM")*engine.getMassMol(109) +
				environment.getEnvProps("TROPICAL","k.aT.aM")*engine.getMassMol(132) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Moderate climate zone").put("upper ocean water",(
				environment.getEnvProps("CONTINENTAL","k.w2C.w2M")*engine.getMassMol(58)  +
				environment.getEnvProps("ARCTIC","k.w2A.w2M")*engine.getMassMol(116) +
				environment.getEnvProps("TROPICAL","k.w2T.w2M")*engine.getMassMol(139) +
				environment.getEnvProps("MODERATE","k.w3M.w2M")*engine.getMassMol(97) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Moderate climate zone").put("deep sea",(
				environment.getEnvProps("MODERATE","k.w2M.w3M")*engine.getMassMol(93)  +
				environment.getEnvProps("ARCTIC","k.w3A.w3M")*engine.getMassMol(120) +
				environment.getEnvProps("TROPICAL","k.w3T.w3M")*engine.getMassMol(143) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("MODERATE","k.aM.aA")*engine.getMassMol(86)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Arctic climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2A")*engine.getMassMol(93)  +
				environment.getEnvProps("ARCTIC","k.w3A.w2A")*engine.getMassMol(120) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Arctic climate zone").put("deep sea",(
				environment.getEnvProps("ARCTIC","k.w2A.w3A")*engine.getMassMol(116)  +
				environment.getEnvProps("MODERATE","k.w3M.w3A")*engine.getMassMol(97) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("MODERATE","k.aM.aT")*engine.getMassMol(86)*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Tropical climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2T")*engine.getMassMol(93)  +
				environment.getEnvProps("TROPICAL","k.w3T.w2T")*engine.getMassMol(143) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Tropical climate zone").put("deep sea",(
				environment.getEnvProps("TROPICAL","k.w2T.w3T")*engine.getMassMol(139)  +
				environment.getEnvProps("MODERATE","k.w3M.w3T")*engine.getMassMol(97) )*
				input.getSubstancesData("Molweight")
				);
	}


	void buildInflowNano()
	{			
		inflow.put("Regional Scale", new HashMap< String, Double >() );		
		inflow.get("Regional Scale").put("air", environment.getEnvProps("CONTINENTAL","k.aC.aR")
				*(engine.getConcentration(44) + engine.getMassMol(45) + engine.getMassMol(46) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("fresh water lakes", environment.getEnvProps("REGIONAL","k.w1R.w0R")
				*(engine.getMassMol(12) + engine.getMassMol(13) + engine.getMassMol(14) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("fresh water",
				( environment.getEnvProps("REGIONAL","WATERflow.w0R.w1R")*
						( engine.getConcentration(8) + engine.getConcentration(9) + engine.getConcentration(10)  ) + 
						environment.getEnvProps("CONTINENTAL","WATERflow.w1C.w1R")*
						( engine.getConcentration(55) +  engine.getConcentration(56) + engine.getConcentration(57) ) )* 
				input.getSubstancesData("Molweight")
				);

		inflow.get("Regional Scale").put("coastal sea water",
				(environment.getEnvProps("REGIONAL","k.w1R.w2R")*(engine.getMassMol(12) + engine.getMassMol(13) + engine.getMassMol(14) )  + 
						environment.getEnvProps("CONTINENTAL","k.w2C.w2R")*( engine.getMassMol(59) + engine.getMassMol(60) + engine.getMassMol(61) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Continental Scale", new HashMap< String, Double >() );		
		inflow.get("Continental Scale").put("air",
				(environment.getEnvProps("REGIONAL","k.aR.aC")*(engine.getMassMol(1) + engine.getMassMol(2) + engine.getMassMol(3) ) +     
						environment.getEnvProps("MODERATE","k.aM.aC")*(engine.getMassMol(87) + engine.getMassMol(88) + engine.getMassMol(89)  )  )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("fresh water lakes", environment.getEnvProps("CONTINENTAL","k.w1C.w0C")
				*(engine.getMassMol(55) + engine.getMassMol(56) + engine.getMassMol(57)  ) *
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("fresh water", environment.getEnvProps("CONTINENTAL","k.w0C.w1C")
				*(engine.getMassMol(51) + engine.getMassMol(52) + engine.getMassMol(53) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Continental Scale").put("coastal sea water",
				(environment.getEnvProps("MODERATE","k.w2M.w2C")*(engine.getMassMol(94) + engine.getMassMol(95) + engine.getMassMol(96) )  + 
						environment.getEnvProps("REGIONAL","k.w2R.w2C")*(engine.getMassMol(16) + engine.getMassMol(17) + engine.getMassMol(18) )  +
						environment.getEnvProps("CONTINENTAL","k.w1C.w2C")*(engine.getMassMol(55) +engine.getMassMol(56) + engine.getMassMol(57) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Moderate climate zone").put("air",(
				environment.getEnvProps("CONTINENTAL","k.aC.aM")*(engine.getMassMol(44) + engine.getMassMol(45) + engine.getMassMol(46) )  +
				environment.getEnvProps("ARCTIC","k.aA.aM")*(engine.getMassMol(110) + engine.getMassMol(111) + engine.getMassMol(112) ) +
				environment.getEnvProps("TROPICAL","k.aT.aM")*(engine.getMassMol(133) + engine.getMassMol(134) + engine.getMassMol(135) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Moderate climate zone").put("upper ocean water",(
				environment.getEnvProps("CONTINENTAL","k.w2C.w2M")*(engine.getMassMol(59) + engine.getMassMol(60) + engine.getMassMol(61) ) +
				environment.getEnvProps("ARCTIC","k.w2A.w2M")*(engine.getMassMol(117) + engine.getMassMol(118) + engine.getMassMol(119) ) +
				environment.getEnvProps("TROPICAL","k.w2T.w2M")*(engine.getMassMol(140) + engine.getMassMol(141) + engine.getMassMol(142) ) +
				environment.getEnvProps("MODERATE","k.w3M.w2M")*(engine.getMassMol(98) + engine.getMassMol(99) + engine.getMassMol(100) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Moderate climate zone").put("deep sea",(
				environment.getEnvProps("MODERATE","k.w2M.w3M")*(engine.getMassMol(94) + engine.getMassMol(95) + engine.getMassMol(96) )  +
				environment.getEnvProps("ARCTIC","k.w3A.w3M")*(engine.getMassMol(121) + engine.getMassMol(122) + engine.getMassMol(123) ) +
				environment.getEnvProps("TROPICAL","k.w3T.w3M")*(engine.getMassMol(144) + engine.getMassMol(145) + engine.getMassMol(146) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("MODERATE","k.aM.aA")*(engine.getMassMol(87) + engine.getMassMol(88) + engine.getMassMol(89))*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Arctic climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2A")*( engine.getMassMol(94) + engine.getMassMol(95) + engine.getMassMol(96) ) +
				environment.getEnvProps("ARCTIC","k.w3A.w2A")*(engine.getMassMol(121) + engine.getMassMol(122) + engine.getMassMol(123) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Arctic climate zone").put("deep sea",(
				environment.getEnvProps("ARCTIC","k.w2A.w3A")*(engine.getMassMol(117) + engine.getMassMol(118) + engine.getMassMol(119)  ) +
				environment.getEnvProps("MODERATE","k.w3M.w3A")*(engine.getMassMol(98) + engine.getMassMol(99) + engine.getMassMol(100) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		inflow.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("MODERATE","k.aM.aT")*(engine.getMassMol(87) + engine.getMassMol(88) + engine.getMassMol(89) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Tropical climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2T")*(engine.getMassMol(94) + engine.getMassMol(95) + engine.getMassMol(96)  ) +
				environment.getEnvProps("TROPICAL","k.w3T.w2T")*( engine.getMassMol(144) + engine.getMassMol(145) + engine.getMassMol(146) ) )*
				input.getSubstancesData("Molweight")
				);

		inflow.get("Global Scale - Tropical climate zone").put("deep sea",(
				environment.getEnvProps("TROPICAL","k.w2T.w3T")*(engine.getMassMol(140) + engine.getMassMol(141) + engine.getMassMol(142)  ) +
				environment.getEnvProps("MODERATE","k.w3M.w3T")*(engine.getMassMol(98) + engine.getMassMol(99) + engine.getMassMol(100) ) )*
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
				engine.getMassMol(7)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Regional Scale").put("fresh water",(
				environment.getEnvProps("REGIONAL","WATERflow.w1R.w0R") + 
				environment.getEnvProps("REGIONAL","WATERflow.w1R.w2R") )*
				engine.getConcentration(11)*
				input.getSubstancesData("Molweight")
				);		

		outflow.get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2R.w2C")
				*engine.getMassMol(15)*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Continental Scale", new HashMap< String, Double >() );		

		outflow.get("Continental Scale").put("air",(
				environment.getEnvProps("CONTINENTAL","k.aC.aM") + 
				environment.getEnvProps("CONTINENTAL","k.aC.aR") )*
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("fresh water lakes", (
				environment.getEnvProps("CONTINENTAL","k.w0C.w1C") + 
				environment.getEnvProps("CONTINENTAL","k.w0C.w2C") )*
				engine.getMassMol(50)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.w1C.w0C") + 
				environment.getEnvProps("CONTINENTAL","k.w1C.w1R") +
				environment.getEnvProps("CONTINENTAL","k.w1C.w2C") )*
				engine.getMassMol(54)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("CONTINENTAL","k.w2C.w2M") + 
				environment.getEnvProps("CONTINENTAL","k.w2C.w2R") )*
				engine.getMassMol(58)*
				input.getSubstancesData("Molweight")
				);


		outflow.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Moderate climate zone").put("air",(
				environment.getEnvProps("MODERATE","k.aM.aA") +
				environment.getEnvProps("MODERATE","k.aM.aC") +
				environment.getEnvProps("MODERATE","k.aM.aT") )*
				engine.getMassMol(86)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Moderate climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2C") +
				environment.getEnvProps("MODERATE","k.w2M.w2A") +
				environment.getEnvProps("MODERATE","k.w2M.w2T") +
				environment.getEnvProps("MODERATE","k.w2M.w3M") )*
				engine.getMassMol(93)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Moderate climate zone").put("deep sea",(
				environment.getEnvProps("MODERATE","k.w3M.w2M") +
				environment.getEnvProps("MODERATE","k.w3M.w3A") +
				environment.getEnvProps("MODERATE","k.w3M.w3T") )*
				engine.getMassMol(97)*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("ARCTIC","k.aA.aM")*
				engine.getMassMol(109)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Arctic climate zone").put("upper ocean water",(
				environment.getEnvProps("ARCTIC","k.w2A.w2M") +
				environment.getEnvProps("ARCTIC","k.w2A.w3A") )*
				engine.getMassMol(116)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Arctic climate zone").put("deep sea",(
				environment.getEnvProps("ARCTIC","k.w3A.w2A") +
				environment.getEnvProps("ARCTIC","k.w3A.w3M") )*
				engine.getMassMol(120)*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("TROPICAL","k.aT.aM")*
				engine.getMassMol(132)*
				input.getSubstancesData("Molweight")				
				);

		outflow.get("Global Scale - Tropical climate zone").put("upper ocean water",(
				environment.getEnvProps("TROPICAL","k.w2T.w2M") +
				environment.getEnvProps("TROPICAL","k.w2T.w3T") )*
				engine.getMassMol(139)*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Tropical climate zone").put("deep sea",(
				environment.getEnvProps("TROPICAL","k.w3T.w2T") +
				environment.getEnvProps("TROPICAL","k.w3T.w3M") )*
				engine.getMassMol(143)*
				input.getSubstancesData("Molweight")
				);
	}


	void buildOutflowNano()
	{	

		outflow.clear();

		outflow.put("Regional Scale", new HashMap< String, Double >() );		

		outflow.get("Regional Scale").put("air", 
				environment.getEnvProps("REGIONAL","k.aR.aC")
				*(engine.getMassMol(1) + engine.getMassMol(2) + engine.getMassMol(3) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Regional Scale").put("fresh water lakes", 
				(environment.getEnvProps("REGIONAL","k.w0R.w1R") + 
						environment.getEnvProps("REGIONAL","k.w0R.w2R") )*
				(engine.getMassMol(8) + engine.getMassMol(9) + engine.getMassMol(10) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Regional Scale").put("fresh water",(
				environment.getEnvProps("REGIONAL","WATERflow.w1R.w0R") + 
				environment.getEnvProps("REGIONAL","WATERflow.w1R.w2R") )*
				(engine.getConcentration(12) + engine.getConcentration(13) + engine.getConcentration(14) )*
				input.getSubstancesData("Molweight")
				);		

		outflow.get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2R.w2C")
				*(engine.getMassMol(16) + engine.getMassMol(17) + engine.getMassMol(18) )*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Continental Scale", new HashMap< String, Double >() );		

		outflow.get("Continental Scale").put("air",(
				environment.getEnvProps("CONTINENTAL","k.aC.aM") + 
				environment.getEnvProps("CONTINENTAL","k.aC.aR") )*
				(engine.getMassMol(44) + engine.getMassMol(45) + engine.getMassMol(46))* 
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("fresh water lakes", (
				environment.getEnvProps("CONTINENTAL","k.w0C.w1C") + 
				environment.getEnvProps("CONTINENTAL","k.w0C.w2C") )*
				(engine.getMassMol(51) + engine.getMassMol(52) + engine.getMassMol(53)) *
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.w1C.w0C") + 
				environment.getEnvProps("CONTINENTAL","k.w1C.w1R") +
				environment.getEnvProps("CONTINENTAL","k.w1C.w2C") )*
				(engine.getMassMol(55) + engine.getMassMol(56) + engine.getMassMol(57) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("CONTINENTAL","k.w2C.w2M") + 
				environment.getEnvProps("CONTINENTAL","k.w2C.w2R") )*
				(engine.getMassMol(59) + engine.getMassMol(60) + engine.getMassMol(61) )*
				input.getSubstancesData("Molweight")
				);


		outflow.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Moderate climate zone").put("air",(
				environment.getEnvProps("MODERATE","k.aM.aA") +
				environment.getEnvProps("MODERATE","k.aM.aC") +
				environment.getEnvProps("MODERATE","k.aM.aT") )*
				(engine.getMassMol(87) + engine.getMassMol(88) + engine.getMassMol(89) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Moderate climate zone").put("upper ocean water",(
				environment.getEnvProps("MODERATE","k.w2M.w2C") +
				environment.getEnvProps("MODERATE","k.w2M.w2A") +
				environment.getEnvProps("MODERATE","k.w2M.w2T") +
				environment.getEnvProps("MODERATE","k.w2M.w3M") )*
				(engine.getMassMol(94) + engine.getMassMol(95) + engine.getMassMol(96) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Moderate climate zone").put("deep sea",(
				environment.getEnvProps("MODERATE","k.w3M.w2M") +
				environment.getEnvProps("MODERATE","k.w3M.w3A") +
				environment.getEnvProps("MODERATE","k.w3M.w3T") )*
				(engine.getMassMol(98) + engine.getMassMol(99) + engine.getMassMol(100) )*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("ARCTIC","k.aA.aM")*
				(engine.getMassMol(110) + engine.getMassMol(111) + engine.getMassMol(112) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Arctic climate zone").put("upper ocean water",(
				environment.getEnvProps("ARCTIC","k.w2A.w2M") +
				environment.getEnvProps("ARCTIC","k.w2A.w3A") )*
				(engine.getMassMol(117) + engine.getMassMol(118) + engine.getMassMol(119) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Arctic climate zone").put("deep sea",(
				environment.getEnvProps("ARCTIC","k.w3A.w2A") +
				environment.getEnvProps("ARCTIC","k.w3A.w3M") )*
				(engine.getMassMol(121) + engine.getMassMol(122) + engine.getMassMol(123) )*
				input.getSubstancesData("Molweight")
				);

		outflow.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		outflow.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("TROPICAL","k.aT.aM")*
				(engine.getMassMol(133) + engine.getMassMol(134) + engine.getMassMol(135) )*
				input.getSubstancesData("Molweight")				
				);

		outflow.get("Global Scale - Tropical climate zone").put("upper ocean water",(
				environment.getEnvProps("TROPICAL","k.w2T.w2M") +
				environment.getEnvProps("TROPICAL","k.w2T.w3T") )*
				(engine.getMassMol(140) + engine.getMassMol(141) + engine.getMassMol(142) )*
				input.getSubstancesData("Molweight")
				);

		outflow.get("Global Scale - Tropical climate zone").put("deep sea",(
				environment.getEnvProps("TROPICAL","k.w3T.w2T") +
				environment.getEnvProps("TROPICAL","k.w3T.w3M") )*
				(engine.getMassMol(144) + engine.getMassMol(145) + engine.getMassMol(146) )*
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
				engine.getConcentration(23)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","NETsedrate.w2R")*
				environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
				environment.getEnvProps("REGIONAL","AREAFRAC.w2R")*
				engine.getConcentration(27)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("natural soil", 
				(environment.getEnvProps("REGIONAL","FRACinf.s1R")*
						input.getLandscapeSettings("REGIONAL", "RAINrate.R")/
						environment.getEnvProps("REGIONAL","Ks1w.R")*
						environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
						environment.getEnvProps("REGIONAL","AREAFRAC.s1R")*
						environment.getEnvProps("REGIONAL","CORRleach.s1R"))*
				engine.getConcentration(31)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("agricultural soil", 
				(environment.getEnvProps("REGIONAL","FRACinf.s2R")*
						input.getLandscapeSettings("REGIONAL", "RAINrate.R")/
						environment.getEnvProps("REGIONAL","Ks2w.R")*
						environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
						environment.getEnvProps("REGIONAL","AREAFRAC.s2R")*
						environment.getEnvProps("REGIONAL","CORRleach.s2R"))*
				engine.getConcentration(35)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("other soil", 
				(environment.getEnvProps("REGIONAL","FRACinf.s3R")*
						input.getLandscapeSettings("REGIONAL", "RAINrate.R")/
						environment.getEnvProps("REGIONAL","Ks1w.R")*
						environment.getEnvProps("REGIONAL","SYSTEMAREA.R")*
						environment.getEnvProps("REGIONAL","AREAFRAC.s3R")*
						environment.getEnvProps("REGIONAL","CORRleach.s3R"))*
				engine.getConcentration(39)*
				input.getSubstancesData("Molweight")
				);

		removal.put("Continental Scale", new HashMap< String, Double >() );		
		removal.get("Continental Scale").put("air", 
				environment.getEnvProps("CONTINENTAL","kesc.aC")*
				environment.getEnvProps("CONTINENTAL","VOLUME.aC")
				*engine.getConcentration(43)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","NETsedrate.w1C")*
				environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
				environment.getEnvProps("CONTINENTAL","AREAFRAC.w1C")*
				engine.getConcentration(66)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","NETsedrate.w2C")*
				environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
				environment.getEnvProps("CONTINENTAL","AREAFRAC.w2C")*
				engine.getConcentration(70)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("natural soil", 
				(environment.getEnvProps("CONTINENTAL","FRACinf.s1C")*
						input.getLandscapeSettings("CONTINENTAL", "RAINrate.C")/
						environment.getEnvProps("CONTINENTAL","Ks1w.C")*
						environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
						environment.getEnvProps("CONTINENTAL","AREAFRAC.s1C")*
						environment.getEnvProps("CONTINENTAL","CORRleach.s1C"))*
				engine.getConcentration(74)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("agricultural soil", 
				(environment.getEnvProps("CONTINENTAL","FRACinf.s2C")*
						input.getLandscapeSettings("CONTINENTAL", "RAINrate.C")/
						environment.getEnvProps("CONTINENTAL","Ks2w.C")*
						environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
						environment.getEnvProps("CONTINENTAL","AREAFRAC.s2C")*
						environment.getEnvProps("CONTINENTAL","CORRleach.s2C"))*
				engine.getConcentration(78)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("other soil", 
				(environment.getEnvProps("CONTINENTAL","FRACinf.s3C")*
						input.getLandscapeSettings("CONTINENTAL", "RAINrate.C")/
						environment.getEnvProps("CONTINENTAL","Ks3w.C")*
						environment.getEnvProps("CONTINENTAL","SYSTEMAREA.C")*
						environment.getEnvProps("CONTINENTAL","AREAFRAC.s3C")*
						environment.getEnvProps("CONTINENTAL","CORRleach.s3C"))*
				engine.getConcentration(82)*
				input.getSubstancesData("Molweight")
				);


		removal.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Moderate climate zone").put("air",
				environment.getEnvProps("MODERATE","kesc.aM")*
				environment.getEnvProps("MODERATE","VOLUME.aM")*
				engine.getConcentration(86)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Moderate climate zone").put("ocean sediment",
				environment.getEnvProps("MODERATE","NETsedrate.wM")*
				environment.getEnvProps("MODERATE","SYSTEMAREA.M")*
				environment.getEnvProps("MODERATE","AREAFRAC.wM")*
				engine.getConcentration(101)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Moderate climate zone").put("soil",
				(environment.getEnvProps("MODERATE","FRACinf.sM")*
						environment.getEnvProps("MODERATE", "RAINrate.M")/
						environment.getEnvProps("MODERATE","Ksw.M")*
						environment.getEnvProps("MODERATE","SYSTEMAREA.M")*
						environment.getEnvProps("MODERATE","AREAFRAC.sM")*
						environment.getEnvProps("MODERATE","CORRleach.sM"))*
				engine.getConcentration(105)*
				input.getSubstancesData("Molweight")
				);


		removal.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("ARCTIC","kesc.aA")*
				environment.getEnvProps("ARCTIC","VOLUME.aA")*
				engine.getConcentration(109)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Arctic climate zone").put("ocean sediment",
				environment.getEnvProps("ARCTIC","NETsedrate.wA")*
				environment.getEnvProps("ARCTIC","SYSTEMAREA.A")*
				environment.getEnvProps("ARCTIC","AREAFRAC.wA")*
				engine.getConcentration(124)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Arctic climate zone").put("soil",
				(environment.getEnvProps("ARCTIC","FRACinf.sA")*
						environment.getEnvProps("ARCTIC", "RAINrate.A")/
						environment.getEnvProps("ARCTIC","Ksw.A")*
						environment.getEnvProps("ARCTIC","SYSTEMAREA.A")*
						environment.getEnvProps("ARCTIC","AREAFRAC.sA")*
						environment.getEnvProps("ARCTIC","CORRleach.sA"))*
				engine.getConcentration(128)*
				input.getSubstancesData("Molweight")
				);

		removal.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("TROPICAL","kesc.aT")*
				environment.getEnvProps("TROPICAL","VOLUME.aT")*
				engine.getConcentration(132)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Tropical climate zone").put("ocean sediment",
				environment.getEnvProps("TROPICAL","NETsedrate.wT")*
				environment.getEnvProps("TROPICAL","SYSTEMAREA.T")*
				environment.getEnvProps("TROPICAL","AREAFRAC.wT")*
				engine.getConcentration(147)*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Tropical climate zone").put("soil",
				(environment.getEnvProps("TROPICAL","FRACinf.sT")*						
						environment.getEnvProps("TROPICAL","RAINrate.T")/
						environment.getEnvProps("TROPICAL","Ksw.T")*
						environment.getEnvProps("TROPICAL","SYSTEMAREA.T")*
						environment.getEnvProps("TROPICAL","AREAFRAC.sT")*
						environment.getEnvProps("TROPICAL","CORRleach.sT"))*
				engine.getConcentration(151)*
				input.getSubstancesData("Molweight")
				);
	}

	void buildRemovalNano()
	{
		removal.clear();

		removal.put("Regional Scale", new HashMap< String, Double >() );		
		removal.get("Regional Scale").put("air", 
				environment.getEnvProps("REGIONAL","kesc.aR")*
				environment.getEnvProps("REGIONAL","VOLUME.aR")*
				(engine.getConcentration(1) + engine.getConcentration(2) + engine.getConcentration(3) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("fresh water sediment", 
				environment.getEnvProps("REGIONAL","kbur.sd1R")*
				(engine.getMassMol(24) + engine.getMassMol(25) + engine.getMassMol(26) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","kbur.sd2R")*
				(engine.getMassMol(28) + engine.getMassMol(29) + engine.getMassMol(30) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("natural soil", 
				environment.getEnvProps("REGIONAL","kleach.s1R")*
				(engine.getMassMol(32) + engine.getMassMol(33))*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("agricultural soil", 
				environment.getEnvProps("REGIONAL","kleach.s2R")*
				(engine.getMassMol(36) + engine.getMassMol(37) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","kleach.s3R")*
				(engine.getMassMol(40) + engine.getMassMol(41) )*
				input.getSubstancesData("Molweight")
				);

		removal.put("Continental Scale", new HashMap< String, Double >() );		
		removal.get("Continental Scale").put("air", 
				environment.getEnvProps("CONTINENTAL","kesc.aC")*
				environment.getEnvProps("CONTINENTAL","VOLUME.aC")*
				(engine.getConcentration(44) + engine.getConcentration(45) + engine.getConcentration(46) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","kbur.sd1C")*
				(engine.getMassMol(67) + engine.getMassMol(68) + engine.getMassMol(69) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","kbur.sd2C")*
				(engine.getMassMol(71) + engine.getMassMol(72) + engine.getMassMol(73) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("natural soil", 
				environment.getEnvProps("CONTINENTAL","kleach.s1C")*
				(engine.getMassMol(75) + engine.getMassMol(76) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","kleach.s2C")*
				(engine.getMassMol(79) + engine.getMassMol(80) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","kleach.s3C")*
				(engine.getMassMol(83) + engine.getMassMol(84) )*
				input.getSubstancesData("Molweight")
				);

		removal.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Moderate climate zone").put("air",
				environment.getEnvProps("MODERATE","kesc.aM")*
				environment.getEnvProps("MODERATE","VOLUME.aM")*
				(engine.getConcentration(87) + engine.getConcentration(88) + engine.getConcentration(89) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Moderate climate zone").put("ocean sediment",
				environment.getEnvProps("MODERATE","kbur.sdM")*
				(engine.getMassMol(102) + engine.getMassMol(103) + engine.getMassMol(104) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Moderate climate zone").put("soil",
				environment.getEnvProps("MODERATE","kleach.sM")*
				(engine.getMassMol(106) + engine.getMassMol(107) )*
				input.getSubstancesData("Molweight")
				);


		removal.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Arctic climate zone").put("air",
				environment.getEnvProps("ARCTIC","kesc.aA")*
				environment.getEnvProps("ARCTIC","VOLUME.aA")*
				(engine.getConcentration(110) + engine.getConcentration(111) + engine.getConcentration(112) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Arctic climate zone").put("ocean sediment",
				environment.getEnvProps("ARCTIC","kbur.sdA")*
				(engine.getMassMol(125) + engine.getMassMol(126) + engine.getMassMol(127) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Arctic climate zone").put("soil",
				environment.getEnvProps("ARCTIC","kleach.sA")*
				(engine.getMassMol(129) + engine.getMassMol(130) )*
				input.getSubstancesData("Molweight")
				);

		removal.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		removal.get("Global Scale - Tropical climate zone").put("air",
				environment.getEnvProps("TROPICAL","kesc.aT")*
				environment.getEnvProps("TROPICAL","VOLUME.aT")*
				(engine.getConcentration(133) + engine.getConcentration(134) + engine.getConcentration(135) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Tropical climate zone").put("ocean sediment",
				environment.getEnvProps("TROPICAL","kbur.sdT")*
				(engine.getMassMol(148) + engine.getMassMol(149) + engine.getMassMol(150) )*
				input.getSubstancesData("Molweight")
				);

		removal.get("Global Scale - Tropical climate zone").put("soil",
				environment.getEnvProps("TROPICAL","kleach.sT")*						
				(engine.getMassMol(152) + engine.getMassMol(153) )*
				input.getSubstancesData("Molweight")
				);
	}

	void buildFormationNano() //This is exactly the same as buildFormation()
	{
		formation.put("Regional Scale", new HashMap< String, Double >() );		
		formation.get("Regional Scale").put("fresh water lakes", ( 
				Double.valueOf( nanoData.get("kdis.w0S.w0D") )*engine.getMassMol(8)  +
				Double.valueOf( nanoData.get("kdis.w0A.w0D") )*engine.getMassMol(9) +
				Double.valueOf( nanoData.get("kdis.w0P.w0D") )*engine.getMassMol(10) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("fresh water", ( 
				Double.valueOf( nanoData.get("kdis.w1S.w1D") )*engine.getMassMol(12)  +
				Double.valueOf( nanoData.get("kdis.w1A.w1D") )*engine.getMassMol(13) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(14) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("coastal sea water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(16)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(17) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(18) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("fresh water sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd1S.sd1D") )*engine.getMassMol(24)  +
				Double.valueOf( nanoData.get("kdis.sd1A.sd1D") )*engine.getMassMol(25) +
				Double.valueOf( nanoData.get("kdis.sd1P.sd1D") )*engine.getMassMol(26) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("coastal marine sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(28)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(29) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(30) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("natural soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(32)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(33) +
				Double.valueOf( nanoData.get("kdis.s1P.s1D") )*engine.getMassMol(34) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("agricultural soil", ( 
				Double.valueOf( nanoData.get("kdis.s2S.s2D") )*engine.getMassMol(36)  +
				Double.valueOf( nanoData.get("kdis.s2A.s2D") )*engine.getMassMol(37) +
				Double.valueOf( nanoData.get("kdis.s2P.s2D") )*engine.getMassMol(38) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("other soil", ( 
				Double.valueOf( nanoData.get("kdis.s3S.s3D") )*engine.getMassMol(40)  +
				Double.valueOf( nanoData.get("kdis.s3A.s3D") )*engine.getMassMol(41) +
				Double.valueOf( nanoData.get("kdis.s3P.s3D") )*engine.getMassMol(42) )*
				input.getSubstancesData("Molweight")
				);



		formation.put("Continental Scale", new HashMap< String, Double >() );		
		formation.get("Continental Scale").put("fresh water lakes", ( 
				Double.valueOf( nanoData.get("kdis.w0S.w0D") )*engine.getMassMol(51)  +
				Double.valueOf( nanoData.get("kdis.w0A.w0D") )*engine.getMassMol(52) +
				Double.valueOf( nanoData.get("kdis.w0P.w0D") )*engine.getMassMol(53) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("fresh water", ( 
				Double.valueOf( nanoData.get("kdis.w1S.w1D") )*engine.getMassMol(55)  +
				Double.valueOf( nanoData.get("kdis.w1A.w1D") )*engine.getMassMol(56) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(57) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("coastal sea water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(59)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(60) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(61) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("fresh water sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd1S.sd1D") )*engine.getMassMol(67)  +
				Double.valueOf( nanoData.get("kdis.sd1A.sd1D") )*engine.getMassMol(68) +
				Double.valueOf( nanoData.get("kdis.sd1P.sd1D") )*engine.getMassMol(69) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("coastal marine sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(71)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(72) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(73) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("natural soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(75)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(76) +
				Double.valueOf( nanoData.get("kdis.s1P.s1D") )*engine.getMassMol(77) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("agricultural soil", ( 
				Double.valueOf( nanoData.get("kdis.s2S.s2D") )*engine.getMassMol(79)  +
				Double.valueOf( nanoData.get("kdis.s2A.s2D") )*engine.getMassMol(80) +
				Double.valueOf( nanoData.get("kdis.s2P.s2D") )*engine.getMassMol(81) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("other soil", ( 
				Double.valueOf( nanoData.get("kdis.s3S.s3D") )*engine.getMassMol(83)  +
				Double.valueOf( nanoData.get("kdis.s3A.s3D") )*engine.getMassMol(84) +
				Double.valueOf( nanoData.get("kdis.s3P.s3D") )*engine.getMassMol(85) )*
				input.getSubstancesData("Molweight")
				);


		formation.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		formation.get("Global Scale - Moderate climate zone").put("upper ocean water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(94)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(95) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(96) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Moderate climate zone").put("deep sea", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(98)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(99) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(100) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Moderate climate zone").put("ocean sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(102)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(103) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(104) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Moderate climate zone").put("soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(106)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(107) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(108) )*
				input.getSubstancesData("Molweight")
				);


		formation.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		formation.get("Global Scale - Arctic climate zone").put("upper ocean water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(117)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(118) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(119) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Arctic climate zone").put("deep sea", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(121)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(122) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(123) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Arctic climate zone").put("ocean sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(125)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(126) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(127) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Arctic climate zone").put("soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(129)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(130) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(131) )*
				input.getSubstancesData("Molweight")
				);

		formation.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		formation.get("Global Scale - Tropical climate zone").put("upper ocean water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(140)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(141) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(142) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Tropical climate zone").put("deep sea", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(144)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(145) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(146) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Tropical climate zone").put("ocean sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(148)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(149) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(150) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Tropical climate zone").put("soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(152)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(153) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(154) )*
				input.getSubstancesData("Molweight")
				);		
	}


	void buildFormation()
	{
		formation.put("Regional Scale", new HashMap< String, Double >() );		
		formation.get("Regional Scale").put("fresh water lakes", ( 
				Double.valueOf( nanoData.get("kdis.w0S.w0D") )*engine.getMassMol(8)  +
				Double.valueOf( nanoData.get("kdis.w0A.w0D") )*engine.getMassMol(9) +
				Double.valueOf( nanoData.get("kdis.w0P.w0D") )*engine.getMassMol(10) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("fresh water", ( 
				Double.valueOf( nanoData.get("kdis.w1S.w1D") )*engine.getMassMol(12)  +
				Double.valueOf( nanoData.get("kdis.w1A.w1D") )*engine.getMassMol(13) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(14) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("coastal sea water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(16)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(17) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(18) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("fresh water sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd1S.sd1D") )*engine.getMassMol(24)  +
				Double.valueOf( nanoData.get("kdis.sd1A.sd1D") )*engine.getMassMol(25) +
				Double.valueOf( nanoData.get("kdis.sd1P.sd1D") )*engine.getMassMol(26) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("coastal marine sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(28)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(29) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(30) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("natural soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(32)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(33) +
				Double.valueOf( nanoData.get("kdis.s1P.s1D") )*engine.getMassMol(34) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("agricultural soil", ( 
				Double.valueOf( nanoData.get("kdis.s2S.s2D") )*engine.getMassMol(36)  +
				Double.valueOf( nanoData.get("kdis.s2A.s2D") )*engine.getMassMol(37) +
				Double.valueOf( nanoData.get("kdis.s2P.s2D") )*engine.getMassMol(38) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Regional Scale").put("other soil", ( 
				Double.valueOf( nanoData.get("kdis.s3S.s3D") )*engine.getMassMol(40)  +
				Double.valueOf( nanoData.get("kdis.s3A.s3D") )*engine.getMassMol(41) +
				Double.valueOf( nanoData.get("kdis.s3P.s3D") )*engine.getMassMol(42) )*
				input.getSubstancesData("Molweight")
				);



		formation.put("Continental Scale", new HashMap< String, Double >() );		
		formation.get("Continental Scale").put("fresh water lakes", ( 
				Double.valueOf( nanoData.get("kdis.w0S.w0D") )*engine.getMassMol(51)  +
				Double.valueOf( nanoData.get("kdis.w0A.w0D") )*engine.getMassMol(52) +
				Double.valueOf( nanoData.get("kdis.w0P.w0D") )*engine.getMassMol(53) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("fresh water", ( 
				Double.valueOf( nanoData.get("kdis.w1S.w1D") )*engine.getMassMol(55)  +
				Double.valueOf( nanoData.get("kdis.w1A.w1D") )*engine.getMassMol(56) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(57) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("coastal sea water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(59)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(60) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(61) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("fresh water sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd1S.sd1D") )*engine.getMassMol(67)  +
				Double.valueOf( nanoData.get("kdis.sd1A.sd1D") )*engine.getMassMol(68) +
				Double.valueOf( nanoData.get("kdis.sd1P.sd1D") )*engine.getMassMol(69) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("coastal marine sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(71)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(72) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(73) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("natural soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(75)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(76) +
				Double.valueOf( nanoData.get("kdis.s1P.s1D") )*engine.getMassMol(77) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("agricultural soil", ( 
				Double.valueOf( nanoData.get("kdis.s2S.s2D") )*engine.getMassMol(79)  +
				Double.valueOf( nanoData.get("kdis.s2A.s2D") )*engine.getMassMol(80) +
				Double.valueOf( nanoData.get("kdis.s2P.s2D") )*engine.getMassMol(81) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Continental Scale").put("other soil", ( 
				Double.valueOf( nanoData.get("kdis.s3S.s3D") )*engine.getMassMol(83)  +
				Double.valueOf( nanoData.get("kdis.s3A.s3D") )*engine.getMassMol(84) +
				Double.valueOf( nanoData.get("kdis.s3P.s3D") )*engine.getMassMol(85) )*
				input.getSubstancesData("Molweight")
				);


		formation.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		formation.get("Global Scale - Moderate climate zone").put("upper ocean water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(94)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(95) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(96) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Moderate climate zone").put("deep sea", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(98)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(99) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(100) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Moderate climate zone").put("ocean sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(102)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(103) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(104) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Moderate climate zone").put("soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(106)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(107) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(108) )*
				input.getSubstancesData("Molweight")
				);


		formation.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		formation.get("Global Scale - Arctic climate zone").put("upper ocean water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(117)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(118) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(119) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Arctic climate zone").put("deep sea", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(121)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(122) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(123) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Arctic climate zone").put("ocean sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(125)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(126) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(127) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Arctic climate zone").put("soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(129)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(130) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(131) )*
				input.getSubstancesData("Molweight")
				);

		formation.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		formation.get("Global Scale - Tropical climate zone").put("upper ocean water", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(140)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(141) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(142) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Tropical climate zone").put("deep sea", ( 
				Double.valueOf( nanoData.get("kdis.w2S.w2D") )*engine.getMassMol(144)  +
				Double.valueOf( nanoData.get("kdis.w2A.w2D") )*engine.getMassMol(145) +
				Double.valueOf( nanoData.get("kdis.w2P.w2D") )*engine.getMassMol(146) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Tropical climate zone").put("ocean sediment", ( 
				Double.valueOf( nanoData.get("kdis.sd2S.sd2D") )*engine.getMassMol(148)  +
				Double.valueOf( nanoData.get("kdis.sd2A.sd2D") )*engine.getMassMol(149) +
				Double.valueOf( nanoData.get("kdis.sd2P.sd2D") )*engine.getMassMol(150) )*
				input.getSubstancesData("Molweight")
				);

		formation.get("Global Scale - Tropical climate zone").put("soil", ( 
				Double.valueOf( nanoData.get("kdis.s1S.s1D") )*engine.getMassMol(152)  +
				Double.valueOf( nanoData.get("kdis.s1A.s1D") )*engine.getMassMol(153) +
				Double.valueOf( nanoData.get("kdis.w1P.w1D") )*engine.getMassMol(154) )*
				input.getSubstancesData("Molweight")
				);		
	}

	void buildDegradationNano() 
	{
		degradation.put("Regional Scale", new HashMap< String, Double >() );		

		degradation.get("Regional Scale").put("air", 
				input.getSubstancesData("kdegS.a")*engine.getConcentration(1)* 
				input.getSubstancesData("kdegA.a")*engine.getConcentration(2)* 
				input.getSubstancesData("kdegP.a")*engine.getConcentration(3)* 
				environment.getEnvProps("REGIONAL","VOLUME.aR")*input.getSubstancesData("Molweight")				
				);

		degradation.get("Regional Scale").put("fresh water lakes",  
				input.getSubstancesData("kdegS.w0")*engine.getMassMol(8)* 
				input.getSubstancesData("kdegA.w0")*engine.getMassMol(9)* 
				input.getSubstancesData("kdegP.w0")*engine.getMassMol(10)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Regional Scale").put("fresh water", 				
				input.getSubstancesData("kdegS.w1")*engine.getMassMol(12)* 
				input.getSubstancesData("kdegA.w1")*engine.getMassMol(13)* 
				input.getSubstancesData("kdegP.w1")*engine.getMassMol(14)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Regional Scale").put("coastal sea water",  
				input.getSubstancesData("kdegS.w2")*engine.getMassMol(12)* 
				input.getSubstancesData("kdegA.w2")*engine.getMassMol(13)* 
				input.getSubstancesData("kdegP.w2")*engine.getMassMol(14)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Regional Scale").put("fresh water sediment",  
				input.getSubstancesData("kdegS.sd1")*engine.getMassMol(24)* 
				input.getSubstancesData("kdegA.sd1")*engine.getMassMol(25)* 
				input.getSubstancesData("kdegP.sd1")*engine.getMassMol(26)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Regional Scale").put("coastal marine sediment",  
				input.getSubstancesData("kdegS.sd2")*engine.getMassMol(28)* 
				input.getSubstancesData("kdegA.sd2")*engine.getMassMol(29)* 
				input.getSubstancesData("kdegP.sd2")*engine.getMassMol(30)* 
				input.getSubstancesData("Molweight")		
				);

		degradation.get("Regional Scale").put("natural soil",  
				input.getSubstancesData("kdegS.s1")*engine.getMassMol(32)* 
				input.getSubstancesData("kdegA.s1")*engine.getMassMol(33)* 
				input.getSubstancesData("kdegP.s1")*engine.getMassMol(34)* 
				input.getSubstancesData("Molweight")		
				);

		degradation.get("Regional Scale").put("agricultural soil",  
				input.getSubstancesData("kdegS.s2")*engine.getMassMol(36)* 
				input.getSubstancesData("kdegA.s2")*engine.getMassMol(37)* 
				input.getSubstancesData("kdegP.s2")*engine.getMassMol(38)* 
				input.getSubstancesData("Molweight")		
				);

		degradation.get("Regional Scale").put("other soil", 
				input.getSubstancesData("kdegS.s2")*engine.getMassMol(40)* 
				input.getSubstancesData("kdegA.s2")*engine.getMassMol(41)* 
				input.getSubstancesData("kdegP.s2")*engine.getMassMol(42)* 
				input.getSubstancesData("Molweight")		
				);


		degradation.put("Continental Scale", new HashMap< String, Double >() );		

		degradation.get("Continental Scale").put("air", 
				input.getSubstancesData("kdegS.a")*engine.getConcentration(44)* 
				input.getSubstancesData("kdegA.a")*engine.getConcentration(45)* 
				input.getSubstancesData("kdegP.a")*engine.getConcentration(46)* 
				environment.getEnvProps("CONTINENTAL","VOLUME.aC")*input.getSubstancesData("Molweight")						);

		degradation.get("Continental Scale").put("fresh water lakes",  
				input.getSubstancesData("kdegS.w0")*engine.getMassMol(51)* 
				input.getSubstancesData("kdegA.w0")*engine.getMassMol(52)* 
				input.getSubstancesData("kdegP.w0")*engine.getMassMol(53)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("fresh water", 				
				input.getSubstancesData("kdegS.w1")*engine.getMassMol(55)* 
				input.getSubstancesData("kdegA.w1")*engine.getMassMol(56)* 
				input.getSubstancesData("kdegP.w1")*engine.getMassMol(57)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("coastal sea water",  
				input.getSubstancesData("kdegS.w2")*engine.getMassMol(59)* 
				input.getSubstancesData("kdegA.w2")*engine.getMassMol(60)* 
				input.getSubstancesData("kdegP.w2")*engine.getMassMol(61)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("fresh water sediment",  
				input.getSubstancesData("kdegS.sd1")*engine.getMassMol(67)* 
				input.getSubstancesData("kdegA.sd1")*engine.getMassMol(68)* 
				input.getSubstancesData("kdegP.sd1")*engine.getMassMol(69)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("coastal marine sediment",  
				input.getSubstancesData("kdegS.sd2")*engine.getMassMol(71)* 
				input.getSubstancesData("kdegA.sd2")*engine.getMassMol(72)* 
				input.getSubstancesData("kdegP.sd2")*engine.getMassMol(73)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("natural soil",  
				input.getSubstancesData("kdegS.s1")*engine.getMassMol(75)* 
				input.getSubstancesData("kdegA.s1")*engine.getMassMol(76)* 
				input.getSubstancesData("kdegP.s1")*engine.getMassMol(77)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("agricultural soil",  
				input.getSubstancesData("kdegS.s2")*engine.getMassMol(79)* 
				input.getSubstancesData("kdegA.s2")*engine.getMassMol(80)* 
				input.getSubstancesData("kdegP.s2")*engine.getMassMol(81)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.get("Continental Scale").put("other soil", 
				input.getSubstancesData("kdegS.s3")*engine.getMassMol(83)* 
				input.getSubstancesData("kdegA.s3")*engine.getMassMol(84)* 
				input.getSubstancesData("kdegP.s3")*engine.getMassMol(85)* 
				input.getSubstancesData("Molweight")				
				);

		degradation.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Moderate climate zone").put("air", 
				( input.getSubstancesData("kdegS.a")*engine.getConcentration(87) +
						input.getSubstancesData("kdegA.a")*engine.getConcentration(88) +
						input.getSubstancesData("kdegP.a")*engine.getConcentration(89) )* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("upper ocean water",  
				( input.getSubstancesData("kdegS.w2")*engine.getConcentration(94) +
						input.getSubstancesData("kdegA.w2")*engine.getConcentration(95) +
						input.getSubstancesData("kdegP.w2")*engine.getConcentration(96) )* 
				environment.getEnvProps("MODERATE","VOLUME.w2M")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("deep sea",  
				(input.getSubstancesData("kdegS.w3")*engine.getConcentration(98) +
						input.getSubstancesData("kdegA.w3")*engine.getConcentration(99) +
						input.getSubstancesData("kdegP.w3")*engine.getConcentration(100) )* 
				environment.getEnvProps("MODERATE","VOLUME.w3M")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("ocean sediment",
				(input.getSubstancesData("kdegS.sd2")*engine.getConcentration(102) +
						input.getSubstancesData("kdegA.sd2")*engine.getConcentration(103) +
						input.getSubstancesData("kdegP.sd2")*engine.getConcentration(104) )* 
				environment.getEnvProps("MODERATE","VOLUME.sdM")*input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Moderate climate zone").put("soil",  
				(input.getSubstancesData("kdegS.s1")*engine.getConcentration(106) +
						input.getSubstancesData("kdegA.s1")*engine.getConcentration(107) +
						input.getSubstancesData("kdegP.s1")*engine.getConcentration(108) )* 
				environment.getEnvProps("MODERATE","VOLUME.sM")*input.getSubstancesData("Molweight")
				);		

		degradation.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Arctic climate zone").put("air", 
				input.getSubstancesData("kdegS.a")*engine.getConcentration(110)* 
				input.getSubstancesData("kdegA.a")*engine.getConcentration(111)* 
				input.getSubstancesData("kdegP.a")*engine.getConcentration(112)* 
				environment.getEnvProps("ARCTIC","VOLUME.aA")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("upper ocean water",  
				input.getSubstancesData("kdegS.w2")*engine.getConcentration(117)* 
				input.getSubstancesData("kdegA.w2")*engine.getConcentration(118)* 
				input.getSubstancesData("kdegP.w2")*engine.getConcentration(119)* 
				environment.getEnvProps("ARCTIC","VOLUME.w2A")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("deep sea",  
				input.getSubstancesData("kdegS.w3")*engine.getConcentration(121)* 
				input.getSubstancesData("kdegA.w3")*engine.getConcentration(122)* 
				input.getSubstancesData("kdegP.w3")*engine.getConcentration(123)* 
				environment.getEnvProps("ARCTIC","VOLUME.w3A")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("ocean sediment",
				input.getSubstancesData("kdegS.sd2")*engine.getConcentration(125)* 
				input.getSubstancesData("kdegA.sd2")*engine.getConcentration(126)* 
				input.getSubstancesData("kdegP.sd2")*engine.getConcentration(127)* 
				environment.getEnvProps("ARCTIC","VOLUME.sdA")*input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Arctic climate zone").put("soil",  
				input.getSubstancesData("kdegS.s1")*engine.getConcentration(129)* 
				input.getSubstancesData("kdegA.s1")*engine.getConcentration(130)* 
				input.getSubstancesData("kdegP.s1")*engine.getConcentration(131)* 
				environment.getEnvProps("ARCTIC","VOLUME.sA")*input.getSubstancesData("Molweight")
				);	

		degradation.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Tropical climate zone").put("air", 
				input.getSubstancesData("kdegS.a")*engine.getConcentration(133)*
				input.getSubstancesData("kdegA.a")*engine.getConcentration(134)*
				input.getSubstancesData("kdegP.a")*engine.getConcentration(135)*
				environment.getEnvProps("TROPICAL","VOLUME.aT")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("upper ocean water",  
				input.getSubstancesData("kdegS.w2")*engine.getConcentration(140)*
				input.getSubstancesData("kdegA.w2")*engine.getConcentration(141)*
				input.getSubstancesData("kdegP.w2")*engine.getConcentration(142)*
				environment.getEnvProps("TROPICAL","VOLUME.w2T")*input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("deep sea",  
				input.getSubstancesData("kdegS.w3")*engine.getConcentration(144)*
				input.getSubstancesData("kdegA.w3")*engine.getConcentration(145)*
				input.getSubstancesData("kdegP.w3")*engine.getConcentration(146)*
				environment.getEnvProps("TROPICAL","VOLUME.w3T")*input.getSubstancesData("Molweight")			
				);

		degradation.get("Global Scale - Tropical climate zone").put("ocean sediment",
				input.getSubstancesData("kdegS.sd2")*engine.getConcentration(148)*
				input.getSubstancesData("kdegA.sd2")*engine.getConcentration(149)*
				input.getSubstancesData("kdegP.sd2")*engine.getConcentration(150)*
				environment.getEnvProps("TROPICAL","VOLUME.sdT")*input.getSubstancesData("Molweight")			
				);		

		degradation.get("Global Scale - Tropical climate zone").put("soil",  
				input.getSubstancesData("kdegS.s1")*engine.getConcentration(152)*
				input.getSubstancesData("kdegA.s1")*engine.getConcentration(153)*
				input.getSubstancesData("kdegP.s1")*engine.getConcentration(154)*
				environment.getEnvProps("TROPICAL","VOLUME.sT")*input.getSubstancesData("Molweight")			
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
				engine.getMassMol(7)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("fresh water", 				
				environment.getEnvProps("REGIONAL","KDEG.w1R")*
				environment.getEnvProps("REGIONAL","VOLUME.w1R")*
				engine.getConcentration(11)* 
				input.getSubstancesData("Molweight")

				);

		degradation.get("Regional Scale").put("coastal sea water",  
				environment.getEnvProps("REGIONAL","KDEG.w2R")*
				engine.getMassMol(15)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("fresh water sediment",  
				environment.getEnvProps("REGIONAL","KDEG.sd1R")*
				environment.getEnvProps("REGIONAL","VOLUME.sd1R")*
				engine.getConcentration(23)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("coastal marine sediment",  
				environment.getEnvProps("REGIONAL","KDEG.sd2R")*
				environment.getEnvProps("REGIONAL","VOLUME.sd2R")*
				engine.getConcentration(27)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("natural soil",  
				environment.getEnvProps("REGIONAL","KDEG.s1R")*
				environment.getEnvProps("REGIONAL","VOLUME.s1R")*
				engine.getConcentration(31)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("agricultural soil",  
				environment.getEnvProps("REGIONAL","KDEG.s2R")*
				environment.getEnvProps("REGIONAL","VOLUME.s2R")*
				engine.getConcentration(35)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","KDEG.s3R")*
				environment.getEnvProps("REGIONAL","VOLUME.s3R")*
				engine.getConcentration(39)* 
				input.getSubstancesData("Molweight")
				);


		degradation.put("Continental Scale", new HashMap< String, Double >() );		

		degradation.get("Continental Scale").put("air", 
				environment.getEnvProps("CONTINENTAL","KDEG.aC")*
				environment.getEnvProps("CONTINENTAL","VOLUME.aC")*
				engine.getConcentration(43)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("fresh water lakes",  
				environment.getEnvProps("CONTINENTAL","KDEG.w0C")*
				engine.getMassMol(50)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("fresh water", 				
				environment.getEnvProps("CONTINENTAL","KDEG.w1C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.w1C")*
				engine.getConcentration(54)* 
				input.getSubstancesData("Molweight")

				);

		degradation.get("Continental Scale").put("coastal sea water",  
				environment.getEnvProps("CONTINENTAL","KDEG.w2C")*
				engine.getMassMol(58)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("fresh water sediment",  
				environment.getEnvProps("CONTINENTAL","KDEG.sd1C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.sd1C")*
				engine.getConcentration(66)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("coastal marine sediment",  
				environment.getEnvProps("CONTINENTAL","KDEG.sd2C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.sd2C")*
				engine.getConcentration(70)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("natural soil",  
				environment.getEnvProps("CONTINENTAL","KDEG.s1C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.s1C")*
				engine.getConcentration(74)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("agricultural soil",  
				environment.getEnvProps("CONTINENTAL","KDEG.s2C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.s2C")*
				engine.getConcentration(78)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","KDEG.s3C")*
				environment.getEnvProps("CONTINENTAL","VOLUME.s3C")*
				engine.getConcentration(82)* 
				input.getSubstancesData("Molweight")
				);

		degradation.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Moderate climate zone").put("air", 
				environment.getEnvProps("MODERATE","KDEG.aM")*
				environment.getEnvProps("MODERATE","VOLUME.aM")*
				engine.getConcentration(86)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("upper ocean water",  
				environment.getEnvProps("MODERATE","KDEG.w2M")*
				environment.getEnvProps("MODERATE","VOLUME.w2M")*
				engine.getConcentration(93)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("deep sea",  
				environment.getEnvProps("MODERATE","KDEG.w3M")*
				environment.getEnvProps("MODERATE","VOLUME.w3M")*
				engine.getConcentration(97)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Moderate climate zone").put("ocean sediment",
				environment.getEnvProps("MODERATE","KDEG.sdM")*
				environment.getEnvProps("MODERATE","VOLUME.sdM")*
				engine.getConcentration(101)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Moderate climate zone").put("soil",  
				environment.getEnvProps("MODERATE","KDEG.sM")*
				environment.getEnvProps("MODERATE","VOLUME.sM")*
				engine.getConcentration(105)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Arctic climate zone").put("air", 
				environment.getEnvProps("ARCTIC","KDEG.aA")*
				environment.getEnvProps("ARCTIC","VOLUME.aA")*
				engine.getConcentration(109)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("upper ocean water",  
				environment.getEnvProps("ARCTIC","KDEG.w2A")*
				environment.getEnvProps("ARCTIC","VOLUME.w2A")*
				engine.getConcentration(116)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("deep sea",  
				environment.getEnvProps("ARCTIC","KDEG.w3A")*
				environment.getEnvProps("ARCTIC","VOLUME.w3A")*
				engine.getConcentration(120)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Arctic climate zone").put("ocean sediment",
				environment.getEnvProps("ARCTIC","KDEG.sdA")*
				environment.getEnvProps("ARCTIC","VOLUME.sdA")*
				engine.getConcentration(124)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Arctic climate zone").put("soil",  
				environment.getEnvProps("ARCTIC","KDEG.sA")*
				environment.getEnvProps("ARCTIC","VOLUME.sA")*
				engine.getConcentration(128)* 
				input.getSubstancesData("Molweight")
				);	

		degradation.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );		
		degradation.get("Global Scale - Tropical climate zone").put("air", 
				environment.getEnvProps("TROPICAL","KDEG.aT")*
				environment.getEnvProps("TROPICAL","VOLUME.aT")*
				engine.getConcentration(132)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("upper ocean water",  
				environment.getEnvProps("TROPICAL","KDEG.w2T")*
				environment.getEnvProps("TROPICAL","VOLUME.w2T")*
				engine.getConcentration(139)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("deep sea",  
				environment.getEnvProps("TROPICAL","KDEG.w3T")*
				environment.getEnvProps("TROPICAL","VOLUME.w3T")*
				engine.getConcentration(143)* 
				input.getSubstancesData("Molweight")
				);

		degradation.get("Global Scale - Tropical climate zone").put("ocean sediment",
				environment.getEnvProps("TROPICAL","KDEG.sdT")*
				environment.getEnvProps("TROPICAL","VOLUME.sdT")*
				engine.getConcentration(147)* 
				input.getSubstancesData("Molweight")
				);		

		degradation.get("Global Scale - Tropical climate zone").put("soil",  
				environment.getEnvProps("TROPICAL","KDEG.sT")*
				environment.getEnvProps("TROPICAL","VOLUME.sT")*
				engine.getConcentration(151)* 
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
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.aCG.w1CD")*
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.aCG.w2CD")*
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.aMG.w2MD")*
				engine.getMassMol(86)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.aAG.w2AD")*
				engine.getMassMol(109)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.aTG.w2TD")*
				engine.getMassMol(132)*
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
				engine.getMassMol(7)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.w1RD.aRG")*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2RD.aRG")*
				engine.getMassMol(15)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Continental Scale").put("fresh water lakes", 
				environment.getEnvProps("CONTINENTAL","k.w0CD.aCG")*
				engine.getMassMol(50)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.w1CD.aCG")*
				engine.getMassMol(54)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.w2CD.aCG")*
				engine.getMassMol(58)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.w2MD.aMG")*
				engine.getMassMol(93)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.w2AD.aAG")*
				engine.getMassMol(116)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-air").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.w2TD.aTG")*
				engine.getMassMol(139)*
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
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","k.aCG.s2CD")*
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","k.aCG.s3CD")*
				engine.getMassMol(43)*
				input.getSubstancesData("Molweight")
				);		

		transport.get("air-soil").get("Global Scale - Moderate climate zone").put("soil", 
				environment.getEnvProps("MODERATE","k.aMG.sMD")*
				engine.getMassMol(86)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Global Scale - Arctic climate zone").put("soil", 
				environment.getEnvProps("ARCTIC","k.aAG.sAD")*
				engine.getMassMol(109)*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Global Scale - Tropical climate zone").put("soil", 
				environment.getEnvProps("TROPICAL","k.aTG.sTD")*
				engine.getMassMol(132)*
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
				engine.getMassMol(31)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Regional Scale").put("agricultural soil", 
				environment.getEnvProps("REGIONAL","k.s2RD.aRG")*
				engine.getMassMol(35)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","k.s3RD.aRG")*
				engine.getMassMol(39)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Continental Scale").put("natural soil", 
				environment.getEnvProps("CONTINENTAL","k.s1CD.aCG")*
				engine.getMassMol(74)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","k.s2CD.aCG")*
				engine.getMassMol(78)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","k.s3CD.aCG")*
				engine.getMassMol(82)*
				input.getSubstancesData("Molweight")
				);		

		transport.get("soil-air").get("Global Scale - Moderate climate zone").put("soil", 
				environment.getEnvProps("MODERATE","k.sMD.aMG")*
				engine.getMassMol(105)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Global Scale - Arctic climate zone").put("soil", 
				environment.getEnvProps("ARCTIC","k.sAD.aAG")*
				engine.getMassMol(128)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-air").get("Global Scale - Tropical climate zone").put("soil", 
				environment.getEnvProps("TROPICAL","k.sTD.aTG")*
				engine.getMassMol(151)*
				input.getSubstancesData("Molweight")
				);


		transport.put("soil-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("soil-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("soil-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("soil-water").get("Regional Scale").put("fresh water", (
				environment.getEnvProps("REGIONAL","k.s1RD.w1RD")*engine.getMassMol(31) + 
				environment.getEnvProps("REGIONAL","k.s2RD.w1RD")*engine.getMassMol(35) +
				environment.getEnvProps("REGIONAL","k.s3RD.w1RD")*engine.getMassMol(39) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("natural soil", 
				environment.getEnvProps("REGIONAL","k.s1RD.w1RD")*
				engine.getMassMol(31)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("agricultural soil", 
				environment.getEnvProps("REGIONAL","k.s2RD.w1RD")*
				engine.getMassMol(35)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("other soil", 
				environment.getEnvProps("REGIONAL","k.s3RD.w1RD")*
				engine.getMassMol(39)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.s1C.w1C")*engine.getMassMol(74) + 
				environment.getEnvProps("CONTINENTAL","k.s2C.w1C")*engine.getMassMol(78) +
				environment.getEnvProps("CONTINENTAL","k.s3C.w1C")*engine.getMassMol(82) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("natural soil", 
				environment.getEnvProps("CONTINENTAL","k.s1C.w1C")*
				engine.getMassMol(74)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("agricultural soil", 
				environment.getEnvProps("CONTINENTAL","k.s2C.w1C")*
				engine.getMassMol(78)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("other soil", 
				environment.getEnvProps("CONTINENTAL","k.s3C.w1C")*
				engine.getMassMol(82)*
				input.getSubstancesData("Molweight")
				);		

		transport.get("soil-water").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.sMD.w2MD")*
				engine.getMassMol(105)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Moderate climate zone").put("soil", 
				environment.getEnvProps("MODERATE","k.sMD.w2MD")*
				engine.getMassMol(105)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.sAD.w2AD")*
				engine.getMassMol(128)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Arctic climate zone").put("soil", 
				environment.getEnvProps("ARCTIC","k.sAD.w2AD")*
				engine.getMassMol(128)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.sTD.w2TD")*
				engine.getMassMol(151)*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Global Scale - Tropical climate zone").put("soil", 
				environment.getEnvProps("TROPICAL","k.sTD.w2TD")*
				engine.getMassMol(151)*
				input.getSubstancesData("Molweight")
				);

		transport.put("water-sed", new HashMap< String, Map< String, Double > >() );		
		transport.get("water-sed").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("water-sed").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("water-sed").get("Regional Scale").put("fresh water lakes", 
				environment.getEnvProps("REGIONAL","k.w0RD.sd0RD")*
				engine.getMassMol(7)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.w1RD.sd1RD")*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.w2RD.sd2RD")*
				engine.getMassMol(15)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("water-sed").get("Regional Scale").put("fresh water sediment", 
				environment.getEnvProps("REGIONAL","k.w1RD.sd1RD")*
				engine.getMassMol(11)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","k.w2RD.sd2RD")*
				engine.getMassMol(15)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water lakes", 
				environment.getEnvProps("CONTINENTAL","k.w0CD.sd0CD")*
				engine.getMassMol(50)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.w1CD.sd1CD")*
				engine.getMassMol(54)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.w2CD.sd2CD")*
				engine.getMassMol(58)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","k.w1CD.sd1CD")*
				engine.getMassMol(54)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","k.w2CD.sd2CD")*
				engine.getMassMol(58)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Moderate climate zone").put("deep sea", 
				environment.getEnvProps("MODERATE","k.w3MD.sdMD")*
				engine.getMassMol(97)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Moderate climate zone").put("ocean sediment", 
				environment.getEnvProps("MODERATE","k.w3MD.sdMD")*
				engine.getMassMol(97)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Arctic climate zone").put("deep sea", 
				environment.getEnvProps("ARCTIC","k.w3AD.sdAD")*
				engine.getMassMol(120)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Arctic climate zone").put("ocean sediment", 
				environment.getEnvProps("ARCTIC","k.w3AD.sdAD")*
				engine.getMassMol(120)*
				input.getSubstancesData("Molweight")
				);


		transport.get("water-sed").get("Global Scale - Tropical climate zone").put("deep sea", 
				environment.getEnvProps("TROPICAL","k.w3TD.sdTD")*
				engine.getMassMol(143)*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Tropical climate zone").put("ocean sediment", 
				environment.getEnvProps("TROPICAL","k.w3TD.sdTD")*
				engine.getMassMol(143)*
				input.getSubstancesData("Molweight")
				);

		transport.put("sed-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("sed-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("sed-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("sed-water").get("Regional Scale").put("fresh water lakes", 
				environment.getEnvProps("REGIONAL","k.sd0RD.w0RD")*
				engine.getMassMol(19)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("fresh water", 
				environment.getEnvProps("REGIONAL","k.sd1RD.w1RD")*
				engine.getMassMol(23)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("coastal sea water", 
				environment.getEnvProps("REGIONAL","k.sd2RD.w2RD")*
				engine.getMassMol(27)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("sed-water").get("Regional Scale").put("fresh water sediment", 
				environment.getEnvProps("REGIONAL","k.sd1RD.w1RD")*
				engine.getMassMol(23)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("coastal marine sediment", 
				environment.getEnvProps("REGIONAL","k.sd2RD.w2RD")*
				engine.getMassMol(27)*
				input.getSubstancesData("Molweight")
				);


		transport.get("sed-water").get("Continental Scale").put("fresh water lakes", 
				environment.getEnvProps("CONTINENTAL","k.sd0CD.w0CD")*
				engine.getMassMol(62)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("fresh water", 
				environment.getEnvProps("CONTINENTAL","k.sd1CD.w1CD")*
				engine.getMassMol(66)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("coastal sea water", 
				environment.getEnvProps("CONTINENTAL","k.sd2CD.w2CD")*
				engine.getMassMol(70)*
				input.getSubstancesData("Molweight")	
				);

		transport.get("sed-water").get("Continental Scale").put("fresh water sediment", 
				environment.getEnvProps("CONTINENTAL","k.sd1CD.w1CD")*
				engine.getMassMol(66)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("coastal marine sediment", 
				environment.getEnvProps("CONTINENTAL","k.sd2CD.w2CD")*
				engine.getMassMol(70)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Moderate climate zone").put("upper ocean water", 
				environment.getEnvProps("MODERATE","k.sdMD.w3MD")*
				engine.getMassMol(101)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Moderate climate zone").put("deep sea", 
				environment.getEnvProps("MODERATE","k.sdMD.w3MD")*
				engine.getMassMol(101)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Arctic climate zone").put("upper ocean water", 
				environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*
				engine.getMassMol(124)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Arctic climate zone").put("deep sea", 
				environment.getEnvProps("ARCTIC","k.sdAD.w3AD")*
				engine.getMassMol(124)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Tropical climate zone").put("upper ocean water", 
				environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*
				engine.getMassMol(147)*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Tropical climate zone").put("deep sea", 
				environment.getEnvProps("TROPICAL","k.sdTD.w3TD")*
				engine.getMassMol(147)*
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

	void buildTransportNano() 
	{		
		transport.clear();

		transport.put("air-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("air-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("air-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("air-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("air-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("air-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("air-water").get("Regional Scale").put("fresh water lakes", ((
				environment.getEnvProps("REGIONAL","k.aRS.w0RS")*engine.getMassMol(1)   +
				environment.getEnvProps("REGIONAL","k.aRA.w0RA")*engine.getMassMol(2)   +
				environment.getEnvProps("REGIONAL","k.aRP.w0RP")*engine.getMassMol(3) ) +
				(environment.getEnvProps("REGIONAL","k.cwRS.w0RS")*engine.getMassMol(4) +
						environment.getEnvProps("REGIONAL","k.cwRA.w0RA")*engine.getMassMol(5)  +
						environment.getEnvProps("REGIONAL","k.cwRP.w0RP")*engine.getMassMol(6) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Regional Scale").put("fresh water", ((
				environment.getEnvProps("REGIONAL","k.aRS.w1RS")*engine.getMassMol(1)   +
				environment.getEnvProps("REGIONAL","k.aRA.w1RA")*engine.getMassMol(2)   +
				environment.getEnvProps("REGIONAL","k.aRP.w1RP")*engine.getMassMol(3) ) +
				(environment.getEnvProps("REGIONAL","k.cwRS.w1RS")*engine.getMassMol(4) +
						environment.getEnvProps("REGIONAL","k.cwRA.w1RA")*engine.getMassMol(5)  +
						environment.getEnvProps("REGIONAL","k.cwRP.w1RP")*engine.getMassMol(6) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Regional Scale").put("coastal sea water", ((
				environment.getEnvProps("REGIONAL","k.aRS.w2RS")*engine.getMassMol(1)   +
				environment.getEnvProps("REGIONAL","k.aRA.w2RA")*engine.getMassMol(2)   +
				environment.getEnvProps("REGIONAL","k.aRP.w2RP")*engine.getMassMol(3) ) +
				(environment.getEnvProps("REGIONAL","k.cwRS.w2RS")*engine.getMassMol(4) +
						environment.getEnvProps("REGIONAL","k.cwRA.w2RA")*engine.getMassMol(5)  +
						environment.getEnvProps("REGIONAL","k.cwRP.w2RP")*engine.getMassMol(6) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Regional Scale").put("air", 
				transport.get("air-water").get("Regional Scale").get("fresh water lakes") + 
				transport.get("air-water").get("Regional Scale").get("fresh water") + 
				transport.get("air-water").get("Regional Scale").get("coastal sea water")  
				);

		transport.get("air-water").get("Continental Scale").put("fresh water lakes", ((
				environment.getEnvProps("CONTINENTAL","k.aCS.w0CS")*engine.getMassMol(44)   +
				environment.getEnvProps("CONTINENTAL","k.aCA.w0CA")*engine.getMassMol(45)   +
				environment.getEnvProps("CONTINENTAL","k.aCP.w0CP")*engine.getMassMol(46) ) +
				(environment.getEnvProps("CONTINENTAL","k.cwCS.w0CS")*engine.getMassMol(47) +
						environment.getEnvProps("CONTINENTAL","k.cwCA.w0CA")*engine.getMassMol(48)  +
						environment.getEnvProps("CONTINENTAL","k.cwCP.w0CP")*engine.getMassMol(49) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("fresh water", (( 
				environment.getEnvProps("CONTINENTAL","k.aCS.w1CS")*engine.getMassMol(44)   +
				environment.getEnvProps("CONTINENTAL","k.aCA.w1CA")*engine.getMassMol(45)   +
				environment.getEnvProps("CONTINENTAL","k.aCP.w1CP")*engine.getMassMol(46) ) +
				(environment.getEnvProps("CONTINENTAL","k.cwCS.w1CS")*engine.getMassMol(47) +
						environment.getEnvProps("CONTINENTAL","k.cwCA.w1CA")*engine.getMassMol(48)  +
						environment.getEnvProps("CONTINENTAL","k.cwCP.w1CP")*engine.getMassMol(49) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Continental Scale").put("coastal sea water", ((
				environment.getEnvProps("CONTINENTAL","k.aCS.w2CS")*engine.getMassMol(44)   +
				environment.getEnvProps("CONTINENTAL","k.aCA.w2CA")*engine.getMassMol(45)   +
				environment.getEnvProps("CONTINENTAL","k.aCP.w2CP")*engine.getMassMol(46) ) +
				(environment.getEnvProps("CONTINENTAL","k.cwCS.w2CS")*engine.getMassMol(47) +
						environment.getEnvProps("CONTINENTAL","k.cwCA.w2CA")*engine.getMassMol(48)  +
						environment.getEnvProps("CONTINENTAL","k.cwCP.w2CP")*engine.getMassMol(49) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-water").get("Global Scale - Moderate climate zone").put("upper ocean water", ((
				environment.getEnvProps("MODERATE","k.aMS.w2MS")*engine.getMassMol(87)   +
				environment.getEnvProps("MODERATE","k.aMA.w2MA")*engine.getMassMol(88)   +
				environment.getEnvProps("MODERATE","k.aMP.w2MP")*engine.getMassMol(89) ) +
				(environment.getEnvProps("MODERATE","k.cwMS.w2MS")*engine.getMassMol(90) +
						environment.getEnvProps("MODERATE","k.cwMA.w2MA")*engine.getMassMol(91)  +
						environment.getEnvProps("MODERATE","k.cwMP.w2MP")*engine.getMassMol(92) ))*
				input.getSubstancesData("Molweight")				
				);

		transport.get("air-water").get("Global Scale - Arctic climate zone").put("upper ocean water", (( 
				environment.getEnvProps("ARCTIC","k.aAS.w2AS")*engine.getMassMol(110)   +
				environment.getEnvProps("ARCTIC","k.aAA.w2AA")*engine.getMassMol(111)   +
				environment.getEnvProps("ARCTIC","k.aAP.w2AP")*engine.getMassMol(112) ) +
				(environment.getEnvProps("ARCTIC","k.cwAS.w2AS")*engine.getMassMol(113) +
						environment.getEnvProps("ARCTIC","k.cwAA.w2AA")*engine.getMassMol(114)  +
						environment.getEnvProps("ARCTIC","k.cwAP.w2AP")*engine.getMassMol(115) ))*
				input.getSubstancesData("Molweight")				
				);

		transport.get("air-water").get("Global Scale - Tropical climate zone").put("upper ocean water", (( 
				environment.getEnvProps("TROPICAL","k.aTS.w2TS")*engine.getMassMol(133)   +
				environment.getEnvProps("TROPICAL","k.aTA.w2TA")*engine.getMassMol(134)   +
				environment.getEnvProps("TROPICAL","k.aTP.w2TP")*engine.getMassMol(135) ) +
				(environment.getEnvProps("TROPICAL","k.cwTS.w2TS")*engine.getMassMol(136) +
						environment.getEnvProps("TROPICAL","k.cwTA.w2TA")*engine.getMassMol(137)  +
						environment.getEnvProps("TROPICAL","k.cwTP.w2TP")*engine.getMassMol(138) ))*
				input.getSubstancesData("Molweight")		
				);

		transport.put("water-air", new HashMap< String, Map< String, Double > >() );		
		transport.get("water-air").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("water-air").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("water-air").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("water-air").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("water-air").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("water-air").get("Regional Scale").put("fresh water lakes", 0.0);
		transport.get("water-air").get("Regional Scale").put("fresh water", 0.0);
		transport.get("water-air").get("Regional Scale").put("coastal sea water", 0.0);
		transport.get("water-air").get("Continental Scale").put("fresh water lakes", 0.0);
		transport.get("water-air").get("Continental Scale").put("fresh water", 0.0);
		transport.get("water-air").get("Continental Scale").put("coastal sea water", 0.0);
		transport.get("water-air").get("Global Scale - Moderate climate zone").put("upper ocean water", 0.0); 
		transport.get("water-air").get("Global Scale - Arctic climate zone").put("upper ocean water", 0.0);
		transport.get("water-air").get("Global Scale - Tropical climate zone").put("upper ocean water", 0.0); 

		transport.put("air-soil", new HashMap< String, Map< String, Double > >() );		
		transport.get("air-soil").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("air-soil").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("air-soil").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("air-soil").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("air-soil").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("air-soil").get("Regional Scale").put("natural soil", (( 
				environment.getEnvProps("REGIONAL","k.aRS.s1RS")*engine.getMassMol(1) + 
				environment.getEnvProps("REGIONAL","k.aRA.s1RA")*engine.getMassMol(2) + 
				environment.getEnvProps("REGIONAL","k.aRP.s1RP")*engine.getMassMol(3) ) + 
				(environment.getEnvProps("REGIONAL","k.cwRS.s1RS")*engine.getMassMol(4) + 
						environment.getEnvProps("REGIONAL","k.cwRA.s1RA")*engine.getMassMol(5) + 
						environment.getEnvProps("REGIONAL","k.cwRP.s1RP")*engine.getMassMol(6) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Regional Scale").put("agricultural soil", (( 
				environment.getEnvProps("REGIONAL","k.aRS.s2RS")*engine.getMassMol(1) + 
				environment.getEnvProps("REGIONAL","k.aRA.s2RA")*engine.getMassMol(2) + 
				environment.getEnvProps("REGIONAL","k.aRP.s2RP")*engine.getMassMol(3) ) + 
				(environment.getEnvProps("REGIONAL","k.cwRS.s2RS")*engine.getMassMol(4) + 
						environment.getEnvProps("REGIONAL","k.cwRA.s2RA")*engine.getMassMol(5) + 
						environment.getEnvProps("REGIONAL","k.cwRP.s2RP")*engine.getMassMol(6) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Regional Scale").put("other soil", (( 
				environment.getEnvProps("REGIONAL","k.aRS.s3RS")*engine.getMassMol(1) + 
				environment.getEnvProps("REGIONAL","k.aRA.s3RA")*engine.getMassMol(2) + 
				environment.getEnvProps("REGIONAL","k.aRP.s3RP")*engine.getMassMol(3) ) + 
				(environment.getEnvProps("REGIONAL","k.cwRS.s3RS")*engine.getMassMol(4) + 
						environment.getEnvProps("REGIONAL","k.cwRA.s3RA")*engine.getMassMol(5) + 
						environment.getEnvProps("REGIONAL","k.cwRP.s3RP")*engine.getMassMol(6) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Regional Scale").put("air", 
				transport.get("air-soil").get("Regional Scale").get("natural soil") + 
				transport.get("air-soil").get("Regional Scale").get("agricultural soil") + 
				transport.get("air-soil").get("Regional Scale").get("other soil")  
				);

		transport.get("air-soil").get("Continental Scale").put("natural soil",(( 
				environment.getEnvProps("CONTINENTAL","k.aCS.s1CS")*engine.getMassMol(44) + 
				environment.getEnvProps("CONTINENTAL","k.aCA.s1CA")*engine.getMassMol(45) + 
				environment.getEnvProps("CONTINENTAL","k.aCP.s1CP")*engine.getMassMol(46) ) + 
				(environment.getEnvProps("CONTINENTAL","k.cwCS.s1CS")*engine.getMassMol(47) + 
						environment.getEnvProps("CONTINENTAL","k.cwCA.s1CA")*engine.getMassMol(48) + 
						environment.getEnvProps("CONTINENTAL","k.cwCP.s1CP")*engine.getMassMol(49) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Continental Scale").put("agricultural soil", (( 
				environment.getEnvProps("CONTINENTAL","k.aCS.s2CS")*engine.getMassMol(44) + 
				environment.getEnvProps("CONTINENTAL","k.aCA.s2CA")*engine.getMassMol(45) + 
				environment.getEnvProps("CONTINENTAL","k.aCP.s2CP")*engine.getMassMol(46) ) + 
				(environment.getEnvProps("CONTINENTAL","k.cwCS.s2CS")*engine.getMassMol(47) + 
						environment.getEnvProps("CONTINENTAL","k.cwCA.s2CA")*engine.getMassMol(48) + 
						environment.getEnvProps("CONTINENTAL","k.cwCP.s2CP")*engine.getMassMol(49) ))*
				input.getSubstancesData("Molweight")
				);				

		transport.get("air-soil").get("Continental Scale").put("other soil", (( 
				environment.getEnvProps("CONTINENTAL","k.aCS.s3CS")*engine.getMassMol(44) + 
				environment.getEnvProps("CONTINENTAL","k.aCA.s3CA")*engine.getMassMol(45) + 
				environment.getEnvProps("CONTINENTAL","k.aCP.s3CP")*engine.getMassMol(46) ) + 
				(environment.getEnvProps("CONTINENTAL","k.cwCS.s3CS")*engine.getMassMol(47) + 
						environment.getEnvProps("CONTINENTAL","k.cwCA.s3CA")*engine.getMassMol(48) + 
						environment.getEnvProps("CONTINENTAL","k.cwCP.s3CP")*engine.getMassMol(49) ))*
				input.getSubstancesData("Molweight")
				);		

		transport.get("air-soil").get("Global Scale - Moderate climate zone").put("soil", ((
				environment.getEnvProps("MODERATE","k.aMS.sMS")*engine.getMassMol(87) + 
				environment.getEnvProps("MODERATE","k.aMA.sMA")*engine.getMassMol(88) + 
				environment.getEnvProps("MODERATE","k.aMP.sMP")*engine.getMassMol(89) ) + 
				(environment.getEnvProps("MODERATE","k.cwMS.sMS")*engine.getMassMol(90) + 
						environment.getEnvProps("MODERATE","k.cwMA.sMA")*engine.getMassMol(91) + 
						environment.getEnvProps("MODERATE","k.cwMP.sMP")*engine.getMassMol(92) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Global Scale - Arctic climate zone").put("soil", ((
				environment.getEnvProps("ARCTIC","k.aAS.sAS")*engine.getMassMol(110) + 
				environment.getEnvProps("ARCTIC","k.aAA.sAA")*engine.getMassMol(111) + 
				environment.getEnvProps("ARCTIC","k.aAP.sAP")*engine.getMassMol(112) ) + 
				(environment.getEnvProps("ARCTIC","k.cwAS.sAS")*engine.getMassMol(113) + 
						environment.getEnvProps("ARCTIC","k.cwAA.sAA")*engine.getMassMol(114) + 
						environment.getEnvProps("ARCTIC","k.cwAP.sAP")*engine.getMassMol(115) ))*
				input.getSubstancesData("Molweight")
				);

		transport.get("air-soil").get("Global Scale - Tropical climate zone").put("soil", (( 
				environment.getEnvProps("TROPICAL","k.aTS.sTS")*engine.getMassMol(133) + 
				environment.getEnvProps("TROPICAL","k.aTA.sTA")*engine.getMassMol(134) + 
				environment.getEnvProps("TROPICAL","k.aTP.sTP")*engine.getMassMol(135) ) + 
				(environment.getEnvProps("TROPICAL","k.cwTS.sTS")*engine.getMassMol(136) + 
						environment.getEnvProps("TROPICAL","k.cwTA.sTA")*engine.getMassMol(137) + 
						environment.getEnvProps("TROPICAL","k.cwTP.sTP")*engine.getMassMol(138) ))*
				input.getSubstancesData("Molweight")
				);

		transport.put("soil-air", new HashMap< String, Map< String, Double > >() );		
		transport.get("soil-air").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("soil-air").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("soil-air").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("soil-air").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("soil-air").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("soil-air").get("Regional Scale").put("natural soil", 0.0);
		transport.get("soil-air").get("Regional Scale").put("agricultural soil", 0.0);
		transport.get("soil-air").get("Regional Scale").put("other soil",0.0);
		transport.get("soil-air").get("Continental Scale").put("natural soil",0.0);
		transport.get("soil-air").get("Continental Scale").put("agricultural soil",0.0);
		transport.get("soil-air").get("Continental Scale").put("other soil",0.0);
		transport.get("soil-air").get("Global Scale - Moderate climate zone").put("soil",0.0);
		transport.get("soil-air").get("Global Scale - Arctic climate zone").put("soil",0.0);
		transport.get("soil-air").get("Global Scale - Tropical climate zone").put("soil",0.0);

		transport.put("soil-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("soil-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("soil-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("soil-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("soil-water").get("Regional Scale").put("fresh water", (
				environment.getEnvProps("REGIONAL","k.s1RS.w1RS")*engine.getMassMol(32) + 
				environment.getEnvProps("REGIONAL","k.s1RA.w1RA")*engine.getMassMol(33) +
				environment.getEnvProps("REGIONAL","k.s1RP.w1RP")*engine.getMassMol(34) +
				environment.getEnvProps("REGIONAL","k.s2RS.w1RS")*engine.getMassMol(36) +
				environment.getEnvProps("REGIONAL","k.s2RA.w1RA")*engine.getMassMol(37) +
				environment.getEnvProps("REGIONAL","k.s2RP.w1RP")*engine.getMassMol(38) +
				environment.getEnvProps("REGIONAL","k.s3RS.w1RS")*engine.getMassMol(40) +
				environment.getEnvProps("REGIONAL","k.s3RA.w1RA")*engine.getMassMol(41) +
				environment.getEnvProps("REGIONAL","k.s3RP.w1RP")*engine.getMassMol(42) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("natural soil", ( 
				environment.getEnvProps("REGIONAL","k.s1RS.w1RS")*engine.getMassMol(32) + 
				environment.getEnvProps("REGIONAL","k.s1RA.w1RA")*engine.getMassMol(33) +
				environment.getEnvProps("REGIONAL","k.s1RP.w1RP")*engine.getMassMol(34) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("agricultural soil", ( 
				environment.getEnvProps("REGIONAL","k.s2RS.w1RS")*engine.getMassMol(36) + 
				environment.getEnvProps("REGIONAL","k.s2RA.w1RA")*engine.getMassMol(37) +
				environment.getEnvProps("REGIONAL","k.s2RP.w1RP")*engine.getMassMol(38) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Regional Scale").put("other soil", (
				environment.getEnvProps("REGIONAL","k.s3RS.w1RS")*engine.getMassMol(40) + 
				environment.getEnvProps("REGIONAL","k.s3RA.w1RA")*engine.getMassMol(41) +
				environment.getEnvProps("REGIONAL","k.s3RP.w1RP")*engine.getMassMol(42) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.s1CS.w1CS")*engine.getMassMol(75) + 
				environment.getEnvProps("CONTINENTAL","k.s1CA.w1CA")*engine.getMassMol(76) +
				environment.getEnvProps("CONTINENTAL","k.s1CP.w1CP")*engine.getMassMol(77) +

				environment.getEnvProps("CONTINENTAL","k.s2CS.w1CS")*engine.getMassMol(79) +
				environment.getEnvProps("CONTINENTAL","k.s2CA.w1CA")*engine.getMassMol(80) +
				environment.getEnvProps("CONTINENTAL","k.s2CP.w1CP")*engine.getMassMol(81) +

				environment.getEnvProps("CONTINENTAL","k.s3CS.w1CS")*engine.getMassMol(83) +
				environment.getEnvProps("CONTINENTAL","k.s3CA.w1CA")*engine.getMassMol(84) +
				environment.getEnvProps("CONTINENTAL","k.s3CP.w1CP")*engine.getMassMol(85) )*
				input.getSubstancesData("Molweight")			
				);

		transport.get("soil-water").get("Continental Scale").put("natural soil", (
				environment.getEnvProps("CONTINENTAL","k.s1CS.w1CS")*engine.getMassMol(75) + 
				environment.getEnvProps("CONTINENTAL","k.s1CA.w1CA")*engine.getMassMol(76) +
				environment.getEnvProps("CONTINENTAL","k.s1CP.w1CP")*engine.getMassMol(77) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("agricultural soil", (
				environment.getEnvProps("CONTINENTAL","k.s2CS.w1CS")*engine.getMassMol(79) +
				environment.getEnvProps("CONTINENTAL","k.s2CA.w1CA")*engine.getMassMol(80) +
				environment.getEnvProps("CONTINENTAL","k.s2CP.w1CP")*engine.getMassMol(81) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("soil-water").get("Continental Scale").put("other soil", (
				environment.getEnvProps("CONTINENTAL","k.s3CS.w1CS")*engine.getMassMol(83) +
				environment.getEnvProps("CONTINENTAL","k.s3CA.w1CA")*engine.getMassMol(84) +
				environment.getEnvProps("CONTINENTAL","k.s3CP.w1CP")*engine.getMassMol(85) )*
				input.getSubstancesData("Molweight")			
				);		

		transport.get("soil-water").get("Global Scale - Moderate climate zone").put("upper ocean water", (
				environment.getEnvProps("MODERATE","k.sMS.w2MS")*engine.getMassMol(106) +
				environment.getEnvProps("MODERATE","k.sMA.w2MA")*engine.getMassMol(107) +
				environment.getEnvProps("MODERATE","k.sMP.w2MP")*engine.getMassMol(108) )*
				input.getSubstancesData("Molweight")		
				);

		transport.get("soil-water").get("Global Scale - Moderate climate zone").put("soil", 
				transport.get("soil-water").get("Global Scale - Moderate climate zone").get("upper ocean water")
				);

		transport.get("soil-water").get("Global Scale - Arctic climate zone").put("upper ocean water", (
				environment.getEnvProps("ARCTIC","k.sAS.w2AS")*engine.getMassMol(129) +
				environment.getEnvProps("ARCTIC","k.sAA.w2AA")*engine.getMassMol(130) +
				environment.getEnvProps("ARCTIC","k.sAP.w2AP")*engine.getMassMol(131) )*
				input.getSubstancesData("Molweight")		
				);

		transport.get("soil-water").get("Global Scale - Arctic climate zone").put("soil", 
				transport.get("soil-water").get("Global Scale - Arctic climate zone").get("upper ocean water")
				);

		transport.get("soil-water").get("Global Scale - Tropical climate zone").put("upper ocean water", ( 
				environment.getEnvProps("TROPICAL","k.sTS.w2TS")*engine.getMassMol(152) +
				environment.getEnvProps("TROPICAL","k.sTA.w2TA")*engine.getMassMol(153) +
				environment.getEnvProps("TROPICAL","k.sTP.w2TP")*engine.getMassMol(154) )*
				input.getSubstancesData("Molweight")						
				);

		transport.get("soil-water").get("Global Scale - Tropical climate zone").put("soil", 
				transport.get("soil-water").get("Global Scale - Tropical climate zone").get("upper ocean water")
				);

		transport.put("water-sed", new HashMap< String, Map< String, Double > >() );		
		transport.get("water-sed").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("water-sed").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("water-sed").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("water-sed").get("Regional Scale").put("fresh water lakes", (
				environment.getEnvProps("REGIONAL","k.w0RS.sd0RS")*engine.getMassMol(8) + 
				environment.getEnvProps("REGIONAL","k.w0RA.sd0RA")*engine.getMassMol(9) + 
				environment.getEnvProps("REGIONAL","k.w0RP.sd0RP")*engine.getMassMol(10) )* 
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("fresh water", (
				environment.getEnvProps("REGIONAL","k.w1RS.sd1RS")*engine.getMassMol(12) + 
				environment.getEnvProps("REGIONAL","k.w1RA.sd1RA")*engine.getMassMol(13) + 
				environment.getEnvProps("REGIONAL","k.w1RP.sd1RP")*engine.getMassMol(14) )* 
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("coastal sea water", (
				environment.getEnvProps("REGIONAL","k.w2RS.sd2RS")*engine.getMassMol(16) + 
				environment.getEnvProps("REGIONAL","k.w2RA.sd2RA")*engine.getMassMol(17) + 
				environment.getEnvProps("REGIONAL","k.w2RP.sd2RP")*engine.getMassMol(18) )* 
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Regional Scale").put("fresh water sediment", 
				transport.get("water-sed").get("Regional Scale").get("fresh water")
				);

		transport.get("water-sed").get("Regional Scale").put("coastal marine sediment", 
				transport.get("water-sed").get("Regional Scale").get("coastal sea water")
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water lakes", (
				environment.getEnvProps("CONTINENTAL","k.w0CS.sd0CS")*engine.getMassMol(51) +
				environment.getEnvProps("CONTINENTAL","k.w0CA.sd0CA")*engine.getMassMol(52) +
				environment.getEnvProps("CONTINENTAL","k.w0CP.sd0CP")*engine.getMassMol(53) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water", (
				environment.getEnvProps("CONTINENTAL","k.w1CS.sd1CS")*engine.getMassMol(55) +
				environment.getEnvProps("CONTINENTAL","k.w1CA.sd1CA")*engine.getMassMol(56) +
				environment.getEnvProps("CONTINENTAL","k.w1CP.sd1CP")*engine.getMassMol(57) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("CONTINENTAL","k.w2CS.sd2CS")*engine.getMassMol(59) +
				environment.getEnvProps("CONTINENTAL","k.w2CA.sd2CA")*engine.getMassMol(60) +
				environment.getEnvProps("CONTINENTAL","k.w2CP.sd2CP")*engine.getMassMol(61) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Continental Scale").put("fresh water sediment", 
				transport.get("water-sed").get("Continental Scale").get("fresh water")
				);

		transport.get("water-sed").get("Continental Scale").put("coastal marine sediment", 
				transport.get("water-sed").get("Continental Scale").get("coastal sea water")
				);

		transport.get("water-sed").get("Global Scale - Moderate climate zone").put("deep sea", (
				environment.getEnvProps("MODERATE","k.w3MS.sdMS")*engine.getMassMol(98) +
				environment.getEnvProps("MODERATE","k.w3MA.sdMA")*engine.getMassMol(99) +
				environment.getEnvProps("MODERATE","k.w3MP.sdMP")*engine.getMassMol(100) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("water-sed").get("Global Scale - Moderate climate zone").put("ocean sediment",
				transport.get("water-sed").get("Global Scale - Moderate climate zone").get("deep sea")
				);

		transport.get("water-sed").get("Global Scale - Arctic climate zone").put("deep sea", (
				environment.getEnvProps("ARCTIC","k.w3AS.sdAS")*engine.getMassMol(121) +
				environment.getEnvProps("ARCTIC","k.w3AA.sdAA")*engine.getMassMol(122) +
				environment.getEnvProps("ARCTIC","k.w3AP.sdAP")*engine.getMassMol(123) )*
				input.getSubstancesData("Molweight") 				
				);

		transport.get("water-sed").get("Global Scale - Arctic climate zone").put("ocean sediment", 
				transport.get("water-sed").get("Global Scale - Arctic climate zone").get("deep sea")
				);

		transport.get("water-sed").get("Global Scale - Tropical climate zone").put("deep sea", (  
				environment.getEnvProps("TROPICAL","k.w3TS.sdTS")*engine.getMassMol(144) +
				environment.getEnvProps("TROPICAL","k.w3TA.sdTA")*engine.getMassMol(145) +
				environment.getEnvProps("TROPICAL","k.w3TP.sdTP")*engine.getMassMol(146) )*
				input.getSubstancesData("Molweight") 		
				);

		transport.get("water-sed").get("Global Scale - Tropical climate zone").put("ocean sediment", 
				transport.get("water-sed").get("Global Scale - Tropical climate zone").get("deep sea")
				);

		transport.put("sed-water", new HashMap< String, Map< String, Double > >() );		
		transport.get("sed-water").put("Regional Scale", new HashMap<String, Double>() );
		transport.get("sed-water").put("Continental Scale", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Moderate climate zone", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Arctic climate zone", new HashMap<String, Double>() );
		transport.get("sed-water").put("Global Scale - Tropical climate zone", new HashMap<String, Double>() );

		transport.get("sed-water").get("Regional Scale").put("fresh water lakes", (
				environment.getEnvProps("REGIONAL","k.sd0RS.w0RS")*engine.getMassMol(20) + 
				environment.getEnvProps("REGIONAL","k.sd0RA.w0RA")*engine.getMassMol(21) + 
				environment.getEnvProps("REGIONAL","k.sd0RP.w0RP")*engine.getMassMol(22) )* 
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("fresh water", (
				environment.getEnvProps("REGIONAL","k.sd1RS.w1RS")*engine.getMassMol(24) + 
				environment.getEnvProps("REGIONAL","k.sd1RA.w1RA")*engine.getMassMol(25) + 
				environment.getEnvProps("REGIONAL","k.sd1RP.w1RP")*engine.getMassMol(26) )* 
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Regional Scale").put("coastal sea water",(
				environment.getEnvProps("REGIONAL","k.sd2RS.w2RS")*engine.getMassMol(28) + 
				environment.getEnvProps("REGIONAL","k.sd2RA.w2RA")*engine.getMassMol(29) + 
				environment.getEnvProps("REGIONAL","k.sd2RP.w2RP")*engine.getMassMol(30) )* 
				input.getSubstancesData("Molweight")	
				);

		transport.get("sed-water").get("Regional Scale").put("fresh water sediment", 
				transport.get("sed-water").get("Regional Scale").get("fresh water")
				);

		transport.get("sed-water").get("Regional Scale").put("coastal marine sediment", 
				transport.get("sed-water").get("Regional Scale").get("coastal sea water")
				);


		transport.get("sed-water").get("Continental Scale").put("fresh water lakes", (
				environment.getEnvProps("CONTINENTAL","k.sd0CS.w0CS")*engine.getMassMol(63) +
				environment.getEnvProps("CONTINENTAL","k.sd0CA.w0CA")*engine.getMassMol(64) +
				environment.getEnvProps("CONTINENTAL","k.sd0CP.w0CP")*engine.getMassMol(65) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("fresh water",(
				environment.getEnvProps("CONTINENTAL","k.sd1CS.w1CS")*engine.getMassMol(67) +
				environment.getEnvProps("CONTINENTAL","k.sd1CA.w1CA")*engine.getMassMol(68) +
				environment.getEnvProps("CONTINENTAL","k.sd1CP.w1CP")*engine.getMassMol(69) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("coastal sea water",(
				environment.getEnvProps("CONTINENTAL","k.sd2CS.w2CS")*engine.getMassMol(71) +
				environment.getEnvProps("CONTINENTAL","k.sd2CA.w2CA")*engine.getMassMol(72) +
				environment.getEnvProps("CONTINENTAL","k.sd2CP.w2CP")*engine.getMassMol(73) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Continental Scale").put("fresh water sediment", 
				transport.get("sed-water").get("Continental Scale").get("fresh water")
				);

		transport.get("sed-water").get("Continental Scale").put("coastal marine sediment", 
				transport.get("sed-water").get("Continental Scale").get("coastal sea water")
				);

		transport.get("sed-water").get("Global Scale - Moderate climate zone").put("upper ocean water", ( 
				environment.getEnvProps("MODERATE","k.sdMS.w3MS")*engine.getMassMol(102) +  
				environment.getEnvProps("MODERATE","k.sdMA.w3MA")*engine.getMassMol(103) +  
				environment.getEnvProps("MODERATE","k.sdMP.w3MP")*engine.getMassMol(104) )*  
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Moderate climate zone").put("deep sea", 
				transport.get("sed-water").get("Global Scale - Moderate climate zone").get("upper ocean water")
				);

		transport.get("sed-water").get("Global Scale - Arctic climate zone").put("upper ocean water", ( 
				environment.getEnvProps("ARCTIC","k.sdAS.w3AS")*engine.getMassMol(125) + 
				environment.getEnvProps("ARCTIC","k.sdAA.w3AA")*engine.getMassMol(126) + 
				environment.getEnvProps("ARCTIC","k.sdAP.w3AP")*engine.getMassMol(127) )* 
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Arctic climate zone").put("deep sea", 
				transport.get("sed-water").get("Global Scale - Arctic climate zone").get("upper ocean water")
				);

		transport.get("sed-water").get("Global Scale - Tropical climate zone").put("upper ocean water", ( 
				environment.getEnvProps("TROPICAL","k.sdTS.w3TS")*engine.getMassMol(148) +
				environment.getEnvProps("TROPICAL","k.sdTA.w3TA")*engine.getMassMol(149) +
				environment.getEnvProps("TROPICAL","k.sdTP.w3TP")*engine.getMassMol(150) )*
				input.getSubstancesData("Molweight")
				);

		transport.get("sed-water").get("Global Scale - Tropical climate zone").put("deep sea", 
				transport.get("sed-water").get("Global Scale - Tropical climate zone").get("upper ocean water")
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
				input.getEmissionRates("E.aRG")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("fresh water lakes", 
				input.getEmissionRates("E.w0RD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("fresh water", 
				input.getEmissionRates("E.w1RD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("coastal sea water", 
				input.getEmissionRates("E.w2RD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("natural soil", 
				input.getEmissionRates("E.s1RD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("agricultural soil", 
				input.getEmissionRates("E.s2RD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("other soil", 
				input.getEmissionRates("E.s3RD")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Continental Scale", new HashMap< String, Double >() );

		emission.get("Continental Scale").put("air", 
				input.getEmissionRates("E.aCG")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("fresh water lakes", 
				input.getEmissionRates("E.w0CD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("fresh water", 
				input.getEmissionRates("E.w1CD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("coastal sea water", 
				input.getEmissionRates("E.w2CD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("natural soil", 
				input.getEmissionRates("E.s1CD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("agricultural soil", 
				input.getEmissionRates("E.s2CD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Continental Scale").put("other soil", 
				input.getEmissionRates("E.s3CD")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Global Scale - Moderate climate zone", new HashMap< String, Double >() );
		emission.get("Global Scale - Moderate climate zone").put("air", 
				input.getEmissionRates("E.aMG")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Moderate climate zone").put("upper ocean water", 
				input.getEmissionRates("E.w2MD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Moderate climate zone").put("soil", 
				input.getEmissionRates("E.sMD")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Global Scale - Arctic climate zone", new HashMap< String, Double >() );
		emission.get("Global Scale - Arctic climate zone").put("air", 
				input.getEmissionRates("E.aAG")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Arctic climate zone").put("upper ocean water", 
				input.getEmissionRates("E.w2AD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Arctic climate zone").put("soil", 
				input.getEmissionRates("E.sAD")*
				input.getSubstancesData("Molweight")
				);

		emission.put("Global Scale - Tropical climate zone", new HashMap< String, Double >() );
		emission.get("Global Scale - Tropical climate zone").put("air", 
				input.getEmissionRates("E.aTG")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Tropical climate zone").put("upper ocean water", 
				input.getEmissionRates("E.w2TD")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Global Scale - Tropical climate zone").put("soil", 
				input.getEmissionRates("E.sTD")*
				input.getSubstancesData("Molweight")
				);
	}

	void buildEmissionNano()
	{
		//Clear because it can come from not nano environment
		emission.clear();

		emission.put("Regional Scale", new HashMap< String, Double >() );
		emission.get("Regional Scale").put("air", 
				input.getEmissionRates("E.aRS")*
				input.getSubstancesData("Molweight")
				);

		emission.get("Regional Scale").put("fresh water lakes", 
				input.getEmissionRates("E.w0RS")*
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
				input.getEmissionRates("E.sTS")*
				input.getSubstancesData("Molweight")
				);
	}

	void buildTotals()
	{
		totalD.put("Regional Scale", 		
				masses.get("Air").get("Reg").get("Dissolved/Gas species (G/D)") + 
				masses.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)") + 
				masses.get("Fresh water lake sediment").get("Reg").get("Dissolved/Gas species (G/D)") + 
				masses.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)") + 
				masses.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)") +
				masses.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)") + 
				masses.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)") + 
				masses.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)") 
				);

		totalD.put("Continental Scale", 		
				masses.get("Air").get("Cont").get("Dissolved/Gas species (G/D)") + 
				masses.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)") + 
				masses.get("Fresh water lake sediment").get("Cont").get("Dissolved/Gas species (G/D)") + 
				masses.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)") + 
				masses.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)") +
				masses.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)") + 
				masses.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)") + 
				masses.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)") 
				);

		totalD.put("Moderate Scale", 		
				masses.get("Air").get("Mod").get("Dissolved/Gas species (G/D)") + 
				masses.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") + 
				masses.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") + 
				masses.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)") 
				);

		totalD.put("Arctic Scale", 		
				masses.get("Air").get("Arct").get("Dissolved/Gas species (G/D)") + 
				masses.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") + 
				masses.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") + 
				masses.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)") 
				);

		totalD.put("Tropical Scale", 		
				masses.get("Air").get("Trop").get("Dissolved/Gas species (G/D)") + 
				masses.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") + 
				masses.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") + 
				masses.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)") +
				masses.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)") 
				);

		totalS.put("Regional Scale", 		
				masses.get("Air").get("Reg").get("Solid species (S)") + 
				masses.get("Fresh water lake").get("Reg").get("Solid species (S)") + 
				masses.get("Fresh water lake sediment").get("Reg").get("Solid species (S)") + 
				masses.get("Fresh water").get("Reg").get("Solid species (S)") +
				masses.get("Fresh water sediment").get("Reg").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Solid species (S)") + 
				masses.get("Marine sediment").get("Reg").get("Solid species (S)") +
				masses.get("Natural soil").get("Reg").get("Solid species (S)") + 
				masses.get("Agricultural soil").get("Reg").get("Solid species (S)") + 
				masses.get("Other soil").get("Reg").get("Solid species (S)") 
				);

		totalS.put("Continental Scale", 		
				masses.get("Air").get("Cont").get("Solid species (S)") + 
				masses.get("Fresh water lake").get("Cont").get("Solid species (S)") + 
				masses.get("Fresh water lake sediment").get("Cont").get("Solid species (S)") + 
				masses.get("Fresh water").get("Cont").get("Solid species (S)") +
				masses.get("Fresh water sediment").get("Cont").get("Solid species (S)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Solid species (S)") + 
				masses.get("Marine sediment").get("Cont").get("Solid species (S)") +
				masses.get("Natural soil").get("Cont").get("Solid species (S)") + 
				masses.get("Agricultural soil").get("Cont").get("Solid species (S)") + 
				masses.get("Other soil").get("Cont").get("Solid species (S)") 
				);

		totalS.put("Moderate Scale", 		
				masses.get("Air").get("Mod").get("Solid species (S)") + 
				masses.get("Surface sea/ocean water").get("Mod").get("Solid species (S)") + 
				masses.get("Deep sea/ocean water").get("Mod").get("Solid species (S)") + 
				masses.get("Marine sediment").get("Mod").get("Solid species (S)") +
				masses.get("Other soil").get("Mod").get("Solid species (S)") 
				);

		totalS.put("Arctic Scale", 		
				masses.get("Air").get("Arct").get("Solid species (S)") + 
				masses.get("Surface sea/ocean water").get("Arct").get("Solid species (S)") + 
				masses.get("Deep sea/ocean water").get("Arct").get("Solid species (S)") + 
				masses.get("Marine sediment").get("Arct").get("Solid species (S)") +
				masses.get("Other soil").get("Arct").get("Solid species (S)") 
				);

		totalS.put("Tropical Scale", 		
				masses.get("Air").get("Trop").get("Solid species (S)") + 
				masses.get("Surface sea/ocean water").get("Trop").get("Solid species (S)") + 
				masses.get("Deep sea/ocean water").get("Trop").get("Solid species (S)") + 
				masses.get("Marine sediment").get("Trop").get("Solid species (S)") +
				masses.get("Other soil").get("Trop").get("Solid species (S)") 
				);

		totalA.put("Regional Scale", 		
				masses.get("Air").get("Reg").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Fresh water lake").get("Reg").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Fresh water lake sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Fresh water").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Marine sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Natural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Agricultural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Other soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") 
				);

		totalA.put("Continental Scale", 		
				masses.get("Air").get("Cont").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Fresh water lake").get("Cont").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Fresh water lake sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Fresh water").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Fresh water sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Marine sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Natural soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Agricultural soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Other soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") 
				);

		totalA.put("Moderate Scale", 		
				masses.get("Air").get("Mod").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Surface sea/ocean water").get("Mod").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Deep sea/ocean water").get("Mod").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Marine sediment").get("Mod").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Mod").get("Species attached to NCs (<450 nm) (A)") 
				);

		totalA.put("Arctic Scale", 		
				masses.get("Air").get("Arct").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Surface sea/ocean water").get("Arct").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Deep sea/ocean water").get("Arct").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Marine sediment").get("Arct").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Arct").get("Species attached to NCs (<450 nm) (A)") 
				);

		totalA.put("Tropical Scale", 		
				masses.get("Air").get("Trop").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Surface sea/ocean water").get("Trop").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Deep sea/ocean water").get("Trop").get("Species attached to NCs (<450 nm) (A)") + 
				masses.get("Marine sediment").get("Trop").get("Species attached to NCs (<450 nm) (A)") +
				masses.get("Other soil").get("Trop").get("Species attached to NCs (<450 nm) (A)") 
				);

		totalP.put("Regional Scale", 		
				masses.get("Air").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Fresh water lake").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Fresh water lake sediment").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Fresh water").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Fresh water sediment").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Surface sea/ocean water").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Marine sediment").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Natural soil").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Agricultural soil").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Other soil").get("Reg").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		totalP.put("Continental Scale", 		
				masses.get("Air").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Fresh water lake").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Fresh water lake sediment").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Fresh water").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Fresh water sediment").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Surface sea/ocean water").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Marine sediment").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Natural soil").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Agricultural soil").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Other soil").get("Cont").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		totalP.put("Moderate Scale", 		
				masses.get("Air").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Surface sea/ocean water").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Deep sea/ocean water").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Marine sediment").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Other soil").get("Mod").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		totalP.put("Arctic Scale", 		
				masses.get("Air").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Surface sea/ocean water").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Deep sea/ocean water").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Marine sediment").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Other soil").get("Arct").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		totalP.put("Tropical Scale", 		
				masses.get("Air").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Surface sea/ocean water").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Deep sea/ocean water").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") + 
				masses.get("Marine sediment").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") +
				masses.get("Other soil").get("Trop").get("Species attached to suspended particles (>450 nm) (P)") 
				);

		totalReg = totalD.get("Regional Scale") + totalS.get("Regional Scale") + totalA.get("Regional Scale") + totalP.get("Regional Scale");
		totalCont = totalD.get("Continental Scale") + totalS.get("Continental Scale") + totalA.get("Continental Scale") + totalP.get("Continental Scale");
		totalMod = totalD.get("Moderate Scale") + totalS.get("Moderate Scale") + totalA.get("Moderate Scale") + totalP.get("Moderate Scale");
		totalArct = totalD.get("Arctic Scale") + totalS.get("Arctic Scale") + totalA.get("Arctic Scale") + totalP.get("Arctic Scale");
		totalTrop = totalD.get("Tropical Scale") + totalS.get("Tropical Scale") + totalA.get("Tropical Scale") + totalP.get("Tropical Scale");
	}

	@Listen("onChange=#resultsCombo")
	public void showResultsFor() throws IOException
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		if ( resultsCombo.getSelectedItem().getValue().equals( regPart) ) {
			partBuilder();

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
			partBuilder();			

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
			partBuilder();

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
		else if ( resultsCombo.getSelectedItem().getValue().equals(regDis) ) {
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
		else if ( resultsCombo.getSelectedItem().getValue().equals(nanoSpec) ) {
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
		}
	}

	void updateResTables()
	{
		Locale locale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 

		NumberFormat formatter = new DecimalFormat("0.##E0", otherSymbols); 

		if ( resultsCombo.getSelectedItem().getValue().equals( regDis ) ) {		
			airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );	
			freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );
			otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Reg").get("Dissolved/Gas species (G/D)") ) ) );			
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals( contDis ) ) {		
			airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );	
			freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );
			otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Cont").get("Dissolved/Gas species (G/D)") ) ) );						
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals( globDis ) ) {
			modAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Mod").get("Dissolved/Gas species (G/D)") ) ) );	
			modSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") ) ) );
			modDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Mod").get("Dissolved/Gas species (G/D)") ) ) );
			modSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Mod").get("Dissolved/Gas species (G/D)") ) ) );
			modSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Mod").get("Dissolved/Gas species (G/D)") ) ) );

			arctAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Arct").get("Dissolved/Gas species (G/D)") ) ) );	
			arctSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") ) ) );
			arctDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Arct").get("Dissolved/Gas species (G/D)") ) ) );
			arctSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Arct").get("Dissolved/Gas species (G/D)") ) ) );
			arctSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Arct").get("Dissolved/Gas species (G/D)") ) ) );

			tropAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Trop").get("Dissolved/Gas species (G/D)") ) ) );	
			tropSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") ) ) );
			tropDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Trop").get("Dissolved/Gas species (G/D)") ) ) );
			tropSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Trop").get("Dissolved/Gas species (G/D)") ) ) );
			tropSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Trop").get("Dissolved/Gas species (G/D)") ) ) );			
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(regPart) ) {
			airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );	
			freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );
			otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Reg").get("Species attached to NCs (<450 nm) (A)") ) ) );						
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(contPart) ) {
			airRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Air").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );	
			freshWaterlakeRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water lake").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			freshWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			seaWaterRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Surface sea/ocean water").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			freshWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Fresh water sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			seaWaterSedRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Marine sediment").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			naturalSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Natural soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			agriSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Agricultural soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );
			otherSoilRes.setValue( formatter.format( Double.valueOf(  concentrations.get("Other soil").get("Cont").get("Species attached to NCs (<450 nm) (A)") ) ) );						
		}
		else if ( resultsCombo.getSelectedItem().getValue().equals(globPart) ) {
			modAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Mod").get("Species attached to NCs (<450 nm) (A)") ) ) );	
			modSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Mod").get("Species attached to NCs (<450 nm) (A)") ) ) );
			modDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Mod").get("Species attached to NCs (<450 nm) (A)") ) ) );
			modSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Mod").get("Species attached to NCs (<450 nm) (A)") ) ) );
			modSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Mod").get("Species attached to NCs (<450 nm) (A)") ) ) );

			arctAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Arct").get("Species attached to NCs (<450 nm) (A)") ) ) );	
			arctSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Arct").get("Species attached to NCs (<450 nm) (A)") ) ) );
			arctDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Arct").get("Species attached to NCs (<450 nm) (A)") ) ) );
			arctSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Arct").get("Species attached to NCs (<450 nm) (A)") ) ) );
			arctSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Arct").get("Species attached to NCs (<450 nm) (A)") ) ) );

			tropAirRes.setValue( formatter.format( Double.valueOf(  masses.get("Air").get("Trop").get("Species attached to NCs (<450 nm) (A)") ) ) );	
			tropSurfSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Surface sea/ocean water").get("Trop").get("Species attached to NCs (<450 nm) (A)") ) ) );
			tropDeepSeaWRes.setValue( formatter.format( Double.valueOf(  masses.get("Deep sea/ocean water").get("Trop").get("Species attached to NCs (<450 nm) (A)") ) ) );
			tropSeaWSedRes.setValue( formatter.format( Double.valueOf(  masses.get("Marine sediment").get("Trop").get("Species attached to NCs (<450 nm) (A)") ) ) );
			tropSoilRes.setValue( formatter.format( Double.valueOf(  masses.get("Other soil").get("Trop").get("Species attached to NCs (<450 nm) (A)") ) ) );			
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
		int iRowsNum = 103;
		for ( int i = 0; i < iRowsNum; i++ )
			rows.add( spreadsheet.createRow( i ) );

		rows.get(0).createCell( 1 ).setCellValue( "Output table 1:  Steady-state Concentrations, Fugacities, Emissions and Mass" );
		rows.get(0).getCell(1).setCellStyle(boldStyle);

		//leave a blank row
		rows.get(1).createCell( 0 ).setCellValue(" ");			

		rows.get(2).createCell( 1 ).setCellValue("Model");			
		rows.get(2).getCell( 1 ).setCellStyle( boldStyle );
		rows.get(2).createCell( 2 ).setCellValue("SimpleBox4nano vs 4.01-nano 44139");			

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
		prop1.add("Dissolved/Gas species (G/D)");
		prop1.add("* GAS PHASE");
		prop1.add("* AEROSOL- and CLOUD PHASES");
		prop1.add("Solid species (S)");
		prop1.add("Species attached to NCs (<450 nm) (A)");
		prop1.add("Species attached to suspended particles (>450 nm) (P)");

		ArrayList<String> prop2 = new ArrayList<String>();
		prop2.add("Dissolved/Gas species (G/D)");
		prop2.add("* PORE WATER");
		prop2.add("* SOLID PHASE");
		prop2.add("Solid species (S)");
		prop2.add("Species attached to NCs (<450 nm) (A)");
		prop2.add("Species attached to suspended particles (>450 nm) (P)");

		ArrayList<String> prop3 = new ArrayList<String>();
		prop3.add("Dissolved/Gas species (G/D)");
		prop3.add("* DISSOLVED");
		prop3.add("* SUSPENDED SOLIDS");
		prop3.add("Solid species (S)");
		prop3.add("Species attached to NCs (<450 nm) (A)");
		prop3.add("Species attached to suspended particles (>450 nm) (P)");

		ArrayList<String> prop4 = new ArrayList<String>();
		prop4.add("Dissolved/Gas species (G/D)");
		prop4.add("* PORE WATER / GROUNDWATER");
		prop4.add("* SOLID PHASE");
		prop4.add("Solid species (S)");
		prop4.add("Species attached to NCs (<450 nm) (A)");
		prop4.add("Species attached to suspended particles (>450 nm) (P)");

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
		rows.get( iRow ).createCell(1).setCellValue("Fresh water lake sediment");
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
			rows.get( iRow++ ).createCell(j).setCellValue(masses.get("Fresh water lake sediment").get(regions.get(k)).get("Fresh water lake sediment"));
			for ( String entry:prop2) 
				rows.get( iRow++ ).createCell(j).setCellValue( masses.get("Fresh water lake sediment").get(regions.get(k)).get( entry ) );

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
			for ( String entry:prop2) 
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
			rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
			rows.get( iRow++ ).createCell(j).setCellValue(concentrations.get("Fresh water lake sediment").get(regions.get(k)).get("Fresh water lake sediment"));
			for ( String entry:prop2) {
				if ( entry ==  "* PORE WATER" )
					rows.get( iRow ).createCell(17).setCellValue( "g.L-1" );		
				else
					rows.get( iRow ).createCell(17).setCellValue( "g.kg(w)-1" );		
				rows.get( iRow++ ).createCell(j).setCellValue( concentrations.get("Fresh water lake sediment").get(regions.get(k)).get( entry ) );
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
			for ( String entry:prop2) 
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

		//Writing fugacities (Pa) for Reg and Cont
		k = 0;
		for ( int j = 18; j <= 19; j++, k++) {
			iRow = 9;
			iRow+=2;
			for ( String entry:prop1) 
				if ( fugacities.get("Air").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Air").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				if ( fugacities.get("Fresh water lake").get(regions.get(k)).get( entry ) != null )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Fresh water lake").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop2) 
				iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				if ( fugacities.get("Fresh water").get(regions.get(k)).get( entry ) != null )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Fresh water").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop2) 		
				if ( fugacities.get("Fresh water sediment").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Fresh water sediment").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				if ( fugacities.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				iRow++;

			iRow+=2;
			for ( String entry:prop2) 
				if ( fugacities.get("Marine sediment").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Marine sediment").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop4) 
				if ( fugacities.get("Natural soil").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Natural soil").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop4) 
				if ( fugacities.get("Agricultural soil").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Agricultural soil").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop4) 
				if ( fugacities.get("Other soil").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Other soil").get(regions.get(k)).get( entry ) );
				else
					iRow++;

		}

		//Writing fugacities for Mod, Arct and Trop
		k = 2;
		for ( int j = 20; j <= 22; j++, k++) {
			iRow = 9;
			iRow+=2;
			for ( String entry:prop1) 
				if ( fugacities.get("Air").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Air").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				iRow++;

			iRow+=2;
			for ( String entry:prop2) 
				iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				iRow++;

			iRow+=2;
			for ( String entry:prop2) 
				iRow++;

			iRow+=2;
			for ( String entry:prop3) 
				if ( fugacities.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Surface sea/ocean water").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop3) {
				if ( fugacities.get("Deep sea/ocean water").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Deep sea/ocean water").get(regions.get(k)).get( entry ) );
				else
					iRow++;
			}

			iRow+=2;
			for ( String entry:prop2) 
				if ( fugacities.get("Marine sediment").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Marine sediment").get(regions.get(k)).get( entry ) );
				else
					iRow++;

			iRow+=2;
			for ( String entry:prop4) 
				iRow++;

			iRow+=2;
			for ( String entry:prop4) 
				iRow++;

			iRow+=2;
			for ( String entry:prop4) 
				if ( fugacities.get("Other soil").get(regions.get(k)).get( entry ) != null  )
					rows.get( iRow++ ).createCell(j).setCellValue( fugacities.get("Other soil").get(regions.get(k)).get( entry ) );
				else
					iRow++;

		}
		
		//Re-build in order to write the info - Disolved species
		disBuilder();
		int iCol = addNewSheet( "Steady state mass flows for dissolved species", "Output table 2: Steady-state mass flows for D species only (kg.s-1)",
				workbook, regions, iRow);

		partBuilder();
		iCol = addNewSheet( "Steady state mass flows for particulate species", "Output table 2b: Steady-state mass flows for total particulate species only (kg.s-1)",
				workbook, regions, iRow);
		
		// STEADY-STATE Nano
		spreadsheet = workbook.createSheet("Nano micro output");

		int iRowsNumNano = 100;
		// Create the rows used
		ArrayList< XSSFRow > rowsNano = new ArrayList< XSSFRow >();

		for ( int i = 0; i < iRowsNumNano; i++ )
			rowsNano.add( spreadsheet.createRow( i ) );

		rowsNano.get(0).createCell( 1 ).setCellValue( "Nano micro output" );
		rowsNano.get(0).getCell(1).setCellStyle(boldStyle);

		//leave a blank row
		rowsNano.get(1).createCell( 0 ).setCellValue(" ");			

		rowsNano.get(2).createCell( 1 ).setCellValue("Model");	
		rowsNano.get(2).getCell( 1 ).setCellStyle( boldStyle );	

		rowsNano.get(2).createCell( 2 ).setCellValue("SimpleBox4nano vs 4.01-nano 44139");			

		rowsNano.get(3).createCell( 1 ).setCellValue( "Substance" );			
		rowsNano.get(3).getCell( 1 ).setCellStyle( boldStyle );

		rowsNano.get(3).createCell( 2 ).setCellValue( nanomaterialName );			

		rowsNano.get(4).createCell( 1 ).setCellValue("Scenario");			
		rowsNano.get(4).getCell( 1 ).setCellStyle( boldStyle );

		rowsNano.get(4).createCell( 2 ).setCellValue( scenarioName );			

		ArrayList<String> regLabels = new ArrayList<String>();
		regLabels.add("Regional");
		regLabels.add("Continental");
		regLabels.add("Moderate");
		regLabels.add("Arctic");
		regLabels.add("Tropical");

		iCol = 2;
		for ( String entry:regLabels) 
			rowsNano.get( 10 ).createCell( iCol++ ).setCellValue( entry );

		regions.clear();
		regions.add("Reg");
		regions.add("Cont");
		regions.add("Mod");
		regions.add("Arct");
		regions.add("Trop");

		rowsNano.get(9).createCell( 1 ).setCellValue( "Species Concentration free ENPs (S)" );			
		rowsNano.get(9).getCell(1).setCellStyle(boldStyle);	

		iRow = 11;
		for ( String entry:teras) {
			rowsNano.get(iRow).createCell( 1 ).setCellValue( entry );
			rowsNano.get(iRow).getCell(1).setCellStyle(boldStyle);
			iRow++;
		}

		iCol = 2;
		for ( String reg:regions) {
			iRow = 11;
			for ( String entry:teras) {
				if ( concentrations.get( entry ).get( reg ) != null )
					rowsNano.get( iRow++ ).createCell( iCol ).setCellValue( concentrations.get( entry ).get( reg ).get("Solid species (S)") );
				else
					iRow++;
			}

			iCol++;
		}

		rowsNano.get(23).createCell( 1 ).setCellValue( "Species Concentration  colloidal heteroagglomerates ENP <450nm (A)");			
		rowsNano.get(23).getCell(1).setCellStyle(boldStyle);	

		iCol = 2;
		for ( String entry:regLabels) 
			rowsNano.get( 24 ).createCell( iCol++ ).setCellValue( entry );

		iRow = 25;
		for ( String entry:teras) {
			rowsNano.get(iRow).createCell( 1 ).setCellValue( entry );
			rowsNano.get(iRow).getCell(1).setCellStyle(boldStyle);
			iRow++;
		}

		iCol = 2;
		for ( String reg:regions) {
			iRow = 25;
			for ( String entry:teras) {
				if ( concentrations.get( entry ).get( reg ) != null )
					rowsNano.get( iRow++ ).createCell( iCol ).setCellValue( concentrations.get( entry ).get( reg ).get("Species attached to NCs (<450 nm) (A)") );
				else
					iRow++;
			}

			iCol++;
		}

		rowsNano.get(37).createCell( 1 ).setCellValue( "Species Concentration  ENP coarse heteroagglomerates > 450nm (P)");			
		rowsNano.get(37).getCell(1).setCellStyle(boldStyle);	

		iCol = 2;
		for ( String entry:regLabels) 
			rowsNano.get( 38 ).createCell( iCol++ ).setCellValue( entry );

		iRow = 39;
		for ( String entry:teras) {
			rowsNano.get(iRow).createCell( 1 ).setCellValue( entry );
			rowsNano.get(iRow).getCell(1).setCellStyle(boldStyle);
			iRow++;
		}

		iCol = 2;
		for ( String reg:regions) {
			iRow = 39;
			for ( String entry:teras) {
				if ( concentrations.get( entry ).get( reg ) != null )
					rowsNano.get( iRow++ ).createCell( iCol ).setCellValue( concentrations.get( entry ).get( reg ).get("Species attached to suspended particles (>450 nm) (P)") );
				else
					iRow++;
			}

			iCol++;
		}


		rowsNano.get(52).createCell( 1 ).setCellValue( "Total concentration ENPs");			
		rowsNano.get(52).getCell(1).setCellStyle(boldStyle);	

		iCol = 2;
		for ( String entry:regLabels) 
			rowsNano.get( 53 ).createCell( iCol++ ).setCellValue( entry );

		iRow = 54;
		for ( String entry:teras) {
			rowsNano.get(iRow).createCell( 1 ).setCellValue( entry );
			rowsNano.get(iRow).getCell(1).setCellStyle(boldStyle);
			iRow++;
		}

		iCol = 2;
		for ( String reg:regions) {
			iRow = 54;
			for ( String entry:teras) {
				if ( concentrations.get( entry ).get( reg ) != null )
					rowsNano.get( iRow++ ).createCell( iCol ).setCellValue( 
							concentrations.get( entry ).get( reg ).get("Solid species (S)")+
							concentrations.get( entry ).get( reg ).get("Species attached to suspended particles (>450 nm) (P)")+
							concentrations.get( entry ).get( reg ).get("Species attached to suspended particles (>450 nm) (P)") );
				else
					iRow++;
			}

			iCol++;
		}

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
		transLabels.add("Formation (S,A,P->D)");
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
		iRow = 10;
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

		//Write degradation
		iRow = 10;
		for (String region:regions) {
			if ( region == "Regional Scale" || region == "Continental Scale" ) {
				for ( int i= 1; i <= compartLabels.size(); i++ ) {
					if ( degradation.get(region).get( compartLabels.get(i - 1) ) != null )
						rowsSS.get( iRow + i ).createCell( 7 ).setCellValue( degradation.get(region).get( compartLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 7 ).setCellValue("");
				}

				iRow += compartLabels.size()+1;

			}
			else {
				for ( int i= 1; i <= globalLabels.size(); i++ ) 
					if ( degradation.get(region).get( globalLabels.get( i - 1 ) ) != null )
						rowsSS.get( iRow + i ).createCell( 7 ).setCellValue( degradation.get(region).get( globalLabels.get(i - 1) ) );
					else
						rowsSS.get( iRow + i ).createCell( 7 ).setCellValue("");

				iRow += globalLabels.size()+1;
			}
		}

		//Write air-water transport
		iCol = 8;
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



