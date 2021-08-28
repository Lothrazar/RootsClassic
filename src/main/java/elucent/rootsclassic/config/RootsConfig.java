package elucent.rootsclassic.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import elucent.rootsclassic.Roots;

public class RootsConfig {

  public static class Client {

    public final BooleanValue showTabletWave;
    public final IntValue manaBarOffset;

    Client(ForgeConfigSpec.Builder builder) {
      builder.comment("Client settings")
          .push("client");
      showTabletWave = builder
          .comment("Toggles the wave effect in the Runic Tablet GUI")
          .define("showTabletWave", true);
      manaBarOffset = builder
          .comment("The number of pixels above the bottom of the screen that the mana bar should be rendered. If it's conflicting with a bar from another mod, raising it by 10 will normally position it right")
          .defineInRange("manaBarOffset", 49, 0, 32767);
      builder.pop();
    }
  }

  public static class Common {

    public final IntValue oldRootDropChance, verdantSprigDropChance, infernalStemDropChance, dragonsEyeDropChance, berriesDropChance;
    public final DoubleValue barkKnifeBlockStripChance;
    public final IntValue chargeTicks, staffUses, efficiencyBonus;
    public final BooleanValue disablePVP;
    public final IntValue ticksPerManaRegen, staffUsesBasic, staffUsesEfficiency;

    Common(ForgeConfigSpec.Builder builder) {
      builder.comment("Settings related to actual gameplay-affecting features")
          .push("items");
      oldRootDropChance = builder
          .comment("Old Roots will drop from tall grass with a 1/oldRootDropChance probability")
          .defineInRange("oldRootDropChance", 40, 0, 32767);
      verdantSprigDropChance = builder
          .comment("Verdant Sprigs will drop from grown crops with a 1/verdantSprigDropChance probability")
          .defineInRange("verdantSprigDropChance", 30, 0, 32767);
      infernalStemDropChance = builder
          .comment("Infernal Bulbs will drop from nether wart with a 1/infernalBulbDropChance probability")
          .defineInRange("infernalStemDropChance", 20, 0, 32767);
      dragonsEyeDropChance = builder
          .comment("Dragon's Eyes will drop from chorus flowers with a 1/dragonsEyeDropChance probability")
          .defineInRange("dragonsEyeDropChance", 10, 0, 32767);
      berriesDropChance = builder
          .comment("Berries will drop from oak leaves with a 1/berriesDropChance probability")
          .defineInRange("berriesDropChance", 25, 0, 32767);
      barkKnifeBlockStripChance = builder
          .comment("Chance that the bark knife will strip the log, 1 is always strip on first harvest")
          .defineInRange("barkKnifeBlockStripChance", 0.3F, 0.1F, 1.0F);
      builder.pop();
      builder.comment("Settings related to actual gameplay-affecting features")
          .push("magic");
      chargeTicks = builder
          .comment("The number of ticks required to prepare a spell with a staff")
          .defineInRange("staffChargeTicks", 20, 1, 32767);
      staffUses = builder
          .comment("The number of uses an unmodified staff will have upon being crafted")
          .defineInRange("staffUses", 65, 0, 32767);
      efficiencyBonus = builder
          .comment("The number of additional uses each efficiency modifier gives")
          .defineInRange("efficiencyBonusUses", 32, 0, 32767);
      disablePVP = builder
          .comment("Whether or not damaging spells can affect players")
          .define("disablePVP", false);
      ticksPerManaRegen = builder
          .comment("Number of ticks between each mana regeneration (20 ticks = 1 second)")
          .defineInRange("ticksPerManaRegen", 15, 1, 100);
      staffUsesBasic = builder
          .comment("Number of basic uses for one spell staff")
          .defineInRange("staffUsesBasic", 15, 1, 100);
      staffUsesEfficiency = builder
          .comment("Number of uses added by each efficiency level on a spell")
          .defineInRange("staffUsesEfficiency", 15, 1, 100);
      //TODO: spells category to disable component  on loop?
      //    disabledComponents = config.getStringList("disabledComponents", category, new String[] {
      //        "<example>", "<another example>"
      //    }, "A string list of all disabled components. Valid component names include: \"allium\", \"apple\", \"azurebluet\", \"blueorchid\", \"chorus\", \"dandelion\", \"flareorchid\", \"lilac\", \"lilypad\", \"midnightbloom\", \"netherwart\", \"orangetulip\", \"oxeyedaisy\", \"peony\", \"pinktulip\", \"poisonouspotato\", \"poppy\", \"radiantdaisy\", \"redtulip\", \"rosebush\", \"sunflower\", \"whitetulip\"");
      //TODO: rituals component to disable single on loop
      builder.pop();
    }
  }

  public static final ForgeConfigSpec clientSpec;
  public static final Client CLIENT;
  static {
    final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
    clientSpec = specPair.getRight();
    CLIENT = specPair.getLeft();
  }
  public static final ForgeConfigSpec commonSpec;
  public static final Common COMMON;
  static {
    final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
    commonSpec = specPair.getRight();
    COMMON = specPair.getLeft();
  }

  @SubscribeEvent
  public static void onLoad(final ModConfig.Loading configEvent) {
    Roots.LOGGER.debug("Loaded Roots Classic's config file {}", configEvent.getConfig().getFileName());
  }

  @SubscribeEvent
  public static void onFileChange(final ModConfig.Reloading configEvent) {
    Roots.LOGGER.fatal("Roots Classic's config just got changed on the file system!");
  }
}
