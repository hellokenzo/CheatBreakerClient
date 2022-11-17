package com.cheatbreaker.client.module.type;

import com.cheatbreaker.client.config.Setting;
import com.cheatbreaker.client.event.type.GuiDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.module.CBGuiAnchor;
import com.cheatbreaker.client.ui.util.HudUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolutionBridge;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocationBridge;
import org.lwjgl.opengl.GL11;

public class DirectionHudModule extends AbstractModule {

    private final Setting markerColor;
    private final Setting directionColor;
    private final Setting showWhileTyping;
    private final ResourceLocationBridge texture = Ref.getInstanceCreator().createResourceLocationBridge("textures/gui/compass.png");

    public DirectionHudModule() {
        super("Direction HUD");
        this.setDefaultAnchor(CBGuiAnchor.MIDDLE_MIDDLE);
        this.setState(false);
        this.showWhileTyping = new Setting(this, "Show While Typing").setValue(true);
        this.markerColor = new Setting(this, "Marker Color").setValue(-43691).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.directionColor = new Setting(this, "Direction Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.setPreviewIcon(Ref.getInstanceCreator().createResourceLocationBridge("client/icons/mods/dirhud.png"), 65, 12);
        this.addEvent(GuiDrawEvent.class, this::renderReal);
    }

    private void renderReal(GuiDrawEvent guiDrawEvent) {
        if (!this.isRenderHud()) {
            return;
        }
        int backgroundColor = 0xFF212121;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        this.scaleAndTranslate(guiDrawEvent.getResolution());
        this.setDimensions(66, 18);
        if (!(minecraft.currentScreen instanceof GuiChat) || (Boolean) this.showWhileTyping.getValue()) {
            GL11.glColor4f((float)(backgroundColor >> 16 & 0xFF) / (float)255, (float)(backgroundColor >> 8 & 0xFF) / (float)255, (float)(backgroundColor & 0xFF) / (float)255, (float)(backgroundColor >> 24 & 255) / (float)255);
            this.render(guiDrawEvent.getResolution());
            GL11.glColor4f((float)(backgroundColor >> 16 & 0xFF) / (float)255, (float)(backgroundColor >> 8 & 0xFF) / (float)255, (float)(backgroundColor & 0xFF) / (float)255, (float)(backgroundColor >> 24 & 255) / (float)255);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void render(ScaledResolutionBridge scaledResolution) {
        int n = MathHelper.floor_double((double)(this.minecraft.bridge$getThePlayer().rotationYaw * (float)256 / (float)360) + 0.5) & 0xFF;
        int n2 = 0;
        int n3 = 0;
        int backgroundColor = 0xFF212121;

        if ((Integer)this.directionColor.getValue() != 4095) {
            int n4 = this.directionColor.getColorValue();
            this.minecraft.bridge$getTextureManager().bridge$bindTexture(this.texture);
            if (n < 128) {
                GL11.glColor4f((float)(backgroundColor >> 16 & 0xFF) / (float)255, (float)(backgroundColor >> 8 & 0xFF) / (float)255, (float)(backgroundColor & 0xFF) / (float)255, (float)(backgroundColor >> 24 & 255) / (float)255);
                HudUtil.drawTexturedModalRect(n3, n2, n, 0, 65, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 12, 65, 12, -100);
            }
            GL11.glColor4f((float)(n4 >> 16 & 0xFF) / (float)255, (float)(n4 >> 8 & 0xFF) / (float)255, (float)(n4 & 0xFF) / (float)255, 1.0f);
            if (n < 128) {
                HudUtil.drawTexturedModalRect(n3, n2, n, 24, 65, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 36, 65, 12, -100);
            }
        } else {
            this.minecraft.bridge$getTextureManager().bridge$bindTexture(this.texture);
            if (n < 128) {
                HudUtil.drawTexturedModalRect(n3, n2, n, 0, 65, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 12, 65, 12, -100);
            }
        }
        this.minecraft.bridge$getFontRenderer().bridge$drawString("|", n3 + 32, n2 + 1, this.markerColor.getColorValue());
        this.minecraft.bridge$getFontRenderer().bridge$drawString("|\u00a7r", n3 + 32, n2 + 5, this.markerColor.getColorValue());
    }


}
