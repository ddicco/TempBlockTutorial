package me.ddicco.earthblastremake;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.TempBlock;

public class EarthBlastRemake extends EarthAbility implements AddonAbility {

	private long cooldown;
	private Location location;
	private Location startlocation;
	private Vector direction;
	private double damage;
	
	// list of tempblocks
	private ArrayList<TempBlock> tempblocks = new ArrayList<TempBlock>();
	
	private double range;

	public EarthBlastRemake(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
		
		// first thing to do is set the fields
		
		// cooldown for the ability
		cooldown = 3000;
		
		// used to see how far the move goes, will add checks for it in progress
		range = 20;
		
		// will change to show the location of the ability
		location = player.getLocation().clone();
		
		// will be the start location so that we can check the distance between that and the current location
		startlocation = location.clone();
		
		// saves direction, if you put this code line in the progress method, the ability will go in the direction the player looks at all times (be redirectable)
		direction = player.getLocation().getDirection().clone();
		
		// damage for the ability measured in half-hearts
		damage = 2;
		
		// give cooldown to the player
		bPlayer.addCooldown(this);
		
		// starts the ability
		start();
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return cooldown;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "EarthBlastRemake";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// return false if the player will be able to use FastSwim (water passive) with your ability selected
	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// method that runs repeatedly on every tick
	@Override
	public void progress() {
		// TODO Auto-generated method stub
		
		// necessary base checks
		if(player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		//remove every existing tempblock to make it so the ability is only a single block each time, or else the blocks it passes through will not revert until ability ends
		for(TempBlock tblock : tempblocks) {
			tblock.revertBlock();
		}
		// adds the direction where the player's looking at to the ability, meaning it goes 1 block further to where he was looking when ability started every time progress runs
		location.add(direction);
		
		// creates a tempblock with the material of Stone at the block XYZ of the location
		TempBlock tempblock = new TempBlock(location.getBlock(), Material.STONE);
		// add the tempblock to the tempblocks ArrayList
		tempblocks.add(tempblock);
		
		// checks if the location is to far from where it started, am using distanceSquared instead of distance because it runs better without having to use square roots
		if(location.distanceSquared(startlocation) > range * range) {
			remove();
			return;
		}
	}
	
	// create a different remove method so that you take out the tempblocks
	@Override
	public void remove() {
		// iterate through the tempblocks removing each of them
		for(TempBlock tblock : tempblocks) {
			tblock.revertBlock();
		}
		super.remove();
		return;
	}
	
	// author of the ability
	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "ddicco";
	}
	
	// version of the ability
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		// registers the listener to the projectkorra plugin in order to make the listener run
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new EarthBlastRemakeListener(), ProjectKorra.plugin);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}
	
}
