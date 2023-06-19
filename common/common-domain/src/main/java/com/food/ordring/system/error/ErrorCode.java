package com.food.ordring.system.error;

public enum ErrorCode {
    DOM_101("Order is not in correct state for initialization"),
    DOM_102("Order price must be greater than Zero"),
    DOM_103("Order price and sub total item price are not equal"),
    DOM_104("Item price is not same as product price"),
    DOM_105("Order is not in correct state for payment operation"),
    DOM_106("Order is not in correct state for approval operation"),
    DOM_107("Order is not in correct state for cancelling operation"),
    DOM_108("Order is not in correct state for cancelled operation"),
    DOM_109("Restaurant is not active"),
    DOM_110("Could not find customer"),
    DOM_111("Could not find restaurant "),
    DOM_112("Currency not available"),
    DOM_113("Unable to save order"),
    DOM_114("Order not found"),
    DOM_115(""),
    DOM_116(""),
    DOM_117(""),
    DOM_118(""),
    DOM_119(""),
    DOM_120(""),
    DOM_121(""),
    DOM_122(""),
    DOM_123(""),
    DOM_124(""),
    DOM_125(""),
    DOM_126(""),
    DOM_127(""),
    DOM_128(""),
    DOM_129(""),
    DOM_130(""),
    DOM_131("");

    private String errorDesc;
    private ErrorCode(String errorDesc){
        this.errorDesc = errorDesc;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public String getErrorCode(ErrorCode errorCode){
        return errorCode.name();
    }
}
