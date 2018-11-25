package com.pencil.engine.utils.events;

import com.pencil.engine.utils.action.PencilAction;
import org.bukkit.entity.Player;

/**
 * @author Matthias Kovacic
 * Represents an event caused by Pencil.
 */
public abstract interface PencilEvent {

    Player getPlayer();

    PencilAction getAction();

}