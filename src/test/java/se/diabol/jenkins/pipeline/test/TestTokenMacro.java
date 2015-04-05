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
package se.diabol.jenkins.pipeline.test;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.tokenmacro.DataBoundTokenMacro;
import org.jenkinsci.plugins.tokenmacro.MacroEvaluationException;

import java.io.IOException;

@Extension
@SuppressWarnings("UnusedDeclaration")
public class TestTokenMacro extends DataBoundTokenMacro {

    @Parameter(required=false)
    public boolean throwIO = false;

    @Parameter(required=false)
    public boolean throwEval = false;

    @Parameter(required=false)
    public boolean throwInterrupt = false;


    @Parameter(required=false)
    public boolean returnEmpty = false;

    @Parameter(required=false)
    public boolean returnNull = false;


    @Override
    public String evaluate(AbstractBuild<?, ?> context, TaskListener listener, String macroName)
            throws MacroEvaluationException, IOException, InterruptedException {

        if (throwIO) {
            throw new IOException("Could not process macro!");
        }

        if (throwEval) {
            throw new MacroEvaluationException("Could not evaluate macro!");
        }

        if (throwInterrupt) {
            throw new InterruptedException("Interrupted");
        }

        if (returnEmpty) {
            return "";
        }
        if (returnNull) {
            return null;
        }

        return "Macro Return";
    }

    @Override
    public boolean acceptsMacroName(String macroName) {
        return "TEST_MACRO".equals(macroName);
    }
}
