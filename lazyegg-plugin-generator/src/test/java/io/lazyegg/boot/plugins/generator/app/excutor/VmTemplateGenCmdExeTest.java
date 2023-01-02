package io.lazyegg.boot.plugins.generator.app.excutor;

import io.lazyegg.boot.plugins.generator.client.VmTemplateGenContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VmTemplateGenCmdExeTest {

    private VmTemplateGenCmdExe vmTemplateGenCmdExe;
    private VmTemplateGenContext vmTemplateGenContext;

    @BeforeEach
    void setUp() {
        vmTemplateGenCmdExe = new VmTemplateGenCmdExe();
        vmTemplateGenContext = new VmTemplateGenContext();
    }

    @Test
    void testExecute() {
        vmTemplateGenCmdExe.execute();
    }
}
