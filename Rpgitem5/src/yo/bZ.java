// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import think.rpgitems.Plugin;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import java.util.HashMap;
import think.rpgitems.item.RPGItem;
import java.util.List;

public class bz
{
    public final String a;
    public final List<RPGItem> b;
    public final HashMap<Integer, a> c;
    private final HashMap<List<RPGItem>, a> d;
    
    public bz(final String name, final List<RPGItem> needs, final List<a> parts) {
        this.a = name;
        this.b = needs;
        this.c = new HashMap<Integer, a>();
        this.d = new HashMap<List<RPGItem>, a>();
        int i = 0;
        for (final a part : parts) {
            this.d.put(part.c, part);
            part.b = i;
            this.c.put(i, part);
            ++i;
        }
    }
    
    public a a(final int id) {
        return this.c.get(id);
    }
    
    public static void a(final ItemStack[] itemStacks, final Player player, final bo<RPGItem> callback) {
        final bA itemSetObj = a(player, Arrays.asList(itemStacks));
        for (final RPGItem rItem : itemSetObj.e) {
            if (rItem.b(player)) {
                callback.a(rItem);
            }
        }
    }
    
    public static bA a(final Player player, final Collection<ItemStack> itemstacks) {
        final bA itemSetObj = new bA(itemstacks);
        for (final ItemStack item : itemstacks) {
            final RPGItem rItem = by.a(item);
            if (rItem != null) {
                itemSetObj.b.put(rItem, item);
            }
        }
        for (final RPGItem rItem2 : itemSetObj.b.keySet()) {
            if (rItem2.n != null) {
                Collection<RPGItem> cols = itemSetObj.f.get(rItem2.n);
                if (cols == null) {
                    cols = new ArrayList<RPGItem>();
                    itemSetObj.f.put(rItem2.n, cols);
                }
                cols.add(rItem2);
            }
        }
        itemSetObj.d.addAll(itemSetObj.b.keySet());
        for (final Map.Entry<bz, Collection<RPGItem>> entry : itemSetObj.f.entrySet()) {
            if (entry.getKey() != null) {
                final Collection<a> parts = entry.getKey().b(entry.getValue());
                for (final a part : parts) {
                    itemSetObj.c.addAll(part.d);
                    for (final RPGItem rItem3 : part.c) {
                        final ItemStack item2 = itemSetObj.b.get(rItem3);
                        if (item2 != null) {
                            final aS meta = RPGItem.b(item2);
                            ((X<Integer>)meta).a(4, Integer.valueOf(part.b));
                            RPGItem.a(item2, aO.a(player), meta);
                        }
                        itemSetObj.d.remove(rItem3);
                    }
                }
            }
        }
        for (final RPGItem rItem2 : itemSetObj.d) {
            final ItemStack item3 = itemSetObj.b.get(rItem2);
            final aS meta2 = RPGItem.b(item3);
            ((X<Integer>)meta2).a(4, Integer.valueOf(-1));
            RPGItem.a(item3, aO.a(player), meta2);
        }
        itemSetObj.e.addAll(itemSetObj.b.keySet());
        itemSetObj.e.addAll(itemSetObj.c);
        return itemSetObj;
    }
    
    private Collection<RPGItem> a(final Collection<RPGItem> query) {
        final Collection<List<RPGItem>> queryResult = a(this.d.keySet(), query);
        final List<RPGItem> result = new ArrayList<RPGItem>();
        for (final List<RPGItem> next : queryResult) {
            final a itemSetPart = this.d.get(next);
            if (itemSetPart != null) {
                result.addAll(itemSetPart.d);
            }
        }
        return result;
    }
    
    private Collection<a> b(final Collection<RPGItem> query) {
        final Collection<List<RPGItem>> queryResult = a(this.d.keySet(), query);
        final List<a> result = new ArrayList<a>();
        for (final List<RPGItem> next : queryResult) {
            final a itemSetPart = this.d.get(next);
            if (itemSetPart != null) {
                result.add(itemSetPart);
            }
        }
        return result;
    }
    
    private static <T> Collection<List<T>> a(final Collection<List<T>> sets, final Collection<T> query) {
        final List<List<T>> result = new ArrayList<List<T>>();
        final List<List<T>> nextSets = new ArrayList<List<T>>();
        for (final List<T> next : sets) {
            if (query.containsAll(next)) {
                nextSets.add(next);
            }
        }
        final List<T> nextQuery = new ArrayList<T>((Collection<? extends T>)query);
        final List<T> max = b(nextSets);
        if (max != null) {
            result.add(max);
            nextQuery.removeAll(max);
            if (!nextQuery.isEmpty()) {
                final Collection<List<T>> temp = (Collection<List<T>>)a((Collection<List<Object>>)nextSets, (Collection<Object>)nextQuery);
                result.addAll(temp);
            }
        }
        return result;
    }
    
    private static <T> List<T> a(final List<List<T>> list) {
        final TreeMap<Integer, List<T>> map = a(list, new bp<List<T>, Integer>() {
            @Override
            public Integer a(final List<T> param) {
                return param.size();
            }
        });
        if (map.isEmpty()) {
            return null;
        }
        return map.firstEntry().getValue();
    }
    
    private static <T> List<T> b(final List<List<T>> list) {
        final TreeMap<Integer, List<T>> map = a(list, new bp<List<T>, Integer>() {
            @Override
            public Integer a(final List<T> param) {
                return param.size();
            }
        });
        if (map.isEmpty()) {
            return null;
        }
        return map.lastEntry().getValue();
    }
    
    private static <T> TreeMap<Integer, List<T>> a(final List<List<T>> list, final bp<List<T>, Integer> callback) {
        final TreeMap<Integer, List<T>> map = new TreeMap<Integer, List<T>>();
        for (final List<T> next : list) {
            map.put(callback.a(next), next);
        }
        return map;
    }
    
    public static class a
    {
        public bz a;
        public int b;
        public final List<RPGItem> c;
        public final List<RPGItem> d;
        public final List<String> e;
        
        public a(final List<RPGItem> needs, final List<RPGItem> resultsRPGItem) {
            this(needs, resultsRPGItem, new ArrayList<String>());
        }
        
        public a(final List<RPGItem> needs, final List<RPGItem> resultsRPGItem, final List<String> resultsLore) {
            this.c = needs;
            this.d = resultsRPGItem;
            this.e = resultsLore;
        }
        
        public static a a(final Map<?, ?> map) {
            final List<RPGItem> needs = new ArrayList<RPGItem>();
            final List<RPGItem> results = new ArrayList<RPGItem>();
            Object o = map.get("needs");
            if (o != null && o instanceof List) {
                for (final Object next : (List)o) {
                    final RPGItem item = by.c(next.toString());
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
                for (final Object next : (List)o) {
                    final RPGItem item = by.c(next.toString());
                    if (item == null) {
                        Plugin.a.warning("RPGItem " + next.toString() + " is not exist");
                        return null;
                    }
                    results.add(item);
                }
            }
            o = map.get("resultLores");
            final List<String> resultLores = new ArrayList<String>();
            if (o != null && o instanceof List) {
                for (final Object next2 : (List)o) {
                    resultLores.add(next2.toString());
                }
            }
            return new a(needs, results, resultLores);
        }
    }
}
