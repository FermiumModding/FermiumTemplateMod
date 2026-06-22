package examplemod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import examplemod.ExampleMod;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * Registered commands:
 * - /examplemod hello - Sends a hello message+
 * - /examplemod heal <player> [amount] - Heals a player
 *
 * Permission levels:
 * - 0: Everyone (no permissions needed)
 * - 1: Bypass spawn protection
 * - 2: /gamemode, /difficulty, etc.
 * - 3: /ban, /op, etc.
 * - 4: /stop
 */
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
public class ExampleCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("examplemod")
                .then(Commands.literal("hello")
                        .executes(ExampleCommand::executeHello)
                )
                .then(Commands.literal("heal")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("player", EntityArgument.player()) //There's various ArgumentTypes available
                                .executes(ctx -> executeHeal(ctx, 20.0f)) // Default: full health
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 100))
                                        .executes(ctx -> executeHeal(ctx, IntegerArgumentType.getInteger(ctx, "amount")))
                                )
                        )
                )
        );
    }

    // /examplemod hello
    private static int executeHello(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        source.sendSuccess(() -> Component.literal("Hello from Example Mod!").withStyle(ChatFormatting.GREEN), false);
        return 1; // Success
    }

    // /examplemod heal <player> [amount]
    private static int executeHeal(CommandContext<CommandSourceStack> context, float amount) {
        try {
            ServerPlayer player = EntityArgument.getPlayer(context, "player");

            float newHealth = Math.min(player.getHealth() + amount, player.getMaxHealth());
            player.setHealth(newHealth);

            context.getSource().sendSuccess(
                    () -> Component.literal("Healed " + player.getName().getString() + " for " + amount + " HP!").withStyle(ChatFormatting.GREEN),
                    true
            );

            return 1; // Success
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Player not found!").withStyle(ChatFormatting.RED));
            return 0; // Failure
        }
    }
}