package com.deluan.jenkins.plugins.rtc;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.TaskListener;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * @author deluan
 */
public class JazzClientTest {

    protected JazzConfiguration config;

    @Before
    public void setUp() {
        config = new JazzConfiguration();
        config.setRepositoryLocation("https://jazz/jazz");
        config.setWorkspaceName("My Workspace");
        config.setStreamName("My Stream");
        config.setUsername("user");
        config.setPassword("password");
        config.setJobWorkspace(new FilePath(new File("c:\\test")));
    }

    private JazzClient createTestableJazzClient(Launcher launcher, TaskListener listener, FilePath jobWorkspace, String jazzExecutable) {
        JazzClient client = new JazzClient(launcher, listener, jobWorkspace, jazzExecutable, config);
        return spy(client);
    }

    @Test
    public void foundSCMExecutable() {
        String installDir = "c:\\a\\bb\\ccc";
        JazzClient testClient = createTestableJazzClient(null, null, null, installDir + "\\lscm.bat");

        doReturn(true).when(testClient).canExecute(installDir + "/scm.exe");

        String scmExecutable = testClient.findSCMExecutable();

        assertThat(scmExecutable, is(installDir + "/scm.exe"));
    }

    @Test
    public void useDefaultSCMExecutable() {
        String installDir = "c:\\a\\bb\\ccc";
        JazzClient testClient = createTestableJazzClient(null, null, null, installDir + "\\lscm.bat");

        String scmExecutable = testClient.findSCMExecutable();

        assertThat(scmExecutable, is(JazzClient.SCM_CMD));
    }


}