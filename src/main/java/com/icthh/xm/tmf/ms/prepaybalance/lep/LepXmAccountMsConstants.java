package com.icthh.xm.tmf.ms.prepaybalance.lep;

/**
 * The {@link LepXmAccountMsConstants} class.
 */
public final class LepXmAccountMsConstants {

    public static final String BINDING_KEY_COMMONS = "commons";
    public static final String BINDING_KEY_SERVICES = "services";
    public static final String BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERVICE = "tenantConfigService";
    public static final String BINDING_SUB_KEY_PERMISSION_SERVICE = "permissionService";
    public static final String BINDING_KEY_TEMPLATES = "templates";
    public static final String BINDING_SUB_KEY_TEMPLATE_REST = "rest";
    public static final String BINDING_SUB_KEY_TEMPLATE_KAFKA  = "kafka";
    public static final String BINDING_SUB_KEY_SERVICE_SEPARATE_TRANSACTION_EXECUTOR = "separateTransactionExecutor";
    public static final String BINDING_KEY_JDBC = "jdbc";
    public static final String BINDING_KEY_ASYNC_EXECUTOR = "asyncExecutor";
    public static final String BINDING_SUB_KEY_SERVICE_MAIL = "mailService";

    private LepXmAccountMsConstants() {
        throw new UnsupportedOperationException("Prevent creation for constructor utils class");
    }

}
