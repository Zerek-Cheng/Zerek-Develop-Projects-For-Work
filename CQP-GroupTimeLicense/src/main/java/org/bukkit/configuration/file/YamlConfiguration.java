/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.Validate
 *  org.bukkit.Bukkit
 *  org.yaml.snakeyaml.DumperOptions
 *  org.yaml.snakeyaml.DumperOptions$FlowStyle
 *  org.yaml.snakeyaml.Yaml
 *  org.yaml.snakeyaml.constructor.BaseConstructor
 *  org.yaml.snakeyaml.error.YAMLException
 *  org.yaml.snakeyaml.representer.Representer
 */
package org.bukkit.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfigurationOptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

public class YamlConfiguration
extends FileConfiguration {
    protected static final String COMMENT_PREFIX = "# ";
    protected static final String BLANK_CONFIG = "{}\n";
    private final DumperOptions yamlOptions = new DumperOptions();
    private final Representer yamlRepresenter = new YamlRepresenter();
    private final Yaml yaml = new Yaml((BaseConstructor)new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);

    @Override
    public String saveToString() {
        this.yamlOptions.setIndent(this.options().indent());
        this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yamlOptions.setAllowUnicode(SYSTEM_UTF);
        this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        String header = this.buildHeader();
        String dump = this.yaml.dump(this.getValues(false));
        if (dump.equals(BLANK_CONFIG)) {
            dump = "";
        }
        return header + dump;
    }

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException {
        Map input;
        Validate.notNull((Object)contents, (String)"Contents cannot be null");
        try {
            input = (Map)this.yaml.load(contents);
        }
        catch (YAMLException e) {
            throw new InvalidConfigurationException((Throwable)e);
        }
        catch (ClassCastException e) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }
        String header = this.parseHeader(contents);
        if (header.length() > 0) {
            this.options().header(header);
        }
        if (input != null) {
            this.convertMapsToSections(input, this);
        }
    }

    protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof Map) {
                this.convertMapsToSections((Map)value, section.createSection(key));
                continue;
            }
            section.set(key, value);
        }
    }

    protected String parseHeader(String input) {
        String[] lines = input.split("\r?\n", -1);
        StringBuilder result = new StringBuilder();
        boolean readingHeader = true;
        boolean foundHeader = false;
        for (int i = 0; i < lines.length && readingHeader; ++i) {
            String line = lines[i];
            if (line.startsWith(COMMENT_PREFIX)) {
                if (i > 0) {
                    result.append("\n");
                }
                if (line.length() > COMMENT_PREFIX.length()) {
                    result.append(line.substring(COMMENT_PREFIX.length()));
                }
                foundHeader = true;
                continue;
            }
            if (foundHeader && line.length() == 0) {
                result.append("\n");
                continue;
            }
            if (!foundHeader) continue;
            readingHeader = false;
        }
        return result.toString();
    }

    @Override
    protected String buildHeader() {
        FileConfiguration filedefaults;
        Configuration def;
        String defaultsHeader;
        String header = this.options().header();
        if (this.options().copyHeader() && (def = this.getDefaults()) != null && def instanceof FileConfiguration && (defaultsHeader = (filedefaults = (FileConfiguration)def).buildHeader()) != null && defaultsHeader.length() > 0) {
            return defaultsHeader;
        }
        if (header == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        String[] lines = header.split("\r?\n", -1);
        boolean startedHeader = false;
        for (int i = lines.length - 1; i >= 0; --i) {
            builder.insert(0, "\n");
            if (!startedHeader && lines[i].length() == 0) continue;
            builder.insert(0, lines[i]);
            builder.insert(0, COMMENT_PREFIX);
            startedHeader = true;
        }
        return builder.toString();
    }

    @Override
    public YamlConfigurationOptions options() {
        if (this.options == null) {
            this.options = new YamlConfigurationOptions(this);
        }
        return (YamlConfigurationOptions)this.options;
    }

    public static YamlConfiguration loadConfiguration(File file) {
        Validate.notNull((Object)file, (String)"File cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        }
        catch (FileNotFoundException ex) {
        }
        catch (IOException ex) {
        }
        catch (InvalidConfigurationException ex) {
        }
        return config;
    }

    @Deprecated
    public static YamlConfiguration loadConfiguration(InputStream stream) {
        Validate.notNull((Object)stream, (String)"Stream cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(stream);
        }
        catch (IOException ex) {
        }
        catch (InvalidConfigurationException ex) {
        }
        return config;
    }

    public static YamlConfiguration loadConfiguration(Reader reader) {
        Validate.notNull((Object)reader, (String)"Stream cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(reader);
        }
        catch (IOException ex) {
        }
        catch (InvalidConfigurationException ex) {
        }
        return config;
    }
}

