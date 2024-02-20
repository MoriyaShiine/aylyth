package moriyashiine.aylyth.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class AylythParticleTextureSheets {
    public static final ParticleTextureSheet GLOWING = new ParticleTextureSheet() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            MinecraftClient.getInstance().gameRenderer.getLightmapTextureManager().enable();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            AbstractTexture tex = textureManager.getTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            tex.setFilter(true, false);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tessellator) {
            AbstractTexture tex = MinecraftClient.getInstance().getTextureManager().getTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            tex.setFilter(false, false);
            tessellator.draw();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.disableDepthTest();
        }

        @Override
        public String toString() {
            return Aylyth.MOD_ID + ":glowing";
        }
    };
}
