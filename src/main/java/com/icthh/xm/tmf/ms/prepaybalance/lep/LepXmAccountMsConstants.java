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

    private LepXmAccountMsConstants() {
        throw new UnsupportedOperationException("Prevent creation for constructor utils class");
    }

}
