package eu.proplanet.simplebox4pfas;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Scenario {

	String name;
	
	//Solid species ENPs (S)
	Map<String, Map<String, String> > solid = new HashMap<String, Map<String, String> >();

	///Dissolved or Gas species (G/D)
	Map<String, Map<String, String> > disolvedGas = new HashMap<String, Map<String, String> >();

	//LANSCAPE SETTINGS
	Map<String, Map<String, String> > landscape = new HashMap<String, Map<String, String> >();
	
	//ENVIRONMENTAL PROPERTIES (ALL SCALES)
	Map<String, String> enviromental = new HashMap<String, String>();

	boolean isAdvRegChecked = false;
	boolean isAdvContChecked = false;
	
	public Scenario( String str) {
		name = str;
	}
	
	public String getName() {
		return name;
	}
	
	public void insertSolidInfo( String var, Map< String, String > vals ) {
		solid.put(var, vals);
	}
	
	public void putSolidInfo( String var, String name, String val ) {
		solid.get(var).put(name, val);		
	}

	public Map<String, String > getSolidInfo( String var ) {
		return solid.get( var );
	}

	public void insertDissGasInfo( String var, Map< String, String > vals ) {
		disolvedGas.put(var, vals);
	}

	public void putDissGasInfo( String var, String name, String val ) {
		disolvedGas.get(var).put(name, val);		
	}

	public Map<String, String > getDissGasInfo( String var ) {
		return disolvedGas.get( var );
	}
	
	public void insertLandscapeInfo( String var, Map< String, String > vals ) {
		landscape.put(var, vals);
	}

	public void putLandscapeInfo( String var, String name, String val ) {
		landscape.get(var).put(name, val);		
	}
	
	public Map<String, String > getLandscapeInfo( String var ) {
		return landscape.get( var );
	}

	public void insertEnviInfo( Map< String, String > vals ) {
		enviromental = vals;
	}
	
	public void putEnviInfo( String name, String val ) {
		enviromental.put(name, val);
	}
	
	public String getEnviInfo( String var ) {
		return enviromental.get(var);
	}
	
	public void setAdvRegChecked( boolean isChecked )
	{
		isAdvRegChecked = isChecked;
	}
	
	public boolean getAdvRegChecked()
	{
		return isAdvRegChecked;
	}

	public void setAdvContChecked( boolean isChecked )
	{
		isAdvRegChecked = isChecked;
	}
	
	public boolean getAdvContChecked()
	{
		return isAdvRegChecked;
	}

}
