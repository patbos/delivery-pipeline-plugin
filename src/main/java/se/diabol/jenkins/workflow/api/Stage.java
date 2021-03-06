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
package se.diabol.jenkins.workflow.api;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.util.List;

public class Stage {

    public final String id;
    public final String name;
    public final String status;
    public final DateTime startTimeMillis;
    public final Long durationMillis;

    public Stage(String id,
                 String name,
                 String status,
                 DateTime startTimeMillis,
                 Long durationMillis) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.startTimeMillis = startTimeMillis;
        this.durationMillis = durationMillis;
    }

    public static long getDurationOfStageFromRun(Run previousRun, Stage currentStage) {
        Stage previouslyRunStage = previousRun.getStageByName(currentStage.name);
        if (previouslyRunStage == null || previouslyRunStage.durationMillis == null) {
            return -1L;
        }
        return previouslyRunStage.durationMillis;
    }

    public static long getDurationOf(List<Stage> stages) {
        long result = 0;
        if (stages != null) {
            for (se.diabol.jenkins.workflow.api.Stage stage : stages) {
                result = result + stage.durationMillis;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Stage stage = (Stage) other;
        return Objects.equal(id, stage.id)
                && Objects.equal(name, stage.name)
                && Objects.equal(status, stage.status)
                && Objects.equal(startTimeMillis, stage.startTimeMillis)
                && Objects.equal(durationMillis, stage.durationMillis);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, status, startTimeMillis, durationMillis);
    }

    @Override
    public String toString() {
        return "Stage{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", status='" + status + '\''
                + '}';
    }
}
