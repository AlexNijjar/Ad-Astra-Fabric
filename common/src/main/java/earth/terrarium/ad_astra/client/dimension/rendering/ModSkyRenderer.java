package earth.terrarium.ad_astra.client.dimension.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer.SkyObject;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer.StarsRenderer;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer.SunsetColour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ModSkyRenderer {

    private final StarsRenderer starsRenderer;
    private final SunsetColour sunsetColour;
    private final List<SkyObject> skyObjects;
    private final int horizonAngle;
    private final boolean shouldRenderWhileRaining;

    private VertexBuffer starsBuffer;

    public ModSkyRenderer(PlanetSkyRenderer skyRenderer) {
        this.starsRenderer = skyRenderer.starsRenderer();
        this.sunsetColour = skyRenderer.sunsetColour();
        this.skyObjects = skyRenderer.skyObjects();
        this.horizonAngle = skyRenderer.horizonAngle();
        this.shouldRenderWhileRaining = !skyRenderer.weatherEffects().equals(PlanetSkyRenderer.WeatherEffects.NONE);
    }

    public void render(ClientWorld world, int ticks, float tickDelta, MatrixStack matrices, Camera camera, Matrix4f projectionMatrix, boolean foggy) {

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBufferBuilder();
        MinecraftClient client = MinecraftClient.getInstance();

        if (shouldRenderWhileRaining && world.isRaining()) {
            return;
        }
        // Cancel rendering if the player is in fog, i.e. in lava or powdered snow
        if (SkyUtil.isSubmerged(camera)) {
            return;
        }

        SkyUtil.preRender(world, client.worldRenderer, camera, projectionMatrix, bufferBuilder, this.sunsetColour, horizonAngle, matrices, tickDelta);

        // Stars
        if (this.starsRenderer.fastStars() > 0) {
            int stars = (!client.options.getGraphicsMode().get().equals(GraphicsMode.FAST) ? this.starsRenderer.fancyStars() : this.starsRenderer.fastStars());
            starsBuffer = renderStars(world, matrices, tickDelta, bufferBuilder, stars, this.starsRenderer);
        }

        // Render all sky objects
        for (SkyObject skyObject : this.skyObjects) {

            float scale = skyObject.scale();
            Vec3f rotation = skyObject.rotation();
            switch (skyObject.renderType()) {
                case STATIC -> {
                    // Do not modify the scale or rotation
                }
                case DYNAMIC ->
                        rotation = new Vec3f(world.getSkyAngle(tickDelta) * 360.0f + rotation.getX(), rotation.getY(), rotation.getZ());
                case SCALING -> scale *= SkyUtil.getScale();
                case DEBUG -> // Test things without restarting Minecraft
                        rotation = new Vec3f(60, 0, 0);
            }
            SkyUtil.render(matrices, bufferBuilder, skyObject.texture(), skyObject.colour(), rotation, scale, skyObject.blending());
        }

        SkyUtil.postRender(client.gameRenderer, world, tickDelta);
    }

    private VertexBuffer renderStars(ClientWorld world, MatrixStack matrices, float tickDelta, BufferBuilder bufferBuilder, int stars, StarsRenderer starsRenderer) {

        SkyUtil.startRendering(matrices, new Vec3f(-30.0f, 0.0f, world.getSkyAngle(tickDelta) * 360.0f));
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        if (starsBuffer != null) {
            starsBuffer.close();
        }

        starsBuffer = new VertexBuffer();
        BufferBuilder.RenderedBuffer renderedBuffer = SkyUtil.renderStars(bufferBuilder, stars, starsRenderer.colouredStars());
        starsBuffer.bind();
        starsBuffer.upload(renderedBuffer);
        VertexBuffer.unbind();

        if (!starsRenderer.daylightVisible()) {
            float rot = world.getStarBrightness(tickDelta);
            RenderSystem.setShaderColor(rot, rot, rot, rot);
        } else {
            RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
        }

        BackgroundRenderer.clearFog();
        starsBuffer.bind();
        starsBuffer.setShader(matrices.peek().getModel(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionColorShader());
        VertexBuffer.unbind();

        matrices.pop();
        return starsBuffer;
    }
}