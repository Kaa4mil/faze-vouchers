package dev.kaa4mil.fazevouchers.command.handler;

import dev.kaa4mil.util.ColorTransformer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

import java.util.List;

public class UsageHandler implements InvalidUsageHandler<CommandSender> {

    @Override
    public void handle(CommandSender sender, LiteInvocation liteInvocation, Schematic schematic) {
        final List<String> schematics = schematic.getSchematics();

        schematic.getSchematics().forEach(line -> {
            sender.sendMessage(ColorTransformer.fix("&cPoprawne uzycie: &4" + line));
        });
    }

}
