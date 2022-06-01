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
package com.gmail.filoghost.chestcommands.internal.icon.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.filoghost.chestcommands.bridge.EconomyBridge;
import com.gmail.filoghost.chestcommands.internal.icon.IconCommand;
import com.gmail.filoghost.chestcommands.util.Utils;

public class GiveMoneyIconCommand extends IconCommand {
	
	private double moneyToGive;
	private String errorMessage;
	
	public GiveMoneyIconCommand(String command) {
		super(command);
		
		if (!Utils.isValidPositiveDouble(command)) {
			errorMessage = ChatColor.RED + "Invalid money amount: " + command;
			return;
		}
		
		moneyToGive = Double.parseDouble(command);
	}

	@Override
	public void execute(Player player) {
		if (errorMessage != null) {
			player.sendMessage(errorMessage);
			return;
		}
		
		if (EconomyBridge.hasValidEconomy()) {
			EconomyBridge.giveMoney(player, moneyToGive);
		} else {
			player.sendMessage(ChatColor.RED + "Vault with a compatible economy plugin not found. Please inform the staff.");
		}
	}

}
