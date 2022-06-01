/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.gmail.filoghost.chestcommands.nms;

import java.lang.reflect.Method;
import java.util.Collection;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.filoghost.chestcommands.util.Utils;

public class AttributeRemover {
	
	private static boolean useItemFlags;
	private static boolean useReflection;
	
	// Reflection stuff
	private static Class<?> nbtTagCompoundClass;
	private static Class<?> nbtTagListClass;
	private static Class<?> nmsItemstackClass;
	private static Method asNmsCopyMethod;
	private static Method asCraftMirrorMethod;
	private static Method hasTagMethod;
	private static Method getTagMethod;
	private static Method setTagMethod;
	private static Method nbtSetMethod;
	
	
	public static void setup() throws Throwable {
		if (Utils.isClassLoaded("org.bukkit.inventory.ItemFlag")) {
			// We can use the new Bukkit API (1.8.3+)
			useItemFlags = true;
			
		} else {
			// Try to get the NMS methods and classes
			nbtTagCompoundClass = getNMSClass("NBTTagCompound");
			nbtTagListClass = getNMSClass("NBTTagList");
			nmsItemstackClass = getNMSClass("ItemStack");
			
			asNmsCopyMethod = getOBCClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
			asCraftMirrorMethod = getOBCClass("inventory.CraftItemStack").getMethod("asCraftMirror", nmsItemstackClass);
			
			hasTagMethod = nmsItemstackClass.getMethod("hasTag");
			getTagMethod = nmsItemstackClass.getMethod("getTag");
			setTagMethod = nmsItemstackClass.getMethod("setTag", nbtTagCompoundClass);
			
			nbtSetMethod = nbtTagCompoundClass.getMethod("set", String.class, getNMSClass("NBTBase"));
			
			useReflection = true;
		}
	}
	
	
	private static Class<?> getNMSClass(String name) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + Utils.getNMSVersion() + "." + name);
	}
	
	private static Class<?> getOBCClass(String name) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit." + Utils.getNMSVersion() + "." + name);
	}
	
	public static ItemStack hideAttributes(ItemStack item) {
		if (item == null) {
			return null;
		}
		
		if (useItemFlags) {
			ItemMeta meta = item.getItemMeta();
			if (isNullOrEmpty(meta.getItemFlags())) {
				// Add them only if necessary
				meta.addItemFlags(ItemFlag.values());
				item.setItemMeta(meta);
			}
			return item;
			
		} else if (useReflection) {
			try {
				
				Object nmsItemstack = asNmsCopyMethod.invoke(null, item);
				if (nmsItemstack == null) {
					return item;
				}
				
				Object nbtCompound;
				if ((Boolean) hasTagMethod.invoke(nmsItemstack)) {
					nbtCompound = getTagMethod.invoke(nmsItemstack);
				} else {
					nbtCompound = nbtTagCompoundClass.newInstance();
					setTagMethod.invoke(nmsItemstack, nbtCompound);
				}
				
				if (nbtCompound == null) {
					return item;
				}
				
				Object nbtList = nbtTagListClass.newInstance();
				nbtSetMethod.invoke(nbtCompound, "AttributeModifiers", nbtList);
				return (ItemStack) asCraftMirrorMethod.invoke(null, nmsItemstack);
				
			} catch (Throwable t) {
				// Ignore
			}
		}
		
		// On failure just return the item
		return item;
	}
	
	private static boolean isNullOrEmpty(Collection<?> coll) {
		return coll == null || coll.isEmpty();
	}
	
}
