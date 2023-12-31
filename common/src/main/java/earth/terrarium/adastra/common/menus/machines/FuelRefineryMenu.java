package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.FuelRefineryBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class FuelRefineryMenu extends MachineMenu<FuelRefineryBlockEntity> {

    public FuelRefineryMenu(int id, Inventory inventory, FuelRefineryBlockEntity entity) {
        super(ModMenus.FUEL_REFINERY.get(), id, inventory, entity);
    }

    @Override
    protected int getContainerInputEnd() {
        return 5;
    }

    @Override
    protected int getInventoryStart() {
        return 5;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 102;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new Slot(entity, 1, 12, 22));
        addSlot(new FurnaceResultSlot(player, entity, 2, 12, 52));

        addSlot(new Slot(entity, 3, 127, 22));
        addSlot(new FurnaceResultSlot(player, entity, 4, 127, 52));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 12, 22));
        addConfigSlot(new SlotConfiguration(1, 127, 22));
        addConfigSlot(new SlotConfiguration(2, 12, 52));
        addConfigSlot(new SlotConfiguration(2, 127, 52));

        addConfigSlot(new EnergyConfiguration(3, 150, 22, entity.getEnergyStorage()));
        addConfigSlot(new FluidConfiguration(4, 43, 22, entity.getFluidContainer(), 0));
        addConfigSlot(new FluidConfiguration(5, 100, 22, entity.getFluidContainer(), 1));
    }
}
