package me.oqwe.extralevels.util;

import java.util.Set;

import org.bukkit.attribute.AttributeModifier;

public class Lvl {

	private int lvl;
	private double chance;
	private String nameColor;
	
	private Set<AttributeModifier> attributeModifiers;

	public Lvl(int lvl, double chance, String nameColor, Set<AttributeModifier> attributeModifiers) {

		this.lvl = lvl;
		this.chance = chance;
		this.nameColor = nameColor;
		
		this.attributeModifiers = attributeModifiers;

	}

	public int getLvl() {
		return lvl;
	}

	public double getChance() {
		return chance;
	}

	public String getNameColor() {
		return nameColor;
	}
	
	public Set<AttributeModifier> getModifiers() {
		return attributeModifiers;
	}

}
