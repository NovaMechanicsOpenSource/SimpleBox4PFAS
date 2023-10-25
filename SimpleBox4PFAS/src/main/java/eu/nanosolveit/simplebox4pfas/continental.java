package eu.nanosolveit.simplebox4pfas;

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

//Continental is the exaclty the same as the regional ... 
public class continental  extends SelectorComposer<Window> {
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

	String area = "CONTINENTAL";
	
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
				scenario.getLandscapeInfo(area).put("TOTAREAland.C", String.valueOf( landArea.getValue() ) );
				scenario.getLandscapeInfo(area).put("TOTAREAsea.C", String.valueOf( seaArea.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRAClake.C", String.valueOf( lakeWaterFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACfresh.C", String.valueOf( freshWaterFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACnatsoil.C", String.valueOf( naturalSoilFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACagsoil.C", String.valueOf( AgriSoilFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACothersoil.C", String.valueOf( urbanIndiSoilFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("RAINrate.C", String.valueOf( avePrecipitation.getValue() ) );
				scenario.getLandscapeInfo(area).put("TEMP.C", String.valueOf( temp.getValue() ) );
				scenario.getLandscapeInfo(area).put("WINDspeed.C", String.valueOf( windSpeed.getValue() ) );
				scenario.getLandscapeInfo(area).put("DEPTHlake.C", String.valueOf( lakeWatDepth.getValue() ) );
				scenario.getLandscapeInfo(area).put("DEPTHfreshwater.C", String.valueOf( freshWatDepth.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACrun.C", String.valueOf( runOffFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("FRACinf.C", String.valueOf( infiltFrac.getValue() ) );
				scenario.getLandscapeInfo(area).put("EROSION.C", String.valueOf( soilEros.getValue() ) );

				if ( radioConcSuspMatWat.getSelectedIndex() == 0) {
					scenario.setAdvContChecked( false );
					scenario.getLandscapeInfo(area).put("SUSP.w0C", String.valueOf( dboxConcSuspMatWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w1C", String.valueOf( dboxConcSuspMatWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w2C", String.valueOf( dboxConcSuspMatWat.getValue() ) );
				}
				else {
					scenario.setAdvContChecked( true );
					scenario.getLandscapeInfo(area).put("SUSP.w0C", String.valueOf( concSuspLakeWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w1C", String.valueOf( concSuspFreshWat.getValue() ) );
					scenario.getLandscapeInfo(area).put("SUSP.w2C", String.valueOf( concSuspSeaWat.getValue() ) );					
				}

			}
		});
		
		//Get the scenario as defined in the main window
		Session current = Sessions.getCurrent();	
		scenario = (Scenario) current.getAttribute("Scenario");		

		advanced.setChecked(false);
		advancedParams.setVisible(false);		
		listConcSuspMatWat.setVisible(false);
		
		landArea.setValue( Double.valueOf( scenario.getLandscapeInfo( area ).get("TOTAREAland.C") ) );
		seaArea.setValue( Double.valueOf( scenario.getLandscapeInfo( area ).get("TOTAREAsea.C") ) );
		lakeWaterFrac.setValue( Double.valueOf( scenario.getLandscapeInfo( area ).get("FRAClake.C") ) );
		freshWaterFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACfresh.C") ) );
		naturalSoilFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACnatsoil.C") ) );
		AgriSoilFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACagsoil.C") ) );
		urbanIndiSoilFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACothersoil.C") ) );
		avePrecipitation.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("RAINrate.C") ) );
		
		temp.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("TEMP.C") ) );
		windSpeed.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("WINDspeed.C") ) );
		lakeWatDepth.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("DEPTHlake.C") ) );
		freshWatDepth.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("DEPTHfreshwater.C") ) );
		runOffFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACrun.C") ) );
		infiltFrac.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("FRACinf.C") ) );
		soilEros.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("EROSION.C") ) );
		
		dboxConcSuspMatWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w0C") ) );
		
		concSuspLakeWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w0C") ) );
		concSuspFreshWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w1C") ) );
		concSuspSeaWat.setValue( Double.valueOf( scenario.getLandscapeInfo(area).get("SUSP.w2C") ) );
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
