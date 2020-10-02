package jpa_school;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable //类似于<component>
public class Favorite 
{
	@Column(name="LOVE_FOOD")
	private String loveFood;
	
	@Column(name="LOVE_ANIMAL")
	private String loveAnimal;
	
	@Column(name="LOVE_COLOR")
	private String loveColor;
	
	public String getLoveFood() {
		return loveFood;
	}
	public void setLoveFood(String loveFood) {
		this.loveFood = loveFood;
	}
	public String getLoveAnimal() {
		return loveAnimal;
	}
	public void setLoveAnimal(String loveAnimal) {
		this.loveAnimal = loveAnimal;
	}
	public String getLoveColor() {
		return loveColor;
	}
	public void setLoveColor(String loveColor) {
		this.loveColor = loveColor;
	}
	
}
