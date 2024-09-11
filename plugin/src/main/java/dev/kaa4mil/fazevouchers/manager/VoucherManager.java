package dev.kaa4mil.fazevouchers.manager;

import dev.kaa4mil.fazevouchers.voucher.Voucher;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class VoucherManager {

    @Getter private static final Map<Player, Voucher> edit = new HashMap<>();

}
