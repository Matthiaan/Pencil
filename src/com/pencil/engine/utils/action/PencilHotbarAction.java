package com.pencil.engine.utils.action;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PencilHotbarAction implements PencilAction {

    //TODO: This can cause subsequent actions, these should be all linked!
    private ItemStack item;

    public PencilHotbarAction(ItemStack item) {
        this.item = item;
    }

    public PencilAction.ActionType getActionType() {
        return ActionType.INTERFACE;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public void undo(Player player) {}

    @Override
    public String toString() {
        return "[ActionType -> " + getActionType().toString() + ",Undoable -> " + isUndoable() + "] - Item -> " + item.getItemMeta().getDisplayName();
    }
}
