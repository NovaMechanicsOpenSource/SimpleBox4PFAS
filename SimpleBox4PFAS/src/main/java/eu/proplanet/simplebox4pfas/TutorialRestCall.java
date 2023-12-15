package eu.proplanet.simplebox4pfas;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import eu.proplanet.restapis.RESTApis;
import eu.proplanet.restapis.RESTClient;


/**
 * @author nikol
 *
 */

public class TutorialRestCall extends SelectorComposer<Window>{
	Window win;
	
	@Wire
	Iframe tutorial;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		win = comp;	
		
		try {
			tutorial.setContent( RESTClient.getTutorial("SimpleBox4Nano") ) ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
