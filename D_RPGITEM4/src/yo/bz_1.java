/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package yo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.ao_0;
import yo.as_0;
import yo.ba_0;
import yo.bo_0;
import yo.bp_0;
import yo.by_0;

public class bz_1 {
    public final String a;
    public final List<RPGItem> b;
    public final HashMap<Integer, a> c;
    private final HashMap<List<RPGItem>, a> d;

    public bz_1(String name, List<RPGItem> needs, List<a> parts) {
        this.a = name;
        this.b = needs;
        this.c = new HashMap();
        this.d = new HashMap();
        int i = 0;
        for (a part : parts) {
            this.d.put(part.c, part);
            part.b = i;
            this.c.put(i, part);
            ++i;
        }
    }

    public a a(int id) {
        return this.c.get(id);
    }

    public static void a(ItemStack[] itemStacks, Player player, bo_0<RPGItem> callback) {
        ba_0 itemSetObj = bz_1.a(player, Arrays.asList(itemStacks));
        for (RPGItem rItem : itemSetObj.e) {
            if (!rItem.b(player)) continue;
            callback.a(rItem);
        }
    }

    public static ba_0 a(Player player, Collection<ItemStack> itemstacks) {
        ba_0 itemSetObj = new ba_0(itemstacks);
        for (ItemStack item : itemstacks) {
            RPGItem rItem = by_0.a(item);
            if (rItem == null) continue;
            itemSetObj.b.put(rItem, item);
        }
        for (RPGItem rItem : itemSetObj.b.keySet()) {
            if (rItem.n == null) continue;
            Collection<RPGItem> cols = itemSetObj.f.get(rItem.n);
            if (cols == null) {
                cols = new ArrayList<RPGItem>();
                itemSetObj.f.put(rItem.n, cols);
            }
            cols.add(rItem);
        }
        itemSetObj.d.addAll(itemSetObj.b.keySet());
        for (Map.Entry entry : itemSetObj.f.entrySet()) {
            if (entry.getKey() == null) continue;
            Collection<a> parts = ((bz_1)entry.getKey()).b((Collection)entry.getValue());
            for (a part : parts) {
                itemSetObj.c.addAll(part.d);
                for (RPGItem rItem : part.c) {
                    ItemStack item = itemSetObj.b.get(rItem);
                    if (item != null) {
                        as_0 meta = RPGItem.b(item);
                        meta.a(4, Integer.valueOf(part.b));
                        RPGItem.a(item, ao_0.a(player), meta);
                    }
                    itemSetObj.d.remove(rItem);
                }
            }
        }
        for (RPGItem rItem : itemSetObj.d) {
            ItemStack item = itemSetObj.b.get(rItem);
            as_0 meta = RPGItem.b(item);
            meta.a(4, Integer.valueOf(-1));
            RPGItem.a(item, ao_0.a(player), meta);
        }
        itemSetObj.e.addAll(itemSetObj.b.keySet());
        itemSetObj.e.addAll(itemSetObj.c);
        return itemSetObj;
    }

    private Collection<RPGItem> a(Collection<RPGItem> query) {
        Collection<List<RPGItem>> queryResult = bz_1.a(this.d.keySet(), query);
        ArrayList<RPGItem> result = new ArrayList<RPGItem>();
        for (List<RPGItem> next : queryResult) {
            a itemSetPart = this.d.get(next);
            if (itemSetPart == null) continue;
            result.addAll(itemSetPart.d);
        }
        return result;
    }

    private Collection<a> b(Collection<RPGItem> query) {
        Collection<List<RPGItem>> queryResult = bz_1.a(this.d.keySet(), query);
        ArrayList<a> result = new ArrayList<a>();
        for (List<RPGItem> next : queryResult) {
            a itemSetPart = this.d.get(next);
            if (itemSetPart == null) continue;
            result.add(itemSetPart);
        }
        return result;
    }

    private static <T> Collection<List<T>> a(Collection<List<T>> sets, Collection<T> query) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        ArrayList<List<T>> nextSets = new ArrayList<List<T>>();
        for (List<T> next : sets) {
            if (!query.containsAll(next)) continue;
            nextSets.add(next);
        }
        ArrayList<T> nextQuery = new ArrayList<T>(query);
        List<T> max = bz_1.b(nextSets);
        if (max != null) {
            result.add(max);
            nextQuery.removeAll(max);
            if (!nextQuery.isEmpty()) {
                Collection<List<T>> temp = bz_1.a(nextSets, nextQuery);
                result.addAll(temp);
            }
        }
        return result;
    }

    private static <T> List<T> a(List<List<T>> list) {
        TreeMap<Integer, List<T>> map = bz_1.a(list, new bp_0<List<T>, Integer>(){

            @Override
            public Integer a(List<T> param) {
                return param.size();
            }
        });
        if (map.isEmpty()) {
            return null;
        }
        return map.firstEntry().getValue();
    }

    private static <T> List<T> b(List<List<T>> list) {
        TreeMap<Integer, List<T>> map = bz_1.a(list, new bp_0<List<T>, Integer>(){

            @Override
            public Integer a(List<T> param) {
                return param.size();
            }
        });
        if (map.isEmpty()) {
            return null;
        }
        return map.lastEntry().getValue();
    }

    private static <T> TreeMap<Integer, List<T>> a(List<List<T>> list, bp_0<List<T>, Integer> callback) {
        TreeMap<Integer, List<T>> map = new TreeMap<Integer, List<T>>();
        for (List<T> next : list) {
            map.put(callback.a(next), next);
        }
        return map;
    }

    public static class a {
        public bz_1 a;
        public int b;
        public final List<RPGItem> c;
        public final List<RPGItem> d;
        public final List<String> e;

        public a(List<RPGItem> needs, List<RPGItem> resultsRPGItem) {
            this(needs, resultsRPGItem, new ArrayList<String>());
        }

        public a(List<RPGItem> needs, List<RPGItem> resultsRPGItem, List<String> resultsLore) {
            this.c = needs;
            this.d = resultsRPGItem;
            this.e = resultsLore;
        }

        public static a a(Map<?, ?> map) {
            RPGItem item;
            ArrayList<RPGItem> needs = new ArrayList<RPGItem>();
            ArrayList<RPGItem> results = new ArrayList<RPGItem>();
            Object o = map.get("needs");
            if (o != null && o instanceof List) {
                for (Object next : (List)o) {
                    item = by_0.c(next.toString());
                    if (item == null) {
                        Plugin.a.warning("RPGItem " + next.toString() + " is not exist");
                        return null;
                    }
                    needs.add(item);
                }
            }
            if (needs.isEmpty()) {
                return null;
            }
            o = map.get("resultItems");
            if (o != null && o instanceof List) {
                for (Object next : (List)o) {
                    item = by_0.c(next.toString());
                    if (item == null) {
                        Plugin.a.warning("RPGItem " + next.toString() + " is not exist");
                        return null;
                    }
                    results.add(item);
                }
            }
            o = map.get("resultLores");
            ArrayList<String> resultLores = new ArrayList<String>();
            if (o != null && o instanceof List) {
                for (Object next : (List)o) {
                    resultLores.add(next.toString());
                }
            }
            return new a(needs, results, resultLores);
        }
    }

}

