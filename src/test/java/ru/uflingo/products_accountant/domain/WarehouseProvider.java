package ru.uflingo.products_accountant.domain;

import ru.uflingo.products_accountant.domain.warehouse.Warehouse;

public class WarehouseProvider {
    public static final long USER_ID = 3L;
    public static Warehouse getFirstWarehouse() {
        return new Warehouse().setUserId(USER_ID).setName("firstWarehouseName").setDefault(true).setDeleted(false);
    }
    public static Warehouse getSecondWarehouse() {
        return new Warehouse().setUserId(USER_ID).setName("secondWarehouseName").setDefault(false).setDeleted(false);
    }
    public static Warehouse getDeletedWarehouse() {
        return new Warehouse().setUserId(USER_ID).setName("deletedWarehouse").setDefault(false).setDeleted(true);
    }
}
