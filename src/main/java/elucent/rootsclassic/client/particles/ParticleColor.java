package elucent.rootsclassic.client.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;

import java.util.Random;

/**
 * Modified version of ParticleColor: https://github.com/baileyholl/Ars-Nouveau/blob/1.16.2/src/main/java/com/hollingsworth/arsnouveau/client/particle/ParticleColor.java
 */
public class ParticleColor {
	public static final Codec<ParticleColor> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.INT.fieldOf("color").forGetter(ParticleColor::getColor))
		.apply(inst, ParticleColor::new));
	public static final StreamCodec<ByteBuf, ParticleColor> STREAM_CODEC = new StreamCodec<>() {
		public ParticleColor decode(ByteBuf byteBuf) {
			return new ParticleColor(byteBuf.readInt());
		}

		public void encode(ByteBuf byteBuf, ParticleColor color) {
			byteBuf.writeInt(color.getColor());
		}
	};

	private final float r;
	private final float g;
	private final float b;
	private final float a;
	private final int color;

	public ParticleColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.color = FastColor.ARGB32.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
	}

	public ParticleColor(double r, double g, double b, double a) {
		this.r = (float)r;
		this.g = (float)g;
		this.b = (float)b;
		this.a = (float)a;
		this.color = FastColor.ARGB32.color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
	}

	public ParticleColor(int color) {
		this(
			FastColor.ARGB32.red(color) / 255F,
			FastColor.ARGB32.green(color) / 255F,
			FastColor.ARGB32.blue(color) / 255F,
			FastColor.ARGB32.alpha(color) / 255F
		);
	}

	public ParticleColor(int r, int g, int b, int a) {
		this(a << 24 | r << 16 | g << 8 | b);
	}

	public int getColor() {
		return color;
	}

	public static ParticleColor makeRandomColor(int r, int g, int b, Random random) {
		return new ParticleColor(random.nextInt(r), random.nextInt(g), random.nextInt(b), 255);
	}

	public static ParticleColor getHSBColor(float h, float s, float b) {
		return new ParticleColor(HSBtoRGB(h, s, b));
	}

	public static int HSBtoRGB(float hue, float saturation, float brightness) {
		int r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = (int) (brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h) {
				case 0:
					r = (int) (brightness * 255.0f + 0.5f);
					g = (int) (t * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
					break;
				case 1:
					r = (int) (q * 255.0f + 0.5f);
					g = (int) (brightness * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
					break;
				case 2:
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (brightness * 255.0f + 0.5f);
					b = (int) (t * 255.0f + 0.5f);
					break;
				case 3:
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (q * 255.0f + 0.5f);
					b = (int) (brightness * 255.0f + 0.5f);
					break;
				case 4:
					r = (int) (t * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (brightness * 255.0f + 0.5f);
					break;
				case 5:
					r = (int) (brightness * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (q * 255.0f + 0.5f);
					break;
			}
		}
		return 0xff000000 | (r << 16) | (g << 8) | (b);
	}

	public float getRed() {
		return r;
	}

	public float getGreen() {
		return g;
	}

	public float getBlue() {
		return b;
	}

	public float getAlpha() {
		return a;
	}
}
