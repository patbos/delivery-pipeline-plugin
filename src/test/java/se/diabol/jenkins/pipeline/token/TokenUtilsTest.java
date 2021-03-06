/*
This file is part of Delivery Pipeline Plugin.

Delivery Pipeline Plugin is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Delivery Pipeline Plugin is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Delivery Pipeline Plugin.
If not, see <http://www.gnu.org/licenses/>.
*/
package se.diabol.jenkins.pipeline.token;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.TaskListener;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.WithoutJenkins;

import java.io.IOException;

public class TokenUtilsTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Test
    public void testDecodedTemplate() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject("a");

        jenkins.getInstance().rebuildDependencyGraph();
        jenkins.setQuietPeriod(0);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        assertEquals("1.0.0.1", TokenUtils.decodedTemplate(build, "1.0.0.1"));
    }

    @Test
    public void testDecodedTemplateWithMacroEvaluationException() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject("a");

        jenkins.getInstance().rebuildDependencyGraph();
        jenkins.setQuietPeriod(0);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        assertEquals("${TEST_NESTEDX}", TokenUtils.decodedTemplate(build, "${TEST_NESTEDX}"));
    }

    @Test
    @WithoutJenkins
    public void testDecodedTemplateNoBuild() {
        assertEquals("1.0.0.1", TokenUtils.decodedTemplate(null, "1.0.0.1"));
    }

    @Test
    @WithoutJenkins
    public void testDecodedTemplateWithIOException() throws IOException, InterruptedException {
        final FreeStyleBuild mockFreeStyleBuild = mock(FreeStyleBuild.class);
        when(mockFreeStyleBuild.getEnvironment(TaskListener.NULL)).thenThrow(new IOException());

        assertEquals("", TokenUtils.decodedTemplate(mockFreeStyleBuild, "1.0.0.1"));
    }

    @Test
    @WithoutJenkins
    public void testStringIsNotEmpy() {
        assertThat(TokenUtils.stringIsNotEmpty("string"), is(true));
        assertThat(TokenUtils.stringIsNotEmpty(""), is(false));
        assertThat(TokenUtils.stringIsNotEmpty(null), is(false));
    }

    @Test(expected = IllegalAccessException.class)
    @WithoutJenkins
    public void testConstructorPrivate() throws Exception {
        TokenUtils.class.newInstance();
        fail("Utility class constructor should be private");
    }
}
