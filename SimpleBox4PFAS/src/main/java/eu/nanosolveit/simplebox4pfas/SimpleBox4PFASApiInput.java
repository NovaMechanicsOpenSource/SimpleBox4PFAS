package eu.nanosolveit.simplebox4nano;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="Simple box 4 nano input")
public class SimpleBox4NanoApiInput {
	@Getter @Setter
	@ApiModelProperty(value="Scenarios's name")
	String scenario;
	
	@Getter @Setter
	@ApiModelProperty(value="Nanomaterial's name")
	String nanomaterial;
	
	@Getter @Setter
	@ApiModelProperty(value="The molecular weight of the nanomaterial substance")
	Double Molweight;

	@Getter @Setter
	@ApiModelProperty(value="Radius ENP")
	Double RadS;

	@Getter @Setter
	@ApiModelProperty(value="Density ENP")
	Double RhoS;
	
	@Getter @Setter
	@ApiModelProperty(value="Hamaker constant heteroagglomerate (ENP, water, SiO2)")
	Double AHamakerSP_w;

	@Getter @Setter
	@ApiModelProperty(value="Attachment efficiency of water")
	Double freshWater;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and lake water NCs (<450 nm)")
	Double AtefSA_w0;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and lake water SPM (>450 nm)")
	Double AtefSP_w0;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and fresh water NCs (<450 nm)")
	Double AtefSA_w1;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and fresh water SPM (>450 nm)")
	Double AtefSP_w1;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and natural soil NCs (<450 nm)")
	Double AtefSA_s1;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and natural soil grains")
	Double AtefSP_s1;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and agricultural soil NCs (<450 nm)")
	Double AtefSA_s2;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and agricultural soil grains")
	Double AtefSP_s2;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and other soil NCs (<450 nm)")
	Double AtefSA_s3;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and other soil grains")
	Double AtefSP_s3;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and lake sediment NCs (<450 nm)")
	Double AtefSA_sd0;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and lake sediment grains")
	Double AtefSP_sd0;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and fresh sediment NCs (<450 nm)")
	Double AtefSA_sd1;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and fresh sediment grains")
	Double AtefSP_sd1;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and marine sediment NCs (<450 nm)")
	Double AtefSA_sd2;

	@Getter @Setter
	@ApiModelProperty(value="Attachment Efficiency of ENPs and marine sediment grains")
	Double AtefSP_sd2;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in lake water")
	Double kdis_w0S_w0D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in fresh water")
	Double kdis_w1S_w1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in lake water")
	Double kdis_w0A_w0D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and SPM (P) in lake water")
	Double kdis_w0P_w0D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in fresh water")
	Double kdis_w1A_w1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and SPM (P) in fresh water")
	Double kdis_w1P_w1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in sea water")
	Double kdis_w2S_w2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in sea water")
	Double kdis_w2A_w2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and SPM (P) in sea water")
	Double kdis_w2P_w2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in natural soil pore water")
	Double kdis_s1S_s1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in other soil pore water")
	Double kdis_s2S_s2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in other soil pore water")
	Double kdis_s3S_s3D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in natural soil pore water")
	Double kdis_s1A_s1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and FP (P) in natural soil")
	Double kdis_s1P_s1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in agricultural soil pore water")
	Double kdis_s2A_s2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and FP (P) in agricultural soil")
	Double kdis_s2P_s2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in other soil pore water")
	Double kdis_s3A_s3D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and FP (P) in other soil")
	Double kdis_s3P_s3D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in lake sediment")
	Double kdis_sd0S_sd0D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in fresh sediment")
	Double kdis_sd1S_sd1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in lake sediment")
	Double kdis_sd0A_sd0D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and FP (P) in lake sediment")
	Double kdis_sd0P_sd0D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in fresh sediment")
	Double kdis_sd1A_sd1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and FP (P) in fresh sediment")
	Double kdis_sd1P_sd1D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in marine sediment")
	Double kdis_sd2S_sd2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of ENPs (S) in deep sea water")
	Double kdis_w3S_w3D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in marine sediment")
	Double kdis_sd2A_sd2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and FP (P) in marine sediment")
	Double kdis_sd2P_sd2D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and NCs (A) in deep sea water")
	Double kdis_w3A_w3D;

	@Getter @Setter
	@ApiModelProperty(value="Dissolution rate of heteroagglomerates of ENPs and SPM (P) in deep sea water")
	Double kdis_w3P_w3D;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in air")
	Double kdeg_aS;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in lake water")
	Double kdeg_aA;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and SPM (P) in lake water")
	Double kdeg_aP;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in lake water")
	Double kdeg_w0S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in fresh water")
	Double kdeg_w1S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in lake water")
	Double kdeg_w0A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and SPM (P) in lake water")
	Double kdeg_w0P;
	
	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in fresh water")
	Double kdeg_w1A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and SPM (P) in fresh water")
	Double kdeg_w1P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in sea water")
	Double kdeg_w2S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in sea water")
	Double kdeg_w2A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and SPM (P) in sea water")
	Double kdeg_w2P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in natural soil pore water")
	Double kdeg_s1S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in agricultural soil pore water")
	Double kdeg_s2S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in other soil pore water")
	Double kdeg_s3S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in natural soil pore water")
	Double kdeg_s1A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and FP (P) in natural soil")
	Double kdeg_s1P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in agricultural soil pore water")
	Double kdeg_s2A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and FP (P) in agricultural soil")
	Double kdeg_s2P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in other soil pore water")
	Double kdeg_s3A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and FP (P) in other soil")
	Double kdeg_s3P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in lake sediment")
	Double kdeg_sd0S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in fresh sediment")
	Double kdeg_sd1S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in lake sediment")
	Double kdeg_sd0A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and FP (P) in lake sediment")
	Double kdeg_sd0P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in fresh sediment")
	Double kdeg_sd1A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and FP (P) in fresh sediment")
	Double kdeg_sd1P;
	
	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in marine sediment")
	Double kdeg_sd2S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of ENPs (S) in deep sea water")
	Double kdeg_w3S;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in marine sediment")
	Double kdeg_sd2A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and FP (P) in marine sediment")
	Double kdeg_sd2P;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and NCs (A) in deep sea water")
	Double kdeg_w3A;

	@Getter @Setter
	@ApiModelProperty(value="Degradation rate constant of heteroagglomerates of ENPs and SPM (P) in deep sea water")
	Double kdeg_w3P;

	//Emission rates
	@Getter @Setter
	@ApiModelProperty(value="Descr")
	Double E_aRS;

	@Getter @Setter
	@ApiModelProperty(value="Descr")
	Double E_w0RS;
	
	@Getter @Setter
	@ApiModelProperty(value="Descr")
	Double E_w1RS;

	@Getter @Setter
	@ApiModelProperty(value="Descr")
	Double E_w2RS;

	@Getter @Setter
	@ApiModelProperty(value="Descr")
	Double E_s1RS;

	@Getter @Setter
	@ApiModelProperty(value="Descr")
	Double E_s2RS;

	@Getter @Setter
	@ApiModelProperty(value="Descr")
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
	
	static public Map<String, Map<String, String> > getNanoData( SimpleBox4NanoApiInput input ) {
		Map<String, Map<String, String> > nanoData = new HashMap<String, Map<String, String> >(); 
		
		nanoData.put( input.getNanomaterial(), new HashMap<String, String>() );  
		nanoData.get( input.getNanomaterial() ).put("Molweight", String.valueOf( input.getMolweight() ) );  
		
		nanoData.get( input.getNanomaterial()).put("RadS", String.valueOf( input.getRadS() ) ); 
		nanoData.get( input.getNanomaterial()).put("RhoS", String.valueOf( input.getRhoS() ) ); 
		nanoData.get( input.getNanomaterial()).put("AHamakerSP.w", String.valueOf( input.getAHamakerSP_w() ) ); 

		nanoData.get( input.getNanomaterial() ).put("AtefSA.w0", String.valueOf( input.getAtefSA_w0() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.w0", String.valueOf( input.getAtefSP_w0() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSA.w1", String.valueOf( input.getAtefSA_w1() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.w1", String.valueOf( input.getAtefSP_w1() ) );

		nanoData.get( input.getNanomaterial() ).put("AtefSA.s1", String.valueOf( input.getAtefSA_s1() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.s1", String.valueOf( input.getAtefSP_s1() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSA.s2", String.valueOf( input.getAtefSA_s2() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.s2", String.valueOf( input.getAtefSP_s2() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSA.s3", String.valueOf( input.getAtefSA_s3() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.s3", String.valueOf( input.getAtefSP_s3() ) ); 

		nanoData.get( input.getNanomaterial() ).put("AtefSA.sd0", String.valueOf( input.getAtefSA_sd0() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.sd0", String.valueOf( input.getAtefSP_sd0() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSA.sd1", String.valueOf( input.getAtefSA_sd1() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.sd1", String.valueOf( input.getAtefSP_sd1() ) );		
		nanoData.get( input.getNanomaterial() ).put("AtefSA.sd2", String.valueOf( input.getAtefSA_sd2() ) ); 
		nanoData.get( input.getNanomaterial() ).put("AtefSP.sd2", String.valueOf( input.getAtefSP_sd2() ) );

		nanoData.get( input.getNanomaterial() ).put("kdis.w0S.w0D", String.valueOf( input.getKdis_w0S_w0D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w1S.w1D", String.valueOf( input.getKdis_w1S_w1D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w0A.w0D", String.valueOf( input.getKdis_w0A_w0D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w0P.w0D", String.valueOf( input.getKdis_w0P_w0D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w1A.w1D", String.valueOf( input.getKdis_w1A_w1D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w1P.w1D", String.valueOf( input.getKdis_w1P_w1D() ) ); 

		nanoData.get( input.getNanomaterial() ).put("kdis.w2S.w2D", String.valueOf( input.getKdis_w2S_w2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w2A.w2D", String.valueOf( input.getKdis_w2A_w2D() ) );
		nanoData.get( input.getNanomaterial() ).put("kdis.w2P.w2D", String.valueOf( input.getKdis_w2P_w2D() ) );

		nanoData.get( input.getNanomaterial() ).put("kdis.s1S.s1D", String.valueOf( input.getKdis_s1S_s1D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s2S.s2D", String.valueOf( input.getKdis_s2S_s2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s3S.s3D", String.valueOf( input.getKdis_s3S_s3D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s1A.s1D", String.valueOf( input.getKdis_s1A_s1D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s1P.s1D", String.valueOf( input.getKdis_s1P_s1D() ) );
		nanoData.get( input.getNanomaterial() ).put("kdis.s2A.s2D", String.valueOf( input.getKdis_s2A_s2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s2P.s2D", String.valueOf( input.getKdis_s2P_s2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s3A.s3D", String.valueOf( input.getKdis_s3A_s3D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.s3P.s3D", String.valueOf( input.getKdis_s3P_s3D() ) ); 

		nanoData.get( input.getNanomaterial() ).put("kdis.sd0S.sd0D", String.valueOf( input.getKdis_sd0S_sd0D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.sd1S.sd1D", String.valueOf( input.getKdis_sd1S_sd1D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.sd0A.sd0D", String.valueOf( input.getKdis_sd0A_sd0D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.sd0P.sd0D", String.valueOf( input.getKdis_sd0P_sd0D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.sd1A.sd1D", String.valueOf( input.getKdis_sd1A_sd1D() ) );
		nanoData.get( input.getNanomaterial() ).put("kdis.sd1P.sd1D", String.valueOf( input.getKdis_sd1P_sd1D() ) );
		
		nanoData.get( input.getNanomaterial() ).put("kdis.sd2S.sd2D", String.valueOf( input.getKdis_sd2S_sd2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w3S.w3D", String.valueOf(   input.getKdis_w3S_w3D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.sd2A.sd2D", String.valueOf( input.getKdis_sd2A_sd2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.sd2P.sd2D", String.valueOf( input.getKdis_sd2P_sd2D() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdis.w3A.w3D", String.valueOf(	  input.getKdis_w3A_w3D() ) );
		nanoData.get( input.getNanomaterial() ).put("kdis.w3P.w3D", String.valueOf(   input.getKdis_w3P_w3D() ) );

		nanoData.get( input.getNanomaterial() ).put("kdeg.aS", String.valueOf( input.getKdeg_aS() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.aA", String.valueOf( input.getKdeg_aA() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.aP", String.valueOf( input.getKdeg_aP() ) );			

		nanoData.get( input.getNanomaterial() ).put("kdeg.w0S", String.valueOf( input.getKdeg_w0S() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.w1S", String.valueOf( input.getKdeg_w1S() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.w0A", String.valueOf( input.getKdeg_w0A() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.w0P", String.valueOf( input.getKdeg_w0P() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.w1A", String.valueOf( input.getKdeg_w1A() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.w1P", String.valueOf( input.getKdeg_w1P() ) ); 

		nanoData.get( input.getNanomaterial() ).put("kdeg.w2S", String.valueOf( input.getKdeg_w2S() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.w2A", String.valueOf( input.getKdeg_w2A() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.w2P", String.valueOf( input.getKdeg_w2P() ) ); 

		nanoData.get( input.getNanomaterial() ).put("kdeg.s1S", String.valueOf( input.getKdeg_s1S() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.s2S", String.valueOf( input.getKdeg_s2S() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.s3S", String.valueOf( input.getKdeg_s3S() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.s1A", String.valueOf( input.getKdeg_s1A() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.s1P", String.valueOf( input.getKdeg_s1P() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.s2A", String.valueOf( input.getKdeg_s2A() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.s2P", String.valueOf( input.getKdeg_s2P() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.s3A", String.valueOf( input.getKdeg_s3A() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.s3P", String.valueOf( input.getKdeg_s3P() ) ); 

		nanoData.get( input.getNanomaterial() ).put("kdeg.sd0S", String.valueOf( input.getKdeg_sd0S() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd1S", String.valueOf( input.getKdeg_sd1S() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd0A", String.valueOf( input.getKdeg_sd0A() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd0P", String.valueOf( input.getKdeg_sd0P() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd1A", String.valueOf( input.getKdeg_sd1A() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd1P", String.valueOf( input.getKdeg_sd1P() ) ); 

		nanoData.get( input.getNanomaterial() ).put("kdeg.sd2S", String.valueOf( input.getKdeg_sd2S() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.w3S", String.valueOf(  input.getKdeg_w3S() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd2A", String.valueOf( input.getKdeg_sd2A() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.sd2P", String.valueOf( input.getKdeg_sd2P() ) ); 
		nanoData.get( input.getNanomaterial() ).put("kdeg.w3A", String.valueOf(  input.getKdeg_w3A() ) );
		nanoData.get( input.getNanomaterial() ).put("kdeg.w3P", String.valueOf(  input.getKdeg_w3P() ) );
	
		return nanoData;
	}
	
	static public Map<String, Scenario> getScenariosData( SimpleBox4NanoApiInput input ) {
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
