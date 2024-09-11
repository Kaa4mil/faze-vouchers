package dev.kaa4mil.fazevouchers.config;

import dev.kaa4mil.fazevouchers.shared.ItemBuilder;
import dev.kaa4mil.fazevouchers.voucher.Voucher;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@SuppressWarnings("deprecation")
@Headers({
        @Header("## Faze-Vouchers configuration ##"),
        @Header("## Author: Kaa4mil ##")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class VoucherConfig extends OkaeriConfig {

    @Comment({"", "Informacje o menu edycji vouchera"})
    public MenuConfig menuConfig = new MenuConfig();

    @Comment({"", "Lista aktywnych voucherow do uzytku"})
    public List<Voucher> vouchers = List.of(
           new Voucher(
                   "testowy",
                   "&6VIP",
                   new ItemBuilder(Material.BOOK)
                           .setName("&7Voucher na &6&lVIP")
                           .setLore(List.of("", "&7Po aktywacji otrzymasz:", "&8- &7Range &6VIP &8(&730 dni&8)", "", "&aNacisnij prawym w powietrzu, aby aktywowac!"))
                           .getItem(),
                   List.of("lp user {PLAYER} parent addtemp VIP 30d")
           )
    );

    @Getter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class MenuConfig extends OkaeriConfig {

        public int inventorySize = 45;
        public String inventoryName = "&0Edytuj voucher: &a&l{NAME}";

        Map<Integer, ItemStack> contents = Stream.of(
                new AbstractMap.SimpleEntry<>(0, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(2, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(3, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(4, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(5, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(6, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(7, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(8, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(17, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(35, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(26, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(9, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(18, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(27, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(36, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(37, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(38, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(39, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(40, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(41, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(42, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(43, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem()),
                new AbstractMap.SimpleEntry<>(44, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).getItem())
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        @Comment("Informacje o przedmiocie usuwajacym")
        public ItemStack deleteItem = new ItemBuilder(Material.RED_DYE)
                .setName("&cUsun voucher")
                .getItem();

        public int deleteItemSlot = 20;

        @Comment("Informacje o przedmiocie zmieniajacym komende")
        public ItemStack changeCommandItem = new ItemBuilder(Material.YELLOW_BANNER)
                .setName("&eZmien komende")
                .getItem();

        public int changeCommandSlot = 24;

        @Comment("Na jakim slocie ma ustawic sie voucher przy edycji?")
        public int voucherSlot = 22;

    }

    @Comment({"", "Konfiguracja wiadomosci"})
    public MessageConfig messageConfig = new MessageConfig();

    @Getter
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class MessageConfig extends OkaeriConfig {

        public String onlyPlayerMessage = "&cKomenda dostepna tylko z poziomu gracza!";
        public String invalidPlayerMessage = "&cNie znaleziono takiego gracza!";
        public String givedVoucherToPlayer = "&aPomyslnie nadano voucher dla gracza: &2{PLAYER}";
        public String invalidVoucherMessage = "&cNie znaleziono vouchera o nazwie: &4{NAME}";
        public String successfulyDeletedMessage = "&aPomyslnie usunieto voucher o nazwie: &2{NAME}";
        public String voucherAlreadyExistMessage = "&cVoucher o nazwie &4{NAME} &cjuz istnieje!";
        public String successfulyCreatedMessage = "&aPomyslnie utworzono voucher o nazwie: &2{NAME}";
        public String voucherEditingCommandChange = "&aWpisz na czacie nowa komende dla voucheru";
        public String voucherEditingCommandChangeSuccessful = "&aPomyslnie zmieniono komende dla voucheru: &2{NAME}";
        public String voucherUseSuccessful = "&aPomyslnie wykorzystano voucher!";

        public List<String> voucherUseMessages = List.of(
                "&8----------------------------------------------",
                "&8» &7Gracz &a{PLAYER} &7wykorzystal voucher {PREFIX}",
                "&8» &7Vouchery zakupisz pod &e/sklep",
                "&8----------------------------------------------"
        );

    }

}
