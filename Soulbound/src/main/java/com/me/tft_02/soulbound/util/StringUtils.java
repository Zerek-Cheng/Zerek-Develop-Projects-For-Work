package com.me.tft_02.soulbound.util;

import java.util.List;

public class StringUtils {

	public static int getIndexOfSoulbound(final List<String> itemLore) {
		final int index = -1;

		for (final String line : itemLore) {
			if (line.contains(Misc.SOULBOUND_TAG)) {
				return itemLore.indexOf(line);
			}
		}
		return index;
	}
}
