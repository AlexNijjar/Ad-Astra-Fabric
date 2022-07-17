package com.github.alexnijjar.beyond_earth.gui;

import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.LargeVehicleScreenHandler;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public record LargeVehicleScreenHandlerFactory(VehicleEntity vehicle) implements ExtendedScreenHandlerFactory {

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(vehicle.getId());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new LargeVehicleScreenHandler(syncId, inventory, vehicle);
    }

    @Override
    public Text getDisplayName() {
        return vehicle.getDisplayName();
    }
}