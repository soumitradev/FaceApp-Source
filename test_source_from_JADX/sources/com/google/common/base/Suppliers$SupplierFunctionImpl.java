package com.google.common.base;

enum Suppliers$SupplierFunctionImpl implements Suppliers$SupplierFunction<Object> {
    INSTANCE;

    public Object apply(Supplier<Object> input) {
        return input.get();
    }

    public String toString() {
        return "Suppliers.supplierFunction()";
    }
}
