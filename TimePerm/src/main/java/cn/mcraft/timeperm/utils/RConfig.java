package cn.mcraft.timeperm.utils;

import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

public class RConfig
        extends YamlConfiguration {
    private final DumperOptions yamlOptions = new DumperOptions();
    private final Representer yamlRepresenter = new YamlRepresenter();
    private final Yaml yaml = new Yaml((BaseConstructor) new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);
    private File configFile;
    private String templateName = null;
    private Class<?> resourceClass = RConfig.class;
    private static Charset UTF8 = Charset.forName("UTF-8");

    private RConfig() {
    }

    public RConfig(File file) {
        this(file, "/" + file.getName());
    }

    public RConfig(File file, String templateName) {
        this();
        this.configFile = file;
        this.templateName = templateName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void createFromTemplate() {
        InputStream istr = null;
        OutputStream ostr = null;
        try {
            istr = this.resourceClass.getResourceAsStream(this.templateName);
            if (istr == null) {
                return;
            }
            ostr = new FileOutputStream(this.configFile);
            byte[] buffer = new byte[1024];
            int length = 0;
            length = istr.read(buffer);
            while (length > 0) {
                ostr.write(buffer, 0, length);
                length = istr.read(buffer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("\u5199\u5165\u5931\u8d25: " + this.configFile.toString());
        } finally {
            try {
                if (istr != null) {
                    istr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (ostr != null) {
                    ostr.close();
                }
            } catch (IOException ex) {
                System.out.println("\u65e0\u6cd5\u5173\u95ed: " + this.configFile.toString());
            }
        }
    }

    public void set(String path, Object value) {
        super.set(path, value);
    }

    public RConfig setProperty(String path, Location loc) {
        this.set(path + ".world", loc.getWorld().getName());
        this.set(path + ".x", loc.getX());
        this.set(path + ".y", loc.getY());
        this.set(path + ".z", loc.getZ());
        this.set(path + ".w", Float.valueOf(loc.getYaw()));
        this.set(path + ".p", Float.valueOf(loc.getPitch()));
        return this;
    }

    public Location getProperty(String path) {
        World world = Bukkit.getWorld((String) this.getString(path + ".world"));
        if (world != null) {
            double x = this.getDouble(path + ".x");
            double y = this.getDouble(path + ".y");
            double z = this.getDouble(path + ".z");
            float w = (float) this.getDouble(path + ".w");
            float p = (float) this.getDouble(path + ".p");
            return new Location(world, x, y, z, w, p);
        }
        return null;
    }

    public String saveToString() {
        this.yamlOptions.setIndent(this.options().indent());
        this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        String header = this.buildHeader();
        String dump = this.yaml.dump((Object) this.getValues(false));
        if (dump.equals("{}\n")) {
            dump = "";
        }
        return header + dump;
    }

    public void loadFromString(String contents) throws InvalidConfigurationException {
        Map input;
        Validate.notNull((Object) contents, (String) "Contents cannot be null");
        try {
            input = (Map) this.yaml.load(contents);
        } catch (YAMLException e) {
            throw new InvalidConfigurationException((Throwable) e);
        } catch (ClassCastException e) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }
        String header = this.parseHeader(contents);
        if (header.length() > 0) {
            this.options().header(header);
        }
        if (input != null) {
            this.convertMapsToSections(input, (ConfigurationSection) this);
        }
    }

    public RConfig updateConfig(String resourceFileName) {
        RConfig defaultConfig = new RConfig();
        try {
            defaultConfig.load(this.getClass().getResourceAsStream(resourceFileName));
        } catch (Exception ex) {
            // empty catch block
        }
        return this.updateConfig((Configuration) defaultConfig);
    }

    public RConfig updateConfig(Configuration newConfig) {
        if (newConfig != null) {
            double version = newConfig.getDouble("version");
            if (version > getDouble("version")) {
                Map<String, Object> defaultValues = newConfig.getValues(true);
                Map<String, Object> nowValues = getValues(true);
                for (Map.Entry<String, Object> entry : defaultValues.entrySet()) {
                    if (!nowValues.containsKey(entry.getKey())) {
                        set((String) entry.getKey(), entry.getValue());
                    }
                }
                set("version", Double.valueOf(version));
                forceSave();
            }
        }
        return this;
    }

    public synchronized Map<String, Object> getMap() {
        return this.map;
    }

    public synchronized void save() throws IOException {
        this.save(this.configFile);
    }

    public synchronized void forceSave() {
        try {
            this.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save(File file) throws IOException {
        Validate.notNull((Object) file, (String) "File cannot be null");
        Files.createParentDirs((File) file);
        String data = this.saveToString();
        FileOutputStream fos = new FileOutputStream(file);
        try {
            OutputStreamWriter writer = new OutputStreamWriter((OutputStream) fos, UTF8);
            try {
                writer.write(data);
            } finally {
                writer.close();
            }
        } finally {
            fos.close();
        }
    }

    public void save(String file) throws IOException {
        Validate.notNull((Object) file, (String) "File cannot be null");
        this.save(new File(file));
    }

    public synchronized void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull((Object) file, (String) "File cannot be null");
        this.configFile = file;
        try {
            Files.createParentDirs((File) this.configFile);
        } catch (IOException ex) {
            // empty catch block
        }
        if (!this.configFile.exists() && this.templateName != null) {
            this.createFromTemplate();
        }
        try {
            this.load(new FileInputStream(file));
            if (this.templateName != null) {
                this.updateConfig(this.templateName);
            }
        } catch (FileNotFoundException e) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void load(InputStream stream) throws IOException, InvalidConfigurationException {
        StringBuilder builder;
        Validate.notNull((Object) stream, (String) "Stream cannot be null");
        InputStreamReader reader = new InputStreamReader(stream, UTF8);
        builder = new StringBuilder();
        BufferedReader input = new BufferedReader(reader);
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } finally {
            input.close();
        }
        this.loadFromString(builder.toString());
    }

    public RConfig load() {
        try {
            this.load(this.configFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull((Object) file, (String) "File cannot be null");
        this.load(new File(file));
    }

    public RConfig setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public RConfig setTemplateName(String templateName, Class<?> resClass) {
        this.templateName = templateName;
        this.resourceClass = resClass;
        return this;
    }

    public File getFile() {
        return this.configFile;
    }
}

