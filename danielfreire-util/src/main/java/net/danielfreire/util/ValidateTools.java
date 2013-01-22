package net.danielfreire.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateTools {
	
	private static ValidateTools validate = new ValidateTools();
	
	private final Logger logger = Logger.getLogger(ValidateTools.class.getName()); 
	
	public ValidateTools() {
		super();
	}
	
	public static synchronized ValidateTools getInstancia() {
        return validate;
    }

	/**   
     *  This method validate e-mail
     *  
     *  @param java.lang.String
     */
	public boolean isCep(String cep) {
        boolean isCepIdValid = false;
        if (cep != null && cep.length() == 8) {
            String expression = "[0-9]+";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(cep);
            if (matcher.matches()) {
            	isCepIdValid = true;
            }
        }
        return isCepIdValid;
    }
	
	/**   
     *  This method validate CPF
     *  
     *  @param java.lang.String
     */ 
	public boolean isCPF(String CPF) {
		CPF = CPF.replace(".", "");
		CPF = CPF.replace("-", "");
	    if (CPF==null || CPF.equals("00000000000") || CPF.equals("11111111111") ||
	        CPF.equals("22222222222") || CPF.equals("33333333333") ||
	        CPF.equals("44444444444") || CPF.equals("55555555555") ||
	        CPF.equals("66666666666") || CPF.equals("77777777777") ||
	        CPF.equals("88888888888") || CPF.equals("99999999999") ||
	       (CPF.length() != 11))
	       return(false);
	 
	    char dig10, dig11;
	    int sm, i, r, num, peso;
	 
	    try {
	      sm = 0;
	      peso = 10;
	      for (i=0; i<9; i++) {             
	        num = (int)(CPF.charAt(i) - 48);
	        sm = sm + (num * peso);
	        peso = peso - 1;
	      }
	 
	      r = 11 - (sm % 11);
	      if ((r == 10) || (r == 11))
	         dig10 = '0';
	      else dig10 = (char)(r + 48); 
	 
	      sm = 0;
	      peso = 11;
	      for(i=0; i<10; i++) {
	        num = (int)(CPF.charAt(i) - 48);
	        sm = sm + (num * peso);
	        peso = peso - 1;
	      }
	 
	      r = 11 - (sm % 11);
	      if ((r == 10) || (r == 11))
	         dig11 = '0';
	      else dig11 = (char)(r + 48);
	 
	      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
	         return(true);
	      else return(false);
	    } catch (InputMismatchException erro) {
	        return(false);
	    } catch (Exception e) {
	    	logger.log(Level.WARNING, e.getMessage(), e);
			return(false);
		}
	}
	
	/**   
     *  This method validate e-mail
     *  
     *  @param java.lang.String
     */
	public boolean isEmail(String email) {
        boolean isEmailIdValid = false;
        try {
	        if (email != null && email.length() > 0) {
	            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	            Matcher matcher = pattern.matcher(email);
	            if (matcher.matches()) {
	                isEmailIdValid = true;
	            }
	        }
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
        return isEmailIdValid;
    }
	
	/**   
     *  This method validate if not null
     *  
     *  @param java.lang.String
     */
	public boolean isNotnull(String string) {
		try {
	        if (string != null && string.trim().length() > 0) {
	            return true;
	        }
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			return true;
		}
        return false;
    }
	
	/**   
     *  This method validate if not null
     *  
     *  @param java.lang.String
     */
	public boolean isNullEmpty(String string) {
		try {
	        if (string == null || string.trim().length() == 0) {
	            return true;
	        }
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			return true;
		}
        return false;
    }
	
	/**   
     *  This method validate if not null
     *  
     *  @param java.lang.String
     */
	public boolean isPassword(String password) {
		try {
	        if (password != null && password.trim().length() > 4) {
	        	
	        	boolean vabsic = false;
	        	String expression = "[0-9a-zA-Z]+";
	            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	            Matcher matcher = pattern.matcher(password);
	            if (matcher.matches()) {
	            	vabsic = true;
	            }
	        	
	        	boolean numok = false;
	        	for (char letra : password.toCharArray()) {
	        		try {
	        			Integer.parseInt(String.valueOf(letra));
	        			numok = true;
	        			break;
	        		} catch (Exception e) {
	        		}
	        	}
	        	
	        	boolean letok = false;
	        	for (char letra : password.toCharArray()) {
	        		try {
	        			Integer.parseInt(String.valueOf(letra));
	        		} catch (Exception e) {
	        			letok = true;
	        			break;
	        		}
	        	}
	        	
	        	if (vabsic && numok && letok) {
	        		return true;
	        	}
	        	
	        }
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
        return false;
    }
	
	/**
	 * isNumberValid
	 * @param number
	 * @return
	 */
	public boolean isNumber(String number){
		try {
			String expression = "[0-9]+";
	        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(number);
	        if (matcher.matches()) {
	        	return true;
	        }
	        return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public boolean isUrl(String url){
		try {
			if (!url.substring(0, 7).equals("http://") && !url.substring(0, 8).equals("https://")) {
				return false;
			} 
			
			boolean vabsic = false;
        	String expression = "^[0-9a-z\\.\\-:/?&]+";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(url);
            
            if (matcher.matches()) {
            	vabsic = true;
            }
            
            if (vabsic) {
            	return true;
            } else {
            	return false;
            }
		} catch (Exception e) { 
			logger.log(Level.WARNING, e.getMessage(), e);
		} 
		return false;
	}
	
	public boolean isLong(String value){
		try {
			Long.parseLong(value);
			return true;
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return false;
	}
	
	public boolean isDouble(String value){
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return false;
	}
	
	public boolean isBigDecimal(Double value){
		try {
			new BigDecimal(value);
			return true;
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return false;
	}
	
	public boolean isStringDate(String date) {
		try {
			new SimpleDateFormat("dd/MM/yyyy").parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
