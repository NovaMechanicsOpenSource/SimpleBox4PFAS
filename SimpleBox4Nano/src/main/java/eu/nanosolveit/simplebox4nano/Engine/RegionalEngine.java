package eu.nanosolveit.simplebox4nano.Engine;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class RegionalEngine {
	// ENVIRONMENT
	Map<String, Map<String, Double>> environment = new HashMap<String, Map<String, Double>>();
	InputEngine inputEng = null;

	public RegionalEngine(InputEngine input) {
		inputEng = input;

		regional();
		continental();
		mixedRegional();
		mixedContinental();
		globalModerate();
		globalArctic();
		globalTropical();
		mixedAll();
	}

	public double getEnvProps(String str1, String str2) {
		return environment.get(str1).get(str2);
	}

	public void regional() {
		environment.put("REGIONAL", new HashMap<String, Double>());
		environment.get("REGIONAL").put("FRACw.sdR", 0.8);
		environment.get("REGIONAL").put("FRACw.s3R", 0.2);
		environment.get("REGIONAL").put("FRACa.s3R", 0.2);
		environment.get("REGIONAL").put("FRACw.s2R", 0.2);
		environment.get("REGIONAL").put("FRACa.s2R", 0.2);
		environment.get("REGIONAL").put("FRACw.s1R", 0.2);
		environment.get("REGIONAL").put("FRACa.s1R", 0.2);

		environment.get("REGIONAL").put("FRACaerw.aR", 2e-11);
		environment.get("REGIONAL").put("FRACaers.aR", 2e-11);
		environment.get("REGIONAL").put("FRACcldw.aR", 3e-7);

		environment.get("REGIONAL").put("DEPTH.s1R", 0.05);
		environment.get("REGIONAL").put("DEPTH.s2R", 0.2);
		environment.get("REGIONAL").put("DEPTH.s3R", 0.05);

		environment.get("REGIONAL").put("DEPTH.sd0R", 3e-2);
		environment.get("REGIONAL").put("DEPTH.sd1R", 3e-2);
		environment.get("REGIONAL").put("DEPTH.sd2R", 3e-2);

		environment.get("REGIONAL").put("DEPTH.w0R", inputEng.getLandscapeSettings("REGIONAL", "DEPTHlake.R"));
		environment.get("REGIONAL").put("DEPTH.w1R", inputEng.getLandscapeSettings("REGIONAL", "DEPTHfreshwater.R"));
		environment.get("REGIONAL").put("DEPTH.w2R", 10.);

		environment.get("REGIONAL").put("HEIGHT.aR", 1000.);

		environment.get("REGIONAL").put("FRACs.sdR", 1 - environment.get("REGIONAL").get("FRACw.sdR"));

		environment.get("REGIONAL").put("FRACs.s1R",
				1. - environment.get("REGIONAL").get("FRACa.s1R") - environment.get("REGIONAL").get("FRACw.s1R"));
		environment.get("REGIONAL").put("FRACs.s2R",
				1. - environment.get("REGIONAL").get("FRACa.s2R") - environment.get("REGIONAL").get("FRACw.s2R"));
		environment.get("REGIONAL").put("FRACs.s3R",
				1. - environment.get("REGIONAL").get("FRACa.s3R") - environment.get("REGIONAL").get("FRACw.s3R"));

		environment.get("REGIONAL").put("SYSTEMAREA.R",
				1000000 * (inputEng.getLandscapeSettings("REGIONAL", "AREAland.R")
						+ inputEng.getLandscapeSettings("REGIONAL", "AREAsea.R")) / 1000000.);

		environment.get("REGIONAL").put("AREAFRAC.w0R",
				inputEng.getLandscapeSettings("REGIONAL", "AREAland.R")
						* inputEng.getLandscapeSettings("REGIONAL", "FRAClake.R")
						/ environment.get("REGIONAL").get("SYSTEMAREA.R"));

		environment.get("REGIONAL").put("AREAFRAC.w1R",
				inputEng.getLandscapeSettings("REGIONAL", "AREAland.R")
						* inputEng.getLandscapeSettings("REGIONAL", "FRACfresh.R")
						/ environment.get("REGIONAL").get("SYSTEMAREA.R"));

		environment.get("REGIONAL").put("AREAFRAC.s1R",
				inputEng.getLandscapeSettings("REGIONAL", "AREAland.R")
						* inputEng.getLandscapeSettings("REGIONAL", "FRACnatsoil.R")
						/ environment.get("REGIONAL").get("SYSTEMAREA.R"));

		environment.get("REGIONAL").put("AREAFRAC.s2R",
				inputEng.getLandscapeSettings("REGIONAL", "AREAland.R")
						* inputEng.getLandscapeSettings("REGIONAL", "FRACagsoil.R")
						/ environment.get("REGIONAL").get("SYSTEMAREA.R"));

		environment.get("REGIONAL").put("AREAFRAC.s3R",
				inputEng.getLandscapeSettings("REGIONAL", "AREAland.R")
						* inputEng.getLandscapeSettings("REGIONAL", "FRACothersoil.R")
						/ environment.get("REGIONAL").get("SYSTEMAREA.R"));

		environment.get("REGIONAL").put("AREAFRAC.w2R", 1. - environment.get("REGIONAL").get("AREAFRAC.s3R")
				- environment.get("REGIONAL").get("AREAFRAC.s2R") - environment.get("REGIONAL").get("AREAFRAC.s1R")
				- environment.get("REGIONAL").get("AREAFRAC.w1R") - environment.get("REGIONAL").get("AREAFRAC.w0R"));

		environment.get("REGIONAL").put("VOLUME.aR", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("HEIGHT.aR") * (1. - environment.get("REGIONAL").get("FRACcldw.aR")));

		environment.get("REGIONAL").put("VOLUME.cwR",
				environment.get("REGIONAL").get("VOLUME.aR") * environment.get("REGIONAL").get("FRACcldw.aR"));

		environment.get("REGIONAL").put("VOLUME.w0R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("DEPTH.w0R"));

		environment.get("REGIONAL").put("VOLUME.w1R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("DEPTH.w1R"));

		environment.get("REGIONAL").put("VOLUME.w2R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("DEPTH.w2R"));

		environment.get("REGIONAL").put("VOLUME.sd0R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("DEPTH.sd0R"));

		environment.get("REGIONAL").put("VOLUME.sd1R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("VOLUME.sd2R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("DEPTH.sd2R"));

		environment.get("REGIONAL").put("VOLUME.s1R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("DEPTH.s1R"));

		environment.get("REGIONAL").put("VOLUME.s2R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("DEPTH.s2R"));

		environment.get("REGIONAL").put("VOLUME.s3R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("DEPTH.s3R"));

		environment.get("REGIONAL")
				.put("TAU.aR",
						(1.5 * (0.5 * Math.sqrt(environment.get("REGIONAL").get("SYSTEMAREA.R") * Math.PI / 4.)
								/ inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R")) / (3600. * 24.))
								* (3600 * 24));
		environment.get("REGIONAL").put("k.aR.aC", 1. / environment.get("REGIONAL").get("TAU.aR"));

		environment.get("REGIONAL").put("FRACrun.s1R", inputEng.getLandscapeSettings("REGIONAL", "FRACrun.R"));
		environment.get("REGIONAL").put("FRACrun.s2R", inputEng.getLandscapeSettings("REGIONAL", "FRACrun.R"));
		environment.get("REGIONAL").put("FRACrun.s3R", inputEng.getLandscapeSettings("REGIONAL", "FRACrun.R"));

		environment.get("REGIONAL").put("WATERflow.w0R.w2R", 0.0);
		environment.get("REGIONAL").put("WATERflow.w1R.w0R", 0.0);
	}

	// This is the continental in excel
	// We have to merge them in order to be able to use them in the same class
	public void continental() {
		environment.put("CONTINENTAL", new HashMap<String, Double>());
		environment.get("CONTINENTAL").put("FRACw.sdC", 0.8);
		environment.get("CONTINENTAL").put("FRACw.s3C", 0.2);
		environment.get("CONTINENTAL").put("FRACa.s3C", 0.2);
		environment.get("CONTINENTAL").put("FRACw.s2C", 0.2);
		environment.get("CONTINENTAL").put("FRACa.s2C", 0.2);
		environment.get("CONTINENTAL").put("FRACw.s1C", 0.2);
		environment.get("CONTINENTAL").put("FRACa.s1C", 0.2);
		environment.get("CONTINENTAL").put("FRACaerw.aC", 2e-11);
		environment.get("CONTINENTAL").put("FRACaers.aC", 2e-11);
		environment.get("CONTINENTAL").put("FRACcldw.aC", 3e-7);

		environment.get("CONTINENTAL").put("DEPTH.s1C", 0.05);
		environment.get("CONTINENTAL").put("DEPTH.s2C", 0.2);
		environment.get("CONTINENTAL").put("DEPTH.s3C", 0.05);

		environment.get("CONTINENTAL").put("DEPTH.sd0C", 3e-2);
		environment.get("CONTINENTAL").put("DEPTH.sd1C", 3e-2);
		environment.get("CONTINENTAL").put("DEPTH.sd2C", 3e-2);

		environment.get("CONTINENTAL").put("DEPTH.w0C", inputEng.getLandscapeSettings("CONTINENTAL", "DEPTHlake.C"));
		environment.get("CONTINENTAL").put("DEPTH.w1C",
				inputEng.getLandscapeSettings("CONTINENTAL", "DEPTHfreshwater.C"));
		environment.get("CONTINENTAL").put("DEPTH.w2C", 200.);

		environment.get("CONTINENTAL").put("HEIGHT.aC", 1000.);

		environment.get("CONTINENTAL").put("FRACs.sdC", 1 - environment.get("CONTINENTAL").get("FRACw.sdC"));

		environment.get("CONTINENTAL").put("FRACs.s1C",
				1. - environment.get("CONTINENTAL").get("FRACa.s1C") - environment.get("CONTINENTAL").get("FRACw.s1C"));
		environment.get("CONTINENTAL").put("FRACs.s2C",
				1. - environment.get("CONTINENTAL").get("FRACa.s2C") - environment.get("CONTINENTAL").get("FRACw.s2C"));
		environment.get("CONTINENTAL").put("FRACs.s3C",
				1. - environment.get("CONTINENTAL").get("FRACa.s3C") - environment.get("CONTINENTAL").get("FRACw.s3C"));

		environment.get("CONTINENTAL").put("SYSTEMAREA.C", inputEng.getLandscapeSettings("CONTINENTAL", "AREAland.C")
				+ inputEng.getLandscapeSettings("CONTINENTAL", "AREAsea.C"));

		environment.get("CONTINENTAL").put("AREAFRAC.w0C",
				inputEng.getLandscapeSettings("CONTINENTAL", "AREAland.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "FRAClake.C")
						/ environment.get("CONTINENTAL").get("SYSTEMAREA.C"));

		environment.get("CONTINENTAL").put("AREAFRAC.w1C",
				inputEng.getLandscapeSettings("CONTINENTAL", "AREAland.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "FRACfresh.C")
						/ environment.get("CONTINENTAL").get("SYSTEMAREA.C"));

		environment.get("CONTINENTAL").put("AREAFRAC.s1C",
				inputEng.getLandscapeSettings("CONTINENTAL", "AREAland.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "FRACnatsoil.C")
						/ environment.get("CONTINENTAL").get("SYSTEMAREA.C"));

		environment.get("CONTINENTAL").put("AREAFRAC.s2C",
				inputEng.getLandscapeSettings("CONTINENTAL", "AREAland.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "FRACagsoil.C")
						/ environment.get("CONTINENTAL").get("SYSTEMAREA.C"));

		environment.get("CONTINENTAL").put("AREAFRAC.s3C",
				inputEng.getLandscapeSettings("CONTINENTAL", "AREAland.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "FRACothersoil.C")
						/ environment.get("CONTINENTAL").get("SYSTEMAREA.C"));

		environment.get("CONTINENTAL").put("AREAFRAC.w2C",
				1. - environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						- environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						- environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						- environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						- environment.get("CONTINENTAL").get("AREAFRAC.w0C"));

		environment.get("CONTINENTAL").put("VOLUME.aC",
				environment.get("CONTINENTAL").get("SYSTEMAREA.C") * environment.get("CONTINENTAL").get("HEIGHT.aC")
						* (1. - environment.get("CONTINENTAL").get("FRACcldw.aC")));

		environment.get("CONTINENTAL").put("VOLUME.cwC",
				environment.get("CONTINENTAL").get("VOLUME.aC") * environment.get("CONTINENTAL").get("FRACcldw.aC"));

		environment.get("CONTINENTAL").put("VOLUME.w0C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.w0C") * environment.get("CONTINENTAL").get("DEPTH.w0C"));

		environment.get("CONTINENTAL").put("VOLUME.w1C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.w1C") * environment.get("CONTINENTAL").get("DEPTH.w1C"));

		environment.get("CONTINENTAL").put("VOLUME.w2C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.w2C") * environment.get("CONTINENTAL").get("DEPTH.w2C"));

		environment.get("CONTINENTAL").put("VOLUME.sd0C",
				environment.get("CONTINENTAL").get("SYSTEMAREA.C") * environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("DEPTH.sd0C"));

		environment.get("CONTINENTAL").put("VOLUME.sd1C",
				environment.get("CONTINENTAL").get("SYSTEMAREA.C") * environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("VOLUME.sd2C",
				environment.get("CONTINENTAL").get("SYSTEMAREA.C") * environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("DEPTH.sd2C"));

		environment.get("CONTINENTAL").put("VOLUME.s1C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.s1C") * environment.get("CONTINENTAL").get("DEPTH.s1C"));

		environment.get("CONTINENTAL").put("VOLUME.s2C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.s2C") * environment.get("CONTINENTAL").get("DEPTH.s2C"));

		environment.get("CONTINENTAL").put("VOLUME.s3C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.s3C") * environment.get("CONTINENTAL").get("DEPTH.s3C"));

		environment.get("CONTINENTAL")
				.put("TAU.aC",
						(1.5 * (0.5 * Math.sqrt(environment.get("CONTINENTAL").get("SYSTEMAREA.C") * Math.PI / 4.)
								/ inputEng.getLandscapeSettings("CONTINENTAL", "WINDspeed.C")) / (3600. * 24.))
								* (3600 * 24));

		environment.get("CONTINENTAL").put("FRACrun.s1C", inputEng.getLandscapeSettings("CONTINENTAL", "FRACrun.C"));
		environment.get("CONTINENTAL").put("FRACrun.s2C", inputEng.getLandscapeSettings("CONTINENTAL", "FRACrun.C"));
		environment.get("CONTINENTAL").put("FRACrun.s3C", inputEng.getLandscapeSettings("CONTINENTAL", "FRACrun.C"));

		environment.get("CONTINENTAL").put("WATERflow.w0C.w2C", 0.0);
		environment.get("CONTINENTAL").put("WATERflow.w1C.w0C", 0.0);
		environment.get("CONTINENTAL").put("TAU.w2C", 365. * 3600. * 24.);
	}

	public void mixedRegional() {
		environment.get("CONTINENTAL").put("WATERflow.w1C.w2C", (environment.get("CONTINENTAL").get("AREAFRAC.w1C")
				+ environment.get("CONTINENTAL").get("AREAFRAC.s1C") * environment.get("CONTINENTAL").get("FRACrun.s1C")
				+ environment.get("CONTINENTAL").get("AREAFRAC.s2C") * environment.get("CONTINENTAL").get("FRACrun.s2C")
				+ environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("FRACrun.s3C"))
				* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
				* environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* (1. - inputEng.getLandscapeSettings("CONTINENTAL", "FRAC.w1C.w1R")));

		environment.get("CONTINENTAL").put("WATERflow.w0C.w1C",
				0.1 * environment.get("CONTINENTAL").get("WATERflow.w1C.w2C"));

		environment.get("CONTINENTAL").put("WATERflow.w1C.w1R", ((environment.get("CONTINENTAL").get("AREAFRAC.w1C")
				+ environment.get("CONTINENTAL").get("AREAFRAC.s1C") * environment.get("CONTINENTAL").get("FRACrun.s1C")
				+ environment.get("CONTINENTAL").get("AREAFRAC.s2C") * environment.get("CONTINENTAL").get("FRACrun.s2C")
				+ environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("FRACrun.s3C"))
				* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
				* environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				+ environment.get("CONTINENTAL").get("WATERflow.w0C.w1C"))
				* inputEng.getLandscapeSettings("CONTINENTAL", "FRAC.w1C.w1R"));

		environment.get("REGIONAL").put("WATERflow.w1R.w2R", ((environment.get("REGIONAL").get("AREAFRAC.w1R")
				+ environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("FRACrun.s1R")
				+ environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("FRACrun.s2R")
				+ environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("FRACrun.s3R"))
				* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("SYSTEMAREA.R")
				+ environment.get("CONTINENTAL").get("WATERflow.w1C.w1R"))
				* (1. - inputEng.getLandscapeSettings("REGIONAL", "FRAC.w1R.w1C")));

		environment.get("CONTINENTAL").put("WATERflow.w2C.w2R",
				9 * environment.get("REGIONAL").get("WATERflow.w1R.w2R"));

		environment.get("REGIONAL").put("WATERflow.w2R.w2C", environment.get("REGIONAL").get("WATERflow.w1R.w2R")
				+ environment.get("CONTINENTAL").get("WATERflow.w2C.w2R"));

		environment.get("REGIONAL").put("WATERflow.w0R.w1R",
				0.1 * environment.get("REGIONAL").get("WATERflow.w2R.w2C"));

		environment.get("REGIONAL").put("k.w0R.w1R",
				environment.get("REGIONAL").get("WATERflow.w0R.w1R") / environment.get("REGIONAL").get("VOLUME.w0R"));
		environment.get("REGIONAL").put("k.w0R.w2R",
				environment.get("REGIONAL").get("WATERflow.w0R.w2R") / environment.get("REGIONAL").get("VOLUME.w0R"));
		environment.get("REGIONAL").put("k.w1R.w0R",
				environment.get("REGIONAL").get("WATERflow.w1R.w0R") / environment.get("REGIONAL").get("VOLUME.w1R"));
		environment.get("REGIONAL").put("k.w1R.w2R",
				environment.get("REGIONAL").get("WATERflow.w1R.w2R") / environment.get("REGIONAL").get("VOLUME.w1R"));
		environment.get("REGIONAL").put("k.w2R.w2C",
				environment.get("REGIONAL").get("WATERflow.w2R.w2C") / environment.get("REGIONAL").get("VOLUME.w2R"));

		environment.get("CONTINENTAL").put("k.w0C.w1C", environment.get("CONTINENTAL").get("WATERflow.w0C.w1C")
				/ environment.get("CONTINENTAL").get("VOLUME.w0C"));
		environment.get("CONTINENTAL").put("k.w0C.w2C", environment.get("CONTINENTAL").get("WATERflow.w0C.w2C")
				/ environment.get("CONTINENTAL").get("VOLUME.w0C"));
		environment.get("CONTINENTAL").put("k.w1C.w0C", environment.get("CONTINENTAL").get("WATERflow.w1C.w0C")
				/ environment.get("CONTINENTAL").get("VOLUME.w1C"));
		environment.get("CONTINENTAL").put("k.w1C.w1R", environment.get("CONTINENTAL").get("WATERflow.w1C.w1R")
				/ environment.get("CONTINENTAL").get("VOLUME.w1C"));
		environment.get("CONTINENTAL").put("k.w1C.w2C", environment.get("CONTINENTAL").get("WATERflow.w1C.w2C")
				/ environment.get("CONTINENTAL").get("VOLUME.w1C"));
		environment.get("CONTINENTAL").put("k.w2C.w2R", environment.get("CONTINENTAL").get("WATERflow.w2C.w2R")
				/ environment.get("CONTINENTAL").get("VOLUME.w2C"));
		environment.get("CONTINENTAL").put("WATERflow.w2C.w2M",
				(environment.get("CONTINENTAL").get("VOLUME.w2C") / environment.get("CONTINENTAL").get("TAU.w2C"))
						- environment.get("REGIONAL").get("WATERflow.w2R.w2C"));
		environment.get("CONTINENTAL").put("k.w2C.w2M", environment.get("CONTINENTAL").get("WATERflow.w2C.w2M")
				/ environment.get("CONTINENTAL").get("VOLUME.w2C"));
		environment.get("CONTINENTAL").put("k.aC.aR", environment.get("REGIONAL").get("k.aR.aC")
				* environment.get("REGIONAL").get("VOLUME.aR") / environment.get("CONTINENTAL").get("VOLUME.aC"));

		environment.get("CONTINENTAL").put("k.aC.aM",
				(1. / environment.get("CONTINENTAL").get("TAU.aC")) - environment.get("CONTINENTAL").get("k.aC.aR"));

		environment.get("REGIONAL").put("CORG.sd2R", 0.05);
		environment.get("REGIONAL").put("Kp.sd2R",
				(inputEng.getSubstancesData("FRorig.sd2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.sd2R"));
		environment.get("REGIONAL").put("Ksdw2.R",
				environment.get("REGIONAL").get("FRACw.sdR")
						+ environment.get("REGIONAL").get("FRACs.sdR") * environment.get("REGIONAL").get("Kp.sd2R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("REGIONAL").put("kwsd.water.wR", 0.000002778);
		environment.get("REGIONAL").put("kwsd.sed.sdR", 0.00000002778);

		environment.get("REGIONAL").put("DESORB.sd2R.w2R", environment.get("REGIONAL").get("kwsd.water.wR")
				* environment.get("REGIONAL").get("kwsd.sed.sdR")
				/ (environment.get("REGIONAL").get("kwsd.water.wR") + environment.get("REGIONAL").get("kwsd.sed.sdR"))
				/ environment.get("REGIONAL").get("Ksdw2.R"));

		environment.get("REGIONAL").put("Kp.col2R", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("REGIONAL").put("CORG.susp2R", 0.1);
		environment.get("REGIONAL").put("Kp.susp2R",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.susp2R"));

		environment.get("REGIONAL").put("FRwD.w2R",
				1. / (1. + environment.get("REGIONAL").get("Kp.susp2R")
						* inputEng.getLandscapeSettings("REGIONAL", "SUSP.w2R") / 1000.
						+ environment.get("REGIONAL").get("Kp.col2R")
								* inputEng.getLandscapeSettings("REGIONAL", "COL.w2R") / 1000.));

		environment.get("REGIONAL").put("ADSORB.w2R.sd2R",
				(environment.get("REGIONAL").get("kwsd.water.wR") * environment.get("REGIONAL").get("kwsd.sed.sdR"))
						/ (environment.get("REGIONAL").get("kwsd.water.wR")
								+ environment.get("REGIONAL").get("kwsd.sed.sdR"))
						* environment.get("REGIONAL").get("FRwD.w2R"));

		environment.get("REGIONAL").put("CORG.sd1R", 0.05);
		environment.get("REGIONAL").put("Kp.sd1R",
				(inputEng.getSubstancesData("FRorig.sd1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.sd1R"));
		environment.get("REGIONAL").put("Ksdw1.R",
				environment.get("REGIONAL").get("FRACw.sdR")
						+ environment.get("REGIONAL").get("FRACs.sdR") * environment.get("REGIONAL").get("Kp.sd2R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("REGIONAL").put("DESORB.sd1R.w1R", environment.get("REGIONAL").get("kwsd.water.wR")
				* environment.get("REGIONAL").get("kwsd.sed.sdR")
				/ (environment.get("REGIONAL").get("kwsd.water.wR") + environment.get("REGIONAL").get("kwsd.sed.sdR"))
				/ environment.get("REGIONAL").get("Ksdw1.R"));

		environment.get("REGIONAL").put("Kp.col1R", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("REGIONAL").put("CORG.susp1R", 0.1);
		environment.get("REGIONAL").put("Kp.susp1R",
				(inputEng.getSubstancesData("FRorig.w1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.susp1R"));

		environment.get("REGIONAL").put("FRwD.w1R",
				1. / (1. + environment.get("REGIONAL").get("Kp.susp1R")
						* inputEng.getLandscapeSettings("REGIONAL", "SUSP.w1R") / 1000.
						+ environment.get("REGIONAL").get("Kp.col1R")
								* inputEng.getLandscapeSettings("REGIONAL", "COL.w1R") / 1000.));

		environment.get("REGIONAL").put("ADSORB.w1R.sd1R",
				(environment.get("REGIONAL").get("kwsd.water.wR") * environment.get("REGIONAL").get("kwsd.sed.sdR"))
						/ (environment.get("REGIONAL").get("kwsd.water.wR")
								+ environment.get("REGIONAL").get("kwsd.sed.sdR"))
						* environment.get("REGIONAL").get("FRwD.w1R"));

		environment.get("REGIONAL").put("SETTLvelocity.R", inputEng.getEnvProperties("SetVelSPM.w"));

		environment.get("REGIONAL").put("NETsedrate.w2R", 2.74287972475951E-11);
		environment.get("REGIONAL").put("NETsedrate.w1R", 8.68610476915272E-11);
		environment.get("REGIONAL").put("NETsedrate.w0R", environment.get("REGIONAL").get("NETsedrate.w1R"));

		if (environment.get("REGIONAL").get("SETTLvelocity.R") * inputEng.getLandscapeSettings("REGIONAL", "SUSP.w2R")
				/ (environment.get("REGIONAL").get("FRACs.sdR")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("REGIONAL")
								.get("NETsedrate.w2R")) {
			environment.get("REGIONAL").put("GROSSSEDrate.w2R",
					environment.get("REGIONAL").get("SETTLvelocity.R")
							* inputEng.getLandscapeSettings("REGIONAL", "SUSP.w2R")
							/ (environment.get("REGIONAL").get("FRACs.sdR")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("REGIONAL").put("GROSSSEDrate.w2R", environment.get("REGIONAL").get("NETsedrate.w2R"));

		if (environment.get("REGIONAL").get("SETTLvelocity.R") * inputEng.getLandscapeSettings("REGIONAL", "SUSP.w1R")
				/ (environment.get("REGIONAL").get("FRACs.sdR")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("REGIONAL")
								.get("NETsedrate.w1R")) {

			environment.get("REGIONAL").put("GROSSSEDrate.w1R",
					environment.get("REGIONAL").get("SETTLvelocity.R")
							* inputEng.getLandscapeSettings("REGIONAL", "SUSP.w1R")
							/ (environment.get("REGIONAL").get("FRACs.sdR")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("REGIONAL").put("GROSSSEDrate.w1R", environment.get("REGIONAL").get("NETsedrate.w1R"));

		if (environment.get("REGIONAL").get("SETTLvelocity.R") * inputEng.getLandscapeSettings("REGIONAL", "SUSP.w0R")
				/ (environment.get("REGIONAL").get("FRACs.sdR")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("REGIONAL")
								.get("NETsedrate.w1R")) {

			environment.get("REGIONAL").put("GROSSSEDrate.w0R",
					environment.get("REGIONAL").get("SETTLvelocity.R")
							* inputEng.getLandscapeSettings("REGIONAL", "SUSP.w0R")
							/ (environment.get("REGIONAL").get("FRACs.sdR")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("REGIONAL").put("GROSSSEDrate.w0R", environment.get("REGIONAL").get("NETsedrate.w0R"));

		environment.get("REGIONAL").put("PRODsusp.w1R", environment.get("REGIONAL").get("AREAFRAC.w1R")
				* environment.get("REGIONAL").get("SYSTEMAREA.R") * 10. / (1000. * 3600. * 24. * 365.));

		environment.get("REGIONAL").put("PRODsusp.w2R", environment.get("REGIONAL").get("SYSTEMAREA.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * 10. / (1000. * 3600. * 24. * 365.));

		environment.get("REGIONAL").put("RESUSP.sd2R.w2R", environment.get("REGIONAL").get("GROSSSEDrate.w2R")
				- environment.get("REGIONAL").get("NETsedrate.w2R"));

		environment.get("REGIONAL").put("SED.w2R.sd2R", environment.get("REGIONAL").get("SETTLvelocity.R")
				* (1. - environment.get("REGIONAL").get("FRwD.w2R")));

		environment.get("REGIONAL").put("RESUSP.sd1R.w1R", environment.get("REGIONAL").get("GROSSSEDrate.w1R")
				- environment.get("REGIONAL").get("NETsedrate.w1R"));

		environment.get("REGIONAL").put("SED.w1R.sd1R", environment.get("REGIONAL").get("SETTLvelocity.R")
				* (1. - environment.get("REGIONAL").get("FRwD.w1R")));

		environment.get("REGIONAL").put("RESUSP.sd0R.w0R", environment.get("REGIONAL").get("GROSSSEDrate.w0R")
				- environment.get("REGIONAL").get("NETsedrate.w0R"));

		environment.get("REGIONAL").put("Kp.col0R", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("REGIONAL").put("CORG.susp0R", 0.1);
		environment.get("REGIONAL").put("Kp.susp0R",
				(inputEng.getSubstancesData("FRorig.w1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.susp0R"));

		environment.get("REGIONAL").put("FRwD.w0R",
				1. / (1. + environment.get("REGIONAL").get("Kp.susp0R")
						* inputEng.getLandscapeSettings("REGIONAL", "SUSP.w0R") / 1000.
						+ environment.get("REGIONAL").get("Kp.col0R")
								* inputEng.getLandscapeSettings("REGIONAL", "COL.w0R") / 1000.));

		environment.get("REGIONAL").put("SED.w0R.sd0R", environment.get("REGIONAL").get("SETTLvelocity.R")
				* (1. - environment.get("REGIONAL").get("FRwD.w0R")));

		environment.get("REGIONAL").put("k.sd2RD.w2RD",
				((environment.get("REGIONAL").get("RESUSP.sd2R.w2R")
						+ environment.get("REGIONAL").get("DESORB.sd2R.w2R"))
						/ environment.get("REGIONAL").get("DEPTH.sd2R")));

		environment.get("REGIONAL").put("k.sd1RD.w1RD",
				((environment.get("REGIONAL").get("RESUSP.sd1R.w1R")
						+ environment.get("REGIONAL").get("DESORB.sd1R.w1R"))
						/ environment.get("REGIONAL").get("DEPTH.sd1R")));

		environment.get("REGIONAL").put("k.sd0RD.w0RD",
				(environment.get("REGIONAL").get("RESUSP.sd0R.w0R") / environment.get("REGIONAL").get("DEPTH.sd0R")));

		environment.get("REGIONAL").put("k.w2RD.sd2RD",
				(environment.get("REGIONAL").get("SED.w2R.sd2R") + environment.get("REGIONAL").get("ADSORB.w2R.sd2R"))
						/ environment.get("REGIONAL").get("DEPTH.w2R"));

		environment.get("REGIONAL").put("k.w1RD.sd1RD",
				(environment.get("REGIONAL").get("SED.w1R.sd1R") + environment.get("REGIONAL").get("ADSORB.w1R.sd1R"))
						/ environment.get("REGIONAL").get("DEPTH.w1R"));

		environment.get("REGIONAL").put("k.w0RD.sd0RD",
				environment.get("REGIONAL").get("SED.w0R.sd0R") / environment.get("REGIONAL").get("DEPTH.w0R"));

		environment.get("REGIONAL").put("EROSION.s1R", inputEng.getLandscapeSettings("REGIONAL", "EROSION.R"));
		environment.get("REGIONAL").put("EROSION.s2R", inputEng.getLandscapeSettings("REGIONAL", "EROSION.R"));
		environment.get("REGIONAL").put("EROSION.s3R", inputEng.getLandscapeSettings("REGIONAL", "EROSION.R"));

		environment.get("REGIONAL").put("CORRrunoff.s1R",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")))));
		environment.get("REGIONAL").put("CORRrunoff.s2R",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")))));
		environment.get("REGIONAL").put("CORRrunoff.s3R",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")))));

		environment.get("REGIONAL").put("CORRleach.s1R",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")))));
		environment.get("REGIONAL").put("CORRleach.s2R",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")))));
		environment.get("REGIONAL").put("CORRleach.s3R",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")))));

		environment.get("REGIONAL").put("FRACinf.s1R", inputEng.getLandscapeSettings("REGIONAL", "FRACinf.R"));
		environment.get("REGIONAL").put("FRACinf.s2R", inputEng.getLandscapeSettings("REGIONAL", "FRACinf.R"));
		environment.get("REGIONAL").put("FRACinf.s3R", inputEng.getLandscapeSettings("REGIONAL", "FRACinf.R"));

		environment.get("REGIONAL").put("Tempfactor.wsdsR", Math.pow(inputEng.getSubstancesData("Q.10"),
				((inputEng.getLandscapeSettings("REGIONAL", "TEMP.R") - 298.) / 10.)));

		environment.get("REGIONAL").put("KDEG.sd0R",
				environment.get("REGIONAL").get("Tempfactor.wsdsR") * inputEng.getSubstancesData("kdegD.sed"));	
		environment.get("REGIONAL").put("KDEG.sd1R",
				environment.get("REGIONAL").get("Tempfactor.wsdsR") * inputEng.getSubstancesData("kdegD.sed"));
		environment.get("REGIONAL").put("KDEG.sd2R",
				environment.get("REGIONAL").get("Tempfactor.wsdsR") * inputEng.getSubstancesData("kdegD.sed"));

		environment.get("REGIONAL").put("KDEG.s1R",
				environment.get("REGIONAL").get("Tempfactor.wsdsR") * inputEng.getSubstancesData("kdegD.soil"));
		environment.get("REGIONAL").put("KDEG.s2R",
				environment.get("REGIONAL").get("Tempfactor.wsdsR") * inputEng.getSubstancesData("kdegD.soil"));
		environment.get("REGIONAL").put("KDEG.s3R",
				environment.get("REGIONAL").get("Tempfactor.wsdsR") * inputEng.getSubstancesData("kdegD.soil"));

		environment.get("REGIONAL").put("BACT.w0R", 40000.);
		environment.get("REGIONAL").put("BACT.w1R", 40000.);
		environment.get("REGIONAL").put("BACT.w2R", 40000.);

		environment.get("REGIONAL").put("KDEG.w0R",
				inputEng.getSubstancesData("kdegD.water") * environment.get("REGIONAL").get("Tempfactor.wsdsR")
						* (environment.get("REGIONAL").get("BACT.w0R") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("REGIONAL").get("FRwD.w0R"));
		environment.get("REGIONAL").put("KDEG.w2R",
				inputEng.getSubstancesData("kdegD.water") * environment.get("REGIONAL").get("Tempfactor.wsdsR")
						* (environment.get("REGIONAL").get("BACT.w1R") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("REGIONAL").get("FRwD.w1R"));
		environment.get("REGIONAL").put("KDEG.w3R",
				inputEng.getSubstancesData("kdegD.water") * environment.get("REGIONAL").get("Tempfactor.wsdsR")
						* (environment.get("REGIONAL").get("BACT.w2R") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("REGIONAL").get("FRwD.w2R"));

		environment.get("REGIONAL").put("Tempfactor.aR", Math.exp((inputEng.getSubstancesData("Ea.OHrad") / 8.314)
				* (inputEng.getLandscapeSettings("REGIONAL", "TEMP.R") - 298.) / Math.pow(298, 2)));
		environment.get("REGIONAL").put("C.OHrad.aR", 500000.);

		environment.get("REGIONAL").put("KawD.R",
				inputEng.getSubstancesData("Kaw") * (Math
						.exp((inputEng.getSubstancesData("H0vap") / 8.314)
								* ((1. / 298.) - 1. / inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")))
						* Math.exp(-(inputEng.getSubstancesData("H0sol") / 8.314)
								* ((1. / 298.) - 1. / inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")))
						* (298 / inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))));

		environment.get("REGIONAL").put("Kaerw.aR",
				1. / (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("REGIONAL").put("Kaers.aR",
				0.54 * (inputEng.getSubstancesData("Kow") / inputEng.getSubstancesData("Kaw"))
						* inputEng.getLandscapeSettings("ALL-SCALE", "CORGaers")
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHOaers") / 1000.));

		environment.get("REGIONAL").put("FRaers.aR", environment.get("REGIONAL").get("FRACaers.aR")
				* environment.get("REGIONAL").get("Kaers.aR")
				/ (1. + environment.get("REGIONAL").get("FRACaerw.aR") * environment.get("REGIONAL").get("Kaerw.aR")
						+ environment.get("REGIONAL").get("FRACaers.aR")
								* environment.get("REGIONAL").get("Kaers.aR")));

		environment.get("REGIONAL").put("FRaerw.aR", environment.get("REGIONAL").get("FRACaerw.aR")
				* environment.get("REGIONAL").get("Kaerw.aR")
				/ (1. + environment.get("REGIONAL").get("FRACaerw.aR") * environment.get("REGIONAL").get("Kaerw.aR")
						+ environment.get("REGIONAL").get("FRACaers.aR")
								* environment.get("REGIONAL").get("Kaers.aR")));

		environment.get("REGIONAL")
				.put("FRgas.aR", 1
						- environment.get("REGIONAL").get("FRACaerw.aR") * environment.get("REGIONAL").get("Kaerw.aR")
								/ (1. + environment.get("REGIONAL").get("FRACaerw.aR")
										* environment.get("REGIONAL").get("Kaerw.aR")
										+ environment.get("REGIONAL").get("FRACaers.aR")
												* environment.get("REGIONAL").get("Kaers.aR"))
						- environment.get("REGIONAL").get("FRACaers.aR") * environment.get("REGIONAL").get("Kaers.aR")
								/ (1. + environment.get("REGIONAL").get("FRACaerw.aR")
										* environment.get("REGIONAL").get("Kaerw.aR")
										+ environment.get("REGIONAL").get("FRACaers.aR")
												* environment.get("REGIONAL").get("Kaers.aR")));

		environment.get("REGIONAL").put("KDEG.aR",
				environment.get("REGIONAL").get("FRgas.aR") * inputEng.getSubstancesData("kdegG.air")
						* (environment.get("REGIONAL").get("C.OHrad.aR") / inputEng.getSubstancesData("C.OHrad"))
						* (environment.get("REGIONAL").get("Tempfactor.aR")));

		environment.get("REGIONAL").put("kesc.aR", Math.log(2) / (60. * 365. * 24. * 3600.));

		environment.get("REGIONAL").put("CORG.s1R", 0.02);

		environment.get("REGIONAL").put("Kp.s1R",
				(inputEng.getSubstancesData("FRorig.s1") * inputEng.getSubstancesData("Ksw")
						+ (1 - inputEng.getSubstancesData("FRorig.s1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000 / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.s1R"));

		environment.get("REGIONAL").put("Ks1w.R",
				environment.get("REGIONAL").get("FRACa.s1R")
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("REGIONAL").get("FRACw.s1R")
						+ environment.get("REGIONAL").get("FRACs.s1R") * environment.get("REGIONAL").get("Kp.s1R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("REGIONAL").put("k.s1RD",
				environment.get("REGIONAL").get("KDEG.s1R") + environment.get("REGIONAL").get("FRACinf.s1R")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ environment.get("REGIONAL").get("Ks1w.R") * environment.get("REGIONAL").get("CORRleach.s1R")
						/ environment.get("REGIONAL").get("DEPTH.s1R"));

		environment.get("REGIONAL").put("CORG.s2R", 0.02);

		environment.get("REGIONAL").put("Kp.s2R",
				(inputEng.getSubstancesData("FRorig.s2") * inputEng.getSubstancesData("Ksw")
						+ (1 - inputEng.getSubstancesData("FRorig.s2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000 / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.s2R"));

		environment.get("REGIONAL").put("Ks2w.R",
				environment.get("REGIONAL").get("FRACa.s2R")
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s2w"))
						+ environment.get("REGIONAL").get("FRACw.s2R")
						+ environment.get("REGIONAL").get("FRACs.s2R") * environment.get("REGIONAL").get("Kp.s2R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("REGIONAL").put("k.s2RD",
				environment.get("REGIONAL").get("KDEG.s2R") + environment.get("REGIONAL").get("FRACinf.s2R")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ environment.get("REGIONAL").get("Ks2w.R") * environment.get("REGIONAL").get("CORRleach.s2R")
						/ environment.get("REGIONAL").get("DEPTH.s2R"));

		environment.get("REGIONAL").put("CORG.s3R", 0.02);

		environment.get("REGIONAL").put("Kp.s3R",
				(inputEng.getSubstancesData("FRorig.s3") * inputEng.getSubstancesData("Ksw")
						+ (1 - inputEng.getSubstancesData("FRorig.s3")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000 / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.s3R"));

		environment.get("REGIONAL").put("Ks3w.R",
				environment.get("REGIONAL").get("FRACa.s3R")
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s3w"))
						+ environment.get("REGIONAL").get("FRACw.s3R")
						+ environment.get("REGIONAL").get("FRACs.s3R") * environment.get("REGIONAL").get("Kp.s3R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("REGIONAL").put("k.s3RD",
				environment.get("REGIONAL").get("KDEG.s3R") + environment.get("REGIONAL").get("FRACinf.s3R")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ environment.get("REGIONAL").get("Ks3w.R") * environment.get("REGIONAL").get("CORRleach.s3R")
						/ environment.get("REGIONAL").get("DEPTH.s3R"));

		environment.get("REGIONAL").put("k.sd0RD", environment.get("REGIONAL").get("KDEG.sd0R")
				+ environment.get("REGIONAL").get("NETsedrate.w0R") / environment.get("REGIONAL").get("DEPTH.sd0R"));

		environment.get("REGIONAL").put("k.sd1RD", environment.get("REGIONAL").get("KDEG.sd1R")
				+ environment.get("REGIONAL").get("NETsedrate.w1R") / environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("k.sd2RD", environment.get("REGIONAL").get("KDEG.sd2R")
				+ environment.get("REGIONAL").get("NETsedrate.w2R") / environment.get("REGIONAL").get("DEPTH.sd2R"));

		environment.get("REGIONAL").put("KDEG.w0R",
				inputEng.getSubstancesData("kdegD.water") * environment.get("REGIONAL").get("Tempfactor.wsdsR")
						* (environment.get("REGIONAL").get("BACT.w0R") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("REGIONAL").get("FRwD.w0R"));

		environment.get("REGIONAL").put("KDEG.w1R",
				inputEng.getSubstancesData("kdegD.water") * environment.get("REGIONAL").get("Tempfactor.wsdsR")
						* (environment.get("REGIONAL").get("BACT.w1R") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("REGIONAL").get("FRwD.w1R"));

		environment.get("REGIONAL").put("KDEG.w2R",
				inputEng.getSubstancesData("kdegD.water") * environment.get("REGIONAL").get("Tempfactor.wsdsR")
						* (environment.get("REGIONAL").get("BACT.w2R") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("REGIONAL").get("FRwD.w2R"));

		environment.get("REGIONAL").put("k.w0RD", environment.get("REGIONAL").get("KDEG.w0R"));
		environment.get("REGIONAL").put("k.w1RD", environment.get("REGIONAL").get("KDEG.w1R"));
		environment.get("REGIONAL").put("k.w2RD", environment.get("REGIONAL").get("KDEG.w2R"));

		environment.get("REGIONAL").put("k.aRG",
				environment.get("REGIONAL").get("kesc.aR") + environment.get("REGIONAL").get("KDEG.aR"));

		environment.get("REGIONAL").put("ThermvelCP.aR",
				Math.pow(((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ ((Math.PI * ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadCP.a"), 3))
								* inputEng.getEnvProperties("RhoCP.a")))),
						0.5));

		environment.get("REGIONAL").put("DiffCP.aR",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadCP.a")));

		environment.get("REGIONAL").put("Fuchs.aRS.aRCP", Math.pow((1 + (4
				* (inputEng.getNanoProperties("DiffS.aR") + environment.get("REGIONAL").get("DiffCP.aR")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadCP.a")) * Math.pow(
						(environment.get("REGIONAL").get("ThermvelCP.aR")
								* environment.get("REGIONAL").get("ThermvelCP.aR")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("REGIONAL").put("k.aRS.aRP",
				environment.get("REGIONAL").get("Fuchs.aRS.aRCP")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadCP.a") + inputEng.getSubstancesData("RadS"))
								* (environment.get("REGIONAL").get("DiffCP.aR")
										+ inputEng.getNanoProperties("DiffS.aR")))
						* inputEng.getLandscapeSettings("REGIONAL", "NumConcCP.aR"));

		environment.get("REGIONAL").put("ThermvelAcc.aR",
				Math.pow(((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3)
								* inputEng.getEnvProperties("RhoAcc"))))),
						0.5));

		environment.get("REGIONAL").put("DiffAcc.aR",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadAcc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadAcc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadAcc")));

		environment.get("REGIONAL").put("Fuchs.aRS.aRAcc", Math.pow((1. + (4.
				* (inputEng.getNanoProperties("DiffS.aR") + environment.get("REGIONAL").get("DiffAcc.aR")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadAcc")) * Math.pow(
						(environment.get("REGIONAL").get("ThermvelAcc.aR")
								* environment.get("REGIONAL").get("ThermvelAcc.aR")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("REGIONAL").put("kcoag.aRS.AccR",
				environment.get("REGIONAL").get("Fuchs.aRS.aRAcc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadAcc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("REGIONAL").get("DiffAcc.aR")
										+ inputEng.getNanoProperties("DiffS.aR")))
						* inputEng.getLandscapeSettings("REGIONAL", "NumConcAcc.aR"));

		environment.get("REGIONAL").put("ThermvelNuc.aR",
				Math.pow(((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)
								* inputEng.getEnvProperties("RhoNuc"))))),
						0.5));

		environment.get("REGIONAL").put("DiffNuc.aR",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadNuc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadNuc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadNuc")));

		environment.get("REGIONAL").put("Fuchs.aRS.aRNuc", Math.pow((1 + (4
				* (inputEng.getNanoProperties("DiffS.aR") + environment.get("REGIONAL").get("DiffNuc.aR")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNuc")) * Math.pow(
						(environment.get("REGIONAL").get("ThermvelNuc.aR")
								* environment.get("REGIONAL").get("ThermvelNuc.aR")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("REGIONAL").put("kcoag.aRS.aRNuc",
				environment.get("REGIONAL").get("Fuchs.aRS.aRNuc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadNuc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("REGIONAL").get("DiffNuc.aR")
										+ inputEng.getNanoProperties("DiffS.aR")))
						* inputEng.getLandscapeSettings("REGIONAL", "NumConcNuc.aR"));

		environment.get("REGIONAL").put("k.aRS.aRA",
				environment.get("REGIONAL").get("kcoag.aRS.aRNuc") + environment.get("REGIONAL").get("kcoag.aRS.AccR"));

		environment.get("REGIONAL")
				.put("fGravSNC.wR", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("REGIONAL").put("fBrownSNC.wR",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.w")));

		environment.get("REGIONAL").put("Shear.w2R", 10.);

		environment.get("REGIONAL").put("fInterceptSNC.w2R", (4. / 3.) * environment.get("REGIONAL").get("Shear.w2R")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("REGIONAL").put("fTotalSNC.w2R", environment.get("REGIONAL").get("fInterceptSNC.w2R")
				+ +environment.get("REGIONAL").get("fBrownSNC.wR") + environment.get("REGIONAL").get("fGravSNC.wR"));

		environment.get("REGIONAL").put("NumConcNC.w2R", inputEng.getLandscapeSettings("REGIONAL", "COL.w2R")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("REGIONAL").put("k.w2RS.w2RA", environment.get("REGIONAL").get("fTotalSNC.w2R")
				* environment.get("REGIONAL").get("NumConcNC.w2R") * inputEng.getSubstancesData("AtefSA.w2"));

		environment.get("REGIONAL").put("Shear.w1R", 100.);

		environment.get("REGIONAL").put("fInterceptSNC.w1R", (4. / 3.) * environment.get("REGIONAL").get("Shear.w1R")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("REGIONAL").put("fTotalSNC.w1R", environment.get("REGIONAL").get("fInterceptSNC.w1R")
				+ +environment.get("REGIONAL").get("fBrownSNC.wR") + environment.get("REGIONAL").get("fGravSNC.wR"));

		environment.get("REGIONAL").put("NumConcNC.w1R", inputEng.getLandscapeSettings("REGIONAL", "COL.w1R")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("REGIONAL").put("k.w1RS.w1RA", environment.get("REGIONAL").get("fTotalSNC.w1R")
				* environment.get("REGIONAL").get("NumConcNC.w1R") * inputEng.getSubstancesData("AtefSA.w1"));

		environment.get("REGIONAL").put("Shear.w0R", 1.);

		environment.get("REGIONAL").put("fInterceptSNC.w0R", (4. / 3.) * environment.get("REGIONAL").get("Shear.w0R")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("REGIONAL").put("fTotalSNC.w0R", environment.get("REGIONAL").get("fInterceptSNC.w0R")
				+ +environment.get("REGIONAL").get("fBrownSNC.wR") + environment.get("REGIONAL").get("fGravSNC.wR"));

		environment.get("REGIONAL").put("NumConcNC.w0R", inputEng.getLandscapeSettings("REGIONAL", "COL.w0R")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("REGIONAL").put("k.w0RS.w0RA", environment.get("REGIONAL").get("fTotalSNC.w0R")
				* environment.get("REGIONAL").get("NumConcNC.w0R") * inputEng.getSubstancesData("AtefSA.w0"));

		environment.get("REGIONAL").put("fGravSSPM.wR", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelSPM.w")));

		environment.get("REGIONAL").put("fBrownSSPM.wR",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("REGIONAL").put("fInterceptSSPM.w2R", (4. / 3.) * environment.get("REGIONAL").get("Shear.w2R")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("REGIONAL").put("fTotalSSPM.w2R", environment.get("REGIONAL").get("fInterceptSSPM.w2R")
				+ +environment.get("REGIONAL").get("fBrownSSPM.wR") + environment.get("REGIONAL").get("fGravSSPM.wR"));

		environment.get("REGIONAL").put("NumConcSPM.w2R", inputEng.getLandscapeSettings("REGIONAL", "SUSP.w2R")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("REGIONAL").put("k.w2RS.w2RP", environment.get("REGIONAL").get("fTotalSSPM.w2R")
				* environment.get("REGIONAL").get("NumConcSPM.w2R") * inputEng.getSubstancesData("AtefSP.w2"));

		environment.get("REGIONAL").put("fInterceptSSPM.w1R", (4. / 3.) * environment.get("REGIONAL").get("Shear.w1R")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("REGIONAL").put("fTotalSSPM.w1R", environment.get("REGIONAL").get("fInterceptSSPM.w1R")
				+ +environment.get("REGIONAL").get("fBrownSSPM.wR") + environment.get("REGIONAL").get("fGravSSPM.wR"));

		environment.get("REGIONAL").put("NumConcSPM.w1R", inputEng.getLandscapeSettings("REGIONAL", "SUSP.w1R")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("REGIONAL").put("k.w1RS.w1RP", environment.get("REGIONAL").get("fTotalSSPM.w1R")
				* environment.get("REGIONAL").get("NumConcSPM.w1R") * inputEng.getSubstancesData("AtefSP.w1"));

		environment.get("REGIONAL").put("fInterceptSSPM.w0R", (4. / 3.) * environment.get("REGIONAL").get("Shear.w0R")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("REGIONAL").put("fTotalSSPM.w0R", environment.get("REGIONAL").get("fInterceptSSPM.w0R")
				+ +environment.get("REGIONAL").get("fBrownSSPM.wR") + environment.get("REGIONAL").get("fGravSSPM.wR"));

		environment.get("REGIONAL").put("NumConcSPM.w0R", inputEng.getLandscapeSettings("REGIONAL", "SUSP.w0R")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("REGIONAL").put("k.w0RS.w0RP", environment.get("REGIONAL").get("fTotalSSPM.w0R")
				* environment.get("REGIONAL").get("NumConcSPM.w0R") * inputEng.getSubstancesData("AtefSP.w0"));

		environment.get("REGIONAL")
				.put("fGravSNC.sdR", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("REGIONAL").put("fBrownSNC.sdR",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.sd")));

		environment.get("REGIONAL").put("NumConcNC.sd2R", 2. * environment.get("REGIONAL").get("NumConcNC.w2R"));

		environment.get("REGIONAL").put("k.sd2RS.sd2RA", inputEng.getSubstancesData("AtefSA.sd2")
				* environment.get("REGIONAL").get("NumConcNC.sd2R")
				* (environment.get("REGIONAL").get("fBrownSNC.sdR") + environment.get("REGIONAL").get("fGravSNC.sdR")));

		environment.get("REGIONAL").put("NumConcNC.sd1R", 2. * environment.get("REGIONAL").get("NumConcNC.w1R"));

		environment.get("REGIONAL").put("k.sd1RS.sd1RA", inputEng.getSubstancesData("AtefSA.sd1")
				* environment.get("REGIONAL").get("NumConcNC.sd1R")
				* (environment.get("REGIONAL").get("fBrownSNC.sdR") + environment.get("REGIONAL").get("fGravSNC.sdR")));

		environment.get("REGIONAL").put("NumConcNC.sd0R", 2. * environment.get("REGIONAL").get("NumConcNC.w0R"));

		environment.get("REGIONAL").put("k.sd0RS.sd0RA", inputEng.getSubstancesData("AtefSA.sd0")
				* environment.get("REGIONAL").get("NumConcNC.sd0R")
				* (environment.get("REGIONAL").get("fBrownSNC.sdR") + environment.get("REGIONAL").get("fGravSNC.sdR")));

		environment.get("REGIONAL").put("Udarcy.sdR", 9.e-6);

		environment.get("REGIONAL").put("GravNumberSP.sdR", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("REGIONAL").get("Udarcy.sdR")));

		environment.get("REGIONAL").put("PecletNumberSP.sdR", environment.get("REGIONAL").get("Udarcy.sdR") * 2.
				* inputEng.getEnvProperties("RadFP.sd") / inputEng.getNanoProperties("DiffS.wR"));

		environment.get("REGIONAL").put("aspectratioSP.sdR",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.sd"));

		environment.get("REGIONAL").put("vdWaalsNumberSP.sdR",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")));

		environment.get("REGIONAL").put("fGravSP.sdR",
				0.22 * Math.pow(environment.get("REGIONAL").get("aspectratioSP.sdR"), -0.24)
						* Math.pow(environment.get("REGIONAL").get("GravNumberSP.sdR"), 1.11)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSP.sdR"), 0.053));

		environment.get("REGIONAL").put("fInterceptSP.sdR",
				0.55 * Math.pow(environment.get("REGIONAL").get("aspectratioSP.sdR"), 1.55)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberSP.sdR"), -0.125)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSP.sdR"), 0.125));

		environment.get("REGIONAL").put("Por.sdR", environment.get("REGIONAL").get("FRACw.sdR"));

		environment.get("REGIONAL").put("GammPDF.sdR",
				Math.pow(1. - environment.get("REGIONAL").get("Por.sdR"), 1. / 3.));

		environment.get("REGIONAL").put("ASPDF.sdR",
				(2. * (1. - Math.pow(environment.get("REGIONAL").get("GammPDF.sdR"), 5)))
						/ (2. - 3. * environment.get("REGIONAL").get("GammPDF.sdR")
								+ 3 * Math.pow(environment.get("REGIONAL").get("GammPDF.sdR"), 5)
								- 2 * Math.pow(environment.get("REGIONAL").get("GammPDF.sdR"), 6)));

		environment.get("REGIONAL").put("fBrownSP.sdR",
				2.4 * Math.pow(environment.get("REGIONAL").get("Por.sdR"), 1. / 3.)
						* Math.pow(environment.get("REGIONAL").get("aspectratioSP.sdR"), -0.081)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberSP.sdR"), -0.715)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSP.sdR"), 0.053));

		environment.get("REGIONAL").put("fTotalSP.sdR", environment.get("REGIONAL").get("fBrownSP.sdR")
				+ environment.get("REGIONAL").get("fInterceptSP.sdR") + environment.get("REGIONAL").get("fGravSP.sdR"));

		environment.get("REGIONAL").put("Filter.sdR", (3. / 2.) * ((1. - environment.get("REGIONAL").get("Por.sdR"))
				/ (2 * inputEng.getEnvProperties("RadFP.sd") * environment.get("REGIONAL").get("Por.sdR"))));

		environment.get("REGIONAL").put("k.sd2RS.sd2RP",
				environment.get("REGIONAL").get("Filter.sdR") * environment.get("REGIONAL").get("fTotalSP.sdR")
						* environment.get("REGIONAL").get("Udarcy.sdR") * inputEng.getSubstancesData("AtefSP.sd2"));

		environment.get("REGIONAL").put("k.sd1RS.sd1RP",
				environment.get("REGIONAL").get("Filter.sdR") * environment.get("REGIONAL").get("Udarcy.sdR")
						* environment.get("REGIONAL").get("fTotalSP.sdR") * inputEng.getSubstancesData("AtefSP.sd1"));

		environment.get("REGIONAL").put("k.sd0RS.sd0RP",
				environment.get("REGIONAL").get("Filter.sdR") * environment.get("REGIONAL").get("Udarcy.sdR")
						* environment.get("REGIONAL").get("fTotalSP.sdR") * inputEng.getSubstancesData("AtefSP.sd0"));

		environment.get("REGIONAL").put("fGravSNC.sR", Math.PI
				* (Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2) * Math
						.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.s"))));

		environment.get("REGIONAL").put("fBrownSNC.sR",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.s")));

		environment.get("REGIONAL").put("NumConcNC.sR", inputEng.getLandscapeSettings("REGIONAL", "mConcNC.sR")
				/ (inputEng.getEnvProperties("SingleVolNC.s") * inputEng.getEnvProperties("RhoNC.sd")));

		environment.get("REGIONAL").put("k.s3RS.s3RA", inputEng.getSubstancesData("AtefSA.s3")
				* environment.get("REGIONAL").get("NumConcNC.sR")
				* (environment.get("REGIONAL").get("fBrownSNC.sR") + environment.get("REGIONAL").get("fGravSNC.sR")));

		environment.get("REGIONAL").put("k.s2RS.s2RA", inputEng.getSubstancesData("AtefSA.s2")
				* environment.get("REGIONAL").get("NumConcNC.sR")
				* (environment.get("REGIONAL").get("fBrownSNC.sR") + environment.get("REGIONAL").get("fGravSNC.sR")));

		environment.get("REGIONAL").put("k.s1RS.s1RA", inputEng.getSubstancesData("AtefSA.s1")
				* environment.get("REGIONAL").get("NumConcNC.sR")
				* (environment.get("REGIONAL").get("fBrownSNC.sR") + environment.get("REGIONAL").get("fGravSNC.sR")));

		environment.get("REGIONAL").put("Udarcy.s3R", 9.e-6);

		environment.get("REGIONAL").put("GravNumberS.s3R", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("REGIONAL").get("Udarcy.s3R")));

		environment.get("REGIONAL").put("vdWaalsNumberSFP.s3R",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")));

		environment.get("REGIONAL").put("PecletNumberFP.s3R", environment.get("REGIONAL").get("Udarcy.s3R") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wR"));

		environment.get("REGIONAL").put("aspectratioSFP.s3R",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("REGIONAL").put("fGravSFP.s3R",
				0.22 * Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s3R"), -0.24)
						* Math.pow(environment.get("REGIONAL").get("GravNumberS.s3R"), 1.11)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s3R"), 0.053));

		environment.get("REGIONAL").put("fInterceptSFP.s3R",
				0.55 * Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s3R"), 1.55)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberFP.s3R"), -0.125)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s3R"), 0.125));

		environment.get("REGIONAL").put("Por.s3R", 1. - environment.get("REGIONAL").get("FRACs.s3R"));

		environment.get("REGIONAL").put("GammPDF.s3R",
				Math.pow(1. - environment.get("REGIONAL").get("Por.s3R"), 1. / 3.));

		environment.get("REGIONAL").put("ASPDF.s3R",
				(2. * (1. - Math.pow(environment.get("REGIONAL").get("GammPDF.s3R"), 5)))
						/ (2. - 3. * environment.get("REGIONAL").get("GammPDF.s3R")
								+ 3 * Math.pow(environment.get("REGIONAL").get("GammPDF.s3R"), 5)
								- 2 * Math.pow(environment.get("REGIONAL").get("GammPDF.s3R"), 6)));

		environment.get("REGIONAL").put("fBrownSFP.s3R",
				2.4 * Math.pow(environment.get("REGIONAL").get("ASPDF.s3R"), 1. / 3.)
						* Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s3R"), -0.081)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberFP.s3R"), -0.715)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s3R"), 0.053));

		environment.get("REGIONAL").put("fTotalSFP.s3R", environment.get("REGIONAL").get("fInterceptSFP.s3R")
				+ +environment.get("REGIONAL").get("fBrownSFP.s3R") + environment.get("REGIONAL").get("fGravSFP.s3R"));

		environment.get("REGIONAL").put("Filter.s3R", (3. / 2.) * ((1. - environment.get("REGIONAL").get("Por.s3R"))
				/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("REGIONAL").get("Por.s3R"))));

		environment.get("REGIONAL").put("k.s3RS.s3RP",
				environment.get("REGIONAL").get("Filter.s3R") * environment.get("REGIONAL").get("fTotalSFP.s3R")
						* environment.get("REGIONAL").get("Udarcy.s3R") * inputEng.getSubstancesData("AtefSP.s3"));

		environment.get("REGIONAL").put("Udarcy.s2R", 9.e-6);

		environment.get("REGIONAL").put("GravNumberS.s2R", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("REGIONAL").get("Udarcy.s2R")));

		environment.get("REGIONAL").put("vdWaalsNumberSFP.s2R",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")));

		environment.get("REGIONAL").put("PecletNumberFP.s2R", environment.get("REGIONAL").get("Udarcy.s2R") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wR"));

		environment.get("REGIONAL").put("aspectratioSFP.s2R",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("REGIONAL").put("fGravSFP.s2R",
				0.22 * Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s2R"), -0.24)
						* Math.pow(environment.get("REGIONAL").get("GravNumberS.s2R"), 1.11)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s2R"), 0.053));

		environment.get("REGIONAL").put("fInterceptSFP.s2R",
				0.55 * Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s2R"), 1.55)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberFP.s2R"), -0.125)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s2R"), 0.125));

		environment.get("REGIONAL").put("Por.s2R", 1. - environment.get("REGIONAL").get("FRACs.s2R"));

		environment.get("REGIONAL").put("GammPDF.s2R",
				Math.pow(1. - environment.get("REGIONAL").get("Por.s2R"), 1. / 3.));

		environment.get("REGIONAL").put("ASPDF.s2R",
				(2. * (1. - Math.pow(environment.get("REGIONAL").get("GammPDF.s2R"), 5)))
						/ (2. - 3. * environment.get("REGIONAL").get("GammPDF.s2R")
								+ 3 * Math.pow(environment.get("REGIONAL").get("GammPDF.s2R"), 5)
								- 2 * Math.pow(environment.get("REGIONAL").get("GammPDF.s2R"), 6)));

		environment.get("REGIONAL").put("fBrownSFP.s2R",
				2.4 * Math.pow(environment.get("REGIONAL").get("ASPDF.s2R"), 1. / 3.)
						* Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s2R"), -0.081)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberFP.s2R"), -0.715)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s2R"), 0.053));

		environment.get("REGIONAL").put("fTotalSFP.s2R", environment.get("REGIONAL").get("fInterceptSFP.s2R")
				+ +environment.get("REGIONAL").get("fBrownSFP.s2R") + environment.get("REGIONAL").get("fGravSFP.s2R"));

		environment.get("REGIONAL").put("Filter.s2R", (3. / 2.) * ((1. - environment.get("REGIONAL").get("Por.s2R"))
				/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("REGIONAL").get("Por.s2R"))));

		environment.get("REGIONAL").put("k.s2RS.s2RP",
				environment.get("REGIONAL").get("Filter.s2R") * environment.get("REGIONAL").get("fTotalSFP.s2R")
						* environment.get("REGIONAL").get("Udarcy.s2R") * inputEng.getSubstancesData("AtefSP.s2"));

		environment.get("REGIONAL").put("Udarcy.s1R", 9.e-6);

		environment.get("REGIONAL").put("GravNumberS.s1R", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("REGIONAL").get("Udarcy.s1R")));

		environment.get("REGIONAL").put("vdWaalsNumberSFP.s1R",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")));

		environment.get("REGIONAL").put("PecletNumberFP.s1R", environment.get("REGIONAL").get("Udarcy.s1R") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wR"));

		environment.get("REGIONAL").put("aspectratioSFP.s1R",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("REGIONAL").put("fGravSFP.s1R",
				0.22 * Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s1R"), -0.24)
						* Math.pow(environment.get("REGIONAL").get("GravNumberS.s1R"), 1.11)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s1R"), 0.053));

		environment.get("REGIONAL").put("fInterceptSFP.s1R",
				0.55 * Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s1R"), 1.55)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberFP.s1R"), -0.125)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s1R"), 0.125));

		environment.get("REGIONAL").put("Por.s1R", 1. - environment.get("REGIONAL").get("FRACs.s1R"));

		environment.get("REGIONAL").put("GammPDF.s1R",
				Math.pow(1. - environment.get("REGIONAL").get("Por.s1R"), 1. / 3.));

		environment.get("REGIONAL").put("ASPDF.s1R",
				(2. * (1. - Math.pow(environment.get("REGIONAL").get("GammPDF.s1R"), 5)))
						/ (2. - 3. * environment.get("REGIONAL").get("GammPDF.s1R")
								+ 3 * Math.pow(environment.get("REGIONAL").get("GammPDF.s1R"), 5)
								- 2 * Math.pow(environment.get("REGIONAL").get("GammPDF.s1R"), 6)));

		environment.get("REGIONAL").put("fBrownSFP.s1R",
				2.4 * Math.pow(environment.get("REGIONAL").get("ASPDF.s1R"), 1. / 3.)
						* Math.pow(environment.get("REGIONAL").get("aspectratioSFP.s1R"), -0.081)
						* Math.pow(environment.get("REGIONAL").get("PecletNumberFP.s1R"), -0.715)
						* Math.pow(environment.get("REGIONAL").get("vdWaalsNumberSFP.s1R"), 0.053));

		environment.get("REGIONAL").put("fTotalSFP.s1R", environment.get("REGIONAL").get("fInterceptSFP.s1R")
				+ +environment.get("REGIONAL").get("fBrownSFP.s1R") + environment.get("REGIONAL").get("fGravSFP.s1R"));

		environment.get("REGIONAL").put("Filter.s1R", (3. / 2.) * ((1. - environment.get("REGIONAL").get("Por.s1R"))
				/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("REGIONAL").get("Por.s1R"))));

		environment.get("REGIONAL").put("k.s1RS.s1RP",
				environment.get("REGIONAL").get("Filter.s1R") * environment.get("REGIONAL").get("fTotalSFP.s1R")
						* environment.get("REGIONAL").get("Udarcy.s1R") * inputEng.getSubstancesData("AtefSP.s1"));

		environment.get("REGIONAL").put("FRACtwet", 1.);

		environment.get("REGIONAL").put("RAINrate.wet.R",
				inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R") / environment.get("REGIONAL").get("FRACtwet"));

		environment.get("REGIONAL").put("Rad.cwR",
				((0.7 * (Math.pow(60. * 60. * 1000. * environment.get("REGIONAL").get("RAINrate.wet.R"), 0.25)) / 2.)
						/ 1000.));

		environment.get("REGIONAL").put("Cunningham.cwR", 1.);

		environment.get("REGIONAL").put("TerminalVel.cwR",
				(Math.pow(2 * environment.get("REGIONAL").get("Rad.cwR"), 2)
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")
								- inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * environment.get("REGIONAL").get("Cunningham.cwR"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("REGIONAL").put("ReyNumber.cwR",
				((2. * environment.get("REGIONAL").get("Rad.cwR")) * environment.get("REGIONAL").get("TerminalVel.cwR")
						* inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						/ (2. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("REGIONAL").put("CritStokesNumb.cwR",
				(1.2 + (1. / 12.) * Math.log(1. + environment.get("REGIONAL").get("ReyNumber.cwR")))
						/ (1. + Math.log(1 + environment.get("REGIONAL").get("ReyNumber.cwR"))));

		environment.get("REGIONAL")
				.put("SingleMassA.aR",
						((inputEng.getLandscapeSettings("REGIONAL", "NumConcNuc.aR")
								* (inputEng.getEnvProperties("RhoNuc")
										* ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3))
										+ inputEng.getSubstancesData("RhoS")
												* inputEng.getNanoProperties("SingleVolS")))
								+ (inputEng.getLandscapeSettings("REGIONAL", "NumConcAcc.aR")
										* (inputEng.getEnvProperties("RhoAcc")
												* ((4. / 3.) * Math.PI
														* Math.pow(inputEng.getEnvProperties("RadAcc"), 3))
												+ inputEng.getSubstancesData("RhoS")
														* inputEng.getNanoProperties("SingleVolS"))))
								/ (inputEng.getLandscapeSettings("REGIONAL", "NumConcNuc.aR")
										+ inputEng.getLandscapeSettings("REGIONAL", "NumConcAcc.aR")));

		environment.get("REGIONAL").put("SingleVolA.aR",
				((inputEng.getLandscapeSettings("REGIONAL", "NumConcNuc.aR") * (inputEng.getNanoProperties("SingleVolS")
						+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)))))
						+ (inputEng.getLandscapeSettings("REGIONAL", "NumConcAcc.aR")
								* (inputEng.getNanoProperties("SingleVolS")
										+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3))))))
						/ (inputEng.getLandscapeSettings("REGIONAL", "NumConcNuc.aR")
								+ inputEng.getLandscapeSettings("REGIONAL", "NumConcAcc.aR")));

		environment.get("REGIONAL").put("RhoA.aR",
				environment.get("REGIONAL").get("SingleMassA.aR") / environment.get("REGIONAL").get("SingleVolA.aR"));

		environment.get("REGIONAL").put("RadA.aR",
				Math.pow((environment.get("REGIONAL").get("SingleVolA.aR")) / ((4. / 3.) * Math.PI), 1. / 3.));

		environment.get("REGIONAL").put("tdry.R", 3.3 * 24 * 3600);
		environment.get("REGIONAL").put("twet.R", 3600 * 3.3 * 24. * (0.06 / (1. - 0.06)));
		environment.get("REGIONAL").put("COLLECTeff.R", 200000.);

		environment.get("REGIONAL").put("k.aRP.cwRP",
				((environment.get("REGIONAL").get("tdry.R") + environment.get("REGIONAL").get("twet.R"))
						/ environment.get("REGIONAL").get("twet.R") * environment.get("REGIONAL").get("COLLECTeff.R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R"))
						/ environment.get("REGIONAL").get("HEIGHT.aR"));

		environment.get("REGIONAL").put("CunninghamA.aR", 1. + (66.e-9 / environment.get("REGIONAL").get("RadA.aR"))
				* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / environment.get("REGIONAL").get("RadA.aR")))));

		environment.get("REGIONAL").put("DiffA.aR",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("REGIONAL", "TEMP.R")
						* environment.get("REGIONAL").get("CunninghamA.aR"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* environment.get("REGIONAL").get("RadA.aR")));

		environment.get("REGIONAL").put("RelaxA.aR",
				((environment.get("REGIONAL").get("RhoA.aR") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * environment.get("REGIONAL").get("RadA.aR")), 2)
						* environment.get("REGIONAL").get("CunninghamA.aR"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("REGIONAL").put("SettvelA.aR", (Math.pow(environment.get("REGIONAL").get("RadA.aR"), 2)
				* (environment.get("REGIONAL").get("RhoA.aR") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
				* 9.81 * environment.get("REGIONAL").get("CunninghamA.aR"))
				/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("REGIONAL").put("StokesNumberA.aR", (2. * environment.get("REGIONAL").get("RelaxA.aR")
				* (environment.get("REGIONAL").get("TerminalVel.cwR") - environment.get("REGIONAL").get("SettvelA.aR")))
				/ (2. * environment.get("REGIONAL").get("Rad.cwR")));

		environment.get("REGIONAL").put("SchmidtNumberA.aR", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("REGIONAL").get("DiffA.aR")));

		environment.get("REGIONAL").put("fBrown.aRA.cwRA",
				(4. / (environment.get("REGIONAL").get("ReyNumber.cwR")
						* environment.get("REGIONAL").get("SchmidtNumberA.aR")))
						* (1. + 0.4 * (Math.pow(environment.get("REGIONAL").get("ReyNumber.cwR"), 0.5)
								* Math.pow(environment.get("REGIONAL").get("SchmidtNumberA.aR"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("REGIONAL").get("ReyNumber.cwR"), 0.5)
										* Math.pow(environment.get("REGIONAL").get("SchmidtNumberA.aR"), 0.5))));

		environment.get("REGIONAL").put("fIntercept.aRA.cwRA",
				4. * (environment.get("REGIONAL").get("RadA.aR") / environment.get("REGIONAL").get("Rad.cwR"))
						* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
								+ (1. + 2. * Math.pow(environment.get("REGIONAL").get("ReyNumber.cwR"), 0.5)
										* (environment.get("REGIONAL").get("RadA.aR")
												/ environment.get("REGIONAL").get("Rad.cwR")))));

		if (environment.get("REGIONAL").get("StokesNumberA.aR") > environment.get("REGIONAL").get("CritStokesNumb.cwR"))
			environment.get("REGIONAL").put("fGrav.aRA.cwRA",
					Math.pow(((environment.get("REGIONAL").get("StokesNumberA.aR")
							- environment.get("REGIONAL").get("CritStokesNumb.cwR"))
							/ environment.get("REGIONAL").get("StokesNumberA.aR")
							- environment.get("REGIONAL").get("CritStokesNumb.cwR") + 2. / 3.), 3. / 2.));
		else
			environment.get("REGIONAL").put("fGrav.aRA.cwRA", 0.0);

		environment.get("REGIONAL").put("fTotal.aRA.cwRA",
				environment.get("REGIONAL").get("fBrown.aRA.cwRA")
						+ environment.get("REGIONAL").get("fIntercept.aRA.cwRA")
						+ environment.get("REGIONAL").get("fGrav.aRA.cwRA"));

		environment.get("REGIONAL").put("k.aRA.cwRA",
				((3. / 2.)
						* (environment.get("REGIONAL").get("fTotal.aRA.cwRA")
								* environment.get("REGIONAL").get("RAINrate.wet.R"))
						/ (2. * environment.get("REGIONAL").get("Rad.cwR"))));

		environment.get("REGIONAL").put("RelaxS.aR",
				((inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * inputEng.getSubstancesData("RadS")), 2)
						* inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("REGIONAL").put("SettvelS.aR",
				(Math.pow(2. * inputEng.getSubstancesData("RadS"), 2)
						* (inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("REGIONAL").put("StokesNumberS.aR", (2. * environment.get("REGIONAL").get("RelaxS.aR")
				* (environment.get("REGIONAL").get("TerminalVel.cwR") - environment.get("REGIONAL").get("SettvelS.aR")))
				/ (2. * environment.get("REGIONAL").get("Rad.cwR")));

		environment.get("REGIONAL").put("SchmidtNumberS.aR", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * inputEng.getNanoProperties("DiffS.aR")));

		environment.get("REGIONAL").put("fBrown.aRS.cwRS",
				(4. / (environment.get("REGIONAL").get("ReyNumber.cwR")
						* environment.get("REGIONAL").get("SchmidtNumberS.aR")))
						* (1. + 0.4 * (Math.pow(environment.get("REGIONAL").get("ReyNumber.cwR"), 0.5)
								* Math.pow(environment.get("REGIONAL").get("SchmidtNumberS.aR"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("REGIONAL").get("ReyNumber.cwR"), 0.5)
										* Math.pow(environment.get("REGIONAL").get("SchmidtNumberS.aR"), 0.5))));

		environment.get("REGIONAL").put("fIntercept.aRS.cwRS", 4.
				* (inputEng.getSubstancesData("RadS") / environment.get("REGIONAL").get("Rad.cwR"))
				* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
						/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						+ (1. + 2. * Math.pow(environment.get("REGIONAL").get("ReyNumber.cwR"), 0.5)
								* (inputEng.getSubstancesData("RadS") / environment.get("REGIONAL").get("Rad.cwR")))));

		if (environment.get("REGIONAL").get("StokesNumberS.aR") > environment.get("REGIONAL").get("CritStokesNumb.cwR"))
			environment.get("REGIONAL").put("fGrav.aRS.cwRS",
					Math.pow(((environment.get("REGIONAL").get("StokesNumberS.aR")
							- environment.get("REGIONAL").get("CritStokesNumb.cwR"))
							/ environment.get("REGIONAL").get("StokesNumberS.aR")
							- environment.get("REGIONAL").get("CritStokesNumb.cwR") + 2. / 3.), 3. / 2.));
		else
			environment.get("REGIONAL").put("fGrav.aRS.cwRS", 0.0);

		environment.get("REGIONAL").put("fTotal.aRS.cwRS",
				environment.get("REGIONAL").get("fBrown.aRS.cwRS")
						+ environment.get("REGIONAL").get("fIntercept.aRS.cwRS")
						+ environment.get("REGIONAL").get("fGrav.aRS.cwRS"));

		environment.get("REGIONAL").put("k.aRS.cwRS",
				((3. / 2.)
						* (environment.get("REGIONAL").get("fTotal.aRS.cwRS")
								* environment.get("REGIONAL").get("RAINrate.wet.R"))
						/ (2. * environment.get("REGIONAL").get("Rad.cwR"))));

		environment.get("REGIONAL").put("fGrav.aRAs3RA", Math.pow((environment.get("REGIONAL").get("StokesNumberA.aR")
				/ (environment.get("REGIONAL").get("StokesNumberA.aR") + 0.8)), 2));

		environment.get("REGIONAL").put("veghair.s1R", 1e-5);
		environment.get("REGIONAL").put("largevegradius.s1R", 0.0005);
		environment.get("REGIONAL").put("AREAFRACveg.s1R", 0.01);
		environment.get("REGIONAL").put("FRICvelocity.s1R", 0.19);
		environment.get("REGIONAL").put("AEROresist.s1R", 74.);

		environment.get("REGIONAL").put("veghair.s2R", 1e-5);
		environment.get("REGIONAL").put("largevegradius.s2R", 0.0005);
		environment.get("REGIONAL").put("AREAFRACveg.s2R", 0.01);
		environment.get("REGIONAL").put("FRICvelocity.s2R", 0.19);
		environment.get("REGIONAL").put("AEROresist.s2R", 74.);

		environment.get("REGIONAL").put("veghair.s3R", 1e-5);
		environment.get("REGIONAL").put("largevegradius.s3R", 0.0005);
		environment.get("REGIONAL").put("AREAFRACveg.s3R", 0.01);
		environment.get("REGIONAL").put("FRICvelocity.s3R", 0.19);
		environment.get("REGIONAL").put("AEROresist.s3R", 74.);

		environment.get("REGIONAL").put("fGrav.aRAs3RA", Math.pow((environment.get("REGIONAL").get("StokesNumberA.aR")
				/ (environment.get("REGIONAL").get("StokesNumberA.aR") + 0.8)), 2));

		environment.get("REGIONAL").put("fIntercept.aRA.s3RA", 0.3 * (environment.get("REGIONAL").get("AREAFRACveg.s3R")
				* (environment.get("REGIONAL").get("RadA.aR")
						/ (environment.get("REGIONAL").get("RadA.aR") + environment.get("REGIONAL").get("veghair.s3R")))
				+ (1 - environment.get("REGIONAL").get("AREAFRACveg.s3R"))
						* (environment.get("REGIONAL").get("RadA.aR") / (environment.get("REGIONAL").get("RadA.aR")
								+ environment.get("REGIONAL").get("largevegradius.s3R")))));

		environment.get("REGIONAL").put("fBrown.aRA.s3RA",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberA.aR"), -2. / 3.));

		environment.get("REGIONAL").put("SURFresist.s3RA",
				1 / (environment.get("REGIONAL").get("FRICvelocity.s3R")
						* (environment.get("REGIONAL").get("fBrown.aRA.s3RA")
								+ environment.get("REGIONAL").get("fIntercept.aRA.s3RA")
								+ environment.get("REGIONAL").get("fGrav.aRAs3RA"))));

		environment.get("REGIONAL").put("DEPvelocity.aRA.s3RA",
				1. / (environment.get("REGIONAL").get("AEROresist.s3R")
						+ environment.get("REGIONAL").get("SURFresist.s3RA"))
						+ environment.get("REGIONAL").get("SettvelA.aR"));

		environment.get("REGIONAL").put("k.aRA.s3RA", (environment.get("REGIONAL").get("DEPvelocity.aRA.s3RA")
				* environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRAs2RA", Math.pow((environment.get("REGIONAL").get("StokesNumberA.aR")
				/ (environment.get("REGIONAL").get("StokesNumberA.aR") + 0.8)), 2));

		environment.get("REGIONAL").put("fIntercept.aRA.s2RA", 0.3 * (environment.get("REGIONAL").get("AREAFRACveg.s2R")
				* (environment.get("REGIONAL").get("RadA.aR")
						/ (environment.get("REGIONAL").get("RadA.aR") + environment.get("REGIONAL").get("veghair.s2R")))
				+ (1 - environment.get("REGIONAL").get("AREAFRACveg.s2R"))
						* (environment.get("REGIONAL").get("RadA.aR") / (environment.get("REGIONAL").get("RadA.aR")
								+ environment.get("REGIONAL").get("largevegradius.s2R")))));

		environment.get("REGIONAL").put("fBrown.aRA.s2RA",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberA.aR"), -2. / 3.));

		environment.get("REGIONAL").put("SURFresist.s2RA",
				1 / (environment.get("REGIONAL").get("FRICvelocity.s2R")
						* (environment.get("REGIONAL").get("fBrown.aRA.s2RA")
								+ environment.get("REGIONAL").get("fIntercept.aRA.s2RA")
								+ environment.get("REGIONAL").get("fGrav.aRAs2RA"))));

		environment.get("REGIONAL").put("DEPvelocity.aRA.s2RA",
				1. / (environment.get("REGIONAL").get("AEROresist.s2R")
						+ environment.get("REGIONAL").get("SURFresist.s2RA"))
						+ environment.get("REGIONAL").get("SettvelA.aR"));

		environment.get("REGIONAL").put("k.aRA.s2RA", (environment.get("REGIONAL").get("DEPvelocity.aRA.s2RA")
				* environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRAs1RA", Math.pow((environment.get("REGIONAL").get("StokesNumberA.aR")
				/ (environment.get("REGIONAL").get("StokesNumberA.aR") + 0.8)), 2));

		environment.get("REGIONAL").put("fIntercept.aRA.s1RA", 0.3 * (environment.get("REGIONAL").get("AREAFRACveg.s1R")
				* (environment.get("REGIONAL").get("RadA.aR")
						/ (environment.get("REGIONAL").get("RadA.aR") + environment.get("REGIONAL").get("veghair.s1R")))
				+ (1 - environment.get("REGIONAL").get("AREAFRACveg.s1R"))
						* (environment.get("REGIONAL").get("RadA.aR") / (environment.get("REGIONAL").get("RadA.aR")
								+ environment.get("REGIONAL").get("largevegradius.s1R")))));

		environment.get("REGIONAL").put("fBrown.aRA.s1RA",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberA.aR"), -2. / 3.));

		environment.get("REGIONAL").put("SURFresist.s1RA",
				1 / (environment.get("REGIONAL").get("FRICvelocity.s1R")
						* (environment.get("REGIONAL").get("fBrown.aRA.s1RA")
								+ environment.get("REGIONAL").get("fIntercept.aRA.s1RA")
								+ environment.get("REGIONAL").get("fGrav.aRAs1RA"))));

		environment.get("REGIONAL").put("DEPvelocity.aRA.s1RA",
				1. / (environment.get("REGIONAL").get("AEROresist.s1R")
						+ environment.get("REGIONAL").get("SURFresist.s1RA"))
						+ environment.get("REGIONAL").get("SettvelA.aR"));

		environment.get("REGIONAL").put("k.aRA.s1RA", (environment.get("REGIONAL").get("DEPvelocity.aRA.s1RA")
				* environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRS.s3RS", Math.pow((environment.get("REGIONAL").get("StokesNumberS.aR")
				/ (environment.get("REGIONAL").get("StokesNumberS.aR") + 0.8)), 2));

		environment.get("REGIONAL")
				.put("fIntercept.aRS.s3RS",
						0.3 * (environment.get("REGIONAL").get("AREAFRACveg.s3R") * (inputEng.getSubstancesData("RadS")
								/ (inputEng.getSubstancesData("RadS") + environment.get("REGIONAL").get("veghair.s3R")))
								+ (1 - environment.get("REGIONAL").get("AREAFRACveg.s3R"))
										* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
												+ environment.get("REGIONAL").get("largevegradius.s3R")))));

		environment.get("REGIONAL").put("fBrown.aRS.s3RS",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberS.aR"), -2. / 3.));

		environment.get("REGIONAL").put("SURFresist.s3RS",
				1 / (environment.get("REGIONAL").get("FRICvelocity.s3R")
						* (environment.get("REGIONAL").get("fBrown.aRS.s3RS")
								+ environment.get("REGIONAL").get("fIntercept.aRS.s3RS")
								+ environment.get("REGIONAL").get("fGrav.aRS.s3RS"))));

		environment.get("REGIONAL").put("DEPvelocity.aRS.s3RS",
				1. / (environment.get("REGIONAL").get("AEROresist.s3R")
						+ environment.get("REGIONAL").get("SURFresist.s3RS"))
						+ environment.get("REGIONAL").get("SettvelS.aR"));

		environment.get("REGIONAL").put("k.aRS.s3RS", (environment.get("REGIONAL").get("DEPvelocity.aRS.s3RS")
				* environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRS.s2RS", Math.pow((environment.get("REGIONAL").get("StokesNumberS.aR")
				/ (environment.get("REGIONAL").get("StokesNumberS.aR") + 0.8)), 2));

		environment.get("REGIONAL")
				.put("fIntercept.aRS.s2RS",
						0.3 * (environment.get("REGIONAL").get("AREAFRACveg.s2R") * (inputEng.getSubstancesData("RadS")
								/ (inputEng.getSubstancesData("RadS") + environment.get("REGIONAL").get("veghair.s2R")))
								+ (1 - environment.get("REGIONAL").get("AREAFRACveg.s2R"))
										* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
												+ environment.get("REGIONAL").get("largevegradius.s2R")))));

		environment.get("REGIONAL").put("fBrown.aRS.s2RS",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberS.aR"), -2. / 3.));

		environment.get("REGIONAL").put("SURFresist.s2RS",
				1 / (environment.get("REGIONAL").get("FRICvelocity.s2R")
						* (environment.get("REGIONAL").get("fBrown.aRS.s2RS")
								+ environment.get("REGIONAL").get("fIntercept.aRS.s2RS")
								+ environment.get("REGIONAL").get("fGrav.aRS.s2RS"))));

		environment.get("REGIONAL").put("DEPvelocity.aRS.s2RS",
				1. / (environment.get("REGIONAL").get("AEROresist.s2R")
						+ environment.get("REGIONAL").get("SURFresist.s2RS"))
						+ environment.get("REGIONAL").get("SettvelS.aR"));

		environment.get("REGIONAL").put("k.aRS.s2RS", (environment.get("REGIONAL").get("DEPvelocity.aRS.s2RS")
				* environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRS.s1RS", Math.pow((environment.get("REGIONAL").get("StokesNumberS.aR")
				/ (environment.get("REGIONAL").get("StokesNumberS.aR") + 0.8)), 2));

		environment.get("REGIONAL")
				.put("fIntercept.aRS.s1RS",
						0.3 * (environment.get("REGIONAL").get("AREAFRACveg.s1R") * (inputEng.getSubstancesData("RadS")
								/ (inputEng.getSubstancesData("RadS") + environment.get("REGIONAL").get("veghair.s1R")))
								+ (1 - environment.get("REGIONAL").get("AREAFRACveg.s1R"))
										* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
												+ environment.get("REGIONAL").get("largevegradius.s1R")))));

		environment.get("REGIONAL").put("fBrown.aRS.s1RS",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberS.aR"), -2. / 3.));

		environment.get("REGIONAL").put("SURFresist.s1RS",
				1 / (environment.get("REGIONAL").get("FRICvelocity.s1R")
						* (environment.get("REGIONAL").get("fBrown.aRS.s1RS")
								+ environment.get("REGIONAL").get("fIntercept.aRS.s1RS")
								+ environment.get("REGIONAL").get("fGrav.aRS.s1RS"))));

		environment.get("REGIONAL").put("DEPvelocity.aRS.s1RS",
				1. / (environment.get("REGIONAL").get("AEROresist.s1R")
						+ environment.get("REGIONAL").get("SURFresist.s1RS"))
						+ environment.get("REGIONAL").get("SettvelS.aR"));

		environment.get("REGIONAL").put("k.aRS.s1RS", (environment.get("REGIONAL").get("DEPvelocity.aRS.s1RS")
				* environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("AEROSOLdeprate.R", 0.001);

		environment.get("REGIONAL").put("k.aRP.s1RP",
				(environment.get("REGIONAL").get("AEROSOLdeprate.R") * environment.get("REGIONAL").get("AREAFRAC.s1R")
						* environment.get("REGIONAL").get("SYSTEMAREA.R"))
						/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("k.aRP.s2RP",
				(environment.get("REGIONAL").get("AEROSOLdeprate.R") * environment.get("REGIONAL").get("AREAFRAC.s2R")
						* environment.get("REGIONAL").get("SYSTEMAREA.R"))
						/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("k.aRP.s3RP",
				(environment.get("REGIONAL").get("AEROSOLdeprate.R") * environment.get("REGIONAL").get("AREAFRAC.s3R")
						* environment.get("REGIONAL").get("SYSTEMAREA.R"))
						/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRS.w",
				Math.pow(10, (-3. / environment.get("REGIONAL").get("StokesNumberS.aR"))));

		environment.get("REGIONAL").put("fBrown.aRS.w",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberS.aR"), -1. / 2.));

		environment.get("REGIONAL").put("FRICvelocity.wR", 0.19);

		environment.get("REGIONAL").put("SURFresist.wRS", 1 / (environment.get("REGIONAL").get("FRICvelocity.wR")
				* (environment.get("REGIONAL").get("fBrown.aRS.w") + environment.get("REGIONAL").get("fGrav.aRS.w"))));

		environment.get("REGIONAL").put("AEROresist.w0R", 74.);

		environment.get("REGIONAL").put("DEPvelocity.aRS.w0RS",
				1. / (environment.get("REGIONAL").get("AEROresist.w0R")
						+ environment.get("REGIONAL").get("SURFresist.wRS"))
						+ environment.get("REGIONAL").get("SettvelS.aR"));

		environment.get("REGIONAL").put("k.aRS.w0RS", (environment.get("REGIONAL").get("DEPvelocity.aRS.w0RS")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("AEROresist.w1R", 74.);

		environment.get("REGIONAL").put("DEPvelocity.aRS.w1RS",
				1. / (environment.get("REGIONAL").get("AEROresist.w1R")
						+ environment.get("REGIONAL").get("SURFresist.wRS"))
						+ environment.get("REGIONAL").get("SettvelS.aR"));

		environment.get("REGIONAL").put("k.aRS.w1RS", (environment.get("REGIONAL").get("DEPvelocity.aRS.w1RS")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("AEROresist.w2R", 135.);

		environment.get("REGIONAL").put("DEPvelocity.aRS.w2RS",
				1. / (environment.get("REGIONAL").get("AEROresist.w2R")
						+ environment.get("REGIONAL").get("SURFresist.wRS"))
						+ environment.get("REGIONAL").get("SettvelS.aR"));

		environment.get("REGIONAL").put("k.aRS.w2RS", (environment.get("REGIONAL").get("DEPvelocity.aRS.w2RS")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("fGrav.aRA.w",
				Math.pow(10, (-3. / environment.get("REGIONAL").get("StokesNumberA.aR"))));

		environment.get("REGIONAL").put("fBrown.aRA.w",
				Math.pow(environment.get("REGIONAL").get("SchmidtNumberA.aR"), -1. / 2.));

		environment.get("REGIONAL").put("SURFresist.wRA", 1 / (environment.get("REGIONAL").get("FRICvelocity.wR")
				* (environment.get("REGIONAL").get("fBrown.aRA.w") + environment.get("REGIONAL").get("fGrav.aRA.w"))));

		environment.get("REGIONAL").put("DEPvelocity.aRA.w0RA",
				1. / (environment.get("REGIONAL").get("AEROresist.w0R")
						+ environment.get("REGIONAL").get("SURFresist.wRA"))
						+ environment.get("REGIONAL").get("SettvelA.aR"));

		environment.get("REGIONAL").put("k.aRA.w0RA", (environment.get("REGIONAL").get("DEPvelocity.aRA.w0RA")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("DEPvelocity.aRA.w1RA",
				1. / (environment.get("REGIONAL").get("AEROresist.w1R")
						+ environment.get("REGIONAL").get("SURFresist.wRA"))
						+ environment.get("REGIONAL").get("SettvelA.aR"));

		environment.get("REGIONAL").put("k.aRA.w1RA", (environment.get("REGIONAL").get("DEPvelocity.aRA.w1RA")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("DEPvelocity.aRA.w2RA",
				1. / (environment.get("REGIONAL").get("AEROresist.w2R")
						+ environment.get("REGIONAL").get("SURFresist.wRA"))
						+ environment.get("REGIONAL").get("SettvelA.aR"));

		environment.get("REGIONAL").put("k.aRA.w2RA", (environment.get("REGIONAL").get("DEPvelocity.aRA.w2RA")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ (environment.get("REGIONAL").get("VOLUME.aR")));

		environment.get("REGIONAL").put("k.aRP.w0RP",
				(environment.get("REGIONAL").get("AEROSOLdeprate.R") * environment.get("REGIONAL").get("AREAFRAC.w0R")
						* environment.get("REGIONAL").get("SYSTEMAREA.R"))
						/ environment.get("REGIONAL").get("VOLUME.aR"));

		environment.get("REGIONAL").put("k.aRP.w1RP",
				(environment.get("REGIONAL").get("AEROSOLdeprate.R") * environment.get("REGIONAL").get("AREAFRAC.w1R")
						* environment.get("REGIONAL").get("SYSTEMAREA.R"))
						/ environment.get("REGIONAL").get("VOLUME.aR"));

		environment.get("REGIONAL").put("k.aRP.w2RP",
				(environment.get("REGIONAL").get("AEROSOLdeprate.R") * environment.get("REGIONAL").get("AREAFRAC.w2R")
						* environment.get("REGIONAL").get("SYSTEMAREA.R"))
						/ environment.get("REGIONAL").get("VOLUME.aR"));

		environment.get("REGIONAL").put("k.cwRS.s1RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.s2RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.s3RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRA.s1RA", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRA.s2RA", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRA.s3RA", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRP.s1RP", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRP.s2RP", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRP.s3RP", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.s3R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.w0RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.w1RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.w2RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.w0RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.w1RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRS.w2RS", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRA.w0RA", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRA.w1RA", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRA.w2RA", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRP.w0RP", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w0R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRP.w1RP", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w1R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		environment.get("REGIONAL").put("k.cwRP.w2RP", (inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				* environment.get("REGIONAL").get("AREAFRAC.w2R") * environment.get("REGIONAL").get("SYSTEMAREA.R"))
				/ environment.get("REGIONAL").get("VOLUME.cwR"));

		if (inputEng.nanoProperties.get("SetVelS.w") / environment.get("REGIONAL").get("DEPTH.w0R") > 0)
			environment.get("REGIONAL").put("k.w0RS.sd0RS",
					inputEng.nanoProperties.get("SetVelS.w") / environment.get("REGIONAL").get("DEPTH.w0R"));
		else
			environment.get("REGIONAL").put("k.w0RS.sd0RS", 0.);

		if (inputEng.nanoProperties.get("SetVelS.w") / environment.get("REGIONAL").get("DEPTH.w1R") > 0)
			environment.get("REGIONAL").put("k.w1RS.sd1RS",
					inputEng.nanoProperties.get("SetVelS.w") / environment.get("REGIONAL").get("DEPTH.w1R"));
		else
			environment.get("REGIONAL").put("k.w1RS.sd1RS", 0.);

		if (inputEng.nanoProperties.get("SetVelS.w") / environment.get("REGIONAL").get("DEPTH.w2R") > 0)
			environment.get("REGIONAL").put("k.w2RS.sd2RS",
					inputEng.nanoProperties.get("SetVelS.w") / environment.get("REGIONAL").get("DEPTH.w2R"));
		else
			environment.get("REGIONAL").put("k.w2RS.sd2RS", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") / environment.get("REGIONAL").get("DEPTH.w0R") > 0)
			environment.get("REGIONAL").put("k.w0RA.sd0RA",
					inputEng.nanoProperties.get("SetVelA.w") / environment.get("REGIONAL").get("DEPTH.w0R"));
		else
			environment.get("REGIONAL").put("k.w0RA.sd0RA", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") / environment.get("REGIONAL").get("DEPTH.w1R") > 0)
			environment.get("REGIONAL").put("k.w1RA.sd1RA",
					inputEng.nanoProperties.get("SetVelA.w") / environment.get("REGIONAL").get("DEPTH.w1R"));
		else
			environment.get("REGIONAL").put("k.w1RA.sd0RA", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") / environment.get("REGIONAL").get("DEPTH.w2R") > 0)
			environment.get("REGIONAL").put("k.w2RA.sd2RA",
					inputEng.nanoProperties.get("SetVelA.w") / environment.get("REGIONAL").get("DEPTH.w2R"));
		else
			environment.get("REGIONAL").put("k.w2RA.sd2RA", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") / environment.get("REGIONAL").get("DEPTH.w0R") > 0)
			environment.get("REGIONAL").put("k.w0RP.sd0RP",
					inputEng.nanoProperties.get("SetVelP.w") / environment.get("REGIONAL").get("DEPTH.w0R"));
		else
			environment.get("REGIONAL").put("k.w0RP.sd0RP", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") / environment.get("REGIONAL").get("DEPTH.w1R") > 0)
			environment.get("REGIONAL").put("k.w1RP.sd1RP",
					inputEng.nanoProperties.get("SetVelP.w") / environment.get("REGIONAL").get("DEPTH.w1R"));
		else
			environment.get("REGIONAL").put("k.w1RP.sd1RP", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") / environment.get("REGIONAL").get("DEPTH.w2R") > 0)
			environment.get("REGIONAL").put("k.w2RP.sd2RP",
					inputEng.nanoProperties.get("SetVelP.w") / environment.get("REGIONAL").get("DEPTH.w2R"));
		else
			environment.get("REGIONAL").put("k.w2RP.sd2RP", 0.);

		environment.get("REGIONAL").put("k.sd0RS.w0RS",
				environment.get("REGIONAL").get("RESUSP.sd0R.w0R") / environment.get("REGIONAL").get("DEPTH.sd0R"));

		environment.get("REGIONAL").put("k.sd1RS.w1RS",
				environment.get("REGIONAL").get("RESUSP.sd1R.w1R") / environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("k.sd2RS.w2RS",
				environment.get("REGIONAL").get("RESUSP.sd2R.w2R") / environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("k.sd0RA.w0RA",
				environment.get("REGIONAL").get("RESUSP.sd0R.w0R") / environment.get("REGIONAL").get("DEPTH.sd0R"));

		environment.get("REGIONAL").put("k.sd1RA.w1RA",
				environment.get("REGIONAL").get("RESUSP.sd1R.w1R") / environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("k.sd2RA.w2RA",
				environment.get("REGIONAL").get("RESUSP.sd2R.w2R") / environment.get("REGIONAL").get("DEPTH.sd2R"));

		environment.get("REGIONAL").put("k.sd0RP.w0RP",
				environment.get("REGIONAL").get("RESUSP.sd0R.w0R") / environment.get("REGIONAL").get("DEPTH.sd0R"));

		environment.get("REGIONAL").put("k.sd1RP.w1RP",
				environment.get("REGIONAL").get("RESUSP.sd1R.w1R") / environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("k.sd2RP.w2RP",
				environment.get("REGIONAL").get("RESUSP.sd2R.w2R") / environment.get("REGIONAL").get("DEPTH.sd2R"));

		environment.get("REGIONAL").put("k.s1RS.w1RS",
				((environment.get("REGIONAL").get("FRACrun.s1R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRrunoff.s1R")
						+ environment.get("REGIONAL").get("EROSION.s1R"))
						/ environment.get("REGIONAL").get("DEPTH.s1R")));

		environment.get("REGIONAL").put("k.s2RS.w1RS",
				((environment.get("REGIONAL").get("FRACrun.s2R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRrunoff.s2R")
						+ environment.get("REGIONAL").get("EROSION.s2R"))
						/ environment.get("REGIONAL").get("DEPTH.s2R")));

		environment.get("REGIONAL").put("k.s3RS.w1RS",
				((environment.get("REGIONAL").get("FRACrun.s3R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRrunoff.s3R")
						+ environment.get("REGIONAL").get("EROSION.s3R"))
						/ environment.get("REGIONAL").get("DEPTH.s3R")));

		environment.get("REGIONAL").put("k.s1RA.w1RA",
				((environment.get("REGIONAL").get("FRACrun.s1R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRrunoff.s1R")
						+ environment.get("REGIONAL").get("EROSION.s1R"))
						/ environment.get("REGIONAL").get("DEPTH.s1R")));

		environment.get("REGIONAL").put("k.s2RA.w1RA",
				((environment.get("REGIONAL").get("FRACrun.s2R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRrunoff.s2R")
						+ environment.get("REGIONAL").get("EROSION.s2R"))
						/ environment.get("REGIONAL").get("DEPTH.s2R")));

		environment.get("REGIONAL").put("k.s3RA.w1RA",
				((environment.get("REGIONAL").get("FRACrun.s3R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRrunoff.s3R")
						+ environment.get("REGIONAL").get("EROSION.s3R"))
						/ environment.get("REGIONAL").get("DEPTH.s3R")));

		environment.get("REGIONAL").put("k.s1RP.w1RP",
				(environment.get("REGIONAL").get("EROSION.s1R") / environment.get("REGIONAL").get("DEPTH.s1R")));

		environment.get("REGIONAL").put("k.s2RP.w1RP",
				(environment.get("REGIONAL").get("EROSION.s2R") / environment.get("REGIONAL").get("DEPTH.s2R")));

		environment.get("REGIONAL").put("k.s3RP.w1RP",
				(environment.get("REGIONAL").get("EROSION.s3R") / environment.get("REGIONAL").get("DEPTH.s3R")));

		environment.get("REGIONAL").put("kesc.aR", Math.log(2) / (60. * 365. * 24. * 3600.));

		environment.get("REGIONAL").put("k.aRS",
				environment.get("REGIONAL").get("kesc.aR") + inputEng.getSubstancesData("kdegS.a"));

		environment.get("REGIONAL").put("k.aRP",
				environment.get("REGIONAL").get("kesc.aR") + inputEng.getSubstancesData("kdegP.a"));

		environment.get("REGIONAL").put("k.w0RS", inputEng.getSubstancesData("kdegS.w0"));

		environment.get("REGIONAL").put("k.w0RA", inputEng.getSubstancesData("kdegA.w0"));

		environment.get("REGIONAL").put("k.w0RP", inputEng.getSubstancesData("kdegP.w0"));

		environment.get("REGIONAL").put("k.w1RS", inputEng.getSubstancesData("kdegS.w1"));

		environment.get("REGIONAL").put("k.w1RA", inputEng.getSubstancesData("kdegA.w1"));

		environment.get("REGIONAL").put("k.w1RP", inputEng.getSubstancesData("kdegP.w1"));

		environment.get("REGIONAL").put("k.w2RS", inputEng.getSubstancesData("kdegS.w2"));

		environment.get("REGIONAL").put("k.w2RP", inputEng.getSubstancesData("kdegP.w2"));

		environment.get("REGIONAL").put("kbur.sd0R",
				environment.get("REGIONAL").get("NETsedrate.w0R") / environment.get("REGIONAL").get("DEPTH.sd0R"));

		environment.get("REGIONAL").put("kbur.sd1R",
				environment.get("REGIONAL").get("NETsedrate.w1R") / environment.get("REGIONAL").get("DEPTH.sd1R"));

		environment.get("REGIONAL").put("kbur.sd2R",
				environment.get("REGIONAL").get("NETsedrate.w2R") / environment.get("REGIONAL").get("DEPTH.sd2R"));

		environment.get("REGIONAL").put("kleach.s1R",
				((environment.get("REGIONAL").get("FRACinf.s1R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRleach.s1R"))
						/ environment.get("REGIONAL").get("DEPTH.s1R")));

		environment.get("REGIONAL").put("kleach.s2R",
				((environment.get("REGIONAL").get("FRACinf.s2R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRleach.s2R"))
						/ environment.get("REGIONAL").get("DEPTH.s2R")));

		environment.get("REGIONAL").put("kleach.s3R",
				((environment.get("REGIONAL").get("FRACinf.s3R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("CORRleach.s3R"))
						/ environment.get("REGIONAL").get("DEPTH.s3R")));

		environment.get("REGIONAL").put("k.sd0RS",
				environment.get("REGIONAL").get("kbur.sd0R") + inputEng.getSubstancesData("kdegS.sd0"));

		environment.get("REGIONAL").put("k.sd0RA",
				environment.get("REGIONAL").get("kbur.sd0R") + inputEng.getSubstancesData("kdegA.sd0"));

		environment.get("REGIONAL").put("k.sd0RP",
				environment.get("REGIONAL").get("kbur.sd0R") + inputEng.getSubstancesData("kdegP.sd0"));

		environment.get("REGIONAL").put("k.sd1RS",
				environment.get("REGIONAL").get("kbur.sd1R") + inputEng.getSubstancesData("kdegS.sd1"));

		environment.get("REGIONAL").put("k.sd1RA",
				environment.get("REGIONAL").get("kbur.sd1R") + inputEng.getSubstancesData("kdegA.sd1"));

		environment.get("REGIONAL").put("k.sd1RP",
				environment.get("REGIONAL").get("kbur.sd1R") + inputEng.getSubstancesData("kdegP.sd1"));

		environment.get("REGIONAL").put("k.sd2RS",
				environment.get("REGIONAL").get("kbur.sd2R") + inputEng.getSubstancesData("kdegS.sd2"));

		environment.get("REGIONAL").put("k.sd2RA",
				environment.get("REGIONAL").get("kbur.sd2R") + inputEng.getSubstancesData("kdegA.sd2"));

		environment.get("REGIONAL").put("k.sd2RP",
				environment.get("REGIONAL").get("kbur.sd2R") + inputEng.getSubstancesData("kdegP.sd2"));

		environment.get("REGIONAL").put("k.s1RS",
				environment.get("REGIONAL").get("kleach.s1R") + inputEng.getSubstancesData("kdegS.s1"));

		environment.get("REGIONAL").put("k.s1RA",
				environment.get("REGIONAL").get("kleach.s1R") + inputEng.getSubstancesData("kdegA.s1"));

		environment.get("REGIONAL").put("k.s1RP", inputEng.getSubstancesData("kdegP.s1"));

		environment.get("REGIONAL").put("k.s2RS",
				environment.get("REGIONAL").get("kleach.s2R") + inputEng.getSubstancesData("kdegS.s2"));

		environment.get("REGIONAL").put("k.s2RA",
				environment.get("REGIONAL").get("kleach.s2R") + inputEng.getSubstancesData("kdegA.s2"));

		environment.get("REGIONAL").put("k.s2RP", inputEng.getSubstancesData("kdegP.s2"));

		environment.get("REGIONAL").put("k.s3RS",
				environment.get("REGIONAL").get("kleach.s3R") + inputEng.getSubstancesData("kdegS.s3"));

		environment.get("REGIONAL").put("k.s3RA",
				environment.get("REGIONAL").get("kleach.s3R") + inputEng.getSubstancesData("kdegA.s3"));

		environment.get("REGIONAL").put("k.s3RP", inputEng.getSubstancesData("kdegP.s3"));

		environment.get("REGIONAL").put("Tempfactor.aR", Math.exp((inputEng.getSubstancesData("Ea.OHrad") / 8.314)
				* (inputEng.getLandscapeSettings("REGIONAL", "TEMP.R") - 298.) / Math.pow(298, 2)));

		environment.get("REGIONAL").put("C.OHrad.aR", 500000.);

		environment.get("REGIONAL").put("KDEG.aR",
				environment.get("REGIONAL").get("FRgas.aR") * inputEng.getSubstancesData("kdegG.air")
						* (environment.get("REGIONAL").get("C.OHrad.aR") / inputEng.getSubstancesData("C.OHrad"))
						* environment.get("REGIONAL").get("Tempfactor.aR"));

		environment.get("REGIONAL").put("K.aRG",
				environment.get("REGIONAL").get("kesc.aR") + environment.get("REGIONAL").get("KDEG.aR"));

		environment.get("REGIONAL").put("CORRvolat.s1R",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")))));
		environment.get("REGIONAL").put("CORRvolat.s2R",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")))));
		environment.get("REGIONAL").put("CORRvolat.s3R",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")))));

		environment.get("REGIONAL").put("MTCas.soil.sR", 0.1 * environment.get("REGIONAL").get("KDEG.s1R"));
		environment.get("REGIONAL").put("MTCas.air.aR", 0.43 / (24. * 3600.) / 0.00475);

		environment.get("REGIONAL").put("VOLAT.s1R.aR", (environment.get("REGIONAL").get("MTCas.air.aR")
				* environment.get("REGIONAL").get("MTCas.soil.sR"))
				/ (environment.get("REGIONAL").get("MTCas.air.aR") + environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ((environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s1w"))
								/ environment.get("REGIONAL").get("Ks1w.R")))
				* environment.get("REGIONAL").get("CORRvolat.s1R"));

		environment.get("REGIONAL").put("VOLAT.s2R.aR", (environment.get("REGIONAL").get("MTCas.air.aR")
				* environment.get("REGIONAL").get("MTCas.soil.sR"))
				/ (environment.get("REGIONAL").get("MTCas.air.aR") + environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ((environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s2w"))
								/ environment.get("REGIONAL").get("Ks2w.R")))
				* environment.get("REGIONAL").get("CORRvolat.s2R"));

		environment.get("REGIONAL").put("VOLAT.s3R.aR", (environment.get("REGIONAL").get("MTCas.air.aR")
				* environment.get("REGIONAL").get("MTCas.soil.sR"))
				/ (environment.get("REGIONAL").get("MTCas.air.aR") + environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ((environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s3w"))
								/ environment.get("REGIONAL").get("Ks2w.R")))
				* environment.get("REGIONAL").get("CORRvolat.s3R"));

		environment.get("REGIONAL").put("MTCaw.air.aR",
				0.01 * (0.3 + 0.2 * inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R"))
						* (Math.pow(0.018 / inputEng.getSubstancesData("Molweight"), 0.67 * 0.5)));

		environment.get("REGIONAL").put("MTCaw.water.wR",
				0.01 * (0.0004 + 0.00004 * inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R")
						* inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R"))
						* (Math.pow(0.032 / inputEng.getSubstancesData("Molweight"), 0.5 * 0.5)));

		environment.get("REGIONAL").put("VOLAT.w0R.aR",
				(environment.get("REGIONAL").get("MTCaw.air.aR") * environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR")))
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
						* environment.get("REGIONAL").get("FRwD.w0R"));

		environment.get("REGIONAL").put("VOLAT.w1R.aR",
				(environment.get("REGIONAL").get("MTCaw.air.aR") * environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR")))
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
						* environment.get("REGIONAL").get("FRwD.w1R"));

		environment.get("REGIONAL").put("VOLAT.w2R.aR",
				(environment.get("REGIONAL").get("MTCaw.air.aR") * environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w2"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR")))
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w2"))
						* environment.get("REGIONAL").get("FRwD.w2R"));

		environment.get("REGIONAL").put("k.s1RD.aRG",
				environment.get("REGIONAL").get("VOLAT.s1R.aR") / environment.get("REGIONAL").get("DEPTH.s1R"));

		environment.get("REGIONAL").put("k.s2RD.aRG",
				environment.get("REGIONAL").get("VOLAT.s2R.aR") / environment.get("REGIONAL").get("DEPTH.s2R"));

		environment.get("REGIONAL").put("k.s3RD.aRG",
				environment.get("REGIONAL").get("VOLAT.s3R.aR") / environment.get("REGIONAL").get("DEPTH.s3R"));

		environment.get("REGIONAL").put("k.w0RD.aRG",
				environment.get("REGIONAL").get("VOLAT.w0R.aR") / environment.get("REGIONAL").get("DEPTH.w0R"));

		environment.get("REGIONAL").put("k.w1RD.aRG",
				environment.get("REGIONAL").get("VOLAT.w1R.aR") / environment.get("REGIONAL").get("DEPTH.w1R"));

		environment.get("REGIONAL").put("k.w2RD.aRG",
				environment.get("REGIONAL").get("VOLAT.w2R.aR") / environment.get("REGIONAL").get("DEPTH.w2R"));

		environment.get("REGIONAL").put("GASABS.aR.wR",
				environment.get("REGIONAL").get("FRgas.aR") * (environment.get("REGIONAL").get("MTCaw.air.aR")
						* environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR"))));

		environment.get("REGIONAL").put("GASABS.aR.sR",
				environment.get("REGIONAL").get("FRgas.aR") * environment.get("REGIONAL").get("MTCas.air.aR")
						* environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ( environment.get("REGIONAL").get("MTCas.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1")/ environment.get("REGIONAL").get("Ks1w.R") ) 
								+ environment.get("REGIONAL").get("MTCas.soil.sR") ) );
		
		
		environment.get("REGIONAL").put("GasWashout.RG", environment.get("REGIONAL").get("FRgas.aR")
				* (environment.get("REGIONAL").get("tdry.R") + environment.get("REGIONAL").get("twet.R"))
				/ environment.get("REGIONAL").get("twet.R") * inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
				/ (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("REGIONAL").put("AerosolWashout.R",
				environment.get("REGIONAL").get("FRaers.aR")
						* (environment.get("REGIONAL").get("tdry.R") + environment.get("REGIONAL").get("twet.R"))
						/ environment.get("REGIONAL").get("twet.R") * environment.get("REGIONAL").get("COLLECTeff.R")
						* inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R"));

		environment.get("REGIONAL").put("DRYDEPaerosol.R", environment.get("REGIONAL").get("AEROSOLdeprate.R")
				* (environment.get("REGIONAL").get("FRaerw.aR") + environment.get("REGIONAL").get("FRaers.aR")));

		environment.get("REGIONAL").put("kwet.R",
				(environment.get("REGIONAL").get("AerosolWashout.R") + environment.get("REGIONAL").get("GasWashout.RG"))
						/ environment.get("REGIONAL").get("HEIGHT.aR")
						+ (environment.get("REGIONAL").get("GASABS.aR.wR")
								* (environment.get("REGIONAL").get("AREAFRAC.w0R")
										+ environment.get("REGIONAL").get("AREAFRAC.w1R")
										+ environment.get("REGIONAL").get("AREAFRAC.w2R"))
								+ environment.get("REGIONAL").get("GASABS.aR.sR")
										* (environment.get("REGIONAL").get("AREAFRAC.s1R")
												+ environment.get("REGIONAL").get("AREAFRAC.s2R")
												+ environment.get("REGIONAL").get("AREAFRAC.s3R")))
								/ environment.get("REGIONAL").get("HEIGHT.aR")
						+ environment.get("REGIONAL").get("KDEG.aR") + environment.get("REGIONAL").get("k.aR.aC"));

		environment.get("REGIONAL").put("kdry.R",
				environment.get("REGIONAL").get("DRYDEPaerosol.R") / environment.get("REGIONAL").get("HEIGHT.aR")
						+ (environment.get("REGIONAL").get("GASABS.aR.wR")
								* (environment.get("REGIONAL").get("AREAFRAC.w0R")
										+ environment.get("REGIONAL").get("AREAFRAC.w1R")
										+ environment.get("REGIONAL").get("AREAFRAC.w2R"))
								+ environment.get("REGIONAL").get("GASABS.aR.sR")
										* (environment.get("REGIONAL").get("AREAFRAC.s1R")
												+ environment.get("REGIONAL").get("AREAFRAC.s2R")
												+ environment.get("REGIONAL").get("AREAFRAC.s3R")))
								/ environment.get("REGIONAL").get("HEIGHT.aR")
						+ environment.get("REGIONAL").get("KDEG.aR") + environment.get("REGIONAL").get("k.aR.aC"));

		environment.get("REGIONAL").put("MeanRem.aRG", 1. / ((1. / environment.get("REGIONAL").get("kdry.R"))
				* environment.get("REGIONAL").get("tdry.R")
				/ (environment.get("REGIONAL").get("tdry.R") + environment.get("REGIONAL").get("twet.R"))
				+ (1. / environment.get("REGIONAL").get("kwet.R")) * environment.get("REGIONAL").get("twet.R")
						/ (environment.get("REGIONAL").get("tdry.R") + environment.get("REGIONAL").get("twet.R"))
				- (Math.pow(
						1. / environment.get("REGIONAL").get("kwet.R") - 1. / environment.get("REGIONAL").get("kdry.R"),
						2) / (environment.get("REGIONAL").get("tdry.R") + environment.get("REGIONAL").get("twet.R")))
						* (1. - Math.exp(
								-environment.get("REGIONAL").get("kdry.R") * environment.get("REGIONAL").get("tdry.R")))
						* (1. - Math.exp(
								-environment.get("REGIONAL").get("kwet.R") * environment.get("REGIONAL").get("twet.R")))
						/ (1. - Math.exp(
								-environment.get("REGIONAL").get("kdry.R") * environment.get("REGIONAL").get("tdry.R")
										- environment.get("REGIONAL").get("kwet.R")
												* environment.get("REGIONAL").get("twet.R")))));

		double val1 = environment.get("REGIONAL").get("MeanRem.aRG");
		double val2 = (environment.get("REGIONAL").get("GASABS.aR.wR")
				* (environment.get("REGIONAL").get("AREAFRAC.w0R") + environment.get("REGIONAL").get("AREAFRAC.w1R")
						+ environment.get("REGIONAL").get("AREAFRAC.w2R"))
				+ environment.get("REGIONAL").get("GASABS.aR.sR") * (environment.get("REGIONAL").get("AREAFRAC.s1R")
						+ environment.get("REGIONAL").get("AREAFRAC.s2R")
						+ environment.get("REGIONAL").get("AREAFRAC.s3R")))
				/ environment.get("REGIONAL").get("HEIGHT.aR") + environment.get("REGIONAL").get("KDEG.aR")
				+ environment.get("REGIONAL").get("k.aR.aC");

		environment.get("REGIONAL").put("MeanDep.R", val1 - val2);

		environment.get("REGIONAL").put("k.aRG.w0RD",
				(environment.get("REGIONAL").get("MeanDep.R") + environment.get("REGIONAL").get("GASABS.aR.wR")
						/ environment.get("REGIONAL").get("HEIGHT.aR"))
						* environment.get("REGIONAL").get("AREAFRAC.w0R"));

		environment.get("REGIONAL").put("k.aRG.w1RD",
				(environment.get("REGIONAL").get("MeanDep.R") + environment.get("REGIONAL").get("GASABS.aR.wR")
						/ environment.get("REGIONAL").get("HEIGHT.aR"))
						* environment.get("REGIONAL").get("AREAFRAC.w1R"));

		environment.get("REGIONAL").put("k.aRG.w2RD",
				(environment.get("REGIONAL").get("MeanDep.R") + environment.get("REGIONAL").get("GASABS.aR.wR")
						/ environment.get("REGIONAL").get("HEIGHT.aR"))
						* environment.get("REGIONAL").get("AREAFRAC.w2R"));

		environment.get("REGIONAL").put("k.aRG.s1RD",
				(environment.get("REGIONAL").get("MeanDep.R") + environment.get("REGIONAL").get("GASABS.aR.sR")
						/ environment.get("REGIONAL").get("HEIGHT.aR"))
						* environment.get("REGIONAL").get("AREAFRAC.s1R"));

		environment.get("REGIONAL").put("k.aRG.s2RD",
				(environment.get("REGIONAL").get("MeanDep.R") + environment.get("REGIONAL").get("GASABS.aR.sR")
						/ environment.get("REGIONAL").get("HEIGHT.aR"))
						* environment.get("REGIONAL").get("AREAFRAC.s2R"));

		environment.get("REGIONAL").put("k.aRG.s3RD",
				(environment.get("REGIONAL").get("MeanDep.R") + environment.get("REGIONAL").get("GASABS.aR.sR")
						/ environment.get("REGIONAL").get("HEIGHT.aR"))
						* environment.get("REGIONAL").get("AREAFRAC.s3R"));

		environment.get("REGIONAL").put("CORRvolat.s1R",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s1R")))));
		environment.get("REGIONAL").put("CORRvolat.s2R",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s2R")))));
		environment.get("REGIONAL").put("CORRvolat.s3R",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("REGIONAL").get("DEPTH.s3R")))));

		environment.get("REGIONAL").put("MTCas.soil.sR", 0.1 * environment.get("REGIONAL").get("KDEG.s1R"));
		environment.get("REGIONAL").put("MTCas.air.aR", 0.43 / (24. * 3600.) / 0.00475);

		environment.get("REGIONAL").put("VOLAT.s1R.aR", (environment.get("REGIONAL").get("MTCas.air.aR")
				* environment.get("REGIONAL").get("MTCas.soil.sR"))
				/ (environment.get("REGIONAL").get("MTCas.air.aR") + environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ((environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s1w"))
								/ environment.get("REGIONAL").get("Ks1w.R")))
				* environment.get("REGIONAL").get("CORRvolat.s1R"));

		environment.get("REGIONAL").put("VOLAT.s2R.aR", (environment.get("REGIONAL").get("MTCas.air.aR")
				* environment.get("REGIONAL").get("MTCas.soil.sR"))
				/ (environment.get("REGIONAL").get("MTCas.air.aR") + environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ((environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s2w"))
								/ environment.get("REGIONAL").get("Ks2w.R")))
				* environment.get("REGIONAL").get("CORRvolat.s2R"));

		environment.get("REGIONAL").put("VOLAT.s3R.aR", (environment.get("REGIONAL").get("MTCas.air.aR")
				* environment.get("REGIONAL").get("MTCas.soil.sR"))
				/ (environment.get("REGIONAL").get("MTCas.air.aR") + environment.get("REGIONAL").get("MTCas.soil.sR")
						/ ((environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s3w"))
								/ environment.get("REGIONAL").get("Ks2w.R")))
				* environment.get("REGIONAL").get("CORRvolat.s3R"));

		environment.get("REGIONAL").put("MTCaw.air.aR",
				0.01 * (0.3 + 0.2 * inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R"))
						* (Math.pow(0.018 / inputEng.getSubstancesData("Molweight"), 0.67 * 0.5)));

		environment.get("REGIONAL").put("MTCaw.water.wR",
				0.01 * (0.0004 + 0.00004 * inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R")
						* inputEng.getLandscapeSettings("REGIONAL", "WINDspeed.R"))
						* (Math.pow(0.032 / inputEng.getSubstancesData("Molweight"), 0.5 * 0.5)));

		environment.get("REGIONAL").put("VOLAT.w0R.aR",
				(environment.get("REGIONAL").get("MTCaw.air.aR") * environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR")))
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
						* environment.get("REGIONAL").get("FRwD.w0R"));

		environment.get("REGIONAL").put("VOLAT.w1R.aR",
				(environment.get("REGIONAL").get("MTCaw.air.aR") * environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR")))
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w1"))
						* environment.get("REGIONAL").get("FRwD.w1R"));

		environment.get("REGIONAL").put("VOLAT.w2R.aR",
				(environment.get("REGIONAL").get("MTCaw.air.aR") * environment.get("REGIONAL").get("MTCaw.water.wR")
						/ (environment.get("REGIONAL").get("MTCaw.air.aR")
								* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w2"))
								+ environment.get("REGIONAL").get("MTCaw.water.wR")))
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.w2"))
						* environment.get("REGIONAL").get("FRwD.w2R"));

		environment.get("REGIONAL").put("k.s1RD.aRG",
				environment.get("REGIONAL").get("VOLAT.s1R.aR") / environment.get("REGIONAL").get("DEPTH.s1R"));

		environment.get("REGIONAL").put("k.s2RD.aRG",
				environment.get("REGIONAL").get("VOLAT.s2R.aR") / environment.get("REGIONAL").get("DEPTH.s2R"));

		environment.get("REGIONAL").put("k.s3RD.aRG",
				environment.get("REGIONAL").get("VOLAT.s3R.aR") / environment.get("REGIONAL").get("DEPTH.s3R"));

		environment.get("REGIONAL").put("k.w0RD.aRG",
				environment.get("REGIONAL").get("VOLAT.w0R.aR") / environment.get("REGIONAL").get("DEPTH.w0R"));

		environment.get("REGIONAL").put("k.w1RD.aRG",
				environment.get("REGIONAL").get("VOLAT.w1R.aR") / environment.get("REGIONAL").get("DEPTH.w1R"));

		environment.get("REGIONAL").put("k.w2RD.aRG",
				environment.get("REGIONAL").get("VOLAT.w2R.aR") / environment.get("REGIONAL").get("DEPTH.w2R"));

		environment.get("REGIONAL").put("k.s1RD.w1RD",
				(inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("FRACrun.s1R") / environment.get("REGIONAL").get("Ks1w.R")
						+ environment.get("REGIONAL").get("EROSION.s1R"))
						* environment.get("REGIONAL").get("CORRrunoff.s1R")
						/ environment.get("REGIONAL").get("DEPTH.s1R"));

		environment.get("REGIONAL").put("k.s2RD.w1RD",
				(inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("FRACrun.s2R") / environment.get("REGIONAL").get("Ks2w.R")
						+ environment.get("REGIONAL").get("EROSION.s2R"))
						* environment.get("REGIONAL").get("CORRrunoff.s2R")
						/ environment.get("REGIONAL").get("DEPTH.s2R"));

		environment.get("REGIONAL").put("k.s3RD.w1RD",
				(inputEng.getLandscapeSettings("REGIONAL", "RAINrate.R")
						* environment.get("REGIONAL").get("FRACrun.s3R") / environment.get("REGIONAL").get("Ks3w.R")
						+ environment.get("REGIONAL").get("EROSION.s3R"))
						* environment.get("REGIONAL").get("CORRrunoff.s3R")
						/ environment.get("REGIONAL").get("DEPTH.s3R"));
	}

	void mixedContinental() {
		// For here are the CONTINENTAL
		environment.get("CONTINENTAL").put("CORG.s3C", 0.02);

		environment.get("CONTINENTAL").put("Kaw.C",
				inputEng.getSubstancesData("Kaw") * (Math
						.exp((inputEng.getSubstancesData("H0vap") / 8.314)
								* ((1. / 298.) - 1. / inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")))
						* Math.exp(-(inputEng.getSubstancesData("H0sol") / 8.314)
								* ((1. / 298.) - 1. / inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")))
						* (298 / inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))));

		environment.get("CONTINENTAL").put("Kp.s3C",
				(inputEng.getSubstancesData("FRorig.s3") * inputEng.getSubstancesData("Ksw")
						+ (1 - inputEng.getSubstancesData("FRorig.s3")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000 / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.s3C"));

		environment.get("CONTINENTAL").put("Ks3w.C",
				environment.get("CONTINENTAL").get("FRACa.s3C")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s3w"))
						+ environment.get("CONTINENTAL").get("FRACw.s3C")
						+ environment.get("CONTINENTAL").get("FRACs.s3C") * environment.get("CONTINENTAL").get("Kp.s3C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("CONTINENTAL").put("CORG.s2C", 0.02);

		environment.get("CONTINENTAL").put("Kp.s2C",
				(inputEng.getSubstancesData("FRorig.s2") * inputEng.getSubstancesData("Ksw")
						+ (1 - inputEng.getSubstancesData("FRorig.s2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000 / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.s2C"));

		environment.get("CONTINENTAL").put("Ks2w.C",
				environment.get("CONTINENTAL").get("FRACa.s2C")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s2w"))
						+ environment.get("CONTINENTAL").get("FRACw.s2C")
						+ environment.get("CONTINENTAL").get("FRACs.s2C") * environment.get("CONTINENTAL").get("Kp.s2C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("CONTINENTAL").put("CORG.s1C", 0.02);

		environment.get("CONTINENTAL").put("Kp.s1C",
				(inputEng.getSubstancesData("FRorig.s1") * inputEng.getSubstancesData("Ksw")
						+ (1 - inputEng.getSubstancesData("FRorig.s1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000 / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.s1C"));

		environment.get("CONTINENTAL").put("Ks1w.C",
				environment.get("CONTINENTAL").get("FRACa.s1C")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("CONTINENTAL").get("FRACw.s1C")
						+ environment.get("CONTINENTAL").get("FRACs.s1C") * environment.get("CONTINENTAL").get("Kp.s1C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("CONTINENTAL").put("CORG.sd2C", 0.05);

		environment.get("CONTINENTAL").put("Kp.sd2C",
				(inputEng.getSubstancesData("FRorig.sd2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.sd2C"));

		environment.get("CONTINENTAL").put("Ksdw2.C",
				environment.get("CONTINENTAL").get("FRACw.sdC") + environment.get("CONTINENTAL").get("FRACs.sdC")
						* environment.get("CONTINENTAL").get("Kp.sd2C")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("CONTINENTAL").put("CORG.sd1C", 0.05);

		environment.get("CONTINENTAL").put("Kp.sd1C",
				(inputEng.getSubstancesData("FRorig.sd1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.sd1C"));

		environment.get("CONTINENTAL").put("Ksdw1.C",
				environment.get("CONTINENTAL").get("FRACw.sdC") + environment.get("CONTINENTAL").get("FRACs.sdC")
						* environment.get("CONTINENTAL").get("Kp.sd2C")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("CONTINENTAL").put("Kp.col2C", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("CONTINENTAL").put("CORG.susp2C", 0.1);

		environment.get("CONTINENTAL").put("Kp.susp2C",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.susp2C"));

		environment.get("CONTINENTAL").put("FRw.w2C",
				1. / (1. + environment.get("CONTINENTAL").get("Kp.susp2C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w2C") / 1000.
						+ environment.get("CONTINENTAL").get("Kp.col2C")
								* inputEng.getLandscapeSettings("CONTINENTAL", "COL.w2C") / 1000.));

		environment.get("CONTINENTAL").put("Kp.col1C", 0.08 * inputEng.getSubstancesData("D"));

		environment.get("CONTINENTAL").put("CORG.susp1C", 0.1);

		environment.get("CONTINENTAL").put("Kp.susp1C",
				(inputEng.getSubstancesData("FRorig.w1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.susp1C"));

		environment.get("CONTINENTAL").put("FRw.w1C",
				1. / (1. + environment.get("CONTINENTAL").get("Kp.susp1C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w1C") / 1000.
						+ environment.get("CONTINENTAL").get("Kp.col1C")
								* inputEng.getLandscapeSettings("CONTINENTAL", "COL.w1C") / 1000.));

		environment.get("CONTINENTAL").put("Kp.col0C", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("CONTINENTAL").put("CORG.susp0C", 0.1);

		environment.get("CONTINENTAL").put("Kp.susp0C",
				(inputEng.getSubstancesData("FRorig.w1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.susp0C"));

		environment.get("CONTINENTAL").put("FRw.w0C",
				1. / (1. + environment.get("CONTINENTAL").get("Kp.susp0C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w0C") / 1000.
						+ environment.get("CONTINENTAL").get("Kp.col0C")
								* inputEng.getLandscapeSettings("CONTINENTAL", "COL.w0C") / 1000.));

		environment.get("CONTINENTAL").put("Kaerw.aC",
				1. / (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("CONTINENTAL").put("Kaers.aC",
				0.54 * (inputEng.getSubstancesData("Kow") / inputEng.getSubstancesData("Kaw"))
						* inputEng.getLandscapeSettings("ALL-SCALE", "CORGaers")
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHOaers") / 1000.));

		environment.get("CONTINENTAL").put("FRaers.aC",
				environment.get("CONTINENTAL").get("FRACaers.aC") * environment.get("CONTINENTAL").get("Kaers.aC")
						/ (1. + environment.get("CONTINENTAL").get("FRACaerw.aC")
								* environment.get("CONTINENTAL").get("Kaerw.aC")
								+ environment.get("CONTINENTAL").get("FRACaers.aC")
										* environment.get("CONTINENTAL").get("Kaers.aC")));

		environment.get("CONTINENTAL").put("FRaerw.aC",
				environment.get("CONTINENTAL").get("FRACaerw.aC") * environment.get("CONTINENTAL").get("Kaerw.aC")
						/ (1. + environment.get("CONTINENTAL").get("FRACaerw.aC")
								* environment.get("CONTINENTAL").get("Kaerw.aC")
								+ environment.get("CONTINENTAL").get("FRACaers.aC")
										* environment.get("CONTINENTAL").get("Kaers.aC")));

		environment.get("CONTINENTAL").put("FRgas.aC", 1
				- environment.get("CONTINENTAL").get("FRACaerw.aC") * environment.get("CONTINENTAL").get("Kaerw.aC")
						/ (1. + environment.get("CONTINENTAL").get("FRACaerw.aC")
								* environment.get("CONTINENTAL").get("Kaerw.aC")
								+ environment.get("CONTINENTAL").get("FRACaers.aC")
										* environment.get("CONTINENTAL").get("Kaers.aC"))
				- environment.get("CONTINENTAL").get("FRACaers.aC") * environment.get("CONTINENTAL").get("Kaers.aC")
						/ (1. + environment.get("CONTINENTAL").get("FRACaerw.aC")
								* environment.get("CONTINENTAL").get("Kaerw.aC")
								+ environment.get("CONTINENTAL").get("FRACaers.aC")
										* environment.get("CONTINENTAL").get("Kaers.aC")));

		environment.get("CONTINENTAL").put("EROSION.s1C", inputEng.getLandscapeSettings("CONTINENTAL", "EROSION.C"));
		environment.get("CONTINENTAL").put("EROSION.s2C", inputEng.getLandscapeSettings("CONTINENTAL", "EROSION.C"));
		environment.get("CONTINENTAL").put("EROSION.s3C", inputEng.getLandscapeSettings("CONTINENTAL", "EROSION.C"));

		environment.get("CONTINENTAL").put("CORRrunoff.s1C",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s1C")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s1C")))));
		environment.get("CONTINENTAL").put("CORRrunoff.s2C",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s2C")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s2C")))));
		environment.get("CONTINENTAL").put("CORRrunoff.s3C",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s3C")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s3C")))));

		environment.get("CONTINENTAL").put("CORRleach.s1C",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s1C")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s1C")))));
		environment.get("CONTINENTAL").put("CORRleach.s2C",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s2C")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s2C")))));
		environment.get("CONTINENTAL").put("CORRleach.s3C",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s3C")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s3C")))));

		environment.get("CONTINENTAL").put("PRODsusp.w1C", environment.get("CONTINENTAL").get("AREAFRAC.w1C")
				* environment.get("CONTINENTAL").get("SYSTEMAREA.C") * 10. / (1000. * 3600. * 24. * 365.));

		environment.get("CONTINENTAL").put("PRODsusp.w2C", environment.get("CONTINENTAL").get("SYSTEMAREA.C")
				* environment.get("CONTINENTAL").get("AREAFRAC.w2C") * 5. / (1000. * 3600. * 24. * 365.));

		environment.get("CONTINENTAL").put("NETsedrate.w2C", 0.0000000000274);
		environment.get("CONTINENTAL").put("NETsedrate.w1C", 8.62268518546068E-11);
		environment.get("CONTINENTAL").put("NETsedrate.w0C", 8.62268518546068E-11);

		environment.get("CONTINENTAL").put("SETTLvelocity.C", 2.5 / (24. * 3600.));

		if (environment.get("CONTINENTAL").get("SETTLvelocity.C")
				* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w2C")
				/ (environment.get("CONTINENTAL").get("FRACs.sdC")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("CONTINENTAL")
								.get("NETsedrate.w2C")) {
			environment.get("CONTINENTAL").put("GROSSSEDrate.w2C",
					environment.get("CONTINENTAL").get("SETTLvelocity.C")
							* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w2C")
							/ (environment.get("CONTINENTAL").get("FRACs.sdC")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("CONTINENTAL").put("GROSSSEDrate.w2C",
					environment.get("CONTINENTAL").get("NETsedrate.w2C"));

		if (environment.get("CONTINENTAL").get("SETTLvelocity.C")
				* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w1C")
				/ (environment.get("CONTINENTAL").get("FRACs.sdC")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("CONTINENTAL")
								.get("NETsedrate.w1C")) {

			environment.get("CONTINENTAL").put("GROSSSEDrate.w1C",
					environment.get("CONTINENTAL").get("SETTLvelocity.C")
							* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w1C")
							/ (environment.get("CONTINENTAL").get("FRACs.sdC")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("CONTINENTAL").put("GROSSSEDrate.w1C",
					environment.get("CONTINENTAL").get("NETsedrate.w1C"));

		if (environment.get("CONTINENTAL").get("SETTLvelocity.C")
				* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w0C")
				/ (environment.get("CONTINENTAL").get("FRACs.sdC")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("CONTINENTAL")
								.get("NETsedrate.w1C")) {

			environment.get("CONTINENTAL").put("GROSSSEDrate.w0C",
					environment.get("CONTINENTAL").get("SETTLvelocity.C")
							* inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w0C")
							/ (environment.get("CONTINENTAL").get("FRACs.sdC")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("CONTINENTAL").put("GROSSSEDrate.w0C",
					environment.get("CONTINENTAL").get("NETsedrate.w0C"));

		environment.get("CONTINENTAL").put("RESUSP.sd2C.w2C", environment.get("CONTINENTAL").get("GROSSSEDrate.w2C")
				- environment.get("CONTINENTAL").get("NETsedrate.w2C"));

		environment.get("CONTINENTAL").put("SED.w2C.sd2C", environment.get("CONTINENTAL").get("SETTLvelocity.C")
				* (1. - environment.get("CONTINENTAL").get("FRw.w2C")));

		environment.get("CONTINENTAL").put("RESUSP.sd1C.w1C", environment.get("CONTINENTAL").get("GROSSSEDrate.w1C")
				- environment.get("CONTINENTAL").get("NETsedrate.w1C"));

		environment.get("CONTINENTAL").put("SED.w1C.sd1C", environment.get("CONTINENTAL").get("SETTLvelocity.C")
				* (1. - environment.get("CONTINENTAL").get("FRw.w1C")));

		environment.get("CONTINENTAL").put("RESUSP.sd0C.w0C", environment.get("CONTINENTAL").get("GROSSSEDrate.w0C")
				- environment.get("CONTINENTAL").get("NETsedrate.w0C"));

		environment.get("CONTINENTAL").put("SED.w0C.sd0C", environment.get("CONTINENTAL").get("SETTLvelocity.C")
				* (1. - environment.get("CONTINENTAL").get("FRw.w0C")));

		environment.get("CONTINENTAL").put("kwsd.water.wC", 0.000002778);
		environment.get("CONTINENTAL").put("kwsd.sed.sdC", 0.00000002778);

		environment.get("CONTINENTAL").put("DESORB.sd2C.w2C",
				environment.get("CONTINENTAL").get("kwsd.water.wC") * environment.get("CONTINENTAL").get("kwsd.sed.sdC")
						/ (environment.get("CONTINENTAL").get("kwsd.water.wC")
								+ environment.get("CONTINENTAL").get("kwsd.sed.sdC"))
						/ environment.get("CONTINENTAL").get("Ksdw2.C"));

		environment.get("CONTINENTAL").put("ADSORB.w2C.sd2C",
				(environment.get("CONTINENTAL").get("kwsd.water.wC")
						* environment.get("CONTINENTAL").get("kwsd.sed.sdC"))
						/ (environment.get("CONTINENTAL").get("kwsd.water.wC")
								+ environment.get("CONTINENTAL").get("kwsd.sed.sdC"))
						* environment.get("CONTINENTAL").get("FRw.w2C"));

		environment.get("CONTINENTAL").put("DESORB.sd1C.w1C",
				environment.get("CONTINENTAL").get("kwsd.water.wC") * environment.get("CONTINENTAL").get("kwsd.sed.sdC")
						/ (environment.get("CONTINENTAL").get("kwsd.water.wC")
								+ environment.get("CONTINENTAL").get("kwsd.sed.sdC"))
						/ environment.get("CONTINENTAL").get("Ksdw1.C"));

		environment.get("CONTINENTAL").put("ADSORB.w1C.sd1C",
				(environment.get("CONTINENTAL").get("kwsd.water.wC")
						* environment.get("CONTINENTAL").get("kwsd.sed.sdC"))
						/ (environment.get("CONTINENTAL").get("kwsd.water.wC")
								+ environment.get("CONTINENTAL").get("kwsd.sed.sdC"))
						* environment.get("CONTINENTAL").get("FRw.w1C"));

		environment.get("CONTINENTAL").put("k.sd2CD.w2CD",
				((environment.get("CONTINENTAL").get("RESUSP.sd2C.w2C")
						+ environment.get("CONTINENTAL").get("DESORB.sd2C.w2C"))
						/ environment.get("CONTINENTAL").get("DEPTH.sd2C")));

		environment.get("CONTINENTAL").put("k.sd1CD.w1CD",
				((environment.get("CONTINENTAL").get("RESUSP.sd1C.w1C")
						+ environment.get("CONTINENTAL").get("DESORB.sd1C.w1C"))
						/ environment.get("CONTINENTAL").get("DEPTH.sd1C")));

		environment.get("CONTINENTAL").put("k.sd0CD.w0CD", (environment.get("CONTINENTAL").get("RESUSP.sd0C.w0C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd0C")));

		environment.get("CONTINENTAL").put("k.w2CD.sd2CD",
				(environment.get("CONTINENTAL").get("SED.w2C.sd2C")
						+ environment.get("CONTINENTAL").get("ADSORB.w2C.sd2C"))
						/ environment.get("CONTINENTAL").get("DEPTH.w2C"));

		environment.get("CONTINENTAL").put("k.w1CD.sd1CD",
				(environment.get("CONTINENTAL").get("SED.w1C.sd1C")
						+ environment.get("CONTINENTAL").get("ADSORB.w1C.sd1C"))
						/ environment.get("CONTINENTAL").get("DEPTH.w1C"));

		environment.get("CONTINENTAL").put("k.w0CD.sd0CD",
				environment.get("CONTINENTAL").get("SED.w0C.sd0C") / environment.get("CONTINENTAL").get("DEPTH.w0C"));

		environment.get("CONTINENTAL").put("ThermvelCP.aC",
				Math.pow(((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ ((Math.PI * ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadCP.a"), 3))
								* inputEng.getEnvProperties("RhoCP.a")))),
						0.5));

		environment.get("CONTINENTAL").put("DiffCP.aC",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadCP.a")));

		environment.get("CONTINENTAL").put("Fuchs.aCS.aCCP", Math.pow((1 + (4
				* (inputEng.getNanoProperties("DiffS.aC") + environment.get("CONTINENTAL").get("DiffCP.aC")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadCP.a")) * Math.pow(
						(environment.get("CONTINENTAL").get("ThermvelCP.aC")
								* environment.get("CONTINENTAL").get("ThermvelCP.aC")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("CONTINENTAL").put("k.aCS.aCP",
				environment.get("CONTINENTAL").get("Fuchs.aCS.aCCP")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadCP.a") + inputEng.getSubstancesData("RadS"))
								* (environment.get("CONTINENTAL").get("DiffCP.aC")
										+ inputEng.getNanoProperties("DiffS.aC")))
						* inputEng.getLandscapeSettings("CONTINENTAL", "NumConcCP.aC"));

		environment.get("CONTINENTAL").put("DiffAcc.aC",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadAcc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadAcc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadAcc")));

		environment.get("CONTINENTAL").put("ThermvelAcc.aC",
				Math.pow(((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3)
								* inputEng.getEnvProperties("RhoAcc"))))),
						0.5));

		environment.get("CONTINENTAL").put("Fuchs.aCS.aCAcc", Math.pow((1. + (4.
				* (inputEng.getNanoProperties("DiffS.aC") + environment.get("CONTINENTAL").get("DiffAcc.aC")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadAcc")) * Math.pow(
						(environment.get("CONTINENTAL").get("ThermvelAcc.aC")
								* environment.get("CONTINENTAL").get("ThermvelAcc.aC")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("CONTINENTAL").put("kcoag.aCS.aCAcc",
				environment.get("CONTINENTAL").get("Fuchs.aCS.aCAcc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadAcc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("CONTINENTAL").get("DiffAcc.aC")
										+ inputEng.getNanoProperties("DiffS.aC")))
						* inputEng.getLandscapeSettings("CONTINENTAL", "NumConcAcc.aC"));

		environment.get("CONTINENTAL").put("DiffNuc.aC",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadNuc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadNuc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadNuc")));

		environment.get("CONTINENTAL").put("ThermvelNuc.aC",
				Math.pow(((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)
								* inputEng.getEnvProperties("RhoNuc"))))),
						0.5));

		environment.get("CONTINENTAL").put("Fuchs.aCS.aCNuc", Math.pow((1 + (4.
				* (inputEng.getNanoProperties("DiffS.aC") + environment.get("CONTINENTAL").get("DiffNuc.aC")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNuc")) * Math.pow(
						(environment.get("CONTINENTAL").get("ThermvelNuc.aC")
								* environment.get("CONTINENTAL").get("ThermvelNuc.aC")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("CONTINENTAL").put("kcoag.aCS.aCNuc",
				environment.get("CONTINENTAL").get("Fuchs.aCS.aCNuc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadNuc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("CONTINENTAL").get("DiffNuc.aC")
										+ inputEng.getNanoProperties("DiffS.aC")))
						* inputEng.getLandscapeSettings("CONTINENTAL", "NumConcNuc.aC"));

		environment.get("CONTINENTAL").put("k.aCS.aCA", environment.get("CONTINENTAL").get("kcoag.aCS.aCNuc")
				+ environment.get("CONTINENTAL").get("kcoag.aCS.aCAcc"));

		environment.get("CONTINENTAL")
				.put("fGravSNC.wC", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("CONTINENTAL").put("fBrownSNC.wC",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.w")));

		environment.get("CONTINENTAL").put("Shear.w2C", 10.);

		environment.get("CONTINENTAL").put("fInterceptSNC.w2C",
				(4. / 3.) * environment.get("CONTINENTAL").get("Shear.w2C")
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("CONTINENTAL").put("fTotalSNC.w2C",
				environment.get("CONTINENTAL").get("fInterceptSNC.w2C")
						+ +environment.get("CONTINENTAL").get("fBrownSNC.wC")
						+ environment.get("CONTINENTAL").get("fGravSNC.wC"));

		environment.get("CONTINENTAL").put("NumConcNC.w2C", inputEng.getLandscapeSettings("CONTINENTAL", "COL.w2C")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("CONTINENTAL").put("k.w2CS.w2CA", environment.get("CONTINENTAL").get("fTotalSNC.w2C")
				* environment.get("CONTINENTAL").get("NumConcNC.w2C") * inputEng.getSubstancesData("AtefSA.w2"));

		environment.get("CONTINENTAL").put("Shear.w1C", 100.);

		environment.get("CONTINENTAL").put("fInterceptSNC.w1C",
				(4. / 3.) * environment.get("CONTINENTAL").get("Shear.w1C")
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("CONTINENTAL").put("fTotalSNC.w1C",
				environment.get("CONTINENTAL").get("fInterceptSNC.w1C")
						+ +environment.get("CONTINENTAL").get("fBrownSNC.wC")
						+ environment.get("CONTINENTAL").get("fGravSNC.wC"));

		environment.get("CONTINENTAL").put("NumConcNC.w1C", inputEng.getLandscapeSettings("CONTINENTAL", "COL.w1C")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("CONTINENTAL").put("k.w1CS.w1CA", environment.get("CONTINENTAL").get("fTotalSNC.w1C")
				* environment.get("CONTINENTAL").get("NumConcNC.w1C") * inputEng.getSubstancesData("AtefSA.w1"));

		environment.get("CONTINENTAL").put("Shear.w0C", 1.);

		environment.get("CONTINENTAL").put("fInterceptSNC.w0C",
				(4. / 3.) * environment.get("CONTINENTAL").get("Shear.w0C")
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("CONTINENTAL").put("fTotalSNC.w0C",
				environment.get("CONTINENTAL").get("fInterceptSNC.w0C")
						+ +environment.get("CONTINENTAL").get("fBrownSNC.wC")
						+ environment.get("CONTINENTAL").get("fGravSNC.wC"));

		environment.get("CONTINENTAL").put("NumConcNC.w0C", inputEng.getLandscapeSettings("CONTINENTAL", "COL.w0C")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("CONTINENTAL").put("k.w0CS.w0CA", environment.get("CONTINENTAL").get("fTotalSNC.w0C")
				* environment.get("CONTINENTAL").get("NumConcNC.w0C") * inputEng.getSubstancesData("AtefSA.w0"));

		environment.get("CONTINENTAL").put("fGravSSPM.wC", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelSPM.w")));

		environment.get("CONTINENTAL").put("fBrownSSPM.wC",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("CONTINENTAL").put("fInterceptSSPM.w2C",
				(4. / 3.) * environment.get("CONTINENTAL").get("Shear.w2C")
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("CONTINENTAL").put("fTotalSSPM.w2C",
				environment.get("CONTINENTAL").get("fInterceptSSPM.w2C")
						+ +environment.get("CONTINENTAL").get("fBrownSSPM.wC")
						+ environment.get("CONTINENTAL").get("fGravSSPM.wC"));

		environment.get("CONTINENTAL").put("NumConcSPM.w2C", inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w2C")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("CONTINENTAL").put("k.w2CS.w2CP", environment.get("CONTINENTAL").get("fTotalSSPM.w2C")
				* environment.get("CONTINENTAL").get("NumConcSPM.w2C") * inputEng.getSubstancesData("AtefSP.w2"));

		environment.get("CONTINENTAL").put("fInterceptSSPM.w1C",
				(4. / 3.) * environment.get("CONTINENTAL").get("Shear.w1C")
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("CONTINENTAL").put("fTotalSSPM.w1C",
				environment.get("CONTINENTAL").get("fInterceptSSPM.w1C")
						+ +environment.get("CONTINENTAL").get("fBrownSSPM.wC")
						+ environment.get("CONTINENTAL").get("fGravSSPM.wC"));

		environment.get("CONTINENTAL").put("NumConcSPM.w1C", inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w1C")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("CONTINENTAL").put("k.w1CS.w1CP", environment.get("CONTINENTAL").get("fTotalSSPM.w1C")
				* environment.get("CONTINENTAL").get("NumConcSPM.w1C") * inputEng.getSubstancesData("AtefSP.w1"));

		environment.get("CONTINENTAL").put("fInterceptSSPM.w0C",
				(4. / 3.) * environment.get("CONTINENTAL").get("Shear.w0C")
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("CONTINENTAL").put("fTotalSSPM.w0C",
				environment.get("CONTINENTAL").get("fInterceptSSPM.w0C")
						+ +environment.get("CONTINENTAL").get("fBrownSSPM.wC")
						+ environment.get("CONTINENTAL").get("fGravSSPM.wC"));

		environment.get("CONTINENTAL").put("NumConcSPM.w0C", inputEng.getLandscapeSettings("CONTINENTAL", "SUSP.w0C")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("CONTINENTAL").put("k.w0CS.w0CP", environment.get("CONTINENTAL").get("fTotalSSPM.w0C")
				* environment.get("CONTINENTAL").get("NumConcSPM.w0C") * inputEng.getSubstancesData("AtefSP.w0"));

		environment.get("CONTINENTAL")
				.put("fGravSNC.sdC", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("CONTINENTAL").put("fBrownSNC.sdC",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.sd")));

		environment.get("CONTINENTAL").put("NumConcNC.sd2C", 2. * environment.get("CONTINENTAL").get("NumConcNC.w2C"));

		environment.get("CONTINENTAL").put("k.sd2CS.sd2CA",
				inputEng.getSubstancesData("AtefSA.sd2") * environment.get("CONTINENTAL").get("NumConcNC.sd2C")
						* (environment.get("CONTINENTAL").get("fBrownSNC.sdC")
								+ environment.get("CONTINENTAL").get("fGravSNC.sdC")));

		environment.get("CONTINENTAL").put("NumConcNC.sd1C", 2. * environment.get("CONTINENTAL").get("NumConcNC.w1C"));

		environment.get("CONTINENTAL").put("k.sd1CS.sd1CA",
				inputEng.getSubstancesData("AtefSA.sd1") * environment.get("CONTINENTAL").get("NumConcNC.sd1C")
						* (environment.get("CONTINENTAL").get("fBrownSNC.sdC")
								+ environment.get("CONTINENTAL").get("fGravSNC.sdC")));

		environment.get("CONTINENTAL").put("NumConcNC.sd0C", 2. * environment.get("CONTINENTAL").get("NumConcNC.w0C"));

		environment.get("CONTINENTAL").put("k.sd0CS.sd0CA",
				inputEng.getSubstancesData("AtefSA.sd0") * environment.get("CONTINENTAL").get("NumConcNC.sd0C")
						* (environment.get("CONTINENTAL").get("fBrownSNC.sdC")
								+ environment.get("CONTINENTAL").get("fGravSNC.sdC")));

		environment.get("CONTINENTAL").put("Udarcy.sdC", 9.e-6);

		environment.get("CONTINENTAL").put("GravNumberSP.sdC", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("CONTINENTAL").get("Udarcy.sdC")));

		environment.get("CONTINENTAL").put("PecletNumberSP.sdC", environment.get("CONTINENTAL").get("Udarcy.sdC") * 2.
				* inputEng.getEnvProperties("RadFP.sd") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("CONTINENTAL").put("aspectratioSP.sdC",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.sd"));

		environment.get("CONTINENTAL").put("vdWaalsNumberSP.sdC",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")));

		environment.get("CONTINENTAL").put("fGravSP.sdC",
				0.22 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSP.sdC"), -0.24)
						* Math.pow(environment.get("CONTINENTAL").get("GravNumberSP.sdC"), 1.11)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSP.sdC"), 0.053));

		environment.get("CONTINENTAL").put("fInterceptSP.sdC",
				0.55 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSP.sdC"), 1.55)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberSP.sdC"), -0.125)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSP.sdC"), 0.125));

		environment.get("CONTINENTAL").put("Por.sdC", environment.get("CONTINENTAL").get("FRACw.sdC"));

		environment.get("CONTINENTAL").put("GammPDF.sdC",
				Math.pow(1. - environment.get("CONTINENTAL").get("Por.sdC"), 1. / 3.));

		environment.get("CONTINENTAL").put("ASPDF.sdC",
				(2. * (1. - Math.pow(environment.get("CONTINENTAL").get("GammPDF.sdC"), 5)))
						/ (2. - 3. * environment.get("CONTINENTAL").get("GammPDF.sdC")
								+ 3 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.sdC"), 5)
								- 2 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.sdC"), 6)));

		environment.get("CONTINENTAL").put("fBrownSP.sdC",
				2.4 * Math.pow(environment.get("CONTINENTAL").get("PecletNumberSP.sdC"), 1. / 3.)
						* Math.pow(environment.get("CONTINENTAL").get("aspectratioSP.sdC"), -0.081)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberSP.sdC"), -0.715)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSP.sdC"), 0.053));

		environment.get("CONTINENTAL").put("fTotalSP.sdC",
				environment.get("CONTINENTAL").get("fBrownSP.sdC")
						+ environment.get("CONTINENTAL").get("fInterceptSP.sdC")
						+ environment.get("CONTINENTAL").get("fGravSP.sdC"));

		environment.get("CONTINENTAL").put("Filter.sdC",
				(3. / 2.) * ((1. - environment.get("CONTINENTAL").get("Por.sdC"))
						/ (2 * inputEng.getEnvProperties("RadFP.sd") * environment.get("CONTINENTAL").get("Por.sdC"))));

		environment.get("CONTINENTAL")
				.put("k.sd2CS.sd2CP", environment.get("CONTINENTAL").get("Filter.sdC")
						* environment.get("CONTINENTAL").get("fTotalSP.sdC")
						* environment.get("CONTINENTAL").get("Udarcy.sdC") * inputEng.getSubstancesData("AtefSP.sd2"));

		environment.get("CONTINENTAL").put("k.sd1CS.sd1CP",
				environment.get("CONTINENTAL").get("Filter.sdC") * environment.get("CONTINENTAL").get("Udarcy.sdC")
						* environment.get("CONTINENTAL").get("fTotalSP.sdC")
						* inputEng.getSubstancesData("AtefSP.sd1"));

		environment.get("CONTINENTAL").put("k.sd0CS.sd0CP",
				environment.get("CONTINENTAL").get("Filter.sdC") * environment.get("CONTINENTAL").get("Udarcy.sdC")
						* environment.get("CONTINENTAL").get("fTotalSP.sdC")
						* inputEng.getSubstancesData("AtefSP.sd0"));

		environment.get("CONTINENTAL").put("fGravSNC.sC", Math.PI
				* (Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2) * Math
						.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.s"))));

		environment.get("CONTINENTAL").put("fBrownSNC.sC",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.s")));

		environment.get("CONTINENTAL").put("NumConcNC.sC", inputEng.getLandscapeSettings("CONTINENTAL", "mConcNC.sC")
				/ (inputEng.getEnvProperties("SingleVolNC.s") * inputEng.getEnvProperties("RhoNC.sd")));

		environment.get("CONTINENTAL").put("k.s3CS.s3CA",
				inputEng.getSubstancesData("AtefSA.s3") * environment.get("CONTINENTAL").get("NumConcNC.sC")
						* (environment.get("CONTINENTAL").get("fBrownSNC.sC")
								+ environment.get("CONTINENTAL").get("fGravSNC.sC")));

		environment.get("CONTINENTAL").put("k.s2CS.s2CA",
				inputEng.getSubstancesData("AtefSA.s2") * environment.get("CONTINENTAL").get("NumConcNC.sC")
						* (environment.get("CONTINENTAL").get("fBrownSNC.sC")
								+ environment.get("CONTINENTAL").get("fGravSNC.sC")));

		environment.get("CONTINENTAL").put("k.s1CS.s1CA",
				inputEng.getSubstancesData("AtefSA.s1") * environment.get("CONTINENTAL").get("NumConcNC.sC")
						* (environment.get("CONTINENTAL").get("fBrownSNC.sC")
								+ environment.get("CONTINENTAL").get("fGravSNC.sC")));

		environment.get("CONTINENTAL").put("Udarcy.s3C", 9.e-6);

		environment.get("CONTINENTAL").put("GravNumberS.s3C", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("CONTINENTAL").get("Udarcy.s3C")));

		environment.get("CONTINENTAL").put("vdWaalsNumberSFP.s3C",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")));

		environment.get("CONTINENTAL").put("PecletNumberFP.s3C", environment.get("CONTINENTAL").get("Udarcy.s3C") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("CONTINENTAL").put("aspectratioSFP.s3C",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("CONTINENTAL").put("fGravSFP.s3C",
				0.22 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s3C"), -0.24)
						* Math.pow(environment.get("CONTINENTAL").get("GravNumberS.s3C"), 1.11)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s3C"), 0.053));

		environment.get("CONTINENTAL").put("fInterceptSFP.s3C",
				0.55 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s3C"), 1.55)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberFP.s3C"), -0.125)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s3C"), 0.125));

		environment.get("CONTINENTAL").put("Por.s3C", 1. - environment.get("CONTINENTAL").get("FRACs.s3C"));

		environment.get("CONTINENTAL").put("GammPDF.s3C",
				Math.pow(1. - environment.get("CONTINENTAL").get("Por.s3C"), 1. / 3.));

		environment.get("CONTINENTAL").put("ASPDF.s3C",
				(2. * (1. - Math.pow(environment.get("CONTINENTAL").get("GammPDF.s3C"), 5)))
						/ (2. - 3. * environment.get("CONTINENTAL").get("GammPDF.s3C")
								+ 3 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.s3C"), 5)
								- 2 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.s3C"), 6)));

		environment.get("CONTINENTAL").put("fBrownSFP.s3C",
				2.4 * Math.pow(environment.get("CONTINENTAL").get("ASPDF.s3C"), 1. / 3.)
						* Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s3C"), -0.081)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberFP.s3C"), -0.715)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s3C"), 0.053));

		environment.get("CONTINENTAL").put("fTotalSFP.s3C",
				environment.get("CONTINENTAL").get("fInterceptSFP.s3C")
						+ +environment.get("CONTINENTAL").get("fBrownSFP.s3C")
						+ environment.get("CONTINENTAL").get("fGravSFP.s3C"));

		environment.get("CONTINENTAL").put("Filter.s3C",
				(3. / 2.) * ((1. - environment.get("CONTINENTAL").get("Por.s3C"))
						/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("CONTINENTAL").get("Por.s3C"))));

		environment.get("CONTINENTAL")
				.put("k.s3CS.s3CP", environment.get("CONTINENTAL").get("Filter.s3C")
						* environment.get("CONTINENTAL").get("fTotalSFP.s3C")
						* environment.get("CONTINENTAL").get("Udarcy.s3C") * inputEng.getSubstancesData("AtefSP.s3"));

		environment.get("CONTINENTAL").put("Udarcy.s2C", 9.e-6);

		environment.get("CONTINENTAL").put("GravNumberS.s2C", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("CONTINENTAL").get("Udarcy.s2C")));

		environment.get("CONTINENTAL").put("vdWaalsNumberSFP.s2C",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")));

		environment.get("CONTINENTAL").put("PecletNumberFP.s2C", environment.get("CONTINENTAL").get("Udarcy.s2C") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wR"));

		environment.get("CONTINENTAL").put("aspectratioSFP.s2C",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("CONTINENTAL").put("fGravSFP.s2C",
				0.22 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s2C"), -0.24)
						* Math.pow(environment.get("CONTINENTAL").get("GravNumberS.s2C"), 1.11)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s2C"), 0.053));

		environment.get("CONTINENTAL").put("fInterceptSFP.s2C",
				0.55 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s2C"), 1.55)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberFP.s2C"), -0.125)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s2C"), 0.125));

		environment.get("CONTINENTAL").put("Por.s2C", 1. - environment.get("CONTINENTAL").get("FRACs.s2C"));

		environment.get("CONTINENTAL").put("GammPDF.s2C",
				Math.pow(1. - environment.get("CONTINENTAL").get("Por.s2C"), 1. / 3.));

		environment.get("CONTINENTAL").put("ASPDF.s2C",
				(2. * (1. - Math.pow(environment.get("CONTINENTAL").get("GammPDF.s2C"), 5)))
						/ (2. - 3. * environment.get("CONTINENTAL").get("GammPDF.s2C")
								+ 3 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.s2C"), 5)
								- 2 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.s2C"), 6)));

		environment.get("CONTINENTAL").put("fBrownSFP.s2C",
				2.4 * Math.pow(environment.get("CONTINENTAL").get("ASPDF.s2C"), 1. / 3.)
						* Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s2C"), -0.081)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberFP.s2C"), -0.715)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s2C"), 0.053));

		environment.get("CONTINENTAL").put("fTotalSFP.s2C",
				environment.get("CONTINENTAL").get("fInterceptSFP.s2C")
						+ +environment.get("CONTINENTAL").get("fBrownSFP.s2C")
						+ environment.get("CONTINENTAL").get("fGravSFP.s2C"));

		environment.get("CONTINENTAL").put("Filter.s2C",
				(3. / 2.) * ((1. - environment.get("CONTINENTAL").get("Por.s2C"))
						/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("CONTINENTAL").get("Por.s2C"))));

		environment.get("CONTINENTAL")
				.put("k.s2CS.s2CP", environment.get("CONTINENTAL").get("Filter.s2C")
						* environment.get("CONTINENTAL").get("fTotalSFP.s2C")
						* environment.get("CONTINENTAL").get("Udarcy.s2C") * inputEng.getSubstancesData("AtefSP.s2"));

		environment.get("CONTINENTAL").put("Udarcy.s1C", 9.e-6);

		environment.get("CONTINENTAL").put("GravNumberS.s1C", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("CONTINENTAL").get("Udarcy.s1C")));

		environment.get("CONTINENTAL").put("vdWaalsNumberSFP.s1C",
				inputEng.getSubstancesData("AHamakerSP.w") / (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")));

		environment.get("CONTINENTAL").put("PecletNumberFP.s1C", environment.get("CONTINENTAL").get("Udarcy.s1C") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wR"));

		environment.get("CONTINENTAL").put("aspectratioSFP.s1C",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("CONTINENTAL").put("fGravSFP.s1C",
				0.22 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s1C"), -0.24)
						* Math.pow(environment.get("CONTINENTAL").get("GravNumberS.s1C"), 1.11)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s1C"), 0.053));

		environment.get("CONTINENTAL").put("fInterceptSFP.s1C",
				0.55 * Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s1C"), 1.55)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberFP.s1C"), -0.125)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s1C"), 0.125));

		environment.get("CONTINENTAL").put("Por.s1C", 1. - environment.get("CONTINENTAL").get("FRACs.s1C"));
		environment.get("CONTINENTAL").put("GammPDF.s1C",
				Math.pow(1. - environment.get("CONTINENTAL").get("Por.s1C"), 1. / 3.));

		environment.get("CONTINENTAL").put("ASPDF.s1C",
				(2. * (1. - Math.pow(environment.get("CONTINENTAL").get("GammPDF.s1C"), 5)))
						/ (2. - 3. * environment.get("CONTINENTAL").get("GammPDF.s1C")
								+ 3 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.s1C"), 5)
								- 2 * Math.pow(environment.get("CONTINENTAL").get("GammPDF.s1C"), 6)));

		environment.get("CONTINENTAL").put("fBrownSFP.s1C",
				2.4 * Math.pow(environment.get("CONTINENTAL").get("ASPDF.s1C"), 1. / 3.)
						* Math.pow(environment.get("CONTINENTAL").get("aspectratioSFP.s1C"), -0.081)
						* Math.pow(environment.get("CONTINENTAL").get("PecletNumberFP.s1C"), -0.715)
						* Math.pow(environment.get("CONTINENTAL").get("vdWaalsNumberSFP.s1C"), 0.053));

		environment.get("CONTINENTAL").put("fTotalSFP.s1C",
				environment.get("CONTINENTAL").get("fInterceptSFP.s1C")
						+ +environment.get("CONTINENTAL").get("fBrownSFP.s1C")
						+ environment.get("CONTINENTAL").get("fGravSFP.s1C"));

		environment.get("CONTINENTAL").put("Filter.s1C",
				(3. / 2.) * ((1. - environment.get("CONTINENTAL").get("Por.s1C"))
						/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("CONTINENTAL").get("Por.s1C"))));

		environment.get("CONTINENTAL")
				.put("k.s1CS.s1CP", environment.get("CONTINENTAL").get("Filter.s1C")
						* environment.get("CONTINENTAL").get("fTotalSFP.s1C")
						* environment.get("CONTINENTAL").get("Udarcy.s1C") * inputEng.getSubstancesData("AtefSP.s1"));

		environment.get("CONTINENTAL").put("Cunningham.cwC", 1.);

		environment.get("CONTINENTAL").put("FRACtwet.C", 1.);

		environment.get("CONTINENTAL").put("RAINrate.wet.C", inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
				/ environment.get("CONTINENTAL").get("FRACtwet.C"));

		environment.get("CONTINENTAL").put("Rad.cwC",
				((0.7 * (Math.pow(60. * 60. * 1000. * environment.get("CONTINENTAL").get("RAINrate.wet.C"), 0.25)) / 2.)
						/ 1000.));

		environment.get("CONTINENTAL").put("TerminalVel.cwC",
				(Math.pow(2 * environment.get("CONTINENTAL").get("Rad.cwC"), 2)
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")
								- inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * environment.get("CONTINENTAL").get("Cunningham.cwC"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("CONTINENTAL").put("ReyNumber.cwC",
				((2. * environment.get("CONTINENTAL").get("Rad.cwC"))
						* environment.get("CONTINENTAL").get("TerminalVel.cwC")
						* inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						/ (2. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("CONTINENTAL").put("CritStokesNumb.cwC",
				(1.2 + (1. / 12.) * Math.log(1. + environment.get("CONTINENTAL").get("ReyNumber.cwC")))
						/ (1. + Math.log(environment.get("CONTINENTAL").get("ReyNumber.cwC"))));

		environment.get("CONTINENTAL")
				.put("SingleMassA.aC",
						((inputEng.getLandscapeSettings("CONTINENTAL", "NumConcNuc.aC")
								* (inputEng.getEnvProperties("RhoNuc")
										* ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3))
										+ inputEng.getSubstancesData("RhoS")
												* inputEng.getNanoProperties("SingleVolS")))
								+ (inputEng.getLandscapeSettings("CONTINENTAL", "NumConcAcc.aC")
										* (inputEng.getEnvProperties("RhoAcc")
												* ((4. / 3.) * Math.PI
														* Math.pow(inputEng.getEnvProperties("RadAcc"), 3))
												+ inputEng.getSubstancesData("RhoS")
														* inputEng.getNanoProperties("SingleVolS"))))
								/ (inputEng.getLandscapeSettings("CONTINENTAL", "NumConcNuc.aC")
										+ inputEng.getLandscapeSettings("CONTINENTAL", "NumConcAcc.aC")));

		environment.get("CONTINENTAL").put("SingleVolA.aC",
				((inputEng.getLandscapeSettings("CONTINENTAL", "NumConcNuc.aC")
						* (inputEng.getNanoProperties("SingleVolS")
								+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)))))
						+ (inputEng.getLandscapeSettings("CONTINENTAL", "NumConcAcc.aC")
								* (inputEng.getNanoProperties("SingleVolS")
										+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3))))))
						/ (inputEng.getLandscapeSettings("CONTINENTAL", "NumConcNuc.aC")
								+ inputEng.getLandscapeSettings("CONTINENTAL", "NumConcAcc.aC")));

		environment.get("CONTINENTAL").put("RhoA.aC", environment.get("CONTINENTAL").get("SingleMassA.aC")
				/ environment.get("CONTINENTAL").get("SingleVolA.aC"));

		environment.get("CONTINENTAL").put("RadA.aC",
				Math.pow((environment.get("CONTINENTAL").get("SingleVolA.aC")) / ((4. / 3.) * Math.PI), 1. / 3.));

		environment.get("CONTINENTAL").put("tdry.C", 3.3 * 24. * 3600.);
		environment.get("CONTINENTAL").put("twet.C", 3600. * 3.3 * 24. * (0.06 / (1. - 0.06)));
		environment.get("CONTINENTAL").put("COLLECTeff.C", 2e+5);

		environment.get("CONTINENTAL").put("k.aCP.cwCP",
				((environment.get("CONTINENTAL").get("tdry.C") + environment.get("CONTINENTAL").get("twet.C"))
						/ environment.get("CONTINENTAL").get("twet.C")
						* environment.get("CONTINENTAL").get("COLLECTeff.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C"))
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"));

		environment.get("CONTINENTAL").put("CunninghamA.aC",
				1. + (66.e-9 / environment.get("CONTINENTAL").get("RadA.aC"))
						* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / environment.get("CONTINENTAL").get("RadA.aC")))));

		environment.get("CONTINENTAL").put("DiffA.aC",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz")
						* inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C")
						* environment.get("CONTINENTAL").get("CunninghamA.aC"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* environment.get("CONTINENTAL").get("RadA.aC")));

		environment.get("CONTINENTAL").put("RelaxA.aC",
				((environment.get("CONTINENTAL").get("RhoA.aC") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * environment.get("CONTINENTAL").get("RadA.aC")), 2)
						* environment.get("CONTINENTAL").get("CunninghamA.aC"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("CONTINENTAL").put("SettvelA.aC",
				(Math.pow(2. * environment.get("CONTINENTAL").get("RadA.aC"), 2)
						* (environment.get("CONTINENTAL").get("RhoA.aC")
								- inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * environment.get("CONTINENTAL").get("CunninghamA.aC"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("CONTINENTAL").put("StokesNumberA.aC",
				(2. * environment.get("CONTINENTAL").get("RelaxA.aC")
						* (environment.get("CONTINENTAL").get("TerminalVel.cwC")
								- environment.get("CONTINENTAL").get("SettvelA.aC")))
						/ (2. * environment.get("CONTINENTAL").get("Rad.cwC")));

		environment.get("CONTINENTAL").put("SchmidtNumberA.aC",
				inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
						/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a")
								* environment.get("CONTINENTAL").get("DiffA.aC")));

		environment.get("CONTINENTAL").put("fBrown.aCA.cwCA",
				(4. / (environment.get("CONTINENTAL").get("ReyNumber.cwC")
						* environment.get("CONTINENTAL").get("SchmidtNumberA.aC")))
						* (1. + 0.4 * (Math.pow(environment.get("CONTINENTAL").get("ReyNumber.cwC"), 0.5)
								* Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberA.aC"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("CONTINENTAL").get("ReyNumber.cwC"), 0.5)
										* Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberA.aC"), 0.5))));

		environment.get("CONTINENTAL").put("fIntercept.aCA.cwCA",
				4. * (environment.get("CONTINENTAL").get("RadA.aC") / environment.get("CONTINENTAL").get("Rad.cwC"))
						* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
								+ (1. + 2. * Math.pow(environment.get("CONTINENTAL").get("ReyNumber.cwC"), 0.5)
										* (environment.get("CONTINENTAL").get("RadA.aC")
												/ environment.get("CONTINENTAL").get("Rad.cwC")))));

		if (environment.get("CONTINENTAL").get("StokesNumberA.aC") > environment.get("CONTINENTAL")
				.get("CritStokesNumb.cwC"))
			environment.get("CONTINENTAL").put("fGrav.aCA.cwCA",
					Math.pow(((environment.get("CONTINENTAL").get("StokesNumberA.aC")
							- environment.get("CONTINENTAL").get("CritStokesNumb.cwC"))
							/ environment.get("CONTINENTAL").get("StokesNumberA.aC")
							- environment.get("CONTINENTAL").get("CritStokesNumb.cwC") + 2. / 3.), 3. / 2.));
		else
			environment.get("CONTINENTAL").put("fGrav.aCA.cwCA", 0.0);

		environment.get("CONTINENTAL").put("fTotal.aCA.cwCA",
				environment.get("CONTINENTAL").get("fBrown.aCA.cwCA")
						+ environment.get("CONTINENTAL").get("fIntercept.aCA.cwCA")
						+ environment.get("CONTINENTAL").get("fGrav.aCA.cwCA"));

		environment.get("CONTINENTAL").put("k.aCA.cwCA",
				((3. / 2.)
						* (environment.get("CONTINENTAL").get("fTotal.aCA.cwCA")
								* environment.get("CONTINENTAL").get("RAINrate.wet.C"))
						/ (2. * environment.get("CONTINENTAL").get("Rad.cwC"))));

		environment.get("CONTINENTAL").put("RelaxS.aC",
				((inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * inputEng.getSubstancesData("RadS")), 2)
						* inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("CONTINENTAL").put("SettvelS.aC",
				(Math.pow(2. * inputEng.getSubstancesData("RadS"), 2)
						* (inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("CONTINENTAL").put("StokesNumberS.aC",
				(2. * environment.get("CONTINENTAL").get("RelaxS.aC")
						* (environment.get("CONTINENTAL").get("TerminalVel.cwC")
								- environment.get("CONTINENTAL").get("SettvelS.aC")))
						/ (2. * environment.get("CONTINENTAL").get("Rad.cwC")));

		environment.get("CONTINENTAL").put("SchmidtNumberS.aC", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * inputEng.getNanoProperties("DiffS.aC")));

		environment.get("CONTINENTAL").put("fBrown.aCS.cwCS",
				(4. / (environment.get("CONTINENTAL").get("ReyNumber.cwC")
						* environment.get("CONTINENTAL").get("SchmidtNumberS.aC")))
						* (1. + 0.4 * (Math.pow(environment.get("CONTINENTAL").get("ReyNumber.cwC"), 0.5)
								* Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberS.aC"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("CONTINENTAL").get("ReyNumber.cwC"), 0.5)
										* Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberS.aC"), 0.5))));

		environment.get("CONTINENTAL").put("fIntercept.aCS.cwCS",
				4. * (inputEng.getSubstancesData("RadS") / environment.get("CONTINENTAL").get("Rad.cwC"))
						* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
								+ (1. + 2. * Math.pow(environment.get("CONTINENTAL").get("ReyNumber.cwC"), 0.5)
										* (inputEng.getSubstancesData("RadS")
												/ environment.get("CONTINENTAL").get("Rad.cwC")))));

		if (environment.get("CONTINENTAL").get("StokesNumberS.aC") > environment.get("CONTINENTAL")
				.get("CritStokesNumb.cwC"))
			environment.get("CONTINENTAL").put("fGrav.aCS.cwCS",
					Math.pow(((environment.get("CONTINENTAL").get("StokesNumberS.aC")
							- environment.get("CONTINENTAL").get("CritStokesNumb.cwC"))
							/ environment.get("CONTINENTAL").get("StokesNumberS.aC")
							- environment.get("CONTINENTAL").get("CritStokesNumb.cwC") + 2. / 3.), 3. / 2.));
		else
			environment.get("CONTINENTAL").put("fGrav.aCS.cwCS", 0.0);

		environment.get("CONTINENTAL").put("fTotal.aCS.cwCS",
				environment.get("CONTINENTAL").get("fBrown.aCS.cwCS")
						+ environment.get("CONTINENTAL").get("fIntercept.aCS.cwCS")
						+ environment.get("CONTINENTAL").get("fGrav.aCS.cwCS"));

		environment.get("CONTINENTAL").put("k.aCS.cwCS",
				((3. / 2.)
						* (environment.get("CONTINENTAL").get("fTotal.aCS.cwCS")
								* environment.get("CONTINENTAL").get("RAINrate.wet.C"))
						/ (2. * environment.get("CONTINENTAL").get("Rad.cwC"))));

		environment.get("CONTINENTAL").put("veghair.s1C", 1e-5);
		environment.get("CONTINENTAL").put("largevegradius.s1C", 0.0005);
		environment.get("CONTINENTAL").put("AREAFRACveg.s1C", 0.01);
		environment.get("CONTINENTAL").put("FRICvelocity.s1C", 0.19);
		environment.get("CONTINENTAL").put("AEROresist.s1C", 74.);

		environment.get("CONTINENTAL").put("veghair.s2C", 1e-5);
		environment.get("CONTINENTAL").put("largevegradius.s2C", 0.0005);
		environment.get("CONTINENTAL").put("AREAFRACveg.s2C", 0.01);
		environment.get("CONTINENTAL").put("FRICvelocity.s2C", 0.19);
		environment.get("CONTINENTAL").put("AEROresist.s2C", 74.);

		environment.get("CONTINENTAL").put("veghair.s3C", 1e-5);
		environment.get("CONTINENTAL").put("largevegradius.s3C", 0.0005);
		environment.get("CONTINENTAL").put("AREAFRACveg.s3C", 0.01);
		environment.get("CONTINENTAL").put("FRICvelocity.s3C", 0.19);
		environment.get("CONTINENTAL").put("AEROresist.s3C", 74.);

		environment.get("CONTINENTAL").put("fGrav.aCS.s1CS",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberS.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberS.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("fGrav.aCS.s3CS",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberS.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberS.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("fIntercept.aCS.s3CS",
				0.3 * (environment.get("CONTINENTAL").get("AREAFRACveg.s3C") * (inputEng.getSubstancesData("RadS")
						/ (inputEng.getSubstancesData("RadS") + environment.get("CONTINENTAL").get("veghair.s3C")))
						+ (1 - environment.get("CONTINENTAL").get("AREAFRACveg.s3C"))
								* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
										+ environment.get("CONTINENTAL").get("largevegradius.s3C")))));

		environment.get("CONTINENTAL").put("fBrown.aCS.s3CS",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberS.aC"), -2. / 3.));

		environment.get("CONTINENTAL").put("SURFresist.s3CS",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.s3C")
						* (environment.get("CONTINENTAL").get("fBrown.aCS.s3CS")
								+ environment.get("CONTINENTAL").get("fIntercept.aCS.s3CS")
								+ environment.get("CONTINENTAL").get("fGrav.aCS.s3CS"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCS.s3CS",
				1. / (environment.get("CONTINENTAL").get("AEROresist.s3C")
						+ environment.get("CONTINENTAL").get("SURFresist.s3CS"))
						+ environment.get("CONTINENTAL").get("SettvelS.aC"));

		environment.get("CONTINENTAL").put("k.aCS.s3CS",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCS.s3CS")
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("fGrav.aRS.s2RS",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberS.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberS.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("fIntercept.aCS.s2CS",
				0.3 * (environment.get("CONTINENTAL").get("AREAFRACveg.s2C") * (inputEng.getSubstancesData("RadS")
						/ (inputEng.getSubstancesData("RadS") + environment.get("CONTINENTAL").get("veghair.s2C")))
						+ (1 - environment.get("CONTINENTAL").get("AREAFRACveg.s2C"))
								* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
										+ environment.get("CONTINENTAL").get("largevegradius.s2C")))));

		environment.get("CONTINENTAL").put("fBrown.aCS.s2CS",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberS.aC"), -2. / 3.));

		environment.get("CONTINENTAL").put("fGrav.aCS.s2CS",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberS.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberS.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("SURFresist.s2CS",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.s2C")
						* (environment.get("CONTINENTAL").get("fBrown.aCS.s2CS")
								+ environment.get("CONTINENTAL").get("fIntercept.aCS.s2CS")
								+ environment.get("CONTINENTAL").get("fGrav.aCS.s2CS"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCS.s2CS",
				1. / (environment.get("CONTINENTAL").get("AEROresist.s2C")
						+ environment.get("CONTINENTAL").get("SURFresist.s2CS"))
						+ environment.get("CONTINENTAL").get("SettvelS.aC"));

		environment.get("CONTINENTAL").put("k.aCS.s2CS",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCS.s2CS")
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("fIntercept.aCS.s1CS",
				0.3 * (environment.get("CONTINENTAL").get("AREAFRACveg.s1C") * (inputEng.getSubstancesData("RadS")
						/ (inputEng.getSubstancesData("RadS") + environment.get("CONTINENTAL").get("veghair.s1C")))
						+ (1 - environment.get("CONTINENTAL").get("AREAFRACveg.s1C"))
								* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
										+ environment.get("CONTINENTAL").get("largevegradius.s1C")))));

		environment.get("CONTINENTAL").put("fBrown.aCS.s1CS",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberS.aC"), -2. / 3.));

		environment.get("CONTINENTAL").put("SURFresist.s1CS",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.s1C")
						* (environment.get("CONTINENTAL").get("fBrown.aCS.s1CS")
								+ environment.get("CONTINENTAL").get("fIntercept.aCS.s1CS")
								+ environment.get("CONTINENTAL").get("fGrav.aCS.s1CS"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCS.s1CS",
				1. / (environment.get("CONTINENTAL").get("AEROresist.s1C")
						+ environment.get("CONTINENTAL").get("SURFresist.s1CS"))
						+ environment.get("CONTINENTAL").get("SettvelS.aC"));

		environment.get("CONTINENTAL").put("k.aCS.s1CS",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCS.s1CS")
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("AEROSOLdeprate.C", 0.001);

		environment.get("CONTINENTAL").put("k.aCP.w0CP",
				(environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.aC"));

		environment.get("CONTINENTAL").put("k.aCP.w1CP",
				(environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.aC"));

		environment.get("CONTINENTAL").put("k.aCP.w2CP",
				(environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.aC"));

		environment.get("CONTINENTAL").put("AEROresist.w0C", 74.);
		environment.get("CONTINENTAL").put("FRICvelocity.wC", 0.19);

		environment.get("CONTINENTAL").put("fBrown.aCS.w",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberS.aC"), -1. / 2.));

		environment.get("CONTINENTAL").put("fGrav.aCS.w",
				Math.pow(10, (-3. / environment.get("CONTINENTAL").get("StokesNumberS.aC"))));

		environment.get("CONTINENTAL").put("SURFresist.w0CS",
				1. / (environment.get("CONTINENTAL").get("FRICvelocity.wC")
						* (environment.get("CONTINENTAL").get("fBrown.aCS.w")
								+ environment.get("CONTINENTAL").get("fGrav.aCS.w"))));

		environment.get("CONTINENTAL").put("fBrown.aCA.w",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberA.aC"), -1. / 2.));

		environment.get("CONTINENTAL").put("fGrav.aCA.w",
				Math.pow(10, (-3. / environment.get("CONTINENTAL").get("StokesNumberA.aC"))));

		environment.get("CONTINENTAL").put("SURFresist.wCA",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.wC")
						* (environment.get("CONTINENTAL").get("fBrown.aCA.w")
								+ environment.get("CONTINENTAL").get("fGrav.aCA.w"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.w0CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w0C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.w0CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.w0CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("AEROresist.w1C", 74.);

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.w1CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w1C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.w1CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.w1CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("AEROresist.w2C", 135.);

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.w2CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w2C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.w2CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.w2CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.w0CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w0C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.w0CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.w0CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("SURFresist.wCS",
				1. / (environment.get("CONTINENTAL").get("FRICvelocity.wC")
						* (environment.get("CONTINENTAL").get("fBrown.aCS.w")
								+ environment.get("CONTINENTAL").get("fGrav.aCS.w"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCS.w2CS",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w2C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCS"))
						+ environment.get("CONTINENTAL").get("SettvelS.aC"));

		environment.get("CONTINENTAL").put("k.aCS.w2CS",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCS.w2CS")
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("DEPvelocity.aCS.w1CS",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w1C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCS"))
						+ environment.get("CONTINENTAL").get("SettvelS.aC"));

		environment.get("CONTINENTAL").put("k.aCS.w1CS",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCS.w1CS")
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("DEPvelocity.aCS.w0CS",
				1. / (environment.get("CONTINENTAL").get("AEROresist.w0C")
						+ environment.get("CONTINENTAL").get("SURFresist.wCS"))
						+ environment.get("CONTINENTAL").get("SettvelS.aC"));

		environment.get("CONTINENTAL").put("k.aCS.w0CS",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCS.w0CS")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("k.aCP.s1CP",
				(environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("k.aCP.s2CP",
				(environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("k.aCP.s3CP",
				(environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("fGrav.aCAs3CA",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberA.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberA.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("fIntercept.aCA.s3CA",
				0.3 * (environment.get("CONTINENTAL").get("AREAFRACveg.s3C")
						* (environment.get("CONTINENTAL").get("RadA.aC")
								/ (environment.get("CONTINENTAL").get("RadA.aC")
										+ environment.get("CONTINENTAL").get("veghair.s3C")))
						+ (1 - environment.get("CONTINENTAL").get("AREAFRACveg.s3C"))
								* (environment.get("CONTINENTAL").get("RadA.aC")
										/ (environment.get("CONTINENTAL").get("RadA.aC")
												+ environment.get("CONTINENTAL").get("largevegradius.s3C")))));

		environment.get("CONTINENTAL").put("fBrown.aCA.s3CA",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberA.aC"), -2. / 3.));

		environment.get("CONTINENTAL").put("SURFresist.s3CA",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.s3C")
						* (environment.get("CONTINENTAL").get("fBrown.aCA.s3CA")
								+ environment.get("CONTINENTAL").get("fIntercept.aCA.s3CA")
								+ environment.get("CONTINENTAL").get("fGrav.aCAs3CA"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.s3CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.s3C")
						+ environment.get("CONTINENTAL").get("SURFresist.s3CA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.s3CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.s3CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("fGrav.aCAs2CA",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberA.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberA.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("fIntercept.aCA.s2CA",
				0.3 * (environment.get("CONTINENTAL").get("AREAFRACveg.s2C")
						* (environment.get("CONTINENTAL").get("RadA.aC")
								/ (environment.get("CONTINENTAL").get("RadA.aC")
										+ environment.get("CONTINENTAL").get("veghair.s2C")))
						+ (1 - environment.get("CONTINENTAL").get("AREAFRACveg.s2C"))
								* (environment.get("CONTINENTAL").get("RadA.aC")
										/ (environment.get("CONTINENTAL").get("RadA.aC")
												+ environment.get("CONTINENTAL").get("largevegradius.s2C")))));

		environment.get("CONTINENTAL").put("fBrown.aCA.s2CA",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberA.aC"), -2. / 3.));

		environment.get("CONTINENTAL").put("SURFresist.s2CA",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.s2C")
						* (environment.get("CONTINENTAL").get("fBrown.aCA.s2CA")
								+ environment.get("CONTINENTAL").get("fIntercept.aCA.s2CA")
								+ environment.get("CONTINENTAL").get("fGrav.aCAs2CA"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.s2CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.s2C")
						+ environment.get("CONTINENTAL").get("SURFresist.s2CA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.s2CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.s2CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("fGrav.aCAs1CA",
				Math.pow((environment.get("CONTINENTAL").get("StokesNumberA.aC")
						/ (environment.get("CONTINENTAL").get("StokesNumberA.aC") + 0.8)), 2));

		environment.get("CONTINENTAL").put("fIntercept.aCA.s1CA",
				0.3 * (environment.get("CONTINENTAL").get("AREAFRACveg.s1C")
						* (environment.get("CONTINENTAL").get("RadA.aC")
								/ (environment.get("CONTINENTAL").get("RadA.aC")
										+ environment.get("CONTINENTAL").get("veghair.s1C")))
						+ (1 - environment.get("CONTINENTAL").get("AREAFRACveg.s1C"))
								* (environment.get("CONTINENTAL").get("RadA.aC")
										/ (environment.get("CONTINENTAL").get("RadA.aC")
												+ environment.get("CONTINENTAL").get("largevegradius.s1C")))));

		environment.get("CONTINENTAL").put("fBrown.aCA.s1CA",
				Math.pow(environment.get("CONTINENTAL").get("SchmidtNumberA.aC"), -2. / 3.));

		environment.get("CONTINENTAL").put("SURFresist.s1CA",
				1 / (environment.get("CONTINENTAL").get("FRICvelocity.s1C")
						* (environment.get("CONTINENTAL").get("fBrown.aCA.s1CA")
								+ environment.get("CONTINENTAL").get("fIntercept.aCA.s1CA")
								+ environment.get("CONTINENTAL").get("fGrav.aCAs1CA"))));

		environment.get("CONTINENTAL").put("DEPvelocity.aCA.s1CA",
				1. / (environment.get("CONTINENTAL").get("AEROresist.s1C")
						+ environment.get("CONTINENTAL").get("SURFresist.s1CA"))
						+ environment.get("CONTINENTAL").get("SettvelA.aC"));

		environment.get("CONTINENTAL").put("k.aCA.s1CA",
				(environment.get("CONTINENTAL").get("DEPvelocity.aCA.s1CA")
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ (environment.get("CONTINENTAL").get("VOLUME.aC")));

		environment.get("CONTINENTAL").put("k.cwCP.w0CP",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCP.w1CP",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCP.w2CP",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCS.w0CS",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCS.w1CS",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCS.w2CS",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCA.w0CA",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCA.w1CA",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCA.w2CA",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCA.s1CA",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCA.s2CA",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCA.s3CA",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCP.s1CP",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCP.s2CP",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCP.s3CP",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCS.s1CS",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCS.s2CS",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		environment.get("CONTINENTAL").put("k.cwCS.s3CS",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C")
						* environment.get("CONTINENTAL").get("SYSTEMAREA.C"))
						/ environment.get("CONTINENTAL").get("VOLUME.cwC"));

		if (inputEng.nanoProperties.get("SetVelP.w") / environment.get("CONTINENTAL").get("DEPTH.w0C") > 0)
			environment.get("CONTINENTAL").put("k.w0CP.sd0CP",
					inputEng.nanoProperties.get("SetVelP.w") / environment.get("CONTINENTAL").get("DEPTH.w0C"));
		else
			environment.get("CONTINENTAL").put("k.w0CP.sd0CP", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") / environment.get("CONTINENTAL").get("DEPTH.w1C") > 0)
			environment.get("CONTINENTAL").put("k.w1CP.sd1CP",
					inputEng.nanoProperties.get("SetVelP.w") / environment.get("CONTINENTAL").get("DEPTH.w1C"));
		else
			environment.get("CONTINENTAL").put("k.w1CP.sd1CP", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") / environment.get("CONTINENTAL").get("DEPTH.w2C") > 0)
			environment.get("CONTINENTAL").put("k.w2CP.sd2CP",
					inputEng.nanoProperties.get("SetVelP.w") / environment.get("CONTINENTAL").get("DEPTH.w2C"));
		else
			environment.get("CONTINENTAL").put("k.w2CP.sd2CP", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") / environment.get("CONTINENTAL").get("DEPTH.w0C") > 0)
			environment.get("CONTINENTAL").put("k.w0CA.sd0CA",
					inputEng.nanoProperties.get("SetVelA.w") / environment.get("CONTINENTAL").get("DEPTH.w0C"));
		else
			environment.get("CONTINENTAL").put("k.w0CA.sd0CA", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") / environment.get("CONTINENTAL").get("DEPTH.w1C") > 0)
			environment.get("CONTINENTAL").put("k.w1CA.sd1CA",
					inputEng.nanoProperties.get("SetVelA.w") / environment.get("CONTINENTAL").get("DEPTH.w1C"));
		else
			environment.get("CONTINENTAL").put("k.w1CA.sd0CA", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") / environment.get("CONTINENTAL").get("DEPTH.w2C") > 0)
			environment.get("CONTINENTAL").put("k.w2CA.sd2CA",
					inputEng.nanoProperties.get("SetVelA.w") / environment.get("CONTINENTAL").get("DEPTH.w2C"));
		else
			environment.get("CONTINENTAL").put("k.w2CA.sd2CA", 0.);

		if (inputEng.nanoProperties.get("SetVelS.w") / environment.get("CONTINENTAL").get("DEPTH.w0C") > 0)
			environment.get("CONTINENTAL").put("k.w0CS.sd0CS",
					inputEng.nanoProperties.get("SetVelS.w") / environment.get("CONTINENTAL").get("DEPTH.w0C"));
		else
			environment.get("CONTINENTAL").put("k.w0CS.sd0CS", 0.);

		if (inputEng.nanoProperties.get("SetVelS.w") / environment.get("CONTINENTAL").get("DEPTH.w1C") > 0)
			environment.get("CONTINENTAL").put("k.w1CS.sd1CS",
					inputEng.nanoProperties.get("SetVelS.w") / environment.get("CONTINENTAL").get("DEPTH.w1C"));
		else
			environment.get("CONTINENTAL").put("k.w1CS.sd1CS", 0.);

		if (inputEng.nanoProperties.get("SetVelS.w") / environment.get("CONTINENTAL").get("DEPTH.w2C") > 0)
			environment.get("CONTINENTAL").put("k.w2CS.sd2CS",
					inputEng.nanoProperties.get("SetVelS.w") / environment.get("CONTINENTAL").get("DEPTH.w2C"));
		else
			environment.get("CONTINENTAL").put("k.w2CS.sd2CS", 0.);

		environment.get("CONTINENTAL").put("k.sd0CS.w0CS", environment.get("CONTINENTAL").get("RESUSP.sd0C.w0C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd0C"));

		environment.get("CONTINENTAL").put("k.sd1CS.w1CS", environment.get("CONTINENTAL").get("RESUSP.sd1C.w1C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("k.sd2CS.w2CS", environment.get("CONTINENTAL").get("RESUSP.sd2C.w2C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("k.sd0CA.w0CA", environment.get("CONTINENTAL").get("RESUSP.sd0C.w0C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd0C"));

		environment.get("CONTINENTAL").put("k.sd1CA.w1CA", environment.get("CONTINENTAL").get("RESUSP.sd1C.w1C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("k.sd2CA.w2CA", environment.get("CONTINENTAL").get("RESUSP.sd2C.w2C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd2C"));

		environment.get("CONTINENTAL").put("k.sd0CP.w0CP", environment.get("CONTINENTAL").get("RESUSP.sd0C.w0C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd0C"));

		environment.get("CONTINENTAL").put("k.sd1CP.w1CP", environment.get("CONTINENTAL").get("RESUSP.sd1C.w1C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("k.sd2CP.w2CP", environment.get("CONTINENTAL").get("RESUSP.sd2C.w2C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd2C"));

		environment.get("CONTINENTAL").put("FRACinf.s1C", inputEng.getLandscapeSettings("CONTINENTAL", "FRACinf.C"));
		environment.get("CONTINENTAL").put("FRACinf.s2C", inputEng.getLandscapeSettings("CONTINENTAL", "FRACinf.C"));
		environment.get("CONTINENTAL").put("FRACinf.s3C", inputEng.getLandscapeSettings("CONTINENTAL", "FRACinf.C"));

		environment.get("CONTINENTAL").put("kesc.aC", Math.log(2) / (60. * 365. * 24. * 3600.));

		environment.get("CONTINENTAL").put("k.s1CS.w1CS",
				((environment.get("CONTINENTAL").get("FRACrun.s1C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRrunoff.s1C")
						+ environment.get("CONTINENTAL").get("EROSION.s1C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s1C")));

		environment.get("CONTINENTAL").put("k.s2CS.w1CS",
				((environment.get("CONTINENTAL").get("FRACrun.s2C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRrunoff.s2C")
						+ environment.get("CONTINENTAL").get("EROSION.s2C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s2C")));

		environment.get("CONTINENTAL").put("k.s3CS.w1CS",
				((environment.get("CONTINENTAL").get("FRACrun.s3C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRrunoff.s3C")
						+ environment.get("CONTINENTAL").get("EROSION.s3C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s3C")));

		environment.get("CONTINENTAL").put("k.s1CA.w1CA",
				((environment.get("CONTINENTAL").get("FRACrun.s1C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRrunoff.s1C")
						+ environment.get("CONTINENTAL").get("EROSION.s1C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s1C")));

		environment.get("CONTINENTAL").put("k.s2CA.w1CA",
				((environment.get("CONTINENTAL").get("FRACrun.s2C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRrunoff.s2C")
						+ environment.get("CONTINENTAL").get("EROSION.s2C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s2C")));

		environment.get("CONTINENTAL").put("k.s3CA.w1CA",
				((environment.get("CONTINENTAL").get("FRACrun.s3C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRrunoff.s3C")
						+ environment.get("CONTINENTAL").get("EROSION.s3C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s3C")));

		environment.get("CONTINENTAL").put("k.s1CP.w1CP",
				(environment.get("CONTINENTAL").get("EROSION.s1C") / environment.get("CONTINENTAL").get("DEPTH.s1C")));

		environment.get("CONTINENTAL").put("k.s2CP.w1CP",
				(environment.get("CONTINENTAL").get("EROSION.s2C") / environment.get("CONTINENTAL").get("DEPTH.s2C")));

		environment.get("CONTINENTAL").put("k.s3CP.w1CP",
				(environment.get("CONTINENTAL").get("EROSION.s3C") / environment.get("CONTINENTAL").get("DEPTH.s3C")));

		environment.get("CONTINENTAL").put("kbur.sd0C", environment.get("CONTINENTAL").get("NETsedrate.w0C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd0C"));

		environment.get("CONTINENTAL").put("kbur.sd1C", environment.get("CONTINENTAL").get("NETsedrate.w1C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("kbur.sd2C", environment.get("CONTINENTAL").get("NETsedrate.w2C")
				/ environment.get("CONTINENTAL").get("DEPTH.sd2C"));

		environment.get("CONTINENTAL").put("kleach.s1C",
				((environment.get("CONTINENTAL").get("FRACinf.s1C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRleach.s1C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s1C")));

		environment.get("CONTINENTAL").put("kleach.s2C",
				((environment.get("CONTINENTAL").get("FRACinf.s2C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRleach.s2C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s2C")));

		environment.get("CONTINENTAL").put("kleach.s3C",
				((environment.get("CONTINENTAL").get("FRACinf.s3C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("CORRleach.s3C"))
						/ environment.get("CONTINENTAL").get("DEPTH.s3C")));

		environment.get("CONTINENTAL").put("k.aCS",
				environment.get("CONTINENTAL").get("kesc.aC") + inputEng.getSubstancesData("kdegS.a"));

		environment.get("CONTINENTAL").put("k.aCP",
				environment.get("CONTINENTAL").get("kesc.aC") + inputEng.getSubstancesData("kdegP.a"));

		environment.get("CONTINENTAL").put("k.w0CS", inputEng.getSubstancesData("kdegS.w0"));

		environment.get("CONTINENTAL").put("k.w0CA", inputEng.getSubstancesData("kdegA.w0"));

		environment.get("CONTINENTAL").put("k.w0CP", inputEng.getSubstancesData("kdegP.w0"));

		environment.get("CONTINENTAL").put("k.w1CS", inputEng.getSubstancesData("kdegS.w1"));

		environment.get("CONTINENTAL").put("k.w1CA", inputEng.getSubstancesData("kdegA.w1"));

		environment.get("CONTINENTAL").put("k.w1CP", inputEng.getSubstancesData("kdegP.w1"));

		environment.get("CONTINENTAL").put("k.w2CS", inputEng.getSubstancesData("kdegS.w2"));

		environment.get("CONTINENTAL").put("k.w2CP", inputEng.getSubstancesData("kdegP.w2"));

		environment.get("CONTINENTAL").put("k.sd0CS",
				environment.get("CONTINENTAL").get("kbur.sd0C") + inputEng.getSubstancesData("kdegS.sd0"));

		environment.get("CONTINENTAL").put("k.sd0CA",
				environment.get("CONTINENTAL").get("kbur.sd0C") + inputEng.getSubstancesData("kdegA.sd0"));

		environment.get("CONTINENTAL").put("k.sd0CP",
				environment.get("CONTINENTAL").get("kbur.sd0C") + inputEng.getSubstancesData("kdegP.sd0"));

		environment.get("CONTINENTAL").put("k.sd1CS",
				environment.get("CONTINENTAL").get("kbur.sd1C") + inputEng.getSubstancesData("kdegS.sd1"));

		environment.get("CONTINENTAL").put("k.sd1CA",
				environment.get("CONTINENTAL").get("kbur.sd1C") + inputEng.getSubstancesData("kdegA.sd1"));

		environment.get("CONTINENTAL").put("k.sd1CP",
				environment.get("CONTINENTAL").get("kbur.sd1C") + inputEng.getSubstancesData("kdegP.sd1"));

		environment.get("CONTINENTAL").put("k.sd2CS",
				environment.get("CONTINENTAL").get("kbur.sd2C") + inputEng.getSubstancesData("kdegS.sd2"));

		environment.get("CONTINENTAL").put("k.sd2CA",
				environment.get("CONTINENTAL").get("kbur.sd2C") + inputEng.getSubstancesData("kdegA.sd2"));

		environment.get("CONTINENTAL").put("k.sd2CP",
				environment.get("CONTINENTAL").get("kbur.sd2C") + inputEng.getSubstancesData("kdegP.sd2"));

		environment.get("CONTINENTAL").put("k.s1CS",
				environment.get("CONTINENTAL").get("kleach.s1C") + inputEng.getSubstancesData("kdegS.s1"));

		environment.get("CONTINENTAL").put("k.s1CA",
				environment.get("CONTINENTAL").get("kleach.s1C") + inputEng.getSubstancesData("kdegA.s1"));

		environment.get("CONTINENTAL").put("k.s1CP", inputEng.getSubstancesData("kdegP.s1"));

		environment.get("CONTINENTAL").put("k.s2CS",
				environment.get("CONTINENTAL").get("kleach.s2C") + inputEng.getSubstancesData("kdegS.s2"));

		environment.get("CONTINENTAL").put("k.s2CA",
				environment.get("CONTINENTAL").get("kleach.s2C") + inputEng.getSubstancesData("kdegA.s2"));

		environment.get("CONTINENTAL").put("k.s2CP", inputEng.getSubstancesData("kdegP.s2"));

		environment.get("CONTINENTAL").put("k.s3CS",
				environment.get("CONTINENTAL").get("kleach.s3C") + inputEng.getSubstancesData("kdegS.s3"));

		environment.get("CONTINENTAL").put("k.s3CA",
				environment.get("CONTINENTAL").get("kleach.s3C") + inputEng.getSubstancesData("kdegA.s3"));

		environment.get("CONTINENTAL").put("k.s3CP", inputEng.getSubstancesData("kdegP.s3"));

		environment.get("CONTINENTAL").put("Tempfactor.aC", Math.exp((inputEng.getSubstancesData("Ea.OHrad") / 8.314)
				* (inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C") - 298.) / Math.pow(298, 2)));

		environment.get("CONTINENTAL").put("C.OHrad.aC", 500000.);

		environment.get("CONTINENTAL").put("KDEG.aC",
				environment.get("CONTINENTAL").get("FRgas.aC") * inputEng.getSubstancesData("kdegG.air")
						* (environment.get("CONTINENTAL").get("C.OHrad.aC") / inputEng.getSubstancesData("C.OHrad"))
						* environment.get("CONTINENTAL").get("Tempfactor.aC"));

		environment.get("CONTINENTAL").put("k.aC",
				environment.get("CONTINENTAL").get("kesc.aC") + environment.get("CONTINENTAL").get("KDEG.aC"));

		environment.get("CONTINENTAL").put("Tempfactor.wsdsC", Math.pow(inputEng.getSubstancesData("Q.10"),
				((inputEng.getLandscapeSettings("CONTINENTAL", "TEMP.C") - 298.) / 10.)));

		environment.get("CONTINENTAL").put("KDEG.sd0C",
				environment.get("CONTINENTAL").get("Tempfactor.wsdsC") * inputEng.getSubstancesData("kdegD.sed"));
		environment.get("CONTINENTAL").put("KDEG.sd1C",
				environment.get("CONTINENTAL").get("Tempfactor.wsdsC") * inputEng.getSubstancesData("kdegD.sed"));
		environment.get("CONTINENTAL").put("KDEG.sd2C",
				environment.get("CONTINENTAL").get("Tempfactor.wsdsC") * inputEng.getSubstancesData("kdegD.sed"));

		environment.get("CONTINENTAL").put("KDEG.s1C",
				environment.get("CONTINENTAL").get("Tempfactor.wsdsC") * inputEng.getSubstancesData("kdegD.soil"));
		environment.get("CONTINENTAL").put("KDEG.s2C",
				environment.get("CONTINENTAL").get("Tempfactor.wsdsC") * inputEng.getSubstancesData("kdegD.soil"));
		environment.get("CONTINENTAL").put("KDEG.s3C",
				environment.get("CONTINENTAL").get("Tempfactor.wsdsC") * inputEng.getSubstancesData("kdegD.soil"));

		environment.get("CONTINENTAL").put("CORRvolat.s1C",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s1C")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s1C")))));
		environment.get("CONTINENTAL").put("CORRvolat.s2C",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s2C")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s2C")))));
		environment.get("CONTINENTAL").put("CORRvolat.s3C",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s3C")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("CONTINENTAL").get("DEPTH.s3C")))));

		environment.get("CONTINENTAL").put("MTCas.soil.sC", 0.1 * environment.get("CONTINENTAL").get("KDEG.s1C"));
		environment.get("CONTINENTAL").put("MTCas.air.aC", 0.43 / (24. * 3600.) / 0.00475);

		environment.get("CONTINENTAL").put("VOLAT.s1C.aC", (environment.get("CONTINENTAL").get("MTCas.air.aC")
				* environment.get("CONTINENTAL").get("MTCas.soil.sC"))
				/ (environment.get("CONTINENTAL").get("MTCas.air.aC") + environment.get("CONTINENTAL")
						.get("MTCas.soil.sC")
						/ ((environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s1w"))
								/ environment.get("CONTINENTAL").get("Ks1w.C")))
				* environment.get("CONTINENTAL").get("CORRvolat.s1C"));

		environment.get("CONTINENTAL").put("VOLAT.s2C.aC", (environment.get("CONTINENTAL").get("MTCas.air.aC")
				* environment.get("CONTINENTAL").get("MTCas.soil.sC"))
				/ (environment.get("CONTINENTAL").get("MTCas.air.aC") + environment.get("CONTINENTAL")
						.get("MTCas.soil.sC")
						/ ((environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s2w"))
								/ environment.get("CONTINENTAL").get("Ks2w.C")))
				* environment.get("CONTINENTAL").get("CORRvolat.s2C"));

		environment.get("CONTINENTAL").put("VOLAT.s3C.aC", (environment.get("CONTINENTAL").get("MTCas.air.aC")
				* environment.get("CONTINENTAL").get("MTCas.soil.sC"))
				/ (environment.get("CONTINENTAL").get("MTCas.air.aC") + environment.get("CONTINENTAL")
						.get("MTCas.soil.sC")
						/ ((environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s3w"))
								/ environment.get("CONTINENTAL").get("Ks2w.C")))
				* environment.get("CONTINENTAL").get("CORRvolat.s3C"));

		environment.get("CONTINENTAL").put("MTCaw.air.aC",
				0.01 * (0.3 + 0.2 * inputEng.getLandscapeSettings("CONTINENTAL", "WINDspeed.C"))
						* (Math.pow(0.018 / inputEng.getSubstancesData("Molweight"), 0.67 * 0.5)));

		environment.get("CONTINENTAL").put("MTCaw.water.wC",
				0.01 * (0.0004 + 0.00004 * inputEng.getLandscapeSettings("CONTINENTAL", "WINDspeed.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "WINDspeed.C"))
						* (Math.pow(0.032 / inputEng.getSubstancesData("Molweight"), 0.5 * 0.5)));

		environment.get("CONTINENTAL").put("VOLAT.w0C.aC", (environment.get("CONTINENTAL").get("MTCaw.air.aC")
				* environment.get("CONTINENTAL").get("MTCaw.water.wC")
				/ (environment.get("CONTINENTAL").get("MTCaw.air.aC")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w1"))
						+ environment.get("CONTINENTAL").get("MTCaw.water.wC")))
				* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w1"))
				* environment.get("CONTINENTAL").get("FRw.w0C"));

		environment.get("CONTINENTAL").put("VOLAT.w1C.aC", (environment.get("CONTINENTAL").get("MTCaw.air.aC")
				* environment.get("CONTINENTAL").get("MTCaw.water.wC")
				/ (environment.get("CONTINENTAL").get("MTCaw.air.aC")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w1"))
						+ environment.get("CONTINENTAL").get("MTCaw.water.wC")))
				* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w1"))
				* environment.get("CONTINENTAL").get("FRw.w1C"));

		environment.get("CONTINENTAL").put("VOLAT.w2C.aC", (environment.get("CONTINENTAL").get("MTCaw.air.aC")
				* environment.get("CONTINENTAL").get("MTCaw.water.wC")
				/ (environment.get("CONTINENTAL").get("MTCaw.air.aC")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w2"))
						+ environment.get("CONTINENTAL").get("MTCaw.water.wC")))
				* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w2"))
				* environment.get("CONTINENTAL").get("FRw.w2C"));

		environment.get("CONTINENTAL").put("k.s1CD.aCG",
				environment.get("CONTINENTAL").get("VOLAT.s1C.aC") / environment.get("CONTINENTAL").get("DEPTH.s1C"));

		environment.get("CONTINENTAL").put("k.s2CD.aCG",
				environment.get("CONTINENTAL").get("VOLAT.s2C.aC") / environment.get("CONTINENTAL").get("DEPTH.s2C"));

		environment.get("CONTINENTAL").put("k.s3CD.aCG",
				environment.get("CONTINENTAL").get("VOLAT.s3C.aC") / environment.get("CONTINENTAL").get("DEPTH.s3C"));

		environment.get("CONTINENTAL").put("k.w0CD.aCG",
				environment.get("CONTINENTAL").get("VOLAT.w0C.aC") / environment.get("CONTINENTAL").get("DEPTH.w0C"));

		environment.get("CONTINENTAL").put("k.w1CD.aCG",
				environment.get("CONTINENTAL").get("VOLAT.w1C.aC") / environment.get("CONTINENTAL").get("DEPTH.w1C"));

		environment.get("CONTINENTAL").put("k.w2CD.aCG",
				environment.get("CONTINENTAL").get("VOLAT.w2C.aC") / environment.get("CONTINENTAL").get("DEPTH.w2C"));

		environment.get("CONTINENTAL").put("GASABS.aC.wC", 
				environment.get("CONTINENTAL").get("FRgas.aC")*( environment.get("CONTINENTAL").get("MTCaw.air.aC")
				* environment.get("CONTINENTAL").get("MTCaw.water.wC")
				/ ( environment.get("CONTINENTAL").get("MTCaw.air.aC")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w1")  )
						+ environment.get("CONTINENTAL").get("MTCaw.water.wC") ) ) );

		environment.get("CONTINENTAL").put("GASABS.aC.sC", 
				environment.get("CONTINENTAL").get("FRgas.aC") * environment.get("CONTINENTAL").get("MTCas.air.aC")
				* environment.get("CONTINENTAL").get("MTCas.soil.sC")
				/ (environment.get("CONTINENTAL").get("MTCas.air.aC")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.w1") )
						+ environment.get("CONTINENTAL").get("MTCas.soil.sC")));

		environment.get("CONTINENTAL").put("GasWashout.CG",
				environment.get("CONTINENTAL").get("FRgas.aC")
						* (environment.get("CONTINENTAL").get("tdry.C") + environment.get("CONTINENTAL").get("twet.C"))
						/ environment.get("CONTINENTAL").get("twet.C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("CONTINENTAL").put("AerosolWashout.C", environment.get("CONTINENTAL").get("FRaers.aC")
				* (environment.get("CONTINENTAL").get("tdry.C") + environment.get("CONTINENTAL").get("twet.C"))
				/ environment.get("CONTINENTAL").get("twet.C") * environment.get("CONTINENTAL").get("COLLECTeff.C")
				* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C"));

		environment.get("CONTINENTAL").put("DRYDEPaerosol.C", environment.get("CONTINENTAL").get("AEROSOLdeprate.C")
				* (environment.get("CONTINENTAL").get("FRaerw.aC") + environment.get("CONTINENTAL").get("FRaers.aC")));

		environment.get("CONTINENTAL").put("kwet.C",
				(environment.get("CONTINENTAL").get("AerosolWashout.C")
						+ environment.get("CONTINENTAL").get("GasWashout.CG"))
						/ environment.get("CONTINENTAL").get("HEIGHT.aC")
						+ (environment.get("CONTINENTAL").get("GASABS.aC.wC")
								* (environment.get("CONTINENTAL").get("AREAFRAC.w0C")
										+ environment.get("CONTINENTAL").get("AREAFRAC.w1C")
										+ environment.get("CONTINENTAL").get("AREAFRAC.w2C"))
								+ environment.get("CONTINENTAL").get("GASABS.aC.sC")
										* (environment.get("CONTINENTAL").get("AREAFRAC.s1C")
												+ environment.get("CONTINENTAL").get("AREAFRAC.s2C")
												+ environment.get("CONTINENTAL").get("AREAFRAC.s3C")))
								/ environment.get("CONTINENTAL").get("HEIGHT.aC")
						+ environment.get("CONTINENTAL").get("KDEG.aC")
						+ environment.get("CONTINENTAL").get("k.aC.aR"));

		environment.get("CONTINENTAL").put("kdry.C",
				environment.get("CONTINENTAL").get("DRYDEPaerosol.C") / environment.get("CONTINENTAL").get("HEIGHT.aC")
						+ (environment.get("CONTINENTAL").get("GASABS.aC.wC")
								* (environment.get("CONTINENTAL").get("AREAFRAC.w0C")
										+ environment.get("CONTINENTAL").get("AREAFRAC.w1C")
										+ environment.get("CONTINENTAL").get("AREAFRAC.w2C"))
								+ environment.get("CONTINENTAL").get("GASABS.aC.sC")
										* (environment.get("CONTINENTAL").get("AREAFRAC.s1C")
												+ environment.get("CONTINENTAL").get("AREAFRAC.s2C")
												+ environment.get("CONTINENTAL").get("AREAFRAC.s3C")))
								/ environment.get("CONTINENTAL").get("HEIGHT.aC")
						+ environment.get("CONTINENTAL").get("KDEG.aC") + environment.get("CONTINENTAL").get("k.aC.aR")
						+ environment.get("CONTINENTAL").get("k.aC.aM"));

		environment.get("CONTINENTAL").put("MeanRem.aC", 1. / ((1. / environment.get("CONTINENTAL").get("kdry.C"))
				* environment.get("CONTINENTAL").get("tdry.C")
				/ (environment.get("CONTINENTAL").get("tdry.C") + environment.get("CONTINENTAL").get("twet.C"))
				+ (1. / environment.get("CONTINENTAL").get("kwet.C")) * environment.get("CONTINENTAL").get("twet.C")
						/ (environment.get("CONTINENTAL").get("tdry.C") + environment.get("CONTINENTAL").get("twet.C"))
				- (Math.pow(1. / environment.get("CONTINENTAL").get("kwet.C")
						- 1. / environment.get("CONTINENTAL").get("kdry.C"), 2)
						/ (environment.get("CONTINENTAL").get("tdry.C") + environment.get("CONTINENTAL").get("twet.C")))
						* (1. - Math.exp(-environment.get("CONTINENTAL").get("kdry.C")
								* environment.get("CONTINENTAL").get("tdry.C")))
						* (1. - Math.exp(-environment.get("CONTINENTAL").get("kwet.C")
								* environment.get("CONTINENTAL").get("twet.C")))
						/ (1. - Math.exp(-environment.get("CONTINENTAL").get("kdry.C")
								* environment.get("CONTINENTAL").get("tdry.C")
								- environment.get("CONTINENTAL").get("kwet.C")
										* environment.get("CONTINENTAL").get("twet.C")))));

		double val3 = environment.get("CONTINENTAL").get("MeanRem.aC");
		double val4 = (environment.get("CONTINENTAL").get("GASABS.aC.wC")
				* (environment.get("CONTINENTAL").get("AREAFRAC.w0C")
						+ environment.get("CONTINENTAL").get("AREAFRAC.w1C")
						+ environment.get("CONTINENTAL").get("AREAFRAC.w2C"))
				+ environment.get("CONTINENTAL").get("GASABS.aC.sC")
						* (environment.get("CONTINENTAL").get("AREAFRAC.s1C")
								+ environment.get("CONTINENTAL").get("AREAFRAC.s2C")
								+ environment.get("CONTINENTAL").get("AREAFRAC.s3C")))
				/ environment.get("CONTINENTAL").get("HEIGHT.aC") + environment.get("CONTINENTAL").get("KDEG.aC")
				+ environment.get("CONTINENTAL").get("k.aC.aR") + environment.get("CONTINENTAL").get("k.aC.aM");

		environment.get("CONTINENTAL").put("MeanDep.C", val3 - val4);

		environment.get("CONTINENTAL").put("k.aCG.w0CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.wC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.w0C"));

		environment.get("CONTINENTAL").put("k.aCG.w1CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.wC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.w1C"));

		environment.get("CONTINENTAL").put("k.aCG.w2CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.wC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.w2C"));

		environment.get("CONTINENTAL").put("k.aCG.s1CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.sC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.s1C"));

		environment.get("CONTINENTAL").put("k.aCG.s2CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.sC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.s2C"));

		environment.get("CONTINENTAL").put("k.aCG.s3CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.sC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C"));

		environment.get("CONTINENTAL").put("k.aCG.s3CD",
				(environment.get("CONTINENTAL").get("MeanDep.C") + environment.get("CONTINENTAL").get("GASABS.aC.sC")
						/ environment.get("CONTINENTAL").get("HEIGHT.aC"))
						* environment.get("CONTINENTAL").get("AREAFRAC.s3C"));

		environment.get("CONTINENTAL").put("k.s1C.w1C",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("FRACrun.s1C")
						/ environment.get("CONTINENTAL").get("Ks1w.C")
						+ environment.get("CONTINENTAL").get("EROSION.s1C"))
						* environment.get("CONTINENTAL").get("CORRrunoff.s1C")
						/ environment.get("CONTINENTAL").get("DEPTH.s1C"));

		environment.get("CONTINENTAL").put("k.s2C.w1C",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("FRACrun.s2C")
						/ environment.get("CONTINENTAL").get("Ks2w.C")
						+ environment.get("CONTINENTAL").get("EROSION.s2C"))
						* environment.get("CONTINENTAL").get("CORRrunoff.s2C")
						/ environment.get("CONTINENTAL").get("DEPTH.s2C"));

		environment.get("CONTINENTAL").put("k.s3C.w1C",
				(inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						* environment.get("CONTINENTAL").get("FRACrun.s3C")
						/ environment.get("CONTINENTAL").get("Ks3w.C")
						+ environment.get("CONTINENTAL").get("EROSION.s3C"))
						* environment.get("CONTINENTAL").get("CORRrunoff.s3C")
						/ environment.get("CONTINENTAL").get("DEPTH.s3C"));

		environment.get("CONTINENTAL").put("BACT.w0C", 40000.);
		environment.get("CONTINENTAL").put("BACT.w1C", 40000.);
		environment.get("CONTINENTAL").put("BACT.w2C", 40000.);

		environment.get("CONTINENTAL").put("KDEG.w0C",
				inputEng.getSubstancesData("kdegD.water") * environment.get("CONTINENTAL").get("Tempfactor.wsdsC")
						* (environment.get("CONTINENTAL").get("BACT.w0C") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("CONTINENTAL").get("FRw.w0C"));

		environment.get("CONTINENTAL").put("KDEG.w1C",
				inputEng.getSubstancesData("kdegD.water") * environment.get("CONTINENTAL").get("Tempfactor.wsdsC")
						* (environment.get("CONTINENTAL").get("BACT.w1C") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("CONTINENTAL").get("FRw.w1C"));

		environment.get("CONTINENTAL").put("KDEG.w2C",
				inputEng.getSubstancesData("kdegD.water") * environment.get("CONTINENTAL").get("Tempfactor.wsdsC")
						* (environment.get("CONTINENTAL").get("BACT.w2C") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("CONTINENTAL").get("FRw.w2C"));

		environment.get("CONTINENTAL").put("k.w0CD", environment.get("CONTINENTAL").get("KDEG.w0C"));
		environment.get("CONTINENTAL").put("k.w1CD", environment.get("CONTINENTAL").get("KDEG.w1C"));
		environment.get("CONTINENTAL").put("k.w2CD", environment.get("CONTINENTAL").get("KDEG.w2C"));

		environment.get("CONTINENTAL").put("k.sd0CD",
				environment.get("CONTINENTAL").get("KDEG.sd0C") + environment.get("CONTINENTAL").get("NETsedrate.w0C")
						/ environment.get("CONTINENTAL").get("DEPTH.sd0C"));

		environment.get("CONTINENTAL").put("k.sd1C",
				environment.get("CONTINENTAL").get("KDEG.sd1C") + environment.get("CONTINENTAL").get("NETsedrate.w1C")
						/ environment.get("CONTINENTAL").get("DEPTH.sd1C"));

		environment.get("CONTINENTAL").put("k.sd2C",
				environment.get("CONTINENTAL").get("KDEG.sd2C") + environment.get("CONTINENTAL").get("NETsedrate.w2C")
						/ environment.get("CONTINENTAL").get("DEPTH.sd2C"));

		environment.get("CONTINENTAL").put("k.s1C",
				environment.get("CONTINENTAL").get("KDEG.s1C") + environment.get("CONTINENTAL").get("FRACinf.s1C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ environment.get("CONTINENTAL").get("Ks1w.C")
						* environment.get("CONTINENTAL").get("CORRleach.s1C")
						/ environment.get("CONTINENTAL").get("DEPTH.s1C"));

		environment.get("CONTINENTAL").put("k.s2C",
				environment.get("CONTINENTAL").get("KDEG.s2C") + environment.get("CONTINENTAL").get("FRACinf.s2C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ environment.get("CONTINENTAL").get("Ks2w.C")
						* environment.get("CONTINENTAL").get("CORRleach.s2C")
						/ environment.get("CONTINENTAL").get("DEPTH.s2C"));

		environment.get("CONTINENTAL").put("k.s3C",
				environment.get("CONTINENTAL").get("KDEG.s3C") + environment.get("CONTINENTAL").get("FRACinf.s3C")
						* inputEng.getLandscapeSettings("CONTINENTAL", "RAINrate.C")
						/ environment.get("CONTINENTAL").get("Ks3w.C")
						* environment.get("CONTINENTAL").get("CORRleach.s3C")
						/ environment.get("CONTINENTAL").get("DEPTH.s3C"));

		environment.get("CONTINENTAL").put("k.w0C",
				inputEng.getSubstancesData("kdegD.water") * environment.get("CONTINENTAL").get("Tempfactor.wsdsC")
						* (environment.get("CONTINENTAL").get("BACT.w0C") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("CONTINENTAL").get("FRw.w0C"));

		environment.get("CONTINENTAL").put("k.w1C",
				inputEng.getSubstancesData("kdegD.water") * environment.get("CONTINENTAL").get("Tempfactor.wsdsC")
						* (environment.get("CONTINENTAL").get("BACT.w1C") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("CONTINENTAL").get("FRw.w1C"));

		environment.get("CONTINENTAL").put("k.w2C",
				inputEng.getSubstancesData("kdegD.water") * environment.get("CONTINENTAL").get("Tempfactor.wsdsC")
						* (environment.get("CONTINENTAL").get("BACT.w2C") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("CONTINENTAL").get("FRw.w2C"));
	}

	void globalModerate() {
		environment.put("MODERATE", new HashMap<String, Double>());

		environment.get("MODERATE").put("FRACs.sdM", 0.2);
		environment.get("MODERATE").put("FRACw.sdM", 0.8);
		environment.get("MODERATE").put("FRACs.sM", 0.6);
		environment.get("MODERATE").put("FRACa.sM", 0.2);
		environment.get("MODERATE").put("FRACw.sM", 0.2);

		environment.get("MODERATE").put("FRACaerw.aM", 2e-11);
		environment.get("MODERATE").put("FRACaers.aM", 2e-11);

		environment.get("MODERATE").put("FRACcldw.aM", 3e-7);

		environment.get("MODERATE").put("DEPTH.sM", 0.05);
		environment.get("MODERATE").put("DEPTH.sdM", 3. / 100.);

		environment.get("MODERATE").put("DEPTH.w2M", 100.);
		environment.get("MODERATE").put("DEPTH.w3M", 3000.);

		environment.get("MODERATE").put("HEIGHT.aM", 1000.);

		environment.get("MODERATE").put("AREAFRAC.wM", 0.5);

		environment.get("MODERATE").put("AREAFRAC.sM", 1 - environment.get("MODERATE").get("AREAFRAC.wM"));

		environment.get("MODERATE").put("SYSTEMAREA.M",
				(85000000. - ((environment.get("CONTINENTAL").get("SYSTEMAREA.C")
						+ environment.get("REGIONAL").get("SYSTEMAREA.R")) / 1000000.)) * 1000000);

		environment.get("MODERATE").put("VOLUME.aM", environment.get("MODERATE").get("SYSTEMAREA.M")
				* environment.get("MODERATE").get("HEIGHT.aM") * (1. - environment.get("MODERATE").get("FRACcldw.aM")));

		environment.get("MODERATE").put("VOLUME.cwM",
				environment.get("MODERATE").get("VOLUME.aM") * environment.get("MODERATE").get("FRACcldw.aM"));

		environment.get("MODERATE").put("VOLUME.w2M", environment.get("MODERATE").get("SYSTEMAREA.M")
				* environment.get("MODERATE").get("AREAFRAC.wM") * environment.get("MODERATE").get("DEPTH.w2M"));

		environment.get("MODERATE").put("VOLUME.w3M", environment.get("MODERATE").get("SYSTEMAREA.M")
				* environment.get("MODERATE").get("AREAFRAC.wM") * environment.get("MODERATE").get("DEPTH.w3M"));

		environment.get("MODERATE").put("VOLUME.sdM", environment.get("MODERATE").get("SYSTEMAREA.M")
				* environment.get("MODERATE").get("AREAFRAC.wM") * environment.get("MODERATE").get("DEPTH.sdM"));

		environment.get("MODERATE").put("VOLUME.sM", environment.get("MODERATE").get("SYSTEMAREA.M")
				* environment.get("MODERATE").get("AREAFRAC.sM") * environment.get("MODERATE").get("DEPTH.sM"));

		environment.get("MODERATE").put("TAU.surfM", 55. * 3600. * 24.);
		environment.get("MODERATE").put("OceanMixing.M",
				environment.get("MODERATE").get("VOLUME.w2M") / environment.get("MODERATE").get("TAU.surfM"));
		environment.get("MODERATE").put("OceanCurrent", 150000000.);

		environment.get("MODERATE").put("k.w3M.w2M",
				(environment.get("MODERATE").get("OceanCurrent") + environment.get("MODERATE").get("OceanMixing.M"))
						/ environment.get("MODERATE").get("VOLUME.w3M"));

		environment.get("MODERATE").put("k.w2M.w3M",
				(environment.get("MODERATE").get("OceanCurrent") + environment.get("MODERATE").get("OceanMixing.M"))
						/ environment.get("MODERATE").get("VOLUME.w2M"));

		environment.get("MODERATE").put("WATERflow.w2M.w2T", 0.);

		environment.get("MODERATE").put("WATERflow.w2M.w2A", environment.get("MODERATE").get("OceanCurrent"));

		environment.get("MODERATE").put("WATERflow.w2M.w2C", environment.get("CONTINENTAL").get("WATERflow.w2C.w2M"));

		environment.get("MODERATE").put("k.w3M.w3T",
				environment.get("MODERATE").get("OceanCurrent") / environment.get("MODERATE").get("VOLUME.w3M"));

		environment.get("MODERATE").put("k.w3M.w3A", 0.);

		environment.get("MODERATE").put("k.w2M.w2T",
				environment.get("MODERATE").get("WATERflow.w2M.w2T") / environment.get("MODERATE").get("VOLUME.w2M"));

		environment.get("MODERATE").put("k.w2M.w2A",
				environment.get("MODERATE").get("WATERflow.w2M.w2A") / environment.get("MODERATE").get("VOLUME.w2M"));

		environment.get("MODERATE").put("k.w2M.w2C",
				environment.get("MODERATE").get("WATERflow.w2M.w2C") / environment.get("MODERATE").get("VOLUME.w2M"));

		environment.get("MODERATE").put("k.aM.aC", environment.get("CONTINENTAL").get("k.aC.aM")
				* environment.get("CONTINENTAL").get("VOLUME.aC") / environment.get("MODERATE").get("VOLUME.aM"));

		environment.get("MODERATE").put("tdry.M", 3.3 * 24 * 3600.);
		environment.get("MODERATE").put("twet.M", 3600. * 3.3 * 24. * (0.06 / (1. - 0.06)));

		environment.get("MODERATE").put("PRODsusp.wM", environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / (1000. * 3600. * 24. * 365.));

		environment.get("MODERATE").put("NETsedrate.wM", 8.95180294396847E-14);

		environment.get("MODERATE").put("SETTLvelocity.M", 2.5 / (24. * 3600));

		environment.get("MODERATE").put("SUSP.w2M", 5. / 1000.);
		environment.get("MODERATE").put("SUSP.w3M", 5. / 1000.);

		if (environment.get("MODERATE").get("SETTLvelocity.M") * environment.get("MODERATE").get("SUSP.w3M")
				/ (environment.get("MODERATE").get("FRACs.sdM")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("MODERATE")
								.get("NETsedrate.wM")) {

			environment.get("MODERATE").put("GROSSSEDrate.w3M",
					environment.get("MODERATE").get("SETTLvelocity.M") * environment.get("MODERATE").get("SUSP.w3M")
							/ (environment.get("MODERATE").get("FRACs.sdM")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("MODERATE").put("GROSSSEDrate.w3M", environment.get("MODERATE").get("NETsedrate.wM"));

		environment.get("MODERATE").put("RESUSP.sdM.w3M",
				environment.get("MODERATE").get("GROSSSEDrate.w3M") - environment.get("MODERATE").get("NETsedrate.wM"));

		environment.get("MODERATE").put("COL.w2M", 1e-3);
		environment.get("MODERATE").put("COL.w3M", 1e-3);

		environment.get("MODERATE").put("Kp.col2M", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("MODERATE").put("Kp.col3M", 0.08 * inputEng.getSubstancesData("D"));

		environment.get("MODERATE").put("CORG.susp2M", 0.1);
		environment.get("MODERATE").put("CORG.susp3M", 0.1);

		environment.get("MODERATE").put("Kp.susp2M",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("MODERATE").get("CORG.susp2M"));

		environment.get("MODERATE").put("Kp.susp3M",
				(inputEng.getSubstancesData("FRorig.w1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("MODERATE").get("CORG.susp3M"));

		environment.get("MODERATE").put("FRw.w2M", 1. / (1.
				+ environment.get("MODERATE").get("Kp.susp2M") * environment.get("MODERATE").get("SUSP.w2M") / 1000.
				+ environment.get("MODERATE").get("Kp.col2M") * environment.get("MODERATE").get("COL.w2M") / 1000.));

		environment.get("MODERATE").put("FRw.w3M", 1. / (1.
				+ environment.get("MODERATE").get("Kp.susp3M") * environment.get("MODERATE").get("SUSP.w3M") / 1000.
				+ environment.get("MODERATE").get("Kp.col3M") * environment.get("MODERATE").get("COL.w3M") / 1000.));

		environment.get("MODERATE").put("SED.w3M.sdM",
				environment.get("MODERATE").get("SETTLvelocity.M") * (1. - environment.get("MODERATE").get("FRw.w3M")));

		environment.get("MODERATE").put("kwsd.sed.sdM", 0.000002778);
		environment.get("MODERATE").put("kwsd.water.wM", 0.00000002778);

		environment.get("MODERATE").put("CORG.sdM", 0.05);

		environment.get("MODERATE").put("Kp.sdM",
				(inputEng.getSubstancesData("FRorig.sd2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("MODERATE").get("CORG.sdM"));

		environment.get("MODERATE").put("Ksdw.M",
				environment.get("MODERATE").get("FRACw.sdM")
						+ environment.get("MODERATE").get("FRACs.sdM") * environment.get("MODERATE").get("Kp.sdM")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("MODERATE").put("DESORB.sdM.w3M", environment.get("MODERATE").get("kwsd.water.wM")
				* environment.get("MODERATE").get("kwsd.sed.sdM")
				/ (environment.get("MODERATE").get("kwsd.water.wM") + environment.get("MODERATE").get("kwsd.sed.sdM"))
				/ environment.get("MODERATE").get("Ksdw.M"));

		environment.get("MODERATE").put("ADSORB.w3M.sdM",
				(environment.get("MODERATE").get("kwsd.water.wM") * environment.get("MODERATE").get("kwsd.sed.sdM"))
						/ (environment.get("MODERATE").get("kwsd.water.wM")
								+ environment.get("MODERATE").get("kwsd.sed.sdM"))
						* environment.get("MODERATE").get("FRw.w2M"));

		environment.get("MODERATE").put("k.sdMD.w3MD",
				(environment.get("MODERATE").get("RESUSP.sdM.w3M") + environment.get("MODERATE").get("DESORB.sdM.w3M"))
						/ environment.get("MODERATE").get("DEPTH.sdM"));

		environment.get("MODERATE").put("k.w3MD.sdMD",
				(environment.get("MODERATE").get("SED.w3M.sdM") + environment.get("MODERATE").get("ADSORB.w3M.sdM"))
						/ environment.get("MODERATE").get("DEPTH.w3M"));

		environment.get("MODERATE").put("CORRrunoff.sM",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("MODERATE").get("DEPTH.sM")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("MODERATE").get("DEPTH.sM")))));

		environment.get("MODERATE").put("FRACrun.sM", inputEng.getLandscapeSettings("CONTINENTAL", "FRACrun.C"));
		environment.get("MODERATE").put("EROSION.sM", inputEng.getLandscapeSettings("CONTINENTAL", "EROSION.C"));

		environment.get("MODERATE").put("TEMP.M", 285.);

		environment.get("MODERATE").put("DiffCP.aM",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadCP.a")));

		environment.get("MODERATE").put("ThermvelCP.aM", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ ((Math.PI * ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadCP.a"), 3))
								* inputEng.getEnvProperties("RhoCP.a")))),
				0.5));

		environment.get("MODERATE").put("NumConcCP.aM", 3e+5);

		environment.get("MODERATE").put("DiffS.aM",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M")
						* inputEng.getNanoProperties("CunninghamS"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getSubstancesData("RadS")));

		environment.get("MODERATE").put("Fuchs.aMS.aMCP", Math.pow((1 + (4
				* (environment.get("MODERATE").get("DiffS.aM") + environment.get("MODERATE").get("DiffCP.aM")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadCP.a")) * Math.pow(
						(environment.get("MODERATE").get("ThermvelCP.aM")
								* environment.get("MODERATE").get("ThermvelCP.aM")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("MODERATE").put("k.aMS.aMP",
				environment.get("MODERATE").get("Fuchs.aMS.aMCP")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadCP.a") + inputEng.getSubstancesData("RadS"))
								* (environment.get("MODERATE").get("DiffCP.aM")
										+ environment.get("MODERATE").get("DiffS.aM")))
						* environment.get("MODERATE").get("NumConcCP.aM"));

		environment.get("MODERATE").put("NumConcAcc.aM", 2.9e+9);

		environment.get("MODERATE").put("ThermvelAcc.aM", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3)
								* inputEng.getEnvProperties("RhoAcc"))))),
				0.5));

		environment.get("MODERATE").put("DiffAcc.aM",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadAcc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadAcc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadAcc")));

		environment.get("MODERATE").put("Fuchs.aMS.aMAcc", Math.pow((1 + (4
				* (environment.get("MODERATE").get("DiffS.aM") + environment.get("MODERATE").get("DiffAcc.aM")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadAcc")) * Math.pow(
						(environment.get("MODERATE").get("ThermvelAcc.aM")
								* environment.get("MODERATE").get("ThermvelAcc.aM")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("MODERATE").put("kcoag.aMS.aMAcc",
				environment.get("MODERATE").get("Fuchs.aMS.aMAcc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadAcc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("MODERATE").get("DiffAcc.aM")
										+ environment.get("MODERATE").get("DiffS.aM")))
						* environment.get("MODERATE").get("NumConcAcc.aM"));

		environment.get("MODERATE").put("k.aMS.aMP",
				environment.get("MODERATE").get("Fuchs.aMS.aMCP")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadCP.a") + inputEng.getSubstancesData("RadS"))
								* (environment.get("MODERATE").get("DiffCP.aM")
										+ environment.get("MODERATE").get("DiffS.aM")))
						* environment.get("MODERATE").get("NumConcCP.aM"));

		environment.get("MODERATE").put("NumConcNuc.aM", 3.2e+9);

		environment.get("MODERATE").put("ThermvelNuc.aM", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)
								* inputEng.getEnvProperties("RhoNuc"))))),
				0.5));

		environment.get("MODERATE").put("DiffNuc.aM",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadNuc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadNuc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadNuc")));

		environment.get("MODERATE").put("Fuchs.aMS.aMNuc", Math.pow((1 + (4
				* (environment.get("MODERATE").get("DiffS.aM") + environment.get("MODERATE").get("DiffNuc.aM")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNuc")) * Math.pow(
						(environment.get("MODERATE").get("ThermvelNuc.aM")
								* environment.get("MODERATE").get("ThermvelNuc.aM")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("MODERATE").put("kcoag.aMS.aMNuc",
				environment.get("MODERATE").get("Fuchs.aMS.aMNuc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadNuc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("MODERATE").get("DiffNuc.aM")
										+ environment.get("MODERATE").get("DiffS.aM")))
						* environment.get("MODERATE").get("NumConcNuc.aM"));

		environment.get("MODERATE").put("k.aMS.aMA", environment.get("MODERATE").get("kcoag.aMS.aMNuc")
				+ environment.get("MODERATE").get("kcoag.aMS.aMAcc"));

		environment.get("MODERATE").put("fGravSSPM.wM", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelSPM.w")));

		environment.get("MODERATE").put("fBrownSSPM.wM",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("MODERATE").put("Shear.w2M", 10.);
		environment.get("MODERATE").put("Shear.w3M", 10.);

		environment.get("MODERATE").put("fInterceptSSPM.w3M", (4. / 3.) * environment.get("MODERATE").get("Shear.w3M")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("MODERATE").put("fTotalSSPM.w3M", environment.get("MODERATE").get("fInterceptSSPM.w3M")
				+ +environment.get("MODERATE").get("fBrownSSPM.wM") + environment.get("MODERATE").get("fGravSSPM.wM"));

		environment.get("MODERATE").put("NumConcSPM.w3M", environment.get("MODERATE").get("SUSP.w3M")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("MODERATE").put("k.w3MS.w3MP", environment.get("MODERATE").get("fTotalSSPM.w3M")
				* environment.get("MODERATE").get("NumConcSPM.w3M") * inputEng.getSubstancesData("AtefSP.w3"));

		environment.get("MODERATE").put("fBrownSSPM.wM",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("MODERATE").put("fInterceptSSPM.w2M", (4. / 3.) * environment.get("MODERATE").get("Shear.w2M")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("MODERATE").put("fTotalSSPM.w2M", environment.get("MODERATE").get("fInterceptSSPM.w2M")
				+ +environment.get("MODERATE").get("fBrownSSPM.wM") + environment.get("MODERATE").get("fGravSSPM.wM"));

		environment.get("MODERATE").put("NumConcSPM.w2M", environment.get("MODERATE").get("SUSP.w2M")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("MODERATE").put("k.w2MS.w2MP", environment.get("MODERATE").get("fTotalSSPM.w2M")
				* environment.get("MODERATE").get("NumConcSPM.w2M") * inputEng.getSubstancesData("AtefSP.w2"));

		environment.get("MODERATE")
				.put("fGravSNC.wM", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("MODERATE").put("fBrownSNC.wM",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.w")));

		environment.get("MODERATE").put("fInterceptSNC.w3M", (4. / 3.) * environment.get("MODERATE").get("Shear.w3M")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("MODERATE").put("fTotalSNC.w3M", environment.get("MODERATE").get("fInterceptSNC.w3M")
				+ +environment.get("MODERATE").get("fBrownSNC.wM") + environment.get("MODERATE").get("fGravSNC.wM"));

		environment.get("MODERATE").put("NumConcNC.w3M", 2. * environment.get("MODERATE").get("COL.w3M")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("MODERATE").put("k.w3MS.w3MA", environment.get("MODERATE").get("fTotalSNC.w3M")
				* environment.get("MODERATE").get("NumConcNC.w3M") * inputEng.getSubstancesData("AtefSA.w3"));

		environment.get("MODERATE").put("fInterceptSNC.w2M", (4. / 3.) * environment.get("MODERATE").get("Shear.w2M")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("MODERATE").put("fTotalSNC.w2M", environment.get("MODERATE").get("fInterceptSNC.w2M")
				+ +environment.get("MODERATE").get("fBrownSNC.wM") + environment.get("MODERATE").get("fGravSNC.wM"));

		environment.get("MODERATE").put("NumConcNC.w2M", 2. * environment.get("MODERATE").get("COL.w2M")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("MODERATE").put("k.w2MS.w2MA", environment.get("MODERATE").get("fTotalSNC.w2M")
				* environment.get("MODERATE").get("NumConcNC.w2M") * inputEng.getSubstancesData("AtefSA.w2"));

		environment.get("MODERATE").put("Udarcy.sdM", 9e-6);

		environment.get("MODERATE").put("GravNumberSP.sdM", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("MODERATE").get("Udarcy.sdM")));

		environment.get("MODERATE").put("PecletNumberSP.sdM", environment.get("MODERATE").get("Udarcy.sdM") * 2.
				* inputEng.getEnvProperties("RadFP.sd") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("MODERATE").put("aspectratioSP.sdM",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.sd"));

		environment.get("MODERATE").put("vdWaalsNumberSP.sdM", inputEng.getSubstancesData("AHamakerSP.w")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M")));

		environment.get("MODERATE").put("fGravSP.sdM",
				0.22 * Math.pow(environment.get("MODERATE").get("aspectratioSP.sdM"), -0.24)
						* Math.pow(environment.get("MODERATE").get("GravNumberSP.sdM"), 1.11)
						* Math.pow(environment.get("MODERATE").get("vdWaalsNumberSP.sdM"), 0.053));

		environment.get("MODERATE").put("fInterceptSP.sdM",
				0.55 * Math.pow(environment.get("MODERATE").get("aspectratioSP.sdM"), 1.55)
						* Math.pow(environment.get("MODERATE").get("PecletNumberSP.sdM"), -0.125)
						* Math.pow(environment.get("MODERATE").get("vdWaalsNumberSP.sdM"), 0.125));

		environment.get("MODERATE").put("Por.sdM", environment.get("MODERATE").get("FRACw.sdM"));

		environment.get("MODERATE").put("GammPDF.sdM",
				Math.pow(1. - environment.get("MODERATE").get("Por.sdM"), 1. / 3.));

		environment.get("MODERATE").put("ASPDF.sdM",
				(2. * (1. - Math.pow(environment.get("MODERATE").get("GammPDF.sdM"), 5)))
						/ (2. - 3. * environment.get("MODERATE").get("GammPDF.sdM")
								+ 3 * Math.pow(environment.get("MODERATE").get("GammPDF.sdM"), 5)
								- 2 * Math.pow(environment.get("MODERATE").get("GammPDF.sdM"), 6)));

		environment.get("MODERATE").put("fBrownSP.sdM",
				2.4 * Math.pow(environment.get("MODERATE").get("PecletNumberSP.sdM"), 1. / 3.)
						* Math.pow(environment.get("MODERATE").get("aspectratioSP.sdM"), -0.081)
						* Math.pow(environment.get("MODERATE").get("PecletNumberSP.sdM"), -0.715)
						* Math.pow(environment.get("MODERATE").get("vdWaalsNumberSP.sdM"), 0.053));

		environment.get("MODERATE").put("fTotalSP.sdM", environment.get("MODERATE").get("fBrownSP.sdM")
				+ environment.get("MODERATE").get("fInterceptSP.sdM") + environment.get("MODERATE").get("fGravSP.sdM"));

		environment.get("MODERATE").put("Filter.sdM", (3. / 2.) * ((1. - environment.get("MODERATE").get("Por.sdM"))
				/ (2 * inputEng.getEnvProperties("RadFP.sd") * environment.get("MODERATE").get("Por.sdM"))));

		environment.get("MODERATE").put("k.sdMS.sdMP",
				environment.get("MODERATE").get("Filter.sdM") * environment.get("MODERATE").get("Udarcy.sdM")
						* environment.get("MODERATE").get("fTotalSP.sdM") * inputEng.getSubstancesData("AtefSP.sd2"));

		environment.get("MODERATE").put("fGravSNC.sdM", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.sd")));

		environment.get("MODERATE").put("fBrownSNC.sdM",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.sd")));

		environment.get("MODERATE").put("NumConcNC.sdM", environment.get("MODERATE").get("NumConcNC.w2M"));

		environment.get("MODERATE").put("k.sdMS.sdMA", inputEng.getSubstancesData("AtefSA.sd2")
				* environment.get("MODERATE").get("NumConcNC.sdM")
				* (environment.get("MODERATE").get("fBrownSNC.sdM") + environment.get("MODERATE").get("fGravSNC.sdM")));

		// NIK
		environment.get("MODERATE").put("Udarcy.sM", 9e-6);
		environment.get("MODERATE").put("mConcNC.sM", 100. / 1000.);

		environment.get("MODERATE").put("GravNumberS.sM", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("MODERATE").get("Udarcy.sM")));

		environment.get("MODERATE").put("vdWaalsNumberSFP.sM", inputEng.getSubstancesData("AHamakerSP.w")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M")));

		environment.get("MODERATE").put("PecletNumberFP.sM", environment.get("MODERATE").get("Udarcy.sM") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("MODERATE").put("aspectratioSFP.sM",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("MODERATE").put("fGravSFP.sM",
				0.22 * Math.pow(environment.get("MODERATE").get("aspectratioSFP.sM"), -0.24)
						* Math.pow(environment.get("MODERATE").get("GravNumberS.sM"), 1.11)
						* Math.pow(environment.get("MODERATE").get("vdWaalsNumberSFP.sM"), 0.053));

		environment.get("MODERATE").put("fInterceptSFP.sM",
				0.55 * Math.pow(environment.get("MODERATE").get("aspectratioSFP.sM"), 1.55)
						* Math.pow(environment.get("MODERATE").get("PecletNumberFP.sM"), -0.125)
						* Math.pow(environment.get("MODERATE").get("vdWaalsNumberSFP.sM"), 0.125));

		environment.get("MODERATE").put("Por.sM", 1. - environment.get("MODERATE").get("FRACs.sM"));

		environment.get("MODERATE").put("GammPDF.sM",
				Math.pow(1. - environment.get("MODERATE").get("Por.sM"), 1. / 3.));

		environment.get("MODERATE").put("ASPDF.sM",
				(2. * (1. - Math.pow(environment.get("MODERATE").get("GammPDF.sM"), 5)))
						/ (2. - 3. * environment.get("MODERATE").get("GammPDF.sM")
								+ 3 * Math.pow(environment.get("MODERATE").get("GammPDF.sM"), 5)
								- 2 * Math.pow(environment.get("MODERATE").get("GammPDF.sM"), 6)));

		environment.get("MODERATE").put("fBrownSFP.sM",
				2.4 * Math.pow(environment.get("MODERATE").get("ASPDF.sM"), 1. / 3.)
						* Math.pow(environment.get("MODERATE").get("aspectratioSFP.sM"), -0.081)
						* Math.pow(environment.get("MODERATE").get("PecletNumberFP.sM"), -0.715)
						* Math.pow(environment.get("MODERATE").get("vdWaalsNumberSFP.sM"), 0.053));

		environment.get("MODERATE").put("fTotalSFP.sM", environment.get("MODERATE").get("fBrownSFP.sM")
				+ environment.get("MODERATE").get("fInterceptSFP.sM") + environment.get("MODERATE").get("fGravSFP.sM"));

		environment.get("MODERATE").put("Filter.sM", (3. / 2.) * ((1. - environment.get("MODERATE").get("Por.sM"))
				/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("MODERATE").get("Por.sM"))));

		environment.get("MODERATE").put("k.sMS.sMP",
				environment.get("MODERATE").get("Filter.sM") * environment.get("MODERATE").get("Udarcy.sM")
						* environment.get("MODERATE").get("fTotalSFP.sM") * inputEng.getSubstancesData("AtefSP.s1"));

		environment.get("MODERATE")
				.put("fGravSNC.sM", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.s")));

		environment.get("MODERATE").put("fBrownSNC.sM",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.s")));

		environment.get("MODERATE").put("NumConcNC.sM", environment.get("MODERATE").get("mConcNC.sM")
				/ (inputEng.getEnvProperties("SingleVolNC.s") * inputEng.getEnvProperties("RhoNC.s")));

		environment.get("MODERATE").put("k.sMS.sMA", inputEng.getSubstancesData("AtefSA.s1")
				* environment.get("MODERATE").get("NumConcNC.sM")
				* (environment.get("MODERATE").get("fBrownSNC.sM") + environment.get("MODERATE").get("fGravSNC.sM")));

		environment.get("MODERATE").put("Cunningham.cwM", 1.);

		environment.get("MODERATE").put("RAINrate.M", 700. / (1000. * 3600. * 24. * 365.));

		environment.get("MODERATE").put("FRACtwet.M", environment.get("MODERATE").get("twet.M")
				/ (environment.get("MODERATE").get("twet.M") + environment.get("MODERATE").get("tdry.M")));

		environment.get("MODERATE").put("RAINrate.wet.M",
				environment.get("MODERATE").get("RAINrate.M") / environment.get("MODERATE").get("FRACtwet.M"));

		environment.get("MODERATE").put("Rad.cwM",
				((0.7 * (Math.pow(60. * 60. * 1000. * environment.get("MODERATE").get("RAINrate.wet.M"), 0.25)) / 2.)
						/ 1000.));

		environment.get("MODERATE").put("TerminalVel.cwM",
				(Math.pow(2 * environment.get("MODERATE").get("Rad.cwM"), 2)
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")
								- inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * environment.get("MODERATE").get("Cunningham.cwM"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("MODERATE").put("ReyNumber.cwM",
				((2. * environment.get("MODERATE").get("Rad.cwM")) * environment.get("MODERATE").get("TerminalVel.cwM")
						* inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						/ (2. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("MODERATE").put("CritStokesNumb.cwM",
				(1.2 + (1. / 12.) * Math.log(1. + environment.get("MODERATE").get("ReyNumber.cwM")))
						/ (1. + Math.log(environment.get("MODERATE").get("ReyNumber.cwM"))));

		environment.get("MODERATE")
				.put("SingleMassA.aM",
						((environment.get("MODERATE").get("NumConcNuc.aM")
								* (inputEng.getEnvProperties("RhoNuc")
										* ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3))
										+ inputEng.getSubstancesData("RhoS")
												* inputEng.getNanoProperties("SingleVolS")))
								+ (environment.get("MODERATE").get("NumConcAcc.aM")
										* (inputEng.getEnvProperties("RhoAcc")
												* ((4. / 3.) * Math.PI
														* Math.pow(inputEng.getEnvProperties("RadAcc"), 3))
												+ inputEng.getSubstancesData("RhoS")
														* inputEng.getNanoProperties("SingleVolS"))))
								/ (environment.get("MODERATE").get("NumConcNuc.aM")
										+ environment.get("MODERATE").get("NumConcAcc.aM")));

		environment.get("MODERATE").put("SingleVolA.aM",
				((environment.get("MODERATE").get("NumConcNuc.aM") * (inputEng.getNanoProperties("SingleVolS")
						+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)))))
						+ (environment.get("MODERATE").get("NumConcAcc.aM") * (inputEng.getNanoProperties("SingleVolS")
								+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3))))))
						/ (environment.get("MODERATE").get("NumConcNuc.aM")
								+ environment.get("MODERATE").get("NumConcAcc.aM")));

		environment.get("MODERATE").put("RhoA.aM",
				environment.get("MODERATE").get("SingleMassA.aM") / environment.get("MODERATE").get("SingleVolA.aM"));

		environment.get("MODERATE").put("RadA.aM",
				Math.pow((environment.get("MODERATE").get("SingleVolA.aM")) / ((4. / 3.) * Math.PI), 1. / 3.));

		environment.get("MODERATE").put("RelaxS.aM",
				((inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * inputEng.getSubstancesData("RadS")), 2)
						* inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("MODERATE").put("SettvelS.aM",
				(Math.pow(2. * inputEng.getSubstancesData("RadS"), 2)
						* (inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("MODERATE").put("StokesNumberS.aM", (2. * environment.get("MODERATE").get("RelaxS.aM")
				* (environment.get("MODERATE").get("TerminalVel.cwM") - environment.get("MODERATE").get("SettvelS.aM")))
				/ (2. * environment.get("MODERATE").get("Rad.cwM")));

		environment.get("MODERATE").put("SchmidtNumberS.aM", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("MODERATE").get("DiffS.aM")));

		environment.get("MODERATE").put("fBrown.aMS.cwMS",
				(4. / (environment.get("MODERATE").get("ReyNumber.cwM")
						* environment.get("MODERATE").get("SchmidtNumberS.aM")))
						* (1. + 0.4 * (Math.pow(environment.get("MODERATE").get("ReyNumber.cwM"), 0.5)
								* Math.pow(environment.get("MODERATE").get("SchmidtNumberS.aM"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("MODERATE").get("ReyNumber.cwM"), 0.5)
										* Math.pow(environment.get("MODERATE").get("SchmidtNumberS.aM"), 0.5))));

		environment.get("MODERATE").put("fIntercept.aMS.cwMS", 4.
				* (inputEng.getSubstancesData("RadS") / environment.get("MODERATE").get("Rad.cwM"))
				* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
						/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						+ (1. + 2. * Math.pow(environment.get("MODERATE").get("ReyNumber.cwM"), 0.5)
								* (inputEng.getSubstancesData("RadS") / environment.get("MODERATE").get("Rad.cwM")))));

		if (environment.get("MODERATE").get("StokesNumberS.aM") > environment.get("MODERATE").get("CritStokesNumb.cwM"))
			environment.get("MODERATE").put("fGrav.aMS.cwMS",
					Math.pow(((environment.get("MODERATE").get("StokesNumberS.aM")
							- environment.get("MODERATE").get("CritStokesNumb.cwM"))
							/ environment.get("MODERATE").get("StokesNumberS.aM")
							- environment.get("MODERATE").get("CritStokesNumb.cwM") + 2. / 3.), 3. / 2.));
		else
			environment.get("MODERATE").put("fGrav.aMS.cwMS", 0.0);

		environment.get("MODERATE").put("fTotal.aMS.cwMS",
				environment.get("MODERATE").get("fBrown.aMS.cwMS")
						+ environment.get("MODERATE").get("fIntercept.aMS.cwMS")
						+ environment.get("MODERATE").get("fGrav.aMS.cwMS"));

		environment.get("MODERATE").put("k.aMS.cwMS",
				((3. / 2.)
						* (environment.get("MODERATE").get("fTotal.aMS.cwMS")
								* environment.get("MODERATE").get("RAINrate.wet.M"))
						/ (2. * environment.get("MODERATE").get("Rad.cwM"))));

		environment.get("MODERATE").put("CunninghamA.aM", 1. + (66.e-9 / environment.get("MODERATE").get("RadA.aM"))
				* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / environment.get("MODERATE").get("RadA.aM")))));

		environment.get("MODERATE").put("DiffA.aM",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("MODERATE").get("TEMP.M")
						* environment.get("MODERATE").get("CunninghamA.aM"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* environment.get("MODERATE").get("RadA.aM")));

		environment.get("MODERATE").put("RelaxA.aM",
				((environment.get("MODERATE").get("RhoA.aM") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * environment.get("MODERATE").get("RadA.aM")), 2)
						* environment.get("MODERATE").get("CunninghamA.aM"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("MODERATE").put("SettvelA.aM", (Math.pow(2. * environment.get("MODERATE").get("RadA.aM"), 2)
				* (environment.get("MODERATE").get("RhoA.aM") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
				* 9.81 * environment.get("MODERATE").get("CunninghamA.aM"))
				/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("MODERATE").put("StokesNumberA.aM", (2. * environment.get("MODERATE").get("RelaxA.aM")
				* (environment.get("MODERATE").get("TerminalVel.cwM") - environment.get("MODERATE").get("SettvelA.aM")))
				/ (2. * environment.get("MODERATE").get("Rad.cwM")));

		environment.get("MODERATE").put("SchmidtNumberA.aM", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("MODERATE").get("DiffA.aM")));

		environment.get("MODERATE").put("fBrown.aMA.cwMA",
				(4. / (environment.get("MODERATE").get("ReyNumber.cwM")
						* environment.get("MODERATE").get("SchmidtNumberA.aM")))
						* (1. + 0.4 * (Math.pow(environment.get("MODERATE").get("ReyNumber.cwM"), 0.5)
								* Math.pow(environment.get("MODERATE").get("SchmidtNumberA.aM"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("MODERATE").get("ReyNumber.cwM"), 0.5)
										* Math.pow(environment.get("MODERATE").get("SchmidtNumberA.aM"), 0.5))));

		environment.get("MODERATE").put("fIntercept.aMA.cwMA",
				4. * (environment.get("MODERATE").get("RadA.aM") / environment.get("MODERATE").get("Rad.cwM"))
						* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
								+ (1. + 2. * Math.pow(environment.get("MODERATE").get("ReyNumber.cwM"), 0.5)
										* (environment.get("MODERATE").get("RadA.aM")
												/ environment.get("MODERATE").get("Rad.cwM")))));

		if (environment.get("MODERATE").get("StokesNumberA.aM") > environment.get("MODERATE").get("CritStokesNumb.cwM"))
			environment.get("MODERATE").put("fGrav.aMA.cwMA",
					Math.pow(((environment.get("MODERATE").get("StokesNumberA.aM")
							- environment.get("MODERATE").get("CritStokesNumb.cwM"))
							/ environment.get("MODERATE").get("StokesNumberA.aM")
							- environment.get("MODERATE").get("CritStokesNumb.cwM") + 2. / 3.), 3. / 2.));
		else
			environment.get("MODERATE").put("fGrav.aMA.cwMA", 0.0);

		environment.get("MODERATE").put("fTotal.aMA.cwMA",
				environment.get("MODERATE").get("fBrown.aMA.cwMA")
						+ environment.get("MODERATE").get("fIntercept.aMA.cwMA")
						+ environment.get("MODERATE").get("fGrav.aMA.cwMA"));

		environment.get("MODERATE").put("k.aMA.cwMA",
				((3. / 2.)
						* (environment.get("MODERATE").get("fTotal.aMA.cwMA")
								* environment.get("MODERATE").get("RAINrate.wet.M"))
						/ (2. * environment.get("MODERATE").get("Rad.cwM"))));

		environment.get("MODERATE").put("COLLECTeff.M", 200000.);

		environment.get("MODERATE").put("k.aMP.cwMP",
				((environment.get("MODERATE").get("tdry.M") + environment.get("MODERATE").get("twet.M"))
						/ environment.get("MODERATE").get("twet.M") * environment.get("MODERATE").get("COLLECTeff.M")
						* environment.get("MODERATE").get("RAINrate.M"))
						/ environment.get("MODERATE").get("HEIGHT.aM"));

		environment.get("MODERATE").put("AEROresist.sM", 74.);
		environment.get("MODERATE").put("FRICvelocity.sM", 0.19);
		environment.get("MODERATE").put("veghair.sM", 1e-5);
		environment.get("MODERATE").put("largevegradius.sM", 0.0005);
		environment.get("MODERATE").put("AREAFRACveg.sM", 0.01);

		environment.get("MODERATE").put("fGrav.aMS.sMS", Math.pow((environment.get("MODERATE").get("StokesNumberS.aM")
				/ (environment.get("MODERATE").get("StokesNumberS.aM") + 0.8)), 2));

		environment.get("MODERATE").put("fBrown.aMS.sMS",
				Math.pow(environment.get("MODERATE").get("SchmidtNumberS.aM"), -2. / 3.));

		environment.get("MODERATE")
				.put("fIntercept.aMS.sMS",
						0.3 * (environment.get("MODERATE").get("AREAFRACveg.sM") * (inputEng.getSubstancesData("RadS")
								/ (inputEng.getSubstancesData("RadS") + environment.get("MODERATE").get("veghair.sM")))
								+ (1 - environment.get("MODERATE").get("AREAFRACveg.sM"))
										* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
												+ environment.get("MODERATE").get("largevegradius.sM")))));

		environment.get("MODERATE").put("SURFresist.sMS",
				1 / (environment.get("MODERATE").get("FRICvelocity.sM")
						* (environment.get("MODERATE").get("fBrown.aMS.sMS")
								+ environment.get("MODERATE").get("fIntercept.aMS.sMS")
								+ environment.get("MODERATE").get("fGrav.aMS.sMS"))));

		environment.get("MODERATE").put("DEPvelocity.aMS.sMS", 1.
				/ (environment.get("MODERATE").get("AEROresist.sM") + environment.get("MODERATE").get("SURFresist.sMS"))
				+ environment.get("MODERATE").get("SettvelS.aM"));

		environment.get("MODERATE").put("k.aMS.sMS",
				(environment.get("MODERATE").get("DEPvelocity.aMS.sMS") * environment.get("MODERATE").get("AREAFRAC.sM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ (environment.get("MODERATE").get("VOLUME.aM")));

		environment.get("MODERATE").put("fGrav.aMAsMA", Math.pow((environment.get("MODERATE").get("StokesNumberA.aM")
				/ (environment.get("MODERATE").get("StokesNumberA.aM") + 0.8)), 2));

		environment.get("MODERATE").put("fBrown.aMA.sMA",
				Math.pow(environment.get("MODERATE").get("SchmidtNumberA.aM"), -2. / 3.));

		environment.get("MODERATE").put("fIntercept.aMA.sMA", 0.3 * (environment.get("MODERATE").get("AREAFRACveg.sM")
				* (environment.get("MODERATE").get("RadA.aM")
						/ (environment.get("MODERATE").get("RadA.aM") + environment.get("MODERATE").get("veghair.sM")))
				+ (1 - environment.get("MODERATE").get("AREAFRACveg.sM"))
						* (environment.get("MODERATE").get("RadA.aM") / (environment.get("MODERATE").get("RadA.aM")
								+ environment.get("MODERATE").get("largevegradius.sM")))));

		environment.get("MODERATE").put("SURFresist.sMA",
				1 / (environment.get("MODERATE").get("FRICvelocity.sM")
						* (environment.get("MODERATE").get("fBrown.aMA.sMA")
								+ environment.get("MODERATE").get("fIntercept.aMA.sMA")
								+ environment.get("MODERATE").get("fGrav.aMAsMA"))));

		environment.get("MODERATE").put("DEPvelocity.aMA.sMA", 1.
				/ (environment.get("MODERATE").get("AEROresist.sM") + environment.get("MODERATE").get("SURFresist.sMA"))
				+ environment.get("MODERATE").get("SettvelA.aM"));

		environment.get("MODERATE").put("k.aMA.sMA",
				(environment.get("MODERATE").get("DEPvelocity.aMA.sMA") * environment.get("MODERATE").get("AREAFRAC.sM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ (environment.get("MODERATE").get("VOLUME.aM")));

		environment.get("MODERATE").put("AEROSOLdeprate.M", 0.001);

		environment.get("MODERATE").put("k.aMP.sMP",
				environment.get("MODERATE").get("AEROSOLdeprate.M") * environment.get("MODERATE").get("AREAFRAC.sM")
						* environment.get("MODERATE").get("SYSTEMAREA.M")
						/ environment.get("MODERATE").get("VOLUME.aM"));

		environment.get("MODERATE").put("AEROresist.w2M", 135.);
		environment.get("MODERATE").put("FRICvelocity.wM", 0.19);

		environment.get("MODERATE").put("fGrav.aMS.w",
				Math.pow(10, -3. / environment.get("MODERATE").get("StokesNumberS.aM")));

		environment.get("MODERATE").put("fBrown.aMS.w",
				Math.pow(environment.get("MODERATE").get("SchmidtNumberS.aM"), -1. / 2.));

		environment.get("MODERATE").put("fIntercept.aMA.sMA", 0.3 * (environment.get("MODERATE").get("AREAFRACveg.sM")
				* (environment.get("MODERATE").get("RadA.aM")
						/ (environment.get("MODERATE").get("RadA.aM") + environment.get("MODERATE").get("veghair.sM")))
				+ (1 - environment.get("MODERATE").get("AREAFRACveg.sM"))
						* (environment.get("MODERATE").get("RadA.aM") / (environment.get("MODERATE").get("RadA.aM")
								+ environment.get("MODERATE").get("largevegradius.sM")))));

		environment.get("MODERATE").put("SURFresist.w2MS", 1. / (environment.get("MODERATE").get("FRICvelocity.wM")
				* (environment.get("MODERATE").get("fBrown.aMS.w") + environment.get("MODERATE").get("fGrav.aMS.w"))));

		environment.get("MODERATE").put("DEPvelocity.aMS.w2MS",
				1. / (environment.get("MODERATE").get("AEROresist.w2M")
						+ environment.get("MODERATE").get("SURFresist.w2MS"))
						+ environment.get("MODERATE").get("SettvelS.aM"));

		environment.get("MODERATE").put("k.aMS.w2MS", (environment.get("MODERATE").get("DEPvelocity.aMS.w2MS")
				* environment.get("MODERATE").get("AREAFRAC.wM") * environment.get("MODERATE").get("SYSTEMAREA.M"))
				/ (environment.get("MODERATE").get("VOLUME.aM")));

		environment.get("MODERATE").put("fGrav.aMA.w",
				Math.pow(10, -3. / environment.get("MODERATE").get("StokesNumberA.aM")));

		environment.get("MODERATE").put("fBrown.aMA.w",
				Math.pow(environment.get("MODERATE").get("SchmidtNumberA.aM"), -1. / 2.));

		environment.get("MODERATE").put("SURFresist.wMA", 1. / (environment.get("MODERATE").get("FRICvelocity.wM")
				* (environment.get("MODERATE").get("fBrown.aMA.w") + environment.get("MODERATE").get("fGrav.aMA.w"))));

		environment.get("MODERATE").put("DEPvelocity.aMA.w2MA",
				1. / (environment.get("MODERATE").get("AEROresist.w2M")
						+ environment.get("MODERATE").get("SURFresist.wMA"))
						+ environment.get("MODERATE").get("SettvelA.aM"));

		environment.get("MODERATE").put("k.aMA.w2MA", (environment.get("MODERATE").get("DEPvelocity.aMA.w2MA")
				* environment.get("MODERATE").get("AREAFRAC.wM") * environment.get("MODERATE").get("SYSTEMAREA.M"))
				/ (environment.get("MODERATE").get("VOLUME.aM")));

		environment.get("MODERATE").put("k.aMP.wMP",
				(environment.get("MODERATE").get("AEROSOLdeprate.M") * environment.get("MODERATE").get("AREAFRAC.wM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.aM"));

		environment.get("MODERATE").put("k.cwMS.sMS",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.sM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		environment.get("MODERATE").put("k.cwMA.sMA",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.sM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		environment.get("MODERATE").put("k.cwMP.sMP",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.sM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		environment.get("MODERATE").put("k.cwMS.w2MS",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.wM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		environment.get("MODERATE").put("k.cwMA.w2MA",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.wM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		environment.get("MODERATE").put("k.cwMP.w2MP",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.wM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		environment.get("MODERATE").put("k.cwMP.w2MP",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("AREAFRAC.wM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.cwM"));

		if (inputEng.nanoProperties.get("SetVelS.w") * environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / environment.get("MODERATE").get("VOLUME.w2M") > 0)
			environment.get("MODERATE")
					.put("k.w2MS.w3MS",
							environment.get("MODERATE").get("k.w2M.w3M") + (inputEng.nanoProperties.get("SetVelS.w")
									* environment.get("MODERATE").get("AREAFRAC.wM")
									* environment.get("MODERATE").get("SYSTEMAREA.M"))
									/ environment.get("MODERATE").get("VOLUME.w2M"));
		else
			environment.get("MODERATE").put("k.w2MS.w3MS", environment.get("MODERATE").get("k.w2M.w3M"));

		if (inputEng.nanoProperties.get("SetVelA.w") * environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / environment.get("MODERATE").get("VOLUME.w2M") > 0)
			environment.get("MODERATE")
					.put("k.w2MA.w3MA",
							environment.get("MODERATE").get("k.w2M.w3M") + (inputEng.nanoProperties.get("SetVelA.w")
									* environment.get("MODERATE").get("AREAFRAC.wM")
									* environment.get("MODERATE").get("SYSTEMAREA.M"))
									/ environment.get("MODERATE").get("VOLUME.w2M"));
		else
			environment.get("MODERATE").put("k.w2MA.w3MA", environment.get("MODERATE").get("k.w2M.w3M"));

		if (inputEng.nanoProperties.get("SetVelP.w") * environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / environment.get("MODERATE").get("VOLUME.w2M") > 0)
			environment.get("MODERATE")
					.put("k.w2MP.w3MP",
							environment.get("MODERATE").get("k.w2M.w3M") + (inputEng.nanoProperties.get("SetVelP.w")
									* environment.get("MODERATE").get("AREAFRAC.wM")
									* environment.get("MODERATE").get("SYSTEMAREA.M"))
									/ environment.get("MODERATE").get("VOLUME.w2M"));
		else
			environment.get("MODERATE").put("k.w2MP.w3MP", environment.get("MODERATE").get("k.w2M.w3M"));

		if (inputEng.nanoProperties.get("SetVelS.w") * environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / environment.get("MODERATE").get("VOLUME.w3M") > 0)
			environment.get("MODERATE").put("k.w3MS.sdMS",
					(inputEng.nanoProperties.get("SetVelS.w") * environment.get("MODERATE").get("AREAFRAC.wM")
							* environment.get("MODERATE").get("SYSTEMAREA.M"))
							/ environment.get("MODERATE").get("VOLUME.w3M"));
		else
			environment.get("MODERATE").put("k.w3MS.sdMS", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") * environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / environment.get("MODERATE").get("VOLUME.w3M") > 0)
			environment.get("MODERATE").put("k.w3MA.sdMA",
					(inputEng.nanoProperties.get("SetVelA.w") * environment.get("MODERATE").get("AREAFRAC.wM")
							* environment.get("MODERATE").get("SYSTEMAREA.M"))
							/ environment.get("MODERATE").get("VOLUME.w3M"));
		else
			environment.get("MODERATE").put("k.w3MA.sdMA", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") * environment.get("MODERATE").get("AREAFRAC.wM")
				* environment.get("MODERATE").get("SYSTEMAREA.M") / environment.get("MODERATE").get("VOLUME.w3M") > 0)
			environment.get("MODERATE").put("k.w3MP.sdMP",
					(inputEng.nanoProperties.get("SetVelP.w") * environment.get("MODERATE").get("AREAFRAC.wM")
							* environment.get("MODERATE").get("SYSTEMAREA.M"))
							/ environment.get("MODERATE").get("VOLUME.w3M"));
		else
			environment.get("MODERATE").put("k.w3MP.sdMP", 0.);

		environment.get("MODERATE").put("k.sdMS.w3MS",
				environment.get("MODERATE").get("RESUSP.sdM.w3M") / environment.get("MODERATE").get("DEPTH.sdM"));

		environment.get("MODERATE").put("k.sdMA.w3MA",
				environment.get("MODERATE").get("RESUSP.sdM.w3M") / environment.get("MODERATE").get("DEPTH.sdM"));

		environment.get("MODERATE").put("k.sdMP.w3MP",
				environment.get("MODERATE").get("RESUSP.sdM.w3M") / environment.get("MODERATE").get("DEPTH.sdM"));

		environment.get("MODERATE").put("k.sMS.w2MS",
				(environment.get("MODERATE").get("FRACrun.sM") * environment.get("MODERATE").get("RAINrate.M")
						* environment.get("MODERATE").get("CORRrunoff.sM")
						+ environment.get("MODERATE").get("EROSION.sM")) / environment.get("MODERATE").get("DEPTH.sM"));

		environment.get("MODERATE").put("k.sMA.w2MA",
				(environment.get("MODERATE").get("FRACrun.sM") * environment.get("MODERATE").get("RAINrate.M")
						* environment.get("MODERATE").get("CORRrunoff.sM")
						+ environment.get("MODERATE").get("EROSION.sM")) / environment.get("MODERATE").get("DEPTH.sM"));

		environment.get("MODERATE").put("k.sMP.w2MP",
				environment.get("MODERATE").get("EROSION.sM") / environment.get("MODERATE").get("DEPTH.sM"));

		environment.get("MODERATE").put("kesc.aM", Math.log(2.) / (60. * 365. * 24. * 3600.));

		environment.get("MODERATE").put("k.aMS",
				environment.get("MODERATE").get("kesc.aM") + inputEng.getSubstancesData("kdegS.a"));

		environment.get("MODERATE").put("k.aMP",
				environment.get("MODERATE").get("kesc.aM") + inputEng.getSubstancesData("kdegP.a"));

		environment.get("MODERATE").put("k.w2MS", inputEng.getSubstancesData("kdegS.w2"));

		environment.get("MODERATE").put("k.w2MP", inputEng.getSubstancesData("kdegP.w2"));

		environment.get("MODERATE").put("k.w3MS", inputEng.getSubstancesData("kdegS.w3"));

		environment.get("MODERATE").put("k.w3MP", inputEng.getSubstancesData("kdegP.w3"));

		environment.get("MODERATE").put("k.w3MP", inputEng.getSubstancesData("kdegP.w3"));

		environment.get("MODERATE").put("kbur.sdM",
				environment.get("MODERATE").get("NETsedrate.wM") / environment.get("MODERATE").get("DEPTH.sdM"));

		environment.get("MODERATE").put("k.sdMS",
				environment.get("MODERATE").get("kbur.sdM") + inputEng.getSubstancesData("kdegS.sd2"));

		environment.get("MODERATE").put("k.sdMA",
				environment.get("MODERATE").get("kbur.sdM") + inputEng.getSubstancesData("kdegA.sd2"));

		environment.get("MODERATE").put("k.sdMP",
				environment.get("MODERATE").get("kbur.sdM") + inputEng.getSubstancesData("kdegP.sd2"));

		environment.get("MODERATE").put("FRACinf.sM", 0.25);

		environment.get("MODERATE").put("CORRleach.sM",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("MODERATE").get("DEPTH.sM")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("MODERATE").get("DEPTH.sM")))));

		environment.get("MODERATE").put("kleach.sM",
				((environment.get("MODERATE").get("FRACinf.sM") * environment.get("MODERATE").get("RAINrate.M")
						* environment.get("MODERATE").get("CORRleach.sM"))
						/ environment.get("MODERATE").get("DEPTH.sM")));

		environment.get("MODERATE").put("k.sMS",
				environment.get("MODERATE").get("kleach.sM") + inputEng.getSubstancesData("kdegS.s1"));

		environment.get("MODERATE").put("k.sMA",
				environment.get("MODERATE").get("kleach.sM") + inputEng.getSubstancesData("kdegA.s1"));

		environment.get("MODERATE").put("k.sMP", inputEng.getSubstancesData("kdegP.s1"));
	}

	public void globalArctic() {
		environment.put("ARCTIC", new HashMap<String, Double>());

		environment.get("ARCTIC").put("FRACw.sdA", 0.8);
		environment.get("ARCTIC").put("FRACs.sdA", 1 - environment.get("ARCTIC").get("FRACw.sdA"));

		environment.get("ARCTIC").put("FRACw.sA", 0.2);
		environment.get("ARCTIC").put("FRACa.sA", 0.2);

		environment.get("ARCTIC").put("FRACs.sA",
				1 - environment.get("ARCTIC").get("FRACa.sA") - environment.get("ARCTIC").get("FRACw.sA"));

		environment.get("ARCTIC").put("FRACaerw.aA", 2e-11);
		environment.get("ARCTIC").put("FRACaers.aA", 2e-11);
		environment.get("ARCTIC").put("FRACcldw.aA", 3e-7);

		environment.get("ARCTIC").put("DEPTH.sA", 0.05);
		environment.get("ARCTIC").put("DEPTH.sdA", 3. / 100.);

		environment.get("ARCTIC").put("DEPTH.w2A", 100.);
		environment.get("ARCTIC").put("DEPTH.w3A", 3000.);

		environment.get("ARCTIC").put("HEIGHT.aA", 1000.);

		environment.get("ARCTIC").put("AREAFRAC.wA", 0.6);

		environment.get("ARCTIC").put("AREAFRAC.sA", 1 - environment.get("ARCTIC").get("AREAFRAC.wA"));

		environment.get("ARCTIC").put("SYSTEMAREA.A", 4.25e+13);

		environment.get("ARCTIC").put("VOLUME.aA", environment.get("ARCTIC").get("SYSTEMAREA.A")
				* environment.get("ARCTIC").get("HEIGHT.aA") * (1. - environment.get("ARCTIC").get("FRACcldw.aA")));

		environment.get("ARCTIC").put("VOLUME.cwA",
				environment.get("ARCTIC").get("VOLUME.aA") * environment.get("ARCTIC").get("FRACcldw.aA"));

		environment.get("ARCTIC").put("VOLUME.w2A", environment.get("ARCTIC").get("SYSTEMAREA.A")
				* environment.get("ARCTIC").get("AREAFRAC.wA") * environment.get("ARCTIC").get("DEPTH.w2A"));

		environment.get("ARCTIC").put("VOLUME.w3A", environment.get("ARCTIC").get("SYSTEMAREA.A")
				* environment.get("ARCTIC").get("AREAFRAC.wA") * environment.get("ARCTIC").get("DEPTH.w3A"));

		environment.get("ARCTIC").put("VOLUME.sdA", environment.get("ARCTIC").get("SYSTEMAREA.A")
				* environment.get("ARCTIC").get("AREAFRAC.wA") * environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("VOLUME.sA", environment.get("ARCTIC").get("SYSTEMAREA.A")
				* environment.get("ARCTIC").get("AREAFRAC.sA") * environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("TAU.surfA", 55. * 3600. * 24.);

		environment.get("ARCTIC").put("OceanMixing.A",
				environment.get("ARCTIC").get("VOLUME.w2A") / environment.get("ARCTIC").get("TAU.surfA"));

		environment.get("ARCTIC").put("k.w3A.w2A",
				(environment.get("MODERATE").get("OceanCurrent") + environment.get("ARCTIC").get("OceanMixing.A"))
						/ environment.get("ARCTIC").get("VOLUME.w3A"));

		environment.get("ARCTIC").put("k.w2A.w3A",
				(environment.get("MODERATE").get("OceanCurrent") + environment.get("ARCTIC").get("OceanMixing.A"))
						/ environment.get("ARCTIC").get("VOLUME.w2A"));

		environment.get("ARCTIC").put("WATERflow.w2A.w2M", 0.);

		environment.get("ARCTIC").put("k.w3A.w3M",
				environment.get("MODERATE").get("OceanCurrent") / environment.get("ARCTIC").get("VOLUME.w3A"));

		environment.get("ARCTIC").put("k.w2A.w2M",
				environment.get("ARCTIC").get("WATERflow.w2A.w2M") / environment.get("ARCTIC").get("VOLUME.w2A"));

		environment.get("ARCTIC").put("WINDspeed.A", 3.);

		environment.get("ARCTIC").put("TAU.aA",
				(1.5 * (0.5 * Math.sqrt(environment.get("ARCTIC").get("SYSTEMAREA.A") * Math.PI / 4.)
						/ environment.get("ARCTIC").get("WINDspeed.A")) / (3600. * 24.)) * (3600 * 24));

		environment.get("ARCTIC").put("k.aA.aM", 1. / environment.get("ARCTIC").get("TAU.aA"));

		environment.get("ARCTIC").put("CORRrunoff.sA",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")))));

		environment.get("ARCTIC").put("FRACrun.sA", inputEng.getLandscapeSettings("CONTINENTAL", "FRACrun.C"));
		environment.get("ARCTIC").put("EROSION.sA", inputEng.getLandscapeSettings("CONTINENTAL", "EROSION.C"));

		environment.get("ARCTIC").put("TEMP.A", 263.);

		environment.get("ARCTIC").put("RAINrate.A", 250. / (1000. * 3600. * 24. * 365.));

		environment.get("ARCTIC").put("CORG.sA", 0.02);

		environment.get("ARCTIC").put("Kp.sA",
				(inputEng.getSubstancesData("FRorig.s1w") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.s1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("ARCTIC").get("CORG.sA"));

		environment.get("ARCTIC").put("Kaw.A",
				inputEng.getSubstancesData("Kaw") * (Math
						.exp((inputEng.getSubstancesData("H0vap") / 8.314)
								* ((1. / 298.) - 1. / environment.get("ARCTIC").get("TEMP.A")))
						* Math.exp(-(inputEng.getSubstancesData("H0sol") / 8.314)
								* ((1. / 298.) - 1. / environment.get("ARCTIC").get("TEMP.A")))
						* (298 / environment.get("ARCTIC").get("TEMP.A"))));

		environment.get("ARCTIC").put("Ksw.A",
				environment.get("ARCTIC").get("FRACa.sA") * environment.get("ARCTIC").get("Kaw.A")
						* inputEng.getSubstancesData("FRorig.s1w") + environment.get("ARCTIC").get("FRACw.sA")
						+ environment.get("ARCTIC").get("FRACs.sA") * environment.get("ARCTIC").get("Kp.sA")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("ARCTIC").put("k.sAD.w2AD",
				(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("FRACrun.sA")
						/ environment.get("ARCTIC").get("Ksw.A") + environment.get("ARCTIC").get("EROSION.sA"))
						* environment.get("ARCTIC").get("CORRrunoff.sA") / environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("PRODsusp.wA", environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / (1000. * 3600. * 24. * 365.));

		environment.get("ARCTIC").put("NETsedrate.wA", 6.34195839675285E-14);

		environment.get("ARCTIC").put("SETTLvelocity.A", 2.5 / (24. * 3600));

		environment.get("ARCTIC").put("SUSP.w2A", 5. / 1000.);
		environment.get("ARCTIC").put("SUSP.w3A", 5. / 1000.);

		if (environment.get("ARCTIC").get("SETTLvelocity.A") * environment.get("ARCTIC").get("SUSP.w3A")
				/ (environment.get("ARCTIC").get("FRACs.sdA")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("ARCTIC")
								.get("NETsedrate.wA")) {
			environment.get("ARCTIC").put("GROSSSEDrate.w3A",
					environment.get("ARCTIC").get("SETTLvelocity.A") * environment.get("ARCTIC").get("SUSP.w3A")
							/ (environment.get("ARCTIC").get("FRACs.sdA")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("ARCTIC").put("GROSSSEDrate.w3A", environment.get("ARCTIC").get("NETsedrate.wA"));

		environment.get("ARCTIC").put("RESUSP.sdA.w3A",
				environment.get("ARCTIC").get("GROSSSEDrate.w3A") - environment.get("ARCTIC").get("NETsedrate.wA"));

		environment.get("ARCTIC").put("COL.w2A", 1e-3);
		environment.get("ARCTIC").put("COL.w3A", 1e-3);

		environment.get("ARCTIC").put("Kp.col2A", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("ARCTIC").put("Kp.col3A", 0.08 * inputEng.getSubstancesData("D"));

		environment.get("ARCTIC").put("CORG.susp2A", 0.1);
		environment.get("ARCTIC").put("CORG.susp3A", 0.1);

		environment.get("ARCTIC").put("Kp.susp2A",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("ARCTIC").get("CORG.susp2A"));

		environment.get("ARCTIC").put("Kp.susp3A",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("ARCTIC").get("CORG.susp3A"));

		environment.get("ARCTIC").put("FRw.w2A", 1. / (1.
				+ environment.get("ARCTIC").get("Kp.susp2A") * environment.get("ARCTIC").get("SUSP.w2A") / 1000.
				+ environment.get("ARCTIC").get("Kp.col2A") * environment.get("ARCTIC").get("COL.w2A") / 1000.));

		environment.get("ARCTIC").put("FRw.w3A", 1. / (1.
				+ environment.get("ARCTIC").get("Kp.susp3A") * environment.get("ARCTIC").get("SUSP.w3A") / 1000.
				+ environment.get("ARCTIC").get("Kp.col3A") * environment.get("ARCTIC").get("COL.w3A") / 1000.));

		environment.get("ARCTIC").put("SED.w3A.sdA",
				environment.get("ARCTIC").get("SETTLvelocity.A") * (1. - environment.get("ARCTIC").get("FRw.w3A")));

		environment.get("ARCTIC").put("kwsd.sed.sdA", 0.000002778);
		environment.get("ARCTIC").put("kwsd.water.wA", 0.00000002778);

		environment.get("ARCTIC").put("CORG.sdA", 0.05);

		environment.get("ARCTIC").put("Kp.sdA",
				(inputEng.getSubstancesData("FRorig.sd2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("ARCTIC").get("CORG.sdA"));

		environment.get("ARCTIC").put("Ksdw.A",
				environment.get("ARCTIC").get("FRACw.sdA")
						+ environment.get("ARCTIC").get("FRACs.sdA") * environment.get("ARCTIC").get("Kp.sdA")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("ARCTIC").put("DESORB.sdA.w3A", environment.get("ARCTIC").get("kwsd.water.wA")
				* environment.get("ARCTIC").get("kwsd.sed.sdA")
				/ (environment.get("ARCTIC").get("kwsd.water.wA") + environment.get("ARCTIC").get("kwsd.sed.sdA"))
				/ environment.get("ARCTIC").get("Ksdw.A"));

		environment.get("ARCTIC").put("ADSORB.w3A.sdA",
				(environment.get("ARCTIC").get("kwsd.water.wA") * environment.get("ARCTIC").get("kwsd.sed.sdA"))
						/ (environment.get("ARCTIC").get("kwsd.water.wA")
								+ environment.get("ARCTIC").get("kwsd.sed.sdA"))
						* environment.get("ARCTIC").get("FRw.w2A"));

		environment.get("ARCTIC").put("k.sdAD.w3AD",
				(environment.get("ARCTIC").get("RESUSP.sdA.w3A") + environment.get("ARCTIC").get("DESORB.sdA.w3A"))
						/ environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("k.w3AD.sdAD",
				(environment.get("ARCTIC").get("SED.w3A.sdA") + environment.get("ARCTIC").get("ADSORB.w3A.sdA"))
						/ environment.get("ARCTIC").get("DEPTH.w3A"));

		environment.get("ARCTIC").put("ThermvelCP.aA", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ ((Math.PI * ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadCP.a"), 3))
								* inputEng.getEnvProperties("RhoCP.a")))),
				0.5));

		environment.get("ARCTIC").put("NumConcCP.aA", 3e+5);

		environment.get("ARCTIC").put("DiffCP.aA",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadCP.a")));

		environment.get("ARCTIC").put("DiffS.aA",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A")
						* inputEng.getNanoProperties("CunninghamS"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getSubstancesData("RadS")));

		environment.get("ARCTIC").put("Fuchs.aAS.aACP", Math.pow((1 + (4
				* (environment.get("ARCTIC").get("DiffS.aA") + environment.get("ARCTIC").get("DiffCP.aA")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadCP.a")) * Math.pow(
						(environment.get("ARCTIC").get("ThermvelCP.aA") * environment.get("ARCTIC").get("ThermvelCP.aA")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("ARCTIC").put("k.aAS.aAP",
				environment.get("ARCTIC").get("Fuchs.aAS.aACP")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadCP.a") + inputEng.getSubstancesData("RadS"))
								* (environment.get("ARCTIC").get("DiffCP.aA")
										+ environment.get("ARCTIC").get("DiffS.aA")))
						* environment.get("ARCTIC").get("NumConcCP.aA"));

		environment.get("ARCTIC").put("NumConcAcc.aA", 2.9e+9);

		environment.get("ARCTIC").put("ThermvelAcc.aA", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3)
								* inputEng.getEnvProperties("RhoAcc"))))),
				0.5));

		environment.get("ARCTIC").put("DiffAcc.aA",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadAcc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadAcc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadAcc")));

		environment.get("ARCTIC").put("Fuchs.aAS.aAAcc", Math.pow((1 + (4
				* (environment.get("ARCTIC").get("DiffS.aA") + environment.get("ARCTIC").get("DiffAcc.aA")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadAcc")) * Math.pow(
						(environment.get("ARCTIC").get("ThermvelAcc.aA")
								* environment.get("ARCTIC").get("ThermvelAcc.aA")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("ARCTIC").put("kcoag.aAS.aAAcc",
				environment.get("ARCTIC").get("Fuchs.aAS.aAAcc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadAcc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("ARCTIC").get("DiffAcc.aA")
										+ environment.get("ARCTIC").get("DiffS.aA")))
						* environment.get("ARCTIC").get("NumConcAcc.aA"));

		environment.get("ARCTIC").put("NumConcNuc.aA", 3.2e+9);

		environment.get("ARCTIC").put("ThermvelNuc.aA", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)
								* inputEng.getEnvProperties("RhoNuc"))))),
				0.5));

		environment.get("ARCTIC").put("DiffNuc.aA",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadNuc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadNuc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadNuc")));

		environment.get("ARCTIC").put("Fuchs.aAS.aANuc", Math.pow((1 + (4
				* (environment.get("ARCTIC").get("DiffS.aA") + environment.get("ARCTIC").get("DiffNuc.aA")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNuc")) * Math.pow(
						(environment.get("ARCTIC").get("ThermvelNuc.aA")
								* environment.get("ARCTIC").get("ThermvelNuc.aA")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("ARCTIC").put("kcoag.aAS.aANuc",
				environment.get("ARCTIC").get("Fuchs.aAS.aANuc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadNuc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("ARCTIC").get("DiffNuc.aA")
										+ environment.get("ARCTIC").get("DiffS.aA")))
						* environment.get("ARCTIC").get("NumConcNuc.aA"));

		environment.get("ARCTIC").put("k.aAS.aAA",
				environment.get("ARCTIC").get("kcoag.aAS.aANuc") + environment.get("ARCTIC").get("kcoag.aAS.aAAcc"));

		environment.get("ARCTIC").put("fGravSSPM.wA", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelSPM.w")));

		environment.get("ARCTIC").put("fBrownSSPM.wA",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("ARCTIC").put("Shear.w2A", 10.);
		environment.get("ARCTIC").put("Shear.w3A", 10.);

		environment.get("ARCTIC").put("fInterceptSSPM.w3A", (4. / 3.) * environment.get("ARCTIC").get("Shear.w3A")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("ARCTIC").put("fTotalSSPM.w3A", environment.get("ARCTIC").get("fInterceptSSPM.w3A")
				+ +environment.get("ARCTIC").get("fBrownSSPM.wA") + environment.get("ARCTIC").get("fGravSSPM.wA"));

		environment.get("ARCTIC").put("NumConcSPM.w3A", environment.get("ARCTIC").get("SUSP.w3A")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("ARCTIC").put("k.w3AS.w3AP", environment.get("ARCTIC").get("fTotalSSPM.w3A")
				* environment.get("ARCTIC").get("NumConcSPM.w3A") * inputEng.getSubstancesData("AtefSP.w3"));

		environment.get("ARCTIC").put("fBrownSSPM.wA",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("ARCTIC").put("fInterceptSSPM.w2A", (4. / 3.) * environment.get("ARCTIC").get("Shear.w2A")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("ARCTIC").put("fTotalSSPM.w2A", environment.get("ARCTIC").get("fInterceptSSPM.w2A")
				+ +environment.get("ARCTIC").get("fBrownSSPM.wA") + environment.get("ARCTIC").get("fGravSSPM.wA"));

		environment.get("ARCTIC").put("NumConcSPM.w2A", environment.get("ARCTIC").get("SUSP.w2A")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("ARCTIC").put("k.w2AS.w2AP", environment.get("ARCTIC").get("fTotalSSPM.w2A")
				* environment.get("ARCTIC").get("NumConcSPM.w2A") * inputEng.getSubstancesData("AtefSP.w2"));

		environment.get("ARCTIC")
				.put("fGravSNC.wA", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("ARCTIC").put("fBrownSNC.wA",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.w")));

		environment.get("ARCTIC").put("fInterceptSNC.w3A", (4. / 3.) * environment.get("ARCTIC").get("Shear.w3A")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("ARCTIC").put("fTotalSNC.w3A", environment.get("ARCTIC").get("fInterceptSNC.w3A")
				+ +environment.get("ARCTIC").get("fBrownSNC.wA") + environment.get("ARCTIC").get("fGravSNC.wA"));

		environment.get("ARCTIC").put("NumConcNC.w3A", 2. * environment.get("ARCTIC").get("COL.w3A")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("ARCTIC").put("k.w3AS.w3AA", environment.get("ARCTIC").get("fTotalSNC.w3A")
				* environment.get("ARCTIC").get("NumConcNC.w3A") * inputEng.getSubstancesData("AtefSA.w3"));

		environment.get("ARCTIC").put("fInterceptSNC.w2A", (4. / 3.) * environment.get("ARCTIC").get("Shear.w2A")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("ARCTIC").put("fTotalSNC.w2A", environment.get("ARCTIC").get("fInterceptSNC.w2A")
				+ +environment.get("ARCTIC").get("fBrownSNC.wA") + environment.get("ARCTIC").get("fGravSNC.wA"));

		environment.get("ARCTIC").put("NumConcNC.w2A", 2. * environment.get("ARCTIC").get("COL.w2A")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("ARCTIC").put("k.w2AS.w2AA", environment.get("ARCTIC").get("fTotalSNC.w2A")
				* environment.get("ARCTIC").get("NumConcNC.w2A") * inputEng.getSubstancesData("AtefSA.w2"));

		environment.get("ARCTIC").put("Udarcy.sdA", 9e-6);

		environment.get("ARCTIC").put("GravNumberSP.sdA", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("ARCTIC").get("Udarcy.sdA")));

		environment.get("ARCTIC").put("PecletNumberSP.sdA", environment.get("ARCTIC").get("Udarcy.sdA") * 2.
				* inputEng.getEnvProperties("RadFP.sd") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("ARCTIC").put("aspectratioSP.sdA",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.sd"));

		environment.get("ARCTIC").put("vdWaalsNumberSP.sdA", inputEng.getSubstancesData("AHamakerSP.w")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A")));

		environment.get("ARCTIC").put("fGravSP.sdA",
				0.22 * Math.pow(environment.get("ARCTIC").get("aspectratioSP.sdA"), -0.24)
						* Math.pow(environment.get("ARCTIC").get("GravNumberSP.sdA"), 1.11)
						* Math.pow(environment.get("ARCTIC").get("vdWaalsNumberSP.sdA"), 0.053));

		environment.get("ARCTIC").put("fInterceptSP.sdA",
				0.55 * Math.pow(environment.get("ARCTIC").get("aspectratioSP.sdA"), 1.55)
						* Math.pow(environment.get("ARCTIC").get("PecletNumberSP.sdA"), -0.125)
						* Math.pow(environment.get("ARCTIC").get("vdWaalsNumberSP.sdA"), 0.125));

		environment.get("ARCTIC").put("Por.sdA", environment.get("ARCTIC").get("FRACw.sdA"));

		environment.get("ARCTIC").put("GammPDF.sdA", Math.pow(1. - environment.get("ARCTIC").get("Por.sdA"), 1. / 3.));

		environment.get("ARCTIC").put("ASPDF.sdA",
				(2. * (1. - Math.pow(environment.get("ARCTIC").get("GammPDF.sdA"), 5)))
						/ (2. - 3. * environment.get("ARCTIC").get("GammPDF.sdA")
								+ 3 * Math.pow(environment.get("ARCTIC").get("GammPDF.sdA"), 5)
								- 2 * Math.pow(environment.get("ARCTIC").get("GammPDF.sdA"), 6)));

		environment.get("ARCTIC").put("fBrownSP.sdA",
				2.4 * Math.pow(environment.get("ARCTIC").get("PecletNumberSP.sdA"), 1. / 3.)
						* Math.pow(environment.get("ARCTIC").get("aspectratioSP.sdA"), -0.081)
						* Math.pow(environment.get("ARCTIC").get("PecletNumberSP.sdA"), -0.715)
						* Math.pow(environment.get("ARCTIC").get("vdWaalsNumberSP.sdA"), 0.053));

		environment.get("ARCTIC").put("fTotalSP.sdA", environment.get("ARCTIC").get("fBrownSP.sdA")
				+ environment.get("ARCTIC").get("fInterceptSP.sdA") + environment.get("ARCTIC").get("fGravSP.sdA"));

		environment.get("ARCTIC").put("Filter.sdA", (3. / 2.) * ((1. - environment.get("ARCTIC").get("Por.sdA"))
				/ (2 * inputEng.getEnvProperties("RadFP.sd") * environment.get("ARCTIC").get("Por.sdA"))));

		environment.get("ARCTIC").put("k.sdAS.sdAP",
				environment.get("ARCTIC").get("Filter.sdA") * environment.get("ARCTIC").get("Udarcy.sdA")
						* environment.get("ARCTIC").get("fTotalSP.sdA") * inputEng.getSubstancesData("AtefSP.sd2"));

		environment.get("ARCTIC").put("fGravSNC.sdA", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.sd")));

		environment.get("ARCTIC").put("fBrownSNC.sdA",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.sd")));

		environment.get("ARCTIC").put("NumConcNC.sdA", environment.get("ARCTIC").get("NumConcNC.w2A"));

		environment.get("ARCTIC").put("k.sdAS.sdAA", inputEng.getSubstancesData("AtefSA.sd2")
				* environment.get("ARCTIC").get("NumConcNC.sdA")
				* (environment.get("ARCTIC").get("fBrownSNC.sdA") + environment.get("ARCTIC").get("fGravSNC.sdA")));

		environment.get("ARCTIC").put("Udarcy.sA", 9e-6);
		environment.get("ARCTIC").put("mConcNC.sA", 100. / 1000.);

		environment.get("ARCTIC").put("GravNumberS.sA", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("ARCTIC").get("Udarcy.sA")));

		environment.get("ARCTIC").put("vdWaalsNumberSFP.sA", inputEng.getSubstancesData("AHamakerSP.w")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A")));

		environment.get("ARCTIC").put("PecletNumberFP.sA", environment.get("ARCTIC").get("Udarcy.sA") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("ARCTIC").put("aspectratioSFP.sA",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("ARCTIC").put("fGravSFP.sA",
				0.22 * Math.pow(environment.get("ARCTIC").get("aspectratioSFP.sA"), -0.24)
						* Math.pow(environment.get("ARCTIC").get("GravNumberS.sA"), 1.11)
						* Math.pow(environment.get("ARCTIC").get("vdWaalsNumberSFP.sA"), 0.053));

		environment.get("ARCTIC").put("fInterceptSFP.sA",
				0.55 * Math.pow(environment.get("ARCTIC").get("aspectratioSFP.sA"), 1.55)
						* Math.pow(environment.get("ARCTIC").get("PecletNumberFP.sA"), -0.125)
						* Math.pow(environment.get("ARCTIC").get("vdWaalsNumberSFP.sA"), 0.125));

		environment.get("ARCTIC").put("Por.sA", 1. - environment.get("ARCTIC").get("FRACs.sA"));

		environment.get("ARCTIC").put("GammPDF.sA", Math.pow(1. - environment.get("ARCTIC").get("Por.sA"), 1. / 3.));

		environment.get("ARCTIC").put("ASPDF.sA",
				(2. * (1. - Math.pow(environment.get("ARCTIC").get("GammPDF.sA"), 5)))
						/ (2. - 3. * environment.get("ARCTIC").get("GammPDF.sA")
								+ 3 * Math.pow(environment.get("ARCTIC").get("GammPDF.sA"), 5)
								- 2 * Math.pow(environment.get("ARCTIC").get("GammPDF.sA"), 6)));

		environment.get("ARCTIC").put("fBrownSFP.sA",
				2.4 * Math.pow(environment.get("ARCTIC").get("ASPDF.sA"), 1. / 3.)
						* Math.pow(environment.get("ARCTIC").get("aspectratioSFP.sA"), -0.081)
						* Math.pow(environment.get("ARCTIC").get("PecletNumberFP.sA"), -0.715)
						* Math.pow(environment.get("ARCTIC").get("vdWaalsNumberSFP.sA"), 0.053));

		environment.get("ARCTIC").put("fTotalSFP.sA", environment.get("ARCTIC").get("fBrownSFP.sA")
				+ environment.get("ARCTIC").get("fInterceptSFP.sA") + environment.get("ARCTIC").get("fGravSFP.sA"));

		environment.get("ARCTIC").put("Filter.sA", (3. / 2.) * ((1. - environment.get("ARCTIC").get("Por.sA"))
				/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("ARCTIC").get("Por.sA"))));

		environment.get("ARCTIC").put("k.sAS.sAP",
				environment.get("ARCTIC").get("Filter.sA") * environment.get("ARCTIC").get("Udarcy.sA")
						* environment.get("ARCTIC").get("fTotalSFP.sA") * inputEng.getSubstancesData("AtefSP.s1"));

		environment.get("ARCTIC")
				.put("fGravSNC.sA", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.s")));

		environment.get("ARCTIC").put("fBrownSNC.sA",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.s")));

		environment.get("ARCTIC").put("NumConcNC.sA", environment.get("ARCTIC").get("mConcNC.sA")
				/ (inputEng.getEnvProperties("SingleVolNC.s") * inputEng.getEnvProperties("RhoNC.s")));

		environment.get("ARCTIC").put("k.sAS.sAA", inputEng.getSubstancesData("AtefSA.s1")
				* environment.get("ARCTIC").get("NumConcNC.sA")
				* (environment.get("ARCTIC").get("fBrownSNC.sA") + environment.get("ARCTIC").get("fGravSNC.sA")));

		environment.get("ARCTIC").put("Cunningham.cwA", 1.);
		environment.get("ARCTIC").put("tdry.A", 3.3 * 24 * 3600.);
		environment.get("ARCTIC").put("twet.A", 3600. * 3.3 * 24. * (0.06 / (1. - 0.06)));

		environment.get("ARCTIC").put("FRACtwet.A", environment.get("ARCTIC").get("twet.A")
				/ (environment.get("ARCTIC").get("twet.A") + environment.get("ARCTIC").get("tdry.A")));

		environment.get("ARCTIC").put("RAINrate.wet.A",
				environment.get("ARCTIC").get("RAINrate.A") / environment.get("ARCTIC").get("FRACtwet.A"));

		environment.get("ARCTIC").put("Rad.cwA",
				((0.7 * (Math.pow(60. * 60. * 1000. * environment.get("ARCTIC").get("RAINrate.wet.A"), 0.25)) / 2.)
						/ 1000.));

		environment.get("ARCTIC").put("TerminalVel.cwA",
				(Math.pow(2 * environment.get("ARCTIC").get("Rad.cwA"), 2)
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")
								- inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * environment.get("ARCTIC").get("Cunningham.cwA"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("ARCTIC").put("ReyNumber.cwA",
				((2. * environment.get("ARCTIC").get("Rad.cwA")) * environment.get("ARCTIC").get("TerminalVel.cwA")
						* inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						/ (2. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("ARCTIC").put("CritStokesNumb.cwA",
				(1.2 + (1. / 12.) * Math.log(1. + environment.get("ARCTIC").get("ReyNumber.cwA")))
						/ (1. + Math.log(environment.get("ARCTIC").get("ReyNumber.cwA"))));

		environment.get("ARCTIC")
				.put("SingleMassA.aA",
						((environment.get("ARCTIC").get("NumConcNuc.aA")
								* (inputEng.getEnvProperties("RhoNuc")
										* ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3))
										+ inputEng.getSubstancesData("RhoS")
												* inputEng.getNanoProperties("SingleVolS")))
								+ (environment.get("ARCTIC").get("NumConcAcc.aA") * (inputEng.getEnvProperties("RhoAcc")
										* ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3))
										+ inputEng.getSubstancesData("RhoS")
												* inputEng.getNanoProperties("SingleVolS"))))
								/ (environment.get("ARCTIC").get("NumConcNuc.aA")
										+ environment.get("ARCTIC").get("NumConcAcc.aA")));

		environment.get("ARCTIC").put("SingleVolA.aA",
				((environment.get("ARCTIC").get("NumConcNuc.aA") * (inputEng.getNanoProperties("SingleVolS")
						+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)))))
						+ (environment.get("ARCTIC").get("NumConcAcc.aA") * (inputEng.getNanoProperties("SingleVolS")
								+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3))))))
						/ (environment.get("ARCTIC").get("NumConcNuc.aA")
								+ environment.get("ARCTIC").get("NumConcAcc.aA")));

		environment.get("ARCTIC").put("RhoA.aA",
				environment.get("ARCTIC").get("SingleMassA.aA") / environment.get("ARCTIC").get("SingleVolA.aA"));

		environment.get("ARCTIC").put("RadA.aA",
				Math.pow((environment.get("ARCTIC").get("SingleVolA.aA")) / ((4. / 3.) * Math.PI), 1. / 3.));

		environment.get("ARCTIC").put("RelaxS.aA",
				((inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * inputEng.getSubstancesData("RadS")), 2)
						* inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("ARCTIC").put("SettvelS.aA",
				(Math.pow(2. * inputEng.getSubstancesData("RadS"), 2)
						* (inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("ARCTIC").put("StokesNumberS.aA", (2. * environment.get("ARCTIC").get("RelaxS.aA")
				* (environment.get("ARCTIC").get("TerminalVel.cwA") - environment.get("ARCTIC").get("SettvelS.aA")))
				/ (2. * environment.get("ARCTIC").get("Rad.cwA")));

		environment.get("ARCTIC").put("SchmidtNumberS.aA", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("ARCTIC").get("DiffS.aA")));

		environment.get("ARCTIC").put("fBrown.aAS.cwAS", (4.
				/ (environment.get("ARCTIC").get("ReyNumber.cwA") * environment.get("ARCTIC").get("SchmidtNumberS.aA")))
				* (1. + 0.4 * (Math.pow(environment.get("ARCTIC").get("ReyNumber.cwA"), 0.5)
						* Math.pow(environment.get("ARCTIC").get("SchmidtNumberS.aA"), 1. / 3.))
						+ 0.16 * (Math.pow(environment.get("ARCTIC").get("ReyNumber.cwA"), 0.5)
								* Math.pow(environment.get("ARCTIC").get("SchmidtNumberS.aA"), 0.5))));

		environment.get("ARCTIC").put("fIntercept.aAS.cwAS", 4.
				* (inputEng.getSubstancesData("RadS") / environment.get("ARCTIC").get("Rad.cwA"))
				* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
						/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						+ (1. + 2. * Math.pow(environment.get("ARCTIC").get("ReyNumber.cwA"), 0.5)
								* (inputEng.getSubstancesData("RadS") / environment.get("ARCTIC").get("Rad.cwA")))));

		if (environment.get("ARCTIC").get("StokesNumberS.aA") > environment.get("ARCTIC").get("CritStokesNumb.cwA"))
			environment.get("ARCTIC").put("fGrav.aAS.cwAS",
					Math.pow(((environment.get("ARCTIC").get("StokesNumberS.aA")
							- environment.get("ARCTIC").get("CritStokesNumb.cwA"))
							/ environment.get("ARCTIC").get("StokesNumberS.aA")
							- environment.get("ARCTIC").get("CritStokesNumb.cwA") + 2. / 3.), 3. / 2.));
		else
			environment.get("ARCTIC").put("fGrav.aAS.cwAS", 0.0);

		environment.get("ARCTIC").put("fTotal.aAS.cwAS",
				environment.get("ARCTIC").get("fBrown.aAS.cwAS") + environment.get("ARCTIC").get("fIntercept.aAS.cwAS")
						+ environment.get("ARCTIC").get("fGrav.aAS.cwAS"));

		environment.get("ARCTIC").put("k.aAS.cwAS", ((3. / 2.)
				* (environment.get("ARCTIC").get("fTotal.aAS.cwAS") * environment.get("ARCTIC").get("RAINrate.wet.A"))
				/ (2. * environment.get("ARCTIC").get("Rad.cwA"))));

		environment.get("ARCTIC").put("CunninghamA.aA", 1. + (66.e-9 / environment.get("ARCTIC").get("RadA.aA"))
				* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / environment.get("ARCTIC").get("RadA.aA")))));

		environment.get("ARCTIC").put("DiffA.aA",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("ARCTIC").get("TEMP.A")
						* environment.get("ARCTIC").get("CunninghamA.aA"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* environment.get("ARCTIC").get("RadA.aA")));

		environment.get("ARCTIC").put("RelaxA.aA",
				((environment.get("ARCTIC").get("RhoA.aA") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * environment.get("ARCTIC").get("RadA.aA")), 2)
						* environment.get("ARCTIC").get("CunninghamA.aA"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("ARCTIC").put("SettvelA.aA", (Math.pow(2. * environment.get("ARCTIC").get("RadA.aA"), 2)
				* (environment.get("ARCTIC").get("RhoA.aA") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
				* 9.81 * environment.get("ARCTIC").get("CunninghamA.aA"))
				/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("ARCTIC").put("StokesNumberA.aA", (2. * environment.get("ARCTIC").get("RelaxA.aA")
				* (environment.get("ARCTIC").get("TerminalVel.cwA") - environment.get("ARCTIC").get("SettvelA.aA")))
				/ (2. * environment.get("ARCTIC").get("Rad.cwA")));

		environment.get("ARCTIC").put("SchmidtNumberA.aA", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("ARCTIC").get("DiffA.aA")));

		environment.get("ARCTIC").put("fBrown.aAA.cwAA", (4.
				/ (environment.get("ARCTIC").get("ReyNumber.cwA") * environment.get("ARCTIC").get("SchmidtNumberA.aA")))
				* (1. + 0.4 * (Math.pow(environment.get("ARCTIC").get("ReyNumber.cwA"), 0.5)
						* Math.pow(environment.get("ARCTIC").get("SchmidtNumberA.aA"), 1. / 3.))
						+ 0.16 * (Math.pow(environment.get("ARCTIC").get("ReyNumber.cwA"), 0.5)
								* Math.pow(environment.get("ARCTIC").get("SchmidtNumberA.aA"), 0.5))));

		environment.get("ARCTIC").put("fIntercept.aAA.cwAA",
				4. * (environment.get("ARCTIC").get("RadA.aA") / environment.get("ARCTIC").get("Rad.cwA"))
						* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
								+ (1. + 2. * Math.pow(environment.get("ARCTIC").get("ReyNumber.cwA"), 0.5)
										* (environment.get("ARCTIC").get("RadA.aA")
												/ environment.get("ARCTIC").get("Rad.cwA")))));

		if (environment.get("ARCTIC").get("StokesNumberA.aA") > environment.get("ARCTIC").get("CritStokesNumb.cwA"))
			environment.get("ARCTIC").put("fGrav.aMA.cwAA",
					Math.pow(((environment.get("ARCTIC").get("StokesNumberA.aA")
							- environment.get("ARCTIC").get("CritStokesNumb.cwA"))
							/ environment.get("ARCTIC").get("StokesNumberA.aA")
							- environment.get("ARCTIC").get("CritStokesNumb.cwA") + 2. / 3.), 3. / 2.));
		else
			environment.get("ARCTIC").put("fGrav.aAA.cwAA", 0.0);

		environment.get("ARCTIC").put("fTotal.aAA.cwAA",
				environment.get("ARCTIC").get("fBrown.aAA.cwAA") + environment.get("ARCTIC").get("fIntercept.aAA.cwAA")
						+ environment.get("ARCTIC").get("fGrav.aAA.cwAA"));

		environment.get("ARCTIC").put("k.aAA.cwAA", ((3. / 2.)
				* (environment.get("ARCTIC").get("fTotal.aAA.cwAA") * environment.get("ARCTIC").get("RAINrate.wet.A"))
				/ (2. * environment.get("ARCTIC").get("Rad.cwA"))));

		environment.get("ARCTIC").put("AEROresist.sA", 74.);
		environment.get("ARCTIC").put("FRICvelocity.sA", 0.19);
		environment.get("ARCTIC").put("veghair.sA", 1e-5);
		environment.get("ARCTIC").put("largevegradius.sA", 0.0005);
		environment.get("ARCTIC").put("AREAFRACveg.sA", 0.01);

		environment.get("ARCTIC").put("fGrav.aAS.sAS", Math.pow((environment.get("ARCTIC").get("StokesNumberS.aA")
				/ (environment.get("ARCTIC").get("StokesNumberS.aA") + 0.8)), 2));

		environment.get("ARCTIC").put("fBrown.aAS.sAS",
				Math.pow(environment.get("ARCTIC").get("SchmidtNumberS.aA"), -2. / 3.));

		environment.get("ARCTIC").put("fIntercept.aAS.sAS", 0.3 * (environment.get("ARCTIC").get("AREAFRACveg.sA")
				* (inputEng.getSubstancesData("RadS")
						/ (inputEng.getSubstancesData("RadS") + environment.get("ARCTIC").get("veghair.sA")))
				+ (1 - environment.get("ARCTIC").get("AREAFRACveg.sA")) * (inputEng.getSubstancesData("RadS")
						/ (inputEng.getSubstancesData("RadS") + environment.get("ARCTIC").get("largevegradius.sA")))));

		environment.get("ARCTIC").put("SURFresist.sAS",
				1 / (environment.get("ARCTIC").get("FRICvelocity.sA") * (environment.get("ARCTIC").get("fBrown.aAS.sAS")
						+ environment.get("ARCTIC").get("fIntercept.aAS.sAS")
						+ environment.get("ARCTIC").get("fGrav.aAS.sAS"))));

		environment.get("ARCTIC").put("DEPvelocity.aAS.sAS",
				1. / (environment.get("ARCTIC").get("AEROresist.sA") + environment.get("ARCTIC").get("SURFresist.sAS"))
						+ environment.get("ARCTIC").get("SettvelS.aA"));

		environment.get("ARCTIC").put("k.aAS.sAS",
				(environment.get("ARCTIC").get("DEPvelocity.aAS.sAS") * environment.get("ARCTIC").get("AREAFRAC.sA")
						* environment.get("ARCTIC").get("SYSTEMAREA.A"))
						/ (environment.get("ARCTIC").get("VOLUME.aA")));

		environment.get("ARCTIC").put("COLLECTeff.A", 200000.);

		environment.get("ARCTIC").put("k.aAP.cwAP",
				((environment.get("ARCTIC").get("tdry.A") + environment.get("ARCTIC").get("twet.A"))
						/ environment.get("ARCTIC").get("twet.A") * environment.get("ARCTIC").get("COLLECTeff.A")
						* environment.get("ARCTIC").get("RAINrate.A")) / environment.get("ARCTIC").get("HEIGHT.aA"));

		environment.get("ARCTIC").put("fBrown.aAA.sAA",
				Math.pow(environment.get("ARCTIC").get("SchmidtNumberA.aA"), -2. / 3.));

		environment.get("ARCTIC").put("fIntercept.aAA.sAA",
				0.3 * (environment.get("ARCTIC").get("AREAFRACveg.sA") * (environment.get("ARCTIC").get("RadA.aA")
						/ (environment.get("ARCTIC").get("RadA.aA") + environment.get("ARCTIC").get("veghair.sA")))
						+ (1 - environment.get("ARCTIC").get("AREAFRACveg.sA"))
								* (environment.get("ARCTIC").get("RadA.aA") / (environment.get("ARCTIC").get("RadA.aA")
										+ environment.get("ARCTIC").get("largevegradius.sA")))));

		environment.get("ARCTIC").put("fGrav.aAAsMA", Math.pow((environment.get("ARCTIC").get("StokesNumberA.aA")
				/ (environment.get("ARCTIC").get("StokesNumberA.aA") + 0.8)), 2));

		environment.get("ARCTIC").put("SURFresist.sAA",
				1 / (environment.get("ARCTIC").get("FRICvelocity.sA") * (environment.get("ARCTIC").get("fBrown.aAA.sAA")
						+ environment.get("ARCTIC").get("fIntercept.aAA.sAA")
						+ environment.get("ARCTIC").get("fGrav.aAAsMA"))));

		environment.get("ARCTIC").put("DEPvelocity.aAA.sAA",
				1. / (environment.get("ARCTIC").get("AEROresist.sA") + environment.get("ARCTIC").get("SURFresist.sAA"))
						+ environment.get("ARCTIC").get("SettvelA.aA"));

		environment.get("ARCTIC").put("k.aAA.sAA",
				(environment.get("ARCTIC").get("DEPvelocity.aAA.sAA") * environment.get("ARCTIC").get("AREAFRAC.sA")
						* environment.get("ARCTIC").get("SYSTEMAREA.A"))
						/ (environment.get("ARCTIC").get("VOLUME.aA")));

		environment.get("ARCTIC").put("AEROSOLdeprate.A", 0.001);

		environment.get("ARCTIC").put("k.aAP.sAP",
				environment.get("ARCTIC").get("AEROSOLdeprate.A") * environment.get("ARCTIC").get("AREAFRAC.sA")
						* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.aA"));

		environment.get("ARCTIC").put("AEROresist.w2A", 135.);
		environment.get("ARCTIC").put("FRICvelocity.wA", 0.19);

		environment.get("ARCTIC").put("fGrav.aAS.w",
				Math.pow(10, -3. / environment.get("ARCTIC").get("StokesNumberS.aA")));

		environment.get("ARCTIC").put("fBrown.aAS.w",
				Math.pow(environment.get("ARCTIC").get("SchmidtNumberS.aA"), -1. / 2.));

		environment.get("ARCTIC").put("fIntercept.aAA.sAA",
				0.3 * (environment.get("ARCTIC").get("AREAFRACveg.sA") * (environment.get("ARCTIC").get("RadA.aA")
						/ (environment.get("ARCTIC").get("RadA.aA") + environment.get("ARCTIC").get("veghair.sA")))
						+ (1 - environment.get("ARCTIC").get("AREAFRACveg.sA"))
								* (environment.get("ARCTIC").get("RadA.aA") / (environment.get("ARCTIC").get("RadA.aA")
										+ environment.get("ARCTIC").get("largevegradius.sA")))));

		environment.get("ARCTIC").put("SURFresist.w2AS", 1. / (environment.get("ARCTIC").get("FRICvelocity.wA")
				* (environment.get("ARCTIC").get("fBrown.aAS.w") + environment.get("ARCTIC").get("fGrav.aAS.w"))));

		environment.get("ARCTIC").put("DEPvelocity.aAS.w2AS", 1.
				/ (environment.get("ARCTIC").get("AEROresist.w2A") + environment.get("ARCTIC").get("SURFresist.w2AS"))
				+ environment.get("ARCTIC").get("SettvelS.aA"));

		environment.get("ARCTIC").put("k.aAS.w2AS",
				(environment.get("ARCTIC").get("DEPvelocity.aAS.w2AS") * environment.get("ARCTIC").get("AREAFRAC.wA")
						* environment.get("ARCTIC").get("SYSTEMAREA.A"))
						/ (environment.get("ARCTIC").get("VOLUME.aA")));

		environment.get("ARCTIC").put("fGrav.aAA.w",
				Math.pow(10, -3. / environment.get("ARCTIC").get("StokesNumberA.aA")));

		environment.get("ARCTIC").put("fBrown.aAA.w",
				Math.pow(environment.get("ARCTIC").get("SchmidtNumberA.aA"), -1. / 2.));

		environment.get("ARCTIC").put("SURFresist.wAA", 1. / (environment.get("ARCTIC").get("FRICvelocity.wA")
				* (environment.get("ARCTIC").get("fBrown.aAA.w") + environment.get("ARCTIC").get("fGrav.aAA.w"))));

		environment.get("ARCTIC").put("DEPvelocity.aAA.w2AA",
				1. / (environment.get("ARCTIC").get("AEROresist.w2A") + environment.get("ARCTIC").get("SURFresist.wAA"))
						+ environment.get("ARCTIC").get("SettvelA.aA"));

		environment.get("ARCTIC").put("fBrown.aAA.w",
				Math.pow(environment.get("ARCTIC").get("SchmidtNumberA.aA"), -1. / 2.));

		environment.get("ARCTIC").put("SURFresist.wAA", 1. / (environment.get("ARCTIC").get("FRICvelocity.wA")
				* (environment.get("ARCTIC").get("fBrown.aAA.w") + environment.get("ARCTIC").get("fGrav.aAA.w"))));

		environment.get("ARCTIC").put("DEPvelocity.aAA.w2AA",
				1. / (environment.get("ARCTIC").get("AEROresist.w2A") + environment.get("ARCTIC").get("SURFresist.wAA"))
						+ environment.get("ARCTIC").get("SettvelA.aA"));

		environment.get("ARCTIC").put("k.aAA.w2AA",
				(environment.get("ARCTIC").get("DEPvelocity.aAA.w2AA") * environment.get("ARCTIC").get("AREAFRAC.wA")
						* environment.get("ARCTIC").get("SYSTEMAREA.A"))
						/ (environment.get("ARCTIC").get("VOLUME.aA")));

		environment.get("ARCTIC").put("k.aAA.w2AA",
				(environment.get("ARCTIC").get("DEPvelocity.aAA.w2AA") * environment.get("ARCTIC").get("AREAFRAC.wA")
						* environment.get("ARCTIC").get("SYSTEMAREA.A"))
						/ (environment.get("ARCTIC").get("VOLUME.aA")));

		environment.get("ARCTIC")
				.put("k.aAP.w2AP", (environment.get("ARCTIC").get("AEROSOLdeprate.A")
						* environment.get("ARCTIC").get("AREAFRAC.wA") * environment.get("ARCTIC").get("SYSTEMAREA.A"))
						/ environment.get("ARCTIC").get("VOLUME.aA"));

		environment.get("ARCTIC")
				.put("k.cwAS.sAS",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.sA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		environment.get("ARCTIC")
				.put("k.cwAA.sAA",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.sA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		environment.get("ARCTIC")
				.put("k.cwAP.sAP",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.sA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		environment.get("ARCTIC")
				.put("k.cwAS.w2AS",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.wA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		environment.get("ARCTIC")
				.put("k.cwAA.w2AA",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.wA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		environment.get("ARCTIC")
				.put("k.cwAP.w2AP",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.wA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		environment.get("ARCTIC")
				.put("k.cwAP.w2AP",
						(environment.get("ARCTIC").get("RAINrate.A") * environment.get("ARCTIC").get("AREAFRAC.wA")
								* environment.get("ARCTIC").get("SYSTEMAREA.A"))
								/ environment.get("ARCTIC").get("VOLUME.cwA"));

		if (inputEng.nanoProperties.get("SetVelS.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.w2A") > 0)
			environment.get("ARCTIC")
					.put("k.w2AS.w3AS",
							environment.get("ARCTIC").get("k.w2A.w3A") + (inputEng.nanoProperties.get("SetVelS.w")
									* environment.get("ARCTIC").get("AREAFRAC.wA")
									* environment.get("ARCTIC").get("SYSTEMAREA.A"))
									/ environment.get("ARCTIC").get("VOLUME.w2A"));
		else
			environment.get("ARCTIC").put("k.w2AS.w3AS", environment.get("ARCTIC").get("k.w2A.w3A"));

		if (inputEng.nanoProperties.get("SetVelA.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.w2A") > 0)
			environment.get("ARCTIC")
					.put("k.w2AA.w3AA",
							environment.get("ARCTIC").get("k.w2A.w3A") + (inputEng.nanoProperties.get("SetVelA.w")
									* environment.get("ARCTIC").get("AREAFRAC.wA")
									* environment.get("ARCTIC").get("SYSTEMAREA.A"))
									/ environment.get("ARCTIC").get("VOLUME.w2A"));
		else
			environment.get("ARCTIC").put("k.w2AA.w3AA", environment.get("ARCTIC").get("k.w2A.w3A"));

		if (inputEng.nanoProperties.get("SetVelP.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.w2A") > 0)
			environment.get("ARCTIC")
					.put("k.w2AP.w3AP",
							environment.get("ARCTIC").get("k.w2A.w3A") + (inputEng.nanoProperties.get("SetVelP.w")
									* environment.get("ARCTIC").get("AREAFRAC.wA")
									* environment.get("ARCTIC").get("SYSTEMAREA.A"))
									/ environment.get("ARCTIC").get("VOLUME.w2A"));
		else
			environment.get("ARCTIC").put("k.w2AP.w3AP", environment.get("ARCTIC").get("k.w2A.w3A"));

		if (inputEng.nanoProperties.get("SetVelS.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.w3A") > 0)
			environment.get("ARCTIC").put("k.w3AS.sdAS",
					(inputEng.nanoProperties.get("SetVelS.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
							* environment.get("ARCTIC").get("SYSTEMAREA.A"))
							/ environment.get("ARCTIC").get("VOLUME.w3A"));
		else
			environment.get("ARCTIC").put("k.w3AS.sdAS", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.w3A") > 0)
			environment.get("ARCTIC").put("k.w3AA.sdAA",
					(inputEng.nanoProperties.get("SetVelA.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
							* environment.get("ARCTIC").get("SYSTEMAREA.A"))
							/ environment.get("ARCTIC").get("VOLUME.w3A"));
		else
			environment.get("ARCTIC").put("k.w3AA.sdAA", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
				* environment.get("ARCTIC").get("SYSTEMAREA.A") / environment.get("ARCTIC").get("VOLUME.w3A") > 0)
			environment.get("ARCTIC").put("k.w3AP.sdAP",
					(inputEng.nanoProperties.get("SetVelP.w") * environment.get("ARCTIC").get("AREAFRAC.wA")
							* environment.get("ARCTIC").get("SYSTEMAREA.A"))
							/ environment.get("ARCTIC").get("VOLUME.w3A"));
		else
			environment.get("ARCTIC").put("k.w3AP.sdAP", 0.);

		environment.get("ARCTIC").put("k.sdAS.w3AS",
				environment.get("ARCTIC").get("RESUSP.sdA.w3A") / environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("k.sdAA.w3AA",
				environment.get("ARCTIC").get("RESUSP.sdA.w3A") / environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("k.sdAP.w3AP",
				environment.get("ARCTIC").get("RESUSP.sdA.w3A") / environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("k.sAS.w2AS",
				(environment.get("ARCTIC").get("FRACrun.sA") * environment.get("ARCTIC").get("RAINrate.A")
						* environment.get("ARCTIC").get("CORRrunoff.sA") + environment.get("ARCTIC").get("EROSION.sA"))
						/ environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("k.sAA.w2AA",
				(environment.get("ARCTIC").get("FRACrun.sA") * environment.get("ARCTIC").get("RAINrate.A")
						* environment.get("ARCTIC").get("CORRrunoff.sA") + environment.get("ARCTIC").get("EROSION.sA"))
						/ environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("k.sAP.w2AP",
				environment.get("ARCTIC").get("EROSION.sA") / environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("kesc.aA", Math.log(2.) / (60. * 365. * 24. * 3600.));

		environment.get("ARCTIC").put("k.aAS",
				environment.get("ARCTIC").get("kesc.aA") + inputEng.getSubstancesData("kdegS.a"));

		environment.get("ARCTIC").put("k.aAP",
				environment.get("ARCTIC").get("kesc.aA") + inputEng.getSubstancesData("kdegP.a"));

		environment.get("ARCTIC").put("k.w2AS", inputEng.getSubstancesData("kdegS.w2"));

		environment.get("ARCTIC").put("k.w2AP", inputEng.getSubstancesData("kdegP.w2"));

		environment.get("ARCTIC").put("k.w3AS", inputEng.getSubstancesData("kdegS.w3"));

		environment.get("ARCTIC").put("k.w3AP", inputEng.getSubstancesData("kdegP.w3"));

		environment.get("ARCTIC").put("k.w3AP", inputEng.getSubstancesData("kdegP.w3"));

		environment.get("ARCTIC").put("kbur.sdA",
				environment.get("ARCTIC").get("NETsedrate.wA") / environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("k.sdAS",
				environment.get("ARCTIC").get("kbur.sdA") + inputEng.getSubstancesData("kdegS.sd2"));

		environment.get("ARCTIC").put("k.sdAA",
				environment.get("ARCTIC").get("kbur.sdA") + inputEng.getSubstancesData("kdegA.sd2"));

		environment.get("ARCTIC").put("k.sdAP",
				environment.get("ARCTIC").get("kbur.sdA") + inputEng.getSubstancesData("kdegP.sd2"));

		environment.get("ARCTIC").put("FRACinf.sA", 0.25);

		environment.get("ARCTIC").put("CORRleach.sA",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")))));

		environment.get("ARCTIC")
				.put("kleach.sA",
						((environment.get("ARCTIC").get("FRACinf.sA") * environment.get("ARCTIC").get("RAINrate.A")
								* environment.get("ARCTIC").get("CORRleach.sA"))
								/ environment.get("ARCTIC").get("DEPTH.sA")));

		environment.get("ARCTIC").put("k.sAS",
				environment.get("ARCTIC").get("kleach.sA") + inputEng.getSubstancesData("kdegS.s1"));

		environment.get("ARCTIC").put("k.sAA",
				environment.get("ARCTIC").get("kleach.sA") + inputEng.getSubstancesData("kdegA.s1"));

		environment.get("ARCTIC").put("k.sAP", inputEng.getSubstancesData("kdegP.s1"));
	}

	public void globalTropical() {
		environment.put("TROPICAL", new HashMap<String, Double>());

		environment.get("TROPICAL").put("FRACw.sdT", 0.8);
		environment.get("TROPICAL").put("FRACs.sdT", 1 - environment.get("TROPICAL").get("FRACw.sdT"));

		environment.get("TROPICAL").put("FRACw.sT", 0.2);
		environment.get("TROPICAL").put("FRACa.sT", 0.2);

		environment.get("TROPICAL").put("FRACs.sT",
				1 - environment.get("TROPICAL").get("FRACa.sT") - environment.get("TROPICAL").get("FRACw.sT"));

		environment.get("TROPICAL").put("FRACaerw.aT", 2e-11);
		environment.get("TROPICAL").put("FRACaers.aT", 2e-11);
		environment.get("TROPICAL").put("FRACcldw.aT", 3e-7);

		environment.get("TROPICAL").put("DEPTH.sT", 0.05);
		environment.get("TROPICAL").put("DEPTH.sdT", 3. / 100.);

		environment.get("TROPICAL").put("DEPTH.w2T", 100.);
		environment.get("TROPICAL").put("DEPTH.w3T", 3000.);

		environment.get("TROPICAL").put("HEIGHT.aT", 1000.);

		environment.get("TROPICAL").put("AREAFRAC.wT", 0.7);

		environment.get("TROPICAL").put("AREAFRAC.sT", 1 - environment.get("TROPICAL").get("AREAFRAC.wT"));

		environment.get("TROPICAL").put("SYSTEMAREA.T", 127500000. * 1000000.);

		environment.get("TROPICAL").put("VOLUME.aT", environment.get("TROPICAL").get("SYSTEMAREA.T")
				* environment.get("TROPICAL").get("HEIGHT.aT") * (1. - environment.get("TROPICAL").get("FRACcldw.aT")));

		environment.get("TROPICAL").put("VOLUME.cwT",
				environment.get("TROPICAL").get("VOLUME.aT") * environment.get("TROPICAL").get("FRACcldw.aT"));

		environment.get("TROPICAL").put("VOLUME.w2T", environment.get("TROPICAL").get("SYSTEMAREA.T")
				* environment.get("TROPICAL").get("AREAFRAC.wT") * environment.get("TROPICAL").get("DEPTH.w2T"));

		environment.get("TROPICAL").put("VOLUME.w3T", environment.get("TROPICAL").get("SYSTEMAREA.T")
				* environment.get("TROPICAL").get("AREAFRAC.wT") * environment.get("TROPICAL").get("DEPTH.w3T"));

		environment.get("TROPICAL").put("VOLUME.sdT", environment.get("TROPICAL").get("SYSTEMAREA.T")
				* environment.get("TROPICAL").get("AREAFRAC.wT") * environment.get("TROPICAL").get("DEPTH.sdT"));

		environment.get("TROPICAL").put("VOLUME.sT", environment.get("TROPICAL").get("SYSTEMAREA.T")
				* environment.get("TROPICAL").get("AREAFRAC.sT") * environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("TAU.surfT", 55. * 3600. * 24.);

		environment.get("TROPICAL").put("OceanMixing.T",
				environment.get("TROPICAL").get("VOLUME.w2T") / environment.get("TROPICAL").get("TAU.surfT"));

		environment.get("TROPICAL").put("k.w3T.w2T",
				(environment.get("MODERATE").get("OceanCurrent") + environment.get("TROPICAL").get("OceanMixing.T"))
						/ environment.get("TROPICAL").get("VOLUME.w3T"));

		environment.get("TROPICAL").put("k.w2T.w3T",
				(environment.get("MODERATE").get("OceanCurrent") + environment.get("TROPICAL").get("OceanMixing.T"))
						/ environment.get("TROPICAL").get("VOLUME.w2T"));

		environment.get("TROPICAL").put("WATERflow.w2T.w2M", environment.get("MODERATE").get("OceanCurrent"));

		environment.get("TROPICAL").put("k.w3T.w3M", 0.);

		environment.get("TROPICAL").put("k.w2T.w2M",
				environment.get("TROPICAL").get("WATERflow.w2T.w2M") / environment.get("TROPICAL").get("VOLUME.w2T"));

		environment.get("TROPICAL").put("WINDspeed.T", 3.);

		environment.get("TROPICAL").put("TAU.aT",
				(1.5 * (0.5 * Math.sqrt(environment.get("TROPICAL").get("SYSTEMAREA.T") * Math.PI / 4.)
						/ environment.get("TROPICAL").get("WINDspeed.T")) / (3600. * 24.)) * (3600 * 24));

		environment.get("TROPICAL").put("k.aT.aM", 1. / environment.get("TROPICAL").get("TAU.aT"));

		environment.get("TROPICAL").put("CORRrunoff.sT",
				(Math.exp((-1. / 0.1) * 0.) * (1. / 0.1) * environment.get("TROPICAL").get("DEPTH.sT")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("TROPICAL").get("DEPTH.sT")))));

		environment.get("TROPICAL").put("FRACrun.sT", inputEng.getLandscapeSettings("CONTINENTAL", "FRACrun.C"));
		environment.get("TROPICAL").put("EROSION.sT", inputEng.getLandscapeSettings("CONTINENTAL", "EROSION.C"));

		environment.get("TROPICAL").put("TEMP.T", 25. + 273.);

		environment.get("TROPICAL").put("RAINrate.T", 1300. / (1000. * 3600. * 24. * 365.));

		environment.get("TROPICAL").put("CORG.sT", 0.02);

		environment.get("TROPICAL").put("Kp.sT",
				(inputEng.getSubstancesData("FRorig.s1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.s1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("TROPICAL").get("CORG.sT"));

		environment.get("TROPICAL").put("Kaw.T",
				inputEng.getSubstancesData("Kaw") * (Math
						.exp((inputEng.getSubstancesData("H0vap") / 8.314)
								* ((1. / 298.) - 1. / environment.get("TROPICAL").get("TEMP.T")))
						* Math.exp(-(inputEng.getSubstancesData("H0sol") / 8.314)
								* ((1. / 298.) - 1. / environment.get("TROPICAL").get("TEMP.T")))
						* (298 / environment.get("TROPICAL").get("TEMP.T"))));

		environment.get("TROPICAL").put("Ksw.T",
				environment.get("TROPICAL").get("FRACa.sT") * environment.get("TROPICAL").get("Kaw.T")
						* inputEng.getSubstancesData("FRorig.s1w") + environment.get("TROPICAL").get("FRACw.sT")
						+ environment.get("TROPICAL").get("FRACs.sT") * environment.get("TROPICAL").get("Kp.sT")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("TROPICAL").put("k.sTD.w2TD",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("FRACrun.sT")
						/ environment.get("TROPICAL").get("Ksw.T") + environment.get("TROPICAL").get("EROSION.sT"))
						* environment.get("TROPICAL").get("CORRrunoff.sT")
						/ environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("PRODsusp.wT", environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / (1000. * 3600. * 24. * 365.));

		environment.get("TROPICAL").put("NETsedrate.wT", 6.34195839675285E-14);

		environment.get("TROPICAL").put("SETTLvelocity.T", 2.5 / (24. * 3600));

		environment.get("TROPICAL").put("SUSP.w2T", 5. / 1000.);
		environment.get("TROPICAL").put("SUSP.w3T", 5. / 1000.);

		if (environment.get("TROPICAL").get("SETTLvelocity.T") * environment.get("TROPICAL").get("SUSP.w3T")
				/ (environment.get("TROPICAL").get("FRACs.sdT")
						* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")) > environment.get("TROPICAL")
								.get("NETsedrate.wT")) {
			environment.get("TROPICAL").put("GROSSSEDrate.w3T",
					environment.get("TROPICAL").get("SETTLvelocity.T") * environment.get("TROPICAL").get("SUSP.w3T")
							/ (environment.get("TROPICAL").get("FRACs.sdT")
									* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")));
		} else
			environment.get("TROPICAL").put("GROSSSEDrate.w3T", environment.get("TROPICAL").get("NETsedrate.wT"));

		environment.get("TROPICAL").put("RESUSP.sdT.w3T",
				environment.get("TROPICAL").get("GROSSSEDrate.w3T") - environment.get("TROPICAL").get("NETsedrate.wT"));

		environment.get("TROPICAL").put("COL.w2T", 1e-3);
		environment.get("TROPICAL").put("COL.w3T", 1e-3);

		environment.get("TROPICAL").put("Kp.col2T", 0.08 * inputEng.getSubstancesData("D"));
		environment.get("TROPICAL").put("Kp.col3T", 0.08 * inputEng.getSubstancesData("D"));

		environment.get("TROPICAL").put("CORG.susp2T", 0.1);
		environment.get("TROPICAL").put("CORG.susp3T", 0.1);

		environment.get("TROPICAL").put("Kp.susp2T",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("TROPICAL").get("CORG.susp2T"));

		environment.get("TROPICAL").put("Kp.susp3T",
				(inputEng.getSubstancesData("FRorig.w2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.w2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("TROPICAL").get("CORG.susp3T"));

		environment.get("TROPICAL").put("FRw.w2T", 1. / (1.
				+ environment.get("TROPICAL").get("Kp.susp2T") * environment.get("TROPICAL").get("SUSP.w2T") / 1000.
				+ environment.get("TROPICAL").get("Kp.col2T") * environment.get("TROPICAL").get("COL.w2T") / 1000.));

		environment.get("TROPICAL").put("FRw.w3T", 1. / (1.
				+ environment.get("TROPICAL").get("Kp.susp3T") * environment.get("TROPICAL").get("SUSP.w3T") / 1000.
				+ environment.get("TROPICAL").get("Kp.col3T") * environment.get("TROPICAL").get("COL.w3T") / 1000.));

		environment.get("TROPICAL").put("SED.w3T.sdT",
				environment.get("TROPICAL").get("SETTLvelocity.T") * (1. - environment.get("TROPICAL").get("FRw.w3T")));

		environment.get("TROPICAL").put("kwsd.sed.sdT", 0.00000002778);
		environment.get("TROPICAL").put("kwsd.water.wT", 0.000002778);

		environment.get("TROPICAL").put("CORG.sdT", 0.05);

		environment.get("TROPICAL").put("Kp.sdT",
				(inputEng.getSubstancesData("FRorig.sd2") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd2")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("TROPICAL").get("CORG.sdT"));

		environment.get("TROPICAL").put("Ksdw.T",
				environment.get("TROPICAL").get("FRACw.sdT")
						+ environment.get("TROPICAL").get("FRACs.sdT") * environment.get("TROPICAL").get("Kp.sdT")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("TROPICAL").put("DESORB.sdT.w3T", environment.get("TROPICAL").get("kwsd.water.wT")
				* environment.get("TROPICAL").get("kwsd.sed.sdT")
				/ (environment.get("TROPICAL").get("kwsd.water.wT") + environment.get("TROPICAL").get("kwsd.sed.sdT"))
				/ environment.get("TROPICAL").get("Ksdw.T"));

		environment.get("TROPICAL").put("ADSORB.w3T.sdT",
				(environment.get("TROPICAL").get("kwsd.water.wT") * environment.get("TROPICAL").get("kwsd.sed.sdT"))
						/ (environment.get("TROPICAL").get("kwsd.water.wT")
								+ environment.get("TROPICAL").get("kwsd.sed.sdT"))
						* environment.get("TROPICAL").get("FRw.w2T"));

		environment.get("TROPICAL").put("k.sdTD.w3TD",
				(environment.get("TROPICAL").get("RESUSP.sdT.w3T") + environment.get("TROPICAL").get("DESORB.sdT.w3T"))
						/ environment.get("TROPICAL").get("DEPTH.sdT"));

		environment.get("TROPICAL").put("k.w3TD.sdTD",
				(environment.get("TROPICAL").get("SED.w3T.sdT") + environment.get("TROPICAL").get("ADSORB.w3T.sdT"))
						/ environment.get("TROPICAL").get("DEPTH.w3T"));

		environment.get("TROPICAL").put("Cunningham.cwT", 1.);
		environment.get("TROPICAL").put("tdry.T", 3.3 * 24. * 3600.);
		environment.get("TROPICAL").put("twet.T", 3600. * 3.3 * 24. * (0.06 / (1. - 0.06)));

		environment.get("TROPICAL").put("ThermvelCP.aT", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ ((Math.PI * ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadCP.a"), 3))
								* inputEng.getEnvProperties("RhoCP.a")))),
				0.5));

		environment.get("TROPICAL").put("NumConcCP.aT", 3e+5);

		environment.get("TROPICAL").put("DiffCP.aT",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadCP.a")));

		environment.get("TROPICAL").put("DiffS.aT",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T")
						* inputEng.getNanoProperties("CunninghamS"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getSubstancesData("RadS")));

		environment.get("TROPICAL").put("Fuchs.aTS.aTCP", Math.pow((1 + (4
				* (environment.get("TROPICAL").get("DiffS.aT") + environment.get("TROPICAL").get("DiffCP.aT")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadCP.a")) * Math.pow(
						(environment.get("TROPICAL").get("ThermvelCP.aT")
								* environment.get("TROPICAL").get("ThermvelCP.aT")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("TROPICAL").put("k.aTS.aTP",
				environment.get("TROPICAL").get("Fuchs.aTS.aTCP")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadCP.a") + inputEng.getSubstancesData("RadS"))
								* (environment.get("TROPICAL").get("DiffCP.aT")
										+ environment.get("TROPICAL").get("DiffS.aT")))
						* environment.get("TROPICAL").get("NumConcCP.aT"));

		environment.get("TROPICAL").put("NumConcAcc.aT", 2.9e+9);

		environment.get("TROPICAL").put("ThermvelAcc.aT", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3)
								* inputEng.getEnvProperties("RhoAcc"))))),
				0.5));

		environment.get("TROPICAL").put("DiffAcc.aT",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadAcc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadAcc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadAcc")));

		environment.get("TROPICAL").put("Fuchs.aTS.aTAcc", Math.pow((1 + (4
				* (environment.get("TROPICAL").get("DiffS.aT") + environment.get("TROPICAL").get("DiffAcc.aT")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadAcc")) * Math.pow(
						(environment.get("TROPICAL").get("ThermvelAcc.aT")
								* environment.get("TROPICAL").get("ThermvelAcc.aT")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("TROPICAL").put("kcoag.aTS.aTAcc",
				environment.get("TROPICAL").get("Fuchs.aTS.aTAcc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadAcc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("TROPICAL").get("DiffAcc.aT")
										+ environment.get("TROPICAL").get("DiffS.aT")))
						* environment.get("TROPICAL").get("NumConcAcc.aT"));

		environment.get("TROPICAL").put("NumConcNuc.aT", 3.2e+9);

		environment.get("TROPICAL").put("ThermvelNuc.aT", Math.pow(
				((8. * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ ((Math.PI * (4. / 3. * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)
								* inputEng.getEnvProperties("RhoNuc"))))),
				0.5));

		environment.get("TROPICAL").put("DiffNuc.aT",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T")
						* (1 + (66e-9 / inputEng.getEnvProperties("RadNuc"))
								* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / inputEng.getEnvProperties("RadNuc"))))))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* inputEng.getEnvProperties("RadNuc")));

		environment.get("TROPICAL").put("Fuchs.aTS.aTNuc", Math.pow((1 + (4
				* (environment.get("TROPICAL").get("DiffS.aT") + environment.get("TROPICAL").get("DiffNuc.aT")))
				/ ((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNuc")) * Math.pow(
						(environment.get("TROPICAL").get("ThermvelNuc.aT")
								* environment.get("TROPICAL").get("ThermvelNuc.aT")
								+ inputEng.getNanoProperties("ThermvelS") * inputEng.getNanoProperties("ThermvelS")),
						0.5))),
				-1));

		environment.get("TROPICAL").put("kcoag.aTS.aTNuc",
				environment.get("TROPICAL").get("Fuchs.aTS.aTNuc")
						* (4. * Math.PI * (inputEng.getEnvProperties("RadNuc") + inputEng.getSubstancesData("RadS"))
								* (environment.get("TROPICAL").get("DiffNuc.aT")
										+ environment.get("TROPICAL").get("DiffS.aT")))
						* environment.get("TROPICAL").get("NumConcNuc.aT"));

		environment.get("TROPICAL").put("k.aTS.aTA", environment.get("TROPICAL").get("kcoag.aTS.aTNuc")
				+ environment.get("TROPICAL").get("kcoag.aTS.aTAcc"));

		environment.get("TROPICAL").put("fGravSSPM.wT", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelSPM.w")));

		environment.get("TROPICAL").put("fBrownSSPM.wT",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("TROPICAL").put("Shear.w2T", 10.);
		environment.get("TROPICAL").put("Shear.w3T", 10.);

		environment.get("TROPICAL").put("fInterceptSSPM.w3T", (4. / 3.) * environment.get("TROPICAL").get("Shear.w3T")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("TROPICAL").put("fTotalSSPM.w3T", environment.get("TROPICAL").get("fInterceptSSPM.w3T")
				+ +environment.get("TROPICAL").get("fBrownSSPM.wT") + environment.get("TROPICAL").get("fGravSSPM.wT"));

		environment.get("TROPICAL").put("NumConcSPM.w3T", environment.get("TROPICAL").get("SUSP.w3T")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("TROPICAL").put("k.w3TS.w3TP", environment.get("TROPICAL").get("fTotalSSPM.w3T")
				* environment.get("TROPICAL").get("NumConcSPM.w3T") * inputEng.getSubstancesData("AtefSP.w3"));

		environment.get("TROPICAL").put("fBrownSSPM.wT",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadSPM.w")));

		environment.get("TROPICAL").put("fInterceptSSPM.w2T", (4. / 3.) * environment.get("TROPICAL").get("Shear.w2T")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadSPM.w"), 3));

		environment.get("TROPICAL").put("fTotalSSPM.w2T", environment.get("TROPICAL").get("fInterceptSSPM.w2T")
				+ +environment.get("TROPICAL").get("fBrownSSPM.wT") + environment.get("TROPICAL").get("fGravSSPM.wT"));

		environment.get("TROPICAL").put("NumConcSPM.w2T", environment.get("TROPICAL").get("SUSP.w2T")
				/ (inputEng.getEnvProperties("SingleVolSPM.w") * inputEng.getEnvProperties("RhoSPM.w")));

		environment.get("TROPICAL").put("k.w2TS.w2TP", environment.get("TROPICAL").get("fTotalSSPM.w2T")
				* environment.get("TROPICAL").get("NumConcSPM.w2T") * inputEng.getSubstancesData("AtefSP.w2"));

		environment.get("TROPICAL")
				.put("fGravSNC.wT", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.w")));

		environment.get("TROPICAL").put("fBrownSNC.wT",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.w")));

		environment.get("TROPICAL").put("fInterceptSNC.w3T", (4. / 3.) * environment.get("TROPICAL").get("Shear.w3T")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("TROPICAL").put("fTotalSNC.w3T", environment.get("TROPICAL").get("fInterceptSNC.w3T")
				+ +environment.get("TROPICAL").get("fBrownSNC.wT") + environment.get("TROPICAL").get("fGravSNC.wT"));

		environment.get("TROPICAL").put("NumConcNC.w3T", 2. * environment.get("TROPICAL").get("COL.w3T")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("TROPICAL").put("k.w3TS.w3TA", environment.get("TROPICAL").get("fTotalSNC.w3T")
				* environment.get("TROPICAL").get("NumConcNC.w3T") * inputEng.getSubstancesData("AtefSA.w3"));

		environment.get("TROPICAL").put("fInterceptSNC.w2T", (4. / 3.) * environment.get("TROPICAL").get("Shear.w2T")
				* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.w"), 3));

		environment.get("TROPICAL").put("fTotalSNC.w2T", environment.get("TROPICAL").get("fInterceptSNC.w2T")
				+ +environment.get("TROPICAL").get("fBrownSNC.wT") + environment.get("TROPICAL").get("fGravSNC.wT"));

		environment.get("TROPICAL").put("NumConcNC.w2T", 2. * environment.get("TROPICAL").get("COL.w2T")
				/ (inputEng.getEnvProperties("SingleVolNC.w") * inputEng.getEnvProperties("RhoNC.w")));

		environment.get("TROPICAL").put("k.w2TS.w2TA", environment.get("TROPICAL").get("fTotalSNC.w2T")
				* environment.get("TROPICAL").get("NumConcNC.w2T") * inputEng.getSubstancesData("AtefSA.w2"));

		environment.get("TROPICAL").put("Udarcy.sdT", 9e-6);

		environment.get("TROPICAL").put("GravNumberSP.sdT", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("TROPICAL").get("Udarcy.sdT")));

		environment.get("TROPICAL").put("PecletNumberSP.sdT", environment.get("TROPICAL").get("Udarcy.sdT") * 2.
				* inputEng.getEnvProperties("RadFP.sd") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("TROPICAL").put("aspectratioSP.sdT",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.sd"));

		environment.get("TROPICAL").put("vdWaalsNumberSP.sdT", inputEng.getSubstancesData("AHamakerSP.w")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T")));

		environment.get("TROPICAL").put("fGravSP.sdT",
				0.22 * Math.pow(environment.get("TROPICAL").get("aspectratioSP.sdT"), -0.24)
						* Math.pow(environment.get("TROPICAL").get("GravNumberSP.sdT"), 1.11)
						* Math.pow(environment.get("TROPICAL").get("vdWaalsNumberSP.sdT"), 0.053));

		environment.get("TROPICAL").put("fInterceptSP.sdT",
				0.55 * Math.pow(environment.get("TROPICAL").get("aspectratioSP.sdT"), 1.55)
						* Math.pow(environment.get("TROPICAL").get("PecletNumberSP.sdT"), -0.125)
						* Math.pow(environment.get("TROPICAL").get("vdWaalsNumberSP.sdT"), 0.125));

		environment.get("TROPICAL").put("Por.sdT", environment.get("TROPICAL").get("FRACw.sdT"));

		environment.get("TROPICAL").put("GammPDF.sdT",
				Math.pow(1. - environment.get("TROPICAL").get("Por.sdT"), 1. / 3.));

		environment.get("TROPICAL").put("ASPDF.sdT",
				(2. * (1. - Math.pow(environment.get("TROPICAL").get("GammPDF.sdT"), 5)))
						/ (2. - 3. * environment.get("TROPICAL").get("GammPDF.sdT")
								+ 3 * Math.pow(environment.get("TROPICAL").get("GammPDF.sdT"), 5)
								- 2 * Math.pow(environment.get("TROPICAL").get("GammPDF.sdT"), 6)));

		environment.get("TROPICAL").put("fBrownSP.sdT",
				2.4 * Math.pow(environment.get("TROPICAL").get("PecletNumberSP.sdT"), 1. / 3.)
						* Math.pow(environment.get("TROPICAL").get("aspectratioSP.sdT"), -0.081)
						* Math.pow(environment.get("TROPICAL").get("PecletNumberSP.sdT"), -0.715)
						* Math.pow(environment.get("TROPICAL").get("vdWaalsNumberSP.sdT"), 0.053));

		environment.get("TROPICAL").put("fTotalSP.sdT", environment.get("TROPICAL").get("fBrownSP.sdT")
				+ environment.get("TROPICAL").get("fInterceptSP.sdT") + environment.get("TROPICAL").get("fGravSP.sdT"));

		environment.get("TROPICAL").put("Filter.sdT", (3. / 2.) * ((1. - environment.get("TROPICAL").get("Por.sdT"))
				/ (2 * inputEng.getEnvProperties("RadFP.sd") * environment.get("TROPICAL").get("Por.sdT"))));

		environment.get("TROPICAL").put("k.sdTS.sdTP",
				environment.get("TROPICAL").get("Filter.sdT") * environment.get("TROPICAL").get("Udarcy.sdT")
						* environment.get("TROPICAL").get("fTotalSP.sdT") * inputEng.getSubstancesData("AtefSP.sd2"));

		environment.get("TROPICAL").put("fGravSNC.sdT", Math.PI
				* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd")), 2)
				* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.sd")));

		environment.get("TROPICAL").put("fBrownSNC.sdT",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.sd"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.sd")));

		environment.get("TROPICAL").put("NumConcNC.sdT", environment.get("TROPICAL").get("NumConcNC.w2T"));

		environment.get("TROPICAL").put("k.sdTS.sdTA", inputEng.getSubstancesData("AtefSA.sd2")
				* environment.get("TROPICAL").get("NumConcNC.sdT")
				* (environment.get("TROPICAL").get("fBrownSNC.sdT") + environment.get("TROPICAL").get("fGravSNC.sdT")));

		environment.get("TROPICAL").put("Udarcy.sT", 9e-6);
		environment.get("TROPICAL").put("mConcNC.sT", 100. / 1000.);

		environment.get("TROPICAL").put("GravNumberS.sT", 2 * (Math.pow(inputEng.getSubstancesData("RadS"), 2)
				* (Math.abs(inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")))
				* 9.81)
				/ (9. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w")
						* environment.get("TROPICAL").get("Udarcy.sT")));

		environment.get("TROPICAL").put("vdWaalsNumberSFP.sT", inputEng.getSubstancesData("AHamakerSP.w")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T")));

		environment.get("TROPICAL").put("PecletNumberFP.sT", environment.get("TROPICAL").get("Udarcy.sT") * 2.
				* inputEng.getEnvProperties("RadFP.s") / inputEng.getNanoProperties("DiffS.wC"));

		environment.get("TROPICAL").put("aspectratioSFP.sT",
				inputEng.getSubstancesData("RadS") / inputEng.getEnvProperties("RadFP.s"));

		environment.get("TROPICAL").put("fGravSFP.sT",
				0.22 * Math.pow(environment.get("TROPICAL").get("aspectratioSFP.sT"), -0.24)
						* Math.pow(environment.get("TROPICAL").get("GravNumberS.sT"), 1.11)
						* Math.pow(environment.get("TROPICAL").get("vdWaalsNumberSFP.sT"), 0.053));

		environment.get("TROPICAL").put("fInterceptSFP.sT",
				0.55 * Math.pow(environment.get("TROPICAL").get("aspectratioSFP.sT"), 1.55)
						* Math.pow(environment.get("TROPICAL").get("PecletNumberFP.sT"), -0.125)
						* Math.pow(environment.get("TROPICAL").get("vdWaalsNumberSFP.sT"), 0.125));

		environment.get("TROPICAL").put("Por.sT", 1. - environment.get("TROPICAL").get("FRACs.sT"));

		environment.get("TROPICAL").put("GammPDF.sT",
				Math.pow(1. - environment.get("TROPICAL").get("Por.sT"), 1. / 3.));

		environment.get("TROPICAL").put("ASPDF.sT",
				(2. * (1. - Math.pow(environment.get("TROPICAL").get("GammPDF.sT"), 5)))
						/ (2. - 3. * environment.get("TROPICAL").get("GammPDF.sT")
								+ 3 * Math.pow(environment.get("TROPICAL").get("GammPDF.sT"), 5)
								- 2 * Math.pow(environment.get("TROPICAL").get("GammPDF.sT"), 6)));

		environment.get("TROPICAL").put("fBrownSFP.sT",
				2.4 * Math.pow(environment.get("TROPICAL").get("ASPDF.sT"), 1. / 3.)
						* Math.pow(environment.get("TROPICAL").get("aspectratioSFP.sT"), -0.081)
						* Math.pow(environment.get("TROPICAL").get("PecletNumberFP.sT"), -0.715)
						* Math.pow(environment.get("TROPICAL").get("vdWaalsNumberSFP.sT"), 0.053));

		environment.get("TROPICAL").put("fTotalSFP.sT", environment.get("TROPICAL").get("fBrownSFP.sT")
				+ environment.get("TROPICAL").get("fInterceptSFP.sT") + environment.get("TROPICAL").get("fGravSFP.sT"));

		environment.get("TROPICAL").put("Filter.sT", (3. / 2.) * ((1. - environment.get("TROPICAL").get("Por.sT"))
				/ (2 * inputEng.getEnvProperties("RadFP.s") * environment.get("TROPICAL").get("Por.sT"))));

		environment.get("TROPICAL").put("k.sTS.sTP",
				environment.get("TROPICAL").get("Filter.sT") * environment.get("TROPICAL").get("Udarcy.sT")
						* environment.get("TROPICAL").get("fTotalSFP.sT") * inputEng.getSubstancesData("AtefSP.s1"));

		environment.get("TROPICAL")
				.put("fGravSNC.sT", Math.PI
						* Math.pow((inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s")), 2)
						* Math.abs(inputEng.nanoProperties.get("SetVelS.w") - inputEng.getEnvProperties("SetVelNC.s")));

		environment.get("TROPICAL").put("fBrownSNC.sT",
				(2 * inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T"))
						/ (3. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						* Math.pow(inputEng.getSubstancesData("RadS") + inputEng.getEnvProperties("RadNC.s"), 2)
						/ (inputEng.getSubstancesData("RadS") * inputEng.getEnvProperties("RadNC.s")));

		environment.get("TROPICAL").put("NumConcNC.sT", environment.get("TROPICAL").get("mConcNC.sT")
				/ (inputEng.getEnvProperties("SingleVolNC.s") * inputEng.getEnvProperties("RhoNC.s")));

		environment.get("TROPICAL").put("k.sTS.sTA", inputEng.getSubstancesData("AtefSA.s1")
				* environment.get("TROPICAL").get("NumConcNC.sT")
				* (environment.get("TROPICAL").get("fBrownSNC.sT") + environment.get("TROPICAL").get("fGravSNC.sT")));

		environment.get("TROPICAL").put("FRACtwet.T", environment.get("TROPICAL").get("twet.T")
				/ (environment.get("TROPICAL").get("twet.T") + environment.get("TROPICAL").get("tdry.T")));

		environment.get("TROPICAL").put("RAINrate.wet.T",
				environment.get("TROPICAL").get("RAINrate.T") / environment.get("TROPICAL").get("FRACtwet.T"));

		environment.get("TROPICAL").put("Rad.cwT",
				((0.7 * (Math.pow(60. * 60. * 1000. * environment.get("TROPICAL").get("RAINrate.wet.T"), 0.25)) / 2.)
						/ 1000.));

		environment.get("TROPICAL").put("TerminalVel.cwT",
				(Math.pow(2 * environment.get("TROPICAL").get("Rad.cwT"), 2)
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHO.w")
								- inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * environment.get("TROPICAL").get("Cunningham.cwT"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("TROPICAL").put("ReyNumber.cwT",
				((2. * environment.get("TROPICAL").get("Rad.cwT")) * environment.get("TROPICAL").get("TerminalVel.cwT")
						* inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						/ (2. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("TROPICAL").put("CritStokesNumb.cwT",
				(1.2 + (1. / 12.) * Math.log(1. + environment.get("TROPICAL").get("ReyNumber.cwT")))
						/ (1. + Math.log(environment.get("TROPICAL").get("ReyNumber.cwT"))));

		environment.get("TROPICAL")
				.put("SingleMassA.aT",
						((environment.get("TROPICAL").get("NumConcNuc.aT")
								* (inputEng.getEnvProperties("RhoNuc")
										* ((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3))
										+ inputEng.getSubstancesData("RhoS")
												* inputEng.getNanoProperties("SingleVolS")))
								+ (environment.get("TROPICAL").get("NumConcAcc.aT")
										* (inputEng.getEnvProperties("RhoAcc")
												* ((4. / 3.) * Math.PI
														* Math.pow(inputEng.getEnvProperties("RadAcc"), 3))
												+ inputEng.getSubstancesData("RhoS")
														* inputEng.getNanoProperties("SingleVolS"))))
								/ (environment.get("TROPICAL").get("NumConcNuc.aT")
										+ environment.get("TROPICAL").get("NumConcAcc.aT")));

		environment.get("TROPICAL").put("SingleVolA.aT",
				((environment.get("TROPICAL").get("NumConcNuc.aT") * (inputEng.getNanoProperties("SingleVolS")
						+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadNuc"), 3)))))
						+ (environment.get("TROPICAL").get("NumConcAcc.aT") * (inputEng.getNanoProperties("SingleVolS")
								+ (((4. / 3.) * Math.PI * Math.pow(inputEng.getEnvProperties("RadAcc"), 3))))))
						/ (environment.get("TROPICAL").get("NumConcNuc.aT")
								+ environment.get("TROPICAL").get("NumConcAcc.aT")));

		environment.get("TROPICAL").put("RhoA.aT",
				environment.get("TROPICAL").get("SingleMassA.aT") / environment.get("TROPICAL").get("SingleVolA.aT"));

		environment.get("TROPICAL").put("RadA.aT",
				Math.pow((environment.get("TROPICAL").get("SingleVolA.aT")) / ((4. / 3.) * Math.PI), 1. / 3.));

		environment.get("TROPICAL").put("RelaxS.aT",
				((inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * inputEng.getSubstancesData("RadS")), 2)
						* inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("TROPICAL").put("SettvelS.aT",
				(Math.pow(2. * inputEng.getSubstancesData("RadS"), 2)
						* (inputEng.getSubstancesData("RhoS") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* 9.81 * inputEng.getNanoProperties("CunninghamS"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("TROPICAL").put("StokesNumberS.aT", (2. * environment.get("TROPICAL").get("RelaxS.aT")
				* (environment.get("TROPICAL").get("TerminalVel.cwT") - environment.get("TROPICAL").get("SettvelS.aT")))
				/ (2. * environment.get("TROPICAL").get("Rad.cwT")));

		environment.get("TROPICAL").put("SchmidtNumberS.aT", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("TROPICAL").get("DiffS.aT")));

		environment.get("TROPICAL").put("fBrown.aTS.cwTS",
				(4. / (environment.get("TROPICAL").get("ReyNumber.cwT")
						* environment.get("TROPICAL").get("SchmidtNumberS.aT")))
						* (1. + 0.4 * (Math.pow(environment.get("TROPICAL").get("ReyNumber.cwT"), 0.5)
								* Math.pow(environment.get("TROPICAL").get("SchmidtNumberS.aT"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("TROPICAL").get("ReyNumber.cwT"), 0.5)
										* Math.pow(environment.get("TROPICAL").get("SchmidtNumberS.aT"), 0.5))));

		environment.get("TROPICAL").put("fIntercept.aTS.cwTS", 4.
				* (inputEng.getSubstancesData("RadS") / environment.get("TROPICAL").get("Rad.cwT"))
				* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
						/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
						+ (1. + 2. * Math.pow(environment.get("TROPICAL").get("ReyNumber.cwT"), 0.5)
								* (inputEng.getSubstancesData("RadS") / environment.get("TROPICAL").get("Rad.cwT")))));

		if (environment.get("TROPICAL").get("StokesNumberS.aT") > environment.get("TROPICAL").get("CritStokesNumb.cwT"))
			environment.get("TROPICAL").put("fGrav.aTS.cwTS",
					Math.pow(((environment.get("TROPICAL").get("StokesNumberS.aT")
							- environment.get("TROPICAL").get("CritStokesNumb.cwT"))
							/ environment.get("TROPICAL").get("StokesNumberS.aT")
							- environment.get("TROPICAL").get("CritStokesNumb.cwT") + 2. / 3.), 3. / 2.));
		else
			environment.get("TROPICAL").put("fGrav.aTS.cwTS", 0.0);

		environment.get("TROPICAL").put("fTotal.aTS.cwTS",
				environment.get("TROPICAL").get("fBrown.aTS.cwTS")
						+ environment.get("TROPICAL").get("fIntercept.aTS.cwTS")
						+ environment.get("TROPICAL").get("fGrav.aTS.cwTS"));

		environment.get("TROPICAL").put("k.aTS.cwTS",
				((3. / 2.)
						* (environment.get("TROPICAL").get("fTotal.aTS.cwTS")
								* environment.get("TROPICAL").get("RAINrate.wet.T"))
						/ (2. * environment.get("TROPICAL").get("Rad.cwT"))));

		environment.get("TROPICAL").put("CunninghamA.aT", 1. + (66.e-9 / environment.get("TROPICAL").get("RadA.aT"))
				* (1.142 + 0.558 * Math.exp(-0.999 / (66e-9 / environment.get("TROPICAL").get("RadA.aT")))));

		environment.get("TROPICAL").put("DiffA.aT",
				(inputEng.getLandscapeSettings("ALL-SCALE", "kboltz") * environment.get("TROPICAL").get("TEMP.T")
						* environment.get("TROPICAL").get("CunninghamA.aT"))
						/ (6. * Math.PI * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								* environment.get("TROPICAL").get("RadA.aT")));

		environment.get("TROPICAL").put("RelaxA.aT",
				((environment.get("TROPICAL").get("RhoA.aT") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
						* Math.pow((2. * environment.get("TROPICAL").get("RadA.aT")), 2)
						* environment.get("TROPICAL").get("CunninghamA.aT"))
						/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("TROPICAL").put("SettvelA.aT", (Math.pow(2. * environment.get("TROPICAL").get("RadA.aT"), 2)
				* (environment.get("TROPICAL").get("RhoA.aT") - inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a"))
				* 9.81 * environment.get("TROPICAL").get("CunninghamA.aT"))
				/ (18. * inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")));

		environment.get("TROPICAL").put("StokesNumberA.aT", (2. * environment.get("TROPICAL").get("RelaxA.aT")
				* (environment.get("TROPICAL").get("TerminalVel.cwT") - environment.get("TROPICAL").get("SettvelA.aT")))
				/ (2. * environment.get("TROPICAL").get("Rad.cwT")));

		environment.get("TROPICAL").put("SchmidtNumberA.aT", inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
				/ (inputEng.getLandscapeSettings("ALL-SCALE", "Rho.a") * environment.get("TROPICAL").get("DiffA.aT")));

		environment.get("TROPICAL").put("fBrown.aTA.cwTA",
				(4. / (environment.get("TROPICAL").get("ReyNumber.cwT")
						* environment.get("TROPICAL").get("SchmidtNumberA.aT")))
						* (1. + 0.4 * (Math.pow(environment.get("TROPICAL").get("ReyNumber.cwT"), 0.5)
								* Math.pow(environment.get("TROPICAL").get("SchmidtNumberA.aT"), 1. / 3.))
								+ 0.16 * (Math.pow(environment.get("TROPICAL").get("ReyNumber.cwT"), 0.5)
										* Math.pow(environment.get("TROPICAL").get("SchmidtNumberA.aT"), 0.5))));

		environment.get("TROPICAL").put("fIntercept.aTA.cwTA",
				4. * (environment.get("TROPICAL").get("RadA.aT") / environment.get("TROPICAL").get("Rad.cwT"))
						* ((inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.a")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "DYNVISC.w"))
								+ (1. + 2. * Math.pow(environment.get("TROPICAL").get("ReyNumber.cwT"), 0.5)
										* (environment.get("TROPICAL").get("RadA.aT")
												/ environment.get("TROPICAL").get("Rad.cwT")))));

		if (environment.get("TROPICAL").get("StokesNumberA.aT") > environment.get("TROPICAL").get("CritStokesNumb.cwT"))
			environment.get("TROPICAL").put("fGrav.aMA.cwTA",
					Math.pow(((environment.get("TROPICAL").get("StokesNumberA.aT")
							- environment.get("TROPICAL").get("CritStokesNumb.cwT"))
							/ environment.get("TROPICAL").get("StokesNumberA.aT")
							- environment.get("TROPICAL").get("CritStokesNumb.cwT") + 2. / 3.), 3. / 2.));
		else
			environment.get("TROPICAL").put("fGrav.aTA.cwTA", 0.0);

		environment.get("TROPICAL").put("fTotal.aTA.cwTA",
				environment.get("TROPICAL").get("fBrown.aTA.cwTA")
						+ environment.get("TROPICAL").get("fIntercept.aTA.cwTA")
						+ environment.get("TROPICAL").get("fGrav.aTA.cwTA"));

		environment.get("TROPICAL").put("k.aTA.cwTA",
				((3. / 2.)
						* (environment.get("TROPICAL").get("fTotal.aTA.cwTA")
								* environment.get("TROPICAL").get("RAINrate.wet.T"))
						/ (2. * environment.get("TROPICAL").get("Rad.cwT"))));

		environment.get("TROPICAL").put("AEROresist.sT", 74.);
		environment.get("TROPICAL").put("FRICvelocity.sT", 0.19);
		environment.get("TROPICAL").put("veghair.sT", 1e-5);
		environment.get("TROPICAL").put("largevegradius.sT", 0.0005);
		environment.get("TROPICAL").put("AREAFRACveg.sT", 0.01);

		environment.get("TROPICAL").put("fGrav.aTS.sTS", Math.pow((environment.get("TROPICAL").get("StokesNumberS.aT")
				/ (environment.get("TROPICAL").get("StokesNumberS.aT") + 0.8)), 2));

		environment.get("TROPICAL").put("fBrown.aTS.sTS",
				Math.pow(environment.get("TROPICAL").get("SchmidtNumberS.aT"), -2. / 3.));

		environment.get("TROPICAL")
				.put("fIntercept.aTS.sTS",
						0.3 * (environment.get("TROPICAL").get("AREAFRACveg.sT") * (inputEng.getSubstancesData("RadS")
								/ (inputEng.getSubstancesData("RadS") + environment.get("TROPICAL").get("veghair.sT")))
								+ (1 - environment.get("TROPICAL").get("AREAFRACveg.sT"))
										* (inputEng.getSubstancesData("RadS") / (inputEng.getSubstancesData("RadS")
												+ environment.get("TROPICAL").get("largevegradius.sT")))));

		environment.get("TROPICAL").put("SURFresist.sTS",
				1 / (environment.get("TROPICAL").get("FRICvelocity.sT")
						* (environment.get("TROPICAL").get("fBrown.aTS.sTS")
								+ environment.get("TROPICAL").get("fIntercept.aTS.sTS")
								+ environment.get("TROPICAL").get("fGrav.aTS.sTS"))));

		environment.get("TROPICAL").put("DEPvelocity.aTS.sTS", 1.
				/ (environment.get("TROPICAL").get("AEROresist.sT") + environment.get("TROPICAL").get("SURFresist.sTS"))
				+ environment.get("TROPICAL").get("SettvelS.aT"));

		environment.get("TROPICAL").put("k.aTS.sTS",
				(environment.get("TROPICAL").get("DEPvelocity.aTS.sTS") * environment.get("TROPICAL").get("AREAFRAC.sT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ (environment.get("TROPICAL").get("VOLUME.aT")));

		environment.get("TROPICAL").put("COLLECTeff.T", 200000.);

		environment.get("TROPICAL").put("k.aTP.cwAP",
				((environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T"))
						/ environment.get("TROPICAL").get("twet.T") * environment.get("TROPICAL").get("COLLECTeff.T")
						* environment.get("TROPICAL").get("RAINrate.T"))
						/ environment.get("TROPICAL").get("HEIGHT.aT"));

		environment.get("TROPICAL").put("k.aTP.cwTP",
				((environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T"))
						/ environment.get("TROPICAL").get("twet.T") * environment.get("TROPICAL").get("COLLECTeff.T")
						* environment.get("TROPICAL").get("RAINrate.T"))
						/ environment.get("TROPICAL").get("HEIGHT.aT"));

		environment.get("TROPICAL").put("fBrown.aTA.sTA",
				Math.pow(environment.get("TROPICAL").get("SchmidtNumberA.aT"), -2. / 3.));

		environment.get("TROPICAL").put("fIntercept.aTA.sTA", 0.3 * (environment.get("TROPICAL").get("AREAFRACveg.sT")
				* (environment.get("TROPICAL").get("RadA.aT")
						/ (environment.get("TROPICAL").get("RadA.aT") + environment.get("TROPICAL").get("veghair.sT")))
				+ (1 - environment.get("TROPICAL").get("AREAFRACveg.sT"))
						* (environment.get("TROPICAL").get("RadA.aT") / (environment.get("TROPICAL").get("RadA.aT")
								+ environment.get("TROPICAL").get("largevegradius.sT")))));

		environment.get("TROPICAL").put("fGrav.aTA.sTA", Math.pow((environment.get("TROPICAL").get("StokesNumberA.aT")
				/ (environment.get("TROPICAL").get("StokesNumberA.aT") + 0.8)), 2));

		environment.get("TROPICAL").put("SURFresist.sTA",
				1 / (environment.get("TROPICAL").get("FRICvelocity.sT")
						* (environment.get("TROPICAL").get("fBrown.aTA.sTA")
								+ environment.get("TROPICAL").get("fIntercept.aTA.sTA")
								+ environment.get("TROPICAL").get("fGrav.aTA.sTA"))));

		environment.get("TROPICAL").put("DEPvelocity.aTA.sTA", 1.
				/ (environment.get("TROPICAL").get("AEROresist.sT") + environment.get("TROPICAL").get("SURFresist.sTA"))
				+ environment.get("TROPICAL").get("SettvelA.aT"));

		environment.get("TROPICAL").put("k.aTA.sTA",
				(environment.get("TROPICAL").get("DEPvelocity.aTA.sTA") * environment.get("TROPICAL").get("AREAFRAC.sT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ (environment.get("TROPICAL").get("VOLUME.aT")));

		environment.get("TROPICAL").put("AEROSOLdeprate.T", 0.001);

		environment.get("TROPICAL").put("k.aTP.sTP",
				environment.get("TROPICAL").get("AEROSOLdeprate.T") * environment.get("TROPICAL").get("AREAFRAC.sT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T")
						/ environment.get("TROPICAL").get("VOLUME.aT"));

		environment.get("TROPICAL").put("AEROresist.w2T", 135.);
		environment.get("TROPICAL").put("FRICvelocity.wT", 0.19);

		environment.get("TROPICAL").put("fGrav.aTS.w2TS",
				Math.pow(10, -3. / environment.get("TROPICAL").get("StokesNumberS.aT")));

		environment.get("TROPICAL").put("fBrown.aTS.w2TS",
				Math.pow(environment.get("TROPICAL").get("SchmidtNumberS.aT"), -1. / 2.));

		environment.get("TROPICAL").put("fIntercept.aTA.sTA", 0.3 * (environment.get("TROPICAL").get("AREAFRACveg.sT")
				* (environment.get("TROPICAL").get("RadA.aT")
						/ (environment.get("TROPICAL").get("RadA.aT") + environment.get("TROPICAL").get("veghair.sT")))
				+ (1 - environment.get("TROPICAL").get("AREAFRACveg.sT"))
						* (environment.get("TROPICAL").get("RadA.aT") / (environment.get("TROPICAL").get("RadA.aT")
								+ environment.get("TROPICAL").get("largevegradius.sT")))));

		environment.get("TROPICAL").put("SURFresist.w2TS",
				1. / (environment.get("TROPICAL").get("FRICvelocity.wT")
						* (environment.get("TROPICAL").get("fBrown.aTS.w2TS")
								+ environment.get("TROPICAL").get("fGrav.aTS.w2TS"))));

		environment.get("TROPICAL").put("DEPvelocity.aTS.w2TS",
				1. / (environment.get("TROPICAL").get("AEROresist.w2T")
						+ environment.get("TROPICAL").get("SURFresist.w2TS"))
						+ environment.get("TROPICAL").get("SettvelS.aT"));

		environment.get("TROPICAL").put("k.aTS.w2TS", (environment.get("TROPICAL").get("DEPvelocity.aTS.w2TS")
				* environment.get("TROPICAL").get("AREAFRAC.wT") * environment.get("TROPICAL").get("SYSTEMAREA.T"))
				/ (environment.get("TROPICAL").get("VOLUME.aT")));

		environment.get("TROPICAL").put("fGrav.aTA.w2TA",
				Math.pow(10, -3. / environment.get("TROPICAL").get("StokesNumberA.aT")));

		environment.get("TROPICAL").put("fBrown.aTA.w2TA",
				Math.pow(environment.get("TROPICAL").get("SchmidtNumberA.aT"), -1. / 2.));

		environment.get("TROPICAL").put("SURFresist.wTA",
				1. / (environment.get("TROPICAL").get("FRICvelocity.wT")
						* (environment.get("TROPICAL").get("fBrown.aTA.w2TA")
								+ environment.get("TROPICAL").get("fGrav.aTA.w2TA"))));

		environment.get("TROPICAL").put("DEPvelocity.aTA.w2TA",
				1. / (environment.get("TROPICAL").get("AEROresist.w2T")
						+ environment.get("TROPICAL").get("SURFresist.wTA"))
						+ environment.get("TROPICAL").get("SettvelA.aT"));

		environment.get("TROPICAL").put("SURFresist.wTA",
				1. / (environment.get("TROPICAL").get("FRICvelocity.wT")
						* (environment.get("TROPICAL").get("fBrown.aTA.w2TA")
								+ environment.get("TROPICAL").get("fGrav.aTA.w2TA"))));

		environment.get("TROPICAL").put("DEPvelocity.aTA.w2AA",
				1. / (environment.get("TROPICAL").get("AEROresist.w2T")
						+ environment.get("TROPICAL").get("SURFresist.wTA"))
						+ environment.get("TROPICAL").get("SettvelA.aT"));

		environment.get("TROPICAL").put("k.aTA.w2TA", (environment.get("TROPICAL").get("DEPvelocity.aTA.w2TA")
				* environment.get("TROPICAL").get("AREAFRAC.wT") * environment.get("TROPICAL").get("SYSTEMAREA.T"))
				/ (environment.get("TROPICAL").get("VOLUME.aT")));

		environment.get("TROPICAL").put("k.aTA.w2TA", (environment.get("TROPICAL").get("DEPvelocity.aTA.w2TA")
				* environment.get("TROPICAL").get("AREAFRAC.wT") * environment.get("TROPICAL").get("SYSTEMAREA.T"))
				/ (environment.get("TROPICAL").get("VOLUME.aT")));

		environment.get("TROPICAL").put("k.aTP.w2TP",
				(environment.get("TROPICAL").get("AEROSOLdeprate.T") * environment.get("TROPICAL").get("AREAFRAC.wT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.aT"));

		environment.get("TROPICAL").put("k.cwTS.sTS",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.sT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		environment.get("TROPICAL").put("k.cwTA.sTA",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.sT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		environment.get("TROPICAL").put("k.cwTP.sTP",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.sT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		environment.get("TROPICAL").put("k.cwTS.w2TS",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.wT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		environment.get("TROPICAL").put("k.cwTA.w2TA",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.wT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		environment.get("TROPICAL").put("k.cwTP.w2TP",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.wT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		environment.get("TROPICAL").put("k.cwTP.w2TP",
				(environment.get("TROPICAL").get("RAINrate.T") * environment.get("TROPICAL").get("AREAFRAC.wT")
						* environment.get("TROPICAL").get("SYSTEMAREA.T"))
						/ environment.get("TROPICAL").get("VOLUME.cwT"));

		if (inputEng.nanoProperties.get("SetVelS.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / environment.get("TROPICAL").get("VOLUME.w2T") > 0)
			environment.get("TROPICAL")
					.put("k.w2TS.w3TS",
							environment.get("TROPICAL").get("k.w2T.w3T") + (inputEng.nanoProperties.get("SetVelS.w")
									* environment.get("TROPICAL").get("AREAFRAC.wT")
									* environment.get("TROPICAL").get("SYSTEMAREA.T"))
									/ environment.get("TROPICAL").get("VOLUME.w2T"));
		else
			environment.get("TROPICAL").put("k.w2TS.w3TS", environment.get("TROPICAL").get("k.w2T.w3T"));

		if (inputEng.nanoProperties.get("SetVelA.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / environment.get("TROPICAL").get("VOLUME.w2T") > 0)
			environment.get("TROPICAL")
					.put("k.w2TA.w3TA",
							environment.get("TROPICAL").get("k.w2T.w3T") + (inputEng.nanoProperties.get("SetVelA.w")
									* environment.get("TROPICAL").get("AREAFRAC.wT")
									* environment.get("TROPICAL").get("SYSTEMAREA.T"))
									/ environment.get("TROPICAL").get("VOLUME.w2T"));
		else
			environment.get("TROPICAL").put("k.w2TA.w3TA", environment.get("TROPICAL").get("k.w2T.w3T"));

		if (inputEng.nanoProperties.get("SetVelP.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / environment.get("TROPICAL").get("VOLUME.w2T") > 0)
			environment.get("TROPICAL")
					.put("k.w2TP.w3TP",
							environment.get("TROPICAL").get("k.w2T.w3T") + (inputEng.nanoProperties.get("SetVelP.w")
									* environment.get("TROPICAL").get("AREAFRAC.wT")
									* environment.get("TROPICAL").get("SYSTEMAREA.T"))
									/ environment.get("TROPICAL").get("VOLUME.w2T"));
		else
			environment.get("TROPICAL").put("k.w2TP.w3TP", environment.get("TROPICAL").get("k.w2T.w3T"));

		if (inputEng.nanoProperties.get("SetVelS.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / environment.get("TROPICAL").get("VOLUME.w3T") > 0)
			environment.get("TROPICAL").put("k.w3TS.sdTS",
					(inputEng.nanoProperties.get("SetVelS.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
							* environment.get("TROPICAL").get("SYSTEMAREA.T"))
							/ environment.get("TROPICAL").get("VOLUME.w3T"));
		else
			environment.get("TROPICAL").put("k.w3TS.sdTS", 0.);

		if (inputEng.nanoProperties.get("SetVelA.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / environment.get("TROPICAL").get("VOLUME.w3T") > 0)
			environment.get("TROPICAL").put("k.w3TA.sdTA",
					(inputEng.nanoProperties.get("SetVelA.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
							* environment.get("TROPICAL").get("SYSTEMAREA.T"))
							/ environment.get("TROPICAL").get("VOLUME.w3T"));
		else
			environment.get("TROPICAL").put("k.w3TA.sdTA", 0.);

		if (inputEng.nanoProperties.get("SetVelP.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
				* environment.get("TROPICAL").get("SYSTEMAREA.T") / environment.get("TROPICAL").get("VOLUME.w3T") > 0)
			environment.get("TROPICAL").put("k.w3TP.sdTP",
					(inputEng.nanoProperties.get("SetVelP.w") * environment.get("TROPICAL").get("AREAFRAC.wT")
							* environment.get("TROPICAL").get("SYSTEMAREA.T"))
							/ environment.get("TROPICAL").get("VOLUME.w3T"));
		else
			environment.get("TROPICAL").put("k.w3TP.sdTP", 0.);

		environment.get("TROPICAL").put("k.sdTS.w3TS",
				environment.get("TROPICAL").get("RESUSP.sdT.w3T") / environment.get("TROPICAL").get("DEPTH.sdT"));

		environment.get("TROPICAL").put("k.sdTA.w3TA",
				environment.get("TROPICAL").get("RESUSP.sdT.w3T") / environment.get("TROPICAL").get("DEPTH.sdT"));

		environment.get("TROPICAL").put("k.sdTP.w3TP",
				environment.get("TROPICAL").get("RESUSP.sdT.w3T") / environment.get("TROPICAL").get("DEPTH.sdT"));

		environment.get("TROPICAL").put("k.sTS.w2TS",
				(environment.get("TROPICAL").get("FRACrun.sT") * environment.get("TROPICAL").get("RAINrate.T")
						* environment.get("TROPICAL").get("CORRrunoff.sT")
						+ environment.get("TROPICAL").get("EROSION.sT")) / environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("k.sTA.w2TA",
				(environment.get("TROPICAL").get("FRACrun.sT") * environment.get("TROPICAL").get("RAINrate.T")
						* environment.get("TROPICAL").get("CORRrunoff.sT")
						+ environment.get("TROPICAL").get("EROSION.sT")) / environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("k.sTP.w2TP",
				environment.get("TROPICAL").get("EROSION.sT") / environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("kesc.aT", Math.log(2.) / (60. * 365. * 24. * 3600.));

		environment.get("TROPICAL").put("k.aTS",
				environment.get("TROPICAL").get("kesc.aT") + inputEng.getSubstancesData("kdegS.a"));

		environment.get("TROPICAL").put("k.aTP",
				environment.get("TROPICAL").get("kesc.aT") + inputEng.getSubstancesData("kdegP.a"));

		environment.get("TROPICAL").put("k.w2TS", inputEng.getSubstancesData("kdegS.w2"));

		environment.get("TROPICAL").put("k.w2TP", inputEng.getSubstancesData("kdegP.w2"));

		environment.get("TROPICAL").put("k.w3TS", inputEng.getSubstancesData("kdegS.w3"));

		environment.get("TROPICAL").put("k.w3TA", inputEng.getSubstancesData("kdegA.w3"));

		environment.get("TROPICAL").put("k.w3TP", inputEng.getSubstancesData("kdegP.w3"));

		environment.get("TROPICAL").put("kbur.sdT",
				environment.get("TROPICAL").get("NETsedrate.wT") / environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("k.sdTS",
				environment.get("TROPICAL").get("kbur.sdT") + inputEng.getSubstancesData("kdegS.sd2"));

		environment.get("TROPICAL").put("k.sdTA",
				environment.get("TROPICAL").get("kbur.sdT") + inputEng.getSubstancesData("kdegA.sd2"));

		environment.get("TROPICAL").put("k.sdTP",
				environment.get("TROPICAL").get("kbur.sdT") + inputEng.getSubstancesData("kdegP.sd2"));

		environment.get("TROPICAL").put("FRACinf.sT", 0.25);

		environment.get("TROPICAL").put("CORRleach.sT",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("TROPICAL").get("DEPTH.sT")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("TROPICAL").get("DEPTH.sT")))));

		environment.get("TROPICAL").put("kleach.sT",
				((environment.get("TROPICAL").get("FRACinf.sT") * environment.get("TROPICAL").get("RAINrate.T")
						* environment.get("TROPICAL").get("CORRleach.sT"))
						/ environment.get("TROPICAL").get("DEPTH.sT")));

		environment.get("TROPICAL").put("k.sTS",
				environment.get("TROPICAL").get("kleach.sT") + inputEng.getSubstancesData("kdegS.s1"));

		environment.get("TROPICAL").put("k.sTA",
				environment.get("TROPICAL").get("kleach.sT") + inputEng.getSubstancesData("kdegA.s1"));

		environment.get("TROPICAL").put("k.sTP", inputEng.getSubstancesData("kdegP.s1"));

		environment.get("TROPICAL").put("Kaers.aT",
				0.54 * (inputEng.getSubstancesData("Kow") / inputEng.getSubstancesData("Kaw"))
						* inputEng.getLandscapeSettings("ALL-SCALE", "CORGaers")
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHOaers") / 1000.));

		environment.get("TROPICAL").put("Kaerw.aT",
				1. / (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("TROPICAL")
				.put("FRgas.aT", 1
						- environment.get("TROPICAL").get("FRACaerw.aT") * environment.get("TROPICAL").get("Kaerw.aT")
								/ (1. + environment.get("TROPICAL").get("FRACaerw.aT")
										* environment.get("TROPICAL").get("Kaerw.aT")
										+ environment.get("TROPICAL").get("FRACaers.aT")
												* environment.get("TROPICAL").get("Kaers.aT"))
						- environment.get("TROPICAL").get("FRACaers.aT") * environment.get("TROPICAL").get("Kaers.aT")
								/ (1. + environment.get("TROPICAL").get("FRACaerw.aT")
										* environment.get("TROPICAL").get("Kaerw.aT")
										+ environment.get("TROPICAL").get("FRACaers.aT")
												* environment.get("TROPICAL").get("Kaers.aT")));

		environment.get("TROPICAL").put("Tempfactor.aT", Math.exp((inputEng.getSubstancesData("Ea.OHrad") / 8.314)
				* (environment.get("TROPICAL").get("TEMP.T") - 298.) / Math.pow(298, 2)));

		environment.get("TROPICAL").put("C.OHrad.aT", 500000.);

		environment.get("TROPICAL").put("KDEG.aT",
				environment.get("TROPICAL").get("FRgas.aT") * inputEng.getSubstancesData("kdegG.air")
						* (environment.get("TROPICAL").get("C.OHrad.aT") / inputEng.getSubstancesData("C.OHrad"))
						* environment.get("TROPICAL").get("Tempfactor.aT"));

		// This is k.aTG in the excel
		environment.get("TROPICAL").put("k.aT",
				environment.get("TROPICAL").get("kesc.aT") + environment.get("TROPICAL").get("KDEG.aT"));
	}

	public void mixedAll() {
		environment.get("ARCTIC").put("Tempfactor.aA", Math.exp((inputEng.getSubstancesData("Ea.OHrad") / 8.314)
				* (environment.get("ARCTIC").get("TEMP.A") - 298.) / Math.pow(298, 2)));

		environment.get("ARCTIC").put("C.OHrad.aA", 500000.);

		environment.get("MODERATE").put("Kaw.M",
				inputEng.getSubstancesData("Kaw") * (Math
						.exp((inputEng.getSubstancesData("H0vap") / 8.314)
								* ((1. / 298.) - 1. / environment.get("MODERATE").get("TEMP.M")))
						* Math.exp(-(inputEng.getSubstancesData("H0sol") / 8.314)
								* ((1. / 298.) - 1. / environment.get("MODERATE").get("TEMP.M")))
						* (298 / environment.get("MODERATE").get("TEMP.M"))));

		environment.get("MODERATE").put("TEMP.M", 12. + 273.);

		environment.get("MODERATE").put("Kaers.aM",
				0.54 * (inputEng.getSubstancesData("Kow") / inputEng.getSubstancesData("Kaw"))
						* inputEng.getLandscapeSettings("ALL-SCALE", "CORGaers")
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHOaers") / 1000.));

		environment.get("MODERATE").put("Kaerw.aM",
				1. / (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("MODERATE")
				.put("FRgas.aM", 1
						- environment.get("MODERATE").get("FRACaerw.aM") * environment.get("MODERATE").get("Kaerw.aM")
								/ (1. + environment.get("MODERATE").get("FRACaerw.aM")
										* environment.get("MODERATE").get("Kaerw.aM")
										+ environment.get("MODERATE").get("FRACaers.aM")
												* environment.get("MODERATE").get("Kaers.aM"))
						- environment.get("MODERATE").get("FRACaers.aM") * environment.get("MODERATE").get("Kaers.aM")
								/ (1. + environment.get("MODERATE").get("FRACaerw.aM")
										* environment.get("MODERATE").get("Kaerw.aM")
										+ environment.get("MODERATE").get("FRACaers.aM")
												* environment.get("MODERATE").get("Kaers.aM")));

		environment.get("MODERATE").put("Tempfactor.aM", Math.exp((inputEng.getSubstancesData("Ea.OHrad") / 8.314)
				* (environment.get("MODERATE").get("TEMP.M") - 298.) / Math.pow(298, 2)));

		environment.get("MODERATE").put("C.OHrad.aM", 500000.);

		environment.get("MODERATE").put("KDEG.aM",
				environment.get("MODERATE").get("FRgas.aM") * inputEng.getSubstancesData("kdegG.air")
						* (environment.get("MODERATE").get("C.OHrad.aM") / inputEng.getSubstancesData("C.OHrad"))
						* environment.get("MODERATE").get("Tempfactor.aM"));

		// This is the same as k.aMG
		environment.get("MODERATE").put("k.aM",
				environment.get("MODERATE").get("kesc.aM") + environment.get("MODERATE").get("KDEG.aM"));

		environment.get("ARCTIC")
				.put("FRgas.aA", 1
						- environment.get("MODERATE").get("FRACaerw.aM") * environment.get("MODERATE").get("Kaerw.aM")
								/ (1. + environment.get("MODERATE").get("FRACaerw.aM")
										* environment.get("MODERATE").get("Kaerw.aM")
										+ environment.get("MODERATE").get("FRACaers.aM")
												* environment.get("MODERATE").get("Kaers.aM"))
						- environment.get("MODERATE").get("FRACaers.aM") * environment.get("MODERATE").get("Kaers.aM")
								/ (1. + environment.get("MODERATE").get("FRACaerw.aM")
										* environment.get("MODERATE").get("Kaerw.aM")
										+ environment.get("MODERATE").get("FRACaers.aM")
												* environment.get("MODERATE").get("Kaers.aM")));

		environment.get("ARCTIC").put("KDEG.aA",
				environment.get("ARCTIC").get("FRgas.aA") * inputEng.getSubstancesData("kdegG.air")
						* (environment.get("ARCTIC").get("C.OHrad.aA") / inputEng.getSubstancesData("C.OHrad"))
						* environment.get("ARCTIC").get("Tempfactor.aA"));

		// k.aA is k.aAG in excel (is some points)
		environment.get("ARCTIC").put("k.aA",
				environment.get("ARCTIC").get("kesc.aA") + environment.get("ARCTIC").get("KDEG.aA"));

		environment.get("MODERATE").put("k.aM.aA", environment.get("ARCTIC").get("k.aA.aM")
				* environment.get("ARCTIC").get("VOLUME.aA") / environment.get("MODERATE").get("VOLUME.aM"));

		environment.get("MODERATE").put("k.aM.aT", environment.get("TROPICAL").get("k.aT.aM")
				* environment.get("TROPICAL").get("VOLUME.aT") / environment.get("MODERATE").get("VOLUME.aM"));

		environment.get("MODERATE").put("k.aMA",
				environment.get("MODERATE").get("kesc.aM") + environment.get("ARCTIC").get("KDEG.aA"));

		environment.get("ARCTIC").put("Tempfactor.wsdsA",
				Math.pow(inputEng.getSubstancesData("Q.10"), ((environment.get("ARCTIC").get("TEMP.A") - 298.) / 10.)));

		environment.get("ARCTIC").put("BACT.w2A", 40000.);
		environment.get("ARCTIC").put("BACT.w3A", 40000.);

		environment.get("ARCTIC").put("KDEG.w2A",
				inputEng.getSubstancesData("kdegD.water") * environment.get("ARCTIC").get("Tempfactor.wsdsA")
						* (environment.get("ARCTIC").get("BACT.w2A") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("ARCTIC").get("FRw.w2A"));

		environment.get("ARCTIC").put("KDEG.w3A",
				inputEng.getSubstancesData("kdegD.water") * environment.get("ARCTIC").get("Tempfactor.wsdsA")
						* (environment.get("ARCTIC").get("BACT.w3A") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("ARCTIC").get("FRw.w3A"));

		environment.get("MODERATE").put("k.w3MA", environment.get("ARCTIC").get("KDEG.w3A"));

		environment.get("ARCTIC").put("k.aAA",
				environment.get("ARCTIC").get("kesc.aA") + environment.get("ARCTIC").get("KDEG.aA"));

		environment.get("ARCTIC").put("k.w2AA", environment.get("ARCTIC").get("KDEG.w2A"));
		environment.get("ARCTIC").put("k.w3AA", environment.get("ARCTIC").get("KDEG.w3A"));

		environment.get("TROPICAL").put("k.aTA",
				environment.get("TROPICAL").get("kesc.aT") + environment.get("ARCTIC").get("KDEG.aA"));

		environment.get("TROPICAL").put("k.w2TA", environment.get("ARCTIC").get("KDEG.w2A"));
		environment.get("TROPICAL").put("k.w3TA", environment.get("ARCTIC").get("KDEG.w3A"));

		environment.get("REGIONAL").put("k.aRA",
				environment.get("REGIONAL").get("kesc.aR") + environment.get("ARCTIC").get("KDEG.aA"));

		environment.get("REGIONAL").put("k.w2RA", environment.get("ARCTIC").get("KDEG.w2A"));

		environment.get("CONTINENTAL").put("k.aCA",
				environment.get("CONTINENTAL").get("kesc.aC") + environment.get("ARCTIC").get("KDEG.aA"));

		environment.get("CONTINENTAL").put("k.w2CA", environment.get("ARCTIC").get("KDEG.w2A"));

		environment.get("MODERATE").put("CORRvolat.sM",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("MODERATE").get("DEPTH.sM")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("MODERATE").get("DEPTH.sM")))));

		environment.get("MODERATE").put("Tempfactor.wsdsM", Math.pow(inputEng.getSubstancesData("Q.10"),
				((environment.get("MODERATE").get("TEMP.M") - 298.) / 10.)));

		environment.get("MODERATE").put("KDEG.sdM",
				environment.get("MODERATE").get("Tempfactor.wsdsM") * inputEng.getSubstancesData("kdegD.sed"));

		environment.get("MODERATE").put("KDEG.sM",
				environment.get("MODERATE").get("Tempfactor.wsdsM") * inputEng.getSubstancesData("kdegD.soil"));

		environment.get("MODERATE").put("BACT.w2M", 40000.);
		environment.get("MODERATE").put("BACT.w3M", 40000.);

		environment.get("MODERATE").put("k.w2MD",
				inputEng.getSubstancesData("kdegD.water") * environment.get("MODERATE").get("Tempfactor.wsdsM")
						* (environment.get("MODERATE").get("BACT.w2M") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("MODERATE").get("FRw.w2M"));

		environment.get("MODERATE").put("k.w3MD",
				inputEng.getSubstancesData("kdegD.water") * environment.get("MODERATE").get("Tempfactor.wsdsM")
						* (environment.get("MODERATE").get("BACT.w3M") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("MODERATE").get("FRw.w3M"));

		environment.get("MODERATE").put("k.sdMD",
				environment.get("MODERATE").get("Tempfactor.wsdsM") * inputEng.getSubstancesData("kdegD.sed")
						+ environment.get("MODERATE").get("NETsedrate.wM")
								* environment.get("MODERATE").get("SYSTEMAREA.M")
								* environment.get("MODERATE").get("AREAFRAC.wM")
								/ environment.get("MODERATE").get("VOLUME.sdM"));

		environment.get("MODERATE").put("KDEG.w2M",
				inputEng.getSubstancesData("kdegD.water") * environment.get("MODERATE").get("Tempfactor.wsdsM")
						* (environment.get("MODERATE").get("BACT.w2M") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("MODERATE").get("FRw.w2M"));

		environment.get("MODERATE").put("KDEG.w3M",
				inputEng.getSubstancesData("kdegD.water") * environment.get("MODERATE").get("Tempfactor.wsdsM")
						* (environment.get("MODERATE").get("BACT.w3M") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("MODERATE").get("FRw.w3M"));

		environment.get("MODERATE").put("MTCas.soil.sM", 0.1 * environment.get("MODERATE").get("KDEG.sM"));
		environment.get("MODERATE").put("MTCas.air.aM", 0.43 / (24. * 3600.) / 0.00475);

		environment.get("MODERATE").put("WINDspeed.M", 3.);

		environment.get("MODERATE").put("MTCaw.air.aM",
				0.01 * (0.3 + 0.2 * environment.get("MODERATE").get("WINDspeed.M"))
						* (Math.pow(0.018 / inputEng.getSubstancesData("Molweight"), 0.67 * 0.5)));

		environment.get("MODERATE").put("MTCaw.water.wM",
				0.01 * (0.0004 + 0.00004 * environment.get("MODERATE").get("WINDspeed.M")
						* environment.get("MODERATE").get("WINDspeed.M"))
						* (Math.pow(0.032 / inputEng.getSubstancesData("Molweight"), 0.5 * 0.5)));

		environment.get("MODERATE").put("CORG.sM", 0.02);

		environment.get("MODERATE").put("Kp.sM",
				(inputEng.getSubstancesData("FRorig.s1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.s1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("MODERATE").get("CORG.sM"));

		environment.get("MODERATE").put("Ksw.M",
				environment.get("MODERATE").get("FRACa.sM") * environment.get("MODERATE").get("Kaw.M")
						* inputEng.getSubstancesData("FRorig.s1w") + environment.get("MODERATE").get("FRACw.sM")
						+ environment.get("MODERATE").get("FRACs.sM") * environment.get("MODERATE").get("Kp.sM")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.);

		environment.get("MODERATE").put("k.sMD",
				environment.get("MODERATE").get("Tempfactor.wsdsM") * inputEng.getSubstancesData("kdegD.soil")
						+ environment.get("MODERATE").get("FRACinf.sM") * environment.get("MODERATE").get("RAINrate.M")
								/ environment.get("MODERATE").get("Ksw.M")
								* environment.get("MODERATE").get("CORRleach.sM")
								* environment.get("MODERATE").get("SYSTEMAREA.M")
								* environment.get("MODERATE").get("AREAFRAC.sM")
								/ environment.get("MODERATE").get("VOLUME.sM"));

		environment.get("MODERATE").put("VOLAT.sM.aM",
				(environment.get("MODERATE").get("MTCas.air.aM") * environment.get("MODERATE").get("MTCas.soil.sM"))
						/ (environment.get("MODERATE").get("MTCas.air.aM") + environment.get("MODERATE")
								.get("MTCas.soil.sM")
								/ ((environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.s1w"))
										/ environment.get("MODERATE").get("Ksw.M")))
						* environment.get("MODERATE").get("CORRvolat.sM"));

		environment.get("MODERATE").put("VOLAT.w2M.aM",
				(environment.get("MODERATE").get("MTCaw.air.aM") * environment.get("MODERATE").get("MTCaw.water.wM")
						/ (environment.get("MODERATE").get("MTCaw.air.aM")
								* (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.w2"))
								+ environment.get("MODERATE").get("MTCaw.water.wM")))
						* (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.w2"))
						* environment.get("MODERATE").get("FRw.w2M"));

		environment.get("MODERATE").put("GASABS.aM.sM",
				environment.get("MODERATE").get("FRgas.aM") * (environment.get("MODERATE").get("MTCas.air.aM")
						* environment.get("MODERATE").get("MTCas.soil.sM")
						/ (environment.get("MODERATE").get("MTCas.air.aM")
								* (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.s1w"))
								+ environment.get("MODERATE").get("MTCas.soil.sM"))));

		environment.get("MODERATE").put("GASABS.aM.wM",
				environment.get("MODERATE").get("FRgas.aM") * (environment.get("MODERATE").get("MTCaw.air.aM")
						* environment.get("MODERATE").get("MTCaw.water.wM")
						/ (environment.get("MODERATE").get("MTCaw.air.aM")
								* (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.w2"))
								+ environment.get("MODERATE").get("MTCaw.water.wM"))));

		environment.get("MODERATE").put("FRaerw.aM", environment.get("MODERATE").get("FRACaerw.aM")
				* environment.get("MODERATE").get("Kaerw.aM")
				/ (1. + environment.get("MODERATE").get("FRACaerw.aM") * environment.get("MODERATE").get("Kaerw.aM")
						+ environment.get("MODERATE").get("FRACaers.aM")
								* environment.get("MODERATE").get("Kaers.aM")));

		environment.get("MODERATE").put("FRaers.aM", environment.get("MODERATE").get("FRACaers.aM")
				* environment.get("MODERATE").get("Kaers.aM")
				/ (1. + environment.get("MODERATE").get("FRACaerw.aM") * environment.get("MODERATE").get("Kaerw.aM")
						+ environment.get("MODERATE").get("FRACaers.aM")
								* environment.get("MODERATE").get("Kaers.aM")));

		environment.get("MODERATE").put("GasWashout.M",
				environment.get("MODERATE").get("FRgas.aM")
						* (environment.get("MODERATE").get("tdry.M") + environment.get("MODERATE").get("twet.M"))
						/ environment.get("MODERATE").get("twet.M") * environment.get("MODERATE").get("RAINrate.M")
						/ (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("MODERATE").put("AerosolWashout.M",
				environment.get("MODERATE").get("FRaers.aM")
						* (environment.get("MODERATE").get("tdry.M") + environment.get("MODERATE").get("twet.M"))
						/ environment.get("MODERATE").get("twet.M") * environment.get("MODERATE").get("COLLECTeff.M")
						* environment.get("MODERATE").get("RAINrate.M"));

		environment.get("MODERATE").put("DRYDEPaerosol.M", environment.get("MODERATE").get("AEROSOLdeprate.M")
				* (environment.get("MODERATE").get("FRaerw.aM") + environment.get("MODERATE").get("FRaers.aM")));

		environment.get("MODERATE").put("kwet.M", (environment.get("MODERATE").get("AerosolWashout.M")
				+ environment.get("MODERATE").get("GasWashout.M")) / environment.get("MODERATE").get("HEIGHT.aM")
				+ (environment.get("MODERATE").get("GASABS.aM.wM") * environment.get("MODERATE").get("AREAFRAC.wM")
						+ environment.get("MODERATE").get("GASABS.aM.sM")
								* environment.get("MODERATE").get("AREAFRAC.sM"))
						/ environment.get("MODERATE").get("HEIGHT.aM")
				+ environment.get("MODERATE").get("KDEG.aM") + environment.get("MODERATE").get("k.aM.aC")
				+ environment.get("MODERATE").get("k.aM.aA") + environment.get("MODERATE").get("k.aM.aT"));

		environment.get("MODERATE").put("kdry.M", environment.get("MODERATE").get("DRYDEPaerosol.M")
				/ environment.get("MODERATE").get("HEIGHT.aM")
				+ (environment.get("MODERATE").get("GASABS.aM.wM") * environment.get("MODERATE").get("AREAFRAC.wM")
						+ +environment.get("MODERATE").get("GASABS.aM.sM")
								* environment.get("MODERATE").get("AREAFRAC.sM"))
						/ environment.get("MODERATE").get("HEIGHT.aM")
				+ environment.get("MODERATE").get("KDEG.aM") + environment.get("MODERATE").get("k.aM.aC")
				+ environment.get("MODERATE").get("k.aM.aA") + environment.get("MODERATE").get("k.aM.aT"));

		environment.get("MODERATE").put("MeanRem.aM", 1. / ((1. / environment.get("MODERATE").get("kdry.M"))
				* environment.get("MODERATE").get("tdry.M")
				/ (environment.get("MODERATE").get("tdry.M") + environment.get("MODERATE").get("twet.M"))
				+ (1. / environment.get("MODERATE").get("kwet.M")) * environment.get("MODERATE").get("twet.M")
						/ (environment.get("MODERATE").get("tdry.M") + environment.get("MODERATE").get("twet.M"))
				- (Math.pow(
						1. / environment.get("MODERATE").get("kwet.M") - 1. / environment.get("MODERATE").get("kdry.M"),
						2) / (environment.get("MODERATE").get("tdry.M") + environment.get("MODERATE").get("twet.M")))
						* (1. - Math.exp(
								-environment.get("MODERATE").get("kdry.M") * environment.get("MODERATE").get("tdry.M")))
						* (1. - Math.exp(
								-environment.get("MODERATE").get("kwet.M") * environment.get("MODERATE").get("twet.M")))
						/ (1. - Math.exp(
								-environment.get("MODERATE").get("kdry.M") * environment.get("MODERATE").get("tdry.M")
										- environment.get("MODERATE").get("kwet.M")
												* environment.get("MODERATE").get("twet.M")))));

		double val5 = environment.get("MODERATE").get("MeanRem.aM");
		double val6 = (environment.get("MODERATE").get("GASABS.aM.wM") * environment.get("MODERATE").get("AREAFRAC.wM")
				+ environment.get("MODERATE").get("GASABS.aM.sM") * environment.get("MODERATE").get("AREAFRAC.sM"))
				/ environment.get("MODERATE").get("HEIGHT.aM") + environment.get("MODERATE").get("KDEG.aM")
				+ environment.get("MODERATE").get("k.aM.aC") + environment.get("MODERATE").get("k.aM.aA")
				+ environment.get("MODERATE").get("k.aM.aT");

		environment.get("MODERATE").put("MeanDep.M", val5 - val6);

		environment.get("MODERATE").put("k.w2MD.aMG",
				environment.get("MODERATE").get("VOLAT.w2M.aM") / environment.get("MODERATE").get("DEPTH.w2M"));

		environment.get("MODERATE").put("k.sMD.aMG",
				environment.get("MODERATE").get("VOLAT.sM.aM") / environment.get("MODERATE").get("DEPTH.sM"));

		environment.get("MODERATE").put("k.aMG.sMD",
				(environment.get("MODERATE").get("MeanDep.M") + environment.get("MODERATE").get("GASABS.aM.sM")
						/ environment.get("MODERATE").get("HEIGHT.aM"))
						* environment.get("MODERATE").get("AREAFRAC.sM"));

		environment.get("MODERATE").put("k.aMG.w2MD",
				(environment.get("MODERATE").get("MeanDep.M") + environment.get("MODERATE").get("GASABS.aM.wM")
						/ environment.get("MODERATE").get("HEIGHT.aM"))
						* environment.get("MODERATE").get("AREAFRAC.wM"));

		environment.get("MODERATE").put("k.aMG.w2MD",
				(environment.get("MODERATE").get("MeanDep.M") + environment.get("MODERATE").get("GASABS.aM.wM")
						/ environment.get("MODERATE").get("HEIGHT.aM"))
						* environment.get("MODERATE").get("AREAFRAC.wM"));

		environment.get("MODERATE").put("k.sMD.w2MD",
				(environment.get("MODERATE").get("RAINrate.M") * environment.get("MODERATE").get("FRACrun.sM")
						/ environment.get("MODERATE").get("Ksw.M") + environment.get("MODERATE").get("EROSION.sM"))
						* environment.get("MODERATE").get("CORRrunoff.sM")
						/ environment.get("MODERATE").get("DEPTH.sM"));

		environment.get("ARCTIC").put("CORRvolat.sA",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")))));

		environment.get("ARCTIC").put("KDEG.sdA",
				environment.get("ARCTIC").get("Tempfactor.wsdsA") * inputEng.getSubstancesData("kdegD.sed"));

		environment.get("ARCTIC").put("KDEG.sA",
				environment.get("ARCTIC").get("Tempfactor.wsdsA") * inputEng.getSubstancesData("kdegD.soil"));

		environment.get("ARCTIC").put("k.w2AD", environment.get("ARCTIC").get("KDEG.w2A"));
		environment.get("ARCTIC").put("k.w3AD", environment.get("ARCTIC").get("KDEG.w3A"));

		environment.get("ARCTIC").put("k.sdAD", environment.get("ARCTIC").get("KDEG.sdA")
				+ environment.get("ARCTIC").get("NETsedrate.wA") / environment.get("ARCTIC").get("DEPTH.sdA"));

		environment.get("ARCTIC").put("FRACinf.sA", inputEng.getLandscapeSettings("CONTINENTAL", "FRACinf.C"));

		environment.get("ARCTIC").put("CORRleach.sA",
				(Math.exp((-1 / 0.1) * 0.5) * (1 / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")
						/ (1 - Math.exp((-1 / 0.1) * environment.get("ARCTIC").get("DEPTH.sA")))));

		environment.get("ARCTIC").put("k.sAD",
				environment.get("ARCTIC").get("KDEG.sA") + environment.get("ARCTIC").get("FRACinf.sA")
						* environment.get("ARCTIC").get("RAINrate.A") / environment.get("ARCTIC").get("Ksw.A")
						* environment.get("ARCTIC").get("CORRleach.sA") / environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("MTCas.soil.sA", 0.1 * environment.get("ARCTIC").get("KDEG.sA"));
		environment.get("ARCTIC").put("MTCas.air.aA", 0.43 / (24. * 3600.) / 0.00475);

		environment.get("ARCTIC").put("MTCaw.air.aA", 0.01 * (0.3 + 0.2 * environment.get("ARCTIC").get("WINDspeed.A"))
				* (Math.pow(0.018 / inputEng.getSubstancesData("Molweight"), 0.67 * 0.5)));

		environment.get("ARCTIC").put("MTCaw.water.wA",
				0.01 * (0.0004 + 0.00004 * environment.get("ARCTIC").get("WINDspeed.A")
						* environment.get("ARCTIC").get("WINDspeed.A"))
						* (Math.pow(0.032 / inputEng.getSubstancesData("Molweight"), 0.5 * 0.5)));

		environment.get("ARCTIC").put("VOLAT.sA.aA",
				(environment.get("ARCTIC").get("MTCas.air.aA") * environment.get("ARCTIC").get("MTCas.soil.sA"))
						/ (environment.get("ARCTIC").get("MTCas.air.aA") + environment.get("ARCTIC")
								.get("MTCas.soil.sA")
								/ ((environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.s1w"))
										/ environment.get("ARCTIC").get("Ksw.A")))
						* environment.get("ARCTIC").get("CORRvolat.sA"));

		environment.get("ARCTIC").put("VOLAT.w2A.aA",
				(environment.get("ARCTIC").get("MTCaw.air.aA") * environment.get("ARCTIC").get("MTCaw.water.wA")
						/ (environment.get("ARCTIC").get("MTCaw.air.aA")
								* (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.w2"))
								+ environment.get("ARCTIC").get("MTCaw.water.wA")))
						* (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.w2"))
						* environment.get("ARCTIC").get("FRw.w2A"));

		environment.get("ARCTIC").put("GASABS.aA.sA",
				environment.get("ARCTIC").get("FRgas.aA") * (environment.get("ARCTIC").get("MTCas.air.aA")
						* environment.get("ARCTIC").get("MTCas.soil.sA")
						/ (environment.get("ARCTIC").get("MTCas.air.aA")
								* (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.s1w"))
								+ environment.get("ARCTIC").get("MTCas.soil.sA"))));

		environment.get("ARCTIC").put("GASABS.aA.wA",
				environment.get("ARCTIC").get("FRgas.aA") * (environment.get("ARCTIC").get("MTCaw.air.aA")
						* environment.get("ARCTIC").get("MTCaw.water.wA")
						/ (environment.get("ARCTIC").get("MTCaw.air.aA")
								* (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("ARCTIC").get("MTCaw.water.wA"))));

		environment.get("ARCTIC").put("AEROSOLdeprate.A", 0.001);

		environment.get("ARCTIC").put("GasWashout.A",
				environment.get("ARCTIC").get("FRgas.aA")
						* (environment.get("ARCTIC").get("tdry.A") + environment.get("ARCTIC").get("twet.A"))
						/ environment.get("ARCTIC").get("twet.A") * environment.get("ARCTIC").get("RAINrate.A")
						/ (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("ARCTIC").put("Kaerw.aA",
				1. / (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("ARCTIC").put("Kaers.aA",
				0.54 * (inputEng.getSubstancesData("Kow") / inputEng.getSubstancesData("Kaw"))
						* inputEng.getLandscapeSettings("ALL-SCALE", "CORGaers")
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHOaers") / 1000.));

		environment.get("ARCTIC").put("FRaerw.aA", environment.get("MODERATE").get("FRACaerw.aM")
				* environment.get("MODERATE").get("Kaerw.aM")
				/ (1. + environment.get("MODERATE").get("FRACaerw.aM") * environment.get("MODERATE").get("Kaerw.aM")
						+ environment.get("MODERATE").get("FRACaers.aM")
								* environment.get("MODERATE").get("Kaers.aM")));

		environment.get("ARCTIC").put("FRaers.aA", environment.get("MODERATE").get("FRACaers.aM")
				* environment.get("MODERATE").get("Kaers.aM")
				/ (1. + environment.get("MODERATE").get("FRACaerw.aM") * environment.get("MODERATE").get("Kaerw.aM")
						+ environment.get("MODERATE").get("FRACaers.aM")
								* environment.get("MODERATE").get("Kaers.aM")));

		environment.get("ARCTIC").put("AerosolWashout.A",
				environment.get("ARCTIC").get("FRaers.aA")
						* (environment.get("ARCTIC").get("tdry.A") + environment.get("ARCTIC").get("twet.A"))
						/ environment.get("ARCTIC").get("twet.A") * environment.get("ARCTIC").get("COLLECTeff.A")
						* environment.get("ARCTIC").get("RAINrate.A"));

		environment.get("ARCTIC").put("DRYDEPaerosol.A", environment.get("ARCTIC").get("AEROSOLdeprate.A")
				* (environment.get("ARCTIC").get("FRaerw.aA") + environment.get("ARCTIC").get("FRaers.aA")));

		environment.get("ARCTIC").put("kwet.A", (environment.get("ARCTIC").get("AerosolWashout.A")
				+ environment.get("ARCTIC").get("GasWashout.A")) / environment.get("ARCTIC").get("HEIGHT.aA")
				+ (environment.get("ARCTIC").get("GASABS.aA.wA") * environment.get("ARCTIC").get("AREAFRAC.wA")
						+ environment.get("ARCTIC").get("GASABS.aA.sA") * environment.get("ARCTIC").get("AREAFRAC.sA"))
						/ environment.get("ARCTIC").get("HEIGHT.aA")
				+ environment.get("ARCTIC").get("KDEG.aA") + environment.get("ARCTIC").get("k.aA.aM"));

		environment.get("ARCTIC").put("kdry.A", environment.get("ARCTIC").get("DRYDEPaerosol.A")
				/ environment.get("ARCTIC").get("HEIGHT.aA")
				+ (environment.get("ARCTIC").get("GASABS.aA.wA") * environment.get("ARCTIC").get("AREAFRAC.wA")
						+ +environment.get("ARCTIC").get("GASABS.aA.sA") * environment.get("ARCTIC").get("AREAFRAC.sA"))
						/ environment.get("ARCTIC").get("HEIGHT.aA")
				+ environment.get("ARCTIC").get("KDEG.aA") + environment.get("ARCTIC").get("k.aA.aM"));

		environment.get("ARCTIC").put("MeanRem.aA",
				1. / ((1. / environment.get("ARCTIC").get("kdry.A")) * environment.get("ARCTIC").get("tdry.A")
						/ (environment.get("ARCTIC").get("tdry.A") + environment.get("ARCTIC").get("twet.A"))
						+ (1. / environment.get("ARCTIC").get("kwet.A"))
								* environment.get("ARCTIC").get("twet.A")
								/ (environment.get("ARCTIC").get("tdry.A") + environment.get("ARCTIC").get("twet.A"))
						- (Math.pow(1. / environment.get("ARCTIC").get("kwet.A")
								- 1. / environment.get("ARCTIC").get("kdry.A"), 2)
								/ (environment.get("ARCTIC").get("tdry.A") + environment.get("ARCTIC").get("twet.A")))
								* (1. - Math
										.exp(-environment.get("ARCTIC").get("kdry.A")
												* environment.get("ARCTIC").get("tdry.A")))
								* (1. - Math
										.exp(-environment.get("ARCTIC").get("kwet.A")
												* environment.get("ARCTIC").get("twet.A")))
								/ (1. - Math.exp(-environment.get("ARCTIC").get("kdry.A")
										* environment.get("ARCTIC").get("tdry.A")
										- environment.get("ARCTIC").get("kwet.A")
												* environment.get("ARCTIC").get("twet.A")))));

		double val7 = environment.get("ARCTIC").get("MeanRem.aA");
		double val8 = (environment.get("ARCTIC").get("GASABS.aA.wA") * environment.get("ARCTIC").get("AREAFRAC.wA")
				+ environment.get("ARCTIC").get("GASABS.aA.sA") * environment.get("ARCTIC").get("AREAFRAC.sA"))
				/ environment.get("ARCTIC").get("HEIGHT.aA") + environment.get("ARCTIC").get("KDEG.aA")
				+ environment.get("ARCTIC").get("k.aA.aM");

		environment.get("ARCTIC").put("MeanDep.A", val7 - val8);

		environment.get("ARCTIC").put("k.sAD.aAG",
				environment.get("ARCTIC").get("VOLAT.sA.aA") / environment.get("ARCTIC").get("DEPTH.sA"));

		environment.get("ARCTIC").put("k.w2AD.aAG",
				environment.get("ARCTIC").get("VOLAT.w2A.aA") / environment.get("ARCTIC").get("DEPTH.w2A"));

		environment.get("ARCTIC").put("k.aAG.sAD",
				(environment.get("ARCTIC").get("MeanDep.A")
						+ environment.get("ARCTIC").get("GASABS.aA.sA") / environment.get("ARCTIC").get("HEIGHT.aA"))
						* environment.get("ARCTIC").get("AREAFRAC.sA"));

		environment.get("ARCTIC").put("k.aAG.w2AD",
				(environment.get("ARCTIC").get("MeanDep.A")
						+ environment.get("ARCTIC").get("GASABS.aA.wA") / environment.get("ARCTIC").get("HEIGHT.aA"))
						* environment.get("ARCTIC").get("AREAFRAC.wA"));

		environment.get("ARCTIC").put("k.aAG.w2AD",
				(environment.get("ARCTIC").get("MeanDep.A")
						+ environment.get("ARCTIC").get("GASABS.aA.wA") / environment.get("ARCTIC").get("HEIGHT.aA"))
						* environment.get("ARCTIC").get("AREAFRAC.wA"));

		environment.get("TROPICAL").put("CORRvolat.sT",
				(Math.exp((-1. / 0.1) * 0) * (1. / 0.1) * environment.get("TROPICAL").get("DEPTH.sT")
						/ (1. - Math.exp((-1. / 0.1) * environment.get("TROPICAL").get("DEPTH.sT")))));

		environment.get("TROPICAL").put("Tempfactor.wsdsT", Math.pow(inputEng.getSubstancesData("Q.10"),
				((environment.get("TROPICAL").get("TEMP.T") - 298.) / 10.)));

		environment.get("TROPICAL").put("KDEG.sdT",
				environment.get("TROPICAL").get("Tempfactor.wsdsT") * inputEng.getSubstancesData("kdegD.sed"));

		environment.get("TROPICAL").put("KDEG.sT",
				environment.get("TROPICAL").get("Tempfactor.wsdsT") * inputEng.getSubstancesData("kdegD.soil"));

		environment.get("TROPICAL").put("MTCas.soil.sT", 0.1 * environment.get("TROPICAL").get("KDEG.sT"));
		environment.get("TROPICAL").put("MTCas.air.aT", 0.43 / (24. * 3600.) / 0.00475);

		environment.get("TROPICAL").put("MTCaw.air.aT",
				0.01 * (0.3 + 0.2 * environment.get("TROPICAL").get("WINDspeed.T"))
						* (Math.pow(0.018 / inputEng.getSubstancesData("Molweight"), 0.67 * 0.5)));

		environment.get("TROPICAL").put("MTCaw.water.wT",
				0.01 * (0.0004 + 0.00004 * environment.get("TROPICAL").get("WINDspeed.T")
						* environment.get("TROPICAL").get("WINDspeed.T"))
						* (Math.pow(0.032 / inputEng.getSubstancesData("Molweight"), 0.5 * 0.5)));

		environment.get("TROPICAL").put("VOLAT.sT.aT",
				(environment.get("TROPICAL").get("MTCas.air.aT") * environment.get("TROPICAL").get("MTCas.soil.sT"))
						/ (environment.get("TROPICAL").get("MTCas.air.aT") + environment.get("TROPICAL")
								.get("MTCas.soil.sT")
								/ ((environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.s1w"))
										/ environment.get("TROPICAL").get("Ksw.T")))
						* environment.get("TROPICAL").get("CORRvolat.sT"));

		environment.get("TROPICAL").put("VOLAT.w2T.aT",
				(environment.get("TROPICAL").get("MTCaw.air.aT") * environment.get("TROPICAL").get("MTCaw.water.wT")
						/ (environment.get("TROPICAL").get("MTCaw.air.aT")
								* (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.w2"))
								+ environment.get("TROPICAL").get("MTCaw.water.wT")))
						* (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.w2"))
						* environment.get("TROPICAL").get("FRw.w2T"));

		environment.get("TROPICAL").put("GASABS.aT.sT",
				environment.get("TROPICAL").get("FRgas.aT") * environment.get("TROPICAL").get("MTCas.air.aT")
						* environment.get("TROPICAL").get("MTCas.soil.sT")
						/ (environment.get("TROPICAL").get("MTCas.air.aT")
								* (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.s1w"))
								/ environment.get("TROPICAL").get("Ksw.T"))
						+ environment.get("TROPICAL").get("MTCas.soil.sT"));

		environment.get("TROPICAL").put("GASABS.aT.sT", environment.get("TROPICAL").get("FRgas.aT")
				* (environment.get("TROPICAL").get("MTCas.air.aT") * environment.get("TROPICAL").get("MTCas.soil.sT"))
				/ (environment.get("TROPICAL").get("MTCas.air.aT")
						* ((environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.s1w"))
								/ environment.get("TROPICAL").get("Ksw.T"))
						+ environment.get("TROPICAL").get("MTCas.soil.sT"))

		);

		environment.get("TROPICAL").put("GASABS.aT.wT",
				environment.get("TROPICAL").get("FRgas.aT") * (environment.get("TROPICAL").get("MTCaw.air.aT")
						* environment.get("TROPICAL").get("MTCaw.water.wT")
						/ (environment.get("TROPICAL").get("MTCaw.air.aT")
								* (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.w1"))
								+ environment.get("TROPICAL").get("MTCaw.water.wT"))));

		environment.get("TROPICAL").put("AEROSOLdeprate.T", 0.001);

		environment.get("TROPICAL").put("GasWashout.T",
				environment.get("TROPICAL").get("FRgas.aT")
						* (environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T"))
						/ environment.get("TROPICAL").get("twet.T") * environment.get("TROPICAL").get("RAINrate.T")
						/ (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("TROPICAL").put("Kaerw.aT",
				1. / (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.aerw")));

		environment.get("TROPICAL").put("Kaers.aT",
				0.54 * (inputEng.getSubstancesData("Kow") / inputEng.getSubstancesData("Kaw"))
						* inputEng.getLandscapeSettings("ALL-SCALE", "CORGaers")
						* (inputEng.getLandscapeSettings("ALL-SCALE", "RHOaers") / 1000.));

		environment.get("TROPICAL").put("FRaerw.aT", environment.get("TROPICAL").get("FRACaerw.aT")
				* environment.get("TROPICAL").get("Kaerw.aT")
				/ (1. + environment.get("TROPICAL").get("FRACaerw.aT") * environment.get("TROPICAL").get("Kaerw.aT")
						+ environment.get("TROPICAL").get("FRACaers.aT")
								* environment.get("TROPICAL").get("Kaers.aT")));

		environment.get("TROPICAL").put("FRaers.aT", environment.get("TROPICAL").get("FRACaers.aT")
				* environment.get("TROPICAL").get("Kaers.aT")
				/ (1. + environment.get("TROPICAL").get("FRACaerw.aT") * environment.get("TROPICAL").get("Kaerw.aT")
						+ environment.get("TROPICAL").get("FRACaers.aT")
								* environment.get("TROPICAL").get("Kaers.aT")));

		environment.get("TROPICAL").put("AerosolWashout.T",
				environment.get("TROPICAL").get("FRaers.aT")
						* (environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T"))
						/ environment.get("TROPICAL").get("twet.T") * environment.get("TROPICAL").get("COLLECTeff.T")
						* environment.get("TROPICAL").get("RAINrate.T"));

		environment.get("TROPICAL").put("DRYDEPaerosol.T", environment.get("TROPICAL").get("AEROSOLdeprate.T")
				* (environment.get("TROPICAL").get("FRaerw.aT") + environment.get("TROPICAL").get("FRaers.aT")));

		environment.get("TROPICAL").put("kwet.T", (environment.get("TROPICAL").get("AerosolWashout.T")
				+ environment.get("TROPICAL").get("GasWashout.T")) / environment.get("TROPICAL").get("HEIGHT.aT")
				+ (environment.get("TROPICAL").get("GASABS.aT.wT") * environment.get("TROPICAL").get("AREAFRAC.wT")
						+ environment.get("TROPICAL").get("GASABS.aT.sT")
								* environment.get("TROPICAL").get("AREAFRAC.sT"))
						/ environment.get("TROPICAL").get("HEIGHT.aT")
				+ environment.get("TROPICAL").get("KDEG.aT") + environment.get("TROPICAL").get("k.aT.aM"));

		environment.get("TROPICAL").put("kdry.T", environment.get("TROPICAL").get("DRYDEPaerosol.T")
				/ environment.get("TROPICAL").get("HEIGHT.aT")
				+ (environment.get("TROPICAL").get("GASABS.aT.wT") * environment.get("TROPICAL").get("AREAFRAC.wT")
						+ +environment.get("TROPICAL").get("GASABS.aT.sT")
								* environment.get("TROPICAL").get("AREAFRAC.sT"))
						/ environment.get("TROPICAL").get("HEIGHT.aT")
				+ environment.get("TROPICAL").get("KDEG.aT") + environment.get("TROPICAL").get("k.aT.aM"));

		environment.get("TROPICAL").put("MeanRem.aT", 1. / ((1. / environment.get("TROPICAL").get("kdry.T"))
				* environment.get("TROPICAL").get("tdry.T")
				/ (environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T"))
				+ (1. / environment.get("TROPICAL").get("kwet.T")) * environment.get("TROPICAL").get("twet.T")
						/ (environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T"))
				- (Math.pow(
						1. / environment.get("TROPICAL").get("kwet.T") - 1. / environment.get("TROPICAL").get("kdry.T"),
						2) / (environment.get("TROPICAL").get("tdry.T") + environment.get("TROPICAL").get("twet.T")))
						* (1. - Math.exp(
								-environment.get("TROPICAL").get("kdry.T") * environment.get("TROPICAL").get("tdry.T")))
						* (1. - Math.exp(
								-environment.get("TROPICAL").get("kwet.T") * environment.get("TROPICAL").get("twet.T")))
						/ (1. - Math.exp(
								-environment.get("TROPICAL").get("kdry.T") * environment.get("TROPICAL").get("tdry.T")
										- environment.get("TROPICAL").get("kwet.T")
												* environment.get("TROPICAL").get("twet.T")))));

		double val9 = environment.get("TROPICAL").get("MeanRem.aT");
		double val10 = (environment.get("TROPICAL").get("GASABS.aT.wT") * environment.get("TROPICAL").get("AREAFRAC.wT")
				+ environment.get("TROPICAL").get("GASABS.aT.sT") * environment.get("TROPICAL").get("AREAFRAC.sT"))
				/ environment.get("TROPICAL").get("HEIGHT.aT") + environment.get("TROPICAL").get("KDEG.aT")
				+ environment.get("TROPICAL").get("k.aT.aM");

		environment.get("TROPICAL").put("MeanDep.T", val9 - val10);

		environment.get("TROPICAL").put("k.sTD.aTG",
				environment.get("TROPICAL").get("VOLAT.sT.aT") / environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("k.w2TD.aTG",
				environment.get("TROPICAL").get("VOLAT.w2T.aT") / environment.get("TROPICAL").get("DEPTH.w2T"));

		environment.get("TROPICAL").put("k.aTG.sTD",
				(environment.get("TROPICAL").get("MeanDep.T") + environment.get("TROPICAL").get("GASABS.aT.sT")
						/ environment.get("TROPICAL").get("HEIGHT.aT"))
						* environment.get("TROPICAL").get("AREAFRAC.sT"));

		environment.get("TROPICAL").put("k.aTG.w2TD",
				(environment.get("TROPICAL").get("MeanDep.T") + environment.get("TROPICAL").get("GASABS.aT.wT")
						/ environment.get("TROPICAL").get("HEIGHT.aT"))
						* environment.get("TROPICAL").get("AREAFRAC.wT"));

		environment.get("TROPICAL").put("k.aTG.w2TD",
				(environment.get("TROPICAL").get("MeanDep.T") + environment.get("TROPICAL").get("GASABS.aT.wT")
						/ environment.get("TROPICAL").get("HEIGHT.aT"))
						* environment.get("TROPICAL").get("AREAFRAC.wT"));

		environment.get("MODERATE").put("k.aMP.w2MP",
				(environment.get("MODERATE").get("AEROSOLdeprate.M") * environment.get("MODERATE").get("AREAFRAC.wM")
						* environment.get("MODERATE").get("SYSTEMAREA.M"))
						/ environment.get("MODERATE").get("VOLUME.aM"));

		environment.get("MODERATE").put("k.w2MA", environment.get("ARCTIC").get("KDEG.w2A"));

		environment.get("TROPICAL").put("BACT.w2T", 40000.);
		environment.get("TROPICAL").put("BACT.w3T", 40000.);

		environment.get("TROPICAL").put("KDEG.w2T",
				inputEng.getSubstancesData("kdegD.water") * environment.get("TROPICAL").get("Tempfactor.wsdsT")
						* (environment.get("TROPICAL").get("BACT.w2T") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("TROPICAL").get("FRw.w2T"));

		environment.get("TROPICAL").put("KDEG.w3T",
				inputEng.getSubstancesData("kdegD.water") * environment.get("TROPICAL").get("Tempfactor.wsdsT")
						* (environment.get("TROPICAL").get("BACT.w3T") / inputEng.getSubstancesData("BACT.test"))
						* environment.get("TROPICAL").get("FRw.w3T"));

		environment.get("TROPICAL").put("KDEG.sT",
				environment.get("TROPICAL").get("Tempfactor.wsdsT") * inputEng.getSubstancesData("kdegD.soil"));

		environment.get("TROPICAL").put("k.sTD",
				environment.get("TROPICAL").get("KDEG.sT") + environment.get("TROPICAL").get("FRACinf.sT")
						* environment.get("TROPICAL").get("RAINrate.T") / environment.get("TROPICAL").get("Ksw.T")
						* environment.get("TROPICAL").get("CORRleach.sT")
						/ environment.get("TROPICAL").get("DEPTH.sT"));

		environment.get("TROPICAL").put("k.w2TD", environment.get("TROPICAL").get("KDEG.w2T"));
		environment.get("TROPICAL").put("k.w3TD", environment.get("TROPICAL").get("KDEG.w3T"));

		environment.get("TROPICAL").put("k.sdTD", environment.get("TROPICAL").get("KDEG.sdT")
				+ environment.get("TROPICAL").get("NETsedrate.wT") / environment.get("TROPICAL").get("DEPTH.sdT"));

		environment.get("REGIONAL").put("FRwD.s1R",
				environment.get("REGIONAL").get("FRACw.s1R") / (environment.get("REGIONAL").get("FRACa.s1R")
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("REGIONAL").get("FRACw.s1R")
						+ environment.get("REGIONAL").get("FRACs.s1R") * environment.get("REGIONAL").get("Kp.s1R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("REGIONAL").put("FRs.s1R",
				environment.get("REGIONAL").get("FRACw.s1R") / (environment.get("REGIONAL").get("FRACa.s1R")
						* environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s1w")
						/ (environment.get("REGIONAL").get("Kp.s1R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("REGIONAL").get("FRACw.s1R") / (environment.get("REGIONAL").get("Kp.s1R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("REGIONAL").get("FRACa.s1R")));

		environment.get("CONTINENTAL").put("FRw.s1C",
				environment.get("CONTINENTAL").get("FRACw.s1C") / (environment.get("CONTINENTAL").get("FRACa.s1C")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("CONTINENTAL").get("FRACw.s1C")
						+ environment.get("CONTINENTAL").get("FRACs.s1C") * environment.get("CONTINENTAL").get("Kp.s1C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("CONTINENTAL").put("FRs.s1C",
				environment.get("CONTINENTAL").get("FRACw.s1C") / (environment.get("CONTINENTAL").get("FRACa.s1C")
						* environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s1w")
						/ (environment.get("CONTINENTAL").get("Kp.s1C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("CONTINENTAL").get("FRACw.s1C")
								/ (environment.get("CONTINENTAL").get("Kp.s1C")
										* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("CONTINENTAL").get("FRACa.s1C")));

		environment.get("REGIONAL").put("FRwD.s2R",
				environment.get("REGIONAL").get("FRACw.s2R") / (environment.get("REGIONAL").get("FRACa.s2R")
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s2w"))
						+ environment.get("REGIONAL").get("FRACw.s2R")
						+ environment.get("REGIONAL").get("FRACs.s2R") * environment.get("REGIONAL").get("Kp.s2R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("REGIONAL").put("FRs.s2R",
				environment.get("REGIONAL").get("FRACw.s2R") / (environment.get("REGIONAL").get("FRACa.s2R")
						* environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s2w")
						/ (environment.get("REGIONAL").get("Kp.s2R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("REGIONAL").get("FRACw.s2R") / (environment.get("REGIONAL").get("Kp.s2R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("REGIONAL").get("FRACa.s2R")));

		environment.get("CONTINENTAL").put("FRw.s2C",
				environment.get("CONTINENTAL").get("FRACw.s2C") / (environment.get("CONTINENTAL").get("FRACa.s2C")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s2w"))
						+ environment.get("CONTINENTAL").get("FRACw.s2C")
						+ environment.get("CONTINENTAL").get("FRACs.s2C") * environment.get("CONTINENTAL").get("Kp.s2C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("CONTINENTAL").put("FRs.s2C",
				environment.get("CONTINENTAL").get("FRACw.s2C") / (environment.get("CONTINENTAL").get("FRACa.s2C")
						* environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s2w")
						/ (environment.get("CONTINENTAL").get("Kp.s2C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("CONTINENTAL").get("FRACw.s2C")
								/ (environment.get("CONTINENTAL").get("Kp.s2C")
										* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("CONTINENTAL").get("FRACa.s2C")));

		environment.get("REGIONAL").put("FRwD.s3R",
				environment.get("REGIONAL").get("FRACw.s3R") / (environment.get("REGIONAL").get("FRACa.s3R")
						* (environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s3w"))
						+ environment.get("REGIONAL").get("FRACw.s3R")
						+ environment.get("REGIONAL").get("FRACs.s3R") * environment.get("REGIONAL").get("Kp.s3R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("REGIONAL").put("FRs.s3R",
				environment.get("REGIONAL").get("FRACw.s3R") / (environment.get("REGIONAL").get("FRACa.s3R")
						* environment.get("REGIONAL").get("KawD.R") * inputEng.getSubstancesData("FRorig.s3w")
						/ (environment.get("REGIONAL").get("Kp.s3R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("REGIONAL").get("FRACw.s3R") / (environment.get("REGIONAL").get("Kp.s3R")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("REGIONAL").get("FRACa.s3R")));

		environment.get("CONTINENTAL").put("FRw.s3C",
				environment.get("CONTINENTAL").get("FRACw.s3C") / (environment.get("CONTINENTAL").get("FRACa.s3C")
						* (environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s3w"))
						+ environment.get("CONTINENTAL").get("FRACw.s3C")
						+ environment.get("CONTINENTAL").get("FRACs.s3C") * environment.get("CONTINENTAL").get("Kp.s3C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("CONTINENTAL").put("FRs.s3C",
				environment.get("CONTINENTAL").get("FRACw.s3C") / (environment.get("CONTINENTAL").get("FRACa.s3C")
						* environment.get("CONTINENTAL").get("Kaw.C") * inputEng.getSubstancesData("FRorig.s3w")
						/ (environment.get("CONTINENTAL").get("Kp.s3C")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("CONTINENTAL").get("FRACw.s3C")
								/ (environment.get("CONTINENTAL").get("Kp.s3C")
										* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("CONTINENTAL").get("FRACa.s3C")));

		environment.get("MODERATE").put("FRw.sM",
				environment.get("MODERATE").get("FRACw.sM") / (environment.get("MODERATE").get("FRACa.sM")
						* (environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("MODERATE").get("FRACw.sM")
						+ environment.get("MODERATE").get("FRACs.sM") * environment.get("MODERATE").get("Kp.sM")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("MODERATE").put("FRs.sM",
				environment.get("MODERATE").get("FRACw.sM") / (environment.get("MODERATE").get("FRACa.sM")
						* environment.get("MODERATE").get("Kaw.M") * inputEng.getSubstancesData("FRorig.s1w")
						/ (environment.get("MODERATE").get("Kp.sM")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("MODERATE").get("FRACw.sM") / (environment.get("MODERATE").get("Kp.sM")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("MODERATE").get("FRACa.sM")));

		environment.get("ARCTIC").put("FRw.sA",
				environment.get("ARCTIC").get("FRACw.sA") / (environment.get("ARCTIC").get("FRACa.sA")
						* (environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("ARCTIC").get("FRACw.sA")
						+ environment.get("ARCTIC").get("FRACs.sA") * environment.get("ARCTIC").get("Kp.sA")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("ARCTIC").put("FRs.sA",
				environment.get("ARCTIC").get("FRACw.sA") / (environment.get("ARCTIC").get("FRACa.sA")
						* environment.get("ARCTIC").get("Kaw.A") * inputEng.getSubstancesData("FRorig.s1w")
						/ (environment.get("ARCTIC").get("Kp.sA")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("ARCTIC").get("FRACw.sA") / (environment.get("ARCTIC").get("Kp.sA")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("ARCTIC").get("FRACa.sA")));

		environment.get("TROPICAL").put("FRw.sT",
				environment.get("TROPICAL").get("FRACw.sT") / (environment.get("TROPICAL").get("FRACa.sT")
						* (environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.s1w"))
						+ environment.get("TROPICAL").get("FRACw.sT")
						+ environment.get("TROPICAL").get("FRACs.sT") * environment.get("TROPICAL").get("Kp.sT")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.));

		environment.get("TROPICAL").put("FRs.sT",
				environment.get("TROPICAL").get("FRACw.sT") / (environment.get("TROPICAL").get("FRACa.sT")
						* environment.get("TROPICAL").get("Kaw.T") * inputEng.getSubstancesData("FRorig.s1w")
						/ (environment.get("TROPICAL").get("Kp.sT")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("TROPICAL").get("FRACw.sT") / (environment.get("TROPICAL").get("Kp.sT")
								* inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid") / 1000.)
						+ environment.get("TROPICAL").get("FRACa.sT")));

		environment.get("REGIONAL").put("CORG.sd0R", 0.05);
		environment.get("REGIONAL").put("Kp.sd0R",
				(inputEng.getSubstancesData("FRorig.sd1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("REGIONAL").get("CORG.sd0R")

		);

		environment.get("CONTINENTAL").put("CORG.sd0C", 0.05);
		environment.get("CONTINENTAL").put("Kp.sd0C",
				(inputEng.getSubstancesData("FRorig.sd1") * inputEng.getSubstancesData("Ksw")
						+ (1. - inputEng.getSubstancesData("FRorig.sd1")) * inputEng.getSubstancesData("Ksw.alt"))
						* (1000. / inputEng.getLandscapeSettings("ALL-SCALE", "RHOsolid")
								/ inputEng.getLandscapeSettings("ALL-SCALE", "CORG"))
						* environment.get("CONTINENTAL").get("CORG.sd0C")

		);
	}
}
