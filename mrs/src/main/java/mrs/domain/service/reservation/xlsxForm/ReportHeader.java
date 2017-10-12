package mrs.domain.service.reservation.xlsxForm;

import java.math.BigDecimal;
import java.util.Date;

public class ReportHeader {
	private Integer intValue;
	
	private String strValue;
	
	private BigDecimal decimalValue;
	
	private Date dateValue;

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public BigDecimal getDecimalValue() {
		return decimalValue;
	}

	public void setDecimalValue(BigDecimal decimalValue) {
		this.decimalValue = decimalValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	
	
}
