package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class EnergyContainerMachineBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    protected WrappedBlockEnergyContainer energyContainer;
    private long lastEnergy;
    private long energyDifference;

    public EnergyContainerMachineBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state, containerSize);
    }

    public ChargeSlotType getChargeSlotType() {
        return ChargeSlotType.POWER_MACHINE;
    }

    @Override
    public void internalServerTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        super.internalServerTick(level, time, state, pos);
        switch (getChargeSlotType()) {
            case POWER_ITEM -> insertBatterySlot();
            case POWER_MACHINE -> extractBatterySlot();
        }
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        var energy = this.getEnergyStorage();
        this.energyDifference = energy.getStoredEnergy() - this.lastEnergy;
        this.lastEnergy = energy.getStoredEnergy();
    }

    public long energyDifference() {
        return this.energyDifference;
    }

    public void extractBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(holder, this, null, energyContainer.maxInsert(), false);
        if (holder.isDirty()) {
            this.setItem(0, holder.getStack());
        }
    }

    public void insertBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(this, null, holder, energyContainer.maxExtract(), false);
        if (holder.isDirty()) {
            this.setItem(0, holder.getStack());
        }
    }

    public enum ChargeSlotType {
        NONE,
        POWER_MACHINE,
        POWER_ITEM
    }
}