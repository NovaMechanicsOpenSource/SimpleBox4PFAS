package eu.nanosolveit.simplebox4pfas;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value="SimpleBox4Nano API Response Data Structure")
public class SimpleBox4PFASApiResponse {
	@Getter @Setter
	@ApiModelProperty(value="The masses ")
	Map<String, Map<String, Map<String, Double> > > masses;

	@Getter @Setter
	@ApiModelProperty(value="Ôhe concentrations")
	Map<String, Map<String, Map<String, Double> > > concentrations;

	@Getter @Setter
	@ApiModelProperty(value="Ôhe fugacities")
	Map<String, Map<String, Map<String, Double> > > fugacities;

	@Getter @Setter
	@ApiModelProperty(value="Inflow data")
	Map<String, Map<String, Double> > inflow;

	@Getter @Setter
	@ApiModelProperty(value="Outflow data")
	Map<String, Map<String, Double> > outflow;

	@Getter @Setter
	@ApiModelProperty(value="Removal data")
	Map<String, Map<String, Double> > removal;

	@Getter @Setter
	@ApiModelProperty(value="Formation data")
	Map<String, Map<String, Double> > formation;

	@Getter @Setter
	@ApiModelProperty(value="Degradation data")
	Map<String, Map<String, Double> > degradation;

	@Getter @Setter
	@ApiModelProperty(value="Emission data")
	Map<String, Map<String, Double> > emission;
	
	@Getter @Setter
	@ApiModelProperty(value="Transport data ")
	Map<String, Map<String, Map<String, Double> > > transport;

	@Getter @Setter
	@ApiModelProperty(value="The masses ")
	Map<String, Double> totalD;

	@Getter @Setter
	@ApiModelProperty(value="The masses ")
	Map<String, Double> totalS;
}
