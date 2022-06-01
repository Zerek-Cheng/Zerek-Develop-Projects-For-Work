// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import java.util.Iterator;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import think.rpgitems.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import think.rpgitems.item.RPGItem;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.Listener;

public class bH implements Listener
{
    @EventHandler(ignoreCancelled = true)
    void a(final PlayerInteractEntityEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.RIGHTCLICKENTITY, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e);
                    }
                });
                bI.a(rItem, aM.RIGHTCLICK, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.b(e.getPlayer(), (PlayerInteractEvent)null);
                    }
                });
                bI.a(rItem, aM.INTERACT, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), (PlayerInteractEvent)null);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final EnchantItemEvent e) {
        final Player player = e.getEnchanter();
        bz.a(bg.a(player), player, new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(player, e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final EntityTameEvent e) {
        if (e.getOwner() instanceof Player) {
            final Player player = (Player)e.getOwner();
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
                    bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.a(player, e);
                        }
                    });
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final EntityCombustEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
                    bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.a(player, e);
                        }
                    });
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerTeleportEvent e) {
        final Player player = e.getPlayer();
        bz.a(bg.a(player), player, new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(player, e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerItemBreakEvent e) {
        final Player player = e.getPlayer();
        bz.a(bg.a(player), player, new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(player, e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerBucketEmptyEvent e) {
        final Player player = e.getPlayer();
        bz.a(bg.b(player), player, new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(player, e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerBucketFillEvent e) {
        final Player player = e.getPlayer();
        bz.a(bg.b(player), player, new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final HorseJumpEvent e) {
        final Entity entity = e.getEntity().getPassenger();
        if (entity instanceof Player) {
            final Player player = (Player)entity;
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
                    bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.a(player, e);
                        }
                    });
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
                    bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.a(player, e);
                        }
                    });
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerEditBookEvent e) {
        if (!e.isSigning()) {
            return;
        }
        bz.a(bg.b(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerUnleashEntityEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerLeashEntityEvent e) {
        bz.a(bg.b(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerBedEnterEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.BED, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.c(e.getPlayer(), e.getBed());
                    }
                });
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.d(e.getPlayer(), e.getBed());
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerBedLeaveEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.BED, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.c(e.getPlayer(), e.getBed());
                    }
                });
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.e(e.getPlayer(), e.getBed());
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final BlockPlaceEvent e) {
        bz.a(bg.b(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.b(e.getPlayer(), e.getBlock());
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final BlockBreakEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e.getBlock());
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerRespawnEvent e) {
        Bukkit.getScheduler().runTaskLater((org.bukkit.plugin.Plugin)Plugin.c, (Runnable)new Runnable() {
            @Override
            public void run() {
                bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
                    @Override
                    public void a(final RPGItem rItem) {
                        bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                            @Override
                            public void a(final bI pow) {
                                pow.b(e.getPlayer(), e.getRespawnLocation());
                            }
                        });
                    }
                });
            }
        }, 1L);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerItemConsumeEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e.getItem());
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerLevelChangeEvent e) {
        if (e.getOldLevel() < e.getNewLevel()) {
            bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
                    bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.a(e.getPlayer(), e.getOldLevel(), e.getNewLevel());
                        }
                    });
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final ThunderChangeEvent e) {
        if (e.toThunderState()) {
            for (final Player player : bg.c()) {
                bz.a(bg.a(player), player, new bo<RPGItem>() {
                    @Override
                    public void a(final RPGItem rItem) {
                        bI.a(rItem, aM.WEATHER, new bo<bI>() {
                            @Override
                            public void a(final bI pow) {
                                pow.a(player, aU.THUNDER);
                            }
                        });
                        bI.a(rItem, aM.THUNDER, new bo<bI>() {
                            @Override
                            public void a(final bI pow) {
                                pow.k(player);
                            }
                        });
                    }
                });
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            for (final Player player : bg.c()) {
                bz.a(bg.a(player), player, new bo<RPGItem>() {
                    @Override
                    public void a(final RPGItem rItem) {
                        bI.a(rItem, aM.WEATHER, new bo<bI>() {
                            @Override
                            public void a(final bI pow) {
                                pow.a(player, aU.STORM);
                            }
                        });
                        bI.a(rItem, aM.STORM, new bo<bI>() {
                            @Override
                            public void a(final bI pow) {
                                pow.k(player);
                            }
                        });
                    }
                });
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final StructureGrowEvent e) {
        if (e.isFromBonemeal() && e.getPlayer() != null) {
            bz.a(bg.b(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
                    bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.a(e.getPlayer(), e.getLocation(), e.getSpecies());
                        }
                    });
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerFishEvent e) {
        switch (bH$20.a[e.getState().ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            default: {
                return;
            }
        }
        bz.a(bg.b(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.getByEvent((Event)e), new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.a(e.getPlayer(), e);
                    }
                });
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerToggleSneakEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.SNEAK, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.d(e.getPlayer());
                    }
                });
                if (e.isSneaking()) {
                    bI.a(rItem, aM.SNEAKON, new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.e(e.getPlayer());
                        }
                    });
                }
                else {
                    bI.a(rItem, aM.SNEAKOFF, new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.f(e.getPlayer());
                        }
                    });
                }
            }
        });
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void a(final PlayerToggleSprintEvent e) {
        bz.a(bg.a(e.getPlayer()), e.getPlayer(), new bo<RPGItem>() {
            @Override
            public void a(final RPGItem rItem) {
                bI.a(rItem, aM.SPRINT, new bo<bI>() {
                    @Override
                    public void a(final bI pow) {
                        pow.g(e.getPlayer());
                    }
                });
                if (e.isSprinting()) {
                    bI.a(rItem, aM.SPRINTON, new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.h(e.getPlayer());
                        }
                    });
                }
                else {
                    bI.a(rItem, aM.SPRINTOFF, new bo<bI>() {
                        @Override
                        public void a(final bI pow) {
                            pow.i(e.getPlayer());
                        }
                    });
                }
            }
        });
    }
}
