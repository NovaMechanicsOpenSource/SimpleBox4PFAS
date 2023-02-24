package eu.nanosolveit.restapis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
//import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
//import org.jboss.resteasy.plugins.providers.multipart.InputPart;
//import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.nanosolveit.simplebox4nano.SimpleBox4NanoApiInput;
import eu.nanosolveit.simplebox4nano.SimpleBox4NanoApiResponse;
import eu.nanosolveit.simplebox4nano.SimpleBox4NanoModel;
import eu.nanosolveit.simplebox4nano.output;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import xyz.euclia.jaqpotj.Jaqpot;
import xyz.euclia.jaqpotj.JaqpotFactory;
import xyz.euclia.jaqpotj.models.Auth;
import xyz.euclia.jaqpotj.models.Prediction;

@Path("/")
@Api(value = "NanoSolveIT REST APIs")
public class RESTApis{
	/*
	@POST
	@Path(value = "/restapi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful result production", response = MSzetaApiResponseTuple.class),
            @ApiResponse(code = 400, message = "The transaction schema is invalid and therefore the transaction has not been created.", response = String.class),
            //@ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.") 
            })
    */        
	public Response simpleBox4NanoRestApi(SimpleBox4NanoApiInput input)
	{
		if(false)
		{
			return Response.status(415).type("text/plain").entity("just a test\n").build();
		}
		else
		{
			try
			{
				SimpleBox4NanoModel model = new SimpleBox4NanoModel( 
						SimpleBox4NanoApiInput.getNanoData(input), 
						SimpleBox4NanoApiInput.getScenariosData(input), 
						input.getScenario(), 
						input.getNanomaterial() 
						);
				
				output outSB4Nano = new output();				
				outSB4Nano.setAPIInfo(
						model.getInput(), 
						model.getEnvironment(), 
						model.getEngine(), 
						model.getNanoData(), 
						model.getSceneName(), 
						model.getNanoName() 
						);		
			
				
				SimpleBox4NanoApiResponse res = new SimpleBox4NanoApiResponse();
				res.setConcentrations( outSB4Nano.getConcentrations() );
				res.setMasses( outSB4Nano.getMasses() );
				res.setDegradation( outSB4Nano.getDegradation() );
				res.setEmission( outSB4Nano.getEmission() );
				res.setFormation( outSB4Nano.getFormation() );
				res.setFugacities( outSB4Nano.getFugacities() );
				res.setInflow( outSB4Nano.getInflow() );
				res.setOutflow( outSB4Nano.getOutflow() );
				res.setRemoval( outSB4Nano.getRemoval() );
				res.setTotalA( outSB4Nano.getTotalA() );
				res.setTotalS( outSB4Nano.getTotalS() );
				res.setTotalP( outSB4Nano.getTotalP() );
				res.setTotalD( outSB4Nano.getTotalD() );

				return Response.ok( res ).type(MediaType.APPLICATION_JSON).build();
			}
			catch(Exception e)
			{
				return Response.status(500).type("text/plain").entity("An unexpected error has occurred\n").build();
			}
		}
	}
}
