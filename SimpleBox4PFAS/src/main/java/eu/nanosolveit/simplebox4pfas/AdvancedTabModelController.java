package eu.nanosolveit.simplebox4pfas;

import java.io.IOException;
import java.util.HashMap;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

public class AdvancedTabModelController extends SelectorComposer<Window>{

	Window win, attach;
	Scenario scenario;
	
	@Override
	public void doAfterCompose(Window comp ) throws Exception {
		super.doAfterCompose(comp);
		this.win = comp;	

		//Get the scenario as defined in the main window
		Session current = Sessions.getCurrent();	
		scenario = (Scenario) current.getAttribute("Scenario");		
	}

	@Listen("onClick=#regional")
	public void setRegionalParams()
	{		
		attach = (Window) Executions.getCurrent().createComponents("regional.zul", null, null); 
		attach.setClosable(true);
		attach.doModal();					
	}
	
	@Listen("onClick=#continental")
	public void setContinentalParams()
	{
		attach = (Window) Executions.getCurrent().createComponents("continental.zul", null, null); 
		attach.setClosable(true);
		attach.doModal();					
	}


}