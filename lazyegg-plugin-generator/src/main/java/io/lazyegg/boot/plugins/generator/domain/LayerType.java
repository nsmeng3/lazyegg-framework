package io.lazyegg.boot.plugins.generator.domain;

/**
 * 层级类型
 *
 * @author DifferentW
 */
public enum LayerType {
    Adapter(new String[]{"Controller.java", "Adapter.java", "Adaptor.java"}),
    App(new String[]{"CmdExe.java", "QryExe.java", "ServiceImpl.java"}),
    Client(new String[]{"ServiceI.java", "DTO.java", "Event.java", "Constant.java", "Cmd.java", "Qry.java",}),
    Domain(new String[]{"Domain.java", "Gateway.java"}),
    Infrastructure(new String[]{"Mapper.java", "DO.java", "GatewayImpl.java", "Config.java", "Util.java", "DbService.java", "DbServiceImpl.java"}),
    Generator(new String[]{});

    private String[] suffix;

    LayerType(String[] suffix) {
        this.suffix = suffix;
    }

    public String getLowerName() {
        return this.name().toLowerCase();
    }

    public static LayerType of(CodeFile codeFile) {
        if (codeFile.packageName().contains("domain")) {
            return Domain;
        }
        if (codeFile.packageName().contains("dto")) {
            return Client;
        }
        for (LayerType value : LayerType.values()) {
            String suffix = codeFile.fileName();
            String[] suffixs = value.getSuffix();
            for (String suf : suffixs) {
                boolean b = suffix.endsWith(suf);
                if (b) {
                    return value;
                }
            }
        }
        return Generator;

    }

    public String[] getSuffix() {
        return suffix;
    }
}
