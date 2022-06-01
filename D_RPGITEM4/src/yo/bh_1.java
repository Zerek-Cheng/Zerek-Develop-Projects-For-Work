/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.TreeType
 *  org.bukkit.block.Block
 *  org.bukkit.entity.AnimalTamer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.enchantment.EnchantItemEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityTameEvent
 *  org.bukkit.event.entity.HorseJumpEvent
 *  org.bukkit.event.entity.PlayerLeashEntityEvent
 *  org.bukkit.event.player.PlayerBedEnterEvent
 *  org.bukkit.event.player.PlayerBedLeaveEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerEditBookEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerFishEvent$State
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerLevelChangeEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerToggleSneakEvent
 *  org.bukkit.event.player.PlayerToggleSprintEvent
 *  org.bukkit.event.player.PlayerUnleashEntityEvent
 *  org.bukkit.event.weather.ThunderChangeEvent
 *  org.bukkit.event.weather.WeatherChangeEvent
 *  org.bukkit.event.world.StructureGrowEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package yo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.am_0;
import yo.au_0;
import yo.bg_1;
import yo.bi_1;
import yo.bo_0;
import yo.bz_1;

public class bh_1
implements Listener {
    @EventHandler(ignoreCancelled=true)
    void a(final PlayerInteractEntityEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.RIGHTCLICKENTITY, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2);
                    }
                });
                bi_1.a(rItem, am_0.RIGHTCLICK, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.b(e2.getPlayer(), (PlayerInteractEvent)null);
                    }
                });
                bi_1.a(rItem, am_0.INTERACT, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), (PlayerInteractEvent)null);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final EnchantItemEvent e2) {
        final Player player = e2.getEnchanter();
        bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(player, e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final EntityTameEvent e2) {
        if (e2.getOwner() instanceof Player) {
            final Player player = (Player)e2.getOwner();
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.a(player, e2);
                        }
                    });
                }

            });
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final EntityCombustEvent e2) {
        if (e2.getEntity() instanceof Player) {
            final Player player = (Player)e2.getEntity();
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.a(player, e2);
                        }
                    });
                }

            });
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerTeleportEvent e2) {
        final Player player = e2.getPlayer();
        bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(player, e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerItemBreakEvent e2) {
        final Player player = e2.getPlayer();
        bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(player, e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerBucketEmptyEvent e2) {
        final Player player = e2.getPlayer();
        bz_1.a(bg_1.b(player), player, new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(player, e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerBucketFillEvent e2) {
        Player player = e2.getPlayer();
        bz_1.a(bg_1.b(player), player, new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final HorseJumpEvent e2) {
        Entity entity = e2.getEntity().getPassenger();
        if (entity instanceof Player) {
            final Player player = (Player)entity;
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.a(player, e2);
                        }
                    });
                }

            });
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final EntityRegainHealthEvent e2) {
        if (e2.getEntity() instanceof Player) {
            final Player player = (Player)e2.getEntity();
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.a(player, e2);
                        }
                    });
                }

            });
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerEditBookEvent e2) {
        if (!e2.isSigning()) {
            return;
        }
        bz_1.a(bg_1.b(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerUnleashEntityEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerLeashEntityEvent e2) {
        bz_1.a(bg_1.b(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerBedEnterEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.BED, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.c(e2.getPlayer(), e2.getBed());
                    }
                });
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.d(e2.getPlayer(), e2.getBed());
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerBedLeaveEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.BED, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.c(e2.getPlayer(), e2.getBed());
                    }
                });
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.e(e2.getPlayer(), e2.getBed());
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final BlockPlaceEvent e2) {
        bz_1.a(bg_1.b(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.b(e2.getPlayer(), e2.getBlock());
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final BlockBreakEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2.getBlock());
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerRespawnEvent e2) {
        Bukkit.getScheduler().runTaskLater((org.bukkit.plugin.Plugin)Plugin.c, new Runnable(){

            @Override
            public void run() {
                bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

                    @Override
                    public void a(RPGItem rItem) {
                        bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                            @Override
                            public void a(bi_1 pow) {
                                pow.b(e2.getPlayer(), e2.getRespawnLocation());
                            }
                        });
                    }

                });
            }

        }, 1L);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerItemConsumeEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2.getItem());
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerLevelChangeEvent e2) {
        if (e2.getOldLevel() < e2.getNewLevel()) {
            bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.a(e2.getPlayer(), e2.getOldLevel(), e2.getNewLevel());
                        }
                    });
                }

            });
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(ThunderChangeEvent e2) {
        if (e2.toThunderState()) {
            for (final Player player : bg_1.c()) {
                bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                    @Override
                    public void a(RPGItem rItem) {
                        bi_1.a(rItem, am_0.WEATHER, new bo_0<bi_1>(){

                            @Override
                            public void a(bi_1 pow) {
                                pow.a(player, au_0.THUNDER);
                            }
                        });
                        bi_1.a(rItem, am_0.THUNDER, new bo_0<bi_1>(){

                            @Override
                            public void a(bi_1 pow) {
                                pow.k(player);
                            }
                        });
                    }

                });
            }
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(WeatherChangeEvent e2) {
        if (e2.toWeatherState()) {
            for (final Player player : bg_1.c()) {
                bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                    @Override
                    public void a(RPGItem rItem) {
                        bi_1.a(rItem, am_0.WEATHER, new bo_0<bi_1>(){

                            @Override
                            public void a(bi_1 pow) {
                                pow.a(player, au_0.STORM);
                            }
                        });
                        bi_1.a(rItem, am_0.STORM, new bo_0<bi_1>(){

                            @Override
                            public void a(bi_1 pow) {
                                pow.k(player);
                            }
                        });
                    }

                });
            }
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final StructureGrowEvent e2) {
        if (e2.isFromBonemeal() && e2.getPlayer() != null) {
            bz_1.a(bg_1.b(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.a(e2.getPlayer(), e2.getLocation(), e2.getSpecies());
                        }
                    });
                }

            });
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerFishEvent e2) {
        switch (e2.getState()) {
            case CAUGHT_ENTITY: {
                break;
            }
            case CAUGHT_FISH: {
                break;
            }
            default: {
                return;
            }
        }
        bz_1.a(bg_1.b(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.getByEvent((Event)e2), new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.a(e2.getPlayer(), e2);
                    }
                });
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerToggleSneakEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.SNEAK, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.d(e2.getPlayer());
                    }
                });
                if (e2.isSneaking()) {
                    bi_1.a(rItem, am_0.SNEAKON, new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.e(e2.getPlayer());
                        }
                    });
                } else {
                    bi_1.a(rItem, am_0.SNEAKOFF, new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.f(e2.getPlayer());
                        }
                    });
                }
            }

        });
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    void a(final PlayerToggleSprintEvent e2) {
        bz_1.a(bg_1.a(e2.getPlayer()), e2.getPlayer(), new bo_0<RPGItem>(){

            @Override
            public void a(RPGItem rItem) {
                bi_1.a(rItem, am_0.SPRINT, new bo_0<bi_1>(){

                    @Override
                    public void a(bi_1 pow) {
                        pow.g(e2.getPlayer());
                    }
                });
                if (e2.isSprinting()) {
                    bi_1.a(rItem, am_0.SPRINTON, new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.h(e2.getPlayer());
                        }
                    });
                } else {
                    bi_1.a(rItem, am_0.SPRINTOFF, new bo_0<bi_1>(){

                        @Override
                        public void a(bi_1 pow) {
                            pow.i(e2.getPlayer());
                        }
                    });
                }
            }

        });
    }

}

