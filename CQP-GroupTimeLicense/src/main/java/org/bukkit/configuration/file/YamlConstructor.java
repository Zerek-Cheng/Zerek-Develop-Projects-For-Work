/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.yaml.snakeyaml.constructor.SafeConstructor
 *  org.yaml.snakeyaml.constructor.SafeConstructor$ConstructYamlMap
 *  org.yaml.snakeyaml.error.YAMLException
 *  org.yaml.snakeyaml.nodes.Node
 *  org.yaml.snakeyaml.nodes.Tag
 */
package org.bukkit.configuration.file;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlConstructor
        extends SafeConstructor {
    public YamlConstructor() {
        this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
    }

    private class ConstructCustomObject
            extends ConstructYamlMap {
        private ConstructCustomObject() {
            super();
        }

        public Object construct(Node node) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected referential mapping structure. Node: " + (Object) node);
            }
            Map raw = (Map) super.construct(node);
            if (raw.containsKey("==")) {
                LinkedHashMap typed = new LinkedHashMap(raw.size());
                for (Object oentry : raw.entrySet()) {
                    Map.Entry entry = (Map.Entry) oentry;
                    typed.put(entry.getKey().toString(), entry.getValue());
                }
                try {
                    return ConfigurationSerialization.deserializeObject(typed);
                } catch (IllegalArgumentException ex) {
                    throw new YAMLException("Could not deserialize object", (Throwable) ex);
                }
            }
            return raw;
        }

        public void construct2ndStep(Node node, Object object) {
            throw new YAMLException("Unexpected referential mapping structure. Node: " + (Object) node);
        }
    }

}

