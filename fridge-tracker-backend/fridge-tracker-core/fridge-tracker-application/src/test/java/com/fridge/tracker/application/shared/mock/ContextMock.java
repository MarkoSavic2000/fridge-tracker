package com.fridge.tracker.application.shared.mock;

import com.fridge.tracker.application.shared.context.Context;
import com.fridge.tracker.application.shared.context.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ContextMock {
    public static Context context = new Context(new User("1", "username"));
}
