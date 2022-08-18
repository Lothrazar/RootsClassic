package elucent.rootsclassic.ritual;

import elucent.rootsclassic.Roots;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public abstract class RitualBase extends ForgeRegistryEntry<RitualBase> {

	private static final int RADIUS = 4;
	private ArrayList<Block> blocks = new ArrayList<>();
	private ArrayList<BlockPos> positionsRelative = new ArrayList<>();
	private List<ItemStack> incenses = new ArrayList<>();
	private List<ItemStack> ingredients = new ArrayList<>();
	private Vec3 color = new Vec3(255, 255, 255);
	private Vec3 secondaryColor = new Vec3(255, 255, 255);
	private int level;

	public RitualBase(int level, double r, double g, double b) {
		setPrimaryColor(r, g, b);
		setSecondaryColor(r, g, b);
		setLevel(level);
	}

	public void setLevel(int level) {
		if (level < 0 || level > 2) {
			throw new IllegalArgumentException("Level must be 0, 1 or 2");
		}
		this.blocks.clear();
		;
		this.positionsRelative.clear();
		this.level = level;
		//level 0 has no stones
		if (level >= 1) {
			//the first circle of tier 1 stones
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, -3);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 3);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, -3);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 3);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 3, 0, 0);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), -3, 0, 0);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, 3);
			this.addRitualPillar(RootsRegistry.MUNDANE_STANDING_STONE.get(), 0, 0, -3);
		}
		if (level == 2) {
			//the outer tier 2 stones
			this.addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 5, 1, 0);
			this.addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), -5, 1, 0);
			this.addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 0, 1, 5);
			this.addRitualPillar(RootsRegistry.ATTUNED_STANDING_STONE.get(), 0, 1, -5);
		}
	}

	public RitualBase addRitualPillar(Block b, int x, int y, int z) {
		getBlocks().add(b);
		getPositionsRelative().add(new BlockPos(x, y, z));
		return this;
	}

	public RitualBase addIngredient(ItemStack i) {
		getIngredients().add(i);
		return this;
	}

	public RitualBase addIncense(ItemStack i) {
		getIncenses().add(i);
		return this;
	}

	public boolean doIngredientsMatch(RitualBase ritual) {
		return RootsUtil.itemListsMatch(this.getIngredients(), ritual.getIngredients());
	}

	public abstract void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses);

	public boolean verifyPositionBlocks(Level levelAccessor, BlockPos pos) {
		if (!getPositionsRelative().isEmpty() && getPositionsRelative().size() == getBlocks().size()) {
			for (int i = 0; i < getPositionsRelative().size(); i++) {
				BlockPos loopPos = getPositionsRelative().get(i);
				Block loopBlock = getBlocks().get(i);
				BlockPos loopPosOffset = pos.offset(loopPos.getX(), loopPos.getY(), loopPos.getZ());
				if (levelAccessor.getBlockState(loopPosOffset).getBlock() != loopBlock) {
					Roots.LOGGER.info(this.level + " level recipe has Missing block " + loopBlock + " at position " + loopPosOffset);
					return false;
				}
			}
		}
		return true;
	}

	public List<BrazierBlockEntity> getRecipeBraziers(Level levelAccessor, BlockPos pos) {
		List<BrazierBlockEntity> links = new ArrayList<>();
		BlockEntity tileHere;
		for (int i = -1 * RADIUS; i <= RADIUS; i++) {
			for (int j = -1 * RADIUS; j <= RADIUS; j++) {
				if (levelAccessor.getBlockState(pos.offset(i, 0, j)).getBlock() == RootsRegistry.BRAZIER.get()) {
					tileHere = levelAccessor.getBlockEntity(pos.offset(i, 0, j));
					if (tileHere instanceof BrazierBlockEntity) {
						links.add((BrazierBlockEntity) tileHere);
					}
				}
			}
		}
		return links;
	}

	public boolean incenseMatches(Level levelAccessor, BlockPos pos) {
		ArrayList<ItemStack> incenseFromNearby = new ArrayList<>();
		List<BrazierBlockEntity> braziers = getRecipeBraziers(levelAccessor, pos);
		for (BrazierBlockEntity brazier : braziers) {
			if (!brazier.getHeldItem().isEmpty()) {
				//              Roots.logger.info("found brazier item " + brazier.getHeldItem());
				incenseFromNearby.add(brazier.getHeldItem());
			}
		}
		return RootsUtil.itemListsMatch(getIncenses(), incenseFromNearby);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("[A] ");
		for (ItemStack mat : this.getIngredients()) {
			s.append(mat.getHoverName().getString()).append("; ");
		}
		s.append("[I] ");
		for (ItemStack mat : this.getIncenses()) {
			s.append(mat.getHoverName().getString()).append("; ");
		}
		return s.toString();
	}

	public List<ItemStack> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<ItemStack> ingredients)
		throws IllegalArgumentException {
		if (ingredients.size() == 0 || ingredients.size() > 3) {
			throw new IllegalArgumentException("Invalid ritual ingredients, must be in range [1,3]");
		}
		this.ingredients = ingredients;
	}

	public List<ItemStack> getIncenses() {
		return incenses;
	}

	public void setIncenses(List<ItemStack> incenses)
		throws IllegalArgumentException {
		if (incenses.size() == 0 || incenses.size() > 4) {
			throw new IllegalArgumentException("Invalid ritual incense, must be in range [1,4]");
		}
		this.incenses = incenses;
	}

	public ArrayList<BlockPos> getPositionsRelative() {
		return positionsRelative;
	}

	public void setPositionsRelative(ArrayList<BlockPos> positionsRelative) {
		this.positionsRelative = positionsRelative;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}

	public Vec3 getColor() {
		return color;
	}

	public void setPrimaryColor(double r, double g, double b) {
		this.color = buildColor(r, g, b);
	}

	public Vec3 getSecondaryColor() {
		return secondaryColor;
	}

	public RitualBase setSecondaryColor(double r, double g, double b) {
		this.secondaryColor = buildColor(r, g, b);
		return this;
	}

	private Vec3 buildColor(double r, double g, double b) throws IllegalArgumentException {
		if (r < 0 || r > 255 ||
			g < 0 || g > 255 ||
			b < 0 || b > 255) {
			throw new IllegalArgumentException("Invalid color value use [0, 255]");
		}
		return new Vec3(r, g, b);
	}
}
