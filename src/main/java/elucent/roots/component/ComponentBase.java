package elucent.roots.component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import elucent.roots.item.ItemDruidRobes;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ComponentBase {
	String name = "";
	String effectName = "";
	ItemStack itemSource = null;
	public Vec3d primaryColor = new Vec3d(0, 0, 0);
	public Vec3d secondaryColor = new Vec3d(0, 0, 0);
	public int xpCost = 0;
	public boolean singleCast = true;
	Random random = new Random();
	TextFormatting textColor = TextFormatting.WHITE;
	
	public ComponentBase setPrimaryColor(double r, double g, double b){
		this.primaryColor = new Vec3d(r,g,b);
		return this;
	}
	
	public ComponentBase setSecondaryColor(double r, double g, double b){
		this.secondaryColor = new Vec3d(r,g,b);
		return this;
	}
	
	public ComponentBase setTextColor(TextFormatting color){
		this.textColor = color;
		return this;
	}
	
	public ComponentBase(String name, String effectName, ItemStack item, int cost){
		this.name = name;
		this.xpCost = cost;
		this.effectName = effectName;
		itemSource = item;
	}

	public ComponentBase(String name, String effectName, Item item, int cost){
		this.name = name;
		this.xpCost = cost;
		this.effectName = effectName;
		itemSource = new ItemStack(item);
	}

	public ComponentBase(String name, String effectName, Block item, int cost){
		this.name = name;
		this.xpCost = cost;
		this.effectName = effectName;
		itemSource = new ItemStack(item);
	}

	public ComponentBase(String name, String effectName, Item item, int meta, int cost){
		this.name = name;
		this.xpCost = cost;
		this.effectName = effectName;
		itemSource = new ItemStack(item,1,meta);
	}

	public ComponentBase(String name, String effectName, Block item, int meta, int cost){
		this.name = name;
		this.xpCost = cost;
		this.effectName = effectName;
		itemSource = new ItemStack(item,1,meta);
	}
	
	public String getName(){
		return name;
	}
	
	public String getEffectName(){
		return effectName;
	}
	
	public TextFormatting getTextColor(){
		return textColor;
	}
	
	public ItemStack getItem(){
		return itemSource;
	}
	
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		
	}
	
	public void doEffect(World world, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		
	}
	
	public void castingAction(EntityPlayer player, int count, int potency, int efficiency, int size){
	}
}
