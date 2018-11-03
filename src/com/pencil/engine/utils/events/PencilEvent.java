package com.pencil.engine.utils.events;

import com.pencil.engine.utils.action.PencilAction;

/**
 * @author Matthias Kovacic
 * Represents an event caused by Pencil.
 */
public abstract interface PencilEvent {

    PencilAction getAction();

}