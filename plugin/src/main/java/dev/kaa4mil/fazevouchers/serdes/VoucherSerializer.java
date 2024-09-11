package dev.kaa4mil.fazevouchers.serdes;

import dev.kaa4mil.fazevouchers.voucher.Voucher;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class VoucherSerializer implements ObjectSerializer<Voucher> {

    @Override
    public boolean supports(@NonNull Class<? super Voucher> type) {
        return Voucher.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Voucher voucher, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", voucher.getName());
        data.add("prefix", voucher.getPrefix());
        data.add("item", voucher.getItem());
        data.add("commands", voucher.getCommands());
    }

    @Override
    public Voucher deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Voucher(
                data.get("name", String.class),
                data.get("prefix", String.class),
                data.get("item", ItemStack.class),
                data.getAsList("commands", String.class)
        );
    }

}
