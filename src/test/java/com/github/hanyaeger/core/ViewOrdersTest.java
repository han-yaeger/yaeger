package com.github.hanyaeger.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewOrdersTest {

    @Test
    void expectedViewOrdersAreAvailable() {
        // Arrange & Act
        var debugger = ViewOrders.VIEW_ORDER_DEBUGGER;
        var entitySticky = ViewOrders.VIEW_ORDER_ENTITY_STICKY;
        var stickypane = ViewOrders.VIEW_ORDER_STICKYPANE;
        var scrollpane = ViewOrders.VIEW_ORDER_SCROLLPANE;
        var entityDefaultBehind = ViewOrders.VIEW_ORDER_ENTITY_DEFAULT_BEHIND;
        var entityDefault = ViewOrders.VIEW_ORDER_ENTITY_DEFAULT;

        // Assert
        assertEquals(1, debugger);
        assertEquals(10, entitySticky);
        assertEquals(1, stickypane);
        assertEquals(37, scrollpane);
        assertEquals(100, entityDefaultBehind);
        assertEquals(37, entityDefault);
    }
}
