package dev.kaa4mil.fazevouchers.command;

import dev.kaa4mil.fazevouchers.config.VoucherConfig;
import dev.kaa4mil.fazevouchers.manager.VoucherManager;
import dev.kaa4mil.fazevouchers.shared.ItemBuilder;
import dev.kaa4mil.fazevouchers.voucher.Voucher;
import dev.kaa4mil.util.ColorTransformer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Route(name = "voucher")
@Permission("fazedev.vouchers.admin")
public class VoucherCommand {

    private final VoucherConfig voucherConfig;

    @Route(name = "create")
    void createVoucher(@NotNull final Player player, @Arg @Name("name") final String name, @Joiner @Name("command") final String command) {

        final Voucher voucher = this.voucherConfig.getVouchers().stream()
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(voucher != null) {
            player.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getVoucherAlreadyExistMessage()
                    .replace("{NAME}", name))
            );
            return;
        }

        final Voucher voucherCreated = new Voucher(name, "&8???",
                new ItemBuilder(Material.BOOK)
                .setName("&7Voucher na &8???")
                .setLore(List.of("", "&7Po aktywacji otrzymasz:", "&8- &7Do zedytowania pod &a/voucher edit <nazwa>"))
                .getItem(),
                List.of(command
                        .replace("{PLAYER}", player.getName())
                )
        );

        this.voucherConfig.getVouchers().add(voucherCreated);
        this.voucherConfig.save();
        this.voucherConfig.load();

        player.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getSuccessfulyCreatedMessage()
                .replace("{NAME}", voucherCreated.getName()))
        );

    }

    @Route(name = "edit")
    void editVoucher(@NotNull final Player player, @Arg @Name("name") final String name) {

        final Voucher voucher = this.voucherConfig.getVouchers().stream()
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(voucher == null) {
            player.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getInvalidVoucherMessage()
                    .replace("{NAME}", name)
            ));
            return;
        }

        final Gui gui = Gui.gui(GuiType.CHEST)
                .rows(this.voucherConfig.getMenuConfig().getInventorySize() / 9)
                .title(Component.text(ColorTransformer.fix(this.voucherConfig.getMenuConfig().getInventoryName()
                        .replace("{NAME}", voucher.getName())
                )))
                .create();

        this.voucherConfig.getMenuConfig().getContents().forEach((slot, item) -> gui.setItem(slot, new GuiItem(item, event -> event.setCancelled(true))));

        gui.setItem(this.voucherConfig.getMenuConfig().getVoucherSlot(), new GuiItem(voucher.getItem(), event ->
                event.setCancelled(true)
        ));

        gui.setItem(this.voucherConfig.getMenuConfig().getChangeCommandSlot(), new GuiItem(this.voucherConfig.getMenuConfig().getChangeCommandItem(), event -> {
            player.closeInventory();

            VoucherManager.getEdit().put(player, voucher);
            player.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getVoucherEditingCommandChange()));

            event.setCancelled(true);
        }));

        gui.setItem(this.voucherConfig.getMenuConfig().getDeleteItemSlot(), new GuiItem(this.voucherConfig.getMenuConfig().getDeleteItem(), event -> {

            player.closeInventory();
            player.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getSuccessfulyDeletedMessage()
                    .replace("{NAME}", voucher.getName())
            ));

            this.voucherConfig.getVouchers().remove(voucher);
            this.voucherConfig.save();
            this.voucherConfig.load();

            event.setCancelled(true);
        }));

        gui.open(player);

    }

    @Route(name = "list")
    void voucherList(@NotNull final CommandSender sender) {
        sender.sendMessage(ColorTransformer.fix("&7Lista aktywnych voucherow: \n" +
                        this.voucherConfig.getVouchers().stream()
                                .map(Voucher::getName)
                                .collect(Collectors.joining("\n&8- &a", "&8- &a", ""))
        ));
    }

    @Route(name = "give")
    void giveVoucher(@NotNull final CommandSender sender, @Arg @Name("name") final String name, @Arg @Name("player") final Player player) {

        final Voucher voucher = this.voucherConfig.getVouchers().stream()
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(voucher == null) {
            sender.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getInvalidVoucherMessage()
                    .replace("{NAME}", name)
            ));
            return;
        }

        player.getInventory().addItem(voucher.getItem());
        sender.sendMessage(ColorTransformer.fix(this.voucherConfig.getMessageConfig().getGivedVoucherToPlayer()
                .replace("{PLAYER}", player.getName())
        ));

    }

    @Route(name = "reload")
    void reloadConfig(@NotNull final CommandSender sender) {
        this.voucherConfig.save();
        this.voucherConfig.load();

        sender.sendMessage(ColorTransformer.fix("&aPomyslnie przeladowano konfiguracje!"));
    }

}
