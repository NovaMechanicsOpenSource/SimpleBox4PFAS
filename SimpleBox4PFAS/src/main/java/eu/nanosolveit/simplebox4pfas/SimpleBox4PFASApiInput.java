package eu.nanosolveit.simplebox4pfas;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Simple box 4 nano input")
public class SimpleBox4PFASApiInput {
	@Getter @Setter
	@ApiModelProperty(value="Scenarios's name")
	String scenario;
	
	@Getter @Setter
	@ApiModelProperty(value="Substance's name")
	String nanomaterial;
	
	@Getter @Setter
	@ApiModelProperty(value="The molecular weight of the substance")
	Double Molweight;

	@Getter @Setter
	@ApiModelProperty(value="Density ENP")
	Double RhoS;
	
	//Emission rates
	@Getter @Setter
	@ApiModelProperty(value="Emission to air")
	Double E_aRS;

	@Getter @Setter
	@ApiModelProperty(value="Emission to lake water")
	Double E_w0RS;
	
	@Getter @Setter
	@ApiModelProperty(value="Emission to fresh water")
	Double E_w1RS;

	@Getter @Setter
	@ApiModelProperty(value="Emission to sea water")
	Double E_w2RS;

	@Getter @Setter
	@ApiModelProperty(value="Emission to natural soil")
	Double E_s1RS;

	@Getter @Setter
	@ApiModelProperty(value="Emission to agricaltural soil")
	Double E_s2RS;

	@Getter @Setter
	@ApiModelProperty(value="Emission to other soil")
	Double E_s3RS;

	//Advanced 
	//Regional
	@Getter @Setter
	@ApiModelProperty(value="Regional area land")
	Double AREAland_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional area sea")
	Double AREAsea_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional fraction lake water")
	Double FRAClake_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional fraction fresh water")
	Double FRACfresh_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional fraction natural water")
	Double FRACnatsoil_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional fraction agricultural water")
	Double FRACagsoil_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional fraction urban/industrial soil")
	Double FRACothersoil_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional average precipitation")
	Double RAINrate_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional temperature")
	Double TEMP_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional wind speed")
	Double WINDspeed_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional depth lake water")
	Double DEPTHlake_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional depth fresh water")
	Double DEPTHfreshwater_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional fraction run off")
	Double FRACrun_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional infiltration run off")
	Double FRACinf_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional soil erosion")
	Double EROSION_R;

	@Getter @Setter
	@ApiModelProperty(value="Regional concentration suspended matter in lake water")
	Double SUSP_w0R;

	@Getter @Setter
	@ApiModelProperty(value="Regional concentration suspended matter in fresh water")
	Double SUSP_w1R;

	@Getter @Setter
	@ApiModelProperty(value="Regional concentration suspended matter in sea water")
	Double SUSP_w2R;
	
	//Continental
	@Getter @Setter
	@ApiModelProperty(value="Total continental area land")
	Double TOTAREALand_C; 

	@Getter @Setter
	@ApiModelProperty(value="Total continental area sea")
	Double TOTAREAsea_C;  

	@Getter @Setter
	@ApiModelProperty(value="Continental fraction lake water")
	Double FRAClake_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental fraction fresh water")
	Double FRACfresh_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental fraction natural water")
	Double FRACnatsoil_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental fraction agricultural water")
	Double FRACagsoil_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental fraction urban/industrial soil")
	Double FRACothersoil_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental average precipitation")
	Double RAINrate_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental temperature")
	Double TEMP_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental wind speed")
	Double WINDspeed_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental depth lake water")
	Double DEPTHlake_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental depth fresh water")
	Double DEPTHfreshwater_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental fraction run off")
	Double FRACrun_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental infiltration run off")
	Double FRACinf_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental soil erosion")
	Double EROSION_C;

	@Getter @Setter
	@ApiModelProperty(value="Continental concentration suspended matter in lake water")
	Double SUSP_w0C;

	@Getter @Setter
	@ApiModelProperty(value="Continental concentration suspended matter in fresh water")
	Double SUSP_w1C;

	@Getter @Setter
	@ApiModelProperty(value="Continental concentration suspended matter in sea water")
	Double SUSP_w2C;
	
	static public Map<String, Map<String, String> > getNanoData( SimpleBox4PFASApiInput input ) {
		Map<String, Map<String, String> > nanoData = new HashMap<String, Map<String, String> >(); 
		
		nanoData.put( input.getNanomaterial(), new HashMap<String, String>() );  
		nanoData.get( input.getNanomaterial() ).put("Molweight", String.valueOf( input.getMolweight() ) );  		
		nanoData.get( input.getNanomaterial()).put("RhoS", String.valueOf( input.getRhoS() ) ); 
		
		return nanoData;
	}
	
	static public Map<String, Scenario> getScenariosData( SimpleBox4PFASApiInput input ) {
		Map<String, Scenario> scenariosData = new HashMap<String, Scenario>();
		scenariosData.put( input.getScenario(), new Scenario( input.getScenario() ) );
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.aRS",  String.valueOf(input.getE_aRS()  ));
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.w0RS", String.valueOf(input.getE_w0RS() ));
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.w1RS", String.valueOf(input.getE_w1RS() ));
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.w2RS", String.valueOf(input.getE_w2RS() ));
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.s1RS", String.valueOf(input.getE_s1RS() ));
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.s2RS", String.valueOf(input.getE_s2RS() ));
		scenariosData.get( input.getScenario() ).putSolidInfo("REGIONAL", "E.s3RS", String.valueOf(input.getE_s3RS() ));
		
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "AREAland.R",  String.valueOf(input.getAREAland_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "AREAsea.R",  String.valueOf(input.getAREAsea_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRAClake.R",  String.valueOf( input.getFRAClake_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRACfresh.R",  String.valueOf(input.getFRACfresh_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRACnatsoil.R",  String.valueOf(input.getFRACnatsoil_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRACagsoil.R",  String.valueOf(input.getFRACagsoil_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRACothersoil.R",  String.valueOf(input.getFRACothersoil_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "RAINrate.R",  String.valueOf(input.getRAINrate_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "TEMP.R",  String.valueOf(input.getTEMP_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "WINDspeed.R",  String.valueOf(input.getWINDspeed_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "DEPTHlake.R",  String.valueOf(input.getDEPTHlake_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "DEPTHfreshwater.R",  String.valueOf(input.getDEPTHfreshwater_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRACrun.R",  String.valueOf(input.getFRACrun_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "FRACinf.R",  String.valueOf(input.getFRACinf_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "EROSION.R",  String.valueOf(input.getEROSION_R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "SUSP.w0R",  String.valueOf(input.getSUSP_w0R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "SUSP.w1R",  String.valueOf(input.getSUSP_w1R() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("REGIONAL", "SUSP.w2R",  String.valueOf(input.getSUSP_w2R() ) ) ;
				
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "AREAland.C",  String.valueOf(input.getTOTAREALand_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "AREAsea.C",  String.valueOf(input.getTOTAREAsea_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRAClake.C",  String.valueOf(input.getFRAClake_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRACfresh.C",  String.valueOf(input.getFRACfresh_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRACnatsoil.C",  String.valueOf(input.getFRACnatsoil_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRACagsoil.C",  String.valueOf(input.getFRACagsoil_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRACothersoil.C",  String.valueOf(input.getFRACothersoil_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "RAINrate.C",  String.valueOf(input.getRAINrate_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "TEMP.C",  String.valueOf(input.getTEMP_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "WINDspeed.C",  String.valueOf(input.getWINDspeed_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "DEPTHlake.C",  String.valueOf(input.getDEPTHlake_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "DEPTHfreshwater.C",  String.valueOf(input.getDEPTHfreshwater_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRACrun.C",  String.valueOf(input.getFRACrun_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "FRACinf.C",  String.valueOf(input.getFRACinf_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "EROSION.C",  String.valueOf(input.getEROSION_C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "SUSP.w0C",  String.valueOf(input.getSUSP_w0C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "SUSP.w1C",  String.valueOf(input.getSUSP_w1C() ) ) ;
		scenariosData.get( input.getScenario() ).putLandscapeInfo("CONTINENTAL", "SUSP.w2C",  String.valueOf(input.getSUSP_w2C() ) ) ;
		return scenariosData;
	}
}
