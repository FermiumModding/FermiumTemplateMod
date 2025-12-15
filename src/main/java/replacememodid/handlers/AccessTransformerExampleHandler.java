package replacememodid.handlers;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import replacememodid.ReplaceMeModName;

//This class just uses fields, methods and classes whose access got transformed, no claim to make sense
public class AccessTransformerExampleHandler {
    @SubscribeEvent
    public static void onSleepingTimeCheck(SleepingTimeCheckEvent event){
        EntityPlayer player = event.getEntityPlayer();

        //accessing private method isInBed
        if(player.isPlayerSleeping() && player.isInBed()) { // redundant check - just to use an otherwise private method
            //accessing private field sleepTimer
            player.sleepTimer++; //sleep twice as fast, using private field sleepTimer
            ReplaceMeModName.LOGGER.info("Sleep faster using Access Transformers");

            //accessing private inner class SleepEnemyPredicate + its constructor <init>
            EntityPlayer.SleepEnemyPredicate preventsSleeping = new EntityPlayer.SleepEnemyPredicate(player); // instance of a private class

            AxisAlignedBB aabb = new AxisAlignedBB(player.getPosition(), player.getPosition()).grow(8.0D,  5.0D, 8.0D);
            if(!player.world.getEntitiesWithinAABB(EntityMob.class, aabb, preventsSleeping).isEmpty()) {
                //player.wakeUpPlayer(true, true, false); //wake player if mobs around
                event.setResult(Event.Result.DENY);
                ReplaceMeModName.LOGGER.info("Mobs nearby wake you up using Access Transformers");
            }
        }
    }
}
