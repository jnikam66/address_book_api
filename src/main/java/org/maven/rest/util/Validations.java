package org.maven.rest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jyotinikam
 *
 */
public class Validations {
	
	/**
	 * @param email
	 * @return if the email is valid
	 * 	Examples: Following email addresses will pass validation
	 *         abc@xyz.net; ab.c@tx.gov
	 */
	public static boolean isEmailValid(String email){
		boolean isValid = false;
	
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
		isValid = true;
		}
		return isValid;
		}
	
	
	/**
	 * @param phoneNumber
	 * @return if the phone no valid or not
	 *  Examples: Matches following phone numbers:
	 *       (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890
	 */
	public static boolean isPhoneNumberValid(String phoneNumber){
		boolean isValid = false;
		
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
		isValid = true;
		}
		return isValid;
		}
}
