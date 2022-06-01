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
package com.gmail.filoghost.chestcommands.task;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.gmail.filoghost.chestcommands.ChestCommands;
import com.gmail.filoghost.chestcommands.util.ErrorLogger;
import com.gmail.filoghost.chestcommands.util.Utils;

public class ErrorLoggerTask implements Runnable {

	private ErrorLogger errorLogger;

	public ErrorLoggerTask(ErrorLogger errorLogger) {
		this.errorLogger = errorLogger;
	}

	@Override
	public void run() {
		
		List<String> lines = Utils.newArrayList();
		
		lines.add(" ");
		lines.add(ChatColor.RED + "#------------------- Chest Commands Errors -------------------#");
		int count = 1;
		for (String error : errorLogger.getErrors()) {
			lines.add(ChatColor.GRAY + "" + (count++) + ") " + ChatColor.WHITE + error);
		}
		lines.add(ChatColor.RED + "#-------------------------------------------------------------#");
		
		String output = Utils.join(lines, "\n");
		
		if (ChestCommands.getSettings().use_console_colors) {
			Bukkit.getConsoleSender().sendMessage(output);
		} else {
			System.out.println(ChatColor.stripColor(output));
		}
	}
	
}
