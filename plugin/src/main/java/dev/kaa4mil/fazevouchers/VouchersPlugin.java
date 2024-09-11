package dev.kaa4mil.fazevouchers;

import dev.kaa4mil.FazeBukkit;
import dev.kaa4mil.FazeSerdes;
import dev.kaa4mil.fazevouchers.command.VoucherCommand;
import dev.kaa4mil.fazevouchers.command.handler.PermissionHandler;
import dev.kaa4mil.fazevouchers.command.handler.UsageHandler;
import dev.kaa4mil.fazevouchers.config.VoucherConfig;
import dev.kaa4mil.fazevouchers.controller.VoucherController;
import dev.kaa4mil.fazevouchers.serdes.VoucherSerializer;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VouchersPlugin extends FazeBukkit {

    private LiteCommands<CommandSender> liteCommands;


    @Override
    public void enable() {

        this.registerComponent(this);

        final VoucherConfig voucherConfig = this.getConfigLoader().addConfiguration(VoucherConfig.class, "config", this, registry -> {
            registry.register(new FazeSerdes());
            registry.register(new VoucherSerializer());
        });

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), this.getName())
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>(voucherConfig.getMessageConfig().getOnlyPlayerMessage()))
                .invalidUsageHandler(new UsageHandler())
                .permissionHandler(new PermissionHandler())

                .argument(Player.class, new BukkitPlayerArgument<>(this.getServer(), voucherConfig.getMessageConfig().getInvalidPlayerMessage()))

                .commandInstance(
                        this.createInstance(VoucherCommand.class)
                )
                .register();

        this.registerComponent(voucherConfig);
        this.registerComponent(liteCommands);

        this.getServer().getPluginManager().registerEvents(this.createInstance(VoucherController.class), this);
    }

    @Override
    public void shutdown() {
        if(this.liteCommands != null) {
            this.liteCommands.getPlatform().unregisterAll();
        }
    }

}