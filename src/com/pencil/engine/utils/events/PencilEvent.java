package com.pencil.engine.utils.events;

import com.pencil.engine.utils.action.PencilAction;

public abstract interface PencilEvent {

    PencilAction getAction();

}