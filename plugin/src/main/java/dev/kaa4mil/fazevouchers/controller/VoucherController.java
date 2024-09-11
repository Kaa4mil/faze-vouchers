package dev.kaa4mil.fazevouchers.controller;

import dev.kaa4mil.fazevouchers.config.VoucherConfig;
import dev.kaa4mil.fazevouchers.manager.VoucherManager;
import dev.kaa4mil.fazevouchers.voucher.Voucher;
import dev.kaa4mil.util.ColorTransformer;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VoucherController implements Listener {

    private final VoucherConfig voucherConfig;

    @EventHandler
    void onPlayerChat(@NotNull final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();

        final Voucher voucher = VoucherManager.getEdit().get(player);

        if(voucher != null) {

            voucher.setCommands(List.of(message));
            this.voucherConfig.save();
            this.voucherConfig.load();

            player.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getVoucherEditingCommandChangeSuccessful()
                    .replace("{NAME}", voucher.getName())
            ));

            VoucherManager.getEdit().remove(player);
            event.setCancelled(true);
        }

    }

    @EventHandler
    void onPlayerInteract(@NotNull final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItemInMainHand();

        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        final Optional<Voucher> voucher = this.voucherConfig.getVouchers().stream()
                .filter(it -> it.getItem().isSimilar(item))
                .findFirst();

        if(voucher.isPresent()) {
            player.getInventory().remove(item);

            voucher.get().getCommands().forEach(command -> {
                player.getServer().dispatchCommand(player.getServer().getConsoleSender(), command
                        .replace("{PLAYER}", player.getName())
                );
            });

            Bukkit.getOnlinePlayers().forEach(p -> {
                this.voucherConfig.getMessageConfig().getVoucherUseMessages().forEach(message -> {
                    p.sendMessage(ColorTransformer.fix(message
                            .replace("{PLAYER}", player.getName())
                            .replace("{PREFIX}", voucher.get().getPrefix())
                    ));
                });
            });

            event.setCancelled(true);
        }

    }

}
