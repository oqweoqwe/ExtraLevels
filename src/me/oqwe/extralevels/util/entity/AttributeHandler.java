package me.oqwe.extralevels.util.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.oqwe.extralevels.util.Lvl;
import me.oqwe.extralevels.util.LvlUtil;

public class AttributeHandler {

	/*
	 * all possible attributes: GENERIC_ATTACK_DAMAGE GENERIC_ATTACK_KNOCKBACK
	 * GENERIC_ATTACK_SPEED GENERIC_FLYING_SPEED GENERIC_KNOCKBACK_RESISTANCE
	 * GENERIC_MAX_HEALTH GENERIC_MOVEMENT_SPEED
	 */

	private static final String[] attributes = { "attack_damage", "attack_knockback", "attack_speed", "flying_speed",
			"knockback_resistance", "max_health", "movement_speed" };
	private static final String[] operations = { "+", "-", "*", "/"};

	/**
	 * Sets the modifiers based on the level stored in the persistent data of the
	 * entity
	 * 
	 * @param entity
	 */
	public static void setupModifyers(LivingEntity entity) {

		Attributable attributable = (Attributable) entity;
		Lvl lvlObj = LvlUtil.getLvlObject(entity);

		for (var modifier : lvlObj.getModifiers()) {
			
			if (!(attributable.getAttribute(getAttribute(modifier)) == null)) {
				attributable.getAttribute(getAttribute(modifier)).addModifier(modifier);
			}

			// if health mod, heal to max
			EntityUtil.healToMax(entity);

		}

	}

	/**
	 * Gets the attribute that a modifier applies to
	 * 
	 * @param mod Modifier
	 * @return Attribute
	 */
	public static Attribute getAttribute(AttributeModifier mod) {
		return Attribute.valueOf(mod.getName().substring(mod.getName().indexOf(" ") + 1));
	}

	/**
	 * Removes all attribute modifiers added to this entity by ExtraLevels
	 * 
	 * @param entity
	 */
	public static void removeModifiers(Entity entity) {
		Attributable attributable = (Attributable) entity;
		// iterate over enum values
		for (var attribute : Attribute.values()) {
			// if entity doesn't have the attribute, skip this attribute
			if (attributable.getAttribute(attribute) != null) {
				// iterate over modifiers, if added by this plugin, remove it
				for (var modifier : attributable.getAttribute(attribute).getModifiers()) {
					if (modifier.getName().contains("extralevels")) {
						attributable.getAttribute(attribute).removeModifier(modifier);
					}
				}
			}
		}
	}

	public static Attribute translateAttribute(String configKey) {
		for (var attribute : attributes) {
			if (configKey.equalsIgnoreCase(attribute))
				return Attribute.valueOf("GENERIC_" + configKey.toUpperCase());
		}
		return null;
	}

	/**
	 * Gets an attributemodifier from a string from the config
	 * 
	 * @param value
	 * @return Corresponding attributemodifier, or null if configuration is
	 *         unrecognized
	 */
	public static AttributeModifier getAttributeModifier(Attribute attribute, String value) {
		char operationChar;
		try {
			operationChar = Arrays.stream(operations).filter(value::contains).findAny().get().toCharArray()[0];
		} catch (NoSuchElementException e) {
			return null;
		}

		AttributeModifier.Operation operation;
		double modifier;

		try {
			switch (operationChar) {
			case '+':
				operation = AttributeModifier.Operation.ADD_NUMBER;
				modifier = Double.parseDouble(value.substring(1));
				break;
			case '-':
				operation = AttributeModifier.Operation.ADD_NUMBER;
				modifier = Double.parseDouble("-" + value.substring(1));
				break;
			case '*':
				operation = AttributeModifier.Operation.MULTIPLY_SCALAR_1;
				modifier = Double.parseDouble(value.substring(1)) - 1; // remove 1 to counteract the added 1 from the
																		// multiply_scalar_1 operation
				break;
			case '/':
				if (Double.parseDouble(value.substring(1)) == 0)
					return null; // avoid division by zero resulting in modifier being infinity

				operation = AttributeModifier.Operation.MULTIPLY_SCALAR_1;
				// modifier calculated: multiply by 1/value (minus 1 to counteract the added 1 from the
				// multiply_scalar_1 operation)
				modifier = (1 / (Double.parseDouble(value.substring(1)))) - 1;
				break;
			default:
				operation = null;
				modifier = 0;
				break;
			}
		} catch (NumberFormatException e) {
			// cant parse value to double
			return null;
		}

		return new AttributeModifier("extralevels " + attribute.name().toString(), modifier, operation);
	}

}
