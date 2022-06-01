/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.apihelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.libs.apihelper.API;
import su.nightexpress.divineitems.libs.apihelper.RegisteredAPI;
import su.nightexpress.divineitems.libs.apihelper.exception.APIRegistrationException;

public class APIManager {
    private static final Map<API, RegisteredAPI> HOST_MAP = new HashMap<API, RegisteredAPI>();
    private static final Map<Class<? extends API>, Set<Plugin>> PENDING_API_CLASSES = new HashMap<Class<? extends API>, Set<Plugin>>();
    private static final Logger LOGGER = Logger.getLogger("APIManager");

    public static RegisteredAPI registerAPI(API aPI) {
        if (HOST_MAP.containsKey(aPI)) {
            throw new APIRegistrationException("API for '" + aPI.getClass().getName() + "' is already registered");
        }
        RegisteredAPI registeredAPI = new RegisteredAPI(aPI);
        HOST_MAP.put(aPI, registeredAPI);
        aPI.load();
        LOGGER.fine("'" + aPI.getClass().getName() + "' registered as new API");
        return registeredAPI;
    }

    public static RegisteredAPI registerAPI(API aPI, Plugin plugin) {
        APIManager.validatePlugin(plugin);
        APIManager.registerAPI(aPI);
        return APIManager.registerAPIHost(aPI, plugin);
    }

    public static API registerEvents(API aPI, Listener listener) {
        if (!HOST_MAP.containsKey(aPI)) {
            throw new APIRegistrationException("API for '" + aPI.getClass().getName() + "' is not registered");
        }
        RegisteredAPI registeredAPI = HOST_MAP.get(aPI);
        if (registeredAPI.eventsRegistered) {
            return aPI;
        }
        Bukkit.getPluginManager().registerEvents(listener, registeredAPI.getNextHost());
        registeredAPI.eventsRegistered = true;
        return aPI;
    }

    private static void initAPI(API aPI) {
        if (!HOST_MAP.containsKey(aPI)) {
            throw new APIRegistrationException("API for '" + aPI.getClass().getName() + "' is not registered");
        }
        RegisteredAPI registeredAPI = HOST_MAP.get(aPI);
        registeredAPI.init();
    }

    public static void initAPI(Class<? extends API> class_) {
        API aPI = null;
        for (API aPI2 : HOST_MAP.keySet()) {
            if (!aPI2.getClass().equals(class_)) continue;
            aPI = aPI2;
            break;
        }
        if (aPI == null) {
            if (PENDING_API_CLASSES.containsKey(class_)) {
                LOGGER.info("API class '" + class_.getName() + "' is not yet initialized. Creating new instance.");
                try {
                    aPI = class_.newInstance();
                    APIManager.registerAPI(aPI);
                    for (Plugin plugin : PENDING_API_CLASSES.get(class_)) {
                        if (plugin == null) continue;
                        APIManager.registerAPIHost(aPI, plugin);
                    }
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    LOGGER.warning("API class '" + class_.getName() + "' is missing valid constructor");
                }
                PENDING_API_CLASSES.remove(class_);
            } else {
                throw new APIRegistrationException("API for class '" + class_.getName() + "' is not registered");
            }
        }
        APIManager.initAPI(aPI);
    }

    private static void disableAPI(API aPI) {
        if (!HOST_MAP.containsKey(aPI)) {
            return;
        }
        RegisteredAPI registeredAPI = HOST_MAP.get(aPI);
        registeredAPI.disable();
        HOST_MAP.remove(aPI);
    }

    public static void disableAPI(Class<? extends API> class_) {
        API aPI = null;
        for (API aPI2 : HOST_MAP.keySet()) {
            if (!aPI2.getClass().equals(class_)) continue;
            aPI = aPI2;
            break;
        }
        APIManager.disableAPI(aPI);
    }

    public static void require(Class<? extends API> class_, Plugin plugin) {
        try {
            if (plugin == null) {
                throw new APIRegistrationException();
            }
            APIManager.registerAPIHost(class_, plugin);
        }
        catch (APIRegistrationException aPIRegistrationException) {
            if (PENDING_API_CLASSES.containsKey(class_)) {
                PENDING_API_CLASSES.get(class_).add(plugin);
            }
            HashSet<Plugin> hashSet = new HashSet<Plugin>();
            hashSet.add(plugin);
            PENDING_API_CLASSES.put(class_, hashSet);
        }
    }

    private static RegisteredAPI registerAPIHost(API aPI, Plugin plugin) {
        APIManager.validatePlugin(plugin);
        if (!HOST_MAP.containsKey(aPI)) {
            throw new APIRegistrationException("API for '" + aPI.getClass().getName() + "' is not registered");
        }
        RegisteredAPI registeredAPI = HOST_MAP.get(aPI);
        registeredAPI.registerHost(plugin);
        LOGGER.fine("'" + plugin.getName() + "' registered as Host for '" + aPI + "'");
        return registeredAPI;
    }

    public static RegisteredAPI registerAPIHost(Class<? extends API> class_, Plugin plugin) {
        APIManager.validatePlugin(plugin);
        API aPI = null;
        for (API aPI2 : HOST_MAP.keySet()) {
            if (!aPI2.getClass().equals(class_)) continue;
            aPI = aPI2;
            break;
        }
        if (aPI == null) {
            throw new APIRegistrationException("API for class '" + class_.getName() + "' is not registered");
        }
        return APIManager.registerAPIHost(aPI, plugin);
    }

    public static Plugin getAPIHost(API aPI) {
        if (!HOST_MAP.containsKey(aPI)) {
            throw new APIRegistrationException("API for '" + aPI.getClass().getName() + "' is not registered");
        }
        return HOST_MAP.get(aPI).getNextHost();
    }

    private static void validatePlugin(Plugin plugin) {
        if (plugin instanceof API) {
            throw new IllegalArgumentException("Plugin must not implement API");
        }
    }
}

