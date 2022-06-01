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

import com.gmail.filoghost.chestcommands.config.yaml.PluginConfig;
import com.gmail.filoghost.chestcommands.config.yaml.SpecialConfig;

public class Settings extends SpecialConfig {
	
	public boolean use_console_colors = true;
	public String default_color__name = "&f";
	public String default_color__lore = "&7";
	public String multiple_commands_separator = ";";
	public boolean update_notifications = true;
	public int anti_click_spam_delay = 200;
	public boolean use_only_commands_without_args = true;

	public Settings(PluginConfig config) {
		super(config);
		setHeader("ChestCommands configuration file.\nTutorial: http://dev.bukkit.org/bukkit-plugins/chest-commands\n");
	}
	
}
