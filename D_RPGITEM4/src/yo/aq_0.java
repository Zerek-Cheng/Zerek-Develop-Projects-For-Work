/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 *  org.apache.commons.lang.Validate
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.configuration.Configuration
 *  org.bukkit.configuration.ConfigurationOptions
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.MemoryConfigurationOptions
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.configuration.file.YamlConstructor
 *  org.bukkit.configuration.file.YamlRepresenter
 *  org.yaml.snakeyaml.DumperOptions
 *  org.yaml.snakeyaml.DumperOptions$FlowStyle
 *  org.yaml.snakeyaml.Yaml
 *  org.yaml.snakeyaml.constructor.BaseConstructor
 *  org.yaml.snakeyaml.error.YAMLException
 *  org.yaml.snakeyaml.representer.Representer
 */
package yo;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

public class aq_0
extends YamlConfiguration {
    private final DumperOptions a = new DumperOptions();
    private final Representer b = new YamlRepresenter();
    private final Yaml c = new Yaml((BaseConstructor)new YamlConstructor(), this.b, this.a);
    private File d;
    private String e = null;
    private Class<?> f = aq_0.class;
    private static Charset g = Charset.forName("UTF-8");

    private aq_0() {
    }

    public aq_0(File file) {
        this(file, "/" + file.getName());
    }

    public aq_0(File file, String templateName) {
        this();
        this.d = file;
        this.e = templateName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void f() {
        InputStream istr = null;
        OutputStream ostr = null;
        try {
            istr = this.f.getResourceAsStream(this.e);
            if (istr == null) {
                return;
            }
            ostr = new FileOutputStream(this.d);
            byte[] buffer = new byte[1024];
            int length = 0;
            length = istr.read(buffer);
            while (length > 0) {
                ostr.write(buffer, 0, length);
                length = istr.read(buffer);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("\u5199\u5165\u5931\u8d25: " + this.d.toString());
        }
        finally {
            try {
                if (istr != null) {
                    istr.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (ostr != null) {
                    ostr.close();
                }
            }
            catch (IOException ex) {
                System.out.println("\u65e0\u6cd5\u5173\u95ed: " + this.d.toString());
            }
        }
    }

    public void set(String path, Object value) {
        super.set(path, value);
    }

    public aq_0 a(String path, Location loc) {
        this.set(path + ".world", loc.getWorld().getName());
        this.set(path + ".x", loc.getX());
        this.set(path + ".y", loc.getY());
        this.set(path + ".z", loc.getZ());
        this.set(path + ".w", Float.valueOf(loc.getYaw()));
        this.set(path + ".p", Float.valueOf(loc.getPitch()));
        return this;
    }

    public Location a(String path) {
        World world = Bukkit.getWorld((String)this.getString(path + ".world"));
        if (world != null) {
            double x = this.getDouble(path + ".x");
            double y = this.getDouble(path + ".y");
            double z = this.getDouble(path + ".z");
            float w = (float)this.getDouble(path + ".w");
            float p = (float)this.getDouble(path + ".p");
            return new Location(world, x, y, z, w, p);
        }
        return null;
    }

    public String saveToString() {
        this.a.setIndent(this.options().indent());
        this.a.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.b.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        String header = this.buildHeader();
        String dump = this.c.dump((Object)this.getValues(false));
        if (dump.equals("{}\n")) {
            dump = "";
        }
        return header + dump;
    }

    public void loadFromString(String contents) throws InvalidConfigurationException {
        Map input;
        Validate.notNull((Object)contents, (String)"Contents cannot be null");
        try {
            input = (Map)this.c.load(contents);
        }
        catch (YAMLException e2) {
            throw new InvalidConfigurationException((Throwable)e2);
        }
        catch (ClassCastException e3) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }
        String header = this.parseHeader(contents);
        if (header.length() > 0) {
            this.options().header(header);
        }
        if (input != null) {
            this.convertMapsToSections(input, (ConfigurationSection)this);
        }
    }

    public aq_0 b(String resourceFileName) {
        aq_0 defaultConfig = new aq_0();
        try {
            defaultConfig.load(this.getClass().getResourceAsStream(resourceFileName));
        }
        catch (Exception exception) {
            // empty catch block
        }
        return this.a((Configuration)defaultConfig);
    }

    public aq_0 a(Configuration newConfig) {
        double version;
        if (newConfig != null && (version = newConfig.getDouble("version")) > this.getDouble("version")) {
            Map defaultValues = newConfig.getValues(true);
            Map nowValues = this.getValues(true);
            for (Map.Entry entry : defaultValues.entrySet()) {
                if (nowValues.containsKey(entry.getKey())) continue;
                this.set((String)entry.getKey(), entry.getValue());
            }
            this.set("version", version);
            this.c();
        }
        return this;
    }

    public synchronized Map<String, Object> a() {
        return this.map;
    }

    public synchronized void b() throws IOException {
        this.save(this.d);
    }

    public synchronized void c() {
        try {
            this.b();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save(File file) throws IOException {
        Validate.notNull((Object)file, (String)"File cannot be null");
        Files.createParentDirs((File)file);
        String data = this.saveToString();
        FileOutputStream fos = new FileOutputStream(file);
        try {
            OutputStreamWriter writer = new OutputStreamWriter((OutputStream)fos, g);
            try {
                writer.write(data);
            }
            finally {
                writer.close();
            }
        }
        finally {
            fos.close();
        }
    }

    public void save(String file) throws IOException {
        Validate.notNull((Object)file, (String)"File cannot be null");
        this.save(new File(file));
    }

    public synchronized void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull((Object)file, (String)"File cannot be null");
        this.d = file;
        try {
            Files.createParentDirs((File)this.d);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (!this.d.exists() && this.e != null) {
            this.f();
        }
        try {
            this.load(new FileInputStream(file));
            if (this.e != null) {
                this.b(this.e);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void load(InputStream stream) throws IOException, InvalidConfigurationException {
        StringBuilder builder;
        Validate.notNull((Object)stream, (String)"Stream cannot be null");
        InputStreamReader reader = new InputStreamReader(stream, g);
        builder = new StringBuilder();
        BufferedReader input = new BufferedReader(reader);
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }
        finally {
            input.close();
        }
        this.loadFromString(builder.toString());
    }

    public aq_0 d() {
        try {
            this.load(this.d);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull((Object)file, (String)"File cannot be null");
        this.load(new File(file));
    }

    public aq_0 c(String templateName) {
        this.e = templateName;
        return this;
    }

    public aq_0 a(String templateName, Class<?> resClass) {
        this.e = templateName;
        this.f = resClass;
        return this;
    }

    public File e() {
        return this.d;
    }
}

