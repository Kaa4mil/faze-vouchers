package dev.kaa4mil.fazevouchers.command.handler;

import dev.kaa4mil.util.ColorTransformer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import org.bukkit.command.CommandSender;

public class PermissionHandler implements dev.rollczi.litecommands.handle.PermissionHandler<CommandSender> {

    @Override
    public void handle(CommandSender sender, LiteInvocation liteInvocation, RequiredPermissions requiredPermissions) {
        sender.sendMessage(ColorTransformer.fix("&cNie posiadasz uprawnienia: &4" + requiredPermissions.getPermissions().get(0)));
    }

}
