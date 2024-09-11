package dev.kaa4mil.fazevouchers.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@AllArgsConstructor
public class Voucher {
    private String name;
    private String prefix;
    private ItemStack item;
    private List<String> commands;
}
