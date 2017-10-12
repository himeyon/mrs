package mrs.domain.service.reservation.xlsxForm;

import java.math.BigDecimal;
import java.util.Date;

public class ReportDetail {
	private Integer detailNumber;
	
	private String detailCode;
	
	private BigDecimal detailAmount;
	
	private Date detailDate;

	public Integer getDetailNumber() {
		return detailNumber;
	}

	public void setDetailNumber(Integer detailNumber) {
		this.detailNumber = detailNumber;
	}

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public BigDecimal getDetailAmount() {
		return detailAmount;
	}

	public void setDetailAmount(BigDecimal detailAmount) {
		this.detailAmount = detailAmount;
	}

	public Date getDetailDate() {
		return detailDate;
	}

	public void setDetailDate(Date detailDate) {
		this.detailDate = detailDate;
	}
	
}
