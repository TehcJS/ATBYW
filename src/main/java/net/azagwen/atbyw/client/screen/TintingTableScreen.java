package net.azagwen.atbyw.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.azagwen.atbyw.block.entity.TintingTableMode;
import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.screen.TintingTableScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.awt.*;

public class TintingTableScreen extends HandledScreen<TintingTableScreenHandler> implements ScreenHandlerListener {
    private static final Identifier TEXTURE = AtbywMain.id("textures/gui/tinting_table.png");
    private DynamicTexturedButtonWidget switchModeButton;
    private TextFieldWidget hexField;
    private float tickDelta;
    private boolean reverseTickDelta;
    private boolean isHexCodeValid = false;

    public TintingTableScreen(TintingTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 205;
        this.titleX = 8;
        this.titleY = 8;
        this.playerInventoryTitleX = 8;
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        if (this.tickDelta >= 1.0F) {
            this.reverseTickDelta = true;
        } else if (this.tickDelta <= 0.0F){
            this.reverseTickDelta = false;
        }
        if (!reverseTickDelta) {
            this.tickDelta = tickDelta + 0.01F;
        } else {
            this.tickDelta = tickDelta - 0.01F;
        }
        this.update();
        this.hexField.tick();
    }

    //Updates drawables that needs updating (called at init and in tick)
    private void update() {
        switch (this.handler.getMode()) {
            case HEX -> {
                this.switchModeButton.setU(242);
                this.switchModeButton.setMessage(new TranslatableText("container.tinting_table_mode.hex"));
            }
            case RGB -> {
                this.switchModeButton.setU(228);
                this.switchModeButton.setMessage(new TranslatableText("container.tinting_table_mode.rgb"));
            }
        }

        if (this.hexField.isVisible()) {
            try {
                this.handler.setColor(Color.decode(this.hexField.getText()).getRGB());
                this.isHexCodeValid = true;
            } catch (NumberFormatException ignored) {
                this.isHexCodeValid = false;
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        this.client.keyboard.setRepeatEvents(true);
        this.switchModeButton = this.addDrawableChild(new DynamicTexturedButtonWidget(this.x + 13, this.y + 30, 14, 14, 228, 0, 14, TEXTURE, 256, 256, (button) -> {
            if (this.handler.getMode() == TintingTableMode.HEX) {
                this.handler.setMode(TintingTableMode.RGB);
            } else {
                this.handler.setMode(TintingTableMode.HEX);
            }
        }));
        this.hexField = new TextFieldWidget(this.textRenderer, this.x + 68, this.y + 34, 46 , 12, new TranslatableText("container.tinting.hex"));
        this.hexField.setFocusUnlocked(false);
        this.hexField.setEditableColor(Color.decode("#CFBB93").getRGB());
        this.hexField.setDrawsBackground(false);
        this.hexField.setMaxLength(7);
        this.hexField.setText("#FFFFFF");
        this.addSelectableChild(this.hexField);
        this.setInitialFocus(this.hexField);
        this.update();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.hexField.getText();
        this.init(client, width, height);
        this.hexField.setText(string);
    }

    @Override
    public void removed() {
        super.removed();
        this.client.keyboard.setRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.player.closeHandledScreen();
        }

        return this.hexField.keyPressed(keyCode, scanCode, modifiers) || this.hexField.isActive() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, this.titleX, this.titleY, Color.decode("#51493A").getRGB());
        this.textRenderer.draw(matrices, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        if (this.switchModeButton.isHovered()) {
            renderTooltip(matrices, switchModeButton.getMessage(), mouseX, mouseY);
        }
        this.hexField.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        var xPos = (this.width - this.backgroundWidth) / 2;
        var yPos = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, xPos, yPos, 0, 0, this.backgroundWidth, this.backgroundHeight);

        //Render dye fuel bars
        var redAmount = this.handler.getRedAmount();
        var greenAmount = this.handler.getGreenAmount();
        var blueAmount = this.handler.getBlueAmount();
        this.renderDyeBar(matrices, xPos + 181, yPos, 244, redAmount);
        this.renderDyeBar(matrices, xPos + 187, yPos, 248, greenAmount);
        this.renderDyeBar(matrices, xPos + 193, yPos, 252, blueAmount);

        //Render the top left are background (settings area)
        switch (this.handler.getMode()) {
            case HEX -> {
                this.drawTexture(matrices, xPos, yPos, 124, 166, 124, 64);
                if (this.isHexCodeValid) {
                    this.drawTexture(matrices, xPos + 56, yPos + 32, 208, 10, 10, 10);
                } else {
                    this.drawTexture(matrices, xPos + 56, yPos + 32, 208, 0, 10, 10);
                }
                this.hexField.visible = true;
                this.hexField.active = true;
            }
            case RGB -> {
                this.drawTexture(matrices, xPos, yPos, 0, 166, 124, 64);
                this.hexField.visible = false;
                this.hexField.active = false;
            }
        }

        //Render the color widgets in the area mentioned above
        var color = this.handler.getColor();
        RenderSystem.setShaderColor((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F), 1.0F);
        switch (this.handler.getMode()) {
            case HEX -> this.drawTexture(matrices, xPos + 33, yPos + 30, 230, 28, 14, 14);
            case RGB -> this.drawTexture(matrices, xPos + 33, yPos + 20, 230, 42, 14, 34);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderDyeBar(MatrixStack matrices, int x, int y, int u, int amount) {
        var height = Math.floor((amount * 1.02F) / 2.0F);
        var yOffset = (51 - height) + 14;
        if (amount > 0) {
            this.drawTexture(matrices, x, y + (int) yOffset, u, 28, 4, (int) height);
        }
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 0) {
            this.hexField.setEditable(!stack.isEmpty());
            this.setFocused(this.hexField);
        }

    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }
}
