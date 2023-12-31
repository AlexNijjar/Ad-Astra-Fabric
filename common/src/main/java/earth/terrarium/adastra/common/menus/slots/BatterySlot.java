package earth.terrarium.adastra.common.menus.slots;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

public class BatterySlot extends ImageSlot {

    public static final ResourceLocation BATTERY_SLOT_ICON = new ResourceLocation(AdAstra.MOD_ID, "item/icons/battery_slot_icon");
    public static final ResourceLocation BATTERY_SLOT = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/slots/battery.png");

    public BatterySlot(Container container, int slot) {
        super(container, slot, 0, 0, BATTERY_SLOT_ICON);
    }

    @Override
    public ResourceLocation getSlotTexture() {
        return BATTERY_SLOT;
    }
}
