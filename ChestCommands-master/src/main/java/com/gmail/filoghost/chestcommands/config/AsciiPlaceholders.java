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
package com.gmail.filoghost.chestcommands.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

import com.gmail.filoghost.chestcommands.ChestCommands;
import com.gmail.filoghost.chestcommands.util.ErrorLogger;
import com.gmail.filoghost.chestcommands.util.Utils;

/**
 *  This is not a real YAML file ;)
 */
public class AsciiPlaceholders {

	private static Map<String, String> placeholders = Utils.newHashMap();
	
	
	public static void load(ErrorLogger errorLogger) throws IOException, Exception {
		
		placeholders.clear();
		File file = new File(ChestCommands.getInstance().getDataFolder(), "placeholders.yml");
		
		if (!file.exists()) {
			Utils.saveResourceSafe(ChestCommands.getInstance(), "placeholders.yml");
		}
		
		List<String> lines = Utils.readLines(file);
		for (String line : lines) {
			
			// Comment or empty line
			if (line.isEmpty() || line.startsWith("#"))  {
				continue;
			}
			
			if (!line.contains(":")) {
				errorLogger.addError("Unable to parse a line(" + line + ") from placeholders.yml: it must contain ':' to separate the placeholder and the replacement.");
				continue;
			}
				
			int indexOf = line.indexOf(':');
			String placeholder = unquote(line.substring(0, indexOf).trim());
			String replacement = Utils.addColors(StringEscapeUtils.unescapeJava(unquote(line.substring(indexOf + 1, line.length()).trim())));

			if (placeholder.length() == 0 || replacement.length() == 0) {
				errorLogger.addError("Unable to parse a line(" + line + ") from placeholders.yml: the placeholder and the replacement must have both at least 1 character.");
				continue;
			}
			
			if (placeholder.length() > 100) {
				errorLogger.addError("Unable to parse a line(" + line + ") from placeholders.yml: the placeholder cannot be longer than 100 characters.");
				continue;
			}
			
			placeholders.put(placeholder, replacement);
		}
	}
	
	public static List<String> placeholdersToSymbols(List<String> input) {
		if (input == null) return null;
		for (int i = 0; i < input.size(); i++) {
			input.set(i, placeholdersToSymbols(input.get(i)));
		}
		return input;
	}
	
	public static String placeholdersToSymbols(String input) {
		if (input == null) return null;
		for (Entry<String, String> entry : placeholders.entrySet()) {
			input = input.replace(entry.getKey(), entry.getValue());
		}
		return input;
	}
	
	public static String symbolsToPlaceholders(String input) {
		if (input == null) return null;
		for (Entry<String, String> entry : placeholders.entrySet()) {
			input = input.replace(entry.getValue(), entry.getKey());
		}
		return input;
	}
	
	private static String unquote(String input) {
		if (input.length() < 2) {
			// Cannot be quoted
			return input;
		}
		if (input.startsWith("'") && input.endsWith("'")) {
			return input.substring(1, input.length() - 1);
		} else if (input.startsWith("\"") && input.endsWith("\"")) {
			return input.substring(1, input.length() - 1);
		}
		
		return input;
	}
}
