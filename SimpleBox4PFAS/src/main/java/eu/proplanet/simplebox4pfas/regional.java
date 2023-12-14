package eu.proplanet.simplebox4pfas;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;


public class regional extends SelectorComposer<Window> {
	@Wire
	Grid advancedParams;
	
	@Wire
	Checkbox advanced;
		
	Window win;
	
	@Wire
	Listbox listConcSuspMatWat;
	
	@Wire
	Radiogroup radioConcSuspMatWat;
	
	@Wire
	Hbox concSuspMatWatCoarse, concSuspMatWatDetailed, concSuspMatWatReporting;

	@Wire
	Label concSuspMatWatCompact;

	@Wire
	Doublebox concSuspLakeWat, concSuspFreshWat, concSuspSeaWat;
	
	@Wire 
	Doublebox landArea, seaArea, lakeWaterFrac, freshWaterFrac, naturalSoilFrac, AgriSoilFrac, urbanIndiSoilFrac,
	avePrecipitation;
	
	@Wire 
	Doublebox temp, windSpeed, lakeWatDepth, freshWatDepth, runOffFrac, infiltFrac, soilEros,
	dboxConcSuspMatWat;	
	
	String area = "REGIONAL";

	Scenario scenario;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception  
	{
		super.doAfterCompose(comp);
		win = comp;	
		win.addEventListener("onClose", new EventListener() {			
			@Override
			public void onEvent(Event event) throws Exception {
				//After closing the window the scenario must be updated
				scenario.getLandscapeInfo(area).put("AREAland.R", String.valueOf( landArea.getValue() ) );
				scenario.getLandscapeInfo(area).put("AREAsea.R", String.valueOf( seaArea.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRAClake.R", String.valueOf( lakeWaterFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACfresh.R", String.valueOf( freshWaterFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACnatsoil.R", String.valueOf( naturalSoilFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACagsoil.R", String.valueOf( AgriSoilFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACothersoil.R", String.valueOf( urbanIndiSoilFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("RAINrate.R", String.valueOf( avePrecipitation.getValue() ) );
			
				scenario.getLandscapeInfo(area).put("TEMP.R", String.valueOf( temp.getValue() ) );
				scenario.getLandscapeInfo(area).put("WINDspeed.R", String.valueOf( windSpeed.getValue() ) );
				scenario.getLandscapeInfo(area).put("DEPTHlake.R", String.valueOf( lakeWatDepth.getValue() ) );
				scenario.getLandscapeInfo(area).put("DEPTHfreshwater.R", String.valueOf( freshWatDepth.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACrun.R", String.valueOf( runOffFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACinf.R", String.valueOf( infiltFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("EROSION.R", String.valueOf( soilEros.getValue() ) );				
				
				if ( radioConcSuspMatWat.getSelectedIndex() == 0) {
					scenario.setAdvRegChecked( false );
					scenario.getLandscapeInfo(area).put("SUSP.w0R", String.valueOf( dboxConcSuspMatWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w1R", String.valueOf( dboxConcSuspMatWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w2R", String.valueOf( dboxConcSuspMatWat.getValue() ) );
				}
				else {					
					scenario.setAdvRegChecked( true );
					scenario.getLandscapeInfo(area).put("SUSP.w0R", String.valueOf( concSuspLakeWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w1R", String.valueOf( concSuspFreshWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w2R", String.valueOf( concSuspSeaWat.getValue() ) );					
				}
			}
		});

		//Get the scenario as defined in the main window
		Session current = Sessions.getCurrent();	
		scenario = (Scenario) current.getAttribute("Scenario");		
				
		advanced.setChecked(false);
		advancedParams.setVisible(false);		
		listConcSuspMatWat.setVisible(false);
		
		landArea.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("AREAland.R") ) );
		seaArea.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("AREAsea.R") ) );
		lakeWaterFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRAClake.R") ) );
		freshWaterFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACfresh.R") ) );
		naturalSoilFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACnatsoil.R") ) );
		AgriSoilFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACagsoil.R") ) );
		urbanIndiSoilFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACothersoil.R") ) );
		avePrecipitation.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("RAINrate.R") ) );
		
		temp.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("TEMP.R") ) );
		windSpeed.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("WINDspeed.R") ) );
		lakeWatDepth.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("DEPTHlake.R") ) );
		freshWatDepth.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("DEPTHfreshwater.R") ) );
		runOffFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACrun.R") ) );
		infiltFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACinf.R") ) );
		soilEros.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("EROSION.R") ) );
		
		dboxConcSuspMatWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w0R") ) );
		
		concSuspLakeWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w0R") ) );
		concSuspFreshWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w1R") ) );
		concSuspSeaWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w2R") ) );
	}

	@Listen("onCheck=#advanced")
	public void checkAdvanced()
	{
		if ( advanced.isChecked() ) {
			advancedParams.setVisible(true);
			listConcSuspMatWat.setVisible(true);
		}
		else {
			advancedParams.setVisible(false);
			listConcSuspMatWat.setVisible(false);
		}
	}
	
	@Listen("onSelect=#listConcSuspMatWat")
	public void setLine(Event ev)
	{
		Listitem item = listConcSuspMatWat.getSelectedItem();

		if( radioConcSuspMatWat.getSelectedIndex() == 1)
		{
			concSuspMatWatCoarse.setVisible(false);

			if(item.getId().equals("itemConcSuspMatWat"))
			{
				concSuspMatWatDetailed.setVisible(true);
				concSuspMatWatReporting.setVisible(false);
			}
			else
			{
				concSuspMatWatDetailed.setVisible(false);
				concSuspMatWatReporting.setVisible(true);
			}
		}
		else
		{
			concSuspMatWatCoarse.setVisible(true);
			concSuspMatWatDetailed.setVisible(false);
			concSuspMatWatReporting.setVisible(false);
		}
		
		setCompactOfNotSelected();
		listConcSuspMatWat.clearSelection();
	}
	
	@Listen("onCheck=#radioConcSuspMatWat")
	public void setSelectedAirRadioDeg(CheckEvent ev)
	{
		listConcSuspMatWat.setSelectedIndex(0);
		setLine(ev);
	}
	
	void setCompactOfNotSelected()
	{
		try
		{
			concSuspMatWatCompact.setValue("(Concentration of suspended matter in lake water = " + concSuspLakeWat.getText() 
					+ " [mg/L], Concentration of suspended matter in fresh water = " + concSuspFreshWat.getText() 
					+ " [mg/L], Concentration of suspended matter in sea water = " + concSuspSeaWat.getText() 
					+ " [mg/L])");
		}
		catch(Exception e)
		{
			concSuspMatWatCompact.setValue("Missing values");
		}
	}
}
