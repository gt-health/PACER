package edu.gatech.ResultsManager.FHIR2ECR.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.Age;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.StringType;

import ca.uhn.fhir.model.api.IDatatype;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.StringDt;

public class HAPIFHIRUtil {
	//DSTU2
	public static Date getDate(IDatatype data) {
		if(data instanceof DateTimeType) {
			return getDate((DateTimeType)data);
		}
		if(data instanceof Age) {
			return getDate((Age)data);
		}
		if(data instanceof Period) {
			return getDate((Period)data);
		}
		if(data instanceof StringDt) {
			return getDate((StringDt)data);
		}
		return null;
	}
	
	public static Date getDate(DateTimeDt dateTime) {
		return dateTime.getValue();
	}
	public static Date getDate(Age age) {
		return null;
	}
	public static Date getDate(Period period) {
		return period.getStart();
	}
	public static Date getDate(StringDt string) {
		try {
			return DateFormat.getDateInstance().parse(string.toString());
		} catch (ParseException e) {
			return null;
		}
	}
	
	//STU3
	
	public static Date getDate(DateTimeType dateTimeType) {
		return new Date(dateTimeType.getValue().getTime());
	}
	
	public static String addressToString(Address address) {
		String returnString = "";
		for(StringType line: address.getLine()) {
			returnString.concat(line.getValue());
			returnString.concat(" ");
		}
		returnString.concat(address.getCity());
		returnString.concat(", ");
		returnString.concat(address.getState());
		returnString.concat(" ");
		returnString.concat(address.getPostalCode());
		return returnString;
	}
	
	public static Address stringToAddress(String string) {
		Address returnAddress = new Address();
		int lineEndIndex = string.indexOf(", ");
		returnAddress.addLine(string.substring(0, lineEndIndex));
		string = string.substring(lineEndIndex+2);
		int stateEndIndex = string.indexOf(" ");
		returnAddress.setState(string.substring(0,stateEndIndex));
		string = string.substring(stateEndIndex+1);
		returnAddress.setPostalCode(string); //End of formatted string
		return returnAddress;
	}
	
	public static String getIdFromFullURL(String URL) {
		return URL.substring(URL.lastIndexOf('/') + 1);
	}
}
