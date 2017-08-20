package test_tutorial;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class Human {

	private Date birthDay;
	
	private String firstName;
	
	private String lastName;
	
	private double height = 0.0;
	
	private double weight = 0.0;
	
	/**
	 * 年齢を返却する.
	 * 誕生日が未設定の場合は -1 を返却する.
	 * @param now
	 * @return
	 */
	public int getAge(Date now) {
		if (null == this.birthDay) {
			return -1;
		}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    return (Integer.parseInt(sdf.format(now)) - Integer.parseInt(sdf.format(this.birthDay))) / 10000;
	}
	
	public BigDecimal getBmi() {
		double bmi = (this.weight * 10000) / (this.height * this.height);
		BigDecimal bmiNum = new BigDecimal(bmi);
		bmiNum = bmiNum.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bmiNum;
	}

	/**
	 * 姓名を返却する(姓と名の間は半角スペースを付与する)
	 * 姓名が未設定の場合は半角スペースを返却する.
	 * @return
	 */
	public String getFullName() {
		return (StringUtils.isEmpty(firstName) ? "" : this.firstName) + " " + (StringUtils.isEmpty(lastName) ? "" : this.lastName); 
	}
	

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
