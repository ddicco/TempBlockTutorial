package me.ddicco.earthblastremake;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

public class EarthBlastRemakeListener implements Listener{
	
	// Click Event (will run whenever player clicks)
	@EventHandler
	public void onClick(PlayerAnimationEvent event) {
		
		// gets the player that clicked
		Player player = event.getPlayer();
		
		// gets the bending player of the player that clicked
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		
		// stops this if the bPlayer doesnt exist
		if(bPlayer == null) {
			return;
		}
		
		// checks if the player can bend the ability and at the same time does not have the ability active
		if(bPlayer.canBend(CoreAbility.getAbility(EarthBlastRemake.class)) && !CoreAbility.hasAbility(player, EarthBlastRemake.class)) {
			// we now start the ability because he does not have it active and clicked
			new EarthBlastRemake(player);
		}
	}
}
