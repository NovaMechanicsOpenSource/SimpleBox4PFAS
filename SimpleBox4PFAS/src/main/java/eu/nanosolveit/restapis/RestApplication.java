package eu.nanosolveit.restapis;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("apis")
public class RestApplication extends Application{
	
	public RestApplication() {
		init();
	}
	
	private void init() {
	      
  	  BeanConfig beanConfig = new BeanConfig();
      beanConfig.setVersion("1.0.0");
      beanConfig.setSchemes(new String[]{"https"});
      beanConfig.setHost("zetapotential.cloud.nanosolveit.eu"); // exposurepbpk.cloud.nanosolveit.eu //lungexposure.cloud.nanosolveit.eu//aerosol.cloud.nanosolveit.eu//exposurepbpk.cloud.nanosolveit.eu //zetapotential.cloud.nanosolveit.eu // aerosol.cloud.nanosolveit.eu // facetcytotoxicity.cloud.nanosolveit.eu//dermal.cloud.nanosolveit.eu // nanoxtract.cloud.nanosolveit.eu
      beanConfig.setBasePath("/apis"); // 
      beanConfig.setResourcePackage(RESTApis.class.getPackage().getName());
      beanConfig.setTitle("Zeta Potential Prediction RESTful API"); 
      beanConfig.setDescription("NanoSolveIT RESTful APIs for Nanoinformatics Model for Zeta Potential Prediction\n"); // NanoSolveIT RESTful APIs for NanoSolveIT IATA: PBPK models having an input of exposure timeseries which the user can produce using a variety of methods // NanoSolveIT RESTful APIs for Tool for Assessment of Human Exposure to Nanomaterials// NanoSolveIT RESTful APIs for Nanoinformatics Model for Zeta Potential Prediction // NanoSolveIT RESTful APIs for Facet Cytotoxicity: Predicts metal oxide toxicity utilizing facet-based electronic, image, and periodic table properties as descriptors// NanoSolveIT RESTful APIs for Tool for Assessment of Human Exposure to Nanomaterials //NanoSolveIT RESTful APIs for Dermal & Perioral Models
      beanConfig.setScan(true);
  }

}
